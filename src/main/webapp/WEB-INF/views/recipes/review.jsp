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

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script src="${ resourcesPath }/js/common/common.js"></script>
	
	<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@200..900&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="${ resourcesPath }/css/recipe/review.css">
</head>

<body>
	<div id="recipe-review-container">
		<h1>레시피 리뷰 작성</h1>
		<form id="frmReview" action="#" method="post" enctype="multipart/form-data">
			<table width="100%">
				<tr>
					<td align="center">
						<div class="thumbnail-area">						
							<img src="${ resourcesPath }/images/recipe/thumbnails/${ recipe.no }/${ recipe.thumbnail }">
						</div>
					</td>
				</tr>
				<tr>
					<td align="center">
						<div class="title-area">
							${ recipe.title }
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="rating-star-area">
							<div class="rating-star-label">
								평점 선택
							</div>
								<img src="${ resourcesPath }/images/recipe/full_star.png" onclick="setRating(event, 1)">
								<img src="${ resourcesPath }/images/recipe/full_star.png" onclick="setRating(event, 2)">
								<img src="${ resourcesPath }/images/recipe/full_star.png" onclick="setRating(event, 3)">
								<img src="${ resourcesPath }/images/recipe/full_star.png" onclick="setRating(event, 4)">
								<img src="${ resourcesPath }/images/recipe/full_star.png" onclick="setRating(event, 5)">
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
							<input type="button" value="리뷰 쓰기" onclick="onSubmit(event)">
							<input type="button" value="취소" onclick="onCancleButton(event)">
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	
	<script src="${ resourcesPath }/js/recipe/review.js"></script>
	<script>	
		function onSubmit(event) {
			event.preventDefault();
	
			setPicturesString();
	
			const formData = new FormData();
			formData.append('id', '${ sessionScope.userId }');
			formData.append('recipeNo', ${ recipe.no });
			formData.append('pictures', $("#pictures").val());
			formData.append('contents', $("#contents").val());
			formData.append('rating', $("#rating").val());
	
			selectedRealFiles.forEach((file, index) => {
				formData.append('file' + index, file);
			});
	
			$.ajax({
			    url: '${ contextPath }/Recipe/reviewWritePro',
			    type: "POST",
			    data: formData,
			    processData: false,
			    contentType: false,
			    success: function(responseData, status, jqxhr) {
					if (responseData == "1") {
						alert('리뷰를 작성했습니다.');
						location.href = '${ contextPath }/Recipe/read?no=' + ${ recipe.no };	
					}
					else {
						alert('리뷰 작성에 실패했습니다.');
					}
			    },
			    error: function(xhr, status, error) {
			        console.log("error", error);
					alert('리뷰를 작성에 실패했습니다.');
			    }
			});
		}
		
		function setRating(event, ratingValue) {
			event.preventDefault();
			
			let emptyStarPath = '${ resourcesPath }/images/recipe/empty_star.png';
			let fullStarPath = '${ resourcesPath }/images/recipe/full_star.png';
	
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