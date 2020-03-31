.class public Lcom/mercury/game/util/InAppBase;
.super Ljava/lang/Object;
.source "InAppBase.java"


# static fields
.field public static OrderID:Ljava/lang/String; = ""

.field public static appinterface:Lcom/mercury/game/util/APPBaseInterface; = null

.field public static key:Ljava/lang/String; = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALnMsImln+S8fxJtf7NDqQhh8EA318buO6ScnyzNbaBkVmu4oL97ggRrgmI7z1YKYkPNs6ymufqjHDAAqqyMm+KgNYodKsr+LtWOxwHVYEI7rdZL6ioNoOyv396pGQjoyXDB6FfP+dGXGN/WMSyJrcXn2tdY025S+dBbQaMTnqhvAgMBAAECgYEAiaTJN//aF1NJdDZofz5lsA8WNAzqrrX4u3dIOKGrUEJk/4KUm6Z86JdYzTtv21bv+zkdnY8agkJp9GnaBuBX7mVEg3tHqzZrOCq5nVX9OHJoMytGwLxlHvejBZPVS1PmkfFnEYRAkBcti5nmsY+fCV5/lxVScrYGdzfrf1vio1ECQQDpxHmicfwYCTb0ZcUIKU0CQvfD9Te/94TIxr82EKcq/pPfoU3vQY+Ks7LI41Zc2kRYhT1dXezzKf/r2FjAMid3AkEAy3hXmEUZYdg+SPVURzrQt6PGjvLv/5Uxe75t8Ovx2JxHRgCD5j7IArzDg7ACMNn50xMHfQUD4QVdEG2vtvK0yQJBAJp/wiw8zXJNVMa+JDS6pyzhecNHZGs5eccApAtlgjaGPtFEWK/SUr5G+diPd9qyXw1qMh5tH1eu4HfNawrLmw0CQBRZ3hEJ4EcMFPbBKwPQ2y1zARotLFoY9xEUc/Sj9NWgk/Rpesfdwa2cacXTJfTy6Gz3O0mC5eds3OkWv3uB/RkCQQCEkR2M+vdStNq08KV7g+bOZKXElvnYjpUHMdVkO+oeXHPhLf9ltlpBOynA7WA60Jdg0OJa14ngZcu2loawd+q2"

.field public static qc:Lcom/mercury/game/util/MercuryConst; = null

.field public static sTestMode:Z = false


# instance fields
.field protected mAppContext:Landroid/content/Context;

.field protected mContext:Landroid/app/Activity;

.field protected mInstance:Lcom/mercury/game/util/InAppBase;

.field protected mProductDescription:Ljava/lang/String;

.field protected mProductId:Ljava/lang/String;

.field protected mProductPrice:F


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 39
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public ActivityInit(Landroid/app/Activity;Lcom/mercury/game/util/APPBaseInterface;)V
    .locals 1

    .line 67
    iput-object p1, p0, Lcom/mercury/game/util/InAppBase;->mContext:Landroid/app/Activity;

    .line 68
    iput-object p0, p0, Lcom/mercury/game/util/InAppBase;->mInstance:Lcom/mercury/game/util/InAppBase;

    .line 69
    new-instance p1, Lcom/mercury/game/util/MercuryConst;

    invoke-direct {p1}, Lcom/mercury/game/util/MercuryConst;-><init>()V

    sput-object p1, Lcom/mercury/game/util/InAppBase;->qc:Lcom/mercury/game/util/MercuryConst;

    .line 70
    sput-object p2, Lcom/mercury/game/util/InAppBase;->appinterface:Lcom/mercury/game/util/APPBaseInterface;

    .line 71
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "[InAppBase]->init:InAppBase.appinterface="

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p1}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    return-void
.end method

.method public ApplicationInit(Landroid/app/Application;)V
    .locals 2

    .line 84
    iput-object p1, p0, Lcom/mercury/game/util/InAppBase;->mAppContext:Landroid/content/Context;

    .line 85
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[InAppBase]->init:InAppBase.ApplicationInit="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p1}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    return-void
.end method

