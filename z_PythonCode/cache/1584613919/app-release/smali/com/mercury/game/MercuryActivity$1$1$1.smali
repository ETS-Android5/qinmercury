.class Lcom/mercury/game/MercuryActivity$1$1$1;
.super Ljava/lang/Object;
.source "MercuryActivity.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mercury/game/MercuryActivity$1$1;->run()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$2:Lcom/mercury/game/MercuryActivity$1$1;


# direct methods
.method constructor <init>(Lcom/mercury/game/MercuryActivity$1$1;)V
    .locals 0

    .line 103
    iput-object p1, p0, Lcom/mercury/game/MercuryActivity$1$1$1;->this$2:Lcom/mercury/game/MercuryActivity$1$1;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    .line 106
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity$1$1$1;->this$2:Lcom/mercury/game/MercuryActivity$1$1;

    iget-object v0, v0, Lcom/mercury/game/MercuryActivity$1$1;->val$image:Landroid/widget/ImageView;

    .line 107
    invoke-virtual {v0}, Landroid/widget/ImageView;->getParent()Landroid/view/ViewParent;

    move-result-object v0

    check-cast v0, Landroid/view/ViewGroup;

    .line 108
    iget-object v1, p0, Lcom/mercury/game/MercuryActivity$1$1$1;->this$2:Lcom/mercury/game/MercuryActivity$1$1;

    iget-object v1, v1, Lcom/mercury/game/MercuryActivity$1$1;->val$image:Landroid/widget/ImageView;

    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->removeView(Landroid/view/View;)V

    return-void
.end method
