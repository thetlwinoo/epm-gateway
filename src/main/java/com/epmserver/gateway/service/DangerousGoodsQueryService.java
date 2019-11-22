package com.epmserver.gateway.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.epmserver.gateway.domain.DangerousGoods;
import com.epmserver.gateway.domain.*; // for static metamodels
import com.epmserver.gateway.repository.DangerousGoodsRepository;
import com.epmserver.gateway.repository.search.DangerousGoodsSearchRepository;
import com.epmserver.gateway.service.dto.DangerousGoodsCriteria;
import com.epmserver.gateway.service.dto.DangerousGoodsDTO;
import com.epmserver.gateway.service.mapper.DangerousGoodsMapper;

/**
 * Service for executing complex queries for {@link DangerousGoods} entities in the database.
 * The main input is a {@link DangerousGoodsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DangerousGoodsDTO} or a {@link Page} of {@link DangerousGoodsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DangerousGoodsQueryService extends QueryService<DangerousGoods> {

    private final Logger log = LoggerFactory.getLogger(DangerousGoodsQueryService.class);

    private final DangerousGoodsRepository dangerousGoodsRepository;

    private final DangerousGoodsMapper dangerousGoodsMapper;

    private final DangerousGoodsSearchRepository dangerousGoodsSearchRepository;

    public DangerousGoodsQueryService(DangerousGoodsRepository dangerousGoodsRepository, DangerousGoodsMapper dangerousGoodsMapper, DangerousGoodsSearchRepository dangerousGoodsSearchRepository) {
        this.dangerousGoodsRepository = dangerousGoodsRepository;
        this.dangerousGoodsMapper = dangerousGoodsMapper;
        this.dangerousGoodsSearchRepository = dangerousGoodsSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DangerousGoodsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DangerousGoodsDTO> findByCriteria(DangerousGoodsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DangerousGoods> specification = createSpecification(criteria);
        return dangerousGoodsMapper.toDto(dangerousGoodsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DangerousGoodsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DangerousGoodsDTO> findByCriteria(DangerousGoodsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DangerousGoods> specification = createSpecification(criteria);
        return dangerousGoodsRepository.findAll(specification, page)
            .map(dangerousGoodsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DangerousGoodsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DangerousGoods> specification = createSpecification(criteria);
        return dangerousGoodsRepository.count(specification);
    }

    /**
     * Function to convert {@link DangerousGoodsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DangerousGoods> createSpecification(DangerousGoodsCriteria criteria) {
        Specification<DangerousGoods> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DangerousGoods_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), DangerousGoods_.name));
            }
            if (criteria.getStockItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockItemId(),
                    root -> root.join(DangerousGoods_.stockItem, JoinType.LEFT).get(StockItems_.id)));
            }
        }
        return specification;
    }
}
