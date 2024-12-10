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

<c:set var="columnCount" value="4" />
<c:set var="totalRecipeCount" value="${ recipes.size() }" />

<c:set var="recipeCountPerPage" value="12" />
<c:set var="totalPageCount" value="${ Math.ceil(totalRecipeCount / recipeCountPerPage) }" />
<c:set var="currentPage" value="${ empty currentPage ? 0 : currentPage }" />

<c:set var="pageCountPerBlock" value="5" />
<c:set var="totalBlockCount" value="${ Math.ceil(totalPageCount / pageCountPerBlock) }" />
<c:set var="currentBlock" value="${ empty currentBlock ? 0 : currentBlock }" />

<c:set var="firstRecipeIndex" value="${ currentPage * recipeCountPerPage }" />

<%-- <c:set var="id" value="${ userId }" /> --%>
<c:set var="id" value="admin" />

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>나만의 레시피 공유</title>
	
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>	
	<script type="text/javascript">	
		$(function() {			
			console.log('${ recipes }');
			
			$("#write").click(function() {
				location.href = '${ contextPath }/Recipe/write';
			});
			
			$('#word').on('keydown', function(e) {
		        var keyCode = e.which;

		        if (keyCode === 13) {
		        	onSearchButton();
		        }
		    });
		});
	
		function openRecipeContent(recipeNo) {
			document.frmOpen.action = '${ contextPath }/Recipe/read';
			document.frmOpen.no.value = recipeNo;
			document.frmOpen.submit();
		}
		
		function onSearchButton() {
			let category = '${ category }';
			let key = document.getElementById('key').value;
			let word = document.getElementById('word').value;
			
			location.href = '${ contextPath }/Recipe/search?category=' + category + '&key=' + key + '&word=' + word;
		}
	</script>
	
	<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@200..900&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="${ resourcesPath }/css/recipe/list.css">
</head>

