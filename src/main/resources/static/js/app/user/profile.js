function updateProfile(userId) {
	const nickname = document.getElementById('userNickname').value;
	const checkUserNickname = document.getElementById('checkUserNickname');
	const exp = /^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,10}$/;
	
	if(!nickname.match(exp)) {
		toastr.warning('닉네임은 2~10자 사이의 한글, 영문, 숫자로 이루어져야 합니다.');
    } else if(nickname.match(exp)) {
		$.ajax({
			url : "/check/nickname",
			type : "POST",
			dataType : "json",
			data : { "userNickname" : $("#userNickname").val() },
			success : function(data) {
				if($("#userNickname").val() == userNickname) {
					swal.fire({
						title: '회원정보를 변경할까요?',
						text: '하루에 1번만 변경 가능합니다.',
						icon: 'warning',
						showCancelButton: true,
						confirmButtonColor: '#3085d6',
						confirmButtonText: '확인',
						cancelButtonColor: '#d33',
						cancelButtonText: '취소'
					}).then((result) => {
						if(result.isConfirmed) {
							var userNickname = document.getElementById("userNickname");
							var userEmail = document.getElementById("userEmail");
							
							var params = { "userNickname": userNickname.value, "userEmail": userEmail.value };
							
							$.ajax({
								url: updateProfile_uri,
								type: "PUT",
								dataType: "json",
								data: params,
								success: function(response) {
									if(response.result == false) {
										toastr.warning('내 정보 변경에 실패했습니다.');
										return false;
									} else if(response.result == 'exceedCount') {
										toastr.warning(response.message);
										return false;
									} else {
										toastr.success('내 정보 변경을 완료했습니다.');
									}
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
					});
				} else {
					if($("#userNickname").val() == data.userNickname) {
						toastr.warning('이미 사용중인 닉네임입니다.');
					} else {
						swal.fire({
							title: '회원정보를 변경할까요?',
							text: '하루에 1번만 변경 가능합니다.',
							icon: 'warning',
							showCancelButton: true,
							confirmButtonColor: '#3085d6',
							confirmButtonText: '확인',
							cancelButtonColor: '#d33',
							cancelButtonText: '취소'
						}).then((result) => {
							if(result.isConfirmed) {
								var userNickname = document.getElementById("userNickname");
								var userEmail = document.getElementById("userEmail");
								
								var params = { "userNickname": userNickname.value, "userEmail": userEmail.value };
								
								$.ajax({
									url: updateProfile_uri,
									type: "PUT",
									dataType: "json",
									data: params,
									success: function(response) {
										if(response.result == false) {
											toastr.warning('내 정보 변경에 실패했습니다.');
											return false;
										} else if(response.result == 'exceedCount') {
											toastr.warning(response.message);
											return false;
										} else {
											toastr.success('내 정보 변경을 완료했습니다.');
										}
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
						});
					}
				}
		    },
			error: function(data) {
				toastr.options = {
					progressBar: true,
				 	showMethod: 'slideDown',
				 	timeOut: 1500
				};
				toastr.error('서버와의 통신 에러입니다.', '잠시 후 재시도 바랍니다.');
			}
		});
	}
}

const verify = document.getElementById('profile-verify');
verify.style.display = 'none';

// 내 계정 이메일 검증
$(function() {
	var display = $("#check-email-time");
	var isCertification = false;
	var key = "";
	var timer = null;
	var isRunning = false;
	
	// 이메일 인증 버튼
	$('#check-email').click(function() {
		const email = document.getElementById('userEmail').value;
		const checkUserEmail = document.getElementById('checkUserEmail');
		const exp = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
		
		if(!email.match(exp)) {
			toastr.warning('올바르지 않은 이메일 형식입니다.');
	    	$('#check-email').prop('disabled', false);
	    } else if(email.match(exp)) {
	    	$.ajax({
				url : "/check/email",
				type : "POST",
				dataType : "json",
				data : { "userEmail" : $("#userEmail").val() },
				success : function(data) {
					if($("#userEmail").val() == userEmail ) {
						verify.style.display = 'flex';
			        	
			        	$.ajax({
							type : 'post',
							url : '/check/mail',
							data : { "userEmail" : $("#userEmail").val() },
							success : function(data) {
								isCertification = true;
								var leftSec = 180;
								if(isRunning) {
									clearInterval(timer);
									display.html("");
									startTimer(leftSec, display);
								} else {
									startTimer(leftSec, display);
								}
								$('#check-email-number').prop('disabled', false);
								$('#check-email-verify').prop('disabled', false);
								$('#check-email').prop('disabled', true);
								code = data;
								toastr.success('인증번호가 전송되었습니다.');
							},
							error: function(data) {
								toastr.options = {
									progressBar: true,
								 	showMethod: 'slideDown',
								 	timeOut: 1500
								};
								toastr.error('서버와의 통신 에러입니다.', '잠시 후 재시도 바랍니다.');
							}
						});
					} else {
						if($("#userEmail").val() == data.userEmail) {
							toastr.warning('이미 사용중인 이메일입니다.');
						} else {
							verify.style.display = 'flex';
				        	
				        	$.ajax({
								type : 'post',
								url : '/check/mail',
								data : { "userEmail" : $("#userEmail").val() },
								success : function(data) {
									isCertification = true;
									var leftSec = 180;
									if(isRunning) {
										clearInterval(timer);
										display.html("");
										startTimer(leftSec, display);
									} else {
										startTimer(leftSec, display);
									}
									$('#check-email-number').prop('disabled', false);
									$('#check-email-verify').prop('disabled', false);
									$('#check-email').prop('disabled', true);
									code = data;
									toastr.sucess('인증번호가 전송되었습니다.');
								},
								error: function(data) {
									toastr.options = {
										progressBar: true,
									 	showMethod: 'slideDown',
									 	timeOut: 1500
									};
									toastr.error('서버와의 통신 에러입니다.', '잠시 후 재시도 바랍니다.');
								}
							});
						}
					}
			    },
				error: function(data) {
					toastr.options = {
						progressBar: true,
					 	showMethod: 'slideDown',
					 	timeOut: 1500
					};
					toastr.error('서버와의 통신 에러입니다.', '잠시 후 재시도 바랍니다.');
				}
			});
	    }
	});
	
	function startTimer(count, display) {
		var minutes, seconds;
		timer = setInterval(function() {
			minutes = parseInt(count / 60, 10);
			seconds = parseInt(count % 60, 10);
			
			minutes = minutes < 10 ? "0" + minutes : minutes;
			seconds = seconds < 10 ? "0" + seconds : seconds;
			
			display.html(minutes + ":" + seconds);
			
			// 타이머 끝
			if(--count < 0) {
				clearInterval(timer);
				$('#check-email-number').prop('disabled', true);
				$('#check-email-verify').prop('disabled', true);
				$('#check-email').prop('disabled', false);
				isRunning = false;
			}
		}, 1000);
		isRunning = true;
	}
	
	// 인증 확인 버튼
	$('#check-email-verify').click(function() {
		$.ajax({
			type : 'post',
			url : '/check/verifyCode',
			data : { "code" : $("#check-email-number").val() },
			success : function(result) {
				if(result == 1) {
					isCertification = true;
					clearInterval(timer);
					$('#userEmail').prop('disabled', true);
					$('#check-email').prop('disabled', true);
					$('#saveProfile').prop('disabled', false);
					$('#check-email-number').prop('disabled', true);
					$('#check-email-verify').prop('disabled', true);
					toastr.success('정상 인증 되었습니다.');
				} else {
					isCertification = false;
					toastr.warning('인증번호를 정확하게 입력하세요.');
				}
			},
			error: function(result) {
				toastr.options = {
					progressBar: true,
				 	showMethod: 'slideDown',
				 	timeOut: 1500
				};
				toastr.error('서버와의 통신 에러입니다.', '잠시 후 재시도 바랍니다.');
			}
		});
	});
});

// 프로필 사진 업로드
function profileUpload() {
	if(document.profileUploadForm.upload.value != "") {
		toastr.options = {
			progressBar: true,
		 	showMethod: 'slideDown',
		 	timeOut: 1500
		};
		toastr.options.onHidden = function() {
			document.profileUploadForm.action = "/upload/profileImg";
			document.profileUploadForm.submit();
		};
		toastr.success('프로필 사진을 등록하고 있습니다.');
	}
}

function goProfile(userId) {
	location.href = '/' + `${userId}` + '/profile';
}

function goAccount(userId) {
	location.href = '/' + `${userId}` + '/account';
}
