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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.foodjoa.member.service.MemberService;
import com.foodjoa.member.vo.MemberVO;

@Controller
@RequestMapping("Member")
@SessionAttributes("userId")  // 세션 속성으로 userId를 관리
public class MemberController {
    
    @Autowired
    private MemberService memberService;
    
    @Autowired
    private MemberVO memberVO;

    //---------------------------------------
    
    // SNS 회원가입 페이지로 이동
    @RequestMapping("snsjoin")
    public String openMemberJoinView() {
        return "/member/snsjoin";  // Tiles에 맞게 경로 반환
    }
    
    // 네이버 아이디 받아와서 저장하기
    private String processNaverJoin(HttpServletRequest request, Model model) throws ServletException, IOException {
    	
    	System.out.println("네이버 프로 왔음");
        // 네이버 인증 후 아이디 처리
        // 네이버 아이디를 DB에 저장하고 세션에 저장할 아이디를 리턴
        String naverId = memberService.insertNaverMember(request, model);

        // 네이버 아이디가 없거나 빈 값이면 처리
        if (naverId == null || naverId.trim().isEmpty()) {
            // 실패 시 snsjoin 페이지로 리다이렉트
            return "null";
        }
        return naverId;
    }

    // 저장된 아이디 받아오기
    @RequestMapping("naverjoin")
    public String handleNaverJoin(HttpServletRequest request, Model model) throws ServletException, IOException {
  	
        String naverId = processNaverJoin(request, model);
        
        if (naverId != null) {
            // 세션에 userId 저장
            HttpSession session = request.getSession(true); // 세션을 가져옴
            session.setAttribute("userId", naverId); // 세션에 userId 저장
            
            return "redirect:/Member/join"; // /Member/join 으로 리다이렉트
        }
        return "redirect:/Member/snsjoin"; // 실패 시 다시 snsjoin 페이지로 리다이렉트
    }


    // 카카오 회원가입 프로세스
    private String processKakaoJoin(HttpServletRequest request) throws ServletException, IOException {

        // 카카오 인가 코드 가져오기
        String code = request.getParameter("code"); // GET 요청의 "code" 파라미터 가져오기

        if (code == null || code.trim().isEmpty()) {
            System.out.println("인가 코드 없음");
            return null; // 인가 코드가 없으면 null 반환
        }

        try {
            // 카카오 로그인 API를 통해 사용자 정보 가져오기
            String kakaoId = memberService.insertKakaoMember(code);

            if (kakaoId == null || kakaoId.trim().isEmpty()) {
                System.out.println("카카오 로그인 실패");
                return null; // 카카오 로그인 실패 시 null 반환
            }

            System.out.println("카카오 ID: " + kakaoId);
            return kakaoId;

        } catch (Exception e) {
            System.out.println("에러 발생: " + e.getMessage());
            e.printStackTrace();
            return null; // 에러 발생 시 null 반환
        }
    }

    // 카카오 회원가입 처리
    @RequestMapping("kakaojoin")
    public String handleKakaoJoin(HttpServletRequest request, HttpSession session) throws ServletException, IOException {

        // 카카오 회원가입 프로세스 처리
        String kakaoId = processKakaoJoin(request);

        if (kakaoId != null) {
            // 세션에 userId 저장
            session.setAttribute("userId", kakaoId);
            System.out.println("카카오 ID 세션 저장: " + kakaoId);
            return "redirect:/Member/join"; // 회원가입 페이지로 리다이렉트
        }

        System.out.println("카카오 회원가입 실패");
        return "redirect:/Member/snsjoin"; // 실패 시 snsjoin 페이지로 리다이렉트
    }


 // 추가 정보 입력 페이지로 리다이렉트할 때 처리하는 메소드
    @RequestMapping("join")
    public String openJoinMainView(HttpServletRequest request, Model model) {
       
        // 세션에서 userId 가져오기 (카카오 아이디 세션에 저장했다고 가정)
        String userId = (String) request.getSession().getAttribute("userId");

        // userId를 모델에 추가
        model.addAttribute("userId", userId);

        // 추가 정보 입력 페이지로 이동
        return "/member/join"; // Tiles에 맞는 경로
    }

