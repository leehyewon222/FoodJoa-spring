<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${ pageContext.request.contextPath }" />
<c:set var="resourcePath" value="${ contextPath }/resources" />

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>공지사항 게시글 작성</title>
    
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>	
	<link rel="stylesheet" href="${ resourcePath }/css/community/write.css">	
</head>

<body>
	<div id="top_container" align="center">
		<p class="community_p1">NOTICE</p>
		<p class="community_p2">공지 사항</p>
	</div>
	<div id="container">
		<div class="form-container">
			<div>
				<label for="title">제목</label>
				<input type="text" id="title" name="title" placeholder="제목을 입력하세요">
				
				<label for="contents">내용</label>
				<textarea id="contents" name="contents" rows="25" placeholder="내용을 입력해주세요"></textarea>
				
				<div class="bottom_button" align="center">
					<input type="button" value="등록" onclick="onSubmit(event)">
					<button onclick="onListButton(event)" >목록</button>
				</div>
			</div>
		</div>
	</div>
		
		
	<script>
		function onSubmit(event) {
			event.preventDefault();
			
			$.ajax({
				url: '${contextPath}/Notice/writePro',
				type: 'POST',
				async: false,
				data: {
					title: $("#title").val(),
					contents: $("#contents").val()
				},
				dateType: "text",
				success: function(responseData, status, jqxhr) {
					if (responseData == "1") {
						alert("공지사항을 작성했습니다.");
						location.href = '${contextPath}/Notice/list';
					}
					else {
						alert("공지사항 작성에 실패했습니다.");
					}
				},
				error: function(xhr, status, error) {
					console.log(error);
					alert("공지사항 글쓰기 통신 에러");
				}
			});
		}
	
		function onListButton(event) {
			event.preventDefault();
			
			location.href='${contextPath}/Notice/list';
		}
	</script>
</body>
</html>