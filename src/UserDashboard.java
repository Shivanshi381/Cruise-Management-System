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

    public UserDashboard()
    {

    }

        public UserDashboard( String uname) {
            setTitle("User Dashboard");
            setContentPane(udb);
            setMinimumSize(new Dimension(500, 429));
            setSize(1200, 700);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setVisible(true);
            chlabel.setText("Logged in:" + uname);
            newBookingButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    NewBooking nbk = new NewBooking(UserDashboard.this);
                    Booking booking = nbk.booking;
                    if(booking!=null)
                    {
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


    public static void main(String[] args) {
            User user = new User();
        UserDashboard myForm = new UserDashboard(user.name);
    }
    }
