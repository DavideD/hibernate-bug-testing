package com.example.data.repositories;

import com.example.data.entities.Device;
import com.example.data.entities.Feature;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class FeatureRepository {

    @Inject
    Mutiny.SessionFactory rxSessionFactory;

    public Uni<List<Feature>> getAllFeaturesWithMasterDevice() {
        return this.rxSessionFactory.openSession()
                .flatMap(rxSession ->
                        rxSession.createQuery("from Feature features", Feature.class)
                                .getResultList()
                                .onItem()
                                .transformToMulti(features -> Multi.createFrom().iterable(features))
                                // Start fetching block
                                .call(feature -> Mutiny.fetch(feature.getDevice()))
                                // end fetching block: if you remove or not the test will always fails
                                .collect()
                                .asList()
                                .call(ignored -> rxSession.close())
                );
    }

    public Uni<List<Feature>> getAllFeaturesWithMasterDeviceWorks() {
        return this.rxSessionFactory.openSession()
                .flatMap(rxSession ->
                        rxSession.createQuery("from Feature features join fetch features.device", Feature.class)
                                .getResultList()
                                .call(ignored -> rxSession.close())
                );
    }
}
