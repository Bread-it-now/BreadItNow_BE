package com.breaditnow.notification.adapter.out.http;

import com.breaditnow.common.domain.Role;
import com.breaditnow.common.domain.UserIdentifier;
import com.breaditnow.notification.domain.port.out.CustomerApiPort;
import com.breaditnow.notification.domain.port.out.FcmPort;
import com.breaditnow.notification.domain.port.out.OwnerApiPort;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.breaditnow.common.domain.Role.CUSTOMER;
import static com.breaditnow.common.domain.Role.OWNER;

@Component
public class FcmNotifier {
    private final FcmPort fcmPort;
    private final Map<Role, Function<Long, Optional<String>>> fcmTokenFinders;

    public FcmNotifier(FcmPort fcmPort, OwnerApiPort ownerApiPort, CustomerApiPort customerApiPort) {
        this.fcmPort = fcmPort;
        this.fcmTokenFinders = Map.of(
                OWNER, ownerApiPort::findFcmTokenById,
                CUSTOMER, customerApiPort::findFcmTokenById
        );
    }

    public void notify(UserIdentifier recipient, String title, String content) {
        Function<Long, Optional<String>> finder = fcmTokenFinders.get(recipient.type());
        if (finder == null) return;

        finder.apply(recipient.id())
                .ifPresent(token -> fcmPort.sendNotification(token, title, content));
    }
}
