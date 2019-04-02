package com.weixin.word.entity;


import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author yan.yuchen111
 * @create 2019/3/12
 */
@Table(name = "words")
@Data
public class Word implements Serializable {

    private static final long serialVersionUID = 8280900153723114715L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String word;
    private String exchange;
    private String voice;
    private String times;
}
