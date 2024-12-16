<%@page import="Common.StringParser"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.foodjoa.member.vo.MemberVO"%>
<%@page import="com.foodjoa.mealkit.vo.MealkitVO"%>
<%@page import="com.foodjoa.mealkit.vo.MealkitOrderVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html; charset=utf-8");
	String contextPath = request.getContextPath();
	
	String id = (String) session.getAttribute("userId");
	
	ArrayList<HashMap<String, Object>> orderedMealkitList = (ArrayList<HashMap<String, Object>>) request.getAttribute("orderedMealkitList");
%>

<c:set var="contextPath" value="${ pageContext.request.contextPath }" />
<c:set var="resourcesPath" value="${ contextPath }/resources" />

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>배송조회 페이지</title>
	<link rel="stylesheet" href="${ resourcesPath }/css/member/mydelivery.css">
</head>

<body>
	<!-- 메인 컨테이너 -->
	<div id="deliver-container">
		<h1>배송조회 페이지</h1>
		<!-- 테이블을 생성하여 가운데 정렬하고 100% 너비를 설정합니다. -->
		<table width="100%">
			<%
			// 주문된 밀키트 리스트가 비어있는지 확인합니다.
			if (orderedMealkitList == null || orderedMealkitList.size() == 0) {
				%>
				<!-- 주문 내역이 없을 때 표시할 메시지 -->
				<tr>
					<td class="no-order">주문 내역이 없습니다.</td>
				</tr>
				<%
			}
			else {
			%>
				<!-- 테이블 헤더 -->
				<thead>
					<tr>
						<th>배송지</th>
						<th>수량</th>
						<th>배송 여부</th>
						<th>환불 여부</th>
						<th>상품명</th>
						<th>간단소개글</th>
						<th>카테고리</th>
						<th>상품가격(개당)</th>
						<th>상품사진</th>
						<th>판매자 닉네임</th>
						<th>판매자<br> 프로필
						</th>
					</tr>
				</thead>
				<tbody>
					<%
					// 주문된 밀키트 리스트를 반복하면서 각 주문의 정보를 출력합니다.
					for (int i = 0; i < orderedMealkitList.size(); i++) {
						MealkitOrderVO orderVO = (MealkitOrderVO) orderedMealkitList.get(i).get("orderVO");
						MealkitVO mealkitVO = (MealkitVO) orderedMealkitList.get(i).get("mealkitVO");
						MemberVO memberVO = (MemberVO) orderedMealkitList.get(i).get("memberVO");
						
						String thumbnail = StringParser.splitString(mealkitVO.getPictures()).get(0);
					%>
					<!-- 각 주문의 데이터를 테이블 행으로 출력 -->
					<tr>
						<td><p><%=orderVO.getAddress()%></p></td>
						<td><p><%=orderVO.getQuantity()%></p></td>
						<td><p>
								<%
								if (orderVO.getDelivered() == 0) {
								%>배송 전<%
								} else if (orderVO.getDelivered() == 1) {
								%>배송 중<%
								} else {
								%>배송 완료<%
								}
								%>
							</p></td>
						<td><p>
								<%
								if (orderVO.getRefund() == 0) {
								%>환불 전<%
								} else if (orderVO.getRefund() == 1) {
								%>환불 중<%
								} else {
								%>환불 완료<%
								}
								%>
							</p></td>
	
						<td><p><%=mealkitVO.getTitle()%></p></td>
						<td><p><%=mealkitVO.getContents()%></p></td>
						<td><p>
								<%
								if (mealkitVO.getCategory() == 0) {
								%>
								한식요리
								<%
								}
								%>
								<%
								if (mealkitVO.getCategory() == 1) {
								%>
								일식요리
								<%
								}
								%>
								<%
								if (mealkitVO.getCategory() == 2) {
								%>중식요리
								<%
								}
								%>
								<%
								if (mealkitVO.getCategory() == 3) {
								%>양식요리
								<%
								}
								%>
								<%
								if (mealkitVO.getCategory() == 4) {
								%>자취요리
								<%
								}
								%>
							</p></td>
						<td><p><%=mealkitVO.getPrice()%></p></td>
						<td>
							<div class="thumbnail-area">
								<!-- 주문된 밀키트의 사진을 출력 -->
								<img src="<%= contextPath %>/images/mealkit/thumbnails/<%= mealkitVO.getNo() %>/<%= thumbnail %>">
							</div>
						</td>
						<td><p><%=memberVO.getNickname()%></p></td>
						<td>
							<div class="profile-area">
								<!-- 판매자의 프로필 사진을 출력 -->
								<img src="<%= contextPath %>/images/member/userProfiles/<%= mealkitVO.getId() %>/<%=memberVO.getProfile()%>" >
							</div>
						</td>
					</tr>
					<%
					} // for 루프 종료
					%>
				</tbody>
			<%
			} // if-else 종료
			%>
		</table>
	</div>
</body>

</html>
