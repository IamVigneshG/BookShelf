package com.fca.uconnect.raceoptions

import android.content.res.Configuration
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.*
import com.gv.bookshelf.BaseActivity
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import android.util.DisplayMetrics
import java.util.*


private const val BASIC_SAMPLE_PACKAGE = "com.example.android.testing.uiautomator.performancepages"
private const val LAUNCH_TIMEOUT = 5000L
private const val STRING_TO_BE_TYPED = "UiAutomator"
class LaunchControlTest {
    @get:Rule
    val mActivityRule = ActivityScenarioRule(BaseActivity::class.java)
    private val TAG = javaClass.simpleName
    private lateinit var device: UiDevice

//
//    lateinit var appComponentsProvider: RaceOptionAppComponentProvider
//    lateinit var vehicleAccessLayer: RaceOptionVehicleAccessLayer
//    lateinit var storage: RaceOptionLocalStorage

    @Before
    fun before() {
//        activityRule.scenario.onActivity {
//            appComponentsProvider = IFcaApplicationComponentsProvider.fromContext(it) as RaceOptionAppComponentProvider
//            vehicleAccessLayer = appComponentsProvider.provideVehicleAccessLayer(RaceOptionVehicleAccessLayer::class.java)
//            storage = appComponentsProvider.provideLocalStorage()
//        }
    }

    @Test
    fun uiAuto1(){

        Thread.sleep(4000)
        var myDevice = UiDevice.getInstance(getInstrumentation());
        myDevice.pressHome();
        val button = myDevice.findObject(
            UiSelector()
                .text("Active")
                .className("android.widget.Button")
        )
        if(button.exists() && button.isEnabled()) {
            button.click();
            Thread.sleep(4000)
        }

    }
//    private val testApp = UiDevice.getInstance(getInstrumentation())
//
//    private fun enableAirplaneMode() = apply {
//        testApp.uiDevice.openQuickSettings()
//        testApp.uiDevice.waitForIdle()
//
//        var airplaneModeIcon = checkNotNull(testApp.uiDevice.findObject(By.desc("Airplane,mode,Off.,Button")))
//
//        airplaneModeIcon.click()
//    }
//
//    private val testApp1 = UiDevice.getInstance(getInstrumentation())
//
//    private fun disableAirplaneMode() = apply {
//        testApp1.uiDevice.openQuickSettings()
//        testApp1.uiDevice.waitForIdle()
//
//        var airplaneModeIcon = checkNotNull(testApp1.uiDevice.findObject(By.desc("Airplane,mode,On.,Button")))
//
//        airplaneModeIcon.click()
//    }

