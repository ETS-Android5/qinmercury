.class public Lcom/mercury/game/MercuryActivity;
.super Ljava/lang/Object;
.source "MercuryActivity.java"


# static fields
.field public static ChannelForServer:Ljava/lang/String; = null

.field public static DeviceId:Ljava/lang/String; = ""

.field public static LongChannelID:Ljava/lang/String; = ""

.field public static Platform:I = -0x1

.field public static SavePidName:Ljava/lang/String; = ""

.field public static SortChannelID:Ljava/lang/String; = ""

.field public static activityforappbase:Lcom/mercury/game/MercuryActivity; = null

.field private static img:Landroid/widget/ImageView; = null

.field public static isLogOpen:Ljava/lang/String; = ""

.field public static mContext:Landroid/content/Context;

.field public static mSimOperatorId:I

.field public static nikeString:Ljava/lang/String;

.field public static packagenameforuse:Ljava/lang/String;


# instance fields
.field private mChannelId:I

.field private mExtSDKId:I

.field public mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

.field private mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

.field public platform:I


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>()V
    .locals 1

    .line 38
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, -0x1

    .line 47
    iput v0, p0, Lcom/mercury/game/MercuryActivity;->mExtSDKId:I

    return-void
.end method

.method public static LogLocal(Ljava/lang/String;)V
    .locals 1

    const-string v0, "MercurySDK"

    .line 395
    invoke-static {v0, p0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method static synthetic access$000(Lcom/mercury/game/MercuryActivity;)Lcom/mercury/game/InAppChannel/InAppChannel;
    .locals 0

    .line 38
    iget-object p0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    return-object p0
.end method

.method public static getInstance()Ljava/lang/Object;
    .locals 1

    .line 266
    sget v0, Lcom/mercury/game/util/MercuryConst;->Unity:I

    sput v0, Lcom/mercury/game/MercuryActivity;->Platform:I

    .line 267
    sget-object v0, Lcom/mercury/game/MercuryActivity;->mContext:Landroid/content/Context;

    return-object v0
.end method


# virtual methods
.method public ChannelSplash()V
    .locals 4

    const-string v0, "[MercuryActivity][ChannelSplash] ChannelSplash.png"

    .line 74
    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    .line 76
    :try_start_0
    new-instance v0, Landroid/widget/RelativeLayout$LayoutParams;

    const/4 v1, -0x1

    invoke-direct {v0, v1, v1}, Landroid/widget/RelativeLayout$LayoutParams;-><init>(II)V

    const-string v1, "ChannelSplash.png"

    .line 80
    sget-object v2, Lcom/mercury/game/MercuryActivity;->mContext:Landroid/content/Context;

    invoke-virtual {v2}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v2}, Landroid/content/res/Resources;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v2

    invoke-virtual {v2, v1}, Landroid/content/res/AssetManager;->open(Ljava/lang/String;)Ljava/io/InputStream;

    move-result-object v1

    if-eqz v1, :cond_0

    .line 82
    invoke-static {v1}, Landroid/graphics/BitmapFactory;->decodeStream(Ljava/io/InputStream;)Landroid/graphics/Bitmap;

    move-result-object v1

    .line 84
    sget-object v2, Lcom/mercury/game/MercuryActivity;->mContext:Landroid/content/Context;

    check-cast v2, Landroid/app/Activity;

    new-instance v3, Lcom/mercury/game/MercuryActivity$1;

    invoke-direct {v3, p0, v1, v0}, Lcom/mercury/game/MercuryActivity$1;-><init>(Lcom/mercury/game/MercuryActivity;Landroid/graphics/Bitmap;Landroid/widget/RelativeLayout$LayoutParams;)V

    invoke-virtual {v2, v3}, Landroid/app/Activity;->runOnUiThread(Ljava/lang/Runnable;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    .line 118
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 119
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "[MercuryActivity][ChannelSplash] init e="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/Exception;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    :cond_0
    :goto_0
    return-void
.end method

.method public Exchange()V
    .locals 2

    .line 288
    new-instance v0, Landroid/os/Handler;

    sget-object v1, Lcom/mercury/game/MercuryActivity;->mContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getMainLooper()Landroid/os/Looper;

    move-result-object v1

    invoke-direct {v0, v1}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    new-instance v1, Lcom/mercury/game/MercuryActivity$3;

    invoke-direct {v1, p0}, Lcom/mercury/game/MercuryActivity$3;-><init>(Lcom/mercury/game/MercuryActivity;)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    return-void
.end method

.method public ExitGame()V
    .locals 2

    .line 162
    new-instance v0, Landroid/os/Handler;

    sget-object v1, Lcom/mercury/game/MercuryActivity;->mContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getMainLooper()Landroid/os/Looper;

    move-result-object v1

    invoke-direct {v0, v1}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    new-instance v1, Lcom/mercury/game/MercuryActivity$2;

    invoke-direct {v1, p0}, Lcom/mercury/game/MercuryActivity$2;-><init>(Lcom/mercury/game/MercuryActivity;)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    return-void
.end method

.method public InitAd(Lcom/mercury/game/util/APPBaseInterface;)V
    .locals 2

    .line 143
    sget-object v0, Lcom/mercury/game/MercuryActivity;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getApplicationContext()Landroid/content/Context;

    .line 144
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity][InitAd] Local InitAd()->"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    .line 145
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    sget-object v1, Lcom/mercury/game/MercuryActivity;->mContext:Landroid/content/Context;

    check-cast v1, Landroid/app/Activity;

    invoke-virtual {v0, v1, p1}, Lcom/mercury/game/InAppAdvertisement/InAppAD;->ActivityInit(Landroid/app/Activity;Lcom/mercury/game/util/APPBaseInterface;)V

    return-void
