# TrackSportTeam

![Snapshot](https://i.imgur.com/3OAxnAVl.png) ![Snapshot](https://i.imgur.com/7hLM9Iml.png)
![Snapshot](https://i.imgur.com/6zwMraRl.png) ![Snapshot](https://i.imgur.com/ertynhGl.png)

# About Bonus Objectives:
- Monetization strategies:
  - I have implemented AdMob to show interstitial ads. After user finds the team with searching feature and clicks on sport team to display event history, interstitial ad is shown. In order to not bore users with many ads, i show ads after every 3 clicks on sport team.
  
  - I have implemented a premium feature idea which intended to allow users to add the team to their favorites. When user clicks to favorite button on team list, there will be an alert dialog: " Add the team to your favorites to receive live updates about team's events. This is a premium feature. By activating premium membership, you can enjoy premium features with no ads! ". I did not implement Google Play Billing, when user clicks on "ACTIVATE PREMIUM", there will be a toast message which says: "This feature is not implemented yet"
  
  - Other monetization strategies can be like:
    - 1 month free premium membership to demonstrate our beautiful premium features to users(When we are positive that they will love it and then buy :) ).
    - Limiting daily API calls to non-premium users.
  
- Imagine your application is getting popular and you want to add some sort of tracking and analytics to determine how your users are using your app. How would you approach this?
  - In order to analyze user behavior i use Firebase Analytics. Using analytics data i; 
    - Determine most used features.
    - Figure out where to focus.
    - Determine users location to prioritize native language support.
    - Determine unused features and focus on how to improve them etc...
    
  - Also, user's feedback on GooglePlay store always helps.

# Used components/libraries/design patterns:

- 100% Kotlin
- Kotlin Coroutines(instead of RxJava and Java threads)
- Kotlin delegates
- Google AdMob
- Git workflow
- Dagger2
- MVVM with Repository Pattern
- View binding
- Navigation Component with Safe Args
- Livedata
- Lifecycle-Aware Custom DialogUtils
- Retrofit
- Picasso
- OkHttp logging interceptor
- CardView
- Timber
- SharedPref
- Recyclerview
- JUnit
- Mockito
- Espresso

# Some notes:

- Configuration changes are handled gracefully. If we rotate the device when:
  - Recyclerview has data, there will not be any API call. Data will be simply returned from viewmodel.
  - Activate premium membership alert dialog is on the screen, it's state will be recovered in the new screen.
- Http logging are enabled only in debug mode.
- I use "Region - endRegion" to make code cleaner.
- Almost all libraries/classes are injected by Dagger to classes.
- Timber is configured with custom TimberLineNumberDebugTree which provides clickable logs to navigate developer to the point where log was generated.
- I tried to document all classes and necessary methods with KDoc.
- If GET request fails, there will be an error message with a try again button.

# Tests:
- Since testing was not in requirements, to set examples; i implemented one instrumented test with Espresso and one unit test with JUnit and Mockito.

# Tasks before publishing the app:
- Enable ProGuard.
- Run full lint inspection(should be done before every commit).
- Run static code analysis.
- Implement crashlytic.
- Use firebase test lab.
- Add multi-language support.

