.class Lcom/mercury/game/util/MercuryConst$1;
.super Ljava/lang/Object;
.source "MercuryConst.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mercury/game/util/MercuryConst;->Exchange(Lcom/mercury/game/util/InAppBase;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/mercury/game/util/MercuryConst;

.field final synthetic val$inbase:Lcom/mercury/game/util/InAppBase;

.field final synthetic val$inputServer:Landroid/widget/EditText;


# direct methods
.method constructor <init>(Lcom/mercury/game/util/MercuryConst;Landroid/widget/EditText;Lcom/mercury/game/util/InAppBase;)V
    .locals 0

    .line 344
    iput-object p1, p0, Lcom/mercury/game/util/MercuryConst$1;->this$0:Lcom/mercury/game/util/MercuryConst;

    iput-object p2, p0, Lcom/mercury/game/util/MercuryConst$1;->val$inputServer:Landroid/widget/EditText;

    iput-object p3, p0, Lcom/mercury/game/util/MercuryConst$1;->val$inbase:Lcom/mercury/game/util/InAppBase;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 0

    .line 347
    new-instance p1, Ljava/lang/Thread;

    new-instance p2, Lcom/mercury/game/util/MercuryConst$1$1;

    invoke-direct {p2, p0}, Lcom/mercury/game/util/MercuryConst$1$1;-><init>(Lcom/mercury/game/util/MercuryConst$1;)V

    invoke-direct {p1, p2}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;)V

    .line 405
    invoke-virtual {p1}, Ljava/lang/Thread;->start()V

    return-void
.end method
