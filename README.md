# foregroundserviceflutter

Create a service for playing music by giving the possibility to the user to pause or launch it.

## Getting Started


It is a service that must not die even in the event of a memory shortage.
That is to say, it is considered by the system as ultra important for
the user. In this case, it is mandatory to associate a notification to the
service.

• It is good practice that as soon as a service is performed, even for
services that are not foreground, we give the user the possibility
to stop it itself by displaying a notification (when the activity is no longer
visible).