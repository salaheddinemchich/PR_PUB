package com.shopsense.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	int id;
	String title;
	String thumbnailUrl;
	String description;
	String regularPrice;
	String salePrice;
	String category;
	String stockStatus;
	String stockCount;
	@jakarta.persistence.Column(name = "seller_id")
	int sellerId;
	String storeName;
	String status;
}
