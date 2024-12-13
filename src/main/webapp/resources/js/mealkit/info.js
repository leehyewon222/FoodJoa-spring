let mealkitNo = parseInt($('#mealkitNo').val());
let bytePictures = $('#bytePictures').val();

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

// 수량 증가 감소 버튼
let stockInput = $('input[name="stock"]');
let stock = stockInput.val();
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

// 장바구니, 찜목록 버튼
function cartMealkit(contextPath) {
	stock = stockInput.val();
	
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

function wishMealkit(contextPath) {
	$.ajax({
		url: contextPath + "/Mealkit/wishPro",
		type: "POST",
		async: true,
		data: {
			no: mealkitNo
		},
		success: function(response) {
			if (response == "1") {
                alert("찜목록에 추가되었습니다.");
            } else if (response == "-1") {
                alert("이미 찜목록에 있습니다.");
            } else {
                alert("찜목록에 추가를 못 했습니다.");
            }
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

// 결제창으로 이동 
function onPaymentButton(e) {
	e.preventDefault();
	
	let no = $("#mealkitNo").val();
	let quantity = $("#stock").val();
	
	location.href = '${contextPath}/Member/payment.me?isCart=0&combinedNo='+ no + '&CombinedQuantity=' + quantity;
}