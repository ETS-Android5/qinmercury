import sys
import os
import platform

def PythonLocation():
	return os.path.dirname(os.path.realpath(__file__))

import os
import sys
import time
import json
import random
import aiomysql
import requests
import calendar
import shutil

import xml.etree.ElementTree as ET
import sys
import subprocess
class xml_manager(object):
	def __init__(self, path):
		self.path = path
		#从系统加载 xml 文件, getroot () 获取根节点
		self.tree = ET.parse(self.path)
		self.root = self.tree.getroot()
		#为存储配置创建字典
		self.tag = {}
		self.GetAllTag()

	#添加方法
	def AddTag(self, tagName, content, name):
		newEle = ET.Element(tagName, {"name": name})
		newEle.text = content
		newEle.tail="\n"
		self.root.append(newEle)
		self.SaveContext()
	#删除方法
	def DelTag(self, tagName):
		if tagName in self.tag:
			for child in self.root:
				if tagName == child.attrib.get('name'):
					self.root.remove(child)
					return True
		else:
			return False
		self.SaveContext()

    #去重方法
	def DeleteMultiTag_SaveFirstElementPriorities(self):
		tag_h = {}
		tag_x = {}
		for child in self.root:
			tag_x[child.attrib.get('name')] = 0
		for child in self.root:
			if child.attrib.get('name') in tag_h:
				tag_x[child.attrib.get('name')] = int(int(tag_x[child.attrib.get('name')])+1)
			else:
				tag_h[child.attrib.get('name')] = child.text
		for k in tag_x.keys():
			n = int(tag_x[k])
			while n != 0:
				n = n-1
				num = 0
				for ch in self.root:
					if ch.attrib.get('name') ==None:
						continue
					if k == ch.attrib.get('name') and num == 1:
						num = 0
						print("[First Priorities]Mulit Element Delete: "+ch.attrib.get('name'))
						self.root.remove(ch)
					elif k == ch.attrib.get('name'):
						num = 1
		self.tag = tag_h
		self.SaveContext()
	def DeleteMultiTag_SaveLastElementPriorities(self):
		tag_h = {}
		tag_x = {}
		for child in self.root:
			tag_x[child.attrib.get('name')] = 0
		for child in self.root:
			if child.attrib.get('name') in tag_h:
				tag_x[child.attrib.get('name')] = int(int(tag_x[child.attrib.get('name')])+1)
			else:
				tag_h[child.attrib.get('name')] = child.text

		for k in tag_x.keys():
			n = int(tag_x[k])
			while n != 0:
				n = n-1
				print("[First Priorities]Mulit Element Delete: "+ch.attrib.get('name'))
				self.DelTag(k)
		self.tag = tag_h
		self.SaveContext()
	def Merge(self,path2):
		#从系统加载 xml 文件, getroot () 获取根节点
		tree2 = ET.parse(path2)
		root2 = tree2.getroot()
		if self.root.tag == root2.tag:
			for child in root2:
				self.root.append(child)
			self.SaveContext()
		else:
			file_object = open(self.path,encoding="utf8")
			JavaCode=[]
			try:
				all_the_text = file_object.readlines()
				for i in all_the_text:
					JavaCode.append(i)
			finally:
				file_object.close()
			JavaCode.append('\n\n')
			file_object = open(path2,encoding="utf8")
			try:
				all_the_text = file_object.readlines()
				for i in all_the_text:
					JavaCode.append(i)
			finally:
				file_object.close()
			file_object_read = open(self.path,'w',encoding="utf8")
			try:
				file_object_read.writelines(JavaCode)
			finally:
				file_object_read.close()
		print('Merge Success')
	def GetAllTag(self):
		for child in self.root:
			self.tag[child.attrib.get('name')] = child.text

	def isKeyExist(self,tagName:str):
		if tagName in self.tag:
			return True
		else:
			return False

	def SaveContext(self):
		self.tree.write(self.path,encoding="utf-8",xml_declaration=True)

def delete_deplicated_element(_Path):
	XMLop = xml_manager(_Path)
	XMLop.DeleteMultiTag_SaveFirstElementPriorities()
	print("Delete Mulit Element Finished")


def merge_xml(_Path1,_Path2):
	XMLop = xml_manager(_Path1)
	XMLop.Merge(_Path2)
	XMLop.DeleteMultiTag_SaveFirstElementPriorities()

