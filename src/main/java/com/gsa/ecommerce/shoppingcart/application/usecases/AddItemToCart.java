package com.gsa.ecommerce.shoppingcart.application.usecases;

import java.util.UUID;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsa.ecommerce.shoppingcart.domain.models.Money;
import com.gsa.ecommerce.shared.exceptions.DomainException;
import com.gsa.ecommerce.shoppingcart.domain.models.cart.Cart;
import com.gsa.ecommerce.shared.exceptions.ValidationException;
import com.gsa.ecommerce.shoppingcart.domain.models.cart.CartItem;
import com.gsa.ecommerce.shoppingcart.domain.models.cart.CartRepository;
import com.gsa.ecommerce.shoppingcart.domain.models.cart.CartItemQuantity;
import com.gsa.ecommerce.shoppingcart.application.gateways.ProductGateway;

@Service
public class AddItemToCart {
  public record Input(UUID cartId, UUID productId, UUID customerId, int quantity) {
  }

  private final CartRepository cartRepository;
  private final ProductGateway productGateway;

  @Autowired
  public AddItemToCart(CartRepository cartRepository, ProductGateway productGateway) {
    this.cartRepository = cartRepository;
    this.productGateway = productGateway;
  }

  public void execute(Input input) throws DomainException, ValidationException {
    Optional<Cart> cartFound = cartRepository.findOneById(input.cartId);
    Optional<ProductGateway.ProductDTO> productFound = productGateway.findOneById(input.productId);

    productFound.orElseThrow(() -> new DomainException("Product not found"));

    Money money = Money.create(productFound.get().price());
    CartItemQuantity cartItemQuantity = CartItemQuantity.create(input.quantity);
    CartItem cartItem = CartItem.create(productFound.get().id(), money, cartItemQuantity);

    if (cartFound.isPresent()) {
      cartFound.get().addItem(cartItem);
      cartRepository.update(cartFound.get());
      return;
    }

    Cart cart = Cart.create(input.customerId);
    cart.addItem(cartItem);
    cartRepository.create(cart);
  }
}
