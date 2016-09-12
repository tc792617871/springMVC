<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户信息列表</title>
</head>
<body>
	<center>
		<table border="10px solid black">
			<tr>
				<td>用户ID</td>
				<td>用户账号</td>
				<td>用户密码</td>
				<td>用户年龄</td>
			</tr>
			<c:forEach items="${users}" var="user">
				<tr>
					<td align="center">${user.userid}</td>
					<td align="center">${user.username}</td>
					<td align="center">${user.password}</td>
					<td align="center">${user.age}</td>
				</tr>
			</c:forEach>
		</table>
	</center>
</body>
</html>