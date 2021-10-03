package net.uoneweb.tokyotrainnow.repository;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultMetaDataRepositoryTest {

    private final MetaDataRepository repository = new DefaultMetaDataRepository();

    private final LocalDateTime time = LocalDateTime.of(2021, 10, 1, 12, 00, 00);

    @Test
    public void operatorsUpdateTime(){
        repository.setOperatorsUpdateTime(time);
        assertThat(repository.getOperatorsUpdateTime()).isEqualTo(time);
    }

    @Test
    public void railwaysUpdateTime(){
        repository.setRailwaysUpdateTime(time);
        assertThat(repository.getRailwaysUpdateTime()).isEqualTo(time);
    }

    @Test
    public void railDirectionsUpdateTime(){
        repository.setRailDirectionsUpdateTime(time);
        assertThat(repository.getRailDirectionsUpdateTime()).isEqualTo(time);
    }

    @Test
    public void stationsUpdateTime(){
        repository.setStationsUpdateTime(time);
        assertThat(repository.getStationsUpdateTime()).isEqualTo(time);
    }

    @Test
    public void trainTypesUpdateTime(){
        repository.setTrainTypesUpdateTime(time);
        assertThat(repository.getTrainTypesUpdateTime()).isEqualTo(time);
    }
}
