package com.github.zoharwolf.kemono.util.resource;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDirResource implements DirResource
{
	private static final DirResourceFilter NULL_DIR_RESOURCE_FILTER = new DirResourceFilter()
	{
		@Override
		public boolean isAccept( DirResource resource )
		{
			return true;
		}
	};
	
	private static final FileResourceFilter NULL_FILE_RESOURCE_FILTER = new FileResourceFilter()
	{
		@Override
		public boolean isAccept(FileResource resource)
		{
			return true;
		}
	};
	
	private static void addAllToList( List<Resource> resources, List<Resource> source )
	{
		resources.addAll( source );
		for( Resource resource : source )
		{
			if( resource instanceof DirResource == false ) continue;
			
			DirResource dirResource = (DirResource) resource;
			addAllToList(resources, dirResource.list());
		}
	}
	
	
	protected AbstractDirResource()
	{
		
	}
	
	@Override
	public List<Resource> list( boolean subdir )
	{
		if( subdir == false ) return list();
		
		List<Resource> resources = new ArrayList<>();
		addAllToList( resources, list() );
		
		return null;
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
	public List<FileResource> listFiles()
	{
		List<FileResource> files = new ArrayList<>();
		List<Resource> resources = list();

		for( Resource resource : resources )
		{
			if( resource instanceof FileResource == false ) continue;
			files.add( (FileResource) resource );
		}
		
		return files;
	}

	@Override
	public List<FileResource> listFiles( FileResourceFilter filter )
	{
		if( filter == null ) filter = NULL_FILE_RESOURCE_FILTER;
		
		List<FileResource> files = new ArrayList<>();
		List<Resource> resources = list();

		for( Resource resource : resources )
		{
			if( resource instanceof FileResource == false ) continue;
				
			FileResource res = (FileResource) resource;
			if( filter.isAccept(res) ) files.add( res );
		}
		
		return files;
	}

	@Override
	public List<FileResource> listFiles( FileResourceFilter filter, boolean subdir )
	{
		if( filter == null ) filter = NULL_FILE_RESOURCE_FILTER;
		
		List<FileResource> files = new ArrayList<>();
		List<Resource> resources = list(subdir);

		for( Resource resource : resources )
		{
			if( resource instanceof FileResource == false ) continue;
			
			FileResource res = (FileResource) resource;
			if( filter.isAccept(res) ) files.add( res );
		}
		
		return files;
	}

	@Override
	public List<DirResource> listDirs()
	{
		List<DirResource> dirs = new ArrayList<>();
		List<Resource> resources = list();

		for( Resource resource : resources )
		{
			if( resource instanceof DirResource == false ) continue;
			dirs.add( (DirResource) resource );
		}
		
		return dirs;
	}

	@Override
	public List<DirResource> listDirs( DirResourceFilter filter )
	{
		if( filter == null ) filter = NULL_DIR_RESOURCE_FILTER;
		
		List<DirResource> dirs = new ArrayList<>();
		List<Resource> resources = list();

		for( Resource resource : resources )
		{
			if( resource instanceof DirResource == false ) continue;
			
			DirResource res = (DirResource) resource;
			if( filter.isAccept(res) ) dirs.add( res );
		}
		
		return dirs;
	}

	@Override
	public List<DirResource> listDirs( DirResourceFilter filter, boolean subdir )
	{
		if( filter == null ) filter = NULL_DIR_RESOURCE_FILTER;
		
		List<DirResource> dirs = new ArrayList<>();
		List<Resource> resources = list(subdir);

		for( Resource resource : resources )
		{
			if( resource instanceof DirResource == false ) continue;
			
			DirResource res = (DirResource) resource;
			if( filter.isAccept(res) ) dirs.add( res );
		}
		
		return dirs;
	}
}
