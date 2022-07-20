package gui;

import engine.board.Board;
import engine.board.BoardUtils;
import engine.player.Alliance;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Table {

    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);

    private static final Color LIGHT_TILE_COLOR = Color.decode("#FFCE9E");
    private static final Color BLACK_TILE_COLOR = Color.decode("#D18B47");
    private static final String PIECE_ICON_PATH = "";

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

        final var exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        fileMenu.add(exit);

        return fileMenu;
    }

    private class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;

        private BoardPanel() {
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

        private TilePanel(final BoardPanel boardPanel, final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            validate();
        }

        private void assignTileIcon(final Board board) {
            removeAll();

            if (board.getTile(tileId).isOccupied()) {
                try {
                    final var image = ImageIO.read(new File("%s/%s%s.svg"
                            .formatted(PIECE_ICON_PATH,
                                    board.getTile(tileId).getPiece().getAlliance().toString().charAt(0),
                                    board.getTile(tileId).getPiece().toString())));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    throw new IllegalStateException("Failed to load images!");
                }
            }
        }

        private void assignTileColor() {
            setBackground(BoardUtils.getTileColor(tileId) == Alliance.WHITE
                    ? LIGHT_TILE_COLOR : BLACK_TILE_COLOR);
        }
    }
}
