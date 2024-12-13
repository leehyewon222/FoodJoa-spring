<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	
<%
    // request에서 userId 값 가져오기
     String userId = (String) request.getAttribute("userId");

    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=utf-8");

%>

<c:set var="contextPath" value="${ pageContext.request.contextPath }" />
<c:set var="resourcesPath" value="${ contextPath }/resources" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${ resourcesPath }/css/member/deletemember.css">
</head>
<body>
    <div id="container">
        <h3>정말로... 탈퇴하시겠어요...?</h3>
        <p>탈퇴를 원하신다면 아래의 본인 아이디를 다시 입력해주세요...</p>
        <form action="${ contextPath }/Member/deleteMemberPro" method="post">
            <!-- 로그인된 아이디를 input 태그에 readonly로 표시 -->
            <label for="readonlyId">로그인된 아이디</label>
            <input type="text" id="readonlyId" name="readonlyId" value="<%= session.getAttribute("userId") %>" readonly />
            <br><br>
            <label for="inputId">아이디를 다시 입력해주세요</label>
            <input type="text" id="inputId" name="inputId" required />
            <br><br>

            <button type="submit">탈퇴하기</button>
        </form>
    </div>
</body>
</html>

