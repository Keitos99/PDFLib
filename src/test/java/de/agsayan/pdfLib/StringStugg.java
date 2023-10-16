package de.agsayan.pdfLib;

import org.junit.Test;

public class StringStugg {

  public static String pdfBeginning(int i, int width, int height) {
    return "%PDF-1.4\n"
        + "%âãÏÓ\n"
        + "2 0 obj\n"
        + "<</Length 77>>stream\n"
        +
        "q\nBT\n36 806 Td\n0 -30 Td\n/F1 20 Tf\n0.15686 0 0 rg\n(Hello)Tj\n0 g\n0 0 Td\nET\nQ\n"
        + "endstream\n"
        + "endobj\n"
        + "4 0 obj\n"
        +
        "<</Type/Page/MediaBox[0 0 595 842]/Resources<</Font<</F1 1 0 R>>>>/Contents 2 0 R/Parent 3 0 R>>\n"
        + "endobj\n"
        + "5 0 obj\n"
        + "<</Length 4319/Length1 7544/Filter/FlateDecode>>stream\n";
  }

  public static String pdfEnding() {
    return "endstream\n"
        + "endobj\n"
        + "6 0 obj\n"
        +
        "<</Type/FontDescriptor/Ascent 773/CapHeight 785/Descent -1/FontBBox[-2 -277 1053 888]/FontName/UECWFS+BrotherlandPersonalUseOnly/ItalicAngle 0/StemV 80/FontFile2 5 0 R/Flags 32>>\n"
        + "endobj\n"
        + "7 0 obj\n"
        +
        "<</Type/Font/Subtype/CIDFontType2/BaseFont/UECWFS+BrotherlandPersonalUseOnly/FontDescriptor 6 0 R/CIDToGIDMap/Identity/CIDSystemInfo<</Registry(Adobe)/Ordering(Identity)/Supplement 0>>/DW 1000/W [45[651]59[677 696]]>>\n"
        + "endobj\n"
        + "8 0 obj\n"
        + "<</Length 231>>stream\n"
        + "/CIDInit /ProcSet findresource begin\n"
        + "12 dict begin\n"
        + "begincmap\n"
        + "/CIDSystemInfo\n"
        + "<< /Registry (TTX+0)\n"
        + "/Ordering (T42UV)\n"
        + "/Supplement 0\n"
        + ">> def\n"
        + "/CMapName /TTX+0 def\n"
        + "/CMapType 2 def\n"
        + "1 begincodespacerange\n"
        + "<0000><FFFF>\n"
        + "endcodespacerange\n"
        + "3 beginbfrange\n"
        + "<002d><002d><0065>\n"
        + "<003b><003b><0073>\n"
        + "<003c><003c><0074>\n"
        + "endbfrange\n"
        + "endcmap\n"
        + "CMapName currentdict /CMap defineresource pop\n"
        + "end end"
        + "endstream\n"
        + "endobj\n"
        + "1 0 obj\n"
        +
        "<</Type/Font/Subtype/Type0/BaseFont/UECWFS+BrotherlandPersonalUseOnly/Encoding/Identity-H/DescendantFonts[7 0 R]/ToUnicode 8 0 R>>\n"
        + "endobj\n"
        + "3 0 obj\n"
        + "<</Type/Pages/Count 1/Kids[4 0 R]>>\n"
        + "endobj\n"
        + "9 0 obj\n"
        + "<</Type/Catalog/Pages 3 0 R>>\n"
        + "endobj\n"
        + "10 0 obj\n"
        +
        "<</Producer(iText® 5.5.13.2 ©2000-2020 iText Group NV \\(AGPL-version\\))/CreationDate(D:20210901073719+02'00')/ModDate(D:20210901073719+02'00')>>\n"
        + "endobj\n"
        + "xref\n"
        + "0 11\n"
        + "0000000000 65535 f \n"
        + "0000005395 00000 n \n"
        + "0000000015 00000 n \n"
        + "0000005541 00000 n \n"
        + "0000000158 00000 n \n"
        + "0000000270 00000 n \n"
        + "0000004670 00000 n \n"
        + "0000004864 00000 n \n"
        + "0000005097 00000 n \n"
        + "0000005592 00000 n \n"
        + "0000005637 00000 n \n"
        + "trailer\n"
        +
        "<</Size 11/Root 9 0 R/Info 10 0 R/ID [<e3c8b082ec0ff2db9f3ac68a2f5d81e5><e3c8b082ec0ff2db9f3ac68a2f5d81e5>]>>\n"
        + "%iText-5.5.13.2\n"
        + "startxref\n"
        + "5798\n"
        + "%%EOF\n";
  }

  @Test
   public void test() {
     System.out.println(StringStugg.pdfBeginning(1, 100, 100));
     System.out.println(StringStugg.pdfEnding());
   }
}
