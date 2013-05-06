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

package com.github.wolfdogs.kemono.util.resource;

import java.io.InputStream;

/**
 * 文件资源接口。声明了获取文件信息和数据的方法。
 * 
 * @author MK124
 */
public interface FileResource extends Resource
{
	/**
	 * 获取文件资源的大小。
	 * 
	 * @return 大小的值，单位为字节
	 */
	int getSize();
	
	/**
	 * 获取文件资源的输入流。
	 * 
	 * @return 文件的输入流实例
	 */
	InputStream getInputStream();
}
