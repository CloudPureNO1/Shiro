import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;
public class IDCardBuilder {
    private static String m = "";
    private static Random random = null;
    private static String[] num1 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static String[] num2 = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static String[] num3 = {"1", "3", "5", "7", "9"};
    private static String[] num4 = {"2", "4", "6", "8", "0"};
    private static String[] mm = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private static String[] ddFirst = {"1", "2", "3"};
    private static String[] bgMonth = {"01", "03", "05", "07", "08", "10", "12"};
    private static String[] ltMonth = {"04", "06", "09", "11"};
    private static int[] weight = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};//身份证校验的加权因子
    private static String[] checkCodes = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};//校验码
    /**
     * 省（直辖市）编码，身份证前两位：     * 关于地址码含义的详细解释：     * 身份证前六位是地区代码，我们用ABCDEF表示。 代码的解释规则如下：     * A:国内区域     * 1 华北三省二市     * 2 东北三省     * 3 华东六省一市     * 4 华南六省     * 5 西南四省一市     * 6 西北五省     * 7 台湾     * 8 港澳     * B (或者说是AB，就是前2位):省（直辖市，自治区，特别行政区）代码 按照A划定的分区定义省代码，有直辖市的，直辖市列前，其余按离直辖市的距离排序，没有直辖市的，按离北京的远近排序。具体省（直辖市，自治区，特别行政区）代码如下：     * 11-15 京 津 冀 晋 蒙     * 21-23 辽 吉 黑     * 31-37 沪 苏浙 皖 闽 赣 鲁     * 41-46 豫 鄂 湘 粤 桂 琼     * 50-54 渝 川 贵 云 藏     * 61-65 陕 甘青 宁 新     * 81-82 港 澳     * CD:城市代码 从01开始排，对于直辖市，CD=01表示市辖区，CD=02表示辖县；省的城市代码从省会开始排，比如2101=沈阳 2102=大连…… 只有地级城市有独立的城市代码，县级市没有。     * EF:市辖区、郊区、郊县、县级市代码 如果EF=00，指代这个城市，不特定区县；对于非直辖市，如EF=01，指代市辖区（任意一个区），02开始指代特定的区。 其中： E=0代表市辖区， E=1代表郊区， E=2代表郊县， E=8代表县级市。 对于直辖市，从01开始就依次排区，没有市区和郊区的代码区分。     * @param args
     */

    private static String[] provinceCodes = {"11", "12", "13", "14", "15", "21", "22",
            "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43",
            "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63",
            "64", "65", "71", "81", "82", "91"};
    private static String provinceNO = "11";
    /**
     * CD:城市代码 从01开始排，对于直辖市，CD=01表示市辖区，CD=02表示辖县；     * 省的城市代码从省会开始排，比如2101=沈阳 2102=大连…… 只有地级城市有独立的城市代码，县级市没有。     *     *     *      * EF:市辖区、郊区、郊县、县级市代码 如果EF=00，指代这个城市，不特定区县；     *      * 对于非直辖市，如EF=01，指代市辖区（任意一个区），02开始指代特定的区。     *      * 其中： E=0代表市辖区， E=1代表郊区， E=2代表郊县， E=8代表县级市。     *      * 对于直辖市，从01开始就依次排区，没有市区和郊区的代码区分。
     */

    private static String CityNO = "0101";
    /**
     * 生日期码（YYYYMMDD）     * 表示编码对象出生的年、月、日，其中年份用四位数字表示，年、月、日之间不用分隔符。例如：1981年05月11日就用19810511表示。
     */

    private static String birthdayYY = "1987";
    /**
     * 顺序码（XXX）（身份证第十五位到十七位）     * 是县、区级政府所辖派出所的分配码，     * 每个派出所分配码为10个连续号码，     * 例如“000-009”或“060-069”，     * 其中单数为男性分配码，     * 双数为女性分配码，如遇同年同月同日有两人以上时顺延第二、第三、第四、第五个分配码。     * 如：007的就是个男生，而且和他同年月日生的男生至少有两个，他们的后四位是001*和003*。     * 分配顺序码中“999、998、997、996”四个顺序号分别为男女性百岁以上老人专用的特定编号。
     */

    private static String orderNO = "000";
    /**
     * 关于校验码     * 校验码是通过一系列数学计算得出来的，具体校验的计算方式如下：     * 1. 对前17位数字本体码加权求和 公式为：S = Sum(Ai * Wi), i = 0, ... , 16 其中Ai表示第i位置上的身份证号码数字值，Wi表示第i位置上的加权因子，其各位对应的值依次为： 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2     * 通俗解释：身份证第一位数字*7+第二位*9+第三位*10+第四位*5+第五位*5+第六位*8+第七位*4+第八位*1+第九位*6+第十位*3+十一位*7+十二位*9+十三位*10+十四位*5+十五位*8+十六位*4+十七位*2；计算出总和（用S）表示。     * 2. 以11对计算结果取模 Y = mod(S, 11) 通俗解释：用S除以11，看最后的余数。如果除尽，为0；余数为1，则计为1；最大余数为10；全部数字为0-10共11个数字。（用Y表示）。     * 3. 根据模的值得到对应的校验码     * 对应关系为：     * Y值：    0 1 2 3 4 5 6 7 8 9 10     * 校验码： 1 0 X 9 8 7 6 5 4 3 2     * 通俗解释：余数为0，则校验码为1；依次类推：余数为1，则校验码对应0；以下：2–X；3–9；4–8；5–7；6–6；7–5；8–4；9-3；10-2。     * 如果校验码不符合这个规则，则肯定是假号码。 关于18位身份证号码尾数是“X”的一种解释：因为按照上面的规则，校验码有11个，而不是10个，所以不能用0-9表示。所以如果尾号是10，那么就得用X来代替，因为如果用10做尾号，那么此人的身份证就变成了19位，而19位的号码违反了国家标准，并且我国的计算机应用系统也不承认19位的身份证号码。Ⅹ是罗马数字的10，用X来代替10，可以保证公民的身份证符合国家标准。     * 根据〖中华人民共和国国家标准 GB 11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
     */

    private static String checkCode = "";

    /**
     * 取得数组内的随机数     * @param nums     * @return
     */

    public static String getNO(String[] nums) {
        random = new Random();
        return nums[random.nextInt(nums.length)];

    }

    /**
     * * @param year     * @param month     * @param day     * @param sex  1男 0女     * @return
     */
    public static String getIDCard(String year, String month, String day, String sex) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getNO(provinceCodes));//省份编码 第1-2位
        buffer.append(getNO(num1));//城市(自治区) 第一位  第3位
        buffer.append(getNO(num2));//城市(自治区) 第二为  第4位
        buffer.append(getNO(num1));//县（区） 第一位  第5位
        buffer.append(getNO(num2));//县（区） 第二位  第6位
        if (isBlank(year)) {//出生年 第7-10位
            buffer.append(birthdayYY);
            System.out.println(">>>>>>>>>>没有输入年份>>>>取系统默认的年份：" + birthdayYY);
        } else {
            if (checkLength(year, 4)) {
                buffer.append(year);
            } else {
                System.err.println(">>>>>>>出生年必须为4位数>>>>>>>当前输入年份为:" + year);
            }
        }

        if (isBlank(month)) {//出生月 第11-12位
            m = getNO(mm);
            buffer.append(m);
        } else {
            if (checkLength(month, 2)) {
                System.out.println(">>>>>>>>>>没有输入年份>>>>取系统默认的年份：" + birthdayYY);
                buffer.append(month);
                m = month;
            } else {
                System.err.println(">>>>>>>出生年必须为4位数>>>>>>>当前输入年份为:" + month);
            }
        }

        if (isBlank(day)) {//出生日 第13-14位
            buffer.append(getDD(getNO(ddFirst) + getNO(num1)));
        } else {
            buffer.append(getDD(month + day));
        }
        //顺序码  第15-17 位，基数为男，偶数为女
        if (isBlank(sex)) {
            buffer.append(getNO(num1) + getNO(num1) + getNO(num1));//顺序码
        } else {
            if ("1".equals(sex)) {
                buffer.append(getNO(num1) + getNO(num1) + getNO(num3));//顺序码
            } else if ("0".equals(sex)) {
                buffer.append(getNO(num1) + getNO(num1) + getNO(num4));//顺序码
            } else {
                System.out.println(">>>>>>>>>>>>性别输入错误：男请输入1，女请输入0>>>>>当前输入为：" + sex);
            }
        }
        String idCard17 = buffer.toString();
        int sum = 0;        //校验码 第18位
        for (int i = 0; i < weight.length; i++) {
            if (idCard17.length() != weight.length) {
                System.out.println(">>>>>身份证号码有误！");
            } else {
                sum += Integer.parseInt(String.valueOf(idCard17.charAt(i))) * weight[i];
            }
        }
        int mod = sum % 11;//对11进行模运算，即余数 的Y值：1,2,3,4,5,6,7,8,9,10 看做校验码数组的下标
        checkCode = checkCodes[mod]; //根据Y值取校验码
        buffer.append(checkCode);
        //System.out.println(">>>>>>>>>>>>>>>>>>>>>mod="+mod+">>>>>>>>>>>>>>checkCode="+checkCode);
        return buffer.toString();
    }

    private static String getDD(String dd) {
        if (Arrays.asList(bgMonth).contains(m)) {
            if (Integer.parseInt(dd) > 31) {
                dd = "01";
            }
        } else if (Arrays.asList(ltMonth).contains(m)) {
            if (Integer.parseInt(dd) > 30) {
                dd = "01";
            }
        } else {//2月，就不判断平年闰年了
            if (Integer.parseInt(dd) > 28) {
                dd = "01";
            }
        }
        return dd;
    }

    private static boolean isBlank(String str) {
        if (str == null || "".equals(str.trim()) || "null".endsWith(str)) {
            return true;
        }
        return false;
    }

    private static boolean checkLength(String str, int len) {
        if (str.length() == len) {
            return true;
        }
        return false;
    }

    /**
     * 将所有地址编码保存在一个Hashtable中
     *
     * @return Hashtable 对象
     */

    private static Hashtable<String, String> GetAreaCode() {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /**
     * 与数字对应：比如：
     * 0,1,2,3,4,5,6,7,8,9 对应
     * 零，一，二，三，四，五，六，七，八，九  定义有序数组a
     * 所以，但数字为1时： 则转成的中文为：a[1]  对应数组中的一
     */

    private static String[] ltCchaniseNum = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private static String[] bgCchaniseNum = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private static String[] units = {"", "十", "百", "千", "万", "十万", "百万", "千万", "亿", "十亿", "百亿", "千亿", "万亿", "兆"};
    private static String[] bgUnits = {"", "拾", "佰", "仟", "亿", "兆"};

    /**
     * 转换小数点前面的部分
     *
     * @param sNum
     * @return
     */

    public static String transformInteger(String sNum) {
        String gg = sNum.substring(0, 1);
        StringBuffer buffer = new StringBuffer();
        if (!"-".equals(sNum.substring(0, 1))) {
            char[] cNum = sNum.toCharArray();
            cNum = reversChars(cNum);//逆序
            if (cNum.length > 0) {
                for (int i = 0; i < cNum.length; i++) {
                    if (i > 4) {//个 十 百 千 万 十万 从第5,6开始有相同单位
                        String ulast0 = units[i - 1].substring(units[i - 1].length() - 1);
                        String ulast1 = units[i].substring(units[i].length() - 1);
                        if (ulast0.equals(ulast1)) {//单位结尾相同，高位的单位的最后一个去掉
                            buffer.append(units[i].substring(0, units[i].length() - 1));
                        } else {
                            buffer.append(units[i]);
                        }
                    } else {
                        buffer.append(units[i]);
                    }
                    buffer.append(ltCchaniseNum[Integer.parseInt(String.valueOf(cNum[i]))]);
                }
            } else {
                System.out.println(">>>>>>>>>不是有效的数字");
            }
        } else {//为负数
            buffer.append(transformInteger(sNum.substring(1)));//回调
            buffer.append("负");
        }
        return buffer.toString();
    }

    /**
     * 转换小数部分
     *
     * @param sNum
     * @return
     */
    public static String transformDecimal(String sNum) {
        StringBuffer buffer = new StringBuffer();
        char[] chars = sNum.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            buffer.append(ltCchaniseNum[Integer.parseInt(String.valueOf(chars[i]))]);
        }
        return buffer.toString();
    }

    /**
     * 数字转中文
     *
     * @param s
     * @return
     */

    public static String transform(String s) {
        //小数点
        if ((isBlank(s)) || (s.indexOf(".") != s.lastIndexOf(".")) || (s.indexOf(".") == 0) || (s.lastIndexOf(".") == (s.length() - 1))) {
            System.out.println("数字字符串不合法：不能为空且只能包含一个点号,点号不能在第一位也不能再最后一位");
            return "数字字符串不合法：不能为空且只能包含一个点号,点号不能在第一位也不能再最后一位";
        } else {
            int i = s.indexOf(".");
            String[] sArray = s.split("\\.");//此处不转换的话，不能 识别.
            if (sArray.length == 1) {//没有点号
                return getValueInteger(transformInteger(s));
            } else {
                return getValueInteger(transformInteger(sArray[0])) + "点" + transformDecimal(sArray[1]);
            }
        }
    }


    public static String getValueInteger(String s) {
        return reversChars(s);
    }

    private static String reversChars(String sts) {
        char[] chars = sts.toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = chars.length - 1; i >= 0; i--) {
            stringBuffer.append(chars[i]);
        }
        return stringBuffer.toString();
    }

    private static char[] reversChars(char[] chars) {
        char temp;
        for (int start = 0, end = chars.length - 1; start < end; start++, end--) {
            temp = chars[start];
            chars[start] = chars[end];
            chars[end] = temp;
        }
        return chars;
    }

    public static void main(String[] args) {
        System.out.println("123123".split("\\.").length);
        for (int i = 0; i < 10; i++) {
            System.out.println(getIDCard("1989", null, null, null) + ":姓名" + transform(i + "").replace("十", ""));
        }
        System.out.println(">>-1214343.09887878 转换为中文为：>>>>>" + transform("-1214343.09887878"));
    }
}