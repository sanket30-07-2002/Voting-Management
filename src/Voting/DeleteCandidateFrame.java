package Voting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class DeleteCandidateFrame extends JFrame {
    private JTextField idField;

    public DeleteCandidateFrame() {
        setTitle("Delete Candidate");
        setSize(500, 300);
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
        contentPanel.setBackground(new Color(6, 92, 92));

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

        // Delete Button
        JButton deleteButton = new JButton("Delete");
        deleteButton.setBackground(new Color(220, 53, 69)); // Red background
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(deleteButton, gbc);

        deleteButton.addActionListener(e -> {
            String id = idField.getText();
            if (!id.isEmpty()) {
                deleteCandidate(id);
            } else {
                JOptionPane.showMessageDialog(null, "ID is required", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void deleteCandidate(String id) {
        try (Connection connection = Main.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM candidates WHERE id = ?")) {
            statement.setString(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Candidate deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No candidate found with the provided ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting candidate: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
