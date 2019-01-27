package com.weixin.word.impl;


import com.weixin.word.wordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author yan.yuchen111@ztesoft.com
 * @create 2019/1/13
 * @since 1.0.0
 */



@Service
public class WordServiceImpl implements wordService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insertWord(String sql) {
        jdbcTemplate.update(sql);
    }

    @Override
    public
    List<java.util.Map<java.lang.String,java.lang.Object>> getMeans(String words){
        String sql = "select  c.partofspeech,m.means from  cixing c,words w,means m where c.posID=m.posID and w.ID = m.wordID and w.word = ?";
        List<java.util.Map<java.lang.String,java.lang.Object>> x =  jdbcTemplate.queryForList(sql, new Object[]{words});
        return x;
    }

}