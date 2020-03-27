import sys
import os
import platform
import shutil
def PythonLocation():
	return os.path.dirname(os.path.realpath(__file__))

def delete_folder(src):
	'''delete files and folders'''
	if os.path.isfile(src):
		try:
			os.remove(src)
		except:
			pass
	elif os.path.isdir(src):
		for item in os.listdir(src):
			itemsrc=os.path.join(src,item)
			delete_folder(itemsrc) 
		try:
			os.rmdir(src)
		except:
			pass

def main():
	#PythonFunction.FuncFunctionList.CleanCache()
	#PythonFunction.FuncFunctionList.RestSetting()
	os.chdir(PythonLocation())
	os.system("gradle clean makejar")
	os.system('pwd')
	if os.path.exists("./cache"):delete_folder("./cache")
	os.system("mkdir cache")
	os.chdir(PythonLocation()+"/cache")
	if os.path.isfile("./../mercury/build/outputs/aar/mercury-release.aar"):
		os.system("unzip ./../mercury/build/outputs/aar/mercury-release.aar")
		os.rename("./classes.jar","./../MercurySDK.jar")
		if os.path.isfile("./../../../Plugin_UnityJar/UnityJarProject/libs/MercurySDK.jar"):
			os.remove("./../../../Plugin_UnityJar/UnityJarProject/libs/MercurySDK.jar")
		shutil.copy("./../MercurySDK.jar", "./../../../Plugin_UnityJar/UnityJarProject/libs/MercurySDK.jar")
	if os.path.exists("./../cache"):delete_folder("./../cache")


if __name__ == '__main__':
    main()
"""
@echo off
if exist game-release.apk (DEL game-release.apk)
call .\gradlew.bat clean assemblerelease
MOVE app\build\outputs\apk\release\app-release.apk game-release.apk
"""
