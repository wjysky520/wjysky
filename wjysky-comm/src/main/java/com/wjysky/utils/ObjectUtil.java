package com.wjysky.utils;

import com.wjysky.pojo.DataApi;
import io.netty.buffer.ByteBuf;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjectUtil {

    protected static Logger logger = LoggerFactory.getLogger(ObjectUtil.class);

    public static boolean isEqual(Object a, Object b) {
        return a == null ? b == null : a.equals(b);
    }
    public static boolean isNotEqual(Object a, Object b) {
        return !isEqual(a, b);
    }

    public static String null2EmptyString(Object obj){
        if(obj == null) return "";
        return "" + obj;
    }

    public static boolean isNullOrEmpty(Object obj){
        if(obj==null)return true;
        if(obj.toString().equals(""))return true;
        return  false;
    }

    /*从map复制数据到对象*/
    public static void cloneObjectFromMap(Object obj,Map map) {
        Field[] fields = obj.getClass().getDeclaredFields();// 获得类的方法集合
        for (int i = 0; i < fields.length; i++) {
            if (map.get(fields[i].getName()) != null) {
                String fieldSetMethod="set"+fields[i].getName();
                String fieldTypeName=fields[i].getType().getName();
                Method[] methods=obj.getClass().getDeclaredMethods();
                for(int j=0;j<methods.length;j++){
                    if(methods[j].getName().toLowerCase().equals(fieldSetMethod.toLowerCase())) {
                        try {
                            if (fieldTypeName.equals("int")) {
                                methods[j].invoke(obj, Integer.parseInt(String.valueOf(map.get(fields[i].getName()))));
                            } else if (fieldTypeName.equals("long")) {
                                methods[j].invoke(obj, Long.parseLong(String.valueOf(map.get(fields[i].getName()))));
                            } else if (fieldTypeName.equals("float")) {
                                methods[j].invoke(obj, Float.parseFloat(String.valueOf(map.get(fields[i].getName()))));
                            } else if (fieldTypeName.equals("double")) {
                                methods[j].invoke(obj, Double.parseDouble(String.valueOf(map.get(fields[i].getName()))));
                            } else {
                                methods[j].invoke(obj, map.get(fields[i].getName()));
                            }
                        } catch (IllegalAccessException e) {

                        } catch (InvocationTargetException e) {

                        }
                    }
                }
            }
        }
    }


    public static Map getMapFromObject(Object object) throws Exception {
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        HashMap<Object, Object> map = new HashMap<>();
        for (Field field : fields){
            field.setAccessible(true);
            map.put(field.getName(),field.get(object));
        }
        return map;
    }

    /**
     *
     * @Description: 将一个对象转换为另一个对象
     * @param <T1> 要转换的对象
     * @param <T2> 转换后的类
     * @param orimodel 要转换的对象
     * @param castClass 转换后的类
     * @return T2 转换后的对象
     * @author 王俊元（wangjy@qbm360.com）
     * @date 2017年9月5日 上午10:44:52
     * @version 1.0
     */
    @SuppressWarnings("unchecked")
    public static <T1,T2> T2 convertBean(T1 orimodel, Class<T2> castClass) {
        T2 returnModel = null;
        try {
            returnModel = castClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("创建"+castClass.getName()+"对象失败");
        }
        List<Field> fieldlist = new ArrayList<Field>(); //要转换的字段集合
        while (castClass != null && !castClass.getName().toLowerCase().equals("java.lang.object")) { //循环获取要转换的字段,包括父类的字段
            fieldlist.addAll(Arrays.asList(castClass.getDeclaredFields()));
            castClass = (Class<T2>) castClass.getSuperclass(); //得到父类,然后赋给自己
        }
        for (Field field : fieldlist) {
            PropertyDescriptor getpd = null;
            PropertyDescriptor setpd = null;
            try {
                getpd = new PropertyDescriptor(field.getName(), orimodel.getClass());
                setpd = new PropertyDescriptor(field.getName(), returnModel.getClass());
            } catch (Exception e) {
                continue;
            }
            try {
                Method getMethod = getpd.getReadMethod();
                Object transValue = getMethod.invoke(orimodel);
                Method setMethod = setpd.getWriteMethod();
                setMethod.invoke(returnModel, transValue);
            } catch (Exception e) {
                throw new RuntimeException("cast "+ orimodel.getClass().getName() + "to " + castClass.getName() + " failed", e);
            }
        }
        return returnModel;
    }

    /**
     *
     * @ClassName ObjectUtil
     * @Title map2Bean
     * @Description Map转化Bean
     * @param map Map对象
     * @param obj 实体对象
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2018-12-18 17:48
     * @return java.lang.Object
     **/
    public static Object map2Bean(Map map, Object obj) {
        try {
            BeanUtils.populate(obj, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static final String YEAR = "year"; // 年
    public static final String MONTH = "month"; // 月
    public static final String DAY = "day"; // 日
    public static final String HOURS = "hours"; // 时
    public static final String MINUTES = "minutes"; // 分
    public static final String SECONDS = "seconds"; // 秒

    /**
     *
     * @ClassName ObjectUtil
     * @Title getCustomTime
     * @Description 获取以当前时间为基准的自定义时间（默认yyyy-MM-dd HH:mm:ss）
     * @param timeType 时间类型（year：年，month：月，day：日，hours：时，minutes：分，seconds：秒）
     * @param num 时间差距（正数为当前时间之后，负数为当前时间之前）
     * @param referTime 参照时间（为空或格式不正确时参照时间为当前时间，可以是时间戳，时间格式为：yyyy-MM-dd HH:mm:ss）
     * @param format 时间格式（传入timestamp时返回时间戳字符串，为空或格式不正确时为yyyy-MM-dd HH:mm:ss）
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2018-12-25 14:23
     * @return java.lang.String
     **/
    public static String getCustomTime(String timeType, int num, String referTime, String format) {
        SimpleDateFormat sdf = null;
        Calendar cal = null;
        try {
//			Calendar calendar = Calendar.getInstance();
            try {
                sdf = new SimpleDateFormat(format);
            } catch(Exception e) {
//				System.out.println("时间格式不正确");
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
//			Date date = new Date();
            cal = Calendar.getInstance();
            if (referTime != null && !"".equals(referTime.trim())) {
                Date date = new Date();
                if (Pattern.compile("[0-9]*").matcher(referTime).matches()) { // 如果是时间戳
                    date = new Date(Long.valueOf(referTime + "000"));
                } else { // 如果是时间格式
                    try {
                        date = sdf.parse(referTime);
                    } catch(Exception e) { // 如果格式不正确默认为当前时间
                        date = new Date();
                    }
                }
                cal.setTime(date);
            }
            if (YEAR.equals(timeType)) {
//				date.setYear(date.getYear() + num);
                cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + num);
            } else if (MONTH.equals(timeType)) {
//				date.setMonth(date.getMonth() + num);
                cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + num);
            } else if (DAY.equals(timeType)) {
//				date.setDate(date.getDate() + num);
                cal.set(Calendar.DATE, cal.get(Calendar.DATE) + num);
            } else if (HOURS.equals(timeType)) {
//				date.setHours(date.getHours() + num);
                cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + num);
            } else if (MINUTES.equals(timeType)) {
//				date.setMinutes(date.getMinutes() + num);
                cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + num);
            } else if (SECONDS.equals(timeType)) {
//				date.setSeconds(date.getSeconds() + num);
                cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) + num);
            }
        } catch (Exception e) {
            throw new RuntimeException("计算时间时异常", e);
        }
        if ("timestamp".equals(format)) {
            return ((cal.getTime().getTime() / 1000) + "");
        }
        return sdf.format(cal.getTime());
    }

    /**
     *
     * @ClassName ObjectUtil
     * @Title getCustomTime
     * @Description 获取以当前时间为基准的自定义时间（默认yyyy-MM-dd HH:mm:ss）
     * @param timeType time 时间类型（year：年，month：月，day：日，hours：时，minutes：分，seconds：秒）
     * @param num 时间差距（正数为当前时间之后，负数为当前时间之前）
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2018-12-25 14:31
     * @return java.lang.String
     **/
    public static String getCustomTime(String timeType, int num) {
        return getCustomTime(timeType, num, null, null);
    }
    
    /**
     *
     * @ClassName ObjectUtil
     * @Title getCustomTime
     * @Description 获取以当前时间为基准的自定义时间（可自定义时间格式，默认yyyy-MM-dd HH:mm:ss）
     * @param timeType time 时间类型（year：年，month：月，day：日，hours：时，minutes：分，seconds：秒）
     * @param num 时间差距（正数为当前时间之后，负数为当前时间之前）
     * @param format 时间格式（传入timestamp时返回时间戳字符串，为空或格式不正确时为yyyy-MM-dd HH:mm:ss）
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2018-12-25 14:32
     * @return java.lang.String
     **/
    public static String getCustomTime(String timeType, int num, String format) {
        return getCustomTime(timeType, num, null, format);
    }

    /**
     *
     * @ClassName ObjectUtil
     * @Title getCustomTime
     * @Description 获取以当前时间（可自定义时间格式，默认yyyy-MM-dd HH:mm:ss）
     * @param format 时间格式（传入timestamp时返回时间戳字符串，为空或格式不正确时为yyyy-MM-dd HH:mm:ss）
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2018-12-25 14:34
     * @return java.lang.String
     **/
    public static String getCustomTime(String format) {
        return getCustomTime(SECONDS, 0, null, format);
    }

    public static String getCustomTime() {
        return getCustomTime(SECONDS, 0, null, null);
    }

    /**
     *
     * @ClassName ObjectUtil
     * @Title transBean2Map
     * @Description Bean转Map
     * @param obj Bean对象
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2018-12-25 17:26
     * @return java.util.Map
     **/
    public static Map<String, Object> transBean2Map(Object obj) throws Exception {
        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            // 过滤class属性
            if (!key.equals("class")) {
                // 得到property对应的getter方法
                Method getter = property.getReadMethod();
                Object value = getter.invoke(obj);
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     *
     * @ClassName ObjectUtil
     * @Title getSignStr
     * @Description 获取自定义序排列的预加密字符串
     * @param map Map对象
     * @param isSort 是否进行排序
     * @param sort ASC——正序；DESC——倒序。不区分大小写
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2018-12-25 17:27
     * @return java.lang.String
     **/
    public static String getSignStr(Map map, boolean isSort, String sort) {
        StringBuffer strbuf = new StringBuffer();
        if (isSort) { // 是否排序
            final String sort_dtr = sort;
            Map<String, Object> sortMap = new TreeMap<String, Object>(new Comparator<String>() {
                public int compare(String o1, String o2) {
                    if (sort_dtr == null || "".equals(sort_dtr) || "ASC".equals(sort_dtr.toUpperCase())) { // 正序
                        return o1.compareTo(o2);
                    } else if ("DESC".equals(sort_dtr.toUpperCase())) { // 倒序
                        return o2.compareTo(o1);
                    } else {
                        return 0;
                    }
                }
            });
            sortMap.putAll(map);
            for (String key : sortMap.keySet()) {
                if (map.get(key) != null) {
                    strbuf.append(key).append("=").append(map.get(key)).append("&");
                }
            }
        } else {
            for (Object key : map.keySet()) {
                strbuf.append(key).append("=").append(map.get(key)).append("&");
            }
        }
        return strbuf.substring(0, strbuf.length() - 1).toString();
    }

    /**
     *
     * @ClassName ObjectUtil
     * @Title replaceContent
     * @Description 替换字符串中“{****}”相匹配的内容
     * @param mailTemplate
     * @param parameters
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2019-01-28 14:19
     * @return java.lang.String
     **/
    public static String replaceContent(String mailTemplate, Map<String, Object> parameters) {
        if (mailTemplate == null || parameters == null) {
            return mailTemplate;
        }
        String content = mailTemplate;
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            String regex = "\\{" + entry.getKey() + "\\}";
            String value = entry.getValue() != null ? entry.getValue().toString() : "";
            content = content.replaceAll(regex, value);
        }
        return content;
    }

    /**
     *
     * @ClassName ObjectUtil
     * @Title isNetUrl
     * @Description 判断url是否为网络地址
     * @param url 网络地址
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2019-01-25 11:02
     * @return boolean 若是，返回true，否则，返回false
     **/
    public static boolean isNetUrl(String url) {
        boolean reault = false;
        if (url != null) {
            if (url.toLowerCase().startsWith("http") || url.toLowerCase().startsWith("rtsp") || url.toLowerCase().startsWith("mms")) {
                reault = true;
            }
        }
        return reault;
    }

    /**
     *
     * @ClassName ObjectUtil
     * @Title getUUID
     * @Description 获取UUID
     * @param
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2019-02-22 10:53
     * @return java.lang.String
     **/
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    /**
     *
     * @ClassName ObjectUtil
     * @Title byte2file
     * @Description btye数组转文件
     * @param bytes btye数组
     * @param path 文件路径，包含文件名
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2019-03-05 18:17
     * @return boolean
     **/
    public static File byte2file(byte[] bytes, String path) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            if (in == null) { // 未拉取到网络文件的数据
                return null;
            }
            BufferedImage bi = ImageIO.read(in);

            BufferedImage bufferedImage = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics().drawImage(bi, 0, 0, Color.WHITE, null);

            ImageIO.write(bufferedImage, "jpg", new File(path));
            logger.info("数组转文件成功，保存路径为：" + path);
            return new File(path);
        } catch (Exception e) {
            logger.info("btye数组转文件时异常", e);
        }
        return null;
    }

    public static boolean saveFile(byte[] data, File file) {
        if (!initFile(file)) return false;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.flush();
            fos.close();
            return true;
        } catch (IOException e) {
            logger.error("btye数组转文件异常", e);
            return false;
        }
    }

    public static boolean initFile(File file) {
        if (file == null) return false;
        if (!file.exists()) {
            if (!initDirectory(file.getParent())) return false;
            try {
                if (!file.createNewFile()) {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static boolean initDirectory(String path) {
        if (TextUtils.isEmpty(path)) return false;
        File file = new File(path);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return true;
    }

    public static void inputStream2file(InputStream ins, File file) throws Exception {
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
    }


    /**
     *
     * @ClassName ObjectUtil
     * @Title fileToBase64
     * @Description 文件转Base64
     * @param path 文件路径
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2019-04-19 10:49
     * @return java.lang.String
     **/
    public static  String fileToBase64(String path) {
        String base64 = null;
        InputStream in = null;
        try {
            File file = new File(path);
            in = new FileInputStream(file);
            byte[] bytes=new byte[(int)file.length()];
            in.read(bytes);
            base64 = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }

    /**
     *
     * @ClassName ObjectUtil
     * @Title base64ToFile
     * @Description Base64转文件
     * @param destPath 文件保存的文件夹路径
     * @param fileName 文件名
     * @param base64 文件的Base64内容
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2019-04-19 10:50
     * @return boolean
     **/
    public static boolean base64ToFile(String destPath, String fileName, String base64) {
        File file = null;
        //创建文件目录
        String filePath = destPath;
        File  dir = new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            file = new File(filePath + File.separator + fileName);
            fos = new java.io.FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     *
     * @ClassName ObjectUtil
     * @Title compressPictureByQality
     * @Description 压缩图片质量（目前仅支持JPG）
     * @param file 文件对象
     * @param qality 参数qality是取值0~1范围内  代表压缩的程度
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2019-05-15 15:35
     * @return java.io.File
     **/
    public static File compressPictureByQality(File file, float qality) throws IOException {
        BufferedImage src = null;
        FileOutputStream out = null;
        ImageWriter imgWrier;
        ImageWriteParam imgWriteParams;
        logger.info("开始设定压缩图片参数");
        // 指定写图片的方式为 jpg
        imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();
        imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(
                null);
        // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
        imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        // 这里指定压缩的程度，参数qality是取值0~1范围内，
        imgWriteParams.setCompressionQuality(qality);
        imgWriteParams.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
        ColorModel colorModel = ImageIO.read(file).getColorModel();// ColorModel.getRGBdefault();
        imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(
                colorModel, colorModel.createCompatibleSampleModel(32, 32)));
        logger.info("结束设定压缩图片参数");
        if (!file.exists()) {
            logger.info("Not Found Img File,文件不存在");
            throw new FileNotFoundException("Not Found Img File,文件不存在");
        } else {
            logger.info("图片转换前大小" + file.length() + "字节");
            src = ImageIO.read(file);
            out = new FileOutputStream(file);
            imgWrier.reset();
            // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
            // OutputStream构造
            imgWrier.setOutput(ImageIO.createImageOutputStream(out));
            // 调用write方法，就可以向输入流写图片
            imgWrier.write(null, new IIOImage(src, null, null),
                    imgWriteParams);
            out.flush();
            out.close();
            logger.info("图片转换后大小" + file.length() + "字节");
            return file;
        }
    }

    /**
     *
     * @ClassName ObjectUtil
     * @Title encodeBase64File
     * @Description 获取文件Base64内容
     * @param path 
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2019-05-15 16:41
     * @return java.lang.String
     **/
    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer).replaceAll("\r|\n", "");
    }

    /**
     *
     * @ClassName ObjectUtil
     * @Title replaceBlank
     * @Description 去除回车、换行符、制表符
     * @param str
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2019-06-03 15:30
     * @return java.lang.String
     **/
    public static String replaceBlank(String str) {
        if (str != null) {
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(str);
            str = m.replaceAll("");
        }
        return str;
    }

    public static BufferedImage getFileBufferedImage(String filePath, boolean isDelete) {
        BufferedImage bi = null;
        File file = new File(filePath);
        try {
            if (file.exists()) {
                bi = ImageIO.read(file);
            }
        } catch (Exception e) {
            logger.error("获取文件[" + filePath + "]的BufferedImage时异常", e);
        } finally {
            if (isDelete && file.exists()) {
                file.delete();
            }
        }
        return bi;
    }

    /**
     *
     * @ClassName CameraHelper
     * @Title getFileByteArray
     * @Description 获取网络或本地文件的byte数组
     * @param fileUrl 文件网络地址或本地路径
     * @param isDelete 是否删除本地文件
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2019-01-25 10:37
     * @return byte[]
     **/
    public static byte[] getFileByteArray(String fileUrl, boolean isDelete) {
        try {
            if (ObjectUtil.isNetUrl(fileUrl)) { // 判断url是否为网络地址
                URL url = new URL(fileUrl);
                HttpURLConnection uc = (HttpURLConnection) url.openConnection();
                uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true
                uc.connect();
                InputStream iputstream = uc.getInputStream();
                if (iputstream == null) { // 未拉取到网络文件的数据
                    return null;
                }
                BufferedImage bi = ImageIO.read(iputstream);

                File directory = new File("");// 参数为空
                String projectPath = directory.getCanonicalPath() + File.separator + "bak" + File.separator; // 上传文件保存路径
                if (!new File(projectPath).exists()) {
                    logger.info("文件夹[" + projectPath + "]不存在，开始创建");
                    new File(projectPath).mkdirs();
                }
                String filePath = projectPath + ObjectUtil.getUUID() + ".jpg";
                logger.info("将图片复制到本地：" + filePath);
                BufferedImage bufferedImage = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);
                bufferedImage.createGraphics().drawImage(bi, 0, 0, Color.WHITE, null);

                ImageIO.write(bufferedImage, "jpg", new File(filePath));

                if (bi != null) {
                    bi.flush();
                }
                if (iputstream != null) {
                    iputstream.close();
                }
                return getFileByteArray(filePath, isDelete);
//                ByteArrayOutputStream output = new ByteArrayOutputStream();
//                byte[] buffer = new byte[1024 * 4];
//                int n = 0;
//                while (-1 != (n = iputstream.read(buffer))) {
//                    output.write(buffer, 0, n);
//                }
//
//                return output.toByteArray();
            } else { // 不是网络文件的都当做本地文件处理
                File file = new File(fileUrl);
                if (!file.exists()) { // 如果文件不存在
                    logger.info("文件[" + fileUrl + "]不存在");
                    return null;
                }

                FileInputStream fis = new FileInputStream(file);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[1000];
                int n;
                while ((n = fis.read(b)) != -1) {
                    bos.write(b, 0, n);
                }
                if (fis != null) {
                    fis.close();
                }
                if (bos != null) {
                    bos.close();
                }
                if (isDelete) {
                    file.delete();
                }
//                byte[] buffer = bos.toByteArray();
                return bos.toByteArray();
            }
        } catch (Exception e) {
            logger.error("获取[" + fileUrl + "]的byte数组异常", e);
            return null;
        }
    }

    public static byte[] getFileByteArray(File file) {
        try {
            if (!file.exists()) { // 如果文件不存在
                logger.info("文件不存在");
                return null;
            }

            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            if (fis != null) {
                fis.close();
            }
            if (bos != null) {
                bos.close();
            }
            return bos.toByteArray();
        } catch (Exception e) {
            logger.error("获取文件的byte数组异常", e);
        }
        return null;
    }

    /**
     *
     * @ClassName ObjectUtil
     * @Title sendMsgByTCP
     * @Description 通过TCP向指定的IP端口发送信息
     * @param ip 信息接收方IP
     * @param port 信息接收方端口
     * @param msg 发送信息内容
     * @param charset 字符编码格式
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2019-07-04 14:35
     * @return String
     **/
    public static DataApi sendMsgByTCP(String ip, int port, String msg, String charset) {
        try {
            // 向指定的IP端口建立连接
            Socket s = new Socket(ip, port);
            // 打开输出流
            OutputStream os = s.getOutputStream();
            // 发送信息
            os.write(msg.getBytes(charset));
            // 打开输入流
            InputStream is = s.getInputStream();
            // 设置缓冲区大小
            byte[] buf = new byte[1024];
            int len = is.read(buf);
            // 从服务器读取返回信息
            String c = new String(buf,0,len);
            // 关闭连接
            s.close();
            return DataApi.generateSuccessMsg(1, "发送成功", c);
        } catch (Exception e) {
            logger.error("通过TCP向[" + ip + ":" + port + "]发送信息时异常，发送内容为：" + msg, e);
        }
        return DataApi.generateFailMsg(1, "发送失败");
    }

    /**
     *
     * @ClassName ObjectUtil
     * @Title sendMsgByUDP
     * @Description 通过UDP向指定的IP端口发送信息
     * @param ip 信息接收方IP
     * @param port 信息接收方端口
     * @param msg 发送信息内容
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2019-07-04 14:40
     * @return boolean
     **/
    public static boolean sendMsgByUDP(String ip, int port, String msg) {
        try {
            //建立udp的服务
            DatagramSocket datagramSocket = new DatagramSocket();
            //创建了一个数据包
            DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length, InetAddress.getByName(ip), port);
            //调用udp的服务发送数据包
            datagramSocket.send(packet);
            //关闭资源 ---实际上就是释放占用的端口号
            datagramSocket.close();
            return true;
        } catch (Exception e) {
            logger.error("通过UDP向[" + ip + ":" + port + "]发送信息时异常，发送内容为：" + msg, e);
        }
        return false;
    }

    /**
     *
     * @ClassName CameraHelper
     * @Title getType
     * @Description 获取对象的类型
     * @param object
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2019-02-21 17:07
     * @return java.lang.String
     **/
    public static String getType(Object object){
        String typeName = object.getClass().getName();
        int length = typeName.lastIndexOf(".");
        String type = typeName.substring(length + 1);
        return type;
    }

    /**
     *
     * @ClassName CameraHelper
     * @Title saveWebPic
     * @Description 保存网络图片至本地（需要登录）
     * @param fileUrl 网络图片地址
     * @param filePath 本地图片地址
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2019-03-06 10:12
     * @return boolean
     **/
    public static boolean saveWebPic(String fileUrl, String filePath, String username, String password) throws Exception {
        URL url = null;
        BufferedImage bi = null;
        InputStream inputStream = null;
        try {
            url = new URL(fileUrl);
            java.net.Authenticator.setDefault(new java.net.Authenticator(){
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password.toCharArray());
                }
            });

            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            uc.setDoInput(true); // 设置是否要从 URL 连接读取数据,默认为true
            uc.connect();
            inputStream = uc.getInputStream();
            if (inputStream == null) { // 未拉取到网络文件的数据
                return false;
            }
            bi = ImageIO.read(inputStream);

            BufferedImage bufferedImage = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics().drawImage(bi, 0, 0, Color.WHITE, null);

            ImageIO.write(bufferedImage, "jpg", new File(filePath));
            bi.flush();
            inputStream.close();
            return true;
        } catch (Exception e1) {
            e1.printStackTrace();
            return false;
        } finally {
            if (bi != null) {
                bi.flush();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.info("IO流关闭异常");
                }
            }
        }
    }

    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
            stringBuilder.append(' ');
        }
        return stringBuilder.toString();
    }

    public static String ByteBuf2Str(ByteBuf buf) {
        String str;
        if(buf.hasArray()) { // 处理堆缓冲区
            str = new String(buf.array(), buf.arrayOffset() + buf.readerIndex(), buf.readableBytes());
        } else { // 处理直接缓冲区以及复合缓冲区
            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), bytes);
            str = new String(bytes, 0, buf.readableBytes());
        }
        return str;
    }

    public static boolean isJsonStr(String str) {
        boolean result = false;
        if (isNotBlank(str)) {
            str = str.trim();
            if (str.startsWith("{") && str.endsWith("}")) {
                result = true;
            } else if (str.startsWith("[") && str.endsWith("]")) {
                result = true;
            }
        }
        return result;
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     *
     * @ClassName ObjectUtil
     * @Title getRealIp
     * @Description 获取请求者的IP
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     * @param request
     * @Author 王俊元（wangjunyuan@newhow.com.cn）
     * @Date 2021-02-04 14:20
     * @result java.lang.String
     **/
    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        System.out.println("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            System.out.println("Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            System.out.println("WL-Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            System.out.println("HTTP_CLIENT_IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
            System.out.println("X-Real-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            System.out.println("getRemoteAddr ip: " + ip);
        }
        System.out.println("获取客户端ip: " + ip);
        return ip;
    }

    public static void main(String[] args) throws Exception {
//        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
//                "<CreateFDLibList version=\"2.0\" xmlns=\"http://www.isapi.org/ver20/XMLSchema\">" +
//                "<CreateFDLib>" +
//                "<id>{id}</id>" +
//                "<name></name>" +
//                "<thresholdValue></thresholdValue>" +
//                "<customInfo></customInfo>" +
//                "<customFaceLibID></customFaceLibID>" +
//                "</CreateFDLib>" +
//                "</CreateFDLibList>";
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("id", "1231241");
//        System.out.println(replaceContent(str, map));
//        String aaa = "1223532152151";
//        saveFile(aaa.getBytes(), new File("C:\\work\\IntelliJ\\workspaces\\client\\temp\\1.txt"));
//        System.out.println(getWebFileByteArray("https://image.hahamx.cn/2019/07/01/middle/2882687_20c99d62400e079cbf102d89af11e047_1561991588.jpg").toString());
//        System.out.println(getFileByteArray("https://image.hahamx.cn/2019/07/01/middle/2882687_20c99d62400e079cbf102d89af11e047_1561991588.jpg", false).toString());
//        System.out.println(getCustomTime("day", -1, "yyyy-MM-dd 00:00:00"));
        System.out.println(sendMsgByTCP("192.168.80.50", 9999, "你好", "UTF-8").getData());
    }
}
