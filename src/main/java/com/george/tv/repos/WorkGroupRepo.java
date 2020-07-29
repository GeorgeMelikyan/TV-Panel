package com.george.tv.repos;

import com.george.tv.models.WorkGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorkGroupRepo extends CrudRepository<WorkGroup, Long> {
    List<WorkGroup> findAllByPersonalsIn(List<WorkGroup> personal);
}
