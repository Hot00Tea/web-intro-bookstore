package mate.academy.webintrobookstore.service.shoppingcart;

import mate.academy.webintrobookstore.dto.AddBookToCartRequestDto;
import mate.academy.webintrobookstore.dto.ShoppingCartResponseDto;
import mate.academy.webintrobookstore.dto.UpdateCartItemRequestDto;
import mate.academy.webintrobookstore.model.User;

public interface ShoppingCartService {
    ShoppingCartResponseDto getShoppingCart(Long userId);

    ShoppingCartResponseDto addBookToCart(Long userId,
                                          AddBookToCartRequestDto requestDto);

    ShoppingCartResponseDto updateQuantity(Long userId,
                                           Long cartItemId, UpdateCartItemRequestDto requestDto);

    void deleteCartItem(Long userId, Long cartItemId);

    void createShoppingCart(User user);
}
