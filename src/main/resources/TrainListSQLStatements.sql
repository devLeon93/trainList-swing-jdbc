Select t.name,
       t.number,
       t.type,
       s.time_going
From Station s
         INNER JOIN Cities c on s.city_id = c.id
         INNER JOIN Train t on s.train_id = t.id
where s.city_id = 5
  and s.time_going != '00:00:00'
  and s.time_going >= CONVERT('2022-05-27', DATE)
  and t.id in (select train_id from Station where time_going > '00:00:00');


select * from Station;
select * from Train;
select * from Cities;


desc Station;
desc Train;
desc Cities;


create table Train (
  id int auto_increment not null primary key,
  name varchar(20) not null,
  number varchar(20),
  type varchar(20)
);

create table Cities (
  id int auto_increment not null primary key,
  name varchar(20)
);

create table Station (

  city_id  int auto_increment not null primary key,
  train_id int not null,
  time_going time,
  foreign key (train_id) references Train(id),
  foreign key (city_id) references Cities(id)
);

INSERT INTO Train (id, name, number, type)
VALUES (1, 'Aurora', '102С/102M', 'Passenger_train'),
       (2, 'Piterburb', '042В/041M', 'Premium'),
       (3, 'Moscow', '042В/058M', 'Rapid'),
       (4, 'Bridge', '102С/102L', 'Double_Decker'),
       (5, 'Sputnik', '129/106K', 'Premium'),
       (6, 'Putztucher', '130/106K', 'Rapid'),
       (7, 'Jaguar', '136/109K', 'Double Decker'),
       (8, 'Tucano', '98С/102M', 'Premium'),
       (9, 'Cosmenitro', '980С/102M', 'Passenger_train'),
       (10, 'Embrava', '852С/362M', 'Double Decker');


INSERT INTO Station (city_id, train_id, time_going)
VALUES (1, 1, '19:30:10'),
       (2, 2, '15:30:12'),
       (3, 3, '12:15:34'),
       (4, 4, '10:05:12'),
       (5, 5, '09:15:13'),
       (6, 6, '08:20:14'),
       (7, 7, '11:35:13'),
       (8, 8, '22:15:03'),
       (9, 9, '00:00:00'),
       (10, 10, '07:20:10');


INSERT INTO Cities (id, name)
VALUES (1, 'Ungheni'),
       (2, 'Cahul'),
       (3, 'Comrat'),
       (4, 'Soroca'),
       (5, 'Leova'),
       (6, 'Dubăsari'),
       (7, 'Călărași'),
       (8, 'Ocnița'),
       (9, 'Tiraspol'),
       (10, 'Cricova');


UPDATE Cities
set name = 'Leova'
where id = 7;

DROP TABLE Train;
DROP TABLE Cities;
DROP TABLE Station;

/*
jdbc:mysql://localhost:3306/trainlist_db
user_leonid_db
root1993
*/

