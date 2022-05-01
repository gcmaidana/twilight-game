package com.onethirdbetter.twilight;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * GraphicsObject keeps track of position and duration of graphics that you see
 * on the screen similar to a basic particle engine
 */

public class GraphicsObject {
  ShapeRenderer shapeRenderer = new ShapeRenderer();//can put this somewhere else but leaving public for now
  public long startTime;
  public long duration;

  private int transparency;

  public long getDuration(){return this.duration; }
  public void setDuration(long duration){this.duration = duration; }
  public long getStartTime() {return this.startTime; }
  public void setStartTime(long startTime) {this.startTime = startTime; }

  public GraphicsObject() {
    this.startTime = System.currentTimeMillis();
  }
  public void draw () { }
  //@Override
  public boolean equals(GraphicsObject g){ return false; }//this doesnt seem done but i thikn i know what i should be using it for (ie dont replace graphics if they are already there? or update them? is that what refresh was for? -ce 2/16/21)
}
