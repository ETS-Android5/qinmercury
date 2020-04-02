import sys
import os
import platform

def PythonLocation():
	return os.path.dirname(os.path.realpath(__file__))
def main():
	#PythonFunction.FuncFunctionList.CleanCache()
	#PythonFunction.FuncFunctionList.RestSetting()
	os.chdir(PythonLocation())
	if os.path.isfile("./UnityPlugin.jar"):
		os.remove("./UnityPlugin.jar")
	os.system("python3 ./UnityJarProject/BuildJAR.py")
	os.system("mv ./UnityJarProject/UnityPlugin.jar ./../Unity_DemoProject/Assets/Plugins/Android/libs/UnityPlugin.jar")
	# os.system("mv ./UnityJarProject/UnityPlugin.jar ./UnityPlugin.jar")



if __name__ == '__main__':
    main()
"""
@echo off
if exist game-release.apk (DEL game-release.apk)
call .\gradlew.bat clean assemblerelease
MOVE app\build\outputs\apk\release\app-release.apk game-release.apk
"""
