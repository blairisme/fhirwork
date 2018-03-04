<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
            	 <p><span class="label">Enable Cache </span>
            	 <input type="checkbox" id="check" name="enable" value="enable" /> </p>
            	 <input type="submit" value="Submit" class="button"/>
            </form>
        </div>
        <input id="currentCacheChecked" value="${checked}" style="visibility:hidden;"> 
    </div>
    <script type="text/javascript">
	 	  window.onload=function(){
	 	  	    if(document.getElementById('currentCacheChecked').value.equals("true")){
	 	  	        document.getElementById('check').checked=true; 
	 	  	     }
   		   }

    </script>
</body>
</html>
