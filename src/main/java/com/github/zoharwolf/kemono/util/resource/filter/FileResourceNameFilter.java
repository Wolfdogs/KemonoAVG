package com.github.zoharwolf.kemono.util.resource.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.zoharwolf.kemono.util.resource.FileResource;
import com.github.zoharwolf.kemono.util.resource.DirResource.FileResourceFilter;

/** 筛选文件的类，先写个测试用……
 * 
 * @author zohar
 *
 */
public class FileResourceNameFilter implements FileResourceFilter
{
    String fileRex;
    public FileResourceNameFilter(String fileName)
    {
        fileRex = fileName.replaceAll("\\.", "\\\\.");
        fileRex = fileRex.replaceAll("\\*", ".*");
    }
    
    @Override
    public boolean isAccept(FileResource resource)
    {
        String path = resource.getPath();
        String[] pathArr = path.split("/");
        String name = pathArr[pathArr.length-1];
        return match(name);
    }
    
    private boolean match(String target)
    {
        Pattern pattern = Pattern.compile(fileRex);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
    
}
