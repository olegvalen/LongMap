package com.oleg.valen;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class LongMap<V> implements TestMap<V> {
    private int size = 0;
    private int sizeNodes = 0;
    private final static int DEFAULT_CAPACITY = 16;
    private int capacity = DEFAULT_CAPACITY;
    private double load = 0.75;
    private boolean doRehash = true;
    private Node[] nodes;

    public Node[] getNodes() {
        return nodes;
    }

    public LongMap() {
        nodes = new Node[capacity];
    }

    public LongMap(int capacity) {
        this.capacity = capacity;
        nodes = new Node[capacity];
    }

    public long hash(long key) {
        long result = 17;
        result = 37 * result + key;
        return result;
    }

    public V put(long key, V value) {
        Node<V> prev = null;
        V oldValue = null;

        if (capacity > 0 && (double) sizeNodes / capacity > load && doRehash)
            rehash();

        int index = (int) (hash(key) % capacity);
        if (nodes[index] == null) {
            nodes[index] = new Node(key, value);
            size++;
            sizeNodes++;
        } else {
            Node<V> cur = nodes[index];
            while (cur != null && cur.key != key) {
                prev = cur;
                cur = cur.next;
            }
            if (cur == null) {
                prev.next = new Node(key, value);
                size++;
            } else {
                oldValue = cur.value;
                cur.value = value;
            }
        }
        return oldValue;
    }

    public V get(long key) {
        int index = (int) (hash(key) % capacity);
        Node<V> cur = nodes[index];
        if (cur == null)
            return null;
        else {
            while (cur != null && cur.key != key)
                cur = cur.next;
            if (cur == null)
                return null;
            return cur.value;
        }
    }

    public V remove(long key) {
        Node<V> prev = null;
        Node<V> next = null;
        V value;
        int index = (int) (hash(key) % capacity);
        Node<V> cur = nodes[index];
        if (cur == null)
            return null;
        else {
            if (cur.key == key) {
                nodes[index] = cur.next;
                size--;
                if (nodes[index] == null) {
                    sizeNodes--;
                }
                return cur.value;
            } else {
                while (cur != null && cur.key != key) {
                    prev = cur;
                    cur = cur.next;
                    next = cur.next;
                }
                if (cur == null)
                    return null;
                size--;
                value = cur.value;
                prev.next = next;
                return value;
            }
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(long key) {
        int index = (int) (hash(key) % capacity);
        Node<V> cur = nodes[index];
        if (cur == null)
            return false;
        else {
            while (cur != null && cur.key != key)
                cur = cur.next;
            if (cur == null)
                return false;
            return true;
        }
    }

    public boolean containsValue(V value) {
        for (int i = 0; i < nodes.length; i++) {
            Node<V> cur = nodes[i];
            if (cur == null)
                continue;
            while (cur != null && !value.equals(cur.value))
                cur = cur.next;
            if (cur != null)
                return true;
        }
        return false;
    }

    public long[] keys() {
        int k = 0;
        long[] keys = new long[size];
        for (int i = 0; i < nodes.length; i++) {
            Node<V> cur = nodes[i];
            if (cur == null)
                continue;
            while (cur != null) {
                keys[k++] = cur.key;
                cur = cur.next;
            }
        }
        return keys;
    }

    public V[] values() {
        int k = 0;
        V[] values = (V[]) new Object[size];
        for (int i = 0; i < nodes.length; i++) {
            Node<V> cur = nodes[i];
            if (cur == null)
                continue;
            while (cur != null) {
                values[k++] = cur.value;
                cur = cur.next;
            }
        }
        return values;
    }

    public long size() {
        return size;
    }

    public void clear() {
        size = 0;
        sizeNodes = 0;
        nodes = new Node[DEFAULT_CAPACITY];
    }

    private void rehash() {
        size = 0;
        sizeNodes = 0;
        doRehash = false;
        Node[] oldNodes = nodes;
        capacity = capacity * 2;
        nodes = new Node[capacity];
        for (Node<V> oldNode : oldNodes) {
            if (oldNode != null)
                while (oldNode != null) {
                    put(oldNode.key, oldNode.value);
                    oldNode = oldNode.next;
                }
        }
        doRehash = true;
    }

    public void print() {
        for (int i = 0; i < nodes.length; i++) {
            Node<V> node = nodes[i];
            System.out.println("nodes[" + i + "]: ");
            if (node == null)
                System.out.println("   null");
/*
            while (node != null) {
                System.out.println(node);
                node = node.next;
            }
*/
            else {
                for (Node<V> n : node)
                    System.out.println(n);
            }
        }
        System.out.println();
    }

    static class Node<V> implements Iterable<Node<V>> {
        long key;
        V value;
        Node<V> next;

        public Node(long key, V value) {
            this.key = key;
            this.value = value;
        }

        public Iterator<Node<V>> iterator() {
            return new NodeIterator();
        }

        class NodeIterator implements Iterator<Node<V>> {

            Node<V> curNode;

            public NodeIterator() {
                curNode = Node.this;
            }

            public boolean hasNext() {
                return curNode != null;

            }

            public Node<V> next() {
                Node<V> tmpNode = curNode;
                curNode = curNode.next;
                return tmpNode;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

        }

        @Override
        public String toString() {
            return "   node{" + "key=" + key + ", value=" + value + ", next=" + next + '}';
        }

        public static void main(String[] args) {

            Random rand = new Random();
            String[] strings = ("A B C D E F G H I J K L M N O P Q R S T U V W X Y Z").split(" ");

            LongMap<String> sm = new LongMap();

            //9
            sm.put(100, strings[rand.nextInt(strings.length)]);
            sm.put(116, strings[rand.nextInt(strings.length)]);
            sm.put(132, strings[rand.nextInt(strings.length)]);

            //10
            sm.put(101, strings[rand.nextInt(strings.length)]);
            sm.put(117, strings[rand.nextInt(strings.length)]);
            sm.put(133, strings[rand.nextInt(strings.length)]);

            //11
            sm.put(102, strings[rand.nextInt(strings.length)]);
            sm.put(118, strings[rand.nextInt(strings.length)]);
            sm.put(134, strings[rand.nextInt(strings.length)]);

            //12
            sm.put(103, strings[rand.nextInt(strings.length)]);
            sm.put(119, strings[rand.nextInt(strings.length)]);
            sm.put(135, strings[rand.nextInt(strings.length)]);

            //13
            sm.put(104, strings[rand.nextInt(strings.length)]);
            sm.put(120, strings[rand.nextInt(strings.length)]);
            sm.put(136, strings[rand.nextInt(strings.length)]);

            //14
            sm.put(105, strings[rand.nextInt(strings.length)]);
            sm.put(121, strings[rand.nextInt(strings.length)]);
            sm.put(137, strings[rand.nextInt(strings.length)]);

            //15
            sm.put(106, strings[rand.nextInt(strings.length)]);
            sm.put(122, strings[rand.nextInt(strings.length)]);
            sm.put(138, strings[rand.nextInt(strings.length)]);

            //16
            sm.put(107, strings[rand.nextInt(strings.length)]);
            sm.put(123, strings[rand.nextInt(strings.length)]);
            sm.put(139, strings[rand.nextInt(strings.length)]);

            //17
            sm.put(108, strings[rand.nextInt(strings.length)]);
            sm.put(124, strings[rand.nextInt(strings.length)]);
            sm.put(140, strings[rand.nextInt(strings.length)]);

            //18
            sm.put(109, strings[rand.nextInt(strings.length)]);
            sm.put(125, strings[rand.nextInt(strings.length)]);
            sm.put(141, strings[rand.nextInt(strings.length)]);

            //19
            sm.put(110, strings[rand.nextInt(strings.length)]);
            sm.put(126, strings[rand.nextInt(strings.length)]);
            sm.put(142, strings[rand.nextInt(strings.length)]);

            //20
            sm.put(111, strings[rand.nextInt(strings.length)]);
            sm.put(127, strings[rand.nextInt(strings.length)]);
            sm.put(143, strings[rand.nextInt(strings.length)]);

            //21
            sm.put(112, strings[rand.nextInt(strings.length)]);

            sm.print();

            //rehash
            sm.put(128, strings[rand.nextInt(strings.length)]);
            sm.put(144, null);

            sm.print();

            System.out.println(sm.get(138));
            System.out.println(sm.remove(137));
            System.out.println(sm.containsKey(138));
            System.out.println(sm.containsValue("a"));
            System.out.println(Arrays.toString(sm.keys()));
            System.out.println(Arrays.toString(sm.values()));
            System.out.println(sm.size());

        }
    }
}
