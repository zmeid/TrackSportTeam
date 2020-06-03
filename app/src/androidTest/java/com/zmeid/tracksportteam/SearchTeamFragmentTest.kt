package com.zmeid.tracksportteam

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.zmeid.tracksportteam.view.ui.activity.MainActivity
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Contains tests for [SearchTeamFragmentTest].
 */
@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class SearchTeamFragmentTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    /**
     * Searches team 'Arsenal'. Clicks on favorite button and checks if activate premium dialog appears.
     */
    @Test
    fun searchTeamTest() {
        //Click Search
        onView(withId(R.id.menu_search)).perform(click())

        // Search for 'Arsenal'
        onView(withId(R.id.search_src_text)).perform(replaceText("Arsenal"), closeSoftKeyboard())

        // Sleep to wait API response
        Thread.sleep(3000)

        // Click Favorite button
        onView(withId(R.id.recyclerview_team_search)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                clickChildViewWithId(R.id.image_view_favorite)
            )
        )

        // Check if activate premium dialog appears
        onView(withText(R.string.activate_premium)).check(matches(isDisplayed()));

    }

    /**
     * Finds the child with given id and performs click.
     */
    private fun clickChildViewWithId(id: Int): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController?, view: View) {
                val v = view.findViewById<View>(id)
                v.performClick()
            }
        }
    }
}