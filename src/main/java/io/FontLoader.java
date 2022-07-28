/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package io;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.plaf.FontUIResource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FontLoader {

    public static final int FONT_SIZE = 16;
    public static final Font SYSTEM_FONT = new FontUIResource(Font.SANS_SERIF, Font.PLAIN, FONT_SIZE);

    public static Font load(String fontPath) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)).deriveFont(Font.PLAIN, FONT_SIZE);
        } catch (FontFormatException | IOException e) {
            log.warn("Could not load the font, defaulting to system font");
            return SYSTEM_FONT;
        }
    }
}
