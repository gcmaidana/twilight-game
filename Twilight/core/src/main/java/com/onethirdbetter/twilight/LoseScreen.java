package com.onethirdbetter.twilight;
/*
 * LoseScreen draws the game over screen when the player loses the game.
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

public class LoseScreen implements Screen {
  final Twilight game;
  private FitViewport viewport;
  private OrthographicCamera camera;
  private Texture loseTexture;
  private Image loseImage;
  private Stage loseStage;

  /**
   * Default constructor.
   */
  public LoseScreen (final Twilight game) {
    this.game = game;
    camera = new OrthographicCamera();
    viewport = new FitViewport(1280,720,camera);
  }

  /**
   * Loads the lose screen texture in as an image. After setting up the image settings, it adds an actor to the image.
   * This allows for the action sequence to make the lose screen to fade in and out with the appropriate delays. Once
   * it fades out, it runs a method which creates a MainMenuScreen object.
   */
  @Override
  public void show() {
    loseTexture = new Texture(Gdx.files.internal("lose.png"));
    loseImage = new Image(loseTexture);
    loseImage.setSize(1280, 720);
    loseStage = new Stage();
    loseStage.addActor(loseImage);
    loseImage.addAction(Actions.sequence(Actions.alpha(0.0F), Actions.delay(0.5F),
        Actions.fadeIn(1.25F), Actions.delay(2F), Actions.fadeOut(1.25F),
        Actions.run(new Runnable() {
          public void run() {
            game.setScreen(new MainMenuScreen(game));
          }
        })));
  }

  /**
   * Sets the background to white and renders the lose screen.
   */
  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0,0,0,1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    loseStage.act();
    loseStage.draw();
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
   * Disposes of the stages and textures once the lose screen is finished playing and before moving back to the
   * main menu, as they are no longer needed at that point.
   */
  @Override
  public void dispose() {
    loseTexture.dispose();
    loseStage.dispose();
  }
}