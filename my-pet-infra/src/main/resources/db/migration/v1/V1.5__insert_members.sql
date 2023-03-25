INSERT INTO members(email
    , password
    , nickname
    , certification_code
    , certificated_at
    , birth_day
    , phone_number
    , is_deleted
    , created_at
    , modified_at)
VALUES (
    'test@test.com'
    , 'ettMLMzWpR6uBCCPpGvBLyI7FVjor9b5TA7QCpjQlO1w4t4Gbv+KJZrhdyBpecFo'
    , '루트리버'
    , UUID_TO_BIN('b14c21a5-181e-4efd-9863-fb7e0080338b')
    , CURRENT_TIMESTAMP()
    , CURRENT_DATE()
    , '01012345678'
    , false
    , CURRENT_TIMESTAMP()
    , CURRENT_TIMESTAMP()
);
