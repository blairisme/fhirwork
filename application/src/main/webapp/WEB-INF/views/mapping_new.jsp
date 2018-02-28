<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Fhirwork Mapping Configuration</title>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link href="<c:url value="/resources/reset.css"/>" rel="stylesheet"></link>
    <link href="<c:url value="/resources/general.css"/>" rel="stylesheet"></link>
    <link href="<c:url value="/resources/mapping.css"/>" rel="stylesheet"></link>
    <script src="<c:url value="/resources/mapping.js"/>"></script>
</head>
<body>
    <div class="container">
        <div class="navigation">
            <a class="link selected" href="/configuration/mapping">
                <div class="icon mapping"></div>
                <span>FHIR Mappings</span>
            </a>
            <a class="link" href="/configuration/network">
                <div class="icon network"></div>
                <span>Network Settings</span>
            </a>
        </div>

        <div class="content">
            <h1 class="title">Create Observation Conversion</h1>

            <div class="tab">
              <button class="tablinks" onclick="showTab('basic_page')">Basic</button>
              <button class="tablinks" onclick="showTab('script_page')">Advanced</button>
            </div>

            <div id="basic_page" class="tabpage">
                <form action="new/basic" method="POST">
                    <table>
                        <tbody>
                            <tr class="property">
                                <th><label for="text" class="label">Code</label></th>
                                <td><input type="text" id="code" name="code" value="" class="textbox"/></td>
                            </tr>
                            <tr class="property">
                                <th><label for="text" class="label">Text</label></th>
                                <td><input type="text" id="text" name="text" value="" class="textbox"/></td>
                            </tr>
                            <tr class="property">
                                <th><label for="archetype" class="label">Archetype</label></th>
                                <td><input type="text" id="archetype" name="archetype" value="" class="textbox"/></td>
                            </tr>
                            <tr class="property">
                                <th><label for="date" class="label">Date</label></th>
                                <td><input type="text" id="date" name="date" value="" class="textbox"/></td>
                            </tr>
                            <tr class="property">
                                <th><label for="magnitude" class="label">Magnitude</label></th>
                                <td><input type="text" id="magnitude" name="magnitude" value="" class="textbox"/></td>
                            </tr>
                            <tr class="property">
                                <th><label for="unit" class="label">Unit</label></th>
                                <td><input type="text" id="unit" name="unit" value="" class="textbox"/></td>
                            </tr>
                        </tbody>
                    </table>
                    <input type="submit" value="Submit" class="button"/>
                </form>
            </div>

            <div id="script_page" class="tabpage">
                <form action="new/script" method="POST">
                    <input type="text" id="code" name="code" value="" class="textbox"/>
                    <textarea rows="4" cols="50"></textarea>
                    <input type="submit" value="Submit" class="button"/>
                </form>
            </div>

        </div>
    </div>
</body>
</html>
