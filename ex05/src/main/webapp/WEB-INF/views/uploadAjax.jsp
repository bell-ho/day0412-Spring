<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">

 	.uploadResult{
 		width:100%; 
 		background-color: gray; 
 	} 
	
 	.uploadResult ul{ 
 		display:flex; 
 		flex-flow:row; 
 		justify-content:center; 
 		align-items: center; 
 	} 
 	.uploadResult ul li{ 
 		list-style:none; 
 		padding: 10px; 
 		alian-content: center;
 		text-align: center;
 	} 
 	.uploadResult ul li img{ 
 		width: 100px; 
 	}
 	.uploadResult ul li span{
 		color:white;
 	}
 	.bigPictureWrapper{
 		position: absolute;
 		display:  none;
 		justify-content: center;
 		align-items: center;
 		top:0%;
 		width: 100%;
 		height: 100%;
 		background-color: gray;
 		z-index: 100;
 		background: rgba(255,255,255,0.5);
 	}
 	.bigPicture{
 		position: relative;
 		display:flex;
 		justify-content: center;
 		align-items: center;  
 	}
 	.bigPicture img{
 		width: 600px;
 	}
</style>
</head>

<body>
	<h1>업로드 에이작스</h1>

	<div class='uploadDiv'>
		<input type='file' name='uploadFile' multiple>
	</div>

	<div class='uploadResult'>
		<ul>
		
		</ul>
	</div>
	
	<button id='uploadBtn'>Upload</button>

	<script src="https://code.jquery.com/jquery-3.3.1.min.js"
		integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
		crossorigin="anonymous"></script>

<script>

function showImage(fileCallPath){
// 	alert(fileCallPath);

	$(".bigPictureWrapper").on("click",function(e){
		$(".bigPicture").animate({width:"0%", height: '0%'}, 1000);
		setTimeout(function(){
			$('.bicPictureWrapper').hide();
		}, 1000);
	});

// 	$(".bigPicture")
// 	.html("<img src='/display?fileName=" encodeURI(fileCallPath)+"'>").animate({width:'100%', height:  '100%'}, 1000);
}

$(document).ready(function() {
	
var uploadResult = $(".uploadResult ul");
	
	function showUploadedFile(uploadResultArr){
		
		var str = "";

		$(uploadResultArr).each(function(i, obj){

			if(!obj.image){

				var fileCallPath = encodeURIComponent( obj.uploadPath +"/"+ obj.uuid +"_"+ obj.fileName);

				var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
				
				 str += "<li><div><a href='/download?fileName="+fileCallPath+"'>"+
		           "<img src='/resources/img/attach.png'>"+obj.fileName+"</a>"+
		           "<span data-file=\'"+fileCallPath+"\' data-type='file'> x </span>"+
		           "<div></li>"
		           
// 				str += "<li><a href='/download?fileName="+fileCallPath+"'><img src='/resources/img/attach.png'>"+obj.fileName+"</a></li>"
			}else{
				
				var fileCallPath = encodeURIComponent( obj.uploadPath +"/s_"+ obj.uuid +"_"+ obj.fileName);

				var originPath = obj.uploadPath+"\\"+obj.uuid+"_"+obj.fileName;

				originPath = originPath.replace(new RegExp(/\\/g),"/");

				 str += "<li><a href=\"javascript:showImage(\'"+originPath+"\')\">"+
	              "<img src='display?fileName="+fileCallPath+"'></a>"+
	              "<span data-file=\'"+fileCallPath+"\' data-type='image'> x </span>"+
	              "<li>";
				
// 				str += "<li><a href=\"javascript:showImage(\'"+originPath+"\')\"><img src='/display?fileName="+fileCallPath+"'></a><li>";
			}
		});

		uploadResult.append(str);
	}
	
var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	var maxSize = 5242880;

	function checkExtenstion(fileName, fileSize) {

		if (fileSize >= maxSize) {
			alert("사이즈 초과");
			return false;
		}
		if (regex.test(fileName)) {
			alert("불가능한 확장자");
			return false;
		}
		return true;
	}

var cloneObj = $(".uploadDiv").clone();
	
$("#uploadBtn").on("click",function(e) {
	
			var formData = new FormData();

			var inputFile = $("input[name='uploadFile']");

			var files = inputFile[0].files;

			console.log(files);

			//파일추가
			for (var i = 0; i < files.length; i++) {

				if (!checkExtenstion(files[i].name, files[i].size)) {
					return false;
				}

				formData.append("uploadFile", files[i]);

			}
			$.ajax({
				url : '/uploadAjaxAction',
				processData : false,
				contentType : false,
				data : formData,
				type : 'POST',
				dataType: 'json',
				success : function(result) {
					console.log(result);
					
					showUploadedFile(result);
					
					$(".uploadDiv").html(cloneObj.html());
					
				}
			}); //$.ajax

		});
	});
</script>
</body>
</html>