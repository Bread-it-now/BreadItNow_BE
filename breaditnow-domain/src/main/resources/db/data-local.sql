insert into sido (sido_code, sido_name)
values
  (11, '서울특별시'),
  (26, '부산광역시');

insert into gugun (gugun_code, sido_code, gugun_name)
values
  (110, 11, '종로구'),
  (140, 11, '영등포구'),
  (260, 26, '부산진구'),
  (270, 26, '해운대구');

insert into dong (dong_code, gugun_code, dong_name)
values
  (11001, 110, '청운동'),
  (11002, 110, '신교동'),
  (14001, 140, '당산동'),
  (14002, 140, '도림동'),
  (26001, 260, '부전동'),
  (26002, 260, '범천동'),
  (27001, 270, '좌동'),
  (27002, 270, '중동');
