package com.github.zoharwolf.kemono.util.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.zoharwolf.kemono.util.resource.filter.DirResourceFilter;
import com.github.zoharwolf.kemono.util.resource.filter.FileResourceFilter;

/**
 * 通用的抽象DirResource父类。
 * 
 * @author mk124
 */

public abstract class AbstractDirResource implements DirResource
{
	private static void addAllToList( Collection<Resource> resources, Collection<Resource> source )
	{
		resources.addAll( source );
		for( Resource resource : source )
		{
			if( resource instanceof DirResource == false ) continue;
			
			DirResource dirResource = (DirResource) resource;
			addAllToList(resources, dirResource.list());
		}
	}
	
	private static Collection<FileResource> listFiles( Collection<Resource> resources, FileResourceFilter filter )
	{
		List<FileResource> files = new ArrayList<>();
		for( Resource resource : resources )
		{
			if( resource instanceof FileResource == false ) continue;
			
			FileResource res = (FileResource) resource;
			if( filter == null || filter.isAccept(res) ) files.add( res );
		}
		
		return files;
	}
	
	private static Collection<DirResource> listDirs( Collection<Resource> resources, DirResourceFilter filter )
	{
		List<DirResource> dirs = new ArrayList<>();
		for( Resource resource : resources )
		{
			if( resource instanceof DirResource == false ) continue;
			
			DirResource res = (DirResource) resource;
			if( filter == null || filter.isAccept(res) ) dirs.add( res );
		}
		
		return dirs;
	}
	
	
	protected AbstractDirResource()
	{
		
	}
	
	@Override
	public Collection<Resource> list( boolean subdir )
	{
		if( subdir == false ) return list();
		
		List<Resource> resources = new ArrayList<>();
		addAllToList( resources, list() );
		
		return resources;
	}
	
	@Override
	public FileResource getFile( String path )
	{
		Resource resource = get(path);
		if( resource instanceof FileResource == false ) return null;
		
		return (FileResource) resource;
	}

	@Override
	public DirResource getDir( String path )
	{
		Resource resource = get(path);
		if( resource instanceof DirResource == false ) return null;
		
		return (DirResource) resource;
	}

	@Override
	public Collection<FileResource> listFiles()
	{
		return listFiles( list(), null );
	}

	@Override
	public Collection<FileResource> listFiles( FileResourceFilter filter )
	{
		return listFiles( list(), filter );
	}

	@Override
	public Collection<FileResource> listFiles( FileResourceFilter filter, boolean subdir )
	{
		return listFiles( list(subdir), filter );
	}

	@Override
	public Collection<DirResource> listDirs()
	{
		return listDirs( list(), null );
	}

	@Override
	public Collection<DirResource> listDirs( DirResourceFilter filter )
	{
		return listDirs( list(), filter );
	}

	@Override
	public Collection<DirResource> listDirs( DirResourceFilter filter, boolean subdir )
	{
		return listDirs( list(subdir), filter );
	}
}
