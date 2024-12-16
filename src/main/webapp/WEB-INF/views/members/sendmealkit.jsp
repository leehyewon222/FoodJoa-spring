<%@page import="Common.StringParser"%>
<%@page import="com.foodjoa.member.vo.MemberVO"%>
<%@page import="com.foodjoa.mealkit.vo.MealkitVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.foodjoa.mealkit.vo.MealkitOrderVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=utf-8");

    String contextPath = request.getContextPath();

    String id = (String) session.getAttribute("id");
    
    ArrayList<HashMap<String, Object>> orderedMealkitList = (ArrayList<HashMap<String, Object>>) request.getAttribute("orderedMealkitList");
%>

<c:set var="contextPath" value="${ pageContext.request.contextPath }" />
<c:set var="resourcesPath" value="${ contextPath }/resources" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${ resourcesPath }/css/member/sendmealkit.css">
<title>발송조회 페이지</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function() {
    $(".save-button").click(function() {
        // 현재 행의 데이터를 가져옴
        const row = $(this).closest("tr");
        const orderNo = row.data("order-no");
        const deliveredStatus = row.find(".deliveredStatus").val();
        const refundStatus = row.find(".refundStatus").val();

        // Ajax 요청
        $.ajax({
            url: "<%=request.getContextPath()%>/Member/orderUpdate.me",
            type: "POST",
            data: {
                orderNo: orderNo,
                deliveredStatus: deliveredStatus,
                refundStatus: refundStatus
            },
            success: function(response) {
                alert("저장되었습니다.");
                // 필요한 경우 DOM 업데이트를 추가
            },
            error: function() {
                alert("저장에 실패했습니다.");
            }
        });
    });
});
</script>
</head>

<body>
	<!-- 메인 컨테이너 -->
	<div id="deliver-container">
		<h1>발송조회 페이지</h1>
			<table align="center">
				<%
				// 주문된 밀키트 리스트가 비어있는지 확인합니다.
				if (orderedMealkitList == null || orderedMealkitList.size() == 0) {
				%>
				<!-- 주문 내역이 없을 때 표시할 메시지 -->
				<tr>
					<td class="no">주문 내역이 없습니다.</td>
				</tr>
				<%
				} else {
				%>
				<!-- 테이블 헤더 -->
				<thead>
					<tr>
						<th>상품명</th>
						<th>간단소개글</th>
						<th>카테고리</th>
						<th>상품가격(개당)</th>
						<th>상품사진</th>
						<th>주소지</th>
						<th>수량</th>
						<th>배송 여부</th>
						<th>환불 여부</th>
						<th>구매자 닉네임</th>
						<th>구매자 <br> 사진
						</th>
						<th>저장</th>
					</tr>
				</thead>
				<tbody>
    <c:if test="${empty orderedMealkitList}">
        <tr>
            <td class="no">주문 내역이 없습니다.</td>
        </tr>
    </c:if>
    <c:forEach var="orderItem" items="${orderedMealkitList}">
        <c:set var="orderVO" value="${orderItem.orderVO}" />
        <c:set var="mealkitVO" value="${orderItem.mealkitVO}" />
        <c:set var="memberVO" value="${orderItem.memberVO}" />
        <tr data-order-no="${orderVO.no}">
            <td><p>${mealkitVO.title}</p></td>
            <td><p>${mealkitVO.contents}</p></td>
            <td><p>
                <c:choose>
                    <c:when test="${mealkitVO.category == 0}">한식요리</c:when>
                    <c:when test="${mealkitVO.category == 1}">일식요리</c:when>
                    <c:when test="${mealkitVO.category == 2}">중식요리</c:when>
                    <c:when test="${mealkitVO.category == 3}">양식요리</c:when>
                    <c:when test="${mealkitVO.category == 4}">자취요리</c:when>
                </c:choose>
            </p></td>
            <td><p>${mealkitVO.price}</p></td>
            <td>
                <img src="${contextPath}/images/mealkit/thumbnails/${mealkitVO.no}/${fn:split(mealkitVO.pictures, ',')[0]}">
            </td>
            <td><p>${orderVO.address}</p></td>
            <td><p>${orderVO.quantity}</p></td>
            <td>
                <select name="deliveredStatus" class="deliveredStatus">
                    <option value="0" ${orderVO.delivered == 0 ? 'selected' : ''}>배송 전</option>
                    <option value="1" ${orderVO.delivered == 1 ? 'selected' : ''}>배송 중</option>
                    <option value="2" ${orderVO.delivered == 2 ? 'selected' : ''}>배송 완료</option>
                </select>
            </td>
            <td>
                <select name="refundStatus" class="refundStatus">
                    <option value="0" ${orderVO.refund == 0 ? 'selected' : ''}>환불 전</option>
                    <option value="1" ${orderVO.refund == 1 ? 'selected' : ''}>환불 중</option>
                    <option value="2" ${orderVO.refund == 2 ? 'selected' : ''}>환불 완료</option>
                </select>
            </td>
            <td><p>${memberVO.nickname}</p></td>
            <td>
                <img src="${contextPath}/images/member/userProfiles/${orderVO.id}/${memberVO.profile}">
            </td>
            <td>
                <button type="button" class="save-button">저장</button>
            </td>
        </tr>
    </c:forEach>
</tbody>
				<%
				} // if-else 종료
				%>
			</table>
			<div>
		</div>
	</div>
</body>
</html>