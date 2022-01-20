<%@ page import="java.text.DateFormat" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<jsp:useBean id="storage" scope="request" type="ru.javawebinar.topjava.storage.Storage"/>
<jsp:useBean id="action" scope="request" type="java.lang.String"/>

<form method="post" id="mealForm" name="mealForm" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="id" value="${meal.id}">
    <dl>
        <dt>Date:</dt>
        <dd><input type="text" name="date" size=30 value="${TimeUtil.toString(meal.dateTime)}"
                   placeholder="yyyy-MM-dd HH:mm"></dd>

        <dt>Description:</dt>
        <dd><input type="text" name="description" size=50 value="${meal.description}"></dd>

        <dt>Calories:</dt>
        <dd><input type="text" name="calories" size=50 value="${meal.calories}"></dd>
    </dl>

    <button type="submit">Save</button>
    <button onclick="window.history.back()">Cancel</button>
</form>
</body>
</html>
