CREATE TABLE IF NOT EXISTS INGREDIENT (

  id varchar(4) not null,
  name varchar(25) not null,
  type varchar(10) not null
);

CREATE TABLE IF NOT EXISTS TACO (

  id identity,
  name varchar(50) not null,
  created_at timestamp not null
);

CREATE TABLE IF NOT EXISTS TACO_INGREDIENTS (

  taco_id bigint not null,
  ingredients_id varchar(4) not null
);

ALTER TABLE TACO_INGREDIENTS ADD FOREIGN KEY (taco_id) REFERENCES TACO(id);

ALTER TABLE TACO_INGREDIENTS ADD FOREIGN KEY (ingredients_id) REFERENCES INGREDIENT(id);

CREATE TABLE IF NOT EXISTS TACO_ORDER (

  id identity,
  name varchar(50) not null,
  street varchar(50) not null,
  city varchar(50) not null,
  state varchar(2) not null,
  zip varchar(10) not null,
  cc_number varchar(16) not null,
  cc_expiration varchar(5) not null,
  cvv varchar(3) not null,
  placed_at timestamp not null
);

CREATE TABLE IF NOT EXISTS TACO_ORDER_TACOS (

  order_id bigint not null,
  tacos_id bigint not null
);

ALTER TABLE TACO_ORDER_TACOS ADD FOREIGN KEY (order_id) REFERENCES TACO_ORDER(id);

ALTER TABLE TACO_ORDER_TACOS ADD FOREIGN KEY (tacos_id) REFERENCES TACO(id);