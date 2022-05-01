package com.onethirdbetter.twilight;

/*
 * Entity keeps track of entities such as enemies that will be spawned on the map.
 * It holds their position, their point, their health, and other information about
 * entity.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.onethirdbetter.twilight.GameScreen.camera;
import static com.onethirdbetter.twilight.GameScreen.tileList;


public class Entity {
  String name;
  private Texture entityTexture;
  private SpriteBatch entityBatch;
  float maxHealth = 150;
  float currentHealth = 150;
  float xPos = 15;
  float yPos = 15;
  Point location;
  float width = 35;
  float height = 35;
  float attackStrength = 25;

  float hitBoxSize = 20;

  float speed = (float) 20;
  private Texture goblinSword;
  private Animation goblinAnimation;
  private TextureRegion[] animationFrames;
  float elapsedTime;

  boolean HBDraw = true;
  boolean doAnimations = true;

  void setSpeed(float speed){
    this.speed = speed;
  }
  void setHBDraw(boolean b){
    this.HBDraw = b;
  }

  void setName(String name){
    this.name = name;

  }

  public void setAnimations(boolean b){
    this.doAnimations = b;
  }

  public void setTexture(Texture tex){
    this.entityTexture = tex;
  }


  public Entity() {
    entityBatch = new SpriteBatch();
    location = new Point(100,100);
    entityTexture = new Texture(Gdx.files.internal("goblin.png"));
    goblinSword = new Texture(Gdx.files.internal("goblinstransparent.png"));
    animationFrames = new TextureRegion[8];
    int index = 0;
    TextureRegion[][] tempFrames = TextureRegion.split(goblinSword, 65, 58);
    for(int i = 0; i < 8; i++)
      animationFrames[index++] = tempFrames[0][i];
    goblinAnimation = new Animation(1/10f, animationFrames);
  }

  public Entity(int x, int y) {
    entityBatch = new SpriteBatch();
    location = new Point(x, y);
    entityTexture = new Texture(Gdx.files.internal("goblin.png"));
    goblinSword = new Texture(Gdx.files.internal("goblinstransparent.png"));
    animationFrames = new TextureRegion[8];
    int index = 0;
    TextureRegion[][] tempFrames = TextureRegion.split(goblinSword, 64, 58);
    for(int i = 0; i < 8; i++)
      animationFrames[index++] = tempFrames[0][i];
    goblinAnimation = new Animation(1/10f, animationFrames);
  }

  ShapeRenderer shapeRenderer = new ShapeRenderer();//can put this somewhere else

  public void draw() {
    elapsedTime += Gdx.graphics.getDeltaTime();
    if(currentHealth > 0 && HBDraw){
      //if(currentHealth != maxHealth){ //this should not be removed, only commented out for visual testing
        drawHealthBar();
      //}
      //here should be replaced with the ability to add a sprite to the spritebatch for drawing

    }
    if(doAnimations) {
      entityBatch.setProjectionMatrix(camera.combined);
      entityBatch.begin();
      entityBatch.draw((TextureRegion) goblinAnimation.getKeyFrame(elapsedTime, true), location.getX(), location.getY(), width, height);
      entityBatch.end();
    } else {
      entityBatch.setProjectionMatrix(camera.combined);
      entityBatch.begin();
      entityBatch.draw(entityTexture, location.getX(), location.getY(), 40,40); //this should probably be handled by the replacement system
      entityBatch.end();
    }
  }

  private void drawHealthBar() {
    float offset = (float) (hitBoxSize / 1.1);
    int x1 = (int) (location.getX() - offset) + 1; //slightly closer
    int y1 = (int) (location.getY() + offset / 1.1);
    int x2 = (int) (currentHealth / maxHealth * offset + offset);
    int y2 = 5;
    shapeRenderer.setProjectionMatrix(camera.combined);
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(Color.GREEN);
    shapeRenderer.rect(x1, y1, x2, y2);
    shapeRenderer.end();
  }

  //this would be for moving only the same way as the player ie in 8 directions
  public void basicMove(Point p) {
    float xMov = 0;
    float yMov = 0;
    if(p.getY() < yPos){
      yMov -= this.speed;
    }  if(p.getY() < yPos){
      yMov += this.speed;
    }  if(p.getX() < yPos){
      xMov += this.speed;
    }  if(p.getX() < yPos){
      xMov += this.speed;
    }
    if(yMov != 0 && xMov !=0){
      xMov /= Math.sqrt(2);
      yMov /= Math.sqrt(2) ; //1.41 is sufficient unless we wanna move in better, more intereting angles
    }
  }

  public void move(Point p) {
    Point startPoint = new Point(this.location);//can be done without start and end points
    Point endPoint = new Point(p);

    float dx = endPoint.getX() - startPoint.getX();
    float dy = endPoint.getY() - startPoint.getY();
    float len = startPoint.getDistance(endPoint);

    dx /= len;
    dy /= len;
    dx *= speed * Gdx.graphics.getDeltaTime();
    dy *= speed * Gdx.graphics.getDeltaTime();

    int size = (int)hitBoxSize;
    if (tileList.HorizontalCollision(location.getX(), location.getY(), size, size, dx))
      dx = 0;
    if (tileList.VerticalCollision(location.getX(), location.getY(), size, size, dy))
      dy = 0;

    location.translate(dx, dy);
  }

  public float attack(Point p) {
    Point startPoint = new Point(this.location);
    Point endPoint = new Point(p);
    float len = startPoint.getDistance(endPoint);
    if (len < 50)
      return attackStrength;
    return 0;
  }

  public Texture getTexture(){
    return entityTexture;
  }
}