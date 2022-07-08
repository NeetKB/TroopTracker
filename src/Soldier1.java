import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class Soldier1 {


    public static void main(String[] args) {

        try (Socket socket = new Socket("Localhost", 1234)) {
            BufferedReader ServerInputStream = new BufferedReader( // When a server connects, the client will use this input stream to receive data
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter ServerOutput = new PrintWriter(socket.getOutputStream(), true); //  When a server connects, the client will use this output stream to send data

            Connection conn = DriverManager.getConnection(Database.Database);
            Statement statement = conn.createStatement();

            String Menuchoice = "0";

            String ServerResponse;

            final int Goal = 10;
            final int max_ammo = 100;
            final int max_health = 100;
            final int max_supplies = 100;
            final int ID = 1;

            int ammo = max_ammo;
            int health = max_health;
            int supplies = max_supplies;
            int location = 0;

            String status = "INSERT INTO " + Database.Table_Troops + " VALUES(1, 100, 100, 100, 0)";
            ServerOutput.println(status); // THIS IS SENDING THE SERVER THE INPUT



            System.out.println("WELCOME TO THE TROOP TRACKER" + "\n" +
                                "Your ID is 1" + "\n" +
                                "YOUR CURRENT STATUS IS" + "\n" +
                                "Health: 100%" + "\n" +
                                "Supplies: 100%" + "\n" +
                                "Ammunition: 100%" + "\n" +
                                "Location: 0  miles" + "\n" + "\n" +

                    "Your goal is to complete 10 miles with more than 0% health, supplies and ammunition at the end of the 10 miles" + "\n"
                    + "If you run out of health, ammunition or supplies - your mission will be terminated" + "\n");


            do {

                System.out.println("MAIN MENU" + "\n" +
                        "PRESS THE CORRESPONDING NUMBER TO CHOOSE AN OPTION" + "\n" +
                        "1 Check current status" + "\n" +
                        "2 Move" + "\n" +
                        "3 Resupply and request first-aid" + "\n" +
                        "4 Quit"+ "\n" );

                Scanner clientInput = new Scanner(System.in);
                Menuchoice = clientInput.nextLine(); // this is reading the scanner

                switch (Menuchoice) {

                    case "1":
                        System.out.println("Your current status is:" + "\n" +
                                "Health: " + health + "%" + "\n" +
                                "Supplies: " + supplies + "%" + "\n" +
                                "Ammunition: " + ammo + "%" + "\n" +
                                "Location: " + location + "\n");

                        status = "UPDATE " + Database.Table_Troops + " SET "
                                + Database.Column_Health + " = " + health + ", "
                                + Database.Column_Supplies + " = " + supplies + ", "
                                + Database.Column_Ammo + " = " + ammo + ", "
                                + Database.Column_Location + " = " + location
                                + " WHERE " + Database.Column_ID + " = " + ID;

                        ServerOutput.println(status); // THIS IS SENDING THE SERVER THE INPUT
                        break;


                    case "2":
                        int mymovement;
                        Random random = new Random();
                        mymovement =  random.nextInt(4) +1 ;

                        if (mymovement == 1){
                            System.out.println("You have been ambushed. You have moved 1 mile" + "\n"
                                    + "Your health, ammunition and supplies have reduced by 40%" + "\n");
                                    ammo -= 40;
                                    health -= 40;
                                    supplies -= 40;
                                    location += 1;

                                    status = "UPDATE " + Database.Table_Troops + " SET "
                                            + Database.Column_Health + " = " + health + ", "
                                            + Database.Column_Supplies + " = " + supplies + ", "
                                            + Database.Column_Ammo + " = " + ammo + ", "
                                            + Database.Column_Location + " = " + location
                                            + " WHERE " + Database.Column_ID + " = " + ID;
                                    ServerOutput.println(status); // THIS IS SENDING THE SERVER THE INPUT

                        if ((ammo <= 0) || (health <= 0) || (supplies <= 0)) {
                            System.out.println("One or more of your levels is 0%. Your session will now be terminated.");

                            status = "DELETE FROM " + Database.Table_Troops +
                                    " WHERE " + Database.Column_ID + "= " + ID;
                            ServerOutput.println(status); // THIS IS SENDING THE SERVER THE INPUT
                            System.exit(0);

                        } else if (location >= 10) {
                            System.out.println("You have moved 10 miles. You have reached your goal. Your session will now terminate");
                            status = "DELETE FROM " + Database.Table_Troops +
                                    " WHERE " + Database.Column_ID + "= " + ID;
                            ServerOutput.println(status); // THIS IS SENDING THE SERVER THE INPUT
                            System.exit(0);
                        } else ;
                        break;
                    } else if (mymovement == 2) {
                            System.out.println("Your journey has been affected by fire. You have moved 2 miles" + "\n"
                                    + "Your health and supplies have reduced by 30%. Your ammunition has stayed the same" + "\n");
                            health -= 30;
                            supplies -= 30;
                            location += 2;

                            status = "UPDATE " + Database.Table_Troops + " SET "
                                    + Database.Column_Health + " = " + health + ", "
                                    + Database.Column_Supplies + " = " + supplies + ", "
                                    + Database.Column_Ammo + " = " + ammo + ", "
                                    + Database.Column_Location + " = " + location
                                    + " WHERE " + Database.Column_ID + " = " + ID;
                            ServerOutput.println(status); // THIS IS SENDING THE SERVER THE INPUT

                            if ((ammo <= 0) || (health <= 0) || (supplies <= 0)) {
                                System.out.println("One or more of your levels is 0%. Your session will now be terminated.");
                                status = "DELETE FROM " + Database.Table_Troops +
                                        " WHERE " + Database.Column_ID + "= " + ID;
                                ServerOutput.println(status); // THIS IS SENDING THE SERVER THE INPUT
                                System.exit(0);
                            } else if (location >= 10) {
                                System.out.println("You have moved 10 miles. You have reached your goal. Your session will now terminate");
                                status = "DELETE FROM " + Database.Table_Troops +
                                        " WHERE " + Database.Column_ID + "= " + ID;
                                ServerOutput.println(status); // THIS IS SENDING THE SERVER THE INPUT
                                System.exit(0);
                            } else ;
                            break;
                    } else if (mymovement == 3) {
                            System.out.println("Your journey has been affected by bad weather. You have moved 3 miles" + "\n"
                                    + "Your health and supplies have reduced by 20%. Your ammunition has stayed the same" + "\n");
                            health -= 20;
                            supplies -= 20;
                            location += 3;

                            status = "UPDATE " + Database.Table_Troops + " SET "
                                    + Database.Column_Health + " = " + health + ", "
                                    + Database.Column_Supplies + " = " + supplies + ", "
                                    + Database.Column_Ammo + " = " + ammo + ", "
                                    + Database.Column_Location + " = " + location
                                    + " WHERE " + Database.Column_ID + " = " + ID;
                            ServerOutput.println(status); // THIS IS SENDING THE SERVER THE INPUT

                            if ((ammo <= 0) || (health <= 0) || (supplies <= 0)) {
                                System.out.println("One or more of your levels is 0%. Your session will now be terminated.");
                                status = "DELETE FROM " + Database.Table_Troops +
                                        " WHERE " + Database.Column_ID + "= " + ID;
                                ServerOutput.println(status); // THIS IS SENDING THE SERVER THE INPUT
                                System.exit(0);
                            } else if (location >= 10) {
                                System.out.println("You have moved 10 miles. You have reached your goal. Your session will now terminate");
                                status = "DELETE FROM " + Database.Table_Troops +
                                        " WHERE " + Database.Column_ID + "= " + ID;
                                ServerOutput.println(status); // THIS IS SENDING THE SERVER THE INPUT
                                System.exit(0);
                            } else ;
                            break;

                        } else if (mymovement ==4) {
                            System.out.println("Your journey has not been affected by any adverse event. You have moved 4 miles" + "\n"
                                    + "Your health and supplies have reduced by 10%. Your ammunition has stayed the same" + "\n");
                            health -= 10;
                            supplies -= 10;
                            location += 4;
                            status = "UPDATE " + Database.Table_Troops + " SET "
                                    + Database.Column_Health + " = " + health + ", "
                                    + Database.Column_Supplies + " = " + supplies + ", "
                                    + Database.Column_Ammo + " = " + ammo + ", "
                                    + Database.Column_Location + " = " + location
                                    + " WHERE " + Database.Column_ID + " = " + ID;
                            ServerOutput.println(status); // THIS IS SENDING THE SERVER THE INPUT

                            if ((ammo <= 0) || (health <= 0) || (supplies <= 0)) {
                                System.out.println("One or more of your levels is 0%. Your session will now be terminated.");
                                status = "DELETE FROM " + Database.Table_Troops +
                                        " WHERE " + Database.Column_ID + "= " + ID;
                                ServerOutput.println(status); // THIS IS SENDING THE SERVER THE INPUT
                                System.exit(0);
                            } else if (location >= 10) {
                                System.out.println("You have moved 10 miles. You have reached your goal. Your session will now terminate");
                                status = "DELETE FROM " + Database.Table_Troops +
                                        " WHERE " + Database.Column_ID + "= " + ID;
                                ServerOutput.println(status); // THIS IS SENDING THE SERVER THE INPUT
                                System.exit(0);
                            } else ;
                            break;
                        }

                            case "3":
                                System.out.println(" You have requested a re-supply and first-aid");
                                ammo = max_ammo;
                                health = max_health;
                                supplies = max_supplies;
                                ammo = max_ammo;

                                status = "UPDATE " + Database.Table_Troops + " SET "
                                        + Database.Column_Health + " = " + health + ", "
                                        + Database.Column_Supplies + " = " + supplies + ", "
                                        + Database.Column_Ammo + " = " + ammo + ", "
                                        + Database.Column_Location + " = " + location
                                        + " WHERE " + Database.Column_ID + " = " + ID;

                                ServerOutput.println(status); // THIS IS SENDING THE SERVER THE INPUT
                                break;

                            case "4":
                                System.out.println("You are now exiting");
                                status = "DELETE FROM " + Database.Table_Troops +
                                        " WHERE " + Database.Column_ID + "= " + ID;
                                ServerOutput.println(status); // THIS IS SENDING THE SERVER THE INPUT
                                System.exit(0);

                            default:
                                System.out.println("You have entered an incorrect number");
                                break;
                        }

                        statement.close();
                        conn.close();


                } while (Menuchoice != "4" && location < Goal) ;


            } catch(SQLException e){
                throw new RuntimeException(e);
            } catch(IOException e){
                throw new RuntimeException(e);
            }

        }



    }

















