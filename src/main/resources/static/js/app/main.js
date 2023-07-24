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
}