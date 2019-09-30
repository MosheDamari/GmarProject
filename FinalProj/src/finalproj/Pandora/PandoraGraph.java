package Pandora;

import finalproj.Edge;
import finalproj.Graph;
import finalproj.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PandoraGraph extends Graph{
    List<PandoraEdge> edgeList;
    public PandoraGraph(int nDiscoverCost, List<Node> nodes, List<Edge> edges) {
        super(nDiscoverCost, nodes, edges);
        edgeList = new ArrayList<>();
        convertGraphEdgeToPandoraGraphEdge(edges);
        convertGraphNodesToPandoraGraphNode();
    }

    public PandoraGraph(Graph g) {
        super(g);
        edgeList = new ArrayList<>();
        convertGraphEdgeToPandoraGraphEdge(g.getEdges());
        convertGraphNodesToPandoraGraphNode();
    }

    private void convertGraphEdgeToPandoraGraphEdge(List<Edge> edges) {
        HashMap<String,List<Edge>> edgeToNodeMap = new HashMap<>();
        for(Edge e: edges){
            String nodeKey =  e.getNode1().getId() + "," + e.getNode2().getId();
            if(!edgeToNodeMap.containsKey(nodeKey)){
                ArrayList<Edge> tempList = new ArrayList<>();
                tempList.add(e);
                edgeToNodeMap.put(nodeKey,tempList);
            }else{
                edgeToNodeMap.get(nodeKey).add(e);
            }
        }
        for(Map.Entry<String,List<Edge>> entry : edgeToNodeMap.entrySet()){
            HashMap<String, HashMap<Integer,Float>> nEdgeCostToPrecent = new HashMap<>();
            String[] nodeID = entry.getKey().split(",");
            Node node1 = this.getNodeById(Integer.parseInt(nodeID[0].toString()));
            Node node2 = this.getNodeById(Integer.parseInt(nodeID[1].toString()));
            List<Edge> edgesP = entry.getValue();
            HashMap<Integer,Integer> edgesPriceToAmount = new HashMap<>();
            int totalEdges = entry.getValue().size();
            for(Edge e : edgesP){
                if(!edgesPriceToAmount.containsKey(e.getEdgeCost())){
                    edgesPriceToAmount.put(e.getEdgeCost(),1);
                }else {
                    edgesPriceToAmount.put(e.getEdgeCost(),edgesPriceToAmount.get(e.getEdgeCost())+1);
                }
            }
            PandoraEdge pandoraEdge = new PandoraEdge(0,0 , node1, node2);
            for (Map.Entry<Integer,Integer> innerEntry :edgesPriceToAmount.entrySet()) {
                Float precent = (float) innerEntry.getValue() / totalEdges;
                String key = node1.getId() + "," + node2.getId() + "," + innerEntry.getKey();
                HashMap<Integer,Float> tempMap = new HashMap<>();
                tempMap.put(innerEntry.getKey(), precent);
                nEdgeCostToPrecent.put(key , tempMap);
            }
            pandoraEdge.setnEdgeCostToPrecent(nEdgeCostToPrecent);
            edgeList.add(pandoraEdge);
        }
    }
    private void convertGraphNodesToPandoraGraphNode(){
        HashMap<Node,List<Edge>> nodesEdges = new HashMap<>();
        for (Edge pEdge : this.edgeList){
            if(!nodesEdges.containsKey(pEdge.getNode1())){
                ArrayList<Edge> edges = new ArrayList<>();
                edges.add(pEdge);
                nodesEdges.put(pEdge.getNode1(), edges);
            }else{
                nodesEdges.get(pEdge.getNode1()).add(pEdge);
            }
            if(!nodesEdges.containsKey(pEdge.getNode2())){
                ArrayList<Edge> edges = new ArrayList<>();
                edges.add(pEdge);
                nodesEdges.put(pEdge.getNode2(), edges);
            }else {
                nodesEdges.get(pEdge.getNode2()).add(pEdge);
            }

        }
        for (Map.Entry<Node,List<Edge>> entry : nodesEdges.entrySet()) {
            entry.getKey().setLstEdges(entry.getValue());
        }
    }
}
