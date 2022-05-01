package com.onethirdbetter.twilight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


/**
 * This is a test class, it is only temporarily located here and if needed later will be moved to another location
 */
public class GunShot extends GraphicsObject {
  private float x1, y1, x2, y2;



  private Point origin;
  private Point destin;
  //private long duration;
  //private long startTime;

  //constructors
  public GunShot(int x1, int y1, int x2, int y2) {
    origin = new Point(x1, y1);
    destin = new Point(x2, y2);
    duration = 500;
  }

  public GunShot(Point p1, Point p2){
    this.x1 = p1.getX(); //could just make it a point to begin with even
    this.y1 = p1.getY();
    this.x2 = p2.getX();
    this.y2 = p2.getY();
    duration = 500;
  }

  /**
   * as a test, will draw lines on the screen at given coordinates
   */
  public void draw(){
    //stroke(245, 197, 66, 255 - ( (System.currentTimeMillis() - startTime) / (duration / 255)));
    Gdx.gl.glLineWidth(20);
    OrthographicCamera camera = new OrthographicCamera();
    camera.update();
    shapeRenderer.setProjectionMatrix(camera.combined);
    //shapeRenderer.setProjectionMatrix(camera.combined);
    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    shapeRenderer.setColor(245/255f, 197/255f, 66/255f, (duration - ((System.currentTimeMillis()) - startTime)) / 255f);//
    //System.out.println("transparency??: " + (((System.currentTimeMillis() - startTime) / (duration / 255))) / 255f);
    shapeRenderer.setColor(245/255f, 197/255f, 66/255f, 0);
    //System.out.println("transparency??: " + (duration - ((System.currentTimeMillis()) - startTime)) / 255f);
    //shapeRenderer.setColor(Color.GREEN);
    //shapeRenderer.setColor(1, 0, 0, 1); // Red line
    shapeRenderer.rect(origin.getX(), origin.getY(), destin.getX(), destin.getY());
    shapeRenderer.rectLine((int)origin.getX(),(int)origin.getY(),(int)destin.getX(),(int)destin.getY(), 10);

    shapeRenderer.end();
  }
  private void drawLine(){
    //this may be used later
  }
}