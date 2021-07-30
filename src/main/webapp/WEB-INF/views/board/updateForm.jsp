<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
	  <input type="hidden" id="id" value="${board.id}" />
	  <div class="form-group">
	    <label for="title">Title:</label>
	    <input type="text" class="form-control" placeholder="Enter title" id="title" value="${board.title}">
	  </div>
	  <div class="form-group">
	    <label for="comment">Content:</label>
  		<textarea class="form-control summernote" rows="5" id="content">${board.content} </textarea>
	  </div>
	</form>
    <button class="btn btn-primary" id="btn-update">글수정완료</button>
</div>

<script>
  $('.summernote').summernote({
    tabsize: 2,
    height: 300
  });
</script>
<script src="/js/board.js"></script>

<%@ include file="../layout/footer.jsp"%>