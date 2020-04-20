void show_video_IOS(NSString* name)
{
	NSLog(@"%s this is new string ",name);
}
void show_insert_IOS(NSString* name)
{
	NSLog(@"%s this is new string ",name);
}
void show_banner_IOS(NSString* name)
{
	NSLog(@"%s this is new string ",name);
}
void show_push_IOS(NSString* name)
{
	NSLog(@"%s this is new string ",name);
}
void GameInit(NSString* name)
{
	NSLog(@"%s this is new string ",name);
    UnitySendMessage("PluginMercury", "onFunctionCallBack", "GameInit success");
}
