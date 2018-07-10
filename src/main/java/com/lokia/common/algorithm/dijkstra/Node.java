package com.lokia.common.algorithm.dijkstra;

import lombok.Data;

@Data
public class Node {

    public Node() {

    }

    public Node(String name, String next, int distance2Next) {
        setName(name);
    }

    private String name;


}
