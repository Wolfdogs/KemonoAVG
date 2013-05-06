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

/**
 * 资源父接口。
 * 
 * @author MK124
 */
public interface Resource
{
	/**
	 * 获取资源的路径。
	 * 
	 * @return 路径字符串
	 */
	String getPath();
	
	/**
	 * 获取资源的上级目录。
	 * 
	 * @return 上级目录实例，若已经是最上级则返回 {@code null}
	 */
	DirResource getParent();
}
