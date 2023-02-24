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
    	$('#userId').on('keyup', function(e) {
    		if(e.keyCode === 13) {
	    		index.login();    		
    		}
    	});
    	$('#userPwd').on('keyup', function(e) {
    		if(e.keyCode === 13) {
	    		index.login();    		
    		}
    	});
    },
    login: function() {
    	if( ($("#userId").val() == "") || ( $("#userId").val() == null ) ) {
			swal.fire({
				title: '아이디를 입력하세요.',
				icon: 'warning',
				confirmButtonColor: '#3085d6',
				confirmButtonText: '확인'
			});
	        $("#userId").focus();
	        return false;
	    } else if( ($("#userPwd").val() == "") || ($("#userPwd").val() == null) ) {
	        swal.fire({
				title: '비밀번호를 입력하세요.',
				icon: 'warning',
				confirmButtonColor: '#3085d6',
				confirmButtonText: '확인'
			});
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
					swal.fire({
						text: res.message,
						icon: 'warning',
						confirmButtonColor: '#3085d6',
						confirmButtonText: '확인'
					});
            	}
           },
           error: function(res) {
           		swal.fire({
					title: '잠시 후 재시도 바랍니다.',
					footer: '서버와의 통신 에러입니다.',
					icon: 'error',
					confirmButtonColor: '#3085d6',
					confirmButtonText: '확인',
				});
           }
        });
	}
}

$(function() {
   index.init();
});
