.class public Lcom/qinbatista/mercury/QinApplication;
.super Landroid/app/Application;
.source "QinApplication.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 9
    invoke-direct {p0}, Landroid/app/Application;-><init>()V

    return-void
.end method


# virtual methods
.method public onCreate()V
    .locals 2

    .line 13
    invoke-super {p0}, Landroid/app/Application;->onCreate()V

    const-string v0, "MercuryDemo"

    const-string v1, "[step][1]init application"

    .line 16
    invoke-static {v0, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 17
    new-instance v0, Lcom/mercury/game/MercuryApplication;

    invoke-direct {v0}, Lcom/mercury/game/MercuryApplication;-><init>()V

    .line 18
    invoke-virtual {v0, p0}, Lcom/mercury/game/MercuryApplication;->APPApplicationInit(Landroid/app/Application;)V

    return-void
.end method
