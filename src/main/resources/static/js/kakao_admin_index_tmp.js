// let urlName = "http://localhost:8080";
let urlName = "http://20.39.194.142:8080";

var map;
var lat, lon;
var circle;
var markers = new Array();
var palletMarkers = new Array();
var perClickButton = "";
var rangeChecker = 0;

// GeoLocation을 이용해서 접속 위치를 얻어옵니다
lat = 37.59980369431155; // 위도
lon = 126.87231002521617; // 경도

var send_lat = new Array();
var send_lon = new Array();
var send_status = new Array();
var send_id = new Array();


var positions = [
    {
        title: '강릉집하장',
        latlng: new kakao.maps.LatLng(37.8084, 128.8561),
    },
    {
        title: '원주지점',
        latlng: new kakao.maps.LatLng(37.3313, 127.849),
    },
    {
        title: '용인지점',
        latlng: new kakao.maps.LatLng(37.2042, 127.3175),
    }, {
        title: '김포지점',
        latlng: new kakao.maps.LatLng(37.6535, 126.653),
    }, {
        title: '화성지점',
        latlng: new kakao.maps.LatLng(37.1432, 126.8268),
    }, {
        title: '포천지점',
        latlng: new kakao.maps.LatLng(37.7904, 127.2393),
    }, {
        title: '양주지점',
        latlng: new kakao.maps.LatLng(37.8216, 126.8914),
    }, {
        title: '시흥지점',
        latlng: new kakao.maps.LatLng(37.3206, 126.7448),
    }, {
        title: '사천센터',
        latlng: new kakao.maps.LatLng(35.0726, 128.0711),
    }, {
        title: '김해지점',
        latlng: new kakao.maps.LatLng(35.3315, 128.7102),
    }, {
        title: '경산지점',
        latlng: new kakao.maps.LatLng(35.8838, 128.7896),
    }, {
        title: '안동집하장',
        latlng: new kakao.maps.LatLng(36.6026, 128.5318),
    }, {
        title: '대구지점',
        latlng: new kakao.maps.LatLng(35.9435, 128.3189),
    }, {
        title: 'WM센터',
        latlng: new kakao.maps.LatLng(37.4925, 127.1181),
    }, {
        title: '구로지점',
        latlng: new kakao.maps.LatLng(37.491, 126.8348),
    }, {
        title: '부산지점',
        latlng: new kakao.maps.LatLng(35.5101, 129.0969),
    }, {
        title: '인천지점',
        latlng: new kakao.maps.LatLng(37.4978, 126.6651),
    }, {
        title: '목포집하장',
        latlng: new kakao.maps.LatLng(34.8246, 126.3824),
    }, {
        title: '광주지점',
        latlng: new kakao.maps.LatLng(35.2607, 126.7212),
    }, {
        title: '전주지점',
        latlng: new kakao.maps.LatLng(35.9501, 127.1113),
    }, {
        title: '제주지점',
        latlng: new kakao.maps.LatLng(33.522, 126.5446),
    }, {
        title: '아산지점',
        latlng: new kakao.maps.LatLng(36.7666, 126.9453),
    }, {
        title: '청주지점',
        latlng: new kakao.maps.LatLng(36.5654, 127.4403),
    }, {
        title: '진천지점',
        latlng: new kakao.maps.LatLng(36.9108, 127.4355),
    },
    {
        title: '오포지점',
        latlng: new kakao.maps.LatLng(37.3511, 127.2043),
    }
];

var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(lat, lon), // 지도의 중심좌표
        level: 10 // 지도의 확대 레벨
    };

map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
// 마커 이미지의 이미지 주소입니다
var imageSrc = "img/nearShopIcon.png";

for (var i = 0; i < positions.length; i++) {

    // 마커 이미지의 이미지 크기 입니다
    var imageSize = new kakao.maps.Size(35, 35);

    // 마커 이미지를 생성합니다
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: positions[i].latlng, // 마커를 표시할 위치
        title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        image: markerImage, // 마커 이미지
        clickable: true
    });

    kakao.maps.event.addListener(marker, 'click', function() {
        ShowSide2()
    });
    markers.push(marker)
}
imageSrc = "img/myIndexIcon.png";
var imageSize = new kakao.maps.Size(35, 35);

// 마커 이미지를 생성합니다
var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

var marker = new kakao.maps.Marker({
    map: map, // 마커를 표시할 지도
    position: new kakao.maps.LatLng(lat, lon), // 마커를 표시할 위치
    title: '나의 위치', // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
    image: markerImage // 마커 이미지
});
markers.push(marker);




