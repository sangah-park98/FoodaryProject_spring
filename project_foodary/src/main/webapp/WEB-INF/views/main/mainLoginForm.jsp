<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 로그인 폼</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<link rel="stylesheet" href="../css/threeGrid.css"/>
<link rel="stylesheet" href="../css/bootstrap.css"/>
<script type="text/javascript">
	var message = "${msg}";
	if (message) {
		alert(message);
	}
</script>
</head>
<body>
<div class="main" align="center">
	<div style="margin-top: 13%;">
	<form action="loginOK" method="post">
	    <table width="300px;" cellpadding="5" cellspacing="5" align="center">
	       <tr>
	          <th style="text-align: center; font-size: 25pt; padding-right: 40px;">
	         	 <label for="id">ID: </label>
	         	 <input type="text" name="id" width="10px" style="width: 150px; height: 25px; border-left:0; border-right:0; border-top:0; border-bottom:5;">
	          </th>
	       </tr>
	       <tr>
	          <th style="text-align: center; font-size: 25pt; padding-right: 40px;">
	             <label for="password">PW: </label>
	             <input id="password" type="password" name="password"
	             	style="width: 150px; height: 25px; border-left:0; border-right:0; border-top:0; border-bottom: 5; font-family: D2Coding;">
	          </th>
	       </tr>
	       <tr>
	          <td style="text-align: center; padding-right: 45px; padding-top: 10px;">
	        	  <button type="submit" style="background: none; border: 0;">
	             	&nbsp;&nbsp;&nbsp;&nbsp;<span style="background: #baffda; color:black; font-size: 30pt; font-weight: 900;">로그인</span>
	             </button>
	             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	             <button type="button" onclick="history.back()" style="background: none; border: 0;">
	             	<span style="background: #baffda; color:black; font-size: 30pt; font-weight: 900;">돌아가기</span>
	             </button><br/>
	             <button type="button" onclick="location.href='${pageContext.request.contextPath}/register/findId'" style="background: none; border: 0;">
	             &nbsp;&nbsp;&nbsp;
	             	<span style="background: lavender; color:black; font-size: 18pt; cursor: pointer;">아이디 찾기</span>
	             </button>
	             <button type="button" onclick="location.href='${pageContext.request.contextPath}/register/findPassword'" style="background: none; border: 0;">
	             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	             	<span style="background: lavender; color:black; font-size: 18pt; cursor: pointer;">비밀번호 찾기</span>
	             </button>
	          </td>
	       </tr>
	    </table>
	 </form>
	</div>
</div>
</body>
</html>