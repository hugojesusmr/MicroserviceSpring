package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.InvoiceItem;

public interface InvoiceItemsRepository extends JpaRepository<InvoiceItem, Long> {

}