.method public Exchange()V
    .locals 1

    .line 124
    sget-object v0, Lcom/mercury/game/util/InAppBase;->qc:Lcom/mercury/game/util/MercuryConst;

    invoke-virtual {v0, p0}, Lcom/mercury/game/util/MercuryConst;->Exchange(Lcom/mercury/game/util/InAppBase;)V

    return-void
.end method

.method public ExitGame()V
    .locals 1

    .line 131
    sget-object v0, Lcom/mercury/game/util/InAppBase;->qc:Lcom/mercury/game/util/MercuryConst;

    invoke-virtual {v0}, Lcom/mercury/game/util/MercuryConst;->ExitGame()V

    return-void
.end method

.method public FunctionC(Ljava/lang/String;)V
    .locals 0

    return-void
.end method

.method public FunctionK(Ljava/lang/String;)V
    .locals 0

    return-void
.end method

.method public FunctionL(Ljava/lang/String;)V
    .locals 1

    .line 127
    sget-object v0, Lcom/mercury/game/util/InAppBase;->qc:Lcom/mercury/game/util/MercuryConst;

    invoke-virtual {v0, p1}, Lcom/mercury/game/util/MercuryConst;->FunctionL(Ljava/lang/String;)V

    return-void
.end method

.method public FunctionS(Ljava/lang/String;)V
    .locals 0

    return-void
.end method

.method public Purchase(Ljava/lang/String;)V
    .locals 1

    .line 77
    invoke-static {p1}, Lcom/mercury/game/util/MercuryConst;->PayInfo(Ljava/lang/String;)V

    .line 78
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "[InAppBase][Purchase] MercuryConst.QinPid="

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v0, Lcom/mercury/game/util/MercuryConst;->QinPid:Ljava/lang/String;

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p1}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    .line 79
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "[InAppBase][Purchase] MercuryConst.Qindesc="

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v0, Lcom/mercury/game/util/MercuryConst;->Qindesc:Ljava/lang/String;

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p1}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    .line 80
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "[InAppBase][Purchase] MercuryConst.Qinpricefloat="

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget v0, Lcom/mercury/game/util/MercuryConst;->Qinpricefloat:F

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p1}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    return-void
.end method

.method public SharePicture(Ljava/lang/String;Z)V
    .locals 0

    return-void
.end method

.method public ShowTencentAd()V
    .locals 0

    return-void
.end method

.method public TencentLoginOutOnly()V
    .locals 0

    return-void
.end method

.method public attachBaseContext(Landroid/content/Context;)V
    .locals 0

    return-void
.end method

.method public isPurchase()Z
    .locals 1

    const/4 v0, 0x1

    return v0
.end method

.method public letUserLogin()V
    .locals 0

    return-void
.end method

.method public letUserLogout()V
    .locals 0

    return-void
.end method

.method public login(I)V
    .locals 0

    return-void
.end method

.method public logout()V
    .locals 0

    return-void
.end method

.method public onActivityResult()V
    .locals 0

    return-void
.end method

.method public onActivityResult(IILandroid/content/Intent;)V
    .locals 0

    return-void
.end method

.method public onCreateOptionsMenu(Landroid/view/Menu;)Z
    .locals 0

    const/4 p1, 0x0

    return p1
.end method

.method public onDestroy()V
    .locals 0

    return-void
.end method

.method public onFunctionCallBack(Ljava/lang/String;)V
    .locals 1

    .line 151
    sget-object v0, Lcom/mercury/game/util/InAppBase;->qc:Lcom/mercury/game/util/MercuryConst;

    invoke-virtual {v0, p1, p0}, Lcom/mercury/game/util/MercuryConst;->onFunctionCallBack(Ljava/lang/String;Lcom/mercury/game/util/InAppBase;)V

    return-void
.end method

.method public onLoadLib()V
    .locals 0

    return-void
.end method

.method public onLoginCancel(Ljava/lang/String;)V
    .locals 1

    .line 147
    sget-object v0, Lcom/mercury/game/util/InAppBase;->qc:Lcom/mercury/game/util/MercuryConst;

    invoke-virtual {v0, p1, p0}, Lcom/mercury/game/util/MercuryConst;->onLoginCancel(Ljava/lang/String;Lcom/mercury/game/util/InAppBase;)V

    return-void
