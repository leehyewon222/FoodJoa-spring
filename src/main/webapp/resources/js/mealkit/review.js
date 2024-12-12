
let selectedFiles = [];
let selectedRealFiles = [];

function onSubmit(event, contextPath) {
	event.preventDefault();

	setPicturesString();

	let mealkitNo = $("#mealkit_no").val();
	let nickName = $("#nickname").val();

	const formData = new FormData();
	formData.append('mealkit_no', $("#mealkit_no").val());
	formData.append('pictures', $("#pictures").val());
	formData.append('contents', $("#contents").val());
	formData.append('rating', $("#rating").val());

	selectedRealFiles.forEach((file, index) => {
		formData.append('file' + index, file);
	});

	$.ajax({
	    url: contextPath + '/Mealkit/reviewPro',
	    type: "POST",
	    data: formData,
	    processData: false,
	    contentType: false,
	    success: function(responseData, status, jqxhr) {
			location.href = contextPath + '/Mealkit/info?no=' + mealkitNo;
	    },
	    error: function(xhr, status, error) {
	        console.log("error", error);
	    }
	});
}

function onCancleButton(event) {
	event.preventDefault();

	history.back();
}

function handleFileSelect(files) {
	const imagePreview = document.getElementById('imagePreview');

	Array.from(files).forEach(file => {
		if (file.type.startsWith('image/')) {
			let fileIdentifier = `${file.name}-${file.size}`;
			
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

function setRating(event, ratingValue, resourcesPath) {
	event.preventDefault();
	
	let emptyStarPath = resourcesPath + '/images/recipe/empty_star.png';
	let fullStarPath = resourcesPath + '/images/recipe/full_star.png';

	let startButtons = $(".rating-star-area img");
	startButtons.each(function(index, element) {

		let path = (index < ratingValue) ? fullStarPath : emptyStarPath;
		$(element).attr('src', path);
	});

	document.getElementsByName('rating')[0].value = ratingValue;
}