window.onload = () => {
	findAllUser();
}

function findAllUser() {
	if ( !list.length ) {
		document.getElementById('list').innerHTML = '<td colspan="6"><div className="no_data_msg">검색된 결과가 없습니다.</div></td>';
		drawPage();
	}
	
	let num = pagination.totalRecordCount - ((params.page - 1) * params.recordSize);
	
	drawList(list, num);
	drawPage(pagination, params);
}

// 리스트 HTML draw
function drawList(list, num) {
	let html = '';
	
	list.forEach(row => {
        html += `
        	
        	<tr>
                <td class="list-important">
                	<span>${row.userNo}</span>
            	</td>
            	<td>
            		<span>${row.userId}</span>
            	</td>
            	<td>
            		<span>${row.role}</span>
            	</td>
            	<td>
            		<a href="javascript:void(0);" onclick="goViewPage(${row.userNo});"><button>상세보기</button></a>
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

// 상세 페이지로 이동
function goViewPage(userNo) {
    const queryString = (location.search) ? location.search + `&id=${userNo}` : `?id=${userNo}`;
    location.href = '/admin/detail' + queryString;
}
