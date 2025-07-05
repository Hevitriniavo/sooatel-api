package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.OrderLine;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RepositoryFactory {
    private final Map<String, JpaRepository<?, ?>> repositories;

    @SuppressWarnings("unchecked")
    private <T extends JpaRepository<?, ?>> T getRepositoryFor(String name) {
        JpaRepository<?, ?> repository = repositories.get(name);
        if (repository == null) {
            throw new IllegalArgumentException(String.format("Repository %s not defined", name));
        }
        return (T) repository;
    }


    public UserRepository getUserRepository() {
        return getRepositoryFor("userRepository");
    }

    public InvoiceRepository getInvoiceRepository() {
        return getRepositoryFor("invoiceRepository");
    }

    public OrderLineRepository  getOrderLineRepository() {
        return getRepositoryFor("orderLineRepository");
    }

    public InvoiceLineRepository getInvoiceLineRepository() {
        return getRepositoryFor("invoiceLineRepository");
    }


    public MenuOrderRepository getMenuOrderRepository() {
        return getRepositoryFor("menuOrderRepository");
    }

    public ReservationRepository getReservationRepository() {
        return getRepositoryFor("reservationRepository");
    }


    public RoomRepository getRoomRepository() {
        return getRepositoryFor("roomRepository");
    }

   public SessionOccupationRepository getSessionOccupationRepository() {
        return getRepositoryFor("sessionOccupationRepository");
    }

    public FloorRepository getFloorRepository() {
        return getRepositoryFor("floorRepository");
    }

    public CustomerRepository getCustomerRepository() {
        return getRepositoryFor("customerRepository");
    }

    public RoleRepository getRoleRepository() {
        return getRepositoryFor("roleRepository");
    }

    public UnitRepository getUnitRepository() {
        return getRepositoryFor("unitRepository");
    }

    public IngredientRepository getIngredientRepository() {
        return getRepositoryFor("ingredientRepository");
    }

    public StockRepository getStockRepository() {
        return getRepositoryFor("stockRepository");
    }

    public PurchaseRepository getPurchaseRepository() {
        return getRepositoryFor("purchaseRepository");
    }

    public OperationRepository getOperationRepository() {
        return getRepositoryFor("operationRepository");
    }

    public MenuGroupRepository getCategoryRepository() {
        return getRepositoryFor("categoryRepository");
    }

    public MenuRepository getMenuRepository() {
        return getRepositoryFor("menuRepository");
    }

    public TableEntityRepository getTableRepository() {
        return getRepositoryFor("tableRepository");
    }

    public MenuIngredientRepository getMenuIngredientRepository() {
        return getRepositoryFor("menuIngredientRepository");
    }

    public OtpCodeRepository getOtpCodeRepository() {
        return getRepositoryFor("otpCodeRepository");
    }

    public CashRepository getCashRepository() {
        return getRepositoryFor("cashRepository");
    }

    public CashHistoryRepository getCashHistoryRepository() {
        return getRepositoryFor("cashHistoryRepository");
    }

    public IngredientGroupRepository getIngredientGroupRepository() {
        return getRepositoryFor("ingredientGroupRepository");
    }

}
