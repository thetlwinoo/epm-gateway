package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.PhotosService;
import com.epmserver.gateway.domain.Photos;
import com.epmserver.gateway.repository.PhotosRepository;
import com.epmserver.gateway.repository.search.PhotosSearchRepository;
import com.epmserver.gateway.service.dto.PhotosDTO;
import com.epmserver.gateway.service.mapper.PhotosMapper;
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
 * Service Implementation for managing {@link Photos}.
 */
@Service
@Transactional
public class PhotosServiceImpl implements PhotosService {

    private final Logger log = LoggerFactory.getLogger(PhotosServiceImpl.class);

    private final PhotosRepository photosRepository;

    private final PhotosMapper photosMapper;

    private final PhotosSearchRepository photosSearchRepository;

    public PhotosServiceImpl(PhotosRepository photosRepository, PhotosMapper photosMapper, PhotosSearchRepository photosSearchRepository) {
        this.photosRepository = photosRepository;
        this.photosMapper = photosMapper;
        this.photosSearchRepository = photosSearchRepository;
    }

    /**
     * Save a photos.
     *
     * @param photosDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PhotosDTO save(PhotosDTO photosDTO) {
        log.debug("Request to save Photos : {}", photosDTO);
        Photos photos = photosMapper.toEntity(photosDTO);
        photos = photosRepository.save(photos);
        PhotosDTO result = photosMapper.toDto(photos);
        photosSearchRepository.save(photos);
        return result;
    }

    /**
     * Get all the photos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PhotosDTO> findAll() {
        log.debug("Request to get all Photos");
        return photosRepository.findAll().stream()
            .map(photosMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one photos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PhotosDTO> findOne(Long id) {
        log.debug("Request to get Photos : {}", id);
        return photosRepository.findById(id)
            .map(photosMapper::toDto);
    }

    /**
     * Delete the photos by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Photos : {}", id);
        photosRepository.deleteById(id);
        photosSearchRepository.deleteById(id);
    }

    /**
     * Search for the photos corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PhotosDTO> search(String query) {
        log.debug("Request to search Photos for query {}", query);
        return StreamSupport
            .stream(photosSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(photosMapper::toDto)
            .collect(Collectors.toList());
    }
}
