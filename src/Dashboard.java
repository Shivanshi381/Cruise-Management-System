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
                UserDashboard myForm = new UserDashboard(user.name);

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
                UserDashboard myForm = new UserDashboard(user.name);
            }
        });
    }
    public static void main(String[] args) {

        Dashboard myForm = new Dashboard();

    }
}
