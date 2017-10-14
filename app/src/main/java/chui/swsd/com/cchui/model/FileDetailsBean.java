package chui.swsd.com.cchui.model;

import java.util.List;

/**
 * 内容：
 * Created by 关云秀 on 2017\9\22 0022.
 */

public class FileDetailsBean {

    /**
     * id : 4
     * title : 标题
     * time : 2017-09-22 10:15:44
     * files : [{"name":"石刻2.JPG","suffix":"JPG","path":"/upload/1/file/9244f22c-146c-4e59-9f9d-be39b729e859.JPG"},{"name":"丰周瓢饮.jpg","suffix":"jpg","path":"/upload/1/file/5b3d2f77-cb96-43cd-9323-f02a439fa19e.jpg"}]
     */

    private int id;
    private String title;
    private String time;
    private List<FilesBean> files;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<FilesBean> getFiles() {
        return files;
    }

    public void setFiles(List<FilesBean> files) {
        this.files = files;
    }

    public static class FilesBean {
        /**
         * name : 石刻2.JPG
         * suffix : JPG
         * path : /upload/1/file/9244f22c-146c-4e59-9f9d-be39b729e859.JPG
         */

        private String name;
        private String suffix;
        private String path;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
