package mate.academy.webintrobookstore.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class AuthorValidator implements ConstraintValidator<Author, String> {

    private static final String PATTERN_OF_AUTHOR = "^[A-Za-z]+(?:[\\'\\-]?[A-Za-z]+)"
            + "*(?:\\s[A-Za-z]+(?:[\\'\\-]?[A-Za-z]+)*)*$";

    @Override
    public boolean isValid(String author, ConstraintValidatorContext constraintValidatorContext) {
        return author != null && Pattern.compile(PATTERN_OF_AUTHOR).matcher(author).matches();
    }
}
