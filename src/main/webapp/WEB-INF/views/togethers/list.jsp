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
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd HH:mm:ss" />
<c:set var="now" value="${currentDate}" />

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
		<div class="together-header" align="center">
			<p class="together_p1">COMMUNITY</p>
			<p class="together_p2">모임 게시판</p>
			<p>모임을 만들고 참여해보세요!</p>
		</div>
		
		<div class="together-write-button">
			<c:if test="${ not empty id }">
				<input type="button" value="모임 만들기" onclick="onWriteButton()">
			</c:if>
		</div>
		
		<c:choose>
			<c:when test="">
				<div class="together-nodata">
					등록 된 모임이 없습니다.
				</div>
			</c:when>
			<c:otherwise>
				<c:forEach var="together" items="${ togethers }" varStatus="status">
					<div class="together-cell" onclick="onReadTogether(${ together.no })">
						<c:set var="thumbnail" value="${ stringParser.splitString(together.pictures)[0] }"/>
						<div class="together-thumbnail">
							<img src="${ resourcesPath }/images/together/pictures/${ together.no }/${ thumbnail } ">
						</div>
						<div class="together-desciption">
							<p class="together-title">${ together.title }</p>
							<p class="together-nickname">${ together.memberVO.nickname }</p>
							<div class="together-join-info">
								<div class="location-icon">
									<img src="${ resourcesPath }/images/together/location_icon.png">
								</div>
								${ together.place }&nbsp;&middot;
								<fmt:formatDate value="${ together.joinDate }" pattern="MM.dd(E) a hh:mm"/>
								<c:choose>
						    		<c:when test="${ now > together.joinDate }">
						    			<span class="join-state-done">종료</span>
					    			</c:when>
						    		<c:when test="${ together.finished == 1 }">
						    			<span class="join-state-finish">모집 마감</span>
						    		</c:when>
						    		<c:otherwise>
						    			<span class="join-state-ing">모집 중</span>
					    			</c:otherwise>
					    		</c:choose>
							</div>
							<div class="together-join-people">
								<c:set var="joins" value="${ classifiedJoin[together.no] }"/>
								<c:choose>
									<c:when test="${ empty joins }">
										<div class="join-nodata">참여자를 기다리는 중입니다!</div>
									</c:when>
									<c:otherwise>
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
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>
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