package com.example.eventme;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.example.eventme.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SearchEvent {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void searchEvent() {
         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(500);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
        
        ViewInteraction appCompatImageView = onView(
allOf(withClassName(is("androidx.appcompat.widget.AppCompatImageView")), withContentDescription("Search"),
childAtPosition(
allOf(withClassName(is("android.widget.LinearLayout")),
childAtPosition(
withId(R.id.searchView),
0)),
1),
isDisplayed()));
        appCompatImageView.perform(click());
        
        ViewInteraction searchAutoComplete = onView(
allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")),
childAtPosition(
allOf(withClassName(is("android.widget.LinearLayout")),
childAtPosition(
withClassName(is("android.widget.LinearLayout")),
1)),
0),
isDisplayed()));
        searchAutoComplete.perform(replaceText("head"), closeSoftKeyboard());
        
        ViewInteraction searchAutoComplete2 = onView(
allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")), withText("head"),
childAtPosition(
allOf(withClassName(is("android.widget.LinearLayout")),
childAtPosition(
withClassName(is("android.widget.LinearLayout")),
1)),
0),
isDisplayed()));
        searchAutoComplete2.perform(pressImeActionButton());
        
         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(700);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
        
        ViewInteraction textView = onView(
allOf(withId(R.id.Name), withText("Head In the Clouds"),
withParent(allOf(withId(R.id.recycle_item),
withParent(withId(R.id.mRecyclerView)))),
isDisplayed()));
        textView.check(matches(withText("Head In the Clouds")));
        }
    
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup)parent).getChildAt(position));
            }
        };
    }
    }
