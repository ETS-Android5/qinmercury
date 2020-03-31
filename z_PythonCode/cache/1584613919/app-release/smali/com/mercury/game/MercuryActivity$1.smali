.class Lcom/mercury/game/MercuryActivity$1;
.super Ljava/lang/Object;
.source "MercuryActivity.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mercury/game/MercuryActivity;->ChannelSplash()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/mercury/game/MercuryActivity;

.field final synthetic val$bitmap:Landroid/graphics/Bitmap;

.field final synthetic val$params:Landroid/widget/RelativeLayout$LayoutParams;


# direct methods
.method constructor <init>(Lcom/mercury/game/MercuryActivity;Landroid/graphics/Bitmap;Landroid/widget/RelativeLayout$LayoutParams;)V
    .locals 0

    .line 84
    iput-object p1, p0, Lcom/mercury/game/MercuryActivity$1;->this$0:Lcom/mercury/game/MercuryActivity;

    iput-object p2, p0, Lcom/mercury/game/MercuryActivity$1;->val$bitmap:Landroid/graphics/Bitmap;

    iput-object p3, p0, Lcom/mercury/game/MercuryActivity$1;->val$params:Landroid/widget/RelativeLayout$LayoutParams;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .line 88
    new-instance v0, Landroid/widget/ImageView;

    sget-object v1, Lcom/mercury/game/MercuryActivity;->mContext:Landroid/content/Context;

    invoke-direct {v0, v1}, Landroid/widget/ImageView;-><init>(Landroid/content/Context;)V

    .line 89
    iget-object v1, p0, Lcom/mercury/game/MercuryActivity$1;->val$bitmap:Landroid/graphics/Bitmap;

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setImageBitmap(Landroid/graphics/Bitmap;)V

    .line 90
    sget-object v1, Lcom/mercury/game/MercuryActivity;->mContext:Landroid/content/Context;

    check-cast v1, Landroid/app/Activity;

    iget-object v2, p0, Lcom/mercury/game/MercuryActivity$1;->val$params:Landroid/widget/RelativeLayout$LayoutParams;

    invoke-virtual {v1, v0, v2}, Landroid/app/Activity;->addContentView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 91
    sget-object v1, Landroid/widget/ImageView$ScaleType;->FIT_XY:Landroid/widget/ImageView$ScaleType;

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setScaleType(Landroid/widget/ImageView$ScaleType;)V

    .line 92
    new-instance v1, Ljava/lang/Thread;

    new-instance v2, Lcom/mercury/game/MercuryActivity$1$1;

    invoke-direct {v2, p0, v0}, Lcom/mercury/game/MercuryActivity$1$1;-><init>(Lcom/mercury/game/MercuryActivity$1;Landroid/widget/ImageView;)V

    invoke-direct {v1, v2}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;)V

    .line 113
    invoke-virtual {v1}, Ljava/lang/Thread;->start()V

    return-void
.end method
