const goodsImg1 = document.getElementById("goods__img-user1-1");
const goodsImg2 = document.getElementById("goods__img-user1-2");
const goodsImg3 = document.getElementById("goods__img-user1-3");
const goodsImg4 = document.getElementById("goods__img-user1-4");
const goodsImg5 = document.getElementById("goods__img-user1-5");
const goodsImg6 = document.getElementById("goods__img-user1-6");
const goodsImg7 = document.getElementById("goods__img-user1-7");
const goodsImg8 = document.getElementById("goods__img-user1-8");
const goodsImg9 = document.getElementById("goods__img-user1-9");
const goodsImg10 = document.getElementById("goods__img-user1-10");
const goodsImg11 = document.getElementById("goods__img-user2-1");
const goodsImg12 = document.getElementById("goods__img-user2-2");
const goodsImg13 = document.getElementById("goods__img-user2-3");
const goodsImg14 = document.getElementById("goods__img-user2-4");
const goodsImg15 = document.getElementById("goods__img-user2-5");
const goodsImg16 = document.getElementById("goods__img-user2-6");
const goodsImg17 = document.getElementById("goods__img-user2-7");
const goodsImg18 = document.getElementById("goods__img-user2-8");
const goodsImg19 = document.getElementById("goods__img-user2-9");
const goodsImg20 = document.getElementById("goods__img-user2-10");

var resetGoods1 = document.getElementById('resetGoods1');
var resetGoods2 = document.getElementById('resetGoods2');
resetGoods1.style.opacity = '0';
resetGoods2.style.opacity = '0';

