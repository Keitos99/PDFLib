package de.agsayan.pdfLib.pdfObject.page.streamObj;

public class FrameObject extends GrahicsObject{

    public String drawFrame() {
        String frame = "";

        int y=300;
        frame+=drawLine(98,100+y,302,100+y);
        frame+=drawLine(300,100+y,300,0+y);
        frame+=drawLine(100,100+y,100,0+y);
        frame+=drawLine(100,0+y,300,0+y);

        setGraphic(frame);
        return frame;
    }
}
