package com.foodjoa.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.foodjoa.member.service.MemberService;
import com.foodjoa.member.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping("/mypagemain")
    public ModelAndView openMypageMainView(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String userId = (String) request.getSession().getAttribute("userId");
        ModelAndView mav = new ModelAndView();

        // 로그인 여부 확인
        if (userId == null || userId.isEmpty()) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write(
                "<script>alert('로그인이 필요합니다. 로그인 페이지로 이동합니다.');" +
                "location.href='" + request.getContextPath() + "/Member/login.me';</script>"
            );
            return null; // 로그인되지 않았을 경우 null 반환
        }

        // 사용자 정보 및 데이터 가져오기
        MemberVO member = memberService.getMemberProfile(userId);
        List<Integer> deliveredCounts = memberService.getCountOrderDelivered("admin");
        List<Integer> sendedCounts = memberService.getCountOrderSended("admin");

        // 데이터 설정 및 뷰 반환
        mav.addObject("pageTitle", "마이페이지");
        mav.addObject("member", member);
        mav.addObject("deliveredCounts", deliveredCounts);
        mav.addObject("sendedCounts", sendedCounts);
        mav.setViewName("members/mypagemain"); // View 경로 설정

        return mav;
    }
}
