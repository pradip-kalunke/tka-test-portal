<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head><title>Test Page</title></head>
<body>
<h2>Logical Programming Test Page </h2>

<form action="/submit-test" method="post">
    <input type="hidden" name="cid" value="${cid}"/>

    <c:forEach var="q" items="${questions}" varStatus="i">
        <div style="margin:20px 0;border:1px solid #ccc;padding:10px;">
			 <label><b>Q. ${i.index + 1}. ${q.questionText} </b></label><br/>
            <textarea name="q${q.qid}" rows="8" cols="80" placeholder="Write your answer (minimum 5 Lines Code)"></textarea>
        </div>
    </c:forEach>

    <button type="submit">Submit Test</button>
</form>
</body>
</html>
