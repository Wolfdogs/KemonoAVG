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
	
	Resource get( String path ); // 可无差别地获取DirResouce和FileResource
	FileResource getFile( String path );
	DirResource getDir( String path );

	List<Resource> list(); // 取出目录下全部Resource，不包括子文件夹
	List<Resource> list( boolean subdir );

	List<FileResource> listFiles(); // 取出目录下全部文件。不包括子文件夹
	List<FileResource> listFiles( FileResourceFilter filter );
	List<FileResource> listFiles( FileResourceFilter filter, boolean subdir );
	
	List<DirResource> listDirs();
	List<DirResource> listDirs( DirResourceFilter filter );
	List<DirResource> listDirs( DirResourceFilter filter, boolean subdir );
}
