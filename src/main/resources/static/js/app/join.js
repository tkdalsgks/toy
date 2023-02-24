// 회원가입 아이디 유효성 검사
$('#duplicateUserId').on('click' ,function() {
	const id = document.getElementById('userId').value;
	const checkUserId = document.getElementById('checkUserId');
	const exp = /^(?=.*[a-z0-9])[a-z0-9]{4,16}$/;
	
	const imgCheckId = document.getElementById('imgCheckId');
	const imgCancelId = document.getElementById('imgCancelId');
	
	if(!id.match(exp)) {
    	checkUserId.innerHTML = '아이디는 4~20자 사이의 영문, 숫자로 이루어져야 합니다.';
    	imgCheckId.style.display = 'none';
    	imgCancelId.style.display = 'initial';
    	$("#duplicateUserId").attr("value", "");
    } else if(id.match(exp)) {
		$.ajax({
			url : "/check/id",
			type : "post",
			data : { "userId" : $("#userId").val() },
			dataType : "json",
			success : function(data) {
				if(data.result == "false") {
					checkUserId.innerHTML = '이미 사용중인 아이디입니다.';
					checkUserId.style.color = '#DC143C';
					imgCheckId.style.display = 'none';
					imgCancelId.style.display = 'initial';
					$("#duplicateUserId").attr("value", "");
		        } else if(data.result == "true") {
		        	checkUserId.innerHTML = '사용 가능한 아이디입니다.';
					checkUserId.style.color = '#DA70D6';
		        	imgCheckId.style.display = 'initial';
		        	imgCancelId.style.display = 'none';
		        	$('#userId').prop('disabled', true);
		        	$('#duplicateUserId').prop('disabled', true);
		        	$("#duplicateUserId").attr("value", "Y");
		        }
		    },
		    error: function(data){
		    	alert("잠시후 재시도 바랍니다.");
		    }
		});
	}
});

// 회원가입 비밀번호 유효성 검사
$('#userPwd').on('keyup' ,function() {
	const checkUserPwd = document.getElementById('checkUserPwd');
	const imgCheckPwd = document.getElementById('imgCheckPwd');
	const imgCancelPwd = document.getElementById('imgCancelPwd');
	
	if (!/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W])[A-Za-z\d\W]{8,16}$/g.test($('#userPwd').val())) {
		checkUserPwd.innerHTML = '비밀번호는 8~16자 사이의 대/소문자, 숫자, 특수문자가 모두 포함되어야 합니다.';
		checkUserPwd.style.color = '#DC143C';
		imgCheckPwd.style.display = 'none';
		imgCancelPwd.style.display = 'initial';
	} else {
		checkUserPwd.innerHTML = '사용 가능한 비밀번호 입니다.';
		checkUserPwd.style.color = '#DA70D6';
		imgCheckPwd.style.display = 'initial';
		imgCancelPwd.style.display = 'none';
		
	}
});

// 회원가입 비밀번호 확인 유효성 검사
$('#userPwdConfirm').on('keyup', function () {
	const userPwd = $("#userPwd").val();
	const userPwdConfirm = $("#userPwdConfirm").val();
	
	const checkUserPwdConfirm = document.getElementById('checkUserPwdConfirm');
	const imgCheckConfirm = document.getElementById('imgCheckConfirm');
	const imgCancelConfirm = document.getElementById('imgCancelConfirm');
	
	if(userPwd != userPwdConfirm) {
		checkUserPwdConfirm.innerHTML = '입력한 비밀번호가 일치하지 않습니다.';
		checkUserPwdConfirm.style.color = '#DC143C';
	    imgCheckConfirm.style.display = 'none';
		imgCancelConfirm.style.display = 'initial';
	} else {
	  	checkUserPwdConfirm.innerHTML = '입력한 비밀번호가 일치합니다.';
		checkUserPwdConfirm.style.color = '#DA70D6';
	  	imgCheckConfirm.style.display = 'initial';
		imgCancelConfirm.style.display = 'none';
	}
});

