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
	header.innerHTML = '<div class="row text-center"><div class="col-xs-4"><a href="#"><div class="btn btn-custom" data-color="blue" font-size="1.2"><p>トップ</p></div></a></div><div class="col-xs-4"><p id="username">' + username + '</p></div><div class="col-xs-4"><a href="#"><div class="btn btn-custom" data-color="blue" font-size="1.1"><p>ログアウト</p></div></a></div></div>';
	return;
}
/*
		'
		<div class="row text-center">
			<div class="col-xs-4">
				<a href="#"><div class="btn btn-custom" data-color="blue" font-size="1.2"><p>トップ</p></div></a>
			</div>
			<div class="col-xs-4">
				<p id="username">' + username + '</p>
			</div>
			<div class="col-xs-4">
				<a href="#"><div class="btn btn-custom" data-color="blue" font-size="1.1"><p>ログアウト</p></div></a>
			</div>
		</div>
		'
*/



/**
 * 画面下部に固定されるフッターを呼び出す関数です．
 * #menu-button-footer が必要
 * @param {string} message ページの説明文
 */
function getMenuButtonFooter(message) {
	var footer = document.getElementById('menu-button-footer');
	footer.innerHTML = '<p id="footer-info-message">' + message +'</p><div id="footer"><div class="btn btn-custom" data-color="yellow" data-type="footer"><a href="#"><img src="images/top.png"></a></div><div class="btn btn-custom" data-color="yellow" data-type="footer"><a href="#"><img src="images/top.png"></a></div><div class="btn btn-custom" data-color="yellow" data-type="footer"><a href="#"><img src="images/top.png"></a></div><div class="btn btn-custom" data-color="yellow" data-type="footer"><a href="#"><img src="images/top.png"></a></div><div class="btn btn-custom" data-color="yellow" data-type="footer"><a href="#"><img src="images/top.png"></a></div></div>';
	return;
}
/*
	'
	<p id="footer-info-message">' + message + ' </p>
	<div id="footer">
		<div class="btn btn-custom" data-color="yellow" data-type="footer"><a href="#"><img src="images/top.png"></a></div>
		<div class="btn btn-custom" data-color="yellow" data-type="footer"><a href="#"><img src="images/top.png"></a></div>
		<div class="btn btn-custom" data-color="yellow" data-type="footer"><a href="#"><img src="images/top.png"></a></div>
		<div class="btn btn-custom" data-color="yellow" data-type="footer"><a href="#"><img src="images/top.png"></a></div>
		<div class="btn btn-custom" data-color="yellow" data-type="footer"><a href="#"><img src="images/top.png"></a></div>
	</div>
	'
*/
