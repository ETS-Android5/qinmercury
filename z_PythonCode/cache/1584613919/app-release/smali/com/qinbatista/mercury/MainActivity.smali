.class public Lcom/qinbatista/mercury/MainActivity;
.super Landroid/app/Activity;
.source "MainActivity.java"


# static fields
.field public static appcall:Lcom/mercury/game/util/APPBaseInterface;

.field public static context:Landroid/content/Context;


# instance fields
.field public MercurySDK:Lcom/mercury/game/MercuryActivity;


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 17
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    return-void
.end method


# virtual methods
.method public onActivityResult(IILandroid/content/Intent;)V
    .locals 1

    .line 235
    invoke-super {p0, p1, p2, p3}, Landroid/app/Activity;->onActivityResult(IILandroid/content/Intent;)V

    .line 236
    iget-object v0, p0, Lcom/qinbatista/mercury/MainActivity;->MercurySDK:Lcom/mercury/game/MercuryActivity;

    invoke-virtual {v0, p1, p2, p3}, Lcom/mercury/game/MercuryActivity;->onActivityResult(IILandroid/content/Intent;)V

    return-void
.end method

.method protected onCreate(Landroid/os/Bundle;)V
    .locals 2

    .line 23
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    .line 24
    sput-object p0, Lcom/qinbatista/mercury/MainActivity;->context:Landroid/content/Context;

    const-string p1, "MercuryDemo"

    const-string v0, "[step][2]init activity"

    .line 25
    invoke-static {p1, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 26
    new-instance v0, Lcom/mercury/game/MercuryActivity;

    invoke-direct {v0}, Lcom/mercury/game/MercuryActivity;-><init>()V

    iput-object v0, p0, Lcom/qinbatista/mercury/MainActivity;->MercurySDK:Lcom/mercury/game/MercuryActivity;

    const-string v0, "[step][3]init callback"

    .line 27
    invoke-static {p1, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 28
    new-instance v0, Lcom/qinbatista/mercury/MainActivity$1;

    invoke-direct {v0, p0}, Lcom/qinbatista/mercury/MainActivity$1;-><init>(Lcom/qinbatista/mercury/MainActivity;)V

    sput-object v0, Lcom/qinbatista/mercury/MainActivity;->appcall:Lcom/mercury/game/util/APPBaseInterface;

    const-string v0, "[step][4]Init MercurySDK"

    .line 117
    invoke-static {p1, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 118
    iget-object p1, p0, Lcom/qinbatista/mercury/MainActivity;->MercurySDK:Lcom/mercury/game/MercuryActivity;

    sget-object v0, Lcom/qinbatista/mercury/MainActivity;->context:Landroid/content/Context;

    sget-object v1, Lcom/qinbatista/mercury/MainActivity;->appcall:Lcom/mercury/game/util/APPBaseInterface;

    invoke-virtual {p1, v0, v1}, Lcom/mercury/game/MercuryActivity;->InitSDK(Landroid/content/Context;Lcom/mercury/game/util/APPBaseInterface;)V

    const/high16 p1, 0x7f040000

    .line 120
    invoke-virtual {p0, p1}, Lcom/qinbatista/mercury/MainActivity;->setContentView(I)V

    const p1, 0x7f030008

    .line 121
    invoke-virtual {p0, p1}, Lcom/qinbatista/mercury/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/Button;

    .line 123
    new-instance v0, Lcom/qinbatista/mercury/MainActivity$2;

    invoke-direct {v0, p0}, Lcom/qinbatista/mercury/MainActivity$2;-><init>(Lcom/qinbatista/mercury/MainActivity;)V

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const p1, 0x7f030007

    .line 132
    invoke-virtual {p0, p1}, Lcom/qinbatista/mercury/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/Button;

    .line 133
    new-instance v0, Lcom/qinbatista/mercury/MainActivity$3;

    invoke-direct {v0, p0}, Lcom/qinbatista/mercury/MainActivity$3;-><init>(Lcom/qinbatista/mercury/MainActivity;)V

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const p1, 0x7f030003

    .line 142
    invoke-virtual {p0, p1}, Lcom/qinbatista/mercury/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/Button;

    .line 143
    new-instance v0, Lcom/qinbatista/mercury/MainActivity$4;

    invoke-direct {v0, p0}, Lcom/qinbatista/mercury/MainActivity$4;-><init>(Lcom/qinbatista/mercury/MainActivity;)V

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const p1, 0x7f030004

    .line 152
    invoke-virtual {p0, p1}, Lcom/qinbatista/mercury/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/Button;

    .line 153
    new-instance v0, Lcom/qinbatista/mercury/MainActivity$5;

    invoke-direct {v0, p0}, Lcom/qinbatista/mercury/MainActivity$5;-><init>(Lcom/qinbatista/mercury/MainActivity;)V

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const p1, 0x7f030005

    .line 162
    invoke-virtual {p0, p1}, Lcom/qinbatista/mercury/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/Button;

    .line 163
    new-instance v0, Lcom/qinbatista/mercury/MainActivity$6;

    invoke-direct {v0, p0}, Lcom/qinbatista/mercury/MainActivity$6;-><init>(Lcom/qinbatista/mercury/MainActivity;)V

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const p1, 0x7f030006

    .line 171
    invoke-virtual {p0, p1}, Lcom/qinbatista/mercury/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/Button;

    .line 172
    new-instance v0, Lcom/qinbatista/mercury/MainActivity$7;

    invoke-direct {v0, p0}, Lcom/qinbatista/mercury/MainActivity$7;-><init>(Lcom/qinbatista/mercury/MainActivity;)V

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const/high16 p1, 0x7f030000

    .line 179
    invoke-virtual {p0, p1}, Lcom/qinbatista/mercury/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/Button;

    .line 180
    new-instance v0, Lcom/qinbatista/mercury/MainActivity$8;

    invoke-direct {v0, p0}, Lcom/qinbatista/mercury/MainActivity$8;-><init>(Lcom/qinbatista/mercury/MainActivity;)V

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    const p1, 0x7f030001

    .line 188
    invoke-virtual {p0, p1}, Lcom/qinbatista/mercury/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/Button;

    .line 189
    new-instance v0, Lcom/qinbatista/mercury/MainActivity$9;

    invoke-direct {v0, p0}, Lcom/qinbatista/mercury/MainActivity$9;-><init>(Lcom/qinbatista/mercury/MainActivity;)V

    invoke-virtual {p1, v0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    return-void
.end method

.method public onDestroy()V
    .locals 1

    .line 229
    invoke-super {p0}, Landroid/app/Activity;->onDestroy()V

    .line 230
    iget-object v0, p0, Lcom/qinbatista/mercury/MainActivity;->MercurySDK:Lcom/mercury/game/MercuryActivity;

    invoke-virtual {v0}, Lcom/mercury/game/MercuryActivity;->onDestroy()V

    return-void
.end method

.method public onNewIntent(Landroid/content/Intent;)V
    .locals 1

    .line 241
    invoke-super {p0, p1}, Landroid/app/Activity;->onNewIntent(Landroid/content/Intent;)V

    .line 242
    iget-object v0, p0, Lcom/qinbatista/mercury/MainActivity;->MercurySDK:Lcom/mercury/game/MercuryActivity;

    invoke-virtual {v0, p1}, Lcom/mercury/game/MercuryActivity;->onNewIntent(Landroid/content/Intent;)V

    return-void
.end method

.method public onPause()V
    .locals 1

    .line 203
    invoke-super {p0}, Landroid/app/Activity;->onPause()V

    .line 204
    iget-object v0, p0, Lcom/qinbatista/mercury/MainActivity;->MercurySDK:Lcom/mercury/game/MercuryActivity;

    invoke-virtual {v0}, Lcom/mercury/game/MercuryActivity;->onPause()V

    return-void
.end method

.method public onRestart()V
    .locals 1

    .line 217
    invoke-super {p0}, Landroid/app/Activity;->onRestart()V

    .line 218
    iget-object v0, p0, Lcom/qinbatista/mercury/MainActivity;->MercurySDK:Lcom/mercury/game/MercuryActivity;

    invoke-virtual {v0}, Lcom/mercury/game/MercuryActivity;->onRestart()V

    return-void
.end method

.method public onResume()V
    .locals 1

    .line 223
    invoke-super {p0}, Landroid/app/Activity;->onResume()V

    .line 224
    iget-object v0, p0, Lcom/qinbatista/mercury/MainActivity;->MercurySDK:Lcom/mercury/game/MercuryActivity;

    invoke-virtual {v0}, Lcom/mercury/game/MercuryActivity;->onResume()V

    return-void
.end method

.method public onStop()V
    .locals 1

    .line 210
    invoke-super {p0}, Landroid/app/Activity;->onStop()V

    .line 211
    iget-object v0, p0, Lcom/qinbatista/mercury/MainActivity;->MercurySDK:Lcom/mercury/game/MercuryActivity;

    invoke-virtual {v0}, Lcom/mercury/game/MercuryActivity;->onStop()V

    return-void
.end method
