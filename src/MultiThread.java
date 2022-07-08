import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.*;

public class MultiThread extends Thread{

    private Socket socket;

    public MultiThread(Socket socket){
        this.socket = socket;
    }



    @Override
    public void run() {
        try {
            while (true) {
                BufferedReader input = new BufferedReader( // When a client connects, the server will use this input stream to received data
                        new InputStreamReader(socket.getInputStream())); // need to review this topic

                PrintWriter output = new PrintWriter(socket.getOutputStream(), true); //  When a client connects, the server will use this output stream to received data

                Connection conn = DriverManager.getConnection(Database.Database);
                Statement statement = conn.createStatement();


          //      output.println(); // this is sending the client information

                String InitialStatus = input.readLine(); // reads line from client stream
                statement.execute(InitialStatus); // ClientInput inserts the values into the table
                System.out.println(InitialStatus);






                statement.close();
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace(); // see location of errors
        } catch (IOException e){
            System.out.println("Thread1 error:" + e.getMessage());
        }finally {
            try {
                socket.close();
            } catch (IOException e){
                System.out.println("Thread2 error: " + e.getMessage());
            }
        }
    }

    public static String showMenu() {


        return "Please enter the corresponding number to choose one of the following options:"
                + "\n" + "1 Show current status "
                + "\n" + "2 Move "
                + "\n" + "3 Re-supply "
                + "\n" + "4 Exit";

          /*  Scanner input = new Scanner(System.in);

            String menuchoice = input.nextLine();

            switch (menuchoice) {
                case "1":
                   // showCurrentStatus("1");
                case "2":
                    //Move("2");
                case "3":
                    //re-supply
                case "4":
                    //exit
            }*/


    }
}

