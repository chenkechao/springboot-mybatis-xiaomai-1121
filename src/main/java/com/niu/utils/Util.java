package com.niu.utils;

import com.niu.enums.ResultEnum;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ami on 2018/11/21.
 */
public class Util {


    /**
     * log4j
     */
    private final static Logger logger = LoggerFactory.getLogger(Util.class);

    /**
     * 随即获取32位ID
     * <p>
     * 2017年8月3日 13:26:45
     * xj
     *
     * @return String
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 将指定数据转换为int型
     *
     * 2017年8月3日 13:28:34
     * xj
     *
     * @param o 指定数据
     * @return int
     */
    public static int getInt(Object o) {
        if (o instanceof BigDecimal) {
            return ((BigDecimal) o).intValue();
        } else if (o instanceof Integer) {
            return ((Integer) o).intValue();
        } else if (o instanceof Float) {
            return ((Float) o).intValue();
        } else if (o instanceof Double) {
            return ((Double) o).intValue();
        }

        throw new RuntimeException("can't parse object to int");
    }

    /**
     * 获取股票得分表(SV_SCORE)当前月份表名
     *
     * @return
     */
    public static String getCurrentScoreTableName() {
        String name = null;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH) + 1;
//        if (month < 10) {
//            name = "SV_SCORE_" + year + "0" + month;
//        } else {
//            name = "SV_SCORE_" + year + "" + month;
//        }
        name = "SV_SCORE_" + year;

        return name;
    }


    /**
     * 获取股票得分表(SV_SCORE)指定格式的日期的月份表名
     * <p>
     * 2017年8月8日 16:25:18
     * xj
     *
     * @param date 指定日期
     * @return String
     */
    public static String getScoreTableNameByTime(String date) {
        String name = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Util.transformStringToDate(date, "yyyy-MM-dd"));
        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH) + 1;
