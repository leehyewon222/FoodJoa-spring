package com.foodjoa.share.service;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.foodjoa.recipe.vo.RecipeReviewVO;
import com.foodjoa.share.dao.ShareDAO;
import com.foodjoa.share.vo.ShareVO;

import Common.FileIOController;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShareService {

	@Autowired
	private ShareDAO shareDAO;

	public List<ShareVO> getShareList() {
		return shareDAO.selectShareList();
	}

	public int processWrite(ShareVO shareVO, MultipartHttpServletRequest multipartRequest) throws Exception {
		
		int result = shareDAO.insertShare(shareVO);
		
		if (result <= 0) return result;
		
		int shareNo = shareDAO.selectLatestShareNo();
		
		String imagesPath = new ClassPathResource("").getFile().getParentFile().getParent()
				+ File.separator + "src" + File.separator + "main" + File.separator + "webapp" 
				+ File.separator + "resources" + File.separator + "images" + File.separator;
		
		String tempPath = imagesPath + "temp" + File.separator;
		
		File tempDir = new File(tempPath);
		
		if (!tempDir.exists()) {
			tempDir.mkdirs();
        }

		Iterator<String> fileNames = multipartRequest.getFileNames();
		String originalFileName = "";
		
		while (fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			originalFileName = mFile.getOriginalFilename();
			
			if (mFile.getSize() != 0) {
				mFile.transferTo(new File(tempPath + originalFileName));
			}			
		}
		
		String destinationPath = imagesPath + "share" + File.separator + "thumbnails" 
				+ File.separator + shareNo + File.separator;
		
		FileIOController.moveFile(tempPath, destinationPath, originalFileName);
		
		return shareNo;
	}

	public ShareVO getShare(int no) {
		return shareDAO.selectShare(no);
	}

	public int processShareUpdate(ShareVO shareVO, MultipartHttpServletRequest multipartRequest,
			String originThumbnail) throws Exception {
		
		String imagesPath = new ClassPathResource("").getFile().getParentFile().getParent()
				+ File.separator + "src" + File.separator + "main" + File.separator + "webapp" 
				+ File.separator + "resources" + File.separator + "images" + File.separator;
		
		String tempPath = imagesPath + "temp" + File.separator;
		
		File tempDir = new File(tempPath);
		
		if (!tempDir.exists()) {
			tempDir.mkdirs();
        }

		Iterator<String> fileNames = multipartRequest.getFileNames();
		String originalFileName = "";
		
		while (fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			originalFileName = mFile.getOriginalFilename();
			
			if (mFile.getSize() != 0) {
				mFile.transferTo(new File(tempPath + originalFileName));
			}			
		}
		
		boolean flag = false;
		if (originalFileName == null || originalFileName.length() <= 0 || originalFileName.equals("")) {
			flag = true;
			originalFileName = originThumbnail;
		}
		
		shareVO.setThumbnail(originalFileName);
		
		int result = shareDAO.updateShare(shareVO);
		
		if (result <= 0) {			
			FileIOController.deleteFile(tempPath, originalFileName);
			return  result;
		}
		
		if (!flag) {
			String destinationPath = imagesPath + "share" + File.separator + "thumbnails" 
					+ File.separator + shareVO.getNo() + File.separator;
			
			FileIOController.deleteFile(destinationPath, originThumbnail);
			FileIOController.moveFile(tempPath, destinationPath, originalFileName);
		}
		
		return result;
	}

	public int processShareDelete(String no) throws Exception {
		
		ShareVO shareVO = new ShareVO();
		shareVO.setNo(Integer.parseInt(no));
		
		int result = shareDAO.deleteShare(shareVO);
		
		if (result > 0) {
			String imagesPath = new ClassPathResource("").getFile().getParentFile().getParent()
					+ File.separator + "src" + File.separator + "main" + File.separator + "webapp" 
					+ File.separator + "resources" + File.separator + "images" + File.separator;
			
			String destinationPath = imagesPath  + "share" + File.separator +
					"thumbnails" + File.separator + String.valueOf(no);
			
			FileIOController.deleteDirectory(destinationPath);
		}
		return result;
	}

	public List<ShareVO> getSearchedShares(String key, String word) {
		
		word = "%" + word + "%";
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("key", key);
		params.put("word", word);
		
		return shareDAO.selectSearchedShares(params);
	}
}	