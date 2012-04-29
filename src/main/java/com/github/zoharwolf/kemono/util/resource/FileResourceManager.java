package com.github.zoharwolf.kemono.util.resource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;

public class FileResourceManager extends ResourceManager
{
    private String dirName;
    Map<String, byte[]> allFileMap;
    
    public FileResourceManager(String dirName)
    {
        this.dirName = dirName;
        init();
    }
    

    /**
     * init data
     */
    private void init()
    {
        allFileMap = putResourcesInMap(dirName);
    }
    
    /**
     * Put all resources in Map under the path
     * @param path: directory path or file path.
     * @return 
     */
    private Map<String, byte[]> putResourcesInMap(String path)
    {
        File pathFile = new File(path);
        Map<String, byte[]> fileMap = new Hashtable<String, byte[]>();
        
        if (pathFile.isFile())
        {
            if (!fileMap.containsKey(pathFile.getName().toLowerCase()))
            {
                fileMap.put(pathFile.getName().toLowerCase(), convertFileToByte(pathFile));
            }
        } 
        else
        {
            File[] files = pathFile.listFiles();
            if (files != null && files.length > 0) 
            {
                for (File oneFile: files)
                {
                    if (oneFile.isDirectory())
                    {
                        Map<String, byte[]> dirFiles = putResourcesInMap( path + oneFile.getName() + "/" );
                        fileMap.putAll(dirFiles);
                    } 
                    else
                    {
                        if (!fileMap.containsKey(oneFile.getName().toLowerCase()))
                        {
                            fileMap.put(oneFile.getName().toLowerCase(), convertFileToByte(oneFile));
                        }
                    }
                }
            }
        }
        
        return fileMap;
    }
    
    /**
     * Convert a file to a byte array.
     * @param f: file name.
     * @return byte array after converting.
     */
    private byte[] convertFileToByte(File f)
    {
        int size = (int) f.length();
        byte[] b = new byte[size];
        InputStream is = null;
        try
        {
            is = new FileInputStream(f);
            int offset = 0;
            int readBytes = 0;
            while (size - readBytes > 0)
            {
                readBytes = is.read(b, offset, size - offset);
                if (readBytes == -1) break;
                offset += readBytes;
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
        finally
        {
            try
            {
                if (is != null) is.close();
            } 
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        
        return b;
    }


    /**
     * get inputstream by resource name.
     */
    @Override
    public InputStream getResourceAsStream(String fileName)
    {
        byte[] b = allFileMap.get(fileName.toLowerCase());

        return new ByteArrayInputStream(b);
    }

}
