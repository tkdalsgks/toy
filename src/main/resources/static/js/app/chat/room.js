$(document).ready(function() {
		
    //console.log(roomName + ", " + roomId + ", " + username);

    var sockJs = new SockJS("/ws/chat");
    // 1. SockJS를 내부에 들고있는 stomp를 내어준다.
    var stomp = Stomp.over(sockJs);

    // 2. connection이 맺어지면 실행
    stomp.connect({}, function () {
       //console.log("STOMP Connection")

       // 4. subscribe(path, callback)으로 메세지를 받을 수 있음
       stomp.subscribe("/sub/chat/room/" + roomId, function(chat) {
           var chatroom = document.getElementById("msgArea");
           var content = JSON.parse(chat.body);

           var writer = content.writer;
           var message = content.message;
           var str = '';
           
           if(writer === username) {
               str = "<p class='chat-me'>" + message + "</p>";
    	   } else {
               str = "<p class='chat-others'>" + '&#x1F607' + " " + writer + " : " + message + "</p>";
           }
           
           $("#msgArea").append(str);
           chatroom.scrollBy(0, 500);
       });
       
       // 4-1. 입장 메세지
       stomp.subscribe("/sub/chat/entry/" + roomId, function(chat) {
           var chatroom = document.getElementById("msgArea");
           var content = JSON.parse(chat.body);

           var writer = content.writer;
           var message = content.message;
           var str = '';
           
           str = "<div class='alert alert-secondary'>";
    	   str += "<p class='chat-enter'>" + message + "</p>";
    	   str += "</div>";
    	   
    	   $("#msgArea").append(str);
           chatroom.scrollBy(0, 500);
       });
       
       // 4-2. 퇴장 메세지
       stomp.subscribe("/sub/chat/leave/" + roomId, function(chat) {
           var chatroom = document.getElementById("msgArea");
           var content = JSON.parse(chat.body);

           var writer = content.writer;
           var message = content.message;
           var str = '';
           
           str = "<div class='alert alert-secondary'>";
    	   str += "<p class='chat-enter'>" + message + "</p>";
    	   str += "</div>";
    	   
    	   $("#msgArea").append(str);
           chatroom.scrollBy(0, 500);
       });
       
       // 3. send(path, header, message)로 메세지를 보낼 수 있음
       stomp.send('/pub/chat/enter', {}, JSON.stringify({roomId: roomId, writer: username}))
    });
    
    $("#msg").keydown(function(e) {
    	if(e.keyCode === 13) {
    		const msg = document.getElementById("msg");
    		
    		if (msg.value === "" || msg.value == null) {
    			swal.fire({
    				text: '내용을 입력하세요.',
					icon: 'warning',
					confirmButtonColor: '#3085d6',
					confirmButtonText: '확인'
				});
    			msg.focus();
    		} else {
    			//console.log(username + ":" + msg.value);
    			stomp.send('/pub/chat/message', {}, JSON.stringify({roomId: roomId, message: msg.value, writer: username}));
    		}
    		msg.value = '';
    	}
    });
    
    $("#button-send").on("click", function(e){
    	const msg = document.getElementById("msg");
    	
    	if (msg.value === "" || msg.value == null) {
    		swal.fire({
    			text: '내용을 입력하세요.',
				icon: 'warning',
				confirmButtonColor: '#3085d6',
				confirmButtonText: '확인'
			});
			msg.focus();
    	} else {
    		//console.log(username + ":" + msg.value);
    		stomp.send('/pub/chat/message', {}, JSON.stringify({roomId: roomId, message: msg.value, writer: username}));
    	}
    	msg.value = '';
    });
});
