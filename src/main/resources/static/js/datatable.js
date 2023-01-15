$(document).ready(function() {
	$('#selectListUser').DataTable({
		"dataSrc": "",
		"bLengthChange": false,
		"pageLength": 10,
		"language": {
			"emptyTable": "유저가 없습니다.",
			"info": "총 _TOTAL_건",
			"search": "아이디 : ",
			"zeroRecords": "일치하는 데이터가 없습니다.",
			"paginate": {
				"first": "처음",
				"last": "마지막",
				"next": "다음",
				"previous": "이전"
			},
		}
	});
});