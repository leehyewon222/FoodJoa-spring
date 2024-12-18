<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=utf-8");
%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="resourcesPath" value="${contextPath}/resources" />
<jsp:useBean id="stringParser" class="Common.StringParser" />
<c:set var="id" value="${sessionScope.userId}" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${resourcesPath}/css/member/sendmealkit.css">
    <title>발송조회 페이지</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
    $(document).ready(function() {
        $(".save-button").click(function() {
            const row = $(this).closest("tr");
            const orderNo = row.data("order-no");
            const deliveredStatus = row.find(".deliveredStatus").val();
            const refundStatus = row.find(".refundStatus").val();

            $.ajax({
                url: "${contextPath}/Member/orderupdate",  
                type: "POST",
                data: {
                    orderNo: orderNo,
                    deliveredStatus: deliveredStatus,
                    refundStatus: refundStatus
                },
                success: function(response) {
                    alert(response); 
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

        <table width="100%">
            <c:choose>
                <c:when test="${empty orderedMealkitList}">
                    <tr>
                        <td class="no">주문 내역이 없습니다.</td>
                    </tr>
                </c:when>
                <c:otherwise>
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
                            <th>구매자 사진</th>
                            <th>저장</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${orderedMealkitList}">
                            <c:set var="mealkit" value="${order.mealkitVO}" />
                            <c:set var="member" value="${order.memberVO}" />

                            <tr data-order-no="${order.no}">
                                <td><p>${mealkit.title}</p></td>
                                <td><p>${mealkit.contents}</p></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${mealkit.category == 0}">한식</c:when>
                                        <c:when test="${mealkit.category == 1}">일식</c:when>
                                        <c:when test="${mealkit.category == 2}">중식</c:when>
                                        <c:when test="${mealkit.category == 3}">양식</c:when>
                                        <c:when test="${mealkit.category == 4}">자취식</c:when>
                                    </c:choose>
                                </td>
                                <td>
                                    <fmt:formatNumber value="${mealkit.price}" type="number" groupingUsed="true" maxFractionDigits="0" />
                                    &nbsp;원
                                </td>                               
                                <td class="thumbnail-area">
                                    <img src="${resourcesPath}/images/mealkit/thumbnails/${order.mealkitNo}/${stringParser.splitString(mealkit.pictures)[0]}" alt="${mealkit.title}" class="thumbnails_area">
                                </td>
                                <td><p>${order.address}</p></td>
                                <td><p>${order.quantity}</p></td>
                                <td>
                                    <select name="deliveredStatus" class="deliveredStatus">
                                        <option value="0" ${order.delivered == 0 ? 'selected' : ''}>배송 전</option>
                                        <option value="1" ${order.delivered == 1 ? 'selected' : ''}>배송 중</option>
                                        <option value="2" ${order.delivered == 2 ? 'selected' : ''}>배송 완료</option>
                                    </select>
                                </td>
                                <td>
                                    <select name="refundStatus" class="refundStatus">
                                        <option value="0" ${order.refund == 0 ? 'selected' : ''}>환불 전</option>
                                        <option value="1" ${order.refund == 1 ? 'selected' : ''}>환불 중</option>
                                        <option value="2" ${order.refund == 2 ? 'selected' : ''}>환불 완료</option>
                                    </select>
                                </td>
                                <td><p>${member.nickname}</p></td>
                                <td class="profile-area">
                                    <img src="${resourcesPath}/images/member/userProfiles/${order.id}/${member.profile}" alt="${member.nickname}">
                                </td>
                                <td>
                                    <button type="button" class="save-button">저장</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </c:otherwise>
            </c:choose>
        </table>
</body>
</html>
