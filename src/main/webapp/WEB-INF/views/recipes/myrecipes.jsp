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

<c:set var="id" value="${ sessionScope.userId }" />

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
	
	<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@200..900&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="${ resourcesPath }/css/recipe/myrecipes.css">	
</head>

<body>
	<div id="myrecipe-container">
		<h1>내 레시피 관리</h1>
		<table width="100%">
			<c:choose>
				<c:when test="${ empty recipes }">
					<tr>
						<td><div class="myrecipe-nodata">작성한 레시피가 없습니다.</div></td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="i" begin="0" end="${ recipes.size() - 1 }" step="1">
						<c:set var="recipe" value="${ recipes[i] }"/>
						<tr>
							<td>
								<div class="recipe-container">
									<a href="${ contextPath }/Recipe/read?no=${ recipe.no }">
										<table width="100%">
											<tr>
												<td rowspan="3" width="300px">
													<div class="recipe-thumbnail">
														<img src="${ resourcesPath }/images/recipe/thumbnails/${ recipe.no }/${ recipe.thumbnail }">
													</div>
												</td>
												<td class="title-area" width="718px">
													<p>${ recipe.title }</p>
												</td>
												<td class="rating-area" width="180px">
													<img class="rating-star" src="${ resourcesPath }/images/recipe/full_star.png">
													${ recipe.averageRating }
												</td>
											</tr>
											<tr>
												<td class="description-area" rowspan="2">
													<p>${ recipe.description }</p>
												</td>
												<td class="views-area">
													조회수 : ${ recipe.views }
												</td>
											</tr>
											<tr>
												<td class="button-area">
													<input type="button" class="update-button" value="수정" onclick="onUpdateButton(event, '${ recipe.no }')">
													<input type="button" class="delete-button" value="삭제" onclick="onDeleteButton(event, '${ recipe.no}')">
												</td>
											</tr>
										</table>
									</a>
								</div>
							</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</table>
	</div>
	
	
	<script>
		function onUpdateButton(event, no) {
			event.preventDefault();
			
			location.href = '${ contextPath }/Recipe/update?no=' + no;
		}
		
		function onDeleteButton(event, no) {
			event.preventDefault();
			
			if (confirm('정말로 삭제하시겠습니까?')) {
				location.href = '${ contextPath }/Recipe/deletePro?no=' + no;	
			}
		}
	</script>
</body>

</html>