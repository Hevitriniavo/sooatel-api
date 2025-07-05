package com.fresh.coding.sooatelapi.controllers.categories;

import com.fresh.coding.sooatelapi.dtos.categories.MenuGroupSummarized;
import com.fresh.coding.sooatelapi.dtos.categories.SaveCategory;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.CategorySearch;
import com.fresh.coding.sooatelapi.services.categories.MenuGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final MenuGroupService menuGroupService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public MenuGroupSummarized save(@Valid @RequestBody  SaveCategory toSave){
        return menuGroupService.save(toSave);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<MenuGroupSummarized> getAllCategories(){
        return menuGroupService.findAllCategories();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Paginate<List<MenuGroupSummarized>> getAllCategories(
            @ModelAttribute CategorySearch categorySearch,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return menuGroupService.findAllCategories(categorySearch, page, size);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUnit(@PathVariable Long id) {
         menuGroupService.deleteById(id);
    }
}
