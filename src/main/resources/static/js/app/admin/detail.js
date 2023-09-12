// 상세 페이지로 이동
function goViewPageCommunity(id) {
    location.href = '/community/detail' + `?id=${id}`;
}
function goViewPageReview(id) {
    location.href = '/review/detail' + `?id=${id}`;
}

// 회원 추방
function openModal() {
	
	$("#userInfoModal").modal("toggle");
	
	document.getElementById("modalWriter").value = admin_userId;
	document.getElementById("modalNickname").value = admin_userNickname;
	
	document.getElementById("btnUserUpdate").setAttribute("onclick", "updateUser("+ userId +")");
}

function updateUser() {
	alert(admin_userId);
}

// 저장
function saveUser() {
	var userId = document.getElementById("userId").value;
	var selectBox = $("#userModel option:selected").val();
	
	console.log("아이디 : " + userId);
	console.log("계정권한 : " + userModel);
	console.log("선택값 : " + selectBox);
	
	if(role == 'SUPERADMIN') {
		if(selectBox != 'NONE') {
			swal.fire({
				text: '회원정보를 저장할까요?',
				icon: 'warning',
				showCancelButton: true,
				confirmButtonColor: '#3085d6',
				confirmButtonText: '확인',
				cancelButtonColor: '#d33',
				cancelButtonText: '취소'
			}).then((result) => {
				if(result.isConfirmed) {
					var headers = { "Content-Type": "application/json", "X-HTTP-Method-Override": "POST" };
					var params = { "userId": userId, "role": selectBox };
					
					if(selectBox == 'GUEST') {
						selectBox = '게스트';
					} else if(selectBox == 'USER') {
						selectBox = '사용자';
					} else if(selectBox == 'ADMIN') {
						selectBox = '관리자';
					} else if(selectBox == 'SUPERADMIN') {
						selectBOx = '최고 관리자';
					}
					$.ajax({
						url: saveUser_uri,
						type: "POST",
						headers: headers,
						dataType: "json",
						data: JSON.stringify(params),
						success: function(data) {
							$("#admin__role").html('<td id="admin__role" class="admin-content">' + selectBox + '</td>');
						},
						error: function(data) {
							console.log("fail");
						}
					});
				}
			});
		}
	} else {
		toastr.error('최고 관리자만 설정 가능합니다.');
	}
}

// 탭
$(document).ready(function(){
	
	$('ul.tabs li').click(function(){
		var tab_id = $(this).attr('data-tab');

		$('ul.tabs li').removeClass('current');
		$('.tab-content').removeClass('current');

		$(this).addClass('current');
		$("#"+tab_id).addClass('current');
	})
	
	// 관리자 - 적립내역 더보기
	var points = document.getElementById("admin__points");
	var pointsList = document.getElementById("admin__points-list");
	points.style.cursor = 'pointer';
	pointsList.style.display = 'none';
	
	var action = true;
	$(points).click(function() {
		if(action) {
			pointsList.style.display = 'block';		
		} else {
			pointsList.style.display = 'none';
		}
		action = !action;
	});
})


