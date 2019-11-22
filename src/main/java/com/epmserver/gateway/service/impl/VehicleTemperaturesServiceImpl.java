package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.VehicleTemperaturesService;
import com.epmserver.gateway.domain.VehicleTemperatures;
import com.epmserver.gateway.repository.VehicleTemperaturesRepository;
import com.epmserver.gateway.repository.search.VehicleTemperaturesSearchRepository;
import com.epmserver.gateway.service.dto.VehicleTemperaturesDTO;
import com.epmserver.gateway.service.mapper.VehicleTemperaturesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link VehicleTemperatures}.
 */
@Service
@Transactional
public class VehicleTemperaturesServiceImpl implements VehicleTemperaturesService {

    private final Logger log = LoggerFactory.getLogger(VehicleTemperaturesServiceImpl.class);

    private final VehicleTemperaturesRepository vehicleTemperaturesRepository;

    private final VehicleTemperaturesMapper vehicleTemperaturesMapper;

    private final VehicleTemperaturesSearchRepository vehicleTemperaturesSearchRepository;

    public VehicleTemperaturesServiceImpl(VehicleTemperaturesRepository vehicleTemperaturesRepository, VehicleTemperaturesMapper vehicleTemperaturesMapper, VehicleTemperaturesSearchRepository vehicleTemperaturesSearchRepository) {
        this.vehicleTemperaturesRepository = vehicleTemperaturesRepository;
        this.vehicleTemperaturesMapper = vehicleTemperaturesMapper;
        this.vehicleTemperaturesSearchRepository = vehicleTemperaturesSearchRepository;
    }

    /**
     * Save a vehicleTemperatures.
     *
     * @param vehicleTemperaturesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public VehicleTemperaturesDTO save(VehicleTemperaturesDTO vehicleTemperaturesDTO) {
        log.debug("Request to save VehicleTemperatures : {}", vehicleTemperaturesDTO);
        VehicleTemperatures vehicleTemperatures = vehicleTemperaturesMapper.toEntity(vehicleTemperaturesDTO);
        vehicleTemperatures = vehicleTemperaturesRepository.save(vehicleTemperatures);
        VehicleTemperaturesDTO result = vehicleTemperaturesMapper.toDto(vehicleTemperatures);
        vehicleTemperaturesSearchRepository.save(vehicleTemperatures);
        return result;
    }

    /**
     * Get all the vehicleTemperatures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<VehicleTemperaturesDTO> findAll() {
        log.debug("Request to get all VehicleTemperatures");
        return vehicleTemperaturesRepository.findAll().stream()
            .map(vehicleTemperaturesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one vehicleTemperatures by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VehicleTemperaturesDTO> findOne(Long id) {
        log.debug("Request to get VehicleTemperatures : {}", id);
        return vehicleTemperaturesRepository.findById(id)
            .map(vehicleTemperaturesMapper::toDto);
    }

    /**
     * Delete the vehicleTemperatures by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VehicleTemperatures : {}", id);
        vehicleTemperaturesRepository.deleteById(id);
        vehicleTemperaturesSearchRepository.deleteById(id);
    }

    /**
     * Search for the vehicleTemperatures corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<VehicleTemperaturesDTO> search(String query) {
        log.debug("Request to search VehicleTemperatures for query {}", query);
        return StreamSupport
            .stream(vehicleTemperaturesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(vehicleTemperaturesMapper::toDto)
            .collect(Collectors.toList());
    }
}
