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
	headerHTML += '<div class="row text-center">';
	headerHTML += '    <div class="col-xs-3">';
	headerHTML += '        <div class="btn btn-custom" data-color="blue" font-size="1.0" onclick="location.href=\'index.html\'"><p>トップ</p></div>';
	headerHTML += '    </div>';
	headerHTML += '    <div class="col-xs-6">';
	headerHTML += '        <p id="username">' + username + '</p>';
    headerHTML += '        <p id="bonitos">かつお' + bonitos + '個</p>';
	headerHTML += '    </div>';
	headerHTML += '    <div class="col-xs-3">';
	headerHTML += '        <div class="btn btn-custom" data-color="blue" font-size="0.6" onclick="location.href=\'#\'"><p>ログアウト</p></div>';
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
    var nyavatarThumbnail = "";
    nyavatarThumbnail += '<div class="nyavatar-thumbnail">';
    nyavatarThumbnail += '    <div class="nyavatar-heading">';
    nyavatarThumbnail += '        <img src="' + data.icon + '">';
    nyavatarThumbnail += '        <h4>' + data.name + '</h4>';
    nyavatarThumbnail += '    </div>';
    nyavatarThumbnail += '    <div class="nyavatar-body">';
    nyavatarThumbnail += '        <div>';
    nyavatarThumbnail += '            <img src="' + data.picture + '" style="width: 200px; margin: 0 auto;">';
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
    nyavatarThumbnail += '        <div class="btn btn-warning" onclick="like()">いいね</div>';
    nyavatarThumbnail += '        <div class="btn btn-primary" onclick="findcheck()">発見</div>';
    nyavatarThumbnail += '    </div>';
    nyavatarThumbnail += '</div>';
    return nyavatarThumbnail;
}

// いいね
function like() {
    // 後で実装する
}

// にゃばたーの最終発見日時を変更する
function findcheck() {
	// 後で実装する
}

// 参考： https://syncer.jp/javascript-reverse-reference/output-local-image
// [#form]が変更された時にイベントを実行する
//document.getElementById( "picture" ).onchange = function()
function encodeBase64(file)
{
	var fileList , file , fr , result ;
	fileList = this.files ;
	for( var i=0,l=fileList.length; l>i; i++ )
	{
		file = fileList[i] ;
		fr = new FileReader() ;
		fr.onload = function()
		{
			return this.result
            $.ajax({
                type: 'POST',
                url: '../api/picture',
                contentType:'image/jepg',
                data: {src: this.result},
                success: function(xml) {
                    $('#picture').text(xml.picture);
                }
            });
		}
        fr.readAsDataURL(file);
	}
}

function Base64_From_StringOfBinaryData(binary){
	var dic = [
		'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P',
		'Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f',
		'g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v',
		'w','x','y','z','0','1','2','3','4','5','6','7','8','9','+','/'
	];
	var base64 = "";
	var num = binary.length;
	var n = 0;
	var b = 0;

	var i = 0;
	while(i < num){
		b = binary.charCodeAt(i);
		if(b > 0xff) return null;
		base64 += dic[(b >> 2)];
		n = (b & 0x03) << 4;
		i ++;
		if(i >= num) break;

		b = binary.charCodeAt(i);
		if(b > 0xff) return null;
		base64 += dic[n | (b >> 4)];
		n = (b & 0x0f) << 2;
		i ++;
		if(i >= num) break;

		b = binary.charCodeAt(i);
		if(b > 0xff) return null;
		base64 += dic[n | (b >> 6)];
		base64 += dic[(b & 0x3f)];
		i ++;
	}

	var m = num % 3;
	if(m){
		base64 += dic[n];
	}
	if(m == 1){
		base64 += "==";
	}else if(m == 2){
		base64 += "=";
	}
	return base64;
}

console.log('common.js : 基本UIの書き出し終了');
var initialize = function() 
{
    jQuery.ajax({
        type: 'GET',
        url: '../api/user',
        data: {
            userID : 'testUser',
        }
    })
    .done(function(data) {
        getUserInfoHeader(data.name, data.bonitos);
        getMenuButtonFooter("ここにはページの説明を書いて下さい．");
    });
    return;
};
window.addEventListener('load',initialize,false);
