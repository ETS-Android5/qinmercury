import sys
import os
import platform

def PythonLocation():
	return os.path.dirname(os.path.realpath(__file__))
def main():
	#PythonFunction.FuncFunctionList.CleanCache()
	#PythonFunction.FuncFunctionList.RestSetting()
	os.chdir(PythonLocation())
	os.system("python3 ./MercuryJarProject/BuildJAR.py")
	os.system("mv ./MercuryJarProject/UnityPlugin.jar ./MercuryAPKProject/app/src/main/libs/UnityPlugin.jar")



if __name__ == '__main__':
    main()
"""
@echo off
if exist game-release.apk (DEL game-release.apk)
call .\gradlew.bat clean assemblerelease
MOVE app\build\outputs\apk\release\app-release.apk game-release.apk
"""
