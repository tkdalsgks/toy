document.querySelector("#show").addEventListener("click", show);
document.querySelector("#close").addEventListener("click", close);

/*
document.getElementById("menuChat").style.display = "none";
document.getElementById("chatSecond").style.display = "none";
document.getElementById("chatThird").style.display = "none";
*/


function openPopup() {
	$(".openPopup").hide();
}

function show() {
	document.querySelector(".background").className = "background show";
}

function close() {
	document.querySelector(".background").className = "background";
	$("#menu").show();
	$("#menuChat").hide();
	$("#chatFirst").show();
	$("#chatSecond").hide();
	$("#chatThird").hide();
	$(".openPopup").show();
}

function menuChat() {
	$("#menu").hide();
	$("#menuChat").show();
}

function chatCreate() {
	$("#chatFirst").hide();
	$("#chatSecond").show();
	$("#chatThrid").hide();
}

function chatList() {
	$("#chatFirst").hide();
	$("#chatSecond").hide();
	$("#chatThird").show();
}

$(document).ready(function() {
    $("#btn-create").on("click", function (e){
        e.preventDefault();

        var name = $("input[name='name']").val();

        if(name == "") {
			swal.fire({
				text: '채팅방 이름을 입력하세요.',
				icon: 'warning',
				confirmButtonColor: '#3085d6',
				confirmButtonText: '확인'
			});
		} else {
            $("form").submit();			
		}
    });
});

$("#createChatRoom").on("click", function() {
	$.ajax({
		url: "chat/room",
		type: "post",
		dataType: "json",
		data: { "name": $("#roomName").val() },
		success: function() {
			location.replace("");
		},
		error: function() {
			swal.fire({
				text: '잠시 후 재시도 바랍니다.',
				footer: '서버와의 통신 에러입니다.',
				icon: 'error',
				confirmButtonColor: '#3085d6',
				confirmButtonText: '확인'
			});
		}
	});
});