// 회원가입 닉네임 유효성 검사
$('#userNickname').on('keyup' ,function() {
	const nickname = document.getElementById('userNickname').value;
	const checkUserNickname = document.getElementById('checkUserNickname');
	const exp = /^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,10}$/;
	
	const imgCheckName = document.getElementById('imgCheckName');
	const imgCancelName = document.getElementById('imgCancelName');
	
	if(!nickname.match(exp)){
    	checkUserNickname.innerHTML = '닉네임은 2~10자 사이의 한글, 영문, 숫자로 이루어져야 합니다.';
    	imgCheckName.style.display = 'none';
    	imgCancelName.style.display = 'initial';
    } else if(nickname.match(exp)) {
		$.ajax({
			url : "/check/nickname",
			type : "post",
			dataType : "json",
			data : { "userNickname" : $("#userNickname").val() },
			success : function(data) {
				if(data.result == "false") {
					checkUserNickname.innerHTML = '닉네임은 특수문자를 제외한 2~10자리로 입력해주세요. 이미 사용하고 있는 닉네임입니다.';
					checkUserNickname.style.color = '#DC143C';
					imgCheckName.style.display = 'none';
					imgCancelName.style.display = 'initial';
		        } else if(data.result == "true") {
		        	checkUserNickname.innerHTML = '사용 가능한 닉네임입니다.';
		        	checkUserNickname.style.color = '#DA70D6';
		        	imgCheckName.style.display = 'initial';
		        	imgCancelName.style.display = 'none';
		        }
		    },
			error: function(data) {
				alert("잠시후 재시도 바랍니다.");
			}
		});
	}
});

// 회원가입 이메일 유효성 검사
$('#userEmail').on('keyup' ,function() {
	const email = document.getElementById('userEmail').value;
	const checkUserEmail = document.getElementById('checkUserEmail');
	const exp = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
	
	const imgCheckEmail = document.getElementById('imgCheckEmail');
	const imgCancelEmail = document.getElementById('imgCancelEmail');
	
	if(!email.match(exp)) {
    	checkUserEmail.innerHTML = '올바르지 않은 이메일 형식입니다.'
    	imgCheckEmail.style.display = 'none';
    	imgCancelEmail.style.display = 'initial';
    	$('#check-email').prop('disabled', true);
    } else if(email.match(exp)) {
		$.ajax({
			url : "/check/email",
			type : "post",
			dataType : "json",
			data : { "userEmail" : $("#userEmail").val() },
			success : function(data) {
				if(data.result == "false") {
					checkUserEmail.innerHTML = '이미 사용중인 이메일입니다.';
					checkUserEmail.style.color = '#DC143C';
					imgCheckEmail.style.display = 'none';
					imgCancelEmail.style.display = 'initial';
					$('#check-email').prop('disabled', true);
		        } else if(data.result == "true") {
		        	checkUserEmail.innerHTML = '사용 가능한 이메일입니다.';
		        	checkUserEmail.style.color = '#DA70D6';
		        	imgCheckEmail.style.display = 'initial';
		        	imgCancelEmail.style.display = 'none';
		        	$('#check-email').prop('disabled', false);
		        }
		    },
			error: function(data) {
				alert("잠시후 재시도 바랍니다.");
			}
		});
	}
});

// 이메일 인증 유효시간
$(function() {
	var display = $("#check-email-time");
	var isCertification = false;
	var key = "";
	var timer = null;
	var isRunning = false;
	
	// 이메일 인증 버튼
	$('#check-email').click(function() {
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
				alert('입력한 이메일로 인증번호가 전송되었습니다.');
			},
			error: function(data) {
				alert("잠시후 재시도 바랍니다.");
			}
		});
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
					$('#joinSubmit').prop('disabled', false);
					$('#check-email-number').prop('disabled', true);
					$('#check-email-verify').prop('disabled', true);
					$("#check-email-verify").attr("value", "Y");
					alert("인증되었습니다.");
				} else {
					isCertification = false;
					$("#check-email-verify").attr("value", "");
					alert("인증번호를 정확하게 입력해주세요.");
				}
			},
			error: function(result) {
				alert("잠시후 재시도 바랍니다.");
			}
		});
	});
});





$('#joinSubmit').click(function() {
	const duplicate = document.getElementById('duplicateUserId').value;
	const verify = document.getElementById('check-email-verify').value;
	
	if(duplicate != 'Y') {
		alert("아이디 중복확인이 필요합니다.");
	} else if(verify != 'Y') {
		alert("이메일 인증이 필요합니다.");
	} else {
		$.ajax({
			type : 'post',
			url : '/join',
			data : { "userId" : $("#userId").val(),
					"userPwd" : $("#userPwd").val(),
					"userPwdConfirm" : $("#userPwdConfirm").val(),
					"userNickname" : $("#userNickname").val(),
					"userEmail" : $("#userEmail").val() },
			success : function(data) {
				alert("회원가입이 완료되었습니다.\n마이페이지에서 추가 인증으로 OYEZ의 모든 콘텐츠를 이용해보세요.");
				location.href = "/";
			},
			error: function(data) {
				alert("잠시후 재시도 바랍니다.");
			}
		});
	}
});

