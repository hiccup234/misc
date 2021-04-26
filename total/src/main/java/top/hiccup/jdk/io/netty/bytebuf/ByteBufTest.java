package top.hiccup.jdk.io.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * Netty实现IO的数据传输载体ByteBuf类，可类比jdk nio的ByteBuffer：
 *
 *      Netty 使用了堆外内存（由具体的ByteBuf实现决定），需要我们手都分配和回收，通过引用计数的方式来管理内存的（实现ReferenceCounted接口）
 *      默认情况下，当创建完一个ByteBuf对象，它的引用计数为1，然后每次调用retain()方法 -- 保持，它的引用就加一，
 *      release()方法是将引用计数减一，减完之后如果发现引用计数为0，则直接回收ByteBuf底层的内存。
 * 
 *      注意：read/write 与 get/set 的唯一的区别就是：get/set 不会改变读写指针，而 read/write 会改变读写指针，
 *      多个 ByteBuf 可以引用同一段内存，通过引用计数来控制内存的释放，遵循谁 retain() 谁 release() 的原则。
 *
 *
 * 切片：slice()  复制，副本：duplicate()  拷贝：copy()  三个方法的比较：
 *
 *      1、这三个方法的返回值都是一个新的ByteBuf对象
 *      2、slice() 方法从原始 ByteBuf 中截取一段，这段数据是从 readerIndex 到 writeIndex，
 *      同时返回的新的 ByteBuf 的最大容量 maxCapacity 为原始 ByteBuf 的 readableBytes()
 *      duplicate() 方法把整个原始 ByteBuf 都截取出来，包括所有的数据，指针信息
 *      3、slice() 方法与 duplicate() 方法的相同点是：底层内存以及引用计数与原始的 ByteBuf 共享，
 *      也就是说经过 slice() 或者 duplicate() 返回的 ByteBuf 调用 write 系列方法都会影响到 原始的 ByteBuf，
 *      但是它们都维持着与原始 ByteBuf 相同的内存引用计数和不同的读写指针
 *      4、slice() 方法与 duplicate() 不同点就是：slice() 只截取从 readerIndex 到 writerIndex 之间的数据，
 *      它返回的 ByteBuf 的最大容量被限制到 原始 ByteBuf 的 readableBytes(), 而 duplicate() 是把整个 ByteBuf 都与原始的 ByteBuf 共享
 *      5、slice() 方法与 duplicate() 方法不会拷贝数据，它们只是通过改变读写指针来改变读写的行为，
 *      而最后一个方法 copy() 会直接从原始的 ByteBuf 中拷贝所有的信息，包括读写指针以及底层对应的数据，
 *      因此往 copy() 返回的 ByteBuf 中写数据不会影响到原始的 ByteBuf
 *      6、slice() 和 duplicate() 不会改变 ByteBuf 的引用计数，所以原始的 ByteBuf 调用 release() 之后发现引用计数为零，就开始释放内存，
 *      调用这两个方法返回的 ByteBuf 也会被释放，这个时候如果再对它们进行读写，就会报错。因此，我们可以通过调用一次 retain() 方法 来增加引用，
 *      表示它们对应的底层的内存多了一次引用，引用计数为2，在释放内存的时候，需要调用两次 release() 方法，将引用计数降到零，才会释放内存
 *      一般使用retainedSlice() 与 retainedDuplicate()，在拷贝的同时增加引用计数，等价于：slice().retain() 和 duplicate().retain()
 *      7、这三个方法均维护着自己的读写指针，与原始的 ByteBuf 的读写指针无关，相互之间不受影响
 * 
 *
 *      注：扩容时从64Byte开始，指数扩容，直到能装下为止
 *
 * @author wenhy
 * @date 2019/1/24
 */
public class ByteBufTest {

    public static void main(String[] args) {

        // 分配堆内内存：不需要手动释放，GC会自动回收
//        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.heapBuffer(10, 12);

        // 初始化的单位为：字节byte
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(10, 12);
        printBuf(byteBuf, "init");

        byteBuf.writeBytes(new byte[]{1, 2, 3, 4});
        printBuf(byteBuf, "writeBytes");

        // get 方法不改变读指针，set同理
        // index从0开始
        System.out.println(byteBuf.getByte(0));
        printBuf(byteBuf, "getByte");


        byteBuf.writeBytes(new byte[]{1, 2, 3, 4});
        printBuf(byteBuf, "writeBytes");
        // 写入4个字节
        byteBuf.writeInt(234);
        printBuf(byteBuf, "writeInt");
        byteBuf.writeByte(234);

    }

    private static void printBuf(ByteBuf buffer, String methodName) {
        System.out.println("===========" + methodName + "============");
        System.out.println("capacity(): " + buffer.capacity());
        System.out.println("maxCapacity(): " + buffer.maxCapacity());

        System.out.println("isReadable(): " + buffer.isReadable());
        System.out.println("readerIndex(): " + buffer.readerIndex());
        System.out.println("readableBytes(): " + buffer.readableBytes());

        System.out.println("isWritable(): " + buffer.isWritable());
        System.out.println("writerIndex(): " + buffer.writerIndex());
        System.out.println("writableBytes(): " + buffer.writableBytes());
        System.out.println("maxWritableBytes(): " + buffer.maxWritableBytes());
        System.out.println();
    }
}
