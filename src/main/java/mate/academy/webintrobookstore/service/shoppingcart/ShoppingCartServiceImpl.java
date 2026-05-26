package mate.academy.webintrobookstore.service.shoppingcart;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mate.academy.webintrobookstore.dto.AddBookToCartRequestDto;
import mate.academy.webintrobookstore.dto.ShoppingCartResponseDto;
import mate.academy.webintrobookstore.dto.UpdateCartItemRequestDto;
import mate.academy.webintrobookstore.mapper.ShoppingCartMapper;
import mate.academy.webintrobookstore.model.Book;
import mate.academy.webintrobookstore.model.CartItem;
import mate.academy.webintrobookstore.model.ShoppingCart;
import mate.academy.webintrobookstore.repository.book.BookRepository;
import mate.academy.webintrobookstore.repository.shoppingcart.CartItemRepository;
import mate.academy.webintrobookstore.repository.shoppingcart.ShoppingCartRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    private ShoppingCart getShoppingCartOrThrow(Long userId) {
        return shoppingCartRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't find shopping cart for user id: " + userId));
    }

    private CartItem getCartItemOrThrow(Long cartItemId) {
        return cartItemRepository.findById(cartItemId).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't find cart item by id: " + cartItemId)
        );
    }

    private void validateOwnership(CartItem cartItem, Long userId) {
        Long ownerUserId = cartItem.getShoppingCart().getUser().getId();
        if (!ownerUserId.equals(userId)) {
            throw new IllegalArgumentException(
                    "User does not have access");
        }
    }

    @Override
    public ShoppingCartResponseDto getShoppingCart(Long userId) {
        return shoppingCartMapper.toDto(getShoppingCartOrThrow(userId));
    }

    @Override
    public ShoppingCartResponseDto addBookToCart(Long userId, AddBookToCartRequestDto requestDto) {
        ShoppingCart shoppingCart = getShoppingCartOrThrow(userId);
        Book book = bookRepository.findById(requestDto.getBookId()).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't find book by id: " + requestDto.getBookId())
        );
        Optional<CartItem> cartItem = cartItemRepository.findByShoppingCartIdAndBookId(
                shoppingCart.getId(), book.getId());
        if (cartItem.isPresent()) {
            CartItem existingCartItem = cartItem.get();
            int currentQuantity = existingCartItem.getQuantity();
            int updatedQuantity = currentQuantity + requestDto.getQuantity();
            existingCartItem.setQuantity(updatedQuantity);
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setBook(book);
            newCartItem.setShoppingCart(shoppingCart);
            shoppingCart.getCartItems().add(newCartItem);
            newCartItem.setQuantity(requestDto.getQuantity());
            cartItemRepository.save(newCartItem);
        }
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto updateQuantity(
            Long userId, Long cartItemId, UpdateCartItemRequestDto requestDto) {
        CartItem cartItem = getCartItemOrThrow(cartItemId);
        validateOwnership(cartItem, userId);
        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(cartItem.getShoppingCart());
    }

    @Override
    public void deleteCartItem(Long userId, Long cartItemId) {
        CartItem cartItem = getCartItemOrThrow(cartItemId);
        validateOwnership(cartItem, userId);
        cartItemRepository.delete(cartItem);
    }
}