initPage();
function initPage(){
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === xhr.DONE) {
            if (xhr.status === 200 || xhr.status === 201) {

                var obj = JSON.parse(xhr.responseText);

                for (var i = 0; i < obj.latitude.length; i++) {
                    send_lat.push(obj.latitude[i]);
                    send_lon.push(obj.hardness[i]);
                    send_id.push(obj.id[i]);
                    send_status.push(obj.status[i]);
                }
                imageSrc = "img/sendingIndexIcon.png";
                var imageSrc2 = "img/missingIcon.png";
                var markerImageSend = new kakao.maps.MarkerImage(imageSrc, imageSize);
                var markerImageSend2 = new kakao.maps.MarkerImage(imageSrc2, imageSize);
                var check = 0;

                for (var i = 0; i < send_id.length; i++) {
                    for (let j = 0; j < palletMarkers.length; j++) {
                        if (palletMarkers.at(j).Gb == send_id.at(i)) {
                            console.log(palletMarkers.at(j))
                            palletMarkers.at(j).setPosition(new kakao.maps.LatLng(send_lat.at(i), send_lon.at(i)))
                            check = 1;
                        }
                        }
                        if (check == 1){
                            check = 0;
                            continue;

                        }
                    if (send_status.at(i) == 1) {
                        var marker = new kakao.maps.Marker({
                            map: map, // 마커를 표시할 지도
                            position: new kakao.maps.LatLng(send_lat.at(i), send_lon.at(i)), // 마커를 표시할 위치
                            title: send_id.at(i), // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
                            image: markerImageSend // 마커 이미지
                        });
                        palletMarkers.push(marker);

                    } else if (send_status.at(i) == 3) {
                        var marker = new kakao.maps.Marker({
                            map: map, // 마커를 표시할 지도
                            position: new kakao.maps.LatLng(send_lat.at(i), send_lon.at(i)), // 마커를 표시할 위치
                            title: send_id.at(i), // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
                            image: markerImageSend2 // 마커 이미지
                        });
                        palletMarkers.push(marker);
                    }
                }



                }
                console.log(xhr.responseText);

            } else {
                console.error(xhr.responseText);
            }
        }
    xhr.open('GET', urlName + '/getSendingPallet');
// xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send();

};






function clickRangeButton(km) {
    if (rangeChecker == 0) {
        rangeChecker = 1;
        circle = new kakao.maps.Circle({
            center: new kakao.maps.LatLng(lat, lon), // 원의 중심좌표입니다
            radius: km, // 원의 반지름입니다 m 단위 이며 선 객체를 이용해서 얻어옵니다
            strokeWeight: 1, // 선의 두께입니다
            strokeColor: '#00a0e9', // 선의 색깔입니다
            strokeOpacity: 0.3, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
            strokeStyle: 'solid', // 선의 스타일입니다
            fillColor: '#00a0e9', // 채우기 색깔입니다
            fillOpacity: 0.2  // 채우기 불투명도입니다
        });

// 지도에 원을 표시합니다
        circle.setMap(map);

        for (var i = 0; i < markers.length; i++) {
            var makerPoint = markers.at(i).getPosition();
            var circlePoint = new kakao.maps.LatLng(lat, lon)
            var poly = new kakao.maps.Polyline({
                // map: map, 을 하지 않아도 거리는 구할 수 있다.
                path: [makerPoint, circlePoint]
            });

            var dist = poly.getLength();

            if (dist < km) {
                markers.at(i).setMap(map);
            } else {
                markers.at(i).setMap(null);
            }
        }
        for (var i = 0; i < palletMarkers.length; i++) {
            var makerPoint = palletMarkers.at(i).getPosition();
            var circlePoint = new kakao.maps.LatLng(lat, lon)
            var poly = new kakao.maps.Polyline({
                // map: map, 을 하지 않아도 거리는 구할 수 있다.
                path: [makerPoint, circlePoint]
            });

            var dist = poly.getLength();

            if (dist < km) {
                palletMarkers.at(i).setMap(map);
            } else {
                palletMarkers.at(i).setMap(null);
            }
        }

    }
}

