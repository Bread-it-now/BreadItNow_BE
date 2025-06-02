package com.breaditnow.customer.alert.infrastructure.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGlobalAlertSettingEntity is a Querydsl query type for GlobalAlertSettingEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGlobalAlertSettingEntity extends EntityPathBase<GlobalAlertSettingEntity> {

    private static final long serialVersionUID = 745986884L;

    public static final QGlobalAlertSettingEntity globalAlertSettingEntity = new QGlobalAlertSettingEntity("globalAlertSettingEntity");

    public final BooleanPath active = createBoolean("active");

    public final NumberPath<Long> customerId = createNumber("customerId", Long.class);

    public final SimplePath<com.breaditnow.customer.alert.domain.DayOfWeekSet> days = createSimple("days", com.breaditnow.customer.alert.domain.DayOfWeekSet.class);

    public final ComparablePath<com.breaditnow.customer.common.domain.ReleaseTime> endTime = createComparable("endTime", com.breaditnow.customer.common.domain.ReleaseTime.class);

    public final ComparablePath<com.breaditnow.customer.common.domain.ReleaseTime> startTime = createComparable("startTime", com.breaditnow.customer.common.domain.ReleaseTime.class);

    public QGlobalAlertSettingEntity(String variable) {
        super(GlobalAlertSettingEntity.class, forVariable(variable));
    }

    public QGlobalAlertSettingEntity(Path<? extends GlobalAlertSettingEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGlobalAlertSettingEntity(PathMetadata metadata) {
        super(GlobalAlertSettingEntity.class, metadata);
    }

}

