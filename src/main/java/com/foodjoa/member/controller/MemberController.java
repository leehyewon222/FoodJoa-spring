package com.foodjoa.member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return "/members/snsjoin";  // Tiles에 맞게 경로 반환
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

    	String userId = SNSLoginAPI.handleKakaoJoin(request.getParameter("code"));
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
    	return "/members/join";
	}

	// 추가정보입력 후 회원 가입 처리
	@RequestMapping("joinPro")
	public String joinPro(MemberVO memberVO, HttpSession session, MultipartHttpServletRequest request) 
			throws Exception {
		
		int result = memberService.insertMember(memberVO, request, session);
		
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
		return "/members/login";  // 리다이렉트할 경로
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
    	return "/members/deleteMember";
	}
    
    @RequestMapping("deleteMemberPro")
    private String deleteMemberPro(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 세션에서 로그인된 사용자 아이디를 가져옵니다.
        HttpSession session = request.getSession();
        String readonlyId = (String) session.getAttribute("userId");

        // 사용자가 입력한 아이디를 가져옵니다.
        String inputId = request.getParameter("inputId");

        // 아이디가 일치하지 않을 경우
        if (readonlyId == null || !readonlyId.equals(inputId)) {
            response.setContentType("text/html; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script>");
            out.print("alert('아이디가 일치하지 않습니다.');");
            out.print("history.go(-1);");
            out.print("</script>");
            out.close();
            return null; // 여기서 리턴하지 않고 바로 끝내고 페이지 이동
        }

        // 서비스 레이어를 호출하여 탈퇴 처리
        boolean isDeleted = memberService.deleteMember(readonlyId);

        // 탈퇴 성공 시 세션 무효화 및 메인 페이지로 이동
        if (isDeleted) {
            session.invalidate(); // 세션 무효화

            // 리다이렉트 전에 알림 창을 띄운 후, 메인 페이지로 이동
            response.setContentType("text/html; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script>");
            out.print("alert('탈퇴 되셨습니다.');");
            out.print("window.location.href='/Main/home';"); // 리다이렉트 전에 알림창 띄우고 이동
            out.print("</script>");
            out.close();
            return null;
        } else {
            response.setContentType("text/html; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script>");
            out.print("alert('탈퇴 처리 중 오류가 발생하였습니다. 다시 시도 해주세요.');");
            out.print("history.go(-1);");
            out.print("</script>");
            out.close();
            return null; // 다시 페이지로 돌아가도록 처리
        }
    }

    //-------------------------탈퇴처리 완료
    
    @RequestMapping("wishlist")
    public String wishlist(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");

        // 위시리스트 정보 가져오기
        HashMap<String, Object> wishListInfos = memberService.getWishListInfos(userId);
        
        // 분리하여 전달
        model.addAttribute("wishListInfos", wishListInfos);

        // 메인 페이지로 포워딩
        return "/members/wishlist";
    }
    
    @ResponseBody
    @RequestMapping(value = "deleteWishlist", method = { RequestMethod.GET, RequestMethod.POST })
    public String deleteWishlist(@RequestParam int wishType, @RequestParam int no) {
    	
    	return String.valueOf(memberService.deleteWishlist(wishType, no));
    }
    
	@RequestMapping("recentlist")
	public String recentlist(HttpSession session, Model model) {
		
		String userId = (String) session.getAttribute("userId");
			
		HashMap<String, Object> recentViewInfos = memberService.getRecentViews(userId);

		 // 분리하여 전달
        model.addAttribute("recentViewInfos", recentViewInfos);
		
		return "/members/recent";
	}
	
	@RequestMapping("cartlist")
	public String cartlist() {
		return "/members/cartlist";
	}
    
    @RequestMapping("mypagemain")
    public String mypagemain(Model model ,HttpSession session){

        String userId = (String) session.getAttribute("userId");
        
        // 사용자 데이터 가져오기
        MemberVO member = memberService.getMemberById(userId);
        ArrayList<Integer> deliveredCounts = memberService.getCountOrderDelivered(userId);
        ArrayList<Integer> sendedCounts = memberService.getCountOrderSended(userId);

        // 데이터 설정 및 뷰 반환
        model.addAttribute("member", member);
        model.addAttribute("deliveredCounts", deliveredCounts);
        model.addAttribute("sendedCounts", sendedCounts);

        return "/members/mypagemain";
    }
    
    @RequestMapping("profileupdate")
    public String profileupdate(Model model, HttpSession session) {
        // ID 가져오기
        String userId = (String) session.getAttribute("userId");
        
        // 사용자 정보 부름
        MemberVO vo = memberService.getMemberById(userId);

        // Model에 데이터 추가
        model.addAttribute("vo", vo);

        return "/members/profileupdate";
    }
    
    @RequestMapping("impormation")
    private String impormation(HttpServletRequest request, HttpServlet response) {
		request.setAttribute("center", "members/impormation.jsp");
		return "/members/impormation";
	}
}
