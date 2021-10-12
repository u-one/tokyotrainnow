package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.entity.TrainType;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;

public interface TrainTypeRepository extends DatastoreRepository<TrainType, String> {
}
