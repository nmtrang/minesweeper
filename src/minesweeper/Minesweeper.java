package minesweeper;

import java.awt.*;
import java.io.IOException;
import GUI.Input;
import GUI.CustomMap;

public class Minesweeper {
    private static Minesweeper minesweeper;
    private static Input input;

    public static void main(String[] args) {
        minesweeper = new Minesweeper();
        input = new Input(minesweeper);
        input.main(input);
    }
    public void proceed(int size, int bom){
        System.out.println("This is size: " + size + " | This is bom: "+ bom);
        this.running(size,bom);
    }
    public void running(int size, int bom) {
        EventQueue.invokeLater(() -> {
            Game ex;
            try {
                ex = new Game(size,bom);
                ex.setVisible(true);

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }
}
