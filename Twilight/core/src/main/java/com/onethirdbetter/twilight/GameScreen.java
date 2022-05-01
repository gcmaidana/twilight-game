package com.onethirdbetter.twilight;
/*
GameScreen draws all entities and allows the player to move their character. It
allows you to play the game.
 */
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameScreen implements Screen {
  final Twilight game;
  private FitViewport viewport;
  public static OrthographicCamera camera;
  private Rectangle playerIcon;
  private float playerHealth = 150;
  private float worldWidth = 1280, worldHeight = 720;
  private ShapeRenderer gameArea = new ShapeRenderer();
  public static MapTileList tileList;
  private Animation animation;
  private TextureRegion[] animationFrames;
  private TextureRegion[] upFrames;
  private TextureRegion[] rightFrames;
  private TextureRegion[] downFrames;
  private TextureRegion[] leftFrames;
  private TextureRegion[] shotgunFrames;
  private Animation upAnimation;
  private Animation rightAnimation;
  private Animation downAnimation;
  private Animation leftAnimation;
  private Animation shotgun;
  private Texture image;
  private Texture shotgunImage;
  private int actualTileSize;
  float elapsedTime;
  private ItemList inventoryList;
  private Item item;
  private boolean gun_equiped, axe_equiped;
  private float time;
  private int numWood, numCoins;

  /**
   * Default constructor.
   */
  public GameScreen(final Twilight game) {
    this.game = game;
    image = new Texture(Gdx.files.internal("playerTextures.png"));
    shotgunImage = new Texture(Gdx.files.internal("shotgunTransparent.png"));
    TextureRegion[][] tempFrames = TextureRegion.split(image, 30, 54);
    TextureRegion[][] tempShotgun = TextureRegion.split(shotgunImage, 62, 60);
    animationFrames = new TextureRegion[12];
    upFrames = new TextureRegion[3];
    rightFrames = new TextureRegion[3];
    downFrames = new TextureRegion[3];
    leftFrames = new TextureRegion[3];
    shotgunFrames = new TextureRegion[5];
    int index = 0, upIndex = 0, rightIndex = 0, downIndex = 0, leftIndex = 0, shotgunIndex = 0;
    for(int i = 0; i < 4; i++){
      for(int j = 0; j < 3; j++){
        animationFrames[index++] = tempFrames[i][j];
        if(i == 0)
          upFrames[upIndex++] = tempFrames[i][j];
        else if(i == 1)
          rightFrames[rightIndex++] = tempFrames[i][j];
        else if(i == 2)
          downFrames[downIndex++] = tempFrames[i][j];
        else if(i == 3)
          leftFrames[leftIndex++] = tempFrames[i][j];
      }
    }
    for(int i = 0; i < 5; i++)
      shotgunFrames[shotgunIndex++] = tempShotgun[0][i];
    animation = new Animation(1/10f,animationFrames);
    upAnimation = new Animation(1/10f, upFrames);
    rightAnimation = new Animation(1/10f, rightFrames);
    downAnimation = new Animation(1/10f, downFrames);
    leftAnimation = new Animation(1/10f, leftFrames);
    shotgun = new Animation(1/4f, shotgunFrames);
    camera = new OrthographicCamera();
    viewport = new FitViewport(worldWidth,worldHeight,camera);
    camera.position.set(worldWidth/2, worldHeight/2, 0);
    actualTileSize = 40;
    gun_equiped = true;
    axe_equiped = false;
    numWood = 0;
    numCoins = 0;
    tileList = new MapTileList();
    inventoryList = new ItemList();
    playerIcon = new Rectangle();
    playerIcon.x = 100;
    playerIcon.y = 100;
    playerIcon.width = 21;
    playerIcon.height = 35;
    enemyList.add(e1);
    enemyList.add(e2);
    enemyList.add(e3);
    for(int i = 0; i < 10; i ++){
      enemyList.add(new Entity((int) MathUtils.random(300,700), (int)MathUtils.random(300,500)));
    }
    GQ.add(new GunShot(100,200,100,200));
  }

  //test
  Entity e1 = new Entity(101,101);//cant start on same pos as player or bugs happen
  Entity e2 = new Entity(200,400);
  Entity e3 = new Entity(100,300);
  EntityList enemyList = new EntityList();
  GraphicsQueue GQ = new GraphicsQueue(1000,20);
  FrameRate FR = new FrameRate();

  /**
   * Renders the game screen and all of the entities on it. Also handles player movement and prevents the player from
   * going beyond the edge of the screen.
   */
  @Override
  public void render(float delta) {
    time += Gdx.graphics.getDeltaTime();
    if (Gdx.input.isKeyPressed(Keys.E) && time > 0.5) {
      if (gun_equiped) {
        tileList.SwitchToAxe();
        gun_equiped = false;
        axe_equiped = true;
      }
      else {
        tileList.SwitchToGun();
        gun_equiped = true;
        axe_equiped = false;
      }
      time = 0;
    }

    pickUp();//pick up items

    if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && gun_equiped){
      Weapon w = new Weapon();
      Vector3 mousePostition = viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
      int mouseX = (int)mousePostition.x;
      int mouseY = (int)mousePostition.y;
      w.shootShotgun(new Point(playerIcon.x, playerIcon.y), mouseX, mouseY, enemyList);
      w = null;
    }

    elapsedTime += Gdx.graphics.getDeltaTime();
    ScreenUtils.clear(1, 1, 1, 1);

    tileList.draw();

    camera.update();

    Point tempDestination = new Point(playerIcon.getX(), playerIcon.getY());
    enemyList.move(tempDestination);
    float damageInflicted = enemyList.attack(tempDestination) * Gdx.graphics.getDeltaTime();
    playerHealth -= damageInflicted;
    tileList.UpdateHealthBar(playerHealth);
    if (playerHealth <= 0)
      game.setScreen(new LoseScreen(game));
    enemyList.draw();
    //GQ.draw();
    //System.out.println(GQ.getNumGraphics());

    //GQ.draw();
    FR.update();
    FR.render();//feel free to comment this out if you have issues, its just here for teting

    //change this to use player specific speed later
    float speed = 150 * Gdx.graphics.getDeltaTime();
    float chopSpeed = 50 * Gdx.graphics.getDeltaTime();

    if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && axe_equiped) {
      Vector3 mousePostition = viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
      int mouseX = (int)mousePostition.x;
      int mouseY = (int)mousePostition.y;
      int xTileCount = mouseX / 40;
      int yTileCount = mouseY / 40;
      int tile = xTileCount + (yTileCount * 32);
      boolean choppedTree = false;
      if (tile >= 0 && tile < 576)
        choppedTree = tileList.Interact(tile, chopSpeed);
      if (choppedTree) {
        numWood++;
        if (!inventoryList.AddItemToStack("Wood"))
          item = new Item("Wood", 1, 64);
      }
    }

    game.batch.begin();

    game.font.setColor(Color.BLACK);
    game.font.getData().setScale(1, 1);
    game.font.draw(game.batch, String.valueOf(numWood), 650, 25);
    game.font.draw(game.batch, String.valueOf(numCoins), 690, 25);

    game.batch.setProjectionMatrix(camera.combined);

    //These temporary movement tracking variables are used to help easily determine
    //whether direction needs to be normalized.
    float xMov = 0;
    float yMov = 0;

    if (Gdx.input.isKeyPressed(Keys.A)) {
      xMov -= speed; //* Gdx.graphics.getDeltaTime(); //I moved this up to where speed is declared but it might be better here
      game.batch.draw((TextureRegion) leftAnimation.getKeyFrame(elapsedTime, true), playerIcon.x, playerIcon.y, playerIcon.width, playerIcon.height);
    }
    if (Gdx.input.isKeyPressed(Keys.D)) {
      xMov += speed; //* Gdx.graphics.getDeltaTime();
      game.batch.draw((TextureRegion) rightAnimation.getKeyFrame(elapsedTime, true), playerIcon.x, playerIcon.y, playerIcon.width, playerIcon.height);
    }
    if (Gdx.input.isKeyPressed(Keys.W)) {
      yMov += speed; //* Gdx.graphics.getDeltaTime();
      game.batch.draw((TextureRegion) upAnimation.getKeyFrame(elapsedTime, true),playerIcon.x, playerIcon.y, playerIcon.width, playerIcon.height);
    }
    if (Gdx.input.isKeyPressed(Keys.S)) {
      yMov -= speed;// * Gdx.graphics.getDeltaTime();
      game.batch.draw((TextureRegion) downAnimation.getKeyFrame(elapsedTime, true),playerIcon.x, playerIcon.y, playerIcon.width, playerIcon.height);
    }
    if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
    {
      game.batch.draw((TextureRegion) shotgun.getKeyFrame(elapsedTime, true), playerIcon.x, playerIcon.y, playerIcon.width, playerIcon.height);
    }
    else if(xMov == 0 && yMov == 0){
      game.batch.draw(animationFrames[7], playerIcon.x, playerIcon.y, playerIcon.width, playerIcon.height);
    }
    if(yMov != 0 && xMov !=0){
      float sqrt2 = (float) Math.sqrt(2);
      xMov /= sqrt2;
      yMov /= sqrt2; //1.41 is sufficient unless we wanna move in better, more interesting angles
    }
    //playerIcon.translate(); we might eventually want to use point class for this
    int width = (int)playerIcon.width;
    int height = (int)playerIcon.height;
    if (!tileList.HorizontalCollision(playerIcon.x, playerIcon.y, width, height, xMov))
      playerIcon.x += xMov ;
    if (!tileList.VerticalCollision(playerIcon.x, playerIcon.y, width, height, yMov))
      playerIcon.y += yMov ;
    game.batch.end();
  }

  public float getWorldWidth() {
    return worldHeight;
  }
  public float getWorldHeight() {
    return worldHeight;
  }


  /**
   * Method needed for this class to implement Screen class.
   */
  @Override
  public void resize(int width, int height) {
    viewport.update(width, height, true);
  }

  /**
   * Method needed for this class to implement Screen class.
   */
  @Override
  public void show() {}

  /**
   * Method needed for this class to implement Screen class.
   */
  @Override
  public void hide() {}

  /**
   * Method needed for this class to implement Screen class.
   */
  @Override
  public void pause() {}

  /**
   * Method needed for this class to implement Screen class.
   */
  @Override
  public void resume() {}

  /**
   * Method needed for this class to implement Screen class.
   */
  @Override
  public void dispose() {}
  public void pickUp(){
    for(int i= 0; i < enemyList.getNumEntities(); i ++){
        if(!enemyList.get(i).doAnimations && enemyList.get(i).location.getDistance(new Point(playerIcon.x, playerIcon.y)) < 30){
          enemyList.remove(i);
          inventoryList.AddItemToStack("gold");
          numCoins++;
        }
    }
  }
}
