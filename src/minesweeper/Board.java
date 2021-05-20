package minesweeper;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.io.IOException;
import java.util.Stack;

public class Board extends JPanel implements ActionListener {

    //DEFINE FIELDS

    //Fields related to board and cells

    private final int CELL_SIZE = 30;

    private int n_size; // the size of a row or a column
    private int BoardSize;
    private int n_mines; // the amount of mines
    private int minesLeft;//keeps track of how many mines are left based on what user has flagged
    //2D array to represent game board
    protected static Cell[][] gameBoard;
    //total number of cells
    private int allCells;

    //Fields related to images used in our game to represent cells and bombs
    private final int NUM_IMAGES = 13;
    //Using map as collection to store images and their names, which can make it more easily retrievable
    private java.util.Map<String, Image> images;

    //Fields related to game status
    private boolean inGame;
    private static JLabel statusbar;
    private static JButton bUndo;

    private Stack gameSteps = new Stack();


    //Constructor
    public Board(JLabel statusbar, JButton bUndo, int size, int mines) throws IOException {
        this.n_size = size;
        this.n_mines = mines;
        BoardSize = n_size * CELL_SIZE + 1;
        System.out.println("n_size: " + n_size + " | n_mines: " + n_mines);
        this.statusbar = statusbar;
        this.bUndo = bUndo;
        this.bUndo.addActionListener(this);
        initBoard();
    }


