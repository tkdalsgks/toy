
var prevScrollPos = window.pageYOffset;
window.onscroll = headerbarToggle
function headerbarToggle() {
	var header = document.getElementById('header');
	var headerMobile = document.getElementById('header-mobile');
	
	var currentScrollPos = window.pageYOffset;
	if(currentScrollPos > 50) {
		header.style.paddingTop = '0';
		header.style.paddingBottom = '0';
		header.style.transition = 'all 1s';
		
		headerMobile.style.paddingTop = '0';
		headerMobile.style.paddingBottom = '0';
		headerMobile.style.transition = 'all 1s';
	} else if(currentScrollPos < 50) {
		header.style.paddingTop = '5px';
		header.style.paddingBottom = '5px';
		header.style.transition = 'all 1s';
		
		headerMobile.style.paddingTop = '5px';
		headerMobile.style.paddingBottom = '5px';
		headerMobile.style.transition = 'all 1s';
	}
}
