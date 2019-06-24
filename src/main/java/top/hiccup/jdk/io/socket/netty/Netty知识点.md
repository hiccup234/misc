
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
    
## 拆包与粘包（操作系统为了提升TCP协议的有效载荷，会对TCP的数据包进行粘包和拆包）
    1、固定长度的拆包器 FixedLengthFrameDecoder
    2、行拆包器 LineBasedFrameDecoder
    3、分隔符拆包器 DelimiterBasedFrameDecoder
    4、基于长度域拆包器 LengthFieldBasedFrameDecoder
        自定义协议常用第四种拆包器，类比Java的字节码文件格式和Redis的压缩表，可以做到数据结构紧凑，节省网络带宽。
        WebSocket和HTTP协议，Netty已经处理的拆包粘包问题。