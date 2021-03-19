# AndroidBF(Android Basic Framework)-----Android开发基础框架

[![](https://jitpack.io/v/MarkSDGD/QQAtInput.svg)](https://jitpack.io/#MarkSDGD/QQAtInput)

## 前言
AndroidBF 是一个基于AndroidX,能够简化android开发，封装集成了部分主流开源框架，采用MVP架构方式的基础项目。
项目主要涉及到以下方面：
1. `retrofit2 + okhttp3 + rxjava2 好基友组合网络框架`
2. `butterknife view注入框架`
3. `BaseRecyclerViewAdapterHelper 万能适配器`
4. `SmartRefreshLayout 智能下拉上拉刷新框架 + loadinglayout 加载状态布局`
5. `immersionbar 沉浸式状态栏和导航栏`
6. `FlycoTabLayout 库`
7. `flexbox 高级的排版库`
8. `SlideCloseLib 侧滑返回`
9. `klog android Log 日志框架`
10.`banner2 + viewpager2 轮播图框架`
11.`glide 图片加载框架`
10.`google material design 库`

如果觉得对你有帮助的话，请帮忙 **star** 一下！

## 项目截图

1.项目总体截图

![](https://raw.githubusercontent.com/MarkSDGD/repositoryResources/main/AndroidBF/111.jpg)


## demo apk下载

[[点击下载体验](https://raw.githubusercontent.com/MarkSDGD/repositoryResources/main/QQAtInput/QQAtInput.apk)]

扫码下载体验：

![](https://raw.githubusercontent.com/MarkSDGD/repositoryResources/main/QQAtInput/download_qrcode.png)


## 演示截图

### 输入文本以及获取对应成员id
![](https://raw.githubusercontent.com/MarkSDGD/repositoryResources/main/QQAtInput/inputAndMemberId.png)

### 成员选择
![](https://raw.githubusercontent.com/MarkSDGD/repositoryResources/main/QQAtInput/memberSelect.png)


## 录屏gif

### 操作视频录屏
![](https://raw.githubusercontent.com/MarkSDGD/repositoryResources/main/QQAtInput/QQAtInputVideo.gif)


## 导入方式
### 项目根目录下build.gradle文件添加JitPack
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

###  app目录下build.gradle文件添加依赖项
```
dependencies {
    implementation 'com.github.MarkSDGD:QQAtInput:1.1.0'
}
```

###  使用方式
```
<com.mark.atlibrary.AtEditText
        android:layout_marginTop="30dp"
        android:id="@+id/chat_edit"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@drawable/shape_corner_edittext_bg"
        android:maxLines="6"
        android:focusable="true"
        android:focusableInTouchMode="true"
        android:text=""
        android:hint="请输入消息"
        android:textSize="16dp" />
```

###  api说明
####  @模式设置获取
```

     public void setOnlySupportLastAt(boolean onlySupportLastAt)

     public boolean isOnlySupportLastAt()

```
####  添加一个@块
```

     public void addSpan(String showText, int spanBgResId, int textColor, String userId,int maxEms)

```

####  获取输入文本中所有成员的id
```

     public String getUserIdString()

```

####  存草稿恢复草稿相关方法
```

     public String spannableString2JsonString(SpannableString ss)

     public SpannableString jsonString2SpannableString(String strjson)

```



## 实现原理

1. 首先将 **@李白** 字符串生成图片，在文本中插入图片span，每个图片span存储用户文本，id等信息；根据@模式，计算插入图片span后的光标位置；

2. 由于复杂文本无法直接存储，首先将复杂文本转换成整个普通文本+所有span块关键信息（普通文本中的起始位置，span块文字，id, 背景资源，文字颜色），然后将这些信息转换成json对象，再把json对象转成字符串即可。

3. 恢复草稿的时候，进行相反的操作，根据存储的整个文本，加上所有span块的关键信息，重新构建图片span块插入到文本中即可

## 声明

此组件属于个人开发作品，目前已满足大部分使用场景，如有个别场景，请下载源码自行更改。

## 支持与鼓励
![](https://raw.githubusercontent.com/MarkSDGD/repositoryResources/main/QQAtInput/donate.png)
