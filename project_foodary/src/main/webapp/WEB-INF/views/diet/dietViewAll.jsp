<%@page import="com.foodary.vo.DietList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>푸드어리 보기</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="../css/threeGrid.css"/>
<script type="text/javascript" src="../js/jquery-3.7.0.js"></script>
<script type="text/javascript" src="../js/mainFunction.js"></script>
<script type="text/javascript" src="../js/dietViewAll.js" defer="defer"></script>
<style type="text/css">
	input[type='date']::before {
	  content: attr(data-placeholder);
	  width: 100%;
	}

	input[type='date']:active {
	  content: attr(data-placeholder);
	}

	input[type='date'] {
		padding: 5px;
		width: 40%; width: 200px;
		border-radius: 10px;
	}

	input[type='date']:focus::before,
	input[type='date']:valid::before {
	  display: none;
	}

	input[type='time']::before {
	  content: attr(data-placeholder);
	  width: 100%;
	}

	input[type='time'] {
		padding: 5px;
		width: 40%; width: 200px;
		border-radius: 10px;
	}
	
	input[type='time']:focus::before,
	input[type='time']:valid::before {
	  display: none;
	}
</style>
</head><body onload = "showNutrient()">
<div class="container">
   <div class="header">
      <jsp:include page="./headerAfter.jsp"></jsp:include>
   </div>
	<div class="main" align="center" style="text-align: center;">
<div class="main" style="text-align: center;" align="center">
<input id="id" type="hidden" name="id" value="${rvo.id}"/>
<input id="height" type="hidden" name="height" value="${rvo.height}">
<input id="currentWeight" type="hidden" name="currentWeight" value="${rvo.currentWeight}">
<input id="goalWeight" type="hidden" name="goalWeight" value="${rvo.goalWeight}">
<input id="age" type="hidden" name="age" value="${rvo.age}">
<input id="gender" type="hidden" name="gender" value="${rvo.gender}"/>
<c:if test="${rvo.state eq 'health'}">
    <input id="health" type="radio" name="mode" value="health" checked="checked" style="display: none;"/>
    <input id="diet" type="radio" name="mode" value="diet" style="display: none;"/>
</c:if>
<c:if test="${rvo.state eq 'diet'}">
    <input id="health" type="radio" name="mode" value="health" style="display: none;"/>
    <input id="diet" type="radio" name="mode" value="diet" checked="checked" style="display: none;"/>
</c:if>
<c:set var="active" value="${rvo.active}"/>
<select name="active" style="height: 40px; display: none;">
   <option value="다시" name="active" 
      <c:if test="${active == '다시'}">selected="selected"
      </c:if>>활동량 선택
   </option>
     <option value="1.2" name="active" 
        <c:if test="${active == '1.2'}">selected="selected"
        </c:if>>많이 앉아있는 경우
     </option>
   <option value="1.375" name="active" 
      <c:if test="${active == '1.375'}">selected="selected"
      </c:if>>앉아있는 일이 적은 경우
   </option>
   <option value="1.55" name="active" 
      <c:if test="${active == '1.55'}">selected="selected"
      </c:if>>움직임이 많은 경우
   </option>
   <option value="1.725" name="active" 
      <c:if test="${active == '1.725'}">selected="selected"
      </c:if>>운동 될 움직임을 할 경우
   </option>
   <option value="1.9" name="active" 
      <c:if test="${active == '1.9'}">selected="selected"
      </c:if>>매우 많은 운동량
   </option>
