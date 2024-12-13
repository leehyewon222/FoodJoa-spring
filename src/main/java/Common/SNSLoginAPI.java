package Common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import org.json.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SNSLoginAPI {

	public static String handleNaverLogin(String code, String state) throws IOException {
		String naverId = null;

		// 인증 코드가 있으면 액세스 토큰 요청
		if (code != null) {
			String clientId = "XhLz64aZjKhLJHJUdga6"; // 발급받은 Client ID
			String clientSecret = "SIITGJFkea"; // 발급받은 Client Secret code

			String apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&" + "client_id="
					+ clientId + "&client_secret=" + clientSecret + "&code=" + code + "&state=" + state;

			// URL 연결 및 응답 코드 확인
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			int responseCode = con.getResponseCode();
			BufferedReader br = (responseCode == 200) ?
					new BufferedReader(new InputStreamReader(con.getInputStream()))
					: new BufferedReader(new InputStreamReader(con.getErrorStream()));

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
				JSONObject userProfile = handleUserProfile(accessToken);
				// id 값을 추출하고 세션에 저장
				naverId = userProfile.getString("id");
			} catch (IOException e) {
				e.printStackTrace();
				log.error("네이버 프로필 정보를 가져오는 데 실패했습니다.");
			}
		} else {
			log.error("인증 실패");
		}

		return naverId;
	}

	// 사용자 프로필 정보 요청
	private static JSONObject handleUserProfile(String accessToken) throws IOException {

		// 인증 헤더 생성
		String header = "Bearer " + accessToken;
		String apiURL = "https://openapi.naver.com/v1/nid/me";
		URL url = new URL(apiURL);

		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Authorization", header);

		int responseCode = con.getResponseCode();
		BufferedReader br = (responseCode == 200) ? new BufferedReader(new InputStreamReader(con.getInputStream()))
				: new BufferedReader(new InputStreamReader(con.getErrorStream()));

		String inputLine;
		StringBuffer responseContent = new StringBuffer();
		while ((inputLine = br.readLine()) != null) {
			responseContent.append(inputLine);
		}
		br.close();

		// JSON 객체로 사용자 프로필 정보 반환
		return new JSONObject(responseContent.toString()).getJSONObject("response");
	}
	
	public static String handleKakaoLogin(String code) throws IOException {
		String kakaoId = null;
		try {
			// 1. 액세스 토큰 요청
			String tokenUrl = "https://kauth.kakao.com/oauth/token";
			String tokenParams = "grant_type=authorization_code" + "&client_id=dfedef18f339b433884cc51b005f2b42" // REST
																													// API
																													// 키
					+ "&redirect_uri=http://localhost:8090/FoodJoa/Member/kakaologin" // Redirect URI
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
		} catch (Exception e) {
			log.error("카카오 프로필 정보를 가져오는 데 실패했습니다.");
			e.printStackTrace();
		}

		return kakaoId; // 카카오 아이디 반환
	}
	
	public static String handleKakaoJoin(String code) throws IOException {
		String kakaoId = null;
		try {
			// 1. 액세스 토큰 요청
			String tokenUrl = "https://kauth.kakao.com/oauth/token";
			String tokenParams = "grant_type=authorization_code" + "&client_id=dfedef18f339b433884cc51b005f2b42" // REST
																													// API
																													// 키
					+ "&redirect_uri=http://localhost:8090/foodjoa/Member/kakaojoin" // Redirect URI
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
		} catch (Exception e) {
			log.error("카카오 프로필 정보를 가져오는 데 실패했습니다.");
			e.printStackTrace();
		}

		return kakaoId; // 카카오 아이디 반환
	}
}
