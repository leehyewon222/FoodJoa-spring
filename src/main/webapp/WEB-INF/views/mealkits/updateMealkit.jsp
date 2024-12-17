<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<c:set var="resourcesPath" value="${contextPath}/resources" />
<jsp:useBean id="stringParser" class="Common.StringParser"/>

<c:set var="orders" value="${stringParser.splitString(mealkitInfo.orders) }"/>
<c:set var="pictures" value="${stringParser.splitString(mealkitInfo.pictures) }"/>
	
<c:set var="id" value="${sessionScope.userId }"/>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>밀키트 판매 게시글 수정</title>
    
    <script src="https://code.jquery.com/jquery-latest.min.js"></script>

    <link rel="stylesheet" type="text/css" href="${resourcesPath}/css/mealkit/write.css">
</head>
<body>
	<div id="mealkit-container">
		<form action="${contextPath}/Mealkit/updatePro" method="post" id="frmWrite" enctype="multipart/form-data">
			<table class="write-form" width="100%">
				<tr>
					<th width="30%">글 제목</th>
					<td width="70%">
						<div class="write-title-area">
							<input type="text" class="title" name="title" value="${mealkitInfo.title }" placeholder="ex) 김치볶음밥 밀키트" required>

							<input type="hidden" name="id" value="${id }">
							<input type="hidden" name="no" value="${mealkitInfo.no }">
							<input type="hidden" id="origin_pictures" name="origin_pictures" value="${mealkitInfo.pictures }">
						</div>
					</td>
				</tr>
				<tr>
					<th>
						사진 추가 <br>
						<small class="notice-text">
							※ 사진은 최대 5장까지<br>업로드 가능합니다.<br>※ 해당 사진을 클릭하면<br>삭제됩니다.
						</small>
					</th>
					<td>
						<div id="write-preview-area">
							<ul id="imagePreview">
								<li>
									<button type="button" id="addFileBtn" onclick="triggerFileInput()">사진 추가</button>
								</li>
							</ul>							
						</div>
						<input type="file" id="pictureFiles" name="pictureFiles" 
							accept=".jpg,.jpeg,.png" multiple onchange="handleFileSelect(this.files)">
						<input type="hidden" id="pictures" name="pictures">
					</td>
				</tr>
				<tr>
					<th>카테고리</th>
					<td>
						<select class="category" name="category" required>
					        <option value="" <c:if test="${empty mealkitInfo.category}">selected</c:if>>선택하세요</option>
					        <option value="0" <c:if test="${mealkitInfo.category == 1}">selected</c:if>>한식</option>
					        <option value="1" <c:if test="${mealkitInfo.category == 2}">selected</c:if>>일식</option>
					        <option value="2" <c:if test="${mealkitInfo.category == 3}">selected</c:if>>중식</option>
					        <option value="3" <c:if test="${mealkitInfo.category == 4}">selected</c:if>>양식</option>
					    </select>
					</td>
				</tr>
				<tr>
					<th>간단 소개글</th>
					<td>
						<textarea name="contents" class="contents" rows="4" 
							placeholder="해당 밀키트의 조리법을 적어주세요" required>${mealkitInfo.contents }</textarea>
					</td>
				</tr>
				<tr>
					<th>가격</th>
					<td><input type="text" name="price" class="price" value="${mealkitInfo.price }" required min="0">원</td>
				</tr>
				<tr>
					<th>재고 수량</th>
					<td><input type="number" name="stock" class="stock" value="${mealkitInfo.stock }" required min="0">개</td>
				</tr>
				<tr>
					<th>간단 조리 순서</th>
					<td>
						<div>
							<input type="button" class="add-orders" value="순서 추가하기">
						</div>
						<div class="orders-container">
		    				<table width="100%">
		    				</table>
						</div>
						<input type="hidden" id="orders" name="orders">
					</td>
				</tr>
				<tr>
					<th>원산지 표기</th>
					<td>
						<input type="text" name="origin" class="origin" value="${mealkitInfo.origin }" 
							placeholder="ex) 쌀(국내산), 소고기(미국산), ..." required >
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="button" class="write" value="작성 완료" onclick="onSubmit(event, '${contextPath}')">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
	let originSelectedFileNames = [];
	let selectedFiles = [];
	let selectedRealFiles = [];

	initialize();

	function initialize() {
		
		<c:forEach var="order" items="${orders}">
	        var newOrderHtml = `
				<tr class="added-orders">
					<td width="70%">
						<div class="added-order">${order}</div>
					</td>
					<td height="30%">
						<button type="button" class="remove-orders">삭제</button>
					</td>
				</tr>`;
	        $(".orders-container>table").append(newOrderHtml);
        </c:forEach>
	    
	    // 사진 미리보기 추가
		<c:forEach var="picture" items="${pictures}">
	        const fileName = "${picture}";
	        originSelectedFileNames.push(fileName);
	
	        const $li = $('<li>');
	        const $img = $('<img>', {
	            class: 'review-origin-preview-image',
	            src: "${resourcesPath}" + "/images/mealkit/thumbnails/" + "${mealkitInfo.no}" + "/" + fileName,
	            css: {
	                cursor: 'pointer',
	            },
	        });
	
	        $img.on('click', function () {
	            $(this).parent().remove();
	            removeOriginFileName(fileName);
	        });
	
	        $li.append($img);
	        $('#imagePreview').append($li);
	    </c:forEach>
	}

	function removeOriginFileName(fileName) {
	    const index = originSelectedFileNames.indexOf(fileName);
	    if (index > -1) {
	        originSelectedFileNames.splice(index, 1);
	    }
	}
			
		function removeOriginFileName(fileName) {
			for (let i = 0; i < originSelectedFileNames.length; i++) {
				if (originSelectedFileNames[i] == fileName) {
					originSelectedFileNames.splice(i, 1);
					break;
				}
			}
		}
			
	// 선택한 파일 제거
	function removeSelectedFile(fileIdentifier) {
		for (let i = 0; i < selectedFiles.length; i++) {
			if (selectedFiles[i] == fileIdentifier) {
				selectedFiles.splice(i, 1);
				selectedRealFiles.splice(i, 1);
				break;
			}
		}
	}

	function setPicturesString() {
		let strings = [];

		selectedFiles.forEach(fileIdentifier => {
			// fileIdentifier는 "파일이름-파일크기" 형식
			let fileName = fileIdentifier.split('-')[0]; // 파일 이름 부분만 추출
			strings.push(fileName);
		});

		let pictures = combineStrings(strings);

		document.getElementsByName('pictures')[0].value = pictures;
	}

	function handleFileSelect(files) {
		const imagePreview = document.getElementById('imagePreview');

		Array.from(files).forEach(file => {
			if (file.type.startsWith('image/')) {
				let fileIdentifier = file.name + '-' + file.size;
				
				if (!selectedFiles.includes(fileIdentifier)) {
					selectedFiles.push(fileIdentifier);
					selectedRealFiles.push(file);
		
					const reader = new FileReader();
		
					reader.readAsDataURL(file);
		
					reader.onload = function(e) {
						const li = document.createElement('li');
						const img = document.createElement('img');
						img.src = e.target.result;
		
						img.dataset.filename = file.name;
						img.classList.add('preview_image');
		
						img.addEventListener('click', function() {
							imagePreview.removeChild(img.parentElement);
							removeSelectedFile(fileIdentifier);
							document.getElementById('pictureFiles').value = '';
						});
		
						img.style.cursor = 'pointer';
						
						li.appendChild(img);
						imagePreview.appendChild(li);
					}				
				} 
			}
		});

		document.getElementById('pictureFiles').value = '';
	}

	function triggerFileInput() {
		if (selectedFiles.length >= 5) {
			alert("사진은 최대 5장까지 추가할 수 있습니다.");
			return;
		}
		document.getElementById('pictureFiles').click();
	}

	// 전송버튼 
	function onSubmit(e, contextPath) {
		e.preventDefault();

		setOrdersString();
		setPicturesString();

		const formData = new FormData();
		formData.append('no', $("input[name='no']").val());
		formData.append('id', $("input[name='id']").val());
		formData.append('title', $("input[name='title']").val());
		formData.append('pictures', $("#pictures").val());
		formData.append('origin_pictures', $("#origin_pictures").val());
		formData.append('origin_selected_pictures', combineStrings(originSelectedFileNames));
		formData.append('category', $("select[name='category']").val());
		formData.append('contents', $("textarea[name='contents']").val());
		formData.append('price', $("input[name='price']").val());
		formData.append('stock', $("input[name='stock']").val());
		formData.append('origin', $("input[name='origin']").val());
		formData.append('orders', $("#orders").val());

		selectedRealFiles.forEach((file, index) => {
			formData.append('file' + index, file);
		});

		$.ajax({
			url: contextPath + "/Mealkit/updatePro",
			type: "POST",
			data: formData,
			processData: false,
			contentType: false,
			success: function(response) {
				if (response) {
					alert("글 작성이 성공적으로 완료되었습니다.");
					location.href = contextPath + "/Mealkit/info?no=" + response;
				} else {
					alert("글 작성에 실패했습니다. 다시 시도해주세요.");
				}
			},
			error: function() {
				alert("서버 요청 중 에러가 발생했습니다.");
			}
		});
	}

	// 받은 조리 순서를 하나의 배열로 만드는 함수 
	function setOrdersString() {

		let orders = $(".added-order");
		let ordersString = [];
				
		orders.each(function(index, element) {
			ordersString.push($(element).text());
		});
			
		let combinedOrderString = combineStrings(ordersString);
		
		document.getElementsByName('orders')[0].value = combinedOrderString;
	}
				
	// 문자열을 합치는 함수
	function combineStrings(strings) {
		
		let result = strings.map(str => {
	        const length = str.length;
	        // 길이를 4자리로 포맷하고 0으로 패딩
	        const lengthStr = String(length).padStart(4, '0');
	        return lengthStr + str; // 길이와 문자열을 합침
	    }).join(''); // 모든 요소를 하나의 문자열로 결합
		
		console.log("result : " + result);
		return result;
	}
	$(function() {
	    $(".write").click(function(event) {
	        event.preventDefault();
	    });

	    $(".add-orders").click(function() {
	        var newOrderHtml = 
	            "<tr class='orders-row'>" +
	                "<td width='70%'>" +
	                    "<input type='text' class='name-orders' placeholder='조리 순서를 간단히 적어주세요.'>" +
	                "</td>" +
	                "<td width='30%'>" +
	                    "<button type='button' class='addrow-orders'>추가</button>" +
	                    "<button type='button' class='cancle-orders'>취소</button>" +
	                "</td>" +
	            "</tr>";
	        $(".orders-container>table").append(newOrderHtml);
	    });

	    // 추가 버튼을 눌렀을 때 
	    $(document).on('click', '.addrow-orders', function() {
	        var $row = $(this).closest('.orders-row');
	        var name = $row.find('.name-orders').val();
	        
	        if(name) {
	            var newOrderHtml = 
	                "<tr class='added-orders'>" +
	                    "<td width='70%'>" +
	                        "<div class='added-order'>" + name + "</div>" +
	                    "</td>" +
	                    "<td height='30%'>" +
	                        "<button type='button' class='remove-orders'>삭제</button>" +
	                    "</td>" +
	                "</tr>";
	            $row.replaceWith(newOrderHtml);    
	        } else {
	            alert("조리 순서를 입력 해주세요.");
	        }
	    });

	    // 조리 순서 취소 버튼 
	    $(document).on('click', '.cancle-orders', function() {
	        $(this).closest('.orders-row').remove();
	    });

	    // 조리 순서 삭제 버튼 
	    $(document).on('click', '.remove-orders', function() {
	        $(this).closest('.added-orders').remove();
	    });
	});
	</script>
</body>
</html>
