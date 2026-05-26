package mate.academy.webintrobookstore.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.webintrobookstore.dto.AddBookToCartRequestDto;
import mate.academy.webintrobookstore.dto.ShoppingCartResponseDto;
import mate.academy.webintrobookstore.dto.UpdateCartItemRequestDto;
import mate.academy.webintrobookstore.model.User;
import mate.academy.webintrobookstore.service.shoppingcart.ShoppingCartService;
import mate.academy.webintrobookstore.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoint for shopping carts")
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final UserService userService;

    private Long getCurrentUserId(UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);
        return user.getId();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ShoppingCartResponseDto getUserCart(@AuthenticationPrincipal UserDetails userDetails) {
        return shoppingCartService.getShoppingCart(getCurrentUserId(userDetails));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ShoppingCartResponseDto addToCart(@Valid @RequestBody AddBookToCartRequestDto requestDto,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        return shoppingCartService.addBookToCart(getCurrentUserId(userDetails), requestDto);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/items/{cartItemId}")
    public ShoppingCartResponseDto updateCart(@PathVariable Long cartItemId,
                          @RequestBody @Valid UpdateCartItemRequestDto requestDto,
                          @AuthenticationPrincipal UserDetails userDetails
    ) {
        return shoppingCartService.updateQuantity(getCurrentUserId(userDetails),
                cartItemId, requestDto);
    }

    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/items/{cartItemId}")
    public void delete(@PathVariable Long cartItemId,
                       @AuthenticationPrincipal UserDetails userDetails) {
        shoppingCartService.deleteCartItem(getCurrentUserId(userDetails), cartItemId);
    }

}
