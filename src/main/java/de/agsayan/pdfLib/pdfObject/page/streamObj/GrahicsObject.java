package de.agsayan.pdfLib.pdfObject.page.streamObj;

public class GrahicsObject extends StreamObject {

    private static final String MovePointer = "m";
    private static final String LINE = "l";
    private static final String LineWidth = "w";
    private static final String Rectangle = "re";
    private static final String Fill = "f";
    private static final String BEZIER_CURVE = "c";
    private static final String BorderColor = "rg";
    private static final String Fill_Color = "RG";
    private static final String Stroke = "S";
    private static final String CLOSE_FILL_STROKE = "b";

    private int lineWith = 1;

    private String graphic;

    public String drawLine(int xFrom, int yFrom, int xTo, int yTo) {
        String lineWidth = "3 "+ LineWidth +"\n";

        String color =""+lineWidth
                +"          "+"0.0 1.0 0.0"+ Fill_Color +"\n"
                + "         "+"0.0 0.0 0.5 "+ BorderColor +"\n";

        String line = "\n         "+color
                + +xFrom + " " + yFrom + " " + MovePointer + "\n"
                + "         " + xTo + " " + yTo + " \n"
                + "         "+LINE + " " + Stroke +"\n";
        return line;
    }

    public String drawRect(int a, int b, int c, int d) {
        String color =""
                +"          "+"1.0 1.0 1.0"+ Fill_Color +"\n"
                + "         "+"0.0 0.0 0.0 "+ BorderColor +"\n";

        String rect ="\n "+color
                + a + " " + b + " " + c + " " + d + " " + Rectangle + " " + Stroke;
        setGraphic(rect);

        return "";
    }

    public String drawFrame() {
        String frame = "";

        int y=300;
        frame+=drawLine(100,100+y,300,100+y);
        frame+=drawLine(300,100+y,300,0+y);
        frame+=drawLine(100,100+y,100,0+y);
        frame+=drawLine(100,0+y,300,0+y);

        setGraphic(frame);
        return frame;
    }

    public void setGraphic(String graphic) {
        this.graphic = graphic;
    }

    @Override
    public String buildStream() {
        return graphic;
    }
}
