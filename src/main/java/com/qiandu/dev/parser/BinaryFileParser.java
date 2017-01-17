package com.qiandu.dev.parser;

import com.qiandu.dev.bean.BinaryFileBean;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by LTN on 2016/5/7.
 */
public class BinaryFileParser {

    /**
     *
     * @param inputStream 传入的文档流
     * @return 解析得到的bean
     */
    public static BinaryFileBean parseBinary(InputStream inputStream){
        BinaryFileBean bean=new BinaryFileBean();

        // 自动检测文档类型，自动创建相应的解析器
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();

        File file=null;
        String fileName = "";
        try {
            ParseContext context = new ParseContext();
            context.set(Parser.class, parser);
            parser.parse(inputStream, handler, metadata, context);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }

        //获取单纯的文件名，不带文件后缀
        bean.setType(metadata.get("Content-Type")); //文件类型
        bean.setContent(handler.toString()); // 文件内容
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public static BinaryFileBean parseBinary(String uri){
        BinaryFileBean bean=new BinaryFileBean();

        // 自动检测文档类型，自动创建相应的解析器
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();

        File file=null;
        String fileName = "";
        try {
            file =new File(uri);
            fileName= file.getName().toString();
            FileInputStream inputstream = new FileInputStream(file);
            ParseContext context = new ParseContext();
            context.set(Parser.class, parser);
            parser.parse(inputstream, handler, metadata, context);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }

        //获取单纯的文件名，不带文件后缀
        String fileTitle=fileName.substring(0,fileName.lastIndexOf("."));
        Date nowTime= new Date();
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String parseTime =time.format(nowTime);

        bean.setUri(uri); //文件地址
        bean.setType(metadata.get("Content-Type")); //文件类型
        bean.setTitle(fileTitle); //文件名
        String content=handler.toString();
        String refineStr = content.replace("\n", "");
        bean.setContent(refineStr); // 文件内容
        return bean;
    }

    public static void main(final String[] args) throws IOException,TikaException,SAXException {
        String uri="E:\\TestData\\hdfs\\upload\\pdf\\luyi.pdf";
        BinaryFileBean bean=new BinaryFileParser().parseBinary(uri);
        System.out.print(bean.getContent());
    }
}