package atmin.repository;

import atmin.entity.Watch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WatchRepository extends JpaRepository<Watch, Long> {
    @Query("from Watch where isDeleted = false")
    List<Watch> findAllWatches();

    @Query("from Watch where id = :id and isDeleted = false ")
    Watch findWatchById(long id);

    @Query("select w from Watch w where w.isDeleted = false and " +
            "(:model_name is null or :model_name = '' or lower(w.model_name) like lower(concat('%', :model_name, '%'))) and " +
            "(:brand is null or :brand = '' or lower(w.brand) like lower(concat('%', :brand, '%')))")
    Page<Watch> search(@Param("model_name") String model_name, @Param("brand") String brand, Pageable pageable);
}
