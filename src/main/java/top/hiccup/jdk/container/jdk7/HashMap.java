package top.hiccup.jdk.container.jdk7;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * 参考JDK1.7
 *
 * 1、HashMap中可以允许key和value同时为空（只能有一个为null的key，放在第一个桶中）
 * 2、多线程并发访问时，动态扩容时可能会引起后面get或put方法的假死锁（CPU 100%）
 * 3、动态扩容是把hash表的数组长度扩为原来的2倍（长度必须是2的x次方）
 * 4、JDK1.8对hash表的链表长度默认超过8时做了优化，改成红黑树来实现
 * 5.默认初始化容量（数组长度）为16，加载因子为0.75
 * 6.如果加载因子小于1，则Map的size永远小于哈希表的数组长度，（默认0.75的初衷就是空间换时间）
 *    如果考虑直接用数组存储的话，则没法处理hash冲突的问题
 *
 * @author wenhy
 * @date 2018/3/8
 */
public class HashMap<K,V> extends AbstractMap<K,V>
        implements Map<K,V>, Cloneable, Serializable {

    /**
     * The default initial capacity - MUST be a power of two.
     * HashMap初始化容量大小16，必须是2的x次方
     * 为什么这里的常量都不是private的呢？
     */
    static final int DEFAULT_INITIAL_CAPACITY = 16;

    /**
     * 最大容量为2的30次方（Hash表中数组的大小，int能表示的最大2的x次方正整数）
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     *  默认的加载因子为0.75
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 注意这里也是transient修饰的，table是HashMap的哈希表中的数组
     */
    transient Entry<K,V>[] table;

    /**
     * 所有的键值对总数（注意跟Map容量作区分：容量为哈希表数组的长度）
     */
    transient int size;

    /**
     * The next size value at which to resize (capacity * load factor).
     * 下次扩容的阀值，size>=threshold就会扩容
     */
    int threshold;

    final float loadFactor;
    /**
     * fast-fail模式 悲观模式
     */
    transient int modCount;

    static final int ALTERNATIVE_HASHING_THRESHOLD_DEFAULT = Integer.MAX_VALUE;

    private static class Holder {
        // Unsafe mechanics
        /**
         * Unsafe utilities
         */
        static final sun.misc.Unsafe UNSAFE;
        /**
         * Offset of "final" hashSeed field we must set in readObject() method.
         */
        static final long HASHSEED_OFFSET;
        /**
         * Table capacity above which to switch to use alternative hashing.
         */
        static final int ALTERNATIVE_HASHING_THRESHOLD;
        static {
            String altThreshold = java.security.AccessController.doPrivileged(
                    new sun.security.action.GetPropertyAction(
                            "jdk.map.althashing.threshold"));
            int threshold;
            try {
                threshold = (null != altThreshold)
                        ? Integer.parseInt(altThreshold)
                        : ALTERNATIVE_HASHING_THRESHOLD_DEFAULT;
                // disable alternative hashing if -1
                if (threshold == -1) {
                    threshold = Integer.MAX_VALUE;
                }
                if (threshold < 0) {
                    throw new IllegalArgumentException("value must be positive integer.");
                }
            } catch(IllegalArgumentException failed) {
                throw new Error("Illegal value for 'jdk.map.althashing.threshold'", failed);
            }
            ALTERNATIVE_HASHING_THRESHOLD = threshold;
            try {
                // 不是系统类加载器加载的类不能直接获取Unsafe
//                UNSAFE = sun.misc.Unsafe.getUnsafe();
//                HASHSEED_OFFSET = UNSAFE.objectFieldOffset(
//                        HashMap.class.getDeclaredField("hashSeed"));
                UNSAFE = null;
                HASHSEED_OFFSET = 0;
            } catch (SecurityException e) {
                throw new Error("Failed to record hashSeed offset", e);
            }
        }
    }

    transient boolean useAltHashing;

    // hash种子
//    transient final int hashSeed = sun.misc.Hashing.randomHashSeed(this);

    transient final int hashSeed = 0;

    public HashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                    initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                    loadFactor);

        // Find a power of 2 >= initialCapacity
        int capacity = 1;
        // 大于initialCapacity的最近的2的次方， initialCapacity = 5 则 capacity = 8
        // 每次左移一位就等价于乘以2
        while (capacity < initialCapacity)
            capacity <<= 1;

        this.loadFactor = loadFactor;
        // 下次扩容阀值，8*0.75=6
        threshold = (int)Math.min(capacity * loadFactor, MAXIMUM_CAPACITY + 1);
        table = new Entry[capacity];
        //TODO 这里干嘛的呢？
        useAltHashing = sun.misc.VM.isBooted() &&
                (capacity >= Holder.ALTERNATIVE_HASHING_THRESHOLD);
        init();
    }

    public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public HashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashMap(Map<? extends K, ? extends V> m) {
        // 先构造一个同等大小的Map
        this(Math.max((int) (m.size() / DEFAULT_LOAD_FACTOR) + 1,
                DEFAULT_INITIAL_CAPACITY), DEFAULT_LOAD_FACTOR);
        // 再将m的元素put进新的Map
        putAllForCreate(m);
    }
    // internal utilities
    /**
     * 钩子方法，方便继承的子类做其他事情（为什么不是protected呢？）
     */
    void init() {
    }

    /**
     * hash算法（注意是final的），对key取hashCode然后再做其他操作
     * @param k
     * @return
     */
    final int hash(Object k) {
        int h = 0;
        if (useAltHashing) {
            if (k instanceof String) {
//                return sun.misc.Hashing.stringHash32((String) k);
                return 0;
            }
            h = hashSeed;
        }
        h ^= k.hashCode();
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     * 返回key的hash值对应的哈希表数组的下标（注意这里用到了低位与运算，所以要求Map的容量必须是2的N次方）
     * @param h
     * @param length
     * @return
     */
    static int indexFor(int h, int length) {
        // 这里相当于取模运算
        // h为key的hashCode，length为数组的长度，2的x次方二进制表示只有一个1，减1则低位全为1，正好可以和h做与运算
        return h & (length-1);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public V get(Object key) {
        // get和put方法中，key为null值的情况下做单独处理（因为null没有hashCode，不能直接indexFor，所以默认为0）
        if (key == null)
            return getForNullKey();
        Entry<K,V> entry = getEntry(key);

        return null == entry ? null : entry.getValue();
    }

    /**
     * 注意null键是放在table[0]的位置
     * @return
     */
    private V getForNullKey() {
        for (Entry<K,V> e = table[0]; e != null; e = e.next) {
            if (e.key == null)
                return e.value;
        }
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        // 这里不用考虑key,value为空的情况，因为是通过Entry包装的
        return getEntry(key) != null;
    }

    final Entry<K,V> getEntry(Object key) {
        int hash = (key == null) ? 0 : hash(key);
        for (Entry<K,V> e = table[indexFor(hash, table.length)]; e != null; e = e.next) {
            Object k;
            if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                return e;
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        if (key == null)
            return putForNullKey(value);
        // 跟据hash方法计算出hash值
        int hash = hash(key);
        // 再找出对应的数组下标（这里为什么不直接调用getEntry方法呢？节省一次方法调用吗？）
        int i = indexFor(hash, table.length);
        // 然后遍历对应槽中的链表判断有没有存在对应的key
        for (Entry<K,V> e = table[i]; e != null; e = e.next) {
            Object k;
            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                V oldValue = e.value;
                e.value = value;
                e.recordAccess(this);
                return oldValue;
            }
        }
        // 程序走到这里说明当前Map中没有存在key对应的Entry，则新增Entry
        modCount++;
        addEntry(hash, key, value, i);
        return null;
    }

    private V putForNullKey(V value) {
        // 先遍历找看看当前Map容器里有没有key为null的Entry
        for (Entry<K,V> e = table[0]; e != null; e = e.next) {
            if (e.key == null) {
                V oldValue = e.value;
                e.value = value;
                e.recordAccess(this);
                return oldValue;
            }
        }
        modCount++;
        addEntry(0, null, value, 0);
        return null;
    }

    private void putForCreate(K key, V value) {
        // 如果key为null的话，hash值为0
        int hash = null == key ? 0 : hash(key);
        int i = indexFor(hash, table.length);

        /**
         * Look for preexisting entry for key.  This will never happen for
         * clone or deserialize.  It will only happen for construction if the
         * input Map is a sorted map whose ordering is inconsistent w/ equals.
         */
        // 如果入参是个有序的map，这里可能会打乱
        for (Entry<K,V> e = table[i]; e != null; e = e.next) {
            Object k;
            if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k)))) {
                e.value = value;
                return;
            }
        }
        createEntry(hash, key, value, i);
    }

    private void putAllForCreate(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet())
            putForCreate(e.getKey(), e.getValue());
    }

    /**
     * TODO 面试的时候爱问的
     * 动态扩容，直接new一个Entry数组，再对hash表里的链表做遍历通过hash值(可能做reHash)做indexOf，再加入头节点
     * 这里可能会造成问题：当多线程访问时，造成死锁现象，CPU 100%
     * @param newCapacity
     */
    void resize(int newCapacity) {
        Entry[] oldTable = table;
        int oldCapacity = oldTable.length;
        // 如果当前容量已经达到，则直接把“下次扩容阀值”Integer.MAX_VALUE并返回
        if (oldCapacity == MAXIMUM_CAPACITY) {
            // TODO 这里为什么不返回MAXIMUM_CAPACITY呢？ A: 这里赋值的是阈值，如果加载因子大于1，则threshold可以大于capacity
            threshold = Integer.MAX_VALUE;
            return;
        }

        Entry[] newTable = new Entry[newCapacity];
        boolean oldAltHashing = useAltHashing;
        useAltHashing |= sun.misc.VM.isBooted() &&
                (newCapacity >= Holder.ALTERNATIVE_HASHING_THRESHOLD);
        boolean rehash = oldAltHashing ^ useAltHashing;
        transfer(newTable, rehash);
        table = newTable;
        //TODO 这里为什么要加 1 呢？
        threshold = (int)Math.min(newCapacity * loadFactor, MAXIMUM_CAPACITY + 1);
    }

    void transfer(Entry[] newTable, boolean rehash) {
        int newCapacity = newTable.length;
        for (Entry<K,V> e : table) {
            while(null != e) {
                Entry<K,V> next = e.next;
                if (rehash) {
                    e.hash = null == e.key ? 0 : hash(e.key);
                }
                int i = indexFor(e.hash, newCapacity);
                // 直接在hash链表的头节点插入当前Entry
                e.next = newTable[i];
                newTable[i] = e;
                e = next;
            }
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        int numKeysToBeAdded = m.size();
        if (numKeysToBeAdded == 0)
            return;
        // TODO 这里的numKeysToBeAdded是不是应该要this.size+m.size()呢？
        // TODO 这里确实有点问题，下面的for循环中put操作可能会导致再次resize，奇怪怎么没人提出这个问题呢？
        if (numKeysToBeAdded > threshold) {
            // +1是为了补上被强转为int而抹去的小数部分
            int targetCapacity = (int)(numKeysToBeAdded / loadFactor + 1);
            if (targetCapacity > MAXIMUM_CAPACITY)
                targetCapacity = MAXIMUM_CAPACITY;
            int newCapacity = table.length;
            while (newCapacity < targetCapacity)
                newCapacity <<= 1;
            // 这里是为了防止溢出吗？ 因为只要进到if语句里，targetCapacity一定大于table.length（numKeysToBeAdded > threshold >= this.size）
            if (newCapacity > table.length)
                resize(newCapacity);
        }

        for (Map.Entry<? extends K, ? extends V> e : m.entrySet())
            put(e.getKey(), e.getValue());
    }

    @Override
    public V remove(Object key) {
        Entry<K,V> e = removeEntryForKey(key);
        return (e == null ? null : e.value);
    }

    final Entry<K,V> removeEntryForKey(Object key) {
        int hash = (key == null) ? 0 : hash(key);
        int i = indexFor(hash, table.length);
        Entry<K,V> prev = table[i];
        Entry<K,V> e = prev;
        while (e != null) {
            Entry<K,V> next = e.next;
            Object k;
            if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k)))) {
                modCount++;
                size--;
                // 如果是hash表中链表的头节点
                if (prev == e)
                    table[i] = next;
                else
                    prev.next = next;
                // recordRemoval目前是个空实现
                e.recordRemoval(this);
                return e;
            }
            prev = e;
            e = next;
        }
        return e;
    }

    // Special version of remove for EntrySet using {@code Map.Entry.equals()} for matching.
    final Entry<K,V> removeMapping(Object o) {
        //TODO 为什么不直接接收一个Map.Entry<K,V>类型的对象呢？
        if (!(o instanceof Map.Entry))
            return null;
        Map.Entry<K,V> entry = (Map.Entry<K,V>) o;
        Object key = entry.getKey();
        int hash = (key == null) ? 0 : hash(key);
        int i = indexFor(hash, table.length);
        Entry<K,V> prev = table[i];
        Entry<K,V> e = prev;
        while (e != null) {
            Entry<K,V> next = e.next;
            // 方法：removeEntryForKey用的是key做判断((k = e.key) == key || (key != null && key.equals(k)))
            // 而这里是直接判断entry对象是否equals，其他没有区别。。
            if (e.hash == hash && e.equals(entry)) {
                modCount++;
                size--;
                if (prev == e)
                    table[i] = next;
                else
                    prev.next = next;
                e.recordRemoval(this);
                return e;
            }
            prev = e;
            e = next;
        }
        return e;
    }

    /**
     * Removes all of the mappings from this map.
     * The map will be empty after this call returns.
     */
    @Override
    public void clear() {
        modCount++;
        Entry[] tab = table;
        // 直接把hash表中的槽置null，这里的垃圾回收算法就不能用“引用计数”了
        for (int i = 0; i < tab.length; i++)
            tab[i] = null;
        size = 0;
    }

    @Override
    public boolean containsValue(Object value) {
        // 如果value是null就走分支直接用“==”判断来做优化，JDK的大神们真实无所不用其极啊
        if (value == null)
            // 这里为什么要调方法而不直接把逻辑写这里呢？跟其他地方的优化思想不一样啊
            return containsNullValue();
        Entry[] tab = table;
        for (int i = 0; i < tab.length ; i++)
            for (Entry e = tab[i] ; e != null ; e = e.next)
                // 这里怎么没用先判 == 判 null 再 equals 呢?
                if (value.equals(e.value))
                    return true;
        return false;
    }

    /**
     * Special-case code for containsValue with null argument
     */
    private boolean containsNullValue() {
        Entry[] tab = table;
        for (int i = 0; i < tab.length ; i++)
            for (Entry e = tab[i] ; e != null ; e = e.next)
                if (e.value == null)
                    return true;
        return false;
    }

    /**
     * Returns a shallow copy of this <tt>HashMap</tt> instance: the keys and
     * values themselves are not cloned.
     * clone只是做浅拷贝
     */
    @Override
    public Object clone() {
        HashMap<K,V> result = null;
        try {
            // 重写clone方法的第一步一定要调用super的clone()，最后会调用到Object的本地方法clone()
            result = (HashMap<K,V>)super.clone();
        } catch (CloneNotSupportedException e) {
            // assert false;  TODO 这里为什么什么都不干啊？
        }
        // hash表是重新建立的，Entry也是重新new的，只不过key和value没有做拷贝
        result.table = new Entry[table.length];
        result.entrySet = null;
        result.modCount = 0;
        result.size = 0;
        result.init();
        result.putAllForCreate(this);
        return result;
    }

    /**
     * hash表中保存的元素，重点，Entry不是final的，而ConcurrentHashMap的HashEntry是final的
     * int hash成员变量也不是final的，JDK1.8中Node为final int hash
     * @param <K>
     * @param <V>
     */
    static class Entry<K,V> implements Map.Entry<K,V> {
        final K key;
        V value;
        Entry<K,V> next;
        /**
         * 用来缓存计算好的hash值，就不用每次取到key再調hash方法了
         */
        int hash;

        Entry(int h, K k, V v, Entry<K,V> n) {
            value = v;
            next = n;
            key = k;
            hash = h;
        }

        public final K getKey() {
            return key;
        }

        public final V getValue() {
            return value;
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry e = (Map.Entry)o;
            Object k1 = getKey();
            Object k2 = e.getKey();
            // 先用==做判断，如果相等就直接短路，不等再調equals方法，这种优化思想在整个HashMap里到处都有体现
            if (k1 == k2 || (k1 != null && k1.equals(k2))) {
                Object v1 = getValue();
                Object v2 = e.getValue();
                if (v1 == v2 || (v1 != null && v1.equals(v2)))
                    return true;
            }
            return false;
        }

        public final int hashCode() {
            // 按位异或：相同为0，相异为1
            return (key==null   ? 0 : key.hashCode()) ^
                    (value==null ? 0 : value.hashCode());
        }

        public final String toString() {
            return getKey() + "=" + getValue();
        }

        /**
         * This method is invoked whenever the value in an entry is
         * overwritten by an invocation of put(k,v) for a key k that's already
         * in the HashMap.
         */
        void recordAccess(HashMap<K,V> m) {
        }

        /**
         * This method is invoked whenever the entry is
         * removed from the table.
         */
        void recordRemoval(HashMap<K,V> m) {
        }
    }

    void addEntry(int hash, K key, V value, int bucketIndex) {
        if ((size >= threshold) && (null != table[bucketIndex])) {
            // 动态扩容，直接是按2倍来扩容，这里为什么不用左移呢 << 1
            resize(2 * table.length);
            hash = (null != key) ? hash(key) : 0;
            bucketIndex = indexFor(hash, table.length);
        }
        createEntry(hash, key, value, bucketIndex);
    }

    void createEntry(int hash, K key, V value, int bucketIndex) {
        Entry<K,V> e = table[bucketIndex];
        // 直接是在hash表中链表的头部做插入，这样速度快
        table[bucketIndex] = new Entry<>(hash, key, value, e);
        size++;
    }

    /**
     * 类似List中的ListIterator实现
     * @param <E>
     */
    private abstract class HashIterator<E> implements Iterator<E> {
        Entry<K,V> next;        // next entry to return
        int expectedModCount;   // For fast-fail
        int index;              // current slot
        Entry<K,V> current;     // current entry
        HashIterator() {
            expectedModCount = modCount;
            if (size > 0) { // advance to first entry
                Entry[] t = table;
                // 找到第一个Entry
                while (index < t.length && (next = t[index++]) == null)
                    ;
            }
        }

        public final boolean hasNext() {
            return next != null;
        }

        final Entry<K,V> nextEntry() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            Entry<K,V> e = next;
            if (e == null)
                throw new NoSuchElementException();
            // 往前迭代当前槽的链表，并修改了next，如果到达链表末尾这沿着hash表中数组往后遍历，直到找到不为空的槽
            if ((next = e.next) == null) {
                Entry[] t = table;
                while (index < t.length && (next = t[index++]) == null)
                    ;
            }
            current = e;
            return e;
        }

        public void remove() {
            if (current == null)
                throw new IllegalStateException();
            // 如果这时modCount与expectedModCount已经不相等，则证明HashMap已经被做了修改，在迭代遍历过程中不能直接修改HashMap
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            Object k = current.key;
            // 注意这里current被置为null了，所以在一次迭代中调用两次remove()会造成抛出异常
            current = null;
            HashMap.this.removeEntryForKey(k);
            // 重新修改expectedModCount
            expectedModCount = modCount;
        }
    }

    private final class ValueIterator extends HashIterator<V> {
        public V next() {
            return nextEntry().value;
        }
    }

    private final class KeyIterator extends HashIterator<K> {
        public K next() {
            return nextEntry().getKey();
        }
    }

    private final class EntryIterator extends HashIterator<Map.Entry<K,V>> {
        public Map.Entry<K,V> next() {
            return nextEntry();
        }
    }

    // Subclass overrides these to alter behavior of views' iterator() method
    Iterator<K> newKeyIterator() {
        return new KeyIterator();
    }
    Iterator<V> newValueIterator()   {
        return new ValueIterator();
    }
    Iterator<Map.Entry<K,V>> newEntryIterator()   {
        return new EntryIterator();
    }

    private transient Set<Map.Entry<K,V>> entrySet = null;

    /**
     * 这两个变量是定义在AbstractMap中的，因为不在同一个包中访问不了，现在挪到这里来
     */
    transient volatile Set<K>        keySet = null;
    transient volatile Collection<V> values = null;

    @Override
    public Set<K> keySet() {
        Set<K> ks = keySet;
        return (ks != null ? ks : (keySet = new KeySet()));
    }

    private final class KeySet extends AbstractSet<K> {
        public Iterator<K> iterator() {
            return newKeyIterator();
        }
        public int size() {
            return size;
        }
        public boolean contains(Object o) {
            return containsKey(o);
        }
        public boolean remove(Object o) {
            return HashMap.this.removeEntryForKey(o) != null;
        }
        public void clear() {
            HashMap.this.clear();
        }
    }

    @Override
    public Collection<V> values() {
        Collection<V> vs = values;
        return (vs != null ? vs : (values = new Values()));
    }

    private final class Values extends AbstractCollection<V> {
        public Iterator<V> iterator() {
            return newValueIterator();
        }
        public int size() {
            return size;
        }
        public boolean contains(Object o) {
            return containsValue(o);
        }
        public void clear() {
            HashMap.this.clear();
        }
    }

    @Override
    public Set<Map.Entry<K,V>> entrySet() {
        // 为什么还要調entrySet0()呢？这种写法有什么巧妙之处吗？
        return entrySet0();
    }

    private Set<Map.Entry<K,V>> entrySet0() {
        Set<Map.Entry<K,V>> es = entrySet;
        return es != null ? es : (entrySet = new EntrySet());
    }

    private final class EntrySet extends AbstractSet<Map.Entry<K,V>> {
        public Iterator<Map.Entry<K,V>> iterator() {
            return newEntryIterator();
        }
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<K,V> e = (Map.Entry<K,V>) o;
            Entry<K,V> candidate = getEntry(e.getKey());
            return candidate != null && candidate.equals(e);
        }
        public boolean remove(Object o) {
            return removeMapping(o) != null;
        }
        public int size() {
            return size;
        }
        public void clear() {
            HashMap.this.clear();
        }
    }

    private void writeObject(java.io.ObjectOutputStream s) throws IOException {
        Iterator<Map.Entry<K,V>> i =
                (size > 0) ? entrySet0().iterator() : null;
        // Write out the threshold, loadfactor, and any hidden stuff
        s.defaultWriteObject();
        // Write out number of buckets
        s.writeInt(table.length);
        // Write out size (number of Mappings)
        s.writeInt(size);
        // Write out keys and values (alternating)
        if (size > 0) {
            for(Map.Entry<K,V> e : entrySet0()) {
                s.writeObject(e.getKey());
                s.writeObject(e.getValue());
            }
        }
    }

    /**
     * 为什么想起来把serialVersionUID放这里呢，一般不是放头部的么
     */
    private static final long serialVersionUID = 362498820763181265L;

    private void readObject(java.io.ObjectInputStream s)
            throws IOException, ClassNotFoundException {
        // Read in the threshold (ignored), loadfactor, and any hidden stuff
        s.defaultReadObject();
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new InvalidObjectException("Illegal load factor: " +
                    loadFactor);
        // set hashSeed (can only happen after VM boot)
//        Holder.UNSAFE.putIntVolatile(this, Holder.HASHSEED_OFFSET,
//                sun.misc.Hashing.randomHashSeed(this));
        // Read in number of buckets and allocate the bucket array;
        s.readInt(); // ignored
        // Read number of mappings
        int mappings = s.readInt();
        if (mappings < 0)
            throw new InvalidObjectException("Illegal mappings count: " +
                    mappings);
        int initialCapacity = (int) Math.min(
                // capacity chosen by number of mappings
                // and desired load (if >= 0.25)
                mappings * Math.min(1 / loadFactor, 4.0f),
                // we have limits...
                HashMap.MAXIMUM_CAPACITY);
        int capacity = 1;
        // find smallest power of two which holds all mappings
        while (capacity < initialCapacity) {
            capacity <<= 1;
        }
        table = new Entry[capacity];
        threshold = (int) Math.min(capacity * loadFactor, MAXIMUM_CAPACITY + 1);
        useAltHashing = sun.misc.VM.isBooted() &&
                (capacity >= Holder.ALTERNATIVE_HASHING_THRESHOLD);
        init();  // Give subclass a chance to do its thing.
        // Read the keys and values, and put the mappings in the HashMap
        for (int i=0; i<mappings; i++) {
            K key = (K) s.readObject();
            V value = (V) s.readObject();
            putForCreate(key, value);
        }
    }

    // These methods are used when serializing HashSets
    int   capacity()     { return table.length; }
    float loadFactor()   { return loadFactor;   }

}