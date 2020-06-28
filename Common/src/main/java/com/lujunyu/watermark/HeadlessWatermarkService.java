package com.lujunyu.watermark;

import static com.lujunyu.watermark.ImageHelper.unbuffer;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Headless mode watermark service
 *
 * <p>default alpha: 0.3 package com.airbnb.watermark.service;
 *
 * <p>import static com.airbnb.watermark.service.ImageHelper.unbuffer;
 *
 * <p>import java.awt.*; import java.awt.image.BufferedImage; import java.io.IOException;
 *
 * <p>/** Headless mode watermark service
 *
 * <p>default alpha: 0.3
 */
public class HeadlessWatermarkService {

  public static void main(String[] args) throws IOException {

    File srcImgFile = new File("/Users/jerry_lu/Desktop/a.jpg");

    File outputRotateImageFile = new File("/Users/jerry_lu/Desktop/22.png");

    Image srcImg = ImageIO.read(srcImgFile);

    Color color = new Color(0x33000000, true);
    String text = "仅限于入住人信息备案";

    Image png = new HeadlessWatermarkService().execute(srcImg, text, color, "png");

    ImageIO.write((RenderedImage) png, "png", new FileOutputStream(outputRotateImageFile));
  }

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

  private static float alpha(Color color) {
    switch (color.getTransparency()) {
      case Transparency.BITMASK:
        return 1.0f;
      case Transparency.TRANSLUCENT:
        return color.getAlpha() / 255.0f;
      case Transparency.OPAQUE:
      default:
        return 0.3f;
    }
  }

  private Image execute(Image image, String text, Color color, String format) throws IOException {
    if (text.isEmpty()) return image;
    BufferedImage source = ImageHelper.buffer(image);
    Graphics2D graphics = (Graphics2D) source.getGraphics();

    AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
    graphics.setComposite(alphaComposite);

    drawText(graphics, text, source, color);
    Image result = unbuffer(source, format);
    graphics.dispose();
    return result;
  }

  private void drawText(Graphics2D graphics, String text, BufferedImage source, Color color) {
    graphics.rotate(Math.toRadians(315), 0, source.getHeight());
    graphics.setColor(color);
    graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD | Font.ITALIC, source.getHeight() / 20));
    Rectangle2D textRect = graphics.getFontMetrics().getStringBounds(text, graphics);

    for (int j = 0; j < source.getHeight() * 2; j += textRect.getHeight() * 2) {
      for (int i = 0; i < source.getWidth() * 2; i += textRect.getWidth() * 1.2) {
        graphics.drawString(text, i, j);
      }
    }
    graphics.dispose();
  }
}
