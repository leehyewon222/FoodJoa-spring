<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="Common.StringParser"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<c:set var="resourcesPath" value="${contextPath}/resources" />
<jsp:useBean id="stringParser" class="Common.StringParser"/>

<c:set var="id" value="aronId"/>

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
	
	<script src="${resourcesPath}/js/mealkit/info.js"></script>
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
								<br>
								<h3>조리 순서</h3>
								<c:set var="orders" value="${stringParser.splitString(mealkitInfo.orders)}"/>
								<c:forEach var="i" begin="0" end="${orders.size() - 1}" step="1">
									<c:set var="order" value="${orders[i]}"/>
									<p>
										<span>${i + 1}: ${order}</span>
									</p>
								</c:forEach>
							</div>
						</div>
						<div class="info_text">
							<span>
								<button class="wishlist_button" type="button" onclick="wishMealkit('${contextPath}')">
									<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
								    	<path stroke="#000" d="M16.471 3c-1.838 0-3.466.915-4.471 2.321C10.995 3.915 9.367 3 7.529 3 4.475 3 2 5.522 2 8.633 2 13.367 12 21 12 21s10-7.633 10-12.367C22 5.523 19.525 3 16.471 3"></path>
								 	</svg>
								</button>
								<h1>${mealkitInfo.title}</h1>
								<hr>
								<strong>글쓴이: ${mealkitInfo.memberVO.nickname}</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<strong>게시일: ${mealkitInfo.postDate}</strong><br>
								<!-- 나중에 평점 수정 -->
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
							<!-- 수량 정하는 박스 -->
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
							<!-- 간단 소개글  -->
							<div class="contents_text">
								<textarea readonly >${mealkitInfo.contents}</textarea>
							</div>
							<!-- 구매, 장바구니 버튼 -->
							<div class="button_row">
								<button class="cart_button" type="button" onclick="cartMealkit('${contextPath}')">장바구니</button>
								<button class="buy_button" id="payment" onclick="onPaymentButton(event)">구매하기</button>
							</div>
							<!-- 수정 삭제 버튼 -->
				            <div class="edit_delete_buttons">
				            	<c:if test="${not empty id}">
				                	<button class="edit_button" type="button" onclick="editMealkit('${contextPath}')">수정</button>
				                	<button class="delete_button" type="button"
				                		onclick="deleteMealkit(${mealkitInfo.no}, '${contextPath}')">삭제</button>
				            	</c:if>
				            </div>
					    </div>
					</div>
				</td>
			</tr>
			
			<!-- ------------------------------------------ -->
			
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
															<c:forEach var="picture" items="${pictures }">
																<li class="review-pictures">
																	<img src="${resourcesPath }/images/mealkit/reviews/${mealkitInfo.no }/${review.id }/${picture}">
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
								<c:if test="${not empty id}">
									<input type="button" value="리뷰 작성" class="review-button"
										onclick="location.href='${contextPath}/Mealkit/review?no=${mealkitInfo.no }'">
								</c:if>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>