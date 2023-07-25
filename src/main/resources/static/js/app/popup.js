/* MENU */
document.querySelector("#show__menu").addEventListener("click", show__menu);
document.querySelector("#close__menu").addEventListener("click", close__menu);

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

/*
function show__chat() {
	document.querySelector(".background__chat").className = "background__chat show__chat";
}
*/

function close__chat() {
	document.querySelector(".background__chat").className = "background__chat";
	$(".openPopupChat").show();
}

/* CHAT - ROOM */
$(document).ready(function() {
    $("#createChatRoom").on("click", function (e){
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
            var roomName = $("#roomName").val();
	
			$.ajax({
				url: "/chat/room",
				type: "POST",
				dataType: "JSON",
				data: { "name": roomName },
				success: function() {
					
					loadChatRoom();
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
		}
    });
});

function openChatRoom() {
	
	if(role == 'GUEST') {
		swal.fire({
			title: '인증이 필요한 계정입니다.',
			text: '계정 인증 페이지로 이동할까요?',
			footer: '인증 후 OYEZ의 모든 콘텐츠 이용이 가능합니다.',
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: '이동',
			cancelButtonText: '취소'
		}).then((result) => {
			if(result.isConfirmed) {
				location.href = '/' + userId + '/account';
			}
		});
	} else if(role == 'USER' || role == 'ADMIN' || role == 'SUPERADMIN') {
		// 게스트 이외의 인증된 권한
		// 채팅 팝업 열림
		document.querySelector(".background__chat").className = "background__chat show__chat";
		
		// 채팅방 리스트 조회
		loadChatRoom();
	}
}

function loadChatRoom() {

	$.ajax({
        url: "/chat/list",
        type: "POST",
        success: function(data) {
            // 채팅방 목록 갱신
            $("#chat__list-chatrooms").html("");
            		
            $.each(data, function(index, value) {
            	//console.log("room : " + index);
            	//console.log("roomId : " + value.roomId);
            	//console.log("roomName : " + value.name);
            	
            	$("#chat__list-chatrooms").append(
	                `<div class="chat__list-name">` +
	                `<a href="/chat/room?roomId=`+ value.roomId + `">` + value.name + `</a>` +
	                `</div>`
	            );
            });
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
}
