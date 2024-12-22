package com.foodjoa.together.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.foodjoa.together.dao.TogetherDAO;
import com.foodjoa.together.vo.TogetherJoinVO;
import com.foodjoa.together.vo.TogetherReplyVO;
import com.foodjoa.together.vo.TogetherVO;

import Common.FileIOController;
import Common.StringParser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class TogetherService {

	@Autowired
	private TogetherDAO togetherDAO;

	public List<TogetherVO> getTogethers() {
		return togetherDAO.selectTogethers();
	}

	public TogetherVO getTogether(int no) {
		
		return (no <= 0) ? new TogetherVO() : togetherDAO.selectTogether(no);
	}

	public int processTogetherEdit(TogetherVO togetherVO, 
			MultipartHttpServletRequest multipartRequest,
			String originPictures,
			String originSelectedPictures) throws Exception {
		
		String imagesPath = new ClassPathResource("").getFile().getParentFile().getParent()
				+ File.separator + "src" + File.separator + "main" + File.separator + "webapp" 
				+ File.separator + "resources" + File.separator + "images" + File.separator;
		
		String tempPath = imagesPath + "temp" + File.separator;

		File tempDir = new File(tempPath);
		
		if (!tempDir.exists()) {
			tempDir.mkdirs();
        }

		Iterator<String> fileNames = multipartRequest.getFileNames();
		ArrayList<String> originalFileNames = new ArrayList<String>();
		
		while (fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			
			String originalFileName = mFile.getOriginalFilename();
			originalFileNames.add(originalFileName);
			
			if (mFile.getSize() != 0) {
				mFile.transferTo(new File(tempPath + originalFileName));
			}			
		}
		
		// XSS 공격 대비
		togetherVO.setContents(StringParser.escapeHtml(togetherVO.getContents()));
		
		// DB 처리 후 열람 할 together no
		int readNo = 0;
		
		if (togetherVO.getNo() <= 0) {
			int result = togetherDAO.insertTogether(togetherVO);
			
			if (result <= 0) return readNo;
			
			readNo = togetherDAO.selectLatestNo();			

			String destinationPath = imagesPath + "together" + File.separator + "pictures" 
					+ File.separator + readNo + File.separator;
			
			for (String name : originalFileNames) {
				FileIOController.moveFile(tempPath, destinationPath, name);
			}
			
		}
		else {			
			readNo = togetherVO.getNo();
			
			int result = togetherDAO.updateTogether(togetherVO);
			
			if (result <= 0) return readNo;
			
			String destinationPath = imagesPath + "together" + File.separator + "pictures" 
					+ File.separator + readNo + File.separator;
			
			System.out.println("originPictures : " + originPictures);
			System.out.println("originSelectedPictures : " + originSelectedPictures);
			
			List<String> originPictureList = StringParser.splitString(originPictures);
			List<String> originSelectedPictureList = StringParser.splitString(originSelectedPictures);
			
			for(String picture : originPictureList) {
				if (!originSelectedPictureList.contains(picture)) {
					FileIOController.deleteFile(destinationPath, picture);
				}
			}
			
			for (String name : originalFileNames) {
				FileIOController.moveFile(tempPath, destinationPath, name);
			}
		}
		
		return readNo;
	}

	public HashMap<String, Object> processTogetherRead(int no, String userId) {
		
		HashMap<String, Object> togetherInfo = new HashMap<String, Object>();

		// 모임 정보 가져오기
		TogetherVO together = togetherDAO.selectTogether(no);
		
		if (together != null) {
			togetherDAO.updateTogetherViews(no);
		}
		
		togetherInfo.put("together", together);
		
		// 글의 댓글들 가져오기
		List<TogetherReplyVO> replies = togetherDAO.selectReplies(no);
		
		togetherInfo.put("replies", replies);
		
		// 참여 수 가져오기
		int joinCount = togetherDAO.selectJoinCount(no);
		
		togetherInfo.put("joinCount", joinCount);
		
		// 참여 여부 확인하기
		int isExistJoin = togetherDAO.selectJoinCountById(no, userId);
		
		togetherInfo.put("isExistJoin", isExistJoin);
		
		return togetherInfo;
	}

	public int deleteTogether(int no) {
		return togetherDAO.deleteTogether(no);
	}

	public HashMap<Integer, List<TogetherJoinVO>> getJoins() {
		
		HashMap<Integer, List<TogetherJoinVO>> classifiedJoin = new HashMap<Integer, List<TogetherJoinVO>>();
		
		List<TogetherJoinVO> joins = togetherDAO.selectJoins();
		
		for (TogetherJoinVO join : joins) {
			if (!classifiedJoin.containsKey(join.getTogetherNo())) {
				classifiedJoin.put(join.getTogetherNo(), new ArrayList<TogetherJoinVO>());
			}
			
			classifiedJoin.get(join.getTogetherNo()).add(join);
		}
		
		return classifiedJoin;
	}

	public int processTogetherJoin(int no, String id) {

		int result = togetherDAO.selectJoinCountById(no, id);
		
		if (result > 0) return 2;
		
		result = togetherDAO.insertJoin(no, id);
		
		return result;
	}

	public int addReply(TogetherReplyVO replyVO) {
		
		replyVO.setContents(StringParser.escapeHtml(replyVO.getContents()));
		
		return (replyVO.getNo() <= 0) ?
				togetherDAO.insertReply(replyVO) :
				togetherDAO.updateReply(replyVO);	
	}

	public int deleteReply(int no) {
		return togetherDAO.deleteReply(no);
	}

	public int deleteJoin(TogetherJoinVO joinVO) {
		return togetherDAO.deleteJoin(joinVO);
	}

	public int processTogetherFinish(int no) {
		return togetherDAO.updateTogetherFinish(no);
	}
}
