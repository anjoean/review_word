package com.weixin.word.controller;

import com.weixin.common.page.PageDao;
import com.weixin.common.page.PageList;
import com.weixin.word.entity.Word;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weixin.FileOperate.readPDF2;
import com.weixin.common.BaseController;
import com.weixin.common.exception.CustomException;
import com.weixin.common.R;
import com.weixin.word.impl.WordServiceImpl;
import com.weixin.word.mapper.WordMapper;
import io.swagger.annotations.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @create 2019/1/13
 * @since 1.0.0
 */
@RestController
@RequestMapping("/word")
@Api("单词相关服务")
public class WordController extends BaseController {

    @Autowired
    private WordServiceImpl wordServiceImpl;

    @Autowired
    private readPDF2 readPDF2;

    @Autowired
    private WordMapper wordMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PageDao pageDao;

    @RequestMapping(value = "/ReadtPDF",method = RequestMethod.POST)
    @ApiOperation(value = "读取PDF中的单词，并生成相应文件")
    public void readPDF() {
        //读取
//        ReadSQL();

        try {
            readPDF2.readFdf("C:\\Users\\49040\\Desktop\\微信开发词典相关\\10319990\\73NG AMM_9309\\70CFM56.PDF",4,109);
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

    @RequestMapping(value = "/countByWord",method = RequestMethod.POST)
    @ApiOperation(value = "根据单词，查找个数")
    @ApiImplicitParams({@ApiImplicitParam(value="单词",name="单词信息",dataType = "String",paramType = "body")})
    public int countByWord( String word) {
        return  wordMapper.countByword(word);
    }

    @RequestMapping(value = "/qryWord",method = RequestMethod.POST)
    @ApiOperation(value = "查询单词")
    public PageInfo<Word> qryWord( String word) {
        PageHelper.startPage(1 , 10);
        List<Word> personList = wordMapper.qryWord();
        //得到分页的结果对象
        PageInfo<Word> personPageInfo = new PageInfo<>(personList);

        return personPageInfo;
    }

    @RequestMapping(value = "/exceptionTest",method = RequestMethod.POST)
    public String exceptionTest( int type) {
        if(type ==1) {
            throw new CustomException(400, "num不能为空");
        }else if(type ==2){
            throw new CustomException(400, "除数为0");
        }
        return "exceptionTest";
    }

    @RequestMapping(value = "/R",method = RequestMethod.POST)
    public String RTest(  ) {
         return renderJson(R.ok());
    }

    @RequestMapping(value = "/qryWordByPdfName",method = RequestMethod.POST)
    public String qryWordByPdfName(String pdfName,int page,int pageRow){
        String sql = "select * from pdf_words p where p.pdf_name = ? order by times desc ";
        PageList dataList = new PageList();
        try{
            dataList = pageDao.queryForList(page,pageRow,sql,new Object[]{pdfName});
        }catch (Exception e ){
            e.printStackTrace();
            return renderJson(R.error(e.toString()));
        }
        Map retMap = new HashMap();
        retMap.put("dataList", dataList);
        return renderJson(R.ok(retMap));
    }

}
