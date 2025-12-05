<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>Candidate Answers</title>
<link rel="stylesheet" href="/css/moderator.css"/>
</head>

<body>

<h2>Answers of ${candidate.name} (CID: ${candidate.cid})</h2>

<table class="answers-table">
    <tr>
        <th>QID</th>
        <th>Question</th>
        <th>Expected Keyword</th>
        <th>Candidate Answer</th>
        <th>Marks</th>
    </tr>

    <c:forEach var="qa" items="${qaList}">
        <tr>
            <td>${qa.qid}</td>
            <td>${qa.questionText}</td>
            <td>${qa.expectedKeywords}</td>
            <td>${qa.answerText}</td>
            <td>${qa.marks}</td>
        </tr>
    </c:forEach>
</table>

<h3>Total Score: <span class="green">${calculatedScore}</span></h3>

<br/>

<a href="/moderator/update-answers/${candidate.cid}" class="btn-edit">Edit Answers</a>
<a href="/moderator/" class="btn-edit">Back to Candidate List</a>

</body>
</html>
