// 参考： https://syncer.jp/javascript-reverse-reference/output-local-image
// [#form]が変更された時にイベントを実行する
document.getElementById( "form-pic" ).onchange = function()
{
	var fileList , file , fr , result ;
	fileList = this.files ;
	for( var i=0,l=fileList.length; l>i; i++ )
	{
		file = fileList[i] ;
		fr = new FileReader() ;
		fr.onload = function()
		{
            $.ajax({
                type: 'POST',
                url: '../api/picture',
                contentType:'image/jepg',
                data: {src: this.result},
                success: function(xml) {
                    $('#pic-id').text(xml.picture);
                }
            });
		}
        fr.readAsDataURL(file);
	}
}

document.getElementById( "form-icon" ).onchange = function()
{
	var fileList , file , fr , result ;
	fileList = this.files ;
	for( var i=0,l=fileList.length; l>i; i++ )
	{
		file = fileList[i] ;
		fr = new FileReader() ;
		fr.onload = function()
		{
            $.ajax({
                type: 'POST',
                url: '../api/icon',
                contentType:'image/png',
                data: {src: this.result},
                success: function(xml) {
                    $('#icon-id').text(xml.icon);
                }
            });
		}
        fr.readAsDataURL(file);
	}
}
