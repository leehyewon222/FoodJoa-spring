<%@page import="java.util.ArrayList"%>
<%@page import="com.foodjoa.member.vo.MemberVO"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.time.LocalDate"%>
<%@ page import="java.time.temporal.ChronoUnit"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.time.ZoneId"%>
<%@ page import="com.foodjoa.member.dao.MemberDAO"%>

<c:set var="contextPath" value="${ pageContext.request.contextPath }" />
<c:set var="resourcesPath" value="${ contextPath }/resources" />

<%
request.setCharacterEncoding("UTF-8");
response.setContentType("text/html; charset=utf-8");
%>

<jsp:useBean id="now" class="java.util.Date" />

<fmt:parseDate var="joinDate" value="${member.joinDate}" pattern="yyyy-MM-dd"/>
<fmt:parseNumber var="specificDay" value="${joinDate.time / (1000*60*60*24)}" integerOnly="true"/>
<fmt:parseNumber var="today" value="${now.time / (1000*60*60*24)}" integerOnly="true"/>

<c:set var="daysBetween" value="${today - specificDay}" />
<jsp:useBean id="stringParser" class="Common.StringParser" />
<c:set var="id" value="${sessionScope.userId}" />

<!DOCTYPE html>
<html lang="ko">

<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="${ resourcesPath }/css/member/mypagemain.css">
</head>

