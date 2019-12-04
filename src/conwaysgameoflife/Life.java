package conwaysgameoflife;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Sourabh
 */
public class Life implements MouseListener, ActionListener, Runnable {

    //Variables and Objects
    static int row, col;
    int[][] grid = new int[row][col];

    JFrame frame = new JFrame("Conway's Game Of Life");
    LifePanel panel = new LifePanel(grid, row, col);

    Container container = new Container();
    JButton clear = new JButton("Clear Grid");
    JButton stop = new JButton("Stop Game");
    JButton start = new JButton("Start Game");
    boolean running = false;

    //Constructor
    public Life() {
        frame.setSize(700, 700);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        panel.addMouseListener(this);

        container.setLayout(new GridLayout(1, 3));

        container.add(clear);
        clear.addActionListener(this);
        clear.setBackground(Color.CYAN);

        container.add(start);
        start.addActionListener(this);
        start.setBackground(Color.CYAN);

        container.add(stop);
        stop.addActionListener(this);
        stop.setBackground(Color.CYAN);

        frame.add(container, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    //main method
    public static void main(String[] args) {
        new Life();
    }

    @Override
    public void mouseReleased(MouseEvent event) {

        double width = (double) panel.getWidth() / col;
        double height = (double) panel.getHeight() / row;
        int column = (int) (event.getX() / width);
        int row = (int) (event.getY() / height);
        System.out.println(row + " " + column);

        grid[row][column] = 1 - grid[row][column];
        frame.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(clear)) {
            System.out.println("Stop and Cleared");
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    grid[i][j] = 0;
                }
            }
            running = false;
            frame.repaint();
        }

        if (event.getSource().equals(start)) {
            System.out.println("Game Started");
            if (running == false) {
                running = true;
                Thread t = new Thread(this);
                t.start();
            }
        }

        if (event.getSource().equals(stop)) {
            System.out.println("Game Stopped");
            running = false;
        }
    }

    @Override
    public void run() {
        while (running == true) {
            generateNextGeneration();
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
    }

    public void generateNextGeneration() {

        int nextGeneration[][] = new int[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {

                int aliveNeighbours = 0;

                if (i == 0 && j == 0) {
                    aliveNeighbours = grid[i][j + 1] + grid[i + 1][j] + grid[i + 1][j + 1];
                } else if (i == 0 && j == (col - 1)) {
                    aliveNeighbours = grid[i][j - 1] + grid[i + 1][j - 1] + grid[i + 1][j];
                } else if (i == (row - 1) && j == 0) {
                    aliveNeighbours = grid[i - 1][j] + grid[i - 1][j + 1] + grid[i][j + 1];
                } else if (i == (row - 1) && j == (col - 1)) {
                    aliveNeighbours = grid[i - 1][j] + grid[i - 1][j - 1] + grid[i][j - 1];
                } else if (i == 0) {
                    aliveNeighbours = grid[i][j - 1] + grid[i + 1][j - 1] + grid[i + 1][j] + grid[i + 1][j + 1] + grid[i][j + 1];
                } else if (i == (row - 1)) {
                    aliveNeighbours = grid[i][j - 1] + grid[i - 1][j - 1] + grid[i - 1][j] + grid[i - 1][j + 1] + grid[i][j + 1];
                } else if (j == 0) {
                    aliveNeighbours = grid[i - 1][j] + grid[i - 1][j + 1] + grid[i][j + 1] + grid[i + 1][j + 1] + grid[i + 1][j];
                } else if (j == (col - 1)) {
                    aliveNeighbours = grid[i - 1][j] + grid[i - 1][j - 1] + grid[i][j - 1] + grid[i + 1][j - 1] + grid[i + 1][j];
                } else {

                    for (int p = -1; p <= 1; p++) {
                        for (int q = -1; q <= 1; q++) {
                            aliveNeighbours += grid[i + p][j + q];
                        }
                    }

                    aliveNeighbours -= grid[i][j];
                }

                if (aliveNeighbours < 2 || aliveNeighbours >= 4) {
                    nextGeneration[i][j] = 0;
                } else if (aliveNeighbours == 3) {
                    nextGeneration[i][j] = 1;
                } else if (aliveNeighbours == 2) {
                    nextGeneration[i][j] = grid[i][j];
                }
            }
        }
        if (Arrays.deepEquals(grid, nextGeneration)) {
            running = false;
            System.out.println("Stop");
        } else {
            grid = nextGeneration;
            panel.setNewCells(nextGeneration);
            frame.repaint();
        }
    }
}
