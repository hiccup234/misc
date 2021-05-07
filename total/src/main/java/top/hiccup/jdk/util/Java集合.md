
# Java集合框架

## Java8中 stream 相关用法、apache 集合处理工具类的使用

## Collection接口与List、Set的区别？
   1、List是有序（添加顺序）集合而Collection没做要求，详见各种的类注释（这里的有序和无序不是指集合中的排序，而是是否按照元素添加的顺序来存储对象）
   2、List除了1.8添加的默认方法有所不同外，其他方法基本一致，而Set则与Collection完全一致
   3、List中可以有重复元素可以用null，而Set中不能存在重复元素
   
    稳定集合：List的所有实现类ArrayList、LinkedList、Vector等 以及 LinkedHashMap 和 LinkedHashSet
    不稳定集合：HashMap、TreeMap（排序有序）、TableMap 以及 HashSet、TreeSet

## Set如何保证元素不重复？
   一般是持有一个HashMap，然后通过HashMap的key的唯一性来实现元素不重复

## 集合类源码学习
[OK]ArrayList 
   1、底层采用数组实现，默认初始化容量为10，1.8初始化为空，添加第一个元素后膨胀为10
   2、扩容方法底层采用Arrays.copyOf（System.arraycopy）来实现数组的复制
     每次扩容增大1.5倍，如果容量int整数溢出了，则每次add容量只加1，addAll方法容量加原容器的size
   3、允许null元素，且允许多个null
   4、ArrayList的clone其实是浅拷贝
[OK]LinkedList
   1、与ArrayList继承AbstractList不同，LinkedList继承自AbstractSequentialList，是一个双向链表，可以用作堆栈、队列或双端队列
[OK]Vector
   1、底层数据结构也是基于数组，历次JDK升级对Vector改动几乎没有，处于被废弃状态
   2、通过synchronized关键字来保证线程安全
[OK]Stack
   1、继承自Vector，所以是线程安全的
   2、其实更应该继承或直接使用LinkedList来实现

[OK]HashMap
   1、HashMap中可以允许key和value同时为空（只能有一个为null的key，放在第一个桶中）
   3、动态扩容是把hash表的数组长度扩为原来的2倍（长度必须是2的x次方，方便hash定位）
   4、JDK1.8对hash表的链表长度默认超过8时做了优化，改成红黑树来实现
   5、默认初始化容量（数组长度）为16，加载因子为0.75
   6、如果加载因子小于1，则Map的size永远小于哈希表的数组长度，（默认0.75的初衷就是空间换时间）
      如果考虑直接用数组存储的话，则没法处理hash冲突的问题
   7、HashMap将“key为null”的元素放在table的位置0处，即table[0]中
[OK]WeakHashMap
   1、key采用弱引用，可以用来做内存缓存，当发生GC时就会被回收掉（这里怎么没提供一个SoftHashMap呢？）
   2、不是线程安全的，可以用Collections.synchronizedMap来构造同步的Map
   3、要求数组长度为2的n次方
[OK]LinkedHashMap
   1、LinkedHashMap直接继承自HashMap，然后添加双向链表数据结构，以支持按插入顺序遍历（访问顺序：put/get/compute）
[OK]TreeMap
   1、TreeMap基于红黑树（Red-Black tree）实现，基本操作 containsKey、get、put 和 remove 的时间复杂度是 log(n)
   2、TreeMap也是非同步，线程不安全的
   
[OK]HashSet
   1、内部持有一个HashMap
[OK]TreeSet
   1、内部持有一个TreeMap，重写了addAll方法

[OK]HashTable
   1、HashMap的同步版本，初始化容量为11，扩容时*2倍+1
[OK]ConcurrentHashMap
   1、JDK1.7采用锁分段的思想，一个Segment就是一个ReenTrantLock
   2、Segment数组不能扩容，扩容是针对Segment元素（一个小型HashMap）进行的
   3、get方法没有加锁，因为HashEntry的value字段是volatile修饰的，可以保证线程间的可见性
        QA：get没加锁会导致并发Map出现数据一致性问题（弱一致性），前后get看到的数据不一致，Map本身没有提供这种同步get方法的途径
            1.5和1.6Segment的get方法会造成更严重的一致性问题（判断count，无法及时看到更新）
[OK]CopyOnWriteArrayList
   1、没有初始容量，最适合于List大小通常保持很小，只读操作远多于写操作，需要在遍历期间防止线程间的冲突
   2、因为通常需要复制整个基础数组，所以可变操作（add()、set() 和 remove() 等等）的开销很大
   3、迭代器支持hasNext(), next()等不可变操作，但不支持可变 remove()等操作
   4、使用迭代器进行遍历的速度很快，并且不会与其他线程发生冲突。在构造迭代器时，迭代器依赖于不变的数组快照
[?]ConcurrentSkipListMap
   1、采用跳跃表实现
   2、可以保持插入的元素排序

## JUC包下的容器主要可以分为3类
1、Concurrent*       偏重并发修改，遍历时具有弱一致性
2、CopyOnWrite*      修改开销重，每次都要复制原数据
3、Blocking*         一般基于锁实现，会阻塞线程

## 其他问题
Collection 和 Collections    接口和工具集
Arrays.asList 获得的 List     使用时需要注意什么？（不可修改）
fail-fast 和 fail-safe       非线程安全容器的检查（fail-fast）

## 有界队列和无界队列
1.有界队列：ArrayBlockingQueue、LinkedBlockingQueue(如果没指定容量，则最大容量为Integer.MAX_VALUE)、SynchronousQueue(容量为0)
2.无界队列：PriorityBlockingQueue、DelayedQueue、LinkedTransferQueue