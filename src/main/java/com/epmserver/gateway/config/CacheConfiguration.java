package com.epmserver.gateway.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.epmserver.gateway.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.epmserver.gateway.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.epmserver.gateway.domain.User.class.getName());
            createCache(cm, com.epmserver.gateway.domain.Authority.class.getName());
            createCache(cm, com.epmserver.gateway.domain.User.class.getName() + ".authorities");
            createCache(cm, com.epmserver.gateway.domain.AddressTypes.class.getName());
            createCache(cm, com.epmserver.gateway.domain.Culture.class.getName());
            createCache(cm, com.epmserver.gateway.domain.Addresses.class.getName());
            createCache(cm, com.epmserver.gateway.domain.BusinessEntityAddress.class.getName());
            createCache(cm, com.epmserver.gateway.domain.ShipMethod.class.getName());
            createCache(cm, com.epmserver.gateway.domain.PersonEmailAddress.class.getName());
            createCache(cm, com.epmserver.gateway.domain.PersonPhone.class.getName());
            createCache(cm, com.epmserver.gateway.domain.PhoneNumberType.class.getName());
            createCache(cm, com.epmserver.gateway.domain.ContactType.class.getName());
            createCache(cm, com.epmserver.gateway.domain.BusinessEntityContact.class.getName());
            createCache(cm, com.epmserver.gateway.domain.Countries.class.getName());
            createCache(cm, com.epmserver.gateway.domain.StateProvinces.class.getName());
            createCache(cm, com.epmserver.gateway.domain.Cities.class.getName());
            createCache(cm, com.epmserver.gateway.domain.SystemParameters.class.getName());
            createCache(cm, com.epmserver.gateway.domain.TransactionTypes.class.getName());
            createCache(cm, com.epmserver.gateway.domain.People.class.getName());
            createCache(cm, com.epmserver.gateway.domain.DeliveryMethods.class.getName());
            createCache(cm, com.epmserver.gateway.domain.SupplierCategories.class.getName());
            createCache(cm, com.epmserver.gateway.domain.Suppliers.class.getName());
            createCache(cm, com.epmserver.gateway.domain.SupplierTransactions.class.getName());
            createCache(cm, com.epmserver.gateway.domain.CurrencyRate.class.getName());
            createCache(cm, com.epmserver.gateway.domain.PurchaseOrders.class.getName());
            createCache(cm, com.epmserver.gateway.domain.PurchaseOrders.class.getName() + ".purchaseOrderLineLists");
            createCache(cm, com.epmserver.gateway.domain.PurchaseOrderLines.class.getName());
            createCache(cm, com.epmserver.gateway.domain.BuyingGroups.class.getName());
            createCache(cm, com.epmserver.gateway.domain.CustomerCategories.class.getName());
            createCache(cm, com.epmserver.gateway.domain.Customers.class.getName());
            createCache(cm, com.epmserver.gateway.domain.CustomerTransactions.class.getName());
            createCache(cm, com.epmserver.gateway.domain.PaymentTransactions.class.getName());
            createCache(cm, com.epmserver.gateway.domain.InvoiceLines.class.getName());
            createCache(cm, com.epmserver.gateway.domain.Invoices.class.getName());
            createCache(cm, com.epmserver.gateway.domain.Invoices.class.getName() + ".invoiceLineLists");
            createCache(cm, com.epmserver.gateway.domain.OrderLines.class.getName());
            createCache(cm, com.epmserver.gateway.domain.Orders.class.getName());
            createCache(cm, com.epmserver.gateway.domain.Orders.class.getName() + ".orderLineLists");
            createCache(cm, com.epmserver.gateway.domain.SpecialDeals.class.getName());
            createCache(cm, com.epmserver.gateway.domain.SpecialDeals.class.getName() + ".cartDiscounts");
            createCache(cm, com.epmserver.gateway.domain.SpecialDeals.class.getName() + ".orderDiscounts");
            createCache(cm, com.epmserver.gateway.domain.ColdRoomTemperatures.class.getName());
            createCache(cm, com.epmserver.gateway.domain.PackageTypes.class.getName());
            createCache(cm, com.epmserver.gateway.domain.Products.class.getName());
            createCache(cm, com.epmserver.gateway.domain.Products.class.getName() + ".stockItemLists");
            createCache(cm, com.epmserver.gateway.domain.BarcodeTypes.class.getName());
            createCache(cm, com.epmserver.gateway.domain.StockItems.class.getName());
            createCache(cm, com.epmserver.gateway.domain.StockItems.class.getName() + ".photoLists");
            createCache(cm, com.epmserver.gateway.domain.StockItems.class.getName() + ".dangerousGoodLists");
            createCache(cm, com.epmserver.gateway.domain.StockItems.class.getName() + ".specialDiscounts");
            createCache(cm, com.epmserver.gateway.domain.StockItemTemp.class.getName());
            createCache(cm, com.epmserver.gateway.domain.UploadTransactions.class.getName());
            createCache(cm, com.epmserver.gateway.domain.UploadTransactions.class.getName() + ".importDocumentLists");
            createCache(cm, com.epmserver.gateway.domain.UploadTransactions.class.getName() + ".stockItemTempLists");
            createCache(cm, com.epmserver.gateway.domain.UploadActionTypes.class.getName());
            createCache(cm, com.epmserver.gateway.domain.StockItemTransactions.class.getName());
            createCache(cm, com.epmserver.gateway.domain.StockItemHoldings.class.getName());
            createCache(cm, com.epmserver.gateway.domain.WarrantyTypes.class.getName());
            createCache(cm, com.epmserver.gateway.domain.ProductAttribute.class.getName());
            createCache(cm, com.epmserver.gateway.domain.ProductAttributeSet.class.getName());
            createCache(cm, com.epmserver.gateway.domain.ProductOption.class.getName());
            createCache(cm, com.epmserver.gateway.domain.ProductOptionSet.class.getName());
            createCache(cm, com.epmserver.gateway.domain.ProductChoice.class.getName());
            createCache(cm, com.epmserver.gateway.domain.ProductSet.class.getName());
            createCache(cm, com.epmserver.gateway.domain.ProductSetDetails.class.getName());
            createCache(cm, com.epmserver.gateway.domain.ProductDocument.class.getName());
            createCache(cm, com.epmserver.gateway.domain.Materials.class.getName());
            createCache(cm, com.epmserver.gateway.domain.DangerousGoods.class.getName());
            createCache(cm, com.epmserver.gateway.domain.ProductBrand.class.getName());
            createCache(cm, com.epmserver.gateway.domain.ProductCategory.class.getName());
            createCache(cm, com.epmserver.gateway.domain.ProductCategory.class.getName() + ".photoLists");
            createCache(cm, com.epmserver.gateway.domain.ProductCatalog.class.getName());
            createCache(cm, com.epmserver.gateway.domain.Currency.class.getName());
            createCache(cm, com.epmserver.gateway.domain.Photos.class.getName());
            createCache(cm, com.epmserver.gateway.domain.UnitMeasure.class.getName());
            createCache(cm, com.epmserver.gateway.domain.VehicleTemperatures.class.getName());
            createCache(cm, com.epmserver.gateway.domain.ShoppingCarts.class.getName());
            createCache(cm, com.epmserver.gateway.domain.ShoppingCarts.class.getName() + ".cartItemLists");
            createCache(cm, com.epmserver.gateway.domain.ShoppingCartItems.class.getName());
            createCache(cm, com.epmserver.gateway.domain.Wishlists.class.getName());
            createCache(cm, com.epmserver.gateway.domain.Wishlists.class.getName() + ".wishlistLists");
            createCache(cm, com.epmserver.gateway.domain.WishlistProducts.class.getName());
            createCache(cm, com.epmserver.gateway.domain.Compares.class.getName());
            createCache(cm, com.epmserver.gateway.domain.Compares.class.getName() + ".compareLists");
            createCache(cm, com.epmserver.gateway.domain.CompareProducts.class.getName());
            createCache(cm, com.epmserver.gateway.domain.Reviews.class.getName());
            createCache(cm, com.epmserver.gateway.domain.Reviews.class.getName() + ".reviewLineLists");
            createCache(cm, com.epmserver.gateway.domain.ReviewLines.class.getName());
            createCache(cm, com.epmserver.gateway.domain.ProductTags.class.getName());
            createCache(cm, com.epmserver.gateway.domain.SupplierImportedDocument.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
