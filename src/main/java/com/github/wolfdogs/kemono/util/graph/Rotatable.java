package com.github.wolfdogs.kemono.util.graph;

/**
 * 可进行旋转操作的接口，由带有旋转数据并支持旋转操作的类所实现。
 */
public interface Rotatable
{
	/**
	 * 获取对象的x轴旋转角度数据。
	 * 
	 * @return x轴旋转角度数据
	 */
	float getRotationX();
	
	/**
	 * 设定对象的x轴旋转角度数据。
	 * 
	 * @param rotationX x轴旋转角度数据
	 */
	void setRotationX(float rotationX);
	
	/**
	 * 获取对象的y轴旋转角度数据。
	 * 
	 * @return y轴旋转角度数据
	 */
	float getRotationY();
	
	/**
	 * 设定对象的y轴旋转角度数据。
	 * 
	 * @param rotationY y轴旋转角度数据
	 */
	void setRotationY(float rotationY);
	
	/**
	 * 获取对象的z轴旋转角度数据。
	 * 
	 * @return z轴旋转角度数据
	 */
	float getRotationZ();
	
	/**
	 * 设定对象的z轴旋转角度数据。
	 * 
	 * @param rotationZ z轴旋转角度数据
	 */
	void setRotationZ(float rotationZ);
}
