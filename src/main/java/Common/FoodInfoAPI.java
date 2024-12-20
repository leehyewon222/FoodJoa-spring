package Common;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FoodInfoAPI {
	
	public Map<String, String> getFoodInfoFromAPI(String productName) throws Exception {
        String serviceKey = "8NeBkQ8m0agqLJC98YAi1dCB%2FzLJ5Ei208LPoZgPMCnWAZ7ceQBBpF9qc7V38P5So9BzO6POqV7Ixl%2Bf0H42Hw%3D%3D";
        String endpoint = "http://api.data.go.kr/openapi/tn_pubr_public_nutri_process_info_api";

        // 음식 이름 URL 인코딩
        String encodedProductName = URLEncoder.encode(productName, "UTF-8");

        // 요청 URL 생성
        URI uri = new URI(endpoint +
                     "?serviceKey=" + serviceKey +
                     "&foodNm=" + encodedProductName + // foodNm으로 수정
                     "&pageNo=1&numOfRows=100&type=xml"); // XML 형식 요청

        // RestTemplate 사용
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(uri, String.class);

        // XML 데이터 파싱
        return parseFoodInfo(response, productName);
    }

    private Map<String, String> parseFoodInfo(String xmlResponse, String productName) throws Exception {
        // XML 파싱용 DocumentBuilderFactory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // XML을 Document로 변환
        InputStream inputStream = new ByteArrayInputStream(xmlResponse.getBytes());
        Document document = builder.parse(inputStream);

        // XPath를 사용하여 <item> 태그 탐색
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();

        // <item> 노드 리스트
        NodeList items = (NodeList) xPath.evaluate("//item", document, XPathConstants.NODESET);

        // 결과 저장용 Map
        Map<String, String> foodInfoMap = new HashMap<>();

        for (int i = 0; i < items.getLength(); i++) {
            Node item = items.item(i);

            // <foodNm> 값을 비교
            String foodNm = xPath.evaluate("foodNm", item);
            if (productName.equals(foodNm)) {
                // 칼로리, 영양소 정보 추출하여 Map에 저장
                foodInfoMap.put("enerc", xPath.evaluate("enerc", item) + " kcal");
                foodInfoMap.put("prot", xPath.evaluate("prot", item) + " g");
                foodInfoMap.put("fatce", xPath.evaluate("fatce", item) + " g");
                foodInfoMap.put("sugar", xPath.evaluate("sugar", item) + " g");
                foodInfoMap.put("nat", xPath.evaluate("nat", item) + " mg");
                foodInfoMap.put("chole", xPath.evaluate("chole", item) + " mg");
                break; // 첫 번째 매칭 결과 반환
            }
        }

        // 매칭 결과가 없을 경우
        if (foodInfoMap.isEmpty()) {
            foodInfoMap.put("결과", "검색 결과를 찾을 수 없습니다.");
        }

        return foodInfoMap;
    }

}
