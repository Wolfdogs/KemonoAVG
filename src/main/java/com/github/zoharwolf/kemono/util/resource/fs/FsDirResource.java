
package com.github.zoharwolf.kemono.util.resource.fs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.zoharwolf.kemono.util.resource.AbstractDirResource;
import com.github.zoharwolf.kemono.util.resource.DirResource;
import com.github.zoharwolf.kemono.util.resource.Resource;

/**
 * 从文件系统中获取文件夹资源类
 * 
 * @author zohar
 * 
 */

public class FsDirResource extends AbstractDirResource
{
	private String dirPath;
	private Map<String, Resource> existResMap;


	public FsDirResource( String path )
	{
		this.dirPath = path;
	}

	@Override
	public Resource get( String path )
	{
		String[] childs = StringUtils.split( path, "/\\", 2 );
		if( childs.length == 0 )
			return this;

		Resource res = getChild( childs[0] );

		if( childs.length == 1 )
			return res;
		if( childs.length == 2 && res instanceof DirResource )
		{
			DirResource dirRes = (DirResource) res;
			return dirRes.get( childs[1] );
		}

		return null;
	}

	private FsFileResource generateFileRes( String path )
	{
		if( existResMap == null )
			existResMap = new HashMap<String, Resource>();

		if( existResMap.containsKey( path.toLowerCase() ) )
		{
			return (FsFileResource) existResMap.get( path.toLowerCase() );
		}
		else
		{
			FsFileResource ffr = new FsFileResource( path );
			existResMap.put( path.toLowerCase(), ffr );
			return ffr;
		}
	}

	private FsDirResource generateDirRes( String path )
	{
		if( existResMap == null )
			existResMap = new HashMap<String, Resource>();

		if( existResMap.containsKey( path.toLowerCase() ) )
		{
			return (FsDirResource) existResMap.get( path.toLowerCase() );
		}
		else
		{
			FsDirResource fdr = new FsDirResource( path );
			existResMap.put( path.toLowerCase(), fdr );
			return fdr;
		}
	}

	@Override
	public String getPath()
	{
		return dirPath;
	}

	@Override
	public DirResource getParent()
	{
		File dirFile = new File( dirPath );
		String parent = dirFile.getParent();
		if( parent == null )
		{
			parent = ".";
		}
		return generateDirRes( parent );
	}

	private Resource getChild( String path )
	{
		String childPath = dirPath + "/" + path;
		File f = new File( childPath );
		if( f.isFile() )
		{
			return generateFileRes( childPath );
		}
		else
		{
			return generateDirRes( childPath );
		}
	}

	@Override
	public List<Resource> list()
	{
		File dirFile = new File( dirPath );
		File[] fileArr = dirFile.listFiles();

		List<Resource> resList = new ArrayList<Resource>();
		for( File oneFile : fileArr )
		{
			if( oneFile.isFile() )
			{
				resList.add( generateFileRes( oneFile.getPath() ) );
			}
			else
			{
				resList.add( generateDirRes( oneFile.getPath() ) );
			}
		}
		return resList;
	}

	@Override
	public String toString()
	{
		return dirPath;
	}

}
