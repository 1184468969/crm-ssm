<%--
  Created by IntelliJ IDEA.
  User: 86157
  Date: 2021/4/6
  Time: 20:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>
<html>
<head>
    <title>Title</title>
    <base href="<%=basePath%>"/>
</head>
<body>

String createTime = DateTimeUtil.getSysTime();
String createBy = ((User)request.getSession().getAttribute("user")).getName();
</body>
</html>
