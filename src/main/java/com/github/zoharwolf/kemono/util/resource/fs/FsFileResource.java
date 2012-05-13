package com.github.zoharwolf.kemono.util.resource.fs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.github.zoharwolf.kemono.util.resource.DirResource;
import com.github.zoharwolf.kemono.util.resource.FileResource;

/**
 * 从文件系统中获取文件资源类
 * @author zohar
 *
 */
public class FsFileResource implements FileResource
{
    String path;
    String rootPath;
    FsDirResource parent;
    
    public FsFileResource(String rootPath, String path, FsDirResource parent)
    {
        this.rootPath = rootPath;
        this.path = path;
        this.parent = parent;
    }

    @Override
    public String getPath()
    {
        return path;
    }

    @Override
    public int getSize()
    {
        File file = new File(rootPath + path);
        return (int) file.length();
    }

    @Override
    public InputStream getInputStream()
    {
        File file = new File(rootPath + path);
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(file);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return fis;
        
    }
    
    @Override
    public String toString()
    {
        return "fs: [root:" + rootPath + "][path:" + path +"]";
    }

    @Override
    public DirResource getParent()
    {
        return parent;
    }



}
