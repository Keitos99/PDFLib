package de.agsayan.pdfLib.pdfObject.page.streamObj;

import de.agsayan.pdfLib.pdfObject.page.XObject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageObject extends GrahicsObject {
  private enum ImageType {
    jpg,
    img,
    jpeg,
    png,
    unknown;
  }

  private final String START_PAINT = "q";
  private final String END_PAINT = "Q";
  private final String PAINT_IMG = "Do";

  private String imgPath;
  private String reference;
  // umbenennen
  private int objectPos;
  private float heigth;
  private float width;
  ImageType imageType = ImageType.unknown;

  public ImageObject(String imgPath) {
    super();
    this.imgPath = imgPath;
    imageType = ImageType.valueOf(imgPath.split("\\.")[1] + "");
    type = "XObject";
    subtype = "Image";
    bitsPerComponent = 8;
    filter = "DCTDecode";
    colorSpace = "DeviceRGB";
  }

  private String paintImg(float posX, float posY, float heigth, float width) {
    return START_PAINT + "\n"
        + "1.00000 0.00000 0.00000 1.00000 0.00000 0.00000 cm\n" + width +
        " 0.00000 0.00000 " + heigth + " " + posX + " " + posY + " cm\n" +
        reference + " " + PAINT_IMG + "\n" + END_PAINT + "\n";
  }

  // zur zeit nur jpg format
  public String embedXObjectWithImg() throws IOException {
    File imgFile = new File(imgPath);
    BufferedImage bufferedImg = ImageIO.read(imgFile);

    this.length = imgFile.length();

    XObject imgXObj = new XObject();
    imgXObj.setSubtype(subtype);
    imgXObj.setColorSpace(colorSpace);
    imgXObj.setWidth(bufferedImg.getWidth());
    imgXObj.setHeight(bufferedImg.getHeight());
    imgXObj.setFilter(filter);
    imgXObj.setContent("imgPath" + imgPath);
    imgXObj.setLength(imgFile.length());
    return imgXObj.buildStream();
  }

  private String getEncoding() {
    switch (imageType) {
    case jpg:
      return "DCTDecode";
    default:
      throw new NullPointerException("Unknown Image Format");
    }
  }

  @Override
  public String buildStream() {
    setGraphic(paintImg(xPos, yPos, heigth, width));
    return super.buildStream();
  }

  public float getHeigth() { return heigth; }

  public void setHeigth(float heigth) { this.heigth = heigth; }

  public float getWidth() { return width; }

  public void setWidth(float width) { this.width = width; }

  public void setReference(String reference) { this.reference = reference; }

  public int getObjectPos() { return objectPos; }

  public void setObjectPos(int objectPos) { this.objectPos = objectPos; }
}
