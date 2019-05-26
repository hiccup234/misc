package top.hiccup.algorithm.problem.geek;

/**
 * 实现一个大小固定的有序数组，支持动态增删改操作
 *
 * @author wenhy
 * @date 2019/5/26
 */
public class $2_InsertOrderedArray<T extends Comparable> {

    private int size;

    private Object[] table;

    public $2_InsertOrderedArray(int size) {
        size = 0;
        table = new Object[size];
    }

    public void add(T t) {
        if (size >= table.length) {
            throw new RuntimeException("超过数组容量");
        }
        for (int i = size-1; i>=0; i--) {
            if (((T) table[i]).compareTo(t) >= 0) {
                table[i+1] = table[i];
            } else {
                table[i] = t;
                size++;
                return ;
            }
        }
    }

    public T delete(int index) {
        if (index < 0 || index >= size) {
            throw new RuntimeException("数组越界");
        }
        if (index == size-1) {
            T ret = (T) table[size-1];
            table[size-1] = null;
            return ret;
        }
        T ret = (T) table[index];
        for (int i = index; i < size; i++) {
            table[i] = table[i+1];
        }
        return ret;
    }

    /**
     * 只更新第一个
     */
    public T alter(T src, T target) {
        for (int i = 0; i < size; i++) {
            if (((T) table[i]).compareTo(src) == 0) {
                T ret = (T) table[i];
                table[i] = target;
                return ret;
            }
        }
        return null;
    }
}
