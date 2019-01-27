package com.weixin.FileOperate;


import com.weixin.word.impl.WordServiceImpl;
import org.apache.pdfbox.PDFReader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * @author yan.yuchen111@ztesoft.com
 * @create 2019/1/12
 * @since 1.0.0
 */
@Service
public class readPDF2 {

    @Autowired
    private WordServiceImpl wordServiceImpl;

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        PDFReader pdfReader = new PDFReader();
        try {
            // 取得E盘下的SpringGuide.pdf的内容
//            readPDF2.readFdf("C:\\Users\\49040\\Desktop\\新建文件夹\\10319990\\73NG AMM_9309\\70CFM56.PDF",4,109);
//            readPDF2.readFdf("D:\\word\\a.pdf",1,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void readFdf(String file,int startPage,int endPage) throws Exception {
        // 是否排序
        boolean sort = false;
        // pdf文件名
        String pdfFile = file;
        // 输入文本文件名称
        String textFile = null;
        // 编码方式
        String encoding = "UTF-8";
        // 开始提取页数
        startPage = 1;
        // 结束提取页数
        endPage = 100;
        // 文件输入流，生成文本文件
        Writer output = null;
        // 内存中存储的PDF Document
        PDDocument document = null;
        try {
            try {
                // 首先当作一个URL来装载文件，如果得到异常再从本地文件系统//去装载文件
                URL url = new URL(pdfFile);
                //注意参数已不是以前版本中的URL.而是File。
                document = PDDocument.load(pdfFile);
                // 获取PDF的文件名
                String fileName = url.getFile();
                // 以原来PDF的名称来命名新产生的txt文件
                if (fileName.length() > 4) {
                    File outputFile = new File(fileName.substring(0, fileName
                            .length() - 4)
                            + ".txt");
                    textFile = outputFile.getName();
                }
            } catch (MalformedURLException e) {
                // 如果作为URL装载得到异常则从文件系统装载
                //注意参数已不是以前版本中的URL.而是File。
                document = PDDocument.load(pdfFile);
                if (pdfFile.length() > 4) {
                    textFile = pdfFile.substring(0, pdfFile.length() - 4)
                            + ".txt";
                }
            }
            // 文件输入流，写入文件倒textFile
//            output = new OutputStreamWriter(new FileOutputStream(textFile),
//                    encoding);
            // PDFTextStripper来提取文本
            PDFTextStripper stripper = null;
            stripper = new PDFTextStripper();
            // 设置是否排序
            stripper.setSortByPosition(sort);
            // 设置起始页
            stripper.setStartPage(startPage);
            // 设置结束页
            stripper.setEndPage(endPage);
            // 调用PDFTextStripper的writeText提取并输出文本
            String words = stripper.getText(document);
            Set<String> pdf_word = new HashSet() ;
            HashMap<String, Integer > hashMap=new HashMap<String,Integer>();

            String [] lines = words.split("\r\n");
            for(int i=0;i<lines.length;i++){
                String [] word = lines[i].split("\\s+");
                for(int j = 0;j<word.length;j++) {
                    boolean result = word[j].matches("[-a-zA-Z]+");
                    if(result&&word[j].length()>4) {
                        if (pdf_word.contains(word[j])) {
                            Integer number = hashMap.get(word[j]);
                            number++;
                            hashMap.put(word[j], number);

                        } else {
                            pdf_word.add(word[j]);
                            hashMap.put(word[j], 1);

                        }
                    }
                 }
            }
            List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(hashMap.entrySet()); //转换为list
            list.sort(new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o2.getValue().compareTo(o1.getValue());
                }
            });

            try {
                File writeName = new File(textFile); // 相对路径，如果没有则要建立一个新的output.txt文件
                writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
                try (FileWriter writer = new FileWriter(writeName);
                     BufferedWriter out = new BufferedWriter(writer)
                ) {
                    for (Map.Entry<String, Integer> m : list)
                    {
                        String k = m.getKey();
                        String val = m.getValue().toString();
                        StringBuffer r = new StringBuffer();
                        List<java.util.Map<java.lang.String,java.lang.Object>> x =  wordServiceImpl.getMeans(k);
                        r.append(k).append(":").append(val).append("\r\n");

                        for (int i=0;i<x.size();i++){
                            String partOfSpeech = (String) x.get(i).get("partofspeech");
                            String mean = (String) x.get(i).get("means");
                            r.append("\t").append(partOfSpeech).append(" ").append(mean).append("\r\n");
                        }
                        r.append("\r\n");
                        out.write(r.toString());
                    }
                    out.flush(); // 把缓存区内容压入文件
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

//            stripper.writeText(document, output);
//            System.out.println(words);
        } finally {
//            if (output != null) {
//                // 关闭输出流
//                output.close();
//            }

            if (document != null) {
                // 关闭PDF Document
                document.close();
            }
        }
    }


}
