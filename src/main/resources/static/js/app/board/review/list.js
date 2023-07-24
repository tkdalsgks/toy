window.onload = () => {
	setQueryStringParams();
	
	findAllBoard();
}

function enterkey() {
	if(event.keyCode == 13) {
		movePage(1);
	}
};

// 쿼리 스트링 파라미터 셋팅
function setQueryStringParams() {
	if ( !location.search ) {
		return false;
	}
	
	const form = document.getElementById('searchForm');
	
	new URLSearchParams(location.search).forEach((value, key) => {
		if (form[key]) {
			form[key].value = value;
		}
	})
}

// 게시글 리스트 조회
function findAllBoard() {
	if ( !list.length ) {
		document.getElementById('list').innerHTML = '<td colspan="6"><div className="no_data_msg">검색된 결과가 없습니다.</div></td>';
		drawPage();
	}
	
	let num = pagination.totalRecordCount - ((params.page - 1) * params.recordSize);
	
	drawList(notice, list, likes, num);
	drawPage(pagination, params);
}

// 리스트 HTML draw
function drawList(notice, list, likes, num) {

    let html = '';
    
    notice.forEach(row => {
        html += `
        	<tr>
                <td class="list-important">
	                	<div>
	                		<a href="javascript:void(0);" onclick="goUserPage(${row.writerNo});">
		                		<div class="list-img">
		                			<img src="/img/app/chat/chat.png">
			                		<span class="list-writer">${row.writer}</span>
			                		<span>&nbsp;·&nbsp;</span>
				                	<span class="list-time">${timeForToday(moment(row.idate).format('YYYY/MM/DD HH:mm'))}</span>
				                	<span>${row.udate != null ? '&nbsp;·&nbsp;' : ''}</span>
				                	<span class="list-udate">${row.udate != null ? '수정됨' : ''}</span>
			                	</div>
			                </a>
		        			<a href="javascript:void(0);" onclick="goViewPage(${row.id});">
			                	<div class="list-title">
			                		<span>${row.title}</span>
			                	</div>
			                </a>
		                	<div class="list-info">
			                	<span class="list-notice">${row.noticeYn === false ? '리뷰' : '공지사항'}</span>
			                	<div>
			                		<svg class="list-view-svg" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true" class="h-5 w-5"><path stroke-linecap="round" stroke-linejoin="round" d="M2.036 12.322a1.012 1.012 0 010-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178z"></path><path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path></svg>
				                	<span class="list-view">${row.viewCnt}</span>
			                		<svg class="list-comment-svg" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true" class="h-5 w-5"><path stroke-linecap="round" stroke-linejoin="round" d="M8.625 9.75a.375.375 0 11-.75 0 .375.375 0 01.75 0zm0 0H8.25m4.125 0a.375.375 0 11-.75 0 .375.375 0 01.75 0zm0 0H12m4.125 0a.375.375 0 11-.75 0 .375.375 0 01.75 0zm0 0h-.375m-13.5 3.01c0 1.6 1.123 2.994 2.707 3.227 1.087.16 2.185.283 3.293.369V21l4.184-4.183a1.14 1.14 0 01.778-.332 48.294 48.294 0 005.83-.498c1.585-.233 2.708-1.626 2.708-3.228V6.741c0-1.602-1.123-2.995-2.707-3.228A48.394 48.394 0 0012 3c-2.392 0-4.744.175-7.043.513C3.373 3.746 2.25 5.14 2.25 6.741v6.018z"></path></svg>
				                	<span class="list-comment">${row.commentCnt}</span>
				                	<span class="list__rating-star">★</span>
				                	<span class="list-likes">${row.likesCnt}</span>
			                	</div>
		                	</div>
	                	</div>
                </td>
            </tr>
        `;
    })
    
    likes.forEach(row => {
        html += `
        	<tr>
                <td class="list-important">
	                	<div>
	                		<a href="javascript:void(0);" onclick="goUserPage(${row.writerNo});">
		                		<div class="list-img">
		                			<img src="/img/app/chat/chat.png">
			                		<span class="list-writer">${row.writer}</span>
			                		<span>&nbsp;·&nbsp;</span>
				                	<span class="list-time">${timeForToday(moment(row.idate).format('YYYY/MM/DD HH:mm'))}</span>
				                	<span>${row.udate != null ? '&nbsp;·&nbsp;' : ''}</span>
				                	<span class="list-udate">${row.udate != null ? '수정됨' : ''}</span>
			                	</div>
			                </a>
		                	<a href="javascript:void(0);" onclick="goViewPage(${row.id});">
			                	<div class="list-title">
			                		<span>${row.title}</span>
			                	</div>
			                </a>
		                	<div class="list-info">
			                	<span class="list-notice">${row.likesCnt >= 1 ? '좋아요' : row.id}</span>
			                	<div>
			                		<svg class="list-view-svg" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true" class="h-5 w-5"><path stroke-linecap="round" stroke-linejoin="round" d="M2.036 12.322a1.012 1.012 0 010-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178z"></path><path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path></svg>
				                	<span class="list-view">${row.viewCnt}</span>
			                		<svg class="list-comment-svg" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true" class="h-5 w-5"><path stroke-linecap="round" stroke-linejoin="round" d="M8.625 9.75a.375.375 0 11-.75 0 .375.375 0 01.75 0zm0 0H8.25m4.125 0a.375.375 0 11-.75 0 .375.375 0 01.75 0zm0 0H12m4.125 0a.375.375 0 11-.75 0 .375.375 0 01.75 0zm0 0h-.375m-13.5 3.01c0 1.6 1.123 2.994 2.707 3.227 1.087.16 2.185.283 3.293.369V21l4.184-4.183a1.14 1.14 0 01.778-.332 48.294 48.294 0 005.83-.498c1.585-.233 2.708-1.626 2.708-3.228V6.741c0-1.602-1.123-2.995-2.707-3.228A48.394 48.394 0 0012 3c-2.392 0-4.744.175-7.043.513C3.373 3.746 2.25 5.14 2.25 6.741v6.018z"></path></svg>
				                	<span class="list-comment">${row.commentCnt}</span>
				                	<span class="list__rating-star">★</span>
				                	<span class="list-likes">${row.likesCnt}</span>
			                	</div>
		                	</div>
	                	</div>
                </td>
            </tr>
        `;
    })
    
    list.forEach(row => {
        html += `
            <tr>
                <td class="list-general">
	                	<div>
	                		<a href="javascript:void(0);" onclick="goUserPage(${row.writerNo});">
		                		<div class="list-img">
		                			<img src="/img/app/chat/chat.png">
			                		<span class="list-writer">${row.writer}</span>
			                		<span>&nbsp;·&nbsp;</span>
				                	<span class="list-time">${timeForToday(moment(row.idate).format('YYYY/MM/DD HH:mm'))}</span>
				                	<span>${row.udate != null ? '&nbsp;·&nbsp;' : ''}</span>
				                	<span class="list-udate">${row.udate != null ? '수정됨' : ''}</span>
			                	</div>
			                </a>
		                	<a href="javascript:void(0);" onclick="goViewPage(${row.id});">
			                	<div class="list-title">
			                		<span>${row.title}</span>
			                	</div>
			                </a>
		                	<div class="list-info">
			                	<span class="list-notice">${row.noticeYn === false ? '리뷰' : '공지'}</span>
			                	<div>
			                		<svg class="list-view-svg" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true" class="h-5 w-5"><path stroke-linecap="round" stroke-linejoin="round" d="M2.036 12.322a1.012 1.012 0 010-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178z"></path><path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path></svg>
				                	<span class="list-view">${row.viewCnt}</span>
			                		<svg class="list-comment-svg" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true" class="h-5 w-5"><path stroke-linecap="round" stroke-linejoin="round" d="M8.625 9.75a.375.375 0 11-.75 0 .375.375 0 01.75 0zm0 0H8.25m4.125 0a.375.375 0 11-.75 0 .375.375 0 01.75 0zm0 0H12m4.125 0a.375.375 0 11-.75 0 .375.375 0 01.75 0zm0 0h-.375m-13.5 3.01c0 1.6 1.123 2.994 2.707 3.227 1.087.16 2.185.283 3.293.369V21l4.184-4.183a1.14 1.14 0 01.778-.332 48.294 48.294 0 005.83-.498c1.585-.233 2.708-1.626 2.708-3.228V6.741c0-1.602-1.123-2.995-2.707-3.228A48.394 48.394 0 0012 3c-2.392 0-4.744.175-7.043.513C3.373 3.746 2.25 5.14 2.25 6.741v6.018z"></path></svg>
				                	<span class="list-comment">${row.commentCnt}</span>
				                	<span class="list__rating-star">★</span>
				                	<span class="list-likes">${row.likesCnt}</span>
			                	</div>
		                	</div>
	                	</div>
                </td>
            </tr>
        `;
    })
    
    document.getElementById('list').innerHTML = html;
}

