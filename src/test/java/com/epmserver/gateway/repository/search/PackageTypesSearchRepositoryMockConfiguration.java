package com.epmserver.gateway.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link PackageTypesSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PackageTypesSearchRepositoryMockConfiguration {

    @MockBean
    private PackageTypesSearchRepository mockPackageTypesSearchRepository;

}
