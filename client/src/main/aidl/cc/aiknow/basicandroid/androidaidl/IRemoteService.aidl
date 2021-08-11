// IRemoteService.aidl
package cc.aiknow.basicandroid.androidaidl;

// Declare any non-default types here with import statements
// 自定义的AIDL接口：ANdroid Studio 支持创建。aidl的快捷方式
// AIDL用于跨进程通信，相当于是服务端、客户端之间定义一个双方都同意的协议接口，双方通过此接口进行通信

interface IRemoteService {
    String getTestString(String test);
}