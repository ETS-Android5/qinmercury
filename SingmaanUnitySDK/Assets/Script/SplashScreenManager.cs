using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using System.Collections.Generic;

//using Umeng;
public enum LoginType { QQ, WX, GUEST }

public class SplashScreenManager : MonoBehaviour
{

    // Splash screen handles
    private float splashStartFadeTimer = -1.0f;
    private float splashFadeDuration = 3f;//splash显示隐藏速率(越小显示越快)
    private float splashOldAlphaValue = 0.0f;
    private float splashNewAlphaValue = 1.0f;
    private float splashCurrentAlphaValue = 0.0f;
    public Texture2D[] splashes;
    private int splashIndex = -1;
    private float splashDisplayTimer = -1.0f;
    private float splashDisplayDuration = 1.0f;
    //[HideInInspector]
    public bool DisplaySplash = true;
    public Texture2D blackPixel;
    bool loadingOver = false;
    private AsyncOperation async;
    //转圈
    public Transform LoadingCircle;
    //登陆框模块
    public GameObject LoginDialog;

    //闪屏秒数
    //public float splashDuration = 10.0f;

    //下一场景
    public string nextSceneName = "DemoMenu";



    [System.Serializable]
    public struct ChannelSplashs
    {
        public string channelName;
        public Texture2D[] splash;
    }
    [Header("--------不同渠道的闪屏设置--------")]
    private Dictionary<string, Texture2D[]> channelSplashsDic;
    public ChannelSplashs[] channelSplashs;
    private Texture2D[] currentSplashes;

    //	[SerializeField]
    //	bool
    //		isDebugVersion = false;
    //	[SerializeField]
    //	bool
    //		isTvVersion = false;
    #region Unity生命周期

    void Awake()
    {

        LoginDialog.SetActive(false);
        //sortSplashes();
        //nextSplash();
        ChannelSplashsSetting();
    }



    private void LoginSuccess()
    {
        GameObject.Find("LoadingCircle").transform.localPosition = new Vector3(0, 0, 0);//显示LoadingCircle
        //Application.LoadLevelAsync(nextSceneName);
        NeedToChangeScence();
    }
    void Start()
    {
        if (DisplaySplash==false)
        {
            Application.LoadLevel(nextSceneName);
        }
        else
        {
            nextSplash();
            StartCoroutine(PreloadingScence(nextSceneName));//预加载
        }

        //LoginDialog.SetActive(E2WSdk.Instance.isShowSplash || E2WSdk.Instance.isLogin);
        // if (!E2WSdk.Instance.isShowSplash && !E2WSdk.Instance.isLogin)
        // {
        //     Debug.Log("LoadLevel");
        //     //Application.LoadLevel(nextSceneName);
        //     NeedToChangeScence();

        // }
        // if (!E2WSdk.Instance.isShowSplash && E2WSdk.Instance.isLogin)
        // {
        //     LoginDialog.SetActive(true);
        // }
        // LoadingCircle.transform.localPosition = new Vector3(2000, 0, 0);//隐藏LoadingCircle

        // if (!E2WSdk.Instance.isTrialVersion)
        // {
        //     E2WSdk.Instance.Message("单机游戏，数据无法同步到其他设备；卸载游戏会导致数据丢失。");
        // }
        // LoginButtons[0].onClick.AddListener(OnWechatBtnClick);
        // LoginButtons[1].onClick.AddListener(OnGuestBtnClick);
        // LoginButtons[2].onClick.AddListener(OnQQBtnClick);

        // //-------模拟补单----------
        // if (PlayerPrefs.GetInt("IssueOrdeRsresend", 0) == 1)
        // {
        //     FindObjectOfType<E2WSdkEvent>().onExchangeSuccess("2");
        // }
        //-----------------
    }

    void Update()
    {
        if (splashStartFadeTimer != -1.0f && Time.time - splashStartFadeTimer < splashFadeDuration)
        {
            float fraction = (Time.time - splashStartFadeTimer) / splashFadeDuration;
            splashCurrentAlphaValue = Mathf.Lerp(splashOldAlphaValue, splashNewAlphaValue, fraction);
            if (splashIndex == splashes.Length - 1 && splashCurrentAlphaValue >= 0.95f)//最后一张图片显示完成不消失，知道场景load完成切换
            {
                LoadMainScene();
            }
        }
        else if (splashStartFadeTimer != -1.0f && Time.time - splashStartFadeTimer >= splashFadeDuration)
        {
            splashDisplayTimer = Time.time;
            splashCurrentAlphaValue = splashNewAlphaValue;
            splashStartFadeTimer = -1.0f;
            if (splashCurrentAlphaValue == 0.0f)
            {
                nextSplash();
            }
        }

        if (splashDisplayTimer != -1.0f && Time.time - splashDisplayTimer >= splashDisplayDuration && splashIndex + 1 <= splashes.Length)
        {
            splashOldAlphaValue = 1.0f;
            splashNewAlphaValue = 0.0f;
            splashCurrentAlphaValue = splashOldAlphaValue;
            splashDisplayTimer = -1.0f;
            splashStartFadeTimer = Time.time;
        }

        if (loadingOver && splashDisplayTimer != -1.0f && Time.time - splashDisplayTimer >= splashDisplayDuration && splashIndex + 1 >= splashes.Length)
        {
            splashOldAlphaValue = 1.0f;
            splashNewAlphaValue = 0.0f;
            splashCurrentAlphaValue = splashOldAlphaValue;
            splashDisplayTimer = -1.0f;
            splashStartFadeTimer = Time.time;
        }
    }
    #endregion


