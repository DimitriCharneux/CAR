<%@ page import="main.java.car.tp4.*"%>

<html>
<h1>Initialisation des données</h1>
<%

String res = (string) request.getAttribute("init");
if ( res != null ) {
    out.print(res);
}

%>

</html>
