package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.PhotosDTO;

public interface StockItemsExtendService {
    PhotosDTO addPhotos(PhotosDTO photosDTO);
    PhotosDTO updatePhotos(PhotosDTO photosDTO);
}
