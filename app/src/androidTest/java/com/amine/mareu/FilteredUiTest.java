package com.amine.mareu;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.amine.mareu.Dialogue.FilterDialogueFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class FilteredUiTest {
  @Rule
  public ActivityScenarioRule<FilterDialogueFragment> filteredTesRule;

  @Before
  public void setUp() throws Exception {
    filteredTesRule = new ActivityScenarioRule<>(FilterDialogueFragment.class);
  }

  @Test
  public void testIfFiltredFragmentIsDisplay() {
    // TODO A implementer
  }

}
