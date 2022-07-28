/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package gui;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;

public class SquarePanel<T extends JPanel> extends JPanel {

    private final T square;

    public SquarePanel(final T square) {
        this.square = square;

        addComponentListener(resizeListener());
        add(this.square);
    }

    private ComponentListener resizeListener() {

        return new ComponentListener() {

            public void componentResized(ComponentEvent e) {
                resize();
                revalidate();
            }

            public void componentMoved(ComponentEvent e) {
                // Do nothing
            }

            public void componentShown(ComponentEvent e) {
                // Do nothing
            }

            public void componentHidden(ComponentEvent e) {
                // Do nothing
            }
        };
    }

    private void resize() {

        var containerHeight = getHeight();
        var containerWidth = getWidth();

        if (containerHeight > containerWidth) {
            square.setPreferredSize(new SquareDimension(containerWidth));
        } else if (containerHeight < containerWidth) {
            square.setPreferredSize(new SquareDimension(containerHeight));
        }
    }

    private static class SquareDimension extends Dimension {
        public SquareDimension(int side) {
            super(side, side);
        }
    }
}
