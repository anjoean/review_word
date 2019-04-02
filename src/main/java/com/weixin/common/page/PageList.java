package com.weixin.common.page;


/**
 * @create 2019/3/25
 * @since 1.0.0
 */

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装分页对象
 **/
@Data
public class PageList {
    private int page;   //当前页
    private int totalRows;   //总行数
    private int pages;    //总页数
    private List list=new ArrayList();
    public PageList(){

    }
    public PageList(int page ,int totalRows,int pages){
        this.page = page;
        this.totalRows = totalRows;
        this.pages = pages;
    }

}