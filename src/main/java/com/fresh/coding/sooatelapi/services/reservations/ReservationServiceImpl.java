package com.fresh.coding.sooatelapi.services.reservations;

import com.fresh.coding.sooatelapi.dtos.customers.CustomerDTO;
import com.fresh.coding.sooatelapi.dtos.reservations.ReservationDTO;
import com.fresh.coding.sooatelapi.dtos.reservations.SaveReservationDTO;
import com.fresh.coding.sooatelapi.dtos.rooms.RoomDTO;
import com.fresh.coding.sooatelapi.dtos.tables.TableSummarized;
import com.fresh.coding.sooatelapi.entities.Reservation;
import com.fresh.coding.sooatelapi.entities.RestTable;
import com.fresh.coding.sooatelapi.entities.Room;
import com.fresh.coding.sooatelapi.enums.ReservationStatus;
import com.fresh.coding.sooatelapi.exceptions.HttpBadRequestException;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final RepositoryFactory repositoryFactory;

    @Override
    @Transactional
    public ReservationDTO saveReservation(SaveReservationDTO saveReservationDTO) {
        validateReservationInput(saveReservationDTO);
        var reservation = createReservation(saveReservationDTO);
        var savedReservation = repositoryFactory.getReservationRepository().save(reservation);
        return mapToDTO(savedReservation);
    }

    @Override
    public void deleteReservation(Long id) {
        var reservationRepository = repositoryFactory.getReservationRepository();
        if (!reservationRepository.existsById(id)) {
            throw new HttpNotFoundException("Reservation not found");
        }
        reservationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ReservationDTO updateReservation(Long id, SaveReservationDTO saveReservationDTO) {
        validateReservationInput(saveReservationDTO);
        var reservation = repositoryFactory.getReservationRepository().findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Reservation not found"));

        updateReservationFields(reservation, saveReservationDTO);
        var updatedReservation = repositoryFactory.getReservationRepository().save(reservation);
        return mapToDTO(updatedReservation);
    }

    @Override
    @Transactional
    public ReservationDTO updateReservationStatus(Long id, ReservationStatus status) {
        var reservation = repositoryFactory.getReservationRepository().findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Reservation not found"));

        reservation.setStatus(status);

        var updatedReservation = repositoryFactory.getReservationRepository().save(reservation);
        return mapToDTO(updatedReservation);
    }

    @Override
    public List<ReservationDTO> findAllReservations() {
        return repositoryFactory.getReservationRepository().findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private void validateReservationInput(SaveReservationDTO saveReservationDTO) {
        if ((saveReservationDTO.getRoomId() == null && saveReservationDTO.getTableId() == null) ||
                (saveReservationDTO.getRoomId() != null && saveReservationDTO.getTableId() != null)) {
            throw new HttpBadRequestException("Either roomId or tableId must be provided, but not both.");
        }
    }

    private Reservation createReservation(SaveReservationDTO saveReservationDTO) {
        var customer = repositoryFactory.getCustomerRepository().findById(saveReservationDTO.getCustomerId())
                .orElseThrow(() -> new HttpNotFoundException("Customer not found"));

        var reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setDescription(saveReservationDTO.getDescription());
        reservation.setReservationStart(saveReservationDTO.getReservationStart());
        reservation.setReservationEnd(saveReservationDTO.getReservationEnd());
        reservation.setStatus(saveReservationDTO.getStatus());

        if (saveReservationDTO.getRoomId() != null) {
            reservation.setRoom(findRoomById(saveReservationDTO.getRoomId()));
        }

        if (saveReservationDTO.getTableId() != null) {
            reservation.setTable(findTableById(saveReservationDTO.getTableId()));
        }

        return reservation;
    }

    private Room findRoomById(Long roomId) {
        return repositoryFactory.getRoomRepository().findById(roomId)
                .orElseThrow(() -> new HttpNotFoundException("Room not found"));
    }

    private RestTable findTableById(Long tableId) {
        return repositoryFactory.getTableRepository().findById(tableId)
                .orElseThrow(() -> new HttpNotFoundException("Table not found"));
    }

    private void updateReservationFields(Reservation reservation, SaveReservationDTO saveReservationDTO) {
        var customer = repositoryFactory.getCustomerRepository().findById(saveReservationDTO.getCustomerId())
                .orElseThrow(() -> new HttpNotFoundException("Customer not found"));

        reservation.setCustomer(customer);
        reservation.setDescription(saveReservationDTO.getDescription());
        reservation.setReservationStart(saveReservationDTO.getReservationStart());
        reservation.setReservationEnd(saveReservationDTO.getReservationEnd());
        reservation.setStatus(saveReservationDTO.getStatus());

        if (saveReservationDTO.getRoomId() != null) {
            reservation.setRoom(findRoomById(saveReservationDTO.getRoomId()));
        } else {
            reservation.setRoom(null);
        }

        if (saveReservationDTO.getTableId() != null) {
            reservation.setTable(findTableById(saveReservationDTO.getTableId()));
        } else {
            reservation.setTable(null);
        }
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
            );
            dto.setTable(tableSummarized);
        }

        return dto;
    }
}
