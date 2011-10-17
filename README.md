# A Music Library for Database.com and Heroku

This small sample application is a music library with an album, artist and genre entity.
It derives from a tutorial build for Java SDK. This adds support for Heroku, oAuth on Database.com.

For more information on how to use the Java SDK, please read our documentation at http://forcedotcom.github.com/java-sdk/force-sdk-overview

# Pre-requisites:
install git

install Heroku 

look at this workbook for help:

http://www.salesforce.com/us/developer/docs/workbook_java_heroku/index.htm

# Create a database.com account from browser:
create a database.com account:

for simplicity add IP range addresses 0.0.0.0 to 255.255.255.255 (to avoid token copy and paste)

# Clone the Maven project from terminal:
clone existing github project:

git clone git@github.com:sebastianocostanzo/JavaAndDatabase.com.git

cd JavaAndDatabase.com

# Create Heroku stack from terminal:
heroku create --stack cedar

Output :

Creating young-sword-6889... done, stack is cedar

http://young-sword-6889.herokuapp.com/ | git@heroku.com:young-sword-6889.git

Git remote heroku added

# Take note of the app's name on heroku : something.herokuApp.com
# Push code to heroku from command line:
git push heroku master

# Setup Database.com for oAuth:
Go back to database.com org in browser:

create remote access on database.com

Application: javaheroku (it's just a name)

Callback url: https://something.herokuapp.com/_auth (the host is different)

Save and click on click to reveal link

Copy the consumer key: consumer_key (long string)

Copy the consumer secret: consumer_secret (shorter number)

# Setup SSL on heroku app:
add the SSL add-on to your heroku app (needed for oauth) from command line:

heroku addons:add ssl:piggyback

# add the following heroku environment variables from the command line (replace the values copied above consumer_key; consumer_secret) :

heroku config:add FORCE_FORCEDATABASE_URL="force://login.database.com?oauth_key=consumer_key&oauth_secret=consumer_secret"

heroku config:add CONNECTION_URL="force://login.salesforce.com?user=<username>&password=<password>" (this is needed to get object created via JPA)
	
# start Heroku app from command line
heroku open

from the browser login with user credentials (don't need to be the same used in setup above) and click allow


Please read more documentation at http://forcedotcom.github.com/java-sdk/force-sdk-overview


## Content Attribution

<img alt="Freebase CC-BY" height="23px" style="float: left; border: 0;" width="61px" src="http://www.freebase.com/policies/freebase-cc-by-61x23.png"/>

The sampledata directory contains album, artist and genre data from [Freebase.com](http://www.freebase.com), licensed under [CC-BY](http://creativecommons.org/licenses/by/2.5/). This content includes parts from [Wikipedia](http://en.wikipedia.org/), licensed under the [GFDL](http://www.gnu.org/copyleft/fdl.html).

