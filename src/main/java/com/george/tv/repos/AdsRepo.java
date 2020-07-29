package com.george.tv.repos;

import com.george.tv.models.Ads;
import com.george.tv.models.Personal;
import com.george.tv.models.Program;
import com.george.tv.models.WorkGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdsRepo extends CrudRepository<Ads, Long> {
}
