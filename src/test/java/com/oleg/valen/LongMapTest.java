package com.oleg.valen;

import org.junit.Assert;

public class LongMapTest {

    @org.junit.Test
    public void put() throws Exception {
        LongMap<String> sm = new LongMap();
        sm.put(1, "A");
        sm.put(17, "B");
        sm.put(33, "C");
        sm.put(33, "E");
        sm.put(49, "F");
        StringBuilder sb = new StringBuilder();
        LongMap.Node<String> node = sm.getNodes()[6];
        while (node != null) {
            sb.append(node.value);
            node = node.next;
        }
        Assert.assertEquals("ABEF", sb.toString());
    }

    @org.junit.Test
    public void get1() throws Exception {
        LongMap<String> sm = new LongMap();
        sm.put(1, "A");
        sm.put(17, "B");
        sm.put(33, "C");
        sm.put(33, "E");
        sm.put(49, "F");
        Assert.assertEquals("A", sm.get(1));
    }

    @org.junit.Test
    public void get2() throws Exception {
        LongMap<String> sm = new LongMap();
        sm.put(1, "A");
        sm.put(17, "B");
        sm.put(33, "C");
        sm.put(33, "E");
        sm.put(49, "F");
        Assert.assertEquals("F", sm.get(49));
    }

    @org.junit.Test
    public void get3() throws Exception {
        LongMap<String> sm = new LongMap();
        sm.put(1, "A");
        sm.put(17, "B");
        sm.put(33, "C");
        sm.put(33, "E");
        sm.put(49, "F");
        Assert.assertEquals("E", sm.get(33));
    }

    @org.junit.Test
    public void get4() throws Exception {
        LongMap<String> sm = new LongMap();
        sm.put(1, "A");
        sm.put(17, "B");
        sm.put(33, "C");
        sm.put(33, "E");
        sm.put(49, "F");
        Assert.assertNull(sm.get(50));
    }

    @org.junit.Test
    public void remove1() throws Exception {
        LongMap<String> sm = new LongMap();
        sm.put(1, "A");
        sm.put(17, "B");
        sm.put(33, "C");
        sm.put(33, "E");
        sm.put(49, "F");
        sm.remove(1);
        StringBuilder sb = new StringBuilder();
        LongMap.Node<String> node = sm.getNodes()[6];
        while (node != null) {
            sb.append(node.value);
            node = node.next;
        }
        Assert.assertEquals("BEF", sb.toString());
    }

    @org.junit.Test
    public void remove2() throws Exception {
        LongMap<String> sm = new LongMap();
        sm.put(1, "A");
        sm.put(17, "B");
        sm.put(33, "C");
        sm.put(33, "E");
        sm.put(49, "F");
        sm.remove(33);
        StringBuilder sb = new StringBuilder();
        LongMap.Node<String> node = sm.getNodes()[6];
        while (node != null) {
            sb.append(node.value);
            node = node.next;
        }
        Assert.assertEquals("ABF", sb.toString());
    }

    @org.junit.Test
    public void remove3() throws Exception {
        LongMap<String> sm = new LongMap();
        sm.put(1, "A");
        sm.put(17, "B");
        sm.put(33, "C");
        sm.put(33, "E");
        sm.put(49, "F");
        sm.remove(49);
        StringBuilder sb = new StringBuilder();
        LongMap.Node<String> node = sm.getNodes()[6];
        while (node != null) {
            sb.append(node.value);
            node = node.next;
        }
        Assert.assertEquals("ABE", sb.toString());
    }

    @org.junit.Test
    public void containsKeyTrue() throws Exception {
        LongMap<String> sm = new LongMap();
        sm.put(1, "A");
        sm.put(17, "B");
        sm.put(33, "C");
        sm.put(33, "E");
        sm.put(49, "F");
        Assert.assertTrue(sm.containsKey(17));
    }

    @org.junit.Test
    public void containsKeyFalse() throws Exception {
        LongMap<String> sm = new LongMap();
        sm.put(1, "A");
        sm.put(17, "B");
        sm.put(33, "C");
        sm.put(33, "E");
        sm.put(49, "F");
        Assert.assertFalse(sm.containsKey(50));
    }

    @org.junit.Test
    public void containsValueTrue() throws Exception {
        LongMap<String> sm = new LongMap();
        sm.put(1, "A");
        sm.put(17, "B");
        sm.put(33, "C");
        sm.put(33, "E");
        sm.put(49, "F");
        Assert.assertTrue(sm.containsValue("E"));
    }

    @org.junit.Test
    public void containsValueFalse() throws Exception {
        LongMap<String> sm = new LongMap();
        sm.put(1, "A");
        sm.put(17, "B");
        sm.put(33, "C");
        sm.put(33, "E");
        sm.put(49, "F");
        Assert.assertFalse(sm.containsValue("K"));
    }
}