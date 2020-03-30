# encoding=utf8 
import sys, os, platform
import os
import time
import shutil
import chardet
import sys,os
import xml.dom.minidom
import zipfile
import glob
import json
import importlib
import datetime
import FuncXMLOperationClass
import os
import random
import threading
from os.path import join, getsize
# -*- coding: utf-8 -*- 
import urllib.request
import random
import json
import xml.dom.minidom
import unicodedata
import os
from pbxproj import XcodeProject
from pbxproj.pbxextensions.ProjectFiles import FileOptions
path=os.path.abspath(os.path.join(os.path.dirname(os.path.realpath(__file__)),"../.."))#返回上上级地址
sys.path.append(path+"//ToolEngine//")#添加到环境变量
import FileOperationTool,PathTool
importlib.reload(sys)
CallMethodPath=""
copyFileCounts = 0
isTestBuild = False
GameConfiguration = ""
GameVersionName = ""
GameVersionCode = ""
NewName = ""
isMulitDex =False
isFastBuild =False
MultiThread =False
DemoProjectAPKName = "app-release"
PythonVersion = ""
def GetPythonCommand():
	global PythonVersion
	if PythonVersion!="":
		return PythonVersion
	version1 = os.popen("python3 --version")
	version2 = os.popen("python.exe --version")
	version3 = os.popen("python --version")
	# print("Version:"+version1.read())
	# print("show:"+version2.read())
	# print("show:"+version3.read())
	if version1.read()!="":
		PythonVersion="python3"
	if version2.read()!="":
		PythonVersion="python.exe"
	if version3.read()!="":
		PythonVersion="python"
	print("Your are using python command:"+PythonVersion)
	return PythonVersion
def UsePlatform(arg):
	def UsePlatformfunc(FunctionName):
		def UsePlatformfunc_in(*args,**kwargs):
			sysstr = platform.system()
			if(sysstr =="Windows"):
				if arg=="Windows":
					return FunctionName(*args,**kwargs)
				else:
					print("This is not your requied Platform")
					return False
			elif(sysstr == "Linux"):
				if arg=="Linux":
					return FunctionName(*args,**kwargs)
				else:
					print("This is not your requied Platform")
					return False
			else:
				print("This is not your requied Platform")
				return False
			return FunctionName(*args,**kwargs)
		return UsePlatformfunc_in
	return UsePlatformfunc
def RegistDragPromission(arg):
	def RegistDragPromissionfunc(FunctionName):
		def RegistDragPromissionfunc_in(*args,**kwargs):
			if "DragPromission" == arg:
				#print("Enter:"+PythonLocation())
				#print(CallMethodPath)
				if(os.path.isfile(PythonLocation()+"/PythonRegedit.reg")==False):
					print("Can't find "+PythonLocation()+"/PythonRegedit.reg")
					return False
				if os.path.isfile(PythonLocation()+"/__pycache__/PythonRegedit.reg") == False:
					os.system(PythonLocation()+"/"+"PythonRegedit.reg")
					shutil.copy(PythonLocation()+"/PythonRegedit.reg",PythonLocation()+"/__pycache__/PythonRegedit.reg")
					os.chdir(CallMethodPath)
					return FunctionName(*args,**kwargs)
				else:
					os.chdir(CallMethodPath)
					return FunctionName(*args,**kwargs)
			else:
				return False
		return RegistDragPromissionfunc_in
	return RegistDragPromissionfunc
def CallMethodLocation(arg):
	def CallMethodLocationfunc(FunctionName):
		def CallMethodLocationfunc_in(*args,**kwargs):
			global CallMethodPath
			CallMethodPath = os.path.abspath('.')
			ret = FunctionName(*args,**kwargs)
			return ret
		return CallMethodLocationfunc_in
	return CallMethodLocationfunc
def CheckIfHaveSignature(_CheckLocation):
	APKname = _CheckLocation.split("/")[-1]
	PointCount = APKname.rfind(".")
	foldername=APKname[:PointCount]
	os.chdir(r''+PythonLocation()+"/__pycache__")
	if os.path.exists(os.path.abspath('.')+"/assets"):
		DeleteFolder(os.path.abspath('.')+"/assets")
	if os.path.exists(os.path.abspath('.')+"/META-INF"):
		DeleteFolder(os.path.abspath('.')+"/META-INF")
	if os.path.exists(os.path.abspath('.')+"/res"):
		DeleteFolder(os.path.abspath('.')+"/res")
	if os.path.exists(os.path.abspath('.')+"/AndroidManifest.xml"):
		DeleteFolder(os.path.abspath('.')+"/AndroidManifest.xml")
	if os.path.exists(os.path.abspath('.')+"/classes.dex"):
		DeleteFolder(os.path.abspath('.')+"/classes.dex")
	if os.path.exists(os.path.abspath('.')+"/resources.arsc"):
		DeleteFolder(os.path.abspath('.')+"/resources.arsc")
	if os.path.exists(os.path.abspath('.')+"/copy.apk"):
		DeleteFolder(os.path.abspath('.')+"/copy.apk")
	if os.path.exists(os.path.abspath('.')+"/copy_signed.apk"):
		DeleteFolder(os.path.abspath('.')+"/copy_signed.apk")
	if os.path.exists(os.path.abspath('.')+"/"+foldername):
		DeleteFolder(os.path.abspath('.')+"/"+foldername)
	#print("jar xf "+_CheckLocation)
	os.system("jar xf "+_CheckLocation)
	if os.path.exists(os.path.abspath('.')+"/META-INF"):
		#print("[Python Notice] Please delete android signature and try again(delete /META-INF folder)")
		return False
	os.chdir(r''+CallMethodPath)
	return True
def MoveWorkSpace():
	if(os.path.isfile(PythonLocation()+"/../01_MercurySDKPlugin/workspace.xml")) and os.path.exists(PythonLocation()+"/../01_MercurySDKPlugin/.idea"):
		shutil.copy(PythonLocation()+"/../01_MercurySDKPlugin/workspace.xml",PythonLocation()+"/../01_MercurySDKPlugin/.idea/workspace.xml")
	#if(os.path.isfile(PythonLocation()+"/../01_MercurySDKPlugin/workspace.xml")):
	#	shutil.copy(PythonLocation()+"/../01_MercurySDKPlugin/workspace.xml",PythonLocation()+"/../01_MercurySDKPlugin/workspace.xml")
def CleanCache():
	MoveWorkSpace()
	MyList = FindAllInFolder(os.path.dirname(os.path.realpath(__file__))+"/__pycache__")
	for i in MyList:
		if ".pyc" in i:
			continue
		if "PythonRegedit.reg" in i:
			continue
		if "." not in i :
			print("[Clean Cache] Delete Folder:"+i)
			DeleteFolder(i)
		else: 
			if os.path.isfile(i):
				print("[Clean Cache] Delete File:"+i)
				os.remove(i)
			if os.path.isdir(i):
				print("[Clean Cache] Delete Folder:"+i)
				DeleteFolder(i)
def RestSetting():
	if os.path.isfile(PythonLocation()+"/../MultiThread"):
		os.remove(PythonLocation()+"/../MultiThread")
def SwitchBuildMode():
	if os.path.isfile(PythonLocation()+"/../../01_MercurySDK/FastBuild")==False:
		shutil.copy(PythonLocation()+"/../../02_ChannelSdk/ChannelTemplate/Template",PythonLocation()+"/../../01_MercurySDK/FastBuild")
		print("Fast Build Mode [Open]")
	else:
		os.remove(PythonLocation()+"/../../01_MercurySDK/FastBuild")
		print("Fast Build Mode [Close]")
	time.sleep(1)
def MultiThreadState():
	if os.path.isfile(PythonLocation()+"/../../01_MercurySDK/MultiThread")==False:
		shutil.copy(PythonLocation()+"/../../02_ChannelSdk/ChannelTemplate/Template",PythonLocation()+"/../../01_MercurySDK/MultiThread")
		print("MultiThread Mode [Open]")
	else:
		os.remove(PythonLocation()+"/../../01_MercurySDK/MultiThread")
		print("MultiThread Mode [Close]")
	time.sleep(1)
def UpdateProject():
	ChannelList = ListFolder(PythonLocation()+"/../01_MercurySDKPlugin/east2west/src/main/java/com/east2west/game/inApp")
	ChannelListName =[]
	ConfigurationName = []
	ConfigurationList = ListFolder(PythonLocation()+"/../../")
	JsonChannelList = {}
	UpdateGameConfiguration()
	for ConfigurationFile in ConfigurationList:
		if "Configuration_" in ConfigurationFile:
			ConfigurationName.append(ConfigurationFile)
	for JavaFile in ChannelList:
		if "InApp" in JavaFile and ".java" in JavaFile:
			JavaFile = JavaFile.replace("InApp","")
			JavaFile = JavaFile.replace(".java","")
			JavaFile = JavaFile.lower()
			ChannelListName.append(JavaFile)
	#ADConfiguration
	ADJson()
	#Template
	for Cfoler in ConfigurationName:
		for channelname in ChannelListName:
			if os.path.isfile(PythonLocation()+"/../../02_ChannelSdk/ChannelTemplate/"+channelname) and os.path.isfile(PythonLocation()+"/../../"+Cfoler+"/ChannelTemplate/"+channelname)==False :
				shutil.copy(PythonLocation()+"/../../02_ChannelSdk/ChannelTemplate/"+channelname,PythonLocation()+"/../../"+Cfoler+"/ChannelTemplate/"+channelname)
				#print("["+Cfoler+"] Update ChannelTemplate:"+channelname)
			if os.path.isfile(PythonLocation()+"/../../02_ChannelSdk/ChannelTemplate/"+channelname) ==False and os.path.isfile(PythonLocation()+"/../../"+Cfoler+"/ChannelTemplate/"+channelname)==False :
				if os.path.exists(PythonLocation()+"/../../"+Cfoler+"/ChannelTemplate")==False:
					os.makedirs(PythonLocation()+"/../../"+Cfoler+"/ChannelTemplate")
				shutil.copy(PythonLocation()+"/../../02_ChannelSdk/ChannelTemplate/Template",PythonLocation()+"/../../"+Cfoler+"/ChannelTemplate/"+channelname)
				#print("["+Cfoler+"] Set default ChannelTemplate:"+channelname)
	#XML
	#for Cfoler in ConfigurationName:
	#	for channelname in ChannelListName:
			if os.path.isfile(PythonLocation()+"/../../02_ChannelSdk/ChannelXML/"+channelname+".qinxml") and os.path.isfile(PythonLocation()+"/../../"+Cfoler+"/ChannelXML/"+channelname+".qinxml")==False :
				if os.path.exists(PythonLocation()+"/../../"+Cfoler+"/ChannelXML")==False:
					os.makedirs(PythonLocation()+"/../../"+Cfoler+"/ChannelXML")
				shutil.copy(PythonLocation()+"/../../02_ChannelSdk/ChannelXML/"+channelname+".qinxml",PythonLocation()+"/../../"+Cfoler+"/ChannelXML/"+channelname+".qinxml")
				#print("["+Cfoler+"] Update ChannelXML:"+channelname)
			if os.path.isfile(PythonLocation()+"/../../02_ChannelSdk/ChannelXML/"+channelname+".qinxml") ==False and os.path.isfile(PythonLocation()+"/../../"+Cfoler+"/ChannelTemplate/"+channelname+".qinxml")==False :
				print("["+Cfoler+"] ERROR------->: Don't have ChannelXML in database:"+channelname)
	#ChannelDecompile
	#for Cfoler in ConfigurationName:
	#	for channelname in ChannelListName:
			if os.path.exists(PythonLocation()+"/../../"+Cfoler+"/ChannelDecompile/"+channelname+"/assets") ==False:
				os.makedirs(PythonLocation()+"/../../"+Cfoler+"/ChannelDecompile/"+channelname+"/assets")
			if os.path.exists(PythonLocation()+"/../../"+Cfoler+"/ChannelDecompile/"+channelname+"/res") ==False:
				os.makedirs(PythonLocation()+"/../../"+Cfoler+"/ChannelDecompile/"+channelname+"/res")
			if os.path.exists(PythonLocation()+"/../../"+Cfoler+"/ChannelDecompile/"+channelname+"/lib") ==False:
				os.makedirs(PythonLocation()+"/../../"+Cfoler+"/ChannelDecompile/"+channelname+"/lib")
			if os.path.exists(PythonLocation()+"/../../"+Cfoler+"/ChannelDecompile/"+channelname+"/smali") ==False:
				os.makedirs(PythonLocation()+"/../../"+Cfoler+"/ChannelDecompile/"+channelname+"/smali")
			if os.path.exists(PythonLocation()+"/../../"+Cfoler+"/ChannelICON/"+channelname) ==False:
				os.makedirs(PythonLocation()+"/../../"+Cfoler+"/ChannelICON/"+channelname)
	#UpdatePackageName:
	#for Cfoler in ConfigurationName:
	#	for channelname in ChannelListName:
			if os.path.isfile(PythonLocation()+"/../../"+Cfoler+"/package.json") ==False:
				shutil.copy(PythonLocation()+"/../../02_ChannelSdk/package.json",PythonLocation()+"/../../"+Cfoler+"/package.json")
			if os.path.isfile(PythonLocation()+"/../../"+Cfoler+"/package.json"):
				readed = json.load(open(PythonLocation()+"/../../"+Cfoler+"/package.json", 'r',encoding=GetEncoding(PythonLocation()+"/../../"+Cfoler+"/package.json")))
				try:
					adddirc = {channelname:readed[channelname]}
					JsonChannelList.update(adddirc)
				except:
					data = {}
					data[channelname] = ""
					JsonChannelList.update(data)
					#print("["+Cfoler+"] add channel:"+channelname)
			CreateBuildPython(Cfoler,channelname)
			CreateGamePython(Cfoler,channelname)
		with open(PythonLocation()+"/../../"+Cfoler+"/package.json", 'w',encoding=GetEncoding(PythonLocation()+"/../../"+Cfoler+"/package.json")) as json_file:
			json_file.write(json.dumps(JsonChannelList,ensure_ascii=False,sort_keys=True, indent=4, separators=(',', ':')))
		
		#UpdateGameConfiguration
		if os.path.isfile(PythonLocation()+"/../../02_ChannelSdk/GameConfiguration") and os.path.isfile(PythonLocation()+"/../../"+Cfoler+"/GameConfiguration")==False :
			shutil.copy(PythonLocation()+"/../../02_ChannelSdk/GameConfiguration",PythonLocation()+"/../../"+Cfoler+"/GameConfiguration")
		#UpdateProductMap
		if os.path.isfile(PythonLocation()+"/../../02_ChannelSdk/ProductMap") and os.path.isfile(PythonLocation()+"/../../"+Cfoler+"/ProductMap")==False :
			shutil.copy(PythonLocation()+"/../../02_ChannelSdk/ProductMap",PythonLocation()+"/../../"+Cfoler+"/ProductMap")
			#print("["+Cfoler+"] Update ChannelTemplate:"+channelname)
	print("__________________________Sync Success__________________________")
def UpdateJar(FolderName):
	ChannelSDKList = FindAll(PythonLocation()+"/../../02_ChannelSdk/"+FolderName)
	ChannelSDKJarList = []
	#find all jar
	for mylist in ChannelSDKList:
		if mylist.find(".jar")!=-1:
			if CurrentPlatform()=="Windows":
				if mylist.find("\\libs\\")!=-1:
					ChannelSDKJarList.append(mylist)
			if CurrentPlatform()=="Mac":
				if mylist.find("/libs/")!=-1:
					ChannelSDKJarList.append(mylist)
	for myList in ChannelSDKJarList:
		print(myList[myList.find(FolderName)+len(FolderName)+1:myList.find("libs")-1]+"_"+myList[myList.find("libs")+len("libs")+1:])
		print(myList[:myList.find("libs")+len("libs")+1])
		if myList.find(myList[myList.find(FolderName)+len(FolderName)+1:myList.find("libs")-1])!=-1:
			os.rename(myList,myList[:myList.find("libs")+len("libs")+1]+myList[myList.find(FolderName)+len(FolderName)+1:myList.find("libs")-1]+"_"+myList[myList.find("libs")+len("libs")+1:])

def CurrentPlatform():
	sysstr = platform.system()
	if(sysstr =="Windows"):
		return "Windows"
	elif(sysstr == "Linux"):
		return "Linux"
	elif(sysstr == "Darwin"):
		return "Mac"
	else:
		return "None"
def CreateBuildPython(Cfoler,channelname):
	if os.path.isfile(PythonLocation()+"/../../01_MercurySDK/BuildingConfigration") and os.path.isfile(PythonLocation()+"/../../"+Cfoler+"/BuildingConfigration")==False :
			shutil.copy(PythonLocation()+"/../../01_MercurySDK/BuildingConfigration",PythonLocation()+"/../../"+Cfoler+"/BuildingConfigration")

	if os.path.isfile(PythonLocation()+"/../31_Template.py"):
		file_object = open(PythonLocation()+"/../31_Template.py",encoding=GetEncoding(PythonLocation()+"/../31_Template.py"))
		PythonCode = []
		try:
			all_the_text = file_object.readlines()
			for i in all_the_text:
				i.replace(" ","")
				if(i.find("main():")!=-1):
					PythonCode.append(i)
					if CurrentPlatform()=="Windows":
						PythonCode.append("	Channel =\""+channelname+"\""+"\n")
					if CurrentPlatform()=="Mac":
						PythonCode.append("	Channel =\""+channelname+"\""+"\r")
				else:
					PythonCode.append(i)
		finally:
			file_object.close( )
	if os.path.isfile(PythonLocation()+"/../"+channelname+".py") ==False:
		open(PythonLocation()+"/../"+channelname+".py","w",encoding="utf-8")
	with open(PythonLocation()+"/../"+channelname+".py", 'w',encoding=GetEncoding(PythonLocation()+"/../"+channelname+".py")) as json_file:
		for i in PythonCode:
			json_file.writelines(i)
