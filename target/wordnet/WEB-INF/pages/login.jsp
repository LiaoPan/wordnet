<%--
  Created by IntelliJ IDEA.
  User: Wang Haobo
  Date: 2016/9/4
  Time: 11:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Login</title>
</head>
<body>
<!-- 输出错误提示信息 -->
<span style="color:red; font-weight:bold">
<%
    if(request.getAttribute("err")!=null){
        out.println(request.getAttribute("err") + "<br/>");
    }
%>
</span>

请输入用户名和密码：
<form id="login" method="post" action="login">
    用户名：<input type="text" name="id" /><br/>
    密码&nbsp;<input type="password" name="password" /><br/>
    <input type="submit" value="登陆"/><br/>
</form>
<a href="register">注册用户</a>
</body>
</html>
