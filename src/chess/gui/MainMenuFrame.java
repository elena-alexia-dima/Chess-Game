package chess.gui;

import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {
    public MainMenuFrame() {
        initUI();
        setVisible(true);
    }

    private void initUI() {
        setTitle("Chess Game - Main Menu");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Chess Game", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        add(title, BorderLayout.NORTH);

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(4, 1, 0, 15));
        buttons.setBorder(BorderFactory.createEmptyBorder(20, 80, 20, 80));

        JButton startBtn = new JButton("Start Game");
        startBtn.setFont(new Font("Arial", Font.BOLD, 16));
        startBtn.addActionListener(e -> {
            dispose();
            new GameFrame();
        });

        JButton continueBtn = new JButton("Continue Game");
        continueBtn.setFont(new Font("Arial", Font.BOLD, 16));
        continueBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "No saved game available.",
                    "Continue Game",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        JButton exitBtn = new JButton("Exit");
        exitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        exitBtn.addActionListener(e -> System.exit(0));

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 16));
        logoutBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Logged out successfully.",
                    "Logout",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        });

        buttons.add(startBtn);
        buttons.add(continueBtn);
        buttons.add(logoutBtn);
        buttons.add(exitBtn);


        add(buttons, BorderLayout.CENTER);
    }
}
