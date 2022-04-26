import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DeleteBooking extends JDialog{
    private JTextField tfusername;
    private JButton deleteBookingButton;
    private JButton cancelButton;
    private JPanel del;


    public DeleteBooking(JFrame parent)
    {
        super(parent);
        setTitle("Create a new user");
        setContentPane(del);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        deleteBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTickets();

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

    private void deleteTickets() {
        String username = tfusername.getText();
        if(username.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Please Enter all Fields",
                    "try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
//            Booking booking = null;
        booking= deleteBookingfromDatabase(username);
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

    private Booking deleteBookingfromDatabase(String username) {
        Booking booking = null;

        final String DB_URL ="jdbc:mysql://localhost:3306/testusers?serverTimezone=UTC";
        final String USERNAME= "root";
        final String PASSWORD = "root1234";
        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();
            String sql ="  delete from booking where name=? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,username);
            int addedRows = preparedStatement.executeUpdate();
            if(addedRows >0){
                booking = new Booking();
                booking.name = username;
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

}
