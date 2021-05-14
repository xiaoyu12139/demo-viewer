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

if "%TARGET_TOOLS%" == "" echo "error2"
set MAIN_JAR=.\demo-viewer-1.0.0.jar
if not exist %MAIN_JAR% echo "error3"

“%JAVA_HOME%\bin\java.exe” -Xbootclasspath/a:"%TARGET_TOOLS%" -jar "%MAIN_JAR%"
echo "end"
echo "%TARGET_TOOLS%"
echo -Xbootclasspath/a:%TARGET_TOOLS%
echo %MAIN_JAR%
goto :end

:error
wscript .\conf\vbs\error.vbe "%JAVA_HOME%为空，请检查是否安装并配置java" "Not Found JAVA"
goto :end

:error2
wscript .\conf\vbs\error.vbe "tools.jar不存在！" "Not Found tools.jar"
goto :end

:error3
wscript .\conf\vbs\error.vbe "核心demo-viewer.jar不存在！" "Not Found demo-viewer.jar"
goto :end

:end
