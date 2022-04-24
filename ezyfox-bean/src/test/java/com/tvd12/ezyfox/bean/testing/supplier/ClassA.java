package com.tvd12.ezyfox.bean.testing.supplier;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

@Setter
@Getter
@EzyPrototype("ca")
@SuppressWarnings("rawtypes")
public class ClassA {

    @EzyAutoBind
    private Map map;
    @EzyAutoBind
    private HashMap hashMap;
    @EzyAutoBind
    private ConcurrentHashMap concurrentHashMap;
    @EzyAutoBind
    private Set set;
    @EzyAutoBind
    private TreeMap treeMap;
    @EzyAutoBind
    private HashSet hashSet;
    @EzyAutoBind
    private CopyOnWriteArrayList copyOnWriteArrayList;
    @EzyAutoBind
    private CopyOnWriteArraySet copyOnWriteArraySet;
    @EzyAutoBind
    private LinkedList linkedList;
    @EzyAutoBind
    private Queue queue;
    @EzyAutoBind
    private List list;
    @EzyAutoBind
    private ArrayList arrayList;
    @EzyAutoBind
    private Stack stack;
    @EzyAutoBind
    private Collection collection;
}
