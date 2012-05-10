/**
 * Copyright (C) 2012 MK124
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.zoharwolf.kemono.util.resource;

import java.util.Collection;

import com.github.zoharwolf.kemono.util.resource.filter.DirResourceFilter;
import com.github.zoharwolf.kemono.util.resource.filter.FileResourceFilter;

/**
 * 目录资源接口。声明了获取及列举子级资源的方法。
 * 
 * @author MK124
 */

public interface DirResource extends Resource
{
	/**
	 * 根据路径获取子级资源实例。可以获取目录或文件资源，支持多级路径。
	 * 
	 * @param path 路径
	 * @return 对应的资源实例，若不存在则返回 {@code null}
	 */
	Resource get( String path );

	/**
	 * 根据路径获取子级文件资源实例。支持多级路径。
	 * 
	 * @param path 路径
	 * @return 对应的文件资源实例，若不存在则返回 {@code null}
	 */
	FileResource getFile( String path );

	/**
	 * 根据路径获取子级目录资源实例。支持多级路径。
	 * 
	 * @param path 路径
	 * @return 对应的目录资源实例，若不存在则返回 {@code null}
	 */
	DirResource getDir( String path );

	/**
	 * 列举目录下的所有资源。
	 * 
	 * @return 资源的集合
	 */
	Collection<Resource> list();
	
	/**
	 * 列举目录下的所有资源，支持列举所有子目录资源。
	 * 
	 * @param subdir 是否递归列举子目录资源
	 * @return 资源的集合
	 */
	Collection<Resource> list( boolean subdir );

	/**
	 * 列举目录下的所有文件资源。
	 * 
	 * @return 资源的集合
	 */
	Collection<FileResource> listFiles();

	/**
	 * 列举目录下的文件资源，使用过滤器来挑选。
	 * 
	 * @param filter 挑选资源的过滤器，为 {@code null} 的时候列举所有资源
	 * @return 资源的集合
	 */
	Collection<FileResource> listFiles( FileResourceFilter filter );
	
	/**
	 * 列举目录以及所有子目录下的文件资源，使用过滤器来挑选。
	 * 
	 * @param filter 挑选资源的过滤器，为 {@code null} 的时候列举所有资源
	 * @param subdir 是否递归列举子目录资源
	 * @return 资源的集合
	 */
	Collection<FileResource> listFiles( FileResourceFilter filter, boolean subdir );

	/**
	 * 列举目录下的所有子目录资源。
	 * 
	 * @return 资源的集合
	 */
	Collection<DirResource> listDirs();

	/**
	 * 列举目录下的所有子目录资源，使用过滤器来挑选。
	 * 
	 * @param filter 挑选资源的过滤器，为 {@code null} 的时候列举所有资源
	 * @return 资源的集合
	 */
	Collection<DirResource> listDirs( DirResourceFilter filter );

	/**
	 * 列举目录下的所有子目录资源，使用过滤器来挑选。
	 * 
	 * @param filter 挑选资源的过滤器，为 {@code null} 的时候列举所有资源
	 * @param subdir 是否递归列举子目录资源
	 * @return 资源的集合
	 */
	Collection<DirResource> listDirs( DirResourceFilter filter, boolean subdir );
}
