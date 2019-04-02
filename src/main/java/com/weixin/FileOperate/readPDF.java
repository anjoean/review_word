package com.weixin.FileOperate;




import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author yan.yuchen111@ztesoft.weixin
 * @create 2019/1/12
 * @since 1.0.0
 */

public class readPDF {
    public static void main(String [] args){
        String str=getTextFromPDF("D:\\word\\36 PNEUMATIC.pdf");
        System.out.println(str);
    }
    public static String getTextFromPDF(String pdfFilePath)
    {
        String result = null;
        FileInputStream fileInputStream = null;
        PDDocument document = null;
        try {
            fileInputStream = new FileInputStream(pdfFilePath);
            PDFParser parser = new PDFParser(fileInputStream);
            parser.parse();
            document = parser.getPDDocument();
            PDFTextStripper stripper = new PDFTextStripper();
            result = stripper.getText(document);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
