package com.adminportal.repository;

import com.adminportal.domain.Book;
import com.adminportal.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByBook(Book book);
}
