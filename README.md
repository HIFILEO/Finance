# Finance
A financial application that shows different questions to the screen based on JSON data. Screen is 100%
configurable based on ingested JSON data.

For details
[context.docx](https://docs/context.docx) 

Screen Shots
![Alt text](/doc/screen_1.png?raw=true "JSON")

![Alt text](/doc/screen_2.png?raw=true "JSON")

![Alt text](/doc/screen_3.png?raw=true "JSON")

## Usage

Apache License 2.0. Free to use & distribute.

### Build Flavors

Only the original build flavors release / debug. Use Debug since this
not an app for the google play store.

### Tests

This project supports the following type of tests:

1. Java Unit Test
2. Robolectric(There are none - and that was intentional to show how you can test without this when you use MVP)

To run all unit tests:

./gradlew testDebugUnitTest

To run all integration tests:

./gradlew integrationTest '-Pintegration=true'