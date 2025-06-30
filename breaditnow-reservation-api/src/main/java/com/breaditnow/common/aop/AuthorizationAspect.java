package com.breaditnow.common.aop;

import com.breaditnow.common.domain.Role;
import com.breaditnow.common.exception.BreaditnowException;
import com.breaditnow.common.exception.ReservationException;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

import static com.breaditnow.common.exception.CommonErrorCode.INVALID_PARAMETER;
import static com.breaditnow.common.exception.ReservationErrorCode.FORBIDDEN_ACCESS;

@Aspect
@Component
public class AuthorizationAspect {

    @Before("@annotation(com.breaditnow.common.aop.Authorize)")
    public void authorize(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Authorize authorizeAnnotation = method.getAnnotation(Authorize.class);
        Role requiredRole = authorizeAnnotation.value();

        AuthenticatedUser user = findAuthenticatedUser(joinPoint);
        Role userRole = Role.from(user.role());

        if (userRole != requiredRole) {
            throw new ReservationException(FORBIDDEN_ACCESS);
        }
    }

    private AuthenticatedUser findAuthenticatedUser(JoinPoint joinPoint) {
        return Arrays.stream(joinPoint.getArgs())
                .filter(AuthenticatedUser.class::isInstance)
                .map(AuthenticatedUser.class::cast)
                .findFirst()
                .orElseThrow(() -> new BreaditnowException(INVALID_PARAMETER));
    }
}
