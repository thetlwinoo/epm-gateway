package com.epmserver.gateway.web.rest;

import com.epmserver.gateway.EpmgatewayApp;
import com.epmserver.gateway.domain.Photos;
import com.epmserver.gateway.domain.StockItems;
import com.epmserver.gateway.domain.ProductCategory;
import com.epmserver.gateway.repository.PhotosRepository;
import com.epmserver.gateway.repository.search.PhotosSearchRepository;
import com.epmserver.gateway.service.PhotosService;
import com.epmserver.gateway.service.dto.PhotosDTO;
import com.epmserver.gateway.service.mapper.PhotosMapper;
import com.epmserver.gateway.web.rest.errors.ExceptionTranslator;
import com.epmserver.gateway.service.dto.PhotosCriteria;
import com.epmserver.gateway.service.PhotosQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.epmserver.gateway.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PhotosResource} REST controller.
 */
@SpringBootTest(classes = EpmgatewayApp.class)
public class PhotosResourceIT {

    private static final String DEFAULT_THUMBNAIL_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINAL_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_BANNER_TALL_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_BANNER_TALL_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_BANNER_WIDE_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_BANNER_WIDE_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CIRCLE_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_CIRCLE_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SHARPENED_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_SHARPENED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SQUARE_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_SQUARE_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_WATERMARK_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_WATERMARK_IMAGE_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRIORITY = 1;
    private static final Integer UPDATED_PRIORITY = 2;
    private static final Integer SMALLER_PRIORITY = 1 - 1;

    private static final Boolean DEFAULT_DEFAULT_IND = false;
    private static final Boolean UPDATED_DEFAULT_IND = true;

    @Autowired
    private PhotosRepository photosRepository;

    @Autowired
    private PhotosMapper photosMapper;

    @Autowired
    private PhotosService photosService;

    /**
     * This repository is mocked in the com.epmserver.gateway.repository.search test package.
     *
     * @see com.epmserver.gateway.repository.search.PhotosSearchRepositoryMockConfiguration
     */
    @Autowired
    private PhotosSearchRepository mockPhotosSearchRepository;

    @Autowired
    private PhotosQueryService photosQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPhotosMockMvc;