def CreateGamePython(Cfoler,channelname):
	if os.path.isfile(PythonLocation()+"/../32_GameChannelTemplate.py"):
		file_object = open(PythonLocation()+"/../32_GameChannelTemplate.py",encoding=GetEncoding(PythonLocation()+"/../32_GameChannelTemplate.py"))
		PythonCode = []
		try:
			all_the_text = file_object.readlines()
			for i in all_the_text:
				i.replace(" ","")
				if i.find("os.system")!=-1:
					if CurrentPlatform()=="Windows":
						PythonCode.append("	os.system(GetPythonCommand()"+"+\" \"+ PythonLocation()+\"/../01_MercurySDK/"+channelname+".py "+Cfoler+"\")\n")
					if CurrentPlatform()=="Mac":
						PythonCode.append("	os.system(GetPythonCommand()"+"+\" \"+ PythonLocation()+\"/../01_MercurySDK/"+channelname+".py "+Cfoler+"\")\r")
						
				else:
					PythonCode.append(i)
		finally:
			file_object.close( )
	if os.path.isfile(PythonLocation()+"/../../"+Cfoler+"/00_"+channelname+".py") ==False:
		open(PythonLocation()+"/../../"+Cfoler+"/00_"+channelname+".py","w",encoding="utf-8")
	with open(PythonLocation()+"/../../"+Cfoler+"/00_"+channelname+".py", 'w',encoding=GetEncoding(PythonLocation()+"/../../"+Cfoler+"/00_"+channelname+".py")) as json_file:
		for i in PythonCode:
			json_file.writelines(i)
def CreateGameSetting():
	if os.path.isfile(PythonLocation()+"/../01_MercurySDKPlugin/src/com/east2west/game/QinConst.java"):
		file_object = open(PythonLocation()+"/../01_MercurySDKPlugin/src/com/east2west/game/QinConst.java",encoding=GetEncoding(PythonLocation()+"/../01_MercurySDKPlugin/src/com/east2west/game/QinConst.java"))
		isEnter=False

		SettingDic = {}
		try:
			all_the_text = file_object.readlines()
			for i in all_the_text:
				checkI = i.replace(" ","")
				if(i.find("/PythonConfiguration")!=-1):
					isEnter = True	
				elif(isEnter==True and i.find("/end")==-1):
					#print(i)
					if checkI.find("staticString") != -1 and  checkI.find("=") != -1:
						#newI_appid = i.replace("appid",AbbreviateName+"appid")
						#print(newI_appid)
						#temp = newI_appid.replace(" ","")
						start  = checkI.rfind("staticString")
						end = checkI.rfind("=")
						startvalue  = checkI.find("=\"")
						endvalue = checkI.rfind("\";")
						#print(abs(start-end))
						if checkI[start+1:end]!="" :
							data = {}
							data[checkI[start+len("staticString"):end]] = checkI[startvalue+len("=\""):endvalue]
							SettingDic.update(data)
				elif(isEnter==True and i.find("/end")!=-1):
					break
		finally:
			file_object.close( )
		print(PythonLocation()+"/../../02_ChannelSdk/GameConfiguration")
		if os.path.isfile(PythonLocation()+"/../../02_ChannelSdk/GameConfiguration") ==False:
			open(PythonLocation()+"/../../02_ChannelSdk/GameConfiguration","w","utf-8")
		with open(PythonLocation()+"/../../02_ChannelSdk/GameConfiguration", 'w',encoding=GetEncoding(PythonLocation()+"/../../02_ChannelSdk/GameConfiguration")) as json_file:
			#print(json.dumps(SettingDic))
			json_file.write(json.dumps(SettingDic,ensure_ascii=False,sort_keys=True, indent=4, separators=(',', ':')))
def FindAllInFolder(_path):
	ListMyFolder = []
	for filename in os.listdir(_path):
		ListMyFolder.append(_path+"/"+filename)
	return ListMyFolder
def FindAllByFilter(_path,_key):
	ListMyFolder = []
	for filename in glob.glob(_path+"/"+_key):
		#print(filename)
		ListMyFolder.append(filename)
	return ListMyFolder
def FindAll(_path):
	ListMyFolder = []
	for dirpath, dirnames, filenames in os.walk(_path):
		#print ('Directory', dirpath)
		dirnames
		for filename in filenames:
			#print (' File', filename)
			ListMyFolder.append(dirpath+"/"+filename)
	return ListMyFolder
def CopyFiles(sourceDir, targetDir):
    global copyFileCounts
    #print (sourceDir)
    #print (u"%s 当前处理文件夹%s已处理%s 个文件" %(time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time())), sourceDir,copyFileCounts))
    for f in os.listdir(sourceDir):
        sourceF = os.path.join(sourceDir, f)
        targetF = os.path.join(targetDir, f)
        if os.path.isfile(sourceF):
            #创建目录
            if not os.path.exists(targetDir):
                os.makedirs(targetDir)
            copyFileCounts += 1
            #文件不存在，或者存在但是大小不同，覆盖
            if not os.path.exists(targetF) or (os.path.exists(targetF) and (os.path.getsize(targetF) != os.path.getsize(sourceF))):
                #2进制文件
                open(targetF, "wb").write(open(sourceF, "rb").read())
                #print (u"%s %s 复制完毕" %(time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time())), targetF))
            else:
                pass
                #print (u"%s %s 已存在，不重复复制" %(time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time())), targetF))
        if os.path.isdir(sourceF):
            CopyFiles(sourceF, targetF)
def CopyFilesOverwrite(sourceDir, targetDir):
    global copyFileCounts
    #print (sourceDir)
    #print (u"%s 当前处理文件夹%s已处理%s 个文件" %(time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time())), sourceDir,copyFileCounts))
    for f in os.listdir(sourceDir):
        sourceF = os.path.join(sourceDir, f)
        targetF = os.path.join(targetDir, f)
        if os.path.isfile(sourceF):
            #创建目录
            if not os.path.exists(targetDir):
                os.makedirs(targetDir)
            copyFileCounts += 1
            #文件不存在，或者存在但是大小不同，覆盖
            if not os.path.exists(targetF) or (os.path.exists(targetF) and (os.path.getsize(targetF) != os.path.getsize(sourceF))):
                #2进制文件
                open(targetF, "wb").write(open(sourceF, "rb").read())
                #print (u"%s %s 复制完毕" %(time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time())), targetF))
            else:
                open(targetF, "wb").write(open(sourceF, "rb").read())
                #print (u"%s %s 已存在，不重复复制" %(time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time())), targetF))
        if os.path.isdir(sourceF):
            CopyFilesOverwrite(sourceF, targetF)
def CopyFilesDontOverWrite(sourceDir, targetDir):
    global copyFileCounts
    #print (sourceDir)
    #print (u"%s 当前处理文件夹%s已处理%s 个文件" %(time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time())), sourceDir,copyFileCounts))
    for f in os.listdir(sourceDir):
        sourceF = os.path.join(sourceDir, f)
        targetF = os.path.join(targetDir, f)
        if os.path.isfile(sourceF):
            #创建目录
            if not os.path.exists(targetDir):
                os.makedirs(targetDir)
            copyFileCounts += 1
            #文件不存在，或者存在但是大小不同，覆盖
            if not os.path.exists(targetF):
                #2进制文件
                open(targetF, "wb").write(open(sourceF, "rb").read())
                #print (u"%s %s 复制完毕" %(time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time())), targetF))
            else:
                pass
                #print (u"%s %s 已存在，不重复复制" %(time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time())), targetF))
        if os.path.isdir(sourceF):
            CopyFilesDontOverWrite(sourceF, targetF)
def PythonLocation():
	return os.path.dirname(os.path.realpath(__file__))
@CallMethodLocation("")
@UsePlatform("Windows")
def BuildE2WTestAPK():
	if(os.path.exists(PythonLocation()+"/../DemoProject/01_MercurySDKDemoCopy")):
		DeleteFolder(PythonLocation()+"/../DemoProject/01_MercurySDKDemoCopy")
	if(os.path.exists(PythonLocation()+"/../DemoProject/01_MercurySDKDemo")):
		CopyFiles(PythonLocation()+"/../DemoProject/01_MercurySDKDemo",PythonLocation()+"/../DemoProject/01_MercurySDKDemoCopy")
	os.chdir(r''+PythonLocation()+"/../DemoProject/01_MercurySDKDemoCopy")
	os.system("android update project --name game -p ./")
	os.system("ant clean")
	os.system("ant release")
	os.chdir(r''+CallMethodPath)
	SDKpath = PythonLocation()+"/../DemoProject/01_MercurySDKDemoCopy"
	global isTestBuild 
	isTestBuild = True
	return SDKpath
def WriteE2WNumber(_APKLocation,_PackageName):
	_Path=_APKLocation
	dom = xml.dom.minidom.parse(_Path)
	root = dom.documentElement
	#name =root.getAttribute("package")
	name  = root.getElementsByTagName("meta-data")
	global FoundKey
	FoundKey=False
	for node in name:
		nameofname = node.getAttribute("android:name")
		if nameofname=="E2W_NUMBER":
			'''nameofvalue ='''
			node.getAttribute("android:value")
			node.setAttribute("android:value",_PackageName)
			FoundKey=True
	if FoundKey==False:
		print("[ERROR] Can't find E2W_NUMBER")
	try:
		with open(_Path,'w',encoding='UTF-8') as _Path:
			dom.writexml(_Path,indent='',addindent='',newl='',encoding='UTF-8')
	except Exception as err:
		print("ERROR[{0}".format(err))
def DecompileAPK(_APKLocation):
	APKname = _APKLocation.split("/")[-1]
	PointCount = APKname.rfind(".")
	foldername=APKname[:PointCount]
	#print(PythonLocation()+"/__pycache__"+"/"+foldername)
	if os.path.exists(PythonLocation()+"/__pycache__"+"/"+foldername):
		DeleteFolder(PythonLocation()+"/__pycache__"+"/"+foldername)
	os.chdir(r''+PythonLocation()+"/__pycache__")
	#print(PythonLocation()+"/../../03_Appurtenance/Tool/apktool d "+_APKLocation)
	os.system(PythonLocation()+"/../../03_Appurtenance/Tool/apktool d "+_APKLocation)
	os.chdir(r''+CallMethodPath)