//        if (month < 10) {
//            name = "SV_SCORE_" + year + "0" + month;
//        } else {
//            name = "SV_SCORE_" + year + "" + month;
//        }
        name = "SV_SCORE_" + year;
        return name;
    }

    /**
     * 获取指定时间的最近半年时间月份第一天
     * <p>
     * 2017年8月8日 17:19:10
     * xj
     *
     * @param date 指定日期
     * @return String[]
     */
    public static String[] getHalfYear(Date date) {
        String[] last6Months = new String[6];
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //要先+1,才能把本月的算进去
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        for (int i = 0; i < 6; i++) {
            //逐次往前推1个月
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
            last6Months[5 - i] = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-01";
        }

        return last6Months;
    }

    /**
     * 获取Date类型yyyy-MM-dd格式的当前时间
     *
     * 2017年8月3日 10:56:09
     * xj
     *
     * @return Date
     */
    public static Date getCurrentDate() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(date);
        Date curDay = null;
        try {
            curDay = format.parse(dateStr);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }

        return curDay;
    }

    /**
     * 日期加减
     *
     * @param date       指定日期
     * @param dateFormat 指定日期格式
     * @param year       加减年数
     * @return -1 为日期格式错误
     */
    public static String minusYear(String date, String dateFormat, int year) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        try {
            Calendar calendar = Calendar.getInstance();
            Date now = format.parse(date);
            calendar.setTime(now);
            calendar.add(Calendar.YEAR, year);
            return format.format(calendar.getTime());
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            return "-1";
        }
    }

    /**
     * 日期加减
     *
     * @param date       指定日期
     * @param dateFormat 指定日期格式
     * @param day        加减天数
     * @return    -1 为日期格式错误
     */
    public static String minusDay(String date, String dateFormat, int day) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        try {
            Calendar calendar = Calendar.getInstance();
            Date now = format.parse(date);
            calendar.setTime(now);
            calendar.add(Calendar.DAY_OF_MONTH, day);
            return format.format(calendar.getTime());
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            return "-1";
        }
    }

    /**
     * 日期加减
     *
     * @param date       指定日期
     * @param dateFormat 指定日期格式
     * @param day        加减天数
     * @return null 为日期格式错误
     */
    public static Date minusDate(String date, String dateFormat, int day) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        try {
            Calendar calendar = Calendar.getInstance();
            Date now = format.parse(date);
            calendar.setTime(now);
            calendar.add(Calendar.DAY_OF_MONTH, day);
            return calendar.getTime();
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 将日期字符串转换为Date类型
     * String类型的日期格式和要转化的format格式必须一样
     *
     * @param dateStr    日期字符串
     * @param dateFormat 日期格式  yyyy-MM-dd
     * @return date
     */
    public static Date transformStringToDate(String dateStr, String dateFormat) {
        Date date = null;
        DateFormat format = new SimpleDateFormat(dateFormat);
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }

        return date;
    }

    /**
     * 将Date类型转换为日期字符串
     *
     * @param date       日期字符串
     * @param dateFormat 日期格式
     * @return dateStr
     */
    public static String transformDateToString(Date date, String dateFormat) {
        if (date == null || StringUtils.isEmpty(dateFormat)) {
            return "";
        }
        DateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(date);
    }

    /**
     * 获取某日期区间的所有日期  日期倒序
     *
     * @param startDate  开始日期
     * @param endDate    结束日期
     * @param dateFormat 日期格式
     * @return 区间内所有日期
     */
    public static List<String> getPerDaysByStartAndEndDate(String startDate, String endDate, String dateFormat) {
        DateFormat format = new SimpleDateFormat(dateFormat);
        try {
            Date sDate = format.parse(startDate);
            Date eDate = format.parse(endDate);
            long start = sDate.getTime();
            long end = eDate.getTime();
            if (start > end) {
                return null;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(eDate);
            List<String> res = new ArrayList<String>();
            while (end >= start) {
                res.add(format.format(calendar.getTime()));
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                end = calendar.getTimeInMillis();
            }
            return res;
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取某日期区间的所有12月31日的日期  日期倒序
     *
     * @param startDate 开始日期   yyyy-MM-dd
     * @param endDate   结束日期   yyyy-MM-dd
     * @return 区间内所有日期
     */
    public static List<String> getPerYearByStartAndEndDate(String startDate, String endDate) {
        String start = startDate.substring(0, startDate.indexOf("-"));
        String end = endDate.substring(0, endDate.indexOf("-"));
        int s = Integer.parseInt(start);
        int e = Integer.parseInt(end);
        if (!(end + "-12-31").equals(endDate)) {
            e--;
        }
        List<String> res = new ArrayList<String>();
        while (e >= s) {
            res.add(e + "-12-31");
            e--;
        }
        return res;
    }

    /**
     * 获取某日期区间的年份
     *
     * @param startDate 开始日期   yyyy
     * @param endDate   结束日期   yyyy
     * @return 区间内所有日期
     */
    public static List<String> getYearsByStartAndEndDate(String startDate, String endDate) {
        Integer s = Integer.parseInt(startDate);
        Integer e = Integer.parseInt(endDate);
        List<String> res = new ArrayList<String>();
        while (e >= s) {
            res.add(e.toString());
            e--;
        }
        return res;
    }

    /**
     * 对含有map的list排序
     *
     * @param areaList 原始值
     * @param isDesc   TRUE：从大到小  FALSE：从小到大
     */
    public static void sortListMap(List<Map.Entry<String, Double>> areaList, final boolean isDesc) {
        Collections.sort(areaList, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                int flag = 1;
                if (isDesc) {
                    if (o2.getValue() - o1.getValue() < 0) {
                        flag = -1;
                    }
                } else {
                    if (o2.getValue() - o1.getValue() > 0) {
                        flag = -1;
                    }
                }

                return flag;
            }
        });
    }

    /**
     * 对map中含有String类型的日期key值的list进行排序
     * <p>
     * 2017年9月29日 17:19:09
     * xj
     *
     * @param list   List<Map<String,Object>>,String为日期
     * @param format 日期格式
     * @param isDesc TRUE：从大到小  FALSE：从小到大
     */
    public static void sortListStringDateMap(List list, final String format, final boolean isDesc) {
        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Map<String, Object> o1Map = (Map<String, Object>) o1;
                Map<String, Object> o2Map = (Map<String, Object>) o2;
                String o1Key = "";
                for (String key : o1Map.keySet()) {
                    o1Key = key;
                }
                String o2Key = "";
                for (String key : o2Map.keySet()) {
                    o2Key = key;
                }
                Integer o1K = Integer.valueOf(Util.transformDateToString(Util.transformStringToDate(o1Key, format), "yyyyMMdd"));
                Integer o2K = Integer.valueOf(Util.transformDateToString(Util.transformStringToDate(o2Key, format), "yyyyMMdd"));
                int flag = 1;
                if (isDesc) {
                    if (o2K - o1K < 0) {
                        flag = -1;
                    }
                } else {
                    if (o2K - o1K > 0) {
                        flag = -1;
                    }
                }

                return flag;
            }
        });

    }

    /**
     * null转为空字符串
     *
     * @param obj
     * @return
     */
    public static Object nvl(Object obj) {
        if (null == obj) {
            return "";
        }
        return obj;
    }

    /**
     * @methodName nullTrans
     * @describe 将空值转化成空字符串
     * @author xj
     * @date 2017/10/18 15:49
     * @param obj
     * @return java.lang.Object
     */
    public static String nullTrans(Object obj){
        String nullValue = "null";
        String nuValue = "nu";
        if (null == obj || nullValue.equals(obj) || nuValue.equals(obj)){
            return "";
        }
        return obj.toString();
    }

    /**
     * 数字类型数组转换为以逗号分隔的字符串
     *
     * @param arr
     * @return
     */
    public static String transformArrayToString(Object[] arr) {
        if (null == arr || arr.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]).append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * 字符串类型数组转换为以逗号分隔的字符串, 字符串加引号
     *
     * @param arr
     * @return
     */
    public static String transformArrayToStringQ(Object[] arr) {
        if (null == arr || arr.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append("'").append(arr[i]).append("'").append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * double类型加法，防止失去精度
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double addDouble(Double d1, Double d2) {
        BigDecimal bd1 = new BigDecimal(d1.toString());
        BigDecimal bd2 = new BigDecimal(d2.toString());
        return bd1.add(bd2).doubleValue();
    }

    /**
     * 比较两个double是否相等
     *
     * @param d1
     * @param d2
     * @return  当此 BigDecimal 在数字上小于、等于或大于 val 时，返回 -1、0 或 1。
     */
    public static int compareToDouble(String d1, String d2) {
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        return bd1.compareTo(bd2);
    }

    /**
     * double类型减法，防止失去精度
     *
     * @param d1
     * @param d2
     * @return
     */
    public static Double subtractDouble(Double d1, Double d2) {
        BigDecimal bd1 = new BigDecimal(d1.toString());
        BigDecimal bd2 = new BigDecimal(d2.toString());
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * double 乘法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static Double multiplyDouble(Double d1, Double d2) {
        BigDecimal bd1 = new BigDecimal(d1.toString());
        BigDecimal bd2 = new BigDecimal(d2.toString());
        return bd1.multiply(bd2).doubleValue();
    }

    /**
     * double 除法
     *
     * @param d1
     * @param d2
     * @param scale 四舍五入 小数点位数
     * @return
     */
    public static Double divideDouble(Double d1, Double d2, int scale) {
        //  当然在此之前，你要判断分母是否为0，
        //  为0你可以根据实际需求做相应的处理
        BigDecimal bd1 = new BigDecimal(d1.toString());
        BigDecimal bd2 = new BigDecimal(d2.toString());
        return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 精度，四舍五入
     *
     * @param d1
     * @param scale
     * @return
     */
    public static Double round(Double d1, int scale) {
        if (d1 == null) {
            return null;
        }
        BigDecimal bd1 = new BigDecimal(d1.toString());
        return bd1.setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 转码以提供中文名文件下载支持
     *
     * @param fileName
     * @return
     */
    public static String getAttachFileNameForCn(String fileName) {
        StringBuffer sb = new StringBuffer();
        try {
            sb.append("filename=").append(URLEncoder.encode(fileName, "UTF-8"))
                    .append(";filename*=UTF-8''").append(URLEncoder.encode(fileName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }

        return sb.toString();
    }

    /**
     * 判断项目的架包目录中是否存在upload文件夹、svUpload文件夹
     * 如果存在直接返回svUpload文件夹完整url，如果不存在则创建upload文件夹、svUpload文件夹，
     * 在返回svUpload文件夹url
     * <p>
     * 2017年7月10日 15:16:19
     * xj
     *
     * @return String
     */
    public static String getUploadUrl() {
        String url = JarUrlUtil.getJarUrl();
        url += File.separator + "upload";
        File file2 = new File(url);
        if (!file2.isDirectory()) {
            file2.mkdir();
            url += File.separator + "svUpload";
            File file1 = new File(url);
            if (!file1.isDirectory()) {
                file1.mkdir();
            }
        } else {
            url += File.separator + "svUpload";
            File file1 = new File(url);
            if (!file1.isDirectory()) {
                file1.mkdir();
            }
        }
        return url;
    }

    /**
     * 判断项目的架包目录中是否存在download文件夹,
     * 如果存在则返回download文件夹完成路径，如果不存在，创建download文件夹，在返回其完整路径。
     * <p>
     * 2017年7月21日 10:32:54
     * xj
     *
     * @return String
     */
    public static String getDownloadUrl() {
        String url = JarUrlUtil.getJarUrl();
        url += File.separator + "download";
        File downloadFile = new File(url);
        if (!downloadFile.isDirectory()) {
            downloadFile.mkdir();
        }
        return url;
    }

    /**
     * 判断项目的架包目录中是否存在template文件夹,
     * 如果存在则返回template文件夹完成路径，如果不存在，创建template文件夹，在返回其完整路径。
     * <p>
     * 2017年7月27日 14:58:16
     * xj
     *
     * @return String
     */
    public static String getTemplateUrl() {
        String url = JarUrlUtil.getJarUrl();
        url += File.separator + "template";
        File templateFile = new File(url);
        if (!templateFile.isDirectory()) {
            templateFile.mkdir();
        }
        return url;
    }

    /**
     * 判断当前fileUrl是否存在fileName文件夹,
     * 如果存在则返回fileName文件夹完成路径，如果不存在，创建fileName文件夹，在返回其完整路径。
     * <p>
     * 2017年7月28日 14:59:48
     * xj
     *
     * @param fileUrl 文件所在路径
     * @return String
     */
    public static String getFileUrl(String fileUrl) {
        File file = new File(fileUrl);
        if (!file.isDirectory()) {
            file.mkdir();
        }
        return fileUrl;
    }

    /**
     * 格式化数字
     *
     * @param format
     * @param obj
     * @return
     */
    public static String formatByDecimalFormat(String format, Object obj) {
        if (null != obj && !"".equals(obj)) {
            DecimalFormat decimalFormat = new DecimalFormat(format);
            return decimalFormat.format(obj);
        }
        return "";
    }

    /**
     * 返回一个空表格格式Grid
     * <p>
     * 2017年7月12日 16:12:01
     * xj
     *
     * @param idsList
     * @param hdsList
     * @return
     */
//    public static Grid getNullGrid(List<String> idsList, List<String> hdsList) {
//        Grid grid = new Grid(null, 0);
//        grid.setIds(idsList);
//        grid.setHds(hdsList);
//        grid.setRows(null);
//        return grid;
//    }

    /**
     * 将时间戳转换为时间
     * <p>
     * 2017年7月12日 17:40:43
     * xj
     *
     * @param s 时间戳
     * @return "yyyy-MM-dd HH:mm:ss"
     */
    public static String stampToDate(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        String res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将字Clob转成String类型
     * <p>
     * 2017年8月31日 15:24:48
     * xj
     *
     * @param sc clob对象
     * @return String
     * @throws SQLException
     * @throws IOException
     */
    public static String ClobToString(Clob sc) throws SQLException, IOException {
        String reString = "";
        // 得到流
        Reader is = sc.getCharacterStream();
        BufferedReader br = new BufferedReader(is);
        String s = br.readLine();
        StringBuffer sb = new StringBuffer();
        // 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
        while (s != null) {
            sb.append(s);
            s = br.readLine();
        }
        reString = sb.toString();
        return reString;
    }

    /**
     * 获取传入时间某几年之前的日期
     * <p>
     * 2017年9月1日 10:12:40
     * xj
     *
     * @param date     指定日期
     * @param someYear 某几年
     * @return String
     */
    public static String getLastSomeYear(String date, Integer someYear) {
        Integer lastSomeYear = Integer.parseInt(date.substring(0, 4)) - someYear;
        date = lastSomeYear + date.substring(4);
        return date;
    }

    /**
     * 获取指定dateFormat格式的date，某几个月之前的日期
     * <p>
     * 2017年9月1日 11:15:31
     * xj
     *
     * @param date       传入日期
     * @param dateFormat 日期格式
     * @param someMonth  某几个月
     * @return Date
     */
    public static Date getLastSomeMonth(String date, String dateFormat, Integer someMonth) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Calendar c = Calendar.getInstance();
        c.setTime(Util.transformStringToDate(date, dateFormat));
        //得到前几个月
        c.add(Calendar.MONTH, -(someMonth));
        Date time = Util.transformStringToDate(format.format(c.getTime()), dateFormat);
        return time;
    }

    /**
     * 检查上传文件是否为空
     * <p>
     * 2017年9月4日 13:14:36
     * xj
     *
     * @param request 请求request对象
     * @return MultipartFile
     */
    public static MultipartFile checkFile(HttpServletRequest request) throws ServiceException {
        if (!(request instanceof MultipartHttpServletRequest)) {
            throw new ServiceException(ResultEnum.NO_FILE);
        }
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        if (file.isEmpty()) {
            throw new ServiceException(ResultEnum.NO_FILE);
        }
        return file;
    }

    /**
     * <li>功能描述：时间相减得到天数
     * <p>
     * 2017年9月5日 15:13:36
     * xj
     *
     * @param beginDateStr 开始日期
     * @param endDateStr   结束日期
     * @return long
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate;
        Date endDate;
        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
            day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }

        return day;
    }

    /**
     * 获取指定月份的天数
     * <p>
     * 2017年9月6日 13:11:05
     * xj
     *
     * @param date 指定日期
     * @return Integer
     */
    public static Integer getMonthDays(String date) {
        Calendar a = Calendar.getInstance();
        a.setTime(Util.transformStringToDate(date, "yyyy-MM-dd"));
        //把日期设置为当月第一天
        a.set(Calendar.DATE, 1);
        //日期回滚一天，也就是最后一天
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 获得指定月份的，月末日期
     *
     * @param date 指定日期
     * @return String
     */
    public static String getMonthEndStringDateByMonth(String date, String dateFormat) {
        Calendar c = Calendar.getInstance();
        c.setTime(Util.transformStringToDate(date, dateFormat));
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return transformDateToString(c.getTime(), dateFormat);
    }

    /**
     * 比较日期大小
     * <p>
     * 2017年9月7日 16:15:53
     * xj
     *
     * @param DATE1      第一个时间
     * @param DATE2      第二个时间
     * @param dateFormat 日期格式
     * @return Integer null日期格式有误，1：第一个日期大，0：两个日期一样，-1：第二个日期大
     */
    public static Integer compareDate(String DATE1, String DATE2, String dateFormat) {
        DateFormat df = new SimpleDateFormat(dateFormat);
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return null;
    }

}
