package com.github.zoharwolf.kemono.util.graph;

/**
 * 可获取坐标数据的接口。<p>
 * 由带有坐标数据并支持平移的类所实现。
 */

public interface Positionable
{
	/**
	 * 获取对象的x轴坐标数据。
	 * 
	 * @return
	 *  x轴坐标数据
	 */
	float getX();

	/**
	 * 设定对象的x轴坐标数据。
	 * 
	 * @return
	 *  x轴坐标数据。
	 */
	void setX( float x );

	/**
	 * 获取对象的y轴坐标数据。
	 * 
	 * @return
	 *  y轴坐标数据
	 */
	float getY();

	/**
	 * 设定对象的y轴坐标数据。
	 * 
	 * @return
	 *  y轴坐标数据
	 */
	void setY( float y );

	/**
	 * 获取对象的z轴坐标数据。
	 * 
	 * @return
	 *  z轴坐标数据
	 */
	float getZ();

	/**
	 * 设定对象的z轴坐标数据。
	 * 
	 * @return
	 *  z轴坐标数据
	 */
	void setZ( float z );
}
