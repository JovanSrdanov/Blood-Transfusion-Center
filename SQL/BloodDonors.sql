-- Blood donor adresses first digit is changing
INSERT INTO public.address (id,city,country,latitude,longitude,"number",street) VALUES
   ('05886e19-2c10-41fd-8d1f-32af32b80b99','A','asdf',0.0,0.0,'a','asdf'),
   ('15886e19-2c10-41fd-8d1f-32af32b80b99','B','asdf',0.0,0.0,'a','asdf'),
   ('25886e19-2c10-41fd-8d1f-32af32b80b99','C','asdf',0.0,0.0,'a','asdf'),
   ('35886e19-2c10-41fd-8d1f-32af32b80b99','D','asdf',0.0,0.0,'a','asdf'),
   ('45886e19-2c10-41fd-8d1f-32af32b80b99','E','asdf',0.0,0.0,'a','asdf'),
   ('55886e19-2c10-41fd-8d1f-32af32b80b99','F','asdf',0.0,0.0,'a','asdf'),
   ('65886e19-2c10-41fd-8d1f-32af32b80b99','G','asdf',0.0,0.0,'a','asdf'),
   ('75886e19-2c10-41fd-8d1f-32af32b80b99','H','asdf',0.0,0.0,'a','asdf'),
   ('85886e19-2c10-41fd-8d1f-32af32b80b99','H','asdf',0.0,0.0,'a','asdf'),
   ('95886e19-2c10-41fd-8d1f-32af32b80b99','I','asdf',0.0,0.0,'a','asdf'),
   ('a5886e19-2c10-41fd-8d1f-32af32b80b99','J','asdf',0.0,0.0,'a','asdf'),
   ('b5886e19-2c10-41fd-8d1f-32af32b80b99','K','asdf',0.0,0.0,'a','asdf'),
   ('c5886e19-2c10-41fd-8d1f-32af32b80b99','L','asdf',0.0,0.0,'a','asdf');


  -- Blood donors first digit is changing for id and second and third  from the end for jmbg
INSERT INTO public.blood_donor (id,name,phone_number,surname,gender,institution,jmbg,occupation,penalties,points,address_id,questionnaire_id) VALUES
	 ('0c285f71-c0be-4a50-bf10-575a3364e6ab','Stefan','231243','Matkovic',0,'asdfa','1234567890003','asdfa',0,0,'05886e19-2c10-41fd-8d1f-32af32b80b99',NULL),
	 ('1c285f71-c0be-4a50-bf10-575a3364e6ab','Filip','231243','Mrdjanovic',0,'asdfa','1234567890013','asdfa',0,0,'05886e19-2c10-41fd-8d1f-32af32b80b99',NULL),
	 ('2c285f71-c0be-4a50-bf10-575a3364e6ab','Jovana','231243','Nedeljkovic',1,'asdfa','1234567890023','asdfa',0,0,'05886e19-2c10-41fd-8d1f-32af32b80b99',NULL),
	 ('3c285f71-c0be-4a50-bf10-575a3364e6ab','Dane','231243','Misic',0,'asdfa','1234567890033','asdfa',0,0,'05886e19-2c10-41fd-8d1f-32af32b80b99',NULL),
	 ('4c285f71-c0be-4a50-bf10-575a3364e6ab','Dejan','231243','Barcal',0,'asdfa','1234567890043','asdfa',0,0,'05886e19-2c10-41fd-8d1f-32af32b80b99',NULL),
	 ('5c285f71-c0be-4a50-bf10-575a3364e6ab','Sergej','231243','Madic',0,'asdfa','1234567890053','asdfa',0,0,'05886e19-2c10-41fd-8d1f-32af32b80b99',NULL),
	 ('6c285f71-c0be-4a50-bf10-575a3364e6ab','Nikola','231243','Vukic',0,'asdfa','1234567890063','asdfa',0,0,'05886e19-2c10-41fd-8d1f-32af32b80b99',NULL),
	 ('7c285f71-c0be-4a50-bf10-575a3364e6ab','Uros','231243','Spasenic',0,'asdfa','1234567890073','asdfa',0,0,'05886e19-2c10-41fd-8d1f-32af32b80b99',NULL),
	 ('8c285f71-c0be-4a50-bf10-575a3364e6ab','Srdjan','231243','Tosic',0,'asdfa','1234567890083','asdfa',0,0,'05886e19-2c10-41fd-8d1f-32af32b80b99',NULL),
	 ('9c285f71-c0be-4a50-bf10-575a3364e6ab','Stefan','231243','Kalicanin',0,'asdfa','1234567890093','asdfa',0,0,'05886e19-2c10-41fd-8d1f-32af32b80b99',NULL),
	 ('ac285f71-c0be-4a50-bf10-575a3364e6ab','Andrea','231243','Lojpur',1,'asdfa','1234567890103','asdfa',0,0,'05886e19-2c10-41fd-8d1f-32af32b80b99',NULL),
	 ('bc285f71-c0be-4a50-bf10-575a3364e6ab','Milan','231243','Vracar',0,'asdfa','1234567890113','asdfa',0,0,'05886e19-2c10-41fd-8d1f-32af32b80b99',NULL),
	 ('cc285f71-c0be-4a50-bf10-575a3364e6ab','Ilija','231243','Cvetkovic',0,'asdfa','1234567890123','asdfa',0,0,'05886e19-2c10-41fd-8d1f-32af32b80b99',NULL);


