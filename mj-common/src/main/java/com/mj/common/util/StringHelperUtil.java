package com.mj.common.util;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author anyang
 * @CreateTime 2023/3/15
 * @Des
 */
public class StringHelperUtil {
    public static String renderString(String content, Map<String, Object> map){
        Set<Map.Entry<String, Object>> sets = map.entrySet();
        for(Map.Entry<String, Object> entry : sets) {
            String regex = "\\$\\{" + entry.getKey() + "\\}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(content);
            content = matcher.replaceAll(String.valueOf(entry.getValue()));
        }
        return content;
    }
}
