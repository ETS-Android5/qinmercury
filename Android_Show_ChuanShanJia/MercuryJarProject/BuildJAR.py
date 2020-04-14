import sys
import os
import platform
import shutil
import time
import subprocess
import zipfile
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
def __delete_zip_files(_path):
	your_delet_file="qinmercury"
	old_zipfile=_path #旧文件
	new_zipfile=_path+"temp" #新文件
	zin = zipfile.ZipFile (old_zipfile, 'r') #读取对象
	zout = zipfile.ZipFile (new_zipfile, 'w') #被写入对象
	for item in zin.infolist():
		buffer = zin.read(item.filename)
		if ("BuildConfig.class" in item.filename):
			pass
		else:
			zout.writestr(item, buffer) #把文件写入到新对象中
	zout.close()
	zin.close()
	print("deleted signature")
	shutil.move(new_zipfile,old_zipfile)
def main():
	file_path =  os.path.splitext(__file__)[0][os.path.splitext(__file__)[0].rfind("/")+1:]
	if os.path.isfile(PythonLocation()+"/"+file_path+".py"):
		os.remove(PythonLocation()+"/"+file_path+".py")
	if os.path.isfile(PythonLocation()+"/../../z_PythonCode/"+file_path+".py"):
		shutil.copy(PythonLocation()+"/../../z_PythonCode/"+file_path+".py",PythonLocation()+"/"+file_path+".py")

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
		__delete_zip_files("./../MercurySDK.jar")
		if os.path.isfile("./../../../Unity_UnityPlugin/UnityJarProject/mercury/src/main/libs/MercurySDK.jar"):
			os.remove("./../../../Unity_UnityPlugin/UnityJarProject/mercury/src/main/libs/MercurySDK.jar")
		shutil.copy("./../MercurySDK.jar", "./../../../Unity_UnityPlugin/UnityJarProject/mercury/src/main/libs/MercurySDK.jar")
		shutil.copy("./../MercurySDK.jar", "./../../../Unity_DemoProject/Assets/Plugins/Android/libs/MercurySDK.jar")
		shutil.copy("./../MercurySDK.jar", "./../../MercuryAPKProject_pure/app/src/main/libs/MercurySDK.jar")
	if os.path.exists("./../cache"):delete_folder("./../cache")


if __name__ == '__main__':
    main()
"""
@echo off
if exist game-release.apk (DEL game-release.apk)
call .\gradlew.bat clean assemblerelease
MOVE app\build\outputs\apk\release\app-release.apk game-release.apk
"""
