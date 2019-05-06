
## 一点说明


打包上传私库命令：
1、gradle.properties修改为自己的工号命令
2、执行clean assemble uploadArchives

## 功能说明

安全关闭流

https://www.cnblogs.com/qqzy168/p/3670915.html

Twitter的雪花算法

循环引用检测功能！！！！
https://wenshao.iteye.com/blog/1142031/
https://blog.csdn.net/swust_chenpeng/article/details/19617561
https://www.csdn.net/article/2014-09-25/2821866
http://blog.nsfocus.net/analysis-protection-fastjson-remote-code-execution-vulnerability/
https://wenshao.iteye.com/blog/1146897



先思考一个内存占用的问题：字符串 “Hello World” 会占用多少字节内存？

答案：在 32 位虚拟机上是 62 字节，在 64 位虚拟机上是 86 字节。

分别为 8/16 （字符串的对象头） + 11 * 2 （字符） + [8/16 （字符数组的对象头） + 4 （数组长度），加上字节对齐所需的填充，共为 16/24 字节] + 4 （偏移） + 4 （偏移长度） + 4 （哈希码） + 4/8 （指向字符数组的引用）【在 64 位虚拟机上，String 对象的内存占用会因为字节对齐而填充为 40 字节】


https://blog.csdn.net/u011240877/article/details/53545041
https://www.ibm.com/developerworks/java/library/j-jtp04298/index.html
https://www.cnblogs.com/xingzc/p/5760166.html
https://www.cnblogs.com/zabulon/p/5826610.html
https://www.cnblogs.com/hunt/p/7067141.html
https://www.jianshu.com/p/6dc9d9adbce0

https://nofluffjuststuff.com/magazine/2016/09/time_to_really_learn_generics_a_java_8_perspective
https://www.cnblogs.com/zabulon/p/5826610.html

https://www.cnblogs.com/cnmenglang/p/6261435.html
http://www.importnew.com/21836.html

https://blog.csdn.net/mafei6827/article/details/87076371
















