package com.fresh.coding.sooatelapi.services.payments;

import com.fresh.coding.sooatelapi.dtos.customers.CustomerDTO;
import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderSummarized;
import com.fresh.coding.sooatelapi.dtos.menus.MenuSummarized;
import com.fresh.coding.sooatelapi.dtos.payments.InvoiceDTO;
import com.fresh.coding.sooatelapi.dtos.payments.PaymentSummarized;
import com.fresh.coding.sooatelapi.dtos.rooms.RoomDTO;
import com.fresh.coding.sooatelapi.dtos.tables.TableSummarized;
import com.fresh.coding.sooatelapi.entities.MenuOrder;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class InvoiceServiceImpl implements InvoiceService {
    private final RepositoryFactory repositoryFactory;

    @Override
    public InvoiceDTO getInvoice(Long paymentId) {
        var orderRepo = repositoryFactory.getMenuOrderRepository();
        var orders = orderRepo.findAllByPaymentId(paymentId);
        return mapToPaymentDTO(orders);
    }

    private InvoiceDTO mapToPaymentDTO(List<MenuOrder> menuOrders) {
        var invoice = new InvoiceDTO();
        if (!menuOrders.isEmpty()) {
            var payment = menuOrders.getFirst().getPayment();
            if (payment != null) {
                var paymentSummarized = new PaymentSummarized(
                        payment.getId(),
                        payment.getReservation()!= null ? payment.getReservation().getId() : null,
                        payment.getPaymentDate(),
                        payment.getUpdatedAt(),
                        payment.getAmount(),
                        payment.getPaymentMethod(),
                        payment.getStatus(),
                        payment.getDescription()
                );
                invoice.setPayment(paymentSummarized);
            }
        }

        invoice.setOrders(menuOrders.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList()));

        return invoice;
    }


    private MenuOrderSummarized mapToDTO(MenuOrder menuOrder) {
        var dto = new MenuOrderSummarized();

        BeanUtils.copyProperties(menuOrder, dto, "customer", "room", "table", "menu");

        if (menuOrder.getRoom() != null) {
            RoomDTO roomDTO = new RoomDTO();
            BeanUtils.copyProperties(menuOrder.getRoom(), roomDTO);
            dto.setRoom(roomDTO);
        }


        if (menuOrder.getCustomer() != null) {
            var customerDTO = new CustomerDTO();
            BeanUtils.copyProperties(menuOrder.getCustomer(), customerDTO);
            dto.setCustomer(customerDTO);
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


        if (menuOrder.getMenu() != null) {
            var menu = menuOrder.getMenu();

            MenuSummarized menuDTO = new MenuSummarized(
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


        return dto;
    }

}
