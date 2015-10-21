var endpoint = '../api';

// id=like をクリックした際の処理を定義する
$('#like').click(function() {

	// API呼び出し
	$.ajax({
		url: endpoint + '/like'
	});
});

// その他の機能，未完成
// ...
