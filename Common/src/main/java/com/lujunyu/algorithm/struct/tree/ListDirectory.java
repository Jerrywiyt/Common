package com.lujunyu.algorithm.struct.tree;

import java.io.File;
import java.util.LinkedList;

/**
 * 遍历文件夹。
 */
public class ListDirectory {
    public static void main(String[] args){
//        listAll(0,new File("E:\\musics\\"));
        listAll2(new File("E:\\musics\\"));
    }
    /**
     * 先序遍历法。
     */
    private static void listAll(int depth,File file){
        if(!file.exists()){
            return;
        }
        System.out.println(getDepth(depth) + file.getName());

        if(file.isDirectory()){
            depth++;
            File[] files = file.listFiles();
            if(files!=null&&files.length>0){
                for(File file1 : files){
                    listAll(depth,file1);
                }
            }
        }
    }

    private static String getDepth(int depth) {
        StringBuilder res = new StringBuilder();
        for(int i=0;i<depth;i++){
            res.append("-");
        }
        return res.toString();
    }

    /**
     * 层序遍历法
     */
    private static void listAll2(File file){
        if(file==null){
            return;
        }
        LinkedList<File> linkedList = new LinkedList<>();
        linkedList.addLast(file);
        while(!linkedList.isEmpty()){
            File first = linkedList.removeFirst();
            System.out.println(first.getAbsolutePath());
            if(first.isDirectory()){
                File[] files = first.listFiles();
                if(files!=null&&files.length>0){
                    for(File f : files){
                        linkedList.addLast(f);
                    }
                }
            }
        }
    }
}
