理一些非八股核心（generalised方向），BQ概念可以/要扯的点，基本全忘了

* [web server protocol/standard](#web-server-protocolstandard)
* [Java开发三层架构 – 业务分层](#java开发三层架构--业务分层)
* [DAO，DTO，DO等](#daodtodo等)
* [中间件](#中间件)
* [nginx](#nginx)
* [Cookie, session, token, redis](#cookie-session-token-redis)
* [JWT](#jwt)
* [Token存储](#token存储)
* [针对用户信息+缓存](#针对用户信息缓存)
* [redis+ThreadLocal用户信息+SSO](#redisthreadlocal用户信息sso)
* [分布式锁](#分布式锁)
  * [Redis实现](#redis实现)
    * [部署方式](#部署方式)
  * [Redisson](#redisson)
  * [zookeeper实现](#zookeeper实现)
* [java future](#java-future)
* [py协程](#py协程)

# web server protocol/standard

服务器必须将可迭代对象的内容传递给客户端，可迭代对象会产生bytestrings，必须完全完成每个bytestring后才能请求下一个。

服务器程序会在每次客户端的请求传来时，调用我们写好的应用程序，并将处理好的结果返回给客户端。

* 无论多么复杂的Web应用程序，入口都是一个WSGI处理函数。
* Web应用程序就是写一个WSGI的处理函数，主要功能在于交互式地浏览和修改数据，生成动态Web内容，针对每个HTTP请求进行响应。

<!-- 要使 Python 写的程序能在 Web 上被访问，还需要搭建一个支持 Python 的 HTTP 服务器（也就是实现了WSGI server（WSGI协议）的Http服务器）。有如下几种方式： -->

<!-- 可以自己使用Python Socket编程实现一个Http服务器 -->
<!-- 使用支持Python的开源的Http服务器（如：uWSGI，wsgiref，Mod_WSGI等等）。如果是使用Nginx，Apache，Lighttpd等Http服务器需要单独安装支持WSGI server的模块插件。 -->
<!-- 使用Python开源Web框架（如：Flask，Django等等）内置的Http服务器（Django自带的WSGI Server，一般测试使用） -->

# Java开发三层架构 – 业务分层

* 持久层（DAO）：数据访问层用于访问数据库，实现对数据库中数据的读取保存操作。
* 服务层（SERVICE）：处理用户输入信息，建立新的数据存储方式，在存储过程中对数据进行读取，包含“商业逻辑”的描述代码。
* 视图层（VIEW+ACTION）：主要功能是显示数据和传输用户数据，可以为网站系统的运行提供交互式的操作界面。Web页面就是视图层的一种应用方式。

---

DAO层：（持久层）主要与数据库进行交互

* DAO层叫数据访问层，全称为data access object，属于一种比较底层，比较基础的操作，主要是做数据持久层的工作，**主要与数据库进行交互。具体到对于某个表的增删改查，也就是说某个DAO一定是和数据库的某一张表一一对应的，其中封装了增删改查基本操作，建议DAO只做原子操作，增删改查**。
* 注：DAO 层的数据源和数据库连接的参数都是在配置文件中进行配置的。

Service层：（业务层 ）控制业务

* Service层叫服务层，被称为服务，主要负责**业务模块的逻辑**应用设计。粗略的理解就是对**一个或多个DAO进行的再次封装，封装成一个服务，所以这里也就不会是一个原子操作了，需要事物控制**。和DAO层一样都是先设计放接口的类，再创建实现的类，然后在配置文件中进行配置其实现的关联。接下来就可以在service层调用接口进行业务逻辑应用的处理。
  * <font color="deeppink">封装多个单一DAO接口操作</font>
* 注：**封装Service层的业务逻辑有利于业务逻辑的独立性和重复利用性**。

Controler层：（控制层 ）控制业务逻辑

* Controller层负责具体的业务模块流程的控制，其实就是与前台互交，把前台传进来的参数进行处理，controller层主要调用Service层里面的接口控制具体的业务流程，控制的配置也需要在配置文件中进行。
* 注：Controler负责请求转发，接受页面过来的参数，传给Service处理，接到返回值，再传给页面。

# DAO，DTO，DO等

DAO (DataAccessobjects 数据存取对象)是指位于业务逻辑和持久化数据之间实现对持久化数据的访问。通俗来讲，就是将**数据库操作都封装起来**。

* **DAO （Data Access Object）数据访问对象**
* **DTO（Data Transfer Object）数据传输对象**
  * DTO（ Data Transfer Object）：数据传输对象，**Service层向外传输的对象**。
* DO （Domain Object）领域对象
  * DO（ Data Object）：与数据库表的结构一一对应，通过DAO层向上传输数据源对象。
* **VO（View Object）视图模型**
  * VO（ View Object）：显示层对象，通常是Web向模板渲染引擎层传输的对象。
  * 最后一层数据处理= =？
* AO（Application Object）应用对象
  * AO（ Application Object）：应用对象。 在Web层与Service层之间抽象的复用对象模型。
* BO（ Business Object）业务对象
  * BO（ Business Object）：业务对象。 封装业务逻辑的对象。
* **POJO（ Plain Ordinary Java Object）纯普通Java对象**
  * POJO（ Plain Ordinary Java Object）：POJO专指只有setter/getter的简单类，包括DO/DTO/BO/VO等。
  * bean
* PO（Persistent Object）持久化对象
* Entity（应用程序域中的一个概念）实体
* Model （概念实体模型）实体类和模型
* View （概念视图模型）视图模型

# 中间件

中间件除了路由之外，还可以做很多事情，最常见的还有：

* • 负载均衡，转发用户请求
* • 预处理 XSL 等相关数据
* • 限制请求速率，设置白名单

# nginx

nginx可以缓冲请求和响应。如果让Gunicorn直接提供服务，浏览器发起一个请求，鉴于浏览器和网络情况都是未知的，http请求的发起过程可能比较慢，而Gunicorn只能等待请求发起完成后，才去真正处理请求，处理完成后，等客户端完全接收请求后，才继续下一个。nginx缓存客户端发起的请求，直到收完整个请求，转发给Gunicorn，等Gunicorn处理完成后，拿到响应，再发给客户端，这个流程是nginx擅长处理，而Gunicorn不擅长处理的。

因此将Gunicorn置于nginx后面，可以有效提高Gunicorn的处理能力。

# Cookie, session, token, redis

HTTP无状态

* 浏览器访问服务器时，浏览器和服务器之间就建立了连接，连接后浏览器可以向服务器发送多次请求，并且这多次请求之间是没有任何关系的，从服务器的角度而言，即便同一个浏览器像我发送了多次请求，我也不认为这些请求之间有什么关系，仍然把每次请求当做陌生人俩对待。他不对之前的请求和响应状态进行管理，无法根据之前的状态对本次请求进行处理。

---

cookie

> 将敏感的用户信息存放在cookie中是很不安全的，造成cookie截获和cookie欺骗，而且cookie可以存放的内部才能很小，不能存放太多数据。一般对于比较敏感的数据不会选择存放在cookie中，而是存放在session中。

![](/static/2022-05-23-16-35-19.png)

---

session + cookie(session id)

* session的使用方式：客户端的cookie存放session_id，服务端的session保存用户信息，浏览器再次访问服务器时根据session_id到session中查找用户信息。

![](/static/2022-05-23-16-39-22.png)

弊端

（1）服务器压力增大

通常session是存储在内存中的，每个用户通过认证之后都会将session数据保存在服务器的内存中，而当用户量增大时，服务器的压力增大。

（2）CSRF跨站伪造请求攻击

session是基于cookie进行用户识别的, cookie如果被截获，用户就会很容易受到跨站请求伪造的攻击。

（3）**无法实现session共享**。

* 如果将来搭建了多个服务器，**虽然每个服务器都执行的是同样的业务逻辑，但是session数据是保存在内存中的（不是共享的），用户第一次访问的是服务器1，当用户再次请求时可能访问的是另外一台服务器2，服务器2获取不到session信息，就判定用户没有登陆过**。
  * 负载均衡集群时要注意

---

token

* session数据默认存放在服务器的内存中，因此当用户量变多时，服务器压力就会增大。如果将session数据存放在数据库中或者redis缓存中，就要建立session_id相关的数据库表，把session数据通过session_id存放在数据库中比较复杂，不如直接使用token代替session_id，将session数据存放在数据库中。
* 解决session依赖于单个服务器不能实现session共享的问题
* 我们可以在用户第一次请求是生成一个全局唯一的token返回给浏览器，同时将用户信息保存在redis中并设置过期时间（session的过期时间为30分钟），之后浏览器的每次请求都带着这个token，服务器根据每次请求到redis中查找对应的用户信息

![](/static/2022-05-23-16-54-11.png)

# JWT

组成

![](/static/2022-05-23-16-57-59.png)

jwt认证方式：注册登录->服务端将生成一个token，并将token与user加密生成一个密文->将token+user+密文数据 返回给浏览器->再次访问时传递token+user+密文数据，后台会再次使用token+user生成新密文，与传递过来的密文比较，一致则正确。

![](/static/2022-05-23-17-01-01.png)

* 解决session无法共享

# Token存储

sql

![](/static/2022-05-23-17-07-45.png)
![](/static/2022-05-23-17-10-07.png)

nosql

* 使用redis存储登录凭证，因此处理每次请求时，都需要查询用户的登录凭证，访问的频率较高，可以通过redis提高性能。因此将上面存放在数据库中的登录凭证改为存在redis中。

![](/static/2022-05-23-17-14-17.png)
![](/static/2022-05-23-17-15-15.png)

# 针对用户信息+缓存

处理每次请求时都需要通过凭证查询用户信息，因此可以通过将用户信息放在redis缓存中。

使用redis缓存的步骤：

* ①优先从缓存中取值
* ②取不到值时初始化缓存数据
* ③数据变更时清除缓存数据

![](/static/2022-05-23-17-19-15.png)
![](/static/2022-05-23-17-19-30.png)

# redis+ThreadLocal用户信息+SSO

# 分布式锁

![](/static/2022-05-23-18-04-23.png)

* 因为上图中的两个A系统，运行在两个不同的JVM里面，他们加的锁只对属于自己JVM里面的线程有效，对于其他JVM的线程是无效的。
* 因此，这里的问题是：Java提供的原生锁机制在多机部署场景下失效了这是因为两台机器加的锁不是同一个锁(两个锁在不同的JVM里面)。
* 那么，我们只要保证两台机器加的锁是同一个锁，问题不就解决了吗？

分布式全局锁，reids,zookeeper

![](/static/2022-05-23-18-06-32.png)

## Redis实现

![](/static/2022-05-23-18-08-58.png)
![](/static/2022-05-23-18-11-10.png)

### 部署方式

![](/static/2022-05-23-19-08-49.png)

## Redisson

![](/static/2022-05-23-19-10-26.png)
![](/static/2022-05-23-19-10-55.png)

## zookeeper实现

![](/static/2022-05-23-19-14-17.png)
![](/static/2022-05-23-19-15-09.png)

# java future

https://segmentfault.com/a/1190000019558579

# py协程

协程是同步和异步调用的混合体

https://www.jianshu.com/p/db2e5d222bb9

![](/static/2022-05-24-23-14-51.png)
![](/static/2022-05-24-23-16-13.png)
![](/static/2022-05-24-23-17-25.png)