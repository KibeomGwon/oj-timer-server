<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import="com.oj_timer.server.utils.JstlUtil" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css-files/main.css" rel="stylesheet" type="text/css">
    <title>Document</title>
</head>
<body>

<h1>메인 페이지</h1>
<div>
    <form method="get" action="/">
        username : <input type="text" name="username" value="${param.username}">
        site : <input type="text" name="site" value="${param.site}">
        <input type="submit" value="확인">
    </form>
</div>

<div>
    <table id="submission-table">
        <tr>
            <th>이메일</th>
            <th>유저</th>
            <th>제출 번호</th>
            <th>제목</th>
            <th>문제 아이디</th>
            <th>사이트</th>
            <th>제출 경과 시간</th>
            <th>레벨</th>
        </tr>
        <c:forEach var="submission" items="${content}" varStatus="st">
            <tr>
                <td>${submission.email}</td>
                <td>${submission.username}</td>
                <td>${submission.elementId}</td>
                <td><a href="${submission.link}">${submission.title}</a></td>
                <td>
                    <a href="<c:url value='/${submission.problemId}?username=${submission.username}'/>">${submission.problemId}</a>
                </td>
                <td>${JstlUtil.siteEnToKr(submission.site)}</td>
                <td>${JstlUtil.getTimeDiff(submission.recentSubmissionTime)}</td>
                <td>${submission.level}</td>
            </tr>
        </c:forEach>
    </table>
    <p class="total-elements">푼 문제들 수 : ${totalElements}</p>
</div>

<div>
    <ul id="page-index">



    </ul>
</div>

</body>
</html>