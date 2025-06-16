package com.breaditnow.owner.bakery.domain.port;


import com.breaditnow.owner.bakery.domain.Bakery;

public interface BakeryRepository {
    Long saveBakery(Bakery bakery);
}
