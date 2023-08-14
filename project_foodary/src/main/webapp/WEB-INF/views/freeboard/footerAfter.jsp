<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>푸터 로그인 후</title>
</head>
<body>
	<div style="height: 200px; line-height: 70px; display: flex; justify-content: space-between; background: #f0f0f0">
			<div style="line-height: 40px;">
				<h5 style="margin:20px; font-size: 20pt;">Copyright &copy;</h5>
				<h5 style="margin:20px; font-size: 20pt;">아무래도</h5>
				<a href="/foodary_final/JSP_main/foodaryMainPageAfter.jsp"
				style="font-family: 'insungitCutelivelyjisu';
				margin:10px; font-size: 25pt; font-weight:900; color: black; text-decoration: none;">Foodary</a>
			</div>
			<div>
				<h5 style="margin:10px; font-size: 17pt; font-weight:500; line-height: 25px;">
					아무래도<br><br>
					02.8282.8282<br><br>
					ahmooraedo@gmail.com<br><br>
					서울시 종로구 우정국로2길 21 9층
				</h5>
			</div>
				<jsp:useBean id="date" class="java.util.Date"/>
				<jsp:useBean id="time" class="java.util.Date"/>
				<fmt:formatDate  value="${date}" pattern="yyyy-MM-dd" var="dietWriteDate"/>
				<fmt:formatDate  value="${time}" pattern="HH:mm" var="dietWriteTime"/>
			<div style="text-align: center;">
				<div>
					<a href="/foodary_final/JSP_food/selectDate.jsp" style="font-size: 20pt; color: black; text-decoration: none;">식단 쓰기</a><br/>
					<a href="/foodary_final/JSP_diet/dietList.jsp?dietWriteDate=${dietWriteDate}&dietWriteTime=${dietWriteTime}"
						style="font-size: 20pt; color: black; text-decoration: none;">식단 보기</a><br/>
					<a href="../freeboard/listView" style="font-size: 20pt; color: black; text-decoration: none;">자유게시판</a>
				</div>
			</div>
			<div style="text-align: center; line-height: 50px;">
				<div>
					<a href="#" style="font-size: 20pt; color: black; text-decoration: none; padding: 2px;">오한민</a><br/>
					<a href="#" style="font-size: 20pt; color: black; text-decoration: none;">남수연</a><br/>
					<a href="#" style="font-size: 20pt; color: black; text-decoration: none;">박상아</a><br/>
					<a href="#" style="font-size: 20pt; color: black; text-decoration: none;">남현서</a><br/>
				</div>
			</div>
			<div>
				<h5 style="margin:20px; font-size: 25pt;">
					by 아무래도
				</h5>
			</div>
		</div>
</body>
</html>