import sys
import os
import platform
import shutil
import time
import subprocess
import zipfile
from pathlib import Path as PathLib
def PythonLocation():
	return os.path.dirname(os.path.realpath(__file__))

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
		self.__apk_project = PathLib(os.path.dirname(os.path.realpath(__file__)))
		self.__copyFileCounts = 0
		self.__isCommenting = True

	def __all_files_in_folder(self,_path):
		ListMyFolder = []
		for dirpath, dirnames, filenames in os.walk(_path):
			for filename in filenames:
				ListMyFolder.append((dirpath+"/"+filename).replace("\\","/"))
		return ListMyFolder

	def _java_comment(self):
		#merge xml string into APK
		print("self.__isCommenting="+str(self.__isCommenting))
		print(f"{self.__apk_project}/mercury/src/main/java")
		java_files = self.__all_files_in_folder(f"{self.__apk_project}/mercury/src/main/java/com")
		java_files.append(f"{self.__apk_project}/mercury/build.gradle")
		for java_file in java_files:
			with open(java_file,encoding="utf8") as file_object:
				if java_file.find(".DS_Store")!=-1:
					continue
				all_the_text = file_object.readlines()
				new_xml = []
				comment_loop = False
				for line in all_the_text:
					if line.find("//shrinkpartstart")!=-1 and comment_loop == False:
						comment_loop = True
						new_xml.append(line)
					elif comment_loop == True:
						if line.find("//shrinkpartend")!=-1:
							comment_loop=False
							new_xml.append(line)
						else:
							if self.__isCommenting == False:
								new_xml.append(line.replace("//",""))
							else:
								new_xml.append("//"+line)
					else:
						new_xml.append(line)
			with open(java_file,mode='w',encoding="utf8") as file_context:
				file_context.writelines(new_xml)

	def _delete_file(self):
		print("self.__isCommenting="+str(self.__isCommenting))
		if self.__isCommenting == True:
			java_files = self.__list_folder(f"{self.__apk_project}/mercury/src/main/libs")
			for java_file in java_files:
				if java_file.find(".jar")!=-1:
					java_file = f"{self.__apk_project}/mercury/src/main/libs/{java_file}"
					file_name = os.path.splitext(java_file)[0][os.path.splitext(java_file)[0].rfind("/")+1:]
					shutil.move(java_file,PythonLocation()+"/"+file_name)
		else:
			java_files = self.__list_folder(PythonLocation())
			for java_file in java_files:
				if java_file.find(".jar")!=-1:
					file_name = os.path.splitext(java_file)[0][os.path.splitext(java_file)[0].rfind("/")+1:]
					shutil.move(java_file,f"{self.__apk_project}/mercury/src/main/libs")

	def _set_building(self):
		self.__isCommenting = False

	def	__list_folder(self, _path):
		List = []
		for i in os.listdir(_path):
			List.append(i)
		return List

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

def main():
	apk = APKBuildManager()
	apk._java_comment()
	apk._delete_file()
	apk._set_building()
	apk._java_comment()
	apk._delete_file()

	file_path =  os.path.splitext(__file__.replace("\\","/"))[0][os.path.splitext(__file__.replace("\\","/"))[0].rfind("/")+1:]
	if os.path.isfile(PythonLocation()+"/../../z_PythonCode/"+file_path+".py"):
		if os.path.isfile(PythonLocation()+"/"+file_path+".py"):
			os.remove(PythonLocation()+"/"+file_path+".py")
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
		if os.path.isfile("./../MercurySDK.jar"):
			os.remove("./../MercurySDK.jar")
		os.rename("./classes.jar","./../MercurySDK.jar")
		__delete_zip_files("./../MercurySDK.jar")
		if os.path.isfile("./../../../Unity_UnityPlugin/UnityJarProject/mercury/src/main/libs/MercurySDK.jar"):
			os.remove("./../../../Unity_UnityPlugin/UnityJarProject/mercury/src/main/libs/MercurySDK.jar")
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
