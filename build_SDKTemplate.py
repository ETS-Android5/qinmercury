
import os
import sys
import time
import json
import random
import aiomysql
import requests
import calendar
import shutil
import z_PythonCode.xml_manager as xml_manager

class SDKAppendManager():
	def __init__(self, channel, game_apk_path):
		self.__python = "python3"
		self.__apktool = "apktool"
		self.__sdk_apk_name = "app-release.apk"
		self.__sdk_apk_name_only = "app-release"
		self.__cache_position = "/z_PythonCode/cache/"
		self.__file_path = os.path.dirname(os.path.realpath(__file__))
		self.__sdk_path = os.path.dirname(os.path.realpath(__file__))+"/"+channel
		self.__sdk_xml_path = os.path.dirname(os.path.realpath(__file__))+"/"+channel+"/MercuryJarProject/AndroidManifest_sdk.xml"
		self.__sdk_script_path = os.path.dirname(os.path.realpath(__file__))+"/"+channel+"/merge_building.py"
		self.__sdk_apk_path = os.path.dirname(os.path.realpath(__file__))+"/"+channel+"/"+self.__sdk_apk_name
		self.__game_apk_path = game_apk_path
		self.__game_apk_name = os.path.splitext(self.__game_apk_path)[0][game_apk_path.rfind("/")+1:]
		self.__time_tick = str(int(time.time()))
		if os.path.isdir(self.__file_path+self.__cache_position): shutil.rmtree(self.__file_path+self.__cache_position)
		self.__create_cache()
		self.__copyFileCounts = 0
	def _merge_package(self):
		self._decompile_game_apk()
		self._decompile_sdk_apk()

	def _decompile_game_apk(self):
		os.system(f"{self.__apktool} d {self.__game_apk_path}")

	def _decompile_sdk_apk(self):
		if os.path.exists(self.__sdk_apk_path)==False: os.system(f"{self.__python} {self.__sdk_script_path}")
		os.system(f"{self.__apktool} d {self.__sdk_apk_path}")
		self.__merge_sdk_resource()

	def __create_cache(self):
		if os.path.isdir(f"{self.__file_path}/{self.__cache_position}")==False:os.mkdir(f"{self.__file_path}/{self.__cache_position}")
		if os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}")==False:os.mkdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}")
		os.chdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}")

	def __merge_sdk_resource(self):
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

	def __merge_sdk_resource_assets(self):
		if os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}/assets") and os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/assets"):
			self.__copy_files_overwrite(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}/assets",f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/assets")

	def __merge_sdk_resource_lib(self):
		if os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}/lib") and os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/lib"):
			self.__copy_files_overwrite(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}/lib",f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/lib")

	def __merge_sdk_resource_smali(self):
		if os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}/smali") and os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/smali"):
			self.__copy_files_overwrite(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}/smali",f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/smali")

	def __merge_sdk_resource_res(self):
		game_res = self.__all_files_in_folder(f"{self.__file_path}/{self.__game_apk_name}/res")
		sdk_res  = self.__all_files_in_folder(f"{self.__file_path}/{self.__sdk_apk_name_only}/res")
		for g_res in game_res:
			for s_res in sdk_res:
				self.__copy_files_dont_overwrite(f"{self.__file_path}/{self.__sdk_apk_name_only}/res",f"{self.__file_path}/{self.__game_apk_name}/res")
				gameresfile = g_res[g_res.rfind("/"):]
				sdkresfile = s_res[s_res.rfind("/"):]
				if sdkresfile == gameresfile and ".xml" in sdkresfile:
					print(f"[_decompile_sdk_apk][__merge_sdk_resource][__merge_sdk_resource_res]merging {g_res}<-{s_res}")
					xml_manager.merge_xml(g_res,s_res)

	def __merge_sdk_resource_xml(self):
		#get sdk string
		with open(f"{self.__sdk_xml_path}",encoding="utf8") as file_object:
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
			# print("sdk_part="+str(new_xml))
			with open(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/AndroidManifest.xml",mode='w',encoding="utf8") as file_context:
				file_context.writelines(new_xml)
	def __all_files_in_folder(self,_path):
		ListMyFolder = []
		for dirpath, dirnames, filenames in os.walk(_path):
			for filename in filenames:
				ListMyFolder.append(dirpath+"/"+filename)
		return ListMyFolder
	def __copy_files_dont_overwrite(self,sourceDir, targetDir):
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
				self.__copy_files_dont_overwrite(sourceF, targetF)

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
def run():
	sam = SDKAppendManager(channel = "Android_SDKTemplate",game_apk_path = "/Users/batista/MyProject/QinMercury/demo.apk")
	sam._merge_package()


if __name__ == '__main__':
	run()
