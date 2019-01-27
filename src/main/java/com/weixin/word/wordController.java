package com.weixin.word;


import com.weixin.FileOperate.readPDF2;
import com.weixin.word.impl.WordServiceImpl;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * @author yan.yuchen111@ztesoft.com
 * @create 2019/1/13
 * @since 1.0.0
 */
@RestController

public class wordController {

    @Autowired
    private WordServiceImpl wordServiceImpl;

    @Autowired
    private readPDF2 readPDF2;

    @RequestMapping("/insertWords")
    public void insertWords() {
        //读取
//        ReadSQL();

        try {
            readPDF2.readFdf("C:\\Users\\49040\\Desktop\\新建文件夹\\10319990\\73NG AMM_9309\\70CFM56.PDF",4,109);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ReadSQL(){
        File file=new File("C:\\Users\\49040\\Desktop\\新建文件夹\\10319990\\words.txt");
        BufferedReader reader=null;
        String temp=null;
        try{
            reader=new BufferedReader(new FileReader(file));
            for(int i=1;i<=367369;i++){
                temp=reader.readLine();
            }
            for(int i=367370;i<=502483;i++){
                temp=reader.readLine();
                wordServiceImpl.insertWord(temp);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(reader!=null){
                try{
                    reader.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

}
