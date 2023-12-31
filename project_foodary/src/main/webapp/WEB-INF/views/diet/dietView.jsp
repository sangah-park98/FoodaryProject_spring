<%@page import="com.foodary.vo.DietList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>캘린더에서 내가 선택한 날짜의 음식 목록 보기</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="../css/threeGrid.css"/>
</head>
<body>
<div class="container">
   <div class="header">
      <jsp:include page="./headerAfter.jsp"/>
   </div>
	<div class="main" align="center" style="text-align: center;">
<%-- <%
   request.setCharacterEncoding("UTF-8");
%> --%>
<form action="dietUpdateView" method="post">
   	<span style="background: #fafcd9; font-size: 35pt; font-weight: 900;">${dvo.dietWriteDate} 푸드어리</span>
		<div class="diet">
		  <tr>
		         <td align="center" colspan="12" class="text-center">
		             <input type="hidden" id="dietWriteDate" name="dietWriteTime" value="${dvo.dietWriteTime}" />
		           	 <input	type="hidden" id="dietWriteDate" name="dietWriteDate" value="${dvo.dietWriteDate}"  />
		             <input	type="hidden" id="id" name="id" value="${dvo.id}"  />
		             <input	type="hidden" id="idx" name="idx" value="${dvo.idx}"  />
		             <input	type="hidden" id="gup" name="gup" value="${dvo.gup}"  />
		         </td>
		      </tr>
			<!-- 1 -->
			<c:set var="list" value="${userFoodList.list}"/>
			<table width="1500" align="center" border="0" cellpadding="10" cellspacing="10">
			   <tr></tr>
			   <c:forEach var="uvo" items="${list}">
			   <tr>
			      <th><span style="background: lavender; font-size: 25pt;">음식 이름</span> :&nbsp; 
			         <input type="text" name="userFoodName" value="${uvo.foodName}" style="width: 50%; height: 50%; text-align: center;" readonly="readonly"/>      
			      </th>
			      <th><span style="background: lavender; font-size: 25pt;">칼로리</span> :&nbsp; 
			         <input type="text" name="userKcal" value="${uvo.kcal}" style="width: 30%; height: 50%; text-align: center;" readonly="readonly"/> &nbsp;kcal 
			      </th>
			      <th><span style="background: lavender; font-size: 25pt;">탄수화물</span> :&nbsp; 
			         <input type="text" name="userCarbs" value="${uvo.carbs}" style="width: 30%; height: 50%; text-align: center;" readonly="readonly"/> &nbsp;g
			      </th>
			      <th><span style="background: lavender; font-size: 25pt;">단백질</span> :&nbsp; 
			         <input type="text" name="userProtein" value="${uvo.protein}" style="width: 30%; height: 50%; text-align: center;" readonly="readonly"/> &nbsp;g  
			      </th>
			      <th><span style="background: lavender; font-size: 25pt;">지방</span> :&nbsp; 
			         <input type="text" name="userFat" value="${uvo.fat}" style="width: 30%; height: 50%; text-align: center;" readonly="readonly"/> &nbsp;g
			      </th>
			   </tr>
			   </c:forEach>
			   <!-- 2 -->
			   <tr>
			      <th colspan="1"><span style="background: lavender; font-size: 30pt;">메모</span></th>
			      <th colspan="4">
			         <textarea 
			            rows="10" 
			            name="dietMemo" 
			            style="resize: none; width: 90%; height: 90%; vertical-align: middle; padding: 10px;"
			            readonly="readonly"
			            >${dvo.dietMemo}</textarea>
			      </th>
			   </tr>
			   <!-- 3 -->
			   <tr>
			   	<th colspan="5">
			       	<c:if test="${not empty dvo.dietPicture}">
						<img src="/upload/diet/${dvo.dietPicture}" style="max-width: 450px; max-height: 300px;">
					</c:if>
				</th>
				<tr>
			   <!-- 4 -->
			   <tr>
			      <th colspan="10">
			         <button
			            type="submit" 
			            style="background: none; border: 0; cursor: pointer;">
						<span style="background: #baffda; font-size: 25pt; font-weight: 900;">수정하기</span>
			         </button>&nbsp;&nbsp;
			         <button 
			            type="button" 
			             style="background: none; border: 0; cursor: pointer;"
			            onclick="location.href='dietDelete?idx=${dvo.idx}&id=${rvo.id}&dietWriteDate=${dvo.dietWriteDate}&gup=${dvo.gup}'">
			            <span style="background: #baffda; font-size: 25pt; font-weight: 900;">삭제하기</span>
			            </button>&nbsp;&nbsp;
			         <button
			            type="button" 
			            onclick="location.href='dietListView?dietWriteDate=${dvo.dietWriteDate}'" 
			             style="background: none; border: 0; cursor: pointer;">
			            <span style="background: #baffda; font-size: 25pt; font-weight: 900;">목록보기</span>
			            </button>&nbsp;&nbsp;
			      </th>
			   </tr>
			</table>
		</form>
		</div>
	</div>
   <div class="footer">
      <jsp:include page="./footerAfter.jsp"/>
   </div>
  </div>
</body>
</html>