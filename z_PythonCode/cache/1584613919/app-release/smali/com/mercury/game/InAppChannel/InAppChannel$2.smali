.class Lcom/mercury/game/InAppChannel/InAppChannel$2;
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

    .line 81
    iput-object p1, p0, Lcom/mercury/game/InAppChannel/InAppChannel$2;->this$0:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 0

    .line 84
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    return-void
.end method
