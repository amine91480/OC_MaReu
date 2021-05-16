package com.amine.mareu;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class MainActivityUiTest {

  @Rule
  public ActivityScenarioRule<MainActivity> activityTestRule =
      new ActivityScenarioRule<>(MainActivity.class);

  @BeforeEach
  @DisplayName("Sleep 1,5s after each tests ")
  public void sleeper() throws InterruptedException {
    sleep(1500);
  }

  // TEST MainActivity ->
  @Test
  @DisplayName("Check if the recyclerView is Empty")
  public void myListMeeting_shouldBeEmpty() {
    onView(withId(R.id.myRecyclerView)).check(matches(hasMinimumChildCount(0)));
  }
  // TEST MainActivity ->

  // TEST AddNewMeeting ->
  @Test
  @DisplayName("This test lunch the Activity AddNewMeeting and check all textInput if is visible -> OK")
  public void myFabButton_lunchAddNewMeeting() {
    try {
      onView(withId(R.id.fab)).check(matches(isDisplayed()));
      onView(withId(R.id.fab)).perform(click()).
          check(matches(withId(R.id.appBarLayout))).
          check(matches(withId(R.id.label_autoComplete_room))).
          check(matches(withId(R.id.label_subject))).
          check(matches(withId(R.id.label_info))).
          check(matches(withId(R.id.label_participant))).
          check(matches(withId(R.id.addMeeting)));
    } catch ( NoMatchingViewException e ) {
      onView(withId(R.id.button_menu)).check(doesNotExist());
    }
  }

  @Test
  @DisplayName("We Lunch AddNewMeeeting, fill the elements and valid to return on the MainActivity and check if the Meeting is create -> OK")
  public void goToAddNewActivity_andCheckTheAutoCompleteView() {
    onView(withId(R.id.fab)).perform(click());// TODO To TEst for See If it's Work

    onView(withId(R.id.autoComplete_room)).perform(click());
    onData(anything()).atPosition(2).inRoot(RootMatchers.isPlatformPopup()).perform(click());

    onView(withId(R.id.subject)).perform(replaceText("UITestEspresso"));

    onView(withId(R.id.label_info)).perform(click());
    onView(withText("OK")).perform(click());
    onView(withText("OK")).perform(click());

    onView(withId(R.id.label_participant)).perform(click());
    onView(withId(R.id.participant)).perform(replaceText("amine91480@hotmail.fr"));

    onView(withId(R.id.label_participant)).perform(click());
    onView(withId(R.id.participant)).perform(new ClickDrawableAction(ClickDrawableAction.Right));

    onView(withId(R.id.participant)).perform(closeSoftKeyboard());

    onView(withId(R.id.addMeeting)).perform(click());

    onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
  }
  // TEST AddNewMeeting ->

  // TEST FilteredDialogue ->
  @Test
  @DisplayName("On MainActivity, click on the drawable on the toolbar and check if the dialogue and the textInput is visible")
  public void myMenuItem_lunchFilter() {
    try {
      onView(withId(R.id.button_menu)).check(matches(isDisplayed()));
      onView(withId(R.id.button_menu)).perform(click()).
          check(matches(withId(R.id.label_filter_room))).
          check(matches(withId(R.id.button_to_date))).
          check(matches(withId(R.id.label_result)));
    } catch ( NoMatchingViewException e ) {
      onView(withId(R.id.button_menu)).check((doesNotExist()));
    }
  }
  // TEST FilteredDialogue ->

  public static ViewAction clickDrawables() {
    return new ViewAction() {
      @Override
      public Matcher<View> getConstraints()//must be a textview with drawables to do perform
      {
        return allOf(isAssignableFrom(TextView.class), new BoundedMatcher<View, TextView>(TextView.class) {
          @Override
          public void describeTo(org.hamcrest.Description description) {

          }

          @Override
          protected boolean matchesSafely(final TextView tv) {
            if ( tv.requestFocusFromTouch() )//get fpocus so drawables become visible
              for ( Drawable d : tv.getCompoundDrawables() )//if the textview has drawables then return a match
                if ( d != null )
                  return true;

            return false;
          }
        });
      }

      @Override
      public String getDescription() {
        return "click drawables";
      }

      @Override
      public void perform(final UiController uiController, final View view) {
        TextView tv = ( TextView ) view;
        if ( tv != null && tv.requestFocusFromTouch() )//get focus so drawables are visible
        {
          Drawable[] drawables = tv.getCompoundDrawables();

          Rect tvLocation = new Rect();
          tv.getHitRect(tvLocation);

          Point[] tvBounds = new Point[4];//find textview bound locations
          tvBounds[0] = new Point(tvLocation.left, tvLocation.centerY());
          tvBounds[1] = new Point(tvLocation.centerX(), tvLocation.top);
          tvBounds[2] = new Point(tvLocation.right, tvLocation.centerY());
          tvBounds[3] = new Point(tvLocation.centerX(), tvLocation.bottom);

          for ( int location = 0; location < 4; location++ )
            if ( drawables[location] != null ) {
              Rect bounds = drawables[location].getBounds();
              tvBounds[location].offset(bounds.width() / 2, bounds.height() / 2);//get drawable click location for left, top, right, bottom
              if ( tv.dispatchTouchEvent(MotionEvent.obtain(android.os.SystemClock.uptimeMillis(), android.os.SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, tvBounds[location].x, tvBounds[location].y, 0)) )
                tv.dispatchTouchEvent(MotionEvent.obtain(android.os.SystemClock.uptimeMillis(), android.os.SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, tvBounds[location].x, tvBounds[location].y, 0));
            }
        }
      }
    };
  }

  public static class ClickDrawableAction implements ViewAction {
    public static final int Left = 0;
    public static final int Top = 1;
    public static final int Right = 2;
    public static final int Bottom = 3;

    @Location
    private final int drawableLocation;

    public ClickDrawableAction(@Location int drawableLocation) {
      this.drawableLocation = drawableLocation;
    }

    @Override
    public Matcher<View> getConstraints() {
      return allOf(isAssignableFrom(TextView.class), new BoundedMatcher<View, TextView>(TextView.class) {
        @Override
        public void describeTo(Description description) {

        }

        @Override
        protected boolean matchesSafely(final TextView tv) {
          //get focus so drawables are visible and if the textview has a drawable in the position then return a match
          return tv.requestFocusFromTouch() && tv.getCompoundDrawables()[drawableLocation] != null;

        }
      });
    }

    @Override
    public String getDescription() {
      return "click drawable ";
    }

    @Override
    public void perform(final UiController uiController, final View view) {
      TextView tv = ( TextView ) view;//we matched
      if ( tv != null && tv.requestFocusFromTouch() )//get focus so drawables are visible
      {
        //get the bounds of the drawable image
        Rect drawableBounds = tv.getCompoundDrawables()[drawableLocation].getBounds();

        //calculate the drawable click location for left, top, right, bottom
        final Point[] clickPoint = new Point[4];
        clickPoint[Left] = new Point(tv.getLeft() + (drawableBounds.width() / 2), ( int ) (tv.getPivotY() + (drawableBounds.height() / 2)));
        clickPoint[Top] = new Point(( int ) (tv.getPivotX() + (drawableBounds.width() / 2)), tv.getTop() + (drawableBounds.height() / 2));
        clickPoint[Right] = new Point(tv.getRight() + (drawableBounds.width() / 2), ( int ) (tv.getPivotY() + (drawableBounds.height() / 2)));
        clickPoint[Bottom] = new Point(( int ) (tv.getPivotX() + (drawableBounds.width() / 2)), tv.getBottom() + (drawableBounds.height() / 2));

        if ( tv.dispatchTouchEvent(MotionEvent.obtain(android.os.SystemClock.uptimeMillis(), android.os.SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, clickPoint[drawableLocation].x, clickPoint[drawableLocation].y, 0)) )
          tv.dispatchTouchEvent(MotionEvent.obtain(android.os.SystemClock.uptimeMillis(), android.os.SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, clickPoint[drawableLocation].x, clickPoint[drawableLocation].y, 0));
      }
    }

    @IntDef({ Left, Top, Right, Bottom })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Location {}
  }
}
