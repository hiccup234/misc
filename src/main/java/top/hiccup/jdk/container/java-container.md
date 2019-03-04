
##Java集合框架

#Java8中 stream 相关用法、apache 集合处理工具类的使用

#Collection接口与List、Set的区别？
   1、List是有序（添加顺序）集合而Collection没做要求，详见各种的类注释（这里的有序和无序不是指集合中的排序，而是是否按照元素添加的顺序来存储对象）
   2、List除了1.8添加的默认方法有所不同外，其他方法基本一致，而Set则与Collection完全一致
   3、List中可以有重复元素可以用null，而Set中不能存在重复元素
   
有序集合： List的所有实现类ArrayList、LinkedList、Vector 以及 LinkedHashMap 和 LinkedHashSet
无序集合： HashMap、TreeMap（排序有序）、TableMap 以及 HashSet、TreeSet

#Set如何保证元素不重复？


# 集合类源码学习
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

[]HashMap

[OK]HashTable
   1、HashMap的同步版本，初始化容量为11，扩容时*2倍+1


[]ConcurrentHashMap
[]CopyOnWriteArrayList
[]ConcurrentSkipListMap


##################################################################

Collection 和 Collections    接口和工具集

Arrays.asList 获得的 List     使用时需要注意什么（不可修改）

Enumeration 和 Iterator      区别

fail-fast 和 fail-safe       非线程安全容器的检查（fail-fast）

