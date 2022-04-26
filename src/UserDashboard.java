import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class UserDashboard extends JFrame{
    private JPanel udb;
    private JButton newBookingButton;
    private JButton updateExistingBookingButton;
    private JButton deleteBookingButton;
    private JLabel chlabel;

        public UserDashboard() {
            setTitle("User Dashboard");
            setContentPane(udb);
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
            newBookingButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    NewBooking nbk = new NewBooking(UserDashboard.this);
                    Booking booking = nbk.booking;
                    if(booking!=null)
                    {
//            System.out.println("Successful Booking of tickets"+ booking.name);
                        JOptionPane.showMessageDialog(nbk,
                                "Booked "+booking.noofppl+" tickets for cruise "+ booking.crname +" Pay Rs." + booking.cost,
                                "Booking Successful",
                                JOptionPane.INFORMATION_MESSAGE);

                    }
                    else{
                        System.out.println("booking Cancelled");
                    }

                }
            });
            updateExistingBookingButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    UpdateBooking ubk = new UpdateBooking(null);
                    Booking booking = ubk.booking;
                    if(booking!=null)
                    {
//            System.out.println("Successful Booking of tickets"+ booking.name);
                        JOptionPane.showMessageDialog(ubk,
                                "Booked "+booking.noofppl+" tickets for cruise "+ booking.crname +" Pay Rs." + booking.cost,
                                "Update Successful",
                                JOptionPane.ERROR_MESSAGE);

                    }
                    else{
                        System.out.println("Update Cancelled");
                    }
                }
            });


            deleteBookingButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DeleteBooking dbk = new DeleteBooking(null);

                    Booking booking = dbk.booking;

                    if(booking!=null)
                    {
//            System.out.println("Successful Booking of tickets"+ booking.name);
                        JOptionPane.showMessageDialog(dbk,
                                "Booking of " + booking.name + " deleted",
                                "Deletion Successful",
                                JOptionPane.ERROR_MESSAGE);

                    }
                    else{
                        System.out.println("Deletion Cancelled");
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
        UserDashboard myForm = new UserDashboard();
    }
    }
