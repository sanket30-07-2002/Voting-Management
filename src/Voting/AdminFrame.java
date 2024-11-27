package Voting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AdminFrame extends JFrame {
    public AdminFrame() {
        setTitle("Admin Dashboard");
        setSize(400, 400); // Increased size to fit improved UI
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.setBackground(new Color(10, 10, 10)); // Light gray background
        add(backgroundPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setOpaque(false); // Transparent to show background panel color
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around buttons
        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);

        placeComponents(buttonPanel);
    }

    private void placeComponents(JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton addCandidateButton = createStyledButton("Add Candidate");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(addCandidateButton, gbc);

        JButton updateCandidateButton = createStyledButton("Update Candidate");
        gbc.gridy = 1;
        panel.add(updateCandidateButton, gbc);

        JButton deleteCandidateButton = createStyledButton("Delete Candidate");
        gbc.gridy = 2;
        panel.add(deleteCandidateButton, gbc);

        JButton registerUserButton = createStyledButton("Register User");
        gbc.gridy = 3;
        panel.add(registerUserButton, gbc);

        JButton viewVotesButton = createStyledButton("View Votes");
        gbc.gridy = 4;
        panel.add(viewVotesButton, gbc);

        addCandidateButton.addActionListener(e -> new AddCandidateFrame().setVisible(true));
        updateCandidateButton.addActionListener(e -> new UpdateCandidateFrame().setVisible(true));
        deleteCandidateButton.addActionListener(e -> new DeleteCandidateFrame().setVisible(true));
        registerUserButton.addActionListener(e -> new RegisterUserFrame().setVisible(true));
        viewVotesButton.addActionListener(e -> new ViewVotesFrame().setVisible(true));
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0, 123, 255)); // Blue background
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 123, 255), 2));
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(200, 40));

        // Adding hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(28, 115, 211)); // Darker blue on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 123, 255)); // Original blue
            }
        });

        return button;
    }
}

