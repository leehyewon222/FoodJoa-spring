package Common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.ui.Model;

public class NaverLoginAPI {

	public static String handleNaverLogin(HttpServletRequest request, Model model) throws IOException {
	    String code = request.getParameter("code");
	    String state = request.getParameter("state");
	    String naverId = "";
	    
	    
	    // 인증 코드가 있으면 액세스 토큰 요청
	    if (code != null) {
	    	String clientId = "XhLz64aZjKhLJHJUdga6"; // 발급받은 Client ID
	        String clientSecret = "SIITGJFkea"; // 발급받은 Client Secret code

	        String apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&" 
	                + "client_id=" + clientId 
	                + "&client_secret=" + clientSecret 
	                + "&code=" + code 
	                + "&state=" + state;

	        // URL 연결 및 응답 코드 확인
	        URL url = new URL(apiURL);
	        HttpURLConnection con = (HttpURLConnection) url.openConnection();
	        con.setRequestMethod("GET");

	        int responseCode = con.getResponseCode();
	        BufferedReader br = (responseCode == 200) ?
	                new BufferedReader(new InputStreamReader(con.getInputStream())) :
	                new BufferedReader(new InputStreamReader(con.getErrorStream()));

	        String inputLine;
	        StringBuilder responseContent = new StringBuilder();
	        while ((inputLine = br.readLine()) != null) {
	            responseContent.append(inputLine);
	        }
	        br.close();

	        JSONObject jsonResponse = new JSONObject(responseContent.toString());
	        String accessToken = jsonResponse.getString("access_token");

	        // 네이버 사용자 정보 요청
	        try {
	        
	            JSONObject userProfile = NaverLoginAPI.handleUserProfile(accessToken);
	            
	            // id 값을 추출하고 세션에 저장
	            naverId = userProfile.getString("id");
	            System.out.println(naverId);

	            request.getSession().setAttribute("naverId", naverId);
	            
	            
	            
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	            ((ServletResponse) model).getWriter().write("프로필 정보를 가져오는 데 실패했습니다.");
	        }
	    } else {
	        ((ServletResponse) model).getWriter().write("인증 실패!");
	    }
	    return naverId; //네이버 아이디 리턴  (MemberService로 )
	}


	// 사용자 프로필 정보 요청
	public static JSONObject handleUserProfile(String accessToken) throws IOException {

		// 인증 헤더 생성
		String header = "Bearer " + accessToken;
		String apiURL = "https://openapi.naver.com/v1/nid/me";
		URL url = new URL(apiURL);

		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Authorization", header);

		int responseCode = con.getResponseCode();
		BufferedReader br = (responseCode == 200) ? 
				new BufferedReader(new InputStreamReader(con.getInputStream())) : 
				new BufferedReader(new InputStreamReader(con.getErrorStream()));

		String inputLine;
		StringBuffer responseContent = new StringBuffer();
		while ((inputLine = br.readLine()) != null) {
			responseContent.append(inputLine);
		}
		br.close();

		// JSON 객체로 사용자 프로필 정보 반환
        return new JSONObject(responseContent.toString()).getJSONObject("response");
        
	}
}
