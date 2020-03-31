.class Lcom/qinbatista/mercury/MainActivity$5;
.super Ljava/lang/Object;
.source "MainActivity.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/qinbatista/mercury/MainActivity;->onCreate(Landroid/os/Bundle;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/qinbatista/mercury/MainActivity;


# direct methods
.method constructor <init>(Lcom/qinbatista/mercury/MainActivity;)V
    .locals 0

    .line 153
    iput-object p1, p0, Lcom/qinbatista/mercury/MainActivity$5;->this$0:Lcom/qinbatista/mercury/MainActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 1

    const-string p1, "MercuryDemo"

    const-string v0, "[step][8]->respondCPserver"

    .line 157
    invoke-static {p1, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 158
    iget-object p1, p0, Lcom/qinbatista/mercury/MainActivity$5;->this$0:Lcom/qinbatista/mercury/MainActivity;

    iget-object p1, p1, Lcom/qinbatista/mercury/MainActivity;->MercurySDK:Lcom/mercury/game/MercuryActivity;

    invoke-virtual {p1}, Lcom/mercury/game/MercuryActivity;->respondCPserver()V

    return-void
.end method
