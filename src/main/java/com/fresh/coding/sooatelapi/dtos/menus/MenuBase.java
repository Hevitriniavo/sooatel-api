package com.fresh.coding.sooatelapi.dtos.menus;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
 class MenuBase  {

    private Long id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    private String description;

    @NotNull(message = "Price cannot be null")
    private Double price;

    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;
}
