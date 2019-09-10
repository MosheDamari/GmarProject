/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreeNode
{
    private int id;
    private int cost;
    private int parentId;
    private double chance;
    private double totalCost;
    private List<TreeNode> children;
    private List<Integer> ancestors;
    
    public TreeNode(int id, int parentId)
    {
        this.id = id;
        this.parentId = parentId;
        this.children = new ArrayList<>();
        this.ancestors = new ArrayList<>();
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
    public int getCost() {return this.cost;}
    public void setCost(int cost) {this.cost = cost;}
    public void setTotalCost(double totalCost) {this.totalCost = totalCost;}
    public double getTotalCost() {return this.totalCost;}
    public double getChance() {return this.chance;}
    public void setChance(double chance) {this.chance = chance;}
    public int getParentId() { return this.parentId; }
    public List<TreeNode> getChildren()
    {
        return children;
    }

    public void setChildren(List<TreeNode> children)
    {
        this.children = children;
    }
    
    public boolean hasChild(int id)
    {
        for( int i = 0; i < this.children.size(); i++)
        {
            if(this.children.get(i).getId() == id)
            {
                return true;
            }
        }
        
        return false;
    }
    
    public TreeNode getChild(int id)
    {
        for( int i = 0; i < this.children.size(); i++)
        {
            if(this.children.get(i).getId() == id)
            {
                return this.children.get(i);
            }
        }
        
        return null;
    }
    
    public void AddChild(int id)
    {
        TreeNode tr;
        
        if(!hasChild(id))
        {
            tr = new TreeNode(id, this.id);
            this.children.add(tr);
        }
    }
    
    public void AddChild(TreeNode treenode)
    {
            this.children.add(treenode);
    }
    
    public boolean hasAncestor(int id)
    {
        for( int i = 0; i < this.ancestors.size(); i++)
        {
            if(this.ancestors.get(i) == id)
            {
                return true;
            }
        }
        
        return false;
    }
    
    public void AddAncestor(int id)
    {
        if(!hasAncestor(id))
        {
            this.ancestors.add(id);
        }
    }

    public void setAncestors(List<Integer> ancestors)
    {
        this.ancestors = ancestors;
    }
    
    public void printTree()
    {
        System.out.println("" + this.id);
        for (TreeNode child : children)
        {
            child.printTree();
        }
    }

    public TreeNode getCopy()
    {
        TreeNode t1 = new TreeNode(this.id, this.parentId);
        //t1.setChildren(this.children);

        for (TreeNode child : this.children)
        {
            TreeNode copyChild = child.getCopy();
            t1.AddChild(copyChild);
        }

        t1.setAncestors(this.ancestors);
        t1.setCost(this.cost);
        t1.setChance(this.chance);

        return t1;
    }

    // Set the total cost of each node in the tree
    public double setTreeCost(TreeNode tr, boolean isEven, List<IntPair> choicesPath, List<IntPair> path, int sourceId, int targetId)
    {
        // If this is a leave
        if(tr.getId() == -1)
        {
            // Add the giluy cost
            double leafCost = path.size() - 1;

            List<IntPair> cpPath = new ArrayList<>();
            cpPath.addAll(path);

            int parent = -1;
            List<Integer> totalPath = new ArrayList<>();
            for (int i = cpPath.size()-1; i-- > 0;)
            {
                if(parent == -1) {
                    if (cpPath.get(i).n1 == targetId) {
                        parent = cpPath.get(i).n2;
                        totalPath.add(targetId);
                    }
                }
                else if (cpPath.get(i).n1 == parent)
                {
                    totalPath.add(parent);
                    parent = cpPath.get(i).n2;

                    if (cpPath.get(i).n2 == sourceId)
                    {
                        break;
                    }
                }
            }

            Collections.reverse(totalPath);

            int j = 0, currCost;

            for (int i = 0; i < choicesPath.size(); i++)
            {
                if (choicesPath.get(i).n1 == totalPath.get(j))
                {
                    currCost = choicesPath.get(i).n2;

                    if (totalPath.get(j) == targetId) {
                        for (int k = i + 1; k < choicesPath.size(); k++)
                        {
                            if (choicesPath.get(k).n1 == targetId) {
                                currCost = choicesPath.get(k).n2;
                            }
                        }

                        leafCost += currCost;
                        break;
                    }

                    leafCost += currCost;
                    j++;
                }
            }

            // Set the leave total cost and return it to the parent
            tr.setTotalCost(leafCost);
            return leafCost;
        }

        if (isEven) {
            double childCost, minCost = Double.MAX_VALUE;

            for (TreeNode child : tr.getChildren()) {
                List<IntPair> cpChoices = new ArrayList<>();
                cpChoices.addAll(choicesPath);
                List<IntPair> cpPath = new ArrayList<>();
                cpPath.addAll(path);
                cpPath.add(new IntPair(child.getId(), child.getParentId()));
                childCost = setTreeCost(child, !isEven, cpChoices, cpPath, sourceId, targetId);

                if (childCost < minCost)
                    minCost = childCost;
            }

            tr.setTotalCost(minCost);
            return minCost;
        }
        else
        {
            double sumChildCosts = 0;

            for (TreeNode child : tr.getChildren()) {
                List<IntPair> cpChoices = new ArrayList<>();
                cpChoices.addAll(choicesPath);
                cpChoices.add(new IntPair(child.getId(), child.getCost()));
                List<IntPair> cpPath = new ArrayList<>();
                cpPath.addAll(path);
                sumChildCosts += setTreeCost(child, !isEven, cpChoices, cpPath, sourceId, targetId) * child.getChance();
            }

            tr.setTotalCost(sumChildCosts);
            return sumChildCosts;
        }
    }

    public double getBestPathCost(TreeNode tr)
    {
        if (tr.getId() == -1)
        {
            return tr.getTotalCost();
        }

        double minCost = Double.MAX_VALUE;

        for (TreeNode child : tr.getChildren())
        {
            double currCost = getBestPathCost(child);

            if (currCost < minCost)
            {
                minCost = currCost;
            }
        }

        return minCost;
    }
}
