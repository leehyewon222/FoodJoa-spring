package com.foodjoa.together.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.foodjoa.together.dao.TogetherDAO;
import com.foodjoa.together.vo.TogetherVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class TogetherService {

	@Autowired
	private TogetherDAO togetherDAO;

	public TogetherVO getTogether(int no) {
		
		return (no <= 0) ? new TogetherVO() : togetherDAO.selectTogether(no);
	}

	public int processTogetherEdit(String cmd, TogetherVO togetherVO, 
			MultipartHttpServletRequest multipartRequest, String originPictures) {
		
		log.debug("??????????????? : " + togetherVO.toString());
		
		
		return 0;
	}
}
