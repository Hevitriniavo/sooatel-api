package com.fresh.coding.sooatelapi.services.menus.orders;

import com.fresh.coding.sooatelapi.dtos.customers.CustomerDTO;
import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderSummarized;
import com.fresh.coding.sooatelapi.dtos.menus.MenuSummarized;
import com.fresh.coding.sooatelapi.dtos.pagination.PageInfo;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.rooms.RoomDTO;
import com.fresh.coding.sooatelapi.dtos.searchs.MenuOrderSearch;
import com.fresh.coding.sooatelapi.dtos.tables.TableSummarized;
import com.fresh.coding.sooatelapi.entities.Order;
import com.fresh.coding.sooatelapi.entities.OrderLine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchMenuOrderServiceImpl implements SearchMenuOrderService {
    private final EntityManager entityManager;

    @Override
    public Paginate<List<MenuOrderSummarized>> searchMenuOrders(MenuOrderSearch searchCriteria, Pageable pageable) {
        var cb = entityManager.getCriteriaBuilder();
        var cq = cb.createQuery(Order.class);
        var root = cq.from(Order.class);
        root.fetch("orderLines", JoinType.LEFT);
        List<Predicate> predicates = new ArrayList<>();

        if (searchCriteria.getOrderDate() != null) {
            LocalDate orderDate = searchCriteria.getOrderDate();
            LocalDateTime startDateTime = orderDate.atStartOfDay();
            LocalDateTime endDateTime = orderDate.plusDays(1).atStartOfDay();
            predicates.add(cb.between(root.get("orderDate"), startDateTime, endDateTime));
        }
        if (searchCriteria.getStatus() != null) {
            predicates.add(cb.equal(root.get("orderStatus"), searchCriteria.getStatus()));
        }
        if (searchCriteria.getCustomerId() != null) {
            predicates.add(cb.equal(root.get("customer").get("id"), searchCriteria.getCustomerId()));
        }
        if (searchCriteria.getRoomId() != null) {
            predicates.add(cb.equal(root.get("room").get("id"), searchCriteria.getRoomId()));
        }
        if (searchCriteria.getTableId() != null) {
            predicates.add(cb.equal(root.get("table").get("id"), searchCriteria.getTableId()));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.distinct(true);

        var orders = entityManager.createQuery(cq)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        var countQuery = cb.createQuery(Long.class);
        var countRoot = countQuery.from(Order.class);
        countQuery.select(cb.countDistinct(countRoot));
        List<Predicate> countPredicates = new ArrayList<>();
        if (searchCriteria.getOrderDate() != null) {
            LocalDate orderDate = searchCriteria.getOrderDate();
            LocalDateTime startDateTime = orderDate.atStartOfDay();
            LocalDateTime endDateTime = orderDate.plusDays(1).atStartOfDay();
            countPredicates.add(cb.between(countRoot.get("orderDate"), startDateTime, endDateTime));
        }
        if (searchCriteria.getStatus() != null) {
            countPredicates.add(cb.equal(countRoot.get("orderStatus"), searchCriteria.getStatus()));
        }
        if (searchCriteria.getCustomerId() != null) {
            countPredicates.add(cb.equal(countRoot.get("customer").get("id"), searchCriteria.getCustomerId()));
        }
        if (searchCriteria.getRoomId() != null) {
            countPredicates.add(cb.equal(countRoot.get("room").get("id"), searchCriteria.getRoomId()));
        }
        if (searchCriteria.getTableId() != null) {
            countPredicates.add(cb.equal(countRoot.get("table").get("id"), searchCriteria.getTableId()));
        }
        countQuery.where(countPredicates.toArray(new Predicate[0]));
        long totalItems = entityManager.createQuery(countQuery).getSingleResult();

        var orderLineDTOs = new ArrayList<MenuOrderSummarized>();
        for (var order : orders) {
            for (var orderLine : order.getOrderLines()) {
                orderLineDTOs.add(mapOrderLineToDTO(order, orderLine));
            }
        }

        return new Paginate<>(orderLineDTOs, new PageInfo(
                totalItems > pageable.getOffset() + pageable.getPageSize(),
                pageable.getPageNumber() > 0,
                (int) Math.ceil((double) totalItems / pageable.getPageSize()),
                pageable.getPageNumber(),
                (int) totalItems
        ));
    }

    @Override
    public List<MenuOrderSummarized> findAll() {
        var cb = entityManager.getCriteriaBuilder();
        var cq = cb.createQuery(Order.class);
        var root = cq.from(Order.class);
        root.fetch("orderLines", JoinType.LEFT);
        cq.select(root).distinct(true);
        var orders = entityManager.createQuery(cq).getResultList();

        var dtos = new ArrayList<MenuOrderSummarized>();
        for (var order : orders) {
            for (var orderLine : order.getOrderLines()) {
                dtos.add(mapOrderLineToDTO(order, orderLine));
            }
        }
        return dtos;
    }

    private MenuOrderSummarized mapOrderLineToDTO(Order order, OrderLine orderLine) {
        var dto = new MenuOrderSummarized();

        dto.setId(order.getId());

        if (order.getCustomer() != null) {
            var customerDTO = new CustomerDTO();
            BeanUtils.copyProperties(order.getCustomer(), customerDTO);
            dto.setCustomer(customerDTO);
        }

        if (order.getRoom() != null) {
            var roomDTO = new RoomDTO();
            BeanUtils.copyProperties(order.getRoom(), roomDTO);
            dto.setRoom(roomDTO);
        }

        if (order.getTable() != null) {
            var tableDTO = new TableSummarized(
                    order.getTable().getId(),
                    order.getTable().getNumber(),
                    order.getTable().getCapacity(),
                    order.getTable().getCreatedAt(),
                    order.getTable().getUpdatedAt()
            );
            dto.setTable(tableDTO);
        }

        dto.setOrderDate(order.getOrderDate());
        dto.setQuantity(orderLine.getQuantity().doubleValue());
        dto.setCost(orderLine.getTotalPrice().doubleValue());
        dto.setOrderStatus(order.getOrderStatus());

        if (orderLine.getMenu() != null) {
            var menu = orderLine.getMenu();
            var menuDTO = new MenuSummarized(
                    menu.getId(),
                    menu.getName(),
                    menu.getDescription(),
                    menu.getPrice(),
                    menu.getMenuGroup() != null ? menu.getMenuGroup().getId() : null,
                    menu.getCreatedAt(),
                    menu.getUpdatedAt(),
                    menu.getStatus()
            );
            dto.setMenu(menuDTO);
        }

        return dto;
    }
}
