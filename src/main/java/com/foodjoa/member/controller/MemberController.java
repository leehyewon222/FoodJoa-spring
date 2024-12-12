package com.foodjoa.member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.foodjoa.member.service.MemberService;
import com.foodjoa.member.vo.MemberVO;

import Common.SNSLoginAPI;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("Member")
public class MemberController {
    
    @Autowired
    private MemberService memberService;
    
    @Autowired
    private MemberVO memberVO;

    //---------------------------------------
    
    // SNS 회원가입 페이지로 이동
    @RequestMapping("snsjoin")
    public String snsjoin() {
        return "/member/snsjoin";  // Tiles에 맞게 경로 반환
    }
    
    @RequestMapping("naverjoin")
    public void naverjoin(HttpServletRequest request, HttpServletResponse response) throws Exception {
  	
    	String userId = SNSLoginAPI.handleNaverLogin(
    			request.getParameter("code"),
    			request.getParameter("state"));
    	
    	handleJoin(request, response, userId);
    }
    
    @RequestMapping("kakaojoin")
    public void kakaojoin(HttpServletRequest request, HttpServletResponse response) throws Exception {

    	String userId = SNSLoginAPI.handleKakaoLogin(request.getParameter("code"));
    	handleJoin(request, response, userId);
    }
    
    private void handleJoin(HttpServletRequest request, HttpServletResponse response, String userId) throws Exception {

    	response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
    	if (userId == null || userId.length() <= 0 || userId.trim().isEmpty()) {
    		
    		out.print("<script>");
    		out.print("alert('아이디 정보를 받아오는데 실패했습니다.');");
    		out.print("history.go(-1);");
    		out.print("</script>");
    		
    		out.close();
    		
    		return;
    	}

    	request.getSession().setAttribute("joinId", userId);
    	
    	out.println("<script>");
		out.println("location.href='" + request.getContextPath() + "/Member/join';");
		out.println("</script>");
		out.close();
    }

    // 추가 정보 입력 페이지로 리다이렉트할 때 처리하는 메소드
    @RequestMapping("join")
    public String join() {    	
    	return "/member/join";
	}

	// 추가정보입력 후 회원 가입 처리
	@RequestMapping("joinPro")
	public String joinPro(MemberVO memberVO, HttpSession session, MultipartHttpServletRequest request) 
			throws ServletException, IOException {
		
		int result = memberService.insertMember(memberVO, request);
		
		if (result > 0) {
			session.setAttribute("userId", session.getAttribute("joinId"));
			session.removeAttribute("joinId");
		}
		
		return "redirect:/Main/home";
	}
    
    //--------------------------여기까지 회원가입 처리
    
    //----------이제 로그인 차례
    
    @RequestMapping("login")
	private String login() {
		return "/member/login";  // 리다이렉트할 경로
	}

    // 네이버 로그인 처리 메소드
    @RequestMapping("naverlogin")
    private void naverlogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	response.setContentType("text/html; charset=utf-8");
    	
    	String userId = SNSLoginAPI.handleNaverLogin(
    			request.getParameter("code"),
    			request.getParameter("state"));
    	
    	handleLogin(request, response, userId);
    }

    // 카카오 로그인 처리 메소드
    @RequestMapping("kakaologin")
    private void kakaologin(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	response.setContentType("text/html; charset=utf-8");
    	
    	String userId = SNSLoginAPI.handleKakaoLogin(request.getParameter("code"));

    	handleLogin(request, response, userId);
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response, String userId)
    		throws Exception {
    	
    	PrintWriter out = response.getWriter();
    	
    	if (userId == null || userId.length() <= 0 || userId.trim().isEmpty()) {
    		
    		out.print("<script>");
    		out.print("alert('아이디 정보를 받아오는데 실패했습니다.');");
    		out.print("history.go(-1);");
    		out.print("</script>");
    		
    		out.close();
    		
    		return;
    	}
    	
    	boolean isUserExists = memberService.isUserExists(userId);
    	
    	// SNS 로그인 성공 및 회원정보 있을 때
    	if (isUserExists) {
    		request.getSession().setAttribute("userId", userId);
    		response.sendRedirect(request.getContextPath() + "/Main/home");
    		
			return;
		}
    	
    	request.getSession().setAttribute("joinId", userId);
    	
		out.println("<script>");
		out.println("alert('회원가입이 필요합니다. 회원가입 페이지로 이동합니다.');");
		out.println("location.href='" + request.getContextPath() + "/Member/join';");
		out.println("</script>");
		out.close();
    }
 
    //----------------------------------로그인 끝
    
    //---------------------로그아웃
    
    // 로그아웃 처리
    @RequestMapping("logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        // 세션 무효화
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }

        // 리다이렉트: 로그아웃 후 메인 페이지로 이동
        return "redirect:/Main/home"; // Spring에서는 "redirect:"를 사용하여 리다이렉트
    }
    
    //-----탈퇴
    
    @RequestMapping("deleteMember")
    public String deleteMember(){
    	return "/member/deleteMember";
	}
    
    @RequestMapping("/mypagemain")
    public String mypagemain(Model model ,HttpSession session){

        String userId = (String) session.getAttribute("userId");
        
        // 사용자 정보 및 데이터 가져오기
        MemberVO member = memberService.getMemberById(userId);
        ArrayList<Integer> deliveredCounts = memberService.getCountOrderDelivered(userId);
        ArrayList<Integer> sendedCounts = memberService.getCountOrderSended(userId);

        // 데이터 설정 및 뷰 반환
        model.addAttribute("member", member);
        model.addAttribute("deliveredCounts", deliveredCounts);
        model.addAttribute("sendedCounts", sendedCounts);

        return "/member/mypagemain";
    }
}
