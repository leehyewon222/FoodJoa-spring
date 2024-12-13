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
 
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script src="${ resourcesPath }/js/recipe/write.js"></script>
	<script src="https://cdn.tiny.cloud/1/dvxu8ag2amp0f6jzdha1igxdgal2cpo0waqtixb0z64yirx7/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/pako/2.1.0/pako.min.js"></script>
	<script src="${ resourcesPath }/js/common/common.js"></script>
	
	<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@200..900&display=swap" rel="stylesheet">	
	<link rel="stylesheet" href="${ resourcesPath }/css/recipe/write.css">    
</head>

<body>
	<div id="recipe-container">
		<form action="${ contextPath }/Recipe/writePro" method="post" id="frmWrite" enctype="multipart/form-data">
			<input type="hidden" id="id" name="id" value="admin">
			<table width="100%">
				<tr>
					<td colspan="2" align="right">
						<input type="button" class="recipe-write-button" value="레시피 작성" onclick="onSubmit(event)">
						<input type="button" class="recipe-cancle-button" value="취소" onclick="onCancleButton(event)">
					</td>
				</tr>
				<tr>
					<td style="vertical-align: top;">
						<div class="recipe-label">
							음식 유형
						</div>
					</td>
					<td align="left">
						<div class="category-area">
							<select id="category" name="category">
								<option value="" disabled hidden selected>음식 유형을 선택하세요.</option>
								<option value="1">한식</option>
								<option value="2">일식</option>
								<option value="3">중식</option>
								<option value="4">양식</option>
								<option value="5">자취요리</option>
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: top;">
						<div class="recipe-label">
							음식 사진
						</div>
					</td>
					<td align="center">
						<div class="thumbnail-area">
							<input type="file" name="thumbnail" id="imageInput" accept=".png,.jpeg,.jpg" style="display: none;">
							<img id="imagePreview" src="${ resourcesPath }/images/recipe/addImage.png">
						</div>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: top;">
						<div class="recipe-label">
							레시피 제목
						</div>
					</td>
					<td align="center">
						<div class="title-area">
							<input type="text" id="title" name="title" placeholder="제목 입력">
						</div>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: top;">
						<div class="recipe-label">
							간단 소개글
						</div>
					</td>
					<td align="center">
						<div class="description-area">
							<input type="text" id="description" name="description" placeholder="간단 소개글 입력">
						</div>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: top;">
						<div class="recipe-label">
							상세 내용
						</div>
					</td>
					<td align="center">
						<div class="contents-area">
							<textarea id="contents-field" width="100%"></textarea>
							<input type="hidden" name="contents" required>
						</div>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: top;">
						<div class="recipe-label">
							사용한 재료
						</div>
					</td>
					<td align="center">
						<input type="hidden" id="ingredient" name="ingredient">
						<input type="hidden" id="ingredient_amount" name="ingredientAmount">
						
						<div class="ingredients-container">
							<table width="100%">
							</table>
						</div>
						<div class="ingredients-button-area">
							<button type="button" class="ingredient-new-button">재료 추가하기</button>
						</div>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: top;">
						<div class="recipe-label">
							간단 조리 순서
						</div>
					</td>
					<td align="center">
						<input type="hidden" id="orders" name="orders">
						
						<div class="orders-container">
							<table width="100%">
							</table>
						</div>
						<div class="orders-button-area">
							<button type="button" class="orders-new-button">순서 추가하기</button>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						<input type="button" class="recipe-write-button" value="레시피 작성" onclick="onSubmit(event)">
						<input type="button" class="recipe-cancle-button" value="취소" onclick="onCancleButton(event)">
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	
	<script>
		let selectedFile;
	
		function onSubmit(e) {
		    e.preventDefault();
		    
		    setIngredientString();
		    setOrdersString();
		    
		    const editorContent = tinymce.get('contents-field').getContent();
			const base64Compressed = compressContent(editorContent);
 
			const formData = new FormData();
			formData.append('id', $("#id").val());
			formData.append('category', $("#category").val());
			formData.append('title', $("#title").val());
			formData.append('description', $("#description").val());
			formData.append('contents', base64Compressed);
			formData.append('ingredient', $("#ingredient").val());
			formData.append('ingredientAmount', $("#ingredient_amount").val());
			formData.append('orders', $("#orders").val());
			formData.append('file', selectedFile);
			
			$.ajax({
				url: '${ contextPath }/Recipe/writePro',
				type: 'POST',
				data: formData,
			    processData: false,
			    contentType: false,
			    success: function(responseData, status, jqxhr) {
					if (responseData != "0") {
						alert('레시피를 작성했습니다.');
						location.href = '${ contextPath }/Recipe/read?no=' + responseData;	
					}
					else {
						alert('레시피 작성에 실패했습니다.');
					}
			    },
			    error: function(xhr, status, error) {
			        console.log("error", error);
					alert('레시피 작성 중 통신 에러');
			    }
			});
		}
		
		function onCancleButton(e) {
		    e.preventDefault();
		    
		    history.go(-1);
		}
		
		const imageInput = document.getElementById('imageInput');
		const imagePreview = document.getElementById('imagePreview');

		imagePreview.addEventListener('click', () => {
			imageInput.click();
		});

		imageInput.addEventListener('change', (event) => {
			const file = event.target.files[0];
			selectedFile = file;
			if (file) {
				const reader = new FileReader();
				
				reader.onload = (e) => {
					imagePreview.src = e.target.result;
				};
				
				reader.readAsDataURL(file);
			}
		});
	
		tinymce.init({
	        selector: "#contents-field",
	        statusbar: false,
	        height: 500,
	        toolbar: "undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | outdent indent | bullist numlist",
	        paste_data_images: true,
	        plugins: "paste image imagetools advlist lists",
	        menubar: false,
	        advlist_bullet_styles: 'square',
	        advlist_number_styles: 'lower-alpha,lower-roman,upper-alpha,upper-roman'
	    });
	</script>
</body>

</html>