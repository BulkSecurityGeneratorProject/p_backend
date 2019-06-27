package com.medicai.pillpal.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

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
            createCache(cm, com.medicai.pillpal.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.medicai.pillpal.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.medicai.pillpal.domain.User.class.getName());
            createCache(cm, com.medicai.pillpal.domain.Authority.class.getName());
            createCache(cm, com.medicai.pillpal.domain.User.class.getName() + ".authorities");
            createCache(cm, com.medicai.pillpal.domain.PharmaceuticalCode.class.getName());
            createCache(cm, com.medicai.pillpal.domain.Interaction.class.getName());
            createCache(cm, com.medicai.pillpal.domain.ApplicationInfo.class.getName());
            createCache(cm, com.medicai.pillpal.domain.ApplicationInfo.class.getName() + ".productionInfos");
            createCache(cm, com.medicai.pillpal.domain.ApplicationInfo.class.getName() + ".pharmaceuticalcodes");
            createCache(cm, com.medicai.pillpal.domain.ApplicationInfo.class.getName() + ".baseInteractions");
            createCache(cm, com.medicai.pillpal.domain.ApplicationInfo.class.getName() + ".descInteractions");
            createCache(cm, com.medicai.pillpal.domain.ApplicationInfo.class.getName() + ".dosings");
            createCache(cm, com.medicai.pillpal.domain.ApplicationInfo.class.getName() + ".missedDoses");
            createCache(cm, com.medicai.pillpal.domain.ApplicationInfo.class.getName() + ".useAndStorages");
            createCache(cm, com.medicai.pillpal.domain.ApplicationInfo.class.getName() + ".beforeUses");
            createCache(cm, com.medicai.pillpal.domain.ApplicationInfo.class.getName() + ".precoutions");
            createCache(cm, com.medicai.pillpal.domain.ApplicationInfo.class.getName() + ".properUses");
            createCache(cm, com.medicai.pillpal.domain.ApplicationInfo.class.getName() + ".pregnancies");
            createCache(cm, com.medicai.pillpal.domain.ApplicationInfo.class.getName() + ".allergies");
            createCache(cm, com.medicai.pillpal.domain.ApplicationInfo.class.getName() + ".pediatrics");
            createCache(cm, com.medicai.pillpal.domain.ApplicationInfo.class.getName() + ".geriatrics");
            createCache(cm, com.medicai.pillpal.domain.ApplicationInfo.class.getName() + ".breastfeedings");
            createCache(cm, com.medicai.pillpal.domain.ApplicationInfo.class.getName() + ".applInfoSideEffects");
            createCache(cm, com.medicai.pillpal.domain.ConsumptionDosing.class.getName());
            createCache(cm, com.medicai.pillpal.domain.ConsumptionMissedDose.class.getName());
            createCache(cm, com.medicai.pillpal.domain.UseAndStorage.class.getName());
            createCache(cm, com.medicai.pillpal.domain.ConsumptionBeforeUsing.class.getName());
            createCache(cm, com.medicai.pillpal.domain.ConsumptionPrecoution.class.getName());
            createCache(cm, com.medicai.pillpal.domain.ConsumptionProperUse.class.getName());
            createCache(cm, com.medicai.pillpal.domain.ProductionInfo.class.getName());
            createCache(cm, com.medicai.pillpal.domain.Pregnancy.class.getName());
            createCache(cm, com.medicai.pillpal.domain.Allergy.class.getName());
            createCache(cm, com.medicai.pillpal.domain.Pediatric.class.getName());
            createCache(cm, com.medicai.pillpal.domain.Geriatric.class.getName());
            createCache(cm, com.medicai.pillpal.domain.BreastFeeding.class.getName());
            createCache(cm, com.medicai.pillpal.domain.SideEffect.class.getName());
            createCache(cm, com.medicai.pillpal.domain.SideEffect.class.getName() + ".sideEffets");
            createCache(cm, com.medicai.pillpal.domain.ApplInfoSideEffect.class.getName());
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
