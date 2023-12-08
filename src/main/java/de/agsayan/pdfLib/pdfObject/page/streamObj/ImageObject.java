package de.agsayan.pdfLib.pdfObject.page.streamObj;

import de.agsayan.pdfLib.pdfObject.page.XObject;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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
  private byte[] img;
  private XObject imgXObj;
  ImageType imageType = ImageType.unknown;

  public ImageObject(String imgPath) {
    super();
    this.imgPath = imgPath;
    imageType = ImageType.valueOf(imgPath.split("\\.")[1] + "");

    imgXObj = new XObject();
    File imgFile = new File(imgPath);
    setLength(imgFile.length());
    imgXObj.setLength(getLength());
    try {
      BufferedImage bufferedImg = ImageIO.read(imgFile);
      imgXObj.setWidth(bufferedImg.getWidth());
      imgXObj.setHeight(bufferedImg.getHeight());

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(bufferedImg, "jpg", baos);
      img = baos.toByteArray();
    } catch (IOException e) {
      // pass
    }
  }

  private String paintImg(float posX, float posY, float heigth, float width) {
    return START_PAINT + "\n"
        + "1.00000 0.00000 0.00000 1.00000 0.00000 0.00000 cm\n" + width +
        " 0.00000 0.00000 " + heigth + " " + posX + " " + posY + " cm\n" +
        reference + " " + PAINT_IMG + "\n" + END_PAINT + "\n";
  }

  // zur zeit nur jpg format
  public String embedXObjectWithImg() throws IOException {
    return buildXObject().buildStream();
  }

  public XObject buildXObject() {
    String subtype = "Image";
    String filter = getEncoding();
    String colorSpace = "DeviceRGB";

    imgXObj.setSubtype(subtype);
    imgXObj.setColorSpace(colorSpace);
    imgXObj.setFilter(filter);
    imgXObj.setContent(new String(Base64.getEncoder().encode(img)));
    return imgXObj;
  }

  @Override
  public void write(OutputStream os) {
    buildXObject().write(os);
    // super.write(os);
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
    setGraphic(paintImg(xPosTextSpaceUnit, yPosTextSpaceUnit, heigth, width));
    try {
      return embedXObjectWithImg() + super.buildStream();
    } catch (IOException e) {
      return "";
    }
  }

  public float getHeigth() { return heigth; }

  public void setHeigth(float heigth) { this.heigth = heigth; }

  public float getWidth() { return width; }

  public void setWidth(float width) { this.width = width; }

  public void setReference(int reference) { this.reference = "/I" + reference; }

  public String getReference() { return reference; }

  public int getObjectPos() { return objectPos; }

  public void setObjectPos(int objectPos) { this.objectPos = objectPos; }
}
