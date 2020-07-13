
import os
import sys
import time
import json
import random
import requests
import calendar
import shutil
import z_PythonCode.xml_manager as xml_manager
import zipfile
import xml.dom.minidom
import configparser
import datetime
import subprocess
from os.path import join, getsize
def PythonLocation():
	return os.path.dirname(os.path.realpath(__file__))
class SDKAppendManager():
	def __init__(self, channel_base, channel_show, channel_IAP, game_apk_path, channel_name, package_name, apk_name):
		self.__channel_base = channel_base
		self.__channel_show = channel_show
		self.__channel_IAP = channel_IAP
		if sys.platform=="win32":
			self.__python = "python.exe"
		else:
			self.__python = "python3"
		self.__apktool = "apktool"
		self.__sdk_apk_name = "app-release.apk"
		self.__sdk_apk_name_only = "app-release"
		self.__cache_position = "/z_PythonCode/cache/"
		self.__build_APK_script = "merge_building.py"
		self.__file_path = os.path.dirname(os.path.realpath(__file__)).replace("\\","/")
		self.__sdk_path = os.path.dirname(os.path.realpath(__file__)).replace("\\","/")#+"/"+channel
		self.__sdk_xml_path = os.path.dirname(os.path.realpath(__file__)).replace("\\","/")#+"/"+channel+"/MercuryJarProject/AndroidManifest_sdk.xml"
		self.__sdk_script_path = os.path.dirname(os.path.realpath(__file__)).replace("\\","/")#+"/"+channel+"/merge_building.py"
		self.__sdk_apk_path = os.path.dirname(os.path.realpath(__file__)).replace("\\","/")#+"/"+channel+"/"+self.__sdk_apk_name
		self.__game_apk_path = game_apk_path.replace("\\","/")
		self.__channel_name = channel_name
		self.__package_name = package_name
		self.__apk_name = apk_name
		self.__zipalign_path = ""
		self.__game_apk_name = os.path.splitext(self.__game_apk_path)[0][game_apk_path.rfind("/")+1:]
		files = os.listdir(os.path.dirname(os.path.realpath(__file__)))
		for file_name in files:
			if file_name.find(".keystore")!=-1:
				self.__keystore = os.path.dirname(os.path.realpath(__file__))+"/"+file_name
				break
		self.__time_tick = str(int(time.time()))
		# if os.path.isdir(self.__file_path+self.__cache_position): shutil.rmtree(self.__file_path+self.__cache_position)
		self.__create_cache()
		self.__find_zipalign()
		self.__copyFileCounts = 0
		self.__conflict_list = []

	def __find_zipalign(self):
		p = subprocess.Popen('find ~/Library/Android/sdk/build-tools -name "zipalign"',shell=True,stdout=subprocess.PIPE)
		out,err = p.communicate()
		for line in out.splitlines():
			if line!="":
				self.__zipalign_path = line.decode('UTF-8')

	def _merge_package(self):
		self._decompile_game_apk()
		self._decompile_sdk_apk()
		self._modify_config()
		self._merge_sdk_resource()
		self._rebuild_game_apk()
		self.__signe_signature(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/dist/{self.__game_apk_name}.apk")
		if os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}"): shutil.rmtree(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}")

	def _decompile_game_apk(self):
		os.system(f"{self.__apktool} d {self.__game_apk_path}")

	def _decompile_sdk_apk(self):
		if self.__channel_base!="":
			if os.path.exists(self.__sdk_apk_path+"/"+self.__channel_base+"/"+self.__sdk_apk_name)==False:
				os.system(f"{self.__python} {self.__sdk_script_path}/{self.__channel_base}/{self.__build_APK_script}")
			os.system(f"{self.__apktool} d {self.__sdk_apk_path}/{self.__channel_base}/{self.__sdk_apk_name}")
			shutil.move(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}", f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}_{self.__channel_base}")
		if self.__channel_IAP!="":
			if os.path.exists(self.__sdk_apk_path+"/"+self.__channel_IAP+"/"+self.__sdk_apk_name)==False:
				os.system(f"{self.__python} {self.__sdk_script_path}/{self.__channel_IAP}/{self.__build_APK_script}")
			os.system(f"{self.__apktool} d {self.__sdk_apk_path}/{self.__channel_IAP}/{self.__sdk_apk_name}")
			shutil.move(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}", f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}_{self.__channel_IAP}")
		if self.__channel_show!="":
			if os.path.exists(self.__sdk_apk_path+"/"+self.__channel_show+"/"+self.__sdk_apk_name)==False:
				os.system(f"{self.__python} {self.__sdk_script_path}/{self.__channel_show}/{self.__build_APK_script}")
			os.system(f"{self.__apktool} d {self.__sdk_apk_path}/{self.__channel_show}/{self.__sdk_apk_name}")
			shutil.move(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}", f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}_{self.__channel_show}")



	def _merge_sdk_resource(self):
		#merge assets
		self.__merge_sdk_resource_assets()
		#merge lib
		self.__merge_sdk_resource_lib()
		#merge smali
		self.__merge_sdk_resource_smali()
		#merge res
		self.__merge_sdk_resource_res()
		#merge xml
		self.__merge_sdk_resource_xml()

	def _modify_config(self):
		self.__modify_yml()
		self.__modify_xml()
		self.__APK_name()
	def _rebuild_game_apk(self):
		self.__balance_smali(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}")
		os.system(f"{self.__apktool} b {self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}")#complie apk with sdk
		os.system(f"jar xf  {self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/dist/{self.__game_apk_name}.apk")#extract resource from sdk
		os.system(f"cp {self.__game_apk_path} {self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}.apk")#copy orginal apk
		compress_resrouce = ""
		if os.path.isfile(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/classes.dex"):compress_resrouce += " classes.dex"
		if os.path.isfile(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/classes2.dex"):compress_resrouce += " classes2.dex"
		if os.path.isfile(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/resources.arsc"):compress_resrouce += " resources.arsc"
		if os.path.isfile(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/AndroidManifest.xml"):compress_resrouce += " AndroidManifest.xml"
		if os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/lib"):compress_resrouce += " lib"
		if os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/res"):compress_resrouce += " res"
		os.system(f"jar uvf {self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/dist/{self.__game_apk_name}.apk {compress_resrouce}")# add resrouce to apk

	def __merge_sdk_resource_assets(self):
		if os.path.exists(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}_{self.__channel_base}"):
			if os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}_{self.__channel_base}/assets") and os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/assets"):
				print("[__merge_sdk_resource_assets][__channel_base] copy assets apk with sdk to game apk")
				self.__copy_files_overwrite(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}_{self.__channel_base}/assets",f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/assets")

		if os.path.exists(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}_{self.__channel_IAP}"):
			if os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}_{self.__channel_IAP}/assets") and os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/assets"):
				print("[__merge_sdk_resource_assets][__channel_IAP] copy assets apk with sdk to game apk")
				self.__copy_files_overwrite(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}_{self.__channel_IAP}/assets",f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/assets")

		if os.path.exists(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}_{self.__channel_show}"):
			if os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}_{self.__channel_show}/assets") and os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/assets"):
				print("[__merge_sdk_resource_assets][__channel_show] copy assets apk with sdk to game apk")
				self.__copy_files_overwrite(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}_{self.__channel_show}/assets",f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/assets")


	def __merge_sdk_resource_lib(self):
		self.__merge_sdk_resource_lib_execute(f"{self.__sdk_apk_name_only}_{self.__channel_base}")
		self.__merge_sdk_resource_lib_execute(f"{self.__sdk_apk_name_only}_{self.__channel_IAP}")
		self.__merge_sdk_resource_lib_execute(f"{self.__sdk_apk_name_only}_{self.__channel_show}")

	def __merge_sdk_resource_lib_execute(self, app_release_path):
		#copy lib to project
		if os.path.exists(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/lib")==False:os.mkdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/lib")
		if os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{app_release_path}/lib") and os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/lib"):
			print("[__merge_sdk_resource_lib] copy " + app_release_path + "lib apk with sdk to game apk")
			self.__copy_files_overwrite(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{app_release_path}/lib",f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/lib")

		#copy delete useless libs to project
		if os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/lib")==True:
			for filename in os.listdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/lib"):
				if "armeabi-v7a" != filename and "x86" != filename:
					if os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/lib/"+filename):
						shutil.rmtree(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/lib/"+filename)
						print("[__merge_sdk_resource_lib] deleted "+f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/lib/"+filename)

	def __merge_sdk_resource_smali(self):
		self.__merge_sdk_resource_smali_execute(f"{self.__sdk_apk_name_only}_{self.__channel_base}")
		self.__merge_sdk_resource_smali_execute(f"{self.__sdk_apk_name_only}_{self.__channel_IAP}")
		self.__merge_sdk_resource_smali_execute(f"{self.__sdk_apk_name_only}_{self.__channel_show}")

	def __merge_sdk_resource_smali_execute(self,app_releawse_path):
		#delete apk's smail
		if os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{app_releawse_path}/smali/com/demo"):
			shutil.rmtree(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{app_releawse_path}/smali/com/demo")
		if os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{app_releawse_path}/smali/com/qinbatista"):
			shutil.rmtree(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{app_releawse_path}/smali/com/qinbatista")

		#find all SDKs' smali
		if os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{app_releawse_path}/smali/com/mercury/game"):
			if os.listdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{app_releawse_path}/smali/com/mercury/game"):
				files = self.__all_files_in_folder(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{app_releawse_path}/smali/com/mercury/game")
				if app_releawse_path.find("_BASE")!=-1:
					for file_path in files:
						if file_path.find("/InAppChannel/")!=-1:
							print("[__merge_sdk_resource_smali] Base package need default InAppChannel")
							# os.remove(file_path)
						if file_path.find("/InAppAdvertisement/")!=-1:
							print("[__merge_sdk_resource_smali] Base package need default InAppAdvertisement")
							# os.remove(file_path)
				if app_releawse_path.find("_SHOW")!=-1:
					for file_path in files:
						if file_path.find("/InAppAdvertisement/")==-1: os.remove(file_path)
				if app_releawse_path.find("_IAP")!=-1:
					for file_path in files:
						if file_path.find("/InAppChannel/")==-1: os.remove(file_path)

		if os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{app_releawse_path}/smali") and os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/smali"):
			print("[__merge_sdk_resource_smali] copy smali apk with sdk to game apk")
			self.__copy_files_overwrite(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{app_releawse_path}/smali",f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/smali")

	def __merge_sdk_resource_res(self):
		self.__merge_sdk_resource_res_execute(f"{self.__sdk_apk_name_only}_{self.__channel_base}")
		self.__merge_sdk_resource_res_execute(f"{self.__sdk_apk_name_only}_{self.__channel_IAP}")
		self.__merge_sdk_resource_res_execute(f"{self.__sdk_apk_name_only}_{self.__channel_show}")

	def __merge_sdk_resource_res_execute(self,app_releawse_path):
		if os.path.exists(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{app_releawse_path}/res")==False:
			return
		game_res = self.__all_files_in_folder(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/res")
		sdk_res  = self.__all_files_in_folder(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{app_releawse_path}/res")
		conflict_list = self.__copy_files_dont_overwrite(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{app_releawse_path}/res",f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/res")
		for g_res in game_res:
			for s_res in sdk_res:
				gameresfile = g_res[g_res.rfind("/"):]
				sdkresfile = s_res[s_res.rfind("/"):]
				gameresfolder = g_res[g_res.rfind("/res"):]
				sdkresfolder =  s_res[s_res.rfind("/res"):]
				if gameresfolder!=sdkresfolder:
					continue
				elif sdkresfile == gameresfile and ".xml" in sdkresfile:
					if gameresfile == "/activity_main.xml" or gameresfile == "/main.xml" or gameresfile=="/public.xml":
						print(f"skip{gameresfile}")
						continue
					else:
						print(f"[_decompile_sdk_apk][__merge_sdk_resource]["+app_releawse_path+"]merging {g_res}<-{s_res}")
						xml_manager.merge_xml(g_res,s_res)

	def __merge_sdk_resource_xml(self):
		self.__merge_sdk_resource_xml_execute(self.__channel_base)
		self.__merge_sdk_resource_xml_execute(self.__channel_IAP)
		self.__merge_sdk_resource_xml_execute(self.__channel_show)

	def __merge_sdk_resource_xml_execute(self,channel):
		if channel=="":
			return
		#get sdk string
		with open(f"{self.__sdk_xml_path}/{channel}/MercuryJarProject/AndroidManifest_sdk.xml",encoding="utf8") as file_object:
			sdk_xml = file_object.readlines()
		with open(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/AndroidManifest.xml","r",encoding="UTF-8") as file_object:
			is_sdk_part = False
			loop_old = False
			all_the_text = file_object.readlines()
			new_xml = []
			for i in all_the_text:
				if i.find("<application")!=-1:
					loop_old=True
					loop = False
					for sdk_line in sdk_xml:
						if loop == False:
							if sdk_line.find("<!--sdkxml-->")!=-1:loop = True
						elif loop==True:
							if sdk_line.find("<!--end-->")!=-1:
								loop=False
								break
							else:
								new_xml.append(sdk_line)
					is_sdk_part = True
					new_xml.append(i)
				elif i.find("</application>")!=-1:
					loop_old=True
					loop = False
					for sdk_line in sdk_xml:
						if loop == False:
							if sdk_line.find("<!--sdk-->")!=-1:loop = True
						elif loop==True:
							if sdk_line.find("<!--end-->")!=-1:
								loop=False
								break
							else:
								new_xml.append(sdk_line)
					is_sdk_part = True
					new_xml.append(i)
				else:
						new_xml.append(i)
			with open(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/AndroidManifest.xml",mode='w',encoding="utf8") as file_context:
				file_context.writelines(new_xml)

		#modify applicationId xml
		dom = xml.dom.minidom.parse(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/AndroidManifest.xml")
		root = dom.documentElement
		package_name = root.getAttribute("package")
		print(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/AndroidManifest.xml")
		with open(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/AndroidManifest.xml",mode='r',encoding="utf8") as file_object:
			new_xml=[]
			all_the_text = file_object.readlines()
			for i in all_the_text:
				f = i.replace(" ","")
				if f.find("${applicationId}")!=-1:
					new_xml.append(i.replace("${applicationId}",package_name)+"\r")
				else:
					new_xml.append(i)
			with open(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/AndroidManifest.xml",mode='w',encoding="utf8") as file_context:
				file_context.writelines(new_xml)

		#change package name
		config=configparser.ConfigParser()
		config.read(PythonLocation()+"/android_builder_config.ini",encoding="UTF-8")
		PackageName  = self.__package_name
		self.__change_package_name(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/AndroidManifest.xml",PackageName)
		self.__xml_mete_change(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/AndroidManifest.xml","channel_name",self.__channel_name)

	def __xml_mete_change(self,_path, key, value):
		dom = xml.dom.minidom.parse(_path)
		root = dom.documentElement
		name  = root.getElementsByTagName("meta-data")
		FoundKey=False
		for node in name:
			nameofname = node.getAttribute("android:name")
			if nameofname==key:
				node.setAttribute("android:value",value)
				FoundKey=True
		if FoundKey==False:
			print("[ERROR] Can't find "+key)
		try:
			with open(_path,'w',encoding='UTF-8') as _path:
				dom.writexml(_path,indent='',addindent='',newl='',encoding='UTF-8')
		except Exception as err:
			print("ERROR[{0}".format(err))

	def __all_files_in_folder(self,_path):
		ListMyFolder = []
		for dirpath, dirnames, filenames in os.walk(_path):
			for filename in filenames:
				ListMyFolder.append((dirpath+"/"+filename).replace("\\","/"))
		return ListMyFolder
	def __copy_files_dont_overwrite(self,sourceDir, targetDir):
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
					self.__conflict_list.append(targetF)
					pass
					# print (u"%s %s 已存在，不重复复制" %(time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time())), targetF))
			if os.path.isdir(sourceF):
				self.__copy_files_dont_overwrite(sourceF, targetF)
		my_list = self.__conflict_list
		self.__conflict_list = []
		return my_list

	def __create_cache(self):
		if os.path.isdir(f"{self.__file_path}/{self.__cache_position}")==False:os.mkdir(f"{self.__file_path}/{self.__cache_position}")
		if os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}")==False:os.mkdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}")
		os.chdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}")

	def __copy_files_overwrite(self,sourceDir, targetDir):
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
				self.__copy_files_overwrite(sourceF, targetF)
	def __modify_yml(self):
		with open(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/apktool.yml",encoding="utf8") as file_object:
			JavaCodeGradle=[]
			all_the_text = file_object.readlines()
			for i in all_the_text:
				f = i.replace(" ","")
				if f.find("versionCode")!=-1:
					JavaCodeGradle.append("  versionCode: '"+self.__time_tick+"'\r")
				if f.find("targetSdkVersion")!=-1:
					JavaCodeGradle.append("  targetSdkVersion: '19'\r")
				else:
					JavaCodeGradle.append(i)
		with open(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/apktool.yml",'w',encoding="utf8") as file_object_read:
			file_object_read.writelines(JavaCodeGradle)

	def __modify_xml(self):
		if os.path.exists(PythonLocation()+"/AndroidManifest.xml")==True:
			shutil.copy(PythonLocation()+"/AndroidManifest.xml",f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/AndroidManifest.xml")

	def __APK_name(self):
		if self.__apk_name=="":
			return
		with open(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/res/values/strings.xml",encoding="utf8") as file_object:
			JavaCodeGradle=[]
			all_the_text = file_object.readlines()
			for i in all_the_text:
				f = i.replace(" ","")
				if f.find("app_name")!=-1:
					JavaCodeGradle.append('    <string name="app_name">'+self.__apk_name+'</string>\r')
				else:
					JavaCodeGradle.append(i)
		with open(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/res/values/strings.xml",'w',encoding="utf8") as file_object_read:
			file_object_read.writelines(JavaCodeGradle)

	def __balance_smali(self,_Path):
		# if(os.path.exists(_Path+"/smali_classes2")==False):
		# 	os.mkdir(_Path+"/smali_classes2")
		# List = []
		# for i in os.listdir(_Path+"/smali"):
		# 	List.append(i)
		# SaveSize=0
		# BigestList = []
		# for i in List:
		# 	size = self.__get_dir_size(_Path+"/smali/"+i)
		# 	if SaveSize<size:
		# 		SaveSize=size
		# 		BigestList = i
		# 		#print("Bigest = "+_Path+"/smali/"+i)
		# 	#print("i:"+str(size))
		folder_index = 2
		last_name = ""

		folder_list = self.__list_folder(_Path)
		while "smali_classes"+str(folder_index) in folder_list:
			folder_index=folder_index+1
		last_name = "smali_classes"+str(folder_index)

		if os.path.isdir(_Path+"/"+last_name)==False:os.mkdir(_Path+"/"+last_name)

		folder_list = self.__list_folder(_Path+"/smali")
		for folder_name in folder_list:
			if folder_name!= "com":
				shutil.move(_Path+"/smali/"+folder_name,_Path+"/"+last_name+"/"+folder_name)

		folder_list = self.__list_folder(_Path+"/smali/com")
		for folder_name in folder_list:
			if folder_name== "google":
				folder_index= folder_index+1
				if os.path.isdir(_Path+"/smali_classes"+str(folder_index))==False:os.mkdir(_Path+"/smali_classes"+str(folder_index))
				if os.path.isdir(_Path+"/smali_classes"+str(folder_index)+"/com")==False:os.mkdir(_Path+"/smali_classes"+str(folder_index)+"/com")
				shutil.move(_Path+"/smali/com/"+folder_name, _Path+"/smali_classes"+str(folder_index)+"/com/"+folder_name)

	def __get_dir_size(self,dir):
		size = 0
		for root, dirs, files in os.walk(dir):
			dirs
			size += sum([getsize(join(root, name)) for name in files])
		return size
	def __delete_signature(self,_path):
		your_delet_file="META-INF"
		old_zipfile=_path #旧文件
		new_zipfile=_path+"temp" #新文件
		zin = zipfile.ZipFile (old_zipfile, 'r') #读取对象
		zout = zipfile.ZipFile (new_zipfile, 'w') #被写入对象
		for item in zin.infolist():
			buffer = zin.read(item.filename)
			if ((your_delet_file in item.filename) and (".RSA" in item.filename)) or ((your_delet_file in item.filename)and (".SF" in item.filename)) or ((your_delet_file in item.filename) and (".MF" in item.filename)):pass
			else:zout.writestr(item, buffer) #把文件写入到新对象中
		zout.close()
		zin.close()
		print("deleted signature")
		shutil.move(new_zipfile,old_zipfile)

	def __signe_signature(self,_apk_path):
		self.__delete_signature(_apk_path)
		file_path = _apk_path[:_apk_path.rfind("/")]
		file_name = os.path.splitext(_apk_path)[0][os.path.splitext(_apk_path)[0].rfind("/")+1:]
		file_format = os.path.splitext(_apk_path)[-1]
		if os.path.exists(self.__file_path+"/Y_building")==False: os.mkdir(self.__file_path+"/Y_building")

		signed_apk_path = self.__file_path+"/Y_building/"+file_name+"_"+str(datetime.date.today())+str(time.strftime("_%H_%M_%S"))+"_"+self.__channel_name+file_format
		if os.path.isfile(signed_apk_path):
			os.remove(signed_apk_path)
		os.system("jarsigner -verbose -keystore " + self.__keystore +" -storepass singmaan -signedjar " + signed_apk_path + " -digestalg SHA1 -sigalg MD5withRSA " + _apk_path + " android.keystore")

		#zipalign
		if self.__zipalign_path!="":
			zipalign_apk_path = self.__file_path+"/Y_building/"+file_name+"_"+str(datetime.date.today())+str(time.strftime("_%H_%M_%S"))+"_"+self.__channel_name+"_zipalign"+file_format
			print("zipalign building.......")
			os.system(f"{self.__zipalign_path}  -v 4 {signed_apk_path} {zipalign_apk_path}")

		return signed_apk_path

	def __change_package_name(self,_APK_path,_package_name):
		dom = xml.dom.minidom.parse(_APK_path)
		root = dom.documentElement
		root.setAttribute("package",_package_name)
		with open(_APK_path,'w',encoding='UTF-8') as _Path:
			dom.writexml(_Path,indent='',addindent='',newl='',encoding='UTF-8')

	def	__list_folder(self, _path):
		List = []
		for i in os.listdir(_path):
			List.append(i)
		return List

def clean_project():
	if os.path.isdir(PythonLocation()+"/z_PythonCode/cache"):
		print("[CACHE]deleting "+PythonLocation()+"/z_PythonCode/cache")
		shutil.rmtree(PythonLocation()+"/z_PythonCode/cache")
		print("[CACHE]deleted "+PythonLocation()+"/z_PythonCode/cache")
	if os.path.isdir(PythonLocation()+"/y_building"):
		print("[CACHE]deleting "+PythonLocation()+"/y_building")
		shutil.rmtree(PythonLocation()+"/y_building")
		print("[CACHE]deleted "+PythonLocation()+"/y_building")

def run():
	file_path =  os.path.splitext(__file__.replace("\\","/"))[0][os.path.splitext(__file__.replace("\\","/"))[0].rfind("/")+1:]
	if os.path.isfile(PythonLocation()+"/z_PythonCode/"+file_path+".py"):
		if os.path.isfile(PythonLocation()+"/"+file_path+".py"):
			os.remove(PythonLocation()+"/"+file_path+".py")
		shutil.copy(PythonLocation()+"/z_PythonCode/"+file_path+".py",PythonLocation()+"/"+file_path+".py")

	folder_name = os.path.splitext(__file__.replace("\\","/"))[0][os.path.splitext(__file__.replace("\\","/"))[0].rfind("/")+1:]

	files = os.listdir(os.path.dirname(os.path.realpath(__file__.replace("\\","/"))))
	game_apk_path = ""
	for file_name in files:
		if file_name.find(".keystore")!=-1:
			game_apk_path = os.path.dirname(os.path.realpath(__file__.replace("\\","/"))).replace("\\","/")+"/"+file_name
			break

	config=configparser.ConfigParser()
	config.read(PythonLocation()+"/android_builder_config.ini",encoding="UTF-8")
	channel = config.sections()
	print("---Choice your channel---")
	for index, name in enumerate(channel):
		print(f"[{index}]	{name}	")
	print(f"[99]	Clean Project	")
	print("-------------------------")
	your_channel=""
	while True:
		your_channel=input("Input channel's numbner:")
		if your_channel=="99":
			clean_project()
		if your_channel.isnumeric():
			if int(your_channel)<= len(channel)-1:
				break
			else:
				print('You number can not over'+str(len(channel)-1))
				continue
		else:
			print('Your input is not number')

	ChannelName = channel[int(your_channel)]
	PackageName  = config.get(ChannelName,"PackageName")
	BASE		 = config.get(ChannelName,"BASE")
	SHOW 		 = config.get(ChannelName,"SHOW")
	IAP  		 = config.get(ChannelName,"IAP")
	APK_PATH	 = config.get(ChannelName,"PATH")
	APKName	     = config.get(ChannelName,"APKName")

	config_path=configparser.ConfigParser()
	if os.path.isfile(PythonLocation()+"/android_builder_path.ini"):
		config_path.read(PythonLocation()+"/android_builder_path.ini",encoding="UTF-8")
		if config_path.has_section(ChannelName):
			APK_PATH	 = config_path.get(ChannelName,"PATH")
			print("[News]	Using new path from android_builder_path.ini")
	else:
		shutil.copy(PythonLocation()+"/z_PythonCode/android_builder_path.ini", PythonLocation()+"/android_builder_path.ini")

	print("[Keystore]	"+game_apk_path)
	print("[ChannelName]	"+ChannelName)
	print("[PackageName]	"+PackageName)
	print("[BASE]	"+BASE)
	print("[SHOW]	"+SHOW)
	print("[IAP]	"+IAP)
	print("[APK_PATH]	"+APK_PATH)
	print("[APKName]	"+APKName)
	starttime = datetime.datetime.now()

	sam = SDKAppendManager(channel_base = BASE,channel_show = SHOW, channel_IAP = IAP, game_apk_path = APK_PATH, channel_name = ChannelName, package_name = PackageName, apk_name = APKName)
	sam._merge_package()

	endtime = datetime.datetime.now()
	print ("————————————————————————————————")
	print ("	Total Time:"+str((endtime-starttime).seconds)+" s")
	print ("————————————————————————————————")


if __name__ == '__main__':
	run()
