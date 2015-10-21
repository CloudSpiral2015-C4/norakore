var endpoint = '../api';

// カメラオン
$('#camera_on').click(function() {
	// webrtcを使う準備 （クロスブラウザ対策）
	navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || window.navigator.mozGetUserMedia;

	// webrtcを使う
	navigator.getUserMedia(
		// パラメタ1：webrtcのオプション （カメラを使う・音声は使わない）
		{video:true, audio:false},
		// パラメタ2：webrtcが使えた＝カメラを読み込めた場合の処理
		function(stream) {
			console.log(stream);
			$('#video').attr('src', window.URL.createObjectURL(stream));
		},
		// パラメタ3：失敗した場合の処理
		function(err) {
			console.log(err);
		}
	);
});

// shotボタンを押した場合の動作
$('#shot').click(function(){
	// <video>に書き込んだ画像をテキスト化（シリアライズ）するための処理
	// （参考）<canvas>に<video>の画像を投入してから toDataURL() でシリアライズ
	var v = $('#video').get(0);
	var canv = document.createElement('canvas');
	var ctx = canv.getContext('2d');
	canv.width = 160;
	canv.height = 120;
	ctx.drawImage(v, 0, 0, 160, 120);
	var src = canv.toDataURL();

	// alpacaの画像POST APIを呼び出してサーバに写真を保存
	$.ajax({
		type: 'POST',
		url: endpoint + '/photo',
		data: {src: src},
		success: function(xml) {
			// とりあえずコンソールに書き込んだ画像のIDを書き込んでおく
			// （参考） http://.../photo/id.png にアクセスすれば png が見える
			console.log('shot success. id: ' + $('id', xml).text());
		}
	});
});

var l = setInterval(function() {
	// alpacaの画像取得APIを呼び出してサーバから画像IDリストを取得
	$.ajax({
		type: 'GET',
		url: endpoint + '/photo/list',
		success: function(xml) {
			// 取得した画像IDリストを http://.../photo/id.png という文字列に変換して
			// <a> タグに追加する（画像を並べる）
			$('#photos').empty();
			var ids = $('id',xml);
			for (var i = 0; i < ids.length; i ++) {
				var imgurl = endpoint + '/photo/' + ids.eq(i).text() + '.png';
				$('<a>')
					.attr('href', imgurl)
					.append($('<img>').attr('src', imgurl))
					.appendTo('#photos');
			};
		}
	});
}, 5000);
