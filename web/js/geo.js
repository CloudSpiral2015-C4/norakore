if (navigator.geolocation) {
	navigator.geolocation.getCurrentPosition(showLocation, showError);
} else {
	alert('not supported');
};

function showLocation(position) {
	var lat = position.coords.latitude;
	var lon = position.coords.longitude;

	// 取得した座標をブラウザに表示する （未完成）
	// ...
}

function showError(error) {
	// エラーメッセージを表示する （未完成）
	// ...
}
