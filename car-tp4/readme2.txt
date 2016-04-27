Lescieux Yohann


-----------------Création de servlet et lancement tomcat--------------

	1. Se placer dans le répertoire car_TP4_lescieux_yohann et lancer le serveur tomcat : 

		./apache-tomee/bin/catalina.sh run

	
	2. Sous eclipse, dans le package car.tp4 : 
	
		Créer le fichier Nom.java


	3. Dans le dossier /car_TP4_lescieux_yohann/apache-tomee/conf

		Dans le fichier web.xml

		- Créer la servlet à chaque fois :

 <servlet>
    <servlet-name>Nom</servlet-name>
    <servlet-class>car.tp4.Nom</servlet-class>
 </servlet>


		- Créer la servlet mapping à chaque fois : 

<servlet-mapping>
    <servlet-name>Nom</servlet-name>
    <url-pattern>/Nom</url-pattern>
</servlet-mapping>



	4. Sous un navigateur, entrer l'url : localhost:8080/Nom
