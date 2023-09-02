<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 보기</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<link rel="stylesheet" href="../css/threeGrid.css"/>
<script type="text/javascript" src="../js/setting.js" defer="defer"></script>
<script defer type="text/javascript" src="../js/FreeboardComment.js"></script>
</head>
<body>
<div class="container">
	<div class="header">
	   <jsp:include page="./headerAfter.jsp"></jsp:include>
	</div>
	<div class="main">
		<table cellspacing="10" style="width: 700px; margin-left: auto; margin-right: auto; margin-top: 0px;">
			<tr>
				<th colspan="4" style="text-align: center;"><span style="background: #baffda; font-size: 40px;"><게시글 보기></span></th>
			</tr>
			<tr style="height: 30px;"></tr>
			<tr style="font-weight: 800;">
				<td style="width: 100px; text-align: center;">
					<span style="background: lavender; font-size: 22pt;">No.</span>
				</td>
				<td style="width: 350px; text-align: center;">
					<span style="background: lavender; font-size: 22pt;">이름</span>
				</td>
				<td style="width: 150px; text-align: center;">
					<span style="background: lavender; font-size: 22pt;">작성일</span>
				</td>
				<td style="width: 100px; text-align: center;">
					<span style="background: lavender; font-size: 22pt;">조회수</span>
				</td>
			</tr>
			<tr>
				<td align="center" style="font-size: 22pt;">${vo.idx}</td>
				<td align="center" style="font-size: 22pt;">
					<c:set var="name" value="${fn:replace(vo.name, '<', '&lt;')}"/>
					<c:set var="name" value="${fn:replace(name, '>', '&gt;')}"/>
					${name}
				</td>
				<td align="center" style="font-size: 22pt;">
					<jsp:useBean id="date" class="java.util.Date"/>
					<c:if test="${date.year == vo.writeDate.year && date.month == vo.writeDate.month &&
						date.date == vo.writeDate.date}">
						<fmt:formatDate value="${vo.writeDate}" pattern="a h:mm:ss"/>
					</c:if>
					<c:if test="${date.year != vo.writeDate.year || date.month != vo.writeDate.month ||
						date.date != vo.writeDate.date}">
						<fmt:formatDate value="${vo.writeDate}" pattern="yyyy.MM.dd(E)"/>
					</c:if>
				</td>
				<td align="center" style="font-size: 22pt;">${vo.hit}</td>
			</tr>
			<tr style="height: 20px;"></tr>
			<tr>
				<th><span style="background: lavender; font-size: 22pt;">제목</span></th>
			 	<td colspan="3" style="font-size: 22pt;  margin-bottom: 20px;" align="center">
					<c:set var="subject" value="${fn:replace(vo.subject, '<', '&lt;')}"/>
					<c:set var="subject" value="${fn:replace(subject, '>', '&gt;')}"/>
					${subject}
				</td>
			</tr>
			<tr style="padding: 30px;"></tr>
			<tr>
				<th><span style="background: lavender; font-size: 22pt;">내용</span></th>
				<td colspan="3" style="font-size: 22pt; padding: 10px;">
					<c:set var="content" value="${fn:replace(vo.content, '<', '&lt;')}"/>
					<c:set var="content" value="${fn:replace(content, '>', '&gt;')}"/>
					<c:set var="content" value="${fn:replace(content, enter, '<br/>')}"/>
					${content}<br/>
					<c:if test="${not empty vo.picture}">
						<img src="/upload/${vo.picture}" style="max-width: 450px; max-height: 300px;">
					</c:if>
				</td>
			</tr>
			<tr class="table-secondary">
				<td colspan="4" align="center">
					<c:if test="${rvo.id eq vo.id}">
					<button 
						type="button" 
						style="background: none; border: 0; cursor: pointer;"
						onclick="location.href='selectByIdx?idx=${vo.idx}&currentPage=${currentPage}&job=update'">
						<span style="background: #fafcd9; font-size: 25pt;; font-weight: 800;">수정하기</span></button>&nbsp;&nbsp;&nbsp;
					<button
						type="button" 
						style="background: none; border: 0; cursor: pointer;"
						onclick="location.href='selectByIdx?idx=${vo.idx}&currentPage=${currentPage}&job=delete'">
						<span style="background: #fafcd9; font-size: 25pt;; font-weight: 800;">삭제하기</span></button>&nbsp;&nbsp;&nbsp;
					</c:if>
					<button
						type="button" 
						style="background: none; border: 0; cursor: pointer;"
						onclick="location.href='listView?currentPage=${currentPage}'">
						<span style="background: #fafcd9; font-size: 25pt;; font-weight: 800;">목록보기</span></button>
				</td>
			</tr>
		</table>
	<br/>
	<hr style="color: red; width: 700px; margin-left: auto; margin-right: auto;"/>
	<br/>
	<!-- 댓글 폼 -->
		<form action="commentOK" method="post" name="commentForm" onsubmit="return insertCheck()">
			<table cellpadding="5" cellspacing="5" style="width: 700px; margin-left: auto; margin-right: auto;">
				<tr>
					<th colspan="4" style="text-align: center;"><span style="background: #baffda; font-size: 40px;"><댓글 보기></span></th>
				</tr>
			
				<!-- 이 줄은 원래 보이면 안되는 줄로 작업이 완료되면 화면에 표시되지 않게 한다. -->
				<!-- <tr> -->
				<tr style="display: none;">
					<td colspan="4">
						idx: <input type="text" name="idx" value="${vo.idx}" size="1"/>
						gup: <input type="text" name="gup" value="${vo.idx}" size="1"/>
						mode: <input type="text" name="mode" value="1" size="1"/>
						currentPage: <input type="text" name="currentPage" value="${currentPage}" size="1"/>
						ip: <input type="text" name="ip" value="${pageContext.request.remoteAddr}"/>
					</td>
				</tr>
				<tr style="padding: 20px;"></tr>
				<tr style="margin-bottom: 20px; padding: 10px;">
					<th><span style="background: lavender; font-size: 22pt;">이름: </span></th>
					<td style="width: 250px;">
						<input id="name" type="text" name="name" value="${rvo.username}" readonly="readonly"
							style="text-align: center; border-bottom: 1px; border-left: 0; border-right: 0; border-top: 0; font-size: 22pt;"/>
					</td>
				</tr>
				<tr style=""></tr>
				<input id="id" type="hidden" name="id" value="${rvo.id}"/>
				<tr>
					<th><span style="background: lavender; font-size: 22pt;">내용: </span></th>
					<td colspan="3" style="width: 600px;">
						<textarea 
							id="ccontent"
							rows="3"
							name="content" 
							style="resize: none; width: 580px; padding: 5px;"></textarea>
					</td>
				</tr>
				<tr style="margin-bottom: 20px;">
					<td colspan="4" align="center">
						<button 
							type="submit" 
							style="background: none; border: 0; cursor: pointer;"
							name="commentBtn">
							<span name="text" style="background: #fafcd9; font-size: 25pt; font-weight: 800;">댓글저장</span>
						</button>&nbsp;&nbsp;&nbsp;
						<button 
							type="reset" 
							style="background: none; border: 0; cursor: pointer;"
							onclick="setting(0, 1, '댓글저장', '', '')">
							<span style="background: #fafcd9; font-size: 25pt; font-weight: 800;">다시쓰기</span>
						</button>
					</td>
				</tr>
				<tr style="padding: 20px; margin: 20px;"></tr>
				<tr style="padding: 20px; margin: 20px;"></tr>
				<tr style="padding: 20px; margin: 20px;"></tr>
				<tr style="padding: 20px; margin: 20px;"></tr>
				<tr style="padding: 20px; margin: 20px;"></tr>
				<tr style="padding: 20px; margin: 20px;"></tr>
				<tr style="padding: 20px; margin: 20px;"></tr>
				<tr style="padding: 20px; margin: 20px;"></tr>
				<!-- 댓글을 출력한다. -->
				<c:set var="comment" value="${freeboardCommentList.list}"></c:set>
				
				<!-- 댓글이 없는 경우 -->
				<c:if test="${comment.size() == 0}">
				<tr>
					<td colspan="4">
						<marquee>아직 작성된 댓글이 없습니다ㅠㅡㅜ</marquee>
					</td>
				</tr>
				</c:if>
				<!-- 댓글이 있는 경우 -->
				<c:if test="${comment.size() != 0}">
				<c:forEach var="co" items="${comment}">
				<tr>
					<td colspan="4">
						<span style="background: lavender; font-weight: 600;">${co.idx}.
						<c:set var="name" value="${fn:replace(co.name, '<', '&lt;')}"/>
						<c:set var="name" value="${fn:replace(name, '>', '&gt;')}"/>
						${name}
						<fmt:formatDate value="${co.writeDate}" pattern="yyyy.MM.dd(E) HH:mm:ss"/></span>
						<div align="right">
							<!-- 글에 저장되어있는 정보와 현재 로그인중인 회원 정보가 일치하면 아래 버튼 다 나오고 다르면  -->
							<c:if test="${rvo.id eq co.id}">
								<button
									type="button" 
									style="background: none; border: 0; cursor: pointer;"
									onclick="setting(${co.idx}, 2, '댓글수정', '${name}', '${co.content}')">
									<span style="background: #fafcd9; font-size: 20pt;; font-weight: 800;">수정하기</span>
								</button>&nbsp;&nbsp;&nbsp;
								<button
									type="button" 
									style="background: none; border: 0; cursor: pointer;"
									onclick="setting(${co.idx}, 3, '댓글삭제', '${name}', '${co.content}')">
									<span style="background: #fafcd9; font-size: 20pt;; font-weight: 800;">삭제하기</span>
								</button>
							</c:if>
						</div>
						<c:set var="content" value="${fn:replace(co.content, '<', '&lt;')}"/>
						<c:set var="content" value="${fn:replace(content, '>', '&gt;')}"/>
						<c:set var="content" value="${fn:replace(content, enter, '<br/>')}"/>
						${content}<br><br>
					</td>
				</tr>
				<tr></tr><tr></tr><tr></tr><tr></tr>
				</c:forEach>
				</c:if>
			</table>
		</form>
		<br/>
	</div>
	<div class="footer">
		<jsp:include page="./footerAfter.jsp"></jsp:include>
	</div>
</div>
</body>
</html>











