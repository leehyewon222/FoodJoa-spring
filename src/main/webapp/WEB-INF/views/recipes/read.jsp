<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="contextPath" value="${ pageContext.request.contextPath }" />
<c:set var="resourcesPath" value="${ contextPath }/resources" />

<c:set var="recipeVO" value="${ recipeInfo.recipe }"/>
<c:set var="memberVO" value="${ recipeVO.memberVO }"/>
<c:set var="reviews" value="${ recipeInfo.reviews }"/>

<c:set var="compressedContents" value="${ recipeVO.contents }"/>

<c:choose>
	<c:when test="${ empty category }">
		<c:set var="category" value="0"/>
	</c:when>
	<c:otherwise>
		<c:set var="category" value="${ category }"/>
	</c:otherwise>
</c:choose>
<c:set var="currentPage" value="${ currentPage }"/>
<c:set var="currentBlock" value="${ currentBlock }"/>

<c:set var="id" value="${ sessionScope.userId }"/>

<jsp:useBean id="stringParser" class="Common.StringParser" />

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/pako/2.1.0/pako.min.js"></script>
	<script src="${ resourcesPath }/js/common/common.js"></script>
	
	<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@200..900&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="${ resourcesPath }/css/recipe/read.css">
	
	<script>
		let reviewContents = [];

	    function intializeReviewContent(reviewContent) {
	    	reviewContents.push(reviewContent);	    	
	    }
	</script>
</head>

