package com.onethirdbetter.twilight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * A list of Graphics Objects that are held in a Queue which is able to perform actions on the entire list
 * but the main task will be too track what is currently in the queue and delete those objects that have run past their
 * duration time. Note: duration time is a variable contained in each object, while this class does not track
 * times on its own, it is responsible for checking if said time has been passed.
 */
public class GraphicsQueue {
  private int numGraphics = 0;
  private int augmentSize = 10;
  private GraphicsObject graphicsArray[] = new GraphicsObject[256];

  public GraphicsQueue() { }

  public GraphicsQueue(int size, int augmentSize) {
    graphicsArray = new GraphicsObject[size];
    this.augmentSize = augmentSize;
  }

  int getNumGraphics(){return numGraphics; }

  /**
  * Adds a graphics Object to the queue;
  * if a duplicate object is found and the child object properly employs .equals, the object will not be added
  */
  void add(GraphicsObject g){
    int index = -1;
    for(int i = 0; i < numGraphics; i ++){
      if(graphicsArray[i].equals(g)){
        index = i;
      }
    }
    if(index == -1){
      g.setStartTime(System.currentTimeMillis());
      graphicsArray[numGraphics] = g;
      numGraphics ++;
    } else {
      refresh(index);
    }
  }

  /**
   * Takes and index and removes the object in said index place
   */
  public void remove(int index){
    numGraphics --;
    graphicsArray[index] = graphicsArray[numGraphics];
  }

  /**
   * Resets the start time of each object meaning that they will run again for their entire duration
   * not sure why I thought this was needed
   */
  public void refresh(int index){
    graphicsArray[index].setStartTime(System.currentTimeMillis());
  }

  /**
   * Adds a graphics Object to the queue;
   * if a duplicate object is found and the child object properly employs .equals, the object will not be added
   */
  public void flushWithoutDrawing() {
    for(int i = 0; i < numGraphics; i ++) {
      if (graphicsArray[i].getDuration() < System.currentTimeMillis() - graphicsArray[i].getStartTime()) {
        remove(i);
      }
    }
  }

  /**
   * Runs through the list of graphics objects and employs the draw method for each of them
   */
  public void draw(){
    for(int i = 0; i < numGraphics; i ++){
      if(graphicsArray[i].getDuration() < System.currentTimeMillis() - graphicsArray[i].getStartTime()){
        remove(i);
      }
    }
    for(int i = 0; i < numGraphics; i ++){//cant group these two - may miss graphics while they are being removed
      graphicsArray[i].draw();
    }
  }
}