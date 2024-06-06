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

###### Détail des tables
- Table : Patient
_______________________________________________________________________
Nom de la colonnes |  Type de données  |  Contraintes   |  Description  |
___________________|___________________|________________|_______________|
id		             |	INT	             |  PRIMARY KEY   |               |
		               |		               | AUTO_INCREMENT |               |
___________________|___________________|________________|_______________|
nom                |  VARCHAR(255)     |    NOT NULL    |               |
___________________|___________________|________________|_______________|
age                |     INT           |   NOT NULL     |               |
___________________|___________________|________________|_______________|
genre              |   VARCHAR(10)     |    NOT NULL    |               |
___________________|___________________|________________|_______________|


- Table : Docteur
_______________________________________________________________________
Nom de la colonnes |  Type de données  |  Contraintes   |  Description  |
___________________|___________________|________________|_______________|
id                 | INT               | PRIMARY KEY    |               |
                   |                   | AUTO_INCREMENT |               |
___________________|___________________|________________|_______________|		   
nom                | VARCHAR(255)      | NOT NULL       |               |
___________________|___________________|________________|_______________|
specialisation     |  VARCHAR(255)     |  NOT NULL      |               |
___________________|___________________|________________|_______________|

- Table : RendezVous
________________________________________________________________________
Nom de la colonnes |  Type de données  |  Contraintes   |  Description  |
___________________|___________________|________________|_______________|
id                 | INT               |  PRIMARY KEY   |               |
	                 |                   | AUTO_INCREMENt |               |	
___________________|___________________|________________|_______________|               
                   | INT               |   NOT NULL     |               |
idPatient          |                   | FOREIGN KEY    |               |          
___________________|___________________|________________|_______________|		
                   | INT               |   NOT NULL     |               |
idDocteur          |                   | FOREIGN KEY    |               |
___________________|___________________|________________|_______________|		
dateRendezVous     | DATE              |  NOT NULL      |               |
___________________|___________________|________________|_______________|
