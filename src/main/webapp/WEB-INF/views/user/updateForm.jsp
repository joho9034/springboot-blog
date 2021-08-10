<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">

	<form>
	  <input type="hidden" id="id" value="${principal.user.id}" />
	  <div class="form-group">
	    <label for="username">Username:</label>
	    <input type="text" class="form-control" placeholder="Enter username" id="username" value="${principal.user.username}" readonly>
	  </div>

	  <c:if test="${empty principal.user.oauth}">
		  <div class="form-group">
		    <label for="password">Password:</label>
		    <input type="password" class="form-control" placeholder="Enter password" id="password">
		  </div>
	  </c:if>

	  <div class="form-group">
	    <label for="email">Email address:</label>
	    <input type="email" class="form-control" placeholder="Enter email" id="email" value="${principal.user.email}" <c:if test="${not empty principal.user.oauth}">readonly</c:if>>
	  </div>
	  
	</form>
	<button class="btn btn-primary" id="btn-update">회원수정완료</button>

</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>