package com.sdsjt.model.response;

import java.util.List;



public class SearchSuggestionBean {

    /**
     * data : [{"keyword":"的士高经典"},{"keyword":"的夏游戏"},{"keyword":"的士高"},{"keyword":"的士高广场舞"},{"keyword":"的士高舞曲"},{"keyword":"的士速递"},{"keyword":"的士速递5"},{"keyword":"的哥"},{"keyword":"的地得的用法小口诀"},{"keyword":"的成长与展望"}]
     * message : success
     */

    private String message;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * keyword : 的士高经典
         */

        private String keyword;

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }
    }
}
