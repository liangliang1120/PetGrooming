  CREATE TABLE employee
 (emp_id INTEGER PRIMARY KEY AUTO_INCREMENT, 
  emp_name VARCHAR(20),
	is_supervisor boolean);
	
	insert into employee value(emp_id, 'Peter', FALSE);
	insert into employee value(emp_id, 'Alice', FALSE);
	
CREATE TABLE schedule
 (s_id INTEGER PRIMARY KEY AUTO_INCREMENT, 
 emp_id INTEGER,
  work_time VARCHAR(30),
	available boolean);
	
	insert into schedule value(s_id, 1, '2022-04-09 09:00:00', TRUE);
	insert into schedule value(s_id, 1, '2022-04-09 10:00:00', TRUE);
	insert into schedule value(s_id, 1, '2022-04-09 11:00:00', TRUE);
	
	CREATE TABLE customer
 (cus_id INTEGER PRIMARY KEY AUTO_INCREMENT, 
  cus_name VARCHAR(20),
	gender boolean,
	birthday DATE, 
	phone_num VARCHAR(20));
	
	insert into customer value(cus_id, 'Nancy', 0, '1990-03-26', '999-888-7777');
	insert into customer value(2, 'John', 1, '1991-05-15', '444-555-6666');
	insert into customer value(cus_id, 'Grace', 1, '1992-06-07', '111-222-3333');
	
	CREATE TABLE pet
 (pet_id INTEGER PRIMARY KEY AUTO_INCREMENT, 
  pet_name VARCHAR(20),
	gender boolean,
	birthday DATE, 
	cus_id INTEGER);
	

	insert into pet value(pet_id, 'Snow Ball', 0, '2021-06-16', 1);
	insert into pet value(pet_id, 'Wang Cai', 0, '2021-03-18', 1);
	insert into pet value(pet_id, 'Snoopy', 1, '2020-04-17', 2);
	insert into pet value(pet_id, 'Lucky', 1, '2020-05-19', 3);
	insert into pet value(pet_id, 'Pikachu', 1, '2020-05-09', 4);
-- 	delete FROM pet where pet_id = 7
-- UPDATE pet SET pet_name = "Pluto", gender = true, birthday = "2019-01-01" cus_id = ? WHERE pet_id = ?
	
	-- Mickey， Minnie， Goofy， Datsy，Pluto，Donald，
	
	CREATE TABLE book
 (book_id INTEGER PRIMARY KEY AUTO_INCREMENT, 
  cus_id INTEGER,
	service_kind INTEGER,
	s_id INTEGER,
	emp_id INTEGER,
	pet_id INTEGER);
	
	insert into book value(book_id, 1, 0, 1, 1, 1);
	insert into book value(book_id, 1, 0, 2, 1, 1);
	
	