    private Photos photos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PhotosResource photosResource = new PhotosResource(photosService, photosQueryService);
        this.restPhotosMockMvc = MockMvcBuilders.standaloneSetup(photosResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Photos createEntity(EntityManager em) {
        Photos photos = new Photos()
            .thumbnailImageUrl(DEFAULT_THUMBNAIL_IMAGE_URL)
            .originalImageUrl(DEFAULT_ORIGINAL_IMAGE_URL)
            .bannerTallImageUrl(DEFAULT_BANNER_TALL_IMAGE_URL)
            .bannerWideImageUrl(DEFAULT_BANNER_WIDE_IMAGE_URL)
            .circleImageUrl(DEFAULT_CIRCLE_IMAGE_URL)
            .sharpenedImageUrl(DEFAULT_SHARPENED_IMAGE_URL)
            .squareImageUrl(DEFAULT_SQUARE_IMAGE_URL)
            .watermarkImageUrl(DEFAULT_WATERMARK_IMAGE_URL)
            .priority(DEFAULT_PRIORITY)
            .defaultInd(DEFAULT_DEFAULT_IND);
        return photos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Photos createUpdatedEntity(EntityManager em) {
        Photos photos = new Photos()
            .thumbnailImageUrl(UPDATED_THUMBNAIL_IMAGE_URL)
            .originalImageUrl(UPDATED_ORIGINAL_IMAGE_URL)
            .bannerTallImageUrl(UPDATED_BANNER_TALL_IMAGE_URL)
            .bannerWideImageUrl(UPDATED_BANNER_WIDE_IMAGE_URL)
            .circleImageUrl(UPDATED_CIRCLE_IMAGE_URL)
            .sharpenedImageUrl(UPDATED_SHARPENED_IMAGE_URL)
            .squareImageUrl(UPDATED_SQUARE_IMAGE_URL)
            .watermarkImageUrl(UPDATED_WATERMARK_IMAGE_URL)
            .priority(UPDATED_PRIORITY)
            .defaultInd(UPDATED_DEFAULT_IND);
        return photos;
    }

    @BeforeEach
    public void initTest() {
        photos = createEntity(em);
    }

    @Test
    @Transactional
    public void createPhotos() throws Exception {
        int databaseSizeBeforeCreate = photosRepository.findAll().size();

        // Create the Photos
        PhotosDTO photosDTO = photosMapper.toDto(photos);
        restPhotosMockMvc.perform(post("/api/photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(photosDTO)))
            .andExpect(status().isCreated());

        // Validate the Photos in the database
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeCreate + 1);
        Photos testPhotos = photosList.get(photosList.size() - 1);
        assertThat(testPhotos.getThumbnailImageUrl()).isEqualTo(DEFAULT_THUMBNAIL_IMAGE_URL);
        assertThat(testPhotos.getOriginalImageUrl()).isEqualTo(DEFAULT_ORIGINAL_IMAGE_URL);
        assertThat(testPhotos.getBannerTallImageUrl()).isEqualTo(DEFAULT_BANNER_TALL_IMAGE_URL);
        assertThat(testPhotos.getBannerWideImageUrl()).isEqualTo(DEFAULT_BANNER_WIDE_IMAGE_URL);
        assertThat(testPhotos.getCircleImageUrl()).isEqualTo(DEFAULT_CIRCLE_IMAGE_URL);
        assertThat(testPhotos.getSharpenedImageUrl()).isEqualTo(DEFAULT_SHARPENED_IMAGE_URL);
        assertThat(testPhotos.getSquareImageUrl()).isEqualTo(DEFAULT_SQUARE_IMAGE_URL);
        assertThat(testPhotos.getWatermarkImageUrl()).isEqualTo(DEFAULT_WATERMARK_IMAGE_URL);
        assertThat(testPhotos.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testPhotos.isDefaultInd()).isEqualTo(DEFAULT_DEFAULT_IND);

        // Validate the Photos in Elasticsearch
        verify(mockPhotosSearchRepository, times(1)).save(testPhotos);
    }

    @Test
    @Transactional
    public void createPhotosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = photosRepository.findAll().size();

        // Create the Photos with an existing ID
        photos.setId(1L);
        PhotosDTO photosDTO = photosMapper.toDto(photos);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhotosMockMvc.perform(post("/api/photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(photosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Photos in the database
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeCreate);

        // Validate the Photos in Elasticsearch
        verify(mockPhotosSearchRepository, times(0)).save(photos);
    }


    @Test
    @Transactional
    public void getAllPhotos() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList
        restPhotosMockMvc.perform(get("/api/photos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(photos.getId().intValue())))
            .andExpect(jsonPath("$.[*].thumbnailImageUrl").value(hasItem(DEFAULT_THUMBNAIL_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].originalImageUrl").value(hasItem(DEFAULT_ORIGINAL_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].bannerTallImageUrl").value(hasItem(DEFAULT_BANNER_TALL_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].bannerWideImageUrl").value(hasItem(DEFAULT_BANNER_WIDE_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].circleImageUrl").value(hasItem(DEFAULT_CIRCLE_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].sharpenedImageUrl").value(hasItem(DEFAULT_SHARPENED_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].squareImageUrl").value(hasItem(DEFAULT_SQUARE_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].watermarkImageUrl").value(hasItem(DEFAULT_WATERMARK_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].defaultInd").value(hasItem(DEFAULT_DEFAULT_IND.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPhotos() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get the photos
        restPhotosMockMvc.perform(get("/api/photos/{id}", photos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(photos.getId().intValue()))
            .andExpect(jsonPath("$.thumbnailImageUrl").value(DEFAULT_THUMBNAIL_IMAGE_URL))
            .andExpect(jsonPath("$.originalImageUrl").value(DEFAULT_ORIGINAL_IMAGE_URL))
            .andExpect(jsonPath("$.bannerTallImageUrl").value(DEFAULT_BANNER_TALL_IMAGE_URL))
            .andExpect(jsonPath("$.bannerWideImageUrl").value(DEFAULT_BANNER_WIDE_IMAGE_URL))
            .andExpect(jsonPath("$.circleImageUrl").value(DEFAULT_CIRCLE_IMAGE_URL))
            .andExpect(jsonPath("$.sharpenedImageUrl").value(DEFAULT_SHARPENED_IMAGE_URL))
            .andExpect(jsonPath("$.squareImageUrl").value(DEFAULT_SQUARE_IMAGE_URL))
            .andExpect(jsonPath("$.watermarkImageUrl").value(DEFAULT_WATERMARK_IMAGE_URL))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.defaultInd").value(DEFAULT_DEFAULT_IND.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllPhotosByThumbnailImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where thumbnailImageUrl equals to DEFAULT_THUMBNAIL_IMAGE_URL
        defaultPhotosShouldBeFound("thumbnailImageUrl.equals=" + DEFAULT_THUMBNAIL_IMAGE_URL);

        // Get all the photosList where thumbnailImageUrl equals to UPDATED_THUMBNAIL_IMAGE_URL
        defaultPhotosShouldNotBeFound("thumbnailImageUrl.equals=" + UPDATED_THUMBNAIL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByThumbnailImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where thumbnailImageUrl not equals to DEFAULT_THUMBNAIL_IMAGE_URL
        defaultPhotosShouldNotBeFound("thumbnailImageUrl.notEquals=" + DEFAULT_THUMBNAIL_IMAGE_URL);

        // Get all the photosList where thumbnailImageUrl not equals to UPDATED_THUMBNAIL_IMAGE_URL
        defaultPhotosShouldBeFound("thumbnailImageUrl.notEquals=" + UPDATED_THUMBNAIL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByThumbnailImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where thumbnailImageUrl in DEFAULT_THUMBNAIL_IMAGE_URL or UPDATED_THUMBNAIL_IMAGE_URL
        defaultPhotosShouldBeFound("thumbnailImageUrl.in=" + DEFAULT_THUMBNAIL_IMAGE_URL + "," + UPDATED_THUMBNAIL_IMAGE_URL);

        // Get all the photosList where thumbnailImageUrl equals to UPDATED_THUMBNAIL_IMAGE_URL
        defaultPhotosShouldNotBeFound("thumbnailImageUrl.in=" + UPDATED_THUMBNAIL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByThumbnailImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where thumbnailImageUrl is not null
        defaultPhotosShouldBeFound("thumbnailImageUrl.specified=true");

        // Get all the photosList where thumbnailImageUrl is null
        defaultPhotosShouldNotBeFound("thumbnailImageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosByThumbnailImageUrlContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where thumbnailImageUrl contains DEFAULT_THUMBNAIL_IMAGE_URL
        defaultPhotosShouldBeFound("thumbnailImageUrl.contains=" + DEFAULT_THUMBNAIL_IMAGE_URL);

        // Get all the photosList where thumbnailImageUrl contains UPDATED_THUMBNAIL_IMAGE_URL
        defaultPhotosShouldNotBeFound("thumbnailImageUrl.contains=" + UPDATED_THUMBNAIL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByThumbnailImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where thumbnailImageUrl does not contain DEFAULT_THUMBNAIL_IMAGE_URL
        defaultPhotosShouldNotBeFound("thumbnailImageUrl.doesNotContain=" + DEFAULT_THUMBNAIL_IMAGE_URL);

        // Get all the photosList where thumbnailImageUrl does not contain UPDATED_THUMBNAIL_IMAGE_URL
        defaultPhotosShouldBeFound("thumbnailImageUrl.doesNotContain=" + UPDATED_THUMBNAIL_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllPhotosByOriginalImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where originalImageUrl equals to DEFAULT_ORIGINAL_IMAGE_URL
        defaultPhotosShouldBeFound("originalImageUrl.equals=" + DEFAULT_ORIGINAL_IMAGE_URL);

        // Get all the photosList where originalImageUrl equals to UPDATED_ORIGINAL_IMAGE_URL
        defaultPhotosShouldNotBeFound("originalImageUrl.equals=" + UPDATED_ORIGINAL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByOriginalImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where originalImageUrl not equals to DEFAULT_ORIGINAL_IMAGE_URL
        defaultPhotosShouldNotBeFound("originalImageUrl.notEquals=" + DEFAULT_ORIGINAL_IMAGE_URL);

        // Get all the photosList where originalImageUrl not equals to UPDATED_ORIGINAL_IMAGE_URL
        defaultPhotosShouldBeFound("originalImageUrl.notEquals=" + UPDATED_ORIGINAL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByOriginalImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where originalImageUrl in DEFAULT_ORIGINAL_IMAGE_URL or UPDATED_ORIGINAL_IMAGE_URL
        defaultPhotosShouldBeFound("originalImageUrl.in=" + DEFAULT_ORIGINAL_IMAGE_URL + "," + UPDATED_ORIGINAL_IMAGE_URL);

        // Get all the photosList where originalImageUrl equals to UPDATED_ORIGINAL_IMAGE_URL
        defaultPhotosShouldNotBeFound("originalImageUrl.in=" + UPDATED_ORIGINAL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByOriginalImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where originalImageUrl is not null
        defaultPhotosShouldBeFound("originalImageUrl.specified=true");

        // Get all the photosList where originalImageUrl is null
        defaultPhotosShouldNotBeFound("originalImageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosByOriginalImageUrlContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where originalImageUrl contains DEFAULT_ORIGINAL_IMAGE_URL
        defaultPhotosShouldBeFound("originalImageUrl.contains=" + DEFAULT_ORIGINAL_IMAGE_URL);

        // Get all the photosList where originalImageUrl contains UPDATED_ORIGINAL_IMAGE_URL
        defaultPhotosShouldNotBeFound("originalImageUrl.contains=" + UPDATED_ORIGINAL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByOriginalImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where originalImageUrl does not contain DEFAULT_ORIGINAL_IMAGE_URL
        defaultPhotosShouldNotBeFound("originalImageUrl.doesNotContain=" + DEFAULT_ORIGINAL_IMAGE_URL);

        // Get all the photosList where originalImageUrl does not contain UPDATED_ORIGINAL_IMAGE_URL
        defaultPhotosShouldBeFound("originalImageUrl.doesNotContain=" + UPDATED_ORIGINAL_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllPhotosByBannerTallImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerTallImageUrl equals to DEFAULT_BANNER_TALL_IMAGE_URL
        defaultPhotosShouldBeFound("bannerTallImageUrl.equals=" + DEFAULT_BANNER_TALL_IMAGE_URL);

        // Get all the photosList where bannerTallImageUrl equals to UPDATED_BANNER_TALL_IMAGE_URL
        defaultPhotosShouldNotBeFound("bannerTallImageUrl.equals=" + UPDATED_BANNER_TALL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByBannerTallImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerTallImageUrl not equals to DEFAULT_BANNER_TALL_IMAGE_URL
        defaultPhotosShouldNotBeFound("bannerTallImageUrl.notEquals=" + DEFAULT_BANNER_TALL_IMAGE_URL);

        // Get all the photosList where bannerTallImageUrl not equals to UPDATED_BANNER_TALL_IMAGE_URL
        defaultPhotosShouldBeFound("bannerTallImageUrl.notEquals=" + UPDATED_BANNER_TALL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByBannerTallImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerTallImageUrl in DEFAULT_BANNER_TALL_IMAGE_URL or UPDATED_BANNER_TALL_IMAGE_URL
        defaultPhotosShouldBeFound("bannerTallImageUrl.in=" + DEFAULT_BANNER_TALL_IMAGE_URL + "," + UPDATED_BANNER_TALL_IMAGE_URL);

        // Get all the photosList where bannerTallImageUrl equals to UPDATED_BANNER_TALL_IMAGE_URL
        defaultPhotosShouldNotBeFound("bannerTallImageUrl.in=" + UPDATED_BANNER_TALL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByBannerTallImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerTallImageUrl is not null
        defaultPhotosShouldBeFound("bannerTallImageUrl.specified=true");

        // Get all the photosList where bannerTallImageUrl is null
        defaultPhotosShouldNotBeFound("bannerTallImageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosByBannerTallImageUrlContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerTallImageUrl contains DEFAULT_BANNER_TALL_IMAGE_URL
        defaultPhotosShouldBeFound("bannerTallImageUrl.contains=" + DEFAULT_BANNER_TALL_IMAGE_URL);

        // Get all the photosList where bannerTallImageUrl contains UPDATED_BANNER_TALL_IMAGE_URL
        defaultPhotosShouldNotBeFound("bannerTallImageUrl.contains=" + UPDATED_BANNER_TALL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByBannerTallImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerTallImageUrl does not contain DEFAULT_BANNER_TALL_IMAGE_URL
        defaultPhotosShouldNotBeFound("bannerTallImageUrl.doesNotContain=" + DEFAULT_BANNER_TALL_IMAGE_URL);

        // Get all the photosList where bannerTallImageUrl does not contain UPDATED_BANNER_TALL_IMAGE_URL
        defaultPhotosShouldBeFound("bannerTallImageUrl.doesNotContain=" + UPDATED_BANNER_TALL_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllPhotosByBannerWideImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerWideImageUrl equals to DEFAULT_BANNER_WIDE_IMAGE_URL
        defaultPhotosShouldBeFound("bannerWideImageUrl.equals=" + DEFAULT_BANNER_WIDE_IMAGE_URL);

        // Get all the photosList where bannerWideImageUrl equals to UPDATED_BANNER_WIDE_IMAGE_URL
        defaultPhotosShouldNotBeFound("bannerWideImageUrl.equals=" + UPDATED_BANNER_WIDE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByBannerWideImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerWideImageUrl not equals to DEFAULT_BANNER_WIDE_IMAGE_URL
        defaultPhotosShouldNotBeFound("bannerWideImageUrl.notEquals=" + DEFAULT_BANNER_WIDE_IMAGE_URL);

        // Get all the photosList where bannerWideImageUrl not equals to UPDATED_BANNER_WIDE_IMAGE_URL
        defaultPhotosShouldBeFound("bannerWideImageUrl.notEquals=" + UPDATED_BANNER_WIDE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByBannerWideImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerWideImageUrl in DEFAULT_BANNER_WIDE_IMAGE_URL or UPDATED_BANNER_WIDE_IMAGE_URL
        defaultPhotosShouldBeFound("bannerWideImageUrl.in=" + DEFAULT_BANNER_WIDE_IMAGE_URL + "," + UPDATED_BANNER_WIDE_IMAGE_URL);

        // Get all the photosList where bannerWideImageUrl equals to UPDATED_BANNER_WIDE_IMAGE_URL
        defaultPhotosShouldNotBeFound("bannerWideImageUrl.in=" + UPDATED_BANNER_WIDE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByBannerWideImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerWideImageUrl is not null
        defaultPhotosShouldBeFound("bannerWideImageUrl.specified=true");

        // Get all the photosList where bannerWideImageUrl is null
        defaultPhotosShouldNotBeFound("bannerWideImageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosByBannerWideImageUrlContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerWideImageUrl contains DEFAULT_BANNER_WIDE_IMAGE_URL
        defaultPhotosShouldBeFound("bannerWideImageUrl.contains=" + DEFAULT_BANNER_WIDE_IMAGE_URL);

        // Get all the photosList where bannerWideImageUrl contains UPDATED_BANNER_WIDE_IMAGE_URL
        defaultPhotosShouldNotBeFound("bannerWideImageUrl.contains=" + UPDATED_BANNER_WIDE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByBannerWideImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where bannerWideImageUrl does not contain DEFAULT_BANNER_WIDE_IMAGE_URL
        defaultPhotosShouldNotBeFound("bannerWideImageUrl.doesNotContain=" + DEFAULT_BANNER_WIDE_IMAGE_URL);

        // Get all the photosList where bannerWideImageUrl does not contain UPDATED_BANNER_WIDE_IMAGE_URL
        defaultPhotosShouldBeFound("bannerWideImageUrl.doesNotContain=" + UPDATED_BANNER_WIDE_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllPhotosByCircleImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where circleImageUrl equals to DEFAULT_CIRCLE_IMAGE_URL
        defaultPhotosShouldBeFound("circleImageUrl.equals=" + DEFAULT_CIRCLE_IMAGE_URL);

        // Get all the photosList where circleImageUrl equals to UPDATED_CIRCLE_IMAGE_URL
        defaultPhotosShouldNotBeFound("circleImageUrl.equals=" + UPDATED_CIRCLE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByCircleImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where circleImageUrl not equals to DEFAULT_CIRCLE_IMAGE_URL
        defaultPhotosShouldNotBeFound("circleImageUrl.notEquals=" + DEFAULT_CIRCLE_IMAGE_URL);

        // Get all the photosList where circleImageUrl not equals to UPDATED_CIRCLE_IMAGE_URL
        defaultPhotosShouldBeFound("circleImageUrl.notEquals=" + UPDATED_CIRCLE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByCircleImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where circleImageUrl in DEFAULT_CIRCLE_IMAGE_URL or UPDATED_CIRCLE_IMAGE_URL
        defaultPhotosShouldBeFound("circleImageUrl.in=" + DEFAULT_CIRCLE_IMAGE_URL + "," + UPDATED_CIRCLE_IMAGE_URL);

        // Get all the photosList where circleImageUrl equals to UPDATED_CIRCLE_IMAGE_URL
        defaultPhotosShouldNotBeFound("circleImageUrl.in=" + UPDATED_CIRCLE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByCircleImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where circleImageUrl is not null
        defaultPhotosShouldBeFound("circleImageUrl.specified=true");

        // Get all the photosList where circleImageUrl is null
        defaultPhotosShouldNotBeFound("circleImageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosByCircleImageUrlContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where circleImageUrl contains DEFAULT_CIRCLE_IMAGE_URL
        defaultPhotosShouldBeFound("circleImageUrl.contains=" + DEFAULT_CIRCLE_IMAGE_URL);

        // Get all the photosList where circleImageUrl contains UPDATED_CIRCLE_IMAGE_URL
        defaultPhotosShouldNotBeFound("circleImageUrl.contains=" + UPDATED_CIRCLE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByCircleImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where circleImageUrl does not contain DEFAULT_CIRCLE_IMAGE_URL
        defaultPhotosShouldNotBeFound("circleImageUrl.doesNotContain=" + DEFAULT_CIRCLE_IMAGE_URL);

        // Get all the photosList where circleImageUrl does not contain UPDATED_CIRCLE_IMAGE_URL
        defaultPhotosShouldBeFound("circleImageUrl.doesNotContain=" + UPDATED_CIRCLE_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllPhotosBySharpenedImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where sharpenedImageUrl equals to DEFAULT_SHARPENED_IMAGE_URL
        defaultPhotosShouldBeFound("sharpenedImageUrl.equals=" + DEFAULT_SHARPENED_IMAGE_URL);

        // Get all the photosList where sharpenedImageUrl equals to UPDATED_SHARPENED_IMAGE_URL
        defaultPhotosShouldNotBeFound("sharpenedImageUrl.equals=" + UPDATED_SHARPENED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosBySharpenedImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where sharpenedImageUrl not equals to DEFAULT_SHARPENED_IMAGE_URL
        defaultPhotosShouldNotBeFound("sharpenedImageUrl.notEquals=" + DEFAULT_SHARPENED_IMAGE_URL);

        // Get all the photosList where sharpenedImageUrl not equals to UPDATED_SHARPENED_IMAGE_URL
        defaultPhotosShouldBeFound("sharpenedImageUrl.notEquals=" + UPDATED_SHARPENED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosBySharpenedImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where sharpenedImageUrl in DEFAULT_SHARPENED_IMAGE_URL or UPDATED_SHARPENED_IMAGE_URL
        defaultPhotosShouldBeFound("sharpenedImageUrl.in=" + DEFAULT_SHARPENED_IMAGE_URL + "," + UPDATED_SHARPENED_IMAGE_URL);

        // Get all the photosList where sharpenedImageUrl equals to UPDATED_SHARPENED_IMAGE_URL
        defaultPhotosShouldNotBeFound("sharpenedImageUrl.in=" + UPDATED_SHARPENED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosBySharpenedImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where sharpenedImageUrl is not null
        defaultPhotosShouldBeFound("sharpenedImageUrl.specified=true");

        // Get all the photosList where sharpenedImageUrl is null
        defaultPhotosShouldNotBeFound("sharpenedImageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosBySharpenedImageUrlContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where sharpenedImageUrl contains DEFAULT_SHARPENED_IMAGE_URL
        defaultPhotosShouldBeFound("sharpenedImageUrl.contains=" + DEFAULT_SHARPENED_IMAGE_URL);

        // Get all the photosList where sharpenedImageUrl contains UPDATED_SHARPENED_IMAGE_URL
        defaultPhotosShouldNotBeFound("sharpenedImageUrl.contains=" + UPDATED_SHARPENED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosBySharpenedImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where sharpenedImageUrl does not contain DEFAULT_SHARPENED_IMAGE_URL
        defaultPhotosShouldNotBeFound("sharpenedImageUrl.doesNotContain=" + DEFAULT_SHARPENED_IMAGE_URL);

        // Get all the photosList where sharpenedImageUrl does not contain UPDATED_SHARPENED_IMAGE_URL
        defaultPhotosShouldBeFound("sharpenedImageUrl.doesNotContain=" + UPDATED_SHARPENED_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllPhotosBySquareImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where squareImageUrl equals to DEFAULT_SQUARE_IMAGE_URL
        defaultPhotosShouldBeFound("squareImageUrl.equals=" + DEFAULT_SQUARE_IMAGE_URL);

        // Get all the photosList where squareImageUrl equals to UPDATED_SQUARE_IMAGE_URL
        defaultPhotosShouldNotBeFound("squareImageUrl.equals=" + UPDATED_SQUARE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosBySquareImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where squareImageUrl not equals to DEFAULT_SQUARE_IMAGE_URL
        defaultPhotosShouldNotBeFound("squareImageUrl.notEquals=" + DEFAULT_SQUARE_IMAGE_URL);

        // Get all the photosList where squareImageUrl not equals to UPDATED_SQUARE_IMAGE_URL
        defaultPhotosShouldBeFound("squareImageUrl.notEquals=" + UPDATED_SQUARE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosBySquareImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where squareImageUrl in DEFAULT_SQUARE_IMAGE_URL or UPDATED_SQUARE_IMAGE_URL
        defaultPhotosShouldBeFound("squareImageUrl.in=" + DEFAULT_SQUARE_IMAGE_URL + "," + UPDATED_SQUARE_IMAGE_URL);

        // Get all the photosList where squareImageUrl equals to UPDATED_SQUARE_IMAGE_URL
        defaultPhotosShouldNotBeFound("squareImageUrl.in=" + UPDATED_SQUARE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosBySquareImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where squareImageUrl is not null
        defaultPhotosShouldBeFound("squareImageUrl.specified=true");

        // Get all the photosList where squareImageUrl is null
        defaultPhotosShouldNotBeFound("squareImageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosBySquareImageUrlContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where squareImageUrl contains DEFAULT_SQUARE_IMAGE_URL
        defaultPhotosShouldBeFound("squareImageUrl.contains=" + DEFAULT_SQUARE_IMAGE_URL);

        // Get all the photosList where squareImageUrl contains UPDATED_SQUARE_IMAGE_URL
        defaultPhotosShouldNotBeFound("squareImageUrl.contains=" + UPDATED_SQUARE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosBySquareImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where squareImageUrl does not contain DEFAULT_SQUARE_IMAGE_URL
        defaultPhotosShouldNotBeFound("squareImageUrl.doesNotContain=" + DEFAULT_SQUARE_IMAGE_URL);

        // Get all the photosList where squareImageUrl does not contain UPDATED_SQUARE_IMAGE_URL
        defaultPhotosShouldBeFound("squareImageUrl.doesNotContain=" + UPDATED_SQUARE_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllPhotosByWatermarkImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where watermarkImageUrl equals to DEFAULT_WATERMARK_IMAGE_URL
        defaultPhotosShouldBeFound("watermarkImageUrl.equals=" + DEFAULT_WATERMARK_IMAGE_URL);

        // Get all the photosList where watermarkImageUrl equals to UPDATED_WATERMARK_IMAGE_URL
        defaultPhotosShouldNotBeFound("watermarkImageUrl.equals=" + UPDATED_WATERMARK_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByWatermarkImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where watermarkImageUrl not equals to DEFAULT_WATERMARK_IMAGE_URL
        defaultPhotosShouldNotBeFound("watermarkImageUrl.notEquals=" + DEFAULT_WATERMARK_IMAGE_URL);

        // Get all the photosList where watermarkImageUrl not equals to UPDATED_WATERMARK_IMAGE_URL
        defaultPhotosShouldBeFound("watermarkImageUrl.notEquals=" + UPDATED_WATERMARK_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByWatermarkImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where watermarkImageUrl in DEFAULT_WATERMARK_IMAGE_URL or UPDATED_WATERMARK_IMAGE_URL
        defaultPhotosShouldBeFound("watermarkImageUrl.in=" + DEFAULT_WATERMARK_IMAGE_URL + "," + UPDATED_WATERMARK_IMAGE_URL);

        // Get all the photosList where watermarkImageUrl equals to UPDATED_WATERMARK_IMAGE_URL
        defaultPhotosShouldNotBeFound("watermarkImageUrl.in=" + UPDATED_WATERMARK_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByWatermarkImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where watermarkImageUrl is not null
        defaultPhotosShouldBeFound("watermarkImageUrl.specified=true");

        // Get all the photosList where watermarkImageUrl is null
        defaultPhotosShouldNotBeFound("watermarkImageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhotosByWatermarkImageUrlContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where watermarkImageUrl contains DEFAULT_WATERMARK_IMAGE_URL
        defaultPhotosShouldBeFound("watermarkImageUrl.contains=" + DEFAULT_WATERMARK_IMAGE_URL);

        // Get all the photosList where watermarkImageUrl contains UPDATED_WATERMARK_IMAGE_URL
        defaultPhotosShouldNotBeFound("watermarkImageUrl.contains=" + UPDATED_WATERMARK_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPhotosByWatermarkImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where watermarkImageUrl does not contain DEFAULT_WATERMARK_IMAGE_URL
        defaultPhotosShouldNotBeFound("watermarkImageUrl.doesNotContain=" + DEFAULT_WATERMARK_IMAGE_URL);

        // Get all the photosList where watermarkImageUrl does not contain UPDATED_WATERMARK_IMAGE_URL
        defaultPhotosShouldBeFound("watermarkImageUrl.doesNotContain=" + UPDATED_WATERMARK_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllPhotosByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where priority equals to DEFAULT_PRIORITY
        defaultPhotosShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the photosList where priority equals to UPDATED_PRIORITY
        defaultPhotosShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllPhotosByPriorityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where priority not equals to DEFAULT_PRIORITY
        defaultPhotosShouldNotBeFound("priority.notEquals=" + DEFAULT_PRIORITY);

        // Get all the photosList where priority not equals to UPDATED_PRIORITY
        defaultPhotosShouldBeFound("priority.notEquals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllPhotosByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultPhotosShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the photosList where priority equals to UPDATED_PRIORITY
        defaultPhotosShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllPhotosByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where priority is not null
        defaultPhotosShouldBeFound("priority.specified=true");

        // Get all the photosList where priority is null
        defaultPhotosShouldNotBeFound("priority.specified=false");
    }

    @Test
    @Transactional
    public void getAllPhotosByPriorityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where priority is greater than or equal to DEFAULT_PRIORITY
        defaultPhotosShouldBeFound("priority.greaterThanOrEqual=" + DEFAULT_PRIORITY);

        // Get all the photosList where priority is greater than or equal to UPDATED_PRIORITY
        defaultPhotosShouldNotBeFound("priority.greaterThanOrEqual=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllPhotosByPriorityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where priority is less than or equal to DEFAULT_PRIORITY
        defaultPhotosShouldBeFound("priority.lessThanOrEqual=" + DEFAULT_PRIORITY);

        // Get all the photosList where priority is less than or equal to SMALLER_PRIORITY
        defaultPhotosShouldNotBeFound("priority.lessThanOrEqual=" + SMALLER_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllPhotosByPriorityIsLessThanSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where priority is less than DEFAULT_PRIORITY
        defaultPhotosShouldNotBeFound("priority.lessThan=" + DEFAULT_PRIORITY);

        // Get all the photosList where priority is less than UPDATED_PRIORITY
        defaultPhotosShouldBeFound("priority.lessThan=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllPhotosByPriorityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where priority is greater than DEFAULT_PRIORITY
        defaultPhotosShouldNotBeFound("priority.greaterThan=" + DEFAULT_PRIORITY);

        // Get all the photosList where priority is greater than SMALLER_PRIORITY
        defaultPhotosShouldBeFound("priority.greaterThan=" + SMALLER_PRIORITY);
    }


    @Test
    @Transactional
    public void getAllPhotosByDefaultIndIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where defaultInd equals to DEFAULT_DEFAULT_IND
        defaultPhotosShouldBeFound("defaultInd.equals=" + DEFAULT_DEFAULT_IND);

        // Get all the photosList where defaultInd equals to UPDATED_DEFAULT_IND
        defaultPhotosShouldNotBeFound("defaultInd.equals=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllPhotosByDefaultIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where defaultInd not equals to DEFAULT_DEFAULT_IND
        defaultPhotosShouldNotBeFound("defaultInd.notEquals=" + DEFAULT_DEFAULT_IND);

        // Get all the photosList where defaultInd not equals to UPDATED_DEFAULT_IND
        defaultPhotosShouldBeFound("defaultInd.notEquals=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllPhotosByDefaultIndIsInShouldWork() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where defaultInd in DEFAULT_DEFAULT_IND or UPDATED_DEFAULT_IND
        defaultPhotosShouldBeFound("defaultInd.in=" + DEFAULT_DEFAULT_IND + "," + UPDATED_DEFAULT_IND);

        // Get all the photosList where defaultInd equals to UPDATED_DEFAULT_IND
        defaultPhotosShouldNotBeFound("defaultInd.in=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllPhotosByDefaultIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        // Get all the photosList where defaultInd is not null
        defaultPhotosShouldBeFound("defaultInd.specified=true");

        // Get all the photosList where defaultInd is null
        defaultPhotosShouldNotBeFound("defaultInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllPhotosByStockItemIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);
        StockItems stockItem = StockItemsResourceIT.createEntity(em);
        em.persist(stockItem);
        em.flush();
        photos.setStockItem(stockItem);
        photosRepository.saveAndFlush(photos);
        Long stockItemId = stockItem.getId();

        // Get all the photosList where stockItem equals to stockItemId
        defaultPhotosShouldBeFound("stockItemId.equals=" + stockItemId);

        // Get all the photosList where stockItem equals to stockItemId + 1
        defaultPhotosShouldNotBeFound("stockItemId.equals=" + (stockItemId + 1));
    }


    @Test
    @Transactional
    public void getAllPhotosByProductCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);
        ProductCategory productCategory = ProductCategoryResourceIT.createEntity(em);
        em.persist(productCategory);
        em.flush();
        photos.setProductCategory(productCategory);
        photosRepository.saveAndFlush(photos);
        Long productCategoryId = productCategory.getId();

        // Get all the photosList where productCategory equals to productCategoryId
        defaultPhotosShouldBeFound("productCategoryId.equals=" + productCategoryId);

        // Get all the photosList where productCategory equals to productCategoryId + 1
        defaultPhotosShouldNotBeFound("productCategoryId.equals=" + (productCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPhotosShouldBeFound(String filter) throws Exception {
        restPhotosMockMvc.perform(get("/api/photos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(photos.getId().intValue())))
            .andExpect(jsonPath("$.[*].thumbnailImageUrl").value(hasItem(DEFAULT_THUMBNAIL_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].originalImageUrl").value(hasItem(DEFAULT_ORIGINAL_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].bannerTallImageUrl").value(hasItem(DEFAULT_BANNER_TALL_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].bannerWideImageUrl").value(hasItem(DEFAULT_BANNER_WIDE_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].circleImageUrl").value(hasItem(DEFAULT_CIRCLE_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].sharpenedImageUrl").value(hasItem(DEFAULT_SHARPENED_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].squareImageUrl").value(hasItem(DEFAULT_SQUARE_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].watermarkImageUrl").value(hasItem(DEFAULT_WATERMARK_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].defaultInd").value(hasItem(DEFAULT_DEFAULT_IND.booleanValue())));

        // Check, that the count call also returns 1
        restPhotosMockMvc.perform(get("/api/photos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPhotosShouldNotBeFound(String filter) throws Exception {
        restPhotosMockMvc.perform(get("/api/photos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPhotosMockMvc.perform(get("/api/photos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPhotos() throws Exception {
        // Get the photos
        restPhotosMockMvc.perform(get("/api/photos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhotos() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        int databaseSizeBeforeUpdate = photosRepository.findAll().size();

        // Update the photos
        Photos updatedPhotos = photosRepository.findById(photos.getId()).get();
        // Disconnect from session so that the updates on updatedPhotos are not directly saved in db
        em.detach(updatedPhotos);
        updatedPhotos
            .thumbnailImageUrl(UPDATED_THUMBNAIL_IMAGE_URL)
            .originalImageUrl(UPDATED_ORIGINAL_IMAGE_URL)
            .bannerTallImageUrl(UPDATED_BANNER_TALL_IMAGE_URL)
            .bannerWideImageUrl(UPDATED_BANNER_WIDE_IMAGE_URL)
            .circleImageUrl(UPDATED_CIRCLE_IMAGE_URL)
            .sharpenedImageUrl(UPDATED_SHARPENED_IMAGE_URL)
            .squareImageUrl(UPDATED_SQUARE_IMAGE_URL)
            .watermarkImageUrl(UPDATED_WATERMARK_IMAGE_URL)
            .priority(UPDATED_PRIORITY)
            .defaultInd(UPDATED_DEFAULT_IND);
        PhotosDTO photosDTO = photosMapper.toDto(updatedPhotos);

        restPhotosMockMvc.perform(put("/api/photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(photosDTO)))
            .andExpect(status().isOk());

        // Validate the Photos in the database
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeUpdate);
        Photos testPhotos = photosList.get(photosList.size() - 1);
        assertThat(testPhotos.getThumbnailImageUrl()).isEqualTo(UPDATED_THUMBNAIL_IMAGE_URL);
        assertThat(testPhotos.getOriginalImageUrl()).isEqualTo(UPDATED_ORIGINAL_IMAGE_URL);
        assertThat(testPhotos.getBannerTallImageUrl()).isEqualTo(UPDATED_BANNER_TALL_IMAGE_URL);
        assertThat(testPhotos.getBannerWideImageUrl()).isEqualTo(UPDATED_BANNER_WIDE_IMAGE_URL);
        assertThat(testPhotos.getCircleImageUrl()).isEqualTo(UPDATED_CIRCLE_IMAGE_URL);
        assertThat(testPhotos.getSharpenedImageUrl()).isEqualTo(UPDATED_SHARPENED_IMAGE_URL);
        assertThat(testPhotos.getSquareImageUrl()).isEqualTo(UPDATED_SQUARE_IMAGE_URL);
        assertThat(testPhotos.getWatermarkImageUrl()).isEqualTo(UPDATED_WATERMARK_IMAGE_URL);
        assertThat(testPhotos.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testPhotos.isDefaultInd()).isEqualTo(UPDATED_DEFAULT_IND);

        // Validate the Photos in Elasticsearch
        verify(mockPhotosSearchRepository, times(1)).save(testPhotos);
    }

    @Test
    @Transactional
    public void updateNonExistingPhotos() throws Exception {
        int databaseSizeBeforeUpdate = photosRepository.findAll().size();

        // Create the Photos
        PhotosDTO photosDTO = photosMapper.toDto(photos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhotosMockMvc.perform(put("/api/photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(photosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Photos in the database
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Photos in Elasticsearch
        verify(mockPhotosSearchRepository, times(0)).save(photos);
    }

    @Test
    @Transactional
    public void deletePhotos() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);

        int databaseSizeBeforeDelete = photosRepository.findAll().size();

        // Delete the photos
        restPhotosMockMvc.perform(delete("/api/photos/{id}", photos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Photos> photosList = photosRepository.findAll();
        assertThat(photosList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Photos in Elasticsearch
        verify(mockPhotosSearchRepository, times(1)).deleteById(photos.getId());
    }

    @Test
    @Transactional
    public void searchPhotos() throws Exception {
        // Initialize the database
        photosRepository.saveAndFlush(photos);
        when(mockPhotosSearchRepository.search(queryStringQuery("id:" + photos.getId())))
            .thenReturn(Collections.singletonList(photos));
        // Search the photos
        restPhotosMockMvc.perform(get("/api/_search/photos?query=id:" + photos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(photos.getId().intValue())))
            .andExpect(jsonPath("$.[*].thumbnailImageUrl").value(hasItem(DEFAULT_THUMBNAIL_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].originalImageUrl").value(hasItem(DEFAULT_ORIGINAL_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].bannerTallImageUrl").value(hasItem(DEFAULT_BANNER_TALL_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].bannerWideImageUrl").value(hasItem(DEFAULT_BANNER_WIDE_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].circleImageUrl").value(hasItem(DEFAULT_CIRCLE_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].sharpenedImageUrl").value(hasItem(DEFAULT_SHARPENED_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].squareImageUrl").value(hasItem(DEFAULT_SQUARE_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].watermarkImageUrl").value(hasItem(DEFAULT_WATERMARK_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].defaultInd").value(hasItem(DEFAULT_DEFAULT_IND.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Photos.class);
        Photos photos1 = new Photos();
        photos1.setId(1L);
        Photos photos2 = new Photos();
        photos2.setId(photos1.getId());
        assertThat(photos1).isEqualTo(photos2);
        photos2.setId(2L);
        assertThat(photos1).isNotEqualTo(photos2);
        photos1.setId(null);
        assertThat(photos1).isNotEqualTo(photos2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhotosDTO.class);
        PhotosDTO photosDTO1 = new PhotosDTO();
        photosDTO1.setId(1L);
        PhotosDTO photosDTO2 = new PhotosDTO();
        assertThat(photosDTO1).isNotEqualTo(photosDTO2);
        photosDTO2.setId(photosDTO1.getId());
        assertThat(photosDTO1).isEqualTo(photosDTO2);
        photosDTO2.setId(2L);
        assertThat(photosDTO1).isNotEqualTo(photosDTO2);
        photosDTO1.setId(null);
        assertThat(photosDTO1).isNotEqualTo(photosDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(photosMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(photosMapper.fromId(null)).isNull();
    }
}
