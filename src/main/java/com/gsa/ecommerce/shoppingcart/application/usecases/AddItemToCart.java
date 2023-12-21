package com.gsa.ecommerce.shoppingcart.application.usecases;

import java.util.UUID;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gsa.ecommerce.core.infra.exceptions.DomainException;
import com.gsa.ecommerce.shoppingcart.domain.models.Money;
import com.gsa.ecommerce.core.infra.exceptions.ValidationException;
import com.gsa.ecommerce.order.application.gateways.CustomerGateway;
import com.gsa.ecommerce.shoppingcart.domain.models.cart.Cart;
import com.gsa.ecommerce.shoppingcart.domain.models.cart.CartItem;
import com.gsa.ecommerce.shoppingcart.domain.models.cart.CartRepository;
import com.gsa.ecommerce.shoppingcart.domain.models.cart.CartItemQuantity;
import com.gsa.ecommerce.shoppingcart.application.gateways.ProductGateway;

@Component
public class AddItemToCart {
  public record Input(UUID customerId, UUID productId, int quantity) {
  }

  private final CartRepository cartRepository;
  private final ProductGateway productGateway;
  private final CustomerGateway customerGateway;

  public AddItemToCart(CartRepository cartRepository, ProductGateway productGateway, CustomerGateway customerGateway) {
    this.cartRepository = cartRepository;
    this.productGateway = productGateway;
    this.customerGateway = customerGateway;
  }

  public void execute(Input input) throws DomainException, ValidationException {
    boolean customerExists = customerGateway.exists(input.customerId());

    if (!customerExists) {
      throw new DomainException("Customer not found");
    }

    Optional<Cart> cartFound = cartRepository.findOneByCustomerId(input.customerId());
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
