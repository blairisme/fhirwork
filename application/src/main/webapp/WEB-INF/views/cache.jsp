<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Fhirwork Cache Settings</title>

    <link rel="stylesheet" type="text/css" href="resources/reset.css"/>
    <link rel="stylesheet" type="text/css" href="resources/general.css"/>
    <link rel="stylesheet" type="text/css" href="resources/network.css"/>
</head>
<body>
    <div class="container">
        <div class="navigation">
            <a class="link" href="/configuration/mapping/list">
                <div class="icon mapping"></div>
                <span>FHIR Mappings</span>
            </a>
            <a class="link" href="/configuration/network">
                <div class="icon network"></div>
                <span>Network Settings</span>
            </a>
            <a class="link selected" href="/configuration/cache">
                <div class="icon cache"></div>
                <span>Cache Settings</span>
            </a>
        </div>
        <div class="content">
            <h1 class="title">Cache Settings</h1>
            <form action="cache" method="POST">
                <table>
                    <tbody>
                        <tr class="property">
                            <th><label for="empiCacheEnabled" class="label">Enabled</label></th>
                            <c:choose>
                                <c:when test="${config.empiCacheEnabled}">
                                    <td><input type="checkbox" name="empiCacheEnabled" checked="checked" class="checkbox"/></td>
                                </c:when>
                                <c:otherwise>
                                    <td><input type="checkbox" name="empiCacheEnabled" class="checkbox"/></td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                        <tr class="property">
                            <th><label for="empiCacheSize" class="label">Cache Size (Entries)</label></th>
                            <td><input type="text" name="empiCacheSize" value="${config.empiCacheSize}" class="textbox"/></td>
                        </tr>
                        <tr class="property">
                            <th><label for="empiCacheExpiry" class="label">Cache Expiry (Minutes)</label></th>
                            <td><input type="text" name="empiCacheExpiry" value="${config.empiCacheExpiry}" class="textbox"/></td>
                        </tr>
                    </tbody>
                </table>
               <input type="submit" value="Submit" class="button"/>
            </form>
        </div>
    </div>
</body>
</html>
