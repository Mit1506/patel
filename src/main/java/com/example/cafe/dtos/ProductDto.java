
package com.example.cafe.dtos;

import org.springframework.web.multipart.MultipartFile;




public class ProductDto {

	private long id;
	
	private String name;
	
	private Integer price;
	
	public Integer getPrice() {
		return price;
	}


	public void setPrice(Integer price) {
		this.price = price;
	}

	private String description;
	
	private MultipartFile img;
	
	public byte[] returnedImg;
	
	private Long categoryId;

	private String categoryName;

	public void setReturnedImg(byte[] returnedImg) {
		this.returnedImg = returnedImg;
	}

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MultipartFile getImg() {
		return img;
	}

	public void setImg(MultipartFile img) {
		this.img = img;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public byte[] getReturnedImg() {
		return returnedImg;
	}

	
	
}
