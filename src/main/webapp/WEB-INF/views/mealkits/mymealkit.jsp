<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<c:set var="resourcesPath" value="${contextPath}/resources" />
<jsp:useBean id="stringParser" class="Common.StringParser"/>

<c:set var="id" value="${sessionScope.userId }"/>

<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<title>Insert title here</title>
	
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
	
	<link rel="stylesheet" href="${resourcesPath }/css/mealkit/mymealkit.css">
</head>

<body>
	<div id="container">
		<h1>내 제품 관리</h1>
		<table width="100%">
			<c:choose>
				<c:when test="${empty mymealkits }">
					<tr>
						<td><div class="mealkit-nodata">등록한 제품이 없습니다.</div></td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="i" begin="0" end="${mymealkits.size() - 1 }" step="1">
						<c:set var="mealkit" value="${mymealkits[i]}"/>
						<c:set var="thumbnail" value="${stringParser.splitString(mealkit.pictures)[0]}"/>
						<tr>
							<td>
								<div class="mealkit-container">
									<table width="100%">
										<tr>
											<td rowspan="3" width="300px">
												<div class="mealkit-thumbnail">
													<a href="${contextPath }/Mealkit/info?no=${mealkit.no}">
														<img src="${resourcesPath}/images/mealkit/thumbnails/${mealkit.no }/${thumbnail}">
													</a>
												</div>
											</td>
											<td class="title-area" width="718px">
												<p>${mealkit.title}</p>
											</td>
											<td class="rating-area" width="180px">
												<img class="rating-star" src="${resourcesPath}/images/recipe/full_star.png">
												${mealkit.averageRating }
											</td>
										</tr>
										<tr>
											<td class="description-area" rowspan="2">
												${mealkit.contents }
											</td>
											<td class="views-price-area">
											    <div>조회수 : ${mealkit.views}</div>
											    <div>가격 : ${mealkit.price } 원</div>
											    <div>수량 : ${mealkit.stock }</div>
											</td>
	
										</tr>
										<tr>
											<td class="button-area">
												<input type="button" class="update-button" value="수정" onclick="onUpdateButton('${mealkit.no }', '${contextPath }')">
												<input type="button" class="delete-button" value="삭제" onclick="onDeleteButton('${mealkit.no }', '${contextPath }')">
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</table>
	</div>
	<script src="${resourcesPath}/js/mealkit/mymealkit.js"></script>
	<script type="text/javascript">
		initialize();
	
		function initialize(){
		    if (${insufficientStock} > 0) {
		        alert("재고가 부족한 제품이 있습니다.");
		    }
		}
	</script>
</body>
</html>