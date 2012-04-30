package com.github.zoharwolf.kemono.util.resource;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 存储多个IResourceManager实例，按照初始化顺序从中查找指定的文件
 * @author zohar
 *
 */
public class MultiResourceManager implements IResourceManager
{
    List<IResourceManager> resList;

    public MultiResourceManager(String resName)
    {
        init(resName);
    }
    
    /**
     * 初始化
     * @param resName 资源文件名，格式应该是"<资源类型1>://<资源名1>;<资源类型2>://<资源名2>;..."
     * 目前支持的资源类型: fs  -  一般的文件夹或文件
     *               zip -  封装在zip文件之中的资源
     */
    private void init(String resName)
    {
        resList = new ArrayList<IResourceManager>();
        String[] resArr = resName.split(";");
        for (String oneRes: resArr)
        {
            String[] typeAndRes = oneRes.split("://");
            String type = typeAndRes[0];
            String res = typeAndRes[1];
            switch (type)
            {
            case "fs":
                resList.add(new FileResourceManager(res));
                break;
            case "zip":
                resList.add(new ZipResourceManager(res));
                break;
            }
        }
    }

    /**
     * 按照初始化时的资源加入顺序搜索指定文件。
     */
    @Override
    public InputStream getResourceAsStream(String path) throws FileNotFoundException
    {
        InputStream is = null;
        Iterator<IResourceManager> it = resList.iterator();
        while(it.hasNext())
        {
            is = it.next().getResourceAsStream(path);
            if (is != null) break;
        }
        
        if (is == null)
        {
            throw new FileNotFoundException("ERROR: Cannot find \"" + path +
                        "\" in resources.");
        }
        else
        {
            return is;
        }
    }

}
