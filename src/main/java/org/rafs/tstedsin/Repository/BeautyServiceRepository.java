package org.rafs.tstedsin.Repository;

import org.rafs.tstedsin.Model.BeautyService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeautyServiceRepository extends JpaRepository<BeautyService, Long> {

}
