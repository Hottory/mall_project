<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="../../../temp/bootStrap.jsp"></jsp:include>
<script src="https://cdn.ckeditor.com/4.10.1/standard/ckeditor.js"></script>
<style type="text/css">
.write {
	padding: 20px;
	max-width: 1500px;
}
</style>
<script type="text/javascript">
$(function() {
	$("#btn").click(function() {
		var title = $("#title").val();
		if (title != "") {
			$("#frm").submit();
		} else {
			alert("제목을 입력하세요");
		}
	})
})
</script>
<c:import url="../../../temp/bootStrap.jsp" />
</head>
<c:import url="../../../temp/header.jsp" />
<body id="myPage" data-spy="scroll" data-target=".navbar"
	data-offset="60">

	<jsp:include page="../../../temp/header.jsp"></jsp:include>

	<div class="container-fluid wrapper write">
		<div class="row write">
			<form id="frm" action="./${board}Write.do" method="post">
				<div class="form-group">
					<label for="title">Title:</label> <input type="text"
						class="form-control" id="title" placeholder="Enter Title"
						name="title">
				</div>
				<div class="form-group">
					<label for="writer">Writer:</label> <input type="text"
						class="form-control" id="writer" name="writer"
						value="${member.name}" readonly="readonly">
				</div>
				
				<div class="form-group">
					<label for="contents">Contents:</label>
					<textarea rows="15" cols="" class="form-control" name="contents"></textarea>
				</div>

				<script>
					CKEDITOR.replace('contents');
				</script>
		
				<input type="button" id="btn" class="btn btn-default" value="등록">
			</form>
		</div>
	</div>


	<c:import url="../../../temp/footer.jsp" />

</body>
</html>