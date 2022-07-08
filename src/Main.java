import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        try (ServerSocket ServerSocket = new ServerSocket(1234)) {
            while (true) {
                new MultiThread(ServerSocket.accept()).start();
                //Socket socket = ServerSocket.accept();

                System.out.println("Client Connected to Server");
               /*BufferedReader Clientinputstream = new BufferedReader( // When a client connects, the server will use this input stream to receive data
                        new InputStreamReader(socket.getInputStream())); // need to review this topic
                PrintWriter Clientoutput = new PrintWriter(socket.getOutputStream(), true); //  When a client connects, the server will use this output stream to send data
*/
                Connection conn = DriverManager.getConnection(Database.Database);
                Statement statement = conn.createStatement();

                statement.execute("CREATE TABLE IF NOT EXISTS " + Database.Table_Troops +
                        " (" + Database.Column_ID + " INTEGER, "
                        + Database.Column_Health + " INTEGER, "
                        + Database.Column_Supplies + " INTEGER,"
                        + Database.Column_Ammo + " INTEGER, "
                        + Database.Column_Location + " INTEGER)");

/*
                Clientoutput.println(Soldier1.ViewinitialStatus()); // this is sending the client the initial status information
                Clientoutput.println(Database.showMenu());*/

/*
           String InitialStatus = Clientinputstream.readLine(); // reads line from client stream
                statement.execute(InitialStatus); // ClientInput inserts the values into the table
               InitialStatus = "The Soldiers initial status has been saved";
                System.out.println(InitialStatus); // checking that it works
*/


   //             Clientoutput.println("The server has received the following: " + InitialStatus ); // this echoes on the client part

                statement.close();
                conn.close();

            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace(); // see location of errors
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace(); // see location of errors
        }

    }


}
