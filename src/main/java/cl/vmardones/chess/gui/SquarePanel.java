/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.gui;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.*;

class SquarePanel<T extends JPanel> extends JPanel {

  private final T square;

  SquarePanel(final T square) {
    this.square = square;

    addComponentListener(resizeListener());
    add(this.square);
  }

  private ComponentListener resizeListener() {

    return new ComponentListener() {

      public void componentResized(final ComponentEvent e) {
        resize();
        revalidate();
      }

      public void componentMoved(final ComponentEvent e) {
        // Do nothing
      }

      public void componentShown(final ComponentEvent e) {
        // Do nothing
      }

      public void componentHidden(final ComponentEvent e) {
        // Do nothing
      }
    };
  }

  private void resize() {

    final var containerHeight = getHeight();
    final var containerWidth = getWidth();

    if (containerHeight > containerWidth) {
      square.setPreferredSize(new SquareDimension(containerWidth));
    } else if (containerHeight < containerWidth) {
      square.setPreferredSize(new SquareDimension(containerHeight));
    }
  }

  private static class SquareDimension extends Dimension {
    public SquareDimension(final int side) {
      super(side, side);
    }
  }
}
