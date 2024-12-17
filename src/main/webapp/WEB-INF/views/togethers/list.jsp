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

<c:set var="id" value="${ sessionScope.userId }"/>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	
	<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@200..900&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="${ resourcesPath }/css/together/list.css">
</head>

<body>
	<div class="together-container">
		<div class="together-header">
			<h1>모임 게시판</h1>
			<c:if test="${ not empty id }">
				<input type="button" value="모임 만들기" onclick="onWriteButton()">
			</c:if>
		</div>
		<table width="100%">
		</table>
	</div>
	
	
	<script>
		function onWriteButton() {
			location.href = '${ contextPath }/Together/edit';
		}
	</script>
</body>

</html>