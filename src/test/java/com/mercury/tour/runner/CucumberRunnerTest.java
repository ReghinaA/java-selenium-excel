package com.mercury.tour.runner;

import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/")
@ConfigurationParameters(
        {
                @ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.mercury.tour"),
                @ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"),
        }
)
public class CucumberRunnerTest {
}
