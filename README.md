# Missu
一个带翻译功能的即时通讯安卓应用
_____________________________
<h2>软件界面截图</h2>

![](https://github.com/qolpanjan/Missu/blob/master/raw/img/1.png)
![](https://github.com/qolpanjan/Missu/blob/master/raw/img/2.png)
![](https://github.com/qolpanjan/Missu/blob/master/raw/img/3.png)
![](https://github.com/qolpanjan/Missu/blob/master/raw/img/1-2.png)
![](https://github.com/qolpanjan/Missu/blob/master/raw/img/1-3.png)
![](https://github.com/qolpanjan/Missu/blob/master/raw/img/2-1.png)
![](https://github.com/qolpanjan/Missu/blob/master/raw/img/3-1.png)
![](https://github.com/qolpanjan/Missu/blob/master/raw/img/3-2.png)
![](https://github.com/qolpanjan/Missu/blob/master/raw/img/3-3.png)


<h3>数据库</h3>
<h5>整个项目设计了三个数据库类</h5>
<h6>1.User表：</h6>
    · 所有注册本应用的用户存在本表
    · 并且每次新注册和搜索朋友的时候都会从本表里搜索
    · 本表将会存储在服务器
    · 本地只存储在本机登录过的用户
<h6>2.Friend表：</h6>
    · 本表保存每个用户的通讯录
    · 和User表一对一关系
    · 本表也会同步在服务器
<h6>3.Message表：</h6>
    · 本表保存用户的聊天记录
    · 不同步到服务器，本地删除就消息
    · 和User表一对多关系
    · 和Friend表一对一关系
<hr>

<h3>适配器</h3>
本项目目前使用了三个适配器
<h6>1.MessageList Adapter：</h6>
    * 主要适配Message表，处理用户聊天记录
    * 将会输出两方聊天内容和头像
<h6>2.Message Adapter：</h6>
    * 主要适配正在聊天界面
    * 显示存储在本地的用户的聊天记录
    * 显示聊天朋友头像，最好进行的聊天内容以及事件
    * 用微信红点的形式显示未读消息
<h6>3.Friend Adapter:</h6>
    * 主要适配通讯录
    * 输出通讯录用户的基本资料
<hr>


<h3>用到的框架</h3>
为了节省事件本项目使用在Github网站开源的一下主流的安卓开源项目
<h6>1.GreenDao：</h6>
        * 目前最流行，最方便的ORM开源框架
        * 本项目中主要用于处理数据库操作
        * [框架地址](https://github.com/greenrobot/greenDAO")

<h6>2.Glide：</h6>
        * 是目前为止最方便，最主流的开源图片缓存框架。
        * 本项目中主要用于从服务器加载用户头像
        * [框架地址](https://github.com/bumptech/glide)
<hr>
