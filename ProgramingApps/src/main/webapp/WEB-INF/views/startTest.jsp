<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head><title>Start Test</title></head>
<body>
<h2>Enter Your Details</h2>
<form:form action="/begin-test" method="post" modelAttribute="candidate" >
    <div>
        <form:label path="name">Name</form:label>
        <form:input path="name"/>
        <form:errors path="name" cssClass="error"/>
    </div>
    <div>
        <form:label path="email">Email</form:label>
        <form:input path="email"/>
        <form:errors path="email" cssClass="error"/>
    </div>
    <div>
        <form:label path="mobile">Mobile</form:label>
        <form:input path="mobile"/>
        <form:errors path="mobile" cssClass="error"/>
    </div>
    <button type="submit">Start Test</button>
</form:form>
</body>
</html>
