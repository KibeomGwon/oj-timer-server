<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 2025-01-17
  Time: 오후 8:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<html>
<head>
    <title>online-judge-timer/register</title>
    <link rel="stylesheet" href="css-files/register.css" type="text/css">
</head>
<body>
  <form method="post" action="<c:url value="/register"></c:url>">

    이메일 : <input type="email", name="email">

  <spring:hasBindErrors name="registerForm">
    <c:if test="${errors.hasFieldErrors('email') }">
      <strong id="error-message">${errors.getFieldError( 'email' ).defaultMessage }</strong>
    </c:if>
  </spring:hasBindErrors>

    비밀번호 : <input type="password" name="password">

  <spring:hasBindErrors name="registerForm">
    <c:if test="${errors.hasFieldErrors('password') }">
      <strong id="error-message">${errors.getFieldError( 'password' ).defaultMessage }</strong>
    </c:if>
  </spring:hasBindErrors>

    휴대폰 번호 : <input type="text" name="phone">

  <spring:hasBindErrors name="registerForm">
    <c:if test="${errors.hasFieldErrors('phone') }">
      <strong id="error-message">${errors.getFieldError( 'phone' ).defaultMessage }</strong>
    </c:if>
  </spring:hasBindErrors>

  <input type="submit" value="확인">

  </form>
  <button onclick="location.href='<c:url value="/"></c:url>'">취소</button>
</body>
</html>
