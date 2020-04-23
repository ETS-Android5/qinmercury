
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
import zipfile
import xml.dom.minidom
def PythonLocation():
	return os.path.dirname(os.path.realpath(__file__))
class SDKAppendManager():
	def __init__(self, channel, game_apk_path):
		self.__channel = channel
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
		self.__keystore = self.__file_path+"/android.keystore"
		self.__time_tick = str(int(time.time()))
		if os.path.isdir(self.__file_path+self.__cache_position): shutil.rmtree(self.__file_path+self.__cache_position)
		self.__create_cache()
		self.__copyFileCounts = 0
		self.__conflict_list = []

	def _merge_package(self):
		self._decompile_game_apk()
		self._decompile_sdk_apk()
		self._merge_sdk_resource()
		self._modify_config()
		self._rebuild_game_apk()
		return self.__signe_signature(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/dist/{self.__game_apk_name}.apk")

	def _decompile_game_apk(self):
		os.system(f"{self.__apktool} d {self.__game_apk_path}")

	def _decompile_sdk_apk(self):
		if os.path.exists(self.__sdk_apk_path)==False: os.system(f"{self.__python} {self.__sdk_script_path}")
		os.system(f"{self.__apktool} d {self.__sdk_apk_path}")

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

	def _rebuild_game_apk(self):
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
		if os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}/assets") and os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/assets"):
			print("[__merge_sdk_resource_assets] copy assets apk with sdk to game apk")
			self.__copy_files_overwrite(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}/assets",f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/assets")

	def __merge_sdk_resource_lib(self):
		if os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}/lib") and os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/lib"):
			print("[__merge_sdk_resource_lib] copy lib apk with sdk to game apk")
			self.__copy_files_overwrite(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}/lib",f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/lib")

	def __merge_sdk_resource_smali(self):
		#delete apk's smail
		if os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}/smali/com/demo"):
			shutil.rmtree(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}/smali/com/demo")
		if os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}/smali/com/qinbatista"):
			shutil.rmtree(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}/smali/com/qinbatista")

		#find all SDKs' smali
		if os.listdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}/smali/com/mercury/game"):
			files = self.__all_files_in_folder(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}/smali/com/mercury/game")
			if self.__channel.find("_BASE")!=-1:
				for file_path in files:
					if file_path.find("/InAppChannel/")!=-1: os.remove(file_path)
					if file_path.find("/InAppAdvertisement/")!=-1: os.remove(file_path)
			if self.__channel.find("_SHOW")!=-1:
				for file_path in files:
					if file_path.find("/InAppAdvertisement/")==-1: os.remove(file_path)
			if self.__channel.find("_IAP")!=-1:
				for file_path in files:
					if file_path.find("/InAppChannel/")==-1: os.remove(file_path)

		if os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}/smali") and os.path.isdir(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/smali"):
			print("[__merge_sdk_resource_smali] copy smali apk with sdk to game apk")
			self.__copy_files_overwrite(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}/smali",f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/smali")

	def __merge_sdk_resource_res(self):
		game_res = self.__all_files_in_folder(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/res")
		sdk_res  = self.__all_files_in_folder(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}/res")
		conflict_list = self.__copy_files_dont_overwrite(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__sdk_apk_name_only}/res",f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/res")
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
			with open(f"{self.__file_path}/{self.__cache_position}/{self.__time_tick}/{self.__game_apk_name}/AndroidManifest.xml",mode='w',encoding="utf8") as file_context:
				file_context.writelines(new_xml)

		#modify xml
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

	def __all_files_in_folder(self,_path):
		ListMyFolder = []
		for dirpath, dirnames, filenames in os.walk(_path):
			for filename in filenames:
				ListMyFolder.append(dirpath+"/"+filename)
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
		pass

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

		signed_apk_path = self.__file_path+"/Y_building/"+file_name+"_"+self.__channel+file_format
		if os.path.isfile(self.__file_path+"/Y_building/"+file_name+"_"+self.__channel+file_format):os.remove(self.__file_path+"/Y_building/"+file_name+"_"+self.__channel+file_format)
		os.system("jarsigner -verbose -keystore " + self.__keystore +" -storepass singmaan -signedjar " + signed_apk_path + " -digestalg SHA1 -sigalg MD5withRSA " + _apk_path + " android.keystore")
		return self.__file_path+"/Y_building/"+file_name+"_"+self.__channel+file_format
def run():
	file_path =  os.path.splitext(__file__)[0][os.path.splitext(__file__)[0].rfind("/")+1:]
	if os.path.isfile(PythonLocation()+"/z_PythonCode/"+file_path+".py"):
		if os.path.isfile(PythonLocation()+"/"+file_path+".py"):
			os.remove(PythonLocation()+"/"+file_path+".py")
		shutil.copy(PythonLocation()+"/z_PythonCode/"+file_path+".py",PythonLocation()+"/"+file_path+".py")

	folder_name = os.path.splitext(__file__)[0][os.path.splitext(__file__)[0].rfind("/")+1:]

	files = os.listdir(os.path.dirname(os.path.realpath(__file__)))
	game_apk_path = ""
	for file_name in files:
		if file_name.find(".apk")!=-1:
			game_apk_path = os.path.dirname(os.path.realpath(__file__))+"/"+file_name
			break
	BASE = "Android_BASE_Umeng"
	SHOW = "Android_IAP_Singmaan"
	IAP  = "Android_SHOW_ChuanShanJia"
	if BASE != "":
		sam = SDKAppendManager(channel = BASE, game_apk_path = game_apk_path)
		game_apk_path = sam._merge_package()
	if SHOW != "":
		sam = SDKAppendManager(channel = SHOW, game_apk_path = game_apk_path)
		game_apk_path = sam._merge_package()
	if IAP != "":
		sam = SDKAppendManager(channel = IAP, game_apk_path = game_apk_path)
		game_apk_path = sam._merge_package()


if __name__ == '__main__':
	run()
