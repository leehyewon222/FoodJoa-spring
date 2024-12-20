let selectedFileNames = [];
let selectedRealFiles = [];

let originSelectedFileNames = [];

function handleFileSelect(files) {
	const imagePreview = $(".together-image-preview ul");

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
					const $li = $('<li>');
					const $img = $('<img>').attr('src', e.target.result);
					
					$img.data('filename', file.name)
					    .addClass('preview_image')
					    .css('cursor', 'pointer')
					    .on('click', function() {
					        $(this).parent().remove();
					        removeSelectedFile(fileName);
					        $('#pictureFiles').val('');
					    });
					
					$li.append($img);
					imagePreview.append($li);
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

// 문자열을 합치는 함수
function combineStrings(strings) {
	let result = '';
	
	result = strings.map(str => {
        const length = str.length;

        const lengthStr = String(length).padStart(4, '0');
        return lengthStr + str;
    }).join('');
	
	return result;
}

// 기존에 선택 된 이미지를 클릭 시 이미지 이름을 제거하는 함수		
function removeOriginFileName(fileName) {
	for (let i = 0; i < originSelectedFileNames.length; i++) {
		if (originSelectedFileNames[i] == fileName) {
			originSelectedFileNames.splice(i, 1);
			break;
		}
	}
}