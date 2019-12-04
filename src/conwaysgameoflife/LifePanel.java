package conwaysgameoflife;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Sourabh
 */
public class LifePanel extends JPanel {

    int[][] cells;
    int row, col;
    double width;
    double height;

    public LifePanel(int[][] in, int rows, int cols) {
        cells = in;
        row = rows;
        col = cols;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        width = (double) this.getWidth() / col;
        height = (double) this.getHeight() / row;

        //draw the cells
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (cells[i][j] == 1) {
                    g.fillRect((int) Math.round(j * width),
                            (int) Math.round(i * height),
                            (int) (width + 1), (int) (height + 1));
                }
            }
        }

        //draw the grid
        g.setColor(Color.BLACK);

        for (int x = 0; x < col + 1; x++) {
            g.drawLine((int) Math.round(x * width), 0,
                    (int) Math.round(x * width), this.getHeight());
        }

        for (int y = 0; y < row + 1; y++) {
            g.drawLine(0, (int) Math.round(y * height), this.getWidth(),
                    (int) Math.round(y * height));
        }
    }

    void setNewCells(int[][] grid) {
        cells = grid;
    }
}
