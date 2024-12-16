package com.foodjoa.member.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.foodjoa.mealkit.dao.MealkitDAO;
import com.foodjoa.member.dao.MemberDAO;
import com.foodjoa.member.vo.MemberVO;
import com.foodjoa.recipe.dao.RecipeDAO;
import com.foodjoa.recipe.vo.RecipeWishListVO;
import com.foodjoa.mealkit.vo.MealkitWishListVO;


import Common.FileIOController;

@Service("memberService")
@Transactional(propagation = Propagation.REQUIRED)
public class MemberService {
    
    @Autowired
    private MemberDAO memberDAO;
    
    @Autowired
    private RecipeDAO recipeDAO;
    
    @Autowired
    private MealkitDAO mealkitDAO;

    public int insertMember(MemberVO memberVO, MultipartHttpServletRequest multipartRequest, HttpSession session) throws Exception {
        
        String userId = (String) session.getAttribute("userId");
        
        Iterator<String> fileNames = multipartRequest.getFileNames();
        
        String imagesPath = new ClassPathResource("").getFile().getParentFile().getParent()
                + File.separator + "src" + File.separator + "main" + File.separator + "webapp" 
                + File.separator + "resources" + File.separator + "images" + File.separator;
        
        System.out.println("imagesPath : " + imagesPath);
        
        String tempPath = imagesPath + "temp" + File.separator;

        File tempDir = new File(tempPath);
        
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        
        String originalFileName = "";
        if (fileNames.hasNext()) {
            String fileName = fileNames.next();
            MultipartFile mFile = multipartRequest.getFile(fileName);
            originalFileName = mFile.getOriginalFilename();
            
            if (mFile.getSize() != 0) {
                File file = new File(tempPath + originalFileName);
                mFile.transferTo(file);  // 첫 번째 파일만 저장
                
                int result = memberDAO.insertMember(memberVO);

                if (result <= 0) {
                    return result;  // 성공하지 못한 경우 result 반환
                }
                
                String destinationPath = imagesPath + "member" + File.separator + "userProfiles" 
                        + File.separator + userId;
                
                FileIOController.moveFile(tempPath, destinationPath, originalFileName);
                
                return result;  // 성공한 경우 result 반환
            }
        }
        
        // 파일이 없거나 예외가 발생한 경우에도 int 타입 값 반환
        return 0;  // 기본적으로 0 반환 (파일이 없거나 오류가 발생한 경우)
    }


    	
    	
    	
    	
    	

		  
		 
    

    //--------------------------------------------회원가입 끝
    
	public boolean isUserExists(String userId) {
		// DB에서 아이디가 존재하는지 확인하는 로직
		return memberDAO.isUserExists(userId);
	
	}
	
	public boolean deleteMember(String readonlyId) {
	    try {
	        // 1. DB에서 회원의 프로필 파일명 가져오기
	        String profileFileName = memberDAO.getProfileFileName(readonlyId);

	        // 2. 프로필 사진이 존재하면 파일 시스템에서 삭제
	        if (profileFileName != null && !profileFileName.isEmpty()) {
	            String uploadDir = "C:\\workspace_FoodJoa\\FoodJoa\\src\\main\\webapp\\resources\\images\\member\\userProfiles\\";
	            File profileFile = new File(uploadDir + readonlyId + File.separator + profileFileName);

	            if (profileFile.exists()) {
	                boolean fileDeleted = profileFile.delete();
	                if (!fileDeleted) {
	                    // 파일 삭제 실패 시 로깅하거나 예외 처리
	                    System.out.println("파일 삭제 실패: " + profileFile.getAbsolutePath());
	                }
	            }
	        }

	        // 3. 회원의 프로필 폴더도 삭제
	        String userFolderPath = "C:\\workspace_FoodJoa\\FoodJoa\\src\\main\\webapp\\resources\\images\\member\\userProfiles\\" + readonlyId;
	        File userFolder = new File(userFolderPath);
	        if (userFolder.exists() && userFolder.isDirectory()) {
	            File[] files = userFolder.listFiles();
	            if (files != null && files.length == 0) {
	                // 폴더 안에 파일이 없으면 폴더 삭제
	                boolean folderDeleted = userFolder.delete();
	                if (!folderDeleted) {
	                    // 폴더 삭제 실패 시 로깅하거나 예외 처리
	                    System.out.println("폴더 삭제 실패: " + userFolder.getAbsolutePath());
	                }
	            }
	        }

	        // 4. DB에서 회원 삭제
	        int result = memberDAO.deleteMemberById(readonlyId);

	        // 5. 삭제 성공 여부 반환
	        return result == 1;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false; // 예외 발생 시 false 반환
	    }
	}

	public HashMap<String, Object> getWishListInfos(String userId) {
	   
	    HashMap<String, Object> wishListInfos = new HashMap<>();
	    
	    List<RecipeWishListVO> recipeWishlists = recipeDAO.selectWishListById(userId);
	    List<RecipeWishListVO> mealkitWishlists = mealkitDAO.selectWishListById(userId);
	    
	    wishListInfos.put("recipeWishlists", recipeWishlists);
	    wishListInfos.put("mealkitWishlists", mealkitWishlists);

	    return wishListInfos;
	}
	
	public int deleteWishlist(int wishType, int no) {
		return (wishType == 0) ? 
				recipeDAO.deleteWishlist(no) : mealkitDAO.deleteWishlist(no);
	}
	
	public HashMap<String, Object> getRecentViews(String userId) {
		HashMap<String, Object> recentViewInfos = new HashMap<>();
	    
	    List<RecipeWishListVO> recentViewRecipe = recipeDAO.selectRecentById(userId);
	    List<RecipeWishListVO> recentViewMealkit = mealkitDAO.selectRecentById(userId);
	    
	    recentViewInfos.put("recentViewRecipe", recentViewRecipe);
	    recentViewInfos.put("recentViewMealkit", recentViewMealkit);

	    return recentViewInfos;
	}
	
	public MemberVO getMember(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("userId");
		return memberDAO.selectMember(id);
	}

	 public ArrayList<Integer> getCountOrderDelivered(HttpServletRequest request) {
	 
		 return mealkitDAO.selectCountOrderDelivered((String) request.getSession().getAttribute("userId"));
	    }

	public MemberVO getMemberById(String id) {

		return memberDAO.selectMember(id);
	}

	public ArrayList<Integer> getCountOrderDelivered(String userId) {
		return memberDAO.selectCountOrderDelivered(userId);
	}

	public ArrayList<Integer> getCountOrderSended(String userId) {
		return memberDAO.selectCountOrderSended(userId);
	}


}