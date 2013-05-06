/**
 * Copyright (C) 2012 ZOHAR
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

package com.github.wolfdogs.kemono.util.resource;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.wolfdogs.kemono.util.collection.FilteredMap;

/**
 * 工厂类，制造DirResource的实例。 需要使用addType方法添加可用资源类型。
 * 
 * @author zohar
 */
public final class ResourceManager
{
	private static final Map<String, Class<? extends DirResource>> RES_TYPES = FilteredMap.lowercasedKeyMap(new HashMap<String, Class<? extends DirResource>>());
	
	
	/**
	 * 增加支持的资源类型。
	 * 
	 * @param name 资源类型标识
	 * @param cls 资源实现类型
	 */
	public static void addType(String name, Class<? extends DirResource> cls)
	{
		RES_TYPES.put(name, cls);
	}
	
	/**
	 * 获取资源的方法。
	 * 
	 * @param resName 这样的格式： 资源类型1缩写://资源1文件(夹);资源类型1缩写://资源1文件(夹);... 后面的资源优先级高于前边的。支持通配符*
	 * @return
	 */
	public static DirResource getResource(String resName)
	{
		try
		{
			return new MultiDirResource(getDirs(resName));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	private static List<DirResource> getDirs(String resLocator) throws FileNotFoundException
	{
		List<DirResource> dirs = new ArrayList<>();
		String[] resources = resLocator.split(";");
		
		for (int i = resources.length - 1; i >= 0; i--)
		{
			String oneRes = resources[i];
			String[] typeAndRes = oneRes.split("://");
			String type = typeAndRes[0];
			String res = typeAndRes[1];
			dirs.addAll(getAllMatchedResources(type, res));
		}
		
		return dirs;
	}
	
	private static Collection<? extends DirResource> getAllMatchedResources(String type, final String path) throws FileNotFoundException
	{
		List<DirResource> matches = new ArrayList<>();
		
		// 取到文件所在的父目录
		int lastIndex = path.lastIndexOf('/');
		String parent = ".";
		
		if (lastIndex != -1) parent = path.substring(0, lastIndex);
		
		// 取出父目录下所有与path匹配的资源
		File[] files = new File(parent).listFiles(
				new FileFilter()
				{
					@Override
					public boolean accept(File pathname)
					{
						return match(path, pathname.getName());
					}
				});
		
		// 没找到匹配资源时抛出异常
		if (files.length == 0) throw new FileNotFoundException("No matched resource found in " + path);
		
		for (File file : files)
		{
			if (!RES_TYPES.containsKey(type)) throw new UnsupportedOperationException("This type is not valid: " + type);
			
			try
			{
				Class<? extends DirResource> cls = RES_TYPES.get(type);
				DirResource dirRes = cls.getConstructor(File.class).newInstance(file);
				matches.add(dirRes);
			}
			catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e)
			{
				e.printStackTrace();
			}
		}
		
		return matches;
	}
	
	private static boolean match(String condition, String target)
	{
		String rex = condition.replaceAll("\\.", "\\\\.");
		rex = rex.replaceAll("\\*", ".*");
		Pattern pattern = Pattern.compile(rex);
		Matcher matcher = pattern.matcher(target);
		return matcher.matches();
	}
}
