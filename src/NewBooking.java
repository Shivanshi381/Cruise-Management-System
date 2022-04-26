import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class NewBooking extends JDialog {
    private JTextField tfusername;
    private JTextField tfcrname;
    private JTextField tfguests;
    private JTextField tfDeparture;
    private JButton bookTicketsButton;
    private JButton cancelButton;
    private JPanel newbk;

    public NewBooking(JFrame parent)
    {
        super(parent);
        setTitle("Create a new user");
        setContentPane(newbk);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        bookTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookTickets();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);

    }

    private void bookTickets() {


            String username = tfusername.getText();
            String crname = tfcrname.getText();
            String  guests = tfguests.getText();
            String depart = tfDeparture.getText();
            int noofguests= Integer.parseInt(guests);
            float cost = noofguests * 700;
            if(username.isEmpty()|| crname.isEmpty() || guests.isEmpty()|| depart.isEmpty() ){
                JOptionPane.showMessageDialog(this,
                        "Please Enter all Fields",
                        "try again",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
//            Booking booking = null;
            booking= addBookingToDatabase(username,crname, guests, depart, cost);
            if(booking != null)
            {
                dispose();
            }
            else{
                JOptionPane.showMessageDialog(this,
                        "Failed to register new user",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
            }



        }

        public Booking booking;

    private Booking addBookingToDatabase(String username, String crname, String guests, String depart, float cost) {
        Booking booking = null;
        int noofguests= Integer.parseInt(guests);

        final String DB_URL ="jdbc:mysql://localhost:3306/testusers?serverTimezone=UTC";
        final String USERNAME= "root";
        final String PASSWORD = "root1234";
        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();
            String sql ="INSERT INTO booking (name, crname, noofppl, dateofdeparture, cost)"+ "VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,crname);
            preparedStatement.setInt(3, noofguests);
            preparedStatement.setString(4,depart);
            preparedStatement.setFloat(5,cost);

            int addedRows = preparedStatement.executeUpdate();
            if(addedRows >0){
                booking = new Booking();
                booking.name = username;
                booking.crname= crname;
                booking.dateofdeparture= depart;
                booking.noofppl= guests;
                booking.cost = cost ;
            }
            stmt.close();
            conn.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return booking;

    }
    public static void main(String[] args) {
        NewBooking nbk = new NewBooking(null);
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


}
