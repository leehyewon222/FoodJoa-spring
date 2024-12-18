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

<c:set var="id" value="${sessionScope.userId }"/>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	
    <script type="text/javascript" src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=ug8ym1cpbw&submodules=geocoder"></script>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>

	<link rel="stylesheet" href="${ resourcesPath }/css/share/sharewrite.css">

</head>

<body>
	<div id="top_container" align="center">
		<p class="community_p1">COMMUNITY</p>
		<p class="community_p2">재료 나눔 게시판</p>
		<p>자유롭게 글을 작성해보세요</p>
	</div>
	<div id="container">
		<form method="POST" enctype="multipart/form-data">
			<table width="100%">
	        	<tr>
	        		<td colspan="2" align="right">
						<input type="button" class="write" value="작성" onclick="onSubmit(event)">
						<input type="button" class="write" value="취소" onclick="onCancleButton(event)">
					</td>
				</tr>
                <tr>
                    <th>제목</th>
                    <td><input type="text" id="title" name="title" required placeholder="제목을 입력하세요"></td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td><textarea id="contents" name="contents" rows="4" placeholder="내용을 입력하세요"required></textarea></td>
                </tr>
				<tr>
				    <th>사진 추가</th>
				    <td>
				        <input type="file" name="thumbnail" accept=".png, .jpg, .jpeg" 
				        	required id="thumbnail" onchange="handleFileSelect(this.files)"><br>
				        <div id="imageContainer"></div>
				    </td>
				</tr>
                <tr>
                    <th>지도</th>
                    <td>
                    	<input type="hidden" id="lat" name="lat">
                    	<input type="hidden" id="lng" name="lng">
						<input type="text" id="naverAddress" placeholder="도로명 주소를 입력해주세요">
						<input type="button" id="naverSearch" value="검색">
                    	<div id="map" style="width:100%;height:400px;"></div>
					</td>
                </tr>
                <tr>
	                <td colspan="2" align="right">
						<input type="button" class="write" value="작성" onclick="onSubmit(event)">
						<input type="button" class="write" value="취소" onclick="onCancleButton(event)">
					</td>
				</tr>
            </table>
        </form>
    </div>
    
    <script src="${ resourcesPath }/js/share/shareWrite.js"></script>
	<script>
		function onSubmit(event) {
			event.preventDefault();
			
			if ($("#lat").val() == "" || $("#lng").val() == "") {
				alert('지도 좌표를 선택해주세요');
				return;
			}
			
			const formData = new FormData();
			formData.append('id', '${ sessionScope.userId }');
			formData.append('thumbnail', selectedFile.name);
			formData.append('title', $("#title").val());
			formData.append('contents', $("#contents").val());
			formData.append('lat', $("#lat").val());
			formData.append('lng', $("#lng").val());
			
			formData.append('file', selectedFile);
			
			$.ajax({
				url: '${ contextPath }/Share/writePro',
				type: 'post',
				data: formData,
			    processData: false,
			    contentType: false,
			    success: function(responseData) {
					if (responseData != "0") {
						alert('게시글을 작성했습니다.');
						location.href = '${ contextPath }/Share/read?no=' + responseData;	
					}
					else {
						alert('게시글 작성에 실패했습니다.');
					}
			    },
			    error: function(error) {
			        console.log("error", error);
					alert('게시글 작성 중 통신 에러');
			    }
			});
			
			$("#frmWrite").submit();
		}
		
		function onCancleButton(event) {
			event.preventDefault();
			
			history.go(-1);
		}
	</script>
</body>
</html>