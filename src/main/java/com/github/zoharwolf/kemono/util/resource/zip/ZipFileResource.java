package com.github.zoharwolf.kemono.util.resource.zip;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipException;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

import com.github.zoharwolf.kemono.util.resource.DirResource;
import com.github.zoharwolf.kemono.util.resource.FileResource;

/**
 * 读取源为Zip文件包的FileResource实现。
 * 
 * @author mk124
 */

public class ZipFileResource implements FileResource
{
	private DirResource parent;
	private ZipFile zipFile;
	private ZipArchiveEntry entry;
	

	public ZipFileResource( DirResource parent, ZipFile zipFile, ZipArchiveEntry entry )
	{
		this.parent = parent;
		this.zipFile = zipFile;
		this.entry = entry;
	}

	@Override
	public String getPath()
	{
		return entry.getName();
	}

	@Override
	public DirResource getParent()
	{
		return parent;
	}

	@Override
	public int getSize()
	{
		return (int) entry.getSize();
	}

	@Override
	public InputStream getInputStream()
	{
		try
		{
			return zipFile.getInputStream(entry);
		}
		catch( ZipException e )
		{
			e.printStackTrace();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
		
		return null;
	}
}
