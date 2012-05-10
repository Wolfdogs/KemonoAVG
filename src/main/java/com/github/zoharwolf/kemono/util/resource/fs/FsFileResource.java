
package com.github.zoharwolf.kemono.util.resource.fs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.github.zoharwolf.kemono.util.resource.DirResource;
import com.github.zoharwolf.kemono.util.resource.FileResource;

/**
 * 从文件系统中获取文件资源类
 * 
 * @author zohar
 * 
 */

public class FsFileResource implements FileResource
{
	private String path;


	public FsFileResource( String path )
	{
		this.path = path;
	}

	@Override
	public String getPath()
	{
		return path;
	}

	@Override
	public DirResource getParent()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSize()
	{
		File file = new File( path );
		return (int) file.length();
	}

	@Override
	public InputStream getInputStream()
	{
		File file = new File( path );
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream( file );
		}
		catch( FileNotFoundException e )
		{
			e.printStackTrace();
		}

		return fis;
	}

	@Override
	public String toString()
	{
		return path;
	}
}
