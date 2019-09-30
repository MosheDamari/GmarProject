package Pandora;

import finalproj.Customer;
import finalproj.Edge;
import finalproj.Node;

import java.util.*;


public final class Pandora {

    public static void run(PandoraGraph g, List<Customer> lstC) {
        HashMap<String, LinkedList<Node>> paths;
        double cost = 0;
        Path pathMaker = new Path();
        for(Customer c : lstC){
            String finalPath = "";
            paths = new DFS().getPaths(g,c.getSourceId(),c.getTargetId());
            HashMap<String,HashMap<String, HashMap<Integer,Float>>> test = new HashMap<>();
            for (Map.Entry<String, LinkedList<Node>> entry : paths.entrySet()) {
                LinkedList<Node> backTrace = new LinkedList<>();
                for (Node node : entry.getValue()) {
                    backTrace.addFirst(node);
                }
                test.put(entry.getKey(), getPandoraPriceAndPrecent(entry.getKey(), backTrace));
            }
            finalPath = calculatePandora(g.getDiscoverCost(),test);
            if(finalPath == ""){
                System.out.println("Problem Getting Z");
            }else {
                System.out.println(c.getSourceId() + "->" + c.getTargetId() + "Path: "+ finalPath);
            }
        }
    }
    public static float calculateZ(int discoverCost, LinkedList<Integer> costList, LinkedList<Float> precentList){
        for (Integer integer : costList){
            int index = costList.indexOf(integer);
            float left = 0;
            float right = 0;
            float z = 0;
            while (index >= 0){
                left =+ costList.get(index) * precentList.get(index);
                index--;
            }
            left =+ (float)discoverCost;
            index = costList.indexOf(integer);
            index++;
            while (index < costList.size()){
                right =+ precentList.get(index);
                index++;
            }
            if(right != 0){
                z = left / right;
                if(costList.getFirst() <= z && z <= costList.getLast()){
                    return (float) integer + precentList.get(costList.indexOf(integer));
                }
            }

        }
        return 0;
    }

    public static String  calculatePandora(int discoverCost, HashMap<String,HashMap<String, HashMap<Integer,Float>>> path){
        HashMap<String, HashMap<Integer,Float>> finalPath = new HashMap<>();
        float z = 0;
        for(Map.Entry<String,HashMap<String, HashMap<Integer,Float>>> entry: path.entrySet()){
            String strPath = entry.getKey();
            String fullPath[] = strPath.split(",");
            String myPath = "";
            for (int i = fullPath.length; i > 0; i--){
                if(i-2 > 0) {
                    if( Integer.parseInt(fullPath[i - 2]) <  Integer.parseInt(fullPath[i - 1])){
                        myPath += "[" +  fullPath[i - 2] + "-" + fullPath[i - 1] + "],";
                        myPath += "[" +  fullPath[i - 2] + "-" + fullPath[i - 1] + "]" + ".*-";
                    }else {
                        myPath += "[" +  fullPath[i - 1] + "-" + fullPath[i - 2] + "],";
                        myPath += "[" +  fullPath[i - 1] + "-" + fullPath[i - 2] + "]" + ".*-";
                    }

                }else if(i-2 == 0){
                    if( Integer.parseInt(fullPath[i - 2]) <  Integer.parseInt(fullPath[i - 1])){
                        myPath += fullPath[i - 2] + "," + fullPath[i - 1] + ".*";
                    }else {
                        myPath += fullPath[i - 1] + "," + fullPath[i - 2] + ".*";
                    }
                }
            }
            for(Map.Entry<String, HashMap<Integer,Float>> innerEntry : entry.getValue().entrySet()){
                if(innerEntry.getKey().matches(myPath)){
                    finalPath.put(innerEntry.getKey(), innerEntry .getValue());
                }
            }
        }
        LinkedList<Integer> costList = new LinkedList<>();
        LinkedList<Float> precentList = new LinkedList<>();
        for (Map.Entry<String, HashMap<Integer,Float>> entry : finalPath.entrySet()) {
            for (Map.Entry<Integer,Float> innerEntry : entry.getValue().entrySet()) {
                costList.add(innerEntry.getKey());
                precentList.add(innerEntry.getValue());
            }
        }
        sortLinkedLists(costList, precentList);
        z = calculateZ(discoverCost,costList, precentList);
        if(z != 0) {
            for (Map.Entry<String, HashMap<Integer, Float>> entry : finalPath.entrySet()) {
                for (Map.Entry<Integer, Float> innerEntry : entry.getValue().entrySet()) {
                    if (z == (float) innerEntry.getKey() + innerEntry.getValue()) {
                        return entry.getKey();
                    }
                }
            }
        }
        return "";
    }
    
