create table question
(
	id int auto_increment,
	title varchar(50),
	description TEXT,
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT,
	CREATOR_ID int,
	COMMENT_COUNT int default 0,
	VIEW_COUNT int default 0,
	LIKE_CONUNT int default 0,
	tag VARCHAR(256),
	constraint question_pk
		primary key (id)
);

