package com.uni.labor6_2021_2022_javafx.repository;

import com.uni.labor6_2021_2022_javafx.model.Lehrer;

public class LehrerRepository extends InMemoryRepository<Lehrer>{

    public LehrerRepository()
    {
        super();
    }

    /**
     * @param id das Id eines Objektes aus der Liste "repoList"
     * @return der Lehrer mit der Id "id"
     */
    @Override
    public Lehrer findOne(Long id) {
        Lehrer lehrerToFind = this.repoList.stream()
                .filter(lehrer -> lehrer.getLehrerID() == id)
                .findFirst()
                .orElse(null);

        return lehrerToFind;
    }

    /**
     *
     * @param entity ein Objekt von Typ Lehrer
     * @return eine aktualisierte Version des Objektes
     */
    @Override
    public Lehrer update(Lehrer entity) {
        Lehrer lehrerToUpdate = this.repoList.stream()
                .filter(lehrer -> lehrer.getLehrerID() == entity.getLehrerID())
                .findFirst()
                .orElseThrow();

        lehrerToUpdate.setPersonID(entity.getPersonID());
        lehrerToUpdate.setVorname(entity.getVorname());
        lehrerToUpdate.setNachname(entity.getNachname());
        lehrerToUpdate.setVorlesungen(entity.getVorlesungen());

        return lehrerToUpdate;
    }
}
