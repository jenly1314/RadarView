# RadarView

[![JitPack](https://img.shields.io/jitpack/v/github/jenly1314/RadarView?logo=jitpack)](https://jitpack.io/#jenly1314/RadarView)
[![Download](https://img.shields.io/badge/download-APK-brightgreen?logo=github)](https://raw.githubusercontent.com/jenly1314/RadarView/master/app/app-release.apk)
[![API](https://img.shields.io/badge/API-15%2B-brightgreen?logo=android)](https://developer.android.com/guide/topics/manifest/uses-sdk-element#ApiLevels)
[![License](https://img.shields.io/github/license/jenly1314/RadarView?logo=open-source-initiative)](https://opensource.org/licenses/mit)


RadarView for Android 是一个雷达扫描动画后，然后展示得分效果的控件。

## 效果展示
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

2. 在Module的 **build.gradle** 中添加依赖项

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

## 相关推荐
- [SpinCounterView](https://github.com/jenly1314/SpinCounterView) 一个类似码表变化的旋转计数器动画控件。
- [CounterView](https://github.com/jenly1314/CounterView) 一个数字变化效果的计数器视图控件。
- [SuperTextView](https://github.com/jenly1314/SuperTextView) 一个在TextView的基础上扩展了几种动画效果的控件。
- [LoadingView](https://github.com/jenly1314/LoadingView) 一个圆弧加载过渡动画，圆弧个数，大小，弧度，渐变颜色，完全可配。
- [WaveView](https://github.com/jenly1314/WaveView) 一个水波纹动画控件视图，支持波纹数，波纹振幅，波纹颜色，波纹速度，波纹方向等属性完全可配。
- [GiftSurfaceView](https://github.com/jenly1314/GiftSurfaceView) 一个适用于直播间送礼物拼图案的动画控件。
- [FlutteringLayout](https://github.com/jenly1314/FlutteringLayout) 一个适用于直播间点赞桃心飘动效果的控件。
- [DragPolygonView](https://github.com/jenly1314/DragPolygonView) 一个支持可拖动多边形，支持通过拖拽多边形的角改变其形状的任意多边形控件。
- [CircleProgressView](https://github.com/jenly1314/CircleProgressView) 一个圆形的进度动画控件，动画效果纵享丝滑。
- [ArcSeekBar](https://github.com/jenly1314/ArcSeekBar) 一个弧形的拖动条进度控件，配置参数完全可定制化。
- [DrawBoard](https://github.com/jenly1314/DrawBoard) 一个自定义View实现的画板；方便对图片进行编辑和各种涂鸦相关操作。
- [compose-component](https://github.com/jenly1314/compose-component) 一个Jetpack Compose的组件库；主要提供了一些小组件，便于快速使用。

---

![footer](https://jenly1314.github.io/page/footer.svg)

