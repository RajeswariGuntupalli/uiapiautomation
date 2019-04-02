# uiapiautomation

# config.properties:

config.properties file contains all the environment related config like url, browser type, headless/non headless mode

This environment config can be overridden by bamboo /system variables /maven variables. Automation supports this capability to override the config provided in confg.properites

# Execution : 

Can change/override config by providing system properties (runtime properties)as sif required if running from bamboo/any other CI.

Run as Maven tests

Or can run directly from feature file or can filter tags @UIRegressionPack | @APIRegressionPack to run specif tests for UI/API

for UI : @UIRegressionPack
for API : @APIRegressionPack

#Report :

Report/Failed Screenshots will be  placed by default under \target\cucumber-reports folder


#folder structure:

under test/resources, feature contains all feature files and stepdefinitions contains all step definition implementations.

under main/java, pageobjects, hooks, utilities
 to handle rest service, constants, pageobject interactions.
 
#test data

For each feature file test data is maintained in separate spreadsheet under specific folders under resource path.

Same code works for different test data combinations as it is keyword and data driven.


#Supported browser

Used latest Selenium and related drivers, so latest browsers are required to run UI tests.

Firefox : 66.0.2 (64-bit)
Chrome : 73.0.3683.86 (Official Build) (64-bit)
IE : 11.0
 

