package com.quantum.utils;

import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebDriver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.JavascriptExecutor;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class DriverUtils {

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getDataPasser() {
        return (Map<String, Object>) ConfigurationManager.getBundle().getObject("dataPasser" + Thread.currentThread());
    }

    public static void putDataPasser(String key, Object value) {
        getDataPasser().put(key, value);
    }

    public static Object getDataPasserValue(String key) {
        return getDataPasser().get(key);
    }

    public static AppiumDriver getAppiumDriver() {
        return (AppiumDriver) getDriver().getUnderLayingDriver();
    }

    public static TouchAction getTouchAction() {
        return new TouchAction(getAppiumDriver());
    }

    public static IOSDriver getIOSDriver() {
        return (IOSDriver) getAppiumDriver();
    }

    public static AndroidDriver getAndroidDriver() {
        return (AndroidDriver) getAppiumDriver();
    }

    public static QAFExtendedWebDriver getDriver() {
        return new WebDriverTestBase().getDriver();
    }

    public boolean isRunningAndroid() {
        return getOS().equalsIgnoreCase("android");
    }

    public boolean isRunningIOS() {
        return getOS().equalsIgnoreCase("ios");
    }

    private String getOS() {
        Map<String, String> params = new HashMap<>();
        params.put("property", "os");
        return (String) DriverUtils.getDriver().executeScript("mobile:handset:info", params);
    }

    /**
     * This method will delete the browser cookies for the given url. It will also
     * clear the local storage and the session storage using the javascript executor
     * in the browser window
     *
     * @param url
     */
    public static void clearBrowserCacheAndCookies(String url) {
        DeviceUtils.getQAFDriver().get(url);
        DeviceUtils.getQAFDriver().manage().deleteAllCookies();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JavascriptExecutor js = DeviceUtils.getQAFDriver();
        js.executeScript("window.localStorage.clear();");
        js.executeScript("window.sessionStorage.clear();");
    }

}
