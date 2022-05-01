package com.onethirdbetter.twilight;
import org.junit.Assert;
import org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;


// We could not configure the test folder to LibGDX and IntelliJ because
// of the way LibGDX makes a lot of folders for the project since it's cross platform.
// However, we are still including this test case because we want to
// demonstrate that we wrote a test case, it will not run however.


class GameScreenTest {

  @org.junit.jupiter.api.Test
  void testBounds(){
    GameScreen testObj = null;
    float testHeightBound = testObj.getWorldHeight();
    float testWidthBound = testObj.getWorldWidth();
    Assert.assertTrue(testHeightBound == 720); // Should be true
    Assert.assertTrue(testWidthBound == 1280); // Should be true

    Assert.assertTrue(testHeightBound > 720); // Should be false
    Assert.assertTrue(testWidthBound > 1280); // Should be false
  }


}