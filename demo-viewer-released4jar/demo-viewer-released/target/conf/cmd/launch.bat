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
) ^
else if exist %TOOLS_JRE% (
    set TARGET_TOOLS=%TOOLS_JDK%
) ^
else if exist %TOOLS_ME% (
    set TARGET_TOOLS=%TOOLS_JDK%
)

if "%TARGET_TOOLS%" == "" goto :error2
set MAIN_JAR=.\demo-viewer-1.0.0.jar
if not exist %MAIN_JAR% goto :error3

��%JAVA_HOME%\bin\java.exe�� -Xbootclasspath/a:%TARGET_TOOLS% -jar %MAIN_JAR%

goto :end

:error
wscript .\conf\vbs\error.vbe "%JAVA_HOME%Ϊ�գ������Ƿ�װ������java" "Not Found JAVA"
goto :end

:error2
wscript .\conf\vbs\error.vbe "tools.jar�����ڣ�" "Not Found tools.jar"
goto :end

:error3
wscript .\conf\vbs\error.vbe "����demo-viewer.jar�����ڣ�" "Not Found demo-viewer.jar"
goto :end

:end
