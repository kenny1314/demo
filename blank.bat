@echo on
rem ---------------------------------------------------------------------------
rem Start Script for the demo Project
rem
rem Environment Variable Prerequisites
rem
rem   Do not set the variables in this script. Instead put them into a script
rem   .setenv.bat in BL_HOME to keep your customizations separate.
rem
rem
rem   BL_HOME         May point at your "build" directory.
rem
rem   BL_TMPDIR       (Optional) Directory path location of temporary directory
rem                   the JVM should use (java.io.tmpdir). Defaults to
rem                   %BL_HOME%\.tmp.
rem
rem   JAVA_HOME       Must point at your Java Development Kit installation.
rem                   Required to run the with the "debug" argument.
rem
rem   JRE_HOME        Must point at your Java Runtime installation.
rem                   Defaults to JAVA_HOME if empty. If JRE_HOME and JAVA_HOME
rem                   are both set, JRE_HOME is used.
rem
rem   JPDA_TRANSPORT  (Optional) JPDA transport used when the "jpda"
rem                   command is executed. The default is "dt_socket".
rem
rem   JPDA_ADDRESS    (Optional) Java runtime options used when the "jpda"
rem                   command is executed. The default is localhost:8000.
rem
rem   JPDA_SUSPEND    (Optional) Java runtime options used when the "jpda"
rem                   command is executed. Specifies whether JVM should suspend
rem                   execution immediately after startup. Default is "n".
rem
rem   JPDA_OPTS       (Optional) Java runtime options used when the "jpda"
rem                   command is executed. If used, JPDA_TRANSPORT, JPDA_ADDRESS,
rem                   and JPDA_SUSPEND are ignored. Thus, all required jpda
rem                   options MUST be specified. The default is:
rem
rem                   -agentlib:jdwp=transport=%JPDA_TRANSPORT%,
rem                       address=%JPDA_ADDRESS%,server=y,suspend=%JPDA_SUSPEND%
rem
rem   TITLE           (Optional) Specify the title of window. The default
rem                   TITLE is FitContest if it's not specified.
rem                   Example (all one line)
rem                   set TITLE=FitContest [%DATE% %TIME%]
rem ---------------------------------------------------------------------------

if "%OS%" == "Windows_NT" setlocal

set "CURRENT_DIR=%cd%"

if not "%BL_HOME%" == "" goto okHome
set "BL_HOME=%CURRENT_DIR%"
:okHome

rem Get standard environment variables
if not exist "%BL_HOME%\.setenv.bat" goto setenvDone
call "%BL_HOME%\.setenv.bat"
:setenvDone

rem Make sure prerequisite environment variables are set
if not "%JAVA_HOME%" == "" goto gotJdkHome
if not "%JRE_HOME%" == "" goto gotJreHome
echo Neither the JAVA_HOME nor the JRE_HOME environment variable is defined
echo At least one of these environment variable is needed to run this program
goto end

:gotJreHome
if not exist "%JRE_HOME%\bin\java.exe" goto noJavaHome
if not exist "%JRE_HOME%\bin\javaw.exe" goto noJavaHome
if not ""%1"" == ""debug"" goto okJavaHome
echo JAVA_HOME should point to a JDK in order to run in debug mode.
goto end

:gotJdkHome
if not exist "%JAVA_HOME%\bin\java.exe" goto noJavaHome
if not exist "%JAVA_HOME%\bin\javaw.exe" goto noJavaHome
if not exist "%JAVA_HOME%\bin\jdb.exe" goto noJavaHome
if not exist "%JAVA_HOME%\bin\javac.exe" goto noJavaHome
if not "%JRE_HOME%" == "" goto okJavaHome
set "JRE_HOME=%JAVA_HOME%"
goto okJavaHome

:noJavaHome
echo The JAVA_HOME environment variable is not defined correctly
echo This environment variable is needed to run this program
echo NB: JAVA_HOME should point to a JDK not a JRE
goto end
:okJavaHome

rem Don't override _RUNJAVA if the user has set it previously
if not "%_RUNJAVA%" == "" goto gotRunJava
rem Set standard command for invoking Java.
rem Also note the quoting as JRE_HOME may contain spaces.
set _RUNJAVA="%JRE_HOME%\bin\java.exe"
:gotRunJava

if "%CLASSPATH%" == "" goto emptyClasspath
set "CLASSPATH=%CLASSPATH%;"
:emptyClasspath

rem Set classpath
set "CLASSPATH=%CLASSPATH%;%BL_HOME%\.classes"
set "CLASSPATH=%CLASSPATH%;%BL_HOME%\src\main\resources"

:checkMaven
if not exist "%BL_HOME%\target\demo.jar" goto checkGradle
set "BL_JAR=%BL_HOME%\target\demo.jar"
goto gotJar

:checkGradle
if not exist "%BL_HOME%\build\libs\demo.jar" goto checkError
set "BL_JAR=%BL_HOME%\build\libs\demo.jar"
goto gotJar

:checkError

echo Application jar not found!

goto end

:gotJar
set "CLASSPATH=%CLASSPATH%;%BL_JAR%"

if not "%BL_TMPDIR%" == "" goto gotTmpdir
set "BL_TMPDIR=%BL_HOME%\.tmp"
if exist "%BL_TMPDIR%" goto gotTmpdir
mkdir "%BL_TMPDIR%"
:gotTmpdir

echo Using BL_HOME:         "%BL_HOME%"
echo Using BL_TMPDIR:       "%BL_TMPDIR%"

if ""%1"" == ""jpda"" goto use_jdk
echo Using JRE_HOME:        "%JRE_HOME%"
goto java_dir_displayed
:use_jdk
echo Using JAVA_HOME:       "%JAVA_HOME%"
:java_dir_displayed
echo Using CLASSPATH:       "%CLASSPATH%"

if not "%OS%" == "Windows_NT" goto noTitle
if "%TITLE%" == "" set TITLE=demo Project
set _EXECJAVA=start "%TITLE%" %_RUNJAVA%
goto gotTitle
:noTitle
set _EXECJAVA=start %_RUNJAVA%
:gotTitle

if not ""%1"" == ""jpda"" goto noJpda
set JPDA=jpda
if not "%JPDA_TRANSPORT%" == "" goto gotJpdaTransport
set JPDA_TRANSPORT=dt_socket
:gotJpdaTransport
if not "%JPDA_ADDRESS%" == "" goto gotJpdaAddress
set JPDA_ADDRESS=8000
:gotJpdaAddress
if not "%JPDA_SUSPEND%" == "" goto gotJpdaSuspend
set JPDA_SUSPEND=n
:gotJpdaSuspend
if not "%JPDA_OPTS%" == "" goto gotJpdaOpts
set JPDA_OPTS=-agentlib:jdwp=transport=%JPDA_TRANSPORT%,address=%JPDA_ADDRESS%,server=y,suspend=%JPDA_SUSPEND%
:gotJpdaOpts
:noJpda

set MAINCLASS=org.springframework.boot.loader.JarLauncher

if not "%JPDA%" == "" goto doJpda
%_EXECJAVA% -classpath %CLASSPATH% %OPTS% -Djava.io.tmpdir=%BL_TMPDIR% %MAINCLASS%
goto end
:doJpda
%_EXECJAVA% -classpath %CLASSPATH% %OPTS% -Djava.io.tmpdir=%BL_TMPDIR% %JPDA_OPTS% %MAINCLASS%
:end
