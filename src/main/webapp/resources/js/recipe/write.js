
$(function() {
    $(".ingredient-new-button").click(function() {
        var newIngredientHtml = `
			<tr class="ingredient-new-row">
				<td align="center">
					<input type="text" class="ingredient-new-name" placeholder="음식 재료 이름을 적어주세요.">
				</td>
				<td align="center">
					<input type="text" class="ingredient-new-amount" placeholder="재료의 수량을 단위로 적어주세요.">
				</td>
				<td align="left">
					<button type="button" class="ingredient-add-button">추가</button>
					<button type="button" class="ingredient-cancle-button">취소</button>
				</td>
			</tr>
        `;
		
		$(".ingredients-container>table").append(newIngredientHtml);
    });

    $(document).on('click', '.ingredient-add-button', function() {
        var $row = $(this).closest('.ingredient-new-row');
        var name = $row.find('.ingredient-new-name').val();
        var amount = $row.find('.ingredient-new-amount').val();
        
        if (name && amount) {
        	var newIngredientHtml = 
                '<tr class="ingredient-added-row">' + 
					'<td>' +
						'<div class="ingredient-added-name">' + name + '</div>' +
					'</td>' +
					'<td>' +
						'<div class="ingredient-added-amount">' + amount + '</div>' +
					'</td>' +
					'<td align="left">' +
						'<button type="button" class="ingredient-remove-button">삭제</button>' +
					'</td>' +
				'</tr>';
            
            $row.replaceWith(newIngredientHtml);
        }
		else {
            alert("재료명과 양을 모두 입력해주세요.");
        }
    });

    $(document).on('click', '.ingredient-cancle-button', function() {
        $(this).closest('.ingredient-new-row').remove();
    });

	$(document).on('click', '.ingredient-remove-button', function() {
	    $(this).closest('.ingredient-added-row').remove();
	});
	


	$(".orders-new-button").click(function() {
	    var newOrderHtml = `
			<tr class="orders-new-row">
				<td align="center">
					<input type="text" class="orders-new-name" placeholder="조리 순서를 간단히 적어주세요.">
				</td>
				<td align="left">
		            <button type="button" class="orders-add-button">추가</button>
		            <button type="button" class="orders-cancle-button">취소</button>
				</td>
			</tr>
	    `;
	    
	    $(".orders-container>table").append(newOrderHtml);
	});

	$(document).on('click', '.orders-add-button', function() {
	    var $row = $(this).closest('.orders-new-row');
	    var name = $row.find('.orders-new-name').val();
	    
	    if (name) {
			var newOrderHtml = 
			    '<tr class="orders-added-row">' + 
					'<td>' +
						'<div class="orders-added-name">' + name + '</div>' +
					'</td>' +
					'<td align="left">' +
						'<button type="button" class="orders-remove-button">삭제</button>' +
					'</td>' +
				'</tr>';
				
	        $row.replaceWith(newOrderHtml);
	    }
		else {
	        alert("조리 순서를 입력 해주세요.");
	    }
	});

	$(document).on('click', '.orders-cancle-button', function() {
	    $(this).closest('.orders-new-row').remove();
	});

	$(document).on('click', '.orders-remove-button', function() {
	    $(this).closest('.orders-added-row').remove();
	});
});

function setIngredientString() {
	
	let ingredients = $(".ingredient-added-name");
	let ingredientsString = [];
	
	ingredients.each(function(index, element) {
		ingredientsString.push($(element).text());
	});
	
	let combinedIngredientsString = combineStrings(ingredientsString);
	
	$("#ingredient").val(combinedIngredientsString);
	
		
	let amounts = $(".ingredient-added-amount");
	let amountsString = [];
		
	amounts.each(function(index, element) {
		amountsString.push($(element).text());
	});
	
	let combinedAmountString = combineStrings(amountsString);

	$("#ingredient_amount").val(combinedAmountString);
}

function setOrdersString() {

	let orders = $(".orders-added-name");
	let ordersString = [];
		
	orders.each(function(index, element) {
		ordersString.push($(element).text());
	});
	
	let combinedOrderString = combineStrings(ordersString);

	$("#orders").val(combinedOrderString);
}

function compressContent(editorContent) {
    const contentBytes = new TextEncoder().encode(editorContent);
    const compressedContent = pako.deflate(contentBytes);
    
    // 배열을 문자열로 변환하는 함수
    function arrayToBase64(array) {
        const chunk = 0xffff; // 최대 청크 크기
        let result = '';
        for (let i = 0; i < array.length; i += chunk) {
            result += String.fromCharCode.apply(null, array.subarray(i, i + chunk));
        }
        return btoa(result);
    }

    return arrayToBase64(compressedContent);
}