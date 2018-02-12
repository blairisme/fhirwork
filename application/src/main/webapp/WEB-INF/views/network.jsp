<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Fhirwork Network Settings</title>
    <link rel="stylesheet" type="text/css" href="resources/reset.css"/>
    <link rel="stylesheet" type="text/css" href="resources/general.css"/>
    <link rel="stylesheet" type="text/css" href="resources/network.css"/>
</head>
<body>
    <div class"container">
        <div class="navigation">
            <a class="link" href="/configuration/mapping">Mapping</a>
            <a class="link" href="/configuration/network">Network</a>
        </div>
        <div class="settings">
            <h1 class="title">EMPI Settings</h1>
            <form action = "network" method = "POST">
                <div class="form_element">
                    <span>Address: </span>
                    <input type="text" name="address" value="${empi.address}" />
                </div>
                <div class="form_element">
                    <span>User name: </span>
                    <input type = "text" name="username" value="${empi.username}" />
                </div>
                <div class="form_element">
                    <span>Password: </span>
                    <input type = "text" name="password" value="${empi.password}" />
                </div>
                <input type = "submit" value = "Submit" />
            </form>
        </div>
    </div>
</body>
</html>