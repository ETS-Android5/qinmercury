.class public final Lcom/unity3d/player/i;
.super Ljava/lang/Object;


# static fields
.field static final a:Z

.field static final b:Z

.field static final c:Z

.field static final d:Z

.field static final e:Lcom/unity3d/player/d;


# direct methods
.method static constructor <clinit>()V
    .locals 4

    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/4 v1, 0x1

    const/4 v2, 0x0

    const/16 v3, 0x13

    if-lt v0, v3, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    sput-boolean v0, Lcom/unity3d/player/i;->a:Z

    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v3, 0x15

    if-lt v0, v3, :cond_1

    const/4 v0, 0x1

    goto :goto_1

    :cond_1
    const/4 v0, 0x0

    :goto_1
    sput-boolean v0, Lcom/unity3d/player/i;->b:Z

    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v3, 0x17

    if-lt v0, v3, :cond_2

    const/4 v0, 0x1

    goto :goto_2

    :cond_2
    const/4 v0, 0x0

    :goto_2
    sput-boolean v0, Lcom/unity3d/player/i;->c:Z

    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v3, 0x18

    if-lt v0, v3, :cond_3

    goto :goto_3

    :cond_3
    const/4 v1, 0x0

    :goto_3
    sput-boolean v1, Lcom/unity3d/player/i;->d:Z

    sget-boolean v0, Lcom/unity3d/player/i;->c:Z

    if-eqz v0, :cond_4

    new-instance v0, Lcom/unity3d/player/g;

    invoke-direct {v0}, Lcom/unity3d/player/g;-><init>()V

    goto :goto_4

    :cond_4
    const/4 v0, 0x0

    :goto_4
    sput-object v0, Lcom/unity3d/player/i;->e:Lcom/unity3d/player/d;

    return-void
.end method
