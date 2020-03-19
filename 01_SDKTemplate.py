
import os
import sys
import time
import json
import random
import aiomysql
import requests
import calendar
from aiohttp import web

class SDKAppendManager():
	def __init__(self, worlds = []):
		pass

	def _decompile_apk(self,_path):
		os.system(f"apktool d {_path}")
		# print(os.path.splitext(_path)[0])
	def _extract_sdk_resource(self,_path):
		
	def _execute_statement_update(self, database_name: int, statement: str) -> int:
		pass



def run():
	sam = SDKAppendManager()
	sam._decompile_apk("/Users/batista/MyProject/QinMercury/app-release.apk")


if __name__ == '__main__':
	run()
