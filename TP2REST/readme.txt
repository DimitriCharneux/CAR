Dimitri Charneux

Pour se connecter : 
http://localhost:8080/rest/tp2/ftp/co/login/mdp

se deconnecter : 
http://localhost:8080/rest/tp2/ftp/deco

lister les fichiers et dossiers : 
http://localhost:8080/rest/tp2/ftp/ls

uploader un fichier : 
http://localhost:8080/rest/tp2/ftp/initUpload/

aller dans le dossier 'dossier' :
http://localhost:8080/rest/tp2/ftp/cwd/dossiers

revenir dans le dossier parent : 
http://localhost:8080/rest/tp2/ftp/cdup

créer un dossier 'dossier' : 
http://localhost:8080/rest/tp2/ftp/mkd/dossiers

supprimer fichier ou dossiers : 
http://localhost:8080/rest/tp2/ftp/rm/file

afficher le repertoire courant : 
se connecter : 
http://localhost:8080/rest/tp2/ftp/pwd

Remarques : 
- Pour retourner dans le dossier parent, l'appel a cdup est nécessaire, appuyer sur la flèche de retour du navigateur ne fonctionne pas.

- Ce TP a été très laborieux à réaliser et a poser de nombreux problèmes (\r\n au lieu de \n, parser incompatible, cours sur REST très incomplets, librairie commons-net-3.2.jar donnée sur moodle périmé).
    Mettre des exemples plus concret sur la manière d'utiliser ftpClient et REST en général et mettre un TD sur REST ne serait pas du luxe.