def DecompileSDKAPK(_ChannelName,_AD,SDKFolder):
	global isFastBuild 
	global MultiThread
	global DemoProjectAPKName
	if _ChannelName!="" and _AD=="":
		if _ChannelName!="":
			SDKSmaliLocation = PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName.lower()
			SDKLocation = PythonLocation()+"/../../02_ChannelSdk/ChannelSDK/"+_ChannelName.lower()
			BuildConfigLocation = get_desktop()+"/BuildConfig/DecompileSDK"
			if _ChannelName == "system":
				SDKSmaliLocation = PythonLocation()+"/../../02_ChannelSdk/SystemSDKSmali/"+_ChannelName.lower()
				SDKLocation = PythonLocation()+"/../../02_ChannelSdk/SystemSDK/"+_ChannelName.lower()
				BuildConfigLocation = get_desktop()+"/BuildConfig/DecompileSystem"
		if _ChannelName=="" or os.path.exists(SDKLocation) == False:
			print("make sure your channel is exist:"+_ChannelName)
			return
	elif _ChannelName=="" and _AD!="":
		if _AD!="":
			SDKSmaliLocation = PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD.lower()
			SDKLocation = PythonLocation()+"/../../02_ChannelSdk/ADSDK/"+_AD.lower()
			BuildConfigLocation = get_desktop()+"/BuildConfig/DecompileAD"
		if _AD=="" or os.path.exists(SDKLocation) == False:
				print("make sure your _AD is exist:"+_AD)
				return
	else:
		print("make sure your _AD and _ChannelName is exist:"+_AD)
		return
	os.chdir(r''+PythonLocation()+"/__pycache__/"+SDKFolder)
	#print(PythonLocation()+"/../../03_Appurtenance/Tool/apktool d "+_APKLocation)
	os.system(PythonLocation()+"/../../03_Appurtenance/Tool/apktool d "+PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+".apk")
	if isMultipleBuild(PythonLocation()+"/__pycache__")==False:
		DeleteFolder(SDKSmaliLocation+"/res")
	if isMultipleBuild(PythonLocation()+"/__pycache__")==False:
		DeleteFolder(SDKSmaliLocation+"/assets")
	if isMultipleBuild(PythonLocation()+"/__pycache__")==False:
		DeleteFolder(SDKSmaliLocation+"/smali")
	if isMultipleBuild(PythonLocation()+"/__pycache__")==False:
		DeleteFolder(SDKSmaliLocation+"/lib")
	if os.path.exists(SDKLocation+"/assets"):
		CopyFiles(SDKLocation+"/assets",SDKSmaliLocation+"/assets")
	if os.path.exists(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/assets"):
		CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/assets",SDKSmaliLocation+"/assets")
		if isFastBuild==False and os.path.exists(SDKSmaliLocation+"/assets") and MultiThread ==False:
			CopyFiles(SDKSmaliLocation+"/assets",BuildConfigLocation+"/assets")
	if os.path.exists(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/res"):
		ListSDKRes = FindAll(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/res")
		ListGameRes = FindAll(SDKSmaliLocation+"/res")
		for sdkres in ListSDKRes:
			if DeleteConflictRes(sdkres) :
				continue
			for gameres in ListGameRes:
				sdkresfile = sdkres[sdkres.rfind("/")-4:]
				gameresfile = gameres[gameres.rfind("/")-4:]
				if(sdkresfile ==gameresfile and ".xml" in sdkresfile):
					CombineRES(sdkres,gameres)
					FuncXMLOperationClass.DeleteMultiElement(gameres)
		CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/res",SDKSmaliLocation+"/res")
		if os.path.exists(SDKLocation+"/res"):
			CopyFiles(SDKLocation+"/res",SDKSmaliLocation+"/res")
		if isFastBuild==False and os.path.exists(SDKSmaliLocation+"/res") and MultiThread ==False:
			CopyFiles(SDKSmaliLocation+"/res",BuildConfigLocation+"/res")
		if os.path.exists(SDKSmaliLocation+"/res/values/integers.xml") and os.path.exists(SDKSmaliLocation+"/res/values/integer.xml"):
			CombineRES(SDKSmaliLocation+"/res/values/integers.xml",SDKSmaliLocation+"/res/values/integer.xml")
			os.remove(SDKSmaliLocation+"/res/values/integers.xml")
		if os.path.exists(SDKSmaliLocation+"/res/values/integers.xml") and os.path.exists(SDKSmaliLocation+"/res/values/integer.xml")==False:
			os.rename(SDKSmaliLocation+"/res/values/integers.xml",SDKSmaliLocation+"/res/values/integer.xml")
	if os.path.exists(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/smali"):
		CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/smali",SDKSmaliLocation+"/smali")
		if os.path.exists(SDKLocation+"/smali"):
			CopyFiles(SDKLocation+"/smali",SDKSmaliLocation+"/smali")
		if isFastBuild==False and os.path.exists(SDKSmaliLocation+"/smali") and MultiThread ==False:
			CopyFiles(SDKSmaliLocation+"/smali",BuildConfigLocation+"/smali")
	if os.path.exists(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/lib"):
		CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/lib",SDKSmaliLocation+"/lib")
		if isFastBuild==False and os.path.exists(SDKSmaliLocation+"/lib") and MultiThread ==False:
			CopyFiles(SDKSmaliLocation+"/lib",BuildConfigLocation+"/lib")
	#get assert from jarWaiting Building new Jar
	JarList = FindAllByFilter(PythonLocation()+"/__pycache__/"+SDKFolder+"/app/src/main/libs","*.jar")
	os.chdir(PythonLocation()+"/__pycache__/"+SDKFolder+"/app/src/main/libs")
	for i in JarList:
		os.system(r"jar -xvf  "+i)
	if os.path.exists(PythonLocation()+"/__pycache__/"+SDKFolder+"/app/src/main/libs/assets"):
		CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/app/src/main/libs/assets",SDKSmaliLocation+"/assets" )

	os.chdir(r''+CallMethodPath)
def DecompileE2WPluginAPK(SDKFolder,_ChannelName):
	os.chdir(r''+PythonLocation()+"/__pycache__/"+SDKFolder)
	#print(PythonLocation()+"/../../03_Appurtenance/Tool/apktool d "+_APKLocation)
	os.system(PythonLocation()+"/../../03_Appurtenance/Tool/apktool d "+PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+".apk")
	os.chdir(r''+CallMethodPath)
def DecompileADSDKAPK(SDKFolder,_AD):
	global isFastBuild 
	global DemoProjectAPKName
	os.chdir(r''+PythonLocation()+"/__pycache__/"+SDKFolder)
	#print(PythonLocation()+"/../../03_Appurtenance/Tool/apktool d "+_APKLocation)
	os.system(PythonLocation()+"/../../03_Appurtenance/Tool/apktool d "+PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+".apk")
	if os.path.exists(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/assets"):
		if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD+"/assets"):
			if isMultipleBuild(PythonLocation()+"/__pycache__")==False:
				DeleteFolder(PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD+"/assets")
		CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/assets",PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD+"/assets")
		if isFastBuild==False:
			CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/assets",get_desktop()+"/BuildConfig/DecompileAD/assets")
	# copy res folder from SDK
	if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ADSDK/"+_AD.lower()+"/res"):
		if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD+"/res"):
			if isMultipleBuild(PythonLocation()+"/__pycache__")==False:
				DeleteFolder(PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD+"/res")
		CopyFiles(PythonLocation()+"/../../02_ChannelSdk/ADSDK/"+_AD.lower()+"/res",PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD+"/res")
		if isFastBuild==False:
			CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/res",get_desktop()+"/BuildConfig/DecompileAD/res")
	if os.path.exists(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/smali"):
		if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD+"/smali"):
			if isMultipleBuild(PythonLocation()+"/__pycache__")==False:
				DeleteFolder(PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD+"/smali")
		CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/smali",PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD+"/smali")
		if isFastBuild==False:
			CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/smali",get_desktop()+"/BuildConfig/DecompileAD/smali")

	if os.path.exists(PythonLocation()+"/__pycache__/"+SDKFolder+"/app/src/main/lib"):
		Listall = ListFolder(PythonLocation()+"/__pycache__/"+SDKFolder+"/app/src/main/lib")
		for mylist in Listall:
			if mylist.find(".jar")!=-1:
				os.remove(PythonLocation()+"/__pycache__/"+SDKFolder+"/app/src/main/libs/"+mylist)
		CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/app/src/main/libs",PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD.lower()+"/lib")
		if isFastBuild==False:
			CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/app/src/main/libs",get_desktop()+"/BuildConfig/DecompileAD/lib")
	if os.path.exists(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/lib"):
		CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/lib",PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD.lower()+"/lib")
	if isFastBuild==False:
		CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/lib",get_desktop()+"/BuildConfig/DecompileAD/lib")
	os.chdir(r''+CallMethodPath)
# def RebuildAPK(_APKLocation):
# 	APKname = _APKLocation.split("/")[-1]
# 	PointCount = APKname.rfind(".")
# 	foldername=APKname[:PointCount]
# 	os.chdir(r''+PythonLocation()+"/__pycache__")
# 	os.system(PythonLocation()+"/../Appurtenance/Tool/apktool b "+foldername)
# 	os.chdir(r''+CallMethodPath)
def Unzip(_APKLocation):
	os.chdir(r''+PythonLocation()+"/__pycache__")
	#APKname = _APKLocation.split("/")[-1]
	#PointCount = APKname.rfind(".")
	#foldername=APKname[:PointCount]
	if os.path.exists(PythonLocation()+"/__pycache__/foldername"):
		DeleteFolder(PythonLocation()+"/__pycache__/foldername")
	if os.path.exists(PythonLocation()+"/__pycache__/assets"):
		DeleteFolder(PythonLocation()+"/__pycache__/assets")
	if os.path.exists(PythonLocation()+"/__pycache__/META-INF"):
		DeleteFolder(PythonLocation()+"/__pycache__/META-INF")
	if os.path.exists(PythonLocation()+"/__pycache__/res"):
		DeleteFolder(PythonLocation()+"/__pycache__/res")
	if os.path.isfile(PythonLocation()+"/__pycache__/AndroidManifest.xml"):
		os.remove(PythonLocation()+"/__pycache__/AndroidManifest.xml")
	if os.path.isfile(PythonLocation()+"/__pycache__/classes.dex"):
		os.remove(PythonLocation()+"/__pycache__/classes.dex")
	if os.path.isfile(PythonLocation()+"/__pycache__/resources.arsc"):
		os.remove(PythonLocation()+"/__pycache__/resources.arsc")
	#print("jar xf "+_APKLocation)
	os.system("jar xf "+_APKLocation)
	os.chdir(r''+CallMethodPath)
def SignatureCommond(_APKLocation):
	APKname = _APKLocation.split("/")[-1]
	PointCount = APKname.rfind(".")
	#foldername=APKname[:PointCount]
	APKname[:PointCount]
	if os.path.exists(PythonLocation()+"/__pycache__/META-INF")==False:
		print("Your APK don't have META-INF folder")
		return
	MySignature = ListFolder(PythonLocation()+"/__pycache__/META-INF")
	for name in MySignature:
		if ".RSA" in name:
			os.system("keytool -printcert -file "+PythonLocation()+"/__pycache__/META-INF/"+name)
def ChangePackageName(_APKLocation,_PackageName):
	APKname = _APKLocation.split("/")[-1]
	PointCount = APKname.rfind(".")
	foldername=APKname[:PointCount]
	_Path = PythonLocation()+"/__pycache__/"+foldername+"/AndroidManifest.xml"
	dom = xml.dom.minidom.parse(_Path)
	root = dom.documentElement
	root.setAttribute("package",_PackageName)
	try:
		with open(_Path,'w',encoding='UTF-8') as _Path:
			dom.writexml(_Path,indent='',addindent='',newl='',encoding='UTF-8')
	except Exception as err:
		print("ERROR[{0}".format(err))
# def GetPackageName(_APKLocation):
# 	APKname = _APKLocation.split("/")[-1]
# 	PointCount = APKname.rfind(".")
# 	foldername=APKname[:PointCount]
# 	_Path = PythonLocation()+"/__pycache__/"+foldername+"/AndroidManifest.xml"
# 	dom = xml.dom.minidom.parse(_Path)
# 	root = dom.documentElement
# 	stringForTem = root.getAttribute("package")
# 	return stringForTem
def GetVCVN(_APKLocation):
	APKname = _APKLocation.split("/")[-1]
	PointCount = APKname.rfind(".")
	foldername=APKname[:PointCount]
	_Path = PythonLocation()+"/__pycache__/"+foldername+"/apktool.yml"
	StringList = []
	if os.path.isfile(_Path):
		file_object = open(_Path,encoding="utf8")
		try:
			all_the_text = file_object.readlines()
			for i in all_the_text:
				if "versionCode" in i:
					StringList.append(i)
				if "versionName" in i:
					StringList.append(i)
		finally:
			file_object.close( )
	return StringList
def CheckSignature(_APKLocation):
	Unzip(_APKLocation)
	SignatureCommond(_APKLocation)

def DeleteFolder(src):
    '''delete files and folders'''
    if os.path.isfile(src):
        try:
            os.remove(src)
        except:
            pass
    elif os.path.isdir(src):
        for item in os.listdir(src):
            itemsrc=os.path.join(src,item)
            DeleteFolder(itemsrc) 
        try:
            os.rmdir(src)
        except:
            pass
def CopyBaseAPK(_APKLocation):
	count = 1
	while True:
		if os.path.exists(PythonLocation()+"/__pycache__/copy"+str(count)+".apk"):
			count+=1
		else:
			break
	shutil.copy(_APKLocation,PythonLocation()+"/__pycache__/copy"+str(count)+".apk")
	return "copy"+str(count)+".apk"
def CopyDecomplieAPK(_APKLocation):
	count = 1
	while True:
		if os.path.exists(PythonLocation()+"/__pycache__/de_copy"+str(count)+".apk"):
			count+=1
		else:
			break
	shutil.copy(_APKLocation,PythonLocation()+"/__pycache__/de_copy"+str(count)+".apk")
	return "de_copy"+str(count)+".apk"

def CopyEmptyDemo(_Number):
	global MultiThread
	isSuccess=False

	while not isSuccess:
		try:
			t = time.time()
			nowTime = lambda:int(round(t * 1000))
			count = nowTime()
			print("Try To Copy Temp Empty Folder")
			count+=random.randint(1, 20000)
			print("Copying Temp Empty Folder")
			CopyFiles(PythonLocation()+"/__pycache__/../DemoAPK",PythonLocation()+"/__pycache__/"+"EmptyDemo"+str(count))
			print("Copying Temp Empty Folder finished")
			isSuccess=True
		except Exception as ee:
			print("CopyEmptyDemo:"+ee)
	return "EmptyDemo"+str(count)
def CreatePackageNameForWX(_ChannelName, _isCreate,SDKFolder):
	global NewName
	CreateNewfolderName = NewName.replace(".","/")

	if _isCreate==True:	
		if os.path.exists(PythonLocation()+"/__pycache__/"+SDKFolder+"/east2west/src/main/java/"+CreateNewfolderName+"/wxapi")==False:
			os.makedirs(PythonLocation()+"/__pycache__/"+SDKFolder+"/east2west/src/main/java/"+CreateNewfolderName+"/wxapi")
		if(_ChannelName.find("YSDK")==-1):
			CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/east2west/Necessarysrc/Common",PythonLocation()+"/__pycache__/"+SDKFolder+"/east2west/src/main/java/"+CreateNewfolderName+"/wxapi")
		else:
			CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/east2west/Necessarysrc/YSDK",PythonLocation()+"/__pycache__/"+SDKFolder+"/east2west/src/main/java/"+CreateNewfolderName+"/wxapi")
		List = ListFolder(PythonLocation()+"/__pycache__/"+SDKFolder+"/east2west/src/main/java/"+CreateNewfolderName+"/wxapi")
		for java in List:
			JavaCode=[]
			file_object = open(PythonLocation()+"/__pycache__/"+SDKFolder+"/east2west/src/main/java/"+CreateNewfolderName+"/wxapi"+"/"+java,encoding="utf8")
			try:
				all_the_text = file_object.readlines()
				for i in all_the_text:
					if i.find("package")!=-1:
						if CurrentPlatform()=="Windows":
							JavaCode.append("package "+NewName+".wxapi;\n")
						if CurrentPlatform()=="Mac":
							JavaCode.append("package "+NewName+".wxapi;\r")
					else:
						JavaCode.append(i)
			finally:
				file_object.close( )
			file_object_read = open(PythonLocation()+"/__pycache__/"+SDKFolder+"/east2west/src/main/java/"+CreateNewfolderName+"/wxapi"+"/"+java,'w',encoding="utf8")
			try:
				file_object_read.writelines(JavaCode)
			finally:
				file_object_read.close()
	else:
		if os.path.exists(PythonLocation()+"/__pycache__/"+SDKFolder+"/east2west/src/main/java/"+CreateNewfolderName+"/wxapi")==True:
			DeleteFolder(PythonLocation()+"/__pycache__/"+SDKFolder+"/east2west/src/main/java/"+CreateNewfolderName+"/wxapi")
			CleanEmptyFolder(PythonLocation()+"/__pycache__/"+SDKFolder+"/east2west/src/main/java")
def CleanEmptyFolder(path):
	if os.path.isdir(path):
		for d in os.listdir(path):
			CleanEmptyFolder(os.path.join(path, d))
	if os.path.isfile(path)==False:
		if not os.listdir(path):
			os.rmdir(path)
			print('Remove Empty Folder:' + path)
def BuildE2WJar(_ChannelName,_AD):
	SDKFolder = CopyEmptyDemo("")
	CopyFiles(PythonLocation()+"/../01_MercurySDKPlugin" ,PythonLocation()+"/__pycache__/"+SDKFolder)
	os.chdir(r''+PythonLocation()+"/__pycache__/"+SDKFolder+"/")
	CreatePackageNameForWX(_ChannelName,True,SDKFolder)
	CommentCode(PythonLocation()+"/__pycache__/"+SDKFolder+"/east2west/src/main/java/com/east2west/game/inApp",_ChannelName.lower(), True)
	MoveJarTemporary(PythonLocation()+"/__pycache__/"+SDKFolder+"/east2west/libs",PythonLocation()+"/../../02_ChannelSdk/ChannelSDK",_ChannelName.lower(), True)
	if _AD!="":
		CommentCode(PythonLocation()+"/__pycache__/"+SDKFolder+"/east2west/src/main/java/com/east2west/game/Show",_AD.lower(), True)
		MoveJarTemporary(PythonLocation()+"/__pycache__/"+SDKFolder+"/east2west/libs",PythonLocation()+"/../../02_ChannelSdk/ADSDK",_AD.lower(), True)
	CopyFiles(PythonLocation()+"/../../02_ChannelSdk/ChannelSDK/"+_ChannelName.lower()+"/libs",PythonLocation()+"/__pycache__/"+SDKFolder+"/east2west/libs" )
	CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/east2west/Necessarylibs",PythonLocation()+"/__pycache__/"+SDKFolder+"/east2west/libs" )
	if isMultipleBuild(PythonLocation()+"/__pycache__") == False:
		if os.path.isfile(r''+PythonLocation()+"/__pycache__/"+SDKFolder+"/UnityPlguin.jar"):
			os.remove(PythonLocation()+"/__pycache__/"+SDKFolder+"/UnityPlguin.jar")
	os.system(GetPythonCommand()+" BuildJAR.py")
	CreatePackageNameForWX(_ChannelName,False,SDKFolder)
	CommentCode(PythonLocation()+"/__pycache__/"+SDKFolder+"/east2west/src/main/java/com/east2west/game/inApp",_ChannelName.lower(), False)
	MoveJarTemporary(PythonLocation()+"/__pycache__/"+SDKFolder+"/east2west/libs",PythonLocation()+"/../../02_ChannelSdk/ChannelSDK",_ChannelName.lower(), False)
	if _AD!="":
		CommentCode(PythonLocation()+"/__pycache__/"+SDKFolder+"/east2west/src/main/java/com/east2west/game/Show",_AD.lower(), False)
		MoveJarTemporary(PythonLocation()+"/__pycache__/"+SDKFolder+"/east2west/libs",PythonLocation()+"/../../02_ChannelSdk/ADSDK",_AD.lower(), False)
	os.chdir(r''+CallMethodPath)
	return SDKFolder
def CopyNecessaryLibs():
	CopyFiles(PythonLocation()+"/../01_MercurySDKPlugin/east2west/Necessarylibs",PythonLocation()+"/../01_MercurySDKPlugin/east2west/libs" )

def CommentCode(_Path,_ChannelName,_isComment):
	ListJavaFiles = ListFolder(_Path)
	
	for myJava in ListJavaFiles:
		if myJava.find(".java")!=-1:
			isGoin=True
			#str  =myJava[:myJava.find(".java")]
			#str1 =_ChannelName.upper()
			cutString = myJava[:myJava.find(".java")].replace(_ChannelName.upper(),"")
			length = len(cutString)
			if length == 5 and cutString=="InApp":
				isGoin = False
			if length == 9 and cutString=="InAppShow":
				isGoin = False
			if os.path.isfile(_Path+"/"+myJava) and isGoin:
				file_object = open(_Path+"/"+myJava,encoding="utf8")
				JavaCode=[]
				Entercycle=False
				try:
					all_the_text = file_object.readlines()
					for i in all_the_text:
						f = i.replace(" ","")
						if(f.find("//comment")!=-1):
							Entercycle=True
							if CurrentPlatform()=="Windows":
								JavaCode.append("//comment\n")
							if CurrentPlatform()=="Mac":
								JavaCode.append("//comment\r")
						elif(f.find("//end")!=-1):
							Entercycle=False
							if CurrentPlatform()=="Windows":
								JavaCode.append("//end\n")
							if CurrentPlatform()=="Mac":
								JavaCode.append("//end\r")
						elif Entercycle==True:
							if _isComment==True and i.startswith("///")==False:
								JavaCode.append("///"+i)
							elif _isComment==False and i.startswith("///")==True:
								JavaCode.append(i[3:])
							else:
								JavaCode.append(i)
						elif (f.find("implements")!=-1 and f.find("extends")!=-1 and f.find("InAppBase")!=-1) and _isComment==True:
							#start = i.find("implements")
							#end = i.find("{")
							ii = i.replace("{//","")
							newf = ii.replace("implements","{//implements")
							JavaCode.append(newf)
						elif (f.find("implements")!=-1 and f.find("extends")!=-1 and f.find("InAppBase")!=-1) and _isComment==False:
							#start = i.find("implements")
							#end = i.find("{")
							newf = i.replace("{//implements","implements")
							JavaCode.append(newf)
						else:
							JavaCode.append(i)
				finally:
					file_object.close( )
				file_object_read = open(_Path+"/"+myJava,'w',encoding="utf8")
				try:
					file_object_read.writelines(JavaCode)
				finally:
					file_object_read.close()
			if os.path.isfile(_Path+"/"+myJava) and isGoin and _isComment==False:
				file_object = open(_Path+"/"+myJava,encoding="utf8")
				JavaCode=[]
				Entercycle=False
				try:
					all_the_text = file_object.readlines()
					for i in all_the_text:
						f = i.replace(" ","")
						if(f.find("//comment")!=-1):
							Entercycle=True
							if CurrentPlatform()=="Windows":
								JavaCode.append("//comment\n")
							if CurrentPlatform()=="Mac":
								JavaCode.append("//comment\r")
						elif(f.find("//end")!=-1):
							Entercycle=False
							if CurrentPlatform()=="Windows":
								JavaCode.append("//end\n")
							if CurrentPlatform()=="Mac":
								JavaCode.append("//end\r")
						elif Entercycle==True:
							if _isComment==True and i.startswith("///")==False:
								JavaCode.append("///"+i)
							elif _isComment==False and i.startswith("///")==True:
								JavaCode.append(i[3:])
							else:
								JavaCode.append(i)
						else:
							JavaCode.append(i)
				finally:
					file_object.close( )
				file_object_read = open(_Path+"/"+myJava,'w',encoding="utf8")
				try:
					file_object_read.writelines(JavaCode)
				finally:
					file_object_read.close()
	
def MoveJarTemporary(_SDKToolPath,_ChannelSDKPath, _Channel, _isHideJar):
	if _isHideJar==False:
		SDKList = ListFolder(_SDKToolPath+"/../")
	else:
		SDKList = ListFolder(_SDKToolPath)
	ChannelSDKList = FindAll(_ChannelSDKPath)
	ChannelSDKJarList = []
	isFound=False
	MyChannelList = []
	for mylist in ChannelSDKList:
		if mylist.find(".jar")!=-1:
			if CurrentPlatform()=="Windows":
				if mylist.find("\\"+_Channel+"\\")==-1:
					ChannelSDKJarList.append(mylist)
				else:
					if mylist.find("\\libs\\")!=-1:
						print("Keep jar:"+mylist)
						MyChannelList.append(mylist)
			if CurrentPlatform()=="Mac":
				if mylist.find("/"+_Channel+"/")==-1:
					ChannelSDKJarList.append(mylist)
				else:
					if mylist.find("/libs/")!=-1:
						print("Keep jar:"+mylist)
						MyChannelList.append(mylist)
	for ToolJar in SDKList:
		isFound=False
		for ChannelJar in ChannelSDKJarList:
			if ChannelJar.find(ToolJar)!=-1:
				isFound=True
		if isFound and _isHideJar==True and os.path.isfile(_SDKToolPath+"/../"+ToolJar)==False:
			os.rename(_SDKToolPath+"/"+ToolJar, _SDKToolPath+"/../"+ToolJar)
		if isFound and _isHideJar==False and os.path.isfile(_SDKToolPath+"/"+ToolJar)==False and ToolJar.find(".jar")!=-1:
			if os.path.isfile(_SDKToolPath+"/"+ToolJar):
				os.remove(_SDKToolPath+"/"+ToolJar)
			os.rename(_SDKToolPath+"/../"+ToolJar, _SDKToolPath+"/"+ToolJar)
		elif isFound and _isHideJar==False and os.path.isfile(_SDKToolPath+"/"+ToolJar)==True and ToolJar.find(".jar")!=-1:
			os.remove(_SDKToolPath+"/../"+ToolJar)
	if _isHideJar==True:
		if len(MyChannelList)!=0:
			for myJar in MyChannelList:
				jarname = myJar[myJar.find("libs")+len("libs")+1:]
				shutil.copy(myJar,_SDKToolPath+"/"+jarname)
def CopySDKToFolder(_ChannelName,_AD,SDKFolder):
	DecompilePrjLocation = PythonLocation()+"/__pycache__/"+SDKFolder
	if _ChannelName!="" and _AD=="":
		if _ChannelName!="":
			SDKSmaliLocation = PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName.lower()
			SDKLocation = PythonLocation()+"/../../02_ChannelSdk/ChannelSDK/"+_ChannelName.lower()
			if _ChannelName == "system":
				SDKSmaliLocation = PythonLocation()+"/../../02_ChannelSdk/SystemSDKSmali/"+_ChannelName.lower()
				SDKLocation = PythonLocation()+"/../../02_ChannelSdk/SystemSDK/"+_ChannelName.lower()
			#BuildConfigLocation = get_desktop()+"/BuildConfig/DecompileSDK"
		if _ChannelName=="" or os.path.exists(SDKLocation) == False:
			print("make sure your channel is exist:"+_ChannelName)
			return
	elif _ChannelName=="" and _AD!="":
		if _AD!="":
			SDKSmaliLocation = PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD.lower()
			SDKLocation = PythonLocation()+"/../../02_ChannelSdk/ADSDK/"+_AD.lower()
			#BuildConfigLocation = get_desktop()+"/BuildConfig/DecompileAD"
		if _AD=="" or os.path.exists(SDKLocation) == False:
				print("make sure your _AD is exist:"+_AD)
				return
	else:
		print("make sure your _AD and _ChannelName is exist:"+_AD)
		return

	if os.path.exists(SDKSmaliLocation+"/building")==False:
		os.mkdir(SDKSmaliLocation+"/building")
	os.chdir(r''+PythonLocation()+"/__pycache__")
	if os.path.exists(SDKLocation+"/libs"):
		CopyFiles(SDKLocation+"/libs",DecompilePrjLocation+"/app/src/main/libs")
		MyJarList = ListFolder(SDKLocation+"/libs")
		for myjar in MyJarList:
			if(myjar.find(".jar")==-1):
				print("-----"+str(myjar.find(".DS_Store")))
				if(myjar.find(".DS_Store")==-1):
					CopyFiles(SDKLocation+"/libs/"+myjar, DecompilePrjLocation+"/app/src/main/jniLibs/"+myjar)
				continue
			AddJarToGradle(myjar,SDKFolder)
	AddRemoteJarToGradle(SDKLocation+"/demo.gradle",DecompilePrjLocation+"/app/build.gradle")
	os.chdir(r''+SDKFolder)
	os.system(GetPythonCommand()+" BuildAPK.py")
	os.chdir(r''+CallMethodPath)
def AddJarToGradle(JARName, SDKFolder):
	if os.path.isfile(PythonLocation()+"/__pycache__/"+SDKFolder+"/app/build.gradle"):
		file_object = open(PythonLocation()+"/__pycache__/"+SDKFolder+"/app/build.gradle",encoding="utf8")
	JavaCode=[]
	try:
		all_the_text = file_object.readlines()
		for i in all_the_text:
			f = i.replace(" ","")
			if(f.find("dependencies")!=-1):
				JavaCode.append(i)
				if CurrentPlatform()=="Windows":
					JavaCode.append("implementation files('src/main/libs/"+JARName+"')\n")
				if CurrentPlatform()=="Mac":
					JavaCode.append("implementation files('src/main/libs/"+JARName+"')\r")
			else:
				JavaCode.append(i)
	finally:
		file_object.close( )

	file_object_read = open(PythonLocation()+"/__pycache__/"+SDKFolder+"/app/build.gradle",'w',encoding="utf8")
	try:
		file_object_read.writelines(JavaCode)
	finally:
		file_object_read.close()
def AddRemoteJarToGradle(_DemoGradlePath,_buildGradlePath):
	if os.path.isfile(_DemoGradlePath):
		file_object = open(_DemoGradlePath,encoding="utf8")
	else:
		return
	JavaCode=[]
	try:
		all_the_text = file_object.readlines()
		for i in all_the_text:
			f = i.replace(" ","")
			if(f.find("implementation")!=-1):
				JavaCode.append(i)
			else:
				pass
	finally:
		file_object.close( )

	isFindStart = False
	if os.path.isfile(_buildGradlePath):
		file_object = open(_buildGradlePath,encoding="utf8")
	else:
		return
	JavaCodeGradle=[]
	try:
		all_the_text = file_object.readlines()
		for i in all_the_text:
			f = i.replace(" ","")
			if(f.find("dependencies")!=-1 and isFindStart==False):
				isFindStart=True
				JavaCodeGradle.append(i)
			elif(f.find("}")!=-1 and isFindStart==True):
				for code in JavaCode:
					JavaCodeGradle.append(code)
				if CurrentPlatform()=="Windows":
					JavaCodeGradle.append("}\n")
				if CurrentPlatform()=="Mac":
					JavaCodeGradle.append("}\r")
			else:
				JavaCodeGradle.append(i)
	finally:
		file_object.close()

	file_object_read = open(_buildGradlePath,'w',encoding="utf8")
	try:
		file_object_read.writelines(JavaCodeGradle)
	finally:
		file_object_read.close()
def CopyADSDKToFolder(_ChannelName,SDKFolder):
	if _ChannelName=="" or os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ADSDK/"+_ChannelName.lower()) == False:
		print("make sure your AD is exist:"+_ChannelName)
		return
	os.chdir(r''+PythonLocation()+"/__pycache__")
	if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ADSDK/"+_ChannelName.lower()):
		CopyFiles(PythonLocation()+"/../../02_ChannelSdk/ADSDK/"+_ChannelName.lower(),PythonLocation()+"/__pycache__/"+SDKFolder+"/app/src/main")
	AddRemoteJarToGradle(PythonLocation()+"/../../02_ChannelSdk/ADSDK/"+_ChannelName+"/demo.gradle",PythonLocation()+"/__pycache__/"+SDKFolder+"/app/build.gradle")
	os.chdir(r''+SDKFolder)
	os.system(GetPythonCommand()+" BuildAPK.py")
	os.chdir(r''+CallMethodPath)
def CopyE2WSDKToFolder(SDKFolder,JarFolder):
	if os.path.isfile(PythonLocation()+"/__pycache__/"+JarFolder+"/UnityPlugin.jar"):
		shutil.copy(PythonLocation()+"/__pycache__/"+JarFolder+"/UnityPlugin.jar",PythonLocation()+"/__pycache__/"+SDKFolder+"/app/src/main/libs/UnityPlugin.jar")
		os.chdir(r''+PythonLocation()+"/__pycache__/"+SDKFolder)
		AddRemoteJarToGradle(PythonLocation()+"/../01_MercurySDKPlugin/east2west/demo.gradle",PythonLocation()+"/__pycache__/"+SDKFolder+"/app/build.gradle")
		os.system(GetPythonCommand()+" BuildAPK.py")
		os.chdir(r''+CallMethodPath)
	else:
		print("You don't have UnityPlugin.jar")
def	ListFolder(path):
	List = []
	for i in os.listdir(path):
		List.append(i)
	return List
def CopySDKAssetsLibResSmaliToProject(De_APKName,SDKFolder,_ChannelName,_AD):
	if _ChannelName!="" and _AD=="":
		if _ChannelName!="":
			SDKSmaliLocation = PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName.lower()
			SDKLocation = PythonLocation()+"/../../02_ChannelSdk/ChannelSDK/"+_ChannelName.lower()
			#BuildConfigLocation = get_desktop()+"/BuildConfig/DecompileSDK"
			if _ChannelName=="system":
				SDKSmaliLocation = PythonLocation()+"/../../02_ChannelSdk/SystemSDKSmali/"+_ChannelName.lower()
				SDKLocation = PythonLocation()+"/../../02_ChannelSdk/SystemSDK/"+_ChannelName.lower()
		if _ChannelName=="" or os.path.exists(SDKLocation) == False:
			print("make sure your channel is exist:"+_ChannelName)
			return
	elif _ChannelName=="" and _AD!="":
		if _AD!="":
			SDKSmaliLocation = PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD.lower()
			SDKLocation = PythonLocation()+"/../../02_ChannelSdk/ADSDK/"+_AD.lower()
			#BuildConfigLocation = get_desktop()+"/BuildConfig/DecompileAD"
		if _AD=="" or os.path.exists(SDKLocation) == False:
				print("make sure your _AD is exist:"+_AD)
				return
	else:
		print("make sure your _AD and _ChannelName is exist:"+_AD)
		return
	PointCount = De_APKName.rfind(".")
	foldername=De_APKName[:PointCount]

	if os.path.exists(SDKSmaliLocation+"/assets"):
		CopyFiles(SDKSmaliLocation+"/assets",PythonLocation()+"/__pycache__/"+foldername+"/assets")

	if os.path.exists(SDKSmaliLocation+"/smali"):
		CopyFiles(SDKSmaliLocation+"/smali",PythonLocation()+"/__pycache__/"+foldername+"/smali")

	# copy res folder from SDK and combine  them
	if os.path.exists(SDKSmaliLocation+"/res"):
		ListSDKRes = FindAll(SDKSmaliLocation+"/res")
		ListGameRes = FindAll(PythonLocation()+"/__pycache__/"+foldername+"/res")
		for sdkres in ListSDKRes:
			if DeleteConflictRes(sdkres) :
				continue
			for gameres in ListGameRes:
				sdkresfile = sdkres[sdkres.rfind("/")-4:]
				gameresfile = gameres[gameres.rfind("/")-4:]
				if(sdkresfile ==gameresfile and ".xml" in sdkresfile):
					CombineRES(sdkres,gameres)
					FuncXMLOperationClass.DeleteMultiElement(gameres)
	if os.path.exists(SDKSmaliLocation+"/res"):
		CopyFilesDontOverWrite(SDKSmaliLocation+"/res",PythonLocation()+"/__pycache__/"+foldername+"/res")
	if os.path.exists(SDKSmaliLocation+"/lib"):
		CopyFiles(SDKSmaliLocation+"/lib",PythonLocation()+"/__pycache__/"+foldername+"/lib")
	if os.path.exists(SDKSmaliLocation+"/building")==True:
		DeleteFolder(SDKSmaliLocation+"/building")
def CombineRES(sdkres,gameres):
	FuncXMLOperationClass.MergeXML(gameres,sdkres)
	#if os.path.isfile(sdkres):
	#	file_objectsdk = open(sdkres,encoding=GetEncoding(sdkres))
	#JavaCode=[]
	#if os.path.isfile(gameres):
	#	file_objectgame = open(gameres,encoding=GetEncoding(gameres))
	#GameresCode=[]
	#isadd = False
	#firstLost = False

	#try:
	#	all_the_textgameCode = file_objectgame.readlines()
	#	for temp in all_the_textgameCode:
	#		if "</resources" not in temp:
	#			JavaCode.append(temp)
	#finally:
	#	pass
	#try:
	#	all_the_textsdkCode = file_objectsdk.readlines()
	#	isStartCopy = False
	#	for temp in all_the_textsdkCode:
	#		if "<resources" in temp:
	#			isStartCopy = True
	#			continue
	#		elif(isStartCopy ==True and "</resources" not in temp):
	#			JavaCode.append(temp)
	#finally:
	#	JavaCode.append("</resources>")
	#file_objectsdk.close( )
	#file_objectgame.close( )
	##print("sss:"+GetEncoding(gameres))
	#file_object_read = open(gameres,'w',encoding="utf-8")
	#try:
	#	file_object_read.writelines(JavaCode)
	#finally:
	#	file_object_read.close( )
def DeleteConflictRes(_Path):
	ConflictList=[
		"values/public.xml",
		"layout/activity_main.xml",
		"menu/main.xml"
		]
	for i in ConflictList:
		if _Path.find(i)!=-1 and MultiThread==False:
			print("Remove "+_Path)
			os.remove(_Path)
			return True
	

def CopyADSDKAssetsLibResSmaliToProject(De_APKName,SDKFolder,_AD):
	PointCount = De_APKName.rfind(".")
	foldername=De_APKName[:PointCount]
	global isFastBuild 
	global DemoProjectAPKName
	if os.path.exists(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/assets"):
		CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/assets",PythonLocation()+"/__pycache__/"+foldername+"/app/src/main/assets")
		if isFastBuild==False:
			CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/assets",get_desktop()+"/BuildConfig/DecompileSDK/assets")
	# copy res folder from SDK
	if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ADSDK/"+_AD.lower()+"/res"):
		ListSDKRes = FindAll(PythonLocation()+"/../../02_ChannelSdk/ADSDK/"+_AD.lower()+"/res")
		ListGameRes = FindAll(PythonLocation()+"/__pycache__/"+foldername+"/res")
		for sdkres in ListSDKRes:
			for gameres in ListGameRes:
				sdkresfile = sdkres[sdkres.rfind("/")-4:]
				gameresfile = gameres[gameres.rfind("/")-4:]
				if(sdkresfile ==gameresfile and ".xml" in sdkresfile):
					CombineRES(sdkres,gameres)
		#ListSDK = FindAll(PythonLocation()+"/../../02_ChannelSdk/ADSDK/"+_ChannelName.lower()+"/res")
		#ListGame = FindAll(PythonLocation()+"/__pycache__/"+foldername+"/res")
		CopyFilesDontOverWrite(PythonLocation()+"/../../02_ChannelSdk/ADSDK/"+_AD.lower()+"/res",PythonLocation()+"/__pycache__/"+foldername+"/res")
		if isFastBuild==False:
			CopyFilesDontOverWrite(PythonLocation()+"/../../02_ChannelSdk/ADSDK/"+_AD.lower()+"/res",get_desktop()+"/BuildConfig/DecompileAD/res")

	if os.path.exists(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/smali"):
		CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/smali",PythonLocation()+"/__pycache__/"+foldername+"/smali")
		if isFastBuild==False:
			CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/smali",get_desktop()+"/BuildConfig/DecompileAD/smali")
	if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD.lower()+"/lib"):
		CopyFiles(PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD.lower()+"/lib",PythonLocation()+"/__pycache__/"+foldername+"/lib")
		if isFastBuild==False:
			CopyFiles(PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD.lower()+"/lib",get_desktop()+"/BuildConfig/DecompileAD/lib")
	#get assert from jar
	JarList = FindAllByFilter(PythonLocation()+"/__pycache__/"+SDKFolder+"/app/src/main/libs","*.jar")
	os.chdir(PythonLocation()+"/__pycache__/"+SDKFolder+"/app/src/main/libs")
	for i in JarList:
		os.system(r"jar -xvf  "+i)
	os.chdir(r''+CallMethodPath)
	if os.path.exists(PythonLocation()+"/__pycache__/"+SDKFolder+"/app/src/main/libs/assets"):
		CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/app/src/main/libs/assets",PythonLocation()+"/__pycache__/"+foldername+"/assets")
		if isFastBuild==False and os.path.exists(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/assets"):
			CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/assets",get_desktop()+"/BuildConfig/DecompileAD/assets")
			CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/app/src/main/libs/assets",get_desktop()+"/BuildConfig/DecompileAD/assets")
	if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ADSDK/"+_AD.lower()+"/assets"):
		CopyFiles(PythonLocation()+"/../../02_ChannelSdk/ADSDK/"+_AD.lower()+"/assets",PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD+"/assets")
		if isFastBuild==False:
			CopyFiles(PythonLocation()+"/../../02_ChannelSdk/ADSDK/"+_AD.lower()+"/assets",get_desktop()+"/BuildConfig/DecompileAD/assets")

def isMultipleBuild(_Path):
	Count = 0
	ListFolder = FindAllInFolder(_Path)
	for i in ListFolder:
		if "EmptyDemo" in i:
			Count+=1
		if Count>=4:
			return True
	return False


def ReadXml_GetPackageName(path):
	dom = xml.dom.minidom.parse(path)
	root = dom.documentElement
	name =root.getAttribute("package")
	return name
def ChoiceSDKSmaliToCopy(De_APKName,SDKFolder,_ChannelName,_AD):
	PointCount = De_APKName.rfind(".")
	foldername=De_APKName[:PointCount]
	ListSmali = []
	if os.path.exists(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/smali/com/east2west/game"):
		ListSmali = FindAllInFolder(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/smali/com/east2west/game")
		if isFastBuild==False:
			CopyFiles(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/smali/com/east2west/game",get_desktop()+"/BuildConfig/DecompileSDK/smali/com/east2west/game")

	if os.path.exists(PythonLocation()+"/__pycache__/"+foldername+"/smali/com/east2west/game"):
		ListAPKSmali = FindAllInFolder(PythonLocation()+"/__pycache__/"+foldername+"/smali/com/east2west/game")
		for apkSmali in ListAPKSmali:
			if ".smali" in apkSmali:
				os.remove(apkSmali)
			if "System" in apkSmali:
				DeleteFolder(apkSmali)
			if "util" in apkSmali:
				DeleteFolder(apkSmali)
		for mysmali in ListSmali:
			if ".smali" in mysmali:
				PointCountSmali = mysmali.rfind("/")
				FileName=mysmali[PointCountSmali:]
				shutil.copy(mysmali,PythonLocation()+"/__pycache__/"+foldername+"/smali/com/east2west/game/"+FileName)
			if "System" in mysmali:
				CopyFiles(mysmali,PythonLocation()+"/__pycache__/"+foldername+"/smali/com/east2west/game/System")
			if "util" in mysmali:
				CopyFiles(mysmali,PythonLocation()+"/__pycache__/"+foldername+"/smali/com/east2west/game/util")
	else:
		print("Can't find E2W SDK smali")
		return

	folder = NewName.replace(".","/")
	if os.path.exists(PythonLocation()+"/__pycache__/"+foldername+"/smali/"+folder+"/wxapi")==False:
		os.makedirs(PythonLocation()+"/__pycache__/"+foldername+"/smali/"+folder+"/wxapi")
	print(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+""+"/smali/"+folder+"/wxapi")
	if os.path.exists(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+""+"/smali/"+folder+"/wxapi"):
		javaFilelist = ListFolder(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+""+"/smali/"+folder+"/wxapi")
		for i in javaFilelist:
			shutil.copy(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+""+"/smali/"+folder+"/wxapi/"+i, PythonLocation()+"/__pycache__/"+foldername+"/smali/"+folder+"/wxapi/"+i)

	if os.path.exists(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/smali/com/east2west/game/inApp"):
		ListSmali = FindAllInFolder(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/smali/com/east2west/game/inApp")
		if os.path.exists(PythonLocation()+"/__pycache__/"+foldername+"/smali/com/east2west/game/inApp"):
			ListAPKSmali = FindAllInFolder(PythonLocation()+"/__pycache__/"+foldername+"/smali/com/east2west/game/inApp")
			for apkSmali in ListAPKSmali:
				if "InApp"in apkSmali:
					os.remove(apkSmali)
			for mysmali in ListSmali:
				if "InApp"+_ChannelName.upper() in mysmali:
					PointCountSmali = mysmali.rfind("/")
					FileName=mysmali[PointCountSmali:]
					shutil.copy(mysmali,PythonLocation()+"/__pycache__/"+foldername+"/smali/com/east2west/game/inApp"+FileName)
				if "InAppBase" in mysmali:
					PointCountSmali = mysmali.rfind("/")
					FileName=mysmali[PointCountSmali:]
					shutil.copy(mysmali,PythonLocation()+"/__pycache__/"+foldername+"/smali/com/east2west/game/inApp"+FileName)
				if "InAppDefault" in mysmali:
					PointCountSmali = mysmali.rfind("/")
					FileName=mysmali[PointCountSmali:]
					shutil.copy(mysmali,PythonLocation()+"/__pycache__/"+foldername+"/smali/com/east2west/game/inApp"+FileName)
	else:
		print("Can't find SDK smali")
		return
	if _AD!="":
		#print(PythonLocation()+"/__pycache__/"+SDKFolder+"/bin/"+DemoProjectAPKName+"/smali/com/east2west/game/Show")
		#input()
		if os.path.exists(PythonLocation()+"/__pycache__/"+foldername+"/smali/com/east2west/game/Show") ==False:
			os.mkdir(PythonLocation()+"/__pycache__/"+foldername+"/smali/com/east2west/game/Show")
		if os.path.exists(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/smali/com/east2west/game/Show"):
			ListSmali = FindAllInFolder(PythonLocation()+"/__pycache__/"+SDKFolder+"/"+DemoProjectAPKName+"/smali/com/east2west/game/Show")
			#print(PythonLocation()+"/__pycache__/"+foldername+"/smali/com/east2west/game/Show")
			#input()
			if os.path.exists(PythonLocation()+"/__pycache__/"+foldername+"/smali/com/east2west/game/Show"):
				ListAPKSmali = FindAllInFolder(PythonLocation()+"/__pycache__/"+foldername+"/smali/com/east2west/game/Show")
				for apkSmali in ListAPKSmali:
					if "InAppShow"in apkSmali:
						os.remove(apkSmali)
				for mysmali in ListSmali:
					if "InAppShow"+_AD.upper() in mysmali:
						PointCountSmali = mysmali.rfind("/")
						FileName=mysmali[PointCountSmali:]
						shutil.copy(mysmali,PythonLocation()+"/__pycache__/"+foldername+"/smali/com/east2west/game/Show/"+FileName)
						#print("_AD!="":111")
						#input()
			#print(_AD)
			#input()
		else:
			print("Can't find AD smali:"+_AD)
			return

def DecompileSDK(_ChannelName,De_APKName):
	global isFastBuild
	global MultiThread
	SDKFolder = ""
	if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali")==False:
		os.mkdir(PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali")
	if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName.lower())==False:
		os.mkdir(PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName.lower())
	myList = ListFolder(PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName.lower())
	print(len(myList))
	if len(myList)==0:
		os.mkdir(PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName.lower()+"/building")
	if isFastBuild == False and MultiThread==False:
		SDKFolder = CopyEmptyDemo(SDKFolder)
		CopySDKToFolder(_ChannelName,"",SDKFolder)
		DecompileSDKAPK(_ChannelName,"",SDKFolder)
		CopySDKAssetsLibResSmaliToProject(De_APKName,SDKFolder,_ChannelName,"")
	elif  isFastBuild == True and MultiThread==False:
		if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName.lower()+"/building")==False:
			CopySDKAssetsLibResSmaliToProject(De_APKName,SDKFolder,_ChannelName,"")
		else:
			SDKFolder = CopyEmptyDemo(SDKFolder)
			CopySDKToFolder(_ChannelName,"",SDKFolder)
			DecompileSDKAPK(_ChannelName,"",SDKFolder)
			CopySDKAssetsLibResSmaliToProject(De_APKName,SDKFolder,_ChannelName,"")
	elif  isFastBuild == False and MultiThread==True:
		if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName.lower()+"/building")==False:
			CopySDKAssetsLibResSmaliToProject(De_APKName,SDKFolder,_ChannelName,"")
		else:
			print(str(os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName.lower()+"/building")))
			while os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName.lower()+"/building"):
				print("Wating for ChannelSmali")
				time.sleep(10)
			CopySDKAssetsLibResSmaliToProject(De_APKName,SDKFolder,_ChannelName,"")
	elif  isFastBuild == True and MultiThread==True:
		if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName.lower()+"/building")==False:
			CopySDKAssetsLibResSmaliToProject(De_APKName,SDKFolder,_ChannelName,"")
		else:
			while os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName.lower()+"/building"):
				print("Wating for ChannelSmali")
				time.sleep(10)
			CopySDKAssetsLibResSmaliToProject(De_APKName,SDKFolder,_ChannelName,"")
	return SDKFolder
def DecompileSystemSDK(_ChannelName,De_APKName):
	global isFastBuild
	global MultiThread
	SDKFolder = ""
	if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/SystemSDKSmali")==False:
		os.mkdir(PythonLocation()+"/../../02_ChannelSdk/SystemSDKSmali")
	if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/SystemSDKSmali/"+_ChannelName.lower())==False:
		os.mkdir(PythonLocation()+"/../../02_ChannelSdk/SystemSDKSmali/"+_ChannelName.lower())
	myList = ListFolder(PythonLocation()+"/../../02_ChannelSdk/SystemSDKSmali/"+_ChannelName.lower())
	print(len(myList))
	if len(myList)==0:
		os.mkdir(PythonLocation()+"/../../02_ChannelSdk/SystemSDKSmali/"+_ChannelName.lower()+"/building")
	if isFastBuild == False and MultiThread==False:
		SDKFolder = CopyEmptyDemo(SDKFolder)
		CopySDKToFolder(_ChannelName,"",SDKFolder)
		DecompileSDKAPK(_ChannelName,"",SDKFolder)
		CopySDKAssetsLibResSmaliToProject(De_APKName,SDKFolder,_ChannelName,"")
	elif  isFastBuild == True and MultiThread==False:
		if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/SystemSDKSmali/"+_ChannelName.lower()+"/building")==False:
			CopySDKAssetsLibResSmaliToProject(De_APKName,SDKFolder,_ChannelName,"")
		else:
			SDKFolder = CopyEmptyDemo(SDKFolder)
			CopySDKToFolder(_ChannelName,"",SDKFolder)
			DecompileSDKAPK(_ChannelName,"",SDKFolder)
			CopySDKAssetsLibResSmaliToProject(De_APKName,SDKFolder,_ChannelName,"")
	elif  isFastBuild == False and MultiThread==True:
		if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/SystemSDKSmali/"+_ChannelName.lower()+"/building")==False:
			CopySDKAssetsLibResSmaliToProject(De_APKName,SDKFolder,_ChannelName,"")
		else:
			print(str(os.path.exists(PythonLocation()+"/../../02_ChannelSdk/SystemSDKSmali/"+_ChannelName.lower()+"/building")))
			while os.path.exists(PythonLocation()+"/../../02_ChannelSdk/SystemSDKSmali/"+_ChannelName.lower()+"/building"):
				print("Wating for ChannelSmali")
				time.sleep(10)
			CopySDKAssetsLibResSmaliToProject(De_APKName,SDKFolder,_ChannelName,"")
	elif  isFastBuild == True and MultiThread==True:
		if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/SystemSDKSmali/"+_ChannelName.lower()+"/building")==False:
			CopySDKAssetsLibResSmaliToProject(De_APKName,SDKFolder,_ChannelName,"")
		else:
			while os.path.exists(PythonLocation()+"/../../02_ChannelSdk/SystemSDKSmali/"+_ChannelName.lower()+"/building"):
				print("Wating for ChannelSmali")
				time.sleep(10)
			CopySDKAssetsLibResSmaliToProject(De_APKName,SDKFolder,_ChannelName,"")
	return SDKFolder
def CopySDKSmaliToProject(De_APKName,_ChannelName):
	#copy res to project
	PointCount = De_APKName.rfind(".")
	foldername=De_APKName[:PointCount]
	if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName.lower()+"/res"):
		ListSDKRes = FindAll(PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName.lower()+"/res")
		ListGameRes = FindAll(PythonLocation()+"/__pycache__/"+foldername+"/res")
		for sdkres in ListSDKRes:
			for gameres in ListGameRes:
				sdkresfile = sdkres[sdkres.rfind("/")-4:]
				gameresfile = gameres[gameres.rfind("/")-4:]
				if(sdkresfile ==gameresfile and ".xml" in sdkresfile):
					CombineRES(sdkres,gameres)
		#ListSDK = FindAll(PythonLocation()+"/../../02_ChannelSdk/ChannelSDK/"+_ChannelName.lower()+"/res")
		#ListGame = FindAll(PythonLocation()+"/__pycache__/"+foldername+"/res")
		CopyFilesDontOverWrite(PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName.lower()+"/res",PythonLocation()+"/__pycache__/"+foldername+"/res")
	#copy smali to project
	if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName.lower()+"/smali"):
		CopyFiles(PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName.lower()+"/smali",PythonLocation()+"/__pycache__/"+foldername+"/smali")
	#copy lib to project
	if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName.lower()+"/lib"):
		CopyFiles(PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName.lower()+"/lib",PythonLocation()+"/__pycache__/"+foldername+"/lib")
def DecompileADSDK(_AD,De_APKName):
	SDKFolder = ""
	if _AD=="":
		print("Don't set AD setting Return")
		return SDKFolder
	global isFastBuild
	global MultiThread
	if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali")==False:
		os.mkdir(PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali")
	if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD.lower())==False:
		os.mkdir(PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD.lower())
	myList = ListFolder(PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD.lower())
	print(len(myList))
	if len(myList)==0:
		os.mkdir(PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD.lower()+"/building")

	if isFastBuild == False and MultiThread==False:
		SDKFolder = CopyEmptyDemo(SDKFolder)
		CopySDKToFolder("",_AD,SDKFolder)
		DecompileSDKAPK("",_AD,SDKFolder)
		CopySDKAssetsLibResSmaliToProject(De_APKName,SDKFolder,"",_AD)
	elif  isFastBuild == True and MultiThread==False:
		if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD.lower()+"/building")==False:
			CopySDKAssetsLibResSmaliToProject(De_APKName,SDKFolder,"",_AD)
		else:
			SDKFolder = CopyEmptyDemo(SDKFolder)
			CopySDKToFolder("",_AD,SDKFolder)
			DecompileSDKAPK("",_AD,SDKFolder)
			CopySDKAssetsLibResSmaliToProject(De_APKName,SDKFolder,"",_AD.lower())
	elif  isFastBuild == False and MultiThread==True:
		if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD.lower()+"/building")==False:
			CopySDKAssetsLibResSmaliToProject(De_APKName,SDKFolder,"",_AD.lower())
		else:
			while os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD.lower()+"/building"):
				print("Wating for ChannelSmali")
				time.sleep(10)
			CopySDKAssetsLibResSmaliToProject(De_APKName,SDKFolder,"",_AD.lower())
	elif  isFastBuild == True and MultiThread==True:
		if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD.lower()+"/building")==False:
			CopySDKAssetsLibResSmaliToProject(De_APKName,SDKFolder,"",_AD.lower())
		else:
			while os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD.lower()+"/building"):
				print("Wating for ChannelSmali")
				time.sleep(10)
			CopySDKAssetsLibResSmaliToProject(De_APKName,SDKFolder,"",_AD.lower())
	return SDKFolder

