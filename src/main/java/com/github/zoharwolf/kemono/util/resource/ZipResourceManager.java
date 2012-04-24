package com.github.zoharwolf.kemono.util.resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

public class ZipResourceManager extends ResourceManager
{

    @Override
    public InputStream getResourceAsStream(String path)
    {
        FileInputStream fiStream = null; 
        ZipInputStream ziStream = null;
        try
        {
            fiStream = new FileInputStream(path);
            ziStream = new ZipInputStream(fiStream);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        
        return ziStream;
    }

}
