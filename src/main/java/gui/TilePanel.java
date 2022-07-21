package gui;

import engine.board.Board;
import engine.board.BoardUtils;
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
import java.util.Collection;
import java.util.Collections;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

@Slf4j
final class TilePanel extends JPanel {

    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
    private static final Color LIGHT_TILE_COLOR = Color.decode("#FFCE9E");
    private static final Color BLACK_TILE_COLOR = Color.decode("#D18B47");
    private static final String PIECE_ICON_PATH = "art/pieces";

    private final transient Table table;
    private final int tileId;

    TilePanel(Table table, final BoardPanel boardPanel, final int tileId) {

        super(new GridBagLayout());
        this.table = table;
        this.tileId = tileId;
        setPreferredSize(TILE_PANEL_DIMENSION);
        assignTileColor();
        assignPieceIcon(table.getChessboard());
        validate();

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (isLeftMouseButton(e)) {
                    if (table.getSourceTile() == null) {
                        log.debug("Selected the tile {}", tileId);

                        table.setSourceTile(table.getChessboard().getTile(tileId));
                        table.setSelectedPiece(table.getSourceTile().getPiece());
                        log.debug("The tile contains {}", table.getSelectedPiece());

                        if (table.getSelectedPiece() == null) {
                            log.debug("The tile is unoccupied, unselecting");
                            table.setSourceTile(null);
                        } else {
                            log.debug("Highlighting legal moves");
                        }
                    } else {
                        table.setDestinationTile(table.getChessboard().getTile(tileId));
                        log.debug("Selected the destination {}", tileId);

                        final var move = Move.MoveFactory.create(table.getChessboard(),
                                table.getSourceTile().getCoordinate(),
                                table.getDestinationTile().getCoordinate());
                        final var moveTransition = table.getChessboard().getCurrentPlayer().makeMove(move);

                        if (moveTransition.getMoveStatus().isDone()) {
                            table.setChessboard(moveTransition.getBoard());
                            // TODO: Add the move to the move log
                        }

                        table.resetSelection();
                    }
                } else if (isRightMouseButton(e)) {
                    table.resetSelection();
                    log.debug("Pressed right click, unselecting");
                }

                SwingUtilities.invokeLater(() -> boardPanel.drawBoard(table.getChessboard()));
            }

            @Override
            public void mousePressed(final MouseEvent e) {
                // Do nothing
            }

            @Override
            public void mouseReleased(final MouseEvent e) {
                // Do nothing
            }

            @Override
            public void mouseEntered(final MouseEvent e) {
                // Do nothing
            }

            @Override
            public void mouseExited(final MouseEvent e) {
                // Do nothing
            }
        });
    }

    void drawTile(final Board board) {
        assignTileColor();
        assignPieceIcon(board);
        highlightLegals(board);
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

    private void highlightLegals(final Board board) {
        if (table.isHighlightLegalMoves()) {
            pieceLegalMoves(board).stream()
                    .filter(move -> move.getDestination() == tileId)
                    .forEach(move -> {
                        final var image = SVGImporter.importSVG(new File("art/misc/green_dot.svg"),
                                TILE_PANEL_DIMENSION.width * 4,
                                TILE_PANEL_DIMENSION.height * 4);

                        if (image != null) {
                            add(new JLabel(new ImageIcon(image)));
                        }
                    });
        }
    }

    private Collection<Move> pieceLegalMoves(final Board board) {
        if (table.getSelectedPiece() != null && table.getSelectedPiece().getAlliance() == board.getCurrentPlayer().getAlliance()) {
            return table.getSelectedPiece().calculateLegalMoves(board);
        }

        log.debug("The piece has no legal moves");
        return Collections.emptyList();
    }
}
