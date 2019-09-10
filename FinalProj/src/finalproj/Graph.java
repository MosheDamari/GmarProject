/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproj;

//import com.sun.istack.internal.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Comparator;
import java.util.Objects;

/**
 *
 *
 */
public class Graph
{
    private int nDiscoverCost;
    private List<Node> lstNodes;
    private List<Edge> lstEdges;

    public Graph(int nDiscoverCost, List<Node> nodes, List<Edge> edges)
    {
        this.nDiscoverCost = nDiscoverCost;
        this.lstNodes = nodes;
        this.lstEdges = edges;
    }

    public Graph(Graph g)
    {
        this.nDiscoverCost = g.nDiscoverCost;
        this.lstNodes = new ArrayList<>(g.lstNodes);
        this.lstEdges = new ArrayList<>(g.lstEdges);
    }

    public int getDiscoverCost(){ return this.nDiscoverCost; }

    public List<Node> getNodes() { return this.lstNodes; }

    public List<Edge> getEdges() { return this.lstEdges; }
    
    public void printGraph()
    {
        for(int i = 0; i < lstEdges.size(); i++)
        {
            System.out.println(lstEdges.get(i).getNode1().getId() + " " + lstEdges.get(i).getNode2().getId() + " " + lstEdges.get(i).getSlot());
        }
    }
    
    public Node getNodeById(int id)
    {
        return this.lstNodes.stream().filter(o -> o.getId() == id).findFirst().orElse(null);
    }

    public void removeEdge(Edge delEdge)
    {
        this.lstEdges.remove(delEdge);
        this.getNodeById(delEdge.getNode1().getId()).removeEdge(delEdge);
        this.getNodeById(delEdge.getNode2().getId()).removeEdge(delEdge);
    }


    public List<Edge> getEdgesBetweenNodes(Node n1, Node n2)
    {
        List<Edge> lst = new ArrayList<Edge>();

        for (Edge e : this.lstEdges)
        {
            if ((e.getNode1() == n1 &&
                 e.getNode2() == n2) ||
                (e.getNode1() == n2 &&
                 e.getNode2() == n1))
            {
                lst.add(e);
            }
        }

        return lst;
    }

    // Check if we can route this path
    public List<Edge> checkRoute(HashMap<Integer, Integer> hRoute, int nCustBandWidth)
    {
        List<Edge> lstE;
        List<Edge> lstVisitedEdges = new ArrayList<>();

        Comparator<Edge> edgeComparator = Comparator.comparing(Edge::getEdgeCost);

        // for each source in our path
        for (int nCurrNode : hRoute.keySet())
        {
            // get the edges between the source and the destiny nodes and sort them by cost
            lstE = this.getEdgesBetweenNodes(this.getNodeById(nCurrNode), this.getNodeById(hRoute.get(nCurrNode)));
            lstE.sort(edgeComparator);

            // check if we can navigate through each edge that our dijkstra found
            // if not, one of the edges are catched, than return this unavailable edge
            for (int i = 0; i < nCustBandWidth; i++)
            {
                lstVisitedEdges.add(lstE.get(i));

                if (lstE.get(i).getSlot() == 1)
                {
                    this.removeEdge(lstE.get(i));
                    return lstVisitedEdges;
                }
            }
        }

        return null;
    }

    public List<Edge> navigateThroughPath(HashMap<Integer, Integer> hRoute, int nCustBandWidth)
    {
        List<Edge> lstE;
        List<Edge> lstVisitedEdges = new ArrayList<>();

        Comparator<Edge> edgeComparator = Comparator.comparing(Edge::getEdgeCost);

        // for each source in our path
        for (int nCurrNode : hRoute.keySet())
        {
            // get the edges between the source and the destiny nodes and sort them by cost
            lstE = this.getEdgesBetweenNodes(this.getNodeById(nCurrNode), this.getNodeById(hRoute.get(nCurrNode)));
            lstE.sort(edgeComparator);

            // check if we can navigate through each edge that our dijkstra found
            // if not, one of the edges are catched, than return this unavailable edge
            for (int i = 0; i < nCustBandWidth; i++)
            {
                lstVisitedEdges.add(lstE.get(i));
            }
        }

        return lstVisitedEdges;
    }

