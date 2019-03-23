package website2018.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

public class SearchFilter {

    public enum Operator {
        EQ, LIKE, GT, LT, GTE, LTE, IN
    }

    public String fieldName;
    public Object value;
    public Operator operator;

    public SearchFilter(String fieldName, Operator operator, Object value) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }

    /**
     * searchParams中key的格式为OPERATOR_FIELDNAME
     */
    public static Map<String, SearchFilter> parse(Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = Maps.newHashMap();

        for (Entry<String, Object> entry : searchParams.entrySet()) {
            // 过滤掉空值
            String key = entry.getKey();
            Object value = entry.getValue();
            if ((value == null) || ((value instanceof String) && StringUtils.isBlank((String) value))) {
                continue;
            }

            // 拆分operator与filedAttribute
            String[] names = StringUtils.split(key, "_");
            String fieldName;
            Operator operator;
            if (names.length == 2) {
                fieldName = names[1];
                operator = Operator.valueOf(names[0]);
            }else {
                fieldName = names[0];
                operator = Operator.EQ;
            }
            
            //小于某天的情况，设为小于等于某天最后一毫秒
            if(fieldName.endsWith("Time") && ((operator == Operator.LT) || (operator == Operator.LTE))) {
                Date date = (Date)value;
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.set(Calendar.HOUR_OF_DAY, 23);
                c.set(Calendar.MINUTE, 59);
                c.set(Calendar.SECOND, 59);
                c.set(Calendar.MILLISECOND, 999);
                
                operator = Operator.LTE;
                value = c.getTime();
            }

            // 创建searchFilter
            SearchFilter filter = new SearchFilter(fieldName, operator, value);
            filters.put(key, filter);
        }

        return filters;
    }
}
