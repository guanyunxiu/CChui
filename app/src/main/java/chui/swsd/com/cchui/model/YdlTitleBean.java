package chui.swsd.com.cchui.model;

import java.io.Serializable;
import java.util.List;

/**
 * 内容：
 * Created by 关云秀 on 2017\10\11 0011.
 */

public class YdlTitleBean implements Serializable{

    /**
     * id : 1
     * name : 公司利益
     * bid :
     * base : 0
     * title2s : [{"id":1,"name":"成本意识","bid":"4629a758158b43a39216b16849079927","base":1,"title3s":[],"ft":0},{"id":2,"name":"商业意识","bid":"a8268db5395948698eeb18197ebc4ebe","base":1,"title3s":[],"ft":0}]
     */

    private int id;
    private String name;
    private String bid;
    private int base;
    private List<Title2sBean> title2s;
    private int ft;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public List<Title2sBean> getTitle2s() {
        return title2s;
    }

    public void setTitle2s(List<Title2sBean> title2s) {
        this.title2s = title2s;
    }

    public int getFt() {
        return ft;
    }

    public void setFt(int ft) {
        this.ft = ft;
    }

    public static class Title2sBean implements Serializable{
        /**
         * id : 1
         * name : 成本意识
         * bid : 4629a758158b43a39216b16849079927
         * base : 1
         * title3s : []
         * ft : 0
         */

        private int id;
        private String name;
        private String bid;
        private int base;
        private int ft;
        private List<?> title3s;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }

        public int getBase() {
            return base;
        }

        public void setBase(int base) {
            this.base = base;
        }

        public int getFt() {
            return ft;
        }

        public void setFt(int ft) {
            this.ft = ft;
        }

        public List<?> getTitle3s() {
            return title3s;
        }

        public void setTitle3s(List<?> title3s) {
            this.title3s = title3s;
        }
    }
}
