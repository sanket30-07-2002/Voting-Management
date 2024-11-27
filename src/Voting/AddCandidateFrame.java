package Voting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class AddCandidateFrame extends JFrame {
    private JTextField nameField;
    private JTextField partyField;
    private JTextField addressField;

    public AddCandidateFrame() {
        setTitle("Add Candidate");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create and style the main panel
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.setBackground(new Color(245, 245, 245)); 

        // Create and style the content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(new Color(146, 238, 240));
        
        backgroundPanel.add(contentPanel, BorderLayout.CENTER);
        add(backgroundPanel);

        placeComponents(contentPanel);
    }

    private void placeComponents(JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Name
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // Party
        JLabel partyLabel = new JLabel("Party:");
        partyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(partyLabel, gbc);

        partyField = new JTextField(20);
        partyField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(partyField, gbc);

        // Address
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(addressLabel, gbc);

        addressField = new JTextField(20);
        addressField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(addressField, gbc);

        // Add Button
        JButton addButton = new JButton("Add");
        addButton.setBackground(new Color(0, 123, 255));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(addButton, gbc);

        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String party = partyField.getText();
            String address = addressField.getText();
            if (name.isEmpty() || party.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            addCandidate(name, party, address);
        });
    }

    private void addCandidate(String name, String party, String address) {
        try (Connection connection = Main.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO candidates (name, party, address) VALUES (?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, party);
            statement.setString(3, address);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Candidate added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding candidate: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
