set TOOLS_JDK=%JAVA_HOME%\lib\tools.jar
set TOOLS_JRE=%JAVA_HOME%\jre\lib\tools.jar
set TooLS_ME=.\lib\tools.jar
if exist %TOOLS_JDK% echo "%TOOLS_JDK% exist!"
if exist %TOOLS_JRE% echo "%TOOLS_JDK% exist!"
if exist %TOOLS_ME% echo "%TOOLS_JDK% exist!"