<%@page import="com.foodary.vo.UserRegisterVO"%>
<%@page import="com.foodary.dao.UserRegisterDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원탈퇴 신청</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script type="text/javascript" src="./js/dropInfo.js" defer="defer"></script>
<link rel="stylesheet" href="./css/threeGrid.css"/>
</head>
<body>
<div class="container">
      <div class="header">
      <jsp:include page="./headerAfter.jsp"></jsp:include>
   </div>
   <div align="center">
      <div style="text-align: center;" align="center">
         <span style="background: #fafcd9; font-size: 30pt; font-weight: 900;">비밀번호 재확인</span><br>
         <span style="background: lavender; font-size: 25pt; font-weight: 800;">회원탈퇴를 위해 비밀번호를 다시 한 번 입력해 주세요.</span>
      </div>
      <form action="dropInfo" method="post">
         <table border="0" cellpadding="5" cellspacing="20">
            <tr>
               <th style="vertical-align: middle;">비밀번호</th>
               <td colspan="2">
                  <input 
                     id="password" 
                     name="password"
                     type="password"
                     style="font-family: D2Coding;"
                     />
               </td>
            </tr>
            <tr>
               <th style="vertical-align: middle;">비밀번호 확인</th>
               <td colspan="2">
                  <input
                     id="passwordChk" 
                     name="passwordChk" 
                     type="password" 
                     style="font-family: D2Coding;"
                     />
                  <h5 id="passwordCheckMessage" style="color: red; font-weight: bold;"></h5>
               </td>
            </tr>
            <tr>
               <th colspan="2">
                  <button id="dropInfo" type="submit" name="dropInfo" style="background: none; border: 0; cursor: pointer;">
                     <span style="background: #baffda; font-size: 35px;">탈퇴하기</span>
                  </button>
               </th>
            </tr>
         </table>
         <input type="hidden" name="id" value="${id}">
      </form>
   </div>
   <br><br>
   <div class="footer">
      <jsp:include page="./footerAfter.jsp"></jsp:include>
   </div>
</div>
</body>

<script type="text/javascript">
$(document).ready(function(e){
    $('#dropInfo').click(function(e) {
        e.preventDefault(); // 기본 submit을 막는다.

        if ($('#password').val() == '') {
            alert("비밀번호를 입력해주세요.");
            $('#password').focus();
            return;
        } else if ($('#passwordChk').val() == '') {
            alert("비밀번호를 입력해주세요.");
            $('#passwordChk').focus();
            return;
        }
        if ($('#passwordChk').val() != $('#password').val()){
            alert("비밀번호가 일치하지 않습니다.");
            $('#password').val('');
            $('#passwordChk').val('');
            $('#password').focus();
            return;
        }
        var confirmation = confirm("Foodary에서 탈퇴하시겠습니까?");
        if (confirmation) {
            $('form').submit();
        }
    });
});
</script>
</html>