<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<c:set var="resourcesPath" value="${contextPath}/resources" />
<jsp:useBean id="stringParser" class="Common.StringParser"/>

<c:set var="picture" value="${stringParser.splitString(mealkitInfo.pictures)[0]}"/>

<c:set var="id" value="${sessionScope.userId}"/>

<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<title>Insert title here</title>
	
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	
	<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@200..900&display=swap" rel="stylesheet">
	
	<link rel="stylesheet" href="${resourcesPath }/css/mealkit/review.css">
</head>

<body>
	<div id="recipe-review-container">
		<h1>밀키트 리뷰 작성</h1>
		<form id="frmReview" action="#" method="post" enctype="multipart/form-data">
			<input type="hidden" id="id" name="id" value="${id }">
			<input type="hidden" id="mealkit_no" name="mealkit_no" value="${mealkitInfo.no }">
					
			<table width="100%">
				<tr>
					<td align="center">
						<div class="thumbnail-area">						
							<img src="${resourcesPath }/images/mealkit/thumbnails/${mealkitInfo.no }/${picture}">
						</div>
					</td>
				</tr>
				<tr>
					<td align="center">
						<div class="title-area">
							${mealkitInfo.title }
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="rating-star-area">
							<div class="rating-star-label">
								평점 선택
							</div>
								<img src="${resourcesPath }/images/recipe/full_star.png" onclick="setRating(event, 1, '${resourcesPath}')">
								<img src="${resourcesPath }/images/recipe/full_star.png" onclick="setRating(event, 2, '${resourcesPath}')">
								<img src="${resourcesPath }/images/recipe/full_star.png" onclick="setRating(event, 3, '${resourcesPath}')">
								<img src="${resourcesPath }/images/recipe/full_star.png" onclick="setRating(event, 4, '${resourcesPath}')">
								<img src="${resourcesPath }/images/recipe/full_star.png" onclick="setRating(event, 5, '${resourcesPath}')">
							<input type="hidden" id="rating" name="rating" value="5">
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<p class="review-picture-label">리뷰 사진 업로드</p>
						<div class="preview-area">
	    					<ul id="imagePreview">
	    						<li>
	    							<input type="file" id="pictureFiles" name="pictureFiles" 
										accept=".jpg,.jpeg,.png" multiple onchange="handleFileSelect(this.files)">
	    						</li>
	    					</ul>
    					</div>
							
						<input type="hidden" id="pictures" name="pictures">
					</td>
				</tr>
				<tr>
					<td>					
						<div class="review-contents-label">
							리뷰 내용 작성
						</div>
						<div class="reivew-contents-area">	
							<textarea id="contents" name="contents"></textarea>
						</div>
					</td>
				</tr>
				<tr>
					<td align="right">
						<div class="review-button-area">
							<input type="button" value="리뷰 쓰기" onclick="onSubmit(event,'${contextPath}')">
							<input type="button" value="취소" onclick="onCancleButton(event)">
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<script src="${resourcesPath }/js/common/common.js"></script>
	<script src="${resourcesPath }/js/mealkit/review.js"></script>
</body>
</html>