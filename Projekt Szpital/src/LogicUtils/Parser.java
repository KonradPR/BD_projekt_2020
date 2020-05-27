package LogicUtils;

import java.util.regex.Pattern;

public class Parser {

    //pareses given dosage string and returns numeric value of dosage
    // this will throw IllegalArgumentException if input cannot be parsed in some way
    public static int parseDosageValue(String str) throws Exception{
        String[] elems = str.split("(?<=\\d)(?=\\D)");
        if( elems.length!=2)throw new IllegalArgumentException("Cannot be parsed");
        int res = 0;
        try{
            res = Integer.parseInt(elems[0]);
        }catch(NumberFormatException e){
            throw new IllegalArgumentException("Cannot be parsed");
        }
        DosageType.fromString(elems[1]);

        return res;
    }
    //Similar to parseDosageValu but returns DosageType
    public static DosageType parseDosageUnit(String str) throws Exception{
        String[] elems = str.split("(?<=\\d)(?=\\D)");
        if( elems.length!=2)throw new IllegalArgumentException("Cannot be parsed");
        try{
            Integer.parseInt(elems[0]);
        }catch(NumberFormatException e){
            throw new IllegalArgumentException("Cannot be parsed");
        }
        return DosageType.fromString(elems[1]);
    }

    public static boolean isValidEmail(String str){
        return Pattern.matches("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}",str);
    }

    public static boolean isValidPhone(String str){
        return Pattern.matches("[0-9]{9}",str);
    }

    public static boolean isValidName(String str){
        return Pattern.matches("[A-Z][a-z]{2,10}]",str);
    }

    public static boolean isValidSurname(String str){
        return Pattern.matches("[A-Z][a-z]{1,15}",str);
    }
    

}
