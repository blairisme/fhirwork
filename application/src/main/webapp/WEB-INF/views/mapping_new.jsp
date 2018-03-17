<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	 <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Fhirwork Mapping Configuration</title>
    
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
            <a class="link" href="/configuration/cache">
                <div class="icon cache"></div>
                <span>Cache Settings</span>
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
                    <form action="new/basic" method="POST" id="basicForm">
                        <table>
                            <tbody>
                                <tr class="property">
                                    <th><label for="text" class="label">Code</label></th>
                                    <td><input type="text" id="code_basicForm" name="code" value="" class="textbox"/></td>
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
                        	<input type="button" onclick="checkSubmit('basicForm')" value="Submit" class="button">
                    </form>
                    </section>

                    <section id="Advanced" class="tab-panel">
                    <form id="scriptForm" class="script_form" action="new/scripted" method="POST">
                        <label for="code" class="label">Code</label>
                        <input type="text" id="code_scriptForm" name="code" value="" class="textbox"/>

                        <label for="script" class="label">Script</label>
                        <textarea id="script" name="script" class="script" form="scriptForm" rows="50" cols="100">

/*
 * Instances of this prototype represent a FHIR observation.
 *
 * @param date          a string containing the time the observation was created.
 * @param value         a floating point number containing the quantities value.
 * @param unit          a string containing a unit of measurement. E.g., kg.
 * @param unitSystem    a string containing an identification system that the
 *                      unit belongs to.
 */
function Observation(date, value, unit, unitSystem)
{
    this.date = date;
    this.value = value;
    this.unit = unit;
    this.unitSystem = unitSystem;
}

/*
 * Returns an AQL query. When executed, the results of the AQL query will be
 * provided to the getObservations method as a series of Java Maps, one map
 * for each row matching the AQL query. The names of the values in the
 * resulting Map are defined using the 'as' operator.
 *
 * @return  an AQL query.
 */
function getQuery(ehrId)
{
    return "select example/data/origin/value as value " +
            "from EHR [ehr_id/value='" + ehrId + "'] " +
            "contains COMPOSITION c " +
            "contains OBSERVATION example[openEHR.example.v0] ";
}

/**
 * Converts the given queryResults, obtained by executing the AQL query
 * provided by the getQuery method, into Observation objects, as defined
 * above.
 *
 * @param queryResults  a Java List containing a number of Java Maps. Each map
 *                      contains the values selected in the AQL query produced
 *                      by the getQuery method.
 */
function getObservations(queryResults)
{
    var example1 = new Observation("2010-04-10T00:00:00", 70.0, "kg", "http://unitsofmeasure.org");
    var example2 = new Observation("2010-05-10T00:00:00", 72.0, "kg", "http://unitsofmeasure.org");
    var exampleResult = {example1, example2};
    return exampleResult;
}
                        </textarea>
                        <input type="button" onclick="checkSubmit('scriptForm')" value="Submit" class="button">
                    </form>
                    </section>
                </div>
            </div>
        </div>
    </div>
    
    <script>
    	function checkSubmit(form){
    	   if(document.getElementById('code_' + form).value == ""){
     		   alert("The loinc code for the new mapping has not been defined");
     	   }
     	   else{
     		   if(confirm("Add this mapping?")){
    		       document.getElementById(form).submit();
    		       alert("The new mapping has been submitted.");
    		   }
     	   }
    	}
    </script>
    
</body>
</html>
