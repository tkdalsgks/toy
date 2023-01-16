// 회원가입 아이디 유효성 검사 
$('#userId').on('keyup' ,function(){
	const id = document.getElementById('userId').value;
	const checkResult = document.getElementById('checkUserId');
	const exp = /^(?=.*[a-z0-9])[a-z0-9]{4,16}$/;
	
	if(!id.match(exp)){
    	checkResult.innerHTML = '아이디는 영문, 숫자로 이루어진 4~20자리로 입력해주세요.'
    	checkResult.style.color = 'red';
    } else if(id.match(exp)) {
    	checkResult.innerHTML = '';
		$.ajax({
			url : "check/id",
			type : "post",
			dataType : "json",
			data : {"userId" : $("#userId").val()},
			success : function(data) {
				if(data.result == "false") {
		          checkResult.innerHTML = '이미 사용하고 있는 아이디입니다'
	    		  checkResult.style.color = 'red';
		        } else if(data.result == "true") {
		          $("#userId").attr("value", "Y");
		          checkResult.innerHTML = '사용가능한 아이디입니다'
	    		  checkResult.style.color = 'blue';
		        }
		    }
		})
	}
});

// 회원가입 비밀번호 유효성 검사
$('#userPwd').on('keyup' ,function() {
  if (!/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W])[A-Za-z\d\W]{8,16}$/g.test($('#userPwd').val())) {
    $('#checkUserPwd').css({"color" : "red"});
    $('#checkUserPwd').html("비밀번호는 영문 대/소문자, 숫자, 특수문자가 모두 1개이상 포함된 8~16자리로 입력해주세요.");
    $('#checkUserPwd').show();
  } else {
  	$("#userPwd").attr("value", "Y");
  	$('#checkUserPwd').css({"color" : "blue"});
  	$('#checkUserPwd').html("사용 가능한 비밀번호 입니다.");
  	$('#checkUserPwd').show();
  }
});

// 회원가입 비밀번호 확인 유효성 검사
$('#userPwdConfirm').on('keyup', function () {
	const userPwd = $("#userPwd").val();
	const userPwdConfirm = $("#userPwdConfirm").val();
	
	if(userPwd != userPwdConfirm) {
		$('#userPwdConfirm').css({"color" : "red"});
	    $('#checkUserPwdConfirm').html("입력한 비밀번호가 다릅니다.");
	    $('#checkUserPwdConfirm').show();
	} else {
	  	$("#checkUserPwdConfirm").attr("value", "Y");
	    $('#checkUserPwdConfirm').css({"color" : "blue"});
	  	$('#checkUserPwdConfirm').html("입력한 비밀번호가 일치합니다.");
	  	$('#checkUserPwdConfirm').show();
	}
});

// 회원가입 닉네임 유효성 검사
$('#userNickname').on('keyup' ,function() {
	const nickname = document.getElementById('userNickname').value;
	const checkResult = document.getElementById('checkUserNickname');
	const exp = /^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,10}$/;
	
	if(!nickname.match(exp)){
    	checkResult.innerHTML = '닉네임은 특수문자를 제외한 2~10자리로 입력해주세요.'
    	checkResult.style.color = 'red';
    } else if(nickname.match(exp)) {
		$.ajax({
			url : "check/nickname",
			type : "post",
			dataType : "json",
			data : {"userNickname" : $("#userNickname").val()},
			success : function(data) {
				if(data.result == "false") {
					checkResult.innerHTML = '이미 사용하고 있는 닉네임입니다'
					checkResult.style.color = 'red';
		        } else if(data.result == "true") {
		        	$("#userNickname").attr("value", "Y");
		        	checkResult.innerHTML = '사용가능한 닉네임입니다'
		        	checkResult.style.color = 'blue';
		        }
		    }
		})
	}
});

// 회원가입 이메일 유효성 검사
$('#userEmail').on('keyup' ,function() {
	const email = document.getElementById('userEmail').value;
	const checkResult = document.getElementById('checkUserEmail');
	const exp = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
	
	if(!email.match(exp)){
    	checkResult.innerHTML = '올바르지 않은 이메일 형식입니다.'
    	checkResult.style.color = 'red';
    } else if(email.match(exp)) {
		$.ajax({
			url : "check/email",
			type : "post",
			dataType : "json",
			data : {"userEmail" : $("#userEmail").val()},
			success : function(data) {
				if(data.result == "false") {
		          checkResult.innerHTML = '이미 사용하고 있는 이메일입니다'
    			  checkResult.style.color = 'red';
		        } else if(data.result == "true") {
		          $("#userEmail").attr("value", "Y");
		          checkResult.innerHTML = '사용가능한 이메일입니다.'
    		 	  checkResult.style.color = 'blue';
		        }
		    }
		})
	}
});

//회원가입 버튼 비활성화 -> 활성화
$("#userId, #userPwd, #userPwdConfirm, #userNickname, #userEmail").change(function() {
    if( $("#userId").val() != "" && $("#userPwd").val() != "" && $('#userPwdConfirm').val() != "" && $('#userEmail').val() != "" && $('#userNickname').val() != "") {
    	$('#joinSubmit').prop('disabled', false);
    } else {
    	$('#joinSubmit').prop('disabeld', true);
    }
});