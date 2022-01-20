<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meal List</title>
</head>
<body>
<h2>Meal List</h2>
<a href="?action=add">Add meal</a>

<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        <jsp:useBean id="mealsTo" scope="request" type="java.util.List"/>
        <c:forEach items="${mealsTo}" var="mealTo">
            <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr style="color: <%= mealTo.isExcess()? "red" : "green" %>">
                <td>${TimeUtil.toString(mealTo.dateTime)}</td>
                <td>${mealTo.description}</td>
                <td>${mealTo.calories}</td>

                <td><a href="?id=${mealTo.id}&action=update">Update</a></td>
                <td><a href="?id=${mealTo.id}&action=delete">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