    //Action performed when user wants to undo moves or see the rules 
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getActionCommand().equals("Undo")) {
                this.undo();
            }
        } catch (Exception oe) {
            oe.printStackTrace();
        }
    }


    //Allows user to undo moves 
    private void undo() {
        if (!gameSteps.empty()) {
            int i = (Integer) gameSteps.pop();//gets most recent game step
            //corresponding cell to the game step 
            Cell cell = gameBoard[i / n_size][i % n_size];

            //Handle flagged cells situation, which are covered 
            if (cell.isCoveredCell()) {
                cell.changeWhetherMarked();
                if (cell.isMarkedCell()) {
                    minesLeft--;
                } else {
                    minesLeft++;
                    if (!inGame) {
                        inGame = true;
                    }
                }
            } else if (cell.getCellType() == CellType.Bomb) {
                cell.isCovered = true;
                inGame = true;

            } else if (cell.getCellType() == CellType.BombNeighbor) {
                cell.isCovered = true;
            }

            String msg = Integer.toString(minesLeft);
            this.statusbar.setText("Flags Left: " + msg);

            //Takes care of empty cell situation 
            if (cell.getCellType() == CellType.Empty) {
                cell.isCovered = true;
                while (!gameSteps.empty()) {
                    int j = (Integer) gameSteps.pop();
                    Cell cellNext = gameBoard[j / n_size][j % n_size];
                    if (cellNext.getCellType().equals(CellType.BombNeighbor)) {
                        gameSteps.push(j);
                        break;
                    } else {
                        cellNext.isCovered = true;
                    }
                }

            }

            repaint();
        }
    }

    //Initializes the game board
    private void initBoard() throws IOException {
        setPreferredSize(new Dimension(BoardSize, BoardSize));
        images = new java.util.HashMap<>();


        //Put all relevant images in the map, some images named with integers, others named with descriptors
        for (int i = 1; i < 9; i++) {
            String path = "src/resources/" + i + ".png";
            images.put(Integer.toString(i), (new ImageIcon(path)).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        }

        images.put("Bomb", (new ImageIcon("src/resources/Bomb.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        images.put("Covered", (new ImageIcon("src/resources/Covered.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        images.put("Empty", (new ImageIcon("src/resources/Empty.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        images.put("Marked", (new ImageIcon("src/resources/Marked.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        images.put("Wrongmarked", (new ImageIcon("src/resources/Wrongmarked.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

        addMouseListener(new MinesAdapter());
        newGame();
    }


    //Sets up value of game board for the first time
    private void newGame() {


        inGame = true;
        minesLeft = n_mines;

        allCells = n_size * n_size;

        //2D Array of cells in gameboard
        gameBoard = new Cell[n_size][n_size];

        for (int x = 0; x < n_size; x++) {//initially have everything be empty
            for (int y = 0; y < n_size; y++) {
                gameBoard[x][y] = new EmptyCell();
            }
        }

        statusbar.setText("Flags Left: " + Integer.toString(minesLeft));

        int i = 0;

        //set up the grid
        while (i < n_mines) {
            Random random = new Random();
            int positionX = (int) (random.nextInt(n_size - 1 + 0 + 1) + 0);
            int positionY = (int) (random.nextInt(n_size - 1 + 0 + 1) + 0);

            //randomly place the bomb cell
            if (gameBoard[positionX][positionY].getCellType() != CellType.Bomb) {
                gameBoard[positionX][positionY] = new BombCell();


                //sets up neighbor cells 
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if ((dx != 0 || dy != 0) && positionX + dx < n_size && positionY + dy < n_size
                                && positionX + dx >= 0 && positionY + dy >= 0) {
                            CellType typeOfCell = gameBoard[positionX + dx][positionY + dy].getCellType();
                            if (typeOfCell != CellType.Bomb) {//not already a neighbor cell
                                if (typeOfCell != CellType.BombNeighbor) {
                                    NeighborOfBombCell neighbor = new NeighborOfBombCell();
                                    neighbor.cellCount();
                                    gameBoard[positionX + dx][positionY + dy] = neighbor;
                                } else {//already a neighbor cell, just need to update the neighbor count

                                    gameBoard[positionX + dx][positionY + dy].cellCount();
                                }
                            }

                        }
                    }
                }
                i++;

            }

        }
    }

    //checks this for all neighbors
    public void find_empty_cells(int x, int y) {

        //int current_col = j % N_COLS;
        gameBoard[x][y].flipUp();
        gameSteps.push(x * n_size + y);//add steps to gameSteps Stack
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {//set bounds
                if ((dx != 0 || dy != 0) && x + dx < n_size && y + dy < n_size
                        && x + dx >= 0 && y + dy >= 0) {

                    CellType typeOfCell = gameBoard[x + dx][y + dy].getCellType();
                    //if(typeOfCell == CellType.BombNeighbor && gameBoard[x + dx][y + dy].isCoveredCell()) {
                    //    gameBoard[x + dx][y + dy].flipUp();
                    //}
                    //else
                    if (typeOfCell == CellType.Empty && gameBoard[x + dx][y + dy].isCoveredCell()) {
                        find_empty_cells(x + dx, y + dy);
                    }
                }
            }
        }


    }


    @Override
    public void paintComponent(Graphics g) {

        int uncover = 0;

        for (int i = 0; i < n_size; i++) {
            for (int j = 0; j < n_size; j++) {

                Cell cell = gameBoard[i][j];
                String imageName = cell.getImageName();

                //game over when user clicks on mine
                if (inGame && cell.getCellType() == CellType.Bomb && !cell.isCoveredCell()) {
                    inGame = false;
                }

                if (!inGame) {//when game is over
                    if (cell.getCellType() == CellType.Bomb && !cell.isMarkedCell()) {
                        cell.flipUp();
                        imageName = ImageName.Bomb.toString(); // draw mine 
                    } else if (cell.isCoveredCell() && cell.getCellType() == CellType.Bomb && cell.isMarkedCell()) {
                        imageName = ImageName.Marked.toString(); //draw mark
                    } else if (cell.isCoveredCell() && cell.getCellType() != CellType.Bomb && cell.isMarkedCell()) {//wrongly marked cells
                        imageName = ImageName.Wrongmarked.toString(); //draw wrong mark
                    } else if (cell.isCoveredCell()) {//board cells that are still covered remain covered
                        imageName = ImageName.Covered.toString(); //draw cover 
                    }

                } else {//when game is still going 
                    if (cell.isMarkedCell()) {//draw a mark if user clicks on covered cell 
                        imageName = ImageName.Marked.toString();
                    } else if (cell.isCoveredCell()) {//draw cover when user clicks on a flagged/marked cell, cover is revealed reducing cell value
                        imageName = ImageName.Covered.toString();
                        uncover++;
                    }
                }

                g.drawImage(images.get(imageName), (j * CELL_SIZE),
                        (i * CELL_SIZE), this);
            }
        }

        if (uncover == 0 && inGame) {//when there's nothing left to uncover
            inGame = false;
            statusbar.setText("Game won");
        } else if (!inGame) {
            //Clear all user steps stored in gameSteps so user cannot "undo" moves when
            //bomb is clicked on
            gameSteps.clear();
            statusbar.setText("Game lost");
        }

        //no more "Undo" option once user undoes all the steps 
        if (gameSteps.empty()) {
            this.bUndo.setEnabled(false);
        } else {
            this.bUndo.setEnabled(true);
        }
    }


    //Makes changes based on user action 
    private class MinesAdapter extends MouseAdapter {


        @Override
        public void mousePressed(MouseEvent e) {

            //x and y coordinates
            int x = e.getX();
            int y = e.getY();

            //corresponding column and row in board
            int cCol = x / CELL_SIZE;
            int cRow = y / CELL_SIZE;

            boolean doRepaint = false;

            if (!inGame) {

                newGame();
                repaint();
                gameSteps.clear();
            }

            //check if we are located in mine field area
            if ((x < n_size * CELL_SIZE) && (y < n_size * CELL_SIZE)) {

                //RIGHT MOUSE CLICK
                if (e.getButton() == MouseEvent.BUTTON3) {

                    if (gameBoard[cRow][cCol].isCoveredCell()) {
                        doRepaint = true;//implies that we do nothing if user right clicks on a number cell 

                        //right click on an unmarked cell

                        if (!gameBoard[cRow][cCol].isMarkedCell() && minesLeft > 0) {
                            Cell cell = gameBoard[cRow][cCol];
                            cell.changeWhetherMarked();//changed to marked cell
                            minesLeft--;
                            if (minesLeft > 0) {
                                String msg = Integer.toString(minesLeft);
                                statusbar.setText("Flags Left: " + msg);
                            } else {
                                statusbar.setText("No marks left");
                            }
                            //add steps to gameSteps stack
                            gameSteps.push(cRow * n_size + cCol);
                        } else if (gameBoard[cRow][cCol].isMarkedCell()) {//right click on already marked cell, removes marks and increase number of cells to be marked
                            gameBoard[cRow][cCol].changeWhetherMarked();//changes it to not marked
                            minesLeft++;
                            String msg = Integer.toString(minesLeft);
                            statusbar.setText("Flags Left: " + msg);
                        }


                    }


                    //LEFT MOUSE CLICK
                } else {

                    //nothing happens if user left clicks on covered and marked cell
                    if (gameBoard[cRow][cCol].isMarkedCell()) {
                        return;
                    }

                    //user left clicks to remove a cover from cell 
                    if (gameBoard[cRow][cCol].isCoveredCell()
                        //&& (gameBoard[cRow][cCol].getCellType() == CellType.Bomb )
                    ) {

                        gameBoard[cRow][cCol].flipUp();
                        doRepaint = true;
                        gameSteps.push(cRow * n_size + cCol);

                        //if user clicks on mine, game is over
                        if (gameBoard[cRow][cCol].getCellType() == CellType.Bomb
                                && !gameBoard[cRow][cCol].isCoveredCell()) {
                            inGame = false;
                        }

                        //if user clicks on empty cell, call empty cell function which will handle the situation
                        if (gameBoard[cRow][cCol].getCellType() == CellType.Empty) {
                            find_empty_cells(cRow, cCol);
                        }
                    }
                }

                if (doRepaint) {
                    repaint();
                }


            }
        }
    }
}

