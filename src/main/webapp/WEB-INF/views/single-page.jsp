<%@ page import="com.oj_timer.server.dto.domain.SubmissionDto" %>
<%@ page import="com.oj_timer.server.utils.JstlUtil" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <title>Title</title>
<%--  <link rel="stylesheet" href="/css-files/single-page.css">--%>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f9;
      margin: 0;
      padding: 20px;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      min-height: 100vh;
    }

    h1 {
      color: #333;
      margin-bottom: 20px;
    }

    .table {
      background-color: #fff;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      margin-bottom: 20px;
      width: 100%;
      max-width: 800px;
    }

    table {
      width: 100%;
      border-collapse: collapse;
    }

    th, td {
      padding: 10px;
      border: 1px solid #ddd;
      text-align: left;
    }

    th {
      background-color: #007bff;
      color: white;
    }

    tr:nth-child(even) {
      background-color: #f9f9f9;
    }

    a {
      color: #007bff;
      text-decoration: none;
    }

    a:hover {
      text-decoration: underline;
    }

    #username {
      margin-top: 20px;
      padding: 10px;
      background-color: #fff;
      border-radius: 8px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      position: absolute;
      top: 20px;
      left: 20px;
    }

    #back-button {
      padding: 10px 20px;
      background-color: #007bff;
      color: white;
      border: none;
      border-radius: 8px;
      cursor: pointer;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      transition: background-color 0.3s;
    }

    #back-button:hover {
      background-color: #0056b3;
    }
  </style>
</head>

<script type="text/javascript">

  document.addEventListener("DOMContentLoaded", function() {
    let originalId = "<c:out value="${problem.problemTitleId}"></c:out>";

    const site = "<c:out value="${problem.site}"></c:out>";

    if (site === "baekjoon") {
      originalId = originalId.replace("baekjoon", "");
    }

    document.getElementById("problem-original-id").innerHTML = originalId;

    const path = "<c:out value="${pageContext.request.contextPath}"></c:out>";

    console.log(path);
  });
</script>
<body>

<%--<%--%>
<%--  ProblemDto problemDto = (ProblemDto) request.getAttribute("problem");--%>
<%--  String orignalId = null;--%>
<%--  if (problemDto.getSite().equals("baekjoon")) {--%>
<%--    orignalId = problemDto.getProblemTitleId().replace("baekjoon", "");--%>
<%--    System.out.println(orignalId);--%>
<%--  }--%>
<%--값 사용 법--%>
<%--<%= originalId %>--%>
<%--%>--%>
<%
  List<SubmissionDto> submissions = (List<SubmissionDto>) request.getAttribute("submissions");
  String username = submissions.get(0).getUsername();
%>

  <h1><c:out value="${problem.title}" default="문제 제목"></c:out></h1>
  <div class="table" id="problem-table">
    <table id="problem-info-table">
      <tr>
        <th>번호</th>
        <th>제목</th>
        <th>난이도</th>
        <th>사이트</th>
      </tr>
      <tr>
        <td><a href="${problem.link}" id="problem-original-id"></a></td>
        <td><c:out value="${problem.title}"></c:out></td>
        <td><c:out value="${problem.level}"></c:out></td>
        <td><c:out value="${JstlUtil.siteEnToKr(problem.site)}"></c:out></td>
      </tr>
    </table>
  </div>

  <div id="username">user : <%= username %></div>

  <div class="table">
    <table id="submission-table">
      <tr>
        <th>제출 번호</th>
        <th>제출 일자</th>
        <th>경과 시간</th>
      </tr>
      <c:forEach var="submission" items="${submissions}">
        <tr>
          <td><c:out value="${submission.elementId}"></c:out></td>
          <td><c:out value="${submission.submissionTimeId.toLocalDate()}"></c:out></td>
          <td><c:out value="${JstlUtil.getTimeDiff(submission.submissionTimeId)}"></c:out></td>
        </tr>
      </c:forEach>
    </table>
  </div>

  <button id="back-button" onclick="location.href='<c:url value='/?title=${param.title}&site=${param.site}&language=${param.language}&level=${param.level}'/>'"> 뒤로가기 </button>
</body>
</html>
