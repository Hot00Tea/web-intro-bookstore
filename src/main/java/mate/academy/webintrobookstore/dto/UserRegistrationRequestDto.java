package mate.academy.webintrobookstore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import mate.academy.webintrobookstore.validation.field.FieldMatch;

@Getter
@Setter
@FieldMatch(
        first = "password",
        second = "repeatPassword",
        message = "Password must match")
public class UserRegistrationRequestDto {

    @NotBlank
    @Email(message = "Email is not valid")
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String repeatPassword;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String shippingAddress;
}
