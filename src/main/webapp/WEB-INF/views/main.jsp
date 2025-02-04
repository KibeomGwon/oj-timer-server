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
        제목 : <input type="text" name="title" value="${param.title}">
        site :
        <div class="select-container">
        <select name="site" id="site-select-box">
            <option value="">none</option>
        <c:forEach var="site" items="${sites}">
            <option value="${site}">${site}</option>
        </c:forEach>
        </select>
        </div>

        language :
        <div class="select-container">
        <select name="language" id="language-select-box">
            <option value=""></option>
        </select>
        </div>

        level :
        <div class="select-container">
        <select name="level"  id="level-select-box">
            <option value=""></option>
        </select>
        </div>
        <input type="submit" value="확인">
    </form>
</div>

<div>
    <table id="submission-table">
        <tr>
            <th>이메일</th>
            <th>제출 번호</th>
            <th>제목</th>
            <th>문제 아이디</th>
            <th>사이트</th>
            <th>언어</th>
            <th title="제출 경과 시간">제출 경과 시간</th>
            <th>레벨</th>
        </tr>
        <c:forEach var="submission" items="${paging.content}" varStatus="st">
            <tr>
                <td>${submission.email}</td>
                <td title="${submission.elementId}">${submission.elementId}</td>
                <td title="${submission.title}"><a href="${submission.link}">${submission.title}</a></td>
                <td>
                    <a href="<c:url value='/${submission.problemId}?username=${submission.username}'/>">${submission.problemId}</a>
                </td>
                <td title="${JstlUtil.siteEnToKr(submission.site)}">${JstlUtil.siteEnToKr(submission.site)}</td>
                <td>${submission.language}</td>
                <td>${JstlUtil.getTimeDiff(submission.recentSubmissionTime)}</td>
                <td>${submission.level}</td>
            </tr>
        </c:forEach>
    </table>
<div>
    <ul id="page-index">
        <li id="previous-button">
            <c:choose>
                <c:when test="${paging.hasPrevious()}">
                    <a href="<c:url value="/?title=${param.title}&site=${param.site}&language=${param.language}&level=${param.level}&page=${paging.number - 1}"/>">
                        <span>이전</span>
                    </a>
                </c:when>
                <c:otherwise>
                    <span>이전</span>
                </c:otherwise>
            </c:choose>

        </li>
        <c:forEach var="number" begin="0" end="${paging.totalPages - 1}" step="1">
            <li>
                <c:choose>
                    <c:when test="${number == paging.number}">
                        <span class="active">${number + 1}</span>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value="/?title=${param.title}&site=${param.site}&language=${param.language}&level=${param.level}&page=${number}"/>">
                            <span>${number+1}</span>
                        </a>
                    </c:otherwise>
                </c:choose>
            </li>
        </c:forEach>
        <li id="next-button">
            <c:choose>
                <c:when test="${paging.hasNext()}">
                    <a href="<c:url value="/?title=${param.title}&site=${param.site}&language=${param.language}&level=${param.level}&page=${paging.number + 1}"/>">
                        <span>다음</span>
                    </a>
                </c:when>
                <c:otherwise>
                    <span>다음</span>
                </c:otherwise>
            </c:choose>
        </li>
    </ul>

</div>
    <p class="total-elements">푼 문제들 수 : ${paging.totalElements}</p>
</div>

<div class="logout-container">
    <form id="logout-form" method="post" action='<c:url value="/logout"/>'>
        <input type="submit" value="로그아웃">
    </form>
</div>

<script>
    window.onload = () => {
        let siteSelect = document.getElementById("site-select-box");
        siteSelect.addEventListener('change', addLanguageSelect);
        let languageSelect = document.getElementById("language-select-box");
        languageSelect.addEventListener('change', addLevelSelect);

        addLanguageSelect();
        addLevelSelect();
    }

    function addLanguageSelect() {
        const value = getValue("site-select-box");

        const list = [];

        let lang;

        <c:forEach var="objects" items="${selectObjects}">
        lang = `${objects.language}`;
        if (value === "none") {
            if (!list.includes(lang)) {
                list.push(lang);
            }
        } else {
            if (value === `${objects.site}`) {
                if (!list.includes(lang)) {
                    list.push(lang);
                }
            }
        }
        </c:forEach>

        updateSelectOptions("language-select-box", list);
    }

    function addLevelSelect() {
        const value = getValue("language-select-box");

        const list = [];

        let level;

        <c:forEach var="objects" items="${selectObjects}">
        level = `${objects.level}`;

        if (value === "none") {
            if (!list.includes(level)) {
                list.push(level);
            }
        } else {
            if (value === `${objects.language}`) {
                if (!list.includes(level)) {
                    list.push(level);
                }
            }
        }
        </c:forEach>

        updateSelectOptions("level-select-box", list);
    }


    function updateSelectOptions(selectId, newOptions) {
        const select = document.getElementById(selectId);

        select.innerHTML = "";

        const newOption = document.createElement("option");
        newOption.value = "";
        newOption.textContent = "none";
        select.appendChild(newOption);

        newOptions.forEach(option => {
            const newOption = document.createElement("option");
            newOption.value = option;
            newOption.textContent = option;
            select.appendChild(newOption);
        });
    }

    function getValue(selectId) {
        const sel = document.getElementById(selectId);
        return text= sel.options[sel.selectedIndex].text;
    }

</script>
</body>
</html>