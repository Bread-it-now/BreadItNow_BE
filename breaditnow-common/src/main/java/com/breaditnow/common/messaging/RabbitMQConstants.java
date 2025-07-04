package com.breaditnow.common.messaging;

public final class RabbitMQConstants {

    private RabbitMQConstants() {}

    // Exchange
    public static final String BREADITNOW_TOPIC_EXCHANGE = "breaditnow.topic";

    // --- 재고 감소(Stock Decrease) 관련 ---
    // 예약 서비스 -> 상품 서비스
    public static final String QUEUE_STOCK_DECREASE_REQUEST = "stock.decrease.request.queue";
    public static final String ROUTING_KEY_STOCK_DECREASE_REQUEST = "v1.stock.decrease.requested";

    // 상품 서비스 -> 예약 서비스
    public static final String QUEUE_STOCK_DECREASE_RESULT = "stock.decrease.result.queue";
    public static final String ROUTING_KEY_STOCK_DECREASE_RESULT_WILDCARD = "v1.stock.decrease.*";


    // --- 재고 증가(Stock Increase) 관련 ---
    // 예약 서비스 -> 상품 서비스
    public static final String QUEUE_STOCK_INCREASE_REQUEST = "stock.increase.request.queue";
    public static final String ROUTING_KEY_STOCK_INCREASE_REQUEST = "v1.stock.increase.requested";

    // 상품 서비스 -> 예약 서비스
    public static final String QUEUE_STOCK_INCREASE_RESULT = "stock.increase.result.queue";
    public static final String ROUTING_KEY_STOCK_INCREASE_RESULT_WILDCARD = "v1.stock.increase.*";


    // --- 알림 발송(Notification Send) 관련 ---
    // 예약 서비스 -> 알림 서비스
    public static final String QUEUE_NOTIFICATION_SEND_REQUEST = "notification.send.request.queue";
    public static final String ROUTING_KEY_NOTIFICATION_SEND_REQUEST = "v1.notification.send.requested";

    // --- 결과 발행용 라우팅 키 포맷 ---
    public static final String ROUTING_KEY_STOCK_RESULT_FORMAT = "v1.stock.%s.%s"; // operation, status
}
