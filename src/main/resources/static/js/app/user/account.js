function goProfile(userId) {
    location.href = '/' + `${userId}` + '/profile';
}

function goAccount(userId) {
    location.href = '/' + `${userId}` + '/account';
}

function certifiedEmail(userId) {
	var userEmail = document.getElementById("userEmail");
	var params = { "userEmail": userEmail.value };
	
	$.ajax({
		url: certifiedEmail_uri,
		type: "POST",
		dataType: "json",
		data: params,
		success: function(response) {
			swal.fire({
				text: '등록된 이메일로 인증 링크를 보냈습니다.',
				icon: 'success',
				confirmButtonColor: '#3085d6',
				confirmButtonText: '확인'
			});
		},
		error: function(response) {
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