// 페이지 HTML draw
function drawPage(pagination, params) {
	if ( !pagination || !params ) {
		document.querySelector('.paging').innerHTML = '';
		throw new Error('Missing required parameters...');
	}
	
	let html = '';
	
	// 첫 페이지, 이전 페이지
	if (pagination.existPrevPage) {
		html += `
				<a href="javascript:void(0);" onclick="movePage(1)" class="page_bt first">&lt;&lt;</a>
				<a href="javascript:void(0);" onclick="movePage(${pagination.startPage - 1})" class="page_bt prev">&lt;</a>
				`;
	}
	
	// 페이지 번호
	html += '<p>';
	for (let i = pagination.startPage; i <= pagination.endPage; i++) {
		html += (i !== params.page)
		? `<a href="javascript:void(0);" onclick="movePage(${i});">${i}</a>`
		: `<span class="on">${i}</span>`
	}
	html += '</p>';
	
	// 다음 페이지, 마지막 페이지
	if (pagination.existNextPage) {
		html += `
				<a href="javascript:void(0);" onclick="movePage(${pagination.endPage + 1});" class="page_bt next">&gt;</a>
				<a href="javascript:void(0);" onclick="movePage(${pagination.totalPageCount});" class="page_bt last">&gt;&gt;</a>
				`;
	}
	
	document.querySelector('.paging').innerHTML = html;
}

// 페이지 이동
function movePage(page) {
    const form = document.getElementById('searchForm');
    const queryParams = {
		keyword: form.keyword.value
		, searchType: form.searchType.value
        , page: (page) ? page : 1
    }
    
    location.href = location.pathname + '?' + new URLSearchParams(queryParams).toString();
}

// 게시글 상세 페이지로 이동
function goViewPage(id) {
    const queryString = (location.search) ? location.search + `&id=${id}` : `?id=${id}`;
    location.href = '/review/detail' + queryString;
}

// 사용자 상세 페이지로 이동
function goUserPage(id) {
    const queryString = (location.search) ? location.search + `&id=${id}` : `?id=${id}`;
    location.href = '/member/detail' + queryString;
}

// 게시글 리프레쉬
function refreshBoard() {
	movePage(1);
}

// 게시글 작성하기
function writeBoard() {
	location.href = '/review/write';
}

// 검색 박스 생성
const search = document.getElementById('searchBox');
search.style.display = 'none';

function searchBox() {
	if(search.style.display == 'none') {
		search.style.display = '';
	} else {
		search.style.display = 'none';
	}
}