def DecompileE2WPlugin(De_APKName,_ChannelName,_AD):
	global MultiThread
	SDKFolder = ""
	SDKFolder = CopyEmptyDemo(SDKFolder)
	JarFolder = ""
	if os.path.isfile(r''+PythonLocation()+"/../01_MercurySDKPlugin/UnityPlugin.jar")==True:
		JarFolder = BuildE2WJar(_ChannelName,_AD)
	else:
		while(os.path.isfile(r''+PythonLocation()+"/../01_MercurySDKPlugin/UnityPlugin.jar")==False and JarChecker()):
			time.sleep(5)
			if MultiThread==False:
				JarFolder = BuildE2WJar(_ChannelName,_AD)
				if os.path.isfile(PythonLocation()+"/__pycache__/"+JarFolder+"/UnityPlugin.jar"):
					if os.path.isfile(r''+PythonLocation()+"/../01_MercurySDKPlugin/UnityPlugin.jar")==False:
						shutil.copy(PythonLocation()+"/__pycache__/"+JarFolder+"/UnityPlugin.jar",PythonLocation()+"/../01_MercurySDKPlugin/UnityPlugin.jar")
					break
			else:
				print("Waiting Building new Jar")
		print("Continue building new Jar")
		time.sleep(4)
		JarFolder = BuildE2WJar(_ChannelName,_AD)

	CopyE2WSDKToFolder(SDKFolder, JarFolder)
	DecompileE2WPluginAPK(SDKFolder,_ChannelName)
	ChoiceSDKSmaliToCopy(De_APKName,SDKFolder,_ChannelName,_AD)
	return SDKFolder
