
var prevScrollPos = window.pageYOffset;
window.onscroll = headerbarToggle
function headerbarToggle() {
	var header = document.getElementById('header');
	var headerMobile = document.getElementById('header-mobile');
	
	var currentScrollPos = window.pageYOffset;
	if(currentScrollPos > 50) {
		header.style.padding = '0 345px 0 345px';
		document.body.style.paddingTop = '70px';
		header.style.transition = 'all 1s';
		
		headerMobile.style.padding = '0 150px 0 150px';
		headerMobile.style.transition = 'all 1s';
	} else if(currentScrollPos < 50) {
		header.style.padding = '10px 345px 10px 345px';
		document.body.style.paddingTop = '100px';
		header.style.transition = 'all 1s';
		
		headerMobile.style.padding = '10px 150px 10px 150px';
		headerMobile.style.transition = 'all 1s';
	}
}
