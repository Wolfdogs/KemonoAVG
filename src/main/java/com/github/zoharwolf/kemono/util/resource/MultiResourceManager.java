package com.github.zoharwolf.kemono.util.resource;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.zoharwolf.kemono.util.resource.IResourceManager;
import com.github.zoharwolf.kemono.util.resource.fs.FileResourceManager;
import com.github.zoharwolf.kemono.util.resource.zip.ZipResourceManager;

/**
 * 存储多个IResourceManager实例，按照初始化顺序从中查找指定的文件
 * @author zohar
 *
 */
public class MultiResourceManager extends ResourceManager
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
            resList.addAll(getAllMatchedResource(type, res));
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
        
        // 从资源中找不到指定的文件时，报错。
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
    
    /**
     * 判断和通配符是否匹配，目前只支持*
     * @param condition 含有自定义通配符的完整路径名
     * @param target 待校验的文件名
     * @return
     */
    private boolean match(String condition, String target)
    {
        String rex = condition.replaceAll("\\.", "\\\\.");
        rex = rex.replaceAll("\\*", ".*");
        Pattern pattern = Pattern.compile(rex);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
    
    /**
     * 获取全部名字符合条件，并且在指定目录下的资源
     * @param type 资源类型
     * @param path 资源名，名中可以含有通配符*
     * @return
     */
    private List<IResourceManager> getAllMatchedResource(String type, final String path)
    {
        List<IResourceManager> matchedRes = new ArrayList<IResourceManager>();
        // 取到文件所在的父目录
        int lastIndex = path.lastIndexOf('/');
        String parent = "";
        if (lastIndex == -1)
        {
            parent = ".";
        } 
        else
        {
            parent = path.substring(0, lastIndex);
        }
        
        // 取出父目录下所有与path匹配的资源
        File parentFile = new File(parent);
        File[] parentFileArr = parentFile.listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File pathname)
            {
                return match(path, pathname.getName());
            }
        });
        
        // 没找到匹配资源时会显示警告
        if (parentFileArr.length <= 0)
        {
            try
            {
                throw new FileNotFoundException("WARNING： No matched resource found in " + path);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        
        for (File oneFile: parentFileArr)
        {
            switch (type)
            {
            case "fs":
                matchedRes.add(new FileResourceManager(oneFile.getPath()));
                break;
            case "zip":
                matchedRes.add(new ZipResourceManager(oneFile.getPath()));
                break;           
            }
        }
        
        return matchedRes;
    }


}
