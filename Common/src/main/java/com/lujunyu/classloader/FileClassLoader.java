package com.lujunyu.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class FileClassLoader extends ClassLoader{
    private String root;
    public FileClassLoader(String root){
        this.root = root;
    }
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        String filepath = root + name.replace(".",File.separator) + ".class";
        File file = new File(filepath);
        if(!file.exists()||file.isDirectory()){
            return null;
        }
        try (InputStream in = new FileInputStream(file)) {
            int len = in.available();
            byte[] arr = new byte[len];
            in.read(arr);
            return defineClass(name,arr,0,len);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
