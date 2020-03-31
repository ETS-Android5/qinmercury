.class Lcom/mercury/game/MercuryActivity$2;
.super Ljava/lang/Object;
.source "MercuryActivity.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mercury/game/MercuryActivity;->ExitGame()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/mercury/game/MercuryActivity;


# direct methods
.method constructor <init>(Lcom/mercury/game/MercuryActivity;)V
    .locals 0

    .line 162
    iput-object p1, p0, Lcom/mercury/game/MercuryActivity$2;->this$0:Lcom/mercury/game/MercuryActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 1

    .line 167
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity$2;->this$0:Lcom/mercury/game/MercuryActivity;

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->access$000(Lcom/mercury/game/MercuryActivity;)Lcom/mercury/game/InAppChannel/InAppChannel;

    move-result-object v0

    invoke-virtual {v0}, Lcom/mercury/game/InAppChannel/InAppChannel;->ExitGame()V

    return-void
.end method
