<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JMS Message Sender</title>
</head>
<body>

<h2>Send Message to JMS Queue</h2>

<form action="send" method="post">
    <label>Enter Message:</label>
    <input type="text" name="message" required />
    <button type="submit">Send</button>
</form>

<hr>

<h3>Last Received Message:</h3>
<p>
    <%= application.getAttribute("lastMessage") != null
            ? application.getAttribute("lastMessage")
            : "No message received yet." %>
</p>

</body>
</html>
