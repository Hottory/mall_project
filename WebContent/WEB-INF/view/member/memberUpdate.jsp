<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="../../../temp/bootStrap.jsp" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/memberinit.js"></script>
</head>
<body>
	<c:import url="../../../temp/header.jsp" />
	<div class="container-fluid wrapper">
		<div class="row">
			<form action="./memberUpdate.do" method="post" name="frm">
				<div class="form-group">
					<label for="id">ID:</label> <input type="email"
						class="form-control" value="${member.id}" name="id"
						readonly="readonly">
				</div>
				<div class="form-group">
					<label for="pw1">PASSWORD:</label> <input type="password"
						class="form-control" id="pw1" name="pw1">
				</div>
				<div class="form-group">
					<label for="pw2">PASSWORD:</label> <input type="password"
						class="form-control" id="pw2" name="pw2">
						<span></span>
				</div>
				<div class="form-group">
					<label for="name">이름:</label> <input type="text"
						class="form-control" value="${member.name}" name="name">
						<span></span>
				</div>
				<div class="form-group">
					<label for="phone">연락처:</label> <input type="text"
						class="form-control" value="${member.phone}" name="phone">
						<span></span>
				</div>
				<div class="form-group">
					<label for="address">주소:</label> <input type="text"
						class="form-control" value="${member.address}" name="address">
				</div>
				<input type="button" class="btn btn-default" value="수정 " id="btn">
			</form>
		</div>
	</div>
	<c:import url="../../../temp/footer.jsp" />
</body>
</html>