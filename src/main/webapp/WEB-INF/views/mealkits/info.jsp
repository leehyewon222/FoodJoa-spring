<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<c:set var="resourcesPath" value="${contextPath}/resources" />
<c:set var="thumbnail" value="${stringParser.splitString(mealkitInfo.pictures)[0]}"/>
<jsp:useBean id="stringParser" class="Common.StringParser"/>

<c:set var="id" value="${sessionScope.userId }"/>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<title>제목</title>
	
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
  	<script src="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.min.js"></script>
  	<link rel="stylesheet" href="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.css">
  	
  	<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
	
  	<link rel="stylesheet" href="${resourcesPath}/css/mealkit/info.css">
</head>
<body>
	<div id="mealkit-container">
		<table>
			<tr>
				<td>
					<div id="board-container">
						<input type="hidden" id="mealkitNo" value="${mealkitInfo.no}">
						<div class="info_image">
							<c:set var="pictures" value="${stringParser.splitString(mealkitInfo.pictures)}"/>
							<ul class="bxslider">
								<c:forEach var="picture" items="${pictures}">
									<li>
							            <img src="${resourcesPath}/images/mealkit/thumbnails/${mealkitInfo.no}/${picture}" 
							            	title="${picture}" />
							        </li>
								</c:forEach>
							</ul>
							<div class="orders_text">
								<h3>조리 순서</h3>
								<br>
								<c:set var="orders" value="${stringParser.splitString(mealkitInfo.orders)}"/>
								<c:forEach var="i" begin="0" end="${orders.size() - 1}" step="1">
									<c:set var="order" value="${orders[i]}"/>
									<p>
										<span>${i + 1}. ${order}</span>
									</p>
								</c:forEach>
							</div>
						</div>
						<div class="info_text">
							<span>
								<c:if test="${mealkitInfo.id != id }">
									<c:choose>
									    <c:when test="${wish == 1}">
									        <button class="wishlist_button active" type="button" onclick="wishMealkit('${contextPath}', '${mealkitInfo.no}')">
									            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
									                <path stroke="#f00" d="M16.471 3c-1.838 0-3.466.915-4.471 2.321C10.995 3.915 9.367 3 7.529 3 4.475 3 2 5.522 2 8.633 2 13.367 12 21 12 21s10-7.633 10-12.367C22 5.523 19.525 3 16.471 3"></path>
									            </svg>
									        </button>
									    </c:when>
									    <c:otherwise>
									        <button class="wishlist_button" type="button" onclick="wishMealkit('${contextPath}', '${mealkitInfo.no}')">
									            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
									                <path stroke="#000" d="M16.471 3c-1.838 0-3.466.915-4.471 2.321C10.995 3.915 9.367 3 7.529 3 4.475 3 2 5.522 2 8.633 2 13.367 12 21 12 21s10-7.633 10-12.367C22 5.523 19.525 3 16.471 3"></path>
									            </svg>
									        </button>
									    </c:otherwise>
									</c:choose>
								</c:if>
								<div style="display: flex; align-items: center; justify-content: space-between;">
								  <h1>${mealkitInfo.title}</h1>
								  <!-- 카카오톡 공유 버튼 코드 -->
								  <a id="kakaotalk-sharing-btn" href="javascript:shareMessage()">
								    <img src="${resourcesPath}/images/member/kakaologo.png" alt="카카오톡 링크 공유하기" style="width:40px; height:auto;">
								  </a>
								</div>

								<hr>
								<strong>글쓴이: ${mealkitInfo.memberVO.nickname}</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<strong>게시일: ${mealkitInfo.postDate}</strong><br>
								<strong>평점: <fmt:formatNumber value="${mealkitInfo.averageRating}" pattern="#.#" /></strong><hr>
							</span>
							<h2>
								가격 :&nbsp;
								<fmt:formatNumber value="${mealkitInfo.price}" 
									type="number" 
									groupingUsed="true" 
									maxFractionDigits="0" />&nbsp;원
							</h2>
							<hr>
							<br>
							<span class="stock-wrapper">
								<div class="stock_count">
									<input type="text" name="stock" id="stock" value="1" readonly>
									<input type="hidden" id="mealkitStock" value="${mealkitInfo.stock}">
									<div class="stock_controls">
										<button class="stock_plus" type="button">&#9650;</button>
										<button class="stock_minus" type="button">&#9660;</button>
									</div>
								</div>
								<div class="origin-text">
									<p><b>원산지</b></p>
									<p>${mealkitInfo.origin}</p>
								</div>
							</span>
							<br>
							<div class="contents_text">
								<textarea readonly > ${mealkitInfo.contents}</textarea>
							</div>
							<c:if test="${mealkitInfo.id != id }">
								<div class="button_row">
									<button class="cart_button" type="button" onclick="cartMealkit('${contextPath}', '${mealkitInfo.no}')">장바구니</button>
									<button class="buy_button" id="payment" onclick="onPaymentButton(event)">구매하기</button>
								</div>
							</c:if>
							<c:if test="${mealkitInfo.id == id}">
					            <div class="edit_delete_buttons">
				                	<button class="delete_button" type="button"
				                		onclick="deleteMealkit(${mealkitInfo.no}, '${contextPath}')">삭제</button>
				                	<button class="edit_button" type="button" onclick="editMealkit(${mealkitInfo.no}, '${contextPath}')">수정</button>
					            </div>
				            </c:if>
					    </div>
					</div>
				</td>
			</tr>
			
			<tr>
				<td>
					<div id="review-container">
						<table class="list">
							<tr>
								<td colspan="4" class="review-h2"><h1>리뷰</h2></td>
							</tr>
							<c:choose>
								<c:when test="${empty reviewInfo}">
									<tr>
										<td>등록된 리뷰가 없습니다.</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="i" begin="0" end="${reviewInfo.size() - 1}" step="1">
										<c:set var="review" value="${ reviewInfo[i] }"/>
										<tr>
											<th>작성자</th>
											<td class="nickname-td"><input type="text" name="id" class="id" value="${review.memberVO.nickname }" readonly></td>
											<td><span>평점: ${review.rating }</span></td>
										</tr>
										<tr>
											<th>후기</th>
											<td colspan="3">
												<div class="contents-container">
													<div class="review-images">
														<ul>
															<c:set var="pictures" value="${stringParser.splitString(review.pictures)}"/>
															<c:forEach var="picture" items="${pictures}">
																<li class="review-pictures">
																	<img src="${resourcesPath }/images/mealkit/reviews/${mealkitInfo.no}/${review.id}/${picture}"
																		class="review-image">
																</li>
															</c:forEach>
														</ul>
													</div>
													<div class="review-contents-container">
														<textarea name="contents" class="review-contents" rows="5" readonly required>${review.contents }</textarea>
													</div>
												</div>
											</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
							<tr>
								<td colspan="4">
								<c:if test="${mealkitInfo.id != id}">
									<input type="button" value="리뷰 작성" class="review-button"
										onclick="location.href='${contextPath}/Mealkit/review?no=${mealkitInfo.no }'">
								</c:if>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			
			<tr>
				<td>
					<div class="foodinfo-container">
						<table>
							<tr>
								<th>영양정보</th>
								<td>
									칼로리: ${foodInfo['enerc']}&nbsp;&nbsp;&nbsp;&nbsp;
					                단백질: ${foodInfo['prot']}&nbsp;&nbsp;&nbsp;&nbsp;
					                지방: ${foodInfo['fatce']}&nbsp;&nbsp;&nbsp;&nbsp;
					                당류: ${foodInfo['sugar']}&nbsp;&nbsp;&nbsp;&nbsp;
					                나트륨: ${foodInfo['nat']}&nbsp;&nbsp;&nbsp;&nbsp;
					                콜레스테롤: ${foodInfo['chole']}
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<form id="checkoutForm" action="${contextPath}/Member/payment" method="post">
        <input type="hidden" name="isCart" value="0" />
        <input type="hidden" name="combinedNo" id="combinedNo" />
        <input type="hidden" name="CombinedQuantity" id="CombinedQuantity" />
    </form>
	
	
	<script src="${resourcesPath}/js/mealkit/info.js"></script>
	<script>
		function onPaymentButton(e) {
			e.preventDefault();
			
			$("#combinedNo").val($("#mealkitNo").val());
			$("#CombinedQuantity").val($("#stock").val());
			
			$("#checkoutForm").submit();
		}
	</script>
	
	<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
	<script>
		 // 카카오톡 SDK 초기화
		 Kakao.init('ab039484667daeed90e5c9efa4980315'); 
		
		 function shareMessage() {
			Kakao.Link.sendDefault({
				objectType: 'feed',
				content: {
					title: '친구야 이 밀키트 어때? -> ${ mealkitInfo.title }',
					description: '${ mealkitInfo.contents }',
					imageUrl: 'http://localhost:8090/FoodJoa/resources/images/mealkit/thumbnails/${mealkitInfo.no}/${thumbnail}',
					link: {
						webUrl: 'http://localhost:8090/FoodJoa/Mealkit/info?no=${mealkitInfo.no}',
					},
				},
				buttons: [
				  {
					title: '밀키트 보기',
					link: {
						webUrl: 'http://localhost:8090/FoodJoa/Mealkit/info?no=${mealkitInfo.no}',
					},
				  },
				],
			});
		}
	</script>
</body>
</html>