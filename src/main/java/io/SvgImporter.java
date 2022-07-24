/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at COPYRIGHT in the root directory.
 */

package io;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Optional;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.SVGAbstractTranscoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

@Slf4j
public final class SvgImporter {

    private SvgImporter() {
        throw new IllegalStateException("You cannot instantiate me!");
    }

    public static Optional<BufferedImage> importSvg(final File file, final int width, final int height) {

        if (!file.exists()) {
            log.error("The file does not exist!");
            return Optional.empty();
        }

        var transcoder = new PNGTranscoder();

        transcoder.addTranscodingHint(SVGAbstractTranscoder.KEY_WIDTH, (float) width);
        transcoder.addTranscodingHint(SVGAbstractTranscoder.KEY_HEIGHT, (float) height);

        try (var inputStream = new FileInputStream(file)) {

            var input = new TranscoderInput(inputStream);

            var outputStream = new ByteArrayOutputStream();
            var output = new TranscoderOutput(outputStream);

            transcoder.transcode(input, output);

            outputStream.flush();
            outputStream.close();

            var imageData = outputStream.toByteArray();
            return Optional.ofNullable(ImageIO.read(new ByteArrayInputStream(imageData)));

        } catch (IOException | TranscoderException e) {
            log.error("Failed to load images!", e);
        }

        return Optional.empty();
    }
}
