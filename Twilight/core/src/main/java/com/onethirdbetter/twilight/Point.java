package com.onethirdbetter.twilight;
/**
 * Point tracks an x and y value used for location data also known as a vector but with specific functionality
 */
public class Point {
  private float xPos;
  private float yPos;

  //constructors
  public Point(float xPos, float yPos) {
    this.xPos = xPos;
    this.yPos = yPos;
  }
  public Point(int xPos, int yPos) {
    this.xPos = xPos;
    this.yPos = yPos;
  }

  public Point(Point p) {
    this.xPos = p.getX();
    this.yPos = p.getY();
  }

  public float getX() {
    return xPos;
  }

  public float getY() {
    return yPos;
  }

  public void setX(float xPos) {
    this.xPos = xPos;
  }

  public void setY(float yPos) {
    this.yPos = yPos;
  }

  //accepts an x and y position to set a new position
  public void setPos(float xPos, float yPos) {
    this.xPos = xPos;
    this.yPos = yPos;
  }

  //moves the point to a given location
  public void translate(float dX, float dY) {
    xPos += dX;
    yPos += dY;
  }

  //calculates the distance between this point and a given point
  public float getDistance(Point p) {
    float dx = this.xPos - p.getX();
    float dy = this.yPos - p.getY();
    return (float) Math.sqrt(dx * dx + dy * dy); //using internal math lib
  }
}