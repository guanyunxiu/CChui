package chui.swsd.com.cchui.model;

import com.multilevel.treelist.Node;

import java.util.List;

/**
 * 内容：
 * Created by 关云秀 on 2017\10\11 0011.
 */

public class DaFenConBean {

    /**
     * id : 2
     * title : 呵呵呵
     * contents : [{"title":"优秀","strip":["啊啊啊","包不包","踩踩踩","多对多"],"values":10},{"title":"良好","strip":["啊啊啊","包不包","踩踩踩","多对多"],"values":8},{"title":"一般","strip":["啊啊啊","包不包","踩踩踩","多对多"],"values":6},{"title":"差","strip":["啊啊啊","包不包","踩踩踩","多对多"],"values":4}]
     */

    private int id;
    private String title;
    private List<ContentsBean> contents;
    private String describe;
    private int repeat;
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

    public List<ContentsBean> getContents() {
        return contents;
    }

    public void setContents(List<ContentsBean> contents) {
        this.contents = contents;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public static class ContentsBean {
        /**
         * title : 优秀
         * strip : ["啊啊啊","包不包","踩踩踩","多对多"]
         * values : 10
         */

        private String title;
        private int values;
        private List<String> strip;
        private List<Node> nodeList;
        private int ft;
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getValues() {
            return values;
        }

        public void setValues(int values) {
            this.values = values;
        }

        public List<String> getStrip() {
            return strip;
        }

        public void setStrip(List<String> strip) {
            this.strip = strip;
        }

        public List<Node> getNodeList() {
            return nodeList;
        }

        public void setNodeList(List<Node> nodeList) {
            this.nodeList = nodeList;
        }

        public int getFt() {
            return ft;
        }

        public void setFt(int ft) {
            this.ft = ft;
        }
    }
}
