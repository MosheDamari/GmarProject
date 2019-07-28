/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproj;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Liron Levi
 */
public class TreeNode
{
    private int id;
    private List<TreeNode> children;
    private List<Integer> ancestors;
    
    public TreeNode(int id)
    {
        this.id = id;
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
            tr = new TreeNode(id);
            this.children.add(tr);
        }
    }
    
    public void AddChild(TreeNode treenode)
    {
        if(!hasChild(treenode.getId()))
        {
            this.children.add(treenode);
        }
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
}
