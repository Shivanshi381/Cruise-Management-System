import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JDialog {
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton loginButton;
    private JButton cancelButton;
    private JPanel loginPanel;

    public Login(JFrame parent)
    {
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = tfUsername.getText();
                String password = String.valueOf(pfPassword.getPassword());

                user = getAuthenticatedUser(username, password);

                if (user != null){
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(Login.this,
                            "Incorrect Username or Password",
                            "Try Again!",
                            JOptionPane.ERROR_MESSAGE);
                }
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
    public User user;


    private User getAuthenticatedUser(String username, String password) {
        User user = null;
        final String DB_URL ="jdbc:mysql://localhost:3306/testusers?serverTimezone=UTC";
        final String USERNAME= "root";
        final String PASSWORD = "root1234";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM users WHERE name=? AND pass=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                user = new User();
                user.name = resultSet.getString("name");
                user.email = resultSet.getString("email");
                user.phone= resultSet.getString("phone");
                user.password = resultSet.getString("pass");
            }

            conn.close();

        }
        catch (Exception e)
        {
            System.err.println("Error occured"+ e.getMessage());
        }
        return user;
    }

    public static void main( String[] args)
    {
        Login login = new Login(null);
        User user = login.user;
        if(user !=null)
        {
            System.out.println("Successful authentication of:" + user.name+ user.email+ user.phone);


        }
        else
        {
            System.out.println("Authentication Cancelled");
        }

    }
}
