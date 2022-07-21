package gui;

import engine.board.Board;
import engine.board.Tile;
import engine.piece.Piece;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

public class Table {

    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    @Getter @Setter
    private Board chessboard;

    @Getter @Setter
    private Tile sourceTile;

    @Getter @Setter
    private Tile destinationTile;

    @Getter @Setter
    private Piece selectedPiece;

    public Table() {
        gameFrame = new JFrame("Chess game, made in Java");
        gameFrame.setLayout(new BorderLayout());

        gameFrame.setJMenuBar(createMenuBar());
        gameFrame.setSize(OUTER_FRAME_DIMENSION);

        chessboard = Board.createStandardBoard();

        boardPanel = new BoardPanel(this);
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

    public void resetSelection() {
        sourceTile = null;
        destinationTile = null;
        selectedPiece = null;
    }
}
