package com.onethirdbetter.twilight;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.onethirdbetter.twilight.GameScreen.camera;

public class MapTile {
  private String type;
  private Texture tileTexture;
  private boolean passable;
  private int x, y;
  private float hitPoints;

  private SpriteBatch tileBatch;

  public MapTile(int x, int y){
    tileBatch = new SpriteBatch();
    type = "Empty";
    this.x = x;
    this.y = y;
  }

  public void ChangeTile(String type, Texture tileTexture, boolean passable){
    this.type = type;
    if (type.equals("TreeBottom"))
      hitPoints = 100;
    this.tileTexture = tileTexture;
    this.passable = passable;
  }

  public boolean IsEmpty() {
    if (type.equals("Empty"))
      return true;
    return false;
  }

  public boolean IsPassable() {
    return passable;
  }

  public String GetType() {
    return type;
  }

  public float GetHitPoints() {
    return hitPoints;
  }

  public void HitTree(float chopSpeed) {
    hitPoints -= chopSpeed;
  }

  public void draw() {
    tileBatch.setProjectionMatrix(camera.combined);
    tileBatch.begin();
    tileBatch.draw(tileTexture, x, y, 40, 40);
    tileBatch.end();
  }
}
