package net.uoneweb.tokyotrainnow.repository;

import net.uoneweb.tokyotrainnow.entity.MetaData;

import java.util.Optional;

public interface MetaDataRepository {
    MetaData save(MetaData metaData);

    Optional<MetaData> findById(Long id);
}