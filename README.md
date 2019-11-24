# CS541-proj6
Assignment 6 - myWeatherApp

In this assignment, i have designed a simple app that serves two purposes. One being able to detect the current status of the phone i.e get the current co-ordinates of the phone followed by the altitude and how accurate the information is, as well as the address of the phone. For the second part, the user can input any city (standardized name) and the API will fetch related details such as the forecast, temperature, humidity, pressure. For implementing this i have made use of the OpenWeatherAPI.

Following is the summary of the app development:

Day 1: Basic UI development.

Day 2: Working on getting current status of the phone by implementing locationmanager and handled permissions requests.

Day 3: Worked on "startListening()" when the permissions are granted.

Day 4: Getting data and updating the textviews followed by formatting of the parameters. Used Geocoder for getting the data and parsing it.

Day 5: Started working on the weather element of the app. Made use of input stream and asynctask for fetching data via API.

Day 6: Tried basic sample city (London) to check whether the app works. JSON parsing was implemented for seperating the data coming from the API.

Day 7: Added the ability to input any country/city via edittext and parsing additional details as per API standards.

Day 8: Fixed a bug with parsing (object -> array) and additional changes to the UI.
