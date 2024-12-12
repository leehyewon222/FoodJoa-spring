package com.foodjoa.member.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foodjoa.mealkit.dao.MealkitDAO;
import com.foodjoa.member.dao.MemberDAO;
import com.foodjoa.member.vo.MemberVO;
import com.foodjoa.recipe.dao.RecipeDAO;

@Controller
@RequestMapping("member")
public class MemberService{
	

    @Autowired
    private MemberDAO memberDAO;
    

    @Autowired
    private MealkitDAO mealkitDAO;
    

    @Autowired
    private RecipeDAO recipeDAO;
	

	public MemberVO getMember(HttpServletRequest request){

		HttpSession session = request.getSession();
	//	String id = (String) session.getAttribute("userId");
		String id = "admin";
		
		return memberDAO.selectMember("admin");

	}

	 public ArrayList<Integer> getCountOrderDelivered(HttpServletRequest request) {
	 
		 return mealkitDAO.selectCountOrderDelivered((String) request.getSession().getAttribute("userId"));
	    }

	public MemberVO getMemberProfile(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Integer> getCountOrderSended(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Integer> getCountOrderDelivered(String userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}