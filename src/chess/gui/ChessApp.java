package chess.gui;

import javax.swing.*;

public class ChessApp {
    public static void main(String[] args){
        SwingUtilities.invokeLater(()->{
            new LoginFrame();
        });
    }
}
