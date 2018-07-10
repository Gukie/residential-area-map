package com.lokia.common.algorithm;

import com.lokia.common.algorithm.dijkstra.Distance;
import com.lokia.common.algorithm.dijkstra.Node;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DijkstraAlgorithmTest {

    public static void main(String[] args) {

        Set<Distance> dist = generateDistanceSet();

        Node source = new Node();
        source.setName("V1");
        List<Distance> result = DijkstraAlgorithm.getShortestDistance(dist,source);

        System.out.println("=================== output ===================");
        for(Distance distance: result){
            System.out.println(distance);
        }

    }

    private static Set<Distance> generateDistanceSet() {
        Set<Distance> result = new HashSet<>();

        Distance dist = new Distance("V1","V3",10);
        result.add(dist);

        dist = new Distance("V1","V5",30);
        result.add(dist);
        dist = new Distance("V1","V6",100);
        result.add(dist);
        dist = new Distance("V3","V4",50);
        result.add(dist);
        dist = new Distance("V2","V3",5);
        result.add(dist);


        dist = new Distance("V4","V6",10);
        result.add(dist);
        dist = new Distance("V5","V4",20);
        result.add(dist);
        dist = new Distance("V5","V6",60);
        result.add(dist);


        return result;
    }
}