def JarChecker():
	Mylist =  ListFolder(PythonLocation()+"/../01_MercurySDKPlugin/east2west")
	for i in Mylist:
		if i.find(".jar"):
			return True
		else:
			return False
	return False
def CombineChannelXML(De_APKName,_ChannelName):
	global isTestBuild
	if isTestBuild == False:
		if os.path.isfile(PythonLocation()+"/../../"+GameConfiguration+"/AndroidManifest.xml"):
			file_object = open(PythonLocation()+"/../../"+GameConfiguration+"/AndroidManifest.xml",encoding="utf8")
	else:
		if os.path.isfile(PythonLocation()+"/../DemoProject/01_MercurySDKDemoCopy/AndroidManifest.xml"):
			file_object = open(PythonLocation()+"/../DemoProject/01_MercurySDKDemoCopy/AndroidManifest.xml",encoding="utf8")
	JavaCode=[]
	try:
		all_the_text = file_object.readlines()
		for i in all_the_text:
			f = i.replace(" ","")
			if(f.find("<!--sdk-->")!=-1):
				if CurrentPlatform()=="Windows":
					JavaCode.append("<!--sdk-->\n")
				if CurrentPlatform()=="Mac":
					JavaCode.append("<!--sdk-->\r")
				ListXML = GetChannelXml(_ChannelName,"sdk")
				for i in ListXML:
					JavaCode.append(i)
			elif(f.find("<!--sdkxml-->")!=-1):
				if CurrentPlatform()=="Windows":
					JavaCode.append("<!--sdkxml-->\n")
				if CurrentPlatform()=="Mac":
					JavaCode.append("<!--sdkxml-->\r")
				ListXML = GetChannelXml(_ChannelName,"sdkxml")
				for i in ListXML:
					JavaCode.append(i)
			else:
				JavaCode.append(i)
	finally:
		file_object.close( )
	PointCount = De_APKName.rfind(".")
	foldername=De_APKName[:PointCount]
	with open(PythonLocation()+"/__pycache__/"+foldername+"/AndroidManifest.xml",'w',encoding="utf8") as file_object_read:
		file_object_read.writelines(JavaCode)
