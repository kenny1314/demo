set OPTS=-Dlogging.level.root=INFO

set OPTS=%OPTS% -Dserver.port=7777

set OPTS=%OPTS% -Dspring.resources.static-locations=classpath:/static/
set OPTS=%OPTS% -Dspring.resources.cache-period=-1
set OPTS=%OPTS% -Dspring.thymeleaf.prefix=classpath:/templates/
set OPTS=%OPTS% -Dspring.thymeleaf.cache=false

set JPDA_SUSPEND=n