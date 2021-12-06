package com.example.data.repositories;

import com.example.data.entities.Device;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class DeviceRepository {

    @Inject
    Mutiny.SessionFactory rxSessionFactory;

    public Uni<List<Device>> getDevicesWithDetails() {
        return this.rxSessionFactory.openSession()
                .flatMap(rxSession ->
                        rxSession.createQuery("from Device devices", Device.class)
                                .getResultList()
                                .onItem()
                                .transformToMulti(devices -> Multi.createFrom().iterable(devices))
                                // Start fetching block
                                .call(device -> Mutiny.fetch(device.getFeatures()))
                                // end fetching block: if you remove this the test DeviceRepositoryTest::testGetDevicesWithDetails will fail
                                .collect()
                                .asList()
                );
    }
}
