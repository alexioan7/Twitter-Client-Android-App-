# Twitter-Client-Android-App-


An android app that uses Twitter API and after the user logs in with Twitter, giving permission for the app to
collect user data (followers, friends, home TimeLine, tweets). 

I also implemented with Chaquopy library an algorithm written in Python based on this https://github.com/narendraj9/digsep developer.
The app calculates the user’s degree of separation with other profiles.
With that data we try to figure out an interest profile or user’s political opinions.

I used Android Architecture Components (LiveData, ViewModel, Room DB), and implemented an MVVM architecture, also Retrofit to make the API calls needed.


In order for your app to work you need to apply for a twitter developer account and aquire consumer key and consumer secret key.

Place in the application your keys on the following files:

   app/src/main/python/digsep

   app/src/main/res/values/strings.xml
   
   java folder on constants class.
   
   
