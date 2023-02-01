
var prevScrollPos = window.pageYOffset;
window.onscroll = headerbarToggle
function headerbarToggle() {
	var header = document.getElementById('header');
	
	var currentScrollPos = window.pageYOffset;
	if(currentScrollPos > 50) {
		header.style.padding = '0 150px 0 150px';
		document.body.style.paddingTop = '80px';
		header.style.transition = 'all 1s';
	} else if(currentScrollPos < 50) {
		header.style.padding = '16px 150px 0 150px';
		document.body.style.paddingTop = '110px';
		header.style.transition = 'all 1s';
	}
}
