package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.RailDirectionFactory;
import net.uoneweb.tokyotrainnow.odpt.entity.RailDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RailDirectionRepositoryTest {

    @Autowired
    DefaultRailDirectionRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();

        final RailDirection outbound = RailDirectionFactory.outbound();

        repository.save(outbound);
    }

    @Test
    public void findSuccess() {
        final Optional<RailDirection> oRailDirection = repository.findById("odpt.RailDirection:Outbound");
        assertThat(oRailDirection).hasValueSatisfying(rd -> {
            assertThat(rd.getTitle()).isEqualTo("下り");
        });
    }

    @Test
    public void findSuccessOnEmptyId() {
        final Optional<RailDirection> oRailDirection = repository.findById(null);
        assertThat(oRailDirection).isEmpty();
    }

    @Test
    public void findSuccessWithInvalidId() {
        final Optional<RailDirection> oRailDirection = repository.findById("invalid");
        assertThat(oRailDirection).isEmpty();
    }
}
