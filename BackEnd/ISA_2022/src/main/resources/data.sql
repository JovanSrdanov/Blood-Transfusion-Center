INSERT INTO ROLE (name)
VALUES ('ROLE_BLOOD_DONOR');
INSERT INTO ROLE (name)
VALUES ('ROLE_STAFF');
INSERT INTO ROLE (name)
VALUES ('ROLE_SYSTEM_ADMIN');

--- Sifra za sve korinsike je "password", kad se pravi novi korisnik nek se kopira hesovana sifra sto vec stoji ovde
--- Hesovana sifra  -  $2y$10$49qXN7cAPT7IanIsSk.3Be8GFLskf6eL.mcNQQAW6w0RRHCymHyBi


INSERT INTO "address" ("id", "city", "country", "latitude", "longitude", "number", "street")
VALUES ('3eb50b45-9d6c-4f42-992d-4a2bb3c2e0b2', 'Olofström', 'Sweden', '56.2462038', '14.6265298', '250', 'Harbort'),
       ('59575fe2-19b1-474a-a63a-db4b02ab0b0c', 'Šventoji', 'Lithuania', '55.2474741', '24.7743055', '980', 'Melby'),
       ('b9fd203d-9620-4dd1-9f43-e874e3593edc', 'Idritsa', 'Russia', '56.3360497', '28.8883279', '266', 'East'),
       ('5c8cb3b3-4c48-4614-8db2-620f21872785', 'Évreux', 'France', '49.0261841', '1.1518478', '3', 'Meadow Valley'),
       ('288936ca-16f3-4908-b37c-6fc7b8736bde', 'Zeleneč', 'Czech Republic', '50.1336212', '14.6607051', '99',
        'Moland'),
       ('5c3b169a-c50c-4b90-939d-ee3fca1459ea', 'Warungtanjung', 'Indonesia', '3.5053306', '98.6783333', '94652',
        'Sunfield'),
       ('f131fbc0-343e-4b61-9542-a384e4882ba5', 'Smyga', 'Ukraine', '50.2414199', '25.7622611', '4', 'Roxbury'),
       ('5c6ae6d9-966d-40b5-9ba2-8f0198df0e89', 'Sarankhola', 'Bangladesh', '22.0952234', '89.8130356', '391',
        'Sheridan'),
       ('59b007ac-5940-49dd-a19c-a81f1d67c9d3', 'Tevriz', 'Russia', '57.512722', '72.40078', '7', 'Westport'),
       ('b1b2dbfc-4b92-4cb6-931f-7c136e86f80b', 'Dubai', 'United Arab Emirates', '25.2048493', '55.2707828', '7',
        'Ronald Regan'),
       ('e4b6ce9d-5094-4a64-a958-3b919770d870', 'Gharyan', 'Libya', '32.1717952', '13.0184123', '4415', 'Vermont'),
       ('a0cd0598-30f7-430d-8cc5-ef309d31ffb7', 'Kroya', 'Indonesia', '-7.6331351', '109.2474701', '420', 'Oriole'),
       ('59902151-934b-49ce-bd22-a698234a01dd', 'Chicoana', 'Argentina', '-25.1042007', '-65.5381671', '08', 'Corscot'),
       ('bc130695-fcef-47ad-8d97-0e032be574c9', 'Kirzhach', 'Russia', '56.0488224', '39.0652818', '75018', 'Everett'),
       ('c9f0b999-abfa-4ac1-8809-aad34e2cd0c6', 'Bairro', 'Portugal', '41.2828507', '-8.2869478', '648', 'Bonner'),
       ('dc0bbae7-4fa1-47ca-a092-dde2f9efe046', 'Belfast', 'Canada', '46.08341', '-62.88197', '1414', 'Charing Cross'),
       ('553414e2-857e-49b0-85c8-e08f9edc9ae3', 'Ar Rubū‘', 'Yemen', '13.78024', '43.86901', '30', 'Riverside'),
       ('c89aee55-f73f-4bb1-accf-73bad65c36ff', 'Corujeira', 'Portugal', '40.3829273', '-8.7241528', '4', 'Dwight'),
       ('1870b7fd-be6b-43b9-ac6d-d4ca0b9ac416', 'Carcassonne', 'France', '44.346784', '5.8855505', '2775', 'Fallview'),
       ('e307e5e7-f8f6-41d4-a61c-befad5b75ffc', 'Kraskino', 'Russia', '42.7109791', '130.7842448', '48', 'Helena'),
       ('1744ff0b-90dd-4649-9439-da2bd62918ad', 'Liren', 'China', '34.2456501', '108.9877602', '829', 'Canary'),
       ('2d128330-e662-4fda-b55f-7fb5593b8f4f', 'Al Buq‘ah', 'Palestinian Territory', '31.5344951', '35.1245284', '52',
        'Sugar'),
       ('551082eb-b45a-4b6b-8ccd-2cae2b8cb20c', 'Uji', 'Japan', '35.3434929', '132.9039749', '643', 'Northland'),
       ('aa826b84-29aa-4167-a7ee-97e02587aea1', 'Xingtai', 'China', '37.070834', '114.504677', '4082', 'Forest Dale'),
       ('e8475b09-a100-42fa-8a17-7a040924c500', 'Banjar Baleagung', 'Indonesia', '-8.3797336', '115.1681911', '1732',
        'Judy'),
       ('d795331e-5d59-4221-a318-9a4045251c0f', 'Keren', 'Eritrea', '15.7800173', '38.4534461', '7535', 'Judy'),
       ('46876b56-3b9b-41de-8dc9-0925d44089c5', 'Aveleda', 'Portugal', '41.1150364', '-8.2958281', '88', 'Sutteridge'),
       ('f896b9e4-b639-43cc-882f-9d735597ef08', 'Odivelas', 'Portugal', '38.1678374', '-8.1496483', '540', 'Havey'),
       ('640e93ad-460b-40aa-84be-5673a091e2b5', 'Monte Aprazível', 'Brazil', '-20.7736517', '-49.7107263', '62',
        'Mayfield'),
       ('e1fcc078-445b-4243-af17-2e25f86ab352', 'Peteranec', 'Croatia', '46.1743441', '16.9184542', '30712', 'Stuart'),
       ('b58dcc40-13f0-46ed-894c-23a66d6faefd', 'Nanterre', 'France', '48.8967143', '2.1955586', '73384', 'Kropf'),
       ('bac13983-1229-4308-8ffd-22e8c52235f8', 'Haguimit', 'Philippines', '7.0649018', '125.7230302', '2086', 'Esch'),
       ('1d0da8df-c565-49ea-aee5-e788789e3b70', 'Yonezawa', 'Japan', '37.2516991', '139.007103', '7', 'Victoria'),
       ('8021c0fb-99a5-42f0-9d81-baa41265540e', 'Lisewo Malborskie', 'Poland', '54.0962372', '18.82934', '36',
        'Hanson'),
       ('adb7aab0-c6ff-49e4-a151-da471ef18f09', 'Longcang', 'China', '29.339476', '105.287612', '6', 'Continental'),
       ('0532cd76-52c8-4ac9-8306-d063f8f5e1d5', 'Matriz de Camaragibe', 'Brazil', '-9.0972502', '-35.5876697', '7890',
        'La Follette'),
       ('4d7006f5-d219-4871-ab66-e66d54eab457', 'Bungur', 'Indonesia', '-6.1718076', '106.8488128', '21775', 'Banding'),
       ('ea32e111-54e5-4e19-82a9-3098e7d1612b', 'Huangqiang', 'China', '22.2304314', '113.5411611', '4', 'Kropf'),
       ('1e1dd683-41ff-469f-b8a7-a413d64e694b', 'Saint Ann’s Bay', 'Jamaica', '18.4329473', '-77.1973997', '7',
        'Hintze'),
       ('24152c65-435d-4340-939a-42ba9947912b', 'Komprachcice', 'Poland', '50.6375507', '17.8149641', '35', 'Carioca');


