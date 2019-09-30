package Pandora;

import finalproj.Node;

import java.util.HashMap;
import java.util.LinkedList;

public class DFS {
    public HashMap<String, LinkedList<Node>> getPaths(PandoraGraph g, int start, int end){
        boolean visited[] = new boolean[g.getNodes().size()+1];
        HashMap<String, LinkedList<Node>> returnList = new HashMap<>();
        LinkedList<Node> localPathList = new LinkedList();
        localPathList.add(g.getNodeById(start));
        DFS(g, localPathList, start, end, visited, returnList);
        return returnList;
    }
    public static void DFS(PandoraGraph g, LinkedList<Node> localPathList, int start, int end, boolean visited[], HashMap<String, LinkedList<Node>> returnList){
        visited[start] = true;
        LinkedList<Node> nodes = new LinkedList();
        String str = "";
        if (start == end ) {
            for(Node n : localPathList){
                str = str + n.getId() + ",";
                nodes.add(n);
            }
            returnList.put(str, nodes);
            visited[start] = false;
            return;
        }
        Node node = g.getNodeById(start);
        for (Node n: node.getNeighbors()) {
            if(!visited[n.getId()]){
                localPathList.add(n);
                DFS(g, localPathList, n.getId(), end, visited, returnList);
                localPathList.remove(n);
            }
        }
        visited[start] = false;
    }
}
