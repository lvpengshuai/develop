package com.trs.core.util;

import java.util.*;

import static com.trs.core.util.Util.toInt;

/**
 * Created by Administrator on 2017/8/28.
 */
public class GroupSort {
    public static void main(String[] args) {

        ArrayList<String> list = new ArrayList<String>();
        String [] array = {"中国社会科学院","政治研究所","浙江大学经济学院","广东工业大学","北京大学","国际经济和金融学会","厦门大学国务院发展研究中心","中国社会科学院","国家发展和改革委员会","经济学院","商务部","中央财经大学","经济研究部","美联储","联合国","非政府组织","东盟","联合国","经合组织","亚洲开发银行","外交部","世界银行","亚行","中国社科院","世行","社会科学文献出版社","经济科学出版社","经济管理出版社","中国经济出版社","复旦大学出版社","中国社会科学出版社","西南财经大学出版社","现代出版社","石油工业出版社","中国人民大学","美国空军","兰德公司","联合国统计委员会","世界银行","中国国家统计局","联合国统计司","中国社会科学院","辽宁大学","中央编译局俄罗斯研究中心","博鳌亚洲论坛","经济政治研究中心","渣打银行","中国人民大学国际货币研究所","联合国","上海联合国研究会","韩国贸易协会","复旦大学","企业集团","智利企业集团","美联储","中国人民大学","中国社会科学院","中国人民大学商学院","影子银行","东盟","公司总部","世界银行","中国人民大学","中国社会科学院","上游公司","美联储","对外经济贸易大学","西南财经大学","中国国际贸易研究会","南开大学","中央财经大学","南京大学","外经贸大学","中国人民大学商学院","湖北大学商学院","中国人民大学","社会科学文献出版社","中国与二十国集团","经济科学出版社","二十国集团","上海人民出版社","广西师范大学出版社经济科学出版社","社会科学文献出版社","经济科学出版社","社会科学文献出版社","中国社会科学出版社","中国财政经济出版社","中国人民大学商学院","中国人民大学","北京大学","厦门大学","国家统计局","洪堡大学","统计科学研究所","厦门大学经济学院","联合国","中国统计出版社中国人民银行","中国金融出版社","国家统计局","中国人民银行调查统计司","中华人民共和国国家统计局","渣打银行","中国国际商会","渣打银行（中国）有限公司","中国金融出版社社会科学文献出版社","中国财政经济出版社","上海财经大学出版社","机械工业出版社","武汉大学出版社","中国社会科学出版社","中国商务出版社","社会科学文献出版社","中华人民共和国商务部","经济科学出版社","东盟","上海三联书店","西南财经大学出版社","科学出版社","经济科学出版社","中国环境出版社"
        };
        for (String  i : array) {
            list.add(i);
        }

        List listAsArr = Arrays.asList(array);
        Set set = new HashSet(list);
        String [] rid=(String [])set.toArray(new String[0]);

        List<Map<String, Object >> list1 = new ArrayList<>();
        for(int i=0;i<rid.length;i++){
            Map<String, Object > map = new HashMap<>();
            map.put("value", rid[i]);
            map.put("count", Collections.frequency(list, rid[i]));
            list1.add(map);
        }

        try {
            System.out.println(listSort(list1, "count"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     *
     * @param list
     * @return
     */
    public static List<Map<String, Object>> softList(ArrayList list){
        Set set = new HashSet(list);
        String [] rid=(String [])set.toArray(new String[0]);

        List<Map<String, Object >> countList = new ArrayList<>();
        for(int i=0;i<rid.length;i++){
            Map<String, Object > map = new HashMap<>();
            map.put("value", rid[i]);
            map.put("count", Collections.frequency(list, rid[i]));
            countList.add(map);
        }
//        List<Map<String, Object>> softList = null;
//        try {
//            softList = listSort(countList, "count");
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        return countList;
    }

    /**
     * 设定排序
     * @param resultList
     * @throws Exception
     */
    public static List<Map<String, Object>> listSort(
            List<Map<String, Object>> resultList, final String sortkey)
            throws Exception {
        // 返回的结果集
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");

        Collections.sort(resultList, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                // o1，o2是list中的Map，可以在其内取得值，按其排序，此例为升序，s1和s2是排序字段值
                int s1 = toInt(o1.get(sortkey), 0);
                int s2 = toInt(o2.get(sortkey), 0);
                if (s1 < s2) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        return resultList;
    }

    public static Map getCountSort(List list){
        Set set = new HashSet(list);
        String [] rid=(String [])set.toArray(new String[0]);

        Map<String, Integer> unsortMap = new HashMap<>();
        for (int i = 0; i < rid.length; i++) {

            unsortMap.put(rid[i], Collections.frequency(list, rid[i]));

        }

        Map<String, Integer> result = new LinkedHashMap<>();
        Map<String, Integer> resultRtn = new LinkedHashMap<>();

        unsortMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
        int i = 0;
        for(Map.Entry<String, Integer> entry:result.entrySet()){
            if(++i <= 10){
                resultRtn.put(entry.getKey(),entry.getValue());
            }else{
                break;
            }
        }
        result.clear();
        return resultRtn;
    }
}
