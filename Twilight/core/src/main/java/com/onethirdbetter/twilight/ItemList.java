package com.onethirdbetter.twilight;

public class ItemList { // could use generic and combine with entity
  private int listSize = 64;//this is arbitrary, but reasonable
  private int numItems = 0;
  private Item[] list = new Item[listSize];

  private int getNumEntities(){
    return numItems;
  }

  //adds and entity to the list
  void add(Item e){
    list[numItems] = e;
    numItems ++;
  }

  //removes an entity at a given index
  public void remove(int index){
    list[index] = list[--numItems];
  }

  public boolean AddItemToStack(String item) {
    for(int i = 0; i < numItems; i ++)
      if (list[i].IsItem(item))
        if (list[i].HasStackSpace()) {
          list[i].AddItemToStack();
          return true;
        }
    return false;
  }

  public String GetItemCount(String item) {
    for(int i = 0; i < numItems; i ++) {
      if (list[i].IsItem(item))
        return String.valueOf(list[i].GetItemNum());
    }
    return "0";
  }

  /**
   * runs through the list of entities and applies their draw functions
   */
  public void drawAll(){
    for(int i = 0; i < numItems; i ++)
      list[i].draw();
  }
}
