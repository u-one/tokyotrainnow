package net.uoneweb.tokyotrainnow.repository.gcp;

import net.uoneweb.tokyotrainnow.entity.MetaData;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GcpMetaDataRepository extends DatastoreRepository<MetaData, Long> {
}
