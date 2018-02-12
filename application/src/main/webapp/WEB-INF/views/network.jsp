<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HelloWorld page</title>
</head>
<body>
    <form action = "network" method = "POST">
        Address: <input type="text" name="address" value="${empi.address}" /><br>
        User name: <input type = "text" name="username" value="${empi.username}" /><br>
        Password: <input type = "text" name="password" value="${empi.password}" /><br>
        <input type = "submit" value = "Submit" />
    </form>
</body>
</html>