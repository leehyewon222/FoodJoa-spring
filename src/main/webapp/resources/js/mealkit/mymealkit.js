function onUpdateButton(no, contextPath) {
	location.href = contextPath + '/Mealkit/update?no=' + no;
}

function onDeleteButton(no, contextPath) {
	if (confirm('정말로 삭제하시겠습니까?')) {
		$.ajax({
		    url: contextPath + '/Mealkit/delete.pro',
		    type: "POST",
		    data: {
		    	no: no
		    },
		    dataType: "text",
		    async: true,
		    success: function(responseData, status, jqxhr) {
				if(responseData == "1") {
					alert('밀키트를 삭제했습니다.');
					location.href = contextPath + '/Member/mypagemain.me';
				}
				else {
					alert('밀키트 삭제에 실패했습니다.');
				}
		    },
		    error: function(xhr, status, error) {
		        console.log("error", error);
				alert('통신 에러');
		    }
		});
	}
}