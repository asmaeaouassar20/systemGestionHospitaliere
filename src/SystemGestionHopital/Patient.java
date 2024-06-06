package SystemGestionHopital;


import java.sql.Connection; //Permettre à Java de se connecter et d'interagir avec des bases de données relationnelles
import java.sql.PreparedStatement;
import java.sql.ResultSet; //La classe ResultSet est utilisée pour représenter un ensemble de résultats de base de données obtenu en exécutant une requete SQL via un objet Statement
import java.sql.SQLException;
import java.util.Scanner; // La classe 'Scanner' permet de lire l'entrée à partir de diverses sources de données (clavier, fichier, chaine de caracteres ..)

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection,Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }


    public void ajouterPatient(){
        System.out.print("Donner le nom du patient: ");
        String nom=scanner.next();
        System.out.print("Donnez l'age du patient: ");
        int age=scanner.nextInt();
        System.out.print("Donnez le genre du patient: ");
        String genre=scanner.next();

        try{
            String requete="INSERT INTO Patient(nom,age,genre) values(?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(requete);

            preparedStatement.setString(1,nom);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,genre);

            int infosSaisis=preparedStatement.executeUpdate();
            if(infosSaisis>0) System.out.println("le patient est ajouté avec succes!!");
            else System.out.println("échec de l'ajout du patient ");

        } catch (SQLException e) { // C'est une exception qui se produit lorsqu'une erreur se produit lors de l'acces à une base de données à l'aide de JDBC (Java DataBase Connectivity )
            e.printStackTrace();
            //***********************************
            // e.printStackTrace() : - c'est une méthode en Java qui appartient à la classe 'Throwable'
            //                        - Elle est utilisée pour afficher une trace de la pile d'exécution de l'exception
            //### la classe 'Throwable'  est la superclasse de toutes les erreurs (error) et exceptions (exception)
            // *************************************
        }
    }

    public void voirPatients(){
        String requete="SELECT * FROM Patient";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(requete);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------+------------+------+------------+");
            System.out.println("|   id |     nom    |  age |     genre  |");
            System.out.println("+------+------------+------+------------+");
            while(resultSet.next()){
                int id=resultSet.getInt("id");
                String nom= resultSet.getString("nom");
                int age=resultSet.getInt("age");
                String genre=resultSet.getString("genre");
                System.out.printf("|   %2d |   %6s   |  %3d |   %6s   |%n",id,nom,age,genre);
                System.out.println("+------+------------+------+------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean patientExistId(int id){
        String requete="SELECT * FROM Patient WHERE id=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(requete);
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()) return true;
            //else return false;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
