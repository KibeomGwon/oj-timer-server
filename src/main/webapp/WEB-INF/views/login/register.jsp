<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 2025-01-17
  Time: 오후 8:26
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<html>
<head>
    <title>online-judge-timer/register</title>
<%--    <link rel="stylesheet" href="/css-files/register.css" type="text/css">--%>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f9;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
      margin: 0;
    }

    .register-container {
      background: white;
      padding: 30px;
      border-radius: 10px;
      box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
      width: 400px;
      text-align: center;
    }

    h2 {
      margin-bottom: 20px;
      color: #333;
    }

    input {
      width: 100%;
      padding: 10px;
      margin: 10px 0;
      border: 1px solid #ccc;
      border-radius: 5px;
      font-size: 14px;
    }

    input:focus {
      border-color: #007BFF;
      outline: none;
    }

    #error-message {
      color: red;
      font-size: 12px;
      margin-top: -8px;
      display: block;
    }

    button {
      width: 100%;
      padding: 10px;
      background: #007BFF;
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      margin-top: 10px;
      font-size: 16px;
    }

    button:hover {
      background: #0056b3;
    }

    .cancel-btn {
      background: #dc3545;
    }

    .cancel-btn:hover {
      background: #c82333;
    }

    #email-validate-number-box {
      margin-top: 10px;
    }

    #email-is-validate {
      color: red;
      font-size: 12px;
    }
  </style>
</head>
<body>
<div class="register-container">

  <h2>회원가입</h2>
  <label for="input-email">이메일</label>
  <input id="input-email" type="email" name="email" placeholder="이메일을 입력하세요">

  <spring:hasBindErrors name="registerForm">
    <c:if test="${errors.hasFieldErrors('email') }">
      <strong id="error-message">${errors.getFieldError( 'email' ).defaultMessage }</strong>
    </c:if>
  </spring:hasBindErrors>

  <button id="email-validate">인증번호 요청</button>

  <div id="email-validate-number-box" style="display: none">
    <input id="email-validate-number-input" type="text" placeholder="인증번호를 입력하세요"/>
    <label id="email-is-validate" style="visibility: hidden">일치하지 않습니다.</label>
  </div>

  <label for="input-password">비밀번호</label>
  <input id="input-password" type="password" name="password" placeholder="비밀번호를 입력하세요">

  <spring:hasBindErrors name="registerForm">
    <c:if test="${errors.hasFieldErrors('password') }">
      <strong id="error-message">${errors.getFieldError( 'password' ).defaultMessage }</strong>
    </c:if>
  </spring:hasBindErrors>

    <form method="post" action="<c:url value="/register"></c:url>">
      <input id="real-input-email" type="email", name="email" style="display: none">
      <input id="real-input-password" type="password" name="password" style="display: none">
      <input id="input-submit" type="submit" value="확인" disabled>
  </form>
  <button class="cancel-btn" onclick="location.href='<c:url value='/'/>'">취소</button>
</div>

<script>
  window.onload = () => {
    const emailRequestButton = document.getElementById("email-validate");
    emailRequestButton.onclick = EmailValidateButtonEvent;

    const numberInput = document.getElementById('email-validate-number-input');
    numberInput.oninput = changeInputNumberEvent;

    const passwordInput = document.getElementById('input-password');
    passwordInput.oninput = changeInputPasswordEvent;
  }

  let number;

  async function EmailValidateButtonEvent() {
    document.getElementById("email-validate-number-box").style.display = ""

    const email = document.getElementById('input-email').value;

    document.getElementById("real-input-email").value = email;

    const data = { mail : email };

    const response = await fetch("http://localhost:8080/api/mail", {
      method : "post",
      headers : {
        "Content-type" : "application/json"
      },
      body : JSON.stringify(data)
    });

    number = await response.json();
  }

  function changeInputNumberEvent() {
    let inputNumber = document.getElementById('email-validate-number-input').value;
    const label = document.getElementById("email-is-validate");
    const submit = document.getElementById("input-submit");

    inputNumber = parseInt(inputNumber);
    console.log(typeof inputNumber)

    label.style.visibility = "";

    if (inputNumber !== number) {
      label.innerHTML = "일치하지 않습니다.";
      label.style.color = "red";
      submit.disabled = true;
      submit.style.background = "#EFEFEF4D";
      submit.style.color = "#1010104D";

    } else {
      label.innerHTML = "일치합니다.";
      label.style.color = "black";
      submit.disabled = false;
      submit.style.background = "#007BFF";
      submit.style.color = "white";
    }
  }

  function changeInputPasswordEvent() {
    const value = document.getElementById("input-password").value;
    document.getElementById("real-input-password").value = value;
  }
</script>
</body>
</html>