    #region Splash闪屏
    void sortSplashes()
    {
        Texture2D[] splashesFound = Resources.LoadAll<Texture2D>("Splash/");
        splashes = new Texture2D[splashesFound.Length];

        for (int i = 0; i < splashesFound.Length; i++)
        {
            splashes[i] = splashesFound[i];
        }

    }





    IEnumerator loadGame()
    {
        async = Application.LoadLevelAsync(nextSceneName);
        yield return async;
        loadingOver = true;
    }

    public void LoadMainScene()
    {

        loadingOver = true;
        // Application.LoadLevel(nextSceneName);
        NeedToChangeScence();
    }




    void OnGUI()
    {

        //GUI.depth = -10;
        GUI.DrawTexture(new Rect(0, 0, Screen.width, Screen.height), blackPixel);
        Color oldColor = GUI.color;
        GUI.color = new Color(GUI.color.r, GUI.color.g, GUI.color.b, splashCurrentAlphaValue);
        GUI.DrawTexture(new Rect(0, 0, Screen.width, Screen.height), blackPixel);
        float ratio = splashes[splashIndex].height / (splashes[splashIndex].width + 0.0f);
        float ratioScreen = Screen.height / (Screen.width + 0.0f);
        //if (ratio > ratioScreen && ratio < 1)
        float splashScale = 0.5f;

        Texture2D targetPic = splashes[splashIndex];

        if (ratioScreen > 1) //如果屏幕是竖屏，则固定图片宽度，，推算出高度（这样才能保持图片比例不变，上下留出空余把图放中间）
        {
            var H = Screen.width * ratio * splashScale;
            var xPos = (Screen.width - Screen.width * splashScale) * 0.5f;//X的起始坐标
            GUI.DrawTexture(new Rect(xPos, (Screen.height - H) * 0.5f, Screen.width * splashScale, H), splashes[splashIndex]);
        }
        else//如果屏幕是横屏，则固定图片高度，，推算出宽度
        {
            var W = Screen.height / ratio * splashScale;
            var yPos = (Screen.height - Screen.height * splashScale) * 0.5f;//Y的起始坐标
            GUI.DrawTexture(new Rect((Screen.width - W ) * 0.5f, yPos, W , Screen.height * splashScale), splashes[splashIndex]);
        }
        GUI.color = oldColor;
    }

    void nextSplash()
    {
        if (splashIndex + 1 <= splashes.Length)
        {

            splashIndex++;
            if (splashIndex == splashes.Length)
            {
                if (loadingOver)
                {

                    splashOldAlphaValue = 1.0f;
                    splashNewAlphaValue = 0.0f;
                }
                else
                {
                    splashOldAlphaValue = 0.0f;
                    splashNewAlphaValue = 1.0f;
                }
            }
            else
            {
                splashOldAlphaValue = 0.0f;
                splashNewAlphaValue = 1.0f;
            }
            splashCurrentAlphaValue = splashOldAlphaValue;
            splashStartFadeTimer = Time.time;
        }
        else if (loadingOver)
        {
            splashStartFadeTimer = -1.0f;
            splashDisplayTimer = -1.0f;
            DisplaySplash = true;

        }
    }
    #endregion

    #region 按钮点击事件
    public void OnWechatBtnClick()
    {
        // E2WSdk.Instance.Login(LoginType.QQ);
    }
    public void OnQQBtnClick()
    {
        // E2WSdk.Instance.Login(LoginType.WX);
    }
    public void OnGuestBtnClick()
    {
        // E2WSdk.Instance.Login(LoginType.GUEST);
    }
    #endregion



    IEnumerator PreloadingScence(string sceneName)
    {
        async = Application.LoadLevelAsync(sceneName);
        async.allowSceneActivation = false;
        yield return null;
    }

    public void NeedToChangeScence()
    {
        async.allowSceneActivation = true;
    }

    private void ChannelSplashsSetting()
    {
        channelSplashsDic = new Dictionary<string, Texture2D[]>();
        for (int i = 0; i < channelSplashs.Length; i++)
        {
            if (!channelSplashsDic.ContainsKey(channelSplashs[i].channelName))
            {
                channelSplashsDic.Add(channelSplashs[i].channelName, channelSplashs[i].splash);
            }
        }
        if (channelSplashsDic.Count <= 0)
        {
            return;
        }
        SplashScreenManager ssm = GetComponent<SplashScreenManager>();
        foreach (var item in channelSplashsDic)
        {

            currentSplashes = item.Value;
            int index = currentSplashes.Length + ssm.splashes.Length;
            Texture2D[] temp = new Texture2D[index];
            for (int i = 0; i < currentSplashes.Length; i++)
            {
                temp[i] = currentSplashes[i];
            }
            for (int i = currentSplashes.Length; i < index; i++)
            {
                temp[i] = ssm.splashes[i - currentSplashes.Length];
            }
            ssm.splashes = temp;
            break;

        }
    }
}
