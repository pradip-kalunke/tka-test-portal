<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>Update Answers</title>
<link rel="stylesheet" href="/css/style.css"/>
</head>

<body>
<h2>Update Answers for ${candidate.name} (CID: ${candidate.cid})</h2>

	<form action="/moderator/save-updated-answers" method="post">
	    <input type="hidden" name="cid" value="${candidate.cid}"/>

	    <c:forEach var="ans" items="${answers}" varStatus="i">
	        <div class="answer-box">
	            <label><b>Q.${i.index + 1} — ${ans.questionText}</b></label><br/>

	            <!-- Candidate Answer -->
	            <textarea name="ans_${ans.qid}" rows="5">${ans.answerText}</textarea>

	            <!-- Marks Dropdown -->
	            <br/>
	            <label><b>Marks:</b></label>
	            <select name="mark_${ans.qid}" class="mark-select">
	                <option value="0">0</option>
	                <option value="5">5</option>
	                <option value="10">10</option>
	            </select>
	        </div>
	    </c:forEach>

	    <button type="submit" class="btn-save">Save Updates</button>
	</form>

	</body>
	</html>