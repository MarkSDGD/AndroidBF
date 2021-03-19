package com.sdsjt.model.response;

import java.util.List;



public class SearchRecommendBean {


    /**
     * data : {"ab_fields":null,"hot_words":[],"suggest_word_list":[{"type":"hist","word":"军工航天"},{"type":"hist","word":"二本大学"},{"type":"hist","word":"来新闻坊做小记者吧"},{"type":"hist","word":"金晨"},{"type":"recom","word":"二本的医科大学"},{"type":"recom","word":"薛之谦快乐大本营"},{"type":"recom","word":"江西南城"},{"type":"recom","word":"萌妃驾到大结局"},{"type":"recom","word":"vivo和oppo"},{"type":"recom","word":"最好二本大学"},{"type":"recom","word":"上海虹口"},{"type":"recom","word":"vivo新机"},{"type":"recom","word":"军工第一龙头股"},{"type":"recom","word":"二本医科大学"},{"type":"recom","word":"华西村现状吴协恩"},{"type":"recom","word":"金晨男友"},{"type":"recom","word":"军工龙头股"},{"type":"recom","word":"华西村现状"},{"type":"recom","word":"兰州交通大学"},{"type":"recom","word":"萌妃驾到"}]}
     * message : success
     */

    private DataBean data;
    private String message;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * ab_fields : null
         * hot_words : []
         * suggest_word_list : [{"type":"hist","word":"军工航天"},{"type":"hist","word":"二本大学"},{"type":"hist","word":"来新闻坊做小记者吧"},{"type":"hist","word":"金晨"},{"type":"recom","word":"二本的医科大学"},{"type":"recom","word":"薛之谦快乐大本营"},{"type":"recom","word":"江西南城"},{"type":"recom","word":"萌妃驾到大结局"},{"type":"recom","word":"vivo和oppo"},{"type":"recom","word":"最好二本大学"},{"type":"recom","word":"上海虹口"},{"type":"recom","word":"vivo新机"},{"type":"recom","word":"军工第一龙头股"},{"type":"recom","word":"二本医科大学"},{"type":"recom","word":"华西村现状吴协恩"},{"type":"recom","word":"金晨男友"},{"type":"recom","word":"军工龙头股"},{"type":"recom","word":"华西村现状"},{"type":"recom","word":"兰州交通大学"},{"type":"recom","word":"萌妃驾到"}]
         */

        private Object ab_fields;
        private List<?> hot_words;
        private List<SuggestWordListBean> suggest_word_list;

        public Object getAb_fields() {
            return ab_fields;
        }

        public void setAb_fields(Object ab_fields) {
            this.ab_fields = ab_fields;
        }

        public List<?> getHot_words() {
            return hot_words;
        }

        public void setHot_words(List<?> hot_words) {
            this.hot_words = hot_words;
        }

        public List<SuggestWordListBean> getSuggest_word_list() {
            return suggest_word_list;
        }

        public void setSuggest_word_list(List<SuggestWordListBean> suggest_word_list) {
            this.suggest_word_list = suggest_word_list;
        }

        public static class SuggestWordListBean {
            /**
             * type : hist
             * word : 军工航天
             */

            private String type;
            private String word;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getWord() {
                return word;
            }

            public void setWord(String word) {
                this.word = word;
            }
        }
    }
}
