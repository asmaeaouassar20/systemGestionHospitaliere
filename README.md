# Systeme de Gestion Hospitaliere

C'est une solution logicielle intégrée conçue pour gérer les opérations administratives 
et clinique d'un hopital. Ce systeme permet d'automatiser divers fonctions et processus 
pour améliorer l'efficacité, la précision et la qualité des soins aux patients.


## Prérequis
- JDK 8 ou supérieur
- MySQL 5.7 ou supérieur
- Bibliothèque JDBC pour MySQL ( mysql-connector-java)

## Bases de données

#### Les tables
* CREATE TABLE Patient ( id INT AUTO_INCREMENT PRIMARY KEY , nom VARCHAR(255) NOT NULL , age INT NOT NULL , genre VARCHAR(255) NOT NULL);
* CREATE TABLE Docteur ( id INT AUTO_INCREMENT PRIMARY KEY , nom VARCHAR(255) NOT NULL , specialisation VARCHAR(255) NOT NULL );
* CREATE TABLE RendezVous ( id INT AUTO_INCREMENT PRIMARY KEY , idPatient INT NOT NULL , idDocteur INT NOT NULL , dateRendezVous DATE NOT NULL , FOREIGN KEY (idPatient) REFERENCES Patient(id) , FOREIGN KEY (idDocteur) REFERENCES Docteur(id));