 // 추가정보입력 후 회원 가입 처리
    @RequestMapping("addMember")
    public String processMemberJoin(MultipartHttpServletRequest request, RedirectAttributes redirectAttributes) throws ServletException, IOException {

        // 회원 가입 처리
        if (memberService.insertMember(request)) {  // MultipartHttpServletRequest를 전달
            // 네이버나 카카오 로그인 후 받은 아이디를 세션에서 가져옴
            HttpSession session = request.getSession(true); // 세션이 없으면 새로 생성
            String userId = (String) session.getAttribute("userId"); // 네이버나 카카오에서 받은 아이디

            // 세션에 아이디가 없으면 예외 처리
            if (userId == null || "null".equals(userId.trim())) {
                throw new ServletException("아이디 값이 없거나 잘못된 값입니다. 다시 시도해주세요.");
            }

            // 회원 가입 후 아이디를 세션에 저장
            session.setAttribute("userId", userId); // 로그인한 사용자의 아이디를 세션에 저장
        }

        // 회원 가입 후 리다이렉트할 페이지
        redirectAttributes.addFlashAttribute("message", "회원 가입이 완료되었습니다.");
        return "redirect:/Main/home";  // 리다이렉트할 경로
    }
    
    //--------------------------여기까지 회원가입 처리
    
    //----------이제 로그인 차례
    
    @RequestMapping("login")
	private String openLoginView() {

		return "/member/login";  // 리다이렉트할 경로
	}

 // 네이버 로그인 처리 메소드
    @RequestMapping("naverlogin")
    private void processNaverLogin(HttpServletRequest request, HttpServletResponse response, Model model)
            throws ServletException, IOException {
    	

        try {
            String userId = memberService.getNaverId(request, model); // 네이버 ID 가져오기

            handleLogin(userId, request, response, model); // 공통 로직 호출
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "네이버 로그인 처리 중 오류가 발생했습니다.");
        }
    }

    // 카카오 로그인 처리 메소드
    @RequestMapping("kakaologin")
    private void processKakaoLogin(HttpServletRequest request, HttpServletResponse response, Model model)
            throws ServletException, IOException {
        try {
            String code = request.getParameter("code"); // 카카오 인증 후 전달된 코드
            String userId = memberService.getKakaoId(code); // 카카오 ID 가져오기
            handleLogin(userId, request, response, model); // 공통 로직 호출
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "카카오 로그인 처리 중 오류가 발생했습니다.");
        }
    }

    // 공통 로그인 처리 메소드
    private void handleLogin(String userId, HttpServletRequest request, HttpServletResponse response, Model model)
            throws IOException, ServletException {
    	
        if (userId == null || userId.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "아이디를 가져오는 데 실패했습니다.");
            return;
        }

        try {
            // 사용자 ID로 DB 조회 (회원 존재 여부 확인)
            boolean isUserExists = memberService.isUserExists(userId);
            
           if (isUserExists) {
                // 회원 존재 시 세션 저장 및 메인 페이지로 이동
                HttpSession session = request.getSession();
                session.setAttribute("userId", userId);
            	
            	System.out.println( "userId : " + userId);
                response.sendRedirect(request.getContextPath() + "/Main/home");
            } else {
                // 회원이 존재하지 않으면 회원가입 페이지로 이동
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('회원가입이 필요합니다. 회원가입 페이지로 이동합니다.');");
                out.println("location.href='" + request.getContextPath() + "/Member/join?userId=" + userId + "';");
                out.println("</script>");
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "로그인 처리 중 오류가 발생했습니다.");
        }
    }

    // 로그인 처리 메소드
    private void processMemberLogin(HttpServletRequest request, HttpServletResponse response, Model model)
            throws ServletException, IOException {
        String uri = request.getRequestURI(); // 요청된 URI

        if (uri.endsWith("/naverlogin")) {
            processNaverLogin(request, response, model); // 네이버 로그인 처리
        } else if (uri.endsWith("/kakaologin")) {
            processKakaoLogin(request, response, model); // 카카오 로그인 처리
        }
    }
 
    //----------------------------------로그인 끝
    
    //---------------------로그아웃
    
    // 로그아웃 처리
    @RequestMapping("logout")
    public String processMemberLogOut(HttpSession session, RedirectAttributes redirectAttributes) {
        // 세션 무효화
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }

        // 리다이렉트: 로그아웃 후 메인 페이지로 이동
        return "redirect:/Main/home"; // Spring에서는 "redirect:"를 사용하여 리다이렉트
    }
    
    //-----탈퇴
    
    @RequestMapping("deleteMember")
    public String openDeleteMember(){

    	return "/member/deleteMember";
	}
    
}