-- Blood donor accounts first digit is changing for id, mail is incrementing also
INSERT INTO public.account (id,email,is_activated,last_password_update_date,"password",person_id) VALUES
	 ('0cc5196a-fb16-4394-a259-c5c81017cfac','isadonor+3@gmail.com',true,NULL,'$2a$10$P9XieoF311mu20jQIwSoMeNdqV3KBiIhTTZCM4QEITiSbksSsvCha','0c285f71-c0be-4a50-bf10-575a3364e6ab'),
	 ('1cc5196a-fb16-4394-a259-c5c81017cfac','isadonor+4@gmail.com',true,NULL,'$2a$10$P9XieoF311mu20jQIwSoMeNdqV3KBiIhTTZCM4QEITiSbksSsvCha','1c285f71-c0be-4a50-bf10-575a3364e6ab'),
	 ('2cc5196a-fb16-4394-a259-c5c81017cfac','isadonor+5@gmail.com',true,NULL,'$2a$10$P9XieoF311mu20jQIwSoMeNdqV3KBiIhTTZCM4QEITiSbksSsvCha','2c285f71-c0be-4a50-bf10-575a3364e6ab'),
	 ('3cc5196a-fb16-4394-a259-c5c81017cfac','isadonor+6@gmail.com',true,NULL,'$2a$10$P9XieoF311mu20jQIwSoMeNdqV3KBiIhTTZCM4QEITiSbksSsvCha','3c285f71-c0be-4a50-bf10-575a3364e6ab'),
	 ('4cc5196a-fb16-4394-a259-c5c81017cfac','isadonor+7@gmail.com',true,NULL,'$2a$10$P9XieoF311mu20jQIwSoMeNdqV3KBiIhTTZCM4QEITiSbksSsvCha','4c285f71-c0be-4a50-bf10-575a3364e6ab'),
	 ('5cc5196a-fb16-4394-a259-c5c81017cfac','isadonor+8@gmail.com',true,NULL,'$2a$10$P9XieoF311mu20jQIwSoMeNdqV3KBiIhTTZCM4QEITiSbksSsvCha','5c285f71-c0be-4a50-bf10-575a3364e6ab'),
	 ('6cc5196a-fb16-4394-a259-c5c81017cfac','isadonor+9@gmail.com',true,NULL,'$2a$10$P9XieoF311mu20jQIwSoMeNdqV3KBiIhTTZCM4QEITiSbksSsvCha','6c285f71-c0be-4a50-bf10-575a3364e6ab'),
	 ('7cc5196a-fb16-4394-a259-c5c81017cfac','isadonor+10@gmail.com',true,NULL,'$2a$10$P9XieoF311mu20jQIwSoMeNdqV3KBiIhTTZCM4QEITiSbksSsvCha','7c285f71-c0be-4a50-bf10-575a3364e6ab'),
	 ('8cc5196a-fb16-4394-a259-c5c81017cfac','isadonor+11@gmail.com',true,NULL,'$2a$10$P9XieoF311mu20jQIwSoMeNdqV3KBiIhTTZCM4QEITiSbksSsvCha','8c285f71-c0be-4a50-bf10-575a3364e6ab'),
	 ('9cc5196a-fb16-4394-a259-c5c81017cfac','isadonor+12@gmail.com',true,NULL,'$2a$10$P9XieoF311mu20jQIwSoMeNdqV3KBiIhTTZCM4QEITiSbksSsvCha','9c285f71-c0be-4a50-bf10-575a3364e6ab'),
	 ('acc5196a-fb16-4394-a259-c5c81017cfac','isadonor+13@gmail.com',true,NULL,'$2a$10$P9XieoF311mu20jQIwSoMeNdqV3KBiIhTTZCM4QEITiSbksSsvCha','ac285f71-c0be-4a50-bf10-575a3364e6ab'),
	 ('bcc5196a-fb16-4394-a259-c5c81017cfac','isadonor+14@gmail.com',true,NULL,'$2a$10$P9XieoF311mu20jQIwSoMeNdqV3KBiIhTTZCM4QEITiSbksSsvCha','bc285f71-c0be-4a50-bf10-575a3364e6ab'),
	 ('ccc5196a-fb16-4394-a259-c5c81017cfac','isadonor+15@gmail.com',true,NULL,'$2a$10$P9XieoF311mu20jQIwSoMeNdqV3KBiIhTTZCM4QEITiSbksSsvCha','cc285f71-c0be-4a50-bf10-575a3364e6ab');
