
## Netty定义
    官网上说：Netty is an asynchronous myEvent-driven network application framework 
            for rapid development of maintainable high performance protocol servers & clients.

## Netty线程模型
    线程模型分为：线程池，Reactor，Proactor。
    
    Netty采用了Reactor线程模型，响应式编程：事件、异步、流（Spring 5.0也是采用了这种模型--见官网）。
        Reactor直译为反应器、反应堆，这个翻译很容易误导人，其实将Reactor意译为分发器更为恰当。
        Reactor模式本质上是一个事件机制，通过一个或一组线程检查事件，发现事件之后交由
        另一组事件处理线程执行该事件所对应的事件处理器（回调方法），从而实现高响应的程序。
   
    1、单线程模式(1:1)：只有一个线程，既处理连接也处理IO
    2、主从Reactor模式(1:n)：将“监听连接”和“处理IO”分开来，分为监听线程和IO处理线程，也即BossGroup和WorkerGroup
    3、多Reactor线程模式(n:n)：
    
## 拆包与粘包（操作系统为了提升TCP协议的有效载荷，会对TCP的数据包进行粘包和拆包）
    1、固定长度的拆包器 FixedLengthFrameDecoder
    2、行拆包器 LineBasedFrameDecoder
    3、分隔符拆包器 DelimiterBasedFrameDecoder
    4、基于长度域拆包器 LengthFieldBasedFrameDecoder
        
    自定义协议常用第四种拆包器，类比Java的字节码文件格式和Redis的压缩表，可以做到数据结构紧凑，节省网络带宽。
    针对WebSocket和HTTP协议，Netty提供的ChannelHandler已经处理了拆包粘包问题。