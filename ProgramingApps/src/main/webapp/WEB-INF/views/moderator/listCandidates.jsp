<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html><head><title>Candidates</title><link rel="stylesheet" href="/css/style.css"/></head><body>
	
	 
<h2>Candidates</h2>

<c:if test="${not empty msg}"><div style="color:green">${msg}</div></c:if>
<table border="1">
    <tr><th>CID</th><th>Name</th><th>Actions</th></tr>
    <c:forEach var="c" items="${candidates}">
        <tr>
            <td>${c.cid}</td>
            <td>${c.name}</td>
            <td><a href="/moderator/view-answers/${c.cid}">View Answers</a></td>
        </tr>
    </c:forEach>
</table>
</body></html>