.end method

.method public onLoginFailed(Ljava/lang/String;)V
    .locals 1

    .line 149
    sget-object v0, Lcom/mercury/game/util/InAppBase;->qc:Lcom/mercury/game/util/MercuryConst;

    invoke-virtual {v0, p1, p0}, Lcom/mercury/game/util/MercuryConst;->onLoginFailed(Ljava/lang/String;Lcom/mercury/game/util/InAppBase;)V

    return-void
.end method

.method public onLoginSuccess(Ljava/lang/String;)V
    .locals 1

    .line 144
    sget-object v0, Lcom/mercury/game/util/InAppBase;->qc:Lcom/mercury/game/util/MercuryConst;

    invoke-virtual {v0, p1, p0}, Lcom/mercury/game/util/MercuryConst;->onLoginSuccess(Ljava/lang/String;Lcom/mercury/game/util/InAppBase;)V

    return-void
.end method

.method public onNewIntent(Landroid/content/Intent;)V
    .locals 0

    return-void
.end method

.method public onPause()V
    .locals 0

    return-void
.end method

.method public onPurchaseCanceled(Ljava/lang/String;)V
    .locals 1

    .line 141
    sget-object v0, Lcom/mercury/game/util/InAppBase;->qc:Lcom/mercury/game/util/MercuryConst;

    invoke-virtual {v0, p1, p0}, Lcom/mercury/game/util/MercuryConst;->onPurchaseCanceled(Ljava/lang/String;Lcom/mercury/game/util/InAppBase;)V

    return-void
.end method

.method public onPurchaseFailed(Ljava/lang/String;)V
    .locals 1

    .line 138
    sget-object v0, Lcom/mercury/game/util/InAppBase;->qc:Lcom/mercury/game/util/MercuryConst;

    invoke-virtual {v0, p1, p0}, Lcom/mercury/game/util/MercuryConst;->onPurchaseFailed(Ljava/lang/String;Lcom/mercury/game/util/InAppBase;)V

    return-void
.end method

.method public onPurchaseSuccess(Ljava/lang/String;)V
    .locals 1

    .line 135
    sget-object v0, Lcom/mercury/game/util/InAppBase;->qc:Lcom/mercury/game/util/MercuryConst;

    invoke-virtual {v0, p1, p0}, Lcom/mercury/game/util/MercuryConst;->onPurchaseSuccess(Ljava/lang/String;Lcom/mercury/game/util/InAppBase;)V

    return-void
.end method

.method public onRestart()V
    .locals 0

    return-void
.end method

.method public onResume()V
    .locals 0

    return-void
.end method

.method public onStart()V
    .locals 0

    return-void
.end method

.method public onStop()V
    .locals 0

    return-void
.end method

.method public purchaseSuper(Ljava/lang/String;Ljava/lang/String;F)V
    .locals 0

    return-void
.end method

.method public setExtraParam(Ljava/lang/String;Ljava/lang/String;)V
    .locals 0

    return-void
.end method

.method public showDiffLogin()V
    .locals 0

    return-void
.end method

.method public showMessageDialog()V
    .locals 0

    return-void
.end method

.method public show_banner()V
    .locals 0

    return-void
.end method

.method public show_cp()V
    .locals 0

    return-void
.end method

.method public show_insert()V
    .locals 0

    return-void
.end method

.method public show_out()V
    .locals 0

    return-void
.end method

.method public show_push()V
    .locals 0

    return-void
.end method

.method public show_ts(Z)V
    .locals 0

    return-void
.end method

.method public show_tt()V
    .locals 0

    return-void
.end method

.method public show_video()V
    .locals 0

    return-void
.end method

.method public show_wt()V
    .locals 0

    return-void
.end method

.method public stopWaiting()V
    .locals 0

    return-void
.end method

.method public swtichuser()V
    .locals 0

    return-void
.end method

.method public uploadclick()V
    .locals 0

    return-void
.end method
