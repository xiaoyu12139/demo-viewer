echo xiaoyu
echo -------------------
@echo off
setlocal
if "%JAVA_HOME%" == "" goto :error
set TOOLS_JDK=%JAVA_HOME%\lib\tools.jar
set TOOLS_JRE=%JAVA_HOME%\jre\lib\tools.jar
set TooLS_ME=.\lib\tools.jar

if exist %TOOLS_JDK% (
    set TARGET_TOOLS=%TOOLS_JDK%
) else if exist %TOOLS_JRE% (
    set TARGET_TOOLS=%TOOLS_JDK%
) else if exist %TOOLS_ME% (
    set TARGET_TOOLS=%TOOLS_JDK%
)