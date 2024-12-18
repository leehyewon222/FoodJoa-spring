package com.foodjoa.mealkit.service;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.foodjoa.mealkit.dao.MealkitDAO;
import com.foodjoa.mealkit.vo.MealkitCartVO;
import com.foodjoa.mealkit.vo.MealkitReviewVO;
import com.foodjoa.mealkit.vo.MealkitVO;
import com.foodjoa.mealkit.vo.MealkitWishListVO;

import Common.FileIOController;
import Common.StringParser;

@Service
public class MealkitService {
	
	@Autowired
	private MealkitDAO mealkitDAO;
	@Autowired
	private MealkitVO mealkitVO;
	@Autowired
	private MealkitWishListVO wishlistVO;
	@Autowired
	private MealkitCartVO cartVO;
	
	public List<Map<String, Object>> selectMealkitsList(int category) {
		mealkitVO = new MealkitVO();
		mealkitVO.setCategory(category);
		
		return mealkitDAO.selectMealkitsList(mealkitVO);
	}

	public String getCategoryName(int category) {

		String strCategory = "";
		
		if(category == 0){ strCategory = "전체 밀키트 게시글"; }
		else if (category == 1){ strCategory = "한식 밀키트 게시글"; }
		else if (category == 2){ strCategory = "일식 밀키트 게시글"; }
		else if (category == 3){ strCategory = "중식 밀키트 게시글"; }
		else if (category == 4){ strCategory = "양식 밀키트 게시글"; }
		
		return strCategory;
	}

	public MealkitVO selectMealkitInfo(int no) {
		return mealkitDAO.selectMealkitInfo(no);
	}

	public List<Object> selectReviewsInfo(int no) {
		mealkitVO = new MealkitVO();
		mealkitVO.setNo(no);
		
		return mealkitDAO.selectReviewsInfo(mealkitVO);
	}

	public int selectWishlist(int no, String id) {
		wishlistVO = new MealkitWishListVO();
		wishlistVO.setId(id);
		wishlistVO.setMealkitNo(no);
		
		return mealkitDAO.selectMealkitWishlist(wishlistVO);
	}

	public List<Map<String, Object>> selectMyMealkitsList(String id) {
		mealkitVO = new MealkitVO();
		mealkitVO.setId(id);
		
		return mealkitDAO.selectMyMealkitsList(mealkitVO);
	}

	public MealkitReviewVO selectMyReviewInfo(int no) {
		return mealkitDAO.selectMyReviewInfo(no);
	}

	public int processMealkitWrite(MealkitVO mealkitVO, MultipartHttpServletRequest multipartRequest) throws Exception {
		
		int result = mealkitDAO.insertMealkit(mealkitVO);
		
		if (result <= 0) return 0;
		
		String imagesPath = new ClassPathResource("").getFile().getParentFile().getParent()
				+ File.separator + "src" + File.separator + "main" + File.separator + "webapp" 
				+ File.separator + "resources" + File.separator + "images" + File.separator;
		
		String tempPath = imagesPath + "temp" + File.separator;

		File tempDir = new File(tempPath);
		
		if (!tempDir.exists()) {
			tempDir.mkdirs();
        }
		
		Iterator<String> fileNames = multipartRequest.getFileNames();
		
		while (fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			
			if (mFile.getSize() != 0) {
				mFile.transferTo(new File(tempPath + mFile.getOriginalFilename()));	
			}			
		}
		
		int no = mealkitDAO.selectRecentMealkit(mealkitVO).getNo();
		
		List<String> pictures = StringParser.splitString(mealkitVO.getPictures());
		String destinationPath = imagesPath + "mealkit" + File.separator + "thumbnails" 
				+ File.separator + no + File.separator;
		
		for (String picture : pictures) {
			FileIOController.moveFile(tempPath, destinationPath, picture);
		}
		
		return no;
	}

	public int processMealkitUpdate(MealkitVO mealkitVO, MultipartHttpServletRequest multipartRequest) 
		throws Exception{
		
		String originSelectedPictures = multipartRequest.getParameter("origin_selected_pictures");
		String pictures = mealkitVO.getPictures();
		
		mealkitVO.setPictures(originSelectedPictures + pictures);
		
		int result = mealkitDAO.updateMealkit(mealkitVO);
		
		if (result <= 0) return result;		
		
		String imagesPath = new ClassPathResource("").getFile().getParentFile().getParent()
				+ File.separator + "src" + File.separator + "main" + File.separator + "webapp" 
				+ File.separator + "resources" + File.separator + "images" + File.separator;
		
		String tempPath = imagesPath + "temp" + File.separator;
		String destinationPath = imagesPath + "mealkit" + File.separator +
				"thumbnails" + File.separator + mealkitVO.getNo();
		
		File tempDir = new File(tempPath);
		
		if (!tempDir.exists()) {
			tempDir.mkdirs();
        }
		
		Iterator<String> fileNames = multipartRequest.getFileNames();
		
		while (fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			
			if (mFile.getSize() != 0) {
				mFile.transferTo(new File(tempPath + mFile.getOriginalFilename()));
			}
		}
		
		List<String> originFileNames = StringParser.splitString(multipartRequest.getParameter("origin_pictures"));
		List<String> originSelectedFileNames = StringParser.splitString(originSelectedPictures);
		
		for (String fileName : originFileNames) {
			if (!originSelectedFileNames.contains(fileName)) {
				FileIOController.deleteFile(destinationPath, fileName);
			}
		}
		
		List<String> picturesList = StringParser.splitString(pictures);
		
        for (String picture : picturesList) {
    		FileIOController.moveFile(tempPath, destinationPath, picture);
        }
        
		result = mealkitVO.getNo();
		
		return result;
	}
	
