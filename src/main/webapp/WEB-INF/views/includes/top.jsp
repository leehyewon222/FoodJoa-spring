<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
	request.setCharacterEncoding("UTF-8");
%>

<c:set var="contextPath" value="${ pageContext.request.contextPath }" />
<c:set var="userId" value="${ sessionScope.userId }"/>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>top</title>
	
	<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@200..900&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="${ contextPath }/resources/css/includes/top.css?v=1.0">
</head>

<body>
	<div id="top_container">
		<div id="header">
			<!-- 사용자 메뉴 (로그인, 회원가입, 검색 버튼) -->
			<div id="userMenu">
				<c:choose>
					<c:when test="${ not empty userId }">
						<form action="${ contextPath }/Member/logout" method="POST">
			                <button type="submit">로그아웃</button>
			            </form>
					</c:when>					
					<c:otherwise>
						<button onclick="onLoginButton()">로그인</button>
		            	<button onclick="onJoinButton()">회원가입</button>
					</c:otherwise>
				</c:choose>        
    		</div>
		</div>
		
		<div id="logo">
			<a href="${ contextPath }/Main/home">
				<img src="${ contextPath }/resources/images/mainpage/logo.png" alt="푸드조아 로고">
			</a>
		</div>
		
		<!-- 메뉴바 -->
		<nav class="top_nav">
			<ul id="topMenu">
				<li><a href="${ contextPath }/Main/home">홈</a></li>
				<li><a href="${ contextPath }/Recipe/list?category=0">레시피</a>
					<ul>
						<li><a href="${ contextPath }/Recipe/list?category=1">한식 요리</a></li>
						<li><a href="${ contextPath }/Recipe/list?category=2">일식 요리</a></li>
						<li><a href="${ contextPath }/Recipe/list?category=3">중식 요리</a></li>
						<li><a href="${ contextPath }/Recipe/list?category=4">양식 요리</a></li>
						<li><a href="${ contextPath }/Recipe/list?category=5">자취 요리</a></li>
					</ul>
				</li>
				<li>
					<a href="${ contextPath }/Mealkit/list?category=0">스토어</a>
					<ul>
						<li><a href="${ contextPath }/Mealkit/list?category=1">한식 제품</a></li>
						<li><a href="${ contextPath }/Mealkit/list?category=2">일식 제품</a></li>
						<li><a href="${ contextPath }/Mealkit/list?category=3">중식 제품</a></li>
						<li><a href="${ contextPath }/Mealkit/list?category=4">양식 제품</a></li>
					</ul>
				</li>
				<li>
					<a href="#">게시판</a>
					<ul>
						<li><a href="${ contextPath }/Notice/list">공지사항</a></li>
						<li><a href="${ contextPath }/Community/list">자유 게시판</a></li>
						<li><a href="${ contextPath }/Community/shareList">나눔/같이 먹어요</a></li>
					</ul>
				</li>
				<li><a href="javascript:onMypageButton()">마이페이지</a></li>
			</ul>
		</nav>
		<div class="top_empty">
		</div>
	</div>
			
	<c:if test=""></c:if>
			
	<script type="text/javascript">	
		function onJoinButton() {
			location.href = '${ contextPath }/Member/snsjoin';
		}
		
		function onLoginButton(){
			location.href = '${ contextPath }/Member/login';
		}
		
		function onMypageButton() {
			let userId = '${ userId }';
			
			if (userId == null) {
				alert('로그인이 필요합니다. 로그인 페이지로 이동합니다.');
				location.href='${ contextPath }/Member/login';
			}
			else {
				location.href='${ contextPath }/Member/mypagemain';
			}
		}
	</script>
</body>

</html>
