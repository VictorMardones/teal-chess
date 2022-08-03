/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.gui;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

import cl.vmardones.chess.engine.board.Board;
import cl.vmardones.chess.engine.board.Coordinate;
import cl.vmardones.chess.engine.board.Tile;
import cl.vmardones.chess.engine.move.Move;
import cl.vmardones.chess.engine.piece.Piece;
import cl.vmardones.chess.engine.player.Alliance;
import cl.vmardones.chess.io.PieceIconLoader;
import cl.vmardones.chess.io.SvgLoader;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import javax.swing.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class TilePanel extends JPanel {

  private static final Dimension SIZE = new Dimension(10, 10);
  private static final Color LIGHT_TILE_COLOR = Color.decode("#FFCE9E");
  private static final Color BLACK_TILE_COLOR = Color.decode("#D18B47");

  private final transient Table table;
  private final transient Coordinate coordinate;

  TilePanel(final Table table, final Coordinate coordinate) {

    super(new GridBagLayout());

    this.table = table;
    this.coordinate = coordinate;

    setPreferredSize(SIZE);
    assignTileColor();
    assignPieceIcon(table.getTileAt(coordinate));
    validate();

    addMouseListener(clickListener());
  }

  private MouseListener clickListener() {

    return new MouseListener() {
      @Override
      public void mouseClicked(final MouseEvent e) {
        if (isLeftMouseButton(e)) {
          if (table.getSourceTile() == null) {
            firstLeftClick();
          } else {
            secondLeftClick();
          }
        } else if (isRightMouseButton(e)) {
          table.resetSelection();
          log.debug("Pressed right click, unselecting");
        }

        SwingUtilities.invokeLater(table::update);
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
    };
  }

  private void firstLeftClick() {
    log.debug("Selected the tile {}", coordinate);
    table.setSourceTile(table.getTileAt(coordinate));

    if (getSelectedPiece().isPresent()) {
      table.setSelectedPiece(getSelectedPiece().get());
      log.debug("The tile contains {}", table.getSelectedPiece());
      log.debug("Highlighting legal moves");
    } else {
      log.debug("The tile is unoccupied, unselecting");
      table.resetSelection();
    }
  }

  private Optional<Piece> getSelectedPiece() {
    return table.getSourceTile().getPiece();
  }

  private void secondLeftClick() {
    // TODO: Replace all these long method calls with forwarding methods
    log.debug("Selected the destination {}", coordinate);
    table.setDestinationTile(table.getTileAt(coordinate));

    final var move =
        Move.MoveFactory.create(
            table.getGame().getCurrentPlayer().getLegals(),
            table.getSourceTile().getCoordinate(),
            table.getDestinationTile().getCoordinate());

    log.debug("Is there a move that can get to the destination? {}", move.isPresent());

    if (move.isPresent()) {
      final var moveTransition = table.makeMove(move.get());

      if (moveTransition.getMoveStatus().isDone()) {
        table.getGame().createNextTurn(move.get());
        table.addToLog(move.get());
      }
    }

    table.resetSelection();
  }

  void drawTile(final Board board) {
    assignTileColor();
    assignPieceIcon(table.getTileAt(coordinate));
    highlightLegals(board);
    validate();
    repaint();
  }

  private void assignPieceIcon(final Tile tile) {
    removeAll();

    if (tile.getPiece().isPresent()) {
      PieceIconLoader.load(tile.getPiece().get(), SIZE.width * 6, SIZE.height * 6)
          .ifPresent(image -> add(new JLabel(new ImageIcon(image))));
    }
  }

  private void assignTileColor() {
    setBackground(coordinate.getColor() == Alliance.WHITE ? LIGHT_TILE_COLOR : BLACK_TILE_COLOR);
  }

  private void highlightLegals(final Board board) {
    if (table.isHighlightLegals()) {
      selectedPieceLegals(board).stream()
          .filter(move -> move.getDestination() == coordinate)
          .forEach(
              move -> {
                final var image =
                    SvgLoader.load("art/misc/green_dot.svg", SIZE.width * 4, SIZE.height * 4);

                image.ifPresent(bufferedImage -> add(new JLabel(new ImageIcon(bufferedImage))));
              });
    }
  }

  private Collection<Move> selectedPieceLegals(final Board board) {
    if (table.getSelectedPiece() == null || isOpponentPieceSelected()) {
      return Collections.emptyList();
    }

    return table.getSelectedPiece().calculateLegals(board);
  }

  private boolean isOpponentPieceSelected() {
    return table.getSelectedPiece().getAlliance()
        != table.getGame().getCurrentPlayer().getAlliance();
  }
}
