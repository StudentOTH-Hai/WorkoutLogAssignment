# WorkoutLogAssignment

Welcome to my application for the course 'Android Programming with Kotlin'.

The WorkoutLog App is an application for creating and storing own exercises and workouts to follow. This is achieved by creating different models which include the CRUD-Options. 
The persistence is given by Firebase. So there are two different storing classes (one for workouts and one for exercises) which are getting the reference for the Firebase and is called
in every single operation. 

The application always starts with a SplashscreenActivity which will lead us to the LoginActivity. In the LoginActivity we check if a user is already registrated and logged in. If a user is already 
logged in, he will be directly led to the WorkoutLogActivity which is our MainActivity. 

From there the user has the option to press two different buttons. A 'Show Exercise' button will lead the user to the ExerciseListActivty were he can create, edit and delete his own exercises.
The second option is to click the 'Add Workout' button. This will lead him to the WorkoutListActivty where he can create, edit and delete workouts aswell. When we create a workout we 
are accessing the exercises we have already created to build our own workout. There we can use features like the multi selection of exercises and filtering exercises by using a searchView
to make it easier for the user to create his own workout. 

After creating workouts, a user can use them to achieve his goals of training. :) 




References: 
Firebase and Google Auth:
- https://firebase.google.com/docs/android/setup
- https://firebase.google.com/docs/auth/android/google-signin
- https://firebase.google.com/docs

SplashScreen:

-https://stackoverflow.com/questions/37860612/splash-screen-android

Filtering: 
- https://www.youtube.com/watch?v=sJ-Z9G0SDhc&ab_channel=CodinginFlow
- used as inspiration, but own implementation

Mutliselection:
- https://www.youtube.com/watch?v=r75l8GFYoXc&t=346s&ab_channel=MasterCoding
- used as inspiration, but own implementation