</select>

         <span style="background: #fafcd9; font-size: 35pt; font-weight: 900;">${dietWriteDate} 푸드어리</span>
                           <button
                     type="button" 
                     onclick="location.href='dietListView?dietWriteDate=${dietWriteDate}&id=${rvo.id}'" 
                      style="background: none; border: 0; cursor: pointer;">
                     <span style="background: #baffda; font-size: 25pt; font-weight: 900;">목록보기</span>
                     </button>&nbsp;&nbsp;
         
      <div class="diet">
         <form action="dietUpdate" method="post">
         <c:set var="list" value="${userFoodList.list}"/><!-- 해당 시간대만 -->
         <table width="1500" align="center" border="0" cellpadding="10" cellspacing="10">
            <!-- 1 -->
    <%--         <tr>
          ${selectDietList}<br/>
          ${userFoodList}<br/>   
          ${selectDietList[0]}
            </tr> --%>
          <c:forEach var="diet" items="${selectDietList}">
             <tr>
		        <th colspan="5">
		            <span style="background: lavender; font-size: 30pt;">${diet.dietWriteTime}</span>
		        </th>
		    </tr>
			    <!-- UserFoodVO 그룹화 -->
			    <c:forEach var="food" items="${userFoodList.list}" varStatus="status">
			        <c:if test="${food.gup eq diet.gup}">
			            <!-- UserFood 정보 출력 -->
			            <tr>
			                <th class="kcals" id="kcal">
			                    <span style="background: lavender; font-size: 25pt;">음식 이름</span> :&nbsp; 
			                    <input type="text" id="userFoodName_${status.index}" name="userFoodName_${status.index}" value="${food.foodName}" style="width: 50%; height: 50%; text-align: center;" readonly="readonly"/>      
			                </th>
			                <th>
			                    <span style="background: lavender; font-size: 25pt;">칼로리</span> :&nbsp; 
			                    <input type="text" id="userKcal_${status.index}" name="userKcal_${status.index}" value="${food.kcal}" style="width: 30%; height: 50%; text-align: center;" readonly="readonly"/> &nbsp;kcal 
			                </th>
			                <th>
			                    <span style="background: lavender; font-size: 25pt;">탄수화물</span> :&nbsp; 
			                    <input type="text" id="userCarbs_${status.index}" name="userCarbs_${status.index}" value="${food.carbs}" style="width: 30%; height: 50%; text-align: center;" readonly="readonly"/> &nbsp;g
			                </th>
			                <th>
			                    <span style="background: lavender; font-size: 25pt;">단백질</span> :&nbsp; 
			                    <input type="text" id="userProtein_${status.index}" name="userProtein_${status.index}" value="${food.protein}" style="width: 30%; height; 50%; text-align: center;" readonly="readonly"/> &nbsp;g  
			                </th>
			                <th>
			                    <span style="background: lavender; font-size: 25pt;">지방</span> :&nbsp; 
			                    <input type="text" id="userFat_${status.index}" name="userFat_${status.index}" value="${food.fat}" style="width: 30%; height: 50%; text-align: center;" readonly="readonly"/> &nbsp;g
			                </th>
			            </tr>
			        </c:if>
			    </c:forEach>
		    <!-- Diet 정보 출력 -->
		    <tr>
		        <th colspan="5">
		            <span style="background: lavender; font-size: 30pt;">Diet 메모</span>
		        </th>
		    </tr>
		    <tr>
		        <td colspan="5">
		            <textarea 
		                rows="10" 
		                name="dietMemo" 
		                style="resize: none; width: 90%; height: 90%; vertical-align: middle; padding: 10px;"
		                readonly="readonly"
		            >${diet.dietMemo}</textarea>
		        </td>
		    </tr>
            <tr>
               <th colspan="5">
                   <c:if test="${not empty diet.dietPicture}">
                  <img src="/upload/diet/${diet.dietPicture}" style="max-width: 450px; max-height: 300px;">
               </c:if>
            </th>
            <tr>
		     <tr>
               <th colspan="10">
                  <button
                     type="submit" 
                     style="background: none; border: 0; cursor: pointer;"
                  onclick="location.href='dietUpdateView?gup=${diet.gup}&id=${rvo.id}'">
                  <span style="background: #baffda; font-size: 25pt; font-weight: 900;">수정하기</span>
                  </button>&nbsp;&nbsp;
                  <button 
                     type="button" 
                      style="background: none; border: 0; cursor: pointer;"
                     onclick="location.href='dietDelete?idx=${dvo.idx}'">
                     <span style="background: #baffda; font-size: 25pt; font-weight: 900;">삭제하기</span>
                     </button>&nbsp;&nbsp;
               </th>
            </tr>		   
			</c:forEach>
		    </table>
		    <!-- 영양소 그래프 -->
		      <table width="1400" align="center" border="0" cellpadding="10" cellspacing="10">
		    <tr style="height: 30px;"></tr>
		     <tr>
		        <td style="height: 40px; text-align: left;">
                 <span style="background: lavender; font-size: 25pt; margin-left: 300px;"><칼로리></span>
                 <div class="progress" style="height: 40px; width: 800px; margin-left: auto; margin-right: auto;">
                   <div id="kcalGraph" class="progress-bar" role="progressbar" 
                   aria-valuemin="0" style="width:0%; height: 40px; font-size: 18pt; background: #8800ff;">
                   </div>
                 </div>
               </td>   
            </tr>
           <tr style="height: 30px;"></tr>
            <tr>
             <td style="height: 40px; text-align: left;">
                <span style="background: lavender; font-size: 25pt; margin-left: 300px;"><탄수화물></span>
                <div class="progress" style="height: 40px; width: 800px; margin-left: auto; margin-right: auto;">
                  <div id="carbsGraph" class="progress-bar progress-bar-info" role="progressbar" 
                     aria-valuemin="0" style="width:0%; height: 40px; font-size: 18pt; background: #8800ff;">
                  </div>
                </div>
             </td>
            </tr>
            <tr style="height: 30px;"></tr>
            <tr>
             <td style="height: 40px; text-align: left;">
                  <span style="background: lavender; font-size: 25pt; margin-left: 300px;"><단백질></span>
                <div class="progress" style="height: 40px; width: 800px; margin-left: auto; margin-right: auto;">   
                     <div id="proteinGraph" class="progress-bar progress-bar-info" role="progressbar"
                        aria-valuemin="0" style="width:0%; height: 40px; font-size: 18pt; background: #8800ff;">
                        
                     </div>
                </div>
             </td>
            </tr>
            <tr style="height: 30px;"></tr>
            <tr>
             <td style="height: 40px; text-align: left;">
                   <span style="background: lavender; font-size: 25pt; margin-left: 300px;"><지방></span>
                <div class="progress" style="height: 40px; width: 800px; margin-left: auto; margin-right: auto;">
                     <div id="fatGraph" class="progress-bar progress-bar-info" role="progressbar"
                        aria-valuemin="0" style="width:0%; height: 40px; background: #8800ff;">
                     </div>
                </div>
             </td>
		    </tr>
         </table>
      </form>
      </div>
       <button
	    type="button" 
	    onclick="location.href='dietListView?dietWriteDate=${dietWriteDate}&id=${rvo.id}'" 
	     style="background: none; border: 0; cursor: pointer;">
	    <span style="background: #baffda; font-size: 25pt; font-weight: 900;">목록보기</span>
       </button>&nbsp;&nbsp;
   </div>
   <div class="footer">
      <jsp:include page="./footerAfter.jsp"></jsp:include>
   </div>
  </div>
</body>
</html>