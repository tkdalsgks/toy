if(role == 'GUEST') {
	swal.fire({
		title: '인증이 필요한 계정입니다.',
		text: '계정 인증 페이지로 이동할까요?',
		footer: '인증 후 OYEZ의 모든 콘텐츠 이용이 가능합니다.',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: '이동',
		cancelButtonText: '취소'
	}).then((result) => {
		if(result.isConfirmed) {
			location.href = '/' + userId + '/account';
		}
	});
}

$(document).ready(function(){
	savePoints();
});

// 포인트 적립
function savePoints() {
	var pointsCd = "1";
	var points = "100";
	
	var headers = { "Content-Type": "application/json", "X-HTTP-Method-Override": "POST" };
	var params = { "pointsCd": pointsCd, "points": points, "userId": userId };
	
	//console.log("params : " + JSON.stringify(params));
	
	$.ajax({
		url: "/points",
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
			} else {
				return true;
			}
		},
		error: function(xhr, status, error) {
			swal.fire({
				text: '잠시 후 재시도 바랍니다.',
				footer: '서버와의 통신 에러입니다.',
				icon: 'error',
				confirmButtonColor: '#3085d6',
				confirmButtonText: '확인'
			});
			return false;
		}
	});
}
