<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<%
	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html; charset=UTF-8");
%>

<c:set var="contextPath" value="${ pageContext.request.contextPath }" />
<c:set var="resourcesPath" value="${ contextPath }/resources" />

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	
	<title>Insert title here</title>
	
    <script type="text/javascript" src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=ug8ym1cpbw&submodules=geocoder"></script>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    
    <link rel="stylesheet" href="${ resourcesPath }/css/share/shareupdate.css">
</head>

<body>
	<h1>재료 나눔 게시글 작성</h1>
	<div id="container">
		<form id="frmUpdate" action="${contextPath}/Share/updatePro" method="POST" enctype="multipart/form-data">
			<input type="hidden" id="no" name="no">
			<input type="hidden" id="id" value="${ sessionScope.userId }">
			<input type="hidden" id="views" name="views">
			<input type="hidden" id="nowBlock" name="nowBlock">
			<input type="hidden" id="nowPage" name="nowPage">
			<table width="100%">
	        	<tr>
	        		<td colspan="2" align="right">
						<input type="button" class="write" value="수정" onclick="onSubmit(event, '${contextPath}')">
						<input type="reset" class="write" value="취소" onclick="onCancle(event)">
					</td>
				</tr>	
	            <tr>
                    <th>제목</th>
                    <td><input type="text" id="title" name="title" required placeholder="제목을 입력하세요"></td>
                </tr>
                <tr>
				    <th>사진 추가</th>
				    <td>
				    	<input type="hidden" id="origin-thumbnail" name="origin-thumbnail">
				        <input type="file" name="thumbnail" accept=".png, .jpg, .jpeg" 
				        	required id="thumbnail" onchange="handleFileSelect(this.files)"><br>
				        <div id="imageContainer"></div>
				    </td>
				</tr>
                <tr>
                    <th>내용</th>
                    <td><textarea id="contents" name="contents" rows="4" placeholder="내용을 입력하세요"required>${share.contents }</textarea></td>
                </tr>
                <tr>
                    <th>지도</th>
                    <td>
                    	<input type="hidden" id="lat" name="lat">
                    	<input type="hidden" id="lng" name="lng">
						<input type="text" id="naverAddress" >
						<input type="button" id="naverSearch" value="검색">
                    	<div id="map" style="width:100%;height:400px;"></div>
					</td>
                </tr>
                <tr>
	                <td colspan="2" align="right">
						<input type="button" class="write" value="수정" onclick="onSubmit(event, '${contextPath}')">
						<input type="reset" class="write" value="취소" onclick="onCancle(event)">
					</td>
				</tr>
            </table>
        </form>
    </div>
    
    
	<script src="${resourcesPath }/js/share/shareUpdate.js"></script>
	<script>
		function initialize() {
			$("#no").val('${share.no}');
			$("#views").val('${share.views}');
			$("#nowPage").val('${nowPage}');
			$("#nowBlock").val('${nowBlock}');
			$("#title").val('${share.title}');
			$("#origin-thumbnail").val('${share.thumbnail}');
			
			var $img = $('<img>', {
			    src: '${resourcesPath}/images/share/thumbnails/${share.no}/${share.thumbnail}'
			});

			$('#imageContainer').append($img);
			
			let lat = '${share.lat}';
			let lng = '${share.lng}';
			
			$("#lat").val(lat);
			$("#lng").val(lng);
			
			point = new naver.maps.Point(Number(lng), Number(lat));
			
			searchCoordinateToAddress(point);
		}
		
		function onCancle(event){
			event.preventDefault();
			history.go(-1);			
		}
		
		window.onload = initialize();
	</script>
</body>
</html>