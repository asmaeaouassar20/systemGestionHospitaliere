package SystemGestionHopital;

import java.nio.channels.NonReadableChannelException;
import java.sql.*;
import java.util.Scanner;

public class SystemGestionHopital {
    private static final String url="jdbc:mysql://localhost:3306/hopital";
    private static final String username="root";
    private static final String password="";

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        Patient patient = null;
        Docteur docteur = null;
        Connection connection = null;

        // Charger explicitement le pilote JDBC pour MySQL.
        // Bien que ce ne soitplus nécessaire dans les versions moderne de JDBC
        // il peut toujours etre utilisé pour des raisons de compatibilité ou de clarté
        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        //Etablir une connexion à la base de données à partir d'une application Java en utilisant JDBC (Java Database Connectivity)
        try{
            connection= DriverManager.getConnection(url,username,password);
            System.out.println("connexion à la BD avec succes !");
            patient=new Patient(connection,scanner);
            docteur=new Docteur(connection,scanner);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        while(true){
            System.out.println("<<<<<<<<<< System de gestion hospitalière >>>>>>>>>>");
            System.out.println(" 1 : Patient");
            System.out.println(" 2 : Docteur");
            System.out.println(" 3 : Rendez Vous");
            System.out.println(" 4 : Quitter");
            System.out.print("-> Entrez votre choix : ");
            int choix=scanner.nextInt();
            System.out.println("____________________________________________________\n");

            switch (choix){
                case 1 :{
                    System.out.println("1: ajouter patient");
                    System.out.println("2: voir patient");
                    System.out.print("-> Entrez votre choix : ");
                    int choix2=scanner.nextInt();
                    switch (choix2){
                        case 1 :
                            patient.ajouterPatient();
                            System.out.println();
                            break;
                        case 2:
                            patient.voirPatients();
                            System.out.println();
                            break;
                        default:
                            System.out.println("choix invalide !!");
                    }
                    System.out.println();
                    break;
                }
                case 2 :{
                    System.out.println("1: ajouter docteur");
                    System.out.println("2: voir docteur");
                    System.out.print("-> Entrez votre choix : ");
                    int choix2=scanner.nextInt();
                    switch (choix2){
                        case 1 :
                            docteur.ajouterDocteur();
                            System.out.println();
                            break;
                        case 2 :
                            docteur.voirDocteurs();
                            System.out.println();
                            break;
                        default:
                            System.out.println("choix invalide !!");
                    }
                    System.out.println();
                    break;
                }
                case 3:{
                    System.out.println("1: Prendre un rendez vous");
                    System.out.println("2: Voir l'ensemble des rendez vous");
                    System.out.print("--> Donnez votre choix: ");
                    int choix2=scanner.nextInt();
                    switch (choix2){
                        case 1 : prendreRendezVous(patient,connection,docteur,scanner);
                                System.out.println();
                                break;
                        case 2: voirRendezVous(connection);
                                System.out.println();
                                break;
                        default:
                            System.out.println("choix invalide !!");
                            break;
                    }
                    break;
                }
                case 4:
                    System.out.println("MERIC D'AVOIR UTILISE LE SYSTEME DE GESTION HOSPITALIERE !!");
                    return;
                default:
                    System.out.println("choix invalide !!!");
                    break;
            }
        }

    }
    public static void prendreRendezVous(Patient patient,Connection connection,Docteur docteur, Scanner scanner){
        System.out.print("Entrez l'id du patient: ");
        int idPatient=scanner.nextInt();
        System.out.print("Entrez l'id du docteur: ");
        int idDocteur=scanner.nextInt();
        // on doit vérifier que les ids donnés par l'utilisateur existe, pour cela on utilsera les méthodes patientExistId() et docteurExistId()
        // pour cela on doit faire passer les objets patient et docteur en parametre

        if(patient.patientExistId(idPatient) && docteur.docteurExistId(idDocteur)){
            System.out.println("Entrez la date du rendez vous sous la forme de : (yyyy-mm-dd): ");
            String dateRV=scanner.next();
            if(docteur.docteurDisponible(idDocteur,dateRV)){
                String requete="INSERT INTO RendezVous(idPatient,idDocteur,dateRendezVous) VALUES(?,?,?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(requete);
                    preparedStatement.setInt(1,idPatient);
                    preparedStatement.setInt(2,idDocteur);
                    preparedStatement.setString(3,dateRV);

                    int saisiRV=preparedStatement.executeUpdate();
                    if(saisiRV>0) System.out.println("Le rendez vous est ajouté avec succes");
                    else System.out.println("Echec de l'ajout du rendez vous");
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }else{
                System.out.println("le docteur SIMO"+" n'est pas disponible à cette date "+dateRV+" !!");
            }
        }else System.out.println("l'id du patient ou du docteur n'existent pas");
    }

    public static void voirRendezVous(Connection connection){
        String requete="SELECT * FROM RendezVous";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(requete);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("+------------+------------+----------------------+");
            System.out.println("|  Docteur   |  Patient   | Date du rendez vous  |");
            System.out.println("+------------+------------+----------------------+");
            while(resultSet.next()){
                int idDocteur=resultSet.getInt("idDocteur");
                int idPatient=resultSet.getInt("idPatient");
                String dateRendezVous=resultSet.getString("dateRendezVous");

                String requete1="SELECT nom FROM Patient WHERE id=?";
                PreparedStatement preparedStatement1=connection.prepareStatement(requete1);
                preparedStatement1.setInt(1,idPatient);
                ResultSet resultSet1=preparedStatement1.executeQuery();
                resultSet1.next();
                String nomPatient=resultSet1.getString(1);

                String requete2="SELECT nom FROM Docteur WHERE id=?";
                PreparedStatement preparedStatement2=connection.prepareStatement(requete2);
                preparedStatement2.setInt(1,idDocteur);
                ResultSet resultSet2=preparedStatement2.executeQuery();
                resultSet2.next();
                String nomDocteur=resultSet2.getString(1);

                System.out.printf("| %-10s | %-10s |  %-18s  |%n",nomDocteur,nomPatient,dateRendezVous);
                System.out.println("+------------+------------+----------------------+");

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
