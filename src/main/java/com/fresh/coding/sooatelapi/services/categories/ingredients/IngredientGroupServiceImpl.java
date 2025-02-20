package com.fresh.coding.sooatelapi.services.categories.ingredients;

import com.fresh.coding.sooatelapi.dtos.categories.ingredients.IngredientGroupDTO;
import com.fresh.coding.sooatelapi.dtos.categories.ingredients.IngredientGroupSummarized;
import com.fresh.coding.sooatelapi.entities.IngredientGroup;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientGroupServiceImpl implements IngredientGroupService {

    private final RepositoryFactory repositoryFactory;

    @Override
    @Transactional
    public IngredientGroupSummarized create(IngredientGroupDTO groupDTO) {
        var ingredientGroupRepo = repositoryFactory.getIngredientGroupRepository();
        var ingredientGroup = IngredientGroup.builder()
                .name(groupDTO.getName())
                .build();
        var savedGroup = ingredientGroupRepo.save(ingredientGroup);
        return mapToDTO(savedGroup);
    }

    @Override
    @Transactional
    public IngredientGroupSummarized update(Long id, IngredientGroupDTO groupDTO) {
        var ingredientGroupRepo = repositoryFactory.getIngredientGroupRepository();
        var ingredientGroup = ingredientGroupRepo.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Groupe d'ingrédients avec l'ID " + id + " introuvable"));
        ingredientGroup.setName(groupDTO.getName());
        var savedGroup = ingredientGroupRepo.save(ingredientGroup);
        return mapToDTO(savedGroup);
    }

    @Override
    public List<IngredientGroupSummarized> all() {
        var ingredientGroupRepo = repositoryFactory.getIngredientGroupRepository();
        var allIngredientGroups = ingredientGroupRepo.findAll();
        return allIngredientGroups.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void remove(Long id) {
        var ingredientGroupRepo = repositoryFactory.getIngredientGroupRepository();
        var ingredientGroup = ingredientGroupRepo.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Groupe d'ingrédients avec l'ID " + id + " introuvable"));
        ingredientGroupRepo.delete(ingredientGroup);
    }

    public IngredientGroupSummarized mapToDTO(IngredientGroup ingredientGroup) {
        return new IngredientGroupSummarized(
                ingredientGroup.getId(),
                ingredientGroup.getName(),
                ingredientGroup.getCreatedAt(),
                ingredientGroup.getUpdatedAt()
        );
    }
}
