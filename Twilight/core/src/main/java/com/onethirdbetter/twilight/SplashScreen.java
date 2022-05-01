package com.onethirdbetter.twilight;
/*
SplashScreen draws the 1/3 Better logo when loading into the game.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SplashScreen implements Screen {
  final Twilight game;
  private FitViewport viewport;
  private OrthographicCamera camera;
  private Texture splash1Texture;
  private Texture splash2Texture;
  private Texture splash3Texture;
  private Texture splash4Texture;
  private Texture splash5Texture;
  private Image splash1Image;
  private Image splash2Image;
  private Image splash3Image;
  private Image splash4Image;
  private Image splash5Image;
  private Stage splash1Stage;
  private Stage splash2Stage;
  private Stage splash3Stage;
  private Stage splash4Stage;
  private Stage splash5Stage;


  /**
   * Default constructor.
   */
  public SplashScreen (final Twilight game) {
    this.game = game;
    camera = new OrthographicCamera();
    viewport = new FitViewport(1280,720,camera);
  }

  /**
   * Loads the splash screen texture in as an image. After setting up the image settings, it adds an actor to the image.
   * This allows for the action sequence to make the splash screen to fade in and out with the appropriate delays. Once
   * it fades out, it runs a method which creates a MainMenuScreen object.
   */
  @Override
  public void show() {
    splash1Texture = new Texture(Gdx.files.internal("splash1.png"));
    splash1Image = new Image(splash1Texture);
    splash1Image.setSize(1280, 720);
    splash1Stage = new Stage();
    splash1Stage.addActor(splash1Image);

    splash2Texture = new Texture(Gdx.files.internal("splash2.png"));
    splash2Image = new Image(splash2Texture);
    splash2Image.setSize(1280, 720);
    splash2Stage = new Stage();
    splash2Stage.addActor(splash2Image);

    splash3Texture = new Texture(Gdx.files.internal("splash3.png"));
    splash3Image = new Image(splash3Texture);
    splash3Image.setSize(1280, 720);
    splash3Stage = new Stage();
    splash3Stage.addActor(splash3Image);

    splash4Texture = new Texture(Gdx.files.internal("splash4.png"));
    splash4Image = new Image(splash4Texture);
    splash4Image.setSize(1280, 720);
    splash4Stage = new Stage();
    splash4Stage.addActor(splash4Image);

    splash5Texture = new Texture(Gdx.files.internal("splash5.png"));
    splash5Image = new Image(splash5Texture);
    splash5Image.setSize(1280, 720);
    splash5Stage = new Stage();
    splash5Stage.addActor(splash5Image);

    splash1Image.addAction(Actions.sequence(Actions.alpha(0.0F), Actions.delay(0.5F),
        Actions.fadeIn(0.5F), Actions.delay(1.0F), Actions.hide()));
    splash2Image.addAction(Actions.sequence(Actions.alpha(0.0F), Actions.delay(1.0F),
        Actions.fadeIn(0.4F), Actions.delay(1.0F), Actions.hide()));
    splash3Image.addAction(Actions.sequence(Actions.alpha(0.0F), Actions.delay(1.4F),
        Actions.fadeIn(0.4F), Actions.delay(1.0F), Actions.hide()));
    splash4Image.addAction(Actions.sequence(Actions.alpha(0.0F), Actions.delay(1.8F),
        Actions.fadeIn(0.4F), Actions.delay(1.0F), Actions.hide()));
    splash5Image.addAction(Actions.sequence(Actions.alpha(0.0F), Actions.delay(2.2F),
        Actions.fadeIn(0.4F), Actions.delay(1.25F), Actions.fadeOut(0.4F),
        Actions.run(new Runnable() {
          public void run() {
            game.setScreen(new MainMenuScreen(game));
          }
        })));
  }

  /**
   * Sets the background to white and renders the splash screen.
   */
  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(1,1,1,1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    splash1Stage.act();
    splash1Stage.draw();
    splash2Stage.act();
    splash2Stage.draw();
    splash3Stage.act();
    splash3Stage.draw();
    splash4Stage.act();
    splash4Stage.draw();
    splash5Stage.act();
    splash5Stage.draw();
  }

  /**
   * Method needed for this class to implement Screen class.
   */
  @Override
  public void resize(int width, int height) {
    viewport.update(width, height);
  }

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
  public void hide() {}

  /**
   * Disposes of the stages and textures once the splash screen is finished playing and before moving to the main menu,
   * as they are no longer needed at that point.
   */
  @Override
  public void dispose() {
    splash1Texture.dispose();
    splash2Texture.dispose();
    splash3Texture.dispose();
    splash4Texture.dispose();
    splash5Texture.dispose();
    splash1Stage.dispose();
    splash2Stage.dispose();
    splash3Stage.dispose();
    splash4Stage.dispose();
    splash5Stage.dispose();
  }
}