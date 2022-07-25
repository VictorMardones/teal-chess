/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package gui;

import com.google.common.collect.ImmutableList;
import engine.move.Move;
import engine.piece.Piece;
import engine.player.Alliance;
import io.PieceIconLoader;
import java.awt.*;
import java.util.List;
import java.util.Optional;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class TakenPiecesPanel extends JPanel {

    private static final Color BACKGROUND_COLOR = Color.decode("0xFDF5E6");
    private static final Dimension TAKEN_PIECES_DIMENSION = new Dimension(40, 80);
    private static final EtchedBorder BORDER = new EtchedBorder(EtchedBorder.RAISED);

    private final JPanel northPanel;
    private final JPanel southPanel;

    public TakenPiecesPanel() {
        super(new BorderLayout());

        setBackground(BACKGROUND_COLOR);
        setBorder(BORDER);

        northPanel = new JPanel(new GridLayout(8, 2));
        northPanel.setBackground(BACKGROUND_COLOR);

        southPanel = new JPanel(new GridLayout(8, 2));
        southPanel.setBackground(BACKGROUND_COLOR);

        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);

        setPreferredSize(TAKEN_PIECES_DIMENSION);
    }

    public void redo(final MoveLog moveLog) {
        northPanel.removeAll();
        southPanel.removeAll();

        // TODO: Do this process on a single loop
        getTakenPieces(moveLog, Alliance.WHITE).stream()
                .map(piece -> PieceIconLoader.loadIcon(
                        piece, TAKEN_PIECES_DIMENSION.width / 2, TAKEN_PIECES_DIMENSION.width / 2))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(image -> southPanel.add(new JLabel(new ImageIcon(image))));

        getTakenPieces(moveLog, Alliance.BLACK).stream()
                .map(piece -> PieceIconLoader.loadIcon(
                        piece, TAKEN_PIECES_DIMENSION.width / 2, TAKEN_PIECES_DIMENSION.width / 2))
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
