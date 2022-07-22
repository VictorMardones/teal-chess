package gui;

import com.google.common.collect.Lists;
import engine.board.Board;
import engine.board.BoardService;
import engine.board.Tile;
import engine.piece.Piece;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Table {

  private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);

  private final JFrame gameFrame;
  private final BoardPanel boardPanel;
  @Getter @Setter private Board chessboard;
  @Getter private BoardService boardService;

  @Getter @Setter private Tile sourceTile;
  @Getter @Setter private Tile destinationTile;
  @Getter @Setter private Piece selectedPiece;

  @Getter private BoardDirection boardDirection;
  @Getter private boolean highlightLegalMoves;

  public Table() {
    gameFrame = new JFrame("Chess game, made in Java");
    gameFrame.setLayout(new BorderLayout());

    boardDirection = BoardDirection.NORMAL;
    highlightLegalMoves = true;

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
    menuBar.add(createPreferencesMenu());
    return menuBar;
  }

  private JMenu createFileMenu() {
    final var fileMenu = new JMenu("File");

    final var openPGN = new JMenuItem("Load PGN file");
    openPGN.addActionListener(e -> log.info("Open PGN file!"));
    fileMenu.add(openPGN);

    final var exit = new JMenuItem("Exit");
    exit.addActionListener(e -> System.exit(0));
    fileMenu.add(exit);

    return fileMenu;
  }

  private JMenu createPreferencesMenu() {
    final var preferencesMenu = new JMenu("Preferences");

    final var flipBoard = new JMenuItem("Flip Board");
    flipBoard.addActionListener(
        e -> {
          boardDirection = boardDirection.opposite();
          boardPanel.drawBoard(chessboard);
        });

    final var highlightCheckbox =
        new JCheckBoxMenuItem("Highlight Legal Moves", highlightLegalMoves);
    highlightCheckbox.addActionListener(e -> highlightLegalMoves = highlightCheckbox.isSelected());

    preferencesMenu.add(flipBoard);
    preferencesMenu.add(highlightCheckbox);
    return preferencesMenu;
  }

  public void resetSelection() {
    sourceTile = null;
    destinationTile = null;
    selectedPiece = null;
  }

  public enum BoardDirection {
    NORMAL {
      @Override
      List<TilePanel> traverse(List<TilePanel> boardTiles) {
        return boardTiles;
      }

      @Override
      BoardDirection opposite() {
        return FLIPPED;
      }
    },
    FLIPPED {
      @Override
      List<TilePanel> traverse(List<TilePanel> boardTiles) {
        return Lists.reverse(boardTiles);
      }

      @Override
      BoardDirection opposite() {
        return NORMAL;
      }
    };

    abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);

    abstract BoardDirection opposite();
  }
}
