<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 로그인 폼</title>
</head>
<body>
	<form action="myPageOK" method="post">
		<div style="display: inline-block; background-image:url('./images/memo.png'); margin-left: 30px;
		background-size:260px; background-repeat:no-repeat; width: 270px; height: 270px; padding-top: 70px;">
			<div>
				<table width="300px;" id="mainLoginForm" cellpadding="5" cellspacing="5" align="center">
			       <tr>
			          <th style="text-align: center; font-size: 20pt; padding-right: 40px;">
			         	 <span style="background: #baffda; font-size: 30px;">${rvo.username}</span> 님<br/>
			         	 건강한 하루 되세요!
			          </th>
			       </tr>
			       <tr>
			          <td style="text-align: center; padding-right: 50px;">
						<input id="idx" type="hidden" name="idx" value="${rvo.idx}"/>
			            <button type="submit">
			            	<span style="background: lavender; color:black; font-size: 25pt;">MY페이지</span>
			            </button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			            <button type="button" onclick="location.href='logoutOK?username=${rvo.username}'">
			            	<span style="background: lavender; color:black; font-size: 25pt;">로그아웃</span>
			            </button>
			          </td>
			       </tr>
			    </table>
				</div>
		</div>
	</form>
</body>
</html>