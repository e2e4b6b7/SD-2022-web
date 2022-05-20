create table Homework
(
	id               serial primary key,
	title            text,
	publication_time timestamp not null,
	deadline         timestamp,
	description      text
);

create table Checker
(
	id      serial primary key,
	command text not null
);

create table HomeworkCheckers
(
	homework_id int references Homework,
	checker_id  int references Checker,

	primary key (homework_id, checker_id)
);

create table Submission
(
	id              serial primary key,
	homework_id     int references Homework not null,
	submission_time timestamp               not null,
	solution        text                    not null
);

create table SubmissionCheck
(
	id             serial primary key,
	submission_id  int references Submission not null,
	checker_id     int references Checker    not null,
	mark           bool                      not null,
	checker_output text                      not null
);
