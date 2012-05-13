
package com.github.zoharwolf.kemono.util.resource;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工厂类，制造IResourceManager的实例。
 * 
 * @author zohar
 * 
 */

public abstract class ResourceManager
{
    
    private static Map<String, String> validResType; 
    
	public static DirResource getResource( String resName )
	{
		return new MultiDirResource( getDirList( resName ) );
	}
	
	// 增加一个资源类型
	public static void addRes(String name, String clsName)
	{
	    if (validResType == null)
	    {
	        validResType = new HashMap<String, String>();
	    }
	    
	    if ( !validResType.containsKey(name) )
	    {
	        validResType.put(name, clsName);
	    }
	}
	
	private static List<DirResource> getDirList( String resName )
	{
	    List<DirResource> dirResList = new ArrayList<DirResource>();
        String[] resArr = resName.split(";");
        for ( int i=resArr.length-1; i>=0; i--)
        {
            String oneRes = resArr[i];
            String[] typeAndRes = oneRes.split("://");
            String type = typeAndRes[0];
            String res = typeAndRes[1];
            dirResList.addAll(getAllMatchedResource(type, res));
        }
        
        return dirResList;
	}
	
    private static Collection<? extends DirResource> getAllMatchedResource( String type, final String path )
    {
        List<DirResource> matchedRes = new ArrayList<DirResource>();
        // 取到文件所在的父目录
        int lastIndex = path.lastIndexOf( '/' );
        String parent = "";
        if( lastIndex == -1 )
        {
            parent = ".";
        }
        else
        {
            parent = path.substring( 0, lastIndex );
        }

        // 取出父目录下所有与path匹配的资源
        File parentFile = new File( parent );
        File[] parentFileArr = parentFile.listFiles( new FileFilter()
        {
            @Override
            public boolean accept( File pathname )
            {
                return match( path, pathname.getName() );
            }
        } );

        // 没找到匹配资源时会显示警告
        if( parentFileArr.length <= 0 )
        {
            try
            {
                throw new FileNotFoundException( "WARNING： No matched resource found in " + path );
            }
            catch( FileNotFoundException e )
            {
                e.printStackTrace();
            }
        }

        for( File oneFile : parentFileArr )
        {
            if ( validResType.containsKey(type))
            {
                String clsName = validResType.get(type);
                try
                {
                    Class<?> cls = Class.forName(clsName);
                    DirResource dirRes = (DirResource) cls.getConstructor(File.class).newInstance(oneFile);
                    matchedRes.add(dirRes);
                }
                catch (ClassNotFoundException | InstantiationException | IllegalAccessException 
                        | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e)
                {
                    e.printStackTrace();
                }
                
            }
            else
            {
                try
                {
                    throw new Exception( "WARNING： This type is not valid: " + type );
                }
                catch( Exception e )
                {
                    e.printStackTrace();
                }
            }
        }

        return matchedRes;
    }
    
    private static boolean match( String condition, String target )
    {
        String rex = condition.replaceAll( "\\.", "\\\\." );
        rex = rex.replaceAll( "\\*", ".*" );
        Pattern pattern = Pattern.compile( rex );
        Matcher matcher = pattern.matcher( target );
        return matcher.matches();
    }
}
