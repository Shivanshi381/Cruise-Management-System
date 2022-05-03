import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class UpdateBooking extends JDialog {
    private JTextField tfdays;
    private JTextField tfcrname;
    private JTextField tfnoofppl;
    private JButton updateDetailsButton;
    private JButton cancelButton;
    private JPanel update;
    private JTextField tfusername;

    public UpdateBooking(JFrame parent)
    {
        //creates a model-less dialog with specified frame as the owner if null then hidden frame is the owner.
        super(parent);
        setTitle("Create a new user");
        setContentPane(update);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // actionListner is an inbuilt java interface which is notified whenever a button is clicked.
        // java.awt.event package
        // steps: 1. implement ActionListner 2. register componenet with the listner 3. override the actionPerformed method
        // here we have used the anonymous class method to implement this

        updateDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                updateTickets();

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

    private void updateTickets() {
        String username = tfusername.getText();
        String crname = tfcrname.getText();
        String  guests = tfnoofppl.getText();
        String depart = tfdays.getText();
        int noofguests= Integer.parseInt(guests);
        float cost = noofguests * 700;
        if(crname.isEmpty() || guests.isEmpty()|| depart.isEmpty() ){
            JOptionPane.showMessageDialog(UpdateBooking.this,
                    "Please Enter all Fields",
                    "try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        booking= updateBookingToDatabase(username,crname, guests, depart, cost);
        if(booking != null)
        {
            dispose();// to release all the native screen resources
        }
        else{
            JOptionPane.showMessageDialog(UpdateBooking.this,
                    "Failed to register new user",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }



    }

    public Booking booking;

    private Booking updateBookingToDatabase(String username, String crname, String guests, String depart, float cost) {
        Booking booking = null;
        int noofguests= Integer.parseInt(guests);

        final String DB_URL ="jdbc:mysql://localhost:3306/testusers?serverTimezone=UTC";
        final String USERNAME= "root";
        final String PASSWORD = "root1234";
        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            //prepared statement is used because we want to use input parameters in runtime
            String sql ="  UPDATE booking set crname=?, noofppl=?, dateofdeparture=?, cost=? where name=? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,crname);
            preparedStatement.setInt(2,noofguests);
            preparedStatement.setString(3, depart);
            preparedStatement.setFloat(4,cost);
            preparedStatement.setString(5,username);


            //executeUpdate returns 0 for NULL Sql statements
            int addedRows = preparedStatement.executeUpdate();
            if(addedRows >0){
                booking = new Booking();
                booking.name = username;
                booking.crname= crname;
                booking.dateofdeparture= depart;
                booking.noofppl= guests;
                booking.cost = cost ;
            }
            conn.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return booking;

    }

    public static void main(String[] args) {
        UpdateBooking ubk = new UpdateBooking(null);
        Booking booking = ubk.booking;
        if(booking!=null)
        {
            JOptionPane.showMessageDialog(ubk,
                    "Booked "+booking.noofppl+" tickets for cruise "+ booking.crname +" Pay Rs." + booking.cost,
                    "Update Successful",
                    JOptionPane.ERROR_MESSAGE);

        }
        else{
            System.out.println("Update Cancelled");
        }

    }

}
