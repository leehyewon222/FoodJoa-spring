<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
request.setCharacterEncoding("UTF-8");
response.setContentType("text/html; charset=utf-8");
%>

<c:set var="contextPath" value="${ pageContext.request.contextPath }" />
<c:set var="resourcesPath" value="${ contextPath }/resources" />

<c:set var="recipeReviews" value="${ reviews.recipeReviews }"/>
<c:set var="mealkitReviews" value="${ reviews.mealkitReviews }"/>

<c:set var="id" value="${ sessionScope.userId }" />

<jsp:useBean id="stringParser" class="Common.StringParser" />

<!DOCTYPE html>
<html lang="ko">

<head>
	<meta charset="UTF-8">
	<title>리뷰 관리</title>
	
	<script src="${ resourcesPath }/js/common/common.js"></script>
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
	<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@200..900&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="${ resourcesPath }/css/member/myreview.css">
</head>

<body>
	<h1>내가 쓴 리뷰</h1>
	<div class="myreview-container" style="display: block;">
		<div class="myreview-category-area">
			<input type="button" value="레시피" onclick="changeMyReview(0)">
			<input type="button" value="밀키트" onclick="changeMyReview(1)">
		</div>
		<div class="myreview-recipe">
			<table width="100%">
				<c:choose>
					<c:when test="${ empty recipeReviews }">
						<tr>
							<td align="center">
								<div class="myreview-none-label">
									작성한 리뷰가 없습니다.
								</div>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="i" begin="0" end="${ recipeReviews.size() - 1 }" step="1">
							<c:set var="review" value="${ recipeReviews[i] }"/>
							<c:set var="recipe" value="${ review.recipeVO }"/>
							<c:set var="member" value="${ review.memberVO }"/>
							<c:set var="category" value="${ 
								recipe.category == 1 ? '[한식]' :
								recipe.category == 2 ? '[일식]' :
								recipe.category == 3 ? '[중식]' :
								recipe.category == 4 ? '[양식]' : '[자취]'
							}"/>
							<c:set var="reviewPictures" value="${ stringParser.splitString(review.pictures) }"/>
							
							<tr>
								<td><div class="myreview-cell">
									<table width="100%">
										<tr>
											<td rowspan="3" width="240px" style="vertical-align: top;">
												<div class="myreview-picture">
													<c:if test="${ not empty reviewPictures }">
														<img src="${ resourcesPath }/images/recipe/reviews/${ review.recipeNo }/${ id }/${ reviewPictures[0] }">
													</c:if>										
												</div>
											</td>
											<td>
												<div class="myreview-title">
													${ category }
													${ recipe.title }
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<div class="myreview-rating">
													<ul>
														<c:forEach var="starIndex" begin="1" end="5" step="1">
															<c:set var="starImage" value="${ starIndex <= review.rating ? 'full_star.png' : 'empty_star.png' }" />
											                <li>
											                	<img src="${ resourcesPath }/images/recipe/${ starImage }">
											                </li>
														</c:forEach>
													</ul>
													<span class="myreview-postdate">
														<fmt:formatDate value="${ review.postDate }" pattern="yyyy-MM-dd" />
													</span>
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<div class="myreview-contents">${ review.contents }</div>
											</td>
										</tr>
										<tr>
											<td class="myreview-button-area" colspan="2" align="right">
												<input type="button" value="수정" onclick="onRecipeReviewUpdate(${ review.no })">
												<input type="button" value="삭제" onclick="onRecipeReviewDelete(${ review.recipeNo }, ${ review.no })">
											</td>
										</tr>
									</table>
								</div></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
		<div class="myreview-mealkit" style="display: none;">
			<table width="100%">
				<c:choose>
					<c:when test="${ empty mealkitReviews }">
						<tr>
							<td align="center">
								<div class="myreview-none-label">
									작성한 리뷰가 없습니다.
								</div>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="i" begin="0" end="${ mealkitReviews.size() - 1 }" step="1">
							<c:set var="review" value="${ mealkitReviews[i] }"/>
							<c:set var="mealkit" value="${ review.mealkitVO }"/>
							<c:set var="member" value="${ review.memberVO }"/>
							<c:set var="category" value="${
								mealkit.category == 1 ? '[한식]' :
								mealkit.category == 2 ? '[일식]' :
								mealkit.category == 3 ? '[중식]' : '[양식]'
							}"/>
							<c:set var="reviewPictures" value="${ stringParser.splitString(review.pictures) }"/>
							
							<tr>
								<td><div class="myreview-cell">
								<table width="100%">
									<tr>
										<td rowspan="3" width="240px" style="vertical-align: top;">
											<div class="myreview-picture">
												<c:if test="${ not empty reviewPictures }">
													<img src="${ resourcesPath }/images/mealkit/reviews/${ review.mealkitNo }/${ id }/${ reviewPictures[0] }">
												</c:if>									
											</div>
										</td>
										<td>
											<div class="myreview-title">
												${ category }
												${ mealkit.title }
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="myreview-rating">
												<ul>
													<c:forEach var="starIndex" begin="1" end="5" step="1">
														<c:set var="starImage" value="${ starIndex <= review.rating ? 'full_star.png' : 'empty_star.png' }"/>
														<li>
										                	<img src="${ resourcesPath }/images/recipe/${ starImage }">
										                </li>
													</c:forEach>
												</ul>
												<span class="myreview-postdate">
													<fmt:formatDate value="${ review.postDate }" pattern="yyyy-MM-dd" />
												</span>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="myreview-contents">${ stringParser.unescapeHtml(review.contents) }</div>
										</td>
									</tr>
									<tr>
										<td class="myreview-button-area" colspan="2" align="right">
											<input type="button" value="수정" onclick="onMealkitReviewUpdate(${ review.no })">
											<input type="button" value="삭제" onclick="onMealkitReviewDelete(${ review.no }, ${ review.mealkitNo })">
										</td>
									</tr>
								</table>
							</div></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
	</div>
	
	
	<script>	
		function onRecipeReviewUpdate(reviewNo) {
			location.href = '${ contextPath }/Recipe/reviewUpdate?no=' + reviewNo;
		}
		
		function onRecipeReviewDelete(recipeNo, reviewNo) {
			if (confirm('정말로 삭제하시겠습니까?')) {
				$.ajax({
					url: '${ contextPath }/Recipe/reviewDeletePro',
				    type: "POST",
				    data: {
				    	no: reviewNo,
				    	recipeNo: recipeNo
				    },
				    dataType: "text",
				    success: function(responseData, status, jqxhr) {
						if(responseData == "0") {
							alert('리뷰 삭제에 실패했습니다.');
						}
						else {
							alert('리뷰를 삭제했습니다.');
							location.reload();
						}
				    },
				    error: function(xhr, status, error) {
				        console.log("error", error);
						alert('통신 에러');
				    }
				});
			}
		}
		
		function onMealkitReviewUpdate(reviewNo) {
			location.href = '${ contextPath }/Mealkit/updateReview?no=' + reviewNo;
		}
		
		function onMealkitReviewDelete(reviewNo, mealkitNo) {
			if (confirm('정말로 삭제하시겠습니까?')) {
				$.ajax({
					url: '${ contextPath }/Mealkit/reviewDeletePro',
				    type: "POST",
				    async: true,
				    data: {
				    	no: reviewNo,
				    	mealkitNo: mealkitNo
				    },
				    dataType: "text",
				    success: function(responseData) {
						if(responseData == "1") {
							alert('리뷰를 삭제했습니다.');
							location.reload();
						}
						else {
							alert('리뷰 삭제에 실패했습니다.');
						}
				    }
				});
			}
		}
	
		let categoryButtons = $(".myreview-category-area input");
		const categoryButtonStyles = [
	        { border: 'none', backgroundColor: '#BF917E', color: 'white' },
	        { border: '1px solid #BF917E', backgroundColor: 'white', color: '#BF917E' }
	    ];
		
		function changeMyReview(type) {
			$('.myreview-recipe').css('display', type == 0 ? 'block' : 'none');
			$('.myreview-mealkit').css('display', type == 0 ? 'none' : 'block');

			$.each(categoryButtons, function(index, button) {
		        var style = categoryButtonStyles[type === index ? 0 : 1];
		        $(button).css(style);
		    });
		}
		
		window.onload = changeMyReview(0);
	</script>
</body>
</html>