package com.lujunyu.pdfbox;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.junit.Test;

public class Pdf2Image {

  @Test
  public void test() throws IOException {
    // Loading an existing PDF document
    File file = new File("/Users/jerry_lu/Downloads/API Gateway (Kraken) Design Doc.pdf");
    PDDocument document = PDDocument.load(file);

    // Instantiating the PDFRenderer class
    PDFRenderer renderer = new PDFRenderer(document);

    // Rendering an image from the PDF document
    BufferedImage image = renderer.renderImage(0);

    // Writing the image to a file
    ImageIO.write(image, "JPEG", new File("/Users/jerry_lu/Downloads/test-test-test.jpg"));

    System.out.println("Image created");

    // Closing the document
    document.close();
  }
}
