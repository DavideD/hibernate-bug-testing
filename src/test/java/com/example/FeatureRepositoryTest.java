package com.example;

import com.example.data.entities.Device;
import com.example.data.entities.Feature;
import com.example.data.repositories.FeatureRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

@QuarkusTest
public class FeatureRepositoryTest {

    @Inject
    FeatureRepository featureRepository;

    @Test
    public void testGetDevicesWithDetails() {
        List<Feature> features = this.featureRepository.getAllFeaturesWithMasterDevice().await().indefinitely();
        Assertions.assertNotNull(features);
        Assertions.assertFalse(features.isEmpty());
        Assertions.assertEquals(4, features.size());

        features.forEach(feature -> {
            Device device = feature.getDevice();
            // Block of tests that always work for some reason
            Assertions.assertNotNull(device);
            Assertions.assertNotNull(device.getId());
            // Here the tests will warn that the device description cannot be got
            // expected: the description should be available as concrete field and not as lazy initialization
            // because initialization should be happened in the repository
            Assertions.assertThrows(LazyInitializationException.class, device::getDescription);
        });
    }

    @Test
    public void testGetDevicesWithDetailsShouldWorld() {
        List<Feature> features = this.featureRepository.getAllFeaturesWithMasterDeviceWorks().await().indefinitely();
        Assertions.assertNotNull(features);
        Assertions.assertFalse(features.isEmpty());
        Assertions.assertEquals(4, features.size());

        features.forEach(feature -> {
            Device device = feature.getDevice();
            // Block of tests that always work for some reason
            Assertions.assertNotNull(device);
            Assertions.assertNotNull(device.getId());
            // Now tests will work
            Assertions.assertNotNull(device.getDescription());
            Assertions.assertNotNull(device.getName());
        });
    }
}
