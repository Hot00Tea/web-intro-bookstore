package mate.academy.webintrobookstore.validation.Field;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.util.Objects;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstField;
    private String secondField;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.firstField = constraintAnnotation.first();
        this.secondField = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {

        if (o == null) {
            return true;
        }

        try {
            Field first = o.getClass().getDeclaredField(firstField);
            Field second = o.getClass().getDeclaredField(secondField);
            first.setAccessible(true);
            second.setAccessible(true);
            Object firstValue = first.get(o);
            Object secondValue = second.get(o);
            return Objects.equals(firstValue, secondValue);
        } catch (Exception e) {
            return false;
        }
    }
}
