package com.lh.study.io;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 用于快速操作电脑上多个文件
 */
public class OperationFiles {

    /**
     * 根据文件名快速删除该路径下符合删除规则的文件
     * @param path
     */
    public static void deleteSameFile(String path) {
        File file = new File(path);
        String[] fileArray = file.list();

        List<String> fileList = Arrays.asList(fileArray);
        List<File> moveFiles = new ArrayList();
        for (String name : fileList) {
            String[] nameSplit = name.split("\\.");
            if (nameSplit.length != 2) continue;
            String addName = nameSplit[0] + " (1)." + nameSplit[1];
            if (fileList.contains(addName)) {
                moveFiles.add(new File(path + "\\" + addName));
            }
        }

        for (File delete : moveFiles) {
            delete.delete();
            System.out.println(delete.getName() + " is delete!");
        }
        System.out.println("删除文件数： " + moveFiles.size());
    }

    public static void main(String[] args) {
        String path = "G:\\CloudMusic";
        //deleteSameFile(path);
        findSameFile("F:\\音阙诗听");
        //new FileHandler(new File("F:\\音阙诗听\\pk\\사랑을 했다 (LOVE SCENARIO).mp3"));

    }


    /**
     * 根据文件名快速删除该路径下符合删除规则的文件
     * @param path
     */
    public static void findSameFile(String path) {
        File folderPath = new File(path);
        File[] fileArray = folderPath.listFiles();

        List<File> fileList = Arrays.asList(fileArray);
        List<FileHandler> canOptList = new ArrayList();
        List<FileHandler> canNotOptList = new ArrayList();
        for (File file : fileList) {
            FileHandler fileHandler = new FileHandler(file);
            if (fileHandler.isOK()) {
                canOptList.add(fileHandler);
            } else {
                canNotOptList.add(fileHandler);
            }
        }

        System.out.println("canNotOptList has " + canNotOptList.size() + " music。");

        for (FileHandler handler : canNotOptList) {
            System.out.println(handler.getFileName());
        }

        System.out.println();
        System.out.println("canOptList has " + canOptList.size() + " music。");

        List<FileHandler> deleteList = new ArrayList();

        Iterator<FileHandler> iterator = canOptList.iterator();

        while (iterator.hasNext()) {
            FileHandler handler = iterator.next();
            iterator.remove();
            //若移除之后链表中还存在则加入删除链表
            if (canOptList.contains(handler)) {
                deleteList.add(handler);
                continue;
            }
            if (deleteList.contains(handler)) {
                deleteList.add(handler);
            }
            //System.out.println(handler.getFileName() + "   |||   " + handler.getMusicName());
        }

        System.out.println();
        System.out.println("deleteList has " + deleteList.size() + " music。");
        Collections.sort(deleteList);
        for (FileHandler handler : deleteList) {
            System.out.println(handler.getFileName() + "   |||   " + handler.getMusicName());
        }

    }

}


class FileHandler implements Comparable{

    private String fileName;

    private String musicName;

    private File realFile;

    private boolean isOK;

    public FileHandler(File realFile) {
        this.fileName = realFile.getName();
        this.realFile = realFile;

        //去除后缀.mp3
        String noMapStr = fileName.split("\\.mp3")[0];

        try {
            //去除()（）《》【】『』及内容
            while (noMapStr.contains("(")) {
                StringBuilder sb = new StringBuilder(noMapStr);
                sb.replace(sb.indexOf("("), sb.indexOf(")") + 1, "");
                noMapStr = sb.toString();
            }

            if (noMapStr.contains("（")) {
                StringBuilder sb = new StringBuilder(noMapStr);
                sb.replace(sb.indexOf("（"), sb.indexOf("）") + 1, "");
                noMapStr = sb.toString();
            }

            if (noMapStr.contains("《")) {
                StringBuilder sb = new StringBuilder(noMapStr);
                sb.replace(sb.indexOf("《"), sb.indexOf("》") + 1, "");
                noMapStr = sb.toString();
            }

            if (noMapStr.contains("【")) {
                StringBuilder sb = new StringBuilder(noMapStr);
                sb.replace(sb.indexOf("【"), sb.indexOf("】") + 1, "");
                noMapStr = sb.toString();
            }

            if (noMapStr.contains("『")) {
                StringBuilder sb = new StringBuilder(noMapStr);
                noMapStr = sb.substring(sb.indexOf("『") + 1, sb.indexOf("』"));
            }

            //去除- 取后半段
            String [] strArray = noMapStr.split("-");
            if (strArray.length == 2) {
                this.musicName = strArray[1].trim();
                isOK = true;
            } else if (strArray.length == 1) {
                this.musicName = noMapStr;
                isOK = true;
            } else {
                musicName = "can not opera";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(fileName + " error!");
        }

    }

    public boolean isOK() {
        return isOK;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMusicName() {
        return musicName;
    }

    public File getRealFile() {
        return realFile;
    }

    public void deleteFile() {
        System.out.println(this.realFile.delete() ? fileName + "删除成功!" : fileName + "删除失败!");
    }

    public boolean equals(Object o) {
        FileHandler fileHandler = (FileHandler)o;
        return musicName.equals(fileHandler.musicName);


    }


    @Override
    public int compareTo(Object o) {
        FileHandler fileHandler = (FileHandler)o;
        return musicName.compareTo(fileHandler.getMusicName());
    }
}

