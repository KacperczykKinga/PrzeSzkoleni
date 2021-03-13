package com.example.responsywne.Activities


import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.responsywne.Adapters.ContentAdapter
import com.example.responsywne.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun loginTest() {
        Thread.sleep(7000)

        val haveAccountName = onView(withId(R.id.haveAccount))
        val registerName = onView(withId(R.id.register))

        haveAccountName.check(matches(withText("Zaloguj się")))
        registerName.check(matches(withText("Zarejestruj się")))

        val appCompatButton = onView(
            allOf(withId(R.id.haveAccount), withText("Zaloguj się"),
                childAtPosition(allOf(withId(R.id.startPage),
                        childAtPosition(withId(android.R.id.content), 0)), 1),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        val haveAccountName2 = onView(withId(R.id.register))
        haveAccountName2.check(matches(withText("Zaloguj się")))

        Thread.sleep(7000)

        val appCompatEditText = onView(
            allOf(withId(R.id.eTUsername), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 0),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("test"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(withId(R.id.eTUsername), withText("test"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 0),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(pressImeActionButton())

        val appCompatEditText3 = onView(
            allOf(withId(R.id.eTPassword), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 1),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(replaceText("test"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
            allOf(withId(R.id.eTPassword), withText("test"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 1),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(pressImeActionButton())

        val appCompatButton2 = onView(
            allOf(withId(R.id.register), withText("Zaloguj się"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 2),
                isDisplayed()
            )
        )
        appCompatButton2.perform(click())

        Thread.sleep(7000)

        onData(anything()).inAdapterView(withId(R.id.lVAllLessons)).atPosition(0).onChildView(withId(R.id.tVLesson)).check(matches(withText(startsWith("Filmoznawstwo"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllLessons)).atPosition(1).onChildView(withId(R.id.tVLesson)).check(matches(withText(startsWith("Gotowanie"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllLessons)).atPosition(2).onChildView(withId(R.id.tVLesson)).check(matches(withText(startsWith("Wyszywanie"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllLessons)).atPosition(3).onChildView(withId(R.id.tVLesson)).check(matches(withText(startsWith("Pielęgnacja roślin"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllLessons)).atPosition(4).onChildView(withId(R.id.tVLesson)).check(matches(withText(startsWith("Ekologia"))));

        embroideryTest()
        gradesTest()
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    fun embroideryTest() {

        val linearLayout = onData(anything()).inAdapterView(
            allOf(withId(R.id.lVAllLessons), childAtPosition(withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")), 1)))
        val thirdValue = linearLayout.atPosition(2)

        thirdValue.perform(click())

        Thread.sleep(7000)

        onData(anything()).inAdapterView(withId(R.id.lVAllTopics)).atPosition(0).onChildView(withId(R.id.tVTopic)).check(matches(withText(startsWith("Historia haftu"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllTopics)).atPosition(1).onChildView(withId(R.id.tVTopic)).check(matches(withText(startsWith("Rodzaje haftu"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllTopics)).atPosition(2).onChildView(withId(R.id.tVTopic)).check(matches(withText(startsWith("Praktyka haftu"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllTopics)).atPosition(3).onChildView(withId(R.id.tVTopic)).check(matches(withText(startsWith("Test"))));

        onData(anything()).inAdapterView(withId(R.id.lVAllTopics)).atPosition(0).onChildView(withId(R.id.tVTopicNumber)).check(matches(withText(startsWith("1"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllTopics)).atPosition(1).onChildView(withId(R.id.tVTopicNumber)).check(matches(withText(startsWith("2"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllTopics)).atPosition(2).onChildView(withId(R.id.tVTopicNumber)).check(matches(withText(startsWith("3"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllTopics)).atPosition(3).onChildView(withId(R.id.tVTopicNumber)).check(matches(withText(startsWith("4"))));

        val linearLayout8 = onData(anything())
            .inAdapterView(
                allOf(withId(R.id.lVAllTopics),
                    childAtPosition(withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")), 0)))
            .atPosition(0)
        linearLayout8.perform(click())

        Thread.sleep(7000)

        onData(anything()).inAdapterView(withId(R.id.lVAllContents)).atPosition(0).onChildView(withId(R.id.tVTitle)).check(matches(withText(startsWith("Ze wschodu"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllContents)).atPosition(1).onChildView(withId(R.id.tVTitle)).check(matches(withText(startsWith("Średniowiecze"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllContents)).atPosition(2).onChildView(withId(R.id.tVTitle)).check(matches(withText(startsWith("Stroje ludowe"))));

        val appCompatImageButton8 = onView(
            allOf(withId(R.id.nextTopic), childAtPosition(
                    allOf(withId(R.id.lastButtons), childAtPosition(withClassName(`is`("android.widget.LinearLayout")), 1)), 2),
                isDisplayed()
            )
        )
        appCompatImageButton8.perform(click())

        Thread.sleep(7000)

        onData(anything()).inAdapterView(withId(R.id.lVAllContents)).atPosition(0).onChildView(withId(R.id.tVTitle)).check(matches(withText(startsWith("Haft krzyżykowy"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllContents)).atPosition(1).onChildView(withId(R.id.tVTitle)).check(matches(withText(startsWith("Haft nakładany"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllContents)).atPosition(2).onChildView(withId(R.id.tVTitle)).check(matches(withText(startsWith("Haft richelieu"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllContents)).atPosition(3).onChildView(withId(R.id.tVTitle)).check(matches(withText(startsWith("Mereżka"))));

        val appCompatImageButton9 = onView(
            allOf(
                withId(R.id.nextTopic),
                childAtPosition(allOf(withId(R.id.lastButtons),
                        childAtPosition(withClassName(`is`("android.widget.LinearLayout")), 1)), 2),
                isDisplayed()
            )
        )
        appCompatImageButton9.perform(click())

        Thread.sleep(7000)

        onData(anything()).inAdapterView(withId(R.id.lVAllContents)).atPosition(0).onChildView(withId(R.id.tVTitle)).check(matches(withText(startsWith("Haft łańcuszkowy"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllContents)).atPosition(1).onChildView(withId(R.id.tVTitle)).check(matches(withText(startsWith("Haft matematyczny"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllContents)).atPosition(2).onChildView(withId(R.id.tVTitle)).check(matches(withText(startsWith("Haft supełkowy"))));

        val appCompatImageView3 = onView(
            allOf(withId(R.id.arrow), childAtPosition(
                    childAtPosition(withClassName(`is`("android.widget.LinearLayout")), 0), 0),
                isDisplayed()
            )
        )
        appCompatImageView3.perform(click())

        Thread.sleep(7000)

        onData(anything()).inAdapterView(withId(R.id.lVAllTopics)).atPosition(0).onChildView(withId(R.id.tVTopic)).check(matches(withText(startsWith("Historia haftu"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllTopics)).atPosition(1).onChildView(withId(R.id.tVTopic)).check(matches(withText(startsWith("Rodzaje haftu"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllTopics)).atPosition(2).onChildView(withId(R.id.tVTopic)).check(matches(withText(startsWith("Praktyka haftu"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllTopics)).atPosition(3).onChildView(withId(R.id.tVTopic)).check(matches(withText(startsWith("Test"))));

        onData(anything()).inAdapterView(withId(R.id.lVAllTopics)).atPosition(0).onChildView(withId(R.id.tVTopicNumber)).check(matches(withText(startsWith("1"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllTopics)).atPosition(1).onChildView(withId(R.id.tVTopicNumber)).check(matches(withText(startsWith("2"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllTopics)).atPosition(2).onChildView(withId(R.id.tVTopicNumber)).check(matches(withText(startsWith("3"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllTopics)).atPosition(3).onChildView(withId(R.id.tVTopicNumber)).check(matches(withText(startsWith("4"))));

        val appCompatImageView4 = onView(
            allOf(
                withId(R.id.arrow),
                childAtPosition(childAtPosition(withClassName(`is`("android.widget.LinearLayout")), 0), 0),
                isDisplayed()
            )
        )
        appCompatImageView4.perform(click())
    }

    fun gradesTest()
    {
        Thread.sleep(7000)

        val linearLayout14 = onView(
            allOf(withId(R.id.toGrades), childAtPosition(childAtPosition(withId(R.id.action_bar), 0), 2),
                    isDisplayed()
                )
            )

        linearLayout14.perform(click())

        Thread.sleep(7000)

        onData(anything()).inAdapterView(withId(R.id.lVAllGrades)).atPosition(0).onChildView(withId(R.id.tVName)).check(matches(withText(startsWith("Filmoznawstwo"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllGrades)).atPosition(1).onChildView(withId(R.id.tVName)).check(matches(withText(startsWith("Gotowanie"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllGrades)).atPosition(2).onChildView(withId(R.id.tVName)).check(matches(withText(startsWith("Wyszywanie"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllGrades)).atPosition(3).onChildView(withId(R.id.tVName)).check(matches(withText(startsWith("Pielęgnacja roślin"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllGrades)).atPosition(4).onChildView(withId(R.id.tVName)).check(matches(withText(startsWith("Ekologia"))));

        onData(anything()).inAdapterView(withId(R.id.lVAllGrades)).atPosition(0).onChildView(withId(R.id.tVGrade)).check(matches(withText(startsWith("3"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllGrades)).atPosition(1).onChildView(withId(R.id.tVGrade)).check(matches(withText(startsWith("3"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllGrades)).atPosition(2).onChildView(withId(R.id.tVGrade)).check(matches(withText(startsWith("4"))));
        onData(anything()).inAdapterView(withId(R.id.lVAllGrades)).atPosition(3).onChildView(withId(R.id.tVGrade)).check(matches(withText(startsWith(""))));
        onData(anything()).inAdapterView(withId(R.id.lVAllGrades)).atPosition(4).onChildView(withId(R.id.tVGrade)).check(matches(withText(startsWith(""))));

        val average = onView(withId(R.id.tVAverage))

        average.check(matches(withText("Średnia:  3,33 ")))

        val logOut = onView(withId(R.id.logOutButton))

        logOut.check(matches(withText("Wyloguj")))
    }
}
