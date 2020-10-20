package com.lujunyu.watermark;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Test {
  static {
    /*
     Headless mode is a system configuration in which the display device,
     keyboard, or mouse is lacking. Sounds unexpected,
     but actually you can perform different operations in this mode, even with graphic data.
    */

    /*
     Set system property.
     Call this BEFORE the toolkit has been initialized, that is,
     before Toolkit.getDefaultToolkit() has been called.
    */
    System.setProperty("java.awt.headless", "true");
    assert GraphicsEnvironment.isHeadless();
    /*
     This triggers creation of the toolkit.
     Because java.awt.headless property is set to true, this
     will be an instance of headless toolkit.
    */
    Toolkit tk = Toolkit.getDefaultToolkit();
    /* Standard beep is available. */
    tk.beep();
    /*
     Check whether the application is
     running in headless mode.
    */
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    assert ge != null;
  }

  public static void main(String[] args) {
    File srcImgFile = new File("/Users/jerry_lu/Desktop/11.png");
    String logoText = "[ 天使的翅膀 ]";

    File outputRotateImageFile = new File("/Users/jerry_lu/Desktop/22.png");

    addTextWatermark(logoText, srcImgFile, outputRotateImageFile);
  }

  static void addTextWatermark(String text, File sourceImageFile, File destImageFile) {
    try {
      BufferedImage sourceImage = ImageIO.read(sourceImageFile);
      Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();

      // initializes necessary graphic properties
      AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
      g2d.setComposite(alphaChannel);
      g2d.setColor(Color.BLUE);
      g2d.setFont(new Font("Arial", Font.BOLD, 64));
      FontMetrics fontMetrics = g2d.getFontMetrics();
      Rectangle2D rect = fontMetrics.getStringBounds(text, g2d);

      // calculates the coordinate where the String is painted
      int centerX = (sourceImage.getWidth() - (int) rect.getWidth()) / 2;
      int centerY = sourceImage.getHeight() / 2;

      // paints the textual watermark
      g2d.drawString(text, centerX, centerY);

      ImageIO.write(sourceImage, "png", destImageFile);
      g2d.dispose();

      System.out.println("The tex watermark is added to the image.");

    } catch (IOException ex) {
      System.err.println(ex);
    }
  }
}
