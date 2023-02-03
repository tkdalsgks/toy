$('#others-login').on('click' ,function() {
	const one = document.getElementById('form-index-one');
	const two = document.getElementById('form-index-two');
	
	one.style.display = 'none';
	two.style.display = 'initial';
});

var index = {
    init: function() {
        index.bind();
    },
    bind: function() {
    	$('#login-general').on('click' ,function() {
			index.login();
    	});
    },
    login: function() {
    	if( ($("#userId").val() == "") || ( $("#userId").val() == null ) ) {
	        alert("아이디를 입력해주세요.");
	        $("#userId").focus();
	        return false;
	    } else if( ($("#userPwd").val() == "") || ($("#userPwd").val() == null) ) {
	        alert("비밀번호를 입력해주세요.");
	        $("#userPwd").focus();
	        return false;
	    }
	    
        $.ajax({
           url: '/login',
           type: 'post',
           dataType: 'json',
           data: {
           		userId: $("#userId").val(),
           		userPwd: $("#userPwd").val()
           },
           success: function(res) {
           		if(res.code === '200') {
	            	window.location = "/";
            	} else {
            		alert(res.message);
            	}
           },
           error: function(res) {
           		alert("잠시후 재시도 바랍니다.");
           }
        });
	}
}

$(function() {
   index.init();
});
