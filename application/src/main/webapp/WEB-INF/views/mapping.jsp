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
		  <select id="changeSelection" onchange="requestMappingConfig();">
		      <option value="default" selected="selected">(Loinc Code)</option>
		      <%for(String loinc: (Collection<String>)request.getAttribute("allLoinc")){%>
		          <option value="<%out.print(loinc);%>"> <%out.print(loinc);%> </option>
		      <%}%>
		  </select>
		  </br></br>
		  <form action="mapping" method="POST" id="changeForm">
		      <input type="hidden" id="currentLoinc" name="currentLoinc" value=""/>
		      <table>
		          <tbody>
		              <tr class="property">
		                  <th><label for="text" class="label">Text</label></th>
		                  <td><input type="text" id="text" name="text" value="${loincData.text}" class="textbox"/></td>
		              </tr>
		              <tr class="property">
		                  <th><label for="archetype" class="label">Archetype</label></th>
		                  <td><input type="text" id="archetype" name="archetype" value="${loincData.archetype}" class="textbox"/></td>
		              </tr>
		              <tr class="property">
		                  <th><label for="date" class="label">Date</label></th>
		                  <td><input type="text" id="date" name="date" value="${loincData.date}" class="textbox"/></td>
		              </tr>
		              <tr class="property">
		                  <th><label for="magnitude" class="label">Magnitude</label></th>
		                  <td><input type="text" id="magnitude" name="magnitude" value="${loincData.magnitude}" class="textbox"/></td>
		              </tr>
		              <tr class="property">
		                  <th><label for="unit" class="label">Unit</label></th>
		                  <td><input type="text" id="unit" name="unit" value="${loincData.unit}" class="textbox"/></td>
		              </tr>
		          </tbody>
		      </table>
		      <input type="button" onclick="modificationConfirmation('changeForm')" value="Change Mapping" class="button">
           </form>
		   
		   <br/></br>
		   
		   <form action="mapping" method="POST" id="creationForm">
                <table>
                    <tbody>
	                   <tr class="property">
                           <th><label for="newLoinc" class="label">New Loinc Code</label></th>
                           <td><input type="text" id="newLoinc" name="newLoinc" value="" class="textbox"/></td>
                       </tr>  
                       <tr class="property">
                           <th><label for="text" class="label">Text</label></th>
                           <td><input type="text" id="text" name="text" value="${loincData.text}" class="textbox"/></td>
                       </tr>
                       <tr class="property">
                           <th><label for="archetype" class="label">Archetype</label></th>
                           <td><input type="text" id="archetype" name="archetype" value="${loincData.archetype}" class="textbox"/></td>
                       </tr>
                       <tr class="property">
                           <th><label for="date" class="label">Date</label></th>
                           <td><input type="text" id="date" name="date" value="${loincData.date}" class="textbox"/></td>
                       </tr>
                       <tr class="property">
                           <th><label for="magnitude" class="label">Magnitude</label></th>
                           <td><input type="text" id="magnitude" name="magnitude" value="${loincData.magnitude}" class="textbox"/></td>
                       </tr>
                       <tr class="property">
                           <th><label for="unit" class="label">Unit</label></th>
                           <td><input type="text" id="unit" name="unit" value="${loincData.unit}" class="textbox"/></td>
                       </tr>
	               </tbody>
                </table>
                <input type="button" onclick="creationFormChecking()" value="Create mapping" class="button">
            </form>
            
            </br></br>
            
            <select id="removeSelection" onchange="setMappingToRemove();">
                <option value="default" selected="selected">(Loinc Code)</option>
                <%for(String loinc: (Collection<String>)request.getAttribute("allLoinc")){%>
                    <option value="<%out.print(loinc);%>"> <%out.print(loinc);%> </option>
                <%}%>
            </select>
            </br></br>
            <form action="mapping" method="POST" id="removalForm">
                <input type="hidden" id="loincToRemove" name="loincToRemove" value=""/>
                <input type="button" onclick="modificationConfirmation('removalForm')" value="Remove Mapping" class="button">
            </form>
		</div>
	</div>
    
   <script>
       function requestMappingConfig(){
           var loinc = document.getElementById('changeSelection').value;
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
               $("#currentLoinc").val(loinc);
           }
           else
        	   resetSelection();
       	}
       
       function creationFormChecking(){
    	   if(document.getElementById('newLoinc').value == ""){
    		   alert("The loinc code for the new mapping has not been defined");
    	   }
    	   else{
    		   modificationConfirmation("creationForm");
    	   }
       }
       
       function setMappingToRemove(){
    	   var loincToRemove = document.getElementById('removeSelection').value;
    	   if(loincToRemove != "default"){
    		    $("#loincToRemove").val(loincToRemove);
    	   }
       }
       
       function modificationConfirmation(form){
		   if(confirm("Are you sure you want to make this modification?")){
		       document.getElementById(form).submit();
		       alert("The modification has been submitted, it takes some time to make the updated configuration available to this page.");
		   }
       }
       
       function updateForm(data){
           document.getElementById("text").value = data[0];
           document.getElementById("archetype").value = data[1];
           document.getElementById("date").value = data[2];
           document.getElementById("magnitude").value = data[3];
           document.getElementById("unit").value = data[4];
       }
       
       function resetSelection(){
    	   document.getElementById("changeSelection").value = "default"; 
    	   document.getElementById("removeSelection").value = "default"; 
           document.getElementById("text").value = "";
           document.getElementById("archetype").value = "";
           document.getElementById("date").value = "";
           document.getElementById("magnitude").value = "";
           document.getElementById("unit").value = "";
       }
   </script>
</body>
</html>
