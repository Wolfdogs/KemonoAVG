
package com.github.zoharwolf.kemono.util.resource.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.zoharwolf.kemono.util.resource.FileResource;

/**
 * 筛选文件的类，先写个测试用…… 支持通配符*，多个条件筛选时时用;分隔
 * 
 * @author zohar
 * 
 */

public class FileResourceNameFilter implements FileResourceFilter
{
	private List<String> fileRexList;


	public FileResourceNameFilter( String fileName )
	{
		fileRexList = new ArrayList<String>();
		String[] nameArr = fileName.split( ";" );
		for( String oneName : nameArr )
		{
			String fileRex = oneName.replaceAll( "\\.", "\\\\." );
			fileRex = fileRex.replaceAll( "\\*", ".*" );
			fileRexList.add( fileRex );
		}
	}

	@Override
	public boolean isAcceptable( FileResource resource )
	{
		String path = resource.getPath();
		String[] pathArr = path.split( "/" );
		String name = pathArr[pathArr.length - 1];
		return match( name );
	}

	private boolean match( String target )
	{
		for( String fileRex : fileRexList )
		{
			Pattern pattern = Pattern.compile( fileRex );
			Matcher matcher = pattern.matcher( target );
			if( matcher.matches() )
			{
				return true;
			}
		}
		return false;
	}
}
