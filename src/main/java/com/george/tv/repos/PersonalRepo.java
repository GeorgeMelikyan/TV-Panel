package com.george.tv.repos;

import com.george.tv.models.Personal;
import com.george.tv.models.WorkGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonalRepo extends CrudRepository<Personal, Long> {
    List<Personal> findAllByGroupsIn(List<WorkGroup> group);
}
