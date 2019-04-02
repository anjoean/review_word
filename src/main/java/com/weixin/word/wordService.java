package com.weixin.word;


import java.util.List;

/**
 * @author yan.yuchen111@ztesoft.weixin
 * @create 2019/1/13
 * @since 1.0.0
 */

public interface wordService {

    void insertWord (String sql);
    List<java.util.Map<java.lang.String,java.lang.Object>> getMeans(String words);
    void insertPdfWord(String sql);
    boolean isPlural(String word);
}
