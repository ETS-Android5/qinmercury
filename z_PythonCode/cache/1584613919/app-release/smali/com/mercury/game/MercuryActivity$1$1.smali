.class Lcom/mercury/game/MercuryActivity$1$1;
.super Ljava/lang/Object;
.source "MercuryActivity.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mercury/game/MercuryActivity$1;->run()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/mercury/game/MercuryActivity$1;

.field final synthetic val$image:Landroid/widget/ImageView;


# direct methods
.method constructor <init>(Lcom/mercury/game/MercuryActivity$1;Landroid/widget/ImageView;)V
    .locals 0

    .line 92
    iput-object p1, p0, Lcom/mercury/game/MercuryActivity$1$1;->this$1:Lcom/mercury/game/MercuryActivity$1;

    iput-object p2, p0, Lcom/mercury/game/MercuryActivity$1$1;->val$image:Landroid/widget/ImageView;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    const-wide/16 v0, 0xbb8

    .line 98
    :try_start_0
    invoke-static {v0, v1}, Ljava/lang/Thread;->sleep(J)V
    :try_end_0
    .catch Ljava/lang/InterruptedException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    .line 101
    invoke-virtual {v0}, Ljava/lang/InterruptedException;->printStackTrace()V

    .line 103
    :goto_0
    sget-object v0, Lcom/mercury/game/MercuryActivity;->mContext:Landroid/content/Context;

    check-cast v0, Landroid/app/Activity;

    new-instance v1, Lcom/mercury/game/MercuryActivity$1$1$1;

    invoke-direct {v1, p0}, Lcom/mercury/game/MercuryActivity$1$1$1;-><init>(Lcom/mercury/game/MercuryActivity$1$1;)V

    invoke-virtual {v0, v1}, Landroid/app/Activity;->runOnUiThread(Ljava/lang/Runnable;)V

    return-void
.end method
