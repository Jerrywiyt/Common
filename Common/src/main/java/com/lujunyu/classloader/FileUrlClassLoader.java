package com.lujunyu.classloader;

import sun.net.www.ParseUtil;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class FileUrlClassLoader extends URLClassLoader {
    private FileUrlClassLoader(URL[] urls) {
        super(urls);
    }


    public static FileUrlClassLoader getInstance(String[] filepaths) throws MalformedURLException {
        if(filepaths==null||filepaths.length==0){
            return null;
        }
        URL[] urls = new URL[filepaths.length];
        for(int i=0;i<filepaths.length;i++){
            urls[i] = ParseUtil.fileToEncodedURL(new File(filepaths[i]));
        }
        return new FileUrlClassLoader(urls);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }

}
