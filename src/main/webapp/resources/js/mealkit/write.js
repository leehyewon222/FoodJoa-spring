let selectedFiles = [];
let selectedRealFiles = [];

$(function() {
	$(".write").click(function(event) {
		event.preventDefault();
	});
	
	$(".add-orders").click(function() {
		var newOrderHtml = `
			<tr class="orders-row">
				<td width="70%">
					<input type="text" class="name-orders" placeholder="조리 순서를 간단히 적어주세요.">
				</td>
				<td width="30%">
					<button type="button" class="addrow-orders">추가</button>
					<button type="button" class="cancle-orders">취소</button>
				</td>
			</tr>`;

		$(".orders-container>table").append(newOrderHtml);
	});

	// 조리순서) 추가 버튼을 눌렀을 때 
	$(document).on('click', '.addrow-orders', function() {
		var $row = $(this).closest('.orders-row');
		var name = $row.find('.name-orders').val();
		
		if (name) {
			var newOrderHtml = `
				<tr class="added-orders">
					<td width="70%">
						<div class="added-order">` + name + `</div>
					</td>
					<td height="30%">
						<button type="button" class="remove-orders">삭제</button>
					</td>
				</tr>`;

			$row.replaceWith(newOrderHtml);
		}
		else {
			alert("조리 순서를 입력 해주세요.");
		}
	});

	$(document).on('click', '.cancle-orders', function() {
		$(this).closest('.orders-row').remove();
	});

	$(document).on('click', '.remove-orders', function() {
		$(this).closest('.added-orders').remove();
	});
});

// 전송버튼 
function onSubmit(e, contextPath) {
	e.preventDefault();

	setOrdersString();
	setPicturesString();

	const formData = new FormData();
	formData.append('id', $("input[name='id']").val());
	formData.append('title', $("input[name='title']").val());
	formData.append('pictures', $("#pictures").val());
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
		url: contextPath + "/Mealkit/write.pro",
		type: "POST",
		async: true,
		data: formData,
		processData: false,
		contentType: false,
		success: function(response) {
		       if (response) {
					var responseArray = response.split(',');
			        var no = responseArray[0];
			        var nickName = responseArray[1];
					alert("글 작성이 성공적으로 완료되었습니다.");
					location.href = contextPath + "/Mealkit/info?no=" + no + "&nickName=" + nickName;
		       	} else {
					alert("글 작성에 실패했습니다. 다시 시도해주세요.");
				}
		},
		error: function(xhr, status, error) {
			console.error("Ajax Error:", status, error);
			alert("서버와의 통신 중 오류가 발생했습니다.");
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

function triggerFileInput() {
	if (selectedFiles.length >= 5) {
		alert("사진은 최대 5장까지 추가할 수 있습니다.");
		return;
	}
	document.getElementById('pictureFiles').click();
}

function handleFileSelect(files) {
	const imagePreview = document.getElementById('imagePreview');

	Array.from(files).forEach(file => {
		if (file.type.startsWith('image/')) {
			let fileIdentifier = file.name + '-' + file.size;

			if (!selectedFiles.includes(fileIdentifier)) {
				if (selectedFiles.length >= 5) {
					alert("사진은 최대 5장까지 추가할 수 있습니다.");
					return;
				}

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
						imagePreview.removeChild(li);
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

function removeSelectedFile(fileIdentifier) {
	//selectedFiles = selectedFiles.filter(item => item !== fileIdentifier);
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