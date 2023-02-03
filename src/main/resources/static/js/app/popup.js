document.querySelector("#show").addEventListener("click", show);
document.querySelector("#close").addEventListener("click", close);

document.getElementById("menuChat").style.display = "none";
document.getElementById("chatSecond").style.display = "none";
document.getElementById("chatThird").style.display = "none";

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

        if(name == "")
            alert("채팅방 이름을 입력해주세요.");
        else
            $("form").submit();
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
			alert("잠시후 재시도 바랍니다.");
		}
	});
});