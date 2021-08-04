<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">

	<form action="/auth/loginProc" method="post">
	  <div class="form-group">
	    <label for="username">Username</label>
	    <input type="text" class="form-control" placeholder="Enter username" id="username" name="username">
	  </div>
	  <div class="form-group">
	    <label for="password">Password</label>
	    <input type="password" class="form-control" placeholder="Enter password" id="password" name="password">
	  </div>                                    

	  <button class="btn btn-primary" id="btn-login">로그인</button>
	  <a href="#"><img src="/image/kakao_login_button.jpg" height="38px" /></a>
	</form>

</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>