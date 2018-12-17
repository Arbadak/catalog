delete from office where organization_id in (select id from organization where short_name='Йорик');
delete from organization where short_name='Йорик';
/*update hibernate_sequence set next_val = next_val-2;*/
alter sequence HIBERNATE_SEQUENCE restart with 9;
