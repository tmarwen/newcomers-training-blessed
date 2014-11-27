<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>


<html>
<head>
    <title>EventsJSON</title>
    <style type="text/css">
        .container {
            margin: 20px;
            padding: 20px
        }
    </style>
</head>

<body>

<div id="json-events-wrapper" class="container">

    <h5>Upcoming Events but through JSON Style ;) :</h5>

    <table id="events-table">
        <tr id="events-table-header">
            <th>Event Name</th>
            <th>Year</th>
            <th>Mont</th>
            <th>Hour</th>
            <th>Minute</th>
            <th>Second</th>
        </tr>
    </table>
</div>

<div class="container">
    <portlet:renderURL var="listLegacyEventsURL" />

    <a href="<%= listLegacyEventsURL %>"><span>Back to Home Page</span></a>
</div>

</body>
</html>
