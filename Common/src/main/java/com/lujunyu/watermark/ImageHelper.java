package com.lujunyu.watermark;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageHelper {
  public static BufferedImage buffer(Image img) {
    if (img instanceof BufferedImage) return (BufferedImage) img;

    /* Create a buffered image with transparency */
    BufferedImage o =
        new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

    /* Draw the image on to the buffered image */
    Graphics2D bGr = o.createGraphics();
    bGr.drawImage(img, 0, 0, null);
    bGr.dispose();

    /* Return the buffered image */
    return o;
  }

  static Image unbuffer(BufferedImage bufferedImage, String format) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ImageIO.write(bufferedImage, format, bos);
    return ImageIO.read(new ByteArrayInputStream(bos.toByteArray()));
  }

  static byte[] flat(BufferedImage bufferedImage, String format) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ImageIO.write(bufferedImage, format, bos);
    return bos.toByteArray();
  }

  static Image deflat(byte[] rawOutput, String format) throws IOException {
    ByteArrayInputStream bis = new ByteArrayInputStream(rawOutput);
    return ImageIO.read(bis);
  }
}
