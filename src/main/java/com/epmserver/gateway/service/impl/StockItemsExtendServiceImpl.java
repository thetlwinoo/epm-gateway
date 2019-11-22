package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.domain.Photos;
import com.epmserver.gateway.domain.StockItems;
import com.epmserver.gateway.repository.PhotosRepository;
import com.epmserver.gateway.repository.StockItemsRepository;
import com.epmserver.gateway.service.StockItemsExtendService;
import com.epmserver.gateway.service.dto.PhotosDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StockItemsExtendServiceImpl implements StockItemsExtendService {

    private final Logger log = LoggerFactory.getLogger(StockItemsExtendServiceImpl.class);
    private final StockItemsRepository stockItemsRepository;
    private final PhotosRepository photosRepository;

    public StockItemsExtendServiceImpl(StockItemsRepository stockItemsRepository, PhotosRepository photosRepository) {
        this.stockItemsRepository = stockItemsRepository;
        this.photosRepository = photosRepository;
    }

    @Override
    public PhotosDTO addPhotos(PhotosDTO photosDTO) {
        try {
            StockItems stockItems = stockItemsRepository.getOne(photosDTO.getStockItemId());
            Photos photos = new Photos();
            photos.setStockItem(stockItems);
            photos.setId(photosDTO.getId());
            photos.setOriginalImageUrl(photosDTO.getOriginalImageUrl());
//            photos.setOriginalPhotoBlobContentType(photosDTO.getOriginalPhotoBlobContentType());
            photos.setThumbnailImageUrl(photosDTO.getThumbnailImageUrl());
//            photos.setThumbnailPhotoBlobContentType(photosDTO.getThumbnailPhotoBlobContentType());
            stockItems.getPhotoLists().add(photos);
            stockItemsRepository.save(stockItems);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

        return photosDTO;
    }

    @Override
    public PhotosDTO updatePhotos(PhotosDTO photosDTO) {
        try {
            StockItems stockItems = stockItemsRepository.getOne(photosDTO.getStockItemId());
            for (Photos photo : stockItems.getPhotoLists()) {
                if (photo.getId().equals(photosDTO.getId())) {
                    photo.setOriginalImageUrl(photosDTO.getOriginalImageUrl());
//                    photo.setOriginalPhotoBlobContentType(photosDTO.getOriginalPhotoBlobContentType());
                    photo.setThumbnailImageUrl(photosDTO.getThumbnailImageUrl());
//                    photo.setThumbnailPhotoBlobContentType(photosDTO.getThumbnailPhotoBlobContentType());
                    break;
                }
            }
            stockItemsRepository.save(stockItems);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

        return photosDTO;
    }
}
