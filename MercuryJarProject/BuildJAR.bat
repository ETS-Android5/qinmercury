@echo off
if exist UnityPlugin.jar (DEL UnityPlugin.jar)
call .\gradlew.bat clean makejar
MOVE east2west\build\outputs\jar\UnityPlugin.jar UnityPlugin.jar