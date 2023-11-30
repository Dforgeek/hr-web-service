create table if not exists roles(
                                    id serial,
                                    name varchar,

                                    primary key (id)
);

CREATE TABLE IF NOT EXISTS client(
                                     id serial,
                                     name varchar,
                                     phone int unique ,
                                     username varchar unique ,
                                     password varchar,
                                     login varchar unique ,





                                     primary key (id)


);

CREATE TABLE IF NOT EXISTS client_roles(
    roles_id int,
    client_id int,
    foreign key (roles_id) references roles(id),
    foreign key (client_id) references client(id),
    primary key (roles_id,client_id)

);
create table if not exists vacancy(
                                      id SERIAL,
                                      position varchar,
                                      company_name varchar,
                                      description varchar,
                                      conditions varchar,
                                      key_skills varchar,
                                      would_be_a_plus varchar,
                                      address varchar,
                                      salary float,
                                      creator_id int,
                                      is_business_trip_ready bool,
                                      required_experience float,
                                      employment varchar,
                                      schedule varchar,
                                      business_trips bool,
                                      foreign key (creator_id) references client(id),




                                      primary key (id)


);


Create TABLE if not exists resume (
                                      id SERIAL ,
                                      date_of_creation int,
                                      creator_id int,
                                      vacancy_id int,
                                      position varchar,
                                      sex varchar,
                                      age int,
                                      city varchar,
                                      is_relocation_ready boolean,
                                      is_business_trip_ready boolean,
                                      specializations varchar,
                                      employment varchar,
                                      work_schedule varchar,
                                      desired_salary float,
                                      total_experience float,
                                      previous_position varchar,
                                      skills varchar,
                                      language varchar,
                                      education varchar,
                                      courses varchar,
                                      has_car bool,
                                      driving_categories varchar,
                                      about_me varchar,
                                      job_search_status varchar,
                                      URL varchar,
                                      embedding varchar,



                                      foreign key (creator_id) references client(id),
                                      primary key (id)
);












