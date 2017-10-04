package Validators;

public class AgeValidator {
    public static boolean checkIsValid(Integer age){
        if(18 < age && age < 150){
            return true;
        }
        return false;
    }
}
