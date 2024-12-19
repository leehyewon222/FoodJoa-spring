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

<jsp:useBean id="stringParser" class="Common.StringParser"/>

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
			<c:choose>
				<c:when test="${ empty togethers }">
					<tr>
						<td>
							등록 된 모임이 없습니다.
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="together" items="${ togethers }" varStatus="status">
						<c:if test="${ status.index % 2 == 0 }">
							<tr>
						</c:if>
						
						<td>
							<div class="together-cell" onclick="onReadTogether(${ together.no })">
								<c:set var="thumbnail" value="${ stringParser.splitString(together.pictures)[0] }"/>
								<div class="together-thumbnail">
									<img src="${ resourcesPath }/images/together/pictures/${ together.no }/${ thumbnail } ">
								</div>
								<div class="together-desciption">
									<p class="together-title">${ together.title }</p>
									<p class="together-nickname">${ together.memberVO.nickname }</p>
									<p class="together-join-info">${ together.place }&nbsp;&nbsp;<fmt:formatDate value="${ together.joinDate }" pattern="MM.dd(E) a hh:mm"/></p>
									<div class="together-join-people">
										<c:set var="joins" value="${ classifiedJoin[together.no] }"/>
										<ul>
											<c:forEach var="join" items="${ joins }">
												<li>
													<div class="join-profile">
														<img src="${ resourcesPath }/images/member/userProfiles/${ join.id }/${ join.memberVO.profile }">
													</div>
												</li>
											</c:forEach>
										</ul>
										<div class="join-icon">
											<img src="${ resourcesPath }/images/together/join_icon.png">
										</div>
										<c:set var="joinCount" value="${ (empty joins) ? '0' : joins.size() }"/>
										<span class="join-label">${ joinCount }/${ together.people }</span>
									</div>
								</div>
							</div>
						</td>
						
						<c:if test="${ status.index % 2 == 1 }">
							</tr>
						</c:if>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</table>
	</div>
	
	
	<script>
		function onReadTogether(no) {
			location.href = '${ contextPath }/Together/read?no=' + no;
		}
	
		function onWriteButton() {
			location.href = '${ contextPath }/Together/edit';
		}
	</script>
</body>

</html>