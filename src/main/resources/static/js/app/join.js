// 회원가입 아이디 유효성 검사
$('#duplicateUserId').on('click' ,function() {
	const id = document.getElementById('userId').value;
	const checkResult = document.getElementById('checkUserId');
	const exp = /^(?=.*[a-z0-9])[a-z0-9]{4,16}$/;	
	
	const imgCheckId = document.getElementById('imgCheckId');
	const imgCancelId = document.getElementById('imgCancelId');
	
	if(!id.match(exp)) {
    	checkResult.innerHTML = '아이디는 영문, 숫자로 이루어진 4~20자리로 입력해주세요.'
    	imgCheckId.style.display = 'none';
    	imgCancelId.style.display = 'initial';
    } else if(id.match(exp)) {
		$.ajax({
			url : "/check/id",
			type : "post",
			data : { "userId" : $("#userId").val() },
			dataType : "json",
			success : function(data) {
				if(data.result == "false") {
					checkResult.innerHTML = '아이디는 영문, 숫자로 이루어진 4~20자리로 입력해주세요. 이미 사용중인 아이디입니다.';
					checkResult.style.color = '#DC143C';
					imgCheckId.style.display = 'none';
					imgCancelId.style.display = 'initial';
		        } else if(data.result == "true") {
		        	checkResult.innerHTML = '사용 가능한 아이디입니다.';
					checkResult.style.color = '#DA70D6';
		        	imgCheckId.style.display = 'initial';
		        	imgCancelId.style.display = 'none';
		        }
		    },
		    error: function(data){
		    	console.log("error: " + data);
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
		checkUserPwd.innerHTML = '비밀번호는 대/소문자, 숫자, 특수문자가 모두 포함된 8~16자리로 입력해주세요.';
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
		checkUserPwdConfirm.innerHTML = '입력한 비밀번호가 다릅니다.';
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
    	checkUserNickname.innerHTML = '닉네임은 특수문자를 제외한 2~10자리로 입력해주세요.';
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
    } else if(email.match(exp)) {
		$.ajax({
			url : "/check/email",
			type : "post",
			dataType : "json",
			data : { "userEmail" : $("#userEmail").val() },
			success : function(data) {
				if(data.result == "false") {
					checkUserEmail.innerHTML = '올바르지 않은 이메일 형식입니다. 이미 사용하고 있는 이메일입니다.';
					checkUserEmail.style.color = '#DC143C';
					imgCheckEmail.style.display = 'none';
					imgCancelEmail.style.display = 'initial';
		        } else if(data.result == "true") {
		        	checkUserEmail.innerHTML = '사용 가능한 이메일입니다.';
		        	checkUserEmail.style.color = '#DA70D6';
		        	imgCheckEmail.style.display = 'initial';
		        	imgCancelEmail.style.display = 'none';
		        }
		    }
		});
	}
});

// 회원가입 버튼 비활성화 -> 활성화
$("#userId, #userPwd, #userPwdConfirm, #userNickname, #userEmail").change(function() {
    if( $("#userId").val() != "" && $("#userPwd").val() != "" && $('#userPwdConfirm').val() != "" && $('#userEmail').val() != "" && $('#userNickname').val() != "") {
    	$('#joinSubmit').prop('disabled', false);
    } else {
    	$('#joinSubmit').prop('disabeld', true);
    }
});