
var prevScrollPos = window.pageYOffset;
window.onscroll = headerbarToggle
function headerbarToggle() {
	var header = document.getElementById('header');
	var headerMobile = document.getElementById('header-mobile');
	
	var currentScrollPos = window.pageYOffset;
	if(currentScrollPos > 50) {
		header.style.padding = '0 200px 0 200px';
		document.body.style.paddingTop = '80px';
		header.style.transition = 'all 1s';
		
		headerMobile.style.padding = '0 200px 0 200px';
		headerMobile.style.transition = 'all 1s';
	} else if(currentScrollPos < 50) {
		header.style.padding = '16px 200px 0 200px';
		document.body.style.paddingTop = '110px';
		header.style.transition = 'all 1s';
		
		headerMobile.style.padding = '16px 200px 0 200px';
		headerMobile.style.transition = 'all 1s';
	}
}
