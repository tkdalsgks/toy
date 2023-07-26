ClassicEditor
    .create( document.querySelector( '#content' ), {
        extraPlugins: [uploadAdapterPlugin],
        language: 'ko',
        ckfinder: { uploadUrl: '/upload' }
    })
    .then( editor => {
    	window.ckeditor_write = editor;
    })
    .catch( error => {
        console.error( error );
    } );

function uploadAdapterPlugin(editor) {
	editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
		return uploadAdapter(loader);
	}
}

// 게시글 삭제
function deleteBoard() {
    const writer = document.getElementById('writer').value;
	const userId = document.getElementById('userId').value;
	
	swal.fire({
		text: '게시글을 삭제할까요?',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		confirmButtonText: '확인',
		cancelButtonColor: '#d33',
		cancelButtonText: '취소'
	}).then((result) => {
		if(result.isConfirmed) {
			if(writer != userId) {
        		swal.fire({
        			text: '권한이 없는 게시글입니다.',
    				icon: 'warning',
    				confirmButtonColor: '#3085d6',
    				confirmButtonText: '확인'
    			});
        	} else {
        		let inputHtml = '';
	            new URLSearchParams(location.search).forEach((value, key) => {
	                inputHtml += `<input type="hidden" name="${key}" value="${value}" />`;
	            })
	            
	            const formHtml = `
	                <form id="deleteForm" action="/${id}/delete" method="post">
	                    ${inputHtml}
	                </form>
	            `;
	            
	            const doc = new DOMParser().parseFromString(formHtml, 'text/html');
	            const form = doc.body.firstChild;
	            document.body.append(form);
	            document.getElementById('deleteForm').submit();
        	}
		}
	});
}

// 게시글 리스트 페이지로 이동
function goListPage() {
    const queryString = new URLSearchParams(location.search);
    queryString.delete('id');
    location.href = '/community' + '?' + queryString.toString();
}

// 게시글 수정 페이지로 이동
function goChangePage() {
	const writer = document.getElementById('writer').value;
	const userId = document.getElementById('userId').value;
	
	if(writer != userId) {
		swal.fire({
			text: '권한이 없는 게시글입니다.',
			icon: 'warning',
			confirmButtonColor: '#3085d6',
			confirmButtonText: '확인'
		});
	} else {
        location.href = '/' + id + '/modify' + location.search;
	}
	/* th:href="@{/community/write( id=${board.id} )}"  */
}

// 사용자 상세 페이지로 이동
function goUserPage(id) {
    location.href = '/member/detail' + `?id=${id}`;
}

$(function() {
	printCommentList();
});

function openModal(id, writer, content) {
	
	$("#commentModal").modal("toggle");
	
	document.getElementById("modalWriter").value = writer;
	document.getElementById("modalContent").value = content;
	
	document.getElementById("btnCommentUpdate").setAttribute("onclick", "updateComment("+ id +")");
	document.getElementById("btnCommentDelete").setAttribute("onclick", "deleteComment("+ id +")");
}

// 댓글 작성
function insertComment(boardId) {
	var content = window.ckeditor_write.getData();
	
	var headers = { "Content-Type": "application/json", "X-HTTP-Method-Override": "POST" };
	var params = { "boardId": boardId, "content": content, "writerId": userId, "writer": userName };
	
	if(content == "") {
		window.ckeditor_write.focus();
		return false;
	} else {
		$.ajax({
			url: comment_uri,
			type: "POST",
			headers: headers,
			dataType: "json",
			data: JSON.stringify(params),
			success: function(response) {
				if(response.result == false) {
					swal.fire({
						text: '댓글 등록에 실패했습니다.',
	    				icon: 'warning',
	    				confirmButtonColor: '#3085d6',
	    				confirmButtonText: '확인'
	    			});
					return false;
				} else {
					// 에디터 내용 초기화
					window.ckeditor_write.setData("");
					// 댓글 리스트 출력
					printCommentList();
				}
			},
			error: function(xhr, status, error) {
				swal.fire({
					text: '잠시 후 재시도 바랍니다.',
					footer: '서버와의 통신 에러입니다.',
					icon: 'error',
					confirmButtonColor: '#3085d6',
					confirmButtonText: '확인'
				});
				return false;
			}
		});
	}
}

// 엔터키 댓글 작성
function enterkey() {
	if(event.keyCode == 13) {
		insertComment(boardId);
	}
};

