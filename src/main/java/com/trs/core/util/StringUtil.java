package com.trs.core.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String帮助类
 *
 * @author Administrator
 */
public class StringUtil {

    public final static char[] TRSServerReservedChars =
            {'=', '(', ')', '[', ']', '，', '/', '@', '>', '<', '!', '&', '*', '^',
                    '-', '+', '\'', '\\'};
    public final static char[] filterChars =
            {',', '，', ';', '；', '"', '“', '”', '‘', '’', '=', '(', ')', '[', ']',
                    '，', '/', '@', '>', '<', '!', '&', '*', '^', '-', '+', '\'', '\\'};
    // 国标码和区位码转换常量
    static final int GB_SP_DIFF = 160;
    // 存放国标一级汉字不同读音的起始区位码
    static final int[] secPosValueList =
            {1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212, 3472,
                    3635, 3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249,
                    5600};
    // 存放国标一级汉字不同读音的起始区位码对应读音
    static final char[] firstLetter =
            {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'O',
                    'P', 'Q', 'R', 'S', 'T', 'W', 'X', 'Y', 'Z'};
    private final static char[] DIGITS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                    'e', 'f'};
    private final static int DIGITS_0 = '0';
    private final static int DIGITSA9 = 'A' - '9' - 1;
    private final static String[] PRE_ZERO =
            {"", "0", "00", "000", "0000", "00000", "000000", "0000000"};
    public static char[] alphatable =
            {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',

                    'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                    'Y', 'Z'};
    private static boolean[] xlstInvalidChars = new boolean[256];
    /**
     * String2Alpha
     */
    // 字母Z使用了两个标签，这里有２７个值
    // i, u, v都不做声母, 跟随前面的字母
    private static char[] chartable =
            {'啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈', '哈', '击', '喀', '垃', '妈', '拿',
                    '哦', '啪', '期', '然', '撒', '塌', '塌', '塌', '挖', '昔', '压', '匝', '座'};
    private static int[] table = new int[27];

    static {
        for (int i = 0; i < xlstInvalidChars.length; i++) {
            xlstInvalidChars[i] = false;
        }

        // 0-8
        for (int i = 0; i < 9; i++) {
            xlstInvalidChars[i] = true;
        }

        // 11 0B
        xlstInvalidChars[11] = true;

        // 14-31
        for (int i = 14; i < 32; i++) {
            xlstInvalidChars[i] = true;
        }

        xlstInvalidChars[127] = true;
    }

    // 初始化
    static {
        for (int i = 0; i < 27; ++i) {
            table[i] = gbValue(chartable[i]);
        }
    }

    /**
     * 替换4个字节的utf-8编码（此范围编码包含emoji）
     */
    public static String replace4Utf8(String msg){
        return msg.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
    }
    /**
     * 将字符串转换成ascii码
     */
    public static int stringToAscii(String value) {
        int result = 0;
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                result = result + (int) chars[i];
            } else {
                result = result + ((int) chars[i]);
            }
        }
        return result;

    }

    /**
     * 返回异常的堆栈信息
     */
    public static String throwableToString(Throwable t) {
        if (t == null)
            return "";
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * 解析 IP 地址为长整数
     */
    public static long ip2number(String strIP) {
        if (strIP != null) {
            StringTokenizer st = new StringTokenizer(strIP, ".");
            if (st.countTokens() == 4) {
                long lResultIP = 0;
                while (st.hasMoreTokens()) {
                    int iToken = 0;
                    try {
                        iToken = Integer.parseInt(st.nextToken().trim());
                    } catch (Exception ex) {
                        iToken = -1;
                    }
                    if (iToken < 0 || iToken > 255) {
                        return -1L;
                    }
                    lResultIP = lResultIP << 8;
                    lResultIP = lResultIP | iToken;
                }
                return lResultIP;
            }
        }
        return -1L;
    }

    /**
     * 转化16进制字符串为整数
     */
    public static int hex2int(String s) {
        int iResult = 0;
        int iLength = s.length() < 8 ? s.length() : 8;
        int iTemp = 0;
        for (int i = 0; i < iLength; i++) {
            iTemp = s.charAt(i) - DIGITS_0;
            if (iTemp > 9)
                iTemp = iTemp - DIGITSA9;
            iTemp &= 0xF;
            iResult <<= 4;
            iResult |= iTemp;
        }
        return iResult;
    }

    /**
     * 格式化整数为16进制字符串
     */
    public static String int2dec(int i, int digit) {
        String s = String.valueOf(i);
        int l = s.length();
        if (l < digit) {
            return PRE_ZERO[digit - l] + s;
        }
        if (l > digit) {
            return s.substring(0, digit);
        }
        return s;
    }

    /**
     * 将字符串转成成hash值
     *
     * @param key
     * @return
     */
    public static int hashFunc(String key) {
        int arraySize = 11113;          //数组大小一般取质数
        int hashCode = 0;
        for (int i = 0; i < key.length(); i++) {        //从字符串的左边开始计算
            int letterValue = key.charAt(i) - 96;//将获取到的字符串转换成数字，比如a的码值是97，则97-96=1 就代表a的值，同理b=2；
            hashCode = ((hashCode << 5) + letterValue) % arraySize;//防止编码溢出，对每步结果都进行取模运算
        }
        return hashCode;
    }

    /**
     * 格式化整数为16进制字符串
     */
    public static String int2hex(int j) {
        char[] buf = new char[8];
        for (int i = buf.length - 1; i >= 0; i--) {
            buf[i] = DIGITS[j & 0xF];
            j >>>= 4;
        }
        return new String(buf);
    }

    /**
     * 把输入的字节树组，逐字节转化为其16进制的文本描述形式
     *
     * @param buf 输入的字节树组
     * @param off 需要转化的开始位置
     * @param len 需要转化的结束位置
     * @return 转化结果
     */
    public final static String bytesToHex(byte[] buf, int off, int len) {
        char[] out = new char[len * 2];

        for (int i = 0, j = 0; i < len; i++) {
            int a = buf[off++];
            out[j++] = DIGITS[(a >>> 4) & 0X0F];
            out[j++] = DIGITS[a & 0X0F];
        }
        return (new String(out));
    }

    /**
     * 检测字符串是否为null，或者trim()以后的长度是否为0。
     *
     * @author <a href="mailto:myreligion@163.com">liu kaixuan</a>
     * @date 2005-8-13
     */
    public static boolean isEmpty(String s) {
        if (s == null)
            return true;
        if (s.trim().length() == 0)
            return true;
        return false;
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null)
            return true;
        if (obj.toString().trim().length() == 0)
            return true;
        return false;
    }

    public static boolean isInteger(Object obj) {
        try {
            if (obj == null)
                return false;
            else if (obj.equals(""))
                return false;
            Integer.parseInt(obj.toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 把TRS Search检索中关键字的保留字符去掉，免得出现问题。 <br>
     * 在过滤关键词时不把?, % 转意。
     *
     * @param keyword 要处理的关键字
     * @return 如果keyword为null，返回""；否则返回保留字符被转意以后的串。
     * @see #replace4TRS(String, boolean)
     */
    public static String replace4TRS(String keyword) {
        return replace4TRS(keyword, false);
    }

    /**
     * 把TRS Search检索中关键字的特殊字符换成空格，免得出现问题。<br>
     * 关键字中的字符将会转换成空格。
     *
     * @param keyword                 要处理的关键字
     * @param escapeSimilarSearchChar 是否把keyword中的 ?, % 转意。
     * @return 如果keyword为null，返回""；否则返回保留字符被换成空格以后的串。
     */
    public static String removeTRSChars(String keyword,
                                        boolean escapeSimilarSearchChar) {
        if (keyword == null)
            return "";
        StringBuffer bufKeywords = new StringBuffer(
                (int) (keyword.length() * 1.5));

        for (int i = 0; i < keyword.length(); i++) {
            char ch = keyword.charAt(i);
            boolean removed = false;

            for (int j = 0; j < filterChars.length; j++) {
                if (ch == filterChars[j]) {
                    removed = true;
                    break;
                }
            }

            if (!removed && escapeSimilarSearchChar) {
                if (ch == '%') {
                    removed = true;
                } else if (ch == '?') {
                    removed = true;
                }
            }
            if (!removed) {
                bufKeywords.append(ch);
            } else {
                bufKeywords.append(' ');
            }
        }

        return bufKeywords.toString();
    }

    /**
     * 把TRS Search检索中关键字的保留字符去掉，免得出现问题。
     *
     * @param keyword                 要处理的关键字
     * @param escapeSimilarSearchChar 是否把keyword中的 ?, % 转意。
     * @return 如果keyword为null，返回""；否则返回保留字符被转意以后的串。
     */
    public static String replace4TRS(String keyword,
                                     boolean escapeSimilarSearchChar) {
        if (keyword == null)
            return "";
        StringBuffer bufKeywords = new StringBuffer(
                (int) (keyword.length() * 1.5));

        for (int i = 0; i < keyword.length(); i++) {
            char ch = keyword.charAt(i);
            boolean escaped = false;

            for (int j = 0; j < TRSServerReservedChars.length; j++) {
                if (ch == TRSServerReservedChars[j]) {
                    bufKeywords.append("\\");
                    escaped = true;
                    break;
                }
            }

            if (!escaped && escapeSimilarSearchChar) {
                if (ch == '%') {
                    bufKeywords.append("\\");
                } else if (ch == '?') {
                    bufKeywords.append("\\");
                }
            }

            bufKeywords.append(ch);
        }

        return bufKeywords.toString();
    }

    /**
     * 删掉xlst不支持的字符，然后返回原文
     */
    public static String getXSLTFreeText(String text) {
        if (isEmpty(text))
            return null;

        StringBuffer sb = new StringBuffer(text.length() * 2);
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c > 127) {
                sb.append(c);
            } else {
                if (!xlstInvalidChars[c]) {
                    sb.append(c);
                }
            }
        }

        return sb.toString();
    }

    /**
     * 将URL地址中给定的一个参数去掉。
     *
     * @param queryString 将要处理的URL地址
     * @param toescape    要删掉的参数名称。
     */
    public static String getSubQueryString(String queryString, String toescape) {
        // String queryString = request.getQueryString() ;
        if (queryString == null)
            return "";

        int pos = queryString.indexOf(toescape);
        if (pos < 0)
            return queryString; // 不存在需要的数

        StringBuffer sb = new StringBuffer(128);
        int total = queryString.length();

        if (pos > 0) {
            int i = 0;
            while (i < pos) {
                sb.append(queryString.charAt(i));
                i++;
            }
        }

        while (pos < total && queryString.charAt(pos++) != '&') {
            ;
        }
        while (pos < total) {
            sb.append(queryString.charAt(pos));
            pos++;
        }
        int sbl = sb.length();
        if (sbl > 0 && sb.charAt(sbl - 1) == '&') {
            sb.setLength(sbl - 1);
        }

        return sb.toString();
    }

    /**
     * 把null变成""<br>
     *
     * @author <a href="mailto:myreligion@163.com">liu kaixuan</a>
     * @date 2003年7月28日, 2003年8月3日
     */
    public static String dealNull(String str) {
        String returnstr = null;
        if (str == null)
            returnstr = "";
        else
            returnstr = str;
        return returnstr;
    }

    /**
     * 字符串替换函数
     *
     * @param str    原始字符串
     * @param substr 要替换的字符
     * @param restr  替换后的字符
     * @return 替换完成的字符串
     * @author <a href="mailto:myreligion@163.com">liu kaixuan</a>
     * @date 2003年7月28日, 2003年8月3日
     */
    public static String replaceString(String str, String substr, String restr) {
        String[] tmp = splitString(str, substr);
        String returnstr = null;
        if (tmp.length != 0) {
            returnstr = tmp[0];
            for (int i = 0; i < tmp.length - 1; i++)
                returnstr = dealNull(returnstr) + restr + tmp[i + 1];
        }
        return dealNull(returnstr);
    }

    /**
     * 分割字串，一般情况下客户端要考虑把返回字符数组中每个字符串进行trim()<br>
     * 本方法本身不会添加空格，不过也不会把前后空格删除。
     *
     * @param toSplit   原始字符串
     * @param delimiter 分割字符串
     * @return 字符串数组
     * @author <a href="mailto:myreligion@163.com">liu kaixuan</a>
     * @date 2003年7月28日, 2003年8月3日
     */
    public static String[] splitString(String toSplit, String delimiter) {
        if (toSplit == null)
            return new String[0];

        int arynum = 0, intIdx = 0, intIdex = 0, div_length = delimiter
                .length();
        if (toSplit.compareTo("") != 0) {
            if (toSplit.indexOf(delimiter) != -1) {
                intIdx = toSplit.indexOf(delimiter);
                for (int intCount = 1; ; intCount++) {
                    if (toSplit.indexOf(delimiter, intIdx + div_length) != -1) {
                        intIdx = toSplit
                                .indexOf(delimiter, intIdx + div_length);
                        arynum = intCount;
                    } else {
                        arynum += 2;
                        break;
                    }
                }
            } else
                arynum = 1;
        } else
            arynum = 0;

        intIdx = 0;
        intIdex = 0;
        String[] returnStr = new String[arynum];

        if (toSplit.compareTo("") != 0) {
            if (toSplit.indexOf(delimiter) != -1) {
                intIdx = (int) toSplit.indexOf(delimiter);
                returnStr[0] = (String) toSplit.substring(0, intIdx);
                for (int intCount = 1; ; intCount++) {
                    if (toSplit.indexOf(delimiter, intIdx + div_length) != -1) {
                        intIdex = (int) toSplit.indexOf(delimiter, intIdx
                                + div_length);
                        returnStr[intCount] = (String) toSplit.substring(intIdx
                                + div_length, intIdex);
                        intIdx = (int) toSplit.indexOf(delimiter, intIdx
                                + div_length);
                    } else {
                        returnStr[intCount] = (String) toSplit.substring(intIdx
                                + div_length, toSplit.length());
                        break;
                    }
                }
            } else {
                returnStr[0] = (String) toSplit.substring(0, toSplit.length());
                return returnStr;
            }
        } else {
            return returnStr;
        }
        return returnStr;
    }

    /**
     * 删除字符串中的多余空格。并且把字符串的前后空格删掉。
     * <p>
     * <pre>
     *       例如把&quot;     &quot;变成&quot; &quot;，把制表符'\t'变成&quot; &quot;;
     * </pre>
     *
     * @author <a href="mailto:liu.kaixuan@trs.com.cn">liu kaixuan</a>
     * @date 2005-8-11
     */
    public static String squeezeWhiteSpace(String str) {
        str = dealNull(str);
        StringBuffer sb = new StringBuffer(str);
        StringBuffer sb2 = new StringBuffer();
        boolean flag = false;

        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            if (c != ' ' && c != '\t') {
                if (flag) {
                    sb2.append(' ');
                    flag = false;
                }
                sb2.append(c);
            } else {
                flag = true;
            }
        }
        return sb2.toString().trim();
    }

    public static int toInt(String s, int defaultValue) {
        try {
            return new Integer(s).intValue();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 把string转换成int
     *
     * @param s 要转换成int的String
     * @return 如果转换失败，返回-1
     */
    public static int toInt(String s) {
        return toInt(s, -1);
    }

    /**
     * 把string数组转换成int数组
     * <p>
     * 要转换成int的String数组
     *
     * @return 如果转换失败，返回null
     */
    public static int[] toIntArray(String[] strArray) {

        if (null == strArray || strArray.length <= 0)
            return null;

        int[] intArray = new int[strArray.length];
        for (int i = 0; i < strArray.length; i++) // 同时封多个版
        {
            int j = 0;
            try {
                j = Integer.valueOf(strArray[i]);
            } catch (Exception e) {
                return null;
            }
            if (j > 0)
                intArray[i] = j;
        }
        return intArray;
    }

    /**
     * 分割字串，合并相同项
     *
     * @param str       输入的字符串
     * @param delimiter 分隔符
     * @return set 分割并合并相同项后的字符串set
     * @author li.wenying
     */
    public static HashSet<String> uniqueSplitString(String str, String delimiter) {
        if (str == null || delimiter == null) {
            return null;
        }
        //
        HashSet<String> set = new HashSet<String>();
        StringTokenizer st = new StringTokenizer(str, delimiter);
        while (st.hasMoreTokens()) {
            String s = st.nextToken();
            set.add(s);
        }
        //
        if (set.size() == 0) {
            return null;
        }
        return set;
    }

    // 主函数,输入字符,得到他的声母,
    // 英文字母返回对应的大写字母
    // 其他非简体汉字返回 '0'

    /**
     * 比较两个字符串数组的差异
     *
     * @param str         待比较的字符串
     * @param originalStr 原字符串
     * @return map 含有删除的和新增的字符串数组
     * @author li.wenying
     */
    public static Map compareStrArray(String str, String originalStr) {
        HashSet<String> addTag = new HashSet<String>();
        HashSet<String> newSet = uniqueSplitString(str, " ");

        String[] tempArray = new String[newSet.size()];
        newSet.toArray(tempArray);

        HashSet<String> originalSet = uniqueSplitString(originalStr, " ");
        for (int n = 0; n < newSet.size(); n++) {
            if (originalSet.contains(tempArray[n]))
                originalSet.remove(tempArray[n]);
            else
                addTag.add(tempArray[n]);
        }

        Map<String, String[]> map = new HashMap<String, String[]>();

        String[] addTags = new String[addTag.size()];
        addTag.toArray(addTags);
        String[] deleteTags = new String[originalSet.size()];
        originalSet.toArray(deleteTags);

        map.put("addTag", addTags);
        map.put("deleteTag", deleteTags);

        return map;
    }

    /**
     * 重新构造用户的输入表达式，把多余的空格删掉。 把无用的字符删掉。
     *
     * @param sw            要处理的字符串
     * @param similarSearch 是否在检索词前后添加%
     */
    public static String formatTRSSearchWord(String sw, boolean similarSearch) {

        sw = StringUtil.replaceString(sw, "\\", "\\\\");
        sw = StringUtil.replaceString(sw, "　", " ");
        sw = StringUtil.replaceString(sw, "'", "\\'");
        sw = StringUtil.replaceString(sw, "%", "\\%");
        sw = StringUtil.replaceString(sw, "?", "\\?");
        sw = StringUtil.squeezeWhiteSpace(sw);

        // TODO: 合并到common包的时候，考虑AND操作符当作一个参数之类的传入。
        if (similarSearch) {
            sw = StringUtil.replaceString(sw, " ", "%' AND '%");
        } else {
            sw = StringUtil.replaceString(sw, " ", "' AND  '");
        }

        sw = StringUtil.replaceString(sw, "<", "");
        sw = StringUtil.replaceString(sw, ">", "");

        if (similarSearch) {
            sw = "('%" + sw + "%')";
        } else {
            sw = "('" + sw + "')";
        }

        return sw;
    }

    public static String cleanTRSSearchInput(String tocheckString) {
        if (tocheckString != null && tocheckString.length() > 0) {
            return tocheckString.replaceAll("<", "").replaceAll(">", "");
        } else {
            return tocheckString;
        }
    }

    private static char Char2Alpha(char ch) {

        if (ch >= 'a' && ch <= 'z')
            return (char) (ch - 'a' + 'A');
        if (ch >= 'A' && ch <= 'Z')
            return ch;

        int gb = gbValue(ch);
        if (gb < table[0])
            return '0';

        int i;
        for (i = 0; i < 26; ++i) {
            if (match(i, gb))
                break;
        }

        if (i >= 26)
            return '0';
        else
            return alphatable[i];
    }

    // 根据一个包含汉字的字符串返回一个汉字拼音首字母的字符串
    public static String String2Alpha(String SourceStr) {
        String Result = "";
        try {

            Result = "" + Char2Alpha(SourceStr.charAt(0));
        } catch (Exception e) {
            Result = "";
        }
        return Result;
    }

    private static boolean match(int i, int gb) {
        if (gb < table[i])
            return false;

        int j = i + 1;

        // 字母Z使用了两个标签
        while (j < 26 && (table[j] == table[i]))
            ++j;

        if (j == 26)
            return gb <= table[j];
        else
            return gb < table[j];

    }

    // 取出汉字的编码
    private static int gbValue(char ch) {
        String str = new String();
        str += ch;
        try {
            byte[] bytes = str.getBytes("GB2312");
            if (bytes.length < 2)
                return 0;
            return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
        } catch (Exception e) {
            return 0;
        }

    }

    public static Set<Integer> String2HashSet(String string, String spiltStr) {
        if (StringUtil.isEmpty(string) || StringUtil.isEmpty(spiltStr))
            return null;
        String[] strArray = string.split(spiltStr);
        Set<Integer> set = new HashSet<Integer>();

        int i = 0;
        for (String str : strArray) {
            try {
                i = Integer.parseInt(str);
                if (i > 0) {
                    set.add(i);
                    i++;
                }
            } catch (Exception e) {
                continue;
            }

        }
        return set;
    }

    /**
     * 将字符串数组转换成以delimit指定的字符分割字符串
     *
     * @param strarr  要转换的数组
     * @param delimit 分隔符
     * @return
     */
    public static String toString(String[] strarr, String delimit) {
        String str = "";
        if (strarr != null) {
            for (int i = 0; i < strarr.length; i++) {
                str = str + delimit + strarr[i];
            }
        }
        if (str.length() > 0) {
            str = str.substring(1);
        }
        return str;
    }

    /**
     * 将以delimit分割的字符串str转换成字符数组
     *
     * @param delimitStr 以delimit指定的符号分割的字符串
     * @param delimit    分隔符
     * @return
     */
    public static String[] toStrArray(String delimitStr, String delimit) {
        if (delimitStr != null && !"".equals(delimitStr)) {
            String[] strarr = delimitStr.split(delimit);
            return strarr;
        }
        return null;
    }

    /**
     * @param s String类型的传入参数
     * @return String类型 如88.00
     */
    public static String parseToRmb(String s) {
        DecimalFormat fmt = new DecimalFormat("##.00");
        double result;
        String outStr = "";
        try {
            result = Double.parseDouble(s);
            outStr = fmt.format(result);
        } catch (Exception e) {
            System.out.println("将金额转换为金额出错!");
            return s;
        }
        return outStr;
    }

    public static String parseToString(Object obj) {
        if (obj != null) {
            return obj.toString().trim();
        }
        return null;
    }

    public static double parseToDouble(Object obj) {
        double result = -1;
        if (obj != null) {
            try {
                result = Double.parseDouble(obj.toString());
            } catch (Exception e) {
                System.out.println("Exception in StringUtil :"
                        + e.getLocalizedMessage());
            }
        }
        return result;
    }

    public static int parseToInt(Object obj) {
        int result = -1;
        if (obj != null) {
            try {
                result = Integer.parseInt(obj.toString());
            } catch (Exception e) {
                System.out.println("Exception in StringUtil :"
                        + e.getLocalizedMessage());
            }
        }
        return result;
    }

    public static Date parseToDate(Object obj) {
        Date date = null;
        if (obj != null) {
            date = DateUtil.stringToDate(obj.toString(), "yyyy-MM-dd");
        }
        return date;
    }

    // null转空字符串
    public static String nullToString(String str) {
        if (!isEmpty(str)) {
            return str;
        } else {
            return "";
        }
    }

    // 获取一个字符串的拼音码
    public static String getFirstLetter(String oriStr) {
        String str = oriStr.toUpperCase();
        StringBuffer buffer = new StringBuffer();
        char ch;
        char[] temp;
        for (int i = 0; i < str.length(); i++) { // 依次处理str中每个字符
            ch = str.charAt(i);
            temp = new char[]
                    {ch};
            byte[] uniCode = new String(temp).getBytes();
            if (uniCode[0] < 128 && uniCode[0] > 0) { // 非汉字
                buffer.append(temp);
            } else {
                buffer.append(convert(uniCode));
            }
        }
        return buffer.toString();
    }

    /**
     * 获取一个汉字的拼音首字母。 GB码两个字节分别减去160，转换成10进制码组合就可以得到区位码
     * 例如汉字“你”的GB码是0xC4/0xE3，分别减去0xA0（160）就是0x24/0x43
     * 0x24转成10进制就是36，0x43是67，那么它的区位码就是3667，在对照表中读音为‘n’
     */

    static char convert(byte[] bytes) {

        char result = '-';
        int secPosValue = 0;
        int i;
        for (i = 0; i < bytes.length; i++) {
            bytes[i] -= GB_SP_DIFF;
        }
        secPosValue = bytes[0] * 100 + bytes[1];
        for (i = 0; i < 23; i++) {
            if (secPosValue >= secPosValueList[i]
                    && secPosValue < secPosValueList[i + 1]) {
                result = firstLetter[i];
                break;
            }
        }
        return result;
    }

    /**
     * 生成指定长度的随机码
     *
     * @param size 长度，过长度小于等于0 ，则默认长度 6
     *             默认最大长度为32，超过32，则按32计
     * @return
     */
    public static String RadomCode(int size) {
        String[] code = new String[]{
                "1", "2", "3", "4", "5", "6", "7", "8",
                "9", "0", "A", "B", "C", "D",
                "E", "F", "G", "H", "I", "J", "K", "L",
                "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"
        };
        Integer length = code.length;
        int random = 0;
        StringBuffer randomStr = new StringBuffer();
        if (size <= 0) size = 6;
        if (size > 32) size = 32;
        for (int i = 0; i < size; i++) {
            random = (int) (Math.random() * length);
            randomStr.append(code[random]);
        }
        return randomStr.toString();
    }

    // OBJECT型null转空字符串
    public static String objNullToString(Object obj) {
        if (obj == null) {
            return "";
        } else if (!isEmpty(obj.toString())) {
            return obj.toString();
        } else {
            return "";
        }
    }

    /**
     * 去除html标签
     *
     * @param inputString
     * @return
     */

    public static String toNoHtml(String inputString) {
        String htmlStr = inputString;
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;

        java.util.regex.Pattern p_html1;
        java.util.regex.Matcher m_html1;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
            String regEx_html = "<[^>]+(?!<sub>)*>"; //定义HTML标签的正则表达式
            String regEx_html1 = "<[^>]+";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); //过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); //过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); //过滤html标签

            p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            m_html1 = p_html1.matcher(htmlStr);
            htmlStr = m_html1.replaceAll(""); //过滤html标签

            textStr = htmlStr;
        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }
        return textStr;//返回文本字符串
    }

    /**
     * 获取汉字串拼音，英文字符不变
     *
     * @param chinese 汉字串
     * @return 汉语拼音
     */

    public static String cn2Spell(String chinese) {
        if (chinese == null || chinese.equals("")) {
            return "";
        } else {
            StringBuffer pybf = new StringBuffer();
            char[] arr = chinese.toCharArray();
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] > 128) {
                    try {
                        String[] str = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                        if (str == null || str.length == 0) {
                            break;
                        }
                        pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);
                    } catch (BadHanyuPinyinOutputFormatCombination e) {
                        break;
                    }
                } else {
                    pybf.append(arr[i]);
                }
            }
            return pybf.toString();
        }
    }

    /**
     * 判断字符串中是否有空格
     *
     * @param str
     * @return
     */
    public static boolean hasBlank(String str) {
        if (str.startsWith(" ") || str.endsWith(" ")) {
            return false;
        } else {
            String s[] = str.split(" +");
            if (s.length == 1) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * 判断是否包含数字
     */
    public static boolean hasDigit(String content) {

        boolean flag = false;

        Pattern p = Pattern.compile(".*\\d+.*");

        Matcher m = p.matcher(content);

        if (m.matches())

            flag = true;

        return flag;

    }
}