INSERT INTO "coupon" ("id", "description", "discount")
VALUES ('d43bc35e-bc4b-4566-b076-320f5c669c52', 'Livepath', '52'),
       ('ee9ad85a-a270-484d-8f46-54334b56cd68', 'Thoughtblab', '68'),
       ('ba777665-42c9-4a59-a6e9-f695ce1cba7a', 'Browsebug', '10'),
       ('9888438d-3f98-426f-a153-0f369f2b5cf8', 'Skibox', '13'),
       ('fa6bab80-8e2a-437e-98cc-a0190ec3f653', 'Skilith', '12'),
       ('9a1c03ff-af50-42f2-b302-2bb8bf396ca7', 'Fanoodle', '30'),
       ('7fd27d29-2c9a-4026-9c79-4d3f8c795853', 'Pixope', '30'),
       ('0a3a1559-feda-4032-a8df-77a4e507711c', 'Livefish', '29'),
       ('78c30545-bc32-4156-929a-e18619739961', 'Voolia', '31'),
       ('aeca40e3-030a-4f04-9385-ce6df9f400fd', 'Jaloo', '36'),
       ('2cf0b333-d4bd-405d-9354-53bddbd006b5', 'Browsezoom', '23'),
       ('5acc00bf-dd11-48d4-a10d-5197fee6d489', 'Jabberstorm', '66'),
       ('9dd5ff96-fff2-4f3c-9063-4c0e61293423', 'Divavu', '53'),
       ('33b0443f-6e70-4462-b0f2-2d7cd751b119', 'Meedoo', '55'),
       ('86f0fe75-6e7c-45e4-a6c8-0d0fc50936fd', 'Divavu', '66'),
       ('c87a9520-a413-482a-8f2c-889ac919c046', 'Yombu', '61'),
       ('d57c25ce-aa52-46f1-afc2-933bdf2a525b', 'Tagtune', '44'),
       ('0af16889-d20a-4afd-88c5-e02e8482ba4a', 'Rhynyx', '46'),
       ('0211da39-481f-4a64-a317-4d1a8a6cd00c', 'Leenti', '21'),
       ('9a75c6c5-a9ae-456f-bbdf-b1cb24fc4a94', 'Dynazzy', '65');

INSERT INTO "loyalty_type" ("id", "category_name", "points_req")
VALUES ('d546d803-e4fe-4ba6-b8e0-6e482ad48e58', 'No Loyalty', '0'),
       ('31144a9f-b305-4e1e-90cc-299fbd33ca5d', 'Bronze', '100'),
       ('3c113733-4fa9-475c-8e0d-fb58bff8041e', 'Silver', '200'),
       ('09779dc7-8c6c-47bb-b68d-aa67c63af8f8', 'Gold', '300');

INSERT INTO "loyalty_coupons" ("loyalty_id", "coupon_id")
VALUES ('d546d803-e4fe-4ba6-b8e0-6e482ad48e58', 'd43bc35e-bc4b-4566-b076-320f5c669c52'),
       ('31144a9f-b305-4e1e-90cc-299fbd33ca5d', 'ee9ad85a-a270-484d-8f46-54334b56cd68'),
       ('3c113733-4fa9-475c-8e0d-fb58bff8041e', 'ba777665-42c9-4a59-a6e9-f695ce1cba7a'),
       ('09779dc7-8c6c-47bb-b68d-aa67c63af8f8', '9888438d-3f98-426f-a153-0f369f2b5cf8'),
       ('d546d803-e4fe-4ba6-b8e0-6e482ad48e58', 'fa6bab80-8e2a-437e-98cc-a0190ec3f653'),
       ('31144a9f-b305-4e1e-90cc-299fbd33ca5d', '9a1c03ff-af50-42f2-b302-2bb8bf396ca7'),
       ('3c113733-4fa9-475c-8e0d-fb58bff8041e', '7fd27d29-2c9a-4026-9c79-4d3f8c795853'),
       ('09779dc7-8c6c-47bb-b68d-aa67c63af8f8', '0a3a1559-feda-4032-a8df-77a4e507711c'),
       ('d546d803-e4fe-4ba6-b8e0-6e482ad48e58', '78c30545-bc32-4156-929a-e18619739961'),
       ('31144a9f-b305-4e1e-90cc-299fbd33ca5d', 'aeca40e3-030a-4f04-9385-ce6df9f400fd'),
       ('3c113733-4fa9-475c-8e0d-fb58bff8041e', '2cf0b333-d4bd-405d-9354-53bddbd006b5'),
       ('09779dc7-8c6c-47bb-b68d-aa67c63af8f8', '5acc00bf-dd11-48d4-a10d-5197fee6d489'),
       ('d546d803-e4fe-4ba6-b8e0-6e482ad48e58', '9dd5ff96-fff2-4f3c-9063-4c0e61293423'),
       ('31144a9f-b305-4e1e-90cc-299fbd33ca5d', '33b0443f-6e70-4462-b0f2-2d7cd751b119'),
       ('3c113733-4fa9-475c-8e0d-fb58bff8041e', '86f0fe75-6e7c-45e4-a6c8-0d0fc50936fd'),
       ('09779dc7-8c6c-47bb-b68d-aa67c63af8f8', 'c87a9520-a413-482a-8f2c-889ac919c046'),
       ('d546d803-e4fe-4ba6-b8e0-6e482ad48e58', 'd57c25ce-aa52-46f1-afc2-933bdf2a525b'),
       ('31144a9f-b305-4e1e-90cc-299fbd33ca5d', '0af16889-d20a-4afd-88c5-e02e8482ba4a'),
       ('3c113733-4fa9-475c-8e0d-fb58bff8041e', '0211da39-481f-4a64-a317-4d1a8a6cd00c'),
       ('09779dc7-8c6c-47bb-b68d-aa67c63af8f8', '9a75c6c5-a9ae-456f-bbdf-b1cb24fc4a94');

INSERT INTO "blood_center" ("id", "description", "name", "rating", "end_hours", "end_minutes", "start_hours",
                            "start_minutes", "address_id")
