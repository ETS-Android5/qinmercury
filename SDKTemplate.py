
import os
import sys
import time
import json
import random
import aiomysql
import requests
import calendar
import shutil
import PythonCode.xml_manager as xml_manager

class SDKAppendManager():
	def __init__(self, channel, game_apk_path):
		self.__python = "python3"
		self.__apktool = "apktool"
		self.__sdk_apk_name = "app-release.apk"
		self.__sdk_apk_name_only = "app-release"
		self.__cache_position = "/PythonCode/cache/"
		self.__file_path = os.path.dirname(os.path.realpath(__file__))
		self.__sdk_path = os.path.dirname(os.path.realpath(__file__))+"/"+channel
		self.__sdk_script_path = os.path.dirname(os.path.realpath(__file__))+"/"+channel+"/merge_building.py"
		self.__sdk_apk_path = os.path.dirname(os.path.realpath(__file__))+"/"+channel+"/"+self.__sdk_apk_name
		self.__game_apk_path = game_apk_path
		self.__game_apk_name = os.path.splitext(self.__game_apk_path)[0][game_apk_path.rfind("/")+1:]
		self.__time_tick = str(int(time.time()))
		if os.path.isdir(self.__file_path+self.__cache_position): shutil.rmtree(self.__file_path+self.__cache_position)
		self.__create_cache()

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

	def __merge_sdk_resource(self):
		#merge assets
		#merge lib
		#merge res
		#merge xml
		self.__merge_sdk_resource_xml()

	def __merge_sdk_resource_xml(self):
		game_res = self.__all_files_in_folder(f"./{self.__game_apk_name}/res")
		sdk_res  = self.__all_files_in_folder(f"./{self.__sdk_apk_name_only}/res")
		for g_res in game_res:
			for s_res in sdk_res:
				shutil.copy(f"./{self.__sdk_apk_name_only}/res",f"./{self.__game_apk_name}/res")
				gameresfile = g_res[g_res.rfind("/"):]
				sdkresfile = s_res[s_res.rfind("/"):]
				if sdkresfile == gameresfile and ".xml" in sdkresfile:
					print(f"[_decompile_sdk_apk][__merge_sdk_resource][__merge_sdk_resource_xml]merging {g_res}<-{s_res}")
					xml_manager.merge_xml(g_res,s_res)

	def __all_files_in_folder(self,_path):
		ListMyFolder = []
		for dirpath, dirnames, filenames in os.walk(_path):
			for filename in filenames:
				ListMyFolder.append(dirpath+"/"+filename)
		return ListMyFolder
def run():
	sam = SDKAppendManager(channel = "Android_SDKTemplate",game_apk_path = "/Users/batista/MyProject/QinMercury/game.apk")
	sam._decompile_game_apk()
	sam._decompile_sdk_apk()


if __name__ == '__main__':
	run()
