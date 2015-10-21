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

setInterval(function() {
	console.log("check");
	$.ajax({
		url: endpoint + '/report',
		success: function(data){
			console.log($(data).find("total_like").text());
			$("#total").text($(data).find("total_like").text());
		}
	});
},1000);
