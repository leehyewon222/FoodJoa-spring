<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html; charset=UTF-8");
%>

<c:set var="contextPath" value="${ pageContext.request.contextPath }" />
<c:set var="resourcesPath" value="${ contextPath }/resources" />

<c:set var="id" value="${sessionScope.userId }"/>

<c:set var="member" value="${ share.memberVO }"/>


<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Share Read</title>

    <script type="text/javascript" src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=ug8ym1cpbw&submodules=geocoder"></script>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<link rel="stylesheet" href="${ resourcesPath }/css/share/shareread.css">
	    
    
</head>

<body>
	<div id="commnunity-container">
		<div id="community-header" align="center">
			<p class="community_p1">COMMUNITY</p>
			<p class="community_p2">나눔 / 같이 먹어요</p>
			<p>자유롭게 글을 작성해보세요</p>
		</div>
	</div>
	
	<div id="community-body">
		<table width="100%">
			<tr>
				<td colspan="4" align="right">
					<div class="community-button-area">
						<input type="button" value="목록" onclick="onListButton()">
						<c:if test="${ not empty id and id == share.id }">
							<input type="button" value="수정" onclick="onUpdateButton()">
							<input type="button" value="삭제" onclick="onDeleteButton()">
						</c:if>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<div class="community_title">
						${ share.title }
					</div>
				</td>
			</tr>
			<tr>	
				<td width="50px" rowspan="2">
					<div class="image_profile">
						<img src="${ resourcesPath }/images/member/userProfiles/${ share.id }/${ member.profile }">
					</div>
				</td>
				<td>
					<div class="community_nickname">
						${ member.nickname }
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="community_date">
						<span><fmt:formatDate value="${ share.postDate }" pattern="yyyy-MM-dd"/> </span>
						<span>&nbsp;조회 ${ share.views }</span>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<div id="imageContainer">
						<img src="${ resourcesPath }/images/share/thumbnails/${ share.no }/${ share.thumbnail }">
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<div class="community_contents">
						${ share.contents }
					</div>	
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<input type="hidden" id="lat" value="${ share.lat }">
					<input type="hidden" id="lng" value="${ share.lng }">
				
					<div id="map" style="width:100%;height:400px;"></div>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="right">
					<div class="community-button-area">
						<input type="button" value="목록" onclick="onListButton()">
						<c:if test="${ not empty id and id == share.id }">
							<input type="button" value="수정" onclick="onUpdateButton()">
							<input type="button" value="삭제" onclick="onDeleteButton()">
						</c:if>
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<script src="${ resourcesPath }/js/share/shareRead.js"></script>

	<script>
		let lat = $("#lat").val();
		let lng = $("#lng").val();

		initialize();
		
		function initialize(){
			point = new naver.maps.Point(Number(lng), Number(lat));
			
			searchCoordinateToAddress(point);
		}
		
		function onListButton(){
			location.href = '${ contextPath }/Share/list?nowPage=${ nowPage }&nowBlock=${ nowBlock }';
		}
		
		function onUpdateButton(){
			location.href = '${ contextPath }/Community/shareUpdate?no=${ share.no }&nowPage=${ nowPage }&nowBlock=${ nowBlock }';
		}
		
		function onDeleteButton() {
			location.href='${ contextPath }/Community/shareDeletePro?no=${ share.no }';
		}
	</script>
</body>

</html>