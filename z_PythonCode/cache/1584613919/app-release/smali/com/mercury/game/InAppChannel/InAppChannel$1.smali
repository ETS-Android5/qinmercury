.class Lcom/mercury/game/InAppChannel/InAppChannel$1;
.super Ljava/lang/Object;
.source "InAppChannel.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mercury/game/InAppChannel/InAppChannel;->ExitGame()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/mercury/game/InAppChannel/InAppChannel;


# direct methods
.method constructor <init>(Lcom/mercury/game/InAppChannel/InAppChannel;)V
    .locals 0

    .line 72
    iput-object p1, p0, Lcom/mercury/game/InAppChannel/InAppChannel$1;->this$0:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 0

    .line 76
    sget-object p1, Lcom/mercury/game/MercuryActivity;->mContext:Landroid/content/Context;

    check-cast p1, Landroid/app/Activity;

    invoke-virtual {p1}, Landroid/app/Activity;->finish()V

    .line 77
    invoke-static {}, Landroid/os/Process;->myPid()I

    move-result p1

    invoke-static {p1}, Landroid/os/Process;->killProcess(I)V

    return-void
.end method
