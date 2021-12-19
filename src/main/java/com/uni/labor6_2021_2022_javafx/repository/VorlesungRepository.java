package com.uni.labor6_2021_2022_javafx.repository;

import com.uni.labor6_2021_2022_javafx.model.Vorlesung;

public class VorlesungRepository extends InMemoryRepository<Vorlesung>{

    public VorlesungRepository() {
        super();
    }

    /**
     * @param id das Id eines Objektes aus der Liste "repoList"
     * @return die Vorlesung mit der Id "id"
     */
    @Override
    public Vorlesung findOne(Long id) {
        Vorlesung vorlesungToFind = this.repoList.stream()
                .filter(vorlesung -> vorlesung.getVorlesungID() == id)
                .findFirst()
                .orElse(null);

        return vorlesungToFind;
    }

    /**
     *
     * @param entity ein Objekt von Typ Vorlesung
     * @return eine aktualisierte Version des Objektes
     */
    @Override
    public Vorlesung update(Vorlesung entity) {
        Vorlesung vorlesungToUpdate = this.repoList.stream()
                .filter(vorlesung -> vorlesung.getVorlesungID() == entity.getVorlesungID())
                .findFirst()
                .orElseThrow();

        vorlesungToUpdate.setName(entity.getName());
        vorlesungToUpdate.setLehrer(entity.getLehrer());
        vorlesungToUpdate.setMaxEnrollment(entity.getMaxEnrollment());
        vorlesungToUpdate.setStudentsEnrolled(entity.getStudentsEnrolled());
        vorlesungToUpdate.setCredits(entity.getCredits());

        return vorlesungToUpdate;
    }
}
