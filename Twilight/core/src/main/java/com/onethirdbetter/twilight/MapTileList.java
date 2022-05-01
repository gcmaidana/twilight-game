package com.onethirdbetter.twilight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public class MapTileList {
  private MapTile[] list;
  private Random random;
  private Texture[] textureList =
      {
      new Texture(Gdx.files.internal("grass1.png")),
      new Texture(Gdx.files.internal("grass2.png")),
      new Texture(Gdx.files.internal("grass3.png")),
      new Texture(Gdx.files.internal("grass4.png")),
      new Texture(Gdx.files.internal("grass5.png")),
      new Texture(Gdx.files.internal("grass6.png")),
      new Texture(Gdx.files.internal("grass7.png")),
      new Texture(Gdx.files.internal("grass8.png")),
      new Texture(Gdx.files.internal("house1.png")),
      new Texture(Gdx.files.internal("house2.png")),
      new Texture(Gdx.files.internal("house3.png")),
      new Texture(Gdx.files.internal("house4.png")),
      new Texture(Gdx.files.internal("house5.png")),
      new Texture(Gdx.files.internal("house6.png")),
      new Texture(Gdx.files.internal("house7.png")),
      new Texture(Gdx.files.internal("house8.png")),
      new Texture(Gdx.files.internal("house9.png")),
      new Texture(Gdx.files.internal("house10.png")),
      new Texture(Gdx.files.internal("house11.png")),
      new Texture(Gdx.files.internal("house12.png")),
      new Texture(Gdx.files.internal("house13.png")),
      new Texture(Gdx.files.internal("house14.png")),
      new Texture(Gdx.files.internal("house15.png")),
      new Texture(Gdx.files.internal("house16.png")),
      new Texture(Gdx.files.internal("house17.png")),
      new Texture(Gdx.files.internal("house18.png")),
      new Texture(Gdx.files.internal("house19.png")),
      new Texture(Gdx.files.internal("house20.png")),
      new Texture(Gdx.files.internal("house21.png")),
      new Texture(Gdx.files.internal("house22.png")),
      new Texture(Gdx.files.internal("house23.png")),
      new Texture(Gdx.files.internal("house24.png")),
      new Texture(Gdx.files.internal("house25.png")),
      new Texture(Gdx.files.internal("house26.png")),
      new Texture(Gdx.files.internal("house27.png")),
      new Texture(Gdx.files.internal("house28.png")),
      new Texture(Gdx.files.internal("house29.png")),
      new Texture(Gdx.files.internal("house30.png")),
      new Texture(Gdx.files.internal("tree1.png")),
      new Texture(Gdx.files.internal("tree2.png")),
      new Texture(Gdx.files.internal("tree3.png")),
      new Texture(Gdx.files.internal("tree4.png")),
      new Texture(Gdx.files.internal("tree5.png")),
      new Texture(Gdx.files.internal("tree6.png")),
      new Texture(Gdx.files.internal("tree7.png")),
      new Texture(Gdx.files.internal("heart1.png")),
      new Texture(Gdx.files.internal("heart2.png")),
      new Texture(Gdx.files.internal("heart3.png")),
      new Texture(Gdx.files.internal("heart4.png")),
      new Texture(Gdx.files.internal("heart5.png")),
      new Texture(Gdx.files.internal("heart6.png")),
      new Texture(Gdx.files.internal("heart7.png")),
      new Texture(Gdx.files.internal("heart8.png")),
      new Texture(Gdx.files.internal("heart9.png")),
      new Texture(Gdx.files.internal("axe.png")),
      new Texture(Gdx.files.internal("axe_equiped.png")),
      new Texture(Gdx.files.internal("gun.png")),
      new Texture(Gdx.files.internal("gun_equiped.png")),
      new Texture(Gdx.files.internal("wood.png")),
      new Texture(Gdx.files.internal("coin.png"))
  };

  MapTileList() {
    random = new Random();
    int index = 0;
    list = new MapTile[576];
    for (int y = 0; y < 720; y = y + 40)
      for (int x = 0; x < 1280; x = x + 40) {
        list[index] = new MapTile(x, y);
        index++;
      }
    GenerateHUD();
    GenerateHouse();
    GenerateTree();
    GenerateGrass();
  }

  private void GenerateHUD() {
    list[9].ChangeTile("FullHeart", textureList[45], false);
    list[10].ChangeTile("FullHeart", textureList[48], false);
    list[11].ChangeTile("FullHeart", textureList[51], false);
    list[14].ChangeTile("Gun_Equiped", textureList[57], false);
    list[15].ChangeTile("Axe", textureList[54], false);
    list[16].ChangeTile("Wood", textureList[58], false);
    list[17].ChangeTile("Coin", textureList[59], false);
  }

  private void GenerateHouse() {
    int tile;
    int randomNum = random.nextInt(4);
    if (randomNum == 0)
      tile = 105;
    else if (randomNum == 1)
      tile = 113;
    else if (randomNum == 2)
      tile = 329;
    else
      tile = 337;
    int count = 0;
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 6; j++) {
        if ((count < 7) || (10 < count && count < 13) || (15 < count && count < 19)
            || (22 < count && count < 30))
          list[tile].ChangeTile("House", textureList[count + 8], true);
        else
          list[tile].ChangeTile("House", textureList[count + 8], false);
        count++;
        tile++;
      }
      tile = tile + 26;
    }
  }

  private void GenerateTree() {
    for (int i = 0; i < 544; i++)
      if (list[i].IsEmpty() && list[i + 32].IsEmpty())
        if (random.nextInt(25) == 1) {
          list[i].ChangeTile("TreeBottom", textureList[38], false);
          list[i + 32].ChangeTile("TreeTop", textureList[39], false);
        }
  }

  private void GenerateGrass() {
    for (int i = 0; i < 576; i++)
      if (list[i].IsEmpty())
        list[i].ChangeTile("Grass", textureList[random.nextInt(8)], true);
  }

  public boolean HorizontalCollision (float x, float y, int width, int height, float movement) {
    float xTileDecimal = x / 40;
    float yTileDecimal = y / 40;
    int xTileCount = (int)xTileDecimal;
    int yTileCount = (int)yTileDecimal;
    int tile = xTileCount + (yTileCount * 32);
    if (movement > 0) {
      float newX = (x + width + movement);
      if (newX <= ((xTileCount + 1) * 40))
        return false;
      else if (newX > 1280)
        return true;
      else if (!list[tile + 1].IsPassable()){
        return true;
      }
      else if (y % 40 > 40 - height) {
        if (!list[tile + 33].IsPassable())
          return true;
      }
    }
    if (movement < 0) {
      float newX = (x + movement);
      if (newX > (xTileCount * 40))
        return false;
      else if (newX < 0)
        return true;
      else if (!list[tile - 1].IsPassable()){
        return true;
      }
      else if (y % 40 > 40 - height) {
        if (!list[tile + 31].IsPassable())
          return true;
      }
    }
    return false;
  }

  public boolean VerticalCollision (float x, float y, int width, int height, float movement) {
    float xTileDecimal = x / 40;
    float yTileDecimal = y / 40;
    int xTileCount = (int)xTileDecimal;
    int yTileCount = (int)yTileDecimal;
    int tile = xTileCount + (yTileCount * 32);
    if (movement > 0) {
      float newY = (y + height + movement);
      if (newY <= ((yTileCount + 1) * 40))
        return false;
      else if (newY > 720)
        return true;
      else if (!list[tile + 32].IsPassable()){
        return true;
      }
      else if (x % 40 > 40 - width) {
        if (!list[tile + 33].IsPassable())
          return true;
      }
    }
    if (movement < 0) {
      float newY = (y + movement);
      if (newY > (yTileCount * 40))
        return false;
      else if (newY < 0)
        return true;
      else if (!list[tile - 32].IsPassable()){
        return true;
      }
      else if (x % 40 > 40 - width) {
        if (!list[tile - 31].IsPassable())
          return true;
      }
    }
    return false;
  }

  public boolean Interact(int tile, float chopSpeed) {
    String type = list[tile].GetType();
    if (!type.equals("TreeBottom") && !type.equals("SemiDamagedTreeBottom") && !type.equals("VeryDamagedTreeBottom"))
      return false;
    list[tile].HitTree(chopSpeed);
    if (type.equals("TreeBottom") && list[tile].GetHitPoints() < 100) {
      list[tile].ChangeTile("SemiDamagedTreeBottom", textureList[40], false);
      list[tile + 32].ChangeTile("SemiDamagedTreeTop", textureList[41], false);
      return false;
    }
    if (type.equals("SemiDamagedTreeBottom") && list[tile].GetHitPoints() <= 50) {
      list[tile].ChangeTile("VeryDamagedTreeBottom", textureList[42], false);
      list[tile + 32].ChangeTile("VeryDamagedTreeTop", textureList[43], false);
      return false;
    }
    if (list[tile].GetHitPoints() <= 0) {
      list[tile].ChangeTile("Stump", textureList[44], true);
      list[tile + 32].ChangeTile("Grass", textureList[5], true);
      return true;
    }
    return false;
  }

  public void UpdateHealthBar(float playerHealth) {
    if (!list[11].GetType().equals("NoHeart")) {
      if (list[11].GetType().equals("FullHeart") && playerHealth <= 125)
        list[11].ChangeTile("HalfHeart", textureList[52], false);
      if (list[11].GetType().equals("HalfHeart") && playerHealth <= 100)
        list[11].ChangeTile("NoHeart", textureList[53], false);
    }
    else if (!list[10].GetType().equals("NoHeart")) {
      if (list[10].GetType().equals("FullHeart") && playerHealth <= 75)
        list[10].ChangeTile("HalfHeart", textureList[49], false);
      if (list[10].GetType().equals("HalfHeart") && playerHealth <= 50)
        list[10].ChangeTile("NoHeart", textureList[50], false);
    }
    else {
      if (list[9].GetType().equals("FullHeart") && playerHealth <= 25)
        list[9].ChangeTile("HalfHeart", textureList[46], false);
      if (list[9].GetType().equals("HalfHeart") && playerHealth <= 0)
        list[9].ChangeTile("NoHeart", textureList[47], false);
    }
  }

  public void SwitchToAxe() {
    list[14].ChangeTile("Gun", textureList[56], false);
    list[15].ChangeTile("Axe_Equiped", textureList[55], false);
  }

  public void SwitchToGun() {
    list[14].ChangeTile("Gun_Equiped", textureList[57], false);
    list[15].ChangeTile("Axe", textureList[54], false);
  }

  public void draw() {
    for (int i = 0; i < 576; i++)
      list[i].draw();
  }
}