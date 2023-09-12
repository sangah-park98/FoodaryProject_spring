<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>음식목록</title>
<!-- bootstrap -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="./js/dietUpdate.js" defer="defer"></script>
<link rel="stylesheet" href="./css/threeGrid.css"/>
</head>
<body onload = "showNutrient()">
<div class="container">
      <div class="header">
      <jsp:include page="./headerAfter.jsp"></jsp:include>
   </div>
   <div class="main" style="text-align: center;" align="center">
		<%
		   request.setCharacterEncoding("UTF-8");
		
		   // diet 데이터
		   String dietWriteDate = request.getParameter("dietWriteDate");
		   String dietWriteTime = request.getParameter("dietWriteTime");
		   String dietMemo = request.getParameter("dietMemo");
		
		   // userfood 데이터
		   String userFoodName = request.getParameter("userFoodName");
		   String userKcal = request.getParameter("userKcal");
		   String userCarbs = request.getParameter("userCarbs");
		   String userProtein = request.getParameter("userProtein");
		   String userFat = request.getParameter("userFat");
		
		   	//food 데이터
		   	 String[] foodNames;
		      String[] kcals;
		      String[] carbs;
		      String[] proteins;
		      String[] fats;
		   
		       // 요청 파라미터에서 데이터 가져오기
		       foodNames = request.getParameterValues("foodName");
		       kcals = request.getParameterValues("kcal");
		       carbs = request.getParameterValues("carbs");
		       proteins = request.getParameterValues("protein");
		       fats = request.getParameterValues("fat");
		
		       if (foodNames == null) {
		           foodNames = new String[]{};
		           kcals = new String[]{};
		           carbs = new String[]{};
		           proteins = new String[]{};
		           fats = new String[]{};
		      } else {
			       // 세션에 데이터 저장
			       session.setAttribute("foodNames", foodNames);
			       session.setAttribute("kcals", kcals);
			       session.setAttribute("carbs", carbs);
			       session.setAttribute("proteins", proteins);
			       session.setAttribute("fats", fats);
		      }
				if (session.getAttribute("foodNames") != null) {
				    // 세션으로부터 데이터 가져오기
				    foodNames = (String[]) session.getAttribute("foodNames");
				    kcals = (String[]) session.getAttribute("kcals");
				    carbs = (String[]) session.getAttribute("carbs");
				    proteins = (String[]) session.getAttribute("proteins");
				    fats = (String[]) session.getAttribute("fats");
				    
				    request.setAttribute("foodNames", foodNames);
					request.setAttribute("kcals", kcals);
					request.setAttribute("carbs", carbs);
					request.setAttribute("proteins", proteins);
					request.setAttribute("fats", fats);
				}
				String userFoodDate = request.getParameter("userFoodDate");
				String userFoodTime = request.getParameter("userFoodTime");
			    request.setAttribute("userFoodDate", userFoodDate);
			    request.setAttribute("userFoodTime", userFoodTime); 
		%>
		
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
		
		<%--       <input type="hidden" id="userFoodDate" name="userFoodDate" value="${userFoodDate}" />
		      <input type="hidden" id="userFoodTime" name="userFoodTime" value="${userFoodTime}" /> --%>
		<span style="background: #fafcd9; font-size: 50pt; font-weight: 900;">푸드어리 수정</span>
		<form accept-charset="UTF-8" action="dietViewUpdateOK.jsp" method="post">
		   <c:set var="list" value="${userFoodList.list}"/>
			<div class="diet">
		   <table width="1400" align="center" border="0" cellpadding="10" cellspacing="10">
			      <!-- 1 -->
			      <tr>
			         <td colspan="5" class="text-center">
			         	<span style="background: #baffda; font-size: 30px; font-weight: 900;">일시</span>: &nbsp;
		               <input type="Date" id="userFoodDate" name="userFoodDate" value="${list[0].userFoodDate}" style="width: 30%; height: 90%;"/>       
		               <input type="Time" id="userFoodTime" name="userFoodTime" value="${list[0].userFoodTime}" style="width: 30%; height: 90%;"/>       
		           </td>
				</tr>     
		         <tr style="height: 30px;"></tr>
		         <tr>
		         <td colspan="5" class="text-center" style="font-size: 13px;">
		         	<button  type="button" onclick="foodList()" style="background: none; border: 0; cursor: pointer;">
                 	<span style="background: #baffda; font-size: 35px; font-weight: 900;">음식 검색하러 가기</span>
                	</button>
		         </td>
		      </tr>
		      <tr style="height: 30px;"></tr>
		      </table>
		     <table width="1400" align="center" border="0" cellpadding="0" cellspacing="10">
		      <tbody id="tableBody">
			      <!-- 3 -->
			      <c:set var="foodNames" value="${requestScope.foodNames}" />
			      <c:set var="kcals" value="${requestScope.kcals}" />
			      <c:set var="carbs" value="${requestScope.carbs}" />
			      <c:set var="proteins" value="${requestScope.proteins}" />
			      <c:set var="fats" value="${requestScope.fats}" />
			      <c:if test="${not empty foodNames}">
			      <c:forEach var="index" begin="0" end="${fn:length(foodNames) - 1}">
		      <tr>
				<td>
	               <input type="text" id="dietFoodName" name="dietFoodName" value="${foodNames[index]}" style="border: 0; font-size: 25pt; width: 150px; font-weight: 900;"/>
	            </td>
	            <td>
	              <span style="background: lavender; font-size: 25pt;">칼로리:</span>
	            </td>
	            <td>
	               <input type="text" id="dietKcal" name="dietKcal" value="${kcals[index]}" style="border: 0; font-size: 25pt; width: 80px;"/> kcal  
	            </td>
	            <td>
	                <span style="background: lavender; font-size: 25pt;">탄수화물:</span>
	            </td>
	            <td>
	               <input type="text" id="dietCarbs" name="dietCarbs" value="${carbs[index]}" style="border: 0; font-size: 25pt; width: 80px;"/> g   
	            </td>
	            <td>
	               <span style="background: lavender; font-size: 25pt;">단백질:</span>
	            </td>
	            <td>
	              <input type="text" id="dietProtein" name="dietProtein" value="${proteins[index]}" style="border: 0; font-size: 25pt; width: 80px;"/> g     
	            </td>
	            <td>
	                <span style="background: lavender; font-size: 25pt;">지방:</span>
	            </td>
	            <td>
	               <input type="text" id="dietFat" name="dietFat" value="${fats[index]}" style="border: 0; font-size: 25pt; width: 80px;"/> g     
	            </td>
		         <td align="center">
		             <input type="button" value="추가" onclick="foodPlus(<c:out value='${index}' />)"/>
		         </td>
		      </tr>
		      </c:forEach>
		    </c:if>
		    <c:if test="${empty foodNames}">
		     <tr>
		     </tr>
		   </c:if>
		    </tbody>
		    </table>
		    
		      <!-- 4 -->
		     <table width="1400" align="center" border="0" cellpadding="10" cellspacing="10">
		      <tbody id="tableBody">
		     <fmt:requestEncoding value="UTF-8"/>
		      <!-- foodWriteInsert.jsp에서 request 영역에 저장한 foodList에서 1페이지 분량의 글을 꺼내온다. -->
		      <c:set var="list" value="${userFoodList.list}"/>
		      <c:forEach var="uvo" items="${list}" varStatus="status">
		      <tr>
		         	<td>
		             <input type="text" id="userFoodName_${status.index}" name="userFoodName_${status.index}" value="${uvo.userFoodName}"
		             	style="border: 1; font-size: 25pt; width: 200px; font-weight: 900; height: 45px; text-align: center;"/>   
		           </td>
		           <td>
		               <span style="background: lavender; font-size: 25pt;">칼로리:</span>
		           </td>
		           <td class="kcals" id="kcal">
		             <input type="text" id="userKcal_${status.index}" name="userKcal_${status.index}" value="${uvo.userKcal}"
		             	style="border: 1; font-size: 25pt; width: 80px; height: 45px;"/> kcal      
		           </td>
		           <td>
		                <span style="background: lavender; font-size: 25pt;">탄수화물:</span>
		           </td>
		           <td class="carbs">
		             <input type="text" id="userCarbs_${status.index}" name="userCarbs_${status.index}" value="${uvo.userCarbs}"
		             	style="border: 1; font-size: 25pt; width: 80px; height: 45px;"/> g      
		           </td>
		           <td>
		            <span style="background: lavender; font-size: 25pt;">단백질:</span>
		           </td>
		           <td class="proteins">
		             <input type="text" id="userProtein_${status.index}" name="userProtein_${status.index}" value="${uvo.userProtein}"
		             	style="border: 1; font-size: 25pt; width: 80px; height: 45px;"/> g      
		           </td>
		           <td>
		              <span style="background: lavender; font-size: 25pt;">지방:</span>
		           </td>
		           <td class="fats">
		             <input type="text" id="userFat_${status.index}" name="userFat_${status.index}" value="${uvo.userFat}"
		             	style="border: 1; font-size: 25pt; width: 80px; height: 45px;"/> g    
		           </td>
		        <td align="center">
		        	<button type="button" onclick="updateUserFood(${uvo.idx}, ${status.index})" style="border: 0; font-size: 25pt; background: 0;">
                 		<span style="background: #fafcd9; font-size: 25pt; font-weight: 900;">수정</span>
             		</button>
             		&nbsp;
		          <button type="button" 
		          onclick="location.href='dietDeleteFood.jsp?idx=${uvo.idx}&userFoodDate=${userFoodDate}&userFoodName=${uvo.userFoodName}&userFoodTime=${userFoodTime}&userKcal=${uvo.userKcal}&userCarbs=${uvo.userCarbs}&userProtein=${uvo.userProtein}&userFat=${uvo.userFat}'"
		          style="border: 0; font-size: 25pt; background: 0;">
		             <span style="background: #fafcd9; font-size: 25pt; font-weight: 900;">삭제</span>
           		</button>
		        </td>
		      </tr>
		    </c:forEach>
		    </tbody>
		    </table>
		   <table width="1400" align="center" border="0" cellpadding="10" cellspacing="10">
		   <!-- 4 -->
		   <c:set var="list" value="${dietList.list}"/>
			<input type="hidden"  name="dietIdx" value="${list[0].idx}"/>
			<tr style="height: 30px;"></tr>
		   <tr>
		       <td colspan="2" class="text-center">
	            <span style="background: lavender; font-size: 30pt;">메모</span>
	         </td>
	         <td colspan="12">
	            <textarea 
	               id="memo"
	               rows="5" 
	               name="dietMemo" 
	               style="resize: none; width: 80%; height: 75%; vertical-align: middle; padding: 10px;"
	               ></textarea>
	         </td>
		   </tr>
		   <!-- 5 사진 기능 추후 추가-->
		   <tr style="display: none;">
		      <td colspan="3" class="text-center">
		         <label for="totalcalorie">사진첨부</label>
		      </td>
		      <td colspan="10" align="center">
		         <input type="text" id="province" name="province" placeholder="사진파일명" style="width: 95%;"/>   
		      </td>   
		      <td align="center">
		        <input type="button" value="파일선택" onclick="location.href='showDiet.jsp'"/>
		      </td>
		   </tr>
		    </table>
		</div>
		    <!-- 6 -->
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
           <!-- 7 -->
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
		    <!-- 8 -->
		    <tr style="height: 30px;"></tr>
		    <tr>
		       <td colspan="3" class="text-center">
		          <button type="submit" style="background: none; border: 0; cursor: pointer;">
                	 <span style="background: #fafcd9; font-size: 27pt; font-weight: 900;">수정하기</span>
               	 </button>
               	 &nbsp; &nbsp;
		          <button type="submit" style="background: none; border: 0; cursor: pointer;" onclick="location.href='/foodary/JSP_diet/dietList.jsp'">
                	 <span style="background: #fafcd9; font-size: 27pt; font-weight: 900;">돌아가기</span>
                </button>
		       </td>
		    </tr>
		   </table>
		  </form>
  </div>
	<div class="footer">
      <jsp:include page="./footerAfter.jsp"></jsp:include>
   </div>
</div> 
</body>
</html>