import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Dashboard extends JFrame {
    private JPanel dash;
    private JLabel lbAdmin;
    private JButton btnRegister;
    private JButton loginButton;

    public Dashboard() {
        setTitle("Dashborad");
        setContentPane(dash);
        setMinimumSize(new Dimension(500, 429));
        setSize(1200, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        /*boolean hasRegistredUsers = */
        connectToDatabase();
////        if (hasRegistredUsers) {
//            //show Login form
//            Login loginForm = new Login(this);
//            User user = loginForm.user;
//
//            if (user != null) {
////                lbAdmin.setText("User: " + user.name);
//                setLocationRelativeTo(null);
//                setVisible(true);
//            }
//            else {
//                dispose();
//            }
////        }
////        else {
//            //show Registration form
//            Register registrationForm = new Register(this);
//            User user = registrationForm.user;
//
//            if (user != null) {
////                lbAdmin.setText("User: " + user.name);
//                setLocationRelativeTo(null);
//                setVisible(true);
//            }
//            else {
//                dispose();
//            }
////        }
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register registrationForm = new Register(Dashboard.this);
                User user = registrationForm.user;

                if (user != null) {
                    JOptionPane.showMessageDialog(Dashboard.this,
                            "User: " + user.name,
                            "Successful Registration",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login(Dashboard.this);
                User user = login.user;
                if (user != null) {
                    JOptionPane.showMessageDialog(Dashboard.this,
                            "New user: " + user.name,
                            "Successful Authentication",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    private void connectToDatabase() {
//        boolean hasRegistredUsers = false;

        final String MYSQL_SERVER_URL = "jdbc:mysql://localhost/";
        final String DB_URL = "jdbc:mysql://localhost/MyStore?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "root1234";

        try{
            //First, connect to MYSQL server and create the database if not created
            Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS MyStore");
            statement.close();
            conn.close();

            //Second, connect to the database and create the table "users" if cot created
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INT( 10 ) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "name VARCHAR(200) NOT NULL,"
                    + "email VARCHAR(200) NOT NULL UNIQUE,"
                    + "phone VARCHAR(200),"
                    + "address VARCHAR(200),"
                    + "password VARCHAR(200) NOT NULL"
                    + ")";
            statement.executeUpdate(sql);

            //check if we have users in the table users
            statement = conn.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM users");
//
//            if (resultSet.next()) {
//                int numUsers = resultSet.getInt(1);
//                if (numUsers > 0) {
//                    hasRegistredUsers = true;
//                }
//            }

            statement.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }

//        return hasRegistredUsers;
    }



    public static void main(String[] args) {
        Dashboard myForm = new Dashboard();
    }
}
