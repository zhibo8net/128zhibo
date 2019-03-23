package website2018.code;

import java.util.List;

import com.google.common.collect.Lists;

public class EntityDefination {

	public String chinese;
	public String english;
	public String big;
	public String small;
	public String table;
	
	public List<PropertyDefination> properties = Lists.newArrayList();

    public String getChinese() {
        return chinese;
    }

    public String getEnglish() {
        return english;
    }

    public String getBig() {
        return big;
    }

    public String getSmall() {
        return small;
    }

    public List<PropertyDefination> getProperties() {
        return properties;
    }

    public String getTable() {
        return table;
    }

}
    