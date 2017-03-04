package sql;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Lloyd on 03/03/2016.
 */
public class SQLStatement {

    private Set<String> select;
    private Set<String> from;
    private String where = "";
    private Set<String> group;

    public SQLStatement() {
        this.select = new HashSet<>();
        this.from = new HashSet<>();
        this.group = new HashSet<>();
    }

    public SQLStatement(Set<String> select, Set<String> from, String where) {
        this.select = select;
        this.from = from;
        this.where = where;
        this.group = new HashSet<>();
    }

    public SQLStatement(Set<String> select, Set<String> from) {
        this.select = select;
        this.from = from;
        this.group = new HashSet<>();
    }

    public String getStatement() {
        String returnString;
        returnString = "SELECT " + getSelectString() + " FROM " + getFromString();
        if (!getWhere().isEmpty() && getWhere() != null) {
            returnString += " WHERE " + getWhere();
        }
        String groupString = getGroupString();
        if (!groupString.isEmpty()) {
            returnString += " GROUP BY " + groupString;
        }
        return returnString;
    }

    public Set<String> getSelect() {
        return select;
    }

    public String getSelectString() {
        return convertToString(select);
    }

    public void setSelect(Set<String> Select) {
        this.select.addAll(select);
    }

    public Set<String> getFrom() {
        return from;
    }

    public String getFromString() {
        return convertToString(from);
    }

    public void setFrom(Set<String> from) {
        this.from.addAll(from);
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) { this.where = where;}

    @Override
    public String toString() {
        return getStatement();
    }

    public Set<String> getGroup() {
        return group;
    }

    public String getGroupString() {
        return convertToString(group);
    }

    public void setGroup(Set<String> group) {
        this.group = group;
    }

    private String convertToString(Set<String> set) {
        String returnString = "";
        for (String s : set) {
            if (returnString.isEmpty()) {
                returnString = s;
            } else {
                if (s.startsWith("strftime")){
                    returnString = s + " , " + returnString;
                }else {
                    returnString += " , " + s;
                }
            }
        }
        return returnString;
    }
}