    // Catch random slots in the graph by percentage
    // parameter percentage is int between 0-1
    public void catchRandomSlots(double percentage)
    {
        List<Node> lstVisitedNodes = new ArrayList<Node>();
        List<Edge> lstRelevantEdges;
        Comparator<Edge> edgeComparator = Comparator.comparing(Edge::getEdgeCost);
        int nNumOfConns = Utilities.getNumOfConnections(this);
        int perc = (int)(percentage * 100);
        List<Double> lstPercentages = Utilities.getRandomPercentages(perc, nNumOfConns);
        int numOfEdgesToCatch;

        // For each node in the graph
        for (Node n : this.lstNodes)
        {
            lstVisitedNodes.add(n);

            // Loop over his neighbors
            for (Node nNeighbor : n.getNeighbors())
            {
                // If this node isn't visited already
                if (!lstVisitedNodes.contains(nNeighbor))
                {
                    // Get the edges between those nodes, sort them by cost and catch some of them
                    // by the percentage parameter
                    lstRelevantEdges = this.getEdgesBetweenNodes(n, nNeighbor);
                    lstRelevantEdges.sort(edgeComparator);
                    numOfEdgesToCatch = (int)(lstRelevantEdges.size() * lstPercentages.remove(0));

                    for (int i = 0; i < numOfEdgesToCatch; i++)
                    {
                        lstRelevantEdges.get(i).setSlot(1);
                    }
                }
            }
        }
    }

    public TreeNode getDecisionTree(int sourceID, int parentId, int targetID, List<Integer> ancestors)
    {
        TreeNode treenode = new TreeNode(sourceID, parentId);
        List<IntPair> children;
        List<Integer> walkThroughNodes = new ArrayList<>();
        TreeNode currentChild;

        // if source equals target, return
        if(sourceID == targetID)
        {
            return treenode;
        }

        for (Integer ancestor : ancestors)
        {
            walkThroughNodes.add(ancestor);
        }

        // Get neighbors of the source with his ancestors
        children = getNeighbors(sourceID, ancestors);
        
        walkThroughNodes.add(sourceID);
        //children.sort(Comparator.naturalOrder());
        for (IntPair child : children)
        {
            boolean bIsFound = false;
            TreeNode trChild = new TreeNode(child.n1, child.n2);
            trChild.setCost(1);

            if (child.n1 == targetID)
            {
                List<IntPair> brothers = new ArrayList<>();

                for (IntPair ipBrothers : children)
                {
                    if (!ipBrothers.equals(child))
                    {
                        brothers.add(ipBrothers);
                    }
                }

                currentChild = getEndTree(child.n1, child.n2, brothers ,walkThroughNodes);
            }
            else
                currentChild = getDecisionTree(child.n1, child.n2, targetID, walkThroughNodes);

            for (IntPair tmpChild: children)
            {
                if (tmpChild.n1 == child.n1 && tmpChild.n2 != child.n2)
                {
                    bIsFound = true;
                }
            }

            if (bIsFound)
            {
                if (this.getNodeById(child.n1).getDiffEdges(this.getNodeById(child.n2)).isEmpty())
                {
                    currentChild.setCost(1);
                    TreeNode trCopy = currentChild.getCopy();
                    trCopy.setCost(1);
                    trChild.AddChild(trCopy);
                }
                else {
                    for (Integer i : this.getNodeById(child.n1).getDiffEdges(this.getNodeById(child.n2)))
                    {
                        TreeNode trCopy = currentChild.getCopy();
                        trCopy.setChance(this.getNodeById(child.n1).getNumOfEdgesByCost(this.getNodeById(child.n2), i));
                        trCopy.setCost(i);
                        trChild.AddChild(trCopy);
                    }
                }
            }
            else
            {
                if (this.getNodeById(child.n1).getDiffEdges(this.getNodeById(sourceID)).isEmpty())
                {
                    for (Integer i : this.getNodeById(child.n1).getDiffEdges(this.getNodeById(child.n2)))
                    {
                        TreeNode trCopy = currentChild.getCopy();
                        trCopy.setChance(this.getNodeById(child.n1).getNumOfEdgesByCost(this.getNodeById(child.n2), i));
                        trCopy.setCost(i);
                        trChild.AddChild(trCopy);
                    }
                }
                else {
                    for (Integer i : this.getNodeById(child.n1).getDiffEdges(this.getNodeById(sourceID)))
                    {
                        TreeNode trCopy = currentChild.getCopy();
                        trCopy.setChance(this.getNodeById(child.n1).getNumOfEdgesByCost(this.getNodeById(sourceID), i));
                        trCopy.setCost(i);
                        trChild.AddChild(trCopy);
                    }
                }
            }

            TreeNode trCopy = trChild.getCopy();
            treenode.AddChild(trCopy);
            treenode.setAncestors(walkThroughNodes);
        }
        
        return treenode;
    }
    
