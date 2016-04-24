<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="content-type" content="text/html" charset="utf-8"/>
    <title>Saisie d'un livre</title>
  </head>
  <body>
      <%
        String titre = request.getParameter("titre"), auteur = "", annee="";
        if(titre != null){
            out.println("<h1>Recapitulatif : </h1>");
            out.println("Titre : " + titre + "<br />");
            auteur = request.getParameter("auteur");
            annee = request.getParameter("annee");
            if(auteur!=null)
                out.println("Auteur : " + auteur + "<br />");
            
            try{
                if(annee!=null)
                    out.println("Année de parution : " + annee + "<br />");
                
            }catch(Exception e){}
            
                
        } else
            titre = "";
    %>
  
  
  
  
  <form action="http://localhost:8080/formulaire.jsp" method="post">
    <div>
        <label>Titre :</label>
        <input type="text" name="titre"/>
    </div>
    <div>
        <label>Auteur :</label>
        <input type="text" name="auteur"/>
    </div>
    <div>
        <label>Ann&eacute;e de parution :</label>
        <input type="text" name="annee"/>
    </div>
    
    <div class="button">
        <button type="submit">Envoyer votre message</button>
    </div>
</form>
  </body>
</html>
