package Voting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class VotingFrame extends JFrame
{
    private JButton[] voteButtons;
    private List<Candidate> candidates;
    private String voterId;

    public VotingFrame(String voterId)
    {
        this.voterId = voterId;
        setTitle("Vote for Candidates");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create and style the main panel
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.setBackground(new Color(240, 240, 240)); // Light gray background
        
        // Create and style the content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(new Color(205, 242, 94));
        
        backgroundPanel.add(contentPanel, BorderLayout.CENTER);
        add(backgroundPanel);
        
        placeComponents(contentPanel);
    }

    private void placeComponents(JPanel panel)
    {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("Select a candidate to vote:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        candidates = getCandidates();
        voteButtons = new JButton[candidates.size()];

        for (int i = 0; i < candidates.size(); i++) 
        {
            Candidate candidate = candidates.get(i);
            JButton voteButton = new JButton("Vote for " + candidate.name + " (" + candidate.party + ")");
            voteButton.setBackground(new Color(0, 123, 255));
            voteButton.setForeground(Color.WHITE);
            voteButton.setFocusPainted(false);
            voteButton.setFont(new Font("Arial", Font.BOLD, 14));
            voteButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            voteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.gridwidth = 2;
            panel.add(voteButton, gbc);

            voteButtons[i] = voteButton;

            voteButton.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    if (hasUserVoted(voterId))
                    {
                        JOptionPane.showMessageDialog(null, "You have already voted.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    castVote(candidate.id);
                    markUserAsVoted(voterId);
                }
            });
        }
    }

    private List<Candidate> getCandidates()
    {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection connection = Main.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id, name, party FROM candidates"))
        {
            while (resultSet.next())
            {
                candidates.add(new Candidate(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("party")));
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return candidates;
    }

    private boolean hasUserVoted(String voterId)
    {
        try (Connection connection = Main.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT has_voted FROM users WHERE voter_id = ?")) {
            statement.setString(1, voterId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) 
            {
                return resultSet.getBoolean("has_voted");
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return false;
    }

    private void castVote(int candidateId)
    {
        try (Connection connection = Main.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE candidates SET votes = votes + 1 WHERE id = ?")) 
        {
            statement.setInt(1, candidateId);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Vote cast successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(null, "Error casting vote: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void markUserAsVoted(String voterId)
    {
        try (Connection connection = Main.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE users SET has_voted = TRUE WHERE voter_id = ?")) 
        {
            statement.setString(1, voterId);
            statement.executeUpdate();
        } 
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Error updating user vote status: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private static class Candidate
    {
        int id;
        String name;
        String party;

        Candidate(int id, String name, String party)
        {
            this.id = id;
            this.name = name;
            this.party = party;
        }
    }
}


