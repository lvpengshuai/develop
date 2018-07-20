package com.trs.model;

import java.util.*;

/**
 * Created by panzhen on 2017/3/9.
 */
public class OntologyMap {
    // public String backgroundColor = "new echarts.graphic.RadialGradient(0.3, 0.3, 0.8, [{offset: 0,color: '#f7f8fa' }, {offset: 1,color: '#cdd0d5'    }])";
    public Map title;
    public Map tooltip;
    // public String toolbox = "{show: true,feature: {dataView: {show: true,readOnly: true},restore: {show: true},saveAsImage: {show: true}}}";
    public List<Map> legend;
    public String animationDuration = "3000";
    public String animationEasingUpdate = "quinticInOut";
    public List<Series> series;

    public OntologyMap() {
        title = new HashMap();
        title.put("text", "知识图谱");
        title.put("top", "top");
        title.put("left", "center");
        tooltip = new HashMap<>();
        series = new ArrayList<>();
        legend = new ArrayList<>();
    }

    public void loadSeriesData(String className, List<Map> returnLink, List<Map> seriesData, List legendData, List<Map> categories) {
        title.put("subtext", className);
        Series oseries = new Series();
        oseries.name = className;
        oseries.links = returnLink;
        oseries.data = seriesData;
        this.series.add(oseries);
        Map legendMap = new HashMap();
        legendMap.put("top", "20");
        //根据栏目树状节点显示控制
        //legendMap.put("data", legendData);
        this.legend.add(legendMap);
        oseries.categories = categories;
    }

    public void setForce(int size) {
        Iterator<Series> iterator = series.iterator();
        while (iterator.hasNext()) {
            Series series = iterator.next();
            series.force.put("repulsion", size);
        }
    }

    public class Series {
        public String name;
        public String type = "graph";
        public String layout = "force";
        public Map force;
        public List<Map> data;
        public List<Map> links;
        public String focusNodeAdjacency = "true";
        public boolean roam = true;
        public Map label;
        public Map lineStyle;
        public List<Map> categories;

        public Series() {
            LableNormal lableNormal = new LableNormal();
            lableNormal.position = "top";
            lableNormal.show = "true";
            label = new HashMap();
            label.put("normal", lableNormal);
            LinkNormal linkNormal = new LinkNormal();
            linkNormal.color = "source";
            linkNormal.type = "solid";
            linkNormal.curveness = 0;
            lineStyle = new HashMap();
            lineStyle.put("normal", linkNormal);
            force = new HashMap();
            force.put("repulsion", 500);

        }
    }

    public class LableNormal {
        public String show = "true";
        public String position = "top";

        public LableNormal() {
            show = "true";
            position = "top";
        }
    }

    public class LinkNormal {
        public String color = "source";
        public int curveness = 0;
        public String type = "solid";

        public LinkNormal() {
            color = "source";
            curveness = 0;
            type = "solid";
        }
    }
}
