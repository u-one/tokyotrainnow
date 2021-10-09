package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.odpt.client.OdptApiClient;
import net.uoneweb.tokyotrainnow.odpt.entity.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainRepository {
    @Autowired
    OdptApiClient apiClient;

    public List<Train> find(String railwayId) {
        return apiClient.getTrain(railwayId);
    }
}
