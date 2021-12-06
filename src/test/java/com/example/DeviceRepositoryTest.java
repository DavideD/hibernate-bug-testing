package com.example;

import com.example.data.entities.Device;
import com.example.data.entities.Feature;
import com.example.data.repositories.DeviceRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@QuarkusTest
public class DeviceRepositoryTest {

    @Inject
    DeviceRepository deviceRepository;

    @Test
    public void testGetDevicesWithDetails() {
        List<Device> devices = this.deviceRepository.getDevicesWithDetails().await().indefinitely();
        Assertions.assertNotNull(devices);
        Assertions.assertFalse(devices.isEmpty());
        Assertions.assertEquals(2, devices.size());

        devices.forEach(device -> {
            List<Feature> features = device.getFeatures();
            Assertions.assertFalse(features.isEmpty());
            // We are sure that all features' properties are successfully fetched
            Assertions.assertTrue(features.stream().map(Feature::getFeatureType).noneMatch(Objects::isNull));
            Assertions.assertTrue(features.stream().map(Feature::getName).noneMatch(Objects::isNull));
        });
    }
}
