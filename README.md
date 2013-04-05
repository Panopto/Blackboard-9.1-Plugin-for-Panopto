Panopto plugin for Blackboard 9.1 @ Newcastle University
========================================================

What is it?
-----------

This is a block for Blackboard 9.1, that allows courses in Blackboard to link directly to Panopto folders. It also allows for SSO between Blackboard and Panopto, and automatically syncs user permissions between both systems. This version also allows huge numbers of courses and organisations to be provisioned in one go.

Creating the axis 1.6.2 stubs for your server
---------------------------------------------

Panopto are using a really old version of AXIS to do their webservice calls, we decided to do our webservice call additions using a newer version in the hope that Panopto will update their calls at some point, but as you've probably guessed... "yes", it does mean you have to compile with 2 AXIS jars in the war, the way we found to avoid conflict in the code is to include your libraries in the following order...

1. Panopto's axis.jar
2. The axis website's 1.6.2 jars
3. The CourseSearch jar
4. The commons-discovery-0.2 jar
5. Finally the blackboard API jars

...And then call 1.6.2 jars explicitly using their full package, for example:

* Calling Panopto's axis jar: SessionManagementStub
* Calling AXIS 1.6.2: com.panopto.session.SessionManagementStub

This does mean in order for this code to work straight away you'll have to compile up and set your 1.6.2 stub packages as com.panopto.*whatever endpoint*, for example:

* com.panopto.remoterecorder - for remote recorder stubs
* com.panopto.session - for session stubs
* com.panopto.user - for user stubs

However, if you so wish, you can set the package to whatever you like and change the explicit package references in the source, this still means you have to compile your own stubs though, so how do we do that? (NOTE: the stubs provided in this source have had Newcastle University URL's cleaned, so may not work, they are they to show you what you need to end up with)

1. Using the command line go to where you have downloaded and decompressed your AXIS 1.6.2 library
2. Go into the bin directory
3. Issue this command:

<pre>
	wsdl2java.bat -uri https://panoptoserver.example.com/Panopto/PublicAPISSL/4.2/SessionManagement.svc?wsdl -p com.panopto.session
</pre>

4. You will need to change the server url (and optionally the package)
5. You will find the required directories and stub source under src/ in the bin directory where you have called the wsdl2java.bat (or .sh if you're on a *nix variant!)
6. Replace the source for each respective directory in the plugins src/com/panopto/*whatever endpoint directory*
7. Step 6 will need a different directory structure if you changed the package naming convention

Fixing the CourseSearch jar
---------------------------

In order to fix the CourseSearch jar's bug of not allowing you to submit more than 100 courses for provisioning we had to do a couple of things

1. Override the class file in the jar with our own source file in our src/blackboard/webapps/searchwidgets/ directory with the MAX_ROWS set to a much high number (10000)
2. Changed the HTML interface to *POST* the courses/organistations you want provisioned, NOT *GET*.

A slight caveat to this approach is we've found multiple 1000's of provisionings can cause your server to time out, to counter this you can either raise the server's timeout limit (probably not a good idea) or, provision in batches of ~1000 (what we do).

Credits
-------

This was initially built by Panopto and further adjusted by Andrew Martin and Nacer Anou at Newcastle University mainly in order to make provisioning of a large number of courses work, and then the same functionality again was adapted to work for communities (A.K.A. Blackboard "organisations")

Copyright
---------

Copyright Panopto 2009 - 2011, Newcastle University 2012-2013
