package Voting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class ViewVotesFrame extends JFrame 
{
    private JTextArea votesArea;

    public ViewVotesFrame() 
    {
        setTitle("View Votes");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set the background color and layout
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.setBackground(new Color(250, 148, 15)); // Light gray background

        // Create a panel for form components
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(new Color(250, 148, 15)); // White background
        
        // Create and style the text area
        votesArea = new JTextArea();
        votesArea.setEditable(false);
        votesArea.setFont(new Font("Arial", Font.PLAIN, 14));
        votesArea.setLineWrap(true);
        votesArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(votesArea);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Create and style the refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(0, 123, 255));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add button to the content panel
        contentPanel.add(refreshButton, BorderLayout.SOUTH);
        
        backgroundPanel.add(contentPanel, BorderLayout.CENTER);
        add(backgroundPanel);
        
        refreshButton.addActionListener(e -> refreshVotes());
    }

    private void refreshVotes()
    {
        StringBuilder sb = new StringBuilder();
        try (Connection connection = Main.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT name, party, votes FROM candidates");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String party = resultSet.getString("party");
                int votes = resultSet.getInt("votes");
                sb.append(name).append(" (").append(party).append("): ").append(votes).append(" votes\n");
            }
        } 
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(this, "Error retrieving votes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        votesArea.setText(sb.toString());
    }
}
