<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	 <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Fhirwork Mapping Configuration</title>
    
    <link rel="stylesheet" type="text/css" href="/resources/reset.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/general.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/mapping.css"/>
</head>
<body>
    <div class="container">
        <div class="navigation">
            <a class="link selected" href="/configuration/mapping/list">
                <div class="icon mapping"></div>
                <span>FHIR Mappings</span>
            </a>
            <a class="link" href="/configuration/network">
                <div class="icon network"></div>
                <span>Network Settings</span>
            </a>
            <a class="link" href="/configuration/cache">
                <div class="icon cache"></div>
                <span>Cache Settings</span>
            </a>
        </div>

        <div class="content">
            <h1 class="title">Observation Conversion Settings</h1>

            <table class="list">
                <tbody>
                <c:forEach var="mapping" items="${mappings}">
                    <tr class="entry">
                        <th><span class="label">${mapping}</span></th>
                        <td><a class="button edit" href="/configuration/mapping/edit?code=${mapping}"/></td>
                        <td><a class="button delete" href="/configuration/mapping/delete?code=${mapping}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <a href="/configuration/mapping/new" class="button">
                <span>New Mapping</span>
            </a>
        </div>
    </div>
</body>
</html>
