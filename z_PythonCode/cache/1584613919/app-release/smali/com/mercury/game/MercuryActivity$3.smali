.class Lcom/mercury/game/MercuryActivity$3;
.super Ljava/lang/Object;
.source "MercuryActivity.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mercury/game/MercuryActivity;->Exchange()V
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

    .line 288
    iput-object p1, p0, Lcom/mercury/game/MercuryActivity$3;->this$0:Lcom/mercury/game/MercuryActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    const-string v0, "IAP"

    const-string v1, "[E2WApp]->Exchange:Android"

    .line 291
    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 293
    new-instance v0, Lcom/mercury/game/util/InAppBase;

    invoke-direct {v0}, Lcom/mercury/game/util/InAppBase;-><init>()V

    .line 294
    invoke-virtual {v0}, Lcom/mercury/game/util/InAppBase;->Exchange()V

    return-void
.end method
