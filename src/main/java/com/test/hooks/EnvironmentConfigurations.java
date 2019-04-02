package com.test.hooks;

/**
 * Created by RajeswariG on 17/07/2018.
 */
public class EnvironmentConfigurations {
    private String applicationUIUrl;
    private String apiUrl;
    private String apiKey;
    private String executionBrowser;
    private boolean headlessMode;
    private String screenshotPath;

    public String getApplicationUIUrl() {
        return applicationUIUrl;
    }

    public void setApplicationUIUrl(String applicationUIUrl) {
        this.applicationUIUrl = applicationUIUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getExecutionBrowser() {
        return executionBrowser;
    }

    public void setExecutionBrowser(String executionBrowser) {
        this.executionBrowser = executionBrowser;
    }

    public boolean isHeadlessMode() {
        return headlessMode;
    }

    public void setHeadlessMode(boolean headlessMode) {
        this.headlessMode = headlessMode;
    }

    public String getScreenshotPath() {
        return screenshotPath;
    }

    public void setScreenshotPath(String screenshotPath) {
        this.screenshotPath = screenshotPath;
    }
}
