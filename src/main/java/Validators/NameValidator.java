package Validators;

public class NameValidator {

    public static boolean checkIsValid(String name){
        if(name.length()> 2 && name.length() < 20 && Character.isUpperCase(name.charAt(0))){
            return true;
        }
        return false;
    }
}
