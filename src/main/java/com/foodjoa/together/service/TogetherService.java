package com.foodjoa.together.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foodjoa.together.dao.TogetherDAO;

@Service
@Transactional
public class TogetherService {

	@Autowired
	private TogetherDAO togetherDAO;
}
