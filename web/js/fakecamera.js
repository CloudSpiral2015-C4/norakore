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


$('#shot').click(function(){
	// videoをストップするだけで撮影っぽくなる
	$('#video').get(0).pause();
});