def GetADXml(_ChannelName,sdkorxml):
	
	if _ChannelName =="":
		return
	JavaCode=[]
	if os.path.isfile(PythonLocation()+"/../../02_ChannelSdk/ADXML/"+_ChannelName+".qinxml"):
		file_object = open(PythonLocation()+"/../../02_ChannelSdk/ADXML/"+_ChannelName+".qinxml",encoding="utf8")
		
		isEnter = False
		#print(sdkorxml)
		#input()
		try:
			if sdkorxml=="ad":
				all_the_text = file_object.readlines()
				for i in all_the_text:
					i.replace(" ","")
					if i.find("<!--ad-->")!=-1:
						isEnter = True
					elif(isEnter==True and i.find("<!--end-->")==-1):
						if CurrentPlatform()=="Windows":
							JavaCode.append(i+"\n")
						if CurrentPlatform()=="Mac":
							JavaCode.append(i+"\r")
					elif(isEnter==True and i.find("<!--end-->")!=-1):
						break
			else:
				all_the_text = file_object.readlines()
				for i in all_the_text:
					i.replace(" ","")
					if i.find("<!--adxml-->")!=-1:
						isEnter = True
					elif(isEnter==True and i.find("<!--end-->")==-1):
						if CurrentPlatform()=="Windows":
							JavaCode.append(i+"\n")
						if CurrentPlatform()=="Mac":
							JavaCode.append(i+"\r")
					elif(isEnter==True and i.find("<!--end-->")!=-1):
						break
		finally:
			if file_object!=None:
				file_object.close( )
	return JavaCode

def GetSystemXml(_ChannelName,sdkorxml):
	if os.path.isfile(PythonLocation()+"/../../02_ChannelSdk/SystemXML/"+_ChannelName+".qinxml"):
		file_object = open(PythonLocation()+"/../../02_ChannelSdk/SystemXML/"+_ChannelName+".qinxml",encoding="utf8")
	JavaCode=[]
	isEnter = False
	#print(sdkorxml)
	#input()
	try:
		if sdkorxml=="system":
			all_the_text = file_object.readlines()
			for i in all_the_text:
				i.replace(" ","")
				if i.find("<!--system-->")!=-1:
					isEnter = True
				elif(isEnter==True and i.find("<!--end-->")==-1):
					if CurrentPlatform()=="Windows":
						JavaCode.append(i+"\n")
					if CurrentPlatform()=="Mac":
						JavaCode.append(i+"\r")
				elif(isEnter==True and i.find("<!--end-->")!=-1):
					break
		else:
			all_the_text = file_object.readlines()
			for i in all_the_text:
				i.replace(" ","")
				if i.find("<!--systemxml-->")!=-1:
					isEnter = True
				elif(isEnter==True and i.find("<!--end-->")==-1):
					if CurrentPlatform()=="Windows":
						JavaCode.append(i+"\n")
					if CurrentPlatform()=="Mac":
						JavaCode.append(i+"\r")
				elif(isEnter==True and i.find("<!--end-->")!=-1):
					break
	finally:
		file_object.close( )
	return JavaCode
def GetEncoding(_Path):
	myfile = open(_Path,'rb')
	data = myfile.read()
	di= chardet.detect(data)
	myfile.close()
	myencoding = di["encoding"]
	if myencoding==None:
		myencoding ="utf-8"
	return myencoding
def GetChannelXml(_ChannelName,sdkorxml):
	if os.path.isfile(PythonLocation()+"/../../02_ChannelSdk/ChannelXML/"+_ChannelName+".qinxml"):
		file_object = open(PythonLocation()+"/../../02_ChannelSdk/ChannelXML/"+_ChannelName+".qinxml",encoding=GetEncoding(PythonLocation()+"/../../02_ChannelSdk/ChannelXML/"+_ChannelName+".qinxml"))
	if os.path.isfile(PythonLocation()+"/../../"+GameConfiguration+"/ChannelXML/"+_ChannelName+".qinxml"):
		file_object = open(PythonLocation()+"/../../"+GameConfiguration+"/ChannelXML/"+_ChannelName+".qinxml",encoding=GetEncoding(PythonLocation()+"/../../02_ChannelSdk/ChannelXML/"+_ChannelName+".qinxml"))
	JavaCode=[]
	isEnter = False
	#print(sdkorxml)
	#input()
	try:
		if sdkorxml=="sdk":
			all_the_text = file_object.readlines()
			for i in all_the_text:
				i.replace(" ","")
				if i.find("<!--sdk-->")!=-1:
					isEnter = True
				elif(isEnter==True and i.find("<!--end-->")==-1):
					if CurrentPlatform()=="Windows":
						JavaCode.append(i+"\n")
					if CurrentPlatform()=="Mac":
						JavaCode.append(i+"\r")
				elif(isEnter==True and i.find("<!--end-->")!=-1):
					break
		else:
			all_the_text = file_object.readlines()
			for i in all_the_text:
				i.replace(" ","")
				if i.find("<!--sdkxml-->")!=-1:
					isEnter = True
				elif(isEnter==True and i.find("<!--end-->")==-1):
					if CurrentPlatform()=="Windows":
						JavaCode.append(i+"\n")
					if CurrentPlatform()=="Mac":
						JavaCode.append(i+"\r")
				elif(isEnter==True and i.find("<!--end-->")!=-1):
					break
	finally:
		file_object.close( )
	return JavaCode
def XML_METE_CHANGE(_Path,Key, value):
	dom = xml.dom.minidom.parse(_Path)
	root = dom.documentElement
	#name =root.getAttribute("package")
	name  = root.getElementsByTagName("meta-data")
	global FoundKey
	FoundKey=False
	for node in name:
		nameofname = node.getAttribute("android:name")
		if nameofname==Key:
			#nameofvalue = node.getAttribute("android:value")
			node.setAttribute("android:value",value)
			FoundKey=True
	if FoundKey==False:
		print("[ERROR] Can't find "+Key)
	try:
		with open(_Path,'w',encoding='UTF-8') as _Path:
			dom.writexml(_Path,indent='',addindent='',newl='',encoding='UTF-8')
	except Exception as err:
		print("ERROR[{0}".format(err))
def XML_Application_CHANGE(_Path,Key, value):
	dom = xml.dom.minidom.parse(_Path)
	root = dom.documentElement
	#name =root.getAttribute("package")
	#name  = root.getElementsByTagName("meta-data")
	root.setAttribute(Key,value)
	try:
		with open(_Path,'w',encoding='UTF-8') as _Path:
			dom.writexml(_Path,indent='',addindent='',newl='',encoding='UTF-8')
	except Exception as err:
		print("ERROR[{0}".format(err))
def XML_YML_CHANGE(_Path,VersionName,VersionCode):
	myfile = open(_Path,'rb')
	data = myfile.read()
	di= chardet.detect(data)
	myfile.close()
	if di["encoding"]=="UTF-8-SIG":
		file_object = open(_Path,'r',encoding="UTF-8-SIG")
		#print(di["encoding"])
	if di["encoding"]=="GB2312":
		file_object = open(_Path,'r',encoding="GB2312")
		#print(di["encoding"])
	if di["encoding"]=="utf-8":
		file_object = open(_Path,'r',encoding="utf-8")
	if di["encoding"]=="ascii":
		file_object = open(_Path,'r',encoding="ascii")
		#print(di["encoding"])
	JavaCode=[]
	try:
		all_the_text = file_object.readlines()
		for i in all_the_text:
			#print(i)
			if "versionCode" in i:
				#print("___VersionCode="+VersionCode)
				if CurrentPlatform()=="Windows":
					JavaCode.append("  versionCode: '"+VersionCode+"'\n")
				if CurrentPlatform()=="Mac":
					JavaCode.append("  versionCode: '"+VersionCode+"'\r")
			elif "versionName" in i:
				#print("___VersionName="+VersionName)
				if CurrentPlatform()=="Windows":
					JavaCode.append("  versionName: '"+VersionName+"'\n")
				if CurrentPlatform()=="Mac":
					JavaCode.append("  VersionName: '"+VersionName+"'\r")
			else:
				JavaCode.append(i)
	finally:
		file_object.close( )
	
	if di["encoding"]=="UTF-8-SIG":
		file_object_read = open(_Path,'w',encoding="UTF-8-SIG")
		print(di["encoding"])
	if di["encoding"]=="GB2312":
		file_object_read = open(_Path,'w',encoding="GB2312")
		print(di["encoding"])
	if di["encoding"]=="utf-8":
		file_object_read = open(_Path,'w',encoding="utf-8")
		print(di["encoding"])
	if di["encoding"]=="ascii":
		file_object_read = open(_Path,'w',encoding="ascii")
		print(di["encoding"])
	#file_object_read = open(_Path,'w')
	try:
		#print(JavaCode)
		file_object_read.writelines(JavaCode)
	finally:
		file_object_read.close( )
def CombineADXML(De_APKName,_ADName):
	if _ADName == "":
		return
	PointCount = De_APKName.rfind(".")
	foldername=De_APKName[:PointCount]
	if os.path.isfile(PythonLocation()+"/__pycache__/"+foldername+"/AndroidManifest.xml"):
		file_object = open(PythonLocation()+"/__pycache__/"+foldername+"/AndroidManifest.xml",encoding="utf8")
	JavaCode=[]
	try:
		all_the_text = file_object.readlines()
		for i in all_the_text:
			f = i.replace(" ","")
			if(f.find("<!--sdk-->")!=-1):
				if CurrentPlatform()=="Windows":
					JavaCode.append("<!--sdk-->\n")
				if CurrentPlatform()=="Mac":
					JavaCode.append("<!--sdk-->\r")
				ListXML = GetADXml(_ADName,"ad")
				for i in ListXML:
					JavaCode.append(i)
			elif(f.find("<!--sdkxml-->")!=-1):
				if CurrentPlatform()=="Windows":
					JavaCode.append("<!--sdkxml-->\n")	
				if CurrentPlatform()=="Mac":
					JavaCode.append("<!--sdkxml-->\r")	
				ListXML = GetADXml(_ADName,"adxml")
				for i in ListXML:
					JavaCode.append(i)
			else:
				JavaCode.append(i)
	finally:
		file_object.close( )
	
	file_object_read = open(PythonLocation()+"/__pycache__/"+foldername+"/AndroidManifest.xml",'w',encoding="utf8")
	#print(PythonLocation()+"/__pycache__/"+foldername+"/AndroidManifest.xml")
	try:
		file_object_read.writelines(JavaCode)
	finally:
		file_object_read.close()
def  CombineSystemXML(De_APKName):
	PointCount = De_APKName.rfind(".")
	foldername=De_APKName[:PointCount]
	if os.path.isfile(PythonLocation()+"/__pycache__/"+foldername+"/AndroidManifest.xml"):
		file_object = open(PythonLocation()+"/__pycache__/"+foldername+"/AndroidManifest.xml",encoding="utf8")
	JavaCode=[]
	try:
		all_the_text = file_object.readlines()
		for i in all_the_text:
			f = i.replace(" ","")
			if(f.find("<!--sdk-->")!=-1):
				if CurrentPlatform()=="Windows":
					JavaCode.append("<!--sdk-->\n")
				if CurrentPlatform()=="Mac":
					JavaCode.append("<!--sdk-->\r")
				ListXML = GetSystemXml("system","system")
				for i in ListXML:
					JavaCode.append(i)
			elif(f.find("<!--sdkxml-->")!=-1):
				if CurrentPlatform()=="Windows":
					JavaCode.append("<!--sdkxml-->\n")
				if CurrentPlatform()=="Mac":
					JavaCode.append("<!--sdkxml-->\r")
				ListXML = GetSystemXml("system","systemxml")
				for i in ListXML:
					JavaCode.append(i)
			else:
				JavaCode.append(i)
	finally:
		file_object.close( )
	
	file_object_read = open(PythonLocation()+"/__pycache__/"+foldername+"/AndroidManifest.xml",'w',encoding="utf8")
	#print(PythonLocation()+"/__pycache__/"+foldername+"/AndroidManifest.xml")
	try:
		file_object_read.writelines(JavaCode)
	finally:
		file_object_read.close()
def XML_SpecialPackageName(_XML_Path,_Packagename):
	if os.path.isfile(_XML_Path):
		file_object = open(_XML_Path,encoding="utf8")
	JavaCode=[]
	try:
		all_the_text = file_object.readlines()
		for i in all_the_text:
			f = i.replace(" ","")
			if(f.find("${applicationId}")!=-1 or f.find("UnityPackageName")!=-1):
				i = i.replace("${applicationId}",_Packagename)
				i = i.replace("UnityPackageName",_Packagename)
				if CurrentPlatform()=="Windows":
					JavaCode.append(i+"\n")
				if CurrentPlatform()=="Mac":
					JavaCode.append(i+"\r")
				
			else:
				JavaCode.append(i)
	finally:
		file_object.close( )
	file_object_read = open(_XML_Path,'w',encoding="utf8")
	#print(PythonLocation()+"/__pycache__/"+foldername+"/AndroidManifest.xml")
	try:
		file_object_read.writelines(JavaCode)
	finally:
		file_object_read.close()
def MofiyXML(De_APKName,_ChannelName,_Packagename,_VersionName,_VersionCode):
	global isFastBuild 
	PointCount = De_APKName.rfind(".")
	foldername=De_APKName[:PointCount]
	_XML_Path = PythonLocation()+"/__pycache__/"+foldername+"/AndroidManifest.xml"
	XML_METE_CHANGE(_XML_Path,"E2W_NUMBER","east2west1")
	XML_METE_CHANGE(_XML_Path,"E2W_LOG","open")
	XML_METE_CHANGE(_XML_Path,"CHANNEL_NAME",_ChannelName)
	XML_Application_CHANGE(_XML_Path,"package",_Packagename)
	XML_Application_CHANGE(_XML_Path,"android:versionName",_VersionName)
	XML_Application_CHANGE(_XML_Path,"android:versionCode",_VersionCode)
	XML_YML_CHANGE(PythonLocation()+"/__pycache__/"+foldername+"/apktool.yml",_VersionName,_VersionCode)
	XML_SpecialPackageName(_XML_Path,_Packagename)
	if isFastBuild==False and MultiThread ==False:
		shutil.copy(PythonLocation()+"/__pycache__/"+foldername+"/AndroidManifest.xml",get_desktop()+"/BuildConfig/DecompileSDK/AndroidManifest.xml")
