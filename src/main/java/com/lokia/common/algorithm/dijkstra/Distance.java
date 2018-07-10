package com.lokia.common.algorithm.dijkstra;

import lombok.Data;

@Data
public class Distance {

    String from;
    String to;
    int distance;

    String path;

    public Distance() {

    }

    public Distance(String from, String to, int distance) {
        setFrom(from);
        setTo(to);
        setDistance(distance);

        String path = from+"->"+to;
        setPath(path);
    }


    public String toString(){
        return "<"+from+","+to+">:"+distance+", path:"+path;
    }
}
