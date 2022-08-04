/*
 * Copyright (C) 2022  Víctor Mardones
 * The full notice can be found at README.md in the root directory.
 */

package cl.vmardones.chess.io;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.SVGAbstractTranscoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

@Slf4j
final class SvgImporter {

  private SvgImporter() {
    throw new UnsupportedOperationException("You cannot instantiate me!");
  }

  static Optional<BufferedImage> get(
      final InputStream inputStream, final int width, final int height) throws IOException {

    if (inputStream == null) {
      log.error("The file does not exist!");
      return Optional.empty();
    }

    final var transcoder = new PNGTranscoder();

    transcoder.addTranscodingHint(SVGAbstractTranscoder.KEY_WIDTH, (float) width);
    transcoder.addTranscodingHint(SVGAbstractTranscoder.KEY_HEIGHT, (float) height);

    try (inputStream;
        final var outputStream = new ByteArrayOutputStream()) {

      final var input = new TranscoderInput(inputStream);
      final var output = new TranscoderOutput(outputStream);

      transcoder.transcode(input, output);

      outputStream.flush();

      final var imageData = outputStream.toByteArray();
      return Optional.ofNullable(ImageIO.read(new ByteArrayInputStream(imageData)));

    } catch (final TranscoderException e) {
      log.error("Failed to transcode the SVG image!", e);
      return Optional.empty();
    }
  }
}
