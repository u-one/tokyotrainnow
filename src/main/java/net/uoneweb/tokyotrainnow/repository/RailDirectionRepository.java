package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.RailDirection;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;

public interface RailDirectionRepository extends DatastoreRepository<RailDirection, String> {
}
