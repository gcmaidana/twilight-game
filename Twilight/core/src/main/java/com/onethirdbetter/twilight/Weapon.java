package com.onethirdbetter.twilight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class Weapon extends Item {
  Music gunshotSound = Gdx.audio.newMusic(Gdx.files.internal("gunshot.mp3"));
  long cd = 0;//this will be set to the system time after each action while actual cooldown times are hardcoded in for now
  int range = 225;

  void shootShotgun(Point playerLoc, int x, int y, EntityList enemyList) { //where p is the original location and x  are the destin. If needed change it to point as well in the future
    int range = 225;
    if(System.currentTimeMillis() - cd > 1000) {
      float distance = playerLoc.getDistance(new Point(x,y));

      //determine scale
      float scale = range / distance;
      //rescale shot/graphical endpoints in relation to player position (<-important)
      x = (int)((x - playerLoc.getX()) * scale + playerLoc.getX());
      y = (int)((y - playerLoc.getY()) * scale + playerLoc.getY());

      gunshotSound.setVolume(0.3f);
      gunshotSound.play();

      //scatter a couple of shots randomly for shotgun
      for(int i = 0; i < 8; i ++){
        Point p0 = getRandomPoint(new Point(x, y), 45, 45);
        directShot(playerLoc, p0, enemyList);
      }

      //resetting the cooldown
      cd = System.currentTimeMillis();

    }
  }

  void shootPistol(Point playerLoc, int x, int y, EntityList enemyList){
    int range = 140;
    if(System.currentTimeMillis() - cd > 100) {

      float distance = playerLoc.getDistance(new Point(x,y));

      //determine scale
      float scale = range / distance;
      //rescale shot/graphical endpoints in relation to player position (<-important) (in direction of mouseclick or the x/y input actually)
      x = (int)((x - playerLoc.getX()) * scale + playerLoc.getX());
      y = (int)((y - playerLoc.getY()) * scale + playerLoc.getY());

//      pistol1.amp(.01);more sounds to be played
//      pistol1.play();

      Point p0 = getRandomPoint(new Point(x, y), 10, 10);
      directShot(playerLoc, p0, enemyList);
      //resetting the cooldown
      cd = System.currentTimeMillis();
    }
  }

  private void directShot(Point playerLoc, Point p0, EntityList enemyList){
    for(int j = 0; j < enemyList.getNumEntities(); j ++){//run through the entity list
      //calc area using vector dot product
      float areaTimes2 = Math.abs((p0.getX() - playerLoc.getX()) * (enemyList.get(j).location.getY() - playerLoc.getY()) - (enemyList.get(j).location.getX() - playerLoc.getX()) * ((p0.getY() - playerLoc.getY())    ));

      //distance has previously been calculated
      float len = p0.getDistance(new Point( (int)playerLoc.getX(), (int)playerLoc.getY()));
      float h = areaTimes2 / len; //h being height

      if(h < enemyList.get(j).hitBoxSize && len >   playerLoc.getDistance(enemyList.get(j).location) - enemyList.get(j).hitBoxSize / 2     && enemyList.get(j).location.getDistance(p0) < len){ //probably dont need to normalise the last one to work with really close up shots but could

        enemyList.get(j).currentHealth -= MathUtils.random(1,3);
        if(enemyList.get(j).currentHealth <= 0){
          Point p = enemyList.get(j).location;
          enemyList.remove(j);
          Entity e = new Entity((int)p.getX(), (int)p.getY());
          e.setHBDraw(false);
          e.setSpeed(0);
          e.setAnimations(false);
          e.setTexture(new Texture(Gdx.files.internal("dCoin.png")));
          enemyList.add(e);
        }
      } else {
        //GQ.add(new GunShot(player.getPos(), p0));//make the randomizer better
      }

    }
    //GQ.add(new GunShot(player.getPos(), p0));
    //where graphics would go
  }
  private Point getRandomPoint(Point p, float lower, float upper){
    return new Point((int)(p.getX() + MathUtils.random(-lower,upper)), p.getY() + (int)(MathUtils.random(-lower,upper))); //do not leave this all inside like this lol, its not modular at all
  }
}