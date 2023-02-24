
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
				alert("error: ");
			} else if(data.result == "true") {
				alert("success: " + data.findId);
			}
		},
		error: function(data) {
			swal.fire({
				title: '잠시 후 재시도 바랍니다.',
				footer: '서버와의 통신 에러입니다.',
				icon: 'error',
				confirmButtonColor: '#3085d6',
				confirmButtonText: '확인',
			});
		}
	});
});

$('#findUserPwd').on('click' ,function() {
	const findPwdId = document.getElementById('findPwdId').value;
	const findPwdEmail = document.getElementById('findPwdEmail').value;
	
	if( findPwdId == null || findPwdId == '' ) {
		alert("아이디를 입력하세요.");
		$("#findPwdId").focus();
	} else if( findPwdEmail == null || findPwdEmail == '' ) {
		alert("이메일을 입력하세요.");
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
					alert("등록된 아이디나 이메일이 아니거나 입력한 정보가 일치하지 않습니다.");
				} else if(data.result == "true") {
					alert("success: " + data.findPwd);
				}
			},
			error: function(data) {
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
	
	
});
