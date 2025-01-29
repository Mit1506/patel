package com.example.cafe.dtos;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;



@Component
public class ProductDto {

	
private Long id;
	
	private String name;
	
	private String price;
	
	private String description;
	
	private byte[] imgreturnedImg;
	
	private MultipartFile img;
	
	private Long categoryId;
	
	private String catagoryName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getImgreturnedImg() {
		return imgreturnedImg;
	}

	public void setImgreturnedImg(byte[] imgreturnedImg) {
		this.imgreturnedImg = imgreturnedImg;
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

	public String getCatagoryName() {
		return catagoryName;
	}

	public void setCatagoryName(String catagoryName) {
		this.catagoryName = catagoryName;
	}
	
	
}
