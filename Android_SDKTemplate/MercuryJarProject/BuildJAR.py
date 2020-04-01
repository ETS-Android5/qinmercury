import sys
import os
import platform
import shutil
import time
import subprocess
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
	_path = PythonLocation()
	os.chdir(_path)
	if os.path.exists("./cache"):delete_folder("./cache")
	os.system("mkdir cache")
	os.system('pwd')
	while os.path.exists(_path+"/../cache")==True:
		print("waiting cache")
		os.system('pwd')
	os.system("gradle clean makejar")
	# p = subprocess.Popen("gradle clean makejar", stdout=subprocess.PIPE, shell=True)
	# p.wait()
	os.chdir(_path+"/cache")
	if os.path.isfile("./../mercury/build/outputs/aar/mercury-release.aar"):
		os.system("unzip ./../mercury/build/outputs/aar/mercury-release.aar")
		os.rename("./classes.jar","./../MercurySDK.jar")
		if os.path.isfile("./../../../Unity_UnityPlugin/UnityJarProject/mercury/src/main/libs/MercurySDK.jar"):
			os.remove("./../../../Unity_UnityPlugin/UnityJarProject/mercury/src/main/libs/MercurySDK.jar")
		shutil.copy("./../MercurySDK.jar", "./../../../Unity_UnityPlugin/UnityJarProject/mercury/src/main/libs/MercurySDK.jar")
		shutil.copy("./../MercurySDK.jar", "./../../../Unity_DemoProject/Assets/Plugins/Android/libs/MercurySDK.jar")
	if os.path.exists("./../cache"):delete_folder("./../cache")


if __name__ == '__main__':
    main()
"""
@echo off
if exist game-release.apk (DEL game-release.apk)
call .\gradlew.bat clean assemblerelease
MOVE app\build\outputs\apk\release\app-release.apk game-release.apk
"""
