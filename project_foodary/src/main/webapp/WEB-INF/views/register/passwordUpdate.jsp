<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 변경</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script type="text/javascript" src="../js/jquery-3.7.0.js"></script>
<script type="text/javascript" src="../js/userRegister.js"></script>
<link rel="stylesheet" href="../css/threeGrid.css"/>
</head>
<body>
<div class="container">
      <div class="header">
      <jsp:include page="./headerAfter.jsp"></jsp:include>
   </div>
   <div style="text-align: center;" align="center">
      <form action="newPassword" method="post">
         <div>
            <span style="background: #fafcd9; font-size: 30pt; font-weight: 900;">비밀번호 변경</span><br/>
            <span style="text-decoration: underline; background: lavender; font-size: 25pt; font-weight: 800;">안전한 비밀번호로 내 정보를 보호하세요</span><br/><br/>
            현재 비밀번호: <input id="currentpassword" name="currentpassword" type="password" style="font-family: D2Coding;"/><br/><br/>
            새 비밀번호: <input id="newpassword" name="newpassword" type="password" style="font-family: D2Coding;" onkeyup="passwordCheckFunction()"/><br/><br/>
            새 비밀번호 확인: <input id="newpasswordcheck" name="newpasswordcheck" type="password" style="font-family: D2Coding;" onkeyup="passwordCheckFunction()"/><br/><br/>
            <h5 id="passwordCheckMessage" style="color: red; font-weight: bold;"></h5>
         </div>
         <br/><br/>
         <div>
            <button id="newpassword" type="submit" name="newpassword" onclick="PasswordUpdate()" style="background: none; border: 0; cursor: pointer;">
               <span style="background: #baffda; font-size: 35px;">변경하기</span>
            </button>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <button type="button" onclick="location.href='${pageContext.request.contextPath}/register/myPage'" style="background: none; border: 0; cursor: pointer;">
               <span style="background: #baffda; font-size: 35px;">돌아가기</span>
            </button>
         </div>
         <input type="hidden" name="id" value="${id}">
         <input type="hidden" name="username" value="${username}">
         <input type="hidden" name="password" value="${password}">
      </form>
   </div>
   <div class="footer">
      <jsp:include page="./footerAfter.jsp"></jsp:include>
   </div>
</div>
</body>
</html>