package com.fresh.coding.sooatelapi.controllers.categories;

import com.fresh.coding.sooatelapi.dtos.categories.ingredients.IngredientGroupDTO;
import com.fresh.coding.sooatelapi.dtos.categories.ingredients.IngredientGroupSummarized;
import com.fresh.coding.sooatelapi.services.categories.ingredients.IngredientGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/ingredients/groups")
@RequiredArgsConstructor
public class IngredientGroupController {
    private final IngredientGroupService ingredientGroupService;

    public List<IngredientGroupSummarized> all(){
        return ingredientGroupService.all();
    }

    @PostMapping
    public IngredientGroupSummarized create(@Valid @RequestBody IngredientGroupDTO groupDTO){
        return ingredientGroupService.create(groupDTO);
    }

    @PutMapping("/{id}")
    public IngredientGroupSummarized update(
            @PathVariable Long id,
            @Valid @RequestBody IngredientGroupDTO groupDTO
    ){
        return ingredientGroupService.update(id, groupDTO);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id){
         ingredientGroupService.remove(id);
    }
}
