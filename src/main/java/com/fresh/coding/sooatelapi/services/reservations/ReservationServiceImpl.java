package com.fresh.coding.sooatelapi.services.reservations;

import com.fresh.coding.sooatelapi.dtos.customers.CustomerDTO;
import com.fresh.coding.sooatelapi.dtos.reservations.ReservationDTO;
import com.fresh.coding.sooatelapi.dtos.reservations.SaveReservationDTO;
import com.fresh.coding.sooatelapi.entities.Customer;
import com.fresh.coding.sooatelapi.entities.Reservation;
import com.fresh.coding.sooatelapi.entities.RestTable;
import com.fresh.coding.sooatelapi.entities.Room;
import com.fresh.coding.sooatelapi.enums.ReservationStatus;
import com.fresh.coding.sooatelapi.enums.RoomStatus;
import com.fresh.coding.sooatelapi.enums.TableStatus;
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


    @Transactional
    @Override
    public void deleteReservation(Long id) {
        var reservationRepository = repositoryFactory.getReservationRepository();
        var tableRepository = repositoryFactory.getTableRepository();
        var roomRepository = repositoryFactory.getRoomRepository();
        var reservation = reservationRepository.findById(id);

        if (reservation.isEmpty()) {
            throw new HttpNotFoundException("Reservation not found");
        }

        var existingReservation = reservation.get();

        existingReservation.getRooms().forEach(room -> {
            room.setReservation(null);
            room.setStatus(RoomStatus.AVAILABLE);
            roomRepository.save(room);
        });

        existingReservation.getTables().forEach(table -> {
            table.setReservation(null);
            table.setStatus(TableStatus.AVAILABLE);
            tableRepository.save(table);
        });
        reservationRepository.deleteById(id);
    }


    @Override
    @Transactional
    public ReservationDTO updateReservation(Long id, SaveReservationDTO saveReservationDTO) {
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


    @Override
    @Transactional
    public ReservationDTO saveReservation(SaveReservationDTO saveReservationDTO) {
        if (saveReservationDTO == null || saveReservationDTO.getCustomer() == null) {
            throw new IllegalArgumentException("La réservation ou les informations du client sont nulles");
        }

        var customerRepo = repositoryFactory.getCustomerRepository();
        var customer = saveReservationDTO.getCustomer().getCustomerId() != null
                ? customerRepo.findById(saveReservationDTO.getCustomer().getCustomerId())
                .orElseGet(() -> customerRepo.save(Customer.builder()
                        .name(saveReservationDTO.getCustomer().getName())
                        .phoneNumber(saveReservationDTO.getCustomer().getPhoneNumber())
                        .build()))
                : customerRepo.save(Customer.builder()
                .name(saveReservationDTO.getCustomer().getName())
                .phoneNumber(saveReservationDTO.getCustomer().getPhoneNumber())
                .build());

        List<Room> rooms = repositoryFactory.getRoomRepository().findAllById(saveReservationDTO.getRoomIds());
        List<RestTable> tables = repositoryFactory.getTableRepository().findAllById(saveReservationDTO.getTableIds());

        Reservation reservation = Reservation.builder()
                .customer(customer)
                .rooms(rooms)
                .tables(tables)
                .reservationStart(saveReservationDTO.getReservationStart())
                .reservationEnd(saveReservationDTO.getReservationEnd())
                .status(ReservationStatus.valueOf(saveReservationDTO.getStatus()))
                .description(saveReservationDTO.getDescription())
                .build();
        var savedReservation = repositoryFactory.getReservationRepository().save(reservation);
        return mapToDTO(savedReservation);
    }


    private void updateReservationFields(Reservation reservation, SaveReservationDTO saveReservationDTO) {
        if (saveReservationDTO == null || saveReservationDTO.getCustomer() == null) {
            throw new IllegalArgumentException("La réservation ou les informations du client sont nulles");
        }

        var customerRepo = repositoryFactory.getCustomerRepository();
        var customerDTO = saveReservationDTO.getCustomer();

        var customer = customerRepo.findById(customerDTO.getCustomerId())
                .orElseGet(() -> {
                    var toSave = Customer.builder()
                            .name(customerDTO.getName())
                            .phoneNumber(customerDTO.getPhoneNumber())
                            .build();

                    if (customerDTO.getCustomerId() != null) {
                        toSave.setId(customerDTO.getCustomerId());
                    }

                    return customerRepo.save(toSave);
                });

        reservation.setCustomer(customer);
        reservation.setDescription(saveReservationDTO.getDescription());
        reservation.setReservationStart(saveReservationDTO.getReservationStart());
        reservation.setReservationEnd(saveReservationDTO.getReservationEnd());

    }

    private ReservationDTO mapToDTO(Reservation reservation) {
        var dto = new ReservationDTO();
        BeanUtils.copyProperties(reservation, dto, "customer", "room", "table");

        if (reservation.getCustomer() != null) {
            CustomerDTO customerDTO = new CustomerDTO();
            BeanUtils.copyProperties(reservation.getCustomer(), customerDTO);
            dto.setCustomer(customerDTO);
        }

        return dto;
    }
}
