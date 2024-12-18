<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=utf-8");
%>

<c:set var="contextPath" value="${ pageContext.request.contextPath }" />
<c:set var="resourcesPath" value="${ contextPath }/resources" />

<jsp:useBean id="stringParser" class="Common.StringParser" />

<c:set var="id" value="${ sessionScope.userId }" />

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>최근 본 글 목록</title>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@200..900&display=swap" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>

    <style>
        .recent-view-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 25px;
            width: 100%;
        }

        .recent-view-item {
            border: 1px solid #ddd;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            overflow: hidden;
            background-color: #fff;
            font-family: "Noto Serif KR", serif;
            text-align: center;
            transition: transform 0.3s, box-shadow 0.3s;
        }

        .recent-view-item:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
        }

        .recent-view-item img {
            width: 100%;
            height: auto;
            max-height: 50%;
            object-fit: contain;
            display: block;
            margin: 0 auto;
        }

        .recent-view-item .info {
            padding: 10px;
        }

        .recent-view-item .info div {
            margin-bottom: 8px;
        }

        .recent-category-area input {
            font-family: "Noto Serif KR", serif;
            font-size: 1rem;
            padding: 8px 16px;
            border-radius: 5px;
            cursor: pointer;
            border: 1px solid #BF917E;
            background-color: white;
            color: #BF917E;
            transition: all 0.3s;
        }

        .recent-category-area input:hover {
            background-color: #BF917E;
            color: white;
        }
    </style>
</head>

<body>
    <div id="recent-container">
        <h1>최근에 본 목록</h1>
        <div class="recent-category-area">
            <input type="button" value="레시피" onclick="changeMyRecentView(0)">
            <input type="button" value="밀키트" onclick="changeMyRecentView(1)">
        </div>

        <!-- 최근 본 레시피 목록 -->
        <div id="recentViewRecipe" class="recent-view">
            <div class="recent-view-grid">
                <c:forEach var="item" items="${recipes}">
                    <div class="recent-view-item">
                        <a href="${contextPath}/Recipe/read?no=${item.recipeVO.no}">
                            <img src="${resourcesPath}/images/recipe/thumbnails/${item.recipeVO.no}/${item.recipeVO.thumbnail}" alt="${item.recipeVO.title}">
                        </a>
                        <div class="info">
                            <b>
                                <c:choose>
                                    <c:when test="${item.recipeVO.category == 1}">[한식]</c:when>
                                    <c:when test="${item.recipeVO.category == 2}">[일식]</c:when>
                                    <c:when test="${item.recipeVO.category == 3}">[중식]</c:when>
                                    <c:when test="${item.recipeVO.category == 4}">[양식]</c:when>
                                    <c:when test="${item.recipeVO.category == 5}">[자취]</c:when>
                                </c:choose> ${item.recipeVO.title}
                            </b>
                            <div class="details">작성자: ${item.memberVO.nickname}</div>
                            <div class="details">평점: ${item.recipeVO.averageRating}</div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <!-- 최근 본 밀키트 목록 -->
        <div id="recentViewMealKit" class="recent-view" style="display: none;">
            <div class="recent-view-grid">
                <c:forEach var="item" items="${mealKits}">
                    <div class="recent-view-item">
                       
                        <a href="${contextPath}/Mealkit/info?no=${item.mealkitVO.no}">
                         <c:set var="thumbnail" value="${ stringParser.splitString(item.mealkitVO.pictures)[0] }" />
                            <img src="${resourcesPath}/images/mealkit/thumbnails/${item.mealkitVO.no}/${thumbnail}" alt="${item.mealkitVO.title}">
                        </a>
                        <div class="info">
                            <div>
                                <b>
                                    <c:choose>
                                        <c:when test="${item.mealkitVO.category == 1}">[한식]</c:when>
                                        <c:when test="${item.mealkitVO.category == 2}">[일식]</c:when>
                                        <c:when test="${item.mealkitVO.category == 3}">[중식]</c:when>
                                        <c:when test="${item.mealkitVO.category == 4}">[양식]</c:when>
                                    </c:choose> ${item.mealkitVO.title}
                                </b>
                            </div>
                            <div class="details">작성자: ${item.memberVO.nickname}</div>
                            <div class="details">
                                가격:
                                <fmt:formatNumber value="${item.mealkitVO.price}" groupingUsed="true" maxFractionDigits="0" />
                                원
                            </div>
                            <div class="details">평점: ${item.mealkitVO.averageRating}</div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

    </div>

    <script>
        let categoryButtons = $(".recent-category-area input");
        const categoryButtonStyles = [{
            border: 'none',
            backgroundColor: '#BF917E',
            color: 'white'
        }, {
            border: '1px solid #BF917E',
            backgroundColor: 'white',
            color: '#BF917E'
        }];

        function changeMyRecentView(type) {
            console.log(type);
            $('#recentViewRecipe').css('display', type == 0 ? 'block' : 'none');
            $('#recentViewMealKit').css('display', type == 0 ? 'none' : 'block');

            $.each(categoryButtons, function(index, button) {
                var style = categoryButtonStyles[type === index ? 0 : 1];
                $(button).css(style);
            });
        }

        window.onload = changeMyRecentView(0);
    </script>
</body>

</html>