.end method

.method public InitCarriers(Lcom/mercury/game/util/APPBaseInterface;)V
    .locals 0

    return-void
.end method

.method public InitChannel(Lcom/mercury/game/util/APPBaseInterface;)V
    .locals 2

    .line 136
    sget-object v0, Lcom/mercury/game/MercuryActivity;->mContext:Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getApplicationContext()Landroid/content/Context;

    .line 137
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity][InitChannel] Local InitChannel()->"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    .line 138
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    sget-object v1, Lcom/mercury/game/MercuryActivity;->mContext:Landroid/content/Context;

    check-cast v1, Landroid/app/Activity;

    invoke-virtual {v0, v1, p1}, Lcom/mercury/game/InAppChannel/InAppChannel;->ActivityInit(Landroid/app/Activity;Lcom/mercury/game/util/APPBaseInterface;)V

    return-void
.end method

.method public InitSDK(Landroid/content/Context;Lcom/mercury/game/util/APPBaseInterface;)V
    .locals 0

    .line 64
    sput-object p1, Lcom/mercury/game/MercuryActivity;->mContext:Landroid/content/Context;

    .line 65
    invoke-virtual {p0}, Lcom/mercury/game/MercuryActivity;->ChannelSplash()V

    .line 66
    new-instance p1, Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-direct {p1}, Lcom/mercury/game/InAppChannel/InAppChannel;-><init>()V

    iput-object p1, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    .line 67
    new-instance p1, Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-direct {p1}, Lcom/mercury/game/InAppAdvertisement/InAppAD;-><init>()V

    iput-object p1, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    .line 68
    sput-object p0, Lcom/mercury/game/MercuryActivity;->activityforappbase:Lcom/mercury/game/MercuryActivity;

    .line 69
    invoke-virtual {p0, p2}, Lcom/mercury/game/MercuryActivity;->InitChannel(Lcom/mercury/game/util/APPBaseInterface;)V

    .line 70
    invoke-virtual {p0, p2}, Lcom/mercury/game/MercuryActivity;->InitAd(Lcom/mercury/game/util/APPBaseInterface;)V

    return-void
.end method

.method public LongChannelID()Ljava/lang/String;
    .locals 1

    .line 178
    sget-object v0, Lcom/mercury/game/MercuryActivity;->LongChannelID:Ljava/lang/String;

    return-object v0
.end method

