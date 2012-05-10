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

package com.github.zoharwolf.kemono.util.resource.filter;

import com.github.zoharwolf.kemono.util.resource.FileResource;

/**
 * 文件资源过滤器接口。
 * 
 * @author MK124
 */

public interface FileResourceFilter
{
	/**
	 * 判定对应的文件资源是否被接受。
	 * 
	 * @param resource 文件资源实例
	 * @return 若接受则返回 {@code true}
	 */
	boolean isAcceptable( FileResource resource );
}
