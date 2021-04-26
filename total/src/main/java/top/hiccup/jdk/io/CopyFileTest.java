package top.hiccup.jdk.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

/**
 * Java有几种文件拷贝方式？哪一种最高效？
 * 1、利用java.io的Stream类库来完成拷贝
 * 2、利用java.io的transferTo方法完成拷贝
 * 3、Java标准库java.nio.file.Files.copy
 *
 * 对于 Copy 的效率，这个其实与操作系统和配置等情况相关，总体上来说，NIO transferTo/From 的方式可能更快，
 * 因为它更能利用现代操作系统底层机制，避免不必要拷贝和上下文切换。
 *
 * @author wenhy
 * @date 2019/3/26
 */
public class CopyFileTest {

    public static void copyFileByStream(File source, File dest) throws IOException {
        // try-resource语法糖，is,os会在finally得到释放
        try (InputStream is = new FileInputStream(source);
             OutputStream os = new FileOutputStream(dest);) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }

    public static void copyFileByChannel(File source, File dest) throws IOException {
        try (FileChannel sourceChannel = new FileInputStream(source).getChannel();
             FileChannel targetChannel = new FileOutputStream(dest).getChannel()) {
            for (long count = sourceChannel.size(); count > 0; ) {
                // transferTo可以实现零拷贝方法，省去内核态和用户态的切换
                long transferred = sourceChannel.transferTo(
                        sourceChannel.position(), count, targetChannel);
                sourceChannel.position(sourceChannel.position() + transferred);
                count -= transferred;
            }
        }
    }

    /**
     * copy方法有几个重载版本
     * public static Path copy(Path source, Path target, CopyOption... options)
     *
     * public static long copy(InputStream in, Path target, CopyOption... options)
     *
     * public static long copy(Path source, OutputStream out)
     */
    public static void copyFileByFiles(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }

}
