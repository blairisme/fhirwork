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
            <a class="link selected" href="/configuration/mapping/list">
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

            <div class="tabset">
                <!-- Tab 1 -->
                <input type="radio" name="tabset" id="tab1" aria-controls="Basic" checked>
                <label for="tab1">Basic</label>
                <!-- Tab 2 -->
                <input type="radio" name="tabset" id="tab2" aria-controls="Advanced">
                <label for="tab2">Advanced</label>

                <div class="tab-panels">
                    <section id="Basic" class="tab-panel">
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
                    </section>

                    <section id="Advanced" class="tab-panel">
                    <form id="scriptForm" class="script_form" action="new/scripted" method="POST">
                        <label for="code" class="label">Code</label>
                        <input type="text" id="code" name="code" value="" class="textbox"/>

                        <label for="script" class="label">Script</label>
                        <textarea id="script" name="script" class="script" form="scriptForm" rows="50" cols="100">
/*
* Instances of this prototype represent the constituent parts of an EHR query.
* These will be used to generate a full AQL when combined with query
* statements required by the FHIRWork engine.
*/
function Query(selectors, archetype)
{
    this.selectors = selectors;
    this.archetype = archetype;
}
/*
* Instances of this prototype represent a FHIR observation quantity.
*
* @param value a floating point number containing the quantities value.
* @param unit  a unit of measurement. E.g., kg for kilograms.
*/
function Quantity(value, unit)
{
    this.value = value;
    this.unit = unit;
}
/*
* Provides an AQL query that when made will return all skeletal age
* observations contained in a given heath record.
*
* @return  a Query instance.
*/
function getQuery(ehrId)
{
    //Todo
}

/*
 * Provides a Quantity instance, containing a value for the given query result.
 *
 * @return  a Quantity instance.
 */
function getQuantity(queryResult)
{
    //Todo
}
                        </textarea>
                        <input type="submit" value="Submit" class="button"/>
                    </form>
                    </section>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
