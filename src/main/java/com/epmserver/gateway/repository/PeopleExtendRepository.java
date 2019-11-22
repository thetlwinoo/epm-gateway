package com.epmserver.gateway.repository;

import com.epmserver.gateway.domain.People;

import java.util.Optional;

public interface PeopleExtendRepository extends PeopleRepository {
    Optional<People> findPeopleByEmailAddress(String email);
}
