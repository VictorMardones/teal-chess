package gui;

import engine.board.Board;
import engine.board.BoardUtils;
import engine.board.Tile;
import engine.move.Move;
import engine.piece.Piece;
import engine.player.Alliance;
import io.SVGImporter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

@Slf4j
public class Table {

    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);

    private static final Color LIGHT_TILE_COLOR = Color.decode("#FFCE9E");
    private static final Color BLACK_TILE_COLOR = Color.decode("#D18B47");
    private static final String PIECE_ICON_PATH = "art/pieces";

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private Board chessboard;

    private Tile sourceTile;
    private Tile destinationTile;
    private Piece selectedPiece;

    public Table() {
        gameFrame = new JFrame("Chess game, made in Java");
        gameFrame.setLayout(new BorderLayout());

        gameFrame.setJMenuBar(createMenuBar());
        gameFrame.setSize(OUTER_FRAME_DIMENSION);

        chessboard = Board.createStandardBoard();

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

            IntStream.range(BoardUtils.MIN_TILES, BoardUtils.MAX_TILES)
                    .mapToObj(i -> new TilePanel(this, i))
                    .forEach(tilePanel -> {
                        boardTiles.add(tilePanel);
                        add(tilePanel);
                    });

            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        public void drawBoard(final Board board) {
            removeAll();

            boardTiles.forEach(tilePanel -> {
                tilePanel.drawTile(board);
                add(tilePanel);
            });

            validate();
            repaint();
        }
    }

    private final class TilePanel extends JPanel {
        private final int tileId;

        private TilePanel(final BoardPanel boardPanel, final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignPieceIcon(chessboard);
            validate();

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                    if (isLeftMouseButton(e)) {
                        if (sourceTile == null) {
                            log.debug("Selected the tile {}", tileId);

                            sourceTile = chessboard.getTile(tileId);
                            selectedPiece = sourceTile.getPiece();
                            log.debug("The tile contains {}", selectedPiece);

                            if (selectedPiece == null) {
                                log.debug("The tile is unoccupied, unselecting");
                                sourceTile = null;
                            }
                        } else {
                            destinationTile = chessboard.getTile(tileId);
                            log.debug("Selected the destination {}", tileId);

                            final var move = Move.MoveFactory.create(chessboard,
                                    sourceTile.getCoordinate(), destinationTile.getCoordinate());
                            final var moveTransition = chessboard.getCurrentPlayer().makeMove(move);

                            if (moveTransition.getMoveStatus().isDone()) {
                                chessboard = moveTransition.getBoard();
                                // TODO: Add the move to the move log
                            }

                            sourceTile = null;
                            destinationTile = null;
                            selectedPiece = null;
                        }
                    } else if (isRightMouseButton(e)) {
                        sourceTile = null;
                        destinationTile = null;
                        selectedPiece = null;
                        log.debug("Pressed right click, unselecting");
                    }

                    SwingUtilities.invokeLater(() -> boardPanel.drawBoard(chessboard));
                }

                @Override
                public void mousePressed(final MouseEvent e) {

                }

                @Override
                public void mouseReleased(final MouseEvent e) {

                }

                @Override
                public void mouseEntered(final MouseEvent e) {

                }

                @Override
                public void mouseExited(final MouseEvent e) {

                }
            });
        }

        private void drawTile(final Board board) {
            assignTileColor();
            assignPieceIcon(board);
            validate();
            repaint();
        }

        private void assignPieceIcon(final Board board) {
            removeAll();

            if (board.getTile(tileId).isOccupied()) {
                final var image = SVGImporter.importSVG(
                        new File(getIconPath(board.getTile(tileId).getPiece())),
                        TILE_PANEL_DIMENSION.width * 6,
                        TILE_PANEL_DIMENSION.height * 6);

                if (image != null) {
                    add(new JLabel(new ImageIcon(image)));
                }
            }
        }

        private String getIconPath(Piece piece) {
            return "%s/%s%s.svg"
                    .formatted(PIECE_ICON_PATH,
                           piece.getAlliance().toString().toLowerCase().charAt(0),
                           piece.toChar().toLowerCase());
        }

        private void assignTileColor() {
            setBackground(BoardUtils.getTileColor(tileId) == Alliance.WHITE
                    ? LIGHT_TILE_COLOR : BLACK_TILE_COLOR);
        }
    }
}
