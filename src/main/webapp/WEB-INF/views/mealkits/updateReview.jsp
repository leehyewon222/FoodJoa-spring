<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<c:set var="resourcesPath" value="${contextPath}/resources" />
<jsp:useBean id="stringParser" class="Common.StringParser"/>

<c:set var="pictures" value="${stringParser.splitString(reviewInfo.pictures) }"/>
<c:set var="thumbnail" value="${stringParser.splitString(reviewInfo.mealkitVO.pictures)[0] }"/>
	
<c:set var="id" value="${sessionScope.userId }"/>

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
		<h1>밀키트 리뷰 수정</h1>
		<form id="frmReview" action="#" method="post" enctype="multipart/form-data">
			<input type="hidden" id="mealkit_no" name="mealkit_no" value="${reviewInfo.mealkitNo }">
			<input type="hidden" id="review_no" name="review_no" value="${reviewInfo.no }">
			<input type="hidden" id="origin_pictures" name="origin_pictures" value="${reviewInfo.pictures }">
					
			<table width="100%">
				<tr>
					<td align="center">
						<div class="thumbnail-area">						
							<img src="${resourcesPath}/images/mealkit/thumbnails/${reviewInfo.mealkitNo }/${thumbnail}">
						</div>
					</td>
				</tr>
				<tr>
					<td align="center">
						<div class="title-area">
							${reviewInfo.mealkitVO.title }
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="rating-star-area">
							<div class="rating-star-label">
								평점 선택
							</div>
								<img src="${resourcesPath}/images/recipe/full_star.png" onclick="setRating(event, 1)">
								<img src="${resourcesPath}/images/recipe/full_star.png" onclick="setRating(event, 2)">
								<img src="${resourcesPath}/images/recipe/full_star.png" onclick="setRating(event, 3)">
								<img src="${resourcesPath}/images/recipe/full_star.png" onclick="setRating(event, 4)">
								<img src="${resourcesPath}/images/recipe/full_star.png" onclick="setRating(event, 5)">
							<input type="hidden" id="rating" name="rating" value="${reviewInfo.rating }">
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
							<textarea id="contents" name="contents">${reviewInfo.contents }</textarea>
						</div>
					</td>
				</tr>
				<tr>
					<td align="right">
						<div class="review-button-area">
							<input type="button" value="수정 완료" onclick="onSubmit(event,'${contextPath}')">
							<input type="button" value="취소" onclick="onCancleButton(event)">
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<script src="${resourcesPath}/js/common/common.js"></script>
	<script src="${resourcesPath}/js/mealkit/updateReview.js"></script>
	<script>
		initialize();
	
		function initialize(){
			setRating(null, $("input[name='rating']").val());	

			let $li;
			let $img;
			
			<c:forEach var="picture" items="${pictures}">
				const fileName = "${picture}";
		        originSelectedFileNames.push(fileName);
				
				$li = $('<li>');
				$img = $('<img>', {
					class: 'preview_image',
					src: '${resourcesPath}/images/mealkit/reviews/${reviewInfo.mealkitNo}/${id}/' + fileName ,
					css: {
						cursor: 'pointer'
					}
				});
	
				$img.on('click', function() {
				    $(this).parent().remove();
				    removeOriginFileName(fileName);
				});
	
				$li.append($img);
				$('#imagePreview').append($li);
			</c:forEach>
			
			$("#contents").val('${reviewInfo.contents}');
		}
		
		function removeOriginFileName(fileName) {
			for (let i = 0; i < originSelectedFileNames.length; i++) {
				if (originSelectedFileNames[i] == fileName) {
					originSelectedFileNames.splice(i, 1);
					break;
				}
			}
		}
	
		function setRating(event, ratingValue) {
			if(event != null){
				event.preventDefault();
			}
			
			let emptyStarPath = '${resourcesPath}/images/recipe/empty_star.png';
			let fullStarPath = '${resourcesPath}/images/recipe/full_star.png';
	
			let startButtons = $(".rating-star-area img");
			startButtons.each(function(index, element) {
	
				let path = (index < ratingValue) ? fullStarPath : emptyStarPath;
				$(element).attr('src', path);
			});
	
			document.getElementsByName('rating')[0].value = ratingValue;
		}
	</script>
</body>
</html>