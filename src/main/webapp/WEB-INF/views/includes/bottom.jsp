<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
request.setCharacterEncoding("UTF-8");
%>
<c:set var="contextPath" value="${ pageContext.request.contextPath }" />

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	
	<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@200..900&display=swap" rel="stylesheet">
	<style>	
		a:link, a:visited, a:hover, a:active {
			color: inherit;
			text-decoration: none;
		}
	
		#bottom_container {
			width:100%;
			font-family: "Noto Serif KR", serif;
			font-optical-sizing: auto;
			text-align: center;
			padding: 20px 0;
			border-top: 1px solid #e0e0e0;
			margin-top: 50px; 
		}
		
		body {
			font-family: "Noto Serif KR", serif;
			margin: 0;
			padding: 0;
			background-color: #ffffff; 
			color: #333333; 
		}
		
		#bottomMenu {
			margin-bottom: 20px;
		}
		
		#bottomMenu table {
			width: 100%;
			border-collapse: collapse;
		}
		
		#bottomMenu td {
			font-size: 14px;
			padding: 10px;
			color: #666666;
			cursor: pointer;
			transition: color 0.3s ease;
		}
		
		#bottomMenu td:hover {
			color: #000000;
		}
		
		#sns {
			margin-top: 20px;
		}
		
		#sns a img {
			width: 300px;
			height: 100px;
			object-fit: contain;
			transition: transform 0.3s ease;
		}
		
		#company {
			font-size: 12px;
			line-height: 1.6;
			color: #888888;
		}
		
		#company p {
			margin: 5px 0;
		}	
	</style>
</head>

<body>
	<div id="bottom_container">
		<div id="bottomMenu">
			<div id="sns">
				<a href="#">
					<img src="${ contextPath }/resources/images/mainpage/bottom_image.png" alt="하단이미지">
				</a>
			</div>
			<table>
				<tr>
					<td>이용약관</td>
					<td>개인정보취급방침</td>
					<td>
						<a href="${ contextPath }/Notice/list">
							공지사항
						</a>
					</td>
					<td>자주묻는질문</td>
				</tr>
			</table>
		</div>
		<div id="company">
			<p>상호 : (주)푸드조아</p>
			<p>사업자 등록번호 : 123-45-6789</p>
			<p>통신판매업 신고 : 제 2024-부산-01호</p>
			<p>주소 : 부산광역시 부산진구 중앙대로 100 1동 101호 푸드조아</p>
			<p>전화번호 : 051-456-7890</p>
			<p>이메일 : foodjoa@foodjoa.com</p>
		</div>
	</div>
</body>

</html>