function clickUnRangeButton() {
    if (rangeChecker == 1) {
        rangeChecker = 0;
        for (var i = 0; i < markers.length; i++) {
            if (markers.at(i).getMap() != null) {
                continue
            }
            // var marker = new kakao.maps.Marker({
            //     map: map, // 마커를 표시할 지도
            //     position: markers.at(i).getPosition(), // 마커를 표시할 위치
            //     title: markers.at(i).getTitle(), // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
            //     image: markers.at(i).getImage() // 마커 이미지
            // });
            markers.at(i).setMap(map);
        }
        console.log(circle.getMap())
        console.log(map);
        circle.setMap(null);
        document.getElementById("rangeInit").click();
        // markers.forEach(marker => makeClickListener(marker));
    }
}

function inputFilterMap(data) {

    for (var i = 0; i < markers.length; i++) {
        if (!(markers.at(i).getMap() == null)) {
            markers.at(i).setMap(null);
        }
    }

    for (var i = 0; i < palletMarkers.length; i++) {
        if (!(palletMarkers.at(i).getMap() == null)) {
            palletMarkers.at(i).setMap(null);
        }
    }

    for (var i = 0; i < palletMarkers.length; i++){
        if(palletMarkers.at(i).Gb == data){
            palletMarkers.at(i).setMap(null);
            palletMarkers.splice(i,1);
        }
    }
    console.log("enter the filtering")
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === xhr.DONE) {
            if (xhr.status === 200 || xhr.status === 201) {
                console.log(xhr.responseText);
                imageSrc = "img/sendingIndexIcon.png";
                var imageSrc2 = "img/missingIcon.png";
                var markerImageSend = new kakao.maps.MarkerImage(imageSrc, imageSize);
                var markerImageSend2 = new kakao.maps.MarkerImage(imageSrc2, imageSize);
                var getData = xhr.responseText.split(",");

                const Status = {Ready : "Ready",Sending : "Sending",Working : "Working",Missing: "Missing"}
                console.log(getData)
                var palletStatus = 0;


                if (getData.at(3) ==  Status.Sending) {

                    var marker = new kakao.maps.Marker({
                        map: map, // 마커를 표시할 지도
                        position: new kakao.maps.LatLng(getData.at(0), getData.at(1)), // 마커를 표시할 위치
                        title: getData.at(2), // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
                        image: markerImageSend // 마커 이미지
                    });
                    palletMarkers.push(marker);
                } else if (getData.at(3) == Status.Missing) {
                    var marker = new kakao.maps.Marker({
                        map: map, // 마커를 표시할 지도
                        position: new kakao.maps.LatLng(getData.at(0), getData.at(1)), // 마커를 표시할 위치
                        title: getData.at(2), // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
                        image: markerImageSend2 // 마커 이미지
                    });
                    palletMarkers.push(marker)
                }
            } else {
                console.error(xhr.responseText);
            }
        }
    };
    for (var i = 0; i <palletMarkers.length; i++){
        if(palletMarkers.at(i).Gb == data){
            return;
        }
    }
    xhr.open('GET', urlName + '/getPalletInfo?id='+data);
    xhr.send();
}

function onClickFilterButton() {
    var enterType = document.getElementById("selectType");
    var enterIntent = document.getElementById("inputData");
    console.log("click button")
    inputFilterMap(enterIntent.value)
}

function FilterInitMap() {
    for (var i = 0; i < markers.length; i++) {
        if (markers.at(i).getMap() == null) {
            markers.at(i).setMap(map);
        }
    }

    for (var i = 0; i < palletMarkers.length; i++){
        if (palletMarkers.at(i).getMap() == null) {
            palletMarkers.at(i).setMap(map);
        }
    }
}

function ShowSide2  (){
    document.getElementById("menuicon").checked = true
    document.getElementById("barIntent1").style.display = 'none';
    document.getElementById("barIntent2").style.display = '';
}

function showOrder() {
    document.getElementById("orderWindow").style.display = "block";
}

function showDetail() {
    var checkBox = document.getElementsByClassName("checkBox");
    // console.log(checkBox);
    for (var i = 0; i < checkBox.length; i++) {
        if (checkBox.item(i).checked) {
            location.href = urlName + '/orderDetail?id=' + checkBox.item(i).id;
            return;
        }
    }

}

function showOrder() {
    location.href = urlName + '/orderStatus';
}

function showDeliver() {
    location.href = urlName + '/deliverStatus';
}

function showMap() {
    location.href = urlName + '/dashMain';
}

function backToSpotInfo(){
    var first = document.getElementById("barIntent1");
    var second = document.getElementById("barIntent2");

    second.style.display="none";
    first.style.display='';
}

