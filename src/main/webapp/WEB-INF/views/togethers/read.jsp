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

<jsp:useBean id="stringParser" class="Common.StringParser" />
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd HH:mm:ss" />
<c:set var="now" value="${currentDate}" />

<c:set var="together" value="${ togetherInfo.together }"/>
<c:set var="member" value="${ together.memberVO }"/>
<c:set var="replies" value="${ togetherInfo.replies }"/>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bxslider@4.2.17/dist/jquery.bxslider.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bxslider@4.2.17/dist/jquery.bxslider.min.js"></script>
    <script type="text/javascript" src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=ug8ym1cpbw&submodules=geocoder"></script>
	
	<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@200..900&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="${ resourcesPath }/css/together/read.css">
	
	<script>        
		$(function() {
			$('.bx_slider').bxSlider({
				adaptiveHeight: true,
				auto : false,
				pager : false
			});
		});
	</script>
</head>

<body>
	<div class="together-container">
		<div class="together-button-area">
			<c:if test="${ not empty id and id == together.id }">
				<input type="button" value="수정" onclick="onEditButton()">
				<input type="button" value="삭제" onclick="onDeleteButton()">
			</c:if>
			<input type="button" value="목록" onclick="onListButton()">
		</div>
	
		<div class="together-join-info">
			<div><fmt:formatDate value="${ together.joinDate }" pattern="yyyy-MM-dd HH:mm" /></div>
			<div>
				<span>${ together.place }</span>
				<span>모집인원 &nbsp;&nbsp;${ togetherInfo.joinCount } / ${ together.people }</span>
			</div>
		</div>
		
		<div class="together-join-button">
			<input type="button" value="모임 참여하기" onclick="onTogetherJoinButton()">
		</div>
	
		<div class="together-header">
			<hr>
			<ul class="recipe-list">
			    <li class="profile-img">
			    	<div>
			        	<img src="${ resourcesPath }/images/member/userProfiles/${ together.id }/${ member.profile }">
			    	</div>
			    </li>
			    <li class="recipe-title">
			    	<span>
				    	<c:choose>
				    		<c:when test="${ now > together.joinDate }">종료</c:when>
				    		<c:when test="${ together.finished == 1 }">모집 종료</c:when>
				    		<c:otherwise>모집 중</c:otherwise>
				    	</c:choose>
			    	</span> ${ together.title }
			    </li>
			    <li class="recipe-nickname">
			    	${ member.nickname }
			    </li>
			    <li class="recipe-info">					        
			        <p>조회수 : ${ together.views }</p>
			    </li>
			</ul>
			<hr>
		</div>
		
		<div class="together-pictures">
			<ul class="bx_slider">
				<c:set var="pictures" value="${ stringParser.splitString(together.pictures) }"/>
				<c:forEach var="picture" items="${ pictures }">
					<li>
						<div class="image-area">
							<img src="${ resourcesPath }/images/together/pictures/${ together.no }/${ picture }">
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
		
		<div class="together-contents">${ together.contents }</div>
		
		<div class="together-map">
			<div id="map"></div>
		</div>
		
		<div class="together-reply-area">
			<h1>댓글 (${ replies.size() })</h1>
			<div class="together-reply-input">
				<textarea></textarea>
				<input type="button" value="작성">
			</div>
			<div class="together-reply-list">
				<table width="100%">
					<c:forEach var="reply" items="${ replies }">
						<tr>
							<td>
								<div class="reply-profile">
									<img src="${ resourcesPath }/images/member/userProfiles/${ reply.id }/${ reply.memberVO.profile }">
								</div>
							</td>
							<td>
								<p class="reply-nickname">${ reply.memberVO.nickname }</p>
								<div class="reply-contents">${ reply.contents }</div>
								<p><fmt:formatDate value="${ reply.postDate }" pattern="yyyy-MM-dd a hh:mm"/></p>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		
		<div class="together-button-area">
			<c:if test="${ not empty id and id == together.id }">
				<input type="button" value="수정" onclick="onEditButton()">
				<input type="button" value="삭제" onclick="onDeleteButton()">
			</c:if>
			<input type="button" value="목록" onclick="onListButton()">
		</div>
	</div>
	
	
    <script type="text/javascript" src="${ resourcesPath }/js/common/naverMapAPI.js"></script>
	<script>
		function onTogetherJoinButton() {
			let userId = '${ id }';
			
			if (userId == '') {
				alert('로그인이 필요한 서비스입니다.');
				return;
			}
			
			if (confirm('모임에 참여하시겠습니까?')) {			
				$.ajax({
					url: '${ contextPath }/Together/togetherJoin',
					async: true,
					type: 'get',
					data: {
						no: ${ together.no }
					},
					dataType: 'text',
					success: function(responsedData) {
						if (responsedData == "1") {
							alert('모임에 참석하셨습니다.');
						}
						else if (responsedData == "2") {
							alert('이미 모임에 참석하셨습니다.');
						}
						else {
							alert('모임 참석 등록에 실패했습니다.');
						}
					},
					error: function(error) {
						console.log(error);
						alert('모임 참석 중 통신 장애 발생');
					}
				});
			}
		}
	
		function onListButton() {
			location.href = '${ contextPath }/Together/list';
		}
	
		function onEditButton() {
			location.href = '${ contextPath }/Together/edit?no=' + ${ together.no };
		}
		
		function onDeleteButton() {
			if (confirm('정말로 삭제하시겠습니까?')) {
				$.ajax({
					url: '${ contextPath }/Together/delete',
					async: true,
					type: 'get',
					data: {
						no: ${ together.no }
					},
					dataType: 'text',
					success: function(responsedData) {
						if (Number(responsedData) > 0) {
							alert('모임이 삭제되었습니다.');
							location.href = '${ contextPath }/Together/list';
						}	
						else {
							alert('모임 삭제에 실패했습니다.');
						}
					},
					error: function(error) {
						console.log(error);
						alert('모임 삭제 중 통신 에러 발생');
					}
				});
			}
		}
	
		function initialize() {
			isRead = true;
			
			let lat = '${ together.lat }';
			let lng = '${ together.lng }';
			
			point = new naver.maps.Point(Number(lng), Number(lat));
			
			searchCoordinateToAddress(point);
		}
		
		window.onload = initialize();
	</script>
</body>

</html>