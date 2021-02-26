import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCountMethods {
    //校验中文的正则表达式
    private static String CHINESE_REGEX = "[\\u4e00-\\u9fa5]";
    //空白行的正则表达式
    private static String BLANK_LINE_REGEX = "^\\s*$";
    
    /**
     * 过滤掉中文
     * @param str 待过滤中文的字符串
     * @return 过滤掉中文后字符串
     */
    public static String filterChinese(String str) {
        String result = str;
        //判断字符串中是否包含中文
        Pattern p = Pattern.compile(CHINESE_REGEX);
        Matcher m = p.matcher(str);
        
        if (m.find()) {
            StringBuffer sb = new StringBuffer();
            // 用于校验是否为中文
            boolean flag = false;
            char chinese = 0;
            char[] charArray = str.toCharArray();
            // 过滤中文及中文字符
            for (int i = 0; i < charArray.length; i++) {
                chinese = charArray[i];
                flag = isChineseChar(chinese);
                if (!flag) {
                    sb.append(chinese);
                }
            }
            result = sb.toString();
        }
        return result;
    }
    
    /**
     * 校验一个字符是否是汉字
     * @param c 被校验的字符
     * @return true代表是汉字
     */
    public static boolean isChineseChar(char c) {
        try {
            return String.valueOf(c).getBytes("UTF-8").length > 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 统计文件的字符数
     * @param str 被校验的字符串
     * @return int 文件的字符数
     */
    public static int countChars(String str){
        int count = 0;
        char[] achar = str.toCharArray();
        
        for (int i = 0;i<achar.length;i++) {
            //统计ascii code
            if (33 <= achar[i] && achar[i] <= 126) {
                count++;
            }
            //统计空格，水平制表符，换行符
            if(achar[i] == 32 || achar[i] == 9 || achar[i] == 10) {
                count++;
            }
        }
        return count;
    }

    /**
     * 统计文件的有效行数
     * @param filePath
     * @return int 文件有效行数
     * @throws FileNotFoundException
     */
    public static int CountLines(String filePath) throws FileNotFoundException {
        int validLine = 0;
        int allLine = 0;
        int blankLine = 0;
        
        BufferedReader br = null;
        InputStream inpStr = new FileInputStream(filePath);
        br = new BufferedReader(new InputStreamReader(inpStr));
        //包含空白字符的行的正则匹配器
        Pattern blankLinePattern = Pattern.compile(BLANK_LINE_REGEX);
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                if (blankLinePattern.matcher(line).find()) {
                    blankLine ++;
                }
                allLine ++;
            }
        } catch (IOException e) {
            throw new RuntimeException("读取文件失败!" + e);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                throw new RuntimeException("关闭文件输入流失败");
            }
        }
        validLine = allLine-blankLine;
        return validLine;
    }

}
