package com.fresh.coding.sooatelapi.services.reservations;

import com.fresh.coding.sooatelapi.dtos.customers.CustomerDTO;
import com.fresh.coding.sooatelapi.dtos.reservations.ReservationDTO;
import com.fresh.coding.sooatelapi.dtos.reservations.SaveReservationDTO;
import com.fresh.coding.sooatelapi.dtos.rooms.RoomDTO;
import com.fresh.coding.sooatelapi.dtos.tables.TableSummarized;
import com.fresh.coding.sooatelapi.entities.Customer;
import com.fresh.coding.sooatelapi.entities.Reservation;
import com.fresh.coding.sooatelapi.entities.Room;
import com.fresh.coding.sooatelapi.entities.TableEntity;
import com.fresh.coding.sooatelapi.enums.ReservationStatus;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final RepositoryFactory repositoryFactory;

    @Transactional
    @Override
    public void deleteReservation(Long id) {
        var reservationRepo = repositoryFactory.getReservationRepository();
        var reservation = reservationRepo.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Réservation introuvable avec l'identifiant : " + id));
        if (reservation.getRoom() != null) {
            var room = reservation.getRoom();
            if (room.getReservations() != null) {
                room.getReservations().remove(reservation);
            }
            repositoryFactory.getRoomRepository().save(room);
        }
        if (reservation.getTable() != null) {
            var table = reservation.getTable();
            if (table.getReservations() != null) {
                table.getReservations().remove(reservation);
            }
            repositoryFactory.getTableRepository().save(table);
        }
        reservationRepo.delete(reservation);
    }

    @Transactional
    @Override
    public ReservationDTO saveReservation(SaveReservationDTO dto) {
        if (dto == null || dto.getCustomer() == null) {
            throw new IllegalArgumentException("La réservation ou les informations du client sont manquantes.");
        }
        var customer = getOrCreateCustomer(dto);
        Room room = getFirstRoom(dto.getRoomIds());
        TableEntity table = getFirstTable(dto.getTableIds());
        var reservation = Reservation.builder()
                .customer(customer)
                .room(room)
                .table(table)
                .reservationStart(dto.getReservationStart())
                .reservationEnd(dto.getReservationEnd())
                .status(ReservationStatus.valueOf(dto.getStatus()))
                .description(dto.getDescription())
                .build();
        if (room != null) {
            if (room.getReservations() == null) {
                room.setReservations(new ArrayList<>());
            }
            room.getReservations().add(reservation);
        }
        if (table != null) {
            if (table.getReservations() == null) {
                table.setReservations(new ArrayList<>());
            }
            table.getReservations().add(reservation);
        }
        var saved = repositoryFactory.getReservationRepository().save(reservation);
        return mapToDTO(saved);
    }

    @Transactional
    @Override
    public ReservationDTO updateReservation(Long id, SaveReservationDTO dto) {
        var reservation = repositoryFactory.getReservationRepository().findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Réservation introuvable avec l'identifiant : " + id));
        if (reservation.getRoom() != null && reservation.getRoom().getReservations() != null) {
            reservation.getRoom().getReservations().remove(reservation);
        }
        if (reservation.getTable() != null && reservation.getTable().getReservations() != null) {
            reservation.getTable().getReservations().remove(reservation);
        }
        updateReservationFields(reservation, dto);
        if (reservation.getRoom() != null) {
            if (reservation.getRoom().getReservations() == null) {
                reservation.getRoom().setReservations(new ArrayList<>());
            }
            reservation.getRoom().getReservations().add(reservation);
        }
        if (reservation.getTable() != null) {
            if (reservation.getTable().getReservations() == null) {
                reservation.getTable().setReservations(new ArrayList<>());
            }
            reservation.getTable().getReservations().add(reservation);
        }
        var updated = repositoryFactory.getReservationRepository().save(reservation);
        return mapToDTO(updated);
    }

    @Transactional
    @Override
    public ReservationDTO updateReservationStatus(Long id, ReservationStatus status) {
        var reservation = repositoryFactory.getReservationRepository().findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Impossible de mettre à jour le statut : réservation introuvable avec l'identifiant : " + id));
        reservation.setStatus(status);
        var updated = repositoryFactory.getReservationRepository().save(reservation);
        return mapToDTO(updated);
    }

    @Override
    public List<ReservationDTO> findAllReservations() {
        return repositoryFactory.getReservationRepository().findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private void updateReservationFields(Reservation reservation, SaveReservationDTO dto) {
        if (dto == null || dto.getCustomer() == null) {
            throw new IllegalArgumentException("Les données de réservation ou du client sont manquantes.");
        }
        var customer = getOrCreateCustomer(dto);
        Room room = getFirstRoom(dto.getRoomIds());
        TableEntity table = getFirstTable(dto.getTableIds());
        reservation.setCustomer(customer);
        reservation.setRoom(room);
        reservation.setTable(table);
        reservation.setDescription(dto.getDescription());
        reservation.setReservationStart(dto.getReservationStart());
        reservation.setReservationEnd(dto.getReservationEnd());
    }

    private Customer getOrCreateCustomer(SaveReservationDTO dto) {
        var customerDTO = dto.getCustomer();
        var repo = repositoryFactory.getCustomerRepository();
        return customerDTO.getCustomerId() != null
                ? repo.findById(customerDTO.getCustomerId())
                .orElseGet(() -> repo.save(Customer.builder()
                        .name(customerDTO.getName())
                        .phoneNumber(customerDTO.getPhoneNumber())
                        .build()))
                : repo.save(Customer.builder()
                .name(customerDTO.getName())
                .phoneNumber(customerDTO.getPhoneNumber())
                .build());
    }

    private Room getFirstRoom(List<Long> roomIds) {
        if (roomIds == null || roomIds.isEmpty()) return null;
        Long id = roomIds.get(0);
        return repositoryFactory.getRoomRepository().findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Chambre introuvable avec l'identifiant : " + id));
    }

    private TableEntity getFirstTable(List<Long> tableIds) {
        if (tableIds == null || tableIds.isEmpty()) return null;
        Long id = tableIds.get(0);
        return repositoryFactory.getTableRepository().findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Table introuvable avec l'identifiant : " + id));
    }

    private ReservationDTO mapToDTO(Reservation reservation) {
        var dto = new ReservationDTO();
        BeanUtils.copyProperties(reservation, dto, "customer", "room", "table");
        if (reservation.getCustomer() != null) {
            var customerDTO = new CustomerDTO();
            BeanUtils.copyProperties(reservation.getCustomer(), customerDTO);
            dto.setCustomer(customerDTO);
        }
        if (reservation.getRoom() != null) {
            var roomDTO = new RoomDTO();
            BeanUtils.copyProperties(reservation.getRoom(), roomDTO);
            dto.setRooms(List.of(roomDTO));
        }
        if (reservation.getTable() != null) {
            var tableDTO = new TableSummarized(
                    reservation.getTable().getId(),
                    reservation.getTable().getNumber(),
                    reservation.getTable().getCapacity(),
                    reservation.getTable().getCreatedAt(),
                    reservation.getTable().getUpdatedAt()
            );
            dto.setTables(List.of(tableDTO));
        }
        return dto;
    }
}