<body>
	<div id="recipe-read-container">
 
		<table width="100%">
			<tr>
				<td class="recipe-read-button-area" align="right">
					<c:if test="${ not empty id and id != recipeVO.id }">
						<input type="button" value="리뷰 쓰기" onclick="onReviewWriteButton()">	
					</c:if>
					<input type="button" value="목록" onclick="onListButton()">
					<c:if test="${ id == recipeVO.id }">
						<input type="button" value="수정" onclick="onUpdateButton()">
						<input type="button" value="삭제" onclick="onDeleteButton()">
					</c:if>
				</td>
			</tr>
			<tr>
				<td>
					<hr>
					<ul class="recipe-list">
					    <li class="profile-img">
					    	<div>
					        	<img src="${ resourcesPath }/images/member/userProfiles/${ recipeVO.id }/${ memberVO.profile }">
					    	</div>
					    </li>
					    <li class="recipe-title">
					    	${ recipeVO.title }
					    </li>
					    <li class="recipe-nickname">
					    	${ memberVO.nickname }
					    </li>
					    <li class="recipe-info">
					        <p>
					            <img src="${ resourcesPath }/images/recipe/full_star.png" class="rating-star">
					            ${ recipeVO.averageRating }
					        </p>
					        <p>조회수 : ${ recipeVO.views }</p>
					    </li>
					</ul>
					<hr>
				</td>	
			</tr>
			<tr>
				<td align="center">
					<div id="thumbnail">
						<img src="${ resourcesPath }/images/recipe/thumbnails/${ recipeVO.no }/${ recipeVO.thumbnail }">
						<div id="wishlist">
							<c:if test="${ not empty id and id != recipeVO.id }">
								<img src="${ resourcesPath }/images/recipe/heart.png">
							</c:if>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td align="center">
					<div id="description">
						${ recipeVO.description }
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="contents"></div>
				</td>				
			</tr>
			<tr>
				<td>
					<div id="ingredient">
						<p class="ingredient-title">사용한 재료</p>
						<table width="100%">
							<tr bgcolor="#e9ecef">
								<td width="8%"></td>
								<td width="62%">재료 이름</td>
								<td width="30%">재료 량</td>
							</tr>
							<c:set var="ingredients" value="${ stringParser.splitString(recipeVO.ingredient) }"/>
							<c:set var="ingredientAmounts" value="${ stringParser.splitString(recipeVO.ingredientAmount) }"/>
							<c:choose>
								<c:when test="${ empty ingredients }">
									<tr>
										<td colspan="3" align="center">
											<span class="read-empty-message">작성한 재료가 없습니다.</span>
										</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="i" begin="0" end="${ ingredients.size() - 1 }" step="1">
										<c:set var="ingredient" value="${ ingredients[i] }"/>
										<c:set var="ingredientAmout" value="${ ingredientAmounts[i] }"/>
										
										<tr>
											<td width="5%" align="center">${ i + 1 }</td>
											<td width="65%">${ ingredient }</td>
											<td width="30%">${ ingredientAmout }</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="orders">
						<p class="orders-title">조리 순서</p>
						<table width="100%">
							<c:set var="orders" value="${ stringParser.splitString(recipeVO.orders) }"/>
							
							<c:choose>
								<c:when test="${ empty orders }">
									<tr>
										<td colspan="2" align="center">
											<span class="read-empty-message">작성한 순서가 없습니다.</span>
										<td>
									</tr>
								</c:when>								
								<c:otherwise>							
									<c:forEach var="i" begin="0" end="${ orders.size() - 1 }" step="1">
										<c:set var="order" value="${ orders[i] }"/>
										<tr>
											<td align="center" width="8%">${ i + 1 }</td>
											<td width="92%">${ order }</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="review-title">리뷰 (${ reviews.size() })</div>
					<table class="review-table" width="100%">
						<c:choose>
							<c:when test="${ empty reviews or reviews.size() <= 0 }">
								<tr>
									<td align="center">등록된 리뷰가 없습니다.</td>
								</tr>
							</c:when>
							
							<c:otherwise>
								<c:forEach var="i" begin="0" end="${ reviews.size() - 1 }" step="1">
									<c:set var="review" value="${ reviews[i] }"/>
									<c:set var="reviewNickName" value="${ review.memberVO.nickname  }"/>
									<c:set var="reviewProfile" value="${ review.memberVO.profile  }"/>
									
									<tr>
									<td>
										<table class="review-cell" width="100%">
											<tr>
												<td rowspan="3" class="reviewer-profile" width="10%">
													<div>
														<img src="${ resourcesPath }/images/member/userProfiles/${ review.id }/${ reviewProfile }">
													</div>
													<div>
														<p align="center">${ reviewNickName }</p>
													</div>
												</td>
												<td>
													<ul class="review-star">
													<c:set var="rating" value="${ review.rating }"/>
													<c:forEach var="startIndex" begin="1" end="5" step="1">
														<c:set var="starImage" value="${ (startIndex <= rating) ? 'full_star.png' : 'empty_star.png' }"/>
														
														<li>
										                	<img src="${ resourcesPath }/images/recipe/${ starImage }" alt="별점">
										                </li>
													</c:forEach>
										            </ul>
												</td>
											</tr>
											<tr>
												<td>
													<div class="review-pictures-area">
														<ul>
														<c:set var="pictures" value="${ stringParser.splitString(review.pictures) }"/>
														<c:forEach var="picture" items="${ pictures }">
															<li class="review-pictures">
																<img src="${ resourcesPath }/images/recipe/reviews/${ recipeVO.no }/${ review.id }/${ picture }">
															</li>
														</c:forEach>
														</ul>
													</div>
												</td>
											</tr>
											<tr>
												<td>
													<div class="review-contents">
														<script>intializeReviewContent('${ review.contents }')</script>
													</div>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</table>
				</td>
			</tr>
			<tr>
				<td class="recipe-read-button-area" align="right">
					<c:if test="${ not empty id and id != recipeVO.id }">
						<input type="button" value="리뷰 쓰기" onclick="onReviewWriteButton()">
					</c:if>
					<input type="button" value="목록" onclick="onListButton()">
					<c:if test="${ recipeVO.id == id }">
						<input type="button" value="수정" onclick="onUpdateButton()">
						<input type="button" value="삭제" onclick="onDeleteButton()">
					</c:if>
				</td>
			</tr>
		</table>
	</div>
	
	<script>
		function onListButton() {
			let href = '${ contextPath }/Recipe/list?category=${ category }';
			
			<c:if test="${ not empty currentBlock and not empty currentPage }">
				href += '&currentPage=${ currentPage }&currentBlock=${ currentBlock }';
			</c:if>
			
			location.href = href;
		}
		
		function onReviewWriteButton() {
			$.ajax({
				url: '${ contextPath }/Recipe/reviewCheck',
				type: 'post',
				data: {
					recipeNo: '${ recipeVO.no }',
					id: '${ sessionScope.userId }'
				},
				dataType: 'text',
				success: function(responseData, status, jqxhr) {
					if (responseData == "0") {
						location.href = '${ contextPath }/Recipe/reviewWrite?recipeNo=${ recipeVO.no }';
					}
					else {
						alert('이미 리뷰를 작성하셨습니다.');
					}
				},
				error: function(xhr, status, error) {
					console.log(error);
					alert("리뷰 작성 통신 에러");
				}
			});
		}
		
		function onUpdateButton() {
			location.href = '${ contextPath }/Recipe/update?no=${ recipeVO.no }';
		}
		
		function onDeleteButton() {
			if (confirm("정말 레시피를 삭제 하시겠습니까?")) {
				$.ajax({
					url: "${ contextPath }/Recipe/deletePro",
					type: "POST",
					data: {
						no: ${ recipeVO.no }
					},
					dataType: "text",
					success: function(responseData, status, jqxhr) {
						if (responseData == "1") {
							alert("레시피가 삭제되었습니다.");
							location.href='${ contextPath }/Recipe/list?category=0';
						}
						else {
							alert("레시피 삭제에 실패했습니다.");
						}
					},
					error: function(xhr, status, error) {
						console.log(error);
						alert("레시피 삭제 중 통신 에러 발생");
					}
				});
			}
		}
	
		$("#wishlist").click(function() {
			$.ajax({
				url: "${ contextPath }/Recipe/wishlist",
				type: "POST",
				data: {
					id: '${ id }',
					recipeNo: ${ recipeVO.no }
				},
				dataType: "text",
				success: function(responseData, status, jqxhr) {
					if (responseData == "2") {
						alert("이미 찜 목록에 있습니다.");
					}
					else if (responseData == "1") {
						alert("찜 목록에 추가 되었습니다.");
					}
					else {
						alert("찜 목록 추가에 실패 했습니다.");
					}
				},
				error: function(xhr, status, error) {
					console.log(error);
					alert("찜 목록 추가 중 통신 에러 발생");
				}
			});
		});
		
	    function initialize() {
		    var compressedData = "${ compressedContents }";
		    
	        try {
	            // Base64 디코딩
	            const binaryString = atob(compressedData);
	            const bytes = new Uint8Array(binaryString.length);
	            for (let i = 0; i < binaryString.length; i++) {
	                bytes[i] = binaryString.charCodeAt(i);
	            }

	            const decompressedBytes = pako.inflate(bytes);
	            
	            const decompressedText = new TextDecoder('utf-8').decode(decompressedBytes);
	            
	            document.getElementById('contents').innerHTML = decompressedText;
	        } catch (error) {
	            console.error("압축 해제 중 오류 발생:", error);
	            document.getElementById('contents').innerHTML = "내용을 표시할 수 없습니다.";
	        }
	        
	        $(".review-contents").each(function(index, element) {
				$(element).text(unescapeHtml(reviewContents[index]));
			})
	    }
		
	    // 페이지 로드 시 함수 실행
	    window.onload = initialize;
