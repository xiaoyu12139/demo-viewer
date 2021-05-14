@echo off 
if "%1" == "h" goto begin 
mshta vbscript:createobject("wscript.shell").run("%~nx0  h %2 %3",0)(window.close)&&exit 
:begin

echo xiaoyu
echo -------------------
setlocal
if "%JAVA_HOME%" == "" goto :error
REM 扫描指定路径下的java文件，编译到target目录
set current=%2
for %%i in (%current%\..) do set parent=%%~fi
if exist %parent%\target (
   echo "target exist"
) else (
md %parent%\target
) 
REM 编译java文件到target目录
for /r %current% %%i in (*.java) do ( "%JAVA_HOME%\bin\javac.exe" -d %parent%\target %%i )
REM 传入选定的main类的全路径如：com.xiaoyu.demo
set MAIN_CLASS=%3
set CLASSPATH=%parent%\target
"%JAVA_HOME%\bin\java.exe" -classpath "%CLASSPATH%" "example.MainPanel"

goto :end

:error
echo ERROR: JAVA_HOME not found in your environment.
echo Please, set the JAVA_HOME variable in your environment to match the
echo location of the Java Virtual Machine you want to use.

:end

