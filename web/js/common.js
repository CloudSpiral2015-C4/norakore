/**
 *	どのページにも必要な要素は書き出すようにしています．
 */

/**
 * 画面上部に固定されるヘッダーを呼び出す関数です．
 * #user-info-header が必要
 * @param {string} username ログインユーザの名前
 */
function getUserInfoHeader(username, bonitos) {
	var header = document.getElementById('user-info-header');
	var headerHTML = '';
	var bonitosInt = Math.floor(bonitos);
	headerHTML += '<div class="row text-center">';
	headerHTML += '    <div class="col-xs-3">';
	headerHTML += '        <div class="btn btn-custom" data-color="blue" font-size="1.0" onclick="location.href=\'index.html\'"><p>トップ</p></div>';
	headerHTML += '    </div>';
	headerHTML += '    <div class="col-xs-6">';
	headerHTML += '        <p id="username">' + username + '</p>';
    headerHTML += '        <p id="bonitos">かつお' + bonitosInt + '匹</p>';
	headerHTML += '    </div>';
	headerHTML += '    <div class="col-xs-3">';
	headerHTML += '        <div class="btn btn-custom" data-color="blue" font-size="0.6" onclick="logout()"><p>ログアウト</p></div>';
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
	footerHTML += '<div class="btn btn-custom" data-color="yellow" data-type="footer" onclick="location.href=\'scanner.html\'"><img src="images/scanya.png"></div>';
	footerHTML += '<div class="btn btn-custom" data-color="yellow" data-type="footer" onclick="location.href=\'items.html\'"><img src="images/items.png"></div>';
	footerHTML += '<div class="btn btn-custom" data-color="yellow" data-type="footer" onclick="location.href=\'shop.html\'"><img src="images/shop.png"></div>';
	footerHTML += '</div>';
	footer.innerHTML = footerHTML;
	return;
}

/* にゃばたーサムネイルを作成する関数
 * 画面に出力するサムネイルを作成します．必要なデータはソースを参照して下さい．
 * @param {nyavatar} data 表示すべきにゃばたーのデータが入ったJSON
 */
function getNyavatarThumbnail(data) {
	var nyavatarThumbnail = "";
    nyavatarThumbnail += '<div class="nyavatar-thumbnail">';
    nyavatarThumbnail += '    <div class="nyavatar-heading">';
    nyavatarThumbnail += '        <img src="' + data.icon + '">';
    nyavatarThumbnail += '        <h4>' + data.name + '</h4>';
    nyavatarThumbnail += '    </div>';
    nyavatarThumbnail += '    <div class="nyavatar-body">';
    nyavatarThumbnail += '        <div class="pull-left">';
    nyavatarThumbnail += '            <img src="' + data.picture + '" class="nyavatar-image">';
    nyavatarThumbnail += '        </div>';
    nyavatarThumbnail += '        <div class="nyavatar-status">';
    nyavatarThumbnail += '            <ul>';
    nyavatarThumbnail += '                <li>主な生息地: <span class="location">' + data.location + '</span></li>';
    nyavatarThumbnail += '                <li>最終発見報告: <span class="date">' + data.date + '</span></li>';
    nyavatarThumbnail += '                <li>いいね: <span class="like">' + data.like + '回</span></li>';
    nyavatarThumbnail += '            </ul>';
    nyavatarThumbnail += '        </div>';
    nyavatarThumbnail += '    </div>';
    nyavatarThumbnail += '    <div class="clearfix"></div>';
    nyavatarThumbnail += '    <div class="nyavatar-footer">';
    nyavatarThumbnail += '        <div class="btn btn-default" onclick="location.href=\'nyavatarDetail.html?nyavatarID=' + data.nyavatarID +'\'">詳細</div>';
    nyavatarThumbnail += '        <div class="btn btn-primary" onclick="findcheck()">発見</div>';
    nyavatarThumbnail += '    </div>';
    nyavatarThumbnail += '</div>';
    return nyavatarThumbnail;
}

/* にゃばたー詳細を作成する関数
 * 画面に出力する詳細情報を作成します．必要なデータはソースを参照して下さい．
 * @param {nyavatarDetail} data 表示すべきにゃばたー詳細情報のデータが入ったJSON
 */
function getNyavatarDetail(data) {
    var nyavatarDetail = "";
    nyavatarDetail += '<div class="nyavatar-detail">';
    nyavatarDetail += '    <div class="nyavatar-detail-heading">';
    nyavatarDetail += '        <img src="' + data.icon + '">';
    nyavatarDetail += '        <h4>' + data.name + '</h4>';
    nyavatarDetail += '    </div>';
    nyavatarDetail += '    <div class="nyavatar-detail-body">';
    nyavatarDetail += '        <img class="nyavatar-detail-image" src="' + data.picture + '" title="' + data.say + '">';
    nyavatarDetail += '        <div class="nyavatar-detail-status">';
    nyavatarDetail += '            <ul>';
    nyavatarDetail += '                <li>主な生息地: <span class="location">' + data.location + '</span></li>';
    nyavatarDetail += '                <li>種類: <span class="date">' + data.type + '</span></li>';
    nyavatarDetail += '                <li>最終発見報告: <span class="date">' + data.date + '</span></li>';
    nyavatarDetail += '                <li>いいね: <span class="like">' + data.like + '回</span></li>';
    nyavatarDetail += '            </ul>';
    nyavatarDetail += '        </div>';
    nyavatarDetail += '    </div>';
    nyavatarDetail += '    <div class="clearfix"></div>';
    nyavatarDetail += '    <div class="nyavatar-detail-footer text-center">';
    if (data.isLiked == "false") { 
        nyavatarDetail += '        <div class="btn btn-warning" onclick="like(\'' + data.nyavatarID + '\')">いいね</div>';
    }
    nyavatarDetail += '        <div class="btn btn-primary" onclick="find()">発見</div>';
    nyavatarDetail += '    </div>';
    nyavatarDetail += '</div>';
    return nyavatarDetail;
}

// いいね
function like(nyavatarID) {
    // 後で実装する
    jQuery.ajax({
        type : 'GET',
        url : '../api/like',
        data : {
            nyavatarID : nyavatarID,
            userID : jQuery.cookie('userID')
        }
    })
    .done(function(data) {
        console.log(data.like);
        location.reload();
    });
}

// にゃばたーの最終発見日時を変更する
function find() {
	// 後で実装する
}

// ログアウトの処理
function logout() {
    jQuery.removeCookie('userID');
    location.href = 'login.html';
}

var initialize = function() 
{
    var userID = jQuery.cookie('userID');
    if(userID == null) {
        location.href = 'login.html';
        console.log('common.js : ログインして下さい');
    }
    jQuery.ajax({
        type: 'GET',
        url: '../api/user',
        data: {
            userID : userID,
        }
    })
    .done(function(data) {
        getUserInfoHeader(data.name, data.bonitos);
        getMenuButtonFooter("ここにはページの説明を書いて下さい．");
        console.log('common.js : 基本UIの書き出し終了');
    });
    return;
};
window.addEventListener('load',initialize,false);
