package com.amine.mareu;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;


public class MainActivityInstrumentationTestUI {
  @Rule
  public ActivityTestRule<MainActivity> activityTestRule =
      new ActivityTestRule<MainActivity>(MainActivity.class);

  @Test
}
