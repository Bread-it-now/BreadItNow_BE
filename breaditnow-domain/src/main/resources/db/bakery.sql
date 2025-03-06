-- Bakery 엔티티 insert 예제
insert into bakery
    (owner_id, name, phone, introduction, profile_image, open_time, sido_code, gugun_code, dong_code, latitude, longitude, address_description, operating_status, is_active)
values
    (1, 'Bread It Now Bakery', '010-1111-2222', '신선한 빵을 매일 제공합니다. - Owner 1', 'profile.jpg', '08:00-20:00', '11', '110', '101', 37.5665, 126.9780, '청운동', 'OPEN', 1),
    (2, 'Bread It Now Bakery 2', '010-2222-3333', '매일 신선한 빵을 제공합니다. - Owner 2', 'profile2.jpg', '09:00-21:00', '11', '110', '102', 37.5665, 126.9780, '신교동', 'OPEN', 1),
    (3, 'Bread It Now Bakery 3', '010-3333-4444', '매일 맛있는 빵을 제공합니다. - Owner 3', 'profile3.jpg', '07:00-19:00', '11', '110', '103', 37.5665, 126.9780, '궁정동', 'OPEN', 0),
    (4, 'Bread It Now Bakery 4', '010-4444-5555', '신선한 재료로 만든 빵을 제공합니다. - Owner 4', 'profile4.jpg', '08:30-20:30', '11', '110', '104', 37.5665, 126.9780, '효자동', 'OPEN', 1),
    (5, 'Bread It Now Bakery 5', '010-5555-6666', '건강한 빵을 만드는 전통의 맛. - Owner 5', 'profile5.jpg', '08:00-20:00', '11', '110', '105', 37.5665, 126.9780, '창성동', 'OPEN', 0);


