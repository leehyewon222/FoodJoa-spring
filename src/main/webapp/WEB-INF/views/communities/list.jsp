<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${ pageContext.request.contextPath }" />
<c:set var="resourcePath" value="${ contextPath }/resources" />

<%-- <c:set var="id" value="${ sessionScope.userId }"/> --%>
<c:set var="userId" value="admin" />

<c:set var="totalRecord" value="${ communities.size() }"/>

<c:set var="numPerPage" value="10" />
<c:set var="totalPage" value="0" />
<c:choose>
	<c:when test="${ not empty nowPage }">
		<c:set var="nowPage" value="${ nowPage }" />
	</c:when>
	<c:otherwise>
		<c:set var="nowPage" value="0" />
	</c:otherwise>
</c:choose>
<c:set var="beginPerPage" value="0" />

<c:set var="pagePerBlock" value="5" />
<c:set var="totalBlock" value="0" />
<c:choose>
	<c:when test="${ not empty nowBlock }">
		<c:set var="nowBlock" value="${ nowBlock }" />
	</c:when>
	<c:otherwise>
		<c:set var="nowBlock" value="0" />
	</c:otherwise>
</c:choose>

<c:set var="beginPerPage" value="${ nowPage * numPerPage }" />

<c:set var="totalPage" value="${ Math.ceil(totalRecord / numPerPage) }" />
<c:set var="totalBlock" value="${ Math.ceil(totalPage / pagePerBlock) }" />

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	
	<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@200..900&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="${ resourcePath }/css/community/list.css">

</head>
<body>
	<div id="commnunity-container">
		<div id="community-header" align="center">
			<p class="community_p1">COMMUNITY</p>
			<p class="community_p2">자유게시판</p>
			<p>자유롭게 글을 작성해보세요</p>
		</div>
	</div>
	
	<div id="community-body">
		<table class="table-list" width="100%">
			<tr align="center" bgcolor="#e9ecef">
				<td class="col-no" width="15%">글번호</td>
				<td class="col-title" width="45%">제목</td>
				<td class="col-write" width="15%">작성자</td>
				<td class="col-views" width="10%">조회수</td>
				<td class="col-date" width="15%">작성날짜</td>
			</tr>
			<c:choose>
				<c:when test="${ empty communities }">
					<tr align="center">
						<td colspan="5">등록된 글이 없습니다.</td>
					</tr>
				</c:when>
				
				<c:otherwise>
					<c:set var="loopFlag" value="true"/>
					<c:forEach var="i" begin="${ beginPerPage }" end="${ beginPerPage + numPerPage - 1 }" step="1">
						<c:if test="${ loopFlag == true and i == totalRecord }">
							<c:set var="loopFlag" value="false"/>
						</c:if>
						
						<c:if test="${ loopFlag == true }">
							<c:set var="community" value="${ communities[i].communityVO }"/>
							<c:set var="member" value="${ communities[i].memberVO }"/>
							
							<tr align="center">
								<td>${ totalRecord - i }</td>
								<td align="left">
									<a href="${ contextPath }/Community/read?no=${ community.no }">
										${ community.title }
									</a>
								</td>
								<td>${ member.nickname }</td>
								<td>${ community.views }</td>
								<td><fmt:formatDate value="${community.postDate}" pattern="yyyy-MM-dd" /></td>
							</tr>
						</c:if>
					</c:forEach>					
				</c:otherwise>
			</c:choose>

			<tr class="page_number">
				<td colspan="5" align="center">
					<c:if test="${ totalRecord > 0 }">
						<c:if test="${ nowBlock > 0 }">
							<a href="${ contextPath }/Community/list?nowBlock=${ nowBlock - 1 }&nowPage=${ (nowBlock - 1) * pagePerBlock }">
								<
							</a>
						</c:if>
						
						<c:set var="loopFlag" value="true"/>
						<c:forEach var="i" begin="0" end="${ pagePerBlock - 1 }" step="1">
							<c:if test="${ loopFlag == true && (nowBlock * pagePerBlock) + i == totalPage }">
								<c:set var="loopFlag" value="false"/>
							</c:if>
							
							<c:if test="${ loopFlag == true }">
								<a href="${ contextPath }/Community/list?nowBlock=${ nowBlock }&nowPage=${ (nowBlock * pagePerBlock) + i }">
									${ (nowBlock * pagePerBlock) + i + 1 }
								</a>
							</c:if>
						</c:forEach>
						
						<c:if test="${ nowBlock + 1 < totalBlock }">
							<a href="${ contextPath }/Community/list?nowBlock=${ nowBlock + 1 }&nowPage=${ (nowBlock + 1) * pagePerBlock }">
								>
							</a>
						</c:if>
					</c:if>					
				</td>
			</tr>
			<tr>
				<td colspan="5" align="center">
					<div class="community-table-bottom">
						<form action="${ contextPath }/Community/searchList" method="post" 
							name="frmSearch" onsubmit="fnSearch(); return false;">
							<span class="select-button">
								<select name="key">
									<option value="titleContent">제목+내용</option>								
									<option value="writerContent">작성자</option>								
								</select>
							</span>
							<span class="community-search-area">
								<input type="text" name="word" id="word" placeholder="검색어를 입력해주세요">
							</span>
							<span class="community-search-button">
								<input type="submit" value="검색"/>
							</span>
						</form>
						<c:if test="${ not empty id }">
							<div class="community-write-button">
								<input type="button" value="글쓰기" onclick="onWriteButton(event)">
							</div>
						</c:if>
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	
	<script>
		function fnSearch(){
			var word = document.getElementById("word").value;
			
			if(word == null || word == ""){
				alert("검색어를 입력하세요.");
				document.getElementById("word").focus();
				
				return false;
			}
			else{
				
				document.frmSearch.submit();
			}
		}
	
		function onWriteButton(event) {
			event.preventDefault();
			
			location.href='${ contextPath }/Community/write';
		}
		
		function frmSearch(){
			var word = document.getElementById("word").value;
			
			if(word == null || word == ""){
				alert("검색어를 입력해주세요");
				
				document.getElementById("word").focus();
				
				return false;
			}
		}
	</script>
</body>
</html>