def CreateXML(De_APKName,_ChannelName,_AD,_Packagename,_VersionName,_VersionCode):
	CombineChannelXML(De_APKName,_ChannelName)
	CombineADXML(De_APKName,_AD)
	#input()
	CombineSystemXML(De_APKName)
	#input()
	SettingName = GetPackageNameJson(_ChannelName)
	if SettingName!="":
		_Packagename = SettingName
	MofiyXML(De_APKName,_ChannelName,_Packagename,_VersionName,_VersionCode)
def GetPackageNameJson(_ChannelName):
	if os.path.isfile(PythonLocation()+"/../../"+GameConfiguration+"/package.json"):
		readed = json.load(open(PythonLocation()+"/../../"+GameConfiguration+"/package.json", 'r',encoding=GetEncoding(PythonLocation()+"/../../"+GameConfiguration+"/package.json")))
		SettingPackageName = ""
		try:
			SettingPackageName = readed[_ChannelName]
		except:
			print("No such channel:"+_ChannelName)
		return SettingPackageName
	else:
		return ""

def get_desktop():
	return os.path.join(os.path.expanduser("~"), 'Desktop')
def run(param1,param2):
	print("Thread is working....." + param1)
	print("Thread is working....." + param2)
	os.system("adb install -r "+param1)
	os.system("adb shell am start -n "+param2)
	
def RebuildAPK(De_APKName,Build_APKname,LocationAPK,_ChannelName,_AD):
	global isFastBuild 
	PointCount = De_APKName.rfind(".")
	foldername = De_APKName[:PointCount]
	os.chdir(r''+PythonLocation()+"/__pycache__")
	#print(PythonLocation()+"/../../03_Appurtenance/Tool/apktool b "+foldername)
	if os.path.isfile(PythonLocation()+"/../../"+GameConfiguration+"/ChannelICON/"+_ChannelName+"/app_icon.png") == True:
		Mylist = ListFolder(PythonLocation()+"/__pycache__/"+foldername+"/res")
		for res in Mylist: 
			if "drawable" in res:
				shutil.copy(PythonLocation()+"/../../"+GameConfiguration+"/ChannelICON/"+_ChannelName+"/app_icon.png", PythonLocation()+"/__pycache__/"+foldername+"/res/"+res+"/app_icon.png")
	elif os.path.isfile(PythonLocation()+"/../../"+GameConfiguration+"/app_icon.png") == True:
		Mylist = ListFolder(PythonLocation()+"/__pycache__/"+foldername+"/res")
		for res in Mylist: 
			if "drawable" in res:
				shutil.copy(PythonLocation()+"/../../"+GameConfiguration+"/app_icon.png", PythonLocation()+"/__pycache__/"+foldername+"/res/"+res+"/app_icon.png")
	#copy refix Decompile File
	if os.path.exists(r''+PythonLocation()+"/__pycache__/"+foldername+"/res/layout-v26"):
		CopyFiles(r''+PythonLocation()+"/DecompileFiles/res/layout-v26",PythonLocation()+"/__pycache__/"+foldername+"/res/layout-v26")

	#copy decompile version
	if os.path.exists(r''+PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName+"/assets"):
		CopyFiles(r''+PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName+"/assets",PythonLocation()+"/__pycache__/"+foldername+"/assets")
	if _AD !="":
		if os.path.exists(r''+PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD+"/assets"):
			CopyFiles(r''+PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD+"/assets",PythonLocation()+"/__pycache__/"+foldername+"/assets")
	if os.path.isfile(PythonLocation()+"/../../02_ChannelSdk/ChannelTemplate/"+_ChannelName) == True:
		shutil.copy(PythonLocation()+"/../../02_ChannelSdk/ChannelTemplate/"+_ChannelName,PythonLocation()+"/__pycache__/"+foldername+"/assets/"+_ChannelName)
	if os.path.isfile(PythonLocation()+"/../../"+GameConfiguration+"/ChannelTemplate/"+_ChannelName) == True:
		shutil.copy(PythonLocation()+"/../../"+GameConfiguration+"/ChannelTemplate/"+_ChannelName, PythonLocation()+"/__pycache__/"+foldername+"/assets/"+_ChannelName)
	if os.path.isfile(PythonLocation()+"/../../"+GameConfiguration+"/ProductMap") == True:
		shutil.copy(PythonLocation()+"/../../"+GameConfiguration+"/ProductMap", PythonLocation()+"/__pycache__/"+foldername+"/assets/ProductMap")
	if os.path.isfile(PythonLocation()+"/../../"+GameConfiguration+"/GameConfiguration") == True:
		shutil.copy(PythonLocation()+"/../../"+GameConfiguration+"/GameConfiguration", PythonLocation()+"/__pycache__/"+foldername+"/assets/GameConfiguration")
	CopyChannelDecompileFiles(De_APKName,GameConfiguration,_ChannelName)

	BalanceSmali(PythonLocation()+"/__pycache__/"+foldername)

	os.system(PythonLocation()+"/../../03_Appurtenance/Tool/apktool b "+foldername)
	os.chdir(r''+PythonLocation()+"/__pycache__/"+foldername+"/dist")
	ResigneAPK(PythonLocation()+"/__pycache__/"+foldername+"/dist/"+foldername+".apk",PythonLocation()+"/__pycache__/Signed_"+foldername+".apk")
	if os.path.exists(get_desktop()+"/GameAPK/")==False:
		os.mkdir(get_desktop()+"/GameAPK/")
	timestring  = time.strftime('%Y%m%d%H%M',time.localtime(time.time()))
	add= ""
	t = time.time()
	nowTime = lambda:int(round(t * 1000))
	count = nowTime()
	print("Try To Copy Temp Empty Folder")
	count+=random.randint(1, 20000)
	if MultiThread==True:
		add = "_"+str(count)
	MoveAPK(PythonLocation()+"/__pycache__/Signed_"+foldername+".apk", get_desktop()+"/GameAPK/"+LocationAPK+"_"+_ChannelName+"_"+timestring+"_Decompile"+add+".apk")
	if isFastBuild==True and MultiThread==False:
		thread1 = threading.Thread(target=run,name="线程1",args=(get_desktop()+"/GameAPK/"+LocationAPK+"_"+_ChannelName+"_"+timestring+"_Decompile"+add+".apk",GetPackageName(PythonLocation()+"/__pycache__/"+foldername+"/AndroidManifest.xml")+"/"+GetActivity(PythonLocation()+"/__pycache__/"+foldername+"/AndroidManifest.xml")))
		#创建线程完毕之后，一定要启动
		thread1.start()
	if isFastBuild == False:
		os.system("jar xf "+foldername+".apk")
		CopyThings =""
		if os.path.isfile(r''+PythonLocation()+"/__pycache__/"+foldername+"/dist/classes.dex"):
			CopyThings += " "
			CopyThings += "classes.dex"
		if os.path.isfile(r''+PythonLocation()+"/__pycache__/"+foldername+"/dist/classes2.dex"):
			CopyThings += " "
			CopyThings += "classes2.dex"
		if os.path.isfile(r''+PythonLocation()+"/__pycache__/"+foldername+"/dist/resources.arsc"):
			CopyThings += " "
			CopyThings += "resources.arsc"
		if os.path.isfile(r''+PythonLocation()+"/__pycache__/"+foldername+"/dist/AndroidManifest.xml"):
			CopyThings += " "
			CopyThings += "AndroidManifest.xml"
		if os.path.exists(r''+PythonLocation()+"/__pycache__/"+foldername+"/dist/lib"):
			CopyThings += " "
			CopyThings += "lib"
		if os.path.exists(r''+PythonLocation()+"/__pycache__/"+foldername+"/dist/res"):
			CopyThings += " "
			CopyThings += "res"
		if os.path.isfile(PythonLocation()+"/../../"+GameConfiguration+"/ChannelICON/"+_ChannelName+"/app_icon.png") == True:
			Mylist = ListFolder(PythonLocation()+"/__pycache__/"+foldername+"/res")
			for res in Mylist: 
				if "drawable" in res:
					if os.path.exists(PythonLocation()+"/__pycache__/"+foldername+"/dist/res/"+res):
						shutil.copy(PythonLocation()+"/../../"+GameConfiguration+"/ChannelICON/"+_ChannelName+"/app_icon.png", PythonLocation()+"/__pycache__/"+foldername+"/dist/res/"+res+"/app_icon.png")
		elif os.path.isfile(PythonLocation()+"/../../"+GameConfiguration+"/app_icon.png") == True:
			Mylist = ListFolder(PythonLocation()+"/__pycache__/"+foldername+"/res")
			for res in Mylist: 
				if "drawable" in res:
					shutil.copy(PythonLocation()+"/../../"+GameConfiguration+"/app_icon.png", PythonLocation()+"/__pycache__/"+foldername+"/dist/res/"+res+"/app_icon.png")
		HaveAssets = False
		if os.path.exists(r''+PythonLocation()+"/__pycache__/"+foldername+"/dist/assets"):
			DeleteFolder(PythonLocation()+"/__pycache__/"+foldername+"/dist/assets")
			os.mkdir(PythonLocation()+"/__pycache__/"+foldername+"/dist/assets")

		if os.path.exists(r''+PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName+"/assets"):
			CopyFiles(r''+PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName+"/assets",PythonLocation()+"/__pycache__/"+foldername+"/dist/assets")
			HaveAssets=True
		if os.path.exists(PythonLocation()+"/../../"+GameConfiguration+"/ChannelDecompile/"+_ChannelName) ==True:
			CopyFilesOverwrite(PythonLocation()+"/../../"+GameConfiguration+"/ChannelDecompile/"+_ChannelName,PythonLocation()+"/__pycache__/"+foldername+"/dist")
		if _AD !="":
			if os.path.exists(r''+PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD+"/assets"):
				CopyFiles(r''+PythonLocation()+"/../../02_ChannelSdk/ADSDKSmali/"+_AD+"/assets",PythonLocation()+"/__pycache__/"+foldername+"/dist/assets")
			HaveAssets=True
		if os.path.isfile(PythonLocation()+"/../../02_ChannelSdk/ChannelTemplate/"+_ChannelName) == True:
			shutil.copy(PythonLocation()+"/../../02_ChannelSdk/ChannelTemplate/"+_ChannelName,PythonLocation()+"/__pycache__/"+foldername+"/dist/assets/"+_ChannelName)
			HaveAssets=True
		if os.path.isfile(PythonLocation()+"/../../"+GameConfiguration+"/ChannelTemplate/"+_ChannelName) == True:
			shutil.copy(PythonLocation()+"/../../"+GameConfiguration+"/ChannelTemplate/"+_ChannelName, PythonLocation()+"/__pycache__/"+foldername+"/dist/assets/"+_ChannelName)
			HaveAssets=True
		if os.path.isfile(PythonLocation()+"/../../"+GameConfiguration+"/ProductMap") == True:
			shutil.copy(PythonLocation()+"/../../"+GameConfiguration+"/ProductMap", PythonLocation()+"/__pycache__/"+foldername+"/dist/assets/ProductMap")
			HaveAssets=True
		if os.path.isfile(PythonLocation()+"/../../"+GameConfiguration+"/GameConfiguration") == True:
			shutil.copy(PythonLocation()+"/../../"+GameConfiguration+"/GameConfiguration", PythonLocation()+"/__pycache__/"+foldername+"/dist/assets/GameConfiguration")
			HaveAssets=True

		if os.path.exists(PythonLocation()+"/../../"+GameConfiguration+"/ChannelDecompile/"+_ChannelName) ==True:
			CopyFiles(PythonLocation()+"/../../"+GameConfiguration+"/ChannelDecompile/"+_ChannelName,PythonLocation()+"/__pycache__/"+foldername+"/dist")
		if HaveAssets==True:
			CopyThings += " "
			CopyThings += "assets"

		#print("jar uvf "+PythonLocation()+"/__pycache__/"+Build_APKname + CopyThings)
		os.system("jar uvf "+PythonLocation()+"/__pycache__/"+Build_APKname + CopyThings)
		ResigneAPK(PythonLocation()+"/__pycache__/"+Build_APKname,PythonLocation()+"/__pycache__/Signed_"+Build_APKname)
		if os.path.exists(get_desktop()+"/GameAPK/")==False:
			os.mkdir(get_desktop()+"/GameAPK/")

		MoveAPK(PythonLocation()+"/__pycache__/Signed_"+Build_APKname,get_desktop()+"/GameAPK/"+LocationAPK+"_"+_ChannelName+"_"+time.strftime('%Y%m%d%H%M',time.localtime(time.time()))+add+".apk")
		
		os.chdir(r''+CallMethodPath)
def BalanceSmali(_Path):
	if isMulitDex==False:
		return
	if(os.path.exists(_Path+"/smali_classes2")==False):
		os.mkdir(_Path+"/smali_classes2")
	List = ListFolder(_Path+"/smali")
	SaveSize=0
	BigestList = []
	for i in List:
		size = getdirsize(_Path+"/smali/"+i)
		if SaveSize<size:
			SaveSize=size
			BigestList = i
			#print("Bigest = "+_Path+"/smali/"+i)
		#print("i:"+str(size))
	shutil.move(_Path+"/smali/"+BigestList,_Path+"/smali_classes2/"+BigestList)
def getdirsize(dir):
	size = 0
	for root, dirs, files in os.walk(dir):
		dirs
		size += sum([getsize(join(root, name)) for name in files])
	return size
def GetActivity(_APKLocation):
	dom = xml.dom.minidom.parse(_APKLocation)
	root = dom.documentElement
	#name =root.getAttribute("package")
	name  = root.getElementsByTagName("activity")
	global FoundKey
	nameofvalue=""
	FoundKey=False
	for node in name:
		nameofvalue = node.getAttribute("android:name")
		break
	return nameofvalue
def GetPackageName(_APKLocation):
	_Path = _APKLocation
	#print("--------------"+_Path)
	dom = xml.dom.minidom.parse(_Path)
	root = dom.documentElement
	stringForTem = root.getAttribute("package")
	return stringForTem
def DeleteSignature(_APKLocation):
	#os.chdir(r''+"C:/MyProject/AgileTools")
	#APKname = _APKLocation.split("/")[-1]
	#PointCount = APKname.rfind(".")
	#foldername=APKname[:PointCount]
	your_delet_file="META-INF"
	
	old_zipfile=_APKLocation #旧文件
	new_zipfile=_APKLocation+"temp" #新文件
	zin = zipfile.ZipFile (old_zipfile, 'r') #读取对象
	zout = zipfile.ZipFile (new_zipfile, 'w') #被写入对象
	for item in zin.infolist():
		buffer = zin.read(item.filename)
		if ((your_delet_file in item.filename) and (".RSA" in item.filename)) or ((your_delet_file in item.filename)and (".SF" in item.filename)) or ((your_delet_file in item.filename) and (".MF" in item.filename)):  #剔除要删除的文件
			pass
		else:
			zout.writestr(item, buffer) #把文件写入到新对象中
	zout.close()
	zin.close()
	print("Deleted Signature")
	#用新文件覆盖旧文件
	shutil.move(new_zipfile,old_zipfile)
def FindKeyStore():
	list = ListFolder(PythonLocation()+"/../../"+GameConfiguration)
	for i in list:
		if i.find(".keystore")!=-1:
			return PythonLocation()+"/../../"+GameConfiguration+"/"+i
	return PythonLocation()+"/../../03_Appurtenance/grannysmith.keystore"
def ResigneAPK(res, des):
	DeleteSignature(res)
	keyplace = FindKeyStore()
	print("Using Keystore:"+FindKeyStore())
	resourceAPK = res
	signedAPKPlace = des
	if os.path.isfile(PythonLocation()+"/copy_signed.apk"):
		os.remove(PythonLocation()+"/copy_signed.apk")
	os.system("jarsigner -verbose -keystore " + keyplace +" -storepass hello123456 -signedjar " + signedAPKPlace + " -digestalg SHA1 -sigalg MD5withRSA " + resourceAPK + " android.keystore")
def CheckResource(_ChannelName,_AD):
	if os.path.isfile(PythonLocation()+"/../../02_ChannelSdk/ChannelXML/"+_ChannelName+".qinxml")==False:
		print("Don't have xml:"+_ChannelName+".qinxml")
		#input()
		return False
	if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ChannelSDK/"+_ChannelName)==False:
		print("Don't have sdk folder:"+_ChannelName)
		#input()
		return False
	#if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ChannelSDKSmali/"+_ChannelName)==False and isFastBuild == True:
	#	print("Don't have sdk smali folder:"+_ChannelName)
	#	#input()
	#	return False
	MyJava = ListFolder(PythonLocation()+"/../../01_MercurySDK/01_MercurySDKPlugin/east2west/src/main/java/com/east2west/game/inApp")
	isFindJava = False
	for i in MyJava:
		if "InApp"+_ChannelName.upper()+".java" in i:
			isFindJava = True
	if isFindJava == False:
		print("Don't have sdk code:"+"InApp"+_ChannelName+".java")
		#input()
		return False
	MyJava = ListFolder(PythonLocation()+"/../../01_MercurySDK/01_MercurySDKPlugin/east2west/src/main/java/com/east2west/game/Show")
	isFindJava = False
	if _AD !="":
		for i in MyJava:
			if "InAppShow"+_AD.upper()+".java" in i:
				isFindJava = True
		if isFindJava == False:
			print("Don't have AD sdk code:"+"InAppShow"+_AD+".java")
			#input()
			return False
	return True
