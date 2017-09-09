# JobIntentService as Foreground Service

This is demo app showcasing the bug in JobIntentService Supportlib 26.0.2.

This demo app starts the Foreground Service when the app goes to background and stops the Foreground Service when the app is visible to the user.

Run the app and observe the behavior.
* Foreground Service is started as soon as App goes to the background for the first time.
* When user launches app it does not stop the foreground service.
* This is because JobIntentService stop dequeuing work.

JobIntentService is enqueuing work for subsequent background & foreground transition but none of the work is dequeued and handled ( refer adb log).

Chaning AppStateService to extends JobIntentServiceWitFix fix the issue.

##### Proposed Fix:
In ```processorFinished()```  when async task is done it needs to be set to null, so it can be recreated to execute dequeue of next work.
More details in  JobIntentServiceWitFix line numbers 617 and 621.