<body>
	<div class="mypage-container">
		<div class="mypage-header">
			<span>마이 페이지</span>
		</div>
	
		<div class="profile-wrapper">
			 <div class="profile-section" style="display: flex; justify-content: space-between;">
			    <div class="profile-image">
			        <img src="${ resourcesPath }/images/member/userProfiles/${member.id}/${member.profile}" >
			    </div>
			    <div class="profile-info">
			        <h2>${member.nickname}</h2>
			        <p><c:choose>
			            <c:when test="${ not empty member.joinDate }">
			                ${member.nickname}님은 푸드조아와 함께한지 <strong>${daysBetween + 1}</strong>일째입니다!
			            </c:when>
			            <c:otherwise>
			                <p>가입 정보를 가져올 수 없습니다. 관리자에게 문의하세요.</p>
			            </c:otherwise>
			        </c:choose></p>
			        <div><button id="updateButton">정보수정</button></div>
			    </div>
			    
			    <div class="right-section" style="display: flex; flex-direction: column; align-items: flex-end;">
			        <div id="point" style="margin-bottom: 10px;">
			            <img src="${resourcesPath}/images/member/point.png" style="width:40px; display: inline-block; vertical-align: middle;">
			            <p style="display: inline-block; margin-left: 5px; vertical-align: middle;">${member.point} 포인트</p>
			        </div>
			
			        <div id="btnKakao">
			            <!-- 카카오톡 공유 버튼 코드 -->
			            <a id="kakaotalk-sharing-btn" href="javascript:shareMessage()" style="display: inline-block; vertical-align: middle;">
			                <img src="${resourcesPath}/images/member/kakaologo.png" alt="카카오톡 링크 공유하기" style="width:40px; vertical-align: middle;">
			                <span style="vertical-align: middle;">친구 초대하기!</span>
			            </a>
			        </div>
			    </div>
			 </div>

			 <div class="manage-section">
				<div>
			 		<a href="${contextPath}/Recipe/myrecipes">
						<p>내 레시피 관리</p>
						<img src="${resourcesPath}/images/member/receipe.png" >
					</a>
				</div>
				<div>
					<a href="${contextPath}/Mealkit/myMealkits">
						<p>내 제품 관리</p>
						<img src="${resourcesPath}/images/member/food.png" >
					</a>
				</div>
				<div>
					<a href="${contextPath}/Member/myreviews">
						<p>내 리뷰 관리</p>
						<img src="${resourcesPath}/images/member/review.png">
					</a> 
	
				</div>
			</div>
	
			<!-- Info Sections -->
			<div class="info-section">
				<div>주문/배송조회</div>
					<div class="info1">
					<span>주문건수 : ${deliveredCounts[0] + deliveredCounts[1] + deliveredCounts[2]}</span> &nbsp;&nbsp; |
					&nbsp;&nbsp; <span>배송준비중 : ${deliveredCounts[0]}</span>
					&nbsp;&nbsp; | &nbsp;&nbsp; <span>배송중 : ${deliveredCounts[1]}</span>
					&nbsp;&nbsp; | &nbsp;&nbsp; <span>배송완료 : ${deliveredCounts[2]}</span>
					<a href="${contextPath}/Member/mydelivery"
						style="margin-left: auto;">더보기</a>
				</div>
			</div>
	
			<div class="info-section">
				<div>내 마켓 발송조회</div>
				<div class="info1">
					<span>주문건수 : ${sendedCounts[0] + sendedCounts[1] + sendedCounts[2]}</span> &nbsp;&nbsp; |
					&nbsp;&nbsp; <span>발송준비중 : ${sendedCounts[0]}</span>
					&nbsp;&nbsp; | &nbsp;&nbsp; <span>발송중 : ${sendedCounts[1]}</span>
					&nbsp;&nbsp; | &nbsp;&nbsp; <span>발송완료 : ${sendedCounts[2]}</span>
					<a href="${contextPath}/Member/sendmealkit"
						style="margin-left: auto;">더보기</a>
				</div>
			</div>
			<br>
			
			<div id="btnKakao">
			    <img src="${ resourcesPath }/images/member/kakaologo.png"
			    		id="kakao-link-btn" alt="카카오톡 링크 공유하기"
			    		style="width:40px; height:auto;">
	    		<p>친구 초대하기!</p>	   
			</div><br>
			
			<div>
			    <h1 id="showFormButton">일정 입력</h1>
			    <div id="calendarFormContainer"> 
			        <form id="calendarForm" action="${contextPath}/Member/mypagemain" method="post">
			            <label>일정 제목:</label>
			            <input type="text" name="summary" required><br>
			            <label>일정 내용:</label>
			            <textarea name="description" required></textarea><br>
			            <label>장소:</label>
			            <input type="text" name="location" required><br>
			            <label>시작 시간:</label>
			            <input type="datetime-local" name="startTime" required><br>
			            <label>종료 시간:</label>
			            <input type="datetime-local" name="endTime" required><br>
			            <button type="submit">입력!</button>
			        </form>
			    </div>
			</div>
			<br><br>
			<div>
			    <h2 id="showListButton">일정 목록</h2>
			    <div id="calendarListContainer"> 
			        <p>
			            <c:choose>
			                <c:when test="${empty calendars}">
			                    <p>일정이 없습니다.</p>
			                </c:when>
			                <c:otherwise>
			                    <table border="1">
			                        <thead>
			                            <tr>
			                                <th>제목</th>
			                                <th>내용</th>
			                                <th>장소</th>
			                                <th>시작 시간</th>
			                                <th>종료 시간</th>
			                                <th>삭제</th>
			                            </tr>
			                        </thead>
			                        <tbody>
			                           <c:forEach var="calendar" items="${calendars}">
									    <tr>
									        <td>${calendar.summary}</td>
									        <td>${calendar.description}</td>
									        <td>${calendar.location}</td>
									        <td>${calendar.startTime}</td>
									        <td>${calendar.endTime}</td>
									        <!-- 삭제 버튼을 클릭하면 특정 calendarId를 전달 -->
									       <form action="${contextPath}/Member/mypagemain" method="post">
											    <input type="hidden" name="calendarId" value="${calendar.id}">
											    <td><button type="submit">삭제</button></td>
											</form>
									    </tr>
									</c:forEach>
			                        </tbody>
			                    </table>
			                </c:otherwise>
			            </c:choose>
			        </p>
			    </div>
			</div><br>
			<div class="info-section2">
				<div class="impormation">
					<a href="${contextPath}/Member/impormation" class="impormation">※개인정보처리방침</a>
				</div>
			</div>
			<br>
			<div class="info-section3">
				<div>
					<a href="${contextPath}/Member/deleteMember">
						<button id="getout">탈퇴하기</button>
					</a>
				</div>
			</div>
		</div>
	</div>

	<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
	<script>
		document.getElementById('updateButton').onclick = function() {
			location.href = '${contextPath}/Member/profileupdate';
		};
		
		// 파일을 선택하면 미리보기 이미지를 표시
		function previewImage(event) {
			const reader = new FileReader();
			reader.onload = function() {
				const output = document.getElementById('profilePreview');
				output.src = reader.result;
			};
			reader.readAsDataURL(event.target.files[0]);
		}
		
		//<![CDATA[
	    // // 사용할 앱의 JavaScript 키를 설정해 주세요.
	    Kakao.init('ab039484667daeed90e5c9efa4980315');
	    // // 카카오링크 버튼을 생성합니다. 처음 한번만 호출하면 됩니다.
	    Kakao.Link.createScrapButton({
	        container: '#kakao-link-btn',
	        requestUrl: 'http://localhost:8090/FoodJoa/Main/home',
	        templateId : 115441
	    });
	    //]]>

	    // 카카오톡 공유 기능을 위한 함수
	    function shareMessage() {
	        var userId = "${member.id}"; 

	        Kakao.Link.sendCustom({
	            templateId: 115441,  
	            templateArgs: {
	                "userId": userId  
	            }
	        });
	    }

	    // 버튼 클릭 시 카카오톡 메시지 공유창 열리도록 이벤트 설정
	    document.getElementById('kakao-link-btn').onclick = shareMessage;
		
	    
	    
	    
	    document.getElementById('showFormButton').addEventListener('click', function() {
	        toggleVisibility('calendarFormContainer', 'calendarListContainer');
	    });

	    document.getElementById('showListButton').addEventListener('click', function() {
	        toggleVisibility('calendarListContainer', 'calendarFormContainer');
	    });

	    function toggleVisibility(showId, hideId) {
	        const showElement = document.getElementById(showId);
	        const hideElement = document.getElementById(hideId);

	        // 숨길 섹션 닫기
	        if (hideElement.style.display === 'block') {
	            hideElement.style.display = 'none';
	            hideElement.classList.remove('show');
	        }

	        // 보일 섹션 열기
	        if (showElement.style.display === 'none') {
	            showElement.style.display = 'block';
	            setTimeout(() => showElement.classList.add('show'), 10);
	        } else {
	            showElement.style.display = 'none';
	            showElement.classList.remove('show');
	        }
	    }  
	    
	    $("#submitButton").click(function () {
	        $.ajax({
	            url: "${pageContext.request.contextPath}/calendar/add",
	            type: "POST",
	            data: $("#calendarForm").serialize(),
	            success: function (data) {
	                $("#result").html(data);
	            },
	            error: function () {
	                alert("일정 추가 중 오류가 발생했습니다.");
	            }
	        });
	    });
	</script>
</body>

</html>