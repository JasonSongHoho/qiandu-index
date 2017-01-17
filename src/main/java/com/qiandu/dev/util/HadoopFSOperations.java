package com.qiandu.dev.util;

import com.qiandu.dev.bean.BinaryFileBean;
import com.qiandu.dev.parser.BinaryFileParser;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

/**
 * Created by Diablo on 15/8/7.
 * describe:
 */
public class HadoopFSOperations {
    private static Configuration conf = new Configuration();
    private static FileSystem hdfs = null;
    private static FSDataOutputStream os = null;
    private static FSDataInputStream is = null;
    private static DistributedFileSystem dfs = null;

    public static void main(String[] args) throws Exception {
        com.qiandu.dev.util.FileUtil.traverseFolder("E:\\TestData\\hdfs\\upload\\pdf");
        String javaTmp = System.getProperty("java.io.tmpdir");
        List<File> files = com.qiandu.dev.util.FileUtil.getFileArrayList();
        System.setProperty("HADOOP_USER_NAME", "hdfs");
        conf.set("fs.defaultFS", "hdfs://10.110.13.57:9000");
        hdfs = FileSystem.get(conf);
//        FileStatus[] fileStatuses=HadoopFSOperations.listAll("/qiandu/channel/case/pdf");
       /* for (FileStatus fileStatus : fileStatuses) {
            FSDataInputStream fsDataInputStream=HadoopFSOperations.readHDFSFile2Stream(fileStatus.getPath().toString());
            BinaryFileBean fileBean=BinaryFileParser.parseBinary(fsDataInputStream);
            System.out.println(fileBean.getContent());
        }*/

    }

    public HadoopFSOperations(String activeNodeIP,String port) throws Exception{

        System.setProperty("HADOOP_USER_NAME", "hdfs");
        conf.set("fs.defaultFS", "hdfs://"+activeNodeIP+":"+port);
        hdfs = FileSystem.get(conf);
    }

    public void main_ori(String[] args) throws Exception {
        com.qiandu.dev.util.FileUtil.traverseFolder("/Users/Diablo/Downloads/html");
        String javaTmp = System.getProperty("java.io.tmpdir");
        List<File> files = com.qiandu.dev.util.FileUtil.getFileArrayList();
        com.qiandu.dev.util.FileUtil.saveContent(javaTmp + "result/all.txt", files);
        System.setProperty("HADOOP_USER_NAME", "hdfs");
        conf.set("fs.default.name", "hdfs://10.110.18.242:8020");
        hdfs = FileSystem.get(conf);
        mkdir("/test/html");
//        HadoopFSOperations.uploadLocalFileHDFS(javaTmp + "result/all.txt", "/test/html/all.txt");
    }

    public static void init(String hdfsURL, String user) throws IOException {
        System.setProperty("HADOOP_USER_NAME", user);
        conf.set("fs.default.name", hdfsURL);
        conf.set("fs.hdfs.impl",
                org.apache.hadoop.hdfs.DistributedFileSystem.class.getName()
        );
        conf.set("fs.file.impl",
                org.apache.hadoop.fs.LocalFileSystem.class.getName()
        );
        hdfs = FileSystem.get(conf);
    }

    /**
     * 列出所有DataNode的名字信息
     *
     * @throws IOException
     */
    public static void listDataNodeInfo() throws IOException {
        dfs = (DistributedFileSystem) FileSystem.get(conf);
        DatanodeInfo[] dataNodeStats = dfs.getDataNodeStats();
        System.out.println("hdfs集群下datanode有：");
        for (DatanodeInfo dataNode : dataNodeStats) {
            System.out.println(dataNode.getHostName() + "\t"
                    + dataNode.getName());
        }
    }

    /**
     * 列出目录下包含文件、目录
     */
    public FileStatus[] listAll(String dir) throws IOException {
        FileStatus[] stats = hdfs.listStatus(new Path(dir));
       /* System.out.println("'" + dir + "'目录包含：");
        for (int i = 0; i < stats.length; ++i) {
            if (stats[i].isFile()) {
                // 文件
                System.out.println("文件****" + stats[i].getPath().toString());
            } else if (stats[i].isDirectory()) {
                // 目录
                System.out.println("目录****" + stats[i].getPath().toString());
            } else if (stats[i].isSymlink()) {
                // 链接
                System.out.println("链接****" + stats[i].getPath().toString());
            }
        }*/
        return stats;
    }

