package com.foodjoa.member.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.foodjoa.mealkit.dao.MealkitDAO;
import com.foodjoa.member.dao.MemberDAO;
import com.foodjoa.member.vo.MemberVO;
import com.foodjoa.recipe.dao.RecipeDAO;

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

    public int insertMember(MemberVO memberVO, MultipartHttpServletRequest request) throws IOException {
        // 업로드할 디렉토리 경로 설정
        String uploadDir = "C:\\workspace_FoodJoa\\FoodJoa\\src\\main\\webapp\\resources\\images\\";  // application.properties에서 설정한 경로를 사용하거나, 직접 설정
        
        // 파일 업로드 처리
        MultipartFile profileFile = request.getFile("profileFile");

        // 회원 정보 생성 (프로필 파일명 포함)
        String profileFileName = profileFile != null ? profileFile.getOriginalFilename() : null;
        memberVO.setProfile(profileFileName);
        
        int result = memberDAO.insertMember(memberVO);
        
        if (result <= 0) return result;
        
        // 회원 정보 DB 등록 성공
        String userId = memberVO.getId();
        String descPath = uploadDir + "member" + File.separator + "userProfiles" + File.separator + userId;
        
        File userDir = new File(descPath);
        if (!userDir.exists()) {
            userDir.mkdirs(); // 유저 폴더 없으면 생성
        }
        
        if (profileFile != null && !profileFile.isEmpty()) {
            // 실제 저장될 파일 경로
            File destFile = new File(descPath + File.separator + profileFileName);

            profileFile.transferTo(destFile);
        }
        
        return result;
    }

    //--------------------------------------------회원가입 끝
    
	public boolean isUserExists(String userId) {
		// DB에서 아이디가 존재하는지 확인하는 로직
		return memberDAO.isUserExists(userId);
	
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