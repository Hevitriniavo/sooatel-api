package com.fresh.coding.sooatelapi.services.menus.orders;

import com.fresh.coding.sooatelapi.dtos.customers.CustomerDTO;
import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderSummarized;
import com.fresh.coding.sooatelapi.dtos.menus.MenuSummarized;
import com.fresh.coding.sooatelapi.dtos.pagination.PageInfo;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.rooms.RoomDTO;
import com.fresh.coding.sooatelapi.dtos.searchs.MenuOrderSearch;
import com.fresh.coding.sooatelapi.dtos.tables.TableSummarized;
import com.fresh.coding.sooatelapi.entities.MenuOrder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchMenuOrderServiceImpl implements SearchMenuOrderService {
    private final EntityManager entityManager;

    @Override
    public Paginate<List<MenuOrderSummarized>> searchMenuOrders(MenuOrderSearch searchCriteria, Pageable pageable) {
        var cb = entityManager.getCriteriaBuilder();
        var cq = cb.createQuery(MenuOrder.class);
        var root = cq.from(MenuOrder.class);
        if (searchCriteria.getOrderDate() != null) {
            LocalDate orderDate = searchCriteria.getOrderDate();
            LocalDateTime startDateTime = orderDate.atStartOfDay();
            LocalDateTime endDateTime = orderDate.plusDays(1).atStartOfDay();
            cq.where(cb.and(
                    cb.greaterThanOrEqualTo(root.get("orderDate"), startDateTime),
                    cb.lessThan(root.get("orderDate"), endDateTime)
            ));
        }

        if (searchCriteria.getStatus() != null) {
            cq.where(cb.equal(root.get("orderStatus"), searchCriteria.getStatus()));
        }
        if (searchCriteria.getCustomerId() != null) {
            cq.where(cb.equal(root.get("customer").get("id"), searchCriteria.getCustomerId()));
        }
        if (searchCriteria.getRoomId() != null) {
            cq.where(cb.equal(root.get("room").get("id"), searchCriteria.getRoomId()));
        }
        if (searchCriteria.getTableId() != null) {
            cq.where(cb.equal(root.get("table").get("id"), searchCriteria.getTableId()));
        }

        var orders = entityManager.createQuery(cq)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        var countQuery = cb.createQuery(Long.class);
        var countRoot = countQuery.from(MenuOrder.class);
        countQuery.select(cb.count(countRoot));

        long totalItems = entityManager.createQuery(countQuery).getSingleResult();

        List<MenuOrderSummarized> orderDTOs = orders.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new Paginate<>(orderDTOs, new PageInfo(
                totalItems > pageable.getOffset() + pageable.getPageSize(),
                pageable.getPageNumber() > 0,
                (int) Math.ceil((double) totalItems / pageable.getPageSize()),
                pageable.getPageNumber(),
                (int) totalItems
        ));
    }


    private MenuOrderSummarized mapToDTO(MenuOrder menuOrder) {
        var dto = new MenuOrderSummarized();

        BeanUtils.copyProperties(menuOrder, dto, "customer", "room", "table", "menu");

        if (menuOrder.getCustomer() != null) {
            var customerDTO = new CustomerDTO();
            BeanUtils.copyProperties(menuOrder.getCustomer(), customerDTO);
            dto.setCustomer(customerDTO);
        }

        if (menuOrder.getRoom() != null) {
            var roomDTO = new RoomDTO();
            BeanUtils.copyProperties(menuOrder.getRoom(), roomDTO);
            dto.setRoom(roomDTO);
        }

        if (menuOrder.getMenu() != null) {
            var menu = menuOrder.getMenu();

            var menuDTO = new MenuSummarized(
                    menu.getId(),
                    menu.getName(),
                    menu.getDescription(),
                    menu.getPrice(),
                    menu.getCategory().getId(),
                    menu.getCreatedAt(),
                    menu.getUpdatedAt(),
                    menu.getStatus()
            );

            dto.setMenu(menuDTO);
        }




        if (menuOrder.getTable() != null) {
            var tableDTO = new TableSummarized(
                    menuOrder.getTable().getId(),
                    menuOrder.getTable().getNumber(),
                    menuOrder.getTable().getCapacity(),
                    menuOrder.getTable().getStatus(),
                    menuOrder.getTable().getCreatedAt(),
                    menuOrder.getTable().getUpdatedAt()
            );
            dto.setTable(tableDTO);
        }

        return dto;
    }


}