    @Test
    fun uiAuto(){

        Thread.sleep(4000)
        device = UiDevice.getInstance(getInstrumentation())
        device.pressHome()


        //    UiObject(UiSelector().text("Wi-Fi")).click()

//        UiObject(
//            UiSelector().className("android.widget.GridView").description("AEROPLANE MODE")).click()



//        val scroll = UiScrollable(UiSelector().scrollable(true))
//        val airplane = device.getChildByText(
//            UiSelector().className(TextView::class.java.name),
//            "Settings"
//        ).click()


        //  Main

        val okButton : UiObject = UiScrollable(UiSelector()).getChildByText(
            UiSelector().className(TextView::class.java.name),"Settings"
        )

        okButton.click()

        val scroll = UiScrollable(UiSelector().scrollable(true))

                val airplane = scroll.getChildByText(
            UiSelector().className(TextView::class.java.name),
            "System"
        ).click()

        val scroll1 = UiScrollable(UiSelector().scrollable(true))
        val languages = scroll.getChildByText(
            UiSelector().className(TextView::class.java.name),
            "Gestures"
        ).click()
        Thread.sleep(2000)



        // Sub main

//        val okButton : UiObject = UiScrollable(UiSelector()).getChildByText(
//            UiSelector().className(TextView::class.java.name),"Shift Light"
//        )
//
//        okButton.click()
//        Thread.sleep(2000)

      //  val rule = ActivityTestRule(RaceOptionMainActivity::class.java)
     //   val activityRule = ActivityScenarioRule(RaceOptionMainActivity::class.java)

//        val launcherPackage: String = device.launcherPackageName
//        assertThat(launcherPackage, CoreMatchers.notNullValue())
//        device.wait(
//            Until.hasObject(By.pkg(launcherPackage).depth(0)),
//            LAUNCH_TIMEOUT
//        )
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        val intent = context.packageManager.getLaunchIntentForPackage(
//            BASIC_SAMPLE_PACKAGE)?.apply {
//            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        }
//        device.wait(
//            Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
//            LAUNCH_TIMEOUT)

//        val cancelButton: UiObject = device.findObject(
//            UiSelector().text("Cancel").className("android.widget.ImageButton")
//        )
        //  UiSelector().text("Launch Control").className("android.widget.Button")
//        val okButton: UiObject = device.findObject(
//            UiSelector().className("android.widget.Button")
//        )


//        val okButton : UiObject = device.findObject(
//            UiSelector().text("Settings").className("android.widget.Button")
//        )
//
//            okButton.click()
//        Thread.sleep(2000)

//        val appItem: UiObject = device.findObject(
//            UiSelector().className("android.widget.ListView")
//                .instance(0)
//                .childSelector(
//                    UiSelector().text("Apps")
//                )
//        )
    }

    @Test
    fun theme(){

        Thread.sleep(4000)
        device = UiDevice.getInstance(getInstrumentation())
        device.pressHome()

        val okButton : UiObject = UiScrollable(UiSelector()).getChildByText(
            UiSelector().className(TextView::class.java.name),"Settings"
        )

        okButton.click()

        val scroll = UiScrollable(UiSelector().scrollable(true))

        val airplane = scroll.getChildByText(
            UiSelector().className(TextView::class.java.name),
            "Display"
        ).click()

        airplane

        val dark = device.findObject(
            UiSelector().text("Dark theme").className(TextView::class.java.name)
        ).click()

        val turnon = device.findObject(
            UiSelector().text("Turn on now").className("android.widget.Button")
        ).click()


        Thread.sleep(2000)
    }

    @Test
    fun changeLa(){
        val res: Resources = getInstrumentation().context.resources
        // Change locale settings in the app.
        // Change locale settings in the app.
        val dm: DisplayMetrics = res.getDisplayMetrics()
        val conf: Configuration = res.getConfiguration()
        conf.setLocale(Locale("hi"))
//        res.
//        Thread.sleep(1000)
    }
    @Test
    fun language(){

        Thread.sleep(4000)
        device = UiDevice.getInstance(getInstrumentation())
        device.pressHome()

        val okButton : UiObject = UiScrollable(UiSelector()).getChildByText(
            UiSelector().className(TextView::class.java.name),"Settings"
        )

        okButton.click()

        val scroll = UiScrollable(UiSelector().scrollable(true))

        val system = scroll.getChildByText(
            UiSelector().className(TextView::class.java.name),
            "System"
        ).click()

        val scroll2 = UiScrollable(UiSelector().scrollable(true))
        val systemAndInput = device.findObject(
            UiSelector().text("Gboard").className(TextView::class.java.name)
      ).click()
        val languages = device.findObject(
            UiSelector().text("Languages").className(TextView::class.java.name)
        ).click()
//        val addLanguages = device.findObject(
//            UiSelector().text("Add a language").className("android.widget.Button")
//        ).click()
//        val hindi = scroll.getChildByText(
//            UiSelector().className(TextView::class.java.name),
//            "हिन्दी"
//        ).click()
//        onView(withText("हिन्दी")).perform(object :ViewAction{
//            override fun getConstraints(): Matcher<View> {
//                return isDisplayed()
//            }
//
//            override fun getDescription(): String {
//                return "dragging"
//            }
//
//            override fun perform(uiController: UiController?, view: View?) {
//                Log.d("TestStatus","view"+view)
//            }
//        })

        device.findObject(UiSelector().descriptionContains("More options")).click()
        device.findObject(UiSelector().text("Remove").className(TextView::class.java.name)).click()
        UiScrollable( UiSelector().resourceId("com.android.settings:id/dragList").childSelector(UiSelector().index(0).childSelector(UiSelector().resourceId("com.android.settings:id/checkbox")))).click()
        device.findObject(UiSelector().descriptionContains("Remove").className(TextView::class.java.name)).click()
        device.findObject(UiSelector().text("Remove")).click()
        Thread.sleep(2000)



//        val dark = device.findObject(
//            UiSelector().text("Dark theme").className(TextView::class.java.name)
//        ).click()
//
//        val turnon = device.findObject(
//            UiSelector().text("Turn on now").className("android.widget.Button")
//        ).click()

        Thread.sleep(2000)
    }

    @Test
        fun race(){
        Thread.sleep(4000)
        device = UiDevice.getInstance(getInstrumentation())
        device.pressHome()

//
//        val okButton : UiObject = UiScrollable(UiSelector()).getChildByText(
//            UiSelector().className(TextView::class.java.name),"Launch Control"
//        )
//
//        Thread.sleep(6000)
//        okButton.click()

                val turnon = device.findObject(
            UiSelector().text("Shift Light").className("android.widget.Button")
        ).click()

//        val turnon = device.findObject(
//            UiSelector().text("Shift Light").className(android.widget.Button::class.java.name)
//        ).click()
//
//        val launchControl = device.findObject(
//            UiSelector().text("Launch Control").className(android.widget.Button::class.java.name)
//        ).click()

//        val okButton : UiObject = UiScrollable(UiSelector()).getChildByText(
//            UiSelector().className(TextView::class.java.name),"Launch Control"
//        )
//        okButton.click()


    }
}

