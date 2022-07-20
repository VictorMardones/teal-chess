package gui;

import engine.board.BoardUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Table {

    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;

    public Table() {
        gameFrame = new JFrame("Chess game, made in Java");
        gameFrame.setLayout(new BorderLayout());

        gameFrame.setJMenuBar(createMenuBar());
        gameFrame.setSize(OUTER_FRAME_DIMENSION);

        boardPanel = new BoardPanel();
        gameFrame.add(boardPanel, BorderLayout.CENTER);

        gameFrame.setLocationRelativeTo(null);
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
    }

    private JMenuBar createMenuBar() {
        final var menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        return menuBar;
    }

    private JMenu createFileMenu() {
        final var fileMenu = new JMenu("File");

        final var openPGN = new JMenuItem("Load PGN file");
        openPGN.addActionListener(e -> System.out.println("Open PGN file!"));

        fileMenu.add(openPGN);

        return fileMenu;
    }

    private class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;

        public BoardPanel() {
            super(new GridLayout(8, 8));

            boardTiles = new ArrayList<>();

            for (int i = 0; i < BoardUtils.MAX_TILES; i++) {
                final var tilePanel = new TilePanel(this, i);
                boardTiles.add(tilePanel);
                add(tilePanel);
            }

            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }
    }

    private class TilePanel extends JPanel {
        private final int tileId;

        TilePanel(final BoardPanel boardPanel, final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            validate();
        }

        private void assignTileColor() {

        }
    }
}
