#pragma once

#import <QuartzCore/CADisplayLink.h>

#include "PluginBase/RenderPluginDelegate.h"

@class UnityView;
@class UnityViewControllerBase;
@class DisplayConnection;

@interface UnityAppController : NSObject<UIApplicationDelegate>
{
	UnityView*			_unityView;
	CADisplayLink*		_displayLink;

	UIWindow*			_window;
	UIView*				_rootView;
	UIViewController*	_rootController;
	UIView*				_snapshotView;
	DisplayConnection*	_mainDisplay;

	// we will cache view controllers for fixed orientation
	// auto-rotation view contoller goes to index=0
	UnityViewControllerBase* _viewControllerForOrientation[5];
#if !UNITY_TVOS
	UIInterfaceOrientation	_curOrientation;
#endif

	id<RenderPluginDelegate>	_renderDelegate;
}

// override it to add your render plugin delegate
- (void)shouldAttachRenderDelegate;

// this one is called at the very end of didFinishLaunchingWithOptions:
// after views have been created but before initing engine itself
// override it to register plugins, tweak UI etc
- (void)preStartUnity;

// this one is called at first applicationDidBecomeActive
// NB: it will be started with delay 0, so it will run on next run loop iteration
// this is done to make sure that activity indicator animation starts before blocking loading
- (void)startUnity:(UIApplication*)application;

// this is a part of UIApplicationDelegate protocol starting with ios5
// setter will be generated empty
/** 自己定义的viewController */
@property (strong, nonatomic) UIViewController *vc;
/** 右旋按钮 */
@property (strong, nonatomic) UIButton *rightBtn;
/** 左旋按钮 */
@property (strong, nonatomic) UIButton *leftBtn;
@property (retain, nonatomic) UIWindow*	window;

@property (readonly, copy, nonatomic) UnityView*			unityView;
@property (readonly, copy, nonatomic) CADisplayLink*		unityDisplayLink;

@property (readonly, copy, nonatomic) UIView*				rootView;
@property (readonly, copy, nonatomic) UIViewController*		rootViewController;
@property (readonly, copy, nonatomic) DisplayConnection*	mainDisplay;

#if !UNITY_TVOS
@property (readonly, nonatomic) UIInterfaceOrientation		interfaceOrientation;
#endif

@property (nonatomic, retain) id							renderDelegate;
@property (nonatomic, copy)									void(^quitHandler)();

@end

// Put this into mm file with your subclass implementation
// pass subclass name to define

#define IMPL_APP_CONTROLLER_SUBCLASS(ClassName)	\
@interface ClassName(OverrideAppDelegate)		\
{												\
}												\
+(void)load;									\
@end											\
@implementation ClassName(OverrideAppDelegate)	\
+(void)load										\
{												\
	extern const char* AppControllerClassName;	\
	AppControllerClassName = #ClassName;		\
}												\
@end											\

inline UnityAppController*	GetAppController()
{
	return (UnityAppController*)[UIApplication sharedApplication].delegate;
}

#define APP_CONTROLLER_RENDER_PLUGIN_METHOD(method)							\
do {																		\
	id<RenderPluginDelegate> delegate = GetAppController().renderDelegate;	\
	if([delegate respondsToSelector:@selector(method)])						\
		[delegate method];													\
} while(0)

#define APP_CONTROLLER_RENDER_PLUGIN_METHOD_ARG(method, arg)				\
do {																		\
	id<RenderPluginDelegate> delegate = GetAppController().renderDelegate;	\
	if([delegate respondsToSelector:@selector(method:)])					\
		[delegate method:arg];												\
} while(0)



// these are simple wrappers about ios api, added for convenience
void AppController_SendNotification(NSString* name);
void AppController_SendNotificationWithArg(NSString* name, id arg);

void AppController_SendUnityViewControllerNotification(NSString* name);