$(document).ready(function(){
	if(goodsCntUser1 == 1) {
		goodsImg1.style.opacity = '1';
	} else if(goodsCntUser1 == 2) {
		goodsImg1.style.opacity = '1';
		goodsImg2.style.opacity = '1';
	} else if(goodsCntUser1 == 3) {
		goodsImg1.style.opacity = '1';
		goodsImg2.style.opacity = '1';
		goodsImg3.style.opacity = '1';
	} else if(goodsCntUser1 == 4) {
		goodsImg1.style.opacity = '1';
		goodsImg2.style.opacity = '1';
		goodsImg3.style.opacity = '1';
		goodsImg4.style.opacity = '1';
	} else if(goodsCntUser1 == 5) {
		goodsImg1.style.opacity = '1';
		goodsImg2.style.opacity = '1';
		goodsImg3.style.opacity = '1';
		goodsImg4.style.opacity = '1';
		goodsImg5.style.opacity = '1';
	} else if(goodsCntUser1 == 6) {
		goodsImg1.style.opacity = '1';
		goodsImg2.style.opacity = '1';
		goodsImg3.style.opacity = '1';
		goodsImg4.style.opacity = '1';
		goodsImg5.style.opacity = '1';
		goodsImg6.style.opacity = '1';
	} else if(goodsCntUser1 == 7) {
		goodsImg1.style.opacity = '1';
		goodsImg2.style.opacity = '1';
		goodsImg3.style.opacity = '1';
		goodsImg4.style.opacity = '1';
		goodsImg5.style.opacity = '1';
		goodsImg6.style.opacity = '1';
		goodsImg7.style.opacity = '1';
	} else if(goodsCntUser1 == 8) {
		goodsImg1.style.opacity = '1';
		goodsImg2.style.opacity = '1';
		goodsImg3.style.opacity = '1';
		goodsImg4.style.opacity = '1';
		goodsImg5.style.opacity = '1';
		goodsImg6.style.opacity = '1';
		goodsImg7.style.opacity = '1';
		goodsImg8.style.opacity = '1';
	} else if(goodsCntUser1 == 9) {
		goodsImg1.style.opacity = '1';
		goodsImg2.style.opacity = '1';
		goodsImg3.style.opacity = '1';
		goodsImg4.style.opacity = '1';
		goodsImg5.style.opacity = '1';
		goodsImg6.style.opacity = '1';
		goodsImg7.style.opacity = '1';
		goodsImg8.style.opacity = '1';
		goodsImg9.style.opacity = '1';
	} else if(goodsCntUser1 >= 10) {
		goodsImg1.style.opacity = '1';
		goodsImg2.style.opacity = '1';
		goodsImg3.style.opacity = '1';
		goodsImg4.style.opacity = '1';
		goodsImg5.style.opacity = '1';
		goodsImg6.style.opacity = '1';
		goodsImg7.style.opacity = '1';
		goodsImg8.style.opacity = '1';
		goodsImg9.style.opacity = '1';
		goodsImg10.style.opacity = '1';
		resetGoods1.style.opacity = '1';
	}
	
	if(goodsCntUser2 == 1) {
		goodsImg11.style.opacity = '1';
	} else if(goodsCntUser2 == 2) {
		goodsImg11.style.opacity = '1';
		goodsImg12.style.opacity = '1';
	} else if(goodsCntUser2 == 3) {
		goodsImg11.style.opacity = '1';
		goodsImg12.style.opacity = '1';
		goodsImg13.style.opacity = '1';
	} else if(goodsCntUser2 == 4) {
		goodsImg11.style.opacity = '1';
		goodsImg12.style.opacity = '1';
		goodsImg13.style.opacity = '1';
		goodsImg14.style.opacity = '1';
	} else if(goodsCntUser2 == 5) {
		goodsImg11.style.opacity = '1';
		goodsImg12.style.opacity = '1';
		goodsImg13.style.opacity = '1';
		goodsImg14.style.opacity = '1';
		goodsImg15.style.opacity = '1';
	} else if(goodsCntUser2 == 6) {
		goodsImg11.style.opacity = '1';
		goodsImg12.style.opacity = '1';
		goodsImg13.style.opacity = '1';
		goodsImg14.style.opacity = '1';
		goodsImg15.style.opacity = '1';
		goodsImg16.style.opacity = '1';
	} else if(goodsCntUser2 == 7) {
		goodsImg11.style.opacity = '1';
		goodsImg12.style.opacity = '1';
		goodsImg13.style.opacity = '1';
		goodsImg14.style.opacity = '1';
		goodsImg15.style.opacity = '1';
		goodsImg16.style.opacity = '1';
		goodsImg17.style.opacity = '1';
	} else if(goodsCntUser2 == 8) {
		goodsImg11.style.opacity = '1';
		goodsImg12.style.opacity = '1';
		goodsImg13.style.opacity = '1';
		goodsImg14.style.opacity = '1';
		goodsImg15.style.opacity = '1';
		goodsImg16.style.opacity = '1';
		goodsImg17.style.opacity = '1';
		goodsImg18.style.opacity = '1';
	} else if(goodsCntUser2 == 9) {
		goodsImg11.style.opacity = '1';
		goodsImg12.style.opacity = '1';
		goodsImg13.style.opacity = '1';
		goodsImg14.style.opacity = '1';
		goodsImg15.style.opacity = '1';
		goodsImg16.style.opacity = '1';
		goodsImg17.style.opacity = '1';
		goodsImg18.style.opacity = '1';
		goodsImg19.style.opacity = '1';
	} else if(goodsCntUser2 >= 10) {
		goodsImg11.style.opacity = '1';
		goodsImg12.style.opacity = '1';
		goodsImg13.style.opacity = '1';
		goodsImg14.style.opacity = '1';
		goodsImg15.style.opacity = '1';
		goodsImg16.style.opacity = '1';
		goodsImg17.style.opacity = '1';
		goodsImg18.style.opacity = '1';
		goodsImg19.style.opacity = '1';
		goodsImg20.style.opacity = '1';
		resetGoods2.style.opacity = '1';
	}
});

user1.addEventListener('click', (event) => {
	const userId = 'dwc06131';
	if(goodsCntUser1 < 10) {
		swal.fire({
			title: '도장을 찍을까요?',
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			confirmButtonText: '확인',
			cancelButtonColor: '#d33',
			cancelButtonText: '취소'
		}).then((result) => {
			if(result.isConfirmed) {
				saveGoods(userId);	
			}
		});
	} else {
		toastr.success('도장을 모두 채웠습니다.');
	}
});

user2.addEventListener('click', (event) => {
	const userId = 'i00421';
	if(goodsCntUser2 < 10) {
		swal.fire({
			text: '도장을 찍을까요?',
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			confirmButtonText: '확인',
			cancelButtonColor: '#d33',
			cancelButtonText: '취소'
		}).then((result) => {
			if(result.isConfirmed) {
				saveGoods(userId);	
			}
		});
	} else {
		toastr.success('도장을 모두 채웠습니다.');
	}
});

