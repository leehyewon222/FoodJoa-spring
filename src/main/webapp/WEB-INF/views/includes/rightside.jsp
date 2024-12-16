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


<c:set var="id" value="${ sessionScope.userId }" />

<script>
	function onWishList() {
		location.href = '${ contextPath }/Member/wishlist';
	}
	
	function onRecentList() {
		location.href = '${ contextPath }/Member/recentlist';
	}
	
	function onCartList() {
		location.href = '${ contextPath }/Member/cartlist';
	}		
</script>

<style>
	.sidebar-container {
		position: fixed;
		top: 200px;
		right: 50px;
		background-color: transparent;
		box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.3);
	}
	
	.sidebar-title {
		font-family: "Noto Serif KR", serif;
		border: 1px solid #BF817E;
		background-color: white;
		color: #BF817E;
		border-radius: 5px;
		font-size: 1.5rem;
		text-align: center;
	}
	
	.sidebar-container button {
		font-family: "Noto Serif KR", serif;
		border: none;
		background-color: #BF817E;
		color: white;
		border-radius: 5px;
		font-size: 1rem;
		width: 120px;
		height: 40px;
		margin: 1px;
		cursor: pointer;
	}
</style>

<c:if test="${id != null && not empty id}">
    <div class="sidebar-container">
        <div class="sidebar-title">
            QUICK<br>MENU
        </div>
        <div><button class="button" onclick="onRecentList()">최근 본 목록</button></div>
        <div><button class="button" onclick="onWishList()">찜목록</button></div>
        <div><button class="button" onclick="onCartList()">장바구니</button></div>
    </div>
</c:if>