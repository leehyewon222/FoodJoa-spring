
let selectedFile;
let selectedFileName;

function onSubmit(event, contextPath) {
	event.preventDefault();
	
	if ($("#lat").val() == "" || $("#lng").val() == "") {
		alert('지도 좌표를 선택해주세요');
		return;
	}
	
	let no = $("#no").val();
	let nowPage = $("#nowPage").val();
	let nowBlock = $("#nowBlock").val();
	
	const formData = new FormData();
	formData.append('no', no);
	formData.append('id', $("#id").val());
	formData.append('originThumbnail', $("#origin-thumbnail").val());
	formData.append('thumbnail', selectedFileName);
	formData.append('title', $("#title").val());	
	formData.append('contents', $("#contents").val());	
	formData.append('lat', $("#lat").val());	
	formData.append('lng', $("#lng").val());
	
	formData.append('file', selectedFile);
	
	$.ajax({
		url: contextPath + '/Share/updatePro',
		type: "post",
		data: formData,
		processData: false,
		contentType: false,
		success: function(responsedData) {
			
			if (responsedData == "1") {
				location.href = contextPath + '/Share/read?no=' + no + '&nowPage=' + nowPage + '&nowBlock=' + nowBlock;
			}
			else {
				alert('게시글 수정을 실패 했습니다.');
			}
		},
		error: function(error) {
			console.log(error);
		}
	});
}


function handleFileSelect(files) {

	selectedFile = files[0];
	const imageContainer = document.getElementById('imageContainer');

	if (selectedFile.type.startsWith('image/')) {
		
		const reader = new FileReader();

		reader.readAsDataURL(selectedFile);

		reader.onload = function(e) {
			imageContainer.innerHTML = '';
			
			const img = document.createElement('img');
			img.src = e.target.result;

			img.dataset.filename = selectedFile.name;
			selectedFileName = selectedFile.name;

			imageContainer.appendChild(img);
		}			
	}
}


//------ Naver Map API

function setLatLngValue(point) {
	$("#lat").val(point.y);
	$("#lng").val(point.x);
}

var mapOptions = {
    center: new naver.maps.LatLng(37.3595704, 127.105399),
    zoom: 10
};

var map = new naver.maps.Map("map", {
    center: new naver.maps.LatLng(37.3595316, 127.1052133),
    zoom: 15,
    mapTypeControl: true
});

var infoWindow = new naver.maps.InfoWindow({
    anchorSkew: true
});

map.setCursor('pointer');

function searchCoordinateToAddress(latlng) {

    infoWindow.close();

    naver.maps.Service.reverseGeocode({
        coords: latlng,
        orders: [
            naver.maps.Service.OrderType.ADDR,
            naver.maps.Service.OrderType.ROAD_ADDR
        ].join(',')
    }, function(status, response) {
        if (status === naver.maps.Service.Status.ERROR) {
            return alert('Something Wrong!');
        }

        var items = response.v2.results,
            address = '',
            htmlAddresses = [];

        for (var i=0, ii=items.length, item, addrType; i<ii; i++) {
            item = items[i];
            address = makeAddress(item) || '';
            addrType = item.name === 'roadaddr' ? '[도로명 주소]' : '[지번 주소]';

            htmlAddresses.push((i+1) +'. '+ addrType +' '+ address);
        }

		setLatLngValue(latlng);
		
        infoWindow.setContent([
            '<div style="padding:10px;min-width:200px;line-height:150%;">',
            '<h4 style="margin-top:5px;">검색 좌표</h4><br />',
            htmlAddresses.join('<br />'),
            '</div>'
        ].join('\n'));

        infoWindow.open(map, latlng);
    });
}

function searchAddressToCoordinate(address) {
    naver.maps.Service.geocode({
        query: address
    }, function(status, response) {
        if (status === naver.maps.Service.Status.ERROR) {
            return alert('Something Wrong!');
        }

        if (response.v2.meta.totalCount === 0) {
            return alert('totalCount' + response.v2.meta.totalCount);
        }

        var htmlAddresses = [],
            item = response.v2.addresses[0],
            point = new naver.maps.Point(item.x, item.y);

        if (item.roadAddress) {
            htmlAddresses.push('[도로명 주소] ' + item.roadAddress);
        }

        if (item.jibunAddress) {
            htmlAddresses.push('[지번 주소] ' + item.jibunAddress);
        }

        map.setCenter(point);
		
		setLatLngValue(point);
		
        infoWindow.setContent([
            '<div style="padding:10px;min-width:200px;line-height:150%;">',
            '<h4 style="margin-top:5px;">검색 주소 : '+ address +'</h4><br />',
            htmlAddresses.join('<br />'),
            '</div>'
        ].join('\n'));

        infoWindow.open(map, point);
    });
}

function initGeocoder() {
    map.addListener('click', function(e) {
       	searchCoordinateToAddress(e.coord);
        
        var latlng = e.coord,
        utmk = naver.maps.TransCoord.fromLatLngToUTMK(latlng),
        tm128 = naver.maps.TransCoord.fromUTMKToTM128(utmk),
        naverCoord = naver.maps.TransCoord.fromTM128ToNaver(tm128);

	    utmk.x = parseFloat(utmk.x.toFixed(1));
	    utmk.y = parseFloat(utmk.y.toFixed(1));
    });

    $('#naverAddress').on('keydown', function(e) {
        var keyCode = e.which;

        if (keyCode === 13) { // Enter Key
            searchAddressToCoordinate($('#naverAddress').val());
        }
    });

    $('#naverSearch').on('click', function(e) {
        e.preventDefault();

        searchAddressToCoordinate($('#naverAddress').val());
    });
}

function makeAddress(item) {
    if (!item) {
        return;
    }

    var name = item.name,
        region = item.region,
        land = item.land,
        isRoadAddress = name === 'roadaddr';

    var sido = '', sigugun = '', dongmyun = '', ri = '', rest = '';

    if (hasArea(region.area1)) {
        sido = region.area1.name;
    }

    if (hasArea(region.area2)) {
        sigugun = region.area2.name;
    }

    if (hasArea(region.area3)) {
        dongmyun = region.area3.name;
    }

    if (hasArea(region.area4)) {
        ri = region.area4.name;
    }

    if (land) {
        if (hasData(land.number1)) {
            if (hasData(land.type) && land.type === '2') {
                rest += '산';
            }

            rest += land.number1;

            if (hasData(land.number2)) {
                rest += ('-' + land.number2);
            }
        }

        if (isRoadAddress === true) {
            if (checkLastString(dongmyun, '면')) {
                ri = land.name;
            } else {
                dongmyun = land.name;
                ri = '';
            }

            if (hasAddition(land.addition0)) {
                rest += ' ' + land.addition0.value;
            }
        }
    }

    return [sido, sigugun, dongmyun, ri, rest].join(' ');
}

function hasArea(area) {
    return !!(area && area.name && area.name !== '');
}

function hasData(data) {
    return !!(data && data !== '');
}

function checkLastString (word, lastString) {
    return new RegExp(lastString + '$').test(word);
}

function hasAddition (addition) {
    return !!(addition && addition.value);
}

naver.maps.onJSContentLoaded = initGeocoder;