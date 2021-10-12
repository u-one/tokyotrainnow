package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.Station;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;

public interface StationRepository extends DatastoreRepository<Station, String> {
}
