@echo off 
echo xiaoyu
echo -------------------
setlocal
if "%JAVA_HOME%" == "" goto :error
REM ɨ��ָ��·���µ�java�ļ������뵽targetĿ¼
set current=%1
for %%i in (%current%\..) do set parent=%%~fi
if exist %parent%\target (
   echo "target exist"
) else (
md %parent%\target
) 
REM ����java�ļ���targetĿ¼
for /r %current% %%i in (*.java) do ( "%JAVA_HOME%\bin\javac.exe" -d %parent%\target %%i )
REM ����ѡ����main���ȫ·���磺com.xiaoyu.demo
set MAIN_CLASS=%2
set CLASSPATH=%parent%\target
"%JAVA_HOME%\bin\java.exe" -classpath "%CLASSPATH%" "%MAIN_CLASS%"

setlocal
goto :end

:error
echo ERROR: JAVA_HOME not found in your environment.
echo Please, set the JAVA_HOME variable in your environment to match the
echo location of the Java Virtual Machine you want to use.

:end
