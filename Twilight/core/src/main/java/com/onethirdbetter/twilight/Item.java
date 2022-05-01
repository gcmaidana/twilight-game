package com.onethirdbetter.twilight;

class Item {
  String name;
  //add other stuff as needed, I could really only think of name but we probably aslo need image
  //note this is expected to be different from an item that would be floating around on the map, for those
  //i reccomend using entity since it has everything we need. This is intended to be something to track
  //inventories and stuff

  private int numberOfItems;// i put these here incase we need stackable items
  private int maxStack;//but I'm not sure we want to keep track of them within each item
  //if we do then the itemlist needs to be able to update these somehow when you add an item, otherwise
  //the list needs another way to keep track of these, possible an array,
  //we can also do maxstacking with a lookup table if having it inside of item isnt clean enough

  public Item() {}

  public Item(String name, int numberOfItems, int maxStack) {
    this.name = name;
    this.numberOfItems = numberOfItems;
    this.maxStack = maxStack;
  }

  public boolean IsItem(String item) {
    if (item.equals(name))
      return true;
    return false;
  }

  public boolean HasStackSpace() {
    if (numberOfItems < maxStack)
      return true;
    return false;
  }

  public int GetItemNum() {
    return numberOfItems;
  }

  public void AddItemToStack() {
    numberOfItems++;
  }

  //use a sprite batch as a param?
  public void draw() {
    //needs to be able to add an image to a sprite batch

  }
}
