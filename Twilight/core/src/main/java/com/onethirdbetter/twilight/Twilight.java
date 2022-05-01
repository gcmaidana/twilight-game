package com.onethirdbetter.twilight;
/*
 * Twilight makes the splash screen which then allows all the other screens to be executed.
 * It also contains the SpriteBatch and font used in all other classes.
 */
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Twilight extends Game {
  public SpriteBatch batch;
  public BitmapFont font;

  @Override
  public void create() {
    batch = new SpriteBatch();
    font = new BitmapFont();
    this.setScreen(new SplashScreen(this));
  }

  public void render(){
    super.render();
  }

  public void dispose(){
    batch.dispose();
    font.dispose();
  }
}