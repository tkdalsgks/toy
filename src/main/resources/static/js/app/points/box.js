$(document).ready(function(){
	if(referer == null) {
		location.href = "/";
	}
});

const today = new Date();
const month = today.getMonth() + 1;
const date = today.getDate();
const getRandomBox = document.querySelector('#getRandomBox');
const get100Box = document.querySelector('#get100Box');

getRandomBox.addEventListener('click', (event) => {
	const result = getPointsBox();
	savePoints(result);
});

get100Box.addEventListener('click', (event) => {
	const result = "100";
	savePoints(result);
});

const getPointsBox = function() {
	const ranNum = Math.floor((Math.random() * 99) +1);

    // 경품 생성
    const gift = ['1000', '500', '300', '200', '150', '100'];
    // 확률 생성
    const pbt = [3, 6, 11, 16, 23, 41];
    // 리턴 경품 값
    let res = '';

    for (let i = 0; i < gift.length; i++) {

        if(pbt[i] >= ranNum){
            res = gift[i];
            return res;
        }
        else if (pbt[pbt.length - 1] < ranNum) {
            res = gift[gift.length - 1];
            return res;
        }
    }
}

// 포인트 적립
function savePoints(result) {
	var pointsCd = "1";
	var points = result;
	
	var headers = { "Content-Type": "application/json", "X-HTTP-Method-Override": "POST" };
	var params = { "pointsCd": pointsCd, "points": points, "userId": userId };
	
	//console.log("params : " + JSON.stringify(params));
	
	$.ajax({
		url: "/points/save",
		type: "POST",
		headers: headers,
		dataType: "JSON",
		data: JSON.stringify(params),
		success: function(response) {
			if(response.result == false) {
				swal.fire({
					text: '포인트 적립에 실패했습니다.',
    				icon: 'warning',
    				confirmButtonColor: '#3085d6',
    				confirmButtonText: '확인'
    			});
				return false;
			} else if(response.result == true) {
				const getRoadingBox = document.getElementById('getRoadingBox');
				getRoadingBox.style.zIndex = '1';
				getRoadingBox.style.opacity = '1';
				
				setTimeout(function() {
					swal.fire({
						text: result + ' POINT 가 적립되었습니다.',
	    				icon: 'success',
	    				confirmButtonColor: '#3085d6',
	    				confirmButtonText: '확인'
	    			}).then((result) => {
	    				if(result.isConfirmed) {
	    					location.href = '/';
	    				}
	    				return true;
	    			});								
				}, 2000);
			}
		},
		error: function(xhr, status, error) {
			toastr.options = {
				progressBar: true,
			 	showMethod: 'slideDown',
			 	timeOut: 1500
			};
			toastr.error('서버와의 통신 에러입니다.', '잠시 후 재시도 바랍니다.');
			return false;
		}
	});
}
