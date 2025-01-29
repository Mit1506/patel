package com.example.cafe.entities;

import org.springframework.stereotype.Component;

import com.example.cafe.dtos.CategoryDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Component
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    @Lob
    @Column(columnDefinition = "longblob")
   private byte[] img;
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
	public byte[] getImg() {
		return img;
	}
	public void setImg(byte[] img) {
		this.img = img;
	}
    
	
	
	public CategoryDto getCategoryDto(){
	CategoryDto	categoryDto = new CategoryDto();
		categoryDto.setId(id);
		categoryDto.setName(name);
		categoryDto.setDescription(description);
		categoryDto.setReturnedImg(img);

		return categoryDto;

		

		}
    
    
}
