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
			toastr.success('이메일로 인증링크를 보냈습니다.');
		},
		error: function(response) {
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
