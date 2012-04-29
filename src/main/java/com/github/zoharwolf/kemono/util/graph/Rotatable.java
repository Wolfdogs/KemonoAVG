package com.github.zoharwolf.kemono.util.graph;

/**
 * 可进行旋转操作的接口。<p>
 * 由带有旋转数据并支持旋转操作的类所实现。
 */

public interface Rotatable
{
	/**
	 * 获取对象的x轴旋转角度数据。
	 * 
	 * @return
	 *  x轴旋转角度数据
	 */
	float getRotateX();

	/**
	 * 设定对象的x轴旋转角度数据。
	 * 
	 * @param rotateX
	 *  x轴旋转角度数据
	 */
	void setRotateX( float rotateX  );

	/**
	 * 获取对象的y轴旋转角度数据。
	 * 
	 * @return
	 *  y轴旋转角度数据
	 */
	float getRotateY();
	
	/**
	 * 设定对象的y轴旋转角度数据。
	 * 
	 * @param rotateY
	 *  y轴旋转角度数据
	 */
	void setRotateY( float rotateY );

	/**
	 * 获取对象的z轴旋转角度数据。
	 * 
	 * @return
	 *  z轴旋转角度数据
	 */
	float getRotateZ();
	
	/**
	 * 设定对象的z轴旋转角度数据。
	 * 
	 * @param rotateZ
	 *  z轴旋转角度数据
	 */
	void setRotateZ( float rotateZ );
}
