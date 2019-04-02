package com.weixin.common.page;


import com.weixin.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @create 2019/3/25
 * @since 1.0.0
 */
@Repository
@Slf4j
public class PageDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询分页（MySQL数据库）
     *
     * @param sql     终执行查询的语句
     * @param params  填充sql语句中的问号占位符数
     * @param page    想要第几页的数据    从1开始
     * @param pagerow 每页显示多少条数    从1开始
     * @param cla     要封装成的实体元类型
     * @return pageList对象
     */
    public PageList queryForList(int page,int pagerow,String sql, Object[] params) {
        if(page<1||pagerow<1){
            throw new CustomException(500,"page 和 pageRow 都必须大于等于1");

        }

        String rowsql = "select count(*) from (" + sql + ") gmtxtabs_";   //查询总行数sql
        PageList pl = new PageList(0,0,0);
        int pages = 0;   //总页数
        try {
            int rows = jdbcTemplate.queryForObject(rowsql, params, Integer.class);  //查询总行数

            //判断页数,如果是页大小的整数倍就为rows/pageRow如果不是整数倍就为rows/pageRow+1
            if (rows % pagerow == 0) {
                pages = rows / pagerow;
            } else {
                pages = rows / pagerow + 1;
            }
            //查询第page页的数据sql语句
            if (page <= 1) {
                sql += " limit 0," + pagerow;
            } else {
                sql += " limit " + ((page - 1) * pagerow) + "," + pagerow;
            }
            //查询第page页数据
            List list = null;

            list = jdbcTemplate.queryForList(sql, params);


            //返回分页格式数据

            pl.setPage(page);  //设置显示的当前页数
            pl.setPages(pages);  //设置总页数
            pl.setList(list);   //设置当前页数据
            pl.setTotalRows(rows);    //设置总记录数
        } catch (Exception e) {
            log.error(e.toString() + e);
        }
        return pl;
    }

}
