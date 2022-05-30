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
* [py线程池 & Future](#py线程池--future)
* [py协程](#py协程)
  * [create_task()](#create_task)
  * [future](#future)
  * [线程相关](#线程相关)
  * [普通函数->协程函数run_in_executor()](#普通函数-协程函数run_in_executor)
* [mysql分库分表](#mysql分库分表)
* [缓存设计&使用](#缓存设计使用)
* [TDD](#tdd)
* [MongoDB](#mongodb)

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

# py线程池 & Future

![](/static/2022-05-25-19-24-06.png)

![](/static/2022-05-25-19-26-31.png)

获取future结果, `future.result()`

![](/static/2022-05-25-19-29-43.png)

# py协程

协程是基于单线程的，协程的根是事件总线(event loop)。

所以，想在多线程环境下跑协程任务，就必须在运行任务的线程中手动安排上事件总线才可以！

典型的场景就是loop.run_in_executor(线程池,任务,任务参数) 【普通函数->协程运行】

在一个线程中的某个函数,可以在任何地方保存当前函数的一些临时变量等信息,然后切换到另外一个函数中执行,注意不是通过调用函数的方式做到的,并且**切换的次数以及什么时候再切换到原来的函数都由开发者自己确定**

* 那么这个过程看起来比线程差不多。其实不然, 线程切换从系统层面远不止保存和恢复 CPU上下文这么简单。 **操作系统为了程序运行的高效性每个线程都有自己缓存Cache等等数据,操作系统还会帮你做这些数据的恢复操作。所以线程的切换非常耗性能**。但是协程的切换只是单纯的操作CPU的上下文,所以一秒钟切换个上百万次系统都抗的住
* 线程切换：切换执行不同的任务，由**操作系统调度**（不仅仅是操作CPU上下文）
* 协程切换：切换执行不同的函数，由**开发者调度**（仅仅是操作CPU上下文）

协程是同步和异步调用的混合体

> **针对I/O密集型的程序**，协程的执行效率更高，因为它是程序自身所控制的，这样将节省线程创建和切换所带来的开销。

![](/static/2022-05-25-14-57-22.png)

---

https://blog.51cto.com/u_15127650/4150231

![](/static/2022-05-25-15-14-33.png)
![](/static/2022-05-25-15-19-36.png)
![](/static/2022-05-25-15-20-06.png)

---

https://www.cnblogs.com/wztshine/p/14460847.html

![](/static/2022-05-25-19-32-36.png)
![](/static/2022-05-25-19-42-45.png)
![](/static/2022-05-25-19-48-11.png)

2. `await` 会从 cocroutine 获取数据，它调用了这个协程（执行它）
3. `ensure_future/create_task` 这两个函数会规划 cocroutine 对象，让它们在下次事件循环（event_loop) 的迭代中去执行（尽管不会去等待它们执行完毕，就像守护进程一样）

协程就是迭代器，用来规划协程对象（原本下一次迭代应该执行的任务，，但是如果线程内有其他阻塞情况会直接切换？

* 当一个**协程(函数**)通过 **`asyncio.create_task()`** 等函数被封装为一个 Task，**该协程会被自动调度执行(事件循环会根据情况，自动在各个Task之间调度，决定运行谁**)。Task 是 future 的一个子类。asyncio.Task 从 Future 继承了其除 Future.set_result() 和 Future.set_exception() 以外的所有 API

```
async def test()：  # 声明一个协程函数 test，调用此函数如：test(), 返回的是一个 cocroutine 对象，函数并不会执行。

asyncio.get_running_loop()  # 获取当前OS线程中正在运行的 loop，如果没有，就 RuntimeError
asyncio.get_event_loop()    # 获取当前OS线程中的事件循环，如果没有，就创建一个 loop

await <cocroutine_obj>  # 执行一个协程对象。

asyncio.create_task(obj)  # 会调用 get_running_loop 获取当前运行的loop，然后调用 loop.create_task()，将一个 task 添加进 loop中，并返回一个task对象。

asyncio.ensure_future(obj) # 如果 obj 是一个 cocroutine 对象或其他可等待对象，会直接调用 create_task() 方法。如果是 future，则直接返回此 future。

asyncio.gather(coroutines/futures) # 将可迭代的协程或者future，作为一个整体运行，并且返回一个聚合的结果列表。

asyncio.wait([task1,task2]) # 等待futures或coroutines完成，返回一个 coroutine

loop.run_until_complete(obj)  # 运行一个 Future，并返回它的结果
```

```python
import asyncio

async def test(): # async开头，来定义协程函数
    print('1')
    await asyncio.sleep(2) # 模拟IO操作，await可等待的有：协程对象，Future，Task
    print('3')

t = test() # 不会执行test()，t只是一个协程对象

asyncio.run(t) # 函数运行入口，py3.7以后的功能，可以算作等同于下面三句话

# loop = asyncio.get_event_loop()  # 创建事件循环
# loop.run_until_complete(t)  # 不要被函数名迷惑了，他就是将一个可等待对象放入到事件循环中去执行直到某个future完成了（所以可能还有任务没有执行完毕，就返回结果了）
# loop.close()  # 关闭循环，不关闭也没问题，只是关闭了以后，就不能再次执行 loop.run_until_complete(t) 了。


# 补充：除了 loop.close(),还可以一直运行此loop：
# loop.run_forever()
# loop.stop() 可以停止上面的一直运行

async def nested():
    return 42

async def main():
    print(await nested())  # will print "42".

asyncio.run(main())
```

---

https://www.jianshu.com/p/db2e5d222bb9

![](/static/2022-05-24-23-14-51.png)
![](/static/2022-05-24-23-16-13.png)
![](/static/2022-05-24-23-17-25.png)

## create_task()

* 当一个**协程(函数**)通过 **`asyncio.create_task()`** 等函数被封装为一个 Task，**该协程会被自动调度执行(事件循环会根据情况，自动在各个Task之间调度，决定运行谁**)。Task 是 future 的一个子类。asyncio.Task 从 Future 继承了其除 Future.set_result() 和 Future.set_exception() 以外的所有 API

```python
import asyncio


async def test(): # async开头，来定义协程函数
    print('1')
    await asyncio.sleep(2) # 模拟IO操作，await可等待的有：cocroutine，Future，Task
    print('3')
    return 'Finished.'

async def test2():
	# 注意：任务一旦被创建，就会添加到事件循环里面，会在下次迭代时去执行(它会找到当前正在运行的事件循环，将任务放到里面)
    task1 = asyncio.create_task(test())
    task2 = asyncio.create_task(test())
	# 至此，事件循环里有：test2(),test(),test()
    
    ret1 = await task1  # 上面我们说了 asyncio.run() 等同于那三句话，也就说是事件循环会调用 close()。如果此处不等待，它可能执行到一半（只打印出来1），就自动关闭了。
    ret2 = await task2
    print(ret1,ret2)

asyncio.run(test2())  # 这句话做的事情：
#1.创建事件循环 
#2.将 test2() 添加到事件循环 
#3. 执行事件循环里面的所有事件 
#4. 关闭事件循环
# loop = asyncio.get_event_loop()  # 创建事件循环
# loop.run_until_complete(t)  # 不要被函数名迷惑了，他就是将一个可等待对象放入到事件循环中去执行直到某个future完成了（所以可能还有任务没有执行完毕，就返回结果了）
# loop.close()  # 关闭循环，不关闭也没问题，只是关闭了以后，就不能再次执行 loop.run_until_complete(t) 了。
```

## future

Future 是一种特殊的 低层级 可等待对象，**表示一个异步操作的 最终结果（用人话说，就是一个可以被等待的包含IO事件的对象，当IO完成后，它应当返回一个结果**）

当一个 Future 对象 被等待，这意味着**协程将保持等待直到该 Future 对象**在其他地方操作完毕（Future**产生结果**）。

在 asyncio 中需要 Future 对象以便允许通过 async/await 使用基于回调的代码。

通常情况下 没有必要 在应用层级的代码中创建 Future 对象。

Future 对象有时会由库和某些 asyncio API 暴露给用户，用作可等待对象:
![](/static/2022-05-25-20-05-38.png)

* task是规划进event loop的future实例

```python
import asyncio

async def set_after(fut, delay, value):
    await asyncio.sleep(delay)

    # 给 future 设置结果（如果不设置结果，future会一直阻塞）
    fut.set_result(value)

def cb(fut):  # future 的 callback 函数的唯一参数是 future
    print("!!!")

async def main():
    loop = asyncio.get_running_loop()  # 2. 这个事件循环，就是 main() 所在的事件循环

    # 创建 Future 对象
    fut = loop.create_future()
    fut.add_done_callback(cb)  # 添加 Future 完成后的回调函数
    loop.create_task(set_after(fut, 3, 'Hello world'))

    # 等待 future 有结果后，打印这个结果
    print(await fut)

asyncio.run(main())  # 1. 将 main() 添加进事件循环

######################

import asyncio


async def test(number): # async开头，来定义协程函数
    print(number)
    await asyncio.sleep(2) # 模拟IO操作


async def main():
    # 下面的代码，不加 await 也会把它放到事件循环中执行，但是可能不等它执行完毕，事件循环就关闭了。所以加了 await。
    await asyncio.gather(test(1),test(2),test(3))  # gather 返回的是future，它会将传递的所有参数当作一个整体运行，返回的结果也是一个整体的列表，里面放了所有参数的运行结果。

asyncio.run(main())
```

## 线程相关

**一个线程只能拥有一个事件循环。每个事件循环，每次只能执行一个Task**，每个Task也只能 await 一个对象，事件循环在执行这个Task时如果遇到 await，就会自动调度执行其他 Task。

## 普通函数->协程函数run_in_executor()

event_loop.run_in_executor()
这个函数，它可以让你在一个自定义线程/进程中去执行某个函数，当然，也可以使用默认的线程。

**我们知道普通的函数只能串行执行，无法 await ，也无法在事件循环中自动调度，使用 run_in_executor() 可以将普通的函数放到线程或进程池中执行，并返回一个 Future ，通过这种方式，我们可以实现普通函数和协程函数的执行和切换**。

返回的是一个 asyncio.Future 对象

使用loop.run_in_executor的真正应用场景并不是要把一个协程任务放到线程中去执行，这么做并不会让程序效率有什么明显提升。run_in_executor的真正奥义是解决你的这两个需求：

1. 希望把一个同步阻塞方法异步执行
2. 虽然异步执行，但我要拿到这个方法的返回值
3. <font color="deeppink">异步执行同步阻塞方法用线程就可以，没有必要用Future。但是线程的run方法是没有返回值的，这样说明线程设计的初衷是为了主线程更快的完成业务逻辑，把繁重的具体操作挪到其它线程执行，而且这种执行的结果对主线程的业务逻辑没有什么直接影响。直白的说，利用线程执行的方法最好是不需要返回值的</font>。以前如果需要异步执行方法的结果，最常用的可能就是回调了，现在有了Future，对既要异步又要返回值的处理就多了一种选择。Python这里的Future设计思想和Java的Future是非常相似的

```python
import asyncio
import concurrent.futures

#普通函数
def blocking_io():
    # File operations (such as logging) can block the
    # event loop: run them in a thread pool.
    with open('/dev/urandom', 'rb') as f:
        return f.read(100)
        
#普通函数
def cpu_bound():
    # CPU-bound operations will block the event loop:
    # in general it is preferable to run them in a
    # process pool.
    return sum(i * i for i in range(10 ** 7))

async def main():
    loop = asyncio.get_running_loop()

    ## 三种方式:

    # 1. 在默认事件循环的线程中执行:
    result = await loop.run_in_executor(
        None, blocking_io)
    print('default thread pool', result)

    # 2. 在自定义的线程中运行:
    with concurrent.futures.ThreadPoolExecutor() as pool:  # 自定义一个 concurrent 的线程池
        result = await loop.run_in_executor(pool, blocking_io)
        print('custom thread pool', result)

    # 3. 在自定义的进程中执行:
    with concurrent.futures.ProcessPoolExecutor() as pool:  # 自定义一个 concurrent 的进程池
        result = await loop.run_in_executor(pool, cpu_bound)
        print('custom process pool', result)

asyncio.run(main())
```

# mysql分库分表

https://juejin.cn/post/6844903648670007310

# 缓存设计&使用

https://juejin.cn/post/6844903665845665805

# TDD

https://www.functionize.com/blog/testers-vs-tdd
https://joshpeterson.github.io/you-are-probably-already-doing-tdd

# MongoDB

[电商应用场景1](https://mongoing.com/archives/3734)

字段不规则时使用，其他优先rdb（多一个数据源多一个维护要求，文档不成熟，但是特定要求比如集成es有优点