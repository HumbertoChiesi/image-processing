package histograma;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Histograma extends JPanel {
    private int SIZE = 256;
    // Red, Green, Blue
    private int NUMBER_OF_COLOURS = 3;

    public final int RED = 0;
    public final int GREEN = 1;
    public final int BLUE = 2;

    private int[][] colourBins;
    private volatile boolean loaded = false;
    private int maxY;

    /**
     *
     * @param Path
     *            of image to create Histogram of.
     */
    public Histograma(BufferedImage imagem) {
        colourBins = new int[NUMBER_OF_COLOURS][];

        for (int i = 0; i < NUMBER_OF_COLOURS; i++) {
            colourBins[i] = new int[SIZE];
        }

        loaded = false;

        JFrame frame = new JFrame("Histograma");
        frame.setSize(800, 800);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        load(imagem);
        this.setBackground(Color.gray);
        frame.add(this,BorderLayout.CENTER);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void load(BufferedImage imagem) {
        BufferedImage bi = imagem;

        // Reset all the bins
        for (int i = 0; i < NUMBER_OF_COLOURS; i++) {
            for (int j = 0; j < SIZE; j++) {
                colourBins[i][j] = 0;
            }
        }

        for (int x = 0; x < bi.getWidth(); x++) {
            for (int y = 0; y < bi.getHeight(); y++) {
                Color c = new Color(bi.getRGB(x, y));

                colourBins[RED][c.getRed()]++;
                colourBins[GREEN][c.getGreen()]++;
                colourBins[BLUE][c.getBlue()]++;
            }
        }

        maxY = 0;

        for (int i = 0; i < NUMBER_OF_COLOURS; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (maxY < colourBins[i][j]) {
                    maxY = colourBins[i][j];
                }
            }
        }

        loaded = true;
    }

    @Override
    public void paint(Graphics g) {
        if (loaded) {
            Graphics2D g2 = (Graphics2D)g;

            g2.setColor(Color.white);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setStroke(new BasicStroke(2));

            int xInterval = (int) ((double)getWidth() / ((double)SIZE+1));

            g2.setColor(Color.black);


            for (int i = 0; i < NUMBER_OF_COLOURS; i++) {

                // Set the graph
                if (i == RED) {
                    g2.setColor(Color.red);
                } else if (i == GREEN) {
                    g2.setColor(Color.GREEN);
                } else if (i == BLUE) {
                    g2.setColor(Color.blue);
                }

                // draw the graph for the spesific colour.
                for (int j = 0; j < SIZE - 1 ; j++) {
                    int value = (int) (((double)colourBins[i][j] / (double)maxY) * getHeight());
                    int value2 = (int) (((double)colourBins[i][j+1] / (double)maxY) * getHeight());

                    g2.drawLine(j * xInterval, getHeight() - value, (j+1)*xInterval, getHeight() - value2);
                }
            }
        } else {
            super.paint(g);
        }
    }
}