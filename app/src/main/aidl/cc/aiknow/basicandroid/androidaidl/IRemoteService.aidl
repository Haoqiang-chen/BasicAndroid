// IRemoteService.aidl
package cc.aiknow.basicandroid.androidaidl;

// Declare any non-default types here with import statements
// 自定义的AIDL接口：ANdroid Studio 支持创建。aidl的快捷方式
// AIDL用于跨进程通信，相当于是服务端、客户端之间定义一个双方都同意的协议接口，双方通过此接口进行通信
// 服务端创建的AIDL文件，与客户端的AIDL文件在包名上应该保持一致，可以直接吧服务端的AIDL文件直接连包整体复制过去
// ADIL在客户端访问服务端时，服务端的服务应该已经启动才可以
// GP 8.0 及更高版本中 如果您的项目需要使用 AIDL，必须在 build.gradle 中显式设置 buildFeatures { aidl = true }

interface IRemoteService {
    String getTestString(String test);
}