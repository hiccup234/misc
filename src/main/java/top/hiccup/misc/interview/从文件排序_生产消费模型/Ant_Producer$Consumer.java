package top.hiccup.misc.interview.从文件排序_生产消费模型;

import java.io.*;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.*;

/**
 * 蚂蚁面试题：假设本地有一个文件夹，文件夹下面有若干文件（文件数大于50小于100），文件的存储格式是文本格式（后缀名是.txt)，文件的大小每个文件不会超过100k。
 * 
 * 文件格式如下：
 * 2000102，100，98.32000103，101，73.32000104，102，98.32000105，100，101.32000106，101，45.3……
 * 
 * 文件格式说明：
 * 1.文件每行都由三列构成，第一列是一个id，第二列是分组groupId, 第三列是指标quota。
 * 2.id的数据类型是String，groupId的数据类型String，quota的数据类型float。
 * 
 * 功能要求：
 * 1.把所有文件里面的内容按照分组进行排序，输出所有文件按照分组升序排序之后，每个分组下面的最小指标值。
 * 比如上面的数据输出结果为：100，2000102，98.3101，2000106，45.3102，2000104，98.3
 * 
 * 非功能要求:
 * 1.文件读取要有线程池来执行，线程池的大小固定为10，文件内容需要存储到指定的内容数据结构当中
 * 2.查找要求有独立线程来执行，直接消费读取线程池产生的内存数据结构。
 * 3.文件读取和排序要求并发作业，文件读取只要产生了数据，就可以把数据交给排序线程进行消费，计算最小值。
 * 
 * 代码要求:
 * 1.重上面的要求语意里面抽象出合适的设计模式。
 * 2.需要考虑多线程的并发控制，同步机制。
 * 3.代码实现只能用JDK1.6或者1.8自带的工具类
 *
 * @author wenhy
 * @date 2019/6/11
 */
public class Ant_Producer$Consumer {

    public static final String PATH = "C:\\Ocean\\Work\\MyWork\\IntelliJ Workspace\\Hiccup2\\misc\\src\\main\\java\\top\\hiccup\\misc\\interview\\ant_txt";

    public static void main(String[] args) {
        File dirFile = new File(PATH);
        File[] files = dirFile.listFiles();
        DataWareHouse dataWareHouse = new DataWareHouse();
        CountDownLatch countDownLatch = new CountDownLatch(files.length + 1);
        for (File file : files) {
            Producer producer = new Producer(dataWareHouse.getQueue(), file, countDownLatch);
            //生产者线程使用线程池
            dataWareHouse.getThreadPool().execute(producer);
        }
        Consumer consumer = new Consumer(dataWareHouse.getQueue(), dataWareHouse.getTreeMap(), countDownLatch);
        //一个消费者线程消费
        new Thread(consumer).start();
        try {
            //生产者线程和消费者线程执行完成，关闭线程池，输出结果
            countDownLatch.await();
            dataWareHouse.getThreadPool().shutdownNow();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Iterator<Map.Entry<String, DataItem>> it = dataWareHouse.getTreeMap().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, DataItem> entry = it.next();
            DataItem dataItem = entry.getValue();
            System.out.println(dataItem.getGroupId() + "，" + dataItem.getId() + "，" + dataItem.getQuota());
        }
    }
}


class Producer implements Runnable {

    private LinkedBlockingQueue<DataItem> queue;
    private File file;
    private CountDownLatch countDownLatch;

    public Producer(LinkedBlockingQueue<DataItem> queue, File file, CountDownLatch countDownLatch) {
        this.queue = queue;
        this.file = file;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            InputStreamReader read = new InputStreamReader(new FileInputStream(file));
            BufferedReader br = new BufferedReader(read);
            String line = "";
            String[] arrs = null;
            while ((line = br.readLine()) != null) {
                if (line.equals("")) {
                    continue;
                }
                arrs = line.split("，");
                DataItem dataItem = new DataItem();
                dataItem.setId(arrs[0]);
                dataItem.setGroupId(arrs[1]);
                dataItem.setQuota(new Float(arrs[2]));
                queue.add(dataItem);
            }
            br.close();
            read.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }
}


class Consumer implements Runnable {

    private LinkedBlockingQueue<DataItem> queue;
    private TreeMap<String, DataItem> treeMap;
    private CountDownLatch countDownLatch;

    public Consumer(LinkedBlockingQueue<DataItem> queue, TreeMap<String, DataItem> treeMap, CountDownLatch countDownLatch) {
        this.queue = queue;
        this.treeMap = treeMap;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (!queue.isEmpty()) {
                    DataItem dataItem = queue.take();
                    DataItem mydataItem = treeMap.get(dataItem.getGroupId());
                    if (mydataItem == null) {
                        treeMap.put(dataItem.getGroupId(), dataItem);
                    } else {
                        if (dataItem.getQuota() < mydataItem.getQuota()) {
                            treeMap.put(dataItem.getGroupId(), dataItem);
                        }
                    }
                } else {
                    if (countDownLatch.getCount() <= 1) {
                        countDownLatch.countDown();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class DataItem {
    private String id;
    private String groupId;
    private Float quota;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Float getQuota() {
        return quota;
    }

    public void setQuota(Float quota) {
        this.quota = quota;
    }
}

class DataWareHouse {

    private static final int THREAD_POOL_SIZE = 10;

    public DataWareHouse() {
        queue = new LinkedBlockingQueue<>();
        treeMap = new TreeMap<>(new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                return Long.valueOf(o1).compareTo(Long.valueOf(o2));
            }
        });
        threadPool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 50,
                600, TimeUnit.SECONDS, new ArrayBlockingQueue<>(500), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    /**
     * 缓存生产者线程从文件读取的数据
     */
    private LinkedBlockingQueue<DataItem> queue;
    /**
     * 存储消费者线程处理后的数据（排序、获取同组指标最小的数据）
     */
    private TreeMap<String, DataItem> treeMap;
    private ExecutorService threadPool;

    public LinkedBlockingQueue<DataItem> getQueue() {
        return queue;
    }

    public void setQueue(LinkedBlockingQueue<DataItem> queue) {
        this.queue = queue;
    }

    public static int getThreadPoolSize() {
        return THREAD_POOL_SIZE;
    }

    public TreeMap<String, DataItem> getTreeMap() {
        return treeMap;
    }

    public void setTreeMap(TreeMap<String, DataItem> treeMap) {
        this.treeMap = treeMap;
    }

    public ExecutorService getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }
}

/**
 * 创建测试数据
 */
class CreateDataTest {
    public static void main(String[] args) throws IOException {
        String path = Ant_Producer$Consumer.PATH;
        for (int i = 1; i <= 100; i++) {
            File file = new File(path + "\\" + i + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter br = new BufferedWriter(fileWriter);
            for (int j = 0; j < 5000; j++) {
                br.write(getRandomData());
                br.newLine();
            }
            br.close();
            fileWriter.close();
        }
        System.out.println("success");
    }

    private static String getRandomData() {
        Integer id = (int) (Math.random() * 1000000) + 1000000;
        Integer groupId = (int) (Math.random() * 1000) + 100;
        Float quota = (int) (Math.random() * 1000) / 10.0f + 60;
        return id + "，" + groupId + "，" + quota;
    }
}