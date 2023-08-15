function changeIdBefore() {
    var username = $("#username").val().trim();
    var email = $("#email").val().trim();

    if (username === '') {
        alert('이름을 입력해주세요.');
        return false;
    } else if (email === '') {
        alert('이메일을 입력해주세요.');
        return false;
    }

    return true;
}
