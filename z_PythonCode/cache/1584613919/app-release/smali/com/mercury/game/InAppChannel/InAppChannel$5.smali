.class Lcom/mercury/game/InAppChannel/InAppChannel$5;
.super Ljava/lang/Object;
.source "InAppChannel.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mercury/game/InAppChannel/InAppChannel;->TestPay()V
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

    .line 112
    iput-object p1, p0, Lcom/mercury/game/InAppChannel/InAppChannel$5;->this$0:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 1

    .line 115
    iget-object p2, p0, Lcom/mercury/game/InAppChannel/InAppChannel$5;->this$0:Lcom/mercury/game/InAppChannel/InAppChannel;

    const-string v0, "failed message"

    invoke-virtual {p2, v0}, Lcom/mercury/game/InAppChannel/InAppChannel;->onPurchaseFailed(Ljava/lang/String;)V

    .line 116
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    return-void
.end method
