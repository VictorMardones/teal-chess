/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package cl.vmardones.chess.io;

import java.awt.*;
import java.io.IOException;
import javax.swing.plaf.FontUIResource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class FontLoader {

    private static final String FONT_PATH = "fonts/";
    public static final int FONT_SIZE = 16;
    public static final Font SYSTEM_FONT = new FontUIResource(Font.SANS_SERIF, Font.PLAIN, FONT_SIZE);

    public static Font load(String path) {
        try {
            final var fontResource = ResourceImporter.get(FONT_PATH + path);

            return Font.createFont(Font.TRUETYPE_FONT, fontResource).deriveFont(Font.PLAIN, FONT_SIZE);

        } catch (FontFormatException | IOException e) {
            log.warn("Could not load the font" + path + ", defaulting to system font");
            return SYSTEM_FONT;
        }
    }
}
