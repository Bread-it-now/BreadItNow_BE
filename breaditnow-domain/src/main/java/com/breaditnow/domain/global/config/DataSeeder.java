/*
package com.breaditnow.domain.global.config;

import static com.breaditnow.domain.domain.bakery.enumerate.OperatingStatus.*;
import static com.breaditnow.domain.domain.product.enumerate.ProductType.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.breaditnow.domain.domain.bakery.entity.Address;
import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.domain.owner.entity.Owner;
import com.breaditnow.domain.domain.owner.repository.OwnerRepository;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.repository.ProductRepository;
import com.breaditnow.domain.domain.region.entity.RegionPK;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {
	private static final int BAKERY_COUNT = 2000;
	private static final int PRODUCTS_PER_BAKERY = 70;

	private final OwnerRepository ownerRepo;
	private final BakeryRepository bakeryRepo;
	private final ProductRepository productRepo;

	private static final List<String> BREAD_NAMES = List.of(
		"바게트", "크루아상", "식빵", "단팥빵", "호두파이", "카스테라", "치즈빵", "소보로빵", "슈크림빵", "마들렌",
		"마카롱", "머핀", "베이글", "에클레어", "컵케이크", "티라미수", "피칸파이", "도넛", "파운드케이크", "스콘",
		"찹쌀도넛", "까눌레", "포카치아", "브리오슈", "퀴슈", "갈릭바게트", "허니브레드", "크로플", "밀크빵", "베이컨롤",
		"소시지빵", "앙버터", "양파빵", "감자빵", "옥수수빵", "시나몬롤", "다쿠아즈", "타르트타탱", "브라우니", "초코칩쿠키",
		"쿠키", "푸딩", "파이", "케이크롤", "티브레드", "뮤즐리바", "허니버터브레드", "레드벨벳", "당근케이크", "치즈케이크",
		"찰떡파이", "밀크롤", "프레즐", "베리파이", "애플파이", "레몬파이", "딸기타르트", "초코크런치", "베이컨에그번", "치즈스틱",
		"피자빵", "바나나브레드", "통밀빵", "호밀빵", "플레인스콘", "초코스콘", "치즈브레드", "허브포카치아", "올리브포카치아", "아몬드크로와상",
		"커스타드번", "코코넛번", "땅콩빵", "피넛버터번", "체다치즈롤", "베이글모닝", "허니롤", "말차빵", "녹차스콘", "유자머핀",
		"블루베리머핀", "얼그레이머핀", "초코마들렌", "댄디툴루즈", "프랑스전병", "와플", "글레이즈드도넛", "초코도넛", "소금빵", "마늘바게트",
		"버터크럼블", "베이컨치즈번", "크림치즈번", "다크초코브레드", "화이트초코브레드", "오렌지레몬파이", "초코머핀", "바질빵", "토마토포카치아", "올리브빵"
	);

	private static final List<String> BAKERY_NAMES = List.of(
		"별빛베이커리", "향기로운빵집", "달콤한시간", "빵굽는오후", "따뜻한식탁", "감성베이커리", "행복한빵터", "수플레빵공방", "밀가루이야기", "노을베이커리",
		"파란지붕빵집", "오븐의속삭임", "햇살정원", "빵냄새골목", "골목빵집", "제빵사의비밀", "모든날의빵", "소중한하루", "한조각의행복", "달의오전",
		"구름빵공방", "햄릿베이커리", "빵과커피", "빵향가득", "빵지순례", "소담빵집", "달달베이커리", "따뜻한빵향", "뮤즐리베이커리", "고소한시간",
		"해피베이크", "수호빵집", "토스트와빵", "북촌빵집", "명동베이커리", "청담제빵소", "강남빵터", "홍대빵집", "이태원베이커리", "성수빵공장",
		"당산빵집", "연남동제빵소", "연희동베이커리", "성북동빵구름", "광화문빵굽는집", "삼청동베이크", "누리베이커리", "이든베이커리", "어제의빵집", "오늘의빵공방",
		"내일의빵공장", "달콤한오븐", "빵비빔", "꽃보다빵", "노랑통닭빵집", "노란부엉이", "빵과꿈", "빵가게아저씨", "빵과다과", "행복빵공장",
		"포도베이커리", "밤하늘베이커리", "바람의빵공방", "세상모든빵", "포근한집", "해질녘빵집", "봄향기베이커리", "여름밤베이커리", "가을낙엽빵집", "겨울토피아",
		"시나몬가든", "바닐라스카이빵집", "허니콤베이커리", "로즈베이커리", "라벤더베이커리", "카멜리베이커리", "마스코바도베이커리", "코코넛베이커리", "초코릿에반하다", "딸기나라베이커리"
	);

	@Bean
	public CommandLineRunner seedData() {
		return args -> {
			Random rng = new Random();

			// 1) Owner 생성 (Owner 수는 BAKERY_COUNT)
			List<Owner> owners = new ArrayList<>();
			for (int i = 0; i < BAKERY_COUNT; i++) {
				String bakeryName = BAKERY_NAMES.get(rng.nextInt(BAKERY_NAMES.size()));
				String email = bakeryName.replaceAll("\\s", "") + "@예시.한국";
				String password = bakeryName + "비밀번호";
				String fcmToken = bakeryName + "토큰";

				Owner owner = Owner.builder()
					.email(email)
					.password(password)
					.fcmToken(fcmToken)
					.build();
				owners.add(ownerRepo.save(owner));
			}

			// 법정행정동 코드 예시
			List<RegionPK> regionCodes = List.of(
				new RegionPK("11680000"),
				new RegionPK("11740000"),
				new RegionPK("11140000")
			);

			// 2) Bakery + Product 생성
			for (int i = 0; i < BAKERY_COUNT; i++) {
				Owner owner = owners.get(i);
				RegionPK regionPK = regionCodes.get(rng.nextInt(regionCodes.size()));
				String addrDesc = BAKERY_NAMES.get(rng.nextInt(BAKERY_NAMES.size())) + " 주소";
				Address address = new Address(regionPK, addrDesc);
				address.setLatitude(37 + rng.nextDouble());
				address.setLongitude(127 + rng.nextDouble());

				String bakeryName = BAKERY_NAMES.get(rng.nextInt(BAKERY_NAMES.size()));
				String introduction = bakeryName + " 소개글입니다.";
				String profileImage = bakeryName + "_대표이미지";
				String openTime = String.format("오전 %d:00 ~ 오후 %d:00",
					rng.nextInt(4) + 7, rng.nextInt(5) + 18);

				Bakery bakery = Bakery.builder()
					.owner(owner)
					.name(bakeryName)
					.phone(generateNumericString(rng, 10))
					.introduction(introduction)
					.profileImage(profileImage)
					.openTime(openTime)
					.address(address)
					.operatingStatus(OPEN)
					.build();
				Bakery savedBakery = bakeryRepo.save(bakery);

				// Product 생성: BREAD_NAMES에서 랜덤 선택
				for (int j = 0; j < PRODUCTS_PER_BAKERY; j++) {
					String productName = BREAD_NAMES.get(rng.nextInt(BREAD_NAMES.size()));
					String description = productName + " 맛있게 드세요.";
					String productImage = productName + "_이미지";
					int startHour = rng.nextInt(6) + 6;
					int endHour = startHour + rng.nextInt(10) + 1;
					String releaseTime = String.format("%02d:00;%02d:00", startHour, endHour);

					Product product = Product.builder()
						.bakery(savedBakery)
						.type(BREAD)
						.name(productName)
						.price(rng.nextInt(19001) + 1000)
						.image(productImage)
						.description(description)
						.releaseTime(releaseTime)
						.stock(rng.nextInt(100) + 1)
						.isActive(true)
						.isHidden(false)
						.build();
					productRepo.save(product);
				}
			}
		};
	}

	// 숫자만으로 이루어진 문자열 생성
	private static String generateNumericString(Random rng, int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(rng.nextInt(10));
		}
		return sb.toString();
	}
}
*/
