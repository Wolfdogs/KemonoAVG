package com.github.zoharwolf.kemono.util.resource;

import java.util.Collection;

import com.github.zoharwolf.kemono.util.resource.filter.DirResourceFilter;
import com.github.zoharwolf.kemono.util.resource.filter.FileResourceFilter;

public interface DirResource extends Resource
{	
	Resource get( String path ); // 可无差别地获取DirResouce和FileResource
	FileResource getFile( String path );
	DirResource getDir( String path );

	Collection<Resource> list(); // 取出目录下全部Resource，不包括子文件夹
	Collection<Resource> list( boolean subdir );

	Collection<FileResource> listFiles(); // 取出目录下全部文件。不包括子文件夹
	Collection<FileResource> listFiles( FileResourceFilter filter );
	Collection<FileResource> listFiles( FileResourceFilter filter, boolean subdir );
	
	Collection<DirResource> listDirs();
	Collection<DirResource> listDirs( DirResourceFilter filter );
	Collection<DirResource> listDirs( DirResourceFilter filter, boolean subdir );
}
