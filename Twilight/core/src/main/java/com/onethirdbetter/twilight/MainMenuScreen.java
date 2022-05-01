package com.onethirdbetter.twilight;
/*
MainMenuScreen draws the main menu. It allows you to then play the game, go to options,
or exit the game.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.onethirdbetter.twilight.GameScreen.camera;

public class MainMenuScreen implements Screen {
  final Twilight game;
  private FitViewport viewport;
  private OrthographicCamera camera;
  protected Stage stage;
  private Skin skin;
  public static Music music;

  // When launching the game, the volume is at 20% by default, since
  // it's probably better to have it too quiet at first than too loud.
  public static float VolumeLevel = 0.2f;

  // Default constructor
  public MainMenuScreen(final Twilight game){
    this.game = game;
    camera = new OrthographicCamera();
    viewport = new FitViewport(1280,720,camera);
    stage = new Stage(viewport);
    skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
  }

  // The show() method will play the music by calling the appropriate method,
  // and it will also set up a table in which the buttons will reside in. The
  // listeners for those buttons are included in this show() method as well
  @Override
  public void show() {
    // Comment out below so annoying music doesn't play if you need to test something
    if (music == null)
      playMusic();

    Gdx.input.setInputProcessor(stage);
    Table table = new Table();
    table.setFillParent(true);
    Button startButton = new TextButton("Start Game", skin, "small");
    Button optionsButton = new TextButton("Options", skin, "small");
    Button exitButton = new TextButton("Exit", skin, "small");

    startButton.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent e, float x, float y){

        game.setScreen(new GameScreen(game));
        dispose();
      }
    });

    optionsButton.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent e, float x, float y){
        game.setScreen(new OptionsScreen(game));
        dispose();
      }
    });

    exitButton.addListener(new ClickListener(){
      public void clicked(InputEvent e, float x, float y){
        Gdx.app.exit();
      }
    });

    table.add(startButton);
    table.add(optionsButton);
    table.add(exitButton);
    stage.addActor(table);
  }

  // The render() method renders the colors, the stage (table containing the buttons)
  // and font onto the screen
  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(1,1,1,1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    stage.act();
    stage.draw();
    game.batch.begin();

    Texture twilightImage = new Texture(Gdx.files.internal("Twilighttransparent.png"));
    game.font.setColor(Color.BLACK);
    game.font.getData().setScale(2, 2);
    //game.font.draw(game.batch, "Twilight", 600, 600);

    game.batch.end();

    SpriteBatch s = new SpriteBatch();
    s.setProjectionMatrix(camera.combined);
    s.begin();
    s.draw(twilightImage, 400, 400, 500, 150);
    s.end();
  }

  public static float getVolumeLevel() {
    return VolumeLevel;
  }

  public static void setVolumeLevel(float volumeLevel) {
    VolumeLevel = volumeLevel;
  }

  // This playMusic() method will: locate the background music file,
  // set it to loop, set the volume, and play the song. At the end of the method,
  // the volume level is being set by setVolumeLevel() because we need to retrieve
  // the volume level in the options menu, otherwise we will not have the same volume level
  // when we play the same background music in the options menu.
  public void playMusic() {
    music = Gdx.audio.newMusic(Gdx.files.internal("bg-music.mp3"));
    music.setLooping(true);
    music.setVolume(getVolumeLevel());
    music.play();
    setVolumeLevel(getVolumeLevel());
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