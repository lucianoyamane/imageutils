package br.lucianoyamane.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ColorsUtils {

    public static void blackOrWhite(File source, File result) {


        BufferedImage sourceBufferedImage = null;
        BufferedImage grayScaleBufferedImage = null;

        try {
            sourceBufferedImage = ImageIO.read(source);

            grayScaleBufferedImage = new BufferedImage(
                    sourceBufferedImage.getWidth(),
                    sourceBufferedImage.getHeight(),
                    BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D graphics2D = grayScaleBufferedImage.createGraphics();
            graphics2D.drawImage(sourceBufferedImage, 0, 0, null);

            graphics2D.dispose();

            ImageIO.write(grayScaleBufferedImage, "jpg", result);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (grayScaleBufferedImage != null) {
                grayScaleBufferedImage.flush();
            }
            if (sourceBufferedImage != null) {
                sourceBufferedImage.flush();
            }
        }
    }
}
