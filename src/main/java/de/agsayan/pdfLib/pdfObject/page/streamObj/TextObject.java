package de.agsayan.pdfLib.pdfObject.page.streamObj;

import java.util.ArrayList;
import java.util.Arrays;

public class TextObject extends StreamObj {

    private static final String BeginText = "BT";
    private static final String EndText = "ET";
    private static final String TextFont = "Tf";
    private static final String TextOffset = "Td";
    private static final String ShowText = "Tj";

    private String text;
    private String textAlignment;
    private String textFont;
    private boolean isCursive;
    private boolean isBold;
    private boolean isUnderlined;
    private int fontReference;
    private int textSize;
    private String textColor;

    public String textStreamContent(String text,int pageHeigth, int pageWidth) {

        String[] lines = text.split("\n");
        String newText = "";
        int textSize = 15;
        int yPos = pageHeigth-textSize;
        int xPos = pageWidth-textSize;
        for(String line : lines) {
            if((line.length()*15)>xPos) {
                String[] parsedStr = line.replaceAll("(.{"+(xPos/textSize)*2+"})", "$1\n").split("\n");
                parsedStr = checkSentence(parsedStr);
                for(String test : parsedStr) {
                    newText += pdfTxtSyntax(test,textSize,31,yPos);
                    yPos-=textSize;
                }
            } else {
                newText += pdfTxtSyntax(line,textSize,31,yPos);

            }
            yPos-=textSize;

        }

        return newText;
    }

    private String pdfTxtSyntax(String line, int textSize, float xPos, float yPos) {
        System.out.println(fontReference);
        String txtSyntax = "         "+ BeginText +"\n"
                + "             /F"+ fontReference +" "+textSize+" "+TextFont+"\n" // /F1 is a reference to TextFont
                + "             "+xPos+" "+yPos+" "+TextOffset+"\n"      // xPos and yPos
                + "             ("+line+") "+ShowText+"\n"
                + "         "+ EndText +"\n";

        return txtSyntax;
    }

    private String[] checkSentence(String[] lines) {
        String newText = "";
        String prefix = "";
        for(int i=0;i<lines.length;i++ ) {
            try {
                if(lines[i].charAt(lines[i].length()-1)!=' ' && lines[i+1].charAt(0)!=' '){
                    String[] t =lines[i].split(" ");
                    ArrayList<String> te = new ArrayList<>(Arrays.asList(t));
                    te.remove(te.size()-1);
                    String line = String.join(" ",te)+"\n";
                    newText+=prefix+line;
                    //newText+=i+1+" "++lines[i+1]+"\n";
                    prefix=t[t.length-1];
                    // i++;
                } else {
                    newText+=prefix+lines[i]+"\n";
                    prefix="";

                }
            } catch (ArrayIndexOutOfBoundsException e) {
                newText+=prefix+lines[i]+"\n";
                prefix="";
            }
        }
        String[] liness = newText.split("\n");
        return liness;
    }


    public void setText(String text, int textSize) {
        this.text=text;
        this.textSize=textSize;
    }

    public String getText() {
        return this.text;
    }

    public String getTextAlignment() {
        return textAlignment;
    }

    public void setTextAlignment(String textAlignment) {
        this.textAlignment = textAlignment;
    }

    public boolean isCursive() {
        return isCursive;
    }

    public void setCursive(boolean cursive) {
        isCursive = cursive;
    }

    public boolean isBold() {
        return isBold;
    }

    public void setBold(boolean bold) {
        isBold = bold;
    }

    public boolean isUnderlined() {
        return isUnderlined;
    }

    public void setUnderlined(boolean underlined) {
        isUnderlined = underlined;
    }

    public int getFontReference() {
        return fontReference;
    }

    public void setFontReference(int fontReference) {
        this.fontReference = fontReference;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }


    @Override
    public String buildStream() {
        return pdfTxtSyntax(getText(),getTextSize(),xPos,yPos);
    }

    public String getTextFont() {
        return textFont;
    }

    public void setTextFont(String textFont) {
        this.textFont = textFont;
    }
}
