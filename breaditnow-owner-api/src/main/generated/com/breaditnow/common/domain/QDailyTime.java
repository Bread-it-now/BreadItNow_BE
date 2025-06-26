package com.breaditnow.common.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDailyTime is a Querydsl query type for DailyTime
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDailyTime extends BeanPath<DailyTime> {

    private static final long serialVersionUID = -1331696687L;

    public static final QDailyTime dailyTime = new QDailyTime("dailyTime");

    public final TimePath<java.time.LocalTime> time = createTime("time", java.time.LocalTime.class);

    public QDailyTime(String variable) {
        super(DailyTime.class, forVariable(variable));
    }

    public QDailyTime(Path<DailyTime> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDailyTime(PathMetadata metadata) {
        super(DailyTime.class, metadata);
    }

}

