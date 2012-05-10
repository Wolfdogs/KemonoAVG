
package com.github.zoharwolf.kemono.util.resource;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.zoharwolf.kemono.util.resource.fs.FsDirResource;


public class MultiDirResource extends AbstractDirResource
{
	private List<DirResource> dirResList;


	public MultiDirResource( String resName )
	{
		init( resName );
	}

	private void init( String resName )
	{
		dirResList = new ArrayList<DirResource>();
		String[] resArr = resName.split( ";" );
		for( String oneRes : resArr )
		{
			String[] typeAndRes = oneRes.split( "://" );
			String type = typeAndRes[0];
			String res = typeAndRes[1];
			dirResList.addAll( getAllMatchedResource( type, res ) );
		}
	}

	private Collection<? extends DirResource> getAllMatchedResource( String type, final String path )
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
			switch( type )
			{
			case "fs":
				matchedRes.add( new FsDirResource( oneFile.getPath() ) );
				break;
			// case "zip":
			// matchedRes.add(new ZipResourceManager(oneFile.getPath()));
			// break;
			}
		}

		return matchedRes;
	}

	private boolean match( String condition, String target )
	{
		String rex = condition.replaceAll( "\\.", "\\\\." );
		rex = rex.replaceAll( "\\*", ".*" );
		Pattern pattern = Pattern.compile( rex );
		Matcher matcher = pattern.matcher( target );
		return matcher.matches();
	}

	@Override
	public DirResource getParent()
	{
		dirResList.get( 0 ).getParent();
		return null;
	}

	@Override
	public Resource get( String path )
	{
		Resource r = null;
		for( DirResource dr : dirResList )
		{
			r = dr.get( path );
			if( r != null )
				break;
		}
		return r;
	}

	@Override
	public List<Resource> list()
	{
		List<Resource> resList = new ArrayList<>();
		for( DirResource dr : dirResList )
		{
			resList.addAll( dr.list() );
		}
		return resList;
	}

	@Override
	public String getPath()
	{
		StringBuffer sb = new StringBuffer();
		for( DirResource dr : dirResList )
		{
			sb.append( dr.getPath() );
			sb.append( " ; " );
		}
		return sb.toString();
	}

	@Override
	public String toString()
	{
		return getPath();
	}
}
