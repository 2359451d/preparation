# Content

* [Content](#content)
* [场景&流式查询](#场景流式查询)
* [游标（批量) vs 流式（时间换空间避免OOM](#游标批量-vs-流式时间换空间避免oom)
* [easyexcel](#easyexcel)
* [easypoi](#easypoi)

# 场景&流式查询

[参考](https://blog.csdn.net/xhaimail/article/details/119386460?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-0-119386460-blog-111701149.pc_relevant_antiscanv4&spm=1001.2101.3001.4242.1&utm_relevant_index=3)

在实际工作中当指定查询数据过大时，我们一般使用分页查询的方式一页一页的将数据放到内存处理。但有些情况不需要分页的方式查询数据或分很大一页查询数据时，如果一下子将数据全部加载出来到内存中，很可能会发生OOM(内存溢出)；而且查询会很慢，因为**框架耗费大量的时间和内存**去把数据库查询的结果封装成我们想要的对象（实体类）。

举例：在业务系统需要从 MySQL 数据库里读取 100w 数据行进行处理，应该怎么做？

做法通常如下：

常规查询：一次性读取 100w 数据到 JVM 内存中，或者分页读取
流式查询：建立长连接，利用服务端游标，每次读取一条加载到 JVM 内存（多次获取，一次一行）
游标查询：和流式一样，通过 fetchSize 参数，控制一次读取多少条数据（多次获取，一次多行）

---

[如何通过 MyBatis 查询千万数据并保证内存不溢出](https://blog.csdn.net/qq_37781649/article/details/112169908?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-4-112169908-blog-107201427.pc_relevant_default&spm=1001.2101.3001.4242.3&utm_relevant_index=7)

# 游标（批量) vs 流式（时间换空间避免OOM

# easyexcel

> 大数据导出是当我们的导出数量在几万，到上百万的数据时，一次从数据库查询这么多数据加载到内存，然后写入会对我们的内存和CPU都产生压力，这个时候需要我们像分页一样处理导出，分段写入Excel缓解压力

![](/static/2022-05-27-23-24-04.png)
![](/static/2022-05-27-23-35-16.png)

# easypoi

注解式开发

http://easypoi.mydoc.io/#text_202983