    /**
     * 上传本地文件到hdfs
     */
    public void uploadLocalFileHDFS(String s, String d)
            throws IOException {
        Path src = new Path(s);
        Path dst = new Path(d);
        hdfs.copyFromLocalFile(src, dst);
        System.out.println("上传本地文件：" + s + "到hdfs路径：" + d + "成功");
    }

    /**
     * 取得文件块所在的位置..
     */
    public static void getLocation(String filelocation) {
        try {
            Path path = new Path(filelocation);
            FileStatus fileStatus = hdfs.getFileStatus(path);
            BlockLocation[] blkLocations = hdfs.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
            System.out.println(filelocation + "位置：");
            for (BlockLocation currentLocation : blkLocations) {
                String[] hosts = currentLocation.getHosts();
                for (String host : hosts) {
                    System.out.println(host);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建文件到hdfs
     */
    public void createNewHDFSFile(String toCreateFilePath, String content)
            throws IOException {
        os = hdfs.create(new Path(toCreateFilePath));
        os.write(content.getBytes("UTF-8"));
        System.out.println("创建文件：" + toCreateFilePath + "成功，文件内容：" + content
                + "。");
        os.close();
    }

    /**
     * 查看HDFS文件的最后修改时间
     *
     * @param hdfspath
     * @throws Exception
     */
    public static void testgetModifyTime(String hdfspath) throws Exception {

        hdfs = FileSystem.get(conf);
        Path path = new Path(hdfspath);

        FileStatus files[] = hdfs.listStatus(path);
        for (FileStatus file : files) {
            //标准时间
//            System.out.println(file.getPath() + "\t"
//                    + file.getModificationTime());
            //date格式时间
            System.out.println("文件" + file.getPath() + "最后修改时间：\t"
                    + new Date(file.getModificationTime()));

        }
    }

    /**
     * 文件重命名
     *
     * @param oldName
     * @param newName
     * @throws IOException
     */
    public static void reName(String oldName, String newName)
            throws IOException {
        hdfs = FileSystem.get(conf);
        Path oldPath = new Path(oldName);
        Path newPath = new Path(newName);
        boolean isok = hdfs.rename(oldPath, newPath);
        if (isok) {
            System.out.println("文件" + oldName + "重命名为" + newName + "成功");
        } else {
            System.out.println("文件" + oldName + "重命名失败");
        }
    }

    /**
     * 删除文件
     */
    @SuppressWarnings("deprecation")
    public boolean deleteHDFSFile(String dst) throws IOException {
        Path path = new Path(dst);
        boolean isDeleted = hdfs.delete(path);
        System.out.println("删除文件：" + path + "成功");
        return isDeleted;
    }

    /**
     * 查看文件内容
     */
    public String  readHDFSFile(String filePath) throws Exception {
        String str = "";
        try {
            Path path = new Path(filePath);
            is = hdfs.open(path);
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
//            System.out.print(filePath + "文件内容:");
            String tempStr="";
            while ((tempStr = br.readLine()) != null) {
                str+=tempStr;
//                System.out.println(str);
            }
            br.close();
            isr.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public FSDataInputStream  readHDFSFile2Stream(String filePath) throws Exception {
        FSDataInputStream fsDataInputStream=null;
        try {
            Path path = new Path(filePath);
            is = hdfs.open(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }

    /**
     * 创建目录
     */
    public void mkdir(String dir) throws IOException {
        hdfs = FileSystem.get(conf);
        hdfs.mkdirs(new Path(dir));
        System.out.println("创建目录：" + dir + "成功");
    }

    /**
     * 删除目录
     */
    @SuppressWarnings("deprecation")
    public void deleteDir(String dir) throws IOException {
        hdfs.delete(new Path(dir));
        System.out.println("删除目录：" + dir + "成功");
        hdfs.close();
    }
}