<body>
	<div id="recipe-list-container">
		<h1 class="list-title">
			<c:choose>
				<c:when test="${ category == 1 }">한식 요리</c:when>
				<c:when test="${ category == 2 }">일식 요리</c:when>
				<c:when test="${ category == 3 }">중식 요리</c:when>
				<c:when test="${ category == 4 }">양식 요리</c:when>
				<c:when test="${ category == 5 }">자취 요리</c:when>
				<c:otherwise>전체 레시피</c:otherwise>
			</c:choose>
		</h1>
		<div class="recipe-list-header">
			<div class="recipe-list-search-area">
				<select id="key" name="key">
					<option value="recipe">레시피 명</option>
					<option value="nickname">작성자</option>
				</select>
				<input type="text" id="word" name="word" placeholder="검색할 레시피를 입력하세요.">
				<input type="button" id="search-button" name="search-button" value="검색" onclick="onSearchButton()">
			</div>
			
			<div class="recipe-write-button">
				<c:if test="${ not empty id }">
					<input type="button" id="write" name="write" value="레시피 작성">
				</c:if>
			</div>
		</div>
		
		<table class="recipe-list-table">
			<c:choose>
				<c:when test="${ empty recipes }">
					<tr>
						<td>등록된 레시피가 없습니다.</td>
					</tr>
				</c:when>
				
				<c:otherwise>
					<c:set var="loopFlag" value="true" />
					<c:forEach 
							var="i" 
							begin="${firstRecipeIndex}" 
							end="${firstRecipeIndex + recipeCountPerPage - 1}" 
							step="1"
							varStatus="loop">
						<c:if test="${ loopFlag == true and i >= totalRecipeCount }">
							</tr>
							<c:set var="loopFlag" value="false" />
						</c:if>
						
						<c:if test="${ loopFlag == true }">
							<c:if test="${ i % columnCount == 0 }">
								<tr>
							</c:if>
							
							<c:set var="recipe" value="${ recipes[i].recipeVO }"/>
							<c:set var="reviewCount" value="${ recipes[i].commonVO.reviewCount }"/>
							<c:set var="rating" value="${ recipes[i].commonVO.averageRating }"/>
							<c:set var="nickname" value="${ recipes[i].memberVO.nickname }"/>
							
							<td class="recipe-cell">
								<a href="javascript:openRecipeContent(${ recipe.no })" class="cell-link">
									<table>
										<tr>
											<td colspan="2">
												<div class="recipe-thumbnail">
							    					<img src="${ resourcesPath }/images/recipe/thumbnails/${ recipe.no }/${ recipe.thumbnail }">
							    				</div>
							    				<div class="recipe-review-star">
							    					<c:forEach var="startIndex" begin="1" end="5" step="1">
							    						<c:choose>
							    							<c:when test="${ startIndex <= rating }">
							    								<c:set var="starImage" value="full_star.png"/>
							    							</c:when>
							    							<c:when test="${ startIndex > rating && startIndex < rating + 1 }">
							    								<c:set var="starImage" value="half_star.png"/>
							    							</c:when>
							    							<c:otherwise>
							    								<c:set var="starImage" value="empty_star.png"/>
							    							</c:otherwise>
							    						</c:choose>
							    						<img class="review_star" src="${ resourcesPath }/images/recipe/${ starImage }">
							    					</c:forEach>
							    				</div>
											</td>
										</tr>
										<tr>
							    			<td class="recipe-title" colspan="2">
							    				${ recipe.title }
							    			</td>
							    		</tr>
							    		<tr>
							    			<td class="recipe-nickname" colspan="2">
							    				${ nickname }
							    			</td>
							    		</tr>
							    		<tr>
							    			<td class="recipe-review" align="right">					    			
							    				<img src="${ resourcesPath }/images/recipe/review_icon.png">
							    				<span>${ reviewCount } reviews</span>
							    				&nbsp;
							    			</td>
							    			<td class="recipe-views" align="left">
							    				&nbsp;
							    				<img src="${ resourcesPath }/images/recipe/views_icon.png">
							    				<span>${ recipe.views } views</span>
							    			</td>
							    		</tr>
									</table>
								</a>
							</td>
							
							<c:if test=" ${ i % columnCount == columnCount - 1 }">
								</tr>
							</c:if>
						</c:if>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			
			<tr>
				<td class="paging-area" colspan="4">
					<div>
						<ul>
							<c:if test="${ not empty totalRecipeCount }">
								<c:if test="${ currentBlock > 0 }">
									<li>
										<a href="${ contextPath }/Recipe/list?category=${ category }&
											currentBlock=${ currentBlock - 1 }&currentPage=${ (currentBlock - 1) * pageCountPerBlock }">
											◀
										</a>
									</li>
								</c:if>
								
								<c:set var="loopFlag" value="true" />
								<c:forEach var="i" begin="0" end="${ pageCountPerBlock + 1 }" step="1">
									<c:if test="${ loopFlag == true }">
										<c:set var="pageNumber" value="${ (currentBlock * pageCountPerBlock) + i }" />
										
										<li>
											<a href="${ contextPath }/Recipe/list?category=${ category }&
												currentBlock=${ currentBlock }&currentPage=${ pageNumber }">
												${ pageNumber + 1 }
											</a>
										</li>
									
										<c:if test="${ pageNumber + 1 == totalPageCount }">
											<c:set var="loopFlag" value="false" />
										</c:if>
									</c:if>
								</c:forEach>
								
								<c:if test="${ currentBlock + 1 < totalBlockCount }">
									<li>
										<a href="${ contextPath }/Recipe/list?category=${ category }&
											currentBlock=${ currentBlock + 1 }&currentPage=${ (currentBlock + 1) * pageCountPerBlock }">
											▶
										</a>
									</li>
								</c:if>
							</c:if>
						</ul>
					</div>
				</td>
			</tr>			
		</table>
	</div>
	
	<form name="frmOpen">
		<input type="hidden" name="no">
		<input type="hidden" name="category" value="${ category }">
		<input type="hidden" name="currentPage" value="${ currentPage }">
		<input type="hidden" name="currentBlock" value="${ currentBlock }">
	</form>
</body>

</html>