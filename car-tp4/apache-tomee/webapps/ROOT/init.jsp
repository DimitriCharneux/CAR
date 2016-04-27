<%@ page import="car.tp4.*"%>

<html>
<h1>Initialisation des donnees</h1>
<%

	String res = (String) request.getAttribute("result");
	if ( res != null ) {
    	out.println(res);
	} else {
		out.println("initialisation echouee");
	}

%>

</html>
