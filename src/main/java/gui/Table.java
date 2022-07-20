package gui;

import javax.swing.*;
import java.awt.*;

public class Table {

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(700, 700);

    private final JFrame gameFrame;

    public Table() {
        gameFrame = new JFrame("Chess game, made in Java");

        final JMenuBar menuBar = new JMenuBar();
        populateMenuBar(menuBar);
        gameFrame.setJMenuBar(menuBar);

        gameFrame.setSize(OUTER_FRAME_DIMENSION);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
    }

    private void populateMenuBar(final JMenuBar menuBar) {
        menuBar.add(createFileMenu());
    }

    private JMenu createFileMenu() {
        final var fileMenu = new JMenu("File");

        final var openPGN = new JMenuItem("Load PGN file");
        openPGN.addActionListener(e -> System.out.println("Open PGN file!"));

        fileMenu.add(openPGN);

        return fileMenu;
    }
}
