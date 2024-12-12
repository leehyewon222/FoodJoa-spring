<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="Common.StringParser"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.NumberFormat" %>
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
	<title>나만의 음식 판매</title>
	
	<link rel="stylesheet" type="text/css" href="${resourcesPath}/css/mealkit/list.css">
	
	<script type="text/javascript">
		function fnSearch() {
			var word = document.getElementById("word").value;
			
			if(!word || word.trim() === ""){
			    alert("검색어를 입력하세요");
			    document.getElementById("word").focus();
			    return false;
			}
			document.frmSearch.submit();
		}
	</script>
</head>
<body>
	<div id="container">
		<!-- 검색 기능 -->
		<h1>${categoryName}</h1>
		<div id="search-container">
			<div class="search-form-container">
				<form action="${contextPath}/Mealkit/searchlist.pro" method="post" name="frmSearch" 
					onsubmit="fnSearch(); return false;">
		            <select id="key" name="key">
		                <option value="title">밀키트 명</option>
		                <option value="name">작성자</option>
		            </select>
		            
		            <input type="text" class="search-text" name="word" id="word" />
		            <input type="submit" class="search-button" id="search-button" value="검색" />
				</form>
			</div>
				<!-- 글쓰기 -->
			<div class="write-container">
				<!-- <c:if test="${not empty sessionScope.userId}"> </c:if> -->
				<c:if test="${not empty id}">
					<input type="button" id="newContent" value="글쓰기" 
						onclick="location.href='${contextPath}/Mealkit/write'"/>
				</c:if>
			</div>
		</div>
		
		<table class="list">
			<c:if test="${empty mealkitsList}">
				<tr>
					<td> 등록된 글이 없습니다.</td>
				</tr>
			</c:if>
			<c:forEach var="vo" items="${mealkitsList }" varStatus="status">
				<c:if test="${status.index >= pageData.beginPerPage && status.index < (pageData.beginPerPage + pageData.numPerPage)}">
					<c:set var="pictures" value="${vo.mealkitVO.pictures}" />
					<c:set var="thumbnail" value="${stringParser.splitString(vo.mealkitVO.pictures)[0] }" />
			
			        <c:set var="no" value="${vo.mealkitVO.no}" />
			        <c:set var="id" value="${vo.mealkitVO.id}" />
			        <c:set var="title" value="${vo.mealkitVO.title}" />
			        <c:set var="contents" value="${vo.mealkitVO.contents}" />
			        <c:set var="postDate" value="${vo.mealkitVO.postDate}" />
			        <c:set var="views" value="${vo.mealkitVO.views}" />
			        <c:set var="nickName" value="${vo.memberVO.nickname}" />
			        <c:set var="price" value="${vo.mealkitVO.price}" />
			        <c:set var="ratingAvr" value="${vo.mealkitVO.averageRating}"/>
				<tr>
				    <td colspan="2">
				        <a href="${contextPath}/Mealkit/info?no=${no}" class="row-link">
				            <div style="display: flex; align-items: flex-start;">
				                <!-- 이미지 영역 -->
				                <div>
				                    <img class="thumbnail" 
				                         src="${resourcesPath }/images/mealkit/thumbnails/${no}/${thumbnail}">
				                </div>
				                <!-- 텍스트 정보 영역 -->
				                <div class="info-container" style="margin-left: 16px;">
				                    <!-- 작성자, 작성일, 평점, 조회수 -->
				                    <span>
				                        작성자: ${nickName} &nbsp;&nbsp;&nbsp;&nbsp;
				                        작성일: <fmt:formatDate value="${postDate}" pattern="yyyy-MM-dd HH:mm"/> &nbsp;&nbsp;&nbsp;&nbsp;
				                        평점: <fmt:formatNumber value="${ratingAvr}" pattern="#.#" /> &nbsp;&nbsp;&nbsp;&nbsp;
				                        조회수: ${views}
				                    </span>
				                    <br>
				                    <h2><strong>${title}</strong></h2>
				                    <h3>${price} 원</h3>
				                    <br>
				                    <p>설명: ${contents}</p>
				                </div>
				            </div>
				        </a>
				    </td>
				</tr>
				</c:if>
			</c:forEach>
			<!--페이징-->
			<tr align="center">
			    <td class="pagination">
			    	<c:if test="${not empty pageData.totalRecord }">
			    		<c:if test="${nowBlock > 0 }">
			    			<a href="${contextPath }/Mealkit/list?category=${category}&nowBlock=${nowBlock - 1 }&nowPage=${(nowBlock - 1) * pageData.pagePerBlock }">
		                    이전
		                    </a>
			    		</c:if>
			    		
			    		<c:forEach var="i" begin="0" end="${pageData.pagePerBlock - 1}" varStatus="status">
			                <c:set var="pageNumber" value="${nowBlock * pageData.pagePerBlock + status.index + 1}" />
			                <c:if test="${pageNumber <= pageData.totalPage}">
			                    <c:set var="currentClass" value="${pageNumber == nowPage + 1 ? 'current-page' : ''}" />
			                    <a href="${contextPath}/Mealkit/list?category=${category}&nowBlock=${nowBlock}&nowPage=${pageNumber - 1}" 
			                    	class="${currentClass}">
			                        ${pageNumber}
			                    </a>
			                </c:if>
			            </c:forEach>
					    
					    <c:if test="${pageData.totalBlock > pageData.nowBlock + 1}">
					        <a href="${contextPath}/Mealkit/list?category=${category}&nowBlock=${nowBlock + 1}&nowPage=${(nowBlock + 1) * pageData.pagePerBlock }">
					        다음
					        </a>
					    </c:if>
			    	</c:if>
			    </td>
			</tr>
		</table>
	</div>

</body>
</html>
