<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html; charset=utf-8");
%>

<c:set var="contextPath" value="${ pageContext.request.contextPath }" />
<c:set var="resourcesPath" value="${ contextPath }/resources" />
<jsp:useBean id="stringParser" class="Common.StringParser" />
<c:set var="id" value="${ sessionScope.userId }"/>

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
			<c:choose>
			<c:when test="${ empty deliveredMealkitList }">
				<tr>
					<td class="no-order">주문 내역이 없습니다.</td>
				</tr>
				
			</c:when>
			<c:otherwise>
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
					<c:forEach var="i" begin="0" end="${ deliveredMealkitList.size() - 1 }" step="1">
							<c:set var="mealkitOrder" value="${ deliveredMealkitList[i] }"/>
							<c:set var="mealkit" value="${ mealkitOrder.mealkitVO }"/>
							<c:set var="member" value="${ mealkitOrder.memberVO }"/>
							<c:set var="thumbnail" value="${ stringParser.splitString(mealkit.pictures)[0] }"/>
							
							<tr>
								<td><p>${ mealkitOrder.address }</p></td>
								<td><p>${ mealkitOrder.quantity }</p></td>
								<td><p>
									<c:choose>
										<c:when test="${ mealkitOrder.delivered == 0 }">배송 전</c:when>
										<c:when test="${ mealkitOrder.delivered == 1 }">배송 중</c:when>
										<c:otherwise>배송 완료</c:otherwise>
									</c:choose>
								</p></td>
								<td><p>
									<c:choose>
										<c:when test="${ mealkitOrder.refund == 0 }">환불 전</c:when>
										<c:when test="${ mealkitOrder.refund == 1 }">환불 중</c:when>
										<c:otherwise>환불 완료</c:otherwise>
									</c:choose>
								</p></td>
			
								<td><p>${ mealkit.title }</p></td>
								<td><p>${ mealkit.contents }</p></td>
								<td><p>
									<c:set var="category" value="${ mealkit.category }"/>
									<c:choose>
										<c:when test="${ category == 0 }">한식</c:when>
										<c:when test="${ category == 1 }">일식</c:when>
										<c:when test="${ category == 2 }">중식</c:when>
										<c:otherwise>양식</c:otherwise>
									</c:choose>
								</p></td>
								<td><p>
									<fmt:formatNumber value="${ mealkit.price }" 
											type="number" 
											groupingUsed="true" 
											maxFractionDigits="0" />&nbsp;원
								</p></td>
								<td>
									<div class="thumbnail-area">
										<!-- 주문된 밀키트의 사진을 출력 -->
										<img src="${ resourcesPath }/images/mealkit/thumbnails/${ mealkitOrder.mealkitNo }/${ thumbnail }">
									</div>
								</td>
								<td><p>${ member.nickname }</p></td>
								<td>
									<div class="profile-area">
										<!-- 판매자의 프로필 사진을 출력 -->
										<img src="${ resourcesPath }/images/member/userProfiles/${ mealkit.id }/${ member.profile }" >
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</c:otherwise>
			</c:choose>			
		</table>
	</div>
</body>
					
</html>
