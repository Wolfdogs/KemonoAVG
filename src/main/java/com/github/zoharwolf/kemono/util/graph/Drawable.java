package com.github.zoharwolf.kemono.util.graph;

import org.lwjgl.opengl.GL11;

/**
 * 可使用OpenGL进行绘画的接口
 */

public interface Drawable
{
	/**
	 * 使用传入的OpenGL实例绘画当前对象。<p>
	 * 
	 * @param gl
	 *  OpenGL对象实例
	 */
	void draw( GL11 gl );
}
