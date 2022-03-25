package net.uoneweb.tokyotrainnow.repository.gcp;

import net.uoneweb.tokyotrainnow.odpt.entity.Station;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;

public interface GcpStationRepository extends DatastoreRepository<Station, String> {
}