    public static HashMap<String, HashMap<Integer,Float>> getPandoraPriceAndPrecent(String path , LinkedList<Node> backTrace){
        HashMap<String, HashMap<Integer, Float>> returnSet = new HashMap<>();
        LinkedList<Node> backTraceTemp = backTrace;
        HashMap<String, HashMap<Integer, Float>> tempSetOver = new HashMap<>();
        HashMap<String, HashMap<Integer, Float>> tempSetOver2 = new HashMap<>();
        while(backTraceTemp.peek() !=  null){
            Node node = backTraceTemp.pop();
            String str = "";

            for (Edge edge : node.getEdges()) {
                if (backTraceTemp.peek() !=  null && (backTraceTemp.peek().equals(edge.getNode1()) || backTraceTemp.peek().equals(edge.getNode2()))) {
                    HashMap<String, HashMap<Integer, Float>> CostToPriceSet = ((PandoraEdge) edge).getnEdgeCostToPrecent();
                    if(tempSetOver.isEmpty()) {
                        tempSetOver.putAll(CostToPriceSet);
                        returnSet.putAll(CostToPriceSet);
                    }else {
                        for(Map.Entry<String, HashMap<Integer,Float>>  entry : CostToPriceSet.entrySet()) {
                            for(Map.Entry<Integer, Float> innerEntry : entry.getValue().entrySet()){
                                for(Map.Entry<String, HashMap<Integer,Float>> returnEntry : tempSetOver.entrySet()){
                                    HashMap<Integer ,Float> tempSet = new HashMap<>();
                                    for(Map.Entry<Integer, Float> innerReutrnEntry : returnEntry.getValue().entrySet()){
                                        tempSet.put(innerReutrnEntry.getKey() + innerEntry.getKey(), innerReutrnEntry.getValue() * innerEntry.getValue());
                                    }
                                    str = returnEntry.getKey() + "-" + entry.getKey() ;
                                    returnSet.put(str, tempSet);
                                    tempSetOver2.put(str, tempSet);
                                }
                            }
                        }
                        tempSetOver.putAll(tempSetOver2);
                        tempSetOver2.clear();
                    }
                }
            }
        }

        return returnSet;
    }

    public static void sortLinkedLists(LinkedList<Integer> costList, LinkedList<Float> precentList){
        LinkedList<Float> tempPrecentList = new LinkedList<>();
        LinkedList<Integer> tempCostList = new LinkedList<>();
        int index = 0;
        while (!costList.isEmpty()) {
            Integer inter;
            if(costList.get(index) != null){
                inter = costList.get(index);
                index++;
            }else{
                index++;
                continue;
            }
            boolean small = true;
            for (Integer integer : costList) {
                if (inter > integer) {
                    small = false;
                }
            }
            if (small) {
                tempCostList.add(inter);
                tempPrecentList.add(precentList.get(costList.indexOf(inter)));
                precentList.remove(costList.indexOf(inter));
                costList.remove(inter);
            }
            if(index >= costList.size()){
                index = 0;
            }
        }
        costList.addAll(tempCostList);
        precentList.addAll(tempPrecentList);
    }
}
