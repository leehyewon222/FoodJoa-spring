<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
request.setCharacterEncoding("UTF-8");
response.setContentType("text/html;charset=utf-8");
String contextPath = request.getContextPath();

String id = (String) session.getAttribute("userId");
%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>밀키트 판매 게시글 작성</title>
	
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/mealkit/write.css">
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
</head>

<body>
	<div id="mealkit-container">
		<form action="<%=contextPath%>/Mealkit/write.pro" method="post" id="frmWrite" enctype="multipart/form-data">
			<table class="write-form" width="100%">
				<tr>
					<th width="30%">글 제목</th>
					<td width="70%">
						<div class="write-title-area">
							<input type="text" class="title" name="title" placeholder="ex) 김치볶음밥 밀키트" required>
							 <!-- id --> 
							<input type="hidden" name="id" value="<%=id%>">
						</div>
					</td>
				</tr>
				<tr>
					<th>
						사진 추가 <br>
						<small class="notice-text">
							※ 사진은 최대 5장까지<br>업로드 가능합니다.<br>※ 해당 사진을 클릭하면<br>삭제됩니다.
						</small>
					</th>
					<td>
						<div id="write-preview-area">
							<ul id="imagePreview">
								<li>
									<button type="button" id="addFileBtn" onclick="triggerFileInput()">사진 추가</button>
								</li>
							</ul>
						</div>
						<input type="file" id="pictureFiles" name="pictureFiles" 
							accept=".jpg,.jpeg,.png" multiple onchange="handleFileSelect(this.files)">
						<input type="hidden" id="pictures" name="pictures">
					</td>
				</tr>
				<tr>
					<th>카테고리</th>
					<td>
						<select class="category" name="category" required>
							<option value="">선택하세요</option>
							<option value="1">한식</option>
							<option value="2">일식</option>
							<option value="3">중식</option>
							<option value="4">양식</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>간단 소개글</th>
					<td><textarea name="contents" class="contents" rows="4" 
						placeholder="해당 밀키트의 조리법을 적어주세요" required></textarea></td>
				</tr>
				<tr>
					<th>가격</th>
					<td><input type="text" name="price" class="price" required min="0">원</td>
				</tr>
				<tr>
					<th>재고 수량</th>
					<td><input type="number" name="stock" class="stock" required min="0"> 개</td>
				</tr>
				<tr>
					<th>간단 조리 순서</th>
					<td>
						<div>
							<input type="button" class="add-orders" value="순서 추가하기">
						</div>
						<div class="orders-container">
		    				<table width="100%">
		    				</table>
						</div>
						<input type="hidden" id="orders" name="orders">
					</td>
				</tr>
				<tr>
					<th>원산지 표기</th>
					<td>
						<input type="text" name="origin" class="origin" placeholder="ex) 쌀(국내산), 소고기(미국산), ..." required>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="button" class="write" value="작성 완료" onclick="onSubmit(event, '<%=contextPath%>')">
					</td>
				</tr>
			</table>
		</form>
	</div>

	<script src="<%=contextPath%>/js/mealkit/write.js"></script>
</body>

</html>
