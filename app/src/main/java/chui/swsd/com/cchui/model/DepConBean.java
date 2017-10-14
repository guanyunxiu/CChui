package chui.swsd.com.cchui.model;

import java.util.List;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\1 0001.
 */

public class DepConBean {

    /**
     * id : 3
     * name : 开发部
     * userBasis : [{"userid":1,"username":"小红","headimg":"/Beat/upload/6/headimg/e973155b-6e34-4252-ac26-b38f1e03faa9.png","token":"7Tdz2SBZ83KLi68yYWhXLtrTR+j6tDgVYESqfo8vKZVtCHuka3jsGuX8+ZbDtOkAT8vlM0ut14M1h3f61S/G0A==","basis":0,"life":0,"work":0},{"userid":2,"username":"小明","headimg":null,"token":"XMERlGad9xlr4LKI70dzAtrTR+j6tDgVYESqfo8vKZVtCHuka3jsGnGLe0mJiF32IgNW7Zp+lOk1h3f61S/G0A==","basis":0,"life":0,"work":0},{"userid":3,"username":"小一","headimg":null,"token":"KXl3yEj/+oPmw7ABUckBHTqkHxdGdpf6IUnhlfpJL+uqYhkd4weeE7m6EymNW+CAqY0gjyTJ3apXYAI2yQHQ3g==","basis":0,"life":0,"work":0}]
     */

    private int id;

    private String name;
    private List<UserBasisBean> userBasis;

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

    public List<UserBasisBean> getUserBasis() {
        return userBasis;
    }

    public void setUserBasis(List<UserBasisBean> userBasis) {
        this.userBasis = userBasis;
    }

    public static class UserBasisBean {
        /**
         * userid : 1
         * username : 小红
         * headimg : /Beat/upload/6/headimg/e973155b-6e34-4252-ac26-b38f1e03faa9.png
         * token : 7Tdz2SBZ83KLi68yYWhXLtrTR+j6tDgVYESqfo8vKZVtCHuka3jsGuX8+ZbDtOkAT8vlM0ut14M1h3f61S/G0A==
         * basis : 0
         * life : 0
         * work : 0
         */

        private int userid;
        private String username;
        private String headimg;
        private String token;
        private int basis;
        private int life;
        private int work;
        private String rongid;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getBasis() {
            return basis;
        }

        public void setBasis(int basis) {
            this.basis = basis;
        }

        public int getLife() {
            return life;
        }

        public void setLife(int life) {
            this.life = life;
        }

        public int getWork() {
            return work;
        }

        public void setWork(int work) {
            this.work = work;
        }

        public String getRongid() {
            return rongid;
        }

        public void setRongid(String rongid) {
            this.rongid = rongid;
        }
    }
}
