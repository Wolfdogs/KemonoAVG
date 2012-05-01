package com.github.zoharwolf.kemono.util.resource;

import java.util.List;

public interface DirResource extends Resource
{
	public interface FileResourceFilter
	{
		boolean isAccept( FileResource resource );
	}

	public interface DirResourceFilter
	{
		boolean isAccept( DirResource resource );
	}

	DirResource getParent();
	
	Resource get( String path );
	FileResource getFile( String path );
	DirResource getDir( String path );
	
	List<Resource> list();

	List<FileResource> listFiles();
	List<FileResource> listFiles( FileResourceFilter filter );
	List<FileResource> listFiles( FileResourceFilter filter, boolean subdir );
	
	List<DirResource> listDirs();
	List<DirResource> listDirs( DirResourceFilter filter );
	List<DirResource> listDirs( DirResourceFilter filter, boolean subdir );
}
