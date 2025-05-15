package com.example.myapplication11111

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isNotChecked
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

//无参数
//@RunWith(AndroidJUnit4::class)
//class FirstFragmentTest {
//    @get:Rule
//    val activityRule = ActivityScenarioRule(MainActivity::class.java)
//
//    // 测试1：验证初始文本显示
//    @Test
//    fun textView_shouldShowDefaultText_onLaunch() {
//        onView(withId(R.id.textView)).check(matches(withText(R.string.first_fragment_label)))
//    }
//    // 测试1错误：验证初始文本显示
//    @Test
//    fun textView_shouldShowDefaultText_onLaunch1() {
//        onView(withId(R.id.textView)).check(matches(withText("未知")))
//    }
//
//    // 测试2：验证开关操作更新文本
//    @Test
//    fun switch_shouldUpdateText_whenToggled() {
//        // 首次点击开关（打开状态）
//        onView(withId(R.id.switch1))
//            .perform(click())
//
//        // 验证文本变为"第一页已打开"
//        onView(withId(R.id.textView))
//            .check(matches(withText(R.string.first_page_opened)))
//
//        // 再次点击开关（关闭状态）
//        onView(withId(R.id.switch1))
//            .perform(click())
//
//        // 验证文本恢复默认
//        onView(withId(R.id.textView))
//            .check(matches(withText(R.string.first_fragment_label)))
//    }
//
//    // 测试3：验证导航按钮功能
//    @Test
//    fun button_shouldNavigateToSecondFragment() {
//        // 点击导航按钮
//        onView(withId(R.id.button_first))
//            .perform(click())
//
//        // 验证是否跳转到SecondFragment（假设SecondFragment有textview_second这个ID）
//        onView(withId(R.id.textview_second))
//            .check(matches(isDisplayed()))
//    }
//
//    // 测试4：验证控件可见性
//    @Test
//    fun allViews_shouldBeVisible() {
//        // 验证开关可见
//        onView(withId(R.id.switch1))
//            .check(matches(isDisplayed()))
//
//        // 验证文本视图可见
//        onView(withId(R.id.textView))
//            .check(matches(isDisplayed()))
//
//        // 验证按钮可见
//        onView(withId(R.id.button_first))
//            .check(matches(isDisplayed()))
//    }
//}

//有参数
// 开关状态参数化测试
@RunWith(Parameterized::class)
class SwitchStateTest(
    private val expectedTextResId: String
) {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun switch_shouldUpdateText_whenToggled() {

        // 执行测试操作
        onView(withId(R.id.switch1)).perform(click())
        // 验证文本和状态
        onView(withId(R.id.textView))
            .check(matches(withText(expectedTextResId)))

    }
    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "预期文本={0}")
        fun testData() = listOf(
            // 参数格式：初始开关状态，预期字符串资源ID
            arrayOf("第一页已打开"),
            arrayOf("第一页未打开")
        )
    }
}

// 导航按钮参数化测试
@RunWith(Parameterized::class)
class NavigationButtonTest(
    private val buttonId: String,
    private val expectedDestinationId: String
) {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun button_shouldNavigateToCorrectFragment() {
        val buttonId = getResourceId(buttonId)
        val expectedDestinationId = getResourceId(expectedDestinationId)
        // 执行按钮点击
        onView(withId(buttonId)).perform(click())

        // 验证导航结果
        onView(withId(expectedDestinationId))
            .check(matches(isDisplayed()))
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "按钮ID={0}, 目标页面={1}")
        fun testData() = listOf(
            arrayOf("button_first", "SecondFragment"),
            arrayOf("button_second", "FirstFragment")
        )
    }
    private fun getResourceId(resName: String): Int {
        val context = ApplicationProvider.getApplicationContext<Context>()
        return context.resources.getIdentifier(resName, "id", context.packageName).apply {
            if (this == 0) throw IllegalArgumentException("资源不存在: $resName")
        }
    }
}

// 基础功能测试
class BasicFunctionalityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun allViews_shouldHaveCorrectInitialState() {
        // 验证开关初始状态
        onView(withId(R.id.switch1))
            .check(matches(isNotChecked()))

        // 验证文本初始状态
        onView(withId(R.id.textView))
            .check(matches(withText(R.string.first_fragment_label)))

        // 验证按钮可见性
        onView(withId(R.id.button_first))
            .check(matches(isDisplayed()))
    }
}