	public int deleteMealkit(int no) throws Exception {
		int result = mealkitDAO.deleteMealkit(no);
		
		if (result > 0) {
			String path = new ClassPathResource("").getFile().getParentFile().getParent()
					+ File.separator + "src" + File.separator + "main" + File.separator + "webapp" 
					+ File.separator + "resources" + File.separator + "images" + File.separator + "mealkit" 
					+ File.separator + "thumbnails" + File.separator + no;
			
			String reviewPath = new ClassPathResource("").getFile().getParentFile().getParent()
					+ File.separator + "src" + File.separator + "main" + File.separator + "webapp" 
					+ File.separator + "resources" + File.separator + "images" + File.separator + "mealkit" 
					+ File.separator + "reviews" + File.separator + no;
			
			FileIOController.deleteDirectory(path);
			FileIOController.deleteDirectory(reviewPath);
		}
		
		return result;
	}
	

	public int processReviewWrite(MealkitReviewVO reviewVO, MultipartHttpServletRequest multipartRequest) throws Exception{
		
		int result = mealkitDAO.insertReview(reviewVO);
		
		if (result <= 0) return 0;
		
		String imagesPath = new ClassPathResource("").getFile().getParentFile().getParent()
				+ File.separator + "src" + File.separator + "main" + File.separator + "webapp" 
				+ File.separator + "resources" + File.separator + "images" + File.separator;
		
		String tempPath = imagesPath + "temp" + File.separator;

		File tempDir = new File(tempPath);
		
		if (!tempDir.exists()) {
			tempDir.mkdirs();
        }
		
		Iterator<String> fileNames = multipartRequest.getFileNames();
		
		while (fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			
			if (mFile.getSize() != 0) {
				mFile.transferTo(new File(tempPath + mFile.getOriginalFilename()));	
			}			
		}
		
		int no = reviewVO.getMealkitNo();
		
		List<String> pictures = StringParser.splitString(reviewVO.getPictures());
		String destinationPath = imagesPath + "mealkit" + File.separator + "reviews" + File.separator
				+ reviewVO.getMealkitNo() + File.separator + reviewVO.getId();
		
		for (String picture : pictures) {
			FileIOController.moveFile(tempPath, destinationPath, picture);
		}
		
		return no;
	}

	public int processReviewUpdate(MealkitReviewVO reviewVO, MultipartHttpServletRequest multipartRequest) 
		throws Exception{
		
		String originSelectedPictures = multipartRequest.getParameter("origin_selected_pictures");
		String pictures = reviewVO.getPictures();
		
		reviewVO.setPictures(originSelectedPictures + pictures);
		
		mealkitDAO.updateReview(reviewVO);
		
		String imagesPath = new ClassPathResource("").getFile().getParentFile().getParent()
				+ File.separator + "src" + File.separator + "main" + File.separator + "webapp" 
				+ File.separator + "resources" + File.separator + "images" + File.separator;
		
		String tempPath = imagesPath + "temp" + File.separator;
		String destinationPath = imagesPath + "mealkit" + File.separator +
				"reviews" + File.separator + reviewVO.getMealkitNo() + File.separator + reviewVO.getId();
		
		File tempDir = new File(tempPath);
		
		if (!tempDir.exists()) {
			tempDir.mkdirs();
        }
		
		Iterator<String> fileNames = multipartRequest.getFileNames();
		
		while (fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			
			if (mFile.getSize() != 0) {
				mFile.transferTo(new File(tempPath + mFile.getOriginalFilename()));
			}
		}
		
		List<String> originFileNames = StringParser.splitString(multipartRequest.getParameter("origin_pictures"));
		List<String> originSelectedFileNames = StringParser.splitString(originSelectedPictures);
		
		for (String fileName : originFileNames) {
			if (!originSelectedFileNames.contains(fileName)) {
				FileIOController.deleteFile(destinationPath, fileName);
			}
		}
		
		List<String> picturesList = StringParser.splitString(pictures);
		
        for (String picture : picturesList) {
    		FileIOController.moveFile(tempPath, destinationPath, picture);
        }
        
        return reviewVO.getMealkitNo();
	}

	public int deleteReview(int no, int mealkitNo, String id) throws Exception{
		
		int result = mealkitDAO.deleteReview(no);

		if (result > 0) {
			String reviewPath = new ClassPathResource("").getFile().getParentFile().getParent()
					+ File.separator + "src" + File.separator + "main" + File.separator + "webapp" 
					+ File.separator + "resources" + File.separator + "images" + File.separator + "mealkit" 
					+ File.separator + "reviews" + File.separator + mealkitNo + File.separator + id;
			
			FileIOController.deleteDirectory(reviewPath);
		}
		
		return result;
	}

	public List<Map<String, Object>> selectSearchList(String key, String word) {
		return mealkitDAO.selectSearchList(key, word);
	}

	public int processWishlist(int no, String id) {
		
		wishlistVO = new MealkitWishListVO();
		wishlistVO.setMealkitNo(no);
		wishlistVO.setId(id);
		
		int result = mealkitDAO.selectMealkitWishlist(wishlistVO);
		if (result > 0) {
	        return -1;
	    }
		
		return mealkitDAO.insertMealkitWishlist(wishlistVO);
	}

	public int processCart(int no, int quantity, String id) {

		cartVO = new MealkitCartVO();
		cartVO.setMealkitNo(no);
		cartVO.setQuantity(quantity);
		cartVO.setId(id);
		
		int result = mealkitDAO.selectMealkitCart(cartVO);
		System.out.println(result);
		
		if(result > 0) {
			return mealkitDAO.updateMealkitCart(cartVO);
		} else {
			return mealkitDAO.insertMealkitCart(cartVO);
		}
	}

}
