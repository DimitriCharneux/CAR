Serveur FTP
Dimitri Charneux

Toutes les fonctionnalités demandées ont était implémentées.

Pour lancer le programme, n'oublier pas de spécifier le repertoire principale en argument !

Comme rien n'était spécifié à ce sujet, j'ai décidé qu'un utilisateur est correcte si son pseudo et son mot de passe sont les mêmes.

Ce programme a été testé avec le commande ftp du shell.

Les fonctions implémentées sont les suivantes : 
LIST appelé avec 'ls' dans ftp,
RETR appelé avec 'get file' dans ftp,
STOR appelé avec 'put file' dans ftp,
PWD appelé avec 'pwd' dans ftp,
CWD appelé avec 'cd path' dans ftp,
MKD appelé avec 'mkdir dir' dans ftp,
CDUP appelé avec 'cdup' dans ftp.

Des tests unitaires ont été réalisés dans le package test.