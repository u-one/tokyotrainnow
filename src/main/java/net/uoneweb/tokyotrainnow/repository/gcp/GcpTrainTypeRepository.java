package net.uoneweb.tokyotrainnow.repository.gcp;

import net.uoneweb.tokyotrainnow.odpt.entity.TrainType;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;

public interface GcpTrainTypeRepository extends DatastoreRepository<TrainType, String> {
}
