<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="content-type" content="text/html" charset="utf-8"/>
    <title>Saisie d'un livre</title>
  </head>
  <body>
      <%
        String titre = request.getParameter("titreInput"), auteur = "", annee="";
        if(titre != null && !titre.equals("")){
            auteur = request.getParameter("auteurInput");
            annee = request.getParameter("anneeInput");
            if(auteur==null)
                auteur = "";
            if(annee==null)
                annee = "";
            out.println("<h1>Les informations suivantes sont-elles exactes?</h1>");
        } else
            titre = "";
    %>
  
  
  
  
  <form action="http://localhost:8080/formulaire.jsp" method="post">
    <div>
        <label>Titre :</label>
        <input type="text" name="titreInput" value=<%=titre%>></input>
    </div>
    <div>
        <label>Auteur :</label>
        <input type="text" name="auteurInput" value=<%=auteur%>></input>
    </div>
    <div>
        <label>Ann&eacute;e de parution :</label>
        <input type="text" name="anneeInput" value=<%=annee%>></input>
    </div>
    
    <div class="button">
        <button type="submit">Envoyer votre message</button>
    </div>
</form>
  </body>
</html>
