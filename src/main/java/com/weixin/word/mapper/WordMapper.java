package com.weixin.word.mapper;


/**
 * @author yan.yuchen111@ztesoft.com
 * @create 2019/3/12
 * @since 1.0.0
 */
import com.weixin.word.entity.Word;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * t_user 操作：演示两种方式
 * <p>第一种是基于mybatis3.x版本后提供的注解方式<p/>
 * <p>第二种是早期写法，将SQL写在 XML 中<p/>
 *
 * @author Levin
 * @since 2018/5/7 0007
 */
@Repository
public interface WordMapper extends BaseMapper<Word> {
    int countByword(String word);
    List<Word> qryWord();
}
