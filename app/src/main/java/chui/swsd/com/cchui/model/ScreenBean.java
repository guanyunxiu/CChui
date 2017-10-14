package chui.swsd.com.cchui.model;

import com.multilevel.treelist.Node;

import java.util.List;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\22 0022.
 */

public class ScreenBean {
    private int type;
    private String startTime;
    private String endTime;
    private String typeStr;
    List<Node> nodeList;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
