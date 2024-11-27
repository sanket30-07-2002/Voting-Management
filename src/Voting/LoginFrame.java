//package Voting;
//
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//class LoginFrame extends JFrame {
//    private JTextField emailField;
//    private JPasswordField passwordField;
//
//    public LoginFrame() {
//        setTitle("Admin Login");
//        setSize(300, 150);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        JPanel panel = new JPanel();
//        add(panel);
//        placeComponents(panel);
//
//        setVisible(true);
//    }
//
//    private void placeComponents(JPanel panel) {
//        panel.setLayout(null);
//
//        JLabel emailLabel = new JLabel("Email:");
//        emailLabel.setBounds(10, 20, 80, 25);
//        panel.add(emailLabel);
//
//        emailField = new JTextField(20);
//        emailField.setBounds(100, 20, 165, 25);
//        panel.add(emailField);
//
//        JLabel passwordLabel = new JLabel("Password:");
//        passwordLabel.setBounds(10, 50, 80, 25);
//        panel.add(passwordLabel);
//
//        passwordField = new JPasswordField(20);
//        passwordField.setBounds(100, 50, 165, 25);
//        panel.add(passwordField);
//
//        JButton loginButton = new JButton("Login");
//        loginButton.setBounds(10, 80, 80, 25);
//        panel.add(loginButton);
//
//        loginButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String email = emailField.getText();
//                String password = new String(passwordField.getPassword());
//
//                if (authenticateAdmin(email, password)) {
//                    new AdminFrame().setVisible(true);
//                    dispose();
//                } else {
//                    JOptionPane.showMessageDialog(null, "Invalid email or password");
//                }
//            }
//        });
//    }
//
//    private boolean authenticateAdmin(String email, String password) {
//        try (Connection connection = Main.getConnection();
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM admin WHERE email = ? AND password = ?")) {
//            statement.setString(1, email);
//            statement.setString(2, password);
//            ResultSet resultSet = statement.executeQuery();
//            return resultSet.next();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//}




package Voting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Admin Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Set a custom panel with background color
        JPanel panel = new JPanel();
        panel.setBackground(new Color(34, 49, 63)); // Dark background color
        panel.setLayout(null);
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        // Email Label
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 30, 80, 25);
        emailLabel.setForeground(Color.WHITE); // Set label color
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Set font
        panel.add(emailLabel);

        // Email Field
        emailField = new JTextField(20);
        emailField.setBounds(120, 30, 200, 25);
        emailField.setBackground(new Color(52, 73, 94)); // Darker background for text field
        emailField.setForeground(Color.WHITE); // Text color
        emailField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding inside the text field
        panel.add(emailField);

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 70, 80, 25);
        passwordLabel.setForeground(Color.WHITE); // Set label color
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Set font
        panel.add(passwordLabel);

        // Password Field
        passwordField = new JPasswordField(20);
        passwordField.setBounds(120, 70, 200, 25);
        passwordField.setBackground(new Color(52, 73, 94)); // Darker background for text field
        passwordField.setForeground(Color.WHITE); // Text color
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding inside the text field
        panel.add(passwordField);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(120, 110, 100, 30);
        loginButton.setBackground(new Color(41, 128, 185)); // Blue background color
        loginButton.setForeground(Color.WHITE); // Text color
        loginButton.setFont(new Font("Arial", Font.BOLD, 14)); // Font
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(41, 128, 185), 2)); // Border color
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                if (authenticateAdmin(email, password)) {
                    new AdminFrame().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid email or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private boolean authenticateAdmin(String email, String password) {
        try (Connection connection = Main.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM admin WHERE email = ? AND password = ?")) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
