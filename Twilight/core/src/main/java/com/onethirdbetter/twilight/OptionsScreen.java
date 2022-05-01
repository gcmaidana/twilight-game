package com.onethirdbetter.twilight;
/*
 * OptionsScreen draws the settings menu. It allows you to adjust the volume of
 * the music played when playing the game.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.awt.*;

import static com.onethirdbetter.twilight.MainMenuScreen.music;

public class OptionsScreen implements Screen{
  final Twilight game;
  private FitViewport viewport;
  private OrthographicCamera camera;
  protected Stage stage;
  private Skin skin;

  // Default constructor
  public OptionsScreen(final Twilight game){
    this.game = game;
    camera = new OrthographicCamera();
    viewport = new FitViewport(1280,720,camera);
    stage = new Stage(viewport);
    skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

  }

  // The show() method will play the music at the same volume as it was in the main menu
  // and it will also set up a table in which the back button and the slider will reside in. The
  // listeners for the button and the slider are included in this show() method as well
  @Override
  public void show() {
    float volume = music.getVolume();
    Slider audioSlider = new Slider( 0f, 1f, 0.1f,false, skin );
    audioSlider.setValue(volume);
    Label audioText = new Label("Audio: ", skin);
    audioText.setColor(Color.BLACK);
    TextButton backButton = new TextButton("Back", skin, "small");
    Gdx.input.setInputProcessor(stage);
    Table table = new Table();
    table.setFillParent(true);
    table.add(audioText);
    table.add(audioSlider);
    table.row();
    table.add(backButton);
    stage.addActor(table);

    audioSlider.addListener( new EventListener() {
      @Override
      public boolean handle(Event event) {
        music.setVolume(audioSlider.getValue());
        return false;
      }
    });

    backButton.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent e, float x, float y){
        game.setScreen(new MainMenuScreen(game));
        dispose();
      }
    });

  }

  // The render() method renders the colors, the stage (table containing the button, slider)
  // and font onto the screen
  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(1,1,1,1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    stage.act();
    stage.draw();
  }


  // dispose() disposes the stage object since it is no longer being used when we exit this class.
  @Override
  public void dispose() {
    stage.dispose();
  }

  // Method is needed in the class because Screen implements it, but it's not used,
  // so it's empty.
  @Override
  public void resize(int width, int height) {
    viewport.update(width, height, true);
  }

  // Method is needed in the class because Screen implements it, but it's not used,
  // so it's empty.
  @Override
  public void pause() {

  }

  // Method is needed in the class because Screen implements it, but it's not used,
  // so it's empty.
  @Override
  public void resume() {

  }

  // Method is needed in the class because Screen implements it, but it's not used,
  // so it's empty.
  @Override
  public void hide() {

  }
}
