package com.ecart.easyshopping.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecart.easyshopping.repository.CartRepository;
import com.ecart.easyshopping.repository.OrderRepository;
import com.ecart.easyshopping.repository.StockRepository;
import com.ecart.easyshopping.exception.ItemCannotUpdateException;
import com.ecart.easyshopping.exception.OutOfStockException;
import com.ecart.easyshopping.exception.ResourceNotFoundException;
import com.ecart.easyshopping.model.*;

@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	CartRepository cartRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	StockRepository stockRepository;

	@GetMapping("/cart")
	public List<Cart> getAllItems() {
		return cartRepository.findAll();
	}

	@GetMapping("/cart/{id}")
	public Cart getItemById(@PathVariable(value = "id") Long itemId) {
		return cartRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
	}

	@PostMapping("/cart")
	public Cart createItem(@Valid @RequestBody Cart cart) {
		Stock stock = stockRepository.findByStockName(cart.getName());
		if (stock.getQuantity() > 0 && cart.getQuantity() <= stock.getQuantity()) {
			Cart cart1 = cartRepository.findByItemName(cart.getName()).orElse(null);
			if (cart1 == null) {
				return cartRepository.save(cart);
			} else {
				cart1.setQuantity(cart1.getQuantity() + cart.getQuantity());
				cart1.setTotalPrice(cart1.getTotalPrice() + cart.getTotalPrice());
				return cartRepository.save(cart1);
			}
		} else {
			throw new OutOfStockException(cart.getName());
		}
	}

	@DeleteMapping("/cart/{id}")
	public ResponseEntity<?> deleteItemById(@PathVariable(value = "id") Long itemId) {
		Cart cart = cartRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
		cartRepository.delete(cart);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/cart")
	public ResponseEntity<?> deleteItem() {
		cartRepository.deleteAll();
		return ResponseEntity.ok().build();
	}

	@PutMapping("/cart/{id}")
	public Cart updateItem(@PathVariable(value = "id") Long itemId, @Valid @RequestBody Cart cartDetails) {
		Cart cart = cartRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
		if (cart.getName().equals(cartDetails.getName())) {
			cart.setQuantity(cartDetails.getQuantity());
			cart.setTotalPrice(cartDetails.getTotalPrice());
		} else {
			throw new ItemCannotUpdateException(cart.getName(), itemId);
		}

		Cart updatedCart = cartRepository.save(cart);
		return updatedCart;

	}

	@PostMapping("/order")
	public String placeOrder(@RequestBody Map<String, String> body) throws ParseException {
		String name = body.get("name");
		String emailId = body.get("emailId");
		Cart cart = cartRepository.findByItemName(name)
				.orElseThrow(() -> new ResourceNotFoundException("Item", "name", name));
		Order order = new Order();
		order.setName(name);
		order.setQuantity(cart.getQuantity());
		order.setEmailId(emailId);
		order.setTotalPrice(cart.getTotalPrice());

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date bookingDate = new Date();
		order.setBookingDate(formatter.parse(formatter.format(bookingDate)));

		Calendar c = Calendar.getInstance();
		c.setTime(bookingDate);
		c.add(Calendar.DATE, 7);
		Date deliveryDate = c.getTime();
		order.setDeliveryDate(formatter.parse(formatter.format(deliveryDate)));

		Order savedOrder = orderRepository.save(order);
		if (savedOrder != null) {
			cartRepository.deleteById(cart.getId());
			Stock stock = stockRepository.findByStockName(name);
			stock.setQuantity(stock.getQuantity() - cart.getQuantity());
			stockRepository.save(stock);
			return "Order placed successfully";
		} else {
			return "Error occured while ordering";
		}

	}

	@GetMapping("/order")
	public List<Order> getOrders() {
		return orderRepository.findAll();
	}
}
