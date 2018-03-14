<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Fhirwork Cache Settings</title>

    <link rel="stylesheet" type="text/css" href="resources/reset.css"/>
    <link rel="stylesheet" type="text/css" href="resources/general.css"/>
    <link rel="stylesheet" type="text/css" href="resources/cache.css"/>
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
            <form action="cache" method="POST" id="cache_form">
                <h2 class="subheading">EMPI Cache Settings</h2>
                <table>
                    <tbody>
                        <tr class="property">
                            <th><label for="empiCacheEnabled" class="label">Enabled</label></th>
                            <c:choose>
                                <c:when test="${config.empiCacheEnabled}">
                                    <td><input type="checkbox" id="empiCacheEnabled" checked="checked" class="checkbox"/></td>
                                </c:when>
                                <c:otherwise>
                                    <td><input type="checkbox" id="empiCacheEnabled" class="checkbox"/></td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                        <tr class="property">
                            <th><label for="empiCacheSize" class="label">Maximum size (entries)</label></th>
                            <td><input type="text" name="empiCacheSize" value="${config.empiCacheSize}" class="textbox"/></td>
                        </tr>
                        <tr class="property">
                            <th><label for="empiCacheExpiry" class="label">Expiry time (minutes)</label></th>
                            <td><input type="text" name="empiCacheExpiry" value="${config.empiCacheExpiry}" class="textbox"/></td>
                        </tr>
                    </tbody>
                </table>

                <h2 class="subheading">EHR Cache Settings</h2>
                <table>
                    <tbody>
                        <tr class="property">
                            <th><label for="ehrCacheEnabled" class="label">Enabled</label></th>
                            <c:choose>
                                <c:when test="${config.ehrCacheEnabled}">
                                    <td><input type="checkbox" id="ehrCacheEnabled" checked="checked" class="checkbox"/></td>
                                </c:when>
                                <c:otherwise>
                                    <td><input type="checkbox" id="ehrCacheEnabled" class="checkbox"/></td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                        <tr class="property">
                            <th><label for="ehrCacheSize" class="label">Maximum size (entries)</label></th>
                            <td><input type="text" name="ehrCacheSize" value="${config.ehrCacheSize}" class="textbox"/></td>
                        </tr>
                        <tr class="property">
                            <th><label for="ehrCacheExpiry" class="label">Expiry time (minutes)</label></th>
                            <td><input type="text" name="ehrCacheExpiry" value="${config.ehrCacheExpiry}" class="textbox"/></td>
                        </tr>
                    </tbody>
                </table>

                <input id="ehrCache" name="ehrCacheEnabled" value="${config.ehrCacheEnabled}" class="hiddenInput">
                <input id="empiCache" name="empiCacheEnabled" value="${config.empiCacheEnabled}" class="hiddenInput">

                <input type="button" onclick="confirmSubmit('cache_form')" value="Submit" class="button"/>
            </form>
        </div>
    </div>
    <script>
		function confirmSubmit(form){
            if(confirm("Are you sure you want to make this modification?")) {
			   	if (document.getElementById("empiCacheEnabled").checked) {
  	  	         	document.getElementById('empiCache').value = "true";
                } else {
                    document.getElementById('empiCache').value = "false";
                }
                if (document.getElementById("ehrCacheEnabled").checked) {
                    document.getElementById('ehrCache').value = "true";
                } else {
                    document.getElementById('ehrCache').value = "false";
                }
                document.getElementById(form).submit();
                alert("The modification has been submitted.");
            }
	 	}
    </script>
</body>
</html>
