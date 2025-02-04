<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 2025-01-17
  Time: 오후 8:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
    <title>oline-judge-timer/login</title>
    <link rel="stylesheet" href="css-files/login.css" type="text/css">
</head>
<body>
    <h1>로그인</h1>
    <form action="/login" method="post">
      이메일 : <input type="email" name="email" value="${loginForm.email}"/>
      비밀번호 : <input type="password" name="password" value="${loginForm.password}">
      <input type="submit" value="로그인"/>
      <button type="button" onclick="location.href='<c:url value="/register"></c:url>'">회원가입</button>

    </form>

    <spring:hasBindErrors name="loginForm">
        <c:if test="${not empty errors.globalErrors}">
            <script>
                <c:forEach var="error" items="${errors.globalErrors}">
                alert("<c:out value='${error.defaultMessage}'/>");
                </c:forEach>
            </script>
        </c:if>
    </spring:hasBindErrors>


</body>
</html>
