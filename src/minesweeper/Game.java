package minesweeper;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Game extends JFrame {

    private JLabel statusbar;
    private JButton bUndo;
    private JPanel buttonPanel;
    private JPanel statusPanel;
    protected Board board;
    private int n_mines;
    private int n_size;
    
    public Game(int size, int mines) throws IOException {
        this.n_size = size;
        this.n_mines = mines;
        initUI();
        
    }
    
    private void initUI() throws IOException {

        bUndo = new JButton("Undo");
        buttonPanel = new JPanel();
        
        
        add(buttonPanel, BorderLayout.NORTH);
        buttonPanel.add(bUndo, BorderLayout.SOUTH);
        
        statusPanel = new JPanel();
        statusbar = new JLabel("Flags Left");
        statusPanel.add(statusbar, BorderLayout.NORTH);
        add(statusPanel, BorderLayout.SOUTH);

        board = new Board(statusbar, bUndo, n_size, n_mines);
        add(board);   
        
        setResizable(false);
        pack();
        
        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
    }
    
//    public static void main(String[] args) {
//
//        EventQueue.invokeLater(() -> {
//            Game ex;
//			try {
//				ex = new Game();
//                ex.setVisible(true);
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//        });
//    }
}
