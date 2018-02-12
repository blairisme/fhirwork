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
            <a class="link" href="/configuration/mapping">
                <div class="icon mapping"></div>
                <span>FHIR Mappings</span>
            </a>
            <a class="link selected" href="/configuration/network">
                <div class="icon network"></div>
                <span>Network Settings</span>
            </a>
        </div>
        <div class="settings">
            <h1 class="title">EMPI Settings</h1>
            <form action="network" method="POST">
                <table>
                    <tbody>
                        <tr class="property">
                            <th><label for="address" class="label">Address</label></th>
                            <td><input type="text" name="address" value="${empi.address}" class="textbox"/></td>
                        </tr>
                        <tr class="property">
                            <th><label for="username" class="label">Username</label></th>
                            <td><input type="text" name="username" value="${empi.username}" class="textbox"/></td>
                        </tr>
                        <tr class="property">
                            <th><label for="password" class="label">Password</label></th>
                            <td><input type="text" name="password" value="${empi.password}" class="textbox"/></td>
                        </tr>
                    </tbody>
                </table>
                <input type="submit" value="Submit" class="button"/>
            </form>
        </div>
    </div>
</body>
</html>