.method public Message(Ljava/lang/String;)V
    .locals 2

    .line 391
    sget-object v0, Lcom/mercury/game/MercuryActivity;->mContext:Landroid/content/Context;

    const/4 v1, 0x0

    invoke-static {v0, p1, v1}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object p1

    invoke-virtual {p1}, Landroid/widget/Toast;->show()V

    return-void
.end method

.method public Purchase(Ljava/lang/String;)V
    .locals 2

    .line 155
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity][purchaseProduct] "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    .line 156
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0, p1}, Lcom/mercury/game/InAppChannel/InAppChannel;->Purchase(Ljava/lang/String;)V

    return-void
.end method

.method public SavePidName()Ljava/lang/String;
    .locals 5

    .line 125
    new-instance v0, Ljava/text/SimpleDateFormat;

    const-string v1, "yyyyMMddHHmmss"

    invoke-direct {v0, v1}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;)V

    .line 126
    new-instance v1, Ljava/util/Date;

    invoke-direct {v1}, Ljava/util/Date;-><init>()V

    invoke-virtual {v0, v1}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v0

    .line 128
    invoke-static {}, Ljava/lang/Math;->random()D

    move-result-wide v1

    const-wide/high16 v3, 0x3ff0000000000000L    # 1.0

    add-double/2addr v1, v3

    const-wide v3, 0x40f86a0000000000L    # 100000.0

    mul-double v1, v1, v3

    double-to-int v1, v1

    .line 129
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public SharePicture(Ljava/lang/String;ZLcom/mercury/game/util/APPBaseInterface;)V
    .locals 0

    return-void
.end method

.method public ShortChannelID()Ljava/lang/String;
    .locals 1

    .line 174
    sget-object v0, Lcom/mercury/game/MercuryActivity;->SortChannelID:Ljava/lang/String;

    return-object v0
.end method

.method public ShowTencentAd()V
    .locals 2

    .line 366
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity]->ShowTencentAd:mInAppChannel="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    .line 367
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    if-eqz v0, :cond_0

    .line 369
    invoke-virtual {v0}, Lcom/mercury/game/InAppChannel/InAppChannel;->ShowTencentAd()V

    :cond_0
    return-void
.end method

.method public TencentLogin(I)V
    .locals 2

    .line 342
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity]->TencentLogin:mInAppChannel="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v1, " kind="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    .line 343
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    if-eqz v0, :cond_0

    .line 345
    invoke-virtual {v0, p1}, Lcom/mercury/game/InAppChannel/InAppChannel;->login(I)V

    :cond_0
    return-void
.end method

.method public TencentLoginOut()V
    .locals 2

    .line 350
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity]->TencentLoginOut:mInAppChannel="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    .line 351
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    if-eqz v0, :cond_0

    .line 353
    invoke-virtual {v0}, Lcom/mercury/game/InAppChannel/InAppChannel;->logout()V

    :cond_0
    return-void
.end method

.method public TencentLoginOutOnly()V
    .locals 2

    .line 358
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity]->TencentLoginOutOnly:mInAppChannel="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    .line 359
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    if-eqz v0, :cond_0

    .line 361
    invoke-virtual {v0}, Lcom/mercury/game/InAppChannel/InAppChannel;->TencentLoginOutOnly()V

    :cond_0
    return-void
.end method

.method public getChannelId()I
    .locals 1

    .line 272
    iget v0, p0, Lcom/mercury/game/MercuryActivity;->mChannelId:I

    return v0
.end method

.method public getmInAppAD()Lcom/mercury/game/util/InAppBase;
    .locals 2

    .line 283
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity] getBaseInApp()->mInApp="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    .line 284
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    return-object v0
.end method

.method public getmInAppChannel()Lcom/mercury/game/util/InAppBase;
    .locals 2

    .line 277
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity] getBaseInApp()->mInApp="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    .line 278
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    return-object v0
.end method

