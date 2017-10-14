package com.multilevel.treelist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangke on 2017-1-14.
 */
public class Node<T,B>implements Serializable{

    private String title;
    private int typeFlag;
    /**
     * 传入的实体对象
     */
    public B bean;
    /**
     * 设置开启 关闭的图片
     */
    public int iconExpand=-1, iconNoExpand = -1;

    private String id;
    /**
     * 根节点pId为0
     */
    private String pId ;
    private String rongid;
    private String name;

    /**
     * 当前的级别
     */
    private int level;

    /**
     * 是否展开
     */
    private boolean isExpand = false;

    private int icon = -1;

    /**
     * 下一级的子Node
     */
    private List<Node> children = new ArrayList<>();

    /**
     * 父Node
     */
    private Node parent;
    /**
     * 是否被checked选中
     */
    private boolean isChecked;
    /**
     * 是否为新添加的
     */
    public boolean isNewAdd = true;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public Node() {}

    public Node(String id,String rongid, String pId, String name) {
        super();
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.rongid = rongid;
    }
    public Node(String id, String pId, String name,B bean) {
        super();
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.bean = bean;
    }


    public int getIcon()
    {
        return icon;
    }

    public void setIcon(int icon)
    {
        this.icon = icon;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getpId()
    {
        return pId;
    }

    public void setpId(String pId)
    {
        this.pId = pId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * 是否为跟节点
     *
     * @return
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * 判断父节点是否展开
     *
     * @return
     */
    public boolean isParentExpand() {
        if (parent == null)
            return false;
        return parent.isExpand();
    }

    /**
     * 是否是叶子界点
     *
     * @return
     */
    public boolean isLeaf()
    {
        return children.size() == 0;
    }

    /**
     * 获取level
     */
    public int getLevel() {

        return parent == null ? 0 : parent.getLevel() + 1;
    }

    /**
     * 设置展开
     *
     * @param isExpand
     */
    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (!isExpand) {
            for (Node node : children) {
                node.setExpand(isExpand);
            }
        }
    }


    public String getRongid() {
        return rongid;
    }

    public void setRongid(String rongid) {
        this.rongid = rongid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTypeFlag() {
        return typeFlag;
    }

    public void setTypeFlag(int typeFlag) {
        this.typeFlag = typeFlag;
    }
}
