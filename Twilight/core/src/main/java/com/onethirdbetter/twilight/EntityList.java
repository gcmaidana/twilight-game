package com.onethirdbetter.twilight;
/*
 * Entity list is a list of entities spawned on the map. It also contains the methods to move and draw those entities.
 */
public class EntityList {

  private int listSize = 1000;//this is arbitrary, but reasonable
  private int numEntities = 0;
  private Entity[] list = new Entity[listSize];

  public int getNumEntities() {
    return numEntities;
  }

  //adds and entity to the list
  void add(Entity e) {
    list[numEntities] = e;
    numEntities ++;
  }

  //removes an entity at a given index
  public void remove(int index) {
    list[index] = list[--numEntities];
  }


  Entity get(int index) {
    return list[index];
  }

  /**
   * runs through the list of entities and applies their draw functions
   */
  public void draw() {
    for(int i = 0; i < numEntities; i ++){
      list[i].draw();

    }
  }

  /**
   * runs through the list of entities and applies their move functions
   */
  public void move(Point p) { //this moves every entity to a single point, something needs to be changed to allow entities to move towards arbitrary destinations
    for(int i = 0; i < numEntities; i ++) {
      //System.out.println(i + ": " + list[i].location.getX());//remember to remove xpos from entity
      list[i].move(p);
    }
  }

  public float attack(Point p) {
    float damage = 0;
    for(int i = 0; i < numEntities; i ++)
      damage += list[i].attack(p);
    return damage;
  }
}