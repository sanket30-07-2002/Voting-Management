package Voting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class UpdateCandidateFrame extends JFrame {
    private JTextField idField;
    private JTextField nameField;
    private JTextField partyField;
    private JTextField addressField;

    public UpdateCandidateFrame() {
        setTitle("Update Candidate");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create and style the main panel
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.setBackground(new Color(240, 248, 255)); // Light blue background

        // Create and style the content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(new Color (237, 225, 216));
        
        backgroundPanel.add(contentPanel, BorderLayout.CENTER);
        add(backgroundPanel);

        placeComponents(contentPanel);
    }

    private void placeComponents(JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // ID
        JLabel idLabel = new JLabel("ID:");
        idLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(idLabel, gbc);

        idField = new JTextField(20);
        idField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(idField, gbc);

        // Name
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(nameLabel, gbc);

        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // Party
        JLabel partyLabel = new JLabel("Party:");
        partyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(partyLabel, gbc);

        partyField = new JTextField(20);
        partyField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(partyField, gbc);

        // Address
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(addressLabel, gbc);

        addressField = new JTextField(20);
        addressField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(addressField, gbc);

        // Update Button
        JButton updateButton = new JButton("Update");
        updateButton.setBackground(new Color(40, 167, 69)); // Green background
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
        updateButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        updateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(updateButton, gbc);

        updateButton.addActionListener(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String party = partyField.getText();
            String address = addressField.getText();
            if (!id.isEmpty()) {
                updateCandidate(id, name, party, address);
            } else {
                JOptionPane.showMessageDialog(null, "ID is required", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void updateCandidate(String id, String name, String party, String address) {
        try (Connection connection = Main.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE candidates SET name = ?, party = ?, address = ? WHERE id = ?")) {
            statement.setString(1, name);
            statement.setString(2, party);
            statement.setString(3, address);
            statement.setString(4, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Candidate updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No candidate found with the provided ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating candidate: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