// 댓글 수정
function updateComment(id) {
	//var writer = document.getElementById("modalWriter");
	//var content = document.getElementById("modalContent");
	
	var uri = comment_uri + id;
	var content = window.ckeditor_change.getData();
	
	var headers = { "Content-Type": "application/json", "X-HTTP-Method-Override": "PATCH" };
	var params = { "id": id, "writer": writerName.value, "content": content };
	
	$.ajax({
		url: uri,
		type: "PATCH",
		headers: headers,
		dataType: "json",
		data: JSON.stringify(params),
		success: function(response) {
			if (response.result == false) {
				swal.fire({
					text: '댓글 수정에 실패했습니다.',
    				icon: 'warning',
    				confirmButtonColor: '#3085d6',
    				confirmButtonText: '확인'
    			});
				return false;
			} else if(response.result == "isUnauthorized") {
				swal.fire({
					text: response.message,
    				icon: 'warning',
    				confirmButtonColor: '#3085d6',
    				confirmButtonText: '확인'
    			});
			}
			printCommentList();
			$("#commentModal").modal("hide");
		},
		error: function(response) {
			swal.fire({
				text: '잠시 후 재시도 바랍니다.',
				footer: '서버와의 통신 에러입니다.',
				icon: 'error',
				confirmButtonColor: '#3085d6',
				confirmButtonText: '확인'
			});
			return false;
		}
	});
}

// 댓글 삭제
function deleteComment(id) {
	var uri = comment_uri + id;
	
	swal.fire({
		text: '댓글을 삭제할까요?',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		confirmButtonText: '확인',
		cancelButtonColor: '#d33',
		cancelButtonText: '취소'
	}).then((result) => {
		if(result.isConfirmed) {
			var headers = {"Content-Type": "application/json", "X-HTTP-Method-Override": "DELETE"};

			$.ajax({
				url: uri,
				type: "DELETE",
				headers: headers,
				dataType: "json",
				success: function(response) {
					if (response.result == false) {
						swal.fire({
							text: '댓글 삭제에 실패했습니다.',
		    				icon: 'warning',
		    				confirmButtonColor: '#3085d6',
		    				confirmButtonText: '확인'
		    			});
						return false;
					} else if(response.result == "isUnauthorized") {
						swal.fire({
							text: response.message,
		    				icon: 'warning',
		    				confirmButtonColor: '#3085d6',
		    				confirmButtonText: '확인'
		    			});
					} else {
						swal.fire({
							text: response.message,
		    				icon: 'warning',
		    				confirmButtonColor: '#3085d6',
		    				confirmButtonText: '확인'
		    			});
					}
					printCommentList();
					$("#commentModal").modal("hide");							
				},
				error: function(xhr, status, error) {
					swal.fire({
						text: '잠시 후 재시도 바랍니다.',
						footer: '서버와의 통신 에러입니다.',
						icon: 'error',
						confirmButtonColor: '#3085d6',
						confirmButtonText: '확인'
					});
					return false;
				}
			});
		}
	});
}

function replyUpdate(id) {
	
	const reply = document.getElementById('reply' + id);
	const replyForm = document.getElementById('replyForm' + id);
	const replyUpdate = document.getElementById('replyUpdate' + id);
	const replyCancel = document.getElementById('replyCancel' + id);
	const replySave = document.getElementById('replySave' + id);
	
	reply.style.display = 'none';
	replyUpdate.style.display = 'none';
	replyCancel.style.display = '';
	replySave.style.display = '';
	
	ClassicEditor
        .create( document.querySelector( '#replyEditor' + id ), {
            extraPlugins: [uploadAdapterPlugin],
            language: 'ko',
            ckfinder: { uploadUrl: '/upload' }
        })
        .then( editor => {
        	window.ckeditor_change = editor;
        })
        .catch( error => {
            console.error( error );
        } );
}

function replyCancel(id) {
	
	const reply = document.getElementById('reply' + id);
	const replyForm = document.getElementById('replyForm' + id);
	const replyUpdate = document.getElementById('replyUpdate' + id);
	const replyCancel = document.getElementById('replyCancel' + id);
	const replySave = document.getElementById('replySave' + id);
	
	reply.style.display = '';
	replyForm.innerHTML = '';
	replyUpdate.style.display = '';
	replyCancel.style.display = 'none';
	replySave.style.display = 'none';
	
	replyForm.innerHTML = "<textarea id= 'replyEditor" + id + "' style='display: none;'></textarea>";
}


