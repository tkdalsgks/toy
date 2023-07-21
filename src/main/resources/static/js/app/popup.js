/* MENU */
document.querySelector("#show__menu").addEventListener("click", show__menu);
document.querySelector("#close__menu").addEventListener("click", close__menu);

function openPopupMenu() {
	$(".openPopupMenu").hide();
}

function show__menu() {
	document.querySelector(".background__menu").className = "background__menu show__menu";
}

function close__menu() {
	document.querySelector(".background__menu").className = "background__menu";
	$(".openPopupMenu").show();
}

/* CHAT */
document.querySelector("#show__chat").addEventListener("click", show__chat);
document.querySelector("#close__chat").addEventListener("click", close__chat);

function openPopupChat() {
	$(".openPopupChat").hide();
	
	/* loadChatRoom(); */
}

function show__chat() {
	document.querySelector(".background__chat").className = "background__chat show__chat";
}

function close__chat() {
	document.querySelector(".background__chat").className = "background__chat";
	$(".openPopupChat").show();
}

/* CHAT - ROOM */
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

/*
$("#createChatRoom").on("click", function() {
	$.ajax({
		url: "/chat/room",
		type: "post",
		dataType: "json",
		data: { "name": $("#roomName").val() },
		success: function() {
			$("#chat__list-chatrooms").html(
                `<div id="chat__list-chatrooms">` +
                `<a>create Room!!</a>` +
                `</div>`
            );
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
*/

/*
function loadChatRoom() {
	$("#chat__list-chatrooms").html("");
	
	
	console.log("room : " + room);
	
	$.ajax({
        url: "/chat/room",
        type: "GET",
        data: {
            room: room,
        },
        dataType: "JSON",
        success: function(result) {
        	console.log("success : " + room);
            // 채팅방 목록 갱신
            $("#chat__list-chatrooms").html(
                `<div id="chat__list-chatrooms">` +
                `<a>123123</a>` +
                `</div>`
            );
        },
        error: function() {
        	console.log("fail");
        }
	});
}
*/