class APKBuildManager():
	def __init__(self):
		self.__python = "python3"
		self.__apktool = "apktool"
		self.__sdk_apk_name = "app-release.apk"
		self.__sdk_apk_name_only = "app-release"
		self.__jar_project = os.path.dirname(os.path.realpath(__file__))+"/MercuryJarProject"
		self.__apk_project = os.path.dirname(os.path.realpath(__file__))+"/MercuryAPKProject"
		self.__cache_position = "/PythonCode/cache/"
		self.__copyFileCounts = 0
		# self.__file_path = os.path.dirname(os.path.realpath(__file__))
		# self.__sdk_path = os.path.dirname(os.path.realpath(__file__))+"/"+channel
		# self.__sdk_script_path = os.path.dirname(os.path.realpath(__file__))+"/"+channel+"/merge_building.py"
		# self.__sdk_apk_path = os.path.dirname(os.path.realpath(__file__))+"/"+channel+"/"+self.__sdk_apk_name
		# self.__game_apk_path = game_apk_path
		# self.__game_apk_name = os.path.splitext(self.__game_apk_path)[0][game_apk_path.rfind("/")+1:]
		# self.__time_tick = str(int(time.time()))
		# if os.path.isdir(self.__file_path+self.__cache_position): shutil.rmtree(self.__file_path+self.__cache_position)
		# self.__create_cache()

	def _decompile_game_apk(self):
		os.system(f"{self.__apktool} d {self.__game_apk_path}")
		# print(os.path.splitext(_path)[0])

	def _decompile_sdk_apk(self):
		if os.path.exists(self.__sdk_apk_path)==False: os.system(f"{self.__python} {self.__sdk_script_path}")
		os.system(f"{self.__apktool} d {self.__sdk_apk_path}")
		self.__merge_sdk_resource()

	def __create_cache(self):
		if os.path.isdir(self.__file_path+self.__cache_position)==False:os.mkdir(self.__file_path+self.__cache_position)
		if os.path.isdir(self.__file_path+self.__cache_position+self.__time_tick)==False:os.mkdir(self.__file_path+self.__cache_position+self.__time_tick)
		os.chdir(self.__file_path+self.__cache_position+self.__time_tick)

	def merge_sdk_resource(self):
		#merge assets
		self.__merge_assets()
		#merge lib
		self.__merge_lib()
		#merge res
		self.__merge_res()
		#merge xml
		self.__merge_xml()





	def __merge_lib(self):
		self.__copyFileCounts = 0
		if os.path.isdir(f"{self.__jar_project}/mercury/src/main/libs")==False:os.mkdir(f"{self.__jar_project}/mercury/src/main/libs")
		if os.path.isdir(f"{self.__apk_project}/app/src/main/libs")==False:os.mkdir(f"{self.__apk_project}/app/src/main/libs")
		self._copy_files_overwrite(f"{self.__jar_project}/mercury/src/main/libs",f"{self.__apk_project}/app/src/main/libs")

	def __merge_assets(self):
		self.__copyFileCounts = 0
		if os.path.isdir(f"{self.__jar_project}/mercury/src/main/assets")==False:os.mkdir(f"{self.__jar_project}/mercury/src/main/assets")
		if os.path.isdir(f"{self.__apk_project}/app/src/main/libs")==False:os.mkdir(f"{self.__apk_project}/app/src/main/libs")
		self._copy_files_overwrite(f"{self.__jar_project}/mercury/src/main/assets",f"{self.__apk_project}/app/src/main/assets")

	def __merge_res(self):
		self.__copyFileCounts = 0
		jar_res = self.__all_files_in_folder(f"{self.__jar_project}/mercury/src/main/res")
		apk_res  = self.__all_files_in_folder(f"{self.__apk_project}/app/src/main/res")
		for g_res in jar_res:
			for s_res in apk_res:
				self._copy_files_dont_overwrite(f"{self.__jar_project}/mercury/src/main/res",f"{self.__apk_project}/app/src/main/res")
				gameresfile = g_res[g_res.rfind("/"):]
				sdkresfile = s_res[s_res.rfind("/"):]
				if sdkresfile == gameresfile and ".xml" in sdkresfile:
					if gameresfile == "/activity_main.xml" or gameresfile == "/main.xml":
						print(f"skip{gameresfile}")
						continue
					else:
						print(f"[_decompile_sdk_apk][__merge_sdk_resource][__merge_sdk_resource_xml]merging {g_res}<-{s_res}")
						merge_xml(s_res,g_res)

	def __merge_xml(self):
		#get sdk string
		if os.path.isfile(f"{self.__jar_project}/mercury/src/main/AndroidManifest.xml"):
			sdk_part = []
			with open(f"{self.__jar_project}/mercury/src/main/AndroidManifest.xml",encoding="utf8") as file_object:
				is_sdk_part = False
				all_the_text = file_object.readlines()
				for i in all_the_text:
					f = i.replace(" ","")
					if f.find("<!--sdk-->")!=-1:
						sdk_part.append("<!--sdk-->\r")
						is_sdk_part = True
					elif is_sdk_part == True:
						if f.find("<!--end-->")==-1:
							sdk_part.append(i)
						else:
							sdk_part.append("<!--end-->\r")
							is_sdk_part = False
							continue
					elif(f.find("<!--sdkxml-->")!=-1):
						sdk_part.append("<!--sdkxml-->\r")
						is_sdk_part = True
					elif is_sdk_part == True:
						if f.find("<!--end-->")==-1:
							sdk_part.append(i)
						else:
							is_sdk_part = False
							sdk_part.append("<!--end-->\r")
							continue
				# print("sdk_part="+str(sdk_part))
		#save string into files
		with open(f"{self.__jar_project}/AndroidManifest_sdk.xml",mode='w',encoding="utf8") as file_context:
			file_context.writelines(sdk_part)
		#merge xml string into APK
		# print(f"{self.__apk_project}/app/src/main/AndroidManifest.xml")
		with open(f"{self.__apk_project}/app/src/main/AndroidManifest.xml",encoding="utf8") as file_object:
			is_sdk_part = False
			loop_old = False
			all_the_text = file_object.readlines()
			new_xml = []
			for i in all_the_text:
				f = i.replace(" ","")
				if f.find("<!--sdk-->")!=-1:
					loop_old=True
					new_xml.append("<!--sdk-->\r")
					loop = False
					for sdk_line in sdk_part:
						if loop == False:
							if sdk_line.find("<!--sdk-->")!=-1:loop = True
						elif loop==True:
							if sdk_line.find("<!--end-->")!=-1:
								loop=False
								break
							else:
								new_xml.append(sdk_line)
					is_sdk_part = True
				elif f.find("<!--sdkxml-->")!=-1:
					loop_old=True
					new_xml.append("<!--sdkxml-->\r")
					loop = False
					for sdk_line in sdk_part:
						if loop == False:
							if sdk_line.find("<!--sdkxml-->")!=-1:loop = True
						elif loop==True:
							if sdk_line.find("<!--end-->")!=-1:
								loop=False
								break
							else:
								new_xml.append(sdk_line)
					is_sdk_part = True
				else:
					if loop_old==True:
						if i.find("<!--end-->")!=-1:
							loop_old=False
							new_xml.append("<!--end-->\r")
						else:
							pass
					else:
						new_xml.append(i)
			# print("sdk_part="+str(new_xml))
			with open(f"{self.__apk_project}/app/src/main/AndroidManifest.xml",mode='w',encoding="utf8") as file_context:
				file_context.writelines(new_xml)
	def _copy_files_dont_overwrite(self,sourceDir, targetDir):
		self.__copyFileCounts
		#print (sourceDir)
		#print (u"%s 当前处理文件夹%s已处理%s 个文件" %(time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time())), sourceDir,copyFileCounts))
		for f in os.listdir(sourceDir):
			sourceF = os.path.join(sourceDir, f)
			targetF = os.path.join(targetDir, f)
			if os.path.isfile(sourceF):
				#创建目录
				if not os.path.exists(targetDir):
					os.makedirs(targetDir)
				self.__copyFileCounts += 1
				#文件不存在，或者存在但是大小不同，覆盖
				if not os.path.exists(targetF):
					#2进制文件
					open(targetF, "wb").write(open(sourceF, "rb").read())
					#print (u"%s %s 复制完毕" %(time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time())), targetF))
				else:
					pass
					# print (u"%s %s 已存在，不重复复制" %(time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time())), targetF))
			if os.path.isdir(sourceF):
				self._copy_files_dont_overwrite(sourceF, targetF)

	def _copy_files_overwrite(self,sourceDir, targetDir):
		self.__copyFileCounts
		#print (sourceDir)
		#print (u"%s 当前处理文件夹%s已处理%s 个文件" %(time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time())), sourceDir,copyFileCounts))
		for f in os.listdir(sourceDir):
			sourceF = os.path.join(sourceDir, f)
			targetF = os.path.join(targetDir, f)
			if os.path.isfile(sourceF):
				#创建目录
				if not os.path.exists(targetDir):
					os.makedirs(targetDir)
				self.__copyFileCounts += 1
				#文件不存在，或者存在但是大小不同，覆盖
				if not os.path.exists(targetF):
					#2进制文件
					open(targetF, "wb").write(open(sourceF, "rb").read())
					#print (u"%s %s 复制完毕" %(time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time())), targetF))
				else:
					open(targetF, "wb").write(open(sourceF, "rb").read())
					# print (u"%s %s 已存在，覆盖拷贝" %(time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time())), targetF))
			if os.path.isdir(sourceF):
				self._copy_files_overwrite(sourceF, targetF)

	def __all_files_in_folder(self,_path):
		ListMyFolder = []
		for dirpath, dirnames, filenames in os.walk(_path):
			for filename in filenames:
				ListMyFolder.append(dirpath+"/"+filename)
		return ListMyFolder


def run():
	sam = APKBuildManager()
	sam.merge_sdk_resource()

def main():
	#PythonFunction.FuncFunctionList.CleanCache()
	#PythonFunction.FuncFunctionList.RestSetting()
	run()
	os.chdir(PythonLocation())
	os.system("python3 ./MercuryJarProject/BuildJAR.py")
	os.system("mv ./MercuryJarProject/MercurySDK.jar ./MercuryAPKProject/app/src/main/libs/MercurySDK.jar")
	if os.path.isfile(PythonLocation()+"/app-release.apk"):
		os.remove(PythonLocation()+"/app-release.apk")
	os.system("python3 ./MercuryAPKProject/BuildAPK.py")
	os.system("mv ./MercuryAPKProject/app-release.apk ./app-release.apk")
	os.system("adb install -r  ./app-release.apk")


if __name__ == '__main__':
    main()
"""
@echo off
if exist game-release.apk (DEL game-release.apk)
call .\gradlew.bat clean assemblerelease
MOVE app\build\outputs\apk\release\app-release.apk game-release.apk
"""
