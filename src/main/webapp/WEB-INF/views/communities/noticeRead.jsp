<%@page import="org.apache.jasper.tagplugins.jstl.core.If"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="resourcePath" value="${contextPath}/resources" />

<c:set var="id" value="${sessionScope.userId}"/>
<c:set var="adminId" value="E5WfZ9Dw6uy3PzDsAkaKOEdHtykh5sgibCaIt7BqYqM" />

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>작성글 읽기 및 글 수정, 삭제, 목록</title>
	
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>	
	<link rel="stylesheet" href="${ resourcePath }/css/community/read.css">
</head>

<body>
	<div id="commnunity-container">
		<div id="community-header" align="center">
			<p class="community_p1">NOTICE</p>
			<p class="community_p2">공지 사항</p>
		</div>
		
		<div id="community-body">
			<table width="100%">
				<tr>
					<td colspan="4" align="right">
						<div class="community-button-area">
							<input type="button" value="목록" id="list" onclick="onListButton()">
							<c:if test="${ not empty id && id == adminId }">
								<input type="button" value="수정" id="update" onclick="onUpdateButton()">
								<input type="button" value="삭제" id="delete" onclick="onDeleteButton()">
							</c:if>
						</div>
					</td>
				</tr>
				<tr>
					<td width="80%">
						<div class="community_title">
							${ notice.title }
						</div>
					</td>
					<td width="20%">
						<div class="community_date">
							<span><fmt:formatDate value="${notice.postDate}" pattern="yyyy-MM-dd" /></span>
							<span>&nbsp;조회 ${notice.views}</span>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div class="community_contents">
							${ notice.contents }
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="right">
						<div class="community-button-area">
							<input type="button" value="목록" id="list" onclick="onListButton()">
							<c:if test="${ not empty id && id == adminId }">
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
		<input type="hidden" name="nowPage" value="${nowPage} }">
		<input type="hidden" name="nowBlock" value="${nowBlock} }">
	</form>
	
	<script>
		function onListButton() {
			location.href = '${contextPath}/Notice/list?nowPage=${nowPage}&nowBlock=${nowBlock}';
		}
	
		function onUpdateButton() {
			document.frmUpdate.action = "${contextPath}/Notice/update";
			
			document.frmUpdate.no.value = "${notice.no}";
			
			document.frmUpdate.submit();
		}
		
		function onDeleteButton() {
			$.ajax({
				url: "${contextPath}/Notice/noticeDeletePro",
				type: "post",
				data: {
					no: ${notice.no}
				},
				dataType: "text",
				success: function(responsedData){					
					if(responsedData == "1"){
						alert('공지사항이 삭제되었습니다.');
						location.href ='${contextPath}/Notice/noticeList';
					}
					else {
						alert('공지사항 삭제에 실패했습니다.');
					}
				},
				error: function(error){
					console.log(error);
					alert('공지사항 삭제 통신 오류');
				}				
			});
		}
	</script>
</body>

</html>
