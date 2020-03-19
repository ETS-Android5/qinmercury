import sys
import os
import platform
import shutil
def PythonLocation():
	return os.path.dirname(os.path.realpath(__file__))
def main():
	#PythonFunction.FuncFunctionList.CleanCache()
	#PythonFunction.FuncFunctionList.RestSetting()
	os.chdir(PythonLocation())
	if os.path.isfile("./UnityPlugin.jar"):
		os.remove("./UnityPlugin.jar")
	if os.path.isfile("./classes.jar"):
		os.remove("./classes.jar")
	if os.path.isfile("./R.txt"):
		os.remove("./R.txt")
	if os.path.isfile("./AndroidManifest.xml"):
		os.remove("./AndroidManifest.xml")
	os.system("gradle clean makejar")
	os.system('pwd')
	if os.path.isfile("./mercury/build/outputs/aar/mercury-release.aar"):
		os.system("unzip ./mercury/build/outputs/aar/mercury-release.aar")
		os.rename("./classes.jar","./MercurySDK.jar")
		if os.path.isfile("./../../00_UnityJar/UnityJarProject/libs/MercurySDK.jar"):
			os.remove("./../../00_UnityJar/UnityJarProject/libs/MercurySDK.jar")
		shutil.copy("./MercurySDK.jar", "./../../00_UnityJar/UnityJarProject/libs/MercurySDK.jar")


if __name__ == '__main__':
    main()
"""
@echo off
if exist game-release.apk (DEL game-release.apk)
call .\gradlew.bat clean assemblerelease
MOVE app\build\outputs\apk\release\app-release.apk game-release.apk
"""
