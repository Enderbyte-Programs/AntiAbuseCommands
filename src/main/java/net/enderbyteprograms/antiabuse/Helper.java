package net.enderbyteprograms.antiabuse;

import java.util.List;
import java.util.regex.Pattern;

public class Helper {
    public static boolean DoesStringMatchRegex(String searcher,String pattern) {
        Pattern p = Pattern.compile(pattern);
        return p.matcher(searcher).find();
    }
    public static boolean DoesStringMatchAnyRegex(String searcher, List<String> patterns) {
        boolean result = false;
        for (String pattern:patterns) {
            result |= DoesStringMatchRegex(searcher,pattern);
        }
        return result;
    }
}
