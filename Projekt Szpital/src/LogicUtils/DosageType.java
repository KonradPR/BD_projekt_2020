package LogicUtils;

import java.util.regex.Pattern;

public enum DosageType {
    ml,
    mg,
    tab,
    pill;

    public static DosageType fromString(String str) throws Exception{
        if(Pattern.matches("mls?",str)){
            return DosageType.ml;
        }else if(Pattern.matches("mgs?",str)){
            return  DosageType.mg;
        }else if(Pattern.matches("tabl?e?t?s?",str)){
            return DosageType.tab;
        }else if(Pattern.matches("pill?s?",str)){
            return DosageType.pill;
        }else{
            throw new IllegalArgumentException("Cannot read value from given string");
        }
    }

    //TODO jakos ładnie zrobic liczba pojedyncza mnoga?
    @Override
    public String toString() {
        switch(this){
            case mg: return "mg(s)";
            case ml: return  "ml(s)";
            case tab: return "tablet(s)";
            case pill: return  "pill(s)";
        }
        return "";
    }
}
