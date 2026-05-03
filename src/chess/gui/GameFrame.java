package chess.gui;

import chess.game.Game;
import chess.game.Player;
import chess.model.Colors;
import chess.model.Position;
import chess.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameFrame extends JFrame {
    private Game game;

    private JButton[][] cells = new JButton[8][8];
    private JLabel statusLabel;

    private Position selected = null;
    private List<Position> possibleMoves = new ArrayList<>();

    private JTextArea humanCaptured;
    private JTextArea computerCaptured;

    private DefaultListModel<String> historyModel = new DefaultListModel<>();
    private JList<String> historyList;
    private int moveNumber = 1;

    public GameFrame() {
        initGame();
        initUI();
        setVisible(true);
    }

    private void initGame() {
        Player computer = new Player("Computer", Colors.WHITE);
        Player human = new Player("You", Colors.BLACK);

        game = new Game(1, computer, human);

        game.addObserver((result, gamePoints, totalPoints) -> {
            dispose();
            new EndFrame(result, gamePoints, totalPoints);
        });

        game.start();

        Timer t = new Timer(600, e -> computerMove());
        t.setRepeats(false);
        t.start();
    }

    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, 0));
        panel.setMinimumSize(new Dimension(200, 0));
        panel.setMaximumSize(new Dimension(200, Integer.MAX_VALUE));
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JLabel title = new JLabel("Move History", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 14));

        historyList = new JList<>(historyModel);
        historyList.setFont(new Font("Monospaced", Font.PLAIN, 12));
        historyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(historyList);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        panel.add(title, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private void addMoveToHistory(Position from, Position to, boolean isComputer) {
        String move = from.toString() + "-" + to.toString();

        if (!isComputer) {
            historyModel.addElement(moveNumber + ". " + "Player "+ move);
        } else {
            historyModel.addElement(moveNumber + ". " + "Computer " + move);
            moveNumber++;
        }

        historyList.ensureIndexIsVisible(historyModel.size() - 1);
    }

    private JPanel createRightPanel() {

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(220, 0));
        panel.setMinimumSize(new Dimension(220, 0));
        panel.setMaximumSize(new Dimension(220, Integer.MAX_VALUE));

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Captured Pieces");
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(10));

        JLabel youLabel = new JLabel("You:");
        panel.add(youLabel);

        humanCaptured = new JTextArea(3, 12);
        humanCaptured.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 18));
        humanCaptured.setEditable(false);
        panel.add(humanCaptured);

        panel.add(Box.createVerticalStrut(10));

        JLabel compLabel = new JLabel("Computer:");
        panel.add(compLabel);

        computerCaptured = new JTextArea(3, 12);
        computerCaptured.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 18));
        computerCaptured.setEditable(false);
        panel.add(computerCaptured);

        panel.add(Box.createVerticalStrut(20));
        JButton resignBtn = new JButton("Resign");
        resignBtn.addActionListener(e -> {

            Player resigning = game.getCurrentPlayer();
            Player winner;

            if (resigning == game.getPlayer1()) {
                winner = game.getPlayer2();
            } else {
                winner = game.getPlayer1();
            }

            game.endByResign(resigning, winner);
        });

        JButton saveBtn = new JButton("Save & Exit");
        saveBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Game saved!");
            dispose();
            new MainMenuFrame();
        });

        JButton backBtn = new JButton("Back to Menu");
        backBtn.addActionListener(e -> {
            dispose();
            new MainMenuFrame();
        });

        panel.add(resignBtn);
        panel.add(Box.createVerticalStrut(5));
        panel.add(saveBtn);
        panel.add(Box.createVerticalStrut(5));
        panel.add(backBtn);

        return panel;
    }


    private void initUI() {
        setTitle("Chess Game");
        setSize(1500, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        statusLabel = new JLabel("Your turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(statusLabel, BorderLayout.NORTH);

        add(createBoardPanel(), BorderLayout.CENTER);
        add(createRightPanel(), BorderLayout.EAST);
        add(createHistoryPanel(), BorderLayout.WEST);

        refreshBoard();
    }


    private JPanel createBoardPanel() {
        JPanel board = new JPanel(new GridLayout(8, 8));
        board.setPreferredSize(new Dimension(480, 480));
        board.setMinimumSize(new Dimension(480, 480));
        board.setMaximumSize(new Dimension(480, 480));


        Color light = new Color(242, 19, 181);
        Color dark  = new Color(181, 13, 99);

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {

                JButton cell = new JButton();
                cell.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 36));
                cell.setFocusPainted(false);
                cell.setBorderPainted(false);
                cell.setBackground((r + c) % 2 == 0 ? light : dark);

                final int row = r;
                final int col = c;
                cell.addActionListener(e -> handleClick(row, col));

                cells[r][c] = cell;
                board.add(cell);
            }
        }
        return board;
    }

    private void handleClick(int row, int col) {

        if (game.getCurrentPlayer().getColor() != Colors.BLACK)
            return;

        Position pos = new Position((char) ('A' + col), 8 - row);
        Piece piece = game.getBoard().getPieceAt(pos);

        if (selected != null && possibleMoves.contains(pos)) {
            makeMove(selected, pos);
            clearSelection();
            return;
        }

        if (piece != null && piece.getColor() == Colors.BLACK) {
            selectPiece(pos, row, col);
        } else {
            clearSelection();
        }
    }

    private void selectPiece(Position pos, int row, int col) {
        clearSelection();

        selected = pos;
        Piece p = game.getBoard().getPieceAt(pos);
        possibleMoves = p.getPossibleMoves(game.getBoard());

        cells[row][col].setBackground(Color.YELLOW);

        for (Position m : possibleMoves) {
            int r = 8 - m.getY();
            int c = m.getX() - 'A';
            cells[r][c].setBackground(new Color(144, 238, 144));
        }
    }

    private void makeMove(Position from, Position to) {
        try {
            Player human = game.getPlayer2();
            human.makeMove(from, to, game.getBoard());
            game.addMove(human, from, to);
            addMoveToHistory(from, to, false);


            game.switchPlayer();
            refreshBoard();
            statusLabel.setText("Computer turn");

            Timer t = new Timer(600, e -> computerMove());
            t.setRepeats(false);
            t.start();
            updateCaptured();


        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid move");
        }
    }

    private void clearSelection() {
        selected = null;
        possibleMoves.clear();

        Color light = new Color(242, 19, 181);
        Color dark  = new Color(181, 13, 99);

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                cells[r][c].setBackground((r + c) % 2 == 0 ? light : dark);
            }
        }
    }


    private void refreshBoard() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                cells[r][c].setText("");

                Position pos = new Position((char) ('A' + c), 8 - r);
                Piece p = game.getBoard().getPieceAt(pos);

                if (p != null) {
                    cells[r][c].setText(getUnicode(p));
                }
            }
        }
    }

    private void updateCaptured() {
        humanCaptured.setText("");
        computerCaptured.setText("");

        for (Piece p : game.getPlayer2().getCapturedPieces()) {
            humanCaptured.append(getUnicode(p) + " ");
        }

        for (Piece p : game.getPlayer1().getCapturedPieces()) {
            computerCaptured.append(getUnicode(p) + " ");
        }
    }


    private void computerMove() {
        Player computer = game.getPlayer1();

        List<Position> fromList = new ArrayList<>();
        List<Position> toList = new ArrayList<>();

        for (char x = 'A'; x <= 'H'; x++) {
            for (int y = 1; y <= 8; y++) {
                Position from = new Position(x, y);
                Piece p = game.getBoard().getPieceAt(from);

                if (p != null && p.getColor() == Colors.WHITE) {
                    List<Position> moves = p.getPossibleMoves(game.getBoard());
                    for (Position to : moves) {
                        if (game.getBoard().isValidMove(from, to)) {
                            fromList.add(from);
                            toList.add(to);
                        }
                    }
                }
            }
        }

        if (fromList.isEmpty()) return;

        int idx = (int) (Math.random() * fromList.size());

        try {
            computer.makeMove(fromList.get(idx), toList.get(idx), game.getBoard());
            game.addMove(computer, fromList.get(idx), toList.get(idx));
            addMoveToHistory(fromList.get(idx), toList.get(idx), true);

        } catch (Exception ignored) {}

        game.switchPlayer();
        refreshBoard();
        updateCaptured();
        statusLabel.setText("Your turn");
    }

    private String getUnicode(Piece p) {
        boolean w = p.getColor() == Colors.WHITE;
        switch (p.type()) {
            case 'K': return w ? "♔" : "♚";
            case 'Q': return w ? "♕" : "♛";
            case 'R': return w ? "♖" : "♜";
            case 'B': return w ? "♗" : "♝";
            case 'N': return w ? "♘" : "♞";
            case 'P': return w ? "♙" : "♟";
        }
        return "";
    }
}
