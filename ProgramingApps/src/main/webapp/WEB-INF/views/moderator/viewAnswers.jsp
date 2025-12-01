<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Answers - ${candidate.name}</title>
    <link rel="stylesheet" href="/css/style.css"/> 
</head>

<body>

<h2>Answers for ${candidate.name} (CID: ${candidate.cid})</h2>

<!-- Flash Messages -->
<c:if test="${not empty flash.error}">
    <div class="alert error">${flash.error}</div>
</c:if>

<c:if test="${not empty msg}">
    <div class="alert success">${msg}</div>
</c:if>

<!-- Questions + Answers Table -->
<table class="answers-table">
    <thead>
        <tr>
            <th>QID</th>
            <th>Question</th>
            <th>Expected Keywords</th>
            <th>Answer</th>
            <th>Check-Answer</th>
        </tr>
    </thead>
	<tbody>
	    <c:forEach var="q" items="${qaList}">
	        <tr>
	            <td>${q.qid}</td>
	            <td>${q.questionText}</td>
	            <td>${q.expectedKeywords}</td>

	            <td>
	                <c:choose>
	                    <c:when test="${empty q.answerText}">
	                        <span class="no-answer">--- No Answer Submitted ---</span>
	                    </c:when>
	                    <c:otherwise>
	                        ${q.answerText}
	                    </c:otherwise>
	                </c:choose>
	            </td>

	            <td>
	                <a href="/moderator/update-answers/${candidate.cid}" class="btn-update">
	                    Check Answers
	                </a>
	            </td>
	        </tr>
	    </c:forEach>
	</tbody>

</table>


<!-- Draft Result Section -->
<h3>Draft / Update Result</h3>

<form action="/moderator/draft-result" method="post" class="form-box">
    <input type="hidden" name="cid" value="${candidate.cid}"/>

    <div class="form-row">
        <label>Calculated Score:</label>
        <input type="number" name="score"
               value="${result.score}"
               min="0"
               max="${result.totalQuestions}">
    </div>

    <div class="form-row">
        <label>Status:</label>
        <select name="status">
            <option value="PENDING" ${result.status == 'PENDING' ? 'selected' : ''}>PENDING</option>
            <option value="PASS" ${result.status == 'PASS' ? 'selected' : ''}>PASS</option>
            <option value="FAIL" ${result.status == 'FAIL' ? 'selected' : ''}>FAIL</option>
        </select>
    </div>

    <div class="form-row">
        <label>Drafted By:</label>
        <input type="text" name="draftedBy" value="moderator">
    </div>

    <button type="submit" class="btn primary">Save Draft</button>
</form>


<!-- Final Status Section -->
<h3>Finalize Status</h3>

<form action="/moderator/update-status" method="post" class="form-box">
    <input type="hidden" name="cid" value="${candidate.cid}"/>

    <div class="form-row">
        <label>Final Status:</label>
        <select name="status">
            <option value="PASS">PASS</option>
            <option value="FAIL">FAIL</option>
        </select>
    </div>

    <button type="submit" class="btn success">Update Status</button>
</form>

<div class="back-link">
    <a href="/moderator/candidates">Back to Candidates</a>
</div>

</body>
</html>
