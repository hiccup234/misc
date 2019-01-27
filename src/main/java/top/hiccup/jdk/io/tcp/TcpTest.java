package top.hiccup.jdk.io.tcp;

/**
 * TCP/IP协议
 *
 * 第一次握手：建立连接时，客户端发送syn包(syn=j)到服务器，并进入SYN_SEND状态，等待服务器确认；SYN：同步序列编号(Synchronize Sequence Numbers)。
 *
 * 第二次握手：服务器收到syn包，必须确认客户的SYN（ack=j+1），同时自己也发送一个SYN包（syn=k），即SYN+ACK包，此时服务器进入SYN_RECV状态。
 *
 * 第三次握手：客户端收到服务器的SYN+ACK包，向服务器发送确认包ACK(ack=k+1)，此包发送完毕，客户端和服务器进入ESTABLISHED状态，完成三次握手。
 *
 * 完整的三次握手也就是： 请求---应答---再次确认。
 *
 * =====================================================================================================================
 * 通过网络抓包的查看具体的流程：
 *   1、使用tcpdump -iany tcp port 80 工具来监听80端口
 *   2、再使用telnet 127.0.0.1 80 来开连接
 *
 * 【三次握手】
 *      18:52:24.447860 IP localhost.60390 > localhost.http: Flags [S], seq 3628736133, win 43690, options [mss 65495,sackOK,TS val 180045398 ecr 0,nop,wscale 7], length 0
 *      18:52:24.447870 IP localhost.http > localhost.60390: Flags [S.], seq 3621695642, ack 3628736134, win 43690, options [mss 65495,sackOK,TS val 180045398 ecr 180045398,nop,wscale 7], length 0
 *      18:52:24.447880 IP localhost.60390 > localhost.http: Flags [.], ack 1, win 342, options [nop,nop,TS val 180045398 ecr 180045398], length 0
 *       时间             客户端（端口一般是自动分配的） > 服务端    请求类型     同步序列编号      win 43690（滑动窗口大小）  length（数据包大小）
 *
 * 【通信确认】
 *      18:52:29.628177 IP localhost.60390 > localhost.http: Flags [P.], seq 1:6, ack 1, win 342, options [nop,nop,TS val 180050579 ecr 180045398], length 5: HTTP
 *      18:52:29.628186 IP localhost.http > localhost.60390: Flags [.], ack 6, win 342, options [nop,nop,TS val 180050579 ecr 180050579], length 0
 *      18:52:29.628643 IP localhost.http > localhost.60390: Flags [P.], seq 1:107, ack 6, win 342, options [nop,nop,TS val 180050579 ecr 180050579], length 106: HTTP: HTTP/1.1 400
 *      18:52:29.628649 IP localhost.60390 > localhost.http: Flags [.], ack 107, win 342, options [nop,nop,TS val 180050579 ecr 180050579], length 0
 *
 * 【四次挥手】
 *      18:52:29.628715 IP localhost.http > localhost.60390: Flags [F.], seq 107, ack 6, win 342, options [nop,nop,TS val 180050579 ecr 180050579], length 0
 *      18:52:29.628848 IP localhost.60390 > localhost.http: Flags [F.], seq 6, ack 108, win 342, options [nop,nop,TS val 180050579 ecr 180050579], length 0
 *      18:52:29.628854 IP localhost.http > localhost.60390: Flags [.], ack 7, win 342, options [nop,nop,TS val 180050579 ecr 180050579], length 0
 *
 *
 *      建立一个连接需要三次握手，而终止一个连接要经过四次握手，这是由TCP的半关闭(half-close)造成的。
 *      由于TCP连接是全双工的，因此每个方向都必须单独进行关闭。这个原则是当一方完成它的数据发送任务后就能发送一个FIN来终止这个方向的连接。
 *      收到一个 FIN只意味着这一方向上没有数据流动，一个TCP连接在收到一个FIN后仍能发送数据。首先进行关闭的一方将执行主动关闭，而另一方执行被动关闭。
 *
 *      [S] SYN请求，seq则为同步序列编号
 *      [S.] SYN+ACK，注意这里的SYN是服务器自己的，ACK是对客户端的SYN的seq+1
 *      [.] ACK确认包，注意这里的ack跟seq不完全一样，如果是连接建立后ack是确认对应的seq
 *      [P] 数据推送，可以是从服务器端向客户端推送，也可以从客户端向服务器端推
 *      [F] 表示这是一个FIN包，是关闭连接操作，client/server都有可能发起
 *      [R] 表示这是一个RST包，与F包作用相同，但RST表示连接关闭时，仍然有数据未被处理。可以理解为是强制切断连接
 *
 *      [S] + [S.] + [.] = (client)SYN->(server)SYN+ACK(=SYN+1)->(client)ACT 就是3次握手过程
 *      [F.] + [F.] + [.] 四次挥手
 *
 *      1．为什么建立连接协议是三次握手，而关闭连接却是四次握手呢？
 *
 *      这是因为服务端的LISTEN状态下的SOCKET当收到SYN报文的建连请求后，它可以把ACK和SYN（ACK起应答作用，而SYN起同步作用）放在一个报文里来发送。
 *      但关闭连接时，当收到对方的FIN报文通知时，它仅仅表示对方没有数据发送给你了；但未必你所有的数据都全部发送给对方了，
 *      所以你可以未必会马上会关闭SOCKET,也即你可能还需要发送一些数据给对方之后，
 *      再发送FIN报文给对方来表示你同意现在可以关闭连接了，所以它这里的ACK报文和FIN报文多数情况下都是分开发送的。
 *
 *      2．为什么TIME_WAIT状态还需要等2MSL后才能返回到CLOSED状态？
 *
 *      这是因为虽然双方都同意关闭连接了，而且握手的4个报文也都协调和发送完毕，按理可以直接回到CLOSED状态（就好比从SYN_SEND状态到ESTABLISH状态那样）；
 *      但是因为我们必须要假想网络是不可靠的，你无法保证你最后发送的ACK报文会一定被对方收到，因此对方处于LAST_ACK状态下的SOCKET可能会因为超时未收到ACK报文，
 *      而重发FIN报文，所以这个TIME_WAIT状态的作用就是用来重发可能丢失的ACK报文。
 *
 * =====================================================================================================================
 *
 * @author wenhy
 * @date 2019/1/27
 */
public class TcpTest {
}
