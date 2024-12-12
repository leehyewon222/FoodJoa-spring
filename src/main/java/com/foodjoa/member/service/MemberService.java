package com.foodjoa.member.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.foodjoa.member.dao.MemberDAO;
import com.foodjoa.member.vo.MemberVO;
import Common.FileIOController;
import Common.NaverLoginAPI;

@Service("memberService")
@Transactional(propagation = Propagation.REQUIRED)
public class MemberService {
    
    @Autowired
    private MemberDAO memberDAO;

    // 네이버 아이디 받아오기
    public String insertNaverMember(HttpServletRequest request, Model model) throws IOException {
        String naverId = null;
        try {
            // 네이버 인증 후 콜백 처리
            naverId = NaverLoginAPI.handleNaverLogin(request, model);
        } catch (Exception e) {
            System.out.println("네이버 회원 정보 저장 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
        return naverId;
    }

    // 카카오 아이디 받아오기
    public String insertKakaoMember(String code) throws Exception {
        // 1. 액세스 토큰 요청
        String tokenUrl = "https://kauth.kakao.com/oauth/token";
        String tokenParams = "grant_type=authorization_code" + "&client_id=dfedef18f339b433884cc51b005f2b42" 
                + "&redirect_uri=http://localhost:8090/foodjoa/Member/kakaojoin" 
                + "&code=" + code;

        HttpURLConnection tokenConn = (HttpURLConnection) new URL(tokenUrl).openConnection();
        tokenConn.setRequestMethod("POST");
        tokenConn.setDoOutput(true);
        try (OutputStream os = tokenConn.getOutputStream()) {
            os.write(tokenParams.getBytes());
            os.flush();
        }

        // 응답 확인
        BufferedReader tokenBr = new BufferedReader(new InputStreamReader(tokenConn.getInputStream()));
        StringBuilder tokenResponse = new StringBuilder();
        String line;
        while ((line = tokenBr.readLine()) != null) {
            tokenResponse.append(line);
        }
        tokenBr.close();

        // JSON 파싱: 액세스 토큰 추출
        JSONObject tokenJson = new JSONObject(tokenResponse.toString());
        String accessToken = tokenJson.getString("access_token");

        // 2. 사용자 정보 요청
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";
        HttpURLConnection userInfoConn = (HttpURLConnection) new URL(userInfoUrl).openConnection();
        userInfoConn.setRequestMethod("GET");
        userInfoConn.setRequestProperty("Authorization", "Bearer " + accessToken);

        BufferedReader userBr = new BufferedReader(new InputStreamReader(userInfoConn.getInputStream()));
        StringBuilder userInfoResponse = new StringBuilder();
        while ((line = userBr.readLine()) != null) {
            userInfoResponse.append(line);
        }
        userBr.close();

        // JSON 파싱: 사용자 ID 추출
        JSONObject userInfoJson = new JSONObject(userInfoResponse.toString());
        long kakaoIdLong = userInfoJson.getLong("id");
        String kakaoId = String.valueOf(kakaoIdLong);

        // 카카오 아이디 확인 (디버깅)
        System.out.println("받은 카카오 아이디: " + kakaoId);

        // DB에 카카오 ID 저장 (회원 정보 저장 등 추가 로직 가능)
        MemberVO vo = new MemberVO();
        vo.setId(kakaoId); // 카카오 ID를 VO에 설정

        return kakaoId;
    }

    public boolean insertMember(MultipartHttpServletRequest request) throws IOException {
        // 업로드할 디렉토리 경로 설정
        String uploadDir = "C:\\workspace_FoodJoa\\FoodJoa\\src\\main\\webapp\\resources\\images\\";  // application.properties에서 설정한 경로를 사용하거나, 직접 설정
        
        // 파일 업로드 처리
        MultipartFile profileFile = request.getFile("profileFile");

        String userId = request.getParameter("userId");
        String zipcode = request.getParameter("zipcode");
        String address1 = request.getParameter("address1");
        String address2 = request.getParameter("address2");

        // 회원 정보 생성 (프로필 파일명 포함)
        String profileFileName = profileFile != null ? profileFile.getOriginalFilename() : null;

        // MemberVO 생성
        MemberVO memberVO = new MemberVO(userId, request.getParameter("name"), request.getParameter("nickname"),
                request.getParameter("phone"), zipcode, address1, address2, profileFileName);

        // DB에 저장
        if (memberDAO.insertMember(memberVO) == 1) {
            // 회원가입 성공, 세션에 userId 설정
            request.getSession().setAttribute("userId", userId);

            // 프로필 이미지 이동 처리
            String descPath = uploadDir + "member" + File.separator + "userProfiles" + File.separator + userId;
            
            // 유저별 디렉토리 생성
            File userDir = new File(descPath);
            if (!userDir.exists()) {
                userDir.mkdirs(); // 유저 폴더 없으면 생성
            }

            // 파일이 존재하면 업로드
            if (profileFile != null && !profileFile.isEmpty()) {
                // 실제 저장될 파일 경로
                File destFile = new File(descPath + File.separator + profileFileName);

                System.out.println("목표 파일 경로: " + destFile.getAbsolutePath());

                try {
                    // MultipartFile의 transferTo() 메서드를 이용해 파일을 저장
                    profileFile.transferTo(destFile);
                    System.out.println("파일 저장 성공");
                } catch (IOException e) {
                    System.out.println("파일 저장 실패: " + e.getMessage());
                    e.printStackTrace();
                    return false; // 실패 시 처리
                }
            }

            return true;
        } else {
            return false;
        }
    }

    //--------------------------------------------회원가입 끝
    
    // 로그인 
    
    public String getNaverId(HttpServletRequest request, Model model) throws IOException {
		String naverId = null;
		try {
			// 네이버 인증 후 콜백 처리
			naverId = NaverLoginAPI.handleNaverLogin(request, model); // request와 response 전달

		} catch (Exception e) {
			System.out.println("네이버 회원 정보 저장 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
		}
		return naverId;
	}

	public String getKakaoId(String code) throws Exception {
		String kakaoId = null;
		try {
			// 1. 액세스 토큰 요청
			String tokenUrl = "https://kauth.kakao.com/oauth/token";
			String tokenParams = "grant_type=authorization_code" + "&client_id=dfedef18f339b433884cc51b005f2b42" // REST
																													// API
																													// 키
					+ "&redirect_uri=http://localhost:8090/foodjoa/Member/kakaologin" // Redirect URI
					+ "&code=" + code;

			HttpURLConnection tokenConn = (HttpURLConnection) new URL(tokenUrl).openConnection();
			tokenConn.setRequestMethod("POST");
			tokenConn.setDoOutput(true);
			try (OutputStream os = tokenConn.getOutputStream()) {
				os.write(tokenParams.getBytes());
				os.flush();
			}

			// 응답 확인
			BufferedReader tokenBr = new BufferedReader(new InputStreamReader(tokenConn.getInputStream()));
			StringBuilder tokenResponse = new StringBuilder();
			String line;
			while ((line = tokenBr.readLine()) != null) {
				tokenResponse.append(line);
			}
			tokenBr.close();

			// JSON 파싱: 액세스 토큰 추출
			JSONObject tokenJson = new JSONObject(tokenResponse.toString());
			String accessToken = tokenJson.getString("access_token");

			// 2. 사용자 정보 요청
			String userInfoUrl = "https://kapi.kakao.com/v2/user/me";
			HttpURLConnection userInfoConn = (HttpURLConnection) new URL(userInfoUrl).openConnection();
			userInfoConn.setRequestMethod("GET");
			userInfoConn.setRequestProperty("Authorization", "Bearer " + accessToken);

			BufferedReader userBr = new BufferedReader(new InputStreamReader(userInfoConn.getInputStream()));
			StringBuilder userInfoResponse = new StringBuilder();
			while ((line = userBr.readLine()) != null) {
				userInfoResponse.append(line);
			}
			userBr.close();

			// JSON 파싱: 사용자 ID 추출
			JSONObject userInfoJson = new JSONObject(userInfoResponse.toString());

			// 카카오에서 반환된 사용자 정보 중 ID를 숫자형으로 추출
			long kakaoIdLong = userInfoJson.getLong("id");

			// 숫자형 ID를 문자열로 변환
			kakaoId = String.valueOf(kakaoIdLong);

			// 카카오 아이디 확인 (디버깅)
			System.out.println("받은 카카오 아이디: " + kakaoId);

		} catch (Exception e) {
			System.out.println("카카오 아이디 가져오기 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
		}

		return kakaoId; // 카카오 아이디 반환
	}
	
	public boolean isUserExists(String userId) {
		// DB에서 아이디가 존재하는지 확인하는 로직
		return memberDAO.isUserExists(userId);
	
	}

}
