
## Netty定义
    官网上说：Netty is an asynchronous event-driven network application framework 
            for rapid development of maintainable high performance protocol servers & clients.

## 线程模型
    线程池，reactor，proactor

## Netty线程模型
    Netty采用了Reactor线程模型（Spring 5.0也是采用了这种模型--见官网）（响应式编程：事件、异步、流）
   
    1、单线程模式(1:1)：只有一个线程，既处理连接也处理IO

    2、主从Reactor模式(1:n)：将“监听连接”和“处理IO”分开来，分为监听线程和IO处理线程，也即BossGroup和WorkerGroup

    3、多Reactor线程模式(n:n)：