.method public letUserLogin()V
    .locals 2

    .line 309
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity]->letUserLogin:mInAppChannel="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    .line 310
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    if-eqz v0, :cond_0

    .line 312
    invoke-virtual {v0}, Lcom/mercury/game/InAppChannel/InAppChannel;->letUserLogin()V

    :cond_0
    return-void
.end method

.method public letUserLogout()V
    .locals 2

    .line 325
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity]->letUserLogout:mInAppChannel="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    .line 326
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    if-eqz v0, :cond_0

    .line 328
    invoke-virtual {v0}, Lcom/mercury/game/InAppChannel/InAppChannel;->letUserLogout()V

    :cond_0
    return-void
.end method

.method public onActivityResult(IILandroid/content/Intent;)V
    .locals 3

    .line 255
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    const-string v1, "[MercuryActivity]->onActivityResult:mInAppChannel="

    if-eqz v0, :cond_0

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0, p1, p2, p3}, Lcom/mercury/game/InAppChannel/InAppChannel;->onActivityResult(IILandroid/content/Intent;)V

    .line 256
    :cond_0
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    if-eqz v0, :cond_1

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-virtual {v0, p1, p2, p3}, Lcom/mercury/game/InAppAdvertisement/InAppAD;->onActivityResult(IILandroid/content/Intent;)V

    :cond_1
    return-void
.end method

.method public onDestroy()V
    .locals 2

    .line 250
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    if-eqz v0, :cond_0

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity] mInAppChannel onDestroy()->"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0}, Lcom/mercury/game/InAppChannel/InAppChannel;->onDestroy()V

    .line 251
    :cond_0
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    if-eqz v0, :cond_1

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity] mInAppAD onDestroy()->"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-virtual {v0}, Lcom/mercury/game/InAppAdvertisement/InAppAD;->onDestroy()V

    :cond_1
    return-void
.end method

.method public onNewIntent(Landroid/content/Intent;)V
    .locals 2

    .line 260
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    if-eqz v0, :cond_0

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity]->onNewIntent:mInAppChannel="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0, p1}, Lcom/mercury/game/InAppChannel/InAppChannel;->onNewIntent(Landroid/content/Intent;)V

    .line 261
    :cond_0
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    if-eqz v0, :cond_1

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity]->onNewIntent:mInAppAD="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-virtual {v0, p1}, Lcom/mercury/game/InAppAdvertisement/InAppAD;->onNewIntent(Landroid/content/Intent;)V

    :cond_1
    return-void
.end method

.method public onPause()V
    .locals 2

    .line 223
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    if-eqz v0, :cond_0

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity] mInAppChannel onPause()->"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0}, Lcom/mercury/game/InAppChannel/InAppChannel;->onPause()V

    .line 224
    :cond_0
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    if-eqz v0, :cond_1

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity] mInAppAD onPause()->"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-virtual {v0}, Lcom/mercury/game/InAppAdvertisement/InAppAD;->onPause()V

    :cond_1
    return-void
.end method

.method public onRestart()V
    .locals 2

    .line 239
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    if-eqz v0, :cond_0

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity] mInAppChannel onRestart()->"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0}, Lcom/mercury/game/InAppChannel/InAppChannel;->onRestart()V

    .line 240
    :cond_0
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    if-eqz v0, :cond_1

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity] mInAppAD onRestart()->"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-virtual {v0}, Lcom/mercury/game/InAppAdvertisement/InAppAD;->onRestart()V

    :cond_1
    return-void
.end method

.method public onResume()V
    .locals 2

    .line 244
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    if-eqz v0, :cond_0

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity] mInAppChannel onResume()->"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0}, Lcom/mercury/game/InAppChannel/InAppChannel;->onResume()V

    .line 245
    :cond_0
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    if-eqz v0, :cond_1

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity] mInAppAD onResume()->"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-virtual {v0}, Lcom/mercury/game/InAppAdvertisement/InAppAD;->onResume()V

    :cond_1
    return-void
.end method

