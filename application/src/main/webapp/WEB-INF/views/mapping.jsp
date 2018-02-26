<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Fhirwork Mapping</title>
    <link rel="stylesheet" type="text/css" href="resources/reset.css"/>
    <link rel="stylesheet" type="text/css" href="resources/general.css"/>
    <link rel="stylesheet" type="text/css" href="resources/network.css"/>
    <script src="//lib.sinaapp.com/js/jquery/1.12.4/jquery-1.12.4.min.js"></script>
</head>
<body>
	<div class="container">
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
		  <h1 class="title">FHIR Mappings</h1>
		  <select id="loinc-selection" onchange="requestMappingConfig();">
		      <option value="default" selected="selected">(Loinc Code)</option>
		      <%for(String loinc: (Collection<String>)request.getAttribute("allLoinc")){%>
		          <option value="<%out.print(loinc);%>"> <%out.print(loinc);%> </option>
		      <%}%>
		  </select>
		    
		  <form action="mapping" method="POST">
		      <table>
		          <tbody>
		              <tr class="property">
		                  <th><label for="text" class="label">Text</label></th>
		                  <td><input type="text" id="text" name="text" value="${LoincData.text}" class="textbox"/></td>
		              </tr>
		              <tr class="property">
		                  <th><label for="archetype" class="label">Archetype</label></th>
		                  <td><input type="text" id="archetype" name="archetype" value="${LoincData.archetype}" class="textbox"/></td>
		              </tr>
		              <tr class="property">
		                  <th><label for="date" class="label">Date</label></th>
		                  <td><input type="text" id="date" name="date" value="${LoincData.date}" class="textbox"/></td>
		              </tr>
		              <tr class="property">
		                  <th><label for="magnitude" class="label">Magnitude</label></th>
		                  <td><input type="text" id="magnitude" name="magnitude" value="${LoincData.magnitude}" class="textbox"/></td>
		              </tr>
		              <tr class="property">
		                  <th><label for="unit" class="label">Unit</label></th>
		                  <td><input type="text" id="unit" name="unit" value="${LoincData.unit}" class="textbox"/></td>
		              </tr>
		              
		          </tbody>
		      </table>
		      <input type="submit" value="Submit" class="button"/>
				<input type="text" id="CurrentLoinc" name="CurrentLoinc" value="${CurrentLoinc}" class="textbox" style="visibility:hidden;"/>
		   </form>
		</div>
	</div>
    
   <script>
       function requestMappingConfig(){
           var loinc = document.getElementById('loinc-selection').value;
           if(loinc != "default"){
        	   $.ajax({
                   url:"mapping/content",
                   data:{"loinc":loinc},
                   type:"GET",
                   success:function(data){
                	    updateForm(data);
                   },
                   error:function(){
                	   alert("The mapping data has not been successfully fetched. Please try again.");
                	   resetSelection();
                   }
                });
               $("#CurrentLoinc").val(loinc);
           }
           else
        	   resetSelection();
       	}
       
       function updateForm(data){
           document.getElementById("text").value = data[0];
           document.getElementById("archetype").value = data[1];
           document.getElementById("date").value = data[2];
           document.getElementById("magnitude").value = data[3];
           document.getElementById("unit").value = data[4];
       }
       
       function resetSelection(){
    	   document.getElementById("loinc-selection").value = "default"; 
           document.getElementById("text").value = "";
           document.getElementById("archetype").value = "";
           document.getElementById("date").value = "";
           document.getElementById("magnitude").value = "";
           document.getElementById("unit").value = "";
       }
   </script>
</body>
</html>