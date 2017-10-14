package chui.swsd.com.cchui.model;

import com.multilevel.treelist.Node;

import java.io.Serializable;
import java.util.List;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\30 0030.
 */

public class DaFenBean implements Serializable{
    private int id;
    private String title;
    private String content;
    private String peoples;
    private List<Node> nodeList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPeoples() {
        return peoples;
    }

    public void setPeoples(String peoples) {
        this.peoples = peoples;
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }
}
