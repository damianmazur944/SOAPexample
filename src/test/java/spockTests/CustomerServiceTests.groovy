package spockTests

import Validators.AgeValidator
import Validators.NameValidator
import spock.lang.Specification


class CustomerServiceTests extends Specification {
     void "should return true for valid name example Damian"() {
        given:
            String name
        when:
            name = "Damian"
        then:
            NameValidator.checkIsValid(name)
    }
    void "should return false for invalid name example asd"() {
        given:
        String name
        when:
        name = "asd"
        then:
        !NameValidator.checkIsValid(name)
    }
    void "should return true for valid age example 45"() {
        given:
        Integer age
        when:
        age = 45
        then:
        AgeValidator.checkIsValid(age)
    }
    void "should return true for invalid age example 200"() {
        given:
            Integer age
        when:
            age = 200
        then:
            !AgeValidator.checkIsValid(age)
    }
}
