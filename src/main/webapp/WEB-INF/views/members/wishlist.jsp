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

<c:set var="recipeWishlists" value="${ wishListInfos.recipeWishlists }"/>
<c:set var="mealkitWishlists" value="${ wishListInfos.mealkitWishlists }"/>

<jsp:useBean id="stringParser" class="Common.StringParser" />

<c:set var="id" value="${ sessionScope.userId }" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Wish List</title>
    
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@200..900&display=swap" rel="stylesheet">
    <style>
        #wishlist-container {
            margin: 0 auto;
        }
        
        .wishlist-nodata {
			font-family: "Noto Serif KR", serif;
			font-size: 1.25rem;
			width: 100%;
			height: 200px;
			border: 1px solid #BF817E;
			border-radius: 5px;
			line-height: 200px;
			text-align: center;
        }

        .wishlist-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 25px;
            width: 100%;
        }

        .wishlist-item {
            border: 1px solid #ddd;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            text-align: center;
            display: flex;
            align-items: center;
            padding: 10px;
        }

        .wishlist-item img {
            max-width: 150px;
            height: 150px;
            margin-right: 20px;
        }

        .wishlist-item .info {
            text-align: left;
        }

        #radio {
            margin-bottom: 20px;
        }

        .wishlist-button {            
            font-family: "Noto Serif KR", serif;
            font-size: 1rem;
            padding: 6px 12px;
            border: none;
            background-color: #BF917E;
            color: white;
            border-radius: 5px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div id="wishlist-container">
        <h1>찜 목록</h1>
        <div class="wishlist-category-area">
            <input type="button" class="wishlist-button" value="레시피" onclick="changeMyWishlist(0)">
            <input type="button" class="wishlist-button" value="밀키트" onclick="changeMyWishlist(1)">
        </div>
        
        <!-- 레시피 위시리스트 -->
        <div id="wishListRecipe" class="wishList">
        	<c:choose>
           		<c:when test="${ empty recipeWishlists }">
           			<div class="wishlist-nodata">
           				찜한 레시피가 없습니다.
           			</div>
           		</c:when>
           		<c:otherwise>
            		<div class="wishlist-grid">
	            		<c:forEach var="item" items="${recipeWishlists}">
		                    <div class="wishlist-item">
		                        <a href="${ contextPath }/Recipe/read?no=${item.recipeNo}">
		                            <img src="${ resourcesPath }/images/recipe/thumbnails/${item.recipeNo}/${item.recipeVO.thumbnail}">
		                        </a>
		
		                        <div class="info">
		                            <div>
		                                <b>
		                                    <c:choose>
		                                        <c:when test="${item.recipeVO.category == 1}">[한식]</c:when>
		                                        <c:when test="${item.recipeVO.category == 2}">[일식]</c:when>
		                                        <c:when test="${item.recipeVO.category == 3}">[중식]</c:when>
		                                        <c:when test="${item.recipeVO.category == 4}">[양식]</c:when>
		                                        <c:when test="${item.recipeVO.category == 5}">[자취]</c:when>
		                                    </c:choose>
		                                    ${item.recipeVO.title}
		                                </b>
		                            </div>
		                            <div>작성자: ${item.memberVO.nickname}</div>
		                            <div>${item.recipeVO.description}</div>
		                          	<div>평점: ${item.recipeVO.averageRating}</div>
		                           	<button class="wishlist-button" onclick="onWishlistDelete(0, ${ item.no })">삭제</button>
		                        </div>
		                    </div>
		                </c:forEach>
            		</div>
           		</c:otherwise>
           	</c:choose>
        </div>

        <!-- 밀키트 위시리스트 -->
        <div id="wishListMealKit" class="wishList" style="display: none;">
           	<c:choose>
           		<c:when test="${ empty mealkitWishlists }">
           			<div class="wishlist-nodata">
           				찜한 밀키트가 없습니다.
           			</div>
           		</c:when>
           		<c:otherwise>
            		<div class="wishlist-grid">
		                <c:forEach var="item" items="${mealkitWishlists}">
		                    <div class="wishlist-item">
		                        <a href="${ contextPath }/Mealkit/info?no=${item.mealkitNo}">
		                            <c:set var="thumbnail" value="${ stringParser.splitString(item.mealkitVO.pictures)[0] }" />
		                            <img src="${ resourcesPath }/images/mealkit/thumbnails/${item.mealkitNo}/${thumbnail}">
		                        </a>
		                        <div class="info">
		                            <div>
		                                <b>
		                                    <c:choose>
		                                        <c:when test="${item.mealkitVO.category == 1}">[한식]</c:when>
		                                        <c:when test="${item.mealkitVO.category == 2}">[일식]</c:when>
		                                        <c:when test="${item.mealkitVO.category == 3}">[중식]</c:when>
		                                        <c:when test="${item.mealkitVO.category == 4}">[양식]</c:when>
		                                    </c:choose>    
		                                    ${item.mealkitVO.title}
		                                </b>
		                            </div>
		                            <div>작성자: ${item.memberVO.nickname}</div>
		                            <div>가격: ${item.mealkitVO.price}</div>
		                            <div>평점: ${item.mealkitVO.averageRating}</div>
		                            <button class="wishlist-button" onclick="onWishlistDelete(1, ${ item.no })">삭제</button>
		                        </div>
		                    </div>
		                </c:forEach>
		            </div>
          		</c:otherwise>
          	</c:choose>
        </div>
    </div>
    
    <script>
        let categoryButtons = $(".wishlist-category-area input");
        const categoryButtonStyles = [ {
            border : 'none',
            backgroundColor : '#BF917E',
            color : 'white'
        }, {
            border : '1px solid #BF917E',
            backgroundColor : 'white',
            color : '#BF917E'
        } ];

        function changeMyWishlist(type) {
            $('#wishListRecipe').css('display', type == 0 ? 'block' : 'none');
            $('#wishListMealKit').css('display', type == 0 ? 'none' : 'block');

            $.each(categoryButtons, function(index, button) {
                var style = categoryButtonStyles[type === index ? 0 : 1];
                $(button).css(style); 
            });
        }

        function onWishlistDelete(wishType, no) {
			$.ajax({
				url: '${ contextPath }/Member/deleteWishlist',
				type: 'post',
				data: {
					wishType: wishType,
					no: no
				},
				dataType: 'text',
				success: function(responsedData) {
					if (responsedData == "0") {
						alert('찜목록 삭제에 실패했습니다.');
					}
					else {
						alert('찜목록을 삭제 했습니다.');
						location.reload();
					}
				},
				error: function(error) {
					console.log(error);
					alert('찜목록 삭제 중 통신 에러 발생');
				}
			});
		}
        
        window.onload = changeMyWishlist(0);
    </script>
</body>
</html>
