<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.exoplatform.support.model.beans.Event" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<portlet:defineObjects/>

<%
    List<Event> myEvents = (List<Event>) renderRequest.getAttribute("events");
%>


<html>
<head>
    <title>Events</title>
    <style type="text/css">
        .container {
            margin: 20px;
            padding: 20px
        }
    </style>
</head>

<body>
<div id="events-wrapper" class="container">

    <h6>Upcoming Events:</h6>

    <ol style="list-style: circle">
        <% for (Event event : myEvents)
        { %>
        <li><%= event.getName() + " - " + event.getDate().getTime() %>
        </li>
        <% } %>
    </ol>
</div>

<div id="new-event-wrapper" class="container">
    <h6>Register an Event:</h6>

    <portlet:actionURL var="createEventURL" portletMode="view" />

    <form action="<%= createEventURL %>"
          method="post">
        <label for="event-name">Name: </label><input id="event-name" type="text" name="event-name" />
        <label for="event-date">Date: (yyyy-MM-dd HH:mm:ss)</label><input id="event-date" type="text" name="event-date" />
        <br />
        <input type="submit" value="Create">
    </form>
</div>

<div class="container">

    <portlet:renderURL var="listJSONEventsURL">
        <portlet:param name="isJSONList" value="true" />
    </portlet:renderURL>

    <a href="<%= listJSONEventsURL %>"><span>Get the JSON Style list</span></a>

</div>
</body>
</html>
