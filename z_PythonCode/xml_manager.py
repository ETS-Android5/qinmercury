# -*- coding: utf-8 -*-
import xml.etree.ElementTree as ET
import sys
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

if "__main__" == __name__:
	pass
