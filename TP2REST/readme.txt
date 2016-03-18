Dimitri Charneux

Pour se connecter : 
http://localhost:8080/rest/tp2/ftp/auth

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

Tests unitaires :
Quelques tests unitaires ont été réalisés. L'authentification, la création de dossier, le list, la déconnexion et l'affichage du répertoire courant ont été testés. La suppression de fichier et dossier a été réalisée dans le test de ls.
Le test pour le change diretory et le cdup a été réalisé mais plante pour une raison inconnue, c'est pour cela qu'il a été commenté.
Pour réaliser une couverture de test complète, il aurait fallut tester les points suivant : 
	-l'envoi de fichier en envoyant un fichier et en regardant qu'il est bien dans le répertoire du serveur.
	-la réception de fichier en appelant le méthode pour le recevoir et en testant qu'il est bien égal au fichier source.

 

Remarques : 
- Pour retourner dans le dossier parent, l'appel a cdup est nécessaire, appuyer sur la flèche de retour du navigateur ne fonctionne pas.

- J'ai choisis de supprimer des fichiers ou des doosiers grâce à une methode GET et non un DELETE car ça avait l'air plus simple à faire avec le GET et car aucune explication n'a été donnée concernant l'utilisation du 
DELETE.

- La création et la modification de fichier se font toutes les deux grâce à la méthode POST car le serveur FTP ne fait pas la différence entre création et modification des fichiers.

- Ce TP a été très laborieux à réaliser et a poser de nombreux problèmes (\r\n au lieu de \n, parser incompatible, cours sur REST très incomplets, librairie commons-net-3.2.jar donnée sur moodle périmé).
- Mettre des exemples plus concret sur la manière d'utiliser ftpClient et REST en général et mettre un TD sur REST ne serait pas du luxe.



