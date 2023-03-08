INSERT INTO pet_category(type, name, parent_id, created_at, modified_at)
    VALUES
        ('CAT', '단모종', NULL, NOW(), NOW())
        , ('CAT', '중모종', NULL, NOW(), NOW())
        , ('CAT', '장모종', NULL, NOW(), NOW())
;

SELECT id
INTO @v_parent_id
FROM pet_category
WHERE name = '단모종';

INSERT INTO pet_category(type, name, parent_id, created_at, modified_at)
    VALUES
        ('CAT', '데본렉스', @v_parent_id, NOW(), NOW())
        , ('CAT', '돈스코이', @v_parent_id, NOW(), NOW())
        , ('CAT', '러시안 블루', @v_parent_id, NOW(), NOW())
        , ('CAT', '맹크스', @v_parent_id, NOW(), NOW())
        , ('CAT', '먼치킨', @v_parent_id, NOW(), NOW())
        , ('CAT', '버마고양이', @v_parent_id, NOW(), NOW())
        , ('CAT', '버밀라', @v_parent_id, NOW(), NOW())
        , ('CAT', '벵갈', @v_parent_id, NOW(), NOW())
        , ('CAT', '봄베이', @v_parent_id, NOW(), NOW())
        , ('CAT', '브리티쉬 숏헤어', @v_parent_id, NOW(), NOW())
        , ('CAT', '샴', @v_parent_id, NOW(), NOW())
        , ('CAT', '세이셸루아', @v_parent_id, NOW(), NOW())
        , ('CAT', '소코게', @v_parent_id, NOW(), NOW())
        , ('CAT', '스노우슈', @v_parent_id, NOW(), NOW())
        , ('CAT', '스핑크스', @v_parent_id, NOW(), NOW())
        , ('CAT', '싱가푸라', @v_parent_id, NOW(), NOW())
        , ('CAT', '아메리칸 밥테일', @v_parent_id, NOW(), NOW())
        , ('CAT', '아메리칸 숏헤어', @v_parent_id, NOW(), NOW())
        , ('CAT', '아메리칸 와이어헤어', @v_parent_id, NOW(), NOW())
        , ('CAT', '아메리칸 컬', @v_parent_id, NOW(), NOW())
        , ('CAT', '아시안', @v_parent_id, NOW(), NOW())
        , ('CAT', '오리엔탈', @v_parent_id, NOW(), NOW())
        , ('CAT', '오스트레일미안 미스트', @v_parent_id, NOW(), NOW())
        , ('CAT', '오시캣', @v_parent_id, NOW(), NOW())
        , ('CAT', '이집션 마우', @v_parent_id, NOW(), NOW())
        , ('CAT', '카오 마니', @v_parent_id, NOW(), NOW())
        , ('CAT', '코니시 렉스', @v_parent_id, NOW(), NOW())
        , ('CAT', '코랏', @v_parent_id, NOW(), NOW())
        , ('CAT', '쿠리비안 밥테일', @v_parent_id, NOW(), NOW())
        , ('CAT', '타이', @v_parent_id, NOW(), NOW())
        , ('CAT', '톤키니즈', @v_parent_id, NOW(), NOW())
        , ('CAT', '피터볼드', @v_parent_id, NOW(), NOW())
        , ('CAT', '픽시 밥', @v_parent_id, NOW(), NOW())
        , ('CAT', '하바나', @v_parent_id, NOW(), NOW())
;

SELECT id
INTO @v_parent_id
FROM pet_category
WHERE name='중모종';

INSERT INTO pet_category(type, name, parent_id, created_at, modified_at)
    VALUES
        ('CAT', '노르웨이 숲', @v_parent_id, NOW(), NOW())
         , ('CAT', '라가머핀', @v_parent_id, NOW(), NOW())
         , ('CAT', '라팜', @v_parent_id, NOW(), NOW())
         , ('CAT', '랙돌', @v_parent_id, NOW(), NOW())
         , ('CAT', '메인쿤', @v_parent_id, NOW(), NOW())
         , ('CAT', '버만', @v_parent_id, NOW(), NOW())
         , ('CAT', '샤르트뢰', @v_parent_id, NOW(), NOW())
         , ('CAT', '셀커크 렉스', @v_parent_id, NOW(), NOW())
         , ('CAT', '소말리', @v_parent_id, NOW(), NOW())
         , ('CAT', '스코티시 스트레이트', @v_parent_id, NOW(), NOW())
         , ('CAT', '스코티시 폴드', @v_parent_id, NOW(), NOW())
         , ('CAT', '아비시니안', @v_parent_id, NOW(), NOW())
         , ('CAT', '엑조틱 숏헤어', @v_parent_id, NOW(), NOW())
         , ('CAT', '재패니스 밥테일', @v_parent_id, NOW(), NOW())
         , ('CAT', '킴릭', @v_parent_id, NOW(), NOW())
         , ('CAT', '터키쉬 반', @v_parent_id, NOW(), NOW())
         , ('CAT', '터키쉬 앙고라', @v_parent_id, NOW(), NOW())
;

SELECT id
INTO @v_parent_id
FROM pet_category
WHERE name='장모종';

INSERT INTO pet_category(type, name, parent_id, created_at, modified_at)
VALUES
    ('CAT', '네바 마스커레이드', @v_parent_id, NOW(), NOW())
     , ('CAT', '발리니즈', @v_parent_id, NOW(), NOW())
     , ('CAT', '브리티쉬 롱헤어', @v_parent_id, NOW(), NOW())
     , ('CAT', '시베리안', @v_parent_id, NOW(), NOW())
;
