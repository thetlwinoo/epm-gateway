package com.epmserver.gateway.service.mapper;

import com.epmserver.gateway.domain.*;
import com.epmserver.gateway.service.dto.CustomersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Customers} and its DTO {@link CustomersDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CustomersMapper extends EntityMapper<CustomersDTO, Customers> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    CustomersDTO toDto(Customers customers);

    @Mapping(source = "userId", target = "user")
    Customers toEntity(CustomersDTO customersDTO);

    default Customers fromId(Long id) {
        if (id == null) {
            return null;
        }
        Customers customers = new Customers();
        customers.setId(id);
        return customers;
    }
}
