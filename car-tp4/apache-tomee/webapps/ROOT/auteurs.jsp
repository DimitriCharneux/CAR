<%@ page import="car.tp4.*"%>
<%@ page import="java.util.List"%>

<html>
<h1>Liste des auteurs</h1>
<%

List<String> res = (List<String>) request.getAttribute("auteurs");
if ( res != null  && !res.isEmpty()) {
    out.println("<ul>");
    
    for(String aut : res){
    	out.println("<li>" + aut + "</li>");
    }
    
    out.println("</ul>");
}

%>

</html>