VALUES ('417c9b36-251a-4483-bfbf-abd3df786d96', 'LUMENE OY', 'Will-Wiza', '3', '11', '30', '5', '0',
        '8021c0fb-99a5-42f0-9d81-baa41265540e'),
       ('aa116b1b-c59c-403b-b64f-db201d9375d2', '"Lannett Company, Inc."', '"Grimes, Kautzer and Lebsack"', '2', '17',
        '0', '6', '0', '553414e2-857e-49b0-85c8-e08f9edc9ae3'),
       ('206b28a3-4953-4cce-a077-c7d9fcdd599c', 'Mondel?z Global LLC', '"Tillman, Robel and DuBuque"', '4', '15', '30',
        '7', '30', '5c3b169a-c50c-4b90-939d-ee3fca1459ea'),
       ('29e5ab72-265a-483b-84fc-6a7abbf5a6ab', 'Accord Healthcare Inc.', 'Vandervort-Gleason', '2', '15', '30', '9',
        '0', '640e93ad-460b-40aa-84be-5673a091e2b5'),
       ('f7aaaba3-db07-46e8-8976-8e7c6b83ee4d', 'Uriel Pharmacy Inc.', 'Marvin-Stehr', '4', '14', '0', '9', '30',
        'dc0bbae7-4fa1-47ca-a092-dde2f9efe046'),
       ('c5380ac1-b5cb-4c3e-a5f7-5b1cd25c91f2', 'Pharmacia and Upjohn Company', 'Effertz and Sons', '3', '18', '0', '7',
        '0', 'e4b6ce9d-5094-4a64-a958-3b919770d870'),
       ('bd701425-588c-4d16-ab96-e71be2979a75', '"Preferred Pharmaceuticals, Inc."',
        '"Baumbach, Hartmann and Predovic"', '5', '19', '0', '7', '0', '0532cd76-52c8-4ac9-8306-d063f8f5e1d5'),
       ('e8b6ca12-6a54-4d61-9cda-5a314094968e', 'A-S Medication Solutions LLC', 'Torphy-Fritsch', '4', '18', '30', '8',
        '0', 'e4b6ce9d-5094-4a64-a958-3b919770d870'),
       ('00b4e355-20be-48a6-a192-cfab2f48f012', 'TYA Pharmaceuticals', '"Mosciski, Haley and Denesik"', '5', '16', '0',
        '10', '0', 'c89aee55-f73f-4bb1-accf-73bad65c36ff'),
       ('00f5108f-5ac3-48ed-8b5e-be609d3b4137', 'Rimmel Inc.', '"Runte, Bradtke and Bruen"', '1', '15', '30', '10',
        '30', 'f131fbc0-343e-4b61-9542-a384e4882ba5'),
       ('e642b333-182b-405d-a5b6-038fb518a3db', 'Teva Pharmaceuticals USA Inc', 'Labadie LLC', '4', '18', '0', '10',
        '0', 'bac13983-1229-4308-8ffd-22e8c52235f8'),
       ('63bd71a5-dfdd-4db3-ac00-0b61e6183735', 'Purminerals', 'Hilll-Upton', '1', '15', '30', '8', '30',
        'dc0bbae7-4fa1-47ca-a092-dde2f9efe046'),
       ('2c8956aa-b596-45a1-9713-388c254119fd', '"Dispensing Solutions, Inc."', '"Berge, Cassin and Feil"', '4', '19',
        '0', '9', '0', '1744ff0b-90dd-4649-9439-da2bd62918ad'),
       ('725e573d-e9bd-4658-9da1-d54b72a1d333', 'Kaiser Foundations Hospitals', '"Zulauf, Nolan and Luettgen"', '3',
        '17', '0', '9', '0', '1e1dd683-41ff-469f-b8a7-a413d64e694b'),
       ('65587605-ef93-4beb-a46a-ba141a21ffd7', '"General Injectables & Vaccines, Inc"', '"Gottlieb, Kozey and Metz"',
        '1', '16', '0', '10', '30', '288936ca-16f3-4908-b37c-6fc7b8736bde'),
       ('564661ad-20f3-4aba-841b-34d7ac33217f', '"Aphena Pharma Solutions - Tennessee, LLC"',
        '"Doyle, Padberg and Kon"', '3', '19', '30', '10', '0', '551082eb-b45a-4b6b-8ccd-2cae2b8cb20c'),
       ('d08dd731-d59f-43c8-b2ff-785245f6048b', 'Kroger Company', '"Cummings, Kutch and Huel"', '2', '18', '0', '7',
        '0', 'b1b2dbfc-4b92-4cb6-931f-7c136e86f80b'),
       ('1dc2e158-9faa-4940-9373-879fbe8ceada', 'Teva Pharmaceuticals USA Inc', 'Hara Group', '3', '18', '0', '7', '0',
        'dc0bbae7-4fa1-47ca-a092-dde2f9efe046'),
       ('73548b7b-3695-4e37-989c-e2ba7a0130d0', 'sanofi-aventis U.S. LLC', '"Schmeler, Fahey and Koelpin"', '5', '14',
        '0', '8', '0', '46876b56-3b9b-41de-8dc9-0925d44089c5'),
       ('5f29fc03-f6bd-49a6-9f41-c2ca229de403', '"Energique,
        Inc."', 'Brekke and Sons', '5', '15', '30', '7', '0', '553414e2-857e-49b0-85c8-e08f9edc9ae3');

INSERT INTO "blood_donor" ("id", "name", "phone_number", "surname", "gender", "institution", "jmbg", "penalties",
                           "points", "address_id", "occupation")
VALUES ('07ce2e8b-d34b-4156-9dd4-f29ec4311675', 'Jovan', '140-486-9131', 'Srdanov', '1', 'Valve', '8541087715936',
        '0',
        '0', 'a0cd0598-30f7-430d-8cc5-ef309d31ffb7', 'Shadow Scrum Master');

INSERT INTO "blood_donor" ("id", "name", "phone_number", "surname", "gender", "institution", "jmbg", "penalties",
                           "points", "address_id", "occupation")
VALUES ('26d3381b-319d-425c-abd7-256f24f0a2e0', 'Jovan2', '22140-486-9131', 'Srdanov2', '1', 'Valve2', '8541087515936',
        '75',
        '0', 'a0cd0598-30f7-430d-8cc5-ef309d31ffb7', 'Shadow Scrum Master2');


INSERT INTO "staff" ("id", "name", "phone_number", "surname", "address_id", "blood_center_id")
VALUES ('b2fe1aff-bb8f-4fd1-b88e-aa39af8fc65c', 'Doc1', '850-326-8246', '_',
        'dc0bbae7-4fa1-47ca-a092-dde2f9efe046', '417c9b36-251a-4483-bfbf-abd3df786d96'),
       ('883f13cc-25c8-4b60-b0e5-5ac938ad2594', 'Doc2', '844-991-4460', '_',
        'f896b9e4-b639-43cc-882f-9d735597ef08', '417c9b36-251a-4483-bfbf-abd3df786d96'),
       ('1e2df797-37b7-4241-a2b0-99e262a20912', 'Doc3', '844-991-4460', '_',
        'f896b9e4-b639-43cc-882f-9d735597ef08', '417c9b36-251a-4483-bfbf-abd3df786d96'),
       ('fa41a439-0f90-46a8-96af-16bc1068d50c', 'Doc4', '844-991-4460', '_',
        'f896b9e4-b639-43cc-882f-9d735597ef08', '417c9b36-251a-4483-bfbf-abd3df786d96'),
       ('8180fea1-7623-4a5b-8717-5b34b2abe9d3', 'Doc5', '844-991-4460', '_',
        'f896b9e4-b639-43cc-882f-9d735597ef08', '417c9b36-251a-4483-bfbf-abd3df786d96'),
       ('e3bd5294-441f-4ae3-807d-d85314d91e50', 'Doc6', '844-991-4460', '_',
        'f896b9e4-b639-43cc-882f-9d735597ef08', 'aa116b1b-c59c-403b-b64f-db201d9375d2');



INSERT INTO "account" ("id", "email", "is_activated", "password", "person_id")
VALUES ('fbf7d96c-0bac-4081-bc8d-0d32e2ab0ec1', 'blooddonor@gmail.com', 'true',
        '$2y$10$49qXN7cAPT7IanIsSk.3Be8GFLskf6eL.mcNQQAW6w0RRHCymHyBi',
        '07ce2e8b-d34b-4156-9dd4-f29ec4311675');

INSERT INTO "account" ("id", "email", "is_activated", "password", "person_id")
VALUES ('fbf7d96c-0bac-4081-bc8d-0d33e2ab0ec1', 'blooddonor2@gmail.com', 'true',
        '$2y$10$49qXN7cAPT7IanIsSk.3Be8GFLskf6eL.mcNQQAW6w0RRHCymHyBi',
        '26d3381b-319d-425c-abd7-256f24f0a2e0');

INSERT INTO "account" ("id", "email", "is_activated", "password", "person_id")
VALUES ('fbf7d96c-0bac-4081-bc8d-0d32e2ab0ec2', 'staffnopass@gmail.com', 'true',
        '$2y$10$49qXN7cAPT7IanIsSk.3Be8GFLskf6eL.mcNQQAW6w0RRHCymHyBi',
        'b2fe1aff-bb8f-4fd1-b88e-aa39af8fc65c');

INSERT INTO "account" ("id", "email", "is_activated", "password", "person_id")
VALUES ('fbf7d96c-0bac-4081-bc8d-0d32e2ab0ec3', 'staff@gmail.com', 'true',
        '$2y$10$49qXN7cAPT7IanIsSk.3Be8GFLskf6eL.mcNQQAW6w0RRHCymHyBi',
        '32763cf0-6a56-49f1-ad25-5a90561cd204');

INSERT INTO "account" ("id", "email", "is_activated", "password")
VALUES ('fbf7d96c-0bac-4081-bc8d-0d32e2ab0ec4', 'sysadmin@gmail.com', 'true',
        '$2y$10$49qXN7cAPT7IanIsSk.3Be8GFLskf6eL.mcNQQAW6w0RRHCymHyBi');


INSERT INTO account_role (account_id, role_id)
VALUES ('fbf7d96c-0bac-4081-bc8d-0d33e2ab0ec1', 1);
INSERT INTO account_role (account_id, role_id)
VALUES ('fbf7d96c-0bac-4081-bc8d-0d32e2ab0ec1', 1);
INSERT INTO account_role (account_id, role_id)
VALUES ('fbf7d96c-0bac-4081-bc8d-0d32e2ab0ec2', 2);
INSERT INTO account_role (account_id, role_id)
VALUES ('fbf7d96c-0bac-4081-bc8d-0d32e2ab0ec3', 2);
INSERT INTO account_role (account_id, role_id)
VALUES ('fbf7d96c-0bac-4081-bc8d-0d32e2ab0ec4', 3);






INSERT INTO "blood_quantity" ("id", "quantity", "blood_group", "blood_center_id")
VALUES ('96ea2710-8c48-458b-b5d3-6256d087f257', '79', '0', '417c9b36-251a-4483-bfbf-abd3df786d96'),
       ('ae16c5e9-5d37-4eb1-bbbf-bd56ea2f7588', '88', '0', 'aa116b1b-c59c-403b-b64f-db201d9375d2'),
       ('1e0bbb68-48f8-4576-ab04-affe0c06806e', '91', '0', '206b28a3-4953-4cce-a077-c7d9fcdd599c'),
       ('d44e74e8-2ca9-4998-bcff-4003bf021b23', '28', '0', '29e5ab72-265a-483b-84fc-6a7abbf5a6ab'),
       ('79476721-c1ae-4f27-998c-8e72e9629cac', '27', '0', 'f7aaaba3-db07-46e8-8976-8e7c6b83ee4d'),
       ('63b0e366-03a5-4e5f-82f8-175b5bf89e5b', '9', '0', 'c5380ac1-b5cb-4c3e-a5f7-5b1cd25c91f2'),
       ('b6b157bf-5060-4b46-8973-f112b8871058', '66', '0', 'bd701425-588c-4d16-ab96-e71be2979a75'),
       ('d3892d9a-659d-43be-a574-0867560f59cf', '89', '0', 'e8b6ca12-6a54-4d61-9cda-5a314094968e'),
       ('837a0b74-e9e4-4f79-bd78-9286d89ca1fc', '70', '0', '00b4e355-20be-48a6-a192-cfab2f48f012'),
       ('267f3094-dd62-4d2f-a69a-7a496549c202', '56', '0', '00f5108f-5ac3-48ed-8b5e-be609d3b4137'),
       ('3e7cab68-e2fc-43c7-ae72-9335d65941f3', '100', '0', 'e642b333-182b-405d-a5b6-038fb518a3db'),
       ('8d48565e-8285-488a-bdb1-dd965d3b23e0', '8', '0', '63bd71a5-dfdd-4db3-ac00-0b61e6183735'),
       ('6ce69a34-1135-4961-96c8-b0f8a60b0db2', '40', '0', '2c8956aa-b596-45a1-9713-388c254119fd'),
       ('9a5bb378-d415-4439-94a4-5b734426fbaa', '52', '0', '725e573d-e9bd-4658-9da1-d54b72a1d333'),
       ('11af67b8-da00-44cf-b506-ea5e50b56858', '66', '0', '65587605-ef93-4beb-a46a-ba141a21ffd7'),
       ('ca7539eb-d97b-4a94-b5bd-501d343130d3', '67', '0', '564661ad-20f3-4aba-841b-34d7ac33217f'),
       ('8694d736-9826-4c3c-bfe9-7c28972fde8b', '22', '0', 'd08dd731-d59f-43c8-b2ff-785245f6048b'),
       ('5ebc38b1-1394-4113-a1ab-354cf8d4d2c9', '28', '0', '1dc2e158-9faa-4940-9373-879fbe8ceada'),
       ('7c70cc0b-49e7-413f-a74e-0cb780732cf5', '30', '0', '73548b7b-3695-4e37-989c-e2ba7a0130d0'),
       ('f6ce18d1-c6a6-423b-b6f8-aba98046798a', '37', '0', '5f29fc03-f6bd-49a6-9f41-c2ca229de403'),
       ('e5308475-a341-4930-829c-5c08bbeac7db', '77', '1', '417c9b36-251a-4483-bfbf-abd3df786d96'),
       ('c9e2dad3-f17c-4eea-932b-d50708fbedbc', '53', '1', 'aa116b1b-c59c-403b-b64f-db201d9375d2'),
       ('15538d98-0a8d-4d9b-93d5-ad92a5207cf3', '40', '1', '206b28a3-4953-4cce-a077-c7d9fcdd599c'),
       ('e611a795-fa11-4289-b20b-cd2dd7c76d1b', '95', '1', '29e5ab72-265a-483b-84fc-6a7abbf5a6ab'),
       ('6d2012b6-c6f6-4ab8-8559-a31ac4294374', '73', '1', 'f7aaaba3-db07-46e8-8976-8e7c6b83ee4d'),
       ('713acd59-1eb1-4fa1-953c-3e5cc3ed308e', '51', '1', 'c5380ac1-b5cb-4c3e-a5f7-5b1cd25c91f2'),
       ('4571fbe0-4e14-44b9-a3bb-09c2881f92e8', '18', '1', 'bd701425-588c-4d16-ab96-e71be2979a75'),
       ('7e74b501-f452-43e5-8813-ad7b52192702', '14', '1', 'e8b6ca12-6a54-4d61-9cda-5a314094968e'),
       ('e63e63fb-dda0-4624-8753-f78d439a3b31', '1', '1', '00b4e355-20be-48a6-a192-cfab2f48f012'),
       ('74d11441-c9cb-40e7-acea-80c25afd2350', '23', '1', '00f5108f-5ac3-48ed-8b5e-be609d3b4137'),
       ('76f8f34b-2b42-4b88-a478-ad39fe9c6a11', '25', '1', 'e642b333-182b-405d-a5b6-038fb518a3db'),
       ('1b755d83-d2b3-4994-b7bd-875e7de4e92e', '31', '1', '63bd71a5-dfdd-4db3-ac00-0b61e6183735'),
       ('c85474e1-9227-45f5-86a2-37722142fdb9', '58', '1', '2c8956aa-b596-45a1-9713-388c254119fd'),
       ('ac42cafb-440b-49d8-8896-c58d0ce95464', '29', '1', '725e573d-e9bd-4658-9da1-d54b72a1d333'),
       ('f8270f9f-92d5-4d9a-b248-2e1c98d40820', '36', '1', '65587605-ef93-4beb-a46a-ba141a21ffd7'),
       ('4a296867-c0cc-4ae9-99ce-43427307bdee', '65', '1', '564661ad-20f3-4aba-841b-34d7ac33217f'),
       ('8842e1b6-6550-402b-9ec8-fc06913f92c5', '3', '1', 'd08dd731-d59f-43c8-b2ff-785245f6048b'),
       ('3e7cfeee-ab78-4ca3-8434-2d849a1c724f', '99', '1', '1dc2e158-9faa-4940-9373-879fbe8ceada'),
       ('74432031-d393-4a40-a085-90d14dec0a85', '59', '1', '73548b7b-3695-4e37-989c-e2ba7a0130d0'),
       ('7fb81ae6-88c4-4a6d-a7df-3e6346b1fea8', '47', '1', '5f29fc03-f6bd-49a6-9f41-c2ca229de403'),
       ('79e80bd4-d428-40bf-8205-a9712391f605', '100', '2', '417c9b36-251a-4483-bfbf-abd3df786d96'),
       ('ce02b08a-58c9-4476-88bc-31a3a8e82b00', '23', '2', 'aa116b1b-c59c-403b-b64f-db201d9375d2'),
       ('ad5f0d1e-e418-48d9-b92c-6f35a56f364b', '64', '2', '206b28a3-4953-4cce-a077-c7d9fcdd599c'),
       ('7bc81936-4638-43a6-8783-a16042dfc5eb', '88', '2', '29e5ab72-265a-483b-84fc-6a7abbf5a6ab'),
       ('f81cb350-3659-4f18-a0fb-380c62408a68', '9', '2', 'f7aaaba3-db07-46e8-8976-8e7c6b83ee4d'),
       ('ef6174c5-6ec7-45f5-ac32-3809d757f6a1', '17', '2', 'c5380ac1-b5cb-4c3e-a5f7-5b1cd25c91f2'),
       ('634651df-a600-412c-87c6-a8ce32f2ac4b', '68', '2', 'bd701425-588c-4d16-ab96-e71be2979a75'),
       ('93defe77-fe49-441a-9005-905c19775862', '56', '2', 'e8b6ca12-6a54-4d61-9cda-5a314094968e'),
       ('95b66d5d-e499-4c8b-87d1-f6d3c3e0ba88', '93', '2', '00b4e355-20be-48a6-a192-cfab2f48f012'),
       ('bae0714c-f2cd-459f-8299-a4a3457951fb', '31', '2', '00f5108f-5ac3-48ed-8b5e-be609d3b4137'),
       ('1330dfc5-aca9-4f0d-be1f-84f3b6739ef1', '6', '2', 'e642b333-182b-405d-a5b6-038fb518a3db'),
       ('888364a1-a6d5-4cad-8f25-7b6dbb139598', '46', '2', '63bd71a5-dfdd-4db3-ac00-0b61e6183735'),
       ('74a039a8-43b2-4f5b-ab7e-091ed4ac5f17', '80', '2', '2c8956aa-b596-45a1-9713-388c254119fd'),
       ('72c2a07e-be3a-40d6-855b-112aa79f3ce2', '62', '2', '725e573d-e9bd-4658-9da1-d54b72a1d333'),
       ('9b2decbb-f7d2-49f7-bc2d-9e4e2517c973', '43', '2', '65587605-ef93-4beb-a46a-ba141a21ffd7'),
       ('fd7da503-4a47-460c-ac3d-980767820b6b', '54', '2', '564661ad-20f3-4aba-841b-34d7ac33217f'),
       ('923aaa72-47c8-4cb2-af93-6e60345b2a49', '39', '2', 'd08dd731-d59f-43c8-b2ff-785245f6048b'),
       ('0c36e625-9740-448b-88a8-c1d86241643d', '73', '2', '1dc2e158-9faa-4940-9373-879fbe8ceada'),
       ('52607246-c903-4114-bbdf-f855e41ef055', '13', '2', '73548b7b-3695-4e37-989c-e2ba7a0130d0'),
       ('406d5f81-e8c8-4919-813a-e96e531f5b8a', '65', '2', '5f29fc03-f6bd-49a6-9f41-c2ca229de403'),
       ('6980b5a5-36af-46c5-aa35-edff210c8e7d', '61', '3', '417c9b36-251a-4483-bfbf-abd3df786d96'),
       ('a0240893-e27b-42f0-9904-a4e1a03be109', '80', '3', 'aa116b1b-c59c-403b-b64f-db201d9375d2'),
       ('2cc451d6-1dca-4425-9fa0-2ce4eaf5435f', '35', '3', '206b28a3-4953-4cce-a077-c7d9fcdd599c'),
       ('f0ef3525-09a8-4eeb-af9c-fcb79f6b8a75', '38', '3', '29e5ab72-265a-483b-84fc-6a7abbf5a6ab'),
       ('0ef1e0a2-cc42-4ea0-aa6c-d4f7bed3b795', '45', '3', 'f7aaaba3-db07-46e8-8976-8e7c6b83ee4d'),
       ('39370079-9a49-4804-85c4-7cabe7ebd2b3', '64', '3', 'c5380ac1-b5cb-4c3e-a5f7-5b1cd25c91f2'),
       ('cb25ffc7-4c29-441f-a64e-84b75fe6bb13', '22', '3', 'bd701425-588c-4d16-ab96-e71be2979a75'),
       ('c6692083-e827-493c-b989-18068f80501a', '71', '3', 'e8b6ca12-6a54-4d61-9cda-5a314094968e'),
       ('704f7704-4751-4c64-95d5-d959a2943124', '13', '3', '00b4e355-20be-48a6-a192-cfab2f48f012'),
       ('6bff9bf9-4ff6-44d3-ba52-94a599382b74', '3', '3', '00f5108f-5ac3-48ed-8b5e-be609d3b4137'),
       ('51aa70cf-94ce-4ce6-9a07-9b57f2d7c043', '83', '3', 'e642b333-182b-405d-a5b6-038fb518a3db'),
       ('a3326945-974d-4d03-b4a5-8437e8223e97', '56', '3', '63bd71a5-dfdd-4db3-ac00-0b61e6183735'),
       ('6c518742-580c-4876-b8f5-0df8a2a97c6c', '29', '3', '2c8956aa-b596-45a1-9713-388c254119fd'),
       ('894fb38e-a9ba-4c21-a095-e9b6080f6988', '58', '3', '725e573d-e9bd-4658-9da1-d54b72a1d333'),
       ('51814d4c-935b-4413-aa92-24d45edcfa7e', '88', '3', '65587605-ef93-4beb-a46a-ba141a21ffd7'),
       ('253d36a1-07e3-44ef-aae5-b2056060e95b', '11', '3', '564661ad-20f3-4aba-841b-34d7ac33217f'),
       ('8e51da01-1d54-4083-b2fa-e290e10a7aca', '7', '3', 'd08dd731-d59f-43c8-b2ff-785245f6048b'),
       ('ef0c5b02-87a5-45df-98ec-55807b55d56e', '13', '3', '1dc2e158-9faa-4940-9373-879fbe8ceada'),
       ('07f210e9-e03c-4902-8ebc-d95d9b2ec877', '33', '3', '73548b7b-3695-4e37-989c-e2ba7a0130d0'),
       ('e94b334e-c5d2-4ee9-a06d-cc649a69350b', '37', '3', '5f29fc03-f6bd-49a6-9f41-c2ca229de403'),
       ('d898e8dd-8266-4d29-af97-3be2de344500', '76', '4', '417c9b36-251a-4483-bfbf-abd3df786d96'),
       ('507290f5-c15f-46fd-908d-2db8e409d429', '22', '4', 'aa116b1b-c59c-403b-b64f-db201d9375d2'),
       ('4534aac7-e10b-4b88-8967-d0cd6405a8f6', '68', '4', '206b28a3-4953-4cce-a077-c7d9fcdd599c'),
       ('0ece2542-5caf-43ba-930e-8cb0546581d7', '11', '4', '29e5ab72-265a-483b-84fc-6a7abbf5a6ab'),
       ('41ea586f-1f6b-4752-b3ca-5867646f1aa2', '20', '4', 'f7aaaba3-db07-46e8-8976-8e7c6b83ee4d'),
       ('62cc676d-2959-4e5c-a933-de917df6e2e3', '78', '4', 'c5380ac1-b5cb-4c3e-a5f7-5b1cd25c91f2'),
       ('bb4b6d5d-c62b-47f3-8b82-c95efb4f809f', '7', '4', 'bd701425-588c-4d16-ab96-e71be2979a75'),
       ('4864c257-bab3-4a0e-82be-6168f9de79f8', '29', '4', 'e8b6ca12-6a54-4d61-9cda-5a314094968e'),
       ('c3eede10-ae16-4238-b6d3-570145f74e9e', '37', '4', '00b4e355-20be-48a6-a192-cfab2f48f012'),
       ('c4cb04a6-4b96-4b9e-aab1-6b5d799a9042', '10', '4', '00f5108f-5ac3-48ed-8b5e-be609d3b4137'),
       ('223a276e-1da1-4fa6-96c1-91f16b21363e', '73', '4', 'e642b333-182b-405d-a5b6-038fb518a3db'),
       ('40bd0595-05d4-47bb-8c55-23f66c1acde1', '79', '4', '63bd71a5-dfdd-4db3-ac00-0b61e6183735'),
       ('a44b1be6-1cfa-446b-9005-af24a16a1847', '55', '4', '2c8956aa-b596-45a1-9713-388c254119fd'),
       ('1abfd78a-01d5-470b-83c6-c0b5b837cd22', '55', '4', '725e573d-e9bd-4658-9da1-d54b72a1d333'),
       ('aef6892e-a13f-47b7-93b0-ab284414599a', '66', '4', '65587605-ef93-4beb-a46a-ba141a21ffd7'),
       ('a115fdf5-aef4-42ce-970a-d72ae60456e1', '72', '4', '564661ad-20f3-4aba-841b-34d7ac33217f'),
       ('d55fed8a-a141-4239-9403-9c2930bbd008', '59', '4', 'd08dd731-d59f-43c8-b2ff-785245f6048b'),
       ('c305f136-581a-43dd-b8fb-1c55b2252d8c', '26', '4', '1dc2e158-9faa-4940-9373-879fbe8ceada'),
       ('0f875da7-9d13-4324-a09d-ffb756027084', '66', '4', '73548b7b-3695-4e37-989c-e2ba7a0130d0'),
       ('beeafe29-6455-4665-9bca-5a07f1ea0cc4', '2', '4', '5f29fc03-f6bd-49a6-9f41-c2ca229de403'),
       ('73fcefb8-24a5-4280-b680-b8a6ba9fb3de', '26', '5', '417c9b36-251a-4483-bfbf-abd3df786d96'),
       ('4bb4e660-0245-44b7-b7e6-f4e3f331f8c8', '96', '5', 'aa116b1b-c59c-403b-b64f-db201d9375d2'),
       ('6bea1db6-386e-4b02-b9cb-87798cfc5897', '79', '5', '206b28a3-4953-4cce-a077-c7d9fcdd599c'),
       ('55539cf5-6159-44b6-a8f7-5ca01d06b4ac', '60', '5', '29e5ab72-265a-483b-84fc-6a7abbf5a6ab'),
       ('7d1f7683-73cb-49d1-ba05-5de7e1dcc44e', '86', '5', 'f7aaaba3-db07-46e8-8976-8e7c6b83ee4d'),
       ('74b96bf5-fa6a-4386-a5c0-f6eb8952abed', '94', '5', 'c5380ac1-b5cb-4c3e-a5f7-5b1cd25c91f2'),
       ('243fb6b9-2a6c-495e-93c7-6c679c292d6a', '54', '5', 'bd701425-588c-4d16-ab96-e71be2979a75'),
       ('9f279241-2549-472b-993e-dab275687d59', '55', '5', 'e8b6ca12-6a54-4d61-9cda-5a314094968e'),
       ('20262e80-57c5-4a4d-b399-95b0a578b7bd', '72', '5', '00b4e355-20be-48a6-a192-cfab2f48f012'),
       ('49c9fbfc-df64-40e9-881f-8160c0534785', '45', '5', '00f5108f-5ac3-48ed-8b5e-be609d3b4137'),
       ('e6478d4a-b853-4123-a4a2-9c62bebc2d3c', '37', '5', 'e642b333-182b-405d-a5b6-038fb518a3db'),
       ('b945b007-8e43-4326-b80d-3b242f716bab', '2', '5', '63bd71a5-dfdd-4db3-ac00-0b61e6183735'),
       ('a330fdf0-0b4e-4c32-a3fe-24bb4b6590c4', '7', '5', '2c8956aa-b596-45a1-9713-388c254119fd'),
       ('a9467d1a-1a3c-4b17-a213-88627be0d705', '99', '5', '725e573d-e9bd-4658-9da1-d54b72a1d333'),
       ('60fd86c5-1d93-4e76-89d4-7a475afa16d5', '32', '5', '65587605-ef93-4beb-a46a-ba141a21ffd7'),
       ('37398148-3281-4133-a8ab-96b74f78c3a7', '23', '5', '564661ad-20f3-4aba-841b-34d7ac33217f'),
       ('11ba5a3e-b7fa-41f5-ad6e-de15c25fd551', '26', '5', 'd08dd731-d59f-43c8-b2ff-785245f6048b'),
       ('7444735e-22f7-45d3-8977-76a90ef0afce', '50', '5', '1dc2e158-9faa-4940-9373-879fbe8ceada'),
       ('c7990064-011b-4f36-881f-0ecc81e8db43', '56', '5', '73548b7b-3695-4e37-989c-e2ba7a0130d0'),
       ('da071a0b-c154-4ca8-9fa6-0d9c5159a2f4', '33', '5', '5f29fc03-f6bd-49a6-9f41-c2ca229de403'),
       ('52522ba3-a7dd-489c-bed5-42e2e8a9b9b3', '72', '6', '417c9b36-251a-4483-bfbf-abd3df786d96'),
       ('cc0cc602-25da-49b7-88a2-75d48b63b5e8', '21', '6', 'aa116b1b-c59c-403b-b64f-db201d9375d2'),
       ('df67e36f-a883-4c74-aec5-17898fe3e747', '55', '6', '206b28a3-4953-4cce-a077-c7d9fcdd599c'),
       ('bfbd32f8-330b-4334-b47b-21c12d00b52b', '22', '6', '29e5ab72-265a-483b-84fc-6a7abbf5a6ab'),
       ('7d096509-b329-418a-998d-244d4d6dd39c', '52', '6', 'f7aaaba3-db07-46e8-8976-8e7c6b83ee4d'),
       ('1a014d77-75d6-4ac1-991c-4d0ab5523790', '44', '6', 'c5380ac1-b5cb-4c3e-a5f7-5b1cd25c91f2'),
       ('81b8248b-2d74-4dc1-b269-8dd4743a153a', '74', '6', 'bd701425-588c-4d16-ab96-e71be2979a75'),
       ('859bf271-0b29-4eac-876b-4e6dfbe3a2b4', '62', '6', 'e8b6ca12-6a54-4d61-9cda-5a314094968e'),
       ('475d7063-92b3-438f-813e-1d56d0e0d7a6', '100', '6', '00b4e355-20be-48a6-a192-cfab2f48f012'),
       ('7d087370-fb70-4560-a2aa-166ce8532085', '48', '6', '00f5108f-5ac3-48ed-8b5e-be609d3b4137'),
       ('1f0e65fc-770b-4082-9921-886cba6a4427', '94', '6', 'e642b333-182b-405d-a5b6-038fb518a3db'),
       ('5695a2b0-9d7c-4d86-b440-6cd7d12c3292', '95', '6', '63bd71a5-dfdd-4db3-ac00-0b61e6183735'),
       ('265b4a45-44a0-4467-a43e-32d718c38a05', '71', '6', '2c8956aa-b596-45a1-9713-388c254119fd'),
       ('bd000260-cf30-4e7e-adcc-a1acc18720ba', '54', '6', '725e573d-e9bd-4658-9da1-d54b72a1d333'),
       ('74e607e8-daa7-4c79-831f-42ed574b7b9a', '5', '6', '65587605-ef93-4beb-a46a-ba141a21ffd7'),
       ('0f1fcfc4-a7d5-4dae-81e5-c593fac1fc05', '15', '6', '564661ad-20f3-4aba-841b-34d7ac33217f'),
       ('2815863d-b4a9-4c6f-ac15-3f2acea2718b', '53', '6', 'd08dd731-d59f-43c8-b2ff-785245f6048b'),
       ('41c8589f-3fe0-46a8-84a6-2aef5cdb2441', '59', '6', '1dc2e158-9faa-4940-9373-879fbe8ceada'),
       ('63c46cd3-7af2-4420-931b-83f6946774fc', '81', '6', '73548b7b-3695-4e37-989c-e2ba7a0130d0'),
       ('d5dac8a6-8f79-4811-aeed-3d6ccc24fb5c', '100', '6', '5f29fc03-f6bd-49a6-9f41-c2ca229de403'),
       ('993d6e37-9f43-4228-8ff2-e29e328dcdb8', '8', '7', '417c9b36-251a-4483-bfbf-abd3df786d96'),
       ('1dd65494-4449-47b4-8b0a-061faf08a739', '72', '7', 'aa116b1b-c59c-403b-b64f-db201d9375d2'),
       ('72a6965c-a493-4637-8d1b-2f74c7503cd4', '74', '7', '206b28a3-4953-4cce-a077-c7d9fcdd599c'),
       ('bcfdac6c-5ea7-4108-8c90-ec2811338be8', '81', '7', '29e5ab72-265a-483b-84fc-6a7abbf5a6ab'),
       ('7f221c5a-4daf-4cdf-945d-24870712729a', '77', '7', 'f7aaaba3-db07-46e8-8976-8e7c6b83ee4d'),
       ('053b5f75-0d7d-4756-816a-d64677dfb2d1', '75', '7', 'c5380ac1-b5cb-4c3e-a5f7-5b1cd25c91f2'),
       ('db1ffd6f-9549-43f8-9023-9d3aa0483447', '15', '7', 'bd701425-588c-4d16-ab96-e71be2979a75'),
       ('2abbbc6c-ad3d-4b73-b191-4c9aa35fafb0', '2', '7', 'e8b6ca12-6a54-4d61-9cda-5a314094968e'),
       ('1ede6535-0fd9-4d67-8973-1ec39386f07d', '89', '7', '00b4e355-20be-48a6-a192-cfab2f48f012'),
       ('94755950-3402-425e-be3d-13cac55846d0', '48', '7', '00f5108f-5ac3-48ed-8b5e-be609d3b4137'),
       ('ff88f446-1b2e-402e-beb5-d924a9018171', '16', '7', 'e642b333-182b-405d-a5b6-038fb518a3db'),
       ('a309069e-e2be-4bf6-aa50-312ec1b4fdd1', '51', '7', '63bd71a5-dfdd-4db3-ac00-0b61e6183735'),
       ('fccc02a2-2e49-48bd-8540-5f757285841d', '56', '7', '2c8956aa-b596-45a1-9713-388c254119fd'),
       ('645b0b1f-c479-4806-8afe-94a2204ac27c', '33', '7', '725e573d-e9bd-4658-9da1-d54b72a1d333'),
       ('02f1e4dc-3179-41fb-86cc-d085e7c43b6a', '78', '7', '65587605-ef93-4beb-a46a-ba141a21ffd7'),
       ('d1a3bc3e-85af-46da-b6e3-2a7ff7325ce8', '70', '7', '564661ad-20f3-4aba-841b-34d7ac33217f'),
       ('f7f41d17-cb25-4d3c-a92f-abc793caa3b2', '58', '7', 'd08dd731-d59f-43c8-b2ff-785245f6048b'),
       ('24705880-47de-436c-b2df-5fc4c371c0ba', '18', '7', '1dc2e158-9faa-4940-9373-879fbe8ceada'),
       ('ec0cf96b-fc68-4493-9a44-1af5a3a5a310', '31', '7', '73548b7b-3695-4e37-989c-e2ba7a0130d0'),
       ('be7709e8-f53f-4c0e-b21b-6e38bb458ed1', '95', '7', '5f29fc03-f6bd-49a6-9f41-c2ca229de403');

INSERT INTO appointment (id, start_time, end_time, blood_center_id, is_premade)
VALUES ('dbf02dce-a9a1-4e20-aaf0-5fca92299407', '2022-12-21 05:00:00.000', '2022-12-21 05:30:00.000',
        '417c9b36-251a-4483-bfbf-abd3df786d96', true),
       ('9e16d8b0-a805-4ffb-a08b-d50e7fef6be0', '2022-12-21 06:30:00.000', '2022-12-21 07:00:00.000',
        '417c9b36-251a-4483-bfbf-abd3df786d96', true),
       ('f9854a8d-9dfb-49f5-abf4-d993fac9aac3', '2022-12-21 08:30:00.000', '2022-12-21 09:30:00.000',
        '417c9b36-251a-4483-bfbf-abd3df786d96', false),
       ('c41a832e-b94c-4c6a-b3fa-e743d5f954b4', '2022-12-21 09:30:00.000', '2022-12-21 10:30:00.000',
        '417c9b36-251a-4483-bfbf-abd3df786d96', true),

       ('da9734c6-d91c-47b3-9944-816f51957405', '2022-12-21 05:00:00.000', '2022-12-21 06:00:00.000',
        '417c9b36-251a-4483-bfbf-abd3df786d96', true),
       ('61156c70-936a-428f-91c5-04a9926dd8dc', '2022-12-21 07:30:00.000', '2022-12-21 09:00:00.000',
        '417c9b36-251a-4483-bfbf-abd3df786d96', true),
       ('0a738526-aae9-43f3-b81a-7f2caf792825', '2022-12-21 09:30:00.000', '2022-12-21 11:00:00.000',
        '417c9b36-251a-4483-bfbf-abd3df786d96', true),

       ('69b72768-92df-4bd5-ad0a-47bef7cec66e', '2022-12-21 08:30:00.000', '2022-12-21 10:00:00.000',
        '417c9b36-251a-4483-bfbf-abd3df786d96', true),

       ('be1bc87b-c7b5-4c6d-8149-b3078e3cc479', '2022-12-21 06:00:00.000', '2022-12-21 07:00:00.000',
        '417c9b36-251a-4483-bfbf-abd3df786d96', true),
       ('c166c577-e870-438f-bca8-0b441f348317', '2022-12-21 09:00:00.000', '2022-12-21 09:30:00.000',
        '417c9b36-251a-4483-bfbf-abd3df786d96', true),
       ('c1f74508-c3a1-4beb-b0d3-fe3399040071', '2022-12-21 09:30:00.000', '2022-12-21 10:30:00.000',
        '417c9b36-251a-4483-bfbf-abd3df786d96', true),
       ('9bdaeb58-381a-4586-919c-f923df6bdd39', '2022-12-21 11:15:00.000', '2022-12-21 11:30:00.000',
        '417c9b36-251a-4483-bfbf-abd3df786d96', true),

       ('9bdaeb58-381a-4586-917c-f923df6bdd39', '2023-12-21 11:15:00.000', '2023-12-21 11:30:00.000',
        '417c9b36-251a-4483-bfbf-abd3df786d96', true);

INSERT INTO appointment_staff (appointment_id, staff_id)
VALUES ('dbf02dce-a9a1-4e20-aaf0-5fca92299407', 'b2fe1aff-bb8f-4fd1-b88e-aa39af8fc65c'),
       ('9e16d8b0-a805-4ffb-a08b-d50e7fef6be0', 'b2fe1aff-bb8f-4fd1-b88e-aa39af8fc65c'),
       ('f9854a8d-9dfb-49f5-abf4-d993fac9aac3', 'b2fe1aff-bb8f-4fd1-b88e-aa39af8fc65c'),
       ('c41a832e-b94c-4c6a-b3fa-e743d5f954b4', 'b2fe1aff-bb8f-4fd1-b88e-aa39af8fc65c'),

       ('da9734c6-d91c-47b3-9944-816f51957405', '883f13cc-25c8-4b60-b0e5-5ac938ad2594'),
       ('61156c70-936a-428f-91c5-04a9926dd8dc', '883f13cc-25c8-4b60-b0e5-5ac938ad2594'),
       ('0a738526-aae9-43f3-b81a-7f2caf792825', '883f13cc-25c8-4b60-b0e5-5ac938ad2594'),

       ('9e16d8b0-a805-4ffb-a08b-d50e7fef6be0', '1e2df797-37b7-4241-a2b0-99e262a20912'),
       ('69b72768-92df-4bd5-ad0a-47bef7cec66e', '1e2df797-37b7-4241-a2b0-99e262a20912'),

       ('be1bc87b-c7b5-4c6d-8149-b3078e3cc479', 'fa41a439-0f90-46a8-96af-16bc1068d50c'),
       ('c166c577-e870-438f-bca8-0b441f348317', 'fa41a439-0f90-46a8-96af-16bc1068d50c'),
       ('c1f74508-c3a1-4beb-b0d3-fe3399040071', 'fa41a439-0f90-46a8-96af-16bc1068d50c'),
       ('9bdaeb58-381a-4586-919c-f923df6bdd39', 'fa41a439-0f90-46a8-96af-16bc1068d50c'),
       ('9bdaeb58-381a-4586-917c-f923df6bdd39', 'fa41a439-0f90-46a8-96af-16bc1068d50c');

INSERT INTO appointment_scheduling_history
(id, qrcode, issuing_date, status, appointment_id, blood_donor_id)
VALUES('7af0b527-5209-4c33-b7fb-2d0df7214728', 'QR', '2022-12-21 11:15:00.000', 3,
       '9e16d8b0-a805-4ffb-a08b-d50e7fef6be0', '07ce2e8b-d34b-4156-9dd4-f29ec4311675'),
      ('759415d9-b4d1-45ee-9067-f53b7f945f16', 'QR', '2022-12-21 11:15:00.000', 3,
       'f9854a8d-9dfb-49f5-abf4-d993fac9aac3', '26d3381b-319d-425c-abd7-256f24f0a2e0');