// 댓글 리스트 출력
function printCommentList() {
	$.get(printCommentList_uri, function(response) {
		if (isEmpty(response) == false) {
			var commentsHtml = "";

			$(response.commentList).each(function(id, comment) {
				commentsHtml += `
					<li class="comment-li">
						<div class="comment-img">
							<img src="/img/app/chat/chat.png">
							<div class="comment-name">
								<span class="name"><a href="javascript:void(0);" onclick="goUserPage(${comment.writerNo});">${comment.writer}</a></span>
								<div class="comment-edit" style="display: flex;">
									<span class="time comment-time">${timeForToday(moment(comment.IDate).format('YYYY/MM/DD HH:mm'))}
										<span>${comment.UDate != null ? '·' : ''}</span>
										<span class="comment-udate">${comment.UDate != null ? '수정됨' : ''}</span>
									</span>
									<!-- 
									<span onclick="openModal(${comment.id}, '${comment.writer}', '${comment.content}' )" class="material-symbols-outlined comment-edit" style="align-items: center; font-size: 13px; cursor: pointer;">${comment.deleteYn != 'Y' ? 'edit' : ''}</span> 
									-->
								</div>
							</div>
							<div style="display: flex; flex-direction: column;">
								<!--
								<div style="text-align: center;">
									<span class="rating-star">★★★★★</span>
								</div>
								-->
								<div th:if="${userId.value} == ${comment.writerId}" style="text-align: center;">
									<!--
									<span onclick="openModal( '${comment.id}', '${comment.writer}' )" style="align-items: center; font-size: 13px; cursor: pointer;">${comment.deleteYn != 'Y' ? '수정 ' : ''}</span>
									-->
									<span onclick="replyUpdate(${comment.id})" id="replyUpdate${comment.id}" style="align-items: center; font-size: 13px; cursor: pointer;">${comment.deleteYn != 'Y' ? '수정' : ''}</span>
									<span onclick="replyCancel(${comment.id})" id="replyCancel${comment.id}" style="display: none; align-items: center; font-size: 13px; cursor: pointer;">${comment.deleteYn != 'Y' ? '취소' : ''}</span>
									<span onclick="updateComment('${comment.id}', '${comment.writer}')" id="replySave${comment.id}" style="display: none; align-items: center; font-size: 13px; cursor: pointer; background-color: skyblue;">${comment.deleteYn != 'Y' ? '저장' : ''}</span>
									<span onclick="deleteComment(${comment.id})" style="align-items: center; font-size: 13px; cursor: pointer;">${comment.deleteYn != 'Y' ? '삭제' : ''}</span>
								</div>
							</div>
						</div>
						<div>
							<span id="reply${comment.id}" class="desc comment-content">${comment.content}</span>
							<div id="replyForm${comment.id}">
								<textarea id="replyEditor${comment.id}" style="display: none;">${comment.content}</textarea>
							</div>
						</div>
					</li>
				`;
			});
			
			$(".notice-list").html(commentsHtml);
		}
	}, "json");
}

// 게시글 좋아요, 취소
function insertLikes(boardId) {
	var headers = { "Content-Type": "application/json", "X-HTTP-Method-Override": "POST" };
	var params = { "boardId": boardId, "userId": userId };
	
	$.ajax({
		url: insertLikes_uri,
		type: "POST",
		headers: headers,
		dataType: "json",
		data: JSON.stringify(params),
		success: function(response) {
			if(response.todayLikes >= 5) {
				swal.fire({
					text: response.message,
					icon: 'warning',
					confirmButtonColor: '#3085d6',
					confirmButtonText: '확인'
				});
				return false;
			} else if(response.result == false) {
				swal.fire({
					text: '게시글 좋아요에 실패했습니다.',
					icon: 'warning',
					confirmButtonColor: '#3085d6',
					confirmButtonText: '확인'
				});
				return false;
			} else {
				window.location.reload(true);						
			}
		},
		error: function(xhr, status, error) {
			swal.fire({
				text: '잠시 후 재시도 바랍니다.',
				footer: '서버와의 통신 에러입니다.',
				icon: 'error',
				confirmButtonColor: '#3085d6',
				confirmButtonText: '확인'
			});
			return false;
		}
	});
}

function goSettings(userId) {
    location.href = '/' + `${userId}` + '/account';
}