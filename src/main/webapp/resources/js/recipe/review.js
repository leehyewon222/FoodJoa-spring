let selectedFileNames = [];
let selectedRealFiles = [];

function onCancleButton(event) {
	event.preventDefault();

	history.back();
}

function handleFileSelect(files) {
	const imagePreview = document.getElementById('imagePreview');

	let isDuplicated = false;
	
	Array.from(files).forEach(file => {
		if (file.type.startsWith('image/')) {
			let fileName = file.name;
			
			if (!selectedFileNames.includes(fileName)) {
				selectedFileNames.push(fileName);
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
						removeSelectedFile(fileName);
						document.getElementById('pictureFiles').value = '';
					});
	
					img.style.cursor = 'pointer';
					
					li.appendChild(img);
					imagePreview.appendChild(li);
				}				
			}
			else {
				isDuplicated = true;
			}
		}
	});
	
	if (isDuplicated) {
		alert('동일한 파일명이 있습니다.');
	}

	document.getElementById('pictureFiles').value = '';
}

function removeSelectedFile(fileName) {
	for (let i = 0; i < selectedFileNames.length; i++) {
		if (selectedFileNames[i] == fileName) {
			selectedFileNames.splice(i, 1);
			selectedRealFiles.splice(i, 1);
			break;
		}
	}
}

function setPicturesString() {
	let pictures = combineStrings(selectedFileNames);

	document.getElementsByName('pictures')[0].value = pictures;
}