</script>

<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>

<!-- 카카오톡 공유 버튼 코드 -->
<a id="kakaotalk-sharing-btn" href="javascript:shareMessage()">
  <img src="${ resourcesPath }/images/member/kakaologo.png" alt="카카오톡 링크 공유하기" style="width:40px; height:auto;">
</a>

<script>
  // 카카오톡 SDK 초기화
  Kakao.init('ab039484667daeed90e5c9efa4980315'); 

  function shareMessage() {
	  
	   // contextPath와 recipeVO.no 값 확인
	    console.log('contextPath: ', '${ contextPath }');
	    console.log('recipeVO.no: ', '${ recipeVO.no }');
	    
    Kakao.Link.sendDefault({
      objectType: 'feed',
      content: {
        title: '친구야 이 레시피 어때? -> ${ recipeVO.title }',
        description: '${ recipeVO.description }',
        imageUrl: 'http://localhost:8090/FoodJoa/resource/images/recipe/thumbnails/${ recipeVO.no }/${ recipeVO.thumbnail }',
        link: {
          webUrl: 'http://localhost:8090/FoodJoa/Recipe/read?no=${recipeVO.no}',
        },
      },
      buttons: [
        {
          title: '레시피 보기',
          link: {
            webUrl: 'http://localhost:8090/FoodJoa/Recipe/read?no=${recipeVO.no}',
          },
        },
      ],
    });
  }
</script>




</body>

</html>