package Voting;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main 
{

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }

    public static Connection getConnection() 
    {
        try
        {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/voting_system", "root", "S@nket2002");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}