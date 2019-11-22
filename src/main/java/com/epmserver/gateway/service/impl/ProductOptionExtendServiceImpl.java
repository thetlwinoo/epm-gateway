package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.domain.People;
import com.epmserver.gateway.domain.User;
import com.epmserver.gateway.repository.PeopleExtendRepository;
import com.epmserver.gateway.repository.ProductOptionExtendRepository;
import com.epmserver.gateway.repository.SuppliersExtendRepository;
import com.epmserver.gateway.repository.UserRepository;
import com.epmserver.gateway.service.ProductOptionExtendService;
import com.epmserver.gateway.service.dto.ProductOptionDTO;
import com.epmserver.gateway.service.dto.SuppliersDTO;
import com.epmserver.gateway.service.mapper.ProductOptionMapper;
import com.epmserver.gateway.service.mapper.SuppliersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductOptionExtendServiceImpl implements ProductOptionExtendService {

    private final Logger log = LoggerFactory.getLogger(ProductOptionExtendServiceImpl.class);
    private final PeopleExtendRepository peopleExtendRepository;
    private final SuppliersExtendRepository suppliersExtendRepository;
    private final SuppliersMapper suppliersMapper;
    private final ProductOptionExtendRepository productOptionExtendRepository;
    private final ProductOptionMapper productOptionMapper;
    private final UserRepository userRepository;


    public ProductOptionExtendServiceImpl(PeopleExtendRepository peopleExtendRepository, SuppliersExtendRepository suppliersExtendRepository, SuppliersMapper suppliersMapper, ProductOptionExtendRepository productOptionExtendRepository, ProductOptionMapper productOptionMapper, UserRepository userRepository) {
        this.peopleExtendRepository = peopleExtendRepository;
        this.suppliersExtendRepository = suppliersExtendRepository;
        this.suppliersMapper = suppliersMapper;
        this.productOptionExtendRepository = productOptionExtendRepository;
        this.productOptionMapper = productOptionMapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<ProductOptionDTO> getAllProductOptions(Long optionSetId, Principal principal) {
        log.debug("Request to get all ProductAttributes");
        Optional<User> userOptional = userRepository.findOneByLogin(principal.getName());
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }

        Optional<SuppliersDTO> suppliersDTOOptional = suppliersExtendRepository.findSuppliersByUserId(userOptional.get().getId())
            .map(suppliersMapper::toDto);

        return productOptionExtendRepository.findAllByProductOptionSetIdAndSupplierId(optionSetId, suppliersDTOOptional.get().getId()).stream()
            .map(productOptionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    private People getUserFromPrinciple(Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new IllegalArgumentException("Invalid access");
        }
        Optional<User> userOptional = userRepository.findOneByLogin(principal.getName());
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }

        Optional<People> people = peopleExtendRepository.findPeopleByEmailAddress(userOptional.get().getEmail());
        if (!people.isPresent()) {
            throw new IllegalArgumentException("People not found");
        }
        return people.get();
    }
}
