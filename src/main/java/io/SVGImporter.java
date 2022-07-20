package io;

import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
public final class SVGImporter {

    public static BufferedImage importSVG(File file, int width, int height) {

        var transcoder = new PNGTranscoder();

        transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, (float) width);
        transcoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, (float) height);

        try (var inputStream = new FileInputStream(file)) {

            var input = new TranscoderInput(inputStream);

            var outputStream = new ByteArrayOutputStream();
            var output = new TranscoderOutput(outputStream);

            transcoder.transcode(input, output);

            outputStream.flush();
            outputStream.close();

            var imgData = outputStream.toByteArray();
            return ImageIO.read(new ByteArrayInputStream(imgData));

        } catch (IOException | TranscoderException e) {
            log.error("Failed to load images!", e);
        }

        return null;
    }

}
