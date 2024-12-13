<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${ pageContext.request.contextPath }" />
<c:set var="resourcePath" value="${ contextPath }/resources" />

<c:set var="member" value="${ community.memberVO }"/>

<c:set var="id" value="${sessionScope.userId}" />

<html>
<head>
	<meta charset="UTF-8">
	<title>자유 게시판 읽기</title>
	
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>	
	<link rel="stylesheet" href="${ resourcePath }/css/community/read.css">
	
</head>
<body>
	<div id="commnunity-container">
		<div id="community-header" align="center">
			<p class="community_p1">COMMUNITY</p>
			<p class="community_p2">자유게시판</p>
			<p>자유롭게 글을 작성해보세요</p>
		</div>
		<div id="community-body">
			<table width="100%">
				<tr>
					<td colspan="4" align="right">
						<div class="community-button-area">
							<input type="button" value="목록" id="list" onclick="onListButton()">
							<c:if test="${ not empty id && id == community.id}">
								<input type="button" value="수정" id="update" onclick="onUpdateButton()">
								<input type="button" value="삭제" id="delete" onclick="onDeleteButton()">
							</c:if>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div class="community_title">
							${community.title}
						</div>
					</td>
				</tr>
				<tr>
					<td width="50px" rowspan="2">
						<div class="image_profile">
							<img src="${ resourcePath }/images/member/userProfiles/${ community.id }/${ member.profile }">
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
							<span><fmt:formatDate value="${community.postDate}" pattern="yyyy-MM-dd" /></span>
							<span>&nbsp;조회 ${community.views }</span>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div class="community_contents">
							${community.contents}
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="right">
						<div class="community-button-area">
							<input type="button" value="목록" id="list" onclick="onListButton()">
							<c:if test="${ not empty id && id == community.id}">
								<input type="button" value="수정" id="update" onclick="onUpdateButton()">
								<input type="button" value="삭제" id="delete" onclick="onDeleteButton()">
							</c:if>
						</div>
					</td>	
				</tr>
			</table>
		</div>
	</div>
	
	<form name="frmUpdate" method="post">
		<input type="hidden" name="no">
		<input type="hidden" name="title">
		<input type="hidden" name="contents">
		<input type="hidden" name="views">
	</form>
	
	<script>
		function onListButton() {
			location.href = '${contextPath}/Community/list';
		}
	
		function onUpdateButton() {
			document.frmUpdate.action = '${contextPath}/Community/update';

			document.frmUpdate.no.value = '${ community.no }';
			document.frmUpdate.title.value = `${ community.title }`;
			document.frmUpdate.contents.value = `${ community.contents }`;
			document.frmUpdate.views.value = '${community.views}';
			
			document.frmUpdate.submit();
		}
		
		function onDeleteButton() {
			$.ajax({
				url: "${contextPath}/Community/deletePro",
				type: "post",
				data : {
					no: ${community.no}
				},
				dataType: "text",
				success: function(responsedData){
					
					console.log("responsedData : " + responsedData);
					
					if(responsedData == "1"){
						location.href ='${contextPath}/Community/list';
					}
					else {
						alert('삭제되지 않았습니다.');
					}
				},
				error: function(error){
					console.log(error);
				}				
			});
		}
	</script>
</body>
</html>