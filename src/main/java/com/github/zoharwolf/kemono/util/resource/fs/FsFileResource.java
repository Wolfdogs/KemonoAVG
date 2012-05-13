/**
 * Copyright (C) 2012 ZOHAR
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
