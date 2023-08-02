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
	
	// 사용자 - 적립내역 더보기
	var points = document.getElementById("user__points");
	var pointsList = document.getElementById("user__points-list");
	var profileId = document.getElementById("userId").value;
	points.style.cursor = 'pointer';
	pointsList.style.display = 'none';
	
	var action = true;
	$(points).click(function() {
		if(action) {
			if(userId == profileId) {
				pointsList.style.display = 'block';
			} else {
				pointsList.style.display = 'block';
				pointsList.style.textAlign = 'center';
				pointsList.style.padding = '1rem 0 1rem 0';
				$("#user__points-list").html('다른 사용자의 적립내역은 볼 수 없습니다.');
			}
		} else {
			pointsList.style.display = 'none';
		}
		action = !action;
	});
});

