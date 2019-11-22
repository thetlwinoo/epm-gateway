package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.domain.Photos;
import com.epmserver.gateway.domain.StockItems;
import com.epmserver.gateway.repository.PhotosExtendRepository;
import com.epmserver.gateway.repository.PhotosRepository;
import com.epmserver.gateway.repository.StockItemsRepository;
import com.epmserver.gateway.service.PhotosExtendService;
import com.epmserver.gateway.service.dto.PhotosDTO;
import com.epmserver.gateway.service.mapper.PhotosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PhotosExtendServiceImpl implements PhotosExtendService {

    private final Logger log = LoggerFactory.getLogger(PhotosExtendServiceImpl.class);
    private final PhotosRepository photosRepository;
    private final PhotosExtendRepository photosExtendRepository;
    private final StockItemsRepository stockItemsRepository;
    private final PhotosMapper photosMapper;

    public PhotosExtendServiceImpl(PhotosRepository photosRepository, PhotosExtendRepository photosExtendRepository, StockItemsRepository stockItemsRepository, PhotosMapper photosMapper) {
        this.photosRepository = photosRepository;
        this.photosExtendRepository = photosExtendRepository;
        this.stockItemsRepository = stockItemsRepository;
        this.photosMapper = photosMapper;
    }

    @Override
    public Optional<PhotosDTO> findByStockItemsAndAndDefaultIndIsTrue(Long stockItemId) {
        return photosExtendRepository.findByStockItemAndDefaultIndIsTrue(stockItemId)
            .map(photosMapper::toDto);
    }

    @Override
    public Optional<PhotosDTO> setDefault(Long photoId) {
        Optional<Photos> stockItemPhoto = photosRepository.findById(photoId);
        List<Photos> photosList = new ArrayList<>();

        if (stockItemPhoto.isPresent()) {
            Long stockItemId = stockItemPhoto.get().getStockItem().getId();
            photosList = photosExtendRepository.findAllByStockItemId(stockItemId);
        } else {
            throw new IllegalArgumentException("product photo not found");
        }

        for (Photos i : photosList) {
            if (i.getId() == photoId) {
                i.setDefaultInd(true);
                photosRepository.save(i);

                StockItems stockItems = stockItemsRepository.getOne(i.getStockItem().getId());
                stockItems.setThumbnailUrl(i.getThumbnailImageUrl());
                stockItemsRepository.save(stockItems);
            } else {
                if (i.isDefaultInd()) {
                    i.setDefaultInd(false);
                    photosRepository.save(i);
                }
            }
        }

        return stockItemPhoto.map(photosMapper::toDto);
    }

    @Override
    public List<PhotosDTO> getPhotosByStockItem(Long stockItemId) {
        return photosExtendRepository.findAllByStockItemId(stockItemId).stream()
            .map(photosMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<PhotosDTO> getOneByStockItem(Long stockItemId) {
        return photosExtendRepository.findFirstByStockItemId(stockItemId)
            .map(photosMapper::toDto);
    }
}
