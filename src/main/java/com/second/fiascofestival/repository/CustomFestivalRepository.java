package com.second.fiascofestival.repository;

import com.second.fiascofestival.model.Festival;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface CustomFestivalRepository {
    public List<Festival> findFestivalsByQuery(Query query);

}
