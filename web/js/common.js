/**
 *	どのページにも必要な要素は書き出すようにしています．
 */

/**
 * 画面上部に固定されるヘッダーを呼び出す関数です．
 * #user-info-header が必要
 * @param {string} username ログインユーザの名前
 */
function getUserInfoHeader(username) {
	var header = document.getElementById('user-info-header');
	var headerHTML = '';
	headerHTML += '<div class="row text-center">';
	headerHTML += '    <div class="col-xs-4">';
	headerHTML += '        <div class="btn btn-custom" data-color="blue" font-size="1.2" onclick="location.href=\'index.html\'"><p>トップ</p></div>';
	headerHTML += '    </div>';
	headerHTML += '    <div class="col-xs-4">';
	headerHTML += '        <p id="username">' + username + '</p>';
	headerHTML += '    </div>';
	headerHTML += '    <div class="col-xs-4">';
	headerHTML += '        <div class="btn btn-custom" data-color="blue" font-size="1.1" onclick="location.href=\'#\'"><p>ログアウト</p></div>';
	headerHTML += '    </div>';
	headerHTML += '</div>';
	header.innerHTML = headerHTML;
}



/**
 * 画面下部に固定されるフッターを呼び出す関数です．
 * #menu-button-footer が必要
 * @param {string} message ページの説明文
 */
function getMenuButtonFooter(message) {
	var footer = document.getElementById('menu-button-footer');
	var footerHTML =  '';
	footerHTML += '<p id="footer-info-message">' + message + '</p>';
	footerHTML += '<div id="footer">';
	footerHTML += '<div class="btn btn-custom" data-color="yellow" data-type="footer" onclick="location.href=\'search.html\'"><img src="images/search.png"></div>';
	footerHTML += '<div class="btn btn-custom" data-color="yellow" data-type="footer" onclick="location.href=\'upload.html\'"><img src="images/upload.png"></div>';
	footerHTML += '<div class="btn btn-custom" data-color="yellow" data-type="footer" onclick="location.href=\'my-nyavatars.html\'"><img src="images/my-nyavatars.png"></div>';
	footerHTML += '<div class="btn btn-custom" data-color="yellow" data-type="footer" onclick="location.href=\'scanner.html\'"><img src="images/scanner.png"></div>';
	footerHTML += '<div class="btn btn-custom" data-color="yellow" data-type="footer" onclick="location.href=\'items.html\'"><img src="images/items.png"></div>';
	footerHTML += '<div class="btn btn-custom" data-color="yellow" data-type="footer" onclick="location.href=\'shop.html\'"><img src="images/shop.png"></div>';
	footerHTML += '</div>';
	footer.innerHTML = footerHTML;
	return;
}