    public List<IntPair> getNeighbors(int source, List<Integer> walkThroughNodes)
    {
        List<IntPair> closestNodes = new ArrayList<>();
        List<Integer> newWalkThroughNodes = new ArrayList<>();
        List<IntPair> recursionResult = new ArrayList<>();
        List<IntPair> totalResults = new ArrayList<>();
        Node ndSource = getNodeById(source);
        boolean isLocated = false;

        // Get the closest nodes by loop over the edges of the source node
        for(int i = 0; i < ndSource.getEdges().size(); i++)
        {
            boolean bIsFound = false;

            if(ndSource.getEdges().get(i).getNode1().getId() != source)
            {
                for (IntPair nPair : closestNodes)
                {
                    if (nPair.n1 == ndSource.getEdges().get(i).getNode1().getId() && nPair.n2 == source)
                    {
                        bIsFound = true;
                    }
                }

                if(!bIsFound)
                    closestNodes.add(new IntPair(ndSource.getEdges().get(i).getNode1().getId(), source));
            }
            else
            {
                for (IntPair nPair : closestNodes)
                {
                    if (nPair.n1 == ndSource.getEdges().get(i).getNode2().getId() && nPair.n2 == source)
                    {
                        bIsFound = true;
                    }
                }

                if (!bIsFound)
                    closestNodes.add(new IntPair(ndSource.getEdges().get(i).getNode2().getId(),source));
            }
        }

        // Check if the nodes we walk through already is none
        if(walkThroughNodes.isEmpty())
        {
            return closestNodes;
        }

        // For every node we've been walk through
        for (Integer walkThroughNode : walkThroughNodes)
        {
            // Check if he is in our closest nodes
            for (IntPair closestNode : closestNodes)
            {
                if(Objects.equals(closestNode.n1, walkThroughNode))
                {
                    isLocated = true;
                }
            }

            // If not, then add it to the new nodes we walk through
            if(isLocated == false)
            {
                newWalkThroughNodes.add(walkThroughNode);
            }
            
            isLocated = false;
        }

        // For each closest node
        for (IntPair closestNode : closestNodes)
        {
            // If we walk through this node
            if(walkThroughNodes.contains(closestNode.n1))
            {
                // get neighbots of this node with the new walked through nodes
                recursionResult = getNeighbors(closestNode.n1, newWalkThroughNodes);
                
                for (IntPair recursionResult1 : recursionResult)
                {
                    totalResults.add(recursionResult1);
                }
            }
        }
        
        for (IntPair closestNode : closestNodes)
        {
            totalResults.add(closestNode);
        }

        List<IntPair> tempResult = new ArrayList<>();

        for(IntPair nResPair : totalResults)
        {
             if (nResPair.n1 != source)
            {
                tempResult.add(nResPair);
            }
        }
        totalResults = tempResult;
//        while(totalResults.contains(source))
//        {
//            totalResults.remove((Integer)source);
//        }

        List<IntPair> tempResult2 = new ArrayList<>();
                
        for (Integer walkThroughNode : walkThroughNodes)
        {
//            while(totalResults.contains(walkThroughNode))
//            {
//                totalResults.remove(walkThroughNode);
//            }
            tempResult2 = new ArrayList<>();

            for(IntPair nResPair : totalResults)
            {
                if (nResPair.n1 != walkThroughNode)
                {
                    tempResult2.add(nResPair);
                }
            }

            totalResults = tempResult2;
        }
        
        return totalResults;
    }

    public TreeNode getEndTree(int sourceId, int parent, List<IntPair> brothers, List<Integer> ancestors)
    {
        //List<Integer>
        TreeNode treenode = new TreeNode(sourceId, parent);
        TreeNode trDone = new TreeNode(-1, 0);

        if (sourceId == -1)
            return treenode;

        treenode.AddChild(trDone);

        for (IntPair ipBrother : brothers)
        {
            List<IntPair> lst = getNeighbors(ipBrother.n1, ancestors);
            List<IntPair> lstTemp = new ArrayList<>();

            for (IntPair ipTemp : lst)
            {
                if (ipTemp.n2 != parent)
                {
                    lstTemp.add(ipTemp);
                    ancestors.add(ipTemp.n2);
                }
            }

            List<IntPair> lstOtherBros = new ArrayList<>();

            for (IntPair ipBrothers : brothers)
            {
                if (!ipBrothers.equals(ipBrother))
                {
                    lstOtherBros.add(ipBrothers);
                }
            }

            lstOtherBros.addAll(lstTemp);

            TreeNode trNin = getEndTree(ipBrother.n1, ipBrother.n2, lstOtherBros, ancestors);
            TreeNode trChild = new TreeNode(ipBrother.n1, ipBrother.n2);
            trChild.setCost(1);

            for (Integer i : this.getNodeById(ipBrother.n1).getDiffEdges(this.getNodeById(ipBrother.n2)))
            {
                TreeNode trCopy = trNin.getCopy();
                trCopy.setChance(this.getNodeById(ipBrother.n1).getNumOfEdgesByCost(this.getNodeById(ipBrother.n2), i));
                trCopy.setCost(i);
                trChild.AddChild(trCopy);
            }

            treenode.AddChild(trChild);
        }

        return treenode;
    }
}


