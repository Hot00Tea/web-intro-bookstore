package mate.academy.webintrobookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.security.core.Authentication;
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

    @Operation(
            summary = "Get shopping cart",
            description = "Get current user's shopping cart"
    )
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ShoppingCartResponseDto getUserCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.getShoppingCart(user.getId());
    }

    @Operation(
            summary = "Add book to cart",
            description = "Add a book to the shopping cart"
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ShoppingCartResponseDto addToCart(@Valid @RequestBody AddBookToCartRequestDto requestDto,
                                             Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addBookToCart(user.getId(), requestDto);
    }

    @Operation(
            summary = "Update cart item quantity",
            description = "Update quantity of a book in the shopping cart"
    )
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/items/{cartItemId}")
    public ShoppingCartResponseDto updateCart(@PathVariable Long cartItemId,
                          @RequestBody @Valid UpdateCartItemRequestDto requestDto,
                                              Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.updateQuantity(user.getId(),
                cartItemId, requestDto);
    }

    @Operation(
            summary = "Delete cart item",
            description = "Delete item from shopping cart"
    )
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/items/{cartItemId}")
    public void delete(@PathVariable Long cartItemId,
                       Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        shoppingCartService.deleteCartItem(user.getId(), cartItemId);
    }

}
