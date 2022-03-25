package net.uoneweb.tokyotrainnow.repository.gcp;

import net.uoneweb.tokyotrainnow.odpt.entity.RailDirection;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;

public interface GcpRailDirectionRepository extends DatastoreRepository<RailDirection, String> {
}
