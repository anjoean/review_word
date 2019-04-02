package com.weixin.FileOperate;


import com.weixin.word.impl.WordServiceImpl;
import org.apache.pdfbox.PDFReader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * @author yan.yuchen111@ztesoft.weixin
 * @create 2019/1/12
 * @since 1.0.0
 */
@Service
public class readPDF2 {

    @Autowired
    private WordServiceImpl wordServiceImpl;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        PDFReader pdfReader = new PDFReader();
        try {
            // 取得E盘下的SpringGuide.pdf的内容
//            readFdf("C:\\Users\\49040\\Desktop\\微信开发词典相关\\10319990\\73NG AMM_9309\\70CFM56.PDF",4,109);
//            readPDF2.readFdf("D:\\word\\a.pdf",1,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param file
     * @param startPage
     * @param endPage
     * @throws Exception
     */
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
            Set<String> pdf_word = new HashSet() ;              //存储单词 去重使用  全都存放单词的小写形式

            HashMap<String, Integer > hashMap=new HashMap<String,Integer>();  //String  存贮单词本身 Integer存贮单词出现的次数

            String [] lines = words.split("\r\n");
            for(int i=0;i<lines.length;i++){
                String [] word = lines[i].split("\\s+");
                for(int j = 0;j<word.length;j++) {
                    boolean isWord = word[j].matches("[-a-zA-Z]+");
                    boolean isUpperCase = word[j].matches("[-A-Z]+");

                    if(isWord&&word[j].length()>4) {
                        String str = "";
                        if(!isUpperCase) {    //如果单词 不全是大写 则变成小写
                            str = word[j].toLowerCase();  //全部变成小写
                        }else{                //如果单词 全是大写 则按大写字母存储
                            str = word[j];
                        }
                        String upperCase = str.toUpperCase();
                        if (pdf_word.contains(str)) {
                            Integer number = hashMap.get(str);

                            number++;
                            hashMap.put(str, number);

                        } else {
                            pdf_word.add(str);
                            hashMap.put(str, 1);
                        }
                    }
                 }
            }
            for(String str: pdf_word){
                boolean isUpperCase = str.matches("[-A-Z]+");
                String lowerCase = str.toLowerCase();
                if(isUpperCase&&pdf_word.contains(lowerCase)){
                    int upperCaseNum = hashMap.get(str);
                    int lowerCaseNum = hashMap.get(lowerCase);
                    hashMap.put(lowerCase, upperCaseNum+lowerCaseNum);
                    hashMap.remove(str);
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
                        if(wordServiceImpl.isPlural(k)){
                            continue;
                        }

                        List<java.util.Map<java.lang.String,java.lang.Object>> x =  wordServiceImpl.getMeans(k);
                        r.append(k).append(":").append(val).append("\r\n");
                        StringBuffer meansBuffer = new StringBuffer();
                        for (int i=0;i<x.size();i++){
                            String partOfSpeech = (String) x.get(i).get("partofspeech");
                            String mean = (String) x.get(i).get("means");
                            r.append("\t").append(partOfSpeech).append(" ").append(mean).append("\r\n");
                            meansBuffer.append(partOfSpeech).append(" ").append(mean).append("\r");
                        }
                        String means = meansBuffer.toString();
                        String insertSql = "insert into pdf_words (word,times,means,voice,pdf_name) values (?,?,?,?,?)";
                        if(x.size()>0) {
                            jdbcTemplate.update(insertSql, new Object[]{k, val, means, x.get(0).get("voice"), pdfFile.substring(pdfFile.lastIndexOf("\\") + 1, pdfFile.length() - 4)});
                        }else {
                            jdbcTemplate.update(insertSql, new Object[]{k, val, means, "", pdfFile.substring(pdfFile.lastIndexOf("\\") + 1, pdfFile.length() - 4)});
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
