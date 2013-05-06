package com.github.wolfdogs.kemono.util.graph;

/**
 * 可进行缩放操作的接口，由带有缩放数据并支持缩放操作的类所实现。
 */
public interface Scaleable
{
	/**
	 * 获取对象的水平缩放比例。
	 * 
	 * @return 水平缩放比例
	 */
	float getScaleX();
	
	/**
	 * 设定对象的水平缩放比例。
	 * 
	 * @param scaleX 水平缩放比例
	 */
	void setScaleX(float scaleX);
	
	/**
	 * 获取对象的垂直缩放比例。
	 * 
	 * @return 垂直缩放比例
	 */
	float getScaleY();
	
	/**
	 * 设定对象的垂直缩放比例。
	 * 
	 * @param scaleY 垂直缩放比例
	 */
	void setScaleY(float scaleY);
}
