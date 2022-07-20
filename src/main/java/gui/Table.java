package gui;

import javax.swing.*;
import java.awt.*;

public class Table {

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);

    private final JFrame gameFrame;

    public Table() {
        gameFrame = new JFrame("Chess game, made in Java");
        gameFrame.setSize(OUTER_FRAME_DIMENSION);
        gameFrame.setVisible(true);
    }
}
