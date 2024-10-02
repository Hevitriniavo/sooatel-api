package com.fresh.coding.sooatelapi.dtos.categories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SaveCategory implements Serializable {
    private Long id;
    @NotBlank(message = "The category name cannot be empty.")
    @Size(min = 2, max = 100, message = "The category name must be between 2 and 100 characters.")
    private String name;

}
