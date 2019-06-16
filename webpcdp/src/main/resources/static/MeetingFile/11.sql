select * from user_tab_comments  ;

drop user bscopy;

create USER bscopy identified by bscopy;

grant exp_full_database,imp_full_database to bs;

grant imp_full_database to bscopy;

alter user bscopy quota unlimited on BOOKTBS1;

select * from bscopy.

select user,host,password from mysql.user;

grant create MATERIALIZED_VIEW to bscopy;
