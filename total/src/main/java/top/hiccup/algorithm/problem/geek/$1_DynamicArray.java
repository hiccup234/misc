package top.hiccup.algorithm.problem.geek;

/**
 * 实现一个支持动态扩容的数组
 *
 * @author wenhy
 * @date 2019/5/26
 */
public class $1_DynamicArray<T> {
    /**
     * 保存数组当前存储的数据数量
     */
    private int size;

    private Object[] table;

    public $1_DynamicArray() {
        size = 0;
        table = new Object[10];
    }

    public $1_DynamicArray(int length) {
        size = 0;
        table = new Object[length];
    }

    public void add(T t) {
        if (size >= table.length) {
            resize(table.length << 1);
        }
        table[size] = t;
        size++;
    }

    public T remove(T t) {
        for (int i = 0; i < size; i++) {
            if (table[i] == t || table[i].equals(t)) {
                T ret = (T) table[i];
                int p = i;
                while (p < size && p < table.length - 1) {
                    table[p] = table[p + 1];
                }
                table[size - 1] = null;
                size--;
                return ret;
            }
        }
        if ((size << 1) <= table.length) {
            resize(table.length >> 1);
        }
        return null;
    }

    private void resize(int targetSize) {
        Object[] tmp = new Object[targetSize];
        System.arraycopy(table, 0, tmp, 0, size);
        table = tmp;
    }
}
