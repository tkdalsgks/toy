
window.onload = function(){
	document.querySelector(".find-background").className = "find-background";
}

document.querySelector("#find-show").addEventListener("click", findShow);
document.querySelector("#find-close").addEventListener("click", findClose);

document.getElementById("find-id").style.display = "none";
document.getElementById("find-pwd").style.display = "none";

function findOpenPopup() {
	$(".find-openPopup").hide();
}

function findShow() {
	document.querySelector(".find-background").className = "find-background find-show";
}

function findClose() {
	document.querySelector(".find-background").className = "find-background";
	$("#find").show();
	$("#find-id").hide();
	$("#find-pwd").hide();
}

function findId() {
	$("#find").hide();
	$("#find-id").show();
	$("#find-pwd").hide();
}

function findPwd() {
	$("#find").hide();
	$("#find-id").hide();
	$("#find-pwd").show();
}

$('#findUserId').on('click' ,function() {
	$.ajax({
		url: "/find/id",
		type: "post",
		data: { "userEmail" : $("#findIdEmail").val() },
		dataType: "json",
		success: function(data) {
			if(data.result == "false") {
				toastr.warning('다시 한 번 시도해주세요.');
			} else if(data.result == "true") {
				swal.fire({
					title: '아이디를 찾았습니다.',
					text: data.findId,
					icon: 'success',
					confirmButtonColor: '#3085d6',
					confirmButtonText: '확인'
				});
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
});

$('#findUserPwd').on('click' ,function() {
	const findPwdId = document.getElementById('findPwdId').value;
	const findPwdEmail = document.getElementById('findPwdEmail').value;
	
	if( findPwdId == null || findPwdId == '' ) {
		toastr.warning('아이디를 입력하세요.');
		$("#findPwdId").focus();
	} else if( findPwdEmail == null || findPwdEmail == '' ) {
		toastr.warning('이메일을 입력하세요.');
		$("#findPwdEmail").focus();
	} else {
		$.ajax({
			url: "/find/password",
			type: "post",
			data: { "userId" : $("#findPwdId").val(),
					"userEmail" : $("#findPwdEmail").val() },
			dataType: "json",
			success: function(data) {
				if(data.result == "false") {
					toastr.warning('입력한 정보가 일치하지 않습니다.');
				} else if(data.result == "true") {
					toastr.success('이메일로 임시 비밀번호가 전송되었습니다.');
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
