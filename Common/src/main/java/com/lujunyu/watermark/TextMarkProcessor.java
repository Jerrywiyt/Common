package com.lujunyu.watermark;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;

/**
 * 添加文字水印
 *
 * @author Ricky Fung
 */
public class TextMarkProcessor {

  /** @param args */
  public static void main(String[] args) {

    new TextMarkProcessor().testTextMark();
  }

  public void testTextMark() {
    File srcImgFile = new File("/Users/jerry_lu/Desktop/11.png");
    String logoText = "[ 天使的翅膀 ]";

    File outputRotateImageFile = new File("/Users/jerry_lu/Desktop/22.png");

    createWaterMarkByText(srcImgFile, logoText, outputRotateImageFile, 0);
  }

  public void createWaterMarkByText(
      File srcImgFile, String logoText, File outputImageFile, double degree) {
    OutputStream os = null;
    try {
      Image srcImg = ImageIO.read(srcImgFile);

      BufferedImage buffImg =
          new BufferedImage(
              srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

      Graphics2D graphics = buffImg.createGraphics();
      graphics.setRenderingHint(
          RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      graphics.drawImage(
          srcImg.getScaledInstance(
              srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH),
          0,
          0,
          null);

      if (degree > 0) {
        graphics.rotate(
            Math.toRadians(degree),
            (double) buffImg.getWidth() / 2,
            (double) buffImg.getHeight() / 2);
      }
      graphics.setColor(Color.RED);
      graphics.setFont(new Font("宋体", Font.BOLD, 36));

      float alpha = 0.8f;
      graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

      graphics.drawString(logoText, buffImg.getWidth() / 3, buffImg.getHeight() / 2);
      graphics.dispose();

      os = new FileOutputStream(outputImageFile);
      // 生成图片
      ImageIO.write(buffImg, "JPG", os);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (null != os) os.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
