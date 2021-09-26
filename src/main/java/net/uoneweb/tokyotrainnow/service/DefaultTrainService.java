package net.uoneweb.tokyotrainnow.service;

import lombok.RequiredArgsConstructor;
import net.uoneweb.tokyotrainnow.odpt.client.OdptApiClient;
import net.uoneweb.tokyotrainnow.odpt.entity.Railway;
import net.uoneweb.tokyotrainnow.repository.RailwayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultTrainService implements TrainService {

    @Autowired
    private OdptApiClient odptApiClient;

    @Autowired
    private RailwayRepository railwayRepository;

    @Override
    public void update() {
        List<Railway> railways = odptApiClient.getRailways();
        railwayRepository.deleteAll();
        for (Railway railway : railways) {
            railwayRepository.add(railway.getSameAs(), railway);
        }
    }

    @Override
    public Railway getRailway(String railwayId) {
        return railwayRepository.findByRailwayId(railwayId);
    }
}