.method public onStart()V
    .locals 2

    .line 233
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    if-eqz v0, :cond_0

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity] mInAppChannel onStart()->"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0}, Lcom/mercury/game/InAppChannel/InAppChannel;->onStart()V

    .line 234
    :cond_0
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    if-eqz v0, :cond_1

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity] mInAppAD onStart()->"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-virtual {v0}, Lcom/mercury/game/InAppAdvertisement/InAppAD;->onStart()V

    :cond_1
    return-void
.end method

.method public onStop()V
    .locals 2

    .line 228
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    if-eqz v0, :cond_0

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity] mInAppChannel onStop()->"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0}, Lcom/mercury/game/InAppChannel/InAppChannel;->onStop()V

    .line 229
    :cond_0
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    if-eqz v0, :cond_1

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity] mInAppAD onStop()->"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-virtual {v0}, Lcom/mercury/game/InAppAdvertisement/InAppAD;->onStop()V

    :cond_1
    return-void
.end method

.method public repairindentRequest()V
    .locals 0

    return-void
.end method

.method public respondCPserver()V
    .locals 0

    return-void
.end method

.method public showDiffLogin()V
    .locals 2

    .line 333
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity]->showDiffLogin:mInAppChannel="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    .line 334
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    if-eqz v0, :cond_0

    .line 336
    invoke-virtual {v0}, Lcom/mercury/game/InAppChannel/InAppChannel;->showDiffLogin()V

    :cond_0
    return-void
.end method

.method public showMessageDialog()V
    .locals 2

    .line 382
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity]->showMessageDialog:mInAppChannel="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    .line 383
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    if-eqz v0, :cond_0

    .line 385
    invoke-virtual {v0}, Lcom/mercury/game/InAppChannel/InAppChannel;->showMessageDialog()V

    :cond_0
    return-void
.end method

.method public show_banner()V
    .locals 2

    .line 195
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity][show_banner]"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    .line 196
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Lcom/mercury/game/InAppAdvertisement/InAppAD;->show_banner()V

    :cond_0
    return-void
.end method

.method public show_insert()V
    .locals 2

    .line 201
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity][show_insert]"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    .line 202
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Lcom/mercury/game/InAppAdvertisement/InAppAD;->show_insert()V

    :cond_0
    return-void
.end method

.method public show_out()V
    .locals 2

    .line 211
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity][show_out]"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    .line 212
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Lcom/mercury/game/InAppAdvertisement/InAppAD;->show_out()V

    :cond_0
    return-void
.end method

.method public show_push()V
    .locals 2

    .line 206
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity][show_push]"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    .line 207
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Lcom/mercury/game/InAppAdvertisement/InAppAD;->show_push()V

    :cond_0
    return-void
.end method

.method public show_video()V
    .locals 2

    .line 216
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity][show_video]"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    .line 217
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Lcom/mercury/game/InAppAdvertisement/InAppAD;->show_video()V

    :cond_0
    return-void
.end method

.method public stopWaiting()V
    .locals 2

    .line 317
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "[MercuryActivity]->stopWaiting:mInAppChannel="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    .line 318
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    if-eqz v0, :cond_0

    .line 320
    invoke-virtual {v0}, Lcom/mercury/game/InAppChannel/InAppChannel;->stopWaiting()V

    :cond_0
    return-void
.end method

.method public swtichuser()V
    .locals 1

    const-string v0, "[MercuryActivity][swtichuser]"

    .line 183
    invoke-static {v0}, Lcom/mercury/game/MercuryActivity;->LogLocal(Ljava/lang/String;)V

    .line 184
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppChannel:Lcom/mercury/game/InAppChannel/InAppChannel;

    invoke-virtual {v0}, Lcom/mercury/game/InAppChannel/InAppChannel;->swtichuser()V

    return-void
.end method

.method public uploadclick()V
    .locals 1

    .line 188
    iget-object v0, p0, Lcom/mercury/game/MercuryActivity;->mInAppAD:Lcom/mercury/game/InAppAdvertisement/InAppAD;

    if-eqz v0, :cond_0

    .line 190
    invoke-virtual {v0}, Lcom/mercury/game/InAppAdvertisement/InAppAD;->uploadclick()V

    :cond_0
    return-void
.end method
