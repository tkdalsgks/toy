// 상세 페이지로 이동
function goViewPageCommunity(id) {
    location.href = '/community/detail' + `?id=${id}`;
}
function goViewPageReview(id) {
    location.href = '/review/detail' + `?id=${id}`;
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

});
