package com.github.zoharwolf.kemono.util.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 从指定文件夹中获取文件。
 * @author zohar
 *
 */
public class FileResourceManager extends ResourceManager
{
    private String dirName; // 资源文件夹
    private File searchResult; // 查找结果
    
    public FileResourceManager(String dirName)
    {
        this.dirName = dirName;
    }
    
    /**
     * 根据指定的文件名获取inputstream，找不到文件时返回exception
     */
    
    @Override
    public InputStream getResourceAsStream(String fileName) throws FileNotFoundException
    {
        searchResult = null;
        searchFileFromPath(dirName, fileName);
        
        if (searchResult == null)
        {
            return null;
        }
        
        FileInputStream fis = null;
        fis = new FileInputStream(searchResult);
        
        searchResult = null;

        return fis;
    }


    /**
     * 从指定的path中搜索目标文件。搜索到结果之后会将其赋值给searchResult
     * @param path 指定的path名
     * @param targetFileName 目标文件名
     */
    private void searchFileFromPath(String path, String targetFileName)
    {
        // 如果已经查到指定文件，这之后的查找均无效。
        if (searchResult != null) return;
        
        File filePath = new File(path);
        if (filePath.isFile())
        {
            if (filePath.getName().equalsIgnoreCase(targetFileName))
            {
                // 将第一次查找到的结果，赋值给searchResult
                searchResult =  filePath;
            }
            else
            {
                return;
            }
        }
        else
        {
            File[] filesInPath = filePath.listFiles();
            for (File oneFile: filesInPath)
            {
                searchFileFromPath(oneFile.getPath(), targetFileName);
            }
        }
    }

}
