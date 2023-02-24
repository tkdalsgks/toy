var ws;

window.onload = function(){
	getRoom();
	createRoom();
}

function getRoom(){
	commonAjax('/getRoom', "", 'post', function(result){
		createChatingRoom(result);
	});
}

function createRoom(){
	$("#createRoom").click(function(){
		var msg = {	
			roomName : $('#roomName').val()
		};

		commonAjax('/createRoom', msg, 'post', function(result){
			createChatingRoom(result);
		});

		$("#roomName").val("");
	});
}

function goRoom(number, name){
	location.href="/moveChating?roomName="+name+"&"+"roomNumber="+number;
}

function createChatingRoom(res){
	if(res != null){
		var tag = "<tr><th class='num'>순서</th><th class='room'>방 이름</th><th class='go'></th></tr>";
		res.forEach(function(d, idx){
			var rn = d.roomName.trim();
			var roomNumber = d.roomNumber;
			tag += "<tr>"+
						"<td class='num'>"+(idx+1)+"</td>"+
						"<td class='room'>"+ rn +"</td>"+
						"<td class='go'><button type='button' onclick='goRoom(\""+roomNumber+"\", \""+rn+"\")'>참여</button></td>" +
					"</tr>";	
		});
		$("#roomList").empty().append(tag);
	}
}

function commonAjax(url, parameter, type, calbak, contentType){
	$.ajax({
		url: url,
		data: parameter,
		type: type,
		contentType : contentType!=null?contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		success: function (res) {
			calbak(res);
		},
		error : function(err){
			swal.fire({
				title: '잠시 후 재시도 바랍니다.',
				footer: '서버와의 통신 에러입니다.',
				icon: 'error',
				confirmButtonColor: '#3085d6',
				confirmButtonText: '확인',
			});
			calbak(err);
		}
	});
}