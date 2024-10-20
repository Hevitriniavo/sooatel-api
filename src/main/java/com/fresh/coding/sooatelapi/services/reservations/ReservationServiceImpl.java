package com.fresh.coding.sooatelapi.services.reservations;

import com.fresh.coding.sooatelapi.dtos.customers.CustomerDTO;
import com.fresh.coding.sooatelapi.dtos.reservations.ReservationDTO;
import com.fresh.coding.sooatelapi.dtos.reservations.SaveReservationDTO;
import com.fresh.coding.sooatelapi.dtos.rooms.RoomDTO;
import com.fresh.coding.sooatelapi.dtos.tables.TableSummarized;
import com.fresh.coding.sooatelapi.entities.Reservation;
import com.fresh.coding.sooatelapi.exceptions.HttpBadRequestException;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final RepositoryFactory repositoryFactory;

    @Override
    @Transactional
    public ReservationDTO saveReservation(SaveReservationDTO saveReservationDTO) {
        if ((saveReservationDTO.getRoomId() == null && saveReservationDTO.getTableId() == null) ||
                (saveReservationDTO.getRoomId() != null && saveReservationDTO.getTableId() != null)) {
            throw new HttpBadRequestException("Either roomId or tableId must be provided, but not both.");
        }

        var customer = repositoryFactory.getCustomerRepository().findById(saveReservationDTO.getCustomerId())
                .orElseThrow(() -> new HttpNotFoundException("Customer not found"));

        var reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setDescription(saveReservationDTO.getDescription());
        reservation.setReservationStart(saveReservationDTO.getReservationStart());
        reservation.setReservationEnd(saveReservationDTO.getReservationEnd());
        reservation.setStatus(saveReservationDTO.getStatus());

        if (saveReservationDTO.getRoomId() != null) {
            var room = repositoryFactory.getRoomRepository().findById(saveReservationDTO.getRoomId())
                    .orElseThrow(() -> new HttpNotFoundException("Room not found"));
            reservation.setRoom(room);
        }

        if (saveReservationDTO.getTableId() != null) {
            var table = repositoryFactory.getTableRepository().findById(saveReservationDTO.getTableId())
                    .orElseThrow(() -> new HttpNotFoundException("Table not found"));
            reservation.setTable(table);
        }

        var savedReservation = repositoryFactory.getReservationRepository().save(reservation);
        return mapToDTO(savedReservation);
    }

    private ReservationDTO mapToDTO(Reservation reservation) {
        var dto = new ReservationDTO();
        BeanUtils.copyProperties(reservation, dto, "customer", "room", "table");

        if (reservation.getCustomer() != null) {
            CustomerDTO customerDTO = new CustomerDTO();
            BeanUtils.copyProperties(reservation.getCustomer(), customerDTO);
            dto.setCustomer(customerDTO);
        }

        if (reservation.getRoom() != null) {
            RoomDTO roomDTO = new RoomDTO();
            BeanUtils.copyProperties(reservation.getRoom(), roomDTO);
            dto.setRoom(roomDTO);
        }

        if (reservation.getTable() != null) {
            var tableSummarized = new TableSummarized(
                    reservation.getTable().getId(),
                    reservation.getTable().getNumber(),
                    reservation.getTable().getCapacity(),
                    reservation.getTable().getStatus(),
                    reservation.getTable().getCreatedAt(),
                    reservation.getTable().getUpdatedAt()
            ) ;
            dto.setTable(tableSummarized);
        }

        return dto;
    }
}
