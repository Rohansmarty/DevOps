@ECHO OFF
SETLOCAL

where mvn >NUL 2>&1
IF %ERRORLEVEL% EQU 0 (
  mvn %*
  EXIT /B %ERRORLEVEL%
)

ECHO mvn is not installed and this repository does not include the full Maven Wrapper scripts. 1>&2
ECHO Install Maven ^(mvn^) or add the full Maven Wrapper ^(mvnw + mvnw.cmd + .mvn\wrapper\maven-wrapper.jar^). 1>&2
EXIT /B 1