def MoveAPK(res, des):
	if os.path.isfile(res):
		if os.path.isfile(des):
			os.remove(des)
		shutil.move(res,des)
def CleanProject(folderName1,folderName2,folderName3,De_APKName,APKname,Build_APKname,_AD,_ChannelName):

	#print("folderName1="+folderName1)
	#print("folderName2="+folderName2)
	#print("De_APKName="+De_APKName)

	
	PointCount = De_APKName.rfind(".")
	foldername_De_APKName=De_APKName[:PointCount]
	
	#print("De_APKName="+De_APKName)
	#print("APKname="+APKname)
	#print("Build_APKname="+Build_APKname)
	
	#print("folderName1="+folderName1)
	#print("folderName2="+folderName2)
	#print("foldername_De_APKName="+foldername_De_APKName)
	if os.path.isfile(PythonLocation()+"/__pycache__/"+De_APKName):
		os.remove(PythonLocation()+"/__pycache__/"+De_APKName)
	if os.path.isfile(PythonLocation()+"/__pycache__/"+APKname):
		os.remove(PythonLocation()+"/__pycache__/"+APKname)
	if os.path.isfile(PythonLocation()+"/__pycache__/"+Build_APKname):
		os.remove(PythonLocation()+"/__pycache__/"+Build_APKname)

	if os.path.exists(PythonLocation()+"/__pycache__/"+folderName1):
		DeleteFolder(PythonLocation()+"/__pycache__/"+folderName1)
	if os.path.exists(PythonLocation()+"/__pycache__/"+folderName2):
		DeleteFolder(PythonLocation()+"/__pycache__/"+folderName2)
	if os.path.exists(PythonLocation()+"/__pycache__/"+folderName3) and folderName3!="" :
		DeleteFolder(PythonLocation()+"/__pycache__/"+folderName3)
	if os.path.exists(get_desktop()+"/BuildConfig/DecompileFolder"):
		DeleteFolder(get_desktop()+"/BuildConfig/DecompileFolder")
	if os.path.exists(PythonLocation()+"/__pycache__/"+foldername_De_APKName) and isFastBuild==False and MultiThread==False:
		CopyFiles(PythonLocation()+"/../../02_ChannelSdk/ChannelSDK/"+_ChannelName.lower(),get_desktop()+"/BuildConfig/CompilesSDK")
		shutil.copy(PythonLocation()+"/../01_MercurySDKPlugin/UnityPlugin.jar",get_desktop()+"/BuildConfig/CompilesSDK/libs/UnityPlugin.jar")
		shutil.copy(PythonLocation()+"/../01_MercurySDKPlugin/UnityPlugin.jar",get_desktop()+"/BuildConfig/CompilesSDK/libs/UnityPlugin.jar")
		shutil.copy(get_desktop()+"/BuildConfig/DecompileSDK/AndroidManifest.xml",get_desktop()+"/BuildConfig/CompilesSDK/AndroidManifest.xml")
		if os.path.exists(PythonLocation()+"/../../02_ChannelSdk/ADSDK/"+_AD.lower()) and _AD !="":
			CopyFiles(PythonLocation()+"/../../02_ChannelSdk/ADSDK/"+_AD.lower(), get_desktop()+"/BuildConfig/CompilesSDK")
		CopyFiles(PythonLocation()+"/__pycache__/"+foldername_De_APKName, get_desktop()+"/BuildConfig/DecompileFolder" )
	if os.path.exists(PythonLocation()+"/__pycache__/"+foldername_De_APKName):
		DeleteFolder(PythonLocation()+"/__pycache__/"+foldername_De_APKName)
def CopyChannelDecompileFiles(De_APKName,_Configuration,_ChannelName):
	PointCount = De_APKName.rfind(".")
	foldername=De_APKName[:PointCount]
	if os.path.exists(PythonLocation()+"/../../"+_Configuration+"/ChannelDecompile/"+_ChannelName) ==True:
		CopyFilesOverwrite(PythonLocation()+"/../../"+_Configuration+"/ChannelDecompile/"+_ChannelName,PythonLocation()+"/__pycache__/"+foldername)
@CallMethodLocation("")
def BuildChannelAPK(_APKLocation,_ChannelName,_AD,_Packagename,_VersionName,_VersionCode,_Configuration,_IOSPath):
	starttime = datetime.datetime.now()
	_ChannelName = _ChannelName.lower()
	if ADJsonReadChannel(_ChannelName,_Configuration)!="":
		_AD = ADJsonReadChannel(_ChannelName,_Configuration)
		_AD =_AD.lower()
	_AD =_AD.lower()
	global GameConfiguration
	global GameVersionName
	global GameVersionCode
	global NewName
	global isFastBuild
	global MultiThread
	global isMulitDex
	GameVersionName = _VersionName
	GameVersionCode  = _VersionCode
	GameConfiguration = _Configuration
	NewName = GetPackageNameJson(_ChannelName)
	#isFastBuild = True
	if os.path.isfile(PythonLocation()+"/../../01_MercurySDK/FastBuild")==False:
		isFastBuild=False
	else:
		isFastBuild=True
	if os.path.isfile(PythonLocation()+"/../../01_MercurySDK/MultiThread")==False:
		MultiThread=False
		CleanCache()
		shutil.copy(PythonLocation()+"/../../02_ChannelSdk/ChannelTemplate/Template",PythonLocation()+"/../../01_MercurySDK/MultiThread")
	else:
		MultiThread=True
	if NewName == "":
		NewName=_Packagename
	print("New Pakage Name--->>>["+NewName+"]")
	print("AD Channel--->>>["+_AD+"]")
	print("MultiThread Build Model--->>>["+str(MultiThread)+"]")
	print("isFastBuild Build Model--->>>["+str(isFastBuild)+"]")
	print("isMulitDex Build Model--->>>["+str(isMulitDex)+"]")
	print("New Pakage Name--->>>["+NewName+"]")
	print("Version Name--->>>["+GameVersionName+"]")
	print("Version Code--->>>["+GameVersionCode+"]")
	if os.path.exists(PythonLocation()+"/../../"+GameConfiguration+"/AndroidManifest.xml")==False:
		print("[ERROR] Missing Default XML: "+PythonLocation()+"/../../"+GameConfiguration+"/AndroidManifest.xml")
		print("Press any key to continue...")
		input()
		return
	if os.path.exists(PythonLocation()+"/../../"+GameConfiguration+"/app_icon.png")==False:
		print("[WARNING] Missing Default ICON:"+PythonLocation()+"/../../"+GameConfiguration+"/app_icon.png")
		print("Press any key to continue...")
		input()
	if _IOSPath!="" and _ChannelName=="ios":
		PrepareIOSBuilding(_IOSPath,_AD)
	else:
		_APKLocation = _APKLocation.replace("\\","/")
		APKname = _APKLocation.split("/")[-1]
		PointCount = APKname.rfind(".")
		LocationAPK=APKname[:PointCount]
		APKname = CopyBaseAPK(_APKLocation)
		Build_APKname = CopyBaseAPK(_APKLocation)
		De_APKName = CopyDecomplieAPK(_APKLocation)
		if os.path.exists(get_desktop()+"/BuildConfig"):
			DeleteFolder(get_desktop()+"/BuildConfig")
		if CheckResource(_ChannelName,_AD):
			DecompileAPK(PythonLocation()+"/__pycache__/"+De_APKName)
			# folderName1 = DecompileSDK(_ChannelName,De_APKName)	
			# folderName3 = DecompileADSDK(_AD,De_APKName)
			# folderName2 = DecompileE2WPlugin(De_APKName,_ChannelName,_AD)
			DecompileSystemSDK("system",De_APKName)
			DecompileSDK(_ChannelName,De_APKName)
			DecompileADSDK(_AD,De_APKName)
			DecompileE2WPlugin(De_APKName,_ChannelName,_AD)
			CopyChannelDecompileFiles(De_APKName,_Configuration,_ChannelName)
			CreateXML(De_APKName,_ChannelName,_AD,_Packagename,_VersionName,_VersionCode)
			RebuildAPK(De_APKName,Build_APKname,LocationAPK,_ChannelName,_AD)
			#if MultiThread==False:
				#CleanProject(folderName1,folderName2,folderName3,De_APKName,APKname,Build_APKname,_AD,_ChannelName)
		endtime = datetime.datetime.now()
		if os.path.isfile(PythonLocation()+"/../MultiThread"):
			os.remove(PythonLocation()+"/../MultiThread")
		print ("————————————————")
		print ("	Total Time:"+str((endtime-starttime).seconds)+" s")
	print ("————————————————")
def AddIOSSDK(_XcodeProjLocation,_SDKLocation):
	MyList = PathTool.FindIOSSDKSource(_SDKLocation)
	project = XcodeProject.load(_XcodeProjLocation)
	for i in MyList:
		if i.find(".framework")!=-1:
			frameworks = project.get_or_create_group('Frameworks')
			# project.add_file(i, parent=frameworks,tree='SDKROOT', force=False, file_options=file_options)
			file_options = FileOptions(weak=True)
			project.add_file(i, parent=frameworks, force=False, file_options=file_options)
		else:
		 	project.add_file(i, force=False,file_options=FileOptions(weak=True,create_build_files=True))
	project.save()
def AddIOSFlag(_XcodeProjLocation,_configuration):
	project = XcodeProject.load(_XcodeProjLocation)
	project.add_other_ldflags(_configuration)
	project.save()
def AddIOSLibrary(_XcodeProjLocation,_LibraryName):
	project = XcodeProject.load(_XcodeProjLocation)
	frameworks = project.get_or_create_group('Frameworks')
	file_options = FileOptions(weak=True)
	for i in _LibraryName:
		path = "/Applications/Xcode.app/Contents/Developer/Platforms/iPhoneOS.platform/Developer/SDKs/iPhoneOS.sdk/System/Library/Frameworks/"+i+".framework"
		if os.path.exists(path):
			project.add_file(path, parent=frameworks, force=False, file_options=file_options)
		else:
			print("Can't find "+path)
	project.save()
def ChangeSetting(PbxprojPath,KetWord,replaceLine):
	FileOperationTool.ReplaceKeyWord(PbxprojPath,KetWord,replaceLine)
def PrepareIOSBuilding(_IOSPath,_AD):
	if _AD == "":
		print("_AD have no value")
		return
	PbxprojPath= _IOSPath+"/Unity-iPhone.xcodeproj/project.pbxproj"
	#add iossdk
	AddIOSSDK(PbxprojPath,PythonLocation()+"/../../02_ChannelSdk/ADIOSSDK/"+_AD) 
	#add library
	readed = json.load(open(PythonLocation()+"/../../02_ChannelSdk/ADIOSXML/"+_AD+"/Setting.json", 'r',encoding="UTF-8"))
	LibraryList = []
	FlagList = []
	for i in readed["library"]:
		LibraryList.append(i)
	for i in readed["flag"]:
		FlagList.append(i)
	AddIOSLibrary(PbxprojPath,LibraryList)
	#add flag
	AddIOSFlag(PbxprojPath,FlagList)
	#common setting
	ChangeSetting(PbxprojPath,"name = \"Embed Frameworks\";","			runOnlyForDeploymentPostprocessing = 1;")
def UpdateGameConfiguration():
	ConfigurationName = []
	ConfigurationSetting = []
	JsonChannelList = {}
	ConfigurationList = ListFolder(PythonLocation()+"/../../")
	for ConfigurationFile in ConfigurationList:
		if "Configuration_" in ConfigurationFile:
			ConfigurationName.append(ConfigurationFile)
	if os.path.isfile(PythonLocation()+"/../../02_ChannelSdk/GameConfiguration"):
		readed = json.load(open(PythonLocation()+"/../../02_ChannelSdk/GameConfiguration", 'r',encoding=GetEncoding(PythonLocation()+"/../../02_ChannelSdk/GameConfiguration")))
		for key in readed.items():
			ConfigurationSetting.append(key[0])
	#Template
	for Cfoler in ConfigurationName:
		for channelname in ConfigurationSetting:
			if os.path.isfile(PythonLocation()+"/../../"+Cfoler+"/GameConfiguration") ==False:
				shutil.copy(PythonLocation()+"/../../02_ChannelSdk/GameConfiguration",PythonLocation()+"/../../"+Cfoler+"/GameConfiguration")
			if os.path.isfile(PythonLocation()+"/../../"+Cfoler+"/GameConfiguration"):
				readed = json.load(open(PythonLocation()+"/../../"+Cfoler+"/GameConfiguration", 'r',encoding=GetEncoding(PythonLocation()+"/../../"+Cfoler+"/GameConfiguration")))
				#SettingPackageName = ""
				try:
					#SettingPackageName = readed[channelname]
					adddirc = {channelname:readed[channelname]}
					JsonChannelList.update(adddirc)
				except:
					data = {}
					data[channelname] = ""
					JsonChannelList.update(data)
					#print("["+Cfoler+"] add channel:"+channelname)
		with open(PythonLocation()+"/../../"+Cfoler+"/GameConfiguration", 'w',encoding=GetEncoding(PythonLocation()+"/../../"+Cfoler+"/GameConfiguration")) as json_file:
			json_file.write(json.dumps(JsonChannelList,ensure_ascii=False,sort_keys=True, indent=4, separators=(',', ':')))
def ADJson():
	ChannelList = ListFolder(PythonLocation()+"/../01_MercurySDKPlugin/east2west/src/main/java/com/east2west/game/inApp")
	ChannelListName =[]
	ConfigurationName = []
	ConfigurationList = ListFolder(PythonLocation()+"/../../")
	JsonChannelList = {}
	for ConfigurationFile in ConfigurationList:
		if "Configuration_" in ConfigurationFile:
			ConfigurationName.append(ConfigurationFile)
	for JavaFile in ChannelList:
		if "InApp" in JavaFile and ".java" in JavaFile:
			JavaFile = JavaFile.replace("InApp","")
			JavaFile = JavaFile.replace(".java","")
			JavaFile = JavaFile.lower()
			ChannelListName.append(JavaFile)
	#Template
	for Cfoler in ConfigurationName:
		for channelname in ChannelListName:
			if os.path.isfile(PythonLocation()+"/../../"+Cfoler+"/package_ad.json") ==False:
				shutil.copy(PythonLocation()+"/../../02_ChannelSdk/package_ad.json",PythonLocation()+"/../../"+Cfoler+"/package_ad.json")
			if os.path.isfile(PythonLocation()+"/../../"+Cfoler+"/package_ad.json"):
				readed = json.load(open(PythonLocation()+"/../../"+Cfoler+"/package_ad.json", 'r',encoding=GetEncoding(PythonLocation()+"/../../"+Cfoler+"/package_ad.json")))
				#SettingPackageName = ""
				try:
					#SettingPackageName = readed[channelname]
					adddirc = {channelname:readed[channelname]}
					JsonChannelList.update(adddirc)
				except:
					data = {}
					data[channelname] = ""
					JsonChannelList.update(data)
					#print("["+Cfoler+"] add channel:"+channelname)
		with open(PythonLocation()+"/../../"+Cfoler+"/package_ad.json", 'w',encoding=GetEncoding(PythonLocation()+"/../../"+Cfoler+"/package_ad.json")) as json_file:
			json_file.write(json.dumps(JsonChannelList,ensure_ascii=False,sort_keys=True, indent=4, separators=(',', ':')))
def ADJsonReadChannel(_ChannelName,GameConfiguration):
	if os.path.isfile(PythonLocation()+"/../../"+GameConfiguration+"/package_ad.json"):
		readed = json.load(open(PythonLocation()+"/../../"+GameConfiguration+"/package_ad.json", 'r',encoding=GetEncoding(PythonLocation()+"/../../"+GameConfiguration+"/package_ad.json")))
		SettingPackageName = ""
		try:
			SettingPackageName = readed[_ChannelName]
		except:
			print("No such channel:"+_ChannelName)
		#ChannelSettingModify(PythonLocation()+"/../../"+GameConfiguration+"/ChannelTemplate/"+_ChannelName,SettingPackageName)
		return SettingPackageName
	else:
		return ""
def ChannelSettingModify(_ChannelTemplateLocatoin,_ADChannel):
	if os.path.isfile(_ChannelTemplateLocatoin):
		file_object = open(_ChannelTemplateLocatoin,encoding="utf8")
	JavaCode=[]
	try:
		all_the_text = file_object.readlines()
		for i in all_the_text:
			f = i.replace(" ","")
			if(f.find("ADChannel")!=-1):
				JavaCode.append("	ADChannel =\""+_ADChannel+"\",")
			else:
				JavaCode.append(i)
	finally:
		file_object.close( )

	file_object_read = open(_ChannelTemplateLocatoin,'w',encoding="utf8")
	try:
		file_object_read.writelines(JavaCode)
	finally:
		file_object_read.close()
def FixJson(_path):
	pass
def main():
	
	readed = json.load(open(PythonLocation()+"/../../02_ChannelSdk/ADIOSXML/jrttad20181219/Setting.json", 'r',encoding="UTF-8"))
	LibraryList = []
	FlagList = []
	for i in readed["library"]:
		LibraryList.append(i)
	for i in readed["flag"]:
		FlagList.append(i)
	pass
if __name__ == '__main__':
	main()
