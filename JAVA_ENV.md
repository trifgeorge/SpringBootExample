
# Add Java/Maven to system environments.
I recommend downloading the java binary files and not the installer

1. Go to Windows search bar.
2. Search for "System Environments" and open it.
3. Click on "Environment Variables".
4. In the System variables segment, click on "New".
5. Add `JAVA_HOME` as the variable name and `%PATH_TO_JAVA%\corretto-21.0.2` as the variable value.
6. Do the same for `MAVEN_HOME` (variable name) and `%PATH_TO_MAVEN%\apache-maven-3.9.6` (variable value).
7. Search for the `PATH` variable (System variables segment) and edit it.
8. Add 2 new values: `%JAVA_HOME%\bin` and `%MAVEN_HOME%\bin`.
9. Save and close all windows.
10. Open cmd and run `java -version` and `mvn -v`. You should see both Java and Maven versions.