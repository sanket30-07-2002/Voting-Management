package Voting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class RegisterUserFrame extends JFrame {
    private JTextField usernameField;
    private JTextField voterIdField;
    private JTextField dobField;
    private JTextField addressField;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public RegisterUserFrame() {
        setTitle("Register User");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a panel for background color
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.setBackground(new Color(59, 245, 77)); // Set background color to white

        // Create a panel for form components
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(59, 245, 77));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setLayout(new GridBagLayout());
        
        backgroundPanel.add(formPanel, BorderLayout.CENTER);
        add(backgroundPanel);
        
        placeComponents(formPanel);
    }

    private void placeComponents(JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        usernameField = new JTextField(25);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(usernameField, gbc);

        JLabel voterIdLabel = new JLabel("Voter ID:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(voterIdLabel, gbc);

        voterIdField = new JTextField(25);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(voterIdField, gbc);

        JLabel dobLabel = new JLabel("DOB (yyyy-MM-dd):");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(dobLabel, gbc);

        dobField = new JTextField(25);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(dobField, gbc);

        JLabel addressLabel = new JLabel("Address:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(addressLabel, gbc);

        addressField = new JTextField(25);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(addressField, gbc);

        JButton registerButton = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(registerButton, gbc);

        // Style the button
        registerButton.setBackground(new Color(0, 123, 255));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String voterId = voterIdField.getText();
            String dob = dobField.getText();
            String address = addressField.getText();

            if (username.isEmpty() || voterId.isEmpty() || dob.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidDate(dob)) {
                JOptionPane.showMessageDialog(null, "Invalid date format. Please use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (registerUser(username, voterId, dob, address)) {
                new VotingFrame(voterId).setVisible(true);
                dispose(); 
            }
        });
    }

    private boolean isValidDate(String dateStr) {
        try {
            DATE_FORMAT.setLenient(false);
            DATE_FORMAT.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean registerUser(String username, String voterId, String dob, String address) {
        try (Connection connection = Main.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, voter_id, dob, address, has_voted) VALUES (?, ?, ?, ?, FALSE)")) {
            statement.setString(1, username);
            statement.setString(2, voterId);
            statement.setDate(3, Date.valueOf(dob));
            statement.setString(4, address);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "User registered successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error registering user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
}

