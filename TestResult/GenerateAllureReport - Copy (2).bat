cd %ProjectPath%
echo %ProjectPath%
set p=%PATH%
java -javaagent:"%ProjectPath%\Libraries\Allure Report 2.13.5\aspectjweaver-1.8.10.jar" -classpath "%ProjectPath%Libraries;%ProjectPath%Allure Report\Allure Report 2.13.5\*;%ProjectPath%Log4J\*;%ProjectPath%bin\RunTestcase2_allure_report.xml"
pause