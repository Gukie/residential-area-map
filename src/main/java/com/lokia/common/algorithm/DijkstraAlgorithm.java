package com.lokia.common.algorithm;

import com.lokia.common.algorithm.dijkstra.Distance;
import com.lokia.common.algorithm.dijkstra.Node;

import java.util.*;

public class DijkstraAlgorithm {

    /**
     * refer: https://blog.csdn.net/qq_35644234/article/details/60870719
     * <li>1. 构建源节点到其他所有节点的距离，不可达的化，取Integer.MAX_VALUE</>
     * <li>2. 获取距离源节点(S)最近的那个节点，并以他(M)为出发点，找到他的下一个节点(N)，然后将<S,N>的距离值取 <S,N>, <S,M>+<M,N> 中的较小值. 这一步可以保证当前计算出来的距离是最短的.</li>
     * <li>3. 将刚才计算过的节点放入一个临时集合中，以后不再参与 离源节点最近的节点的计算</li>
     * <li>4. 重复以上步骤，直到所有的节点的距离都计算过了</li>
     *
     * @param distanceSet
     * @param source
     * @return
     */
    public static List<Distance> getShortestDistance(Set<Distance> distanceSet, Node source) {

        if (distanceSet == null || distanceSet.isEmpty()) {
            return new ArrayList<>();
        }
        if (source == null) {
            return new ArrayList<>();
        }

        List<Distance> result = new ArrayList<>();
        String sourceName = source.getName();

        Map<String, Distance> sourceDestNodeDistanceMap = new HashMap<>();
        for (Distance distance : distanceSet) {
            if (Objects.equals(sourceName, distance.getFrom())) {
                String destNodeName = distance.getTo();
                String sourceDestKey = getSourceDestMapKey(sourceName, destNodeName);
                sourceDestNodeDistanceMap.put(sourceDestKey, distance);
            } else {

                // 源节点
                String from = distance.getFrom();
                String sourceDestKey = getSourceDestMapKey(sourceName, from);
                Distance distance1;
                if (!sourceDestNodeDistanceMap.containsKey(sourceDestKey)) {
                    distance1 = generateDistanceWithMaxVal(sourceName, from);
                    if (distance1 != null) {
                        sourceDestNodeDistanceMap.put(sourceDestKey, distance1);
                    }
                }

                // 目标节点
                String to = distance.getTo();
                sourceDestKey = getSourceDestMapKey(sourceName, to);
                if (!sourceDestNodeDistanceMap.containsKey(sourceDestKey)) {
                    distance1 = generateDistanceWithMaxVal(sourceName, to);
                    if (distance1 != null) {
                        sourceDestNodeDistanceMap.put(sourceDestKey, distance1);
                    }
                }
            }
        }

        // 将数据放到result中去
        result.addAll(sourceDestNodeDistanceMap.values());


        // 找到每一次的最小距离的节点(记作M)，它的下一个节点(记作N)，源节点记作S，然后将<S,N>的距离值取 <S,N>, <S,M>+<M,N> 中的较小值;
        Set<String> alreadyCalculatedNode = new HashSet<>();
        Distance minDistance = getMinDistance(result, alreadyCalculatedNode);
        while (minDistance != null) {
            alreadyCalculatedNode.add(minDistance.getTo());

            // 以最小距离的node为出发点，计算它辐射的其他节点的距离
            String minDistanceNode = minDistance.getTo();
            for (Distance item : distanceSet) {
                String currNodeName = item.getFrom();
                if (Objects.equals(minDistanceNode, currNodeName)) {

                    // 源节点到当前节点的距离+当前节点到下一个节点的距离： <S,M>+<M,N>
                    String key = getSourceDestMapKey(sourceName, currNodeName);
                    Distance source2NodeDistance = sourceDestNodeDistanceMap.get(key);
                    int calculatedDistance = source2NodeDistance.getDistance() + item.getDistance();

                    // 源节点到当前节点的下一节点的距离： <S,N>
                    String next = item.getTo();
                    key = getSourceDestMapKey(sourceName, next);
                    Distance source2NextDistance = sourceDestNodeDistanceMap.get(key);

                    if (source2NextDistance.getDistance() > calculatedDistance) {
                        source2NextDistance.setDistance(calculatedDistance);

                        String path = source2NodeDistance.getPath()+"->"+item.getTo();
                        source2NextDistance.setPath(path);
                    }
                }
            }

            minDistance = getMinDistance(result, alreadyCalculatedNode);
        }

        return result;
    }

    private static Distance generateDistanceWithMaxVal(String from, String to) {
        Distance distance1 = new Distance();
        distance1.setFrom(from);
        distance1.setTo(to);
        distance1.setDistance(Integer.MAX_VALUE);
        return distance1;
    }

    private static String getSourceDestMapKey(String source, String dest) {
        return source + "-" + dest;
    }

    /**
     * 获取当前节点集合中，距离的最小值.（排除掉已经计算好的节点）
     *
     * @param result
     * @param alreadyCalculatedNode
     * @return
     */
    private static Distance getMinDistance(List<Distance> result, Set<String> alreadyCalculatedNode) {
        int minDistanceVal = Integer.MAX_VALUE;
        Distance minDistance = null;
        for (Distance distance : result) {
            if (alreadyCalculatedNode.contains(distance.getTo())) {
                continue;
            }
            if (distance.getDistance() < minDistanceVal) {
                minDistanceVal = distance.getDistance();
                minDistance = distance;
            }
        }
        return minDistance;
    }
}
