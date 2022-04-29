package com.mercury.tour.stepdef;

import io.cucumber.java.After;

public class CloseHook {
    public static boolean shouldClose = false;

    @After("@close")
    public void closeWebDriver() {
        shouldClose = true;
    }
}
