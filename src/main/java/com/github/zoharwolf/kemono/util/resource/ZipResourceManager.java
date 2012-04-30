package com.github.zoharwolf.kemono.util.resource;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 从zip文件中获取资源的类
 * @author zohar
 *
 */
public class ZipResourceManager implements IResourceManager
{
    private String zipName;
    
    public ZipResourceManager(String zipName)
    {
        this.zipName = zipName;
    }


    /**
     * 从Zip中猎取指定名字的资源
     */
    @Override
    public InputStream getResourceAsStream(String fileName) throws FileNotFoundException
    {
        return searchEntryFromZip(fileName);
    }


    /**
     * 从zip中找到指定名字的资源，得到其ZipInputStream
     * @param targetFileName 指定的文件名
     * @return 得到的ZipInputStream， 没有找到指定文件就返回null
     */
    private ZipInputStream searchEntryFromZip(String targetFileName)
    {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        ZipInputStream zis = null;
        try
        {
            fis = new FileInputStream(zipName);
            bis = new BufferedInputStream(fis);
            zis = new ZipInputStream(bis);
            ZipEntry ze = zis.getNextEntry();
            while (ze != null)
            {
                String zeFileName = ze.getName();
                int lastIndex = zeFileName.lastIndexOf('/');
                if (lastIndex > -1 && lastIndex < zeFileName.length()-1)
                {
                    zeFileName = zeFileName.substring(lastIndex+1);
                }
                if (!ze.isDirectory() && zeFileName.equalsIgnoreCase(targetFileName))
                {
                    return zis;
                }
                ze = zis.getNextEntry();
            }
            
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return null;
    }

}
