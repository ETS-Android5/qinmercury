.class Lcom/unity3d/player/HFPStatus$1;
.super Landroid/content/BroadcastReceiver;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/unity3d/player/HFPStatus;->requestHFPStat()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic a:Lcom/unity3d/player/HFPStatus;


# direct methods
.method constructor <init>(Lcom/unity3d/player/HFPStatus;)V
    .locals 0

    iput-object p1, p0, Lcom/unity3d/player/HFPStatus$1;->a:Lcom/unity3d/player/HFPStatus;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 1

    const/4 p1, -0x1

    const-string v0, "android.media.extra.SCO_AUDIO_STATE"

    invoke-virtual {p2, v0, p1}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result p2

    if-eq p2, p1, :cond_6

    if-eqz p2, :cond_4

    const/4 p1, 0x1

    if-eq p2, p1, :cond_2

    const/4 v0, 0x2

    if-eq p2, v0, :cond_0

    goto :goto_0

    :cond_0
    iget-object p2, p0, Lcom/unity3d/player/HFPStatus$1;->a:Lcom/unity3d/player/HFPStatus;

    invoke-static {p2}, Lcom/unity3d/player/HFPStatus;->c(Lcom/unity3d/player/HFPStatus;)I

    move-result p2

    sget v0, Lcom/unity3d/player/HFPStatus$a;->b:I

    if-ne p2, v0, :cond_1

    iget-object p2, p0, Lcom/unity3d/player/HFPStatus$1;->a:Lcom/unity3d/player/HFPStatus;

    invoke-static {p2, p1}, Lcom/unity3d/player/HFPStatus;->a(Lcom/unity3d/player/HFPStatus;Z)Z

    return-void

    :cond_1
    iget-object p1, p0, Lcom/unity3d/player/HFPStatus$1;->a:Lcom/unity3d/player/HFPStatus;

    sget p2, Lcom/unity3d/player/HFPStatus$a;->c:I

    invoke-static {p1, p2}, Lcom/unity3d/player/HFPStatus;->a(Lcom/unity3d/player/HFPStatus;I)I

    :goto_0
    return-void

    :cond_2
    iget-object p1, p0, Lcom/unity3d/player/HFPStatus$1;->a:Lcom/unity3d/player/HFPStatus;

    sget p2, Lcom/unity3d/player/HFPStatus$a;->b:I

    invoke-static {p1, p2}, Lcom/unity3d/player/HFPStatus;->a(Lcom/unity3d/player/HFPStatus;I)I

    iget-object p1, p0, Lcom/unity3d/player/HFPStatus$1;->a:Lcom/unity3d/player/HFPStatus;

    invoke-static {p1}, Lcom/unity3d/player/HFPStatus;->a(Lcom/unity3d/player/HFPStatus;)Z

    move-result p1

    if-nez p1, :cond_3

    iget-object p1, p0, Lcom/unity3d/player/HFPStatus$1;->a:Lcom/unity3d/player/HFPStatus;

    invoke-static {p1}, Lcom/unity3d/player/HFPStatus;->b(Lcom/unity3d/player/HFPStatus;)Landroid/media/AudioManager;

    move-result-object p1

    invoke-virtual {p1}, Landroid/media/AudioManager;->stopBluetoothSco()V

    return-void

    :cond_3
    iget-object p1, p0, Lcom/unity3d/player/HFPStatus$1;->a:Lcom/unity3d/player/HFPStatus;

    invoke-static {p1}, Lcom/unity3d/player/HFPStatus;->b(Lcom/unity3d/player/HFPStatus;)Landroid/media/AudioManager;

    move-result-object p1

    const/4 p2, 0x3

    invoke-virtual {p1, p2}, Landroid/media/AudioManager;->setMode(I)V

    return-void

    :cond_4
    iget-object p1, p0, Lcom/unity3d/player/HFPStatus$1;->a:Lcom/unity3d/player/HFPStatus;

    invoke-static {p1}, Lcom/unity3d/player/HFPStatus;->a(Lcom/unity3d/player/HFPStatus;)Z

    move-result p1

    const/4 p2, 0x0

    if-eqz p1, :cond_5

    iget-object p1, p0, Lcom/unity3d/player/HFPStatus$1;->a:Lcom/unity3d/player/HFPStatus;

    invoke-static {p1}, Lcom/unity3d/player/HFPStatus;->b(Lcom/unity3d/player/HFPStatus;)Landroid/media/AudioManager;

    move-result-object p1

    invoke-virtual {p1, p2}, Landroid/media/AudioManager;->setMode(I)V

    :cond_5
    iget-object p1, p0, Lcom/unity3d/player/HFPStatus$1;->a:Lcom/unity3d/player/HFPStatus;

    invoke-static {p1, p2}, Lcom/unity3d/player/HFPStatus;->a(Lcom/unity3d/player/HFPStatus;Z)Z

    :cond_6
    return-void
.end method
