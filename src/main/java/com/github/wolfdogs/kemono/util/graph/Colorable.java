package com.github.wolfdogs.kemono.util.graph;

public interface Colorable
{
	float getR();
	void setR(float r);
	
	float getG();
	void setG(float g);
	
	float getB();
	void setB(float b);
	
	float getA();
	void setA(float a);
	
	void setColor(float r, float g, float b);
	void setColor(float r, float g, float b, float a);
}
