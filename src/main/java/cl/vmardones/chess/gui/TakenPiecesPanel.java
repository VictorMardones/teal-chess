/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.gui;

import cl.vmardones.chess.engine.move.Move;
import cl.vmardones.chess.engine.piece.Piece;
import cl.vmardones.chess.engine.player.Alliance;
import cl.vmardones.chess.io.PieceIconLoader;
import com.google.common.collect.ImmutableList;
import java.awt.*;
import java.util.List;
import java.util.Optional;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

class TakenPiecesPanel extends JPanel {

  private static final Dimension INITIAL_SIZE = new Dimension(40, 80);
  private static final EtchedBorder BORDER = new EtchedBorder(EtchedBorder.RAISED);

  private final JPanel northPanel;
  private final JPanel southPanel;

  public TakenPiecesPanel() {
    super(new BorderLayout());

    setBorder(BORDER);

    northPanel = new JPanel(new GridLayout(8, 2));
    southPanel = new JPanel(new GridLayout(8, 2));

    add(northPanel, BorderLayout.NORTH);
    add(southPanel, BorderLayout.SOUTH);

    setPreferredSize(INITIAL_SIZE);
  }

  public void redo(final MoveLog moveLog) {
    northPanel.removeAll();
    southPanel.removeAll();

    // TODO: Do this process on a single loop
    getTakenPieces(moveLog, Alliance.WHITE).stream()
        .map(piece -> PieceIconLoader.load(piece, INITIAL_SIZE.width / 2, INITIAL_SIZE.width / 2))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .forEach(image -> southPanel.add(new JLabel(new ImageIcon(image))));

    getTakenPieces(moveLog, Alliance.BLACK).stream()
        .map(piece -> PieceIconLoader.load(piece, INITIAL_SIZE.width / 2, INITIAL_SIZE.width / 2))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .forEach(image -> northPanel.add(new JLabel(new ImageIcon(image))));

    validate();
  }

  private List<Piece> getTakenPieces(final MoveLog moveLog, final Alliance alliance) {
    return moveLog.getMoves().stream()
        .filter(Move::isCapturing)
        .map(Move::getCapturedPiece)
        .filter(capturedPiece -> capturedPiece.getAlliance() == alliance)
        .collect(ImmutableList.toImmutableList());
  }
}
