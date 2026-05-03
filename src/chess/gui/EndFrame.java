package chess.gui;

import javax.swing.*;
import java.awt.*;

public class EndFrame extends JFrame {
    public EndFrame(String result, int gamePoints, int totalPoints) {
        initUI(result, gamePoints, totalPoints);
        setVisible(true);
    }

    private void initUI(String result, int gamePoints, int totalPoints) {

        setTitle("Game Over");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Game Finished", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JLabel resultLabel = new JLabel("Result: " + result, SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel gamePointsLabel = new JLabel(
                "Game points: " + format(gamePoints),
                SwingConstants.CENTER
        );
        gamePointsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gamePointsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel totalPointsLabel = new JLabel(
                "Total points: " + totalPoints,
                SwingConstants.CENTER
        );
        totalPointsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        totalPointsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        center.add(resultLabel);
        center.add(Box.createVerticalStrut(15));
        center.add(gamePointsLabel);
        center.add(Box.createVerticalStrut(10));
        center.add(totalPointsLabel);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JButton menuBtn = new JButton("Main Menu");
        menuBtn.addActionListener(e -> {
            dispose();
            new MainMenuFrame();
        });

        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(e -> System.exit(0));

        buttons.add(menuBtn);
        buttons.add(exitBtn);

        add(title, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private String format(int points) {
        return (points > 0 ? "+" : "") + points;
    }
}
