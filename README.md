# RadarView
[![Download](https://img.shields.io/badge/download-App-blue.svg)](https://raw.githubusercontent.com/jenly1314/RadarView/master/app/app-release.apk)
[![Jitpack](https://jitpack.io/v/jenly1314/RadarView.svg)](https://jitpack.io/#jenly1314/RadarView)
[![API](https://img.shields.io/badge/API-15%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=15)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/mit-license.php)

RadarView for Android 是一个雷达扫描动画后，然后展示得分效果的控件。

## Gif 展示
![Image](GIF.gif)

> 你也可以直接下载 [演示App](https://raw.githubusercontent.com/jenly1314/RadarView/master/app/app-release.apk) 体验效果

## 引入

### Gradle:

1. 在Project的 **build.gradle** 或 **setting.gradle** 中添加远程仓库

    ```gradle
    repositories {
        //...
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
    ```

2. 在Module的 **build.gradle** 里面添加引入依赖项

    ```gradle
    implementation 'com.github.jenly1314:RadarView:1.0.2'
    ```

## 使用

布局示例
```Xml
    <com.king.view.radarview.RadarView
        android:id="@+id/rv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="100"
        app:labelText="Score" />
```

自定义属性
```Xml
    <declare-styleable name="RadarView">
        <attr name="android:textSize"/>
        <attr name="android:textColor"/>
        <attr name="android:text"/>
        <attr name="labelTextSize" format="dimension"/>
        <attr name="labelTextColor" format="color"/>
        <attr name="labelText" format="string"/>
        <attr name="format" format="string"/>
        <attr name="sideColor" format="color"/>
        <attr name="outsideBackgroundColor" format="color"/>
        <attr name="insideBackgroundColor" format="color"/>
        <attr name="duration" format="integer"/>
        <attr name="textOffsetY" format="dimension"/>
        <attr name="labelTextOffsetY" format="dimension"/>

        <attr name="circleColor" format="color"/>
        <attr name="lineColor" format="color"/>
        <attr name="showLine" format="boolean"/>
        <attr name="rotate" format="integer"/>
        <attr name="showLabel" format="boolean"/>
        <attr name="showText" format="boolean"/>
        <attr name="scanTime" format="integer"/>
        <attr name="insideStrokeWidth" format="dimension"/>
        <attr name="outsideStrokeWidth" format="dimension"/>
        <attr name="lineStrokeWidth" format="dimension"/>
    </declare-styleable>
```

代码示例（核心调用代码）
```Java
//开始雷达扫描
radarView.start();
//停止雷达扫描
radarView.stop();
//设置雷达扫描区域的颜色，传多个色值表示渐变
radarView.setScanColor(int... colors);
/**
 * 显示分数
 * @param from  初始值
 * @param to    目标值（最终分数）
 * @param duration  从初始值到目标值的动画持续时间
 * @param format
 * @param isShowAnim 是否显示动画
 */
radarView.showScore(float from, float to, int duration, final String format,boolean isShowAnim);

```

更多使用详情，请查看[app](app)中的源码使用示例或直接查看[API帮助文档](https://jitpack.io/com/github/jenly1314/RadarView/latest/javadoc/)

## 赞赏
如果您喜欢RadarView，或感觉RadarView帮助到了您，可以点右上角“Star”支持一下，您的支持就是我的动力，谢谢 :smiley:
<p>您也可以扫描下面的二维码，请作者喝杯咖啡 :coffee:

<div>
   <img src="https://jenly1314.github.io/image/page/rewardcode.png">
</div>

## 关于我

| 我的博客                                                                                | GitHub                                                                                  | Gitee                                                                                  | CSDN                                                                                 | 博客园                                                                            |
|:------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------|:---------------------------------------------------------------------------------------|:-------------------------------------------------------------------------------------|:-------------------------------------------------------------------------------|
| <a title="我的博客" href="https://jenly1314.github.io" target="_blank">Jenly's Blog</a> | <a title="GitHub开源项目" href="https://github.com/jenly1314" target="_blank">jenly1314</a> | <a title="Gitee开源项目" href="https://gitee.com/jenly1314" target="_blank">jenly1314</a>  | <a title="CSDN博客" href="http://blog.csdn.net/jenly121" target="_blank">jenly121</a>  | <a title="博客园" href="https://www.cnblogs.com/jenly" target="_blank">jenly</a>  |

## 联系我

| 微信公众号        | Gmail邮箱                                                                          | QQ邮箱                                                                              | QQ群                                                                                                                       | QQ群                                                                                                                       |
|:-------------|:---------------------------------------------------------------------------------|:----------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------|
| [Jenly666](http://weixin.qq.com/r/wzpWTuPEQL4-ract92-R) | <a title="给我发邮件" href="mailto:jenly1314@gmail.com" target="_blank">jenly1314</a> | <a title="给我发邮件" href="mailto:jenly1314@vip.qq.com" target="_blank">jenly1314</a> | <a title="点击加入QQ群" href="https://qm.qq.com/cgi-bin/qm/qr?k=6_RukjAhwjAdDHEk2G7nph-o8fBFFzZz" target="_blank">20867961</a> | <a title="点击加入QQ群" href="https://qm.qq.com/cgi-bin/qm/qr?k=Z9pobM8bzAW7tM_8xC31W8IcbIl0A-zT" target="_blank">64020761</a> |

<div>
   <img src="https://jenly1314.github.io/image/page/footer.png">
</div>