function saveGoods(userId) {
	var headers = { "Content-Type": "application/json", "X-HTTP-Method-Override": "POST" };
	var params = { "userId": userId };
	
	//console.log("params : " + JSON.stringify(params));
	
	$.ajax({
		url: "/points/goods",
		type: "POST",
		headers: headers,
		dataType: "JSON",
		data: JSON.stringify(params),
		success: function(response) {
			if(response.result == false) {
				toastr.warning('포인트 적립에 실패했습니다.');
				return false;
			} else if(response.result == true) {
				if(response.goodsCntUser1 == 1) {
					goodsImg1.style.opacity = '1';
				} else if(response.goodsCntUser1 == 2) {
					goodsImg1.style.opacity = '1';
					goodsImg2.style.opacity = '1';
				} else if(response.goodsCntUser1 == 3) {
					goodsImg1.style.opacity = '1';
					goodsImg2.style.opacity = '1';
					goodsImg3.style.opacity = '1';
				} else if(response.goodsCntUser1 == 4) {
					goodsImg1.style.opacity = '1';
					goodsImg2.style.opacity = '1';
					goodsImg3.style.opacity = '1';
					goodsImg4.style.opacity = '1';
				} else if(response.goodsCntUser1 == 5) {
					goodsImg1.style.opacity = '1';
					goodsImg2.style.opacity = '1';
					goodsImg3.style.opacity = '1';
					goodsImg4.style.opacity = '1';
					goodsImg5.style.opacity = '1';
				} else if(response.goodsCntUser1 == 6) {
					goodsImg1.style.opacity = '1';
					goodsImg2.style.opacity = '1';
					goodsImg3.style.opacity = '1';
					goodsImg4.style.opacity = '1';
					goodsImg5.style.opacity = '1';
					goodsImg6.style.opacity = '1';
				} else if(response.goodsCntUser1 == 7) {
					goodsImg1.style.opacity = '1';
					goodsImg2.style.opacity = '1';
					goodsImg3.style.opacity = '1';
					goodsImg4.style.opacity = '1';
					goodsImg5.style.opacity = '1';
					goodsImg6.style.opacity = '1';
					goodsImg7.style.opacity = '1';
				} else if(response.goodsCntUser1 == 8) {
					goodsImg1.style.opacity = '1';
					goodsImg2.style.opacity = '1';
					goodsImg3.style.opacity = '1';
					goodsImg4.style.opacity = '1';
					goodsImg5.style.opacity = '1';
					goodsImg6.style.opacity = '1';
					goodsImg7.style.opacity = '1';
					goodsImg8.style.opacity = '1';
				} else if(response.goodsCntUser1 == 9) {
					goodsImg1.style.opacity = '1';
					goodsImg2.style.opacity = '1';
					goodsImg3.style.opacity = '1';
					goodsImg4.style.opacity = '1';
					goodsImg5.style.opacity = '1';
					goodsImg6.style.opacity = '1';
					goodsImg7.style.opacity = '1';
					goodsImg8.style.opacity = '1';
					goodsImg9.style.opacity = '1';
				} else if(response.goodsCntUser1 == 10) {
					goodsImg1.style.opacity = '1';
					goodsImg2.style.opacity = '1';
					goodsImg3.style.opacity = '1';
					goodsImg4.style.opacity = '1';
					goodsImg5.style.opacity = '1';
					goodsImg6.style.opacity = '1';
					goodsImg7.style.opacity = '1';
					goodsImg8.style.opacity = '1';
					goodsImg9.style.opacity = '1';
					goodsImg10.style.opacity = '1';
					resetGoods1.style.opacity = '1';
					goodsCntUser1 = 10;
				} else if(response.goodsCntUser1 > 10) {
					goodsImg1.style.opacity = '1';
					goodsImg2.style.opacity = '1';
					goodsImg3.style.opacity = '1';
					goodsImg4.style.opacity = '1';
					goodsImg5.style.opacity = '1';
					goodsImg6.style.opacity = '1';
					goodsImg7.style.opacity = '1';
					goodsImg8.style.opacity = '1';
					goodsImg9.style.opacity = '1';
					goodsImg10.style.opacity = '1';
					goodsCntUser1 = 10;
				}
				
				if(response.goodsCntUser2 == 1) {
					goodsImg11.style.opacity = '1';
				} else if(response.goodsCntUser2 == 2) {
					goodsImg11.style.opacity = '1';
					goodsImg12.style.opacity = '1';
				} else if(response.goodsCntUser2 == 3) {
					goodsImg11.style.opacity = '1';
					goodsImg12.style.opacity = '1';
					goodsImg13.style.opacity = '1';
				} else if(response.goodsCntUser2 == 4) {
					goodsImg11.style.opacity = '1';
					goodsImg12.style.opacity = '1';
					goodsImg13.style.opacity = '1';
					goodsImg14.style.opacity = '1';
				} else if(response.goodsCntUser2 == 5) {
					goodsImg11.style.opacity = '1';
					goodsImg12.style.opacity = '1';
					goodsImg13.style.opacity = '1';
					goodsImg14.style.opacity = '1';
					goodsImg15.style.opacity = '1';
				} else if(response.goodsCntUser2 == 6) {
					goodsImg11.style.opacity = '1';
					goodsImg12.style.opacity = '1';
					goodsImg13.style.opacity = '1';
					goodsImg14.style.opacity = '1';
					goodsImg15.style.opacity = '1';
					goodsImg16.style.opacity = '1';
				} else if(response.goodsCntUser2 == 7) {
					goodsImg11.style.opacity = '1';
					goodsImg12.style.opacity = '1';
					goodsImg13.style.opacity = '1';
					goodsImg14.style.opacity = '1';
					goodsImg15.style.opacity = '1';
					goodsImg16.style.opacity = '1';
					goodsImg17.style.opacity = '1';
				} else if(response.goodsCntUser2 == 8) {
					goodsImg11.style.opacity = '1';
					goodsImg12.style.opacity = '1';
					goodsImg13.style.opacity = '1';
					goodsImg14.style.opacity = '1';
					goodsImg15.style.opacity = '1';
					goodsImg16.style.opacity = '1';
					goodsImg17.style.opacity = '1';
					goodsImg18.style.opacity = '1';
				} else if(response.goodsCntUser2 == 9) {
					goodsImg11.style.opacity = '1';
					goodsImg12.style.opacity = '1';
					goodsImg13.style.opacity = '1';
					goodsImg14.style.opacity = '1';
					goodsImg15.style.opacity = '1';
					goodsImg16.style.opacity = '1';
					goodsImg17.style.opacity = '1';
					goodsImg18.style.opacity = '1';
					goodsImg19.style.opacity = '1';
				} else if(response.goodsCntUser2 == 10) {
					goodsImg11.style.opacity = '1';
					goodsImg12.style.opacity = '1';
					goodsImg13.style.opacity = '1';
					goodsImg14.style.opacity = '1';
					goodsImg15.style.opacity = '1';
					goodsImg16.style.opacity = '1';
					goodsImg17.style.opacity = '1';
					goodsImg18.style.opacity = '1';
					goodsImg19.style.opacity = '1';
					goodsImg20.style.opacity = '1';
					resetGoods2.style.opacity = '1';
					goodsCntUser2 = 10;
				} else if(response.goodsCntUser2 > 10) {
					goodsImg11.style.opacity = '1';
					goodsImg12.style.opacity = '1';
					goodsImg13.style.opacity = '1';
					goodsImg14.style.opacity = '1';
					goodsImg15.style.opacity = '1';
					goodsImg16.style.opacity = '1';
					goodsImg17.style.opacity = '1';
					goodsImg18.style.opacity = '1';
					goodsImg19.style.opacity = '1';
					goodsImg20.style.opacity = '1';
					goodsCntUser2 = 10;
				}
			}
		},
		error: function(xhr, status, error) {
			toastr.options = {
				progressBar: true,
			 	showMethod: 'slideDown',
			 	timeOut: 1500
			};
			toastr.error('서버와의 통신 에러입니다.', '잠시 후 재시도 바랍니다.');
			return false;
		}
	});
}

resetGoods1.addEventListener('click', (event) => {
	const userId = 'dwc06131';
	if(goodsCntUser1 >= 10) {
		resetGoods(userId);	
	} else {
		
	}
});

resetGoods2.addEventListener('click', (event) => {
	const userId = 'i00421';
	if(goodsCntUser2 >= 10) {
		resetGoods(userId);
	} else {
		
	}
});

function resetGoods(userId) {
	var headers = { "Content-Type": "application/json", "X-HTTP-Method-Override": "POST" };
	var params = { "userId": userId };
	
	//console.log("params : " + JSON.stringify(params));
	
	$.ajax({
		url: "/points/reset",
		type: "POST",
		headers: headers,
		dataType: "JSON",
		data: JSON.stringify(params),
		success: function(response) {
			if(response.result == false) {
				toastr.warning('리셋에 실패했습니다.');
				return false;
			} else if(response.result == true) {
				location.reload();
			}
		},
		error: function(xhr, status, error) {
			toastr.options = {
				progressBar: true,
			 	showMethod: 'slideDown',
			 	timeOut: 1500
			};
			toastr.error('서버와의 통신 에러입니다.', '잠시 후 재시도 바랍니다.');
			return false;
		}
	});
}
