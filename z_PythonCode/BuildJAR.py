import sys
import os
import platform
import shutil
import time
import subprocess
import zipfile
import xml.dom.minidom
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
		if ("BuildConfig.class" in item.filename) or (".class" not in item.filename):
			pass
		else:
			zout.writestr(item, buffer) #把文件写入到新对象中
	zout.close()
	zin.close()
	print("deleted signature")
	shutil.move(new_zipfile,old_zipfile)

def __move_weixin_plugin():
	package_name = __get_package_name(PythonLocation()+"/mercury/src/main/AndroidManifest.xml")
	path_package_name = package_name.replace(".","/")
	print(PythonLocation()+"/mercury/src/main/java/com/mercury/game/InAppChannel/WXEntryActivity.java")
	if os.path.exists(PythonLocation()+"/mercury/src/main/java/com/mercury/game/InAppChannel/WXEntryActivity.java")==True:
		if os.path.isdir(PythonLocation()+"/mercury/src/main/java/"+path_package_name)==False:
			os.makedirs(PythonLocation()+"/mercury/src/main/java/"+path_package_name)
		shutil.move(PythonLocation()+"/mercury/src/main/java/com/mercury/game/InAppChannel/WXEntryActivity.java",PythonLocation()+"/mercury/src/main/java/"+path_package_name+"/WXEntryActivity.java")
		__change_java_package_name(PythonLocation()+"/mercury/src/main/java/"+path_package_name+"/WXEntryActivity.java",package_name)

	if os.path.exists(PythonLocation()+"/mercury/src/main/java/com/mercury/game/InAppChannel/WXPayEntryActivity.java")==True:
		if os.path.isdir(PythonLocation()+"/mercury/src/main/java/"+path_package_name)==False:
			os.makedirs(PythonLocation()+"/mercury/src/main/java/"+path_package_name)
		shutil.move(PythonLocation()+"/mercury/src/main/java/com/mercury/game/InAppChannel/WXPayEntryActivity.java",PythonLocation()+"/mercury/src/main/java/"+path_package_name+"/WXPayEntryActivity.java")
		__change_java_package_name(PythonLocation()+"/mercury/src/main/java/"+path_package_name+"/WXPayEntryActivity.java",package_name)


def __move_back_weixin_plugin():
	package_name = __get_package_name(PythonLocation()+"/mercury/src/main/AndroidManifest.xml")
	path_package_name = package_name.replace(".","/")

	shutil.move(PythonLocation()+"/mercury/src/main/java/"+path_package_name+"/WXEntryActivity.java", PythonLocation()+"/mercury/src/main/java/com/mercury/game/InAppChannel/WXEntryActivity.java")
	__change_back_java_package_name(PythonLocation()+"/mercury/src/main/java/com/mercury/game/InAppChannel/WXEntryActivity.java",package_name)

	shutil.move(PythonLocation()+"/mercury/src/main/java/"+path_package_name+"/WXPayEntryActivity.java", PythonLocation()+"/mercury/src/main/java/com/mercury/game/InAppChannel/WXPayEntryActivity.java")
	__change_back_java_package_name(PythonLocation()+"/mercury/src/main/java/com/mercury/game/InAppChannel/WXPayEntryActivity.java",package_name)


def __change_java_package_name(_java_path,_package_name):
	with open(_java_path,encoding="utf8") as file_object:
		java_codes = []
		all_the_text = file_object.readlines()
		for line in all_the_text:
			if line.find("package com.mercury.game.InAppChannel;")!=-1:
				java_codes.append("package "+_package_name+";\r")
			else:
				java_codes.append(line)
		with open(f"{_java_path}",mode='w',encoding="utf8") as file_context:
			file_context.writelines(java_codes)


def __change_back_java_package_name(_java_path,_package_name):
	with open(_java_path,encoding="utf8") as file_object:
		java_codes = []
		all_the_text = file_object.readlines()
		for line in all_the_text:
			if line.find("package "+_package_name+";")!=-1:
				java_codes.append("package com.mercury.game.InAppChannel;\r")
			else:
				java_codes.append(line)
		with open(f"{_java_path}",mode='w',encoding="utf8") as file_context:
			file_context.writelines(java_codes)


def __get_package_name(_APK_path):
	dom = xml.dom.minidom.parse(_APK_path)
	root = dom.documentElement
	stringForTem = root.getAttribute("package")
	return stringForTem

def main():
	file_path =  os.path.splitext(__file__.replace("\\","/"))[0][os.path.splitext(__file__.replace("\\","/"))[0].rfind("/")+1:]
	if os.path.isfile(PythonLocation()+"/../../z_PythonCode/"+file_path+".py"):
		if os.path.isfile(PythonLocation()+"/"+file_path+".py"):
			os.remove(PythonLocation()+"/"+file_path+".py")
		shutil.copy(PythonLocation()+"/../../z_PythonCode/"+file_path+".py",PythonLocation()+"/"+file_path+".py")


	_path = PythonLocation()
	__move_weixin_plugin()
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
		if os.path.isfile("./../MercurySDK.jar"):
			os.remove("./../MercurySDK.jar")
		os.rename("./classes.jar","./../MercurySDK.jar")
		__delete_zip_files("./../MercurySDK.jar")
		if os.path.isdir("./../../MercuryAPKProject_pure/app/src/main/libs")==False:os.mkdir("./../../MercuryAPKProject_pure/app/src/main/libs")
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
