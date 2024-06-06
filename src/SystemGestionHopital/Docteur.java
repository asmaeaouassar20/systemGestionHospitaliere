package SystemGestionHopital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Docteur {
    private Connection connection;
    private Scanner scanner;

    public Docteur(Connection connection,Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }


    public void ajouterDocteur(){
        System.out.print("Donner le nom du docteur: ");
        String nom=scanner.next();
        System.out.print("Donnez la spécialisation du docteur: ");
        String specialisation=scanner.next();

        try{
            String requete="INSERT INTO Docteur(nom,specialisation) values(?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(requete);

            preparedStatement.setString(1,nom);
            preparedStatement.setString(2,specialisation);

            int infosSaisis=preparedStatement.executeUpdate();
            if(infosSaisis>0) System.out.println("le docteur est ajouté avec succes!!");
            else System.out.println("échec de l'ajout du docteur ");

        } catch (SQLException e) { // C'est une exception qui se produit lorsqu'une erreur se produit lors de l'acces à une base de données à l'aide de JDBC (Java DataBase Connectivity )
            e.printStackTrace();
            //***********************************
            // e.printStackTrace() : - c'est une méthode en Java qui appartient à la classe 'Throwable'
            //                        - Elle est utilisée pour afficher une trace de la pile d'exécution de l'exception
            //### la classe 'Throwable'  est la superclasse de toutes les erreurs (error) et exceptions (exception)
            // *************************************
        }
    }

    public void voirDocteurs(){
        String requete="SELECT * FROM Docteur";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(requete);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("Docteur: ");
            System.out.println("+----+------------+-------------------+");
            System.out.println("| ID | Nom        | Spécialisation    |");// id:4 \  nom:12  \  specialisation:19
            System.out.println("+----+------------+-------------------+");
            while(resultSet.next()){
                int id=resultSet.getInt("id");
                String nom= resultSet.getString("nom");
                String specialisation=resultSet.getString("specialisation");
                System.out.printf("| %-2d | %-10s | %-17s |%n",id,nom,specialisation);// id:6    \ nom:7    \ specialisation:7
                System.out.println("+----+------------+-------------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean docteurExistId(int id){
        String requete="SELECT * FROM Docteur WHERE id=?";
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

    public boolean docteurDisponible(int id,String date){
        String requete="SELECT COUNT(*) FROM RendezVous WHERE idDocteur=? AND dateRendezVous=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(requete);
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,date);
            ResultSet resultSet=preparedStatement.executeQuery(); // resultSet utilisé pour stocker les résultats d'une requete SQL exécutée sur une base de données relationnelle
            if(resultSet.next()) { // resultSet.next() : c'est une méthode qui avance le curseur du ResultSet d'une ligne
                // Ma requete ne sélectionne qu'une seule colonne qui est le resultat de la fonction COUNT(*) donc
                int nombre = resultSet.getInt(1);
                return nombre==0;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
}
