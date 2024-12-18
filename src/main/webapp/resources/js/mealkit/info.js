// bxslide 
$('.bxslider').bxSlider({
	infiniteLoop: false,
	hideControlOnEnd: true,
	slideWidth: 530,
	adaptiveHeight: true,
});

$('.bx-wrapper').css({
    'box-shadow': 'none'
});

let mealkitNo = parseInt($('#mealkitNo').val());
let bytePictures = $('#bytePictures').val();

let stockInput = $('#stock');
let stock = parseInt(stockInput.val());
let maxStock = parseInt($('#mealkitStock').val());
let minStock = 1;

$('.stock_plus').click(function() {
	let currentValue = parseInt(stockInput.val());
	if (currentValue < maxStock) {
		stockInput.val(currentValue + 1);
	}
});

$('.stock_minus').click(function() {
	let currentValue = parseInt(stockInput.val());
	if (currentValue > minStock) {
		stockInput.val(currentValue - 1);
	}
});

function cartMealkit(contextPath, mealkitNo) {

	let stock = parseInt($('#stock').val());
	
	console.log("cartMealkit 함수 실행");
    console.log("contextPath:", contextPath, "mealkitNo:", mealkitNo, "stock:", stock);
	
	$.ajax({
		url: contextPath + "/Mealkit/cartPro",
		type: "POST",
		async: true,
		data: {
			no: mealkitNo,
			quantity: stock
		},
		success: function(response) {
			if (response == "1") alert("장바구니에 추가되었습니다.");
			else alert("장바구니에 추가를 못 했습니다.");
		}
	});
}


function wishMealkit(contextPath, mealkitNo) {
	$.ajax({
		url: contextPath + "/Mealkit/wishPro",
		type: "POST",
		async: true,
		data: {
			no: mealkitNo
		},
		success: function(response) {
			if (response == "-1") {
				alert('이미 찜목록에 있습니다.');
			}
			else if (response == "0") {
                alert('찜목록 추가에 실패했습니다.');
            } else {
                alert("찜목록에 추가되었습니다.");
            }
		},
		error: function(error) {
			console.log(error);
			alert('찜목록 추가 중 통신 에러 발생');
		}
	});
}


// 삭제 함수
function deleteMealkit(no, contextPath) {
	if (confirm('정말로 삭제하시겠습니까?')) {
		$.ajax({
			url: contextPath + "/Mealkit/deletePro",
			type: "POST",
			async: true,
			data: { no: no },
			success: function(response) {
				if (response === "1") {
					alert("삭제되었습니다.");
					location.href = contextPath + "/Mealkit/list?category=0";
				} else {
					alert("삭제에 실패했습니다.");
				}
			}
		});
	}
}

// 수정 함수 
function editMealkit(no, contextPath) {
	location.href = contextPath + "/Mealkit/updateMealkit?no=" + no;
}