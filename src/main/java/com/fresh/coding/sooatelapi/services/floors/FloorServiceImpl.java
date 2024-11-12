    package com.fresh.coding.sooatelapi.services.floors;

    import com.fresh.coding.sooatelapi.dtos.floors.FloorDto;
    import com.fresh.coding.sooatelapi.dtos.floors.SaveFloorDto;
    import com.fresh.coding.sooatelapi.entities.Floor;
    import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
    import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class FloorServiceImpl implements FloorService {
        private final RepositoryFactory repositoryFactory;

        @Override
        public FloorDto createFloor(SaveFloorDto saveFloorDto) {
            var floorRepository = repositoryFactory.getFloorRepository();
            var floor = Floor.builder()
                    .floorNumber(saveFloorDto.getFloorNumber())
                    .description(saveFloorDto.getDescription())
                    .build();
            var savedFloor = floorRepository.save(floor);
            return convertToDto(savedFloor);
        }

        @Override
        public FloorDto getFloorById(Long id) {
            var floorRepository = repositoryFactory.getFloorRepository();
            var floor = floorRepository.findById(id)
                    .orElseThrow(() -> new HttpNotFoundException("Floor not found"));
            return convertToDto(floor);
        }

        @Override
        public List<FloorDto> getAllFloors() {
            var floorRepository = repositoryFactory.getFloorRepository();
            var floors = floorRepository.findAll();
            return floors.stream()
                    .map(this::convertToDto)
                    .toList();
        }

        @Override
        public FloorDto updateFloor(Long id, SaveFloorDto saveFloorDto) {
            var floorRepository = repositoryFactory.getFloorRepository();
            var floor = floorRepository.findById(id)
                    .orElseThrow(() -> new HttpNotFoundException("Floor not found"));
            floor.setFloorNumber(saveFloorDto.getFloorNumber());
            floor.setDescription(saveFloorDto.getDescription());
            var updatedFloor = floorRepository.save(floor);
            return convertToDto(updatedFloor);
        }

        @Override
        @Transactional
        public void deleteFloor(Long id) {
            var floorRepository = repositoryFactory.getFloorRepository();
            var roomRepository = repositoryFactory.getRoomRepository();
            if (!floorRepository.existsById(id)) {
                throw new HttpNotFoundException("Floor not found");
            }
            roomRepository.deleteRoomByFloorId(id);
            floorRepository.deleteById(id);
        }

        private FloorDto convertToDto(Floor floor) {
            return new FloorDto(
                    floor.getId(),
                    floor.getFloorNumber(),
                    floor.getDescription(),
                    floor.getCreatedAt(),
                    floor.getUpdatedAt()
            );
        }
    }
