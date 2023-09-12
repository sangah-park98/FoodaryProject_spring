// 자유게시판의 글쓰기에서 사진을 저장할 때 실행된다.
function photoView(event) {
   // ('src', URL.createObjectURL(event.target.files[0])) 
   // => 파일 객체를 URL로 변환한 후 src 속성에 할당하여 이미지 파일을 화면에 보여준다.
   $('#output').attr('src', URL.createObjectURL(event.target.files[0]));
}

// 아무것도 입력하지 않고 저장하기를 눌렀을 때 onsubmit으로 먼저 확인해준다.
function insertCheck() {
	if ($('#name').val().trim() === "") {
		alert('로그인 정보가 없습니다.')
		return false;
	}
	else if ($('#subject').val().trim() === "") {
		alert('제목을 입력해주세요.')
		$('#subject').val('');
		$('#subject').focus();
		return false;
	}
	else if ($('#content').val().trim() === "") {
		alert('내용을 입력해주세요.')
		$('#content').val('');
		$('#content').focus();
		return false;
	}
	else {
		return true;
	}
	
}