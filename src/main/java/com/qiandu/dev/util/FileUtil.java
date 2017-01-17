package com.qiandu.dev.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diablo on 2016/10/19.
 */
public class FileUtil {
    private static List<File> fileArrayList = new ArrayList<File>();

    public static void traverseFolder(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        traverseFolder(file2.getAbsolutePath());
                    } else {
                        if (file2.getName().endsWith(".html")||file2.getName().endsWith(".htm")||file2.getName().endsWith(".xhtml")||file2.getName().endsWith(".txt")||file2.getName().endsWith(".pdf")) {
                            fileArrayList.add(file2);
                        }
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

    public static List<File> getFileArrayList() {
        return fileArrayList;
    }

    public static void cleanFileArrayList() {
        fileArrayList.clear();
    }

    public static String readFile(File file)  {
        StringBuffer buffer = new StringBuffer();
        String fileCode=getFilecharset(file.getAbsolutePath());
        FileInputStream fInputStream;
        try {
            fInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fInputStream, fileCode);
            BufferedReader in = new BufferedReader(inputStreamReader);
            String row;
            while ((row = in.readLine()) != null) {
                buffer.append(row.trim());
            }
            in.close();
            inputStreamReader.close();
            fInputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static void saveContent(String saveFilePath, List<File> files) {
        int count = 0;
        File writename = new File(saveFilePath);
        File parentFile = writename.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdir();
        }
        if (writename.exists()) {
            writename.delete();
        }
        try {
            writename.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            for (File f : files) {
                try {
                    out.write("####begin\t" + f.getAbsolutePath() + "\t" + HtmlUtil.getHtmlContent(FileUtil.readFile(f)).getTitle() + "\r\n");
                    out.write(HtmlUtil.getHtmlContent(FileUtil.readFile(f)).getContent() + "\r\n");
                    out.write("###end" + "\r\n");
                    count += 1;
                    System.out.println("处理文件数：" + count);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("error file :" + f.getAbsolutePath());
                    continue;
                }
            }
            out.flush();
            out.close();
        }catch (Exception e){
            System.err.println("error: 创建输出文件错误");
        }
    }

    /**
     * 使用java nio 进行文件读写
     * @param saveFilePath 目标文件路径
     * @param files 源文件列表
     */
    public static void saveContentNIO(String saveFilePath, List<File> files) {
        int count = 0;
        File writename = new File(saveFilePath);
        File parentFile = writename.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdir();
        }
        if (writename.exists()) {
            writename.delete();
        }
        try {
            writename.createNewFile();
            Path path = Paths.get(saveFilePath);
            for (File f : files) {
                try {
                    String title = "####begin\t" + f.getAbsolutePath() + "\t" + HtmlUtil.getHtmlContent(FileUtil.readFile(f)).getTitle() + "\r\n";
                    String content = HtmlUtil.getHtmlContent(FileUtil.readFile(f)).getContent() + "\r\n";
                    String end = "###end" + "\r\n";
                    String s = title+content+end;
                    byte[] bytes = s.getBytes(getFilecharset(f.getAbsolutePath()));
                    Files.write(path, bytes, StandardOpenOption.APPEND);
                    count += 1;
                    System.out.println("处理文件数：" + count);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("error file :" + f.getAbsolutePath());
                    continue;
                }
            }
        }catch (Exception e){
            System.err.println("error: 创建输出文件错误");
        }
    }

    public static String getFilecharset(String sourceFile) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                return charset;
            } else if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF)
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF)

                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charset;
    }
}
