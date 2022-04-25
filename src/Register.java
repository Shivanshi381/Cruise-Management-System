import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Register extends JDialog {
    private JTextField tfUsername;
    private JTextField tfemail;
    private JTextField tfPhoneno;
    private JPasswordField pfpassword;
    private JButton createUserButton;
    private JButton cancelButton;
    private JPasswordField pfConfirmpwd;
    private JPanel reg;

    public Register(JFrame parent)
    {
        super(parent);
        setTitle("Create a new user");
        setContentPane(reg);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
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

    private void registerUser() {

        String username = tfUsername.getText();
        String password = String.valueOf(pfpassword.getPassword());
        String email = tfemail.getText();
        String phno = tfPhoneno.getText();
        String confirmPassword = String.valueOf(pfConfirmpwd.getPassword());

        if(username.isEmpty()|| email.isEmpty() || phno.isEmpty()|| password.isEmpty() || confirmPassword.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Please Enter all Fields",
                    "try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        user= addUserToDatabase(username, email, phno, password);
        if(user != null)
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
    public User user;

    private User addUserToDatabase(String username, String email, String phno, String password) {
        User user = null;

        final String DB_URL ="jdbc:mysql://localhost:3306/testusers?serverTimezone=UTC";
        final String USERNAME= "root";
        final String PASSWORD = "root1234";
        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();
            String sql ="INSERT INTO users (name, email, phone, pass)"+ "VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3, phno);
            preparedStatement.setString(4,password);

            int addedRows = preparedStatement.executeUpdate();
            if(addedRows >0){
                user = new User();
                user.name = username;
                user.email= email;
                user.phone= phno;
            }
            stmt.close();
            conn.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return user;

    }

    public static void main(String[] args) {
        Register register = new Register(null);
        User user = register.user;
        if(user!=null)
        {
            System.out.println("Successful registeration of"+ user.name);

        }
        else{
            System.out.println("Registeration Cancelled